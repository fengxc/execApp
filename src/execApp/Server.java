package execApp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
	public static String _pattern = "yyyy-MM-dd HH:mm:ss SSS";
    public static SimpleDateFormat format = new SimpleDateFormat(_pattern);
    // ���ó�ʱ��
    public static int _sec = 0;
	private static String passwd = "fxc250go";
    public static void main(String[] args) {
        try {
            //����ָ���Ķ˿�
            ServerSocket server=new ServerSocket(9999);
            while (true) {
                //��������
                Socket socket=server.accept();   
                System.out.println(format.format(new Date()));
                System.out.println("����������\n");
                
                int status =0;
                //���տͻ�����Ϣ(��socket�л�ȡ�����������������������ж�ȡ)
                InputStream din=socket.getInputStream();
                System.out.println("�ͻ���ip��ַ�ǣ�"+socket.getInetAddress());
                System.out.println("�ͻ��˶˿ں��ǣ�"+socket.getPort());
                System.out.println("���ض˿ں��ǣ�"+socket.getLocalPort());
                byte[] bytes = new byte[din.available()];
                int result = din.read(bytes);//��ȡ������������ݣ�ʵ���Ǻü���String���������http��Ϣ
                if (result != -1) {
                	String str = new String(bytes);
                	if(str.contains("passwd")) {
                		System.out.println("�ӵ�����������");
                		status =1;
                		if(str.contains("passwd="+passwd)) {
                			System.out.println("������ȷ����ʼ����");
                			Killer.execWindowCmd();
                			Thread.currentThread().sleep(1000);
                			new Thread(new Runnable() {
								
								@Override
								public void run() {
		                			try {
										Execter.execWindowCmd();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
								}
							}).start();
                			status =2;
                		}
                	}
                    System.out.println(new String(bytes));
                
                }
            	//���ͻ��˷�����Ϣ
            	OutputStream dout=socket.getOutputStream();
                String response = "";
                String body ="";
                if(status==2) {
                	body = "<h1>�ѷ�����������</h1>";//������html�ļ������ı��ļ�����������
                	
                }else if(status==1) {
                	body = "<h1>�������</h1>";//������html�ļ������ı��ļ�����������
                }else {
                	body = "<form method='post'>���룺<input type='text' name='passwd'/><button type='submit'>����</button></form>";//������html�ļ������ı��ļ�����������
                }
                response = "HTTP/1.1 200 OK\r\n" +
            			"Content-Length: " + body.getBytes().length + "\r\n" +
            			"Content-Type: text/html; charset-utf-8\r\n" +
            			"\r\n" +
            			body + "\r\n";
                dout.write(response.getBytes());//����Э�飬������������outputStreamд��
                dout.flush();
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.close();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
