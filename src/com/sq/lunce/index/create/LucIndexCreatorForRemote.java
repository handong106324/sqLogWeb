package com.sq.lunce.index.create;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.eclipse.jetty.util.BlockingArrayQueue;

import test.WriterFactory;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.sq.shell.currency.ThreadPool;

public class LucIndexCreatorForRemote {

	Logger logger = Logger.getLogger(getClass());
	BlockingArrayQueue<String> queue = new BlockingArrayQueue<String>();
	private boolean loadIndexDataFlag = false, createIndexFlag = false;
	private long count = 0;
	private String fileName = "";
	
	private String indexFilePath ;
	private String ip;
	private String port;
	private String filePath;
	private String okPath;
	public LucIndexCreatorForRemote(String fileName,String indexFilePath,String filePath, String ip, String port,String okPath){
		this.indexFilePath = indexFilePath;
		this.okPath = okPath;
		this.ip = ip;
		this.port = port;
		this.fileName = fileName;
		this.filePath = filePath;
	}

	public void createIndex() {
		if(WriterFactory.indexIsOk(fileName)){
			logger.debug("[LucIndexCreator]["+fileName+"索引已经存在]");
			return;
		}
		
		newThread();
		
		String cmd = "cat " + filePath;
		Session sess = null;
		InputStream stdout = null;
		BufferedReader br = null;
		try {
			long start = System.currentTimeMillis();

			sess = WriterFactory.loadSSH2ConnectionInstance().getConn(ip, port).openSession();
			sess.execCommand(cmd);
			stdout = new StreamGobbler(sess.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout, "UTF-8"));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					this.loadIndexDataFlag = true;
					logger.debug("[LucIndexCreator][createIndexRemoteFile][filePath="
							+ filePath
							+ "][加载完成][耗时："
							+ (System.currentTimeMillis() - start) + "][]");
					break;
				}
				queue.put(line);
			}
			while(!createIndexFlag){};
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (stdout != null) {
					stdout.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sess != null)
				sess.close();
			System.out.println("read end");
		}
	}

	private void newThread() {
		ThreadPool.getInstance().execute(new Runnable() {

			public void run() {
				try {
					IndexWriter writer = WriterFactory.getWriter(indexFilePath);
					while (!(loadIndexDataFlag && queue.size() == 0)) {
						createIndex(writer);
					}
					writer.close();
					createIndexFlag = true;
					System.out.println("[LucIndexCreator][newThread][创建索引线程执行完成][创建完成标识:"
						+ createIndexFlag + "]");
					File okFile = new File(okPath+fileName+".ok");
					okFile.createNewFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.debug("[LucIndexCreator][newThread][创建索引线程执行完成][创建完成标识:"
						+ createIndexFlag + "]");
			}
		});
	}

	private void createIndex(IndexWriter writer) throws IOException {
		long operCount = queue.size();
		for (int i = 0; i < operCount; i++) {
			String data = queue.poll();
			Document doc = WriterFactory.indexDoc(data);
			if (doc != null && doc.getFields().size() > 0) {
				count++;
				writer.addDocument(doc);
			}else {
				logger.error("[LurIndexCreator][createIndex][生成document失败][data="+data+"]");
			}
		}
	}

	public long getCount() {
		return count;
	}
}
class CreatorThread{
	
}
