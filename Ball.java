import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
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
    ArrayList<obstacle> list_obstacle = new ArrayList<obstacle>();

    @Override
    protected void paintComponent(Graphics g) 
    {
        // print ground
        g.setColor(Color.BLACK);
        g.drawLine(0,700,1500,700);

        // print all obstacles
        for (int i = 0; i < list_obstacle.size(); i++)
        {
            g.setColor(Color.RED);
            obstacle o = list_obstacle.get(i);
            g.fillRect(o.posx,o.posy,o.width,o.width);
        }

        //print DEAD if you are
        if (!alive)
        {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial Black", Font.BOLD, 50));
            g.drawString("YOU'RE DEAD",300,400);
        }
        // erase last position
        g.setColor(Color.white);
        g.fillRect(prevx, prevy, width, width);

        // print new position
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, width);

        // save position
        prevx = x;
        prevy = y;		
    }


    public void run() 
    {
        System.out.println("D�but de la m�thode run");
        while (alive) 
        {
            //go forward
            x += 1*speed;

            // if ball is jumping, go upward
            if (on_jump)
                y -= 2*speed;

            // if ball finish jump, have to go downward until touch ground
            if (!on_ground && !on_jump)
                y += 1*speed;

            // touch ground
            if (y == 650)
                on_ground = true; 
            // finish jump
            else if (y < 550)
                on_jump = false;

            //check if ball touch one of the obstacle
            for (int i = 0; i < list_obstacle.size(); i++)
            {
                obstacle o = list_obstacle.get(i);
                if (Math.abs((x + width) - (o.posx + o.width)) < o.width && Math.abs((y + width) - (o.posy + o.width)) < o.width)
                    alive = false;
            }
            
            // call paintComponent to print everything
            this.repaint();

            try 
            {
                // sleep allows smoothly move
                Thread.sleep(10);
            } 
            catch (InterruptedException e) {
                e.printStackTrace();

            }

        }
    }
} 
