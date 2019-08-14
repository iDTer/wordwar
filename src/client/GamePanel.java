package client;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;
import java.util.Random;
import javax.swing.*;


public class GamePanel extends JPanel implements Runnable, ActionListener,KeyListener {
    private int life = 20;
    private char keyChar;
    private JLabel lbMoveChar = new JLabel();
    private JLabel lbLife = new JLabel();
    //选项ABCD
    private JLabel lbA = new JLabel();
    private JLabel lbB = new JLabel();
    private JLabel lbC = new JLabel();
    private JLabel lbD = new JLabel();

    private Socket  s = null;
    private Timer timer = new Timer(300,this);

    private Random rnd = new Random();
    private BufferedReader br = null;
    private PrintStream ps = null;

    private String word = null;
    private String Opt = null;
    private int il;
    String strSave = null;
    String keyStr = null;

    //int in = 0;
    //int ij = 0;
    //int ic = 0;

    //canRun用来在异常或退出游戏时退出死循环
    private boolean canRun = true;

    public GamePanel(){
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);
        this.setSize(1200,500);

        this.add(lbLife);
        lbLife.setFont(new Font("黑体",Font.BOLD,20));
        lbLife.setBackground(Color.yellow);
        lbLife.setForeground(Color.PINK);
        lbLife.setBounds(0,0,500,20);

        this.add(lbA);
        this.add(lbB);
        this.add(lbC);
        this.add(lbD);

        lbA.setForeground(Color.PINK);
        lbB.setForeground(Color.PINK);
        lbC.setForeground(Color.PINK);
        lbD.setForeground(Color.PINK);
        lbA.setBackground(Color.yellow);
        lbB.setBackground(Color.yellow);
        lbC.setBackground(Color.yellow);
        lbD.setBackground(Color.yellow);

        lbA.setFont(new Font("黑体",Font.BOLD,15));
        lbB.setFont(new Font("黑体",Font.BOLD,15));
        lbC.setFont(new Font("黑体",Font.BOLD,15));
        lbD.setFont(new Font("黑体",Font.BOLD,15));

        lbA.setBounds(0,400,300,50);
        lbB.setBounds(300,400,300,50);
        lbC.setBounds(600,400,300,50);
        lbD.setBounds(900,400,300,50);
        //lbA.setText("选项A");
        //lbB.setText("选项B");
        //lbC.setText("选项C");
        //lbD.setText("选项D");


        this.add(lbMoveChar);
        lbMoveChar.setFont(new Font("黑体",Font.BOLD,20));
        lbMoveChar.setForeground(Color.yellow);

        this.init();
        this.addKeyListener( this);
        try{
            s = new Socket("127.0.0.1",9999);

            //JOptionPane.showMessageDialog(this,"连接成功");
            InputStream is = s.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            OutputStream os = s.getOutputStream();
            ps = new PrintStream(os);
            new Thread(this).start();

        }catch (Exception ex){
            javax.swing.JOptionPane.showMessageDialog(this,"游戏异常退出！");
            System.exit(0);

        }
        timer.start();

    }



    public void writeFile(String filename, String str){


        try {
            FileOutputStream fos = new FileOutputStream(filename,true);
            byte[] b = str.getBytes();
            fos.write(b);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public synchronized void readLineFile(String filename, int il){
        try{
            FileInputStream fi = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(fi, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            //br此处用来按行读取文档
            //in++;
            //System.out.println("readLineFile开始执行:"+in);
            while(br.readLine() != null && il >= 0){
                il--;
                //String str;
                if(il < 0){
                    //用来按行读取，并分割出单词和解释

                    //String的split方法支持正则表达式；
                    //正则表达式\s表示匹配任何空白字符，+表示匹配一次或多次。

                    String str1 = br.readLine();
                    //保存的字符串
                    strSave = str1 + "\r\n";

                    String[] strs1 = str1.split("\\s+");
                    word = strs1[0];

                    System.out.println("1单词："+word);

                    //将翻译分配给选项
                    System.out.println("1单词源："+strs1[1]);
                    Random rnd1 = new Random();


                    //rnd.nextInt(300-10+1)+10-->[10,300)
                    //这里的4不能改！！！！
                    int swch = rnd1.nextInt(4);

                    switch (swch) {
                        case 0: {
                            lbA.setText("A:"+strs1[1]);
                            String str2 = br.readLine();
                            String[] strs2 = str2.split("\\s+");
                            lbB.setText("B:"+strs2[1]);
                            String str3 = br.readLine();
                            String[] strs3 = str3.split("\\s+");
                            lbC.setText("C:"+strs3[1]);
                            String str4 = br.readLine();
                            String[] strs4 = str4.split("\\s+");
                            lbD.setText("D:"+strs4[1]);
                            Opt = "A";

                            System.out.println(lbA.getText());
                            System.out.println(lbB.getText());
                            System.out.println(lbC.getText());
                            System.out.println(lbD.getText());

                            break;
                        }
                        case 1:{
                            lbB.setText("B:"+strs1[1]);
                            String str2 = br.readLine();
                            String[] strs2 = str2.split("\\s+");
                            lbA.setText("A:"+strs2[1]);
                            String str3 = br.readLine();
                            String[] strs3 = str3.split("\\s+");
                            lbC.setText("C:"+strs3[1]);
                            String str4 = br.readLine();
                            String[] strs4 = str4.split("\\s+");
                            lbD.setText("D:"+strs4[1]);
                            Opt = "B";

                            System.out.println(lbA.getText());
                            System.out.println(lbB.getText());
                            System.out.println(lbC.getText());
                            System.out.println(lbD.getText());
                            break;
                        }
                        case 2:{
                            lbC.setText("C:"+strs1[1]);
                            String str2 = br.readLine();
                            String[] strs2 = str2.split("\\s+");
                            lbA.setText("A:"+strs2[1]);
                            String str3 = br.readLine();
                            String[] strs3 = str3.split("\\s+");
                            lbB.setText("B:"+strs3[1]);
                            String str4 = br.readLine();
                            String[] strs4 = str4.split("\\s+");
                            lbD.setText("D:"+strs4[1]);
                            Opt = "C";

                            System.out.println(lbA.getText());
                            System.out.println(lbB.getText());
                            System.out.println(lbC.getText());
                            System.out.println(lbD.getText());
                            break;
                        }
                        case 3:{
                            lbD.setText("D:"+strs1[1]);
                            String str2 = br.readLine();
                            String[] strs2 = str2.split("\\s+");
                            lbA.setText("A:"+strs2[1]);
                            String str3 = br.readLine();
                            String[] strs3 = str3.split("\\s+");
                            lbB.setText("B:"+strs3[1]);
                            String str4 = br.readLine();
                            String[] strs4 = str4.split("\\s+");
                            lbC.setText("C:"+strs4[1]);
                            Opt = "D";

                            System.out.println(lbA.getText());
                            System.out.println(lbB.getText());
                            System.out.println(lbC.getText());
                            System.out.println(lbD.getText());

                            break;
                        }
                        default:{
                            System.out.println("Switch语句有误");
                            break;
                        }

                    }
                    System.out.println("正确选项是"+Opt);

                    break;
                }
                //System.out.println("readLineFile结束:"+in);
            }

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public void init() {
        //System.out.println("init开始执行:"+ij);

        //ij++;
        lbLife.setText("当前生命值：" + life);
        //System.out.println("设置生命值");

        //String str = String.valueOf((char)('A'+rnd.nextInt(26)));
        //lbMoveChar.setText(str);
        //不需要客户端产生随机数了，服务器产生随机数
        //int il = rnd.nextInt(10);
        //ps.println("RND#"+il);


        readLineFile("D:\\word.txt", il);

        lbA.setBounds(0,400,300,50);
        lbB.setBounds(300,400,300,50);
        lbC.setBounds(600,400,300,50);
        lbD.setBounds(900,400,300,50);

        lbMoveChar.setText(word);

        lbMoveChar.setBounds(500,0,200,50);

        //System.out.println("init执行完成:"+ij);
    }

    //读取服务器发来的消息，操作生命值
    public void run(){
        try{
            while(canRun){
                String str = br.readLine();
                String[] strs = str.split("#");
                //判断从服务器接收到的消息是初始化number（用作随机读词）
                if(strs[0].equals("START")){
                    il = Integer.parseInt(strs[1]);
                //判断是减生命值的消息
                }else if(strs[0].equals("LIFE")) {
                    //若消息是既包含LIFE又包含RND
                    int score = Integer.parseInt(strs[1]);

                    //实现生命值的减少

                    life+=score;
                    checkFail();
                    //System.out.println("life+=score;语句调用checkFail");
                    //如果strs[]的格式为“LIFE#-1#START#srn"
                    if(strs[2].equals("START")){
                        il = Integer.parseInt(strs[3]);

                    }
                }else if(strs[0].equals("UWIN")){
                    //你赢了并退出游戏
                    timer.stop();
                    javax.swing.JOptionPane.showMessageDialog(this,"游戏结束，你赢了！");
                    System.exit(0);

                }
                init();
                //checkFail();
                //System.out.println("执行完服务器来的任务后调用init");
            }
        }catch (Exception ex){
        canRun = false;
        javax.swing.JOptionPane.showMessageDialog(this,"游戏异常退出！");
        System.exit(0);
        }
    }

    //某用户如果生命值率先变为0分，则判输，游戏退出。
    public void checkFail(){
        //init();
        //System.out.println("开始执行checkFail："+ic);
        lbLife.setText("当前生命值：" + life);

        //ic++;

        //System.out.println("结束ccheckFail："+ic);
        if(life <= 0){
            ps.println("WIN#");
            timer.stop();
            javax.swing.JOptionPane.showMessageDialog(this,"生命值耗尽，游戏失败！");
            System.exit(0);
        }
    }

    //用户选择正确，选项射向词汇
    /*public synchronized void ProLocation(){
        switch (keyStr){
            case "a":{
                lbA.setLocation((lbMoveChar.getX()+lbA.getX())/2,(lbMoveChar.getY()+lbA.getY())/2);
                break;
            }
            case "b":{
                lbB.setLocation((lbMoveChar.getX()+lbB.getX())/2,(lbMoveChar.getY()+lbB.getY())/2);
                break;
            }
            case "c":{
                lbC.setLocation((lbMoveChar.getX()+lbC.getX())/2,(lbMoveChar.getY()+lbC.getY())/2);
                break;
            }
            case "d":{
                lbD.setLocation((lbMoveChar.getX()+lbD.getX())/2,(lbMoveChar.getY()+lbD.getY())/2);
                break;
            }
            default:{
                System.out.println("没有此选项！");
                break;
            }


        }


    }*/


    //若词汇掉到用户界面底部，如果两个用户都还没做出选择，两个用户减1分
    public void actionPerformed(ActionEvent e){
        if(lbMoveChar.getY() >= this.getHeight()){
            writeFile("D:\\wrong.txt",strSave);
            life--;
            checkFail();
            //随机数由服务器产生
            ps.println("ASKRN#");
            //System.out.println("底部调用checkFail");
        }
        lbMoveChar.setLocation(lbMoveChar.getX(),lbMoveChar.getY()+10);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
         keyChar = e.getKeyChar();
         keyStr = String.valueOf(keyChar);
        try{
            //判断是否选对选项
            if(keyStr.equalsIgnoreCase(Opt)) {

                writeFile("D:\\right.txt",strSave);

                life+=2;
                //System.out.println("选正确了");
                ps.println("LIFE#-1");
            }
            else{

                writeFile("D:\\wrong.txt",strSave);

                life-=2;
                //用于向服务器标识需要随机数，对方生命值加一
                ps.println("LIFE#1");
            }

            //实现选项射向lbMoveChar
            //ProLocation();
            //(Timer.scheduleAtFixedRate(TimerTask task,long delay,long period)
            //安排指定的任务在指定的延迟后开始进行重复的固定速率执行．
            /*timer.stop();
            Timer timer2 = new Timer();
            timer2.scheduleAtFixedRate()*/


            init();
            checkFail();

            //System.out.println("向服务器发消息后checkFail");

        }catch (Exception ex){
            canRun = false;
            javax.swing.JOptionPane.showMessageDialog(this,"游戏异常退出！");
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args){
         new GameFrame();
    }

}
