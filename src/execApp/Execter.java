package execApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Execter {
//	static String commandline = "D:\\wintool\\nacos\\bin";
//	public static boolean runIt() throws IOException{
//		Process p = Runtime.getRuntime().exec(commandline);
//		
//		return false;
//	}
	
	public static void execWindowCmd() throws IOException {
		 
        ProcessBuilder pb = new ProcessBuilder();// 命令
        Map<String, String> env = pb.environment();// 独立环境变量
        System.out.println(env);// 打印环境变量
        //env.put("MY_NAME", "KING");// 添加环境变量key-value
        pb.redirectErrorStream(true);// 重定向错误输出流到正常输出流
 
        try {
            pb.directory(new File("D:\\wintool\\nacos\\bin"));// 设置目录test1
            pb.command("cmd", "/c", "start 启动.bat");// 执行命令
            Process process1;
            process1 = pb.start();// 启动进程
            BufferedReader br1;
            br1 = new BufferedReader(new InputStreamReader(process1.getInputStream(), "gbk"));
            String line1 = null;
            while ((line1 = br1.readLine()) != null) {
                System.out.println(line1);
            }
 
//            pb.directory(new File("D:\\wintool\\nacos\\bin"));// 设置目录test2
//            pb.command("cmd", "/c", "dir", ">>", "test1.log");// 执行命令,把结果输出到test1.log
//            Process process2 = pb.start();// 启动进程
//            BufferedReader br2 = new BufferedReader(new InputStreamReader(process2.getInputStream(), "gbk"));
//            String line2 = null;
//            while ((line2 = br2.readLine()) != null) {//因为结果输出到了文件,所以本处无信息返回
//                System.out.println(line2);
//            }
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
