package com.game;

import java.applet.Applet;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Frame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static JTextField textarea1 = new JTextField(3);
	public static JTextField textarea2 = new JTextField(3);
	protected Timer timer;
	protected JPanel panel1 = new JPanel();
	protected Panel centerPanel;

	public Frame() {
	    super("化学元素趣味连连看");
	  }

    public void scheduleOfTime() {
	    timer.schedule(new TimerTask() {
	      public void run() {
	        int i = Integer.valueOf(textarea2.getText()).intValue() - 1;
	        textarea2.setText(String.valueOf(i));

	        if (i == 0)
	        {
	          JOptionPane.showMessageDialog(centerPanel, "时间到");
	          timer.cancel();
	          timer = null;
	          showResult();
	          resetGame();
	        }
	      }
	    }
	    , 0L, 1000L);
	  }

	public void showResult()
	  {
	    int block = Integer.valueOf(textarea1.getText()).intValue();
	    if ((block > 80) && (block != 120))
	    {
	      JOptionPane.showMessageDialog(this.centerPanel, "太棒了！");
	      playAudio("msc\\cheers.wav");
	    }
	    else if (block > 40)
	    {
	      JOptionPane.showMessageDialog(this.centerPanel, "很不错！");
	      playAudio("msc\\cheers.wav");
	    }
	    else if (block > 0) {
	      JOptionPane.showMessageDialog(this.centerPanel, "继续努力！");
	    }
	  }

	public void resetGame()
	  {
	    textarea1.setText(Integer.toString(0));
	    textarea2.setText("600");
	    centerPanel.StartNewGame();
	    centerPanel.Init_Graphic();

	    centerPanel.setVisible(true);
	    centerPanel.setVisible(false);
	  }

	public static void playAudio(String musicUrl) {
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

	public static void playVideo(String filePath) {
	    Runtime runtime = Runtime.getRuntime();
	    try
	    {
	      runtime.exec("cmd /c start " + filePath);
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	}
}
