package execApp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class Client {
	public static String _pattern = "yyyy-MM-dd HH:mm:ss SSS";
    public static SimpleDateFormat format = new SimpleDateFormat(_pattern);

    public static void main(String[] args) {
        try {
            while (true) {
                // �����˽�������
                Socket socket = new Socket("127.0.0.1", 9999);
                // ��������,������˷�����Ϣ
                OutputStream dout = socket.getOutputStream();
                String str = "���ݴ���------";
                dout.write(str.getBytes());
                // ͨ��shutdownOutput���ٷ������Ѿ����������ݣ�����ֻ�ܽ�������
                socket.shutdownOutput();

                // ���շ���˷��͵���Ϣ
                InputStream din = socket.getInputStream();
                byte[] outPut = new byte[4096];
                while (din.read(outPut) > 0) {
                    String result = new String(outPut);
                    System.out.println("����˷��صĵ���Ϣ�ǣ�" + result);
                }
                din.close();
                dout.close();
                socket.close();
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
