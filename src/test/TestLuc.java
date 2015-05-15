package test;
import java.io.File;

import org.junit.Test;

import com.sq.lunce.index.create.LucIndexCreatorForLocal;
public class TestLuc {

	@Test
	public  void testRemoteCreator(){
		String okPath = "D:\\demo\\index\\index_ok";
		String indexFilePath = "D:\\demo\\index\\index_small";
//		String filePath = "/data2/3dgame_log/tianxiawushuang/3d_log_game3/3dgame/billingCenter.log.2015-01-30.log";
//		String fileName = "billingCenter.log.2015-01-30.log";
//		String ip = "117.121.17.32",port= "22201";
//		LucIndexCreatorForRemote creator = new LucIndexCreatorForRemote(fileName, indexFilePath, filePath, ip, port, okPath);
//		creator.createIndex();
		File dataFile = new File("D:\\demo\\index\\doc\\knapsack.log.2014-09-24.log");
		LucIndexCreatorForLocal creator = new LucIndexCreatorForLocal(indexFilePath, dataFile, okPath);
		creator.createIndex();
	}
}
