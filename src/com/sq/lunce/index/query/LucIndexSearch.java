package com.sq.lunce.index.query;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.sq.lunce.index.Constant;

public class LucIndexSearch {

	private String serverName;
	private long startTime;
	private long endTime;
	private String type;
	private String[] queryParas;
	private String[] queryValues;
	private String indexFilePath;
	private List<Map<String, String>> datas;

	public LucIndexSearch(String indexFilePath, String serverName,	String type, long startTime, long endTime,String[] paras,String [] vals) {
		this.indexFilePath = indexFilePath;
		this.serverName = serverName;
		this.startTime = startTime;
		this.type = type;
		this.endTime = endTime;
		this.queryParas = paras;
		this.queryValues = vals;
	}

	public void search() throws Exception {
		BooleanQuery booleanQuery = createQuery();
		searchAndShowResult(booleanQuery);
	}

	private BooleanQuery createQuery() {
		BooleanQuery booleanQuery = new BooleanQuery();
		Query queryServer = new TermQuery(new Term(Constant.LUC_SERVER_KEY,	serverName));
		booleanQuery.add(queryServer, Occur.MUST);

		TermRangeQuery queryTime = TermRangeQuery
				.newStringRange(Constant.LUC_LOG_TIME, "" + startTime, ""+ endTime, true, true);
		booleanQuery.add(queryTime, Occur.MUST);

		Query queryType = new TermQuery(new Term(Constant.LUT_LOG_TYPE_NAME, type));
		booleanQuery.add(queryType, Occur.MUST);
		for (int i = 0; i < queryParas.length; i++) {
			Query query = new TermQuery(new Term(queryParas[i], queryValues[i]));
			booleanQuery.add(query, Occur.MUST);
		}
		return booleanQuery;
	}

	public String getIndexFilePath() {
		return indexFilePath;
	}

	public void setIndexFilePath(String indexFilePath) {
		this.indexFilePath = indexFilePath;
	}

	private void searchAndShowResult(Query query) throws Exception {
		Directory dir = FSDirectory.open(new File(indexFilePath));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		long start = System.currentTimeMillis();

		TopDocs topdocs = searcher.search(query, 100);
		System.out.println("查询结果总数---" + topdocs.totalHits + "最大的评分--"
				+ topdocs.getMaxScore());
		ScoreDoc[] scoreDocs = topdocs.scoreDocs;
		datas = new ArrayList<Map<String, String>>();
		for (int i = 0; i < scoreDocs.length; i++) {
			int doc = scoreDocs[i].doc;
			Document document = searcher.doc(doc);
			List<IndexableField> fields = document.getFields();
			Map<String, String> data = new HashMap<String, String>();
			for (IndexableField fi : fields) {
				String name = fi.name();
				data.put(name, document.get(name));
			}
			datas.add(data);
		}
		reader.close();
		System.out.println("读取索引：耗时：" + (System.currentTimeMillis() - start));
	}

	public List<Map<String, String>> getDatas() {
		return datas;
	}
}
