import java.awt.Color;
import java.awt.Graphics;
import java.util.Random; 
import java.util.ArrayList;

import javax.swing.*;

public class Ball extends JPanel implements Runnable 
{
    private int x = 50;
    private int y = 650;
    private int width = 50;
    private int prevx = x;
    private int prevy = y;
    boolean on_jump = false;
    boolean on_ground = true;
    boolean alive = true;
    float speed = 3/2;
    private Color[] color = { Color.blue, Color.pink, Color.red};
    private Color ColorDefinitive = color[(int) (Math.random() * color.length)];
    ArrayList<obstacle> list_obstacle = new ArrayList<obstacle>();

    @Override
    protected void paintComponent(Graphics g) 
    {
        for (int i = 0; i < list_obstacle.size(); i++)
        {
            obstacle o = list_obstacle.get(i);
            g.fillRect(o.posx,o.posy,o.width,o.width);
        }
        g.setColor(Color.white);
        g.fillRect(prevx, prevy, width, width);
        g.setColor(ColorDefinitive);
        g.fillRect(x, y, width, width);
        prevx = x;
        prevy = y;		
    }


    public void run() 
    {
        System.out.println("D�but de la m�thode run");
        while (alive) 
        {
            x += 1*speed;
            if (on_jump)
                y -= 2*speed;
            if (!on_ground && !on_jump)
                y += 1*speed;

            if (y == 650)
                on_ground = true; 
            else if (y < 550)
                on_jump = false;

            for (int i = 0; i < list_obstacle.size(); i++)
            {
                obstacle o = list_obstacle.get(i);
                if (Math.abs((x + width) - (o.posx + o.width)) < o.width && Math.abs((y + width) - (o.posy + o.width)) < o.width)
                    alive = false;
            }

            this.repaint();
            try 
            {
                Thread.sleep(10);
            } 
            catch (InterruptedException e) {
                e.printStackTrace();

            }

        }
    }
} 
