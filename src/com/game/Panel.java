package com.game;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Panel extends JPanel
  implements MouseListener
{
  private static final long serialVersionUID = 1L;
  private int W = 50;
  private int GameSize = 10;
  private boolean Select_first = false;
  private int x1;
  private int y1;
  private int x2;
  private int y2;
  private Point z1 = new Point(0, 0);
  private Point z2 = new Point(0, 0);
  private int m_nCol = 10;
  private int m_nRow = 10;
  private int[] m_map = new int[100];
  private int[] r_map = new int[100];
  private BufferedImage[] srcPicArray = new BufferedImage[100];
  private BufferedImage srcImage = null;
  private BufferedImage backgroundImage = null;
  private BufferedImage wholeBackgroundImage = null;
  private BufferedImage helpBackgroundImage = null;
  private int BLANK_STATE = -1;
  private enum LinkType {LineType, OneCornerType, TwoCornerType};
  private LinkType LType;

  public Panel()
  {
    setPreferredSize(new Dimension(580, 550));
    addMouseListener(this);
    try
    {
      this.srcImage = ImageIO.read(new File("pic\\element5.bmp"));
      this.backgroundImage = ImageIO.read(new File("pic\\background2.jpg"));
      this.wholeBackgroundImage = ImageIO.read(new File("pic\\wholebackground1.jpg"));
      this.helpBackgroundImage = ImageIO.read(new File("pic\\Help2.jpg"));
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
    StartNewGame();
  }

  public void StartNewGame()
  {
    for (int i = 0; i < this.m_nCol * this.m_nRow; i++) {
      this.srcPicArray[i] = null;
    }
    for (int iNum = 0; iNum < this.m_nCol * this.m_nRow; iNum++)
    {
      this.m_map[iNum] = this.BLANK_STATE;
    }
    Random r = new Random();

    ArrayList<Integer> tmpMap = new ArrayList<Integer>();
    int randomNumber = 0;
    int numElements = 114;
    for (int i = 0; i < this.m_nCol * this.m_nRow / 4; i++)
    {
      randomNumber = r.nextInt(numElements);
      for (int j = 0; j < 4; j++) {
        tmpMap.add(Integer.valueOf(randomNumber));
      }

    }

    int nIndex = 0;
    int rIndex = 0;
    for (int i = 0; i < this.m_nRow * this.m_nCol; i++)
    {
      nIndex = r.nextInt(tmpMap.size());

      this.m_map[i] = ((Integer)tmpMap.get(nIndex)).intValue();

      rIndex = r.nextInt(3);
      this.r_map[i] = rIndex;

      tmpMap.remove(nIndex);
    }
  }

  public void Init_Graphic() {
    Graphics g = getGraphics();
    for (int i = 0; i < 100; i++)
    {
      g.drawImage(create_image(this.m_map[i], this.r_map[i], i), this.W * (i % this.GameSize) + this.W, 
        this.W * (i / this.GameSize) + this.W, this.W, this.W, this);
    }
  }

  private Image create_image(int n, int m, int k)
  {
    if (this.srcPicArray[k] != null) {
      return this.srcPicArray[k];
    }

    int x = m * this.W;

    int y = n * this.W;
    int w = this.W;
    int h = this.W;
    BufferedImage newpic = null;
    newpic = this.srcImage.getSubimage(x, y, w, h);
    this.srcPicArray[k] = newpic;
    return this.srcPicArray[k];
  }

  public void displayHelpImage()
  {
    Graphics g = getGraphics();
    g.drawImage(this.helpBackgroundImage, 10, 10, 570, 620, this);
  }

  boolean IsWin()
  {
    for (int i = 0; i < this.m_nRow * this.m_nCol; i++)
    {
      if (this.m_map[i] != this.BLANK_STATE)
      {
        return false;
      }
    }
    return true;
  }

  private boolean IsSame(int x1, int y1, int x2, int y2) {
    if (this.m_map[(y1 * this.m_nCol + x1)] == this.m_map[(y2 * this.m_nCol + x2)]) {
      return true;
    }
    return false;
  }

  boolean X_Link(int x, int y1, int y2)
  {
    if (y1 > y2)
    {
      int n = y1;
      y1 = y2;
      y2 = n;
    }

    for (int i = y1 + 1; i <= y2; i++)
    {
      if (i == y2)
        return true;
      if (this.m_map[(i * this.m_nCol + x)] != this.BLANK_STATE)
        break;
    }
    return false;
  }

  boolean Y_Link(int x1, int x2, int y)
  {
    if (x1 > x2)
    {
      int x = x1;
      x1 = x2;
      x2 = x;
    }

    for (int i = x1 + 1; i <= x2; i++)
    {
      if (i == x2)
        return true;
      if (this.m_map[(y * this.m_nCol + i)] != this.BLANK_STATE)
        break;
    }
    return false;
  }

  boolean OneCornerLink(int x1, int y1, int x2, int y2)
  {
    if (x1 > x2)
    {
      int n = x1;
      x1 = x2;
      x2 = n;
      n = y1;
      y1 = y2;
      y2 = n;
    }

    if (this.m_map[(y1 * this.m_nCol + x2)] == this.BLANK_STATE)
    {
      if ((Y_Link(x1, x2, y1)) && (X_Link(x2, y1, y2)))
      {
        this.z1.x = x2; this.z1.y = y1;
        return true;
      }
    }

    if (this.m_map[(y2 * this.m_nCol + x1)] == this.BLANK_STATE)
    {
      if ((Y_Link(x2, x1, y2)) && (X_Link(x1, y2, y1)))
      {
        this.z1.x = x1; this.z1.y = y2;
        return true;
      }
    }
    return false;
  }

  boolean TwoCornerLink(int x1, int y1, int x2, int y2)
  {
    if (x1 > x2)
    {
      int n = x1;
      x1 = x2;
      x2 = n;
      n = y1;
      y1 = y2;
      y2 = n;
    }

    for (int x = x1 + 1; x <= this.m_nCol; x++)
    {
      if (x == this.m_nCol)
      {
        if (!XThrough(x2 + 1, y2, true))
          break;
        this.z2.x = this.m_nCol; this.z2.y = y1;
        this.z1.x = this.m_nCol; this.z1.y = y2;
        return true;
      }

      if (this.m_map[(y1 * this.m_nCol + x)] != this.BLANK_STATE)
        break;
      if (OneCornerLink(x, y1, x2, y2))
      {
        this.z2.x = x; this.z2.y = y1;
        return true;
      }
    }

    for (int x = x1 - 1; x >= -1; x--)
    {
      if (x == -1)
      {
        if (!XThrough(x2 - 1, y2, false))
          break;
        this.z2.x = -1; this.z2.y = y1;
        this.z1.x = -1; this.z1.y = y2;
        return true;
      }

      if (this.m_map[(y1 * this.m_nCol + x)] != this.BLANK_STATE)
        break;
      if (OneCornerLink(x, y1, x2, y2))
      {
        this.z2.x = x; this.z2.y = y1;
        return true;
      }
    }

    for (int y = y1 - 1; y >= -1; y--)
    {
      if (y == -1)
      {
        if (!YThrough(x2, y2 - 1, false))
          break;
        this.z2.x = x1; this.z2.y = -1;
        this.z1.x = x2; this.z1.y = -1;
        return true;
      }

      if (this.m_map[(y * this.m_nCol + x1)] != this.BLANK_STATE)
        break;
      if (OneCornerLink(x1, y, x2, y2))
      {
        this.z2.x = x1; this.z2.y = y;
        return true;
      }
    }

    for (int y = y1 + 1; y <= this.m_nRow; y++)
    {
      if (y == this.m_nRow)
      {
        if (!YThrough(x2, y2 + 1, true))
          break;
        this.z2.x = x1; this.z2.y = this.m_nRow;
        this.z1.x = x2; this.z1.y = this.m_nRow;
        return true;
      }

      if (this.m_map[(y * this.m_nCol + x1)] != this.BLANK_STATE)
        break;
      if (OneCornerLink(x1, y, x2, y2))
      {
        this.z2.x = x1; this.z2.y = y;
        return true;
      }
    }
    return false;
  }

  boolean XThrough(int x, int y, boolean bAdd) {
    if (bAdd)
    {
      for (int i = x; i < this.m_nCol; i++) {
        if (this.m_map[(y * this.m_nCol + i)] != this.BLANK_STATE)
          return false;
      }
    }
    else {
      for (int i = 0; i <= x; i++)
        if (this.m_map[(y * this.m_nCol + i)] != this.BLANK_STATE)
          return false;
    }
    return true;
  }

  boolean YThrough(int x, int y, boolean bAdd)
  {
    if (bAdd)
    {
      for (int i = y; i < this.m_nRow; i++) {
        if (this.m_map[(i * this.m_nCol + x)] != this.BLANK_STATE)
          return false;
      }
    }
    else {
      for (int i = 0; i <= y; i++)
        if (this.m_map[(i * this.m_nCol + x)] != this.BLANK_STATE)
          return false;
    }
    return true;
  }

  boolean IsLink(int x1, int y1, int x2, int y2)
  {
    if (x1 == x2)
    {
      if (X_Link(x1, y1, y2)) {
        LType = LinkType.LineType; return true;
      }
    }
    else if (y1 == y2)
    {
      if (Y_Link(x1, x2, y1)) {
        LType = LinkType.LineType; return true;
      }
    }
    if (OneCornerLink(x1, y1, x2, y2))
    {
      LType = LinkType.OneCornerType;
      return true;
    }

    if (TwoCornerLink(x1, y1, x2, y2))
    {
      LType = LinkType.TwoCornerType;
      return true;
    }
    return false;
  }

  private boolean Find2Block()
  {
    boolean bFound = false;

    for (int i = 0; i < this.m_nRow * this.m_nCol; i++)
    {
      if (bFound) {
        break;
      }
      if (this.m_map[i] != this.BLANK_STATE)
      {
        for (int j = i + 1; j < this.m_nRow * this.m_nCol; j++)
        {
          if ((this.m_map[j] != this.BLANK_STATE) && (this.m_map[i] == this.m_map[j]))
          {
            this.x1 = (i % this.m_nCol);
            this.y1 = (i / this.m_nCol);
            this.x2 = (j % this.m_nCol);
            this.y2 = (j / this.m_nCol);

            if (IsLink(this.x1, this.y1, this.x2, this.y2))
            {
              Graphics g = this.getGraphics();
              DrawSelectedBlock(x1, y1, g);
              DrawSelectedBlock(x2, y2, g);
              bFound = true;
              break;
            }
          }
        }
      }
    }
    return bFound;
  }

  private void DrawLinkLine(int x1, int y1, int x2, int y2, LinkType LType)
  {
    Graphics g = getGraphics();
    Point p1 = new Point(x1 * this.W + this.W / 2 + this.W, y1 * this.W + this.W / 2 + this.W);
    Point p2 = new Point(x2 * this.W + this.W / 2 + this.W, y2 * this.W + this.W / 2 + this.W);
    if (LType == LinkType.LineType)
      g.drawLine(p1.x, p1.y, p2.x, p2.y);
    if (LType == LinkType.OneCornerType)
    {
      Point pixel_z1 = new Point(this.z1.x * this.W + this.W / 2 + this.W, this.z1.y * this.W + this.W / 2 + this.W);
      g.drawLine(p1.x, p1.y, pixel_z1.x, pixel_z1.y);
      g.drawLine(pixel_z1.x, pixel_z1.y, p2.x, p2.y);
    }
    if (LType == LinkType.TwoCornerType)
    {
      Point pixel_z1 = new Point(this.z1.x * this.W + this.W / 2 + this.W, this.z1.y * this.W + this.W / 2 + this.W);
      Point pixel_z2 = new Point(this.z2.x * this.W + this.W / 2 + this.W, this.z2.y * this.W + this.W / 2 + this.W);
      if ((p1.x != pixel_z2.x) && (p1.y != pixel_z2.y))
      {
        Point c = pixel_z1;
        pixel_z1 = pixel_z2;
        pixel_z2 = c;
      }
      g.drawLine(p1.x, p1.y, pixel_z2.x, pixel_z2.y);
      g.drawLine(pixel_z2.x, pixel_z2.y, pixel_z1.x, pixel_z1.y);
      g.drawLine(pixel_z1.x, pixel_z1.y, p2.x, p2.y);
    }
  }

  private void DrawSelectedBlock(int x, int y, Graphics g)
  {
    Graphics2D g2 = (Graphics2D)g;
    BasicStroke s = new BasicStroke(2.0F);
    g2.setStroke(s);

    if ((x >= 0) && (x < 10) && (y >= 0) && (y < 10))
      g.drawRect(x * this.W + 1 + this.W, y * this.W + 1 + this.W, this.W - 3, this.W - 3);
  }

  public void CleanLineAndBlock()
  {
    Graphics g = getGraphics();

    g.clearRect(0, 0, this.W, 12 * this.W);
    g.clearRect(11 * this.W, 0, this.W, 12 * this.W);
    g.clearRect(0, 0, 12 * this.W, this.W);
    g.clearRect(0, 11 * this.W, 12 * this.W, this.W);

    g.drawImage(this.wholeBackgroundImage, 0, 0, 600, 650, this);

    for (int i = 0; i < 100; i++)
    {
      if (this.m_map[i] == this.BLANK_STATE) {
        g.clearRect(this.W * (i % this.GameSize) + this.W, this.W * (i / this.GameSize) + this.W, this.W, this.W);
      }
    }
    g.drawImage(this.backgroundImage, this.W, this.W, 500, 500, this);

    for (int i = 0; i < 100; i++)
    {
      if (this.m_map[i] != this.BLANK_STATE) {
          g.drawImage(create_image(this.m_map[i], this.r_map[i], i), this.W * (i % this.GameSize) + this.W, 
            this.W * (i / this.GameSize) + this.W, this.W, this.W, this);  
      }
    }
  }

  public void paint(Graphics g) {
    g.drawImage(this.wholeBackgroundImage, 0, 0, 600, 650, this);
    for (int i = 0; i < 100; i++)
    {
      if (this.m_map[i] == this.BLANK_STATE)
        g.clearRect(this.W * (i % this.GameSize) + this.W, this.W * (i / this.GameSize) + this.W, this.W, this.W);
      else
        g.drawImage(create_image(this.m_map[i], this.r_map[i], i), this.W * (i % this.GameSize) + this.W, 
          this.W * (i / this.GameSize) + this.W, this.W, this.W, this);
    }
  }

  public void mouseClicked(MouseEvent e) {
    Graphics g = getGraphics();

    if (e.getButton() == 1)
    {
      int x = (e.getX() - this.W) / this.W;
      int y = (e.getY() - this.W) / this.W;

      if ((x < 0) || (x >= 10) || (y < 0) || (y >= 10))
      {
        return;
      }

      if (this.m_map[(y * this.m_nCol + x)] == this.BLANK_STATE)
        return;
      if (!this.Select_first)
      {
        this.x1 = x; this.y1 = y;

        DrawSelectedBlock(this.x1, this.y1, g);
        this.Select_first = true;
      }
      else
      {
        this.x2 = x; this.y2 = y;

        if ((this.x1 == this.x2) && (this.y1 == this.y2)) return;

        DrawSelectedBlock(this.x2, this.y2, g);

        if ((IsSame(this.x1, this.y1, this.x2, this.y2)) && (IsLink(this.x1, this.y1, this.x2, this.y2)))
        {
          int grade = Integer.parseInt(Frame.textarea1.getText()) + 2;
          Frame.textarea1.setText(String.valueOf(grade));

          DrawLinkLine(this.x1, this.y1, this.x2, this.y2, this.LType);

          this.m_map[(this.y1 * this.m_nCol + this.x1)] = this.BLANK_STATE;
          this.m_map[(this.y2 * this.m_nCol + this.x2)] = this.BLANK_STATE;
          this.Select_first = false;
          
          ///play audio by static method.
          Frame.playAudio("msc\\cancle.wav");
          
          try
          {
            Thread.sleep(0L);
          }
          catch (InterruptedException ie)
          {
            ie.printStackTrace();
          }
          CleanLineAndBlock();
          
          if (!Find2Block() && !IsWin()) {
        	JOptionPane.showMessageDialog(this, "没有连通的方块了！！");
          }
          else if (IsWin()) {
        	  g.drawImage(this.backgroundImage, this.W, this.W, 500, 500, this);
        	  JOptionPane.showMessageDialog(this, "恭喜您胜利闯关,即将开始新局");
          }
        }
        else
        {
          int i = this.y1 * this.m_nCol + this.x1;
          g.drawImage(create_image(this.m_map[i], this.r_map[i], i), this.W * (i % this.GameSize) + this.W, 
            this.W * (i / this.GameSize) + this.W, this.W, this.W, this);

          this.x1 = x; this.y1 = y;
          this.Select_first = true;
        }
        
      }
      
    }
  }

  public void mouseEntered(MouseEvent arg0)
  {
  }

  public void mouseExited(MouseEvent arg0)
  {
  }

  public void mousePressed(MouseEvent arg0)
  {
  }

  public void mouseReleased(MouseEvent arg0)
  {
  }
}