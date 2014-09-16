package com.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JLabel;

public class GameFrame extends Frame
{
	private static final long serialVersionUID = 1L;
	
	public GameFrame() {
	    panel1.setBackground(Color.CYAN);

	    textarea2.setText("600");
	    textarea2.setForeground(Color.RED);
	    JLabel label1 = new JLabel("时间:");

	    JLabel label2 = new JLabel("消去方块:");
	    textarea1.setForeground(Color.BLACK);

	    JButton newlyButton = new JButton("重置");
	    newlyButton.setForeground(Color.WHITE);

	    newlyButton.setBackground(Color.BLUE);
	    JButton exitButton = new JButton("退出");
	    exitButton.setForeground(Color.WHITE);
	    exitButton.setBackground(Color.BLUE);
	    JButton startButton = new JButton("开始");
	    startButton.setBackground(Color.BLUE);
	    startButton.setForeground(Color.WHITE);
	    JButton pauseButton = new JButton("暂停");
	    pauseButton.setBackground(Color.BLUE);
	    pauseButton.setForeground(Color.WHITE);
	    JButton helpButton = new JButton("帮助");
	    helpButton.setBackground(Color.BLUE);
	    helpButton.setForeground(Color.WHITE);
	    JButton playButton = new JButton("游戏演示");
	    playButton.setBackground(Color.BLUE);
	    playButton.setForeground(Color.WHITE);
	    setLayout(new BorderLayout());
	    this.panel1.setLayout(new FlowLayout());
	    this.panel1.add(label1);
	    this.panel1.add(textarea2);
	    this.panel1.add(label2);
	    this.panel1.add(textarea1);
	    this.panel1.add(startButton);
	    this.panel1.add(pauseButton);
	    this.panel1.add(newlyButton);
	    this.panel1.add(exitButton);
	    this.panel1.add(helpButton);
	    this.panel1.add(playButton);
	    textarea1.setEditable(false);
	    textarea2.setEditable(false);
	    textarea1.setText(Integer.toString(0));
	    Container contentPane = getContentPane();
	    contentPane.add(this.panel1, "North");
	    this.centerPanel = new Panel();
	    this.centerPanel.setBackground(Color.green);
	    contentPane.add(this.centerPanel, "Center");
	    setBounds(430, 30, 600, 700);
	    setVisible(true);
	    setFocusable(true);

	    this.centerPanel.setVisible(false);

	    setResizable(false);
	    setDefaultCloseOperation(3);

	    startButton.addMouseListener(new MouseAdapter()
	    {
	      public void mouseClicked(MouseEvent e)
	      {
	        centerPanel.setVisible(false);
	        centerPanel.setVisible(true);
	        if (timer == null)
	        {
	          timer = new Timer();
	          scheduleOfTime();
	        }
	      }
	    });
	    pauseButton.addMouseListener(new MouseAdapter()
	    {
	      public void mouseClicked(MouseEvent e) {
	        centerPanel.setVisible(false);
	        if (timer != null)
	          timer.cancel();
	        timer = null;
	        centerPanel.setVisible(true);
	        centerPanel.setVisible(false);
	      }
	    });
	    exitButton.addMouseListener(new MouseAdapter()
	    {
	      public void mouseClicked(MouseEvent e) {
	        System.exit(0);
	      }
	    });
	    newlyButton.addMouseListener(new MouseAdapter()
	    {
	      public void mouseClicked(MouseEvent e) {
	        if (timer != null)
	          timer.cancel();
	        timer = null;

	        resetGame();
	        centerPanel.setVisible(false);
	      }
	    });
	    helpButton.addMouseListener(new MouseAdapter()
	    {
	      public void mouseClicked(MouseEvent e)
	      {
	        centerPanel.displayHelpImage();
	        if (timer != null)
	          timer.cancel();
	        timer = null;
	      }
	    });
	    playButton.addMouseListener(new MouseAdapter()
	    {
	      public void mouseClicked(MouseEvent e)
	      {
	        playVideo("play/EndPlay.avi");
	        if (timer != null)
	          timer.cancel();
	        timer = null;
	      }
	    });
	}
}