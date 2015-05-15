package test;

import java.io.File;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.sq.common.Constant;
import com.sq.shell.connection.ConnectionFactory;
import com.sq.utils.PropertiesUtils;

public class WriterFactory {
	public static String jdbcFile = "jdbc.properties";
	public static ConnectionFactory loadSSH2ConnectionInstance(){
		String key = PropertiesUtils.getPropertiesValue(jdbcFile, "shell_key");
		String userName = PropertiesUtils.getPropertiesValue(jdbcFile,"shell_username");
		String pwd = PropertiesUtils.getPropertiesValue(jdbcFile,"shell_password");
		ConnectionFactory.getInstance().init(key, userName, pwd,true);
		return ConnectionFactory.getInstance();
	}
	
	public static IndexWriter getWriter(String indexFilePath) throws Exception{
        File   indexDir = new File(indexFilePath); 
        Directory dir = FSDirectory.open(indexDir);
        
		Analyzer analyzer=new StandardAnalyzer(Version.LUCENE_47);
		IndexWriterConfig iwc=new IndexWriterConfig(Version.LUCENE_47,analyzer);
		IndexWriter instacne =  new IndexWriter(dir,iwc);
		return instacne;
	}
	
	public static Document indexDoc(String logSt) {
		String logStr = logSt.replaceAll("\\[", Constant.SQ_SEPARATOR)
				.replaceAll("\\]", Constant.SQ_SEPARATOR);
		logStr = logStr.replaceAll("\\{", Constant.SQ_SEPARATOR).replaceAll(
				"\\}", Constant.SQ_SEPARATOR);
		logStr = logStr.replaceAll(",", Constant.SQ_SEPARATOR);
		
		String tp = getLucLogType(logSt);
		if(tp == null){
			return null;
		}
		String[] logStrs = logStr.split(Constant.SQ_SEPARATOR);
		Document doc = new Document();
		for (String log : logStrs) {
			if (isNotBlank(log)) {
				if (log.contains(":")) {
					String[] vs = log.split(":");
					if (vs.length == 2) {
						doc.add(new StringField(vs[0].trim(), vs[1], Store.YES));
					}
				}
			}
		}
		doc.add(new StringField(com.sq.lunce.index.Constant.LUT_LOG_TYPE_NAME,tp,Store.YES));
		return doc;
	}
	
	private static String getLucLogType(String logSt) {
		if(logSt.contains("KorWithdrawalFlow")){
			return "注销";
		}else if(logSt.contains("RegistFlow")){
			return "注册";
		}else if(logSt.contains("EnterServerFlow")){
			return "进入游戏";
		}else if(logSt.contains("LogoutServerFlow")){
			return "退出游戏";
		}else if(logSt.contains("ArticleMongoFlow")){
			return "物品";
		}else if(logSt.contains("WhclZengyuFlow")){
			return "赠与";
		}else if(logSt.contains("WhFubenDiaoluoFlow")){
			return "副本掉落";
		}else if(logSt.contains("AuctionDealFlow")){
			return "寄售求购";
		}else if(logSt.contains("AuctionDealSucFlow")){
			return "寄售求购成功";
		}else if(logSt.contains("ItemMongoFlow")){
			return "货币产出消耗";
		}else if(logSt.contains("RechargeFlow3")){
			return "充值";
		}else if(logSt.contains("WhTaskFlow")){
			return "任务";
		}else if(logSt.contains("EmailFlow")){
			return "邮件";
		}else if(logSt.contains("MallFlow")){
			return "商城";
		}else if(logSt.contains("StrengthenFlow")){
			return "装备强化";
		}else if(logSt.contains("BaojiaFlow")){
			return "宝甲";
		}else if(logSt.contains("ShenwuFlow")){
			return "神武";
		}else if(logSt.contains("SkillFlow")){
			return "武学技能";
		}else if(logSt.contains("UpLevelTimesFlow")){
			return "升级耗时";
		}
		return null;
	}

	public static boolean isNotBlank(String s) {
		if (s == null || (s != null && s.length() == 0))
			return false;
		return true;

	}
	
	public static boolean indexIsOk(String name) {
		File file = new File(Constant.okPath+name+".ok");
		return file.exists();
	}

}
