package client;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{
    private GamePanel gp;
    public GameFrame(){
        this.setLayout(new BorderLayout());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setAlwaysOnTop(true);//设置窗体的属性，总是在最上面显示
        this.setAlwaysOnTop(true);
        String nickName = JOptionPane.showInputDialog("输入昵称");
        this.setTitle(nickName);
        gp = new GamePanel();
        this.add(gp);

        gp.setFocusable(true);
        this.setSize(gp.getWidth(),gp.getHeight());
        this.setResizable(false);
        this.setVisible(true);

    }
}
