package com.game;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Login extends JFrame
{
  private static final long serialVersionUID = 1L;
  
  private BufferedImage LoginBackgroundImage = null;

  private JPanel loginJPanel = new JPanel();
  private JPanel loginCenterJPanel = null;

  public Login()
  {
    super("化学元素趣味连连看");
  }

  public void drawLogin() {
	    JButton GameButton = new JButton("游戏模式");
	    GameButton.setBackground(Color.BLUE);
	    GameButton.setForeground(Color.WHITE);
	    JButton ExcButton = new JButton("练习模式");
	    ExcButton.setBackground(Color.BLUE);
	    ExcButton.setForeground(Color.WHITE);
	    setLayout(new BorderLayout());

	    this.loginJPanel.add(ExcButton);
	    this.loginJPanel.add(GameButton);
	    this.loginJPanel.setBackground(Color.CYAN);
	    Container ContentPane = getContentPane();
	    ContentPane.add(this.loginJPanel, "South");
	    this.loginCenterJPanel = new JPanel();
	    ContentPane.add(this.loginCenterJPanel, "Center");

	    setBounds(430, 90, 500, 500);
	    setVisible(true);
	    setFocusable(true);
	    
	    ///exercise mode listener
	    ExcButton.addMouseListener(new MouseAdapter()
	    {
	      public void mouseClicked(MouseEvent e) {
	        new ExcFrame();
	        Login.this.dispose();
	      }
	    });
	    ///game mode listener
	    GameButton.addMouseListener(new MouseAdapter()
	    {
	      public void mouseClicked(MouseEvent e) {
	        new GameFrame();
	        Login.this.dispose();
	      }
	    });
	    setDefaultCloseOperation(3);
  }
  
  public void drawBackgroundImage() {
	    ///load background image
	    try
	    {
	      this.LoginBackgroundImage = ImageIO.read(new File("pic\\Login.jpg"));
	    }
	    catch (Exception e)
	    {
	      System.out.println(e);
	    }
	    ///draw background image
	    Graphics g = this.loginCenterJPanel.getGraphics();
	    g.drawImage(this.LoginBackgroundImage, 12, 12, 460, 460, this.loginCenterJPanel);
  }
  
  public void playAudio(String musicUrl)
  {
    try
    {
      URL url = new File(musicUrl).toURI().toURL();
      Applet.newAudioClip(url).play();
    }
    catch (MalformedURLException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void main(String[] args)
  {
    Login LLK = new Login();
    LLK.drawLogin();
    LLK.drawBackgroundImage();
    LLK.playAudio("msc\\Login.wav");
  }
}