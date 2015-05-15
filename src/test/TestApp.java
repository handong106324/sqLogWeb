package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.sq.shell.connection.ConnectionFactory;

public class TestApp {
	private String fp = "D:/demo/index/index_small";

	// 关键词查询
	@Test
	public void testTermQuery() throws Exception {
		// 对应的查询字符串为：title:lucene
		TermQuery query = new TermQuery(new Term("用户", "360USER_605459541"));
		searchAndShowResult(query);
	}

	// 通配符查询
	// ? 表示一个任意字符
	// * 表示0或多个任意字符
	@Test
	public void testWildcardQuery() throws Exception {
		// 对应的查询字符串为：title:lu*n?
		// WildcardQuery query = new WildcardQuery(new Term("title", "lu*n?"));

		// 对应的查询字符串为：content:互?网
		WildcardQuery query = new WildcardQuery(new Term("用户", "91USER*"));
		searchAndShowResult(query);
	}

	// 查询所有
	@Test
	public void testMatchAllDocsQuery() throws Exception {
		// 对应的查询字符串为：*:*
		MatchAllDocsQuery query = new MatchAllDocsQuery();
		searchAndShowResult(query);
	}

	// 模糊查询
	@Test
	public void testFuzzyQuery() throws Exception {
		// 对应的查询字符串为：title:lucenX~0.9
		// 第二个参数是最小相似度，表示有多少正确的就显示出来，比如0.9表示有90%正确的字符就会显示出来。
		FuzzyQuery query = new FuzzyQuery(new Term("用户", "360USER_605459541"));
		searchAndShowResult(query);
	}

	// 范围查询
	// 仅针对NumbericField哦
	@Test
	public void testNumericRangeQuery() throws Exception {
		// 对应的查询字符串为：id:[5 TO 15]
		// NumericRangeQuery query = NumericRangeQuery.newIntRange("id", 5, 15,
		// true, true);

		// 对应的查询字符串为：id:{5 TO 15}
		// NumericRangeQuery query = NumericRangeQuery.newIntRange("id", 5, 15,
		// false, false);

		// 对应的查询字符串为：id:[5 TO 15}
		
		TermRangeQuery query = TermRangeQuery.newStringRange("角色等级", "55", "60", true, true);
//		NumericRangeQuery query = NumericRangeQuery.newIntRange("角色等级", 50, 60,
//				true, true);

		searchAndShowResult(query);
	}

	@Test
	public void testLevel() throws Exception{
			// 对应的查询字符串为：title:lucene
			TermQuery query = new TermQuery(new Term("角色等级", "60"));
			searchAndShowResult(query);
	}
	
	// 布尔查询
	@Test
	public void testBooleanQuery() throws Exception {
		BooleanQuery booleanQuery = new BooleanQuery();
		// booleanQuery.add(query, Occur.MUST); // 必须满足
		// booleanQuery.add(query, Occur.SHOULD); // 多个SHOULD一起用表示OR的关系
		// booleanQuery.add(query, Occur.MUST_NOT); // 非

		Query query1 = new TermQuery(new Term("title", "lucene"));
		Query query2 = NumericRangeQuery.newIntRange("id", 5, 15, false, true);

		// // 对应的查询字符串为：+title:lucene +id:{5 TO 15]
		// // 对应的查询字符串为（大写的AND）：title:lucene AND id:{5 TO 15]
		// booleanQuery.add(query1, Occur.MUST);
		// booleanQuery.add(query2, Occur.MUST);

		// 对应的查询字符串为：title:lucene id:{5 TO 15]
		// 对应的查询字符串为：title:lucene OR id:{5 TO 15]
		// booleanQuery.add(query1, Occur.SHOULD);
		// booleanQuery.add(query2, Occur.SHOULD);

		// 对应的查询字符串为：+title:lucene -id:{5 TO 15]
		// 对应的查询字符串为：title:lucene (NOT id:{5 TO 15] )
		booleanQuery.add(query1, Occur.MUST);
		booleanQuery.add(query2, Occur.MUST_NOT);

		searchAndShowResult(booleanQuery);
	}

	/**
	 * 
	 */
	@Test
	  public void parse() throws Exception {
		BooleanQuery booleanQuery = new BooleanQuery();
		Query query1 = new TermQuery(new Term("用户", "91USER_683462853"));
		Query query2 = new TermQuery(new Term("消费金额", "36900"));
		booleanQuery.add(query1, Occur.MUST);
		booleanQuery.add(query2, Occur.MUST);

		searchAndShowResult(booleanQuery);
	  }
	@Test
	  public void MutiTest() throws Exception {
		String[] quis = new String[]{"用户","消费金额"};
		String[] quVals = new String[]{"91USER_683462853","36900"};
		Occur[] ocs = new Occur[]{Occur.MUST,Occur.MUST};
		Query booleanQuery = createMutiQuery( quVals,quis,ocs);
		searchAndShowResult(booleanQuery);
	  }
	
	 public  Query createMutiQuery(String[] queries,String [] queryFileds,Occur[] ocs) throws ParseException{  
//	    	MultiFieldQueryParser parser = new MultiFieldQueryParser(queryFileds, getAnalyzer());  
	        Query query = MultiFieldQueryParser.parse(Version.LUCENE_47,queries, queryFileds,ocs, new StandardAnalyzer(Version.LUCENE_47));  
	        return query;  
	   }  
	
	/**
	 * 测试搜索的工具方法
	 * 
	 * @param query
	 * @throws Exception
	 */
	private void searchAndShowResult(Query query) throws Exception {
		File indexDir = new File(fp);
		Directory dir = FSDirectory.open(indexDir);
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		long start = System.currentTimeMillis();

		TopDocs topdocs = searcher.search(query, 100);
		System.out.println("查询结果总数---" + topdocs.totalHits + "最大的评分--"
				+ topdocs.getMaxScore());
		reader.close();
		System.out.println("读取索引：耗时：" + (System.currentTimeMillis() - start));
	}
	
}
