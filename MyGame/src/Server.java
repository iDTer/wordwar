
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

public class Server extends JFrame implements Runnable{
    private Socket s = null;
    private ServerSocket ss = null;
    //private JTextField jtf = new JTextField();
    private JTextArea jta = new JTextArea();

    private Random rnd = new Random();

    private ArrayList<ChatThread>clients = new ArrayList<ChatThread>();

    public Server() throws Exception{
        this.setTitle("服务器端");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(jta,BorderLayout.CENTER);
        jta.setBackground(Color.yellow);
        this.setSize(400,200);
        this.setVisible(true);
        ss = new ServerSocket(9999);
        new Thread(this).start();
    }
    public void run(){
        try{
            while(true){
                s = ss.accept();
                ChatThread ct = new ChatThread(s);
                clients.add(ct);
                ct.start();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,"游戏异常退出！");
            System.exit(0);

        }
    }

    class ChatThread extends Thread{
        private Socket s = null;
        private BufferedReader br = null;
        private PrintStream ps = null;
        private boolean canRun = true;
        public ChatThread(Socket s)throws Exception{
            this.s = s;
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            ps = new PrintStream(s.getOutputStream());
        }
        public void run(){
            try{
                //开始时，服务器首先生成一个大小有限制的随机数，并发给所有的客户端
                int wn = rnd.nextInt(640);
                System.out.println("Start:"+wn);
                String swn = "START#" + Integer.toString(wn);
                sendMessage(swn);
                while (canRun){
                    //在这里
                    String str = br.readLine();

                    String[] strs = str.split("#");
                    if(strs[0].equals("LIFE")){
                        //将生命值转发给所有的客户端
                        //sendMessage(strs[1]);
                        int rn = rnd.nextInt(640);

                        System.out.println("RdNumber:"+rn);
                        System.out.println("减或加生命值");

                        String srn = "START#" + Integer.toString(rn);
                        sendMessage("LIFE#"+strs[1]+"#"+srn);
                    }else if(strs[0].equals("WIN")){
                        //有一方生命值已经归为0，另一方胜利
                        String msgWIN = "UWIN#";
                        sendMessage(msgWIN);
                    }else if(strs[0].equals("ASKRN")){
                        int rn1 = rnd.nextInt(640);
                        String swn1 = "START#" + Integer.toString(rn1);
                        System.out.println("仅用于同步");
                        sendMessage(swn1);
                    }
                    //将str转发给所有的客户端
                    //sendMessage(str);

                }
        }catch (Exception ex){
                canRun = false;
                clients.remove(this);
            }
        }
    }

    //将信息转发给所有的客户端
    public void sendMessage(String msg){
        for(ChatThread ct:clients){
            ct.ps.println(msg);
        }
    }

    /*public void sendStartNumber(int ssn){
        for(ChatThread ct:clients){
            ct.ps.println(ssn);
        }
    }*/

    public static void main(String[] args)throws Exception{
        new Server();
    }

}


