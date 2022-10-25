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
    // 设置超时间
    public static int _sec = 0;
	private static String passwd = "fxc250go";
    public static void main(String[] args) {
        try {
            //监听指定的端口
            ServerSocket server=new ServerSocket(9999);
            while (true) {
                //建立连接
                Socket socket=server.accept();   
                System.out.println(format.format(new Date()));
                System.out.println("建立了链接\n");
                
                int status =0;
                //接收客户端消息(从socket中获取输入流，并建立缓冲区进行读取)
                InputStream din=socket.getInputStream();
                System.out.println("客户端ip地址是："+socket.getInetAddress());
                System.out.println("客户端端口号是："+socket.getPort());
                System.out.println("本地端口号是："+socket.getLocalPort());
                byte[] bytes = new byte[din.available()];
                int result = din.read(bytes);//读取请求的所有内容，实质是好几行String，里面存有http信息
                if (result != -1) {
                	String str = new String(bytes);
                	if(str.contains("passwd")) {
                		System.out.println("接到重启命令了");
                		status =1;
                		if(str.contains("passwd="+passwd)) {
                			System.out.println("密码正确，开始重启");
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
            	//给客户端发送消息
            	OutputStream dout=socket.getOutputStream();
                String response = "";
                String body ="";
                if(status==2) {
                	body = "<h1>已发送重启命令</h1>";//可以是html文件，读文本文件进来就行了
                	
                }else if(status==1) {
                	body = "<h1>密码错误</h1>";//可以是html文件，读文本文件进来就行了
                }else {
                	body = "<form method='post'>密码：<input type='text' name='passwd'/><button type='submit'>重启</button></form>";//可以是html文件，读文本文件进来就行了
                }
                response = "HTTP/1.1 200 OK\r\n" +
            			"Content-Length: " + body.getBytes().length + "\r\n" +
            			"Content-Type: text/html; charset-utf-8\r\n" +
            			"\r\n" +
            			body + "\r\n";
                dout.write(response.getBytes());//按照协议，将返回请求由outputStream写入
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
