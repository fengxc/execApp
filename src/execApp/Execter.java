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
		 
        ProcessBuilder pb = new ProcessBuilder();// ����
        Map<String, String> env = pb.environment();// ������������
        System.out.println(env);// ��ӡ��������
        //env.put("MY_NAME", "KING");// ��ӻ�������key-value
        pb.redirectErrorStream(true);// �ض����������������������
 
        try {
            pb.directory(new File("D:\\wintool\\nacos\\bin"));// ����Ŀ¼test1
            pb.command("cmd", "/c", "start ����.bat");// ִ������
            Process process1;
            process1 = pb.start();// ��������
            BufferedReader br1;
            br1 = new BufferedReader(new InputStreamReader(process1.getInputStream(), "gbk"));
            String line1 = null;
            while ((line1 = br1.readLine()) != null) {
                System.out.println(line1);
            }
 
//            pb.directory(new File("D:\\wintool\\nacos\\bin"));// ����Ŀ¼test2
//            pb.command("cmd", "/c", "dir", ">>", "test1.log");// ִ������,�ѽ�������test1.log
//            Process process2 = pb.start();// ��������
//            BufferedReader br2 = new BufferedReader(new InputStreamReader(process2.getInputStream(), "gbk"));
//            String line2 = null;
//            while ((line2 = br2.readLine()) != null) {//��Ϊ�����������ļ�,���Ա�������Ϣ����
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
