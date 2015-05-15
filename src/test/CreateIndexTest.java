package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.eclipse.jetty.util.BlockingArrayQueue;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.jfinal.kit.PathKit;
import com.sq.base.BaseAction;
import com.sq.base.SqageActionInterface;
import com.sq.common.Constant;
import com.sq.log.result.LogResultInfo;
import com.sq.shell.connection.ConnectionFactory;
import com.sq.shell.currency.ThreadPool;
import com.sq.utils.PropertiesUtils;
import org.junit.Test;
public class CreateIndexTest{
	BlockingArrayQueue<String> queue = new BlockingArrayQueue<String>();
	boolean loadIndexDataFlag = false;
	String indexFilePath;
	 IndexWriter writer;
	private  String jdbcFile = "jdbc.properties";
	private  void init() throws Exception {
		String key = PropertiesUtils.getPropertiesValue(jdbcFile, "shell_key");
		String userName = PropertiesUtils.getPropertiesValue(jdbcFile,
				"shell_username");
		String pwd = PropertiesUtils.getPropertiesValue(jdbcFile,
				"shell_password");
		ConnectionFactory.getInstance().init(key, userName, pwd,true);
	
		indexFilePath="D:/demo/index/index";
        System.out.println("index路径："+indexFilePath);
        File   indexDir = new File(indexFilePath); 
        dir = FSDirectory.open(indexDir);
        
//        writer=WriterFactory.getWriter(dir);
	}
	
	/**
	 * 1. 测试java io性能
	 * 2. 测试shell 命令方式的性能
	 * 3. 测试luc的方式进行创建、查询的性能
	 */
	boolean isAllFlag = true;
	String filePath = "";
	

	long count = 0 ;
	@Test
	public void catAllDatas() throws Exception {
			init();
			String cmd = "cat /data2/3dgame_log/tianxiawushuang/3d_log_game3/3dgame/billingCenter.log.2015-01-30.log" ;
			Session sess = null;
			int index = 0 ;
			 
			try {
		        long start = System.currentTimeMillis();
				newThread();
				sess = ConnectionFactory.getInstance().getConn("117.121.17.32", "22201").openSession();
				sess.execCommand(cmd);
				InputStream stdout = new StreamGobbler(sess.getStdout());
				BufferedReader br = new BufferedReader(new InputStreamReader(stdout, "UTF-8"));
				while (true) {
					String line = br.readLine();
					if(count%1000 ==1){
						System.out.println(count+":"+queue.size());
					}
					if (line == null){
						this.loadIndexDataFlag = true;
						break;
					}
					queue.put(line);
					if(!isAllFlag && ++index  >= 100){
						break;
					}
				}
				System.out.println("耗时："+(System.currentTimeMillis() - start)+"count="+count);
			} catch (Exception e) {
				e.getStackTrace();
			} finally {
				if (sess != null)
					sess.close();
			}
	}

	private void newThread() {
		
		ThreadPool.getInstance().execute(new Runnable() {
			
			public void run() {
				try {
					while(!(loadIndexDataFlag && queue.size() ==0)){
							count += createIndex(writer);
					}
					createOk(indexFilePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	Directory dir;
	 private long createIndex(IndexWriter writer) throws IOException  {
		        long operCount = queue.size();
		        for(int i = 0 ; i < operCount ; i++){
		        	String data = queue.poll();
		        	Document doc = indexDocPer(data);
		            writer.addDocument(doc);
		        }
		return operCount;
	}

	 
	 private void createOk(String indexFilePath2) throws IOException {
			// TODO Auto-generated method stub
			File fi = new File(indexFilePath2+"in.ok");
			fi.createNewFile();
			writer.close();
		}

	 public Document indexDocPer(String log){
		 Document doc = new Document();
		 doc.add(new StringField("content",log,Store.YES));
		 return doc;
	 }
	 
		private Document indexDoc(String logSt) {
	    	String logStr = logSt.replaceAll("\\[", Constant.SQ_SEPARATOR).replaceAll("\\]",  Constant.SQ_SEPARATOR);
			logStr = logStr.replaceAll("\\{", Constant.SQ_SEPARATOR).replaceAll("\\}",  Constant.SQ_SEPARATOR);
			logStr = logStr.replaceAll(",", Constant.SQ_SEPARATOR);

			String[] logStrs = logStr.split(Constant.SQ_SEPARATOR);
			Document doc = new Document();
			for(String log :logStrs){
				if(isNotBlank(log)){
					if(log.contains(":")){
						String[] vs = log.split(":");
						if(vs.length == 2){
							doc.add(new StringField(vs[0].trim(), vs[1], Store.YES));
						}
					}
				}
			}
			return doc;
		}
	 
		   
	    public boolean isNotBlank(String s){
	    	if(s == null || (s!= null && s.length() ==0)) return false;
	    	return true;
	    	
	    }
	    
	
	public List<String> runCMD(String cmd,boolean isAll){
		Session sess = null;
		List<String> result = new ArrayList<String>();
		long start = System.currentTimeMillis();
		int index = 0 ;
		try {
			
			sess = ConnectionFactory.getInstance().getConn("117.121.17.32", "22201").openSession();
			sess.execCommand(cmd);
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout, "UTF-8"));
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				result.add(line);
				if(!isAll && ++index  >= 100){
					break;
				}
			}
			System.out.println("耗时："+(System.currentTimeMillis() - start)+" 结果="+result.size());
		} catch (IOException e) {
			e.getStackTrace();
		} finally {
			if (sess != null)
				sess.close();
		}
		return result;
	}
}
