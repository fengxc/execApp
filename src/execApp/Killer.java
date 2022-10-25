package execApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;


public class Killer {

	public static void execWindowCmd() throws IOException {
		 
        ProcessBuilder pidSearchProc = new ProcessBuilder();// 命令
        Map<String, String> env = pidSearchProc.environment();// 独立环境变量

        pidSearchProc.redirectErrorStream(true);// 重定向错误输出流到正常输出流
        ProcessBuilder killer = new ProcessBuilder();// 命令

        killer.redirectErrorStream(true);// 重定向错误输出流到正常输出流
 
        try {
//            pidSearchProc.directory(new File("D:\\wintool\\nacos\\bin"));// 设置目录test1
            pidSearchProc.command("cmd", "/c", "netstat -aon|findstr \"8848");// 执行命令
            Process process1;
            process1 = pidSearchProc.start();// 启动进程
            BufferedReader br1;
            br1 = new BufferedReader(new InputStreamReader(process1.getInputStream(), "gbk"));
            String line1 = null;
            String pidStr = "";
            while ((line1 = br1.readLine()) != null) {
                System.out.println(line1);
            
	            if(line1.contains("8848")) {
	            	String[] strs = line1.split("       ");
	            	for(int i=0;i<strs.length;i++) {
	            		if(i==strs.length-1) {
	            			pidStr = strs[i];
	            			System.out.println(strs[i]);
	            		}
	            	}
	            }
            }
            if(pidStr.length()>0) {
//            	killer.directory(new File("D:\\wintool\\nacos\\bin"));// 设置目录test1
            	killer.command("cmd", "/c", "taskkill -pid "+pidStr+" -F");// 执行命令
            	Process process2;
            	process2 = killer.start();// 启动进程
            }


        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

	public static void main(String[] args) {
		try {
			execWindowCmd();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
