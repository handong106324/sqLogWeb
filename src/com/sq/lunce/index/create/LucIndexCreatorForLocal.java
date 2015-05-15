package com.sq.lunce.index.create;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import test.WriterFactory;

public class LucIndexCreatorForLocal {

	Logger logger = Logger.getLogger(getClass());
	private long count = 0;
	private IndexWriter writer;
	private String indexFilePath ;
	private File dataFile;
	private String okPath;
	public LucIndexCreatorForLocal(String indexFilePath,File dataFile,String ok_path) {
		this.indexFilePath = indexFilePath;
		this.dataFile = dataFile;
		this.okPath = ok_path;
	}


	public void createIndex() {
		if(indexIsOk(dataFile.getName())){
			logger.debug("[LucIndexCreator]["+dataFile.getName()+"索引已经存在]");
			return;
		}
		try {
			writer = WriterFactory.getWriter(indexFilePath);
			long start = System.currentTimeMillis();
			if (dataFile.isFile()) {

				InputStream stream = new FileInputStream(dataFile);
				BufferedReader logReader = new BufferedReader(
						new InputStreamReader(stream, "UTF-8"));
				String data = null;
				while ((data = logReader.readLine()) != null) {
					Document doc = WriterFactory.indexDoc(data);
					if (doc != null && doc.getFields().size() > 0) {
						writer.addDocument(doc);
					}else {
						logger.error("[LurIndexCreator][createIndex][生成document失败][data="+data+"]");
					}
				}
				logger.debug("[LurIndexCreator][createIndexLocalFiles][通过本地文档创建索引][操作文件："	+ dataFile.getAbsolutePath() + "][]");
			}
			logger.debug("[LurIndexCreator][createIndexLocalFiles][通过本地文档创建索引][耗时："	+ (System.currentTimeMillis() - start) + "][完成]");
			writer.close();
			recordOk();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	private void recordOk() throws IOException {
		File okFile = new File(okPath+"/"+dataFile.getName()+".ok");
		okFile.createNewFile();
	}


	private boolean indexIsOk(String name) {
		return WriterFactory.indexIsOk(name);
	}

	public long getCount() {
		return count;
	}
}
