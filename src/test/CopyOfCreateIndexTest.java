package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.junit.Test;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.sq.shell.currency.ThreadPool;

public class CopyOfCreateIndexTest {
	BlockingArrayQueue<String> queue = new BlockingArrayQueue<String>();
	boolean loadIndexDataFlag = false;
	String indexFilePath = "D:/demo/index/index_small/";
	boolean createIndexFlag = false;
	/**
	 * 1. 测试java io性能 2. 测试shell 命令方式的性能 3. 测试luc的方式进行创建、查询的性能
	 */
	

	long count = 0;

	@Test
	public void catAllDatas() throws Exception {
		
		String cmd = "cat /data2/3dgame_log/tianxiawushuang/3d_log_game3/3dgame/billingCenter.log";
		Session sess = null;
		InputStream stdout = null;
		BufferedReader br = null;
		try {
			long start = System.currentTimeMillis();
			newThread();
			sess = WriterFactory.loadSSH2ConnectionInstance().getConn("117.121.17.32", "22201").openSession();
			sess.execCommand(cmd);
			 stdout = new StreamGobbler(sess.getStdout());
			 br = new BufferedReader(new InputStreamReader(stdout, "UTF-8"));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					this.loadIndexDataFlag = true;
					System.out.println("load Ok");
					break;
				}
				queue.put(line);
			}
			while (!createIndexFlag) {
			}
			System.out.println("耗时：" + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(br!=null){
				br.close();
			}
			if(stdout != null){
				stdout.close();
			}
			if (sess != null)
				sess.close();
			System.out.println("reader end");
		}
	}

	private void newThread() {
		System.out.println("启动创建索引线程");
		ThreadPool.getInstance().execute(new Runnable() {

			public void run() {
				IndexWriter writer = null;
				try {
					writer = WriterFactory.getWriter(indexFilePath);
					while (!(loadIndexDataFlag && queue.size() == 0)) {
						createIndex(writer);
					}
					
					createIndexFlag = true;
					System.out.println("444");
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					System.out.println("线程结束,DOC_count=" +writer.numDocs() +" count="+count);
					try {
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void createIndex(IndexWriter writer) throws IOException {
		long operCount = queue.size();
		for (int i = 0; i < operCount; i++) {
			String data = queue.poll();
			Document doc = WriterFactory.indexDoc(data);
			if (doc.getFields().size() > 0){
				count ++;
				if(writer == null){
					System.out.println("null");
				}
				writer.addDocument(doc);
			}
			if(count % 1000 == 0){
				System.out.println("jindu DOC_count=" +writer.numDocs() +" count="+count);
			}
		}
	}

}
