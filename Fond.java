 
import java.awt.*;
import javax.swing.*;
 
public class Fond extends JPanel
 {
   public Image image;
   BorderLayout borderLayout1 = new BorderLayout();
 
   public Fond()
   {
     image = Toolkit.getDefaultToolkit().getImage("image.jpg");
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
   }
 
   public void paintComponent(Graphics g)
   {
     super.paintComponent(g);
 
     int imageWight = image.getWidth(this);
     int imageHeight = image.getHeight(this);
 
     g.drawImage (image, 10, 10, null);
     repaint();
   }
  private void jbInit() throws Exception
  {
    this.setLayout(borderLayout1);
  }
 }