package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RunSh {

	public static void main(String[] args) throws Exception{  
        String cmd = "nohup /pmapp/liuyi/java/t.sh&";  

        String[] cmds = {"/bin/ksh", "-c", "nohup /someurl/test.sh&"};  
        Process p = Runtime.getRuntime().exec(cmds);  
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));  
        String s = "";                  
        while((s=in.readLine()) != null){  
                System.out.println(s);  
        }  
        System.out.println("finished...");  

}  
}
