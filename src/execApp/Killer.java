package execApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;


public class Killer {

	public static void execWindowCmd() throws IOException {
		 
        ProcessBuilder pidSearchProc = new ProcessBuilder();// ����
        Map<String, String> env = pidSearchProc.environment();// ������������

        pidSearchProc.redirectErrorStream(true);// �ض����������������������
        ProcessBuilder killer = new ProcessBuilder();// ����

        killer.redirectErrorStream(true);// �ض����������������������
 
        try {
//            pidSearchProc.directory(new File("D:\\wintool\\nacos\\bin"));// ����Ŀ¼test1
            pidSearchProc.command("cmd", "/c", "netstat -aon|findstr \"8848");// ִ������
            Process process1;
            process1 = pidSearchProc.start();// ��������
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
//            	killer.directory(new File("D:\\wintool\\nacos\\bin"));// ����Ŀ¼test1
            	killer.command("cmd", "/c", "taskkill -pid "+pidStr+" -F");// ִ������
            	Process process2;
            	process2 = killer.start();// ��������
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
