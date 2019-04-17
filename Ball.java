import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.util.Random; 
import java.util.ArrayList;

import javax.swing.*;

public class Ball extends JPanel implements Runnable 
{
    public int x = 50;
    public int y = 650;
    private int width = 50;
    private int prevx = x;
    private int prevy = y;
    boolean on_jump = false;
    boolean on_ground = true;
    boolean alive = true;
    boolean quit = false;
    float speed = 3/2;
    int score = 0;
    int prev_score = 0;
    int lvl = 1;
    int prev_lvl;
    int next_lvl = 5;
    int prev_next_lvl = 5;
    ArrayList<obstacle> list_obstacle = new ArrayList<obstacle>();

    @Override
    protected void paintComponent(Graphics g) 
    {
        // print ground
        g.setColor(Color.BLACK);
        g.drawLine(0,700,1500,700);

        g.setColor(Color.BLACK);
        g.drawLine(0,50,1500,50);
        g.setColor(Color.BLACK);
        g.drawLine(0,165,1500,165);
        
        //print Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial Black", Font.BOLD, 50));
        g.drawString("SCORE: " + prev_score,200,125);

        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial Black", Font.BOLD, 50));
        g.drawString("SCORE: " + score,200,125);

        //print next_lvl
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial Black", Font.BOLD, 50));
        g.drawString("Next_lvl: " + (prev_score + prev_next_lvl),600,150);

        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial Black", Font.BOLD, 50));
        g.drawString("Next_lvl: " + (score + next_lvl),600,150);
        prev_next_lvl = next_lvl;
        prev_score = score;

        //print level
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial Black", Font.BOLD, 50));
        g.drawString("LEVEL : " + prev_lvl,600,100);

        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial Black", Font.BOLD, 50));
        g.drawString("LEVEL : " + lvl,600,100);
        prev_lvl = lvl;

        //print DEAD if you are
        if (!alive)
        {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial Black", Font.BOLD, 50));
            g.drawString("YOU'RE DEAD",300,400);

            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial Black", Font.BOLD, 20));
            g.drawString("Press Space to Try Again",50,750);
        }

        prev_score = score;
        if (alive)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial Black", Font.BOLD, 50));
            g.drawString("YOU'RE DEAD",300,400);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial Black", Font.BOLD, 20));
            g.drawString("Press Space to Try Again",50,750);
        }

        // erase last position
        g.setColor(Color.white);
        g.fillRect(prevx,prevy,width,width);
        for (int i = 0; i < list_obstacle.size(); i++)
        {
            obstacle o = list_obstacle.get(i);
            g.fillRect(o.prevx, o.prevy, o.width, o.width);
        }

        // print new position
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, width);
        for (int i = 0; i < list_obstacle.size(); i++)
        {
            g.setColor(Color.RED);
            obstacle o = list_obstacle.get(i);
            g.fillRect(o.posx, o.posy, o.width, o.width);
        }

        // save position
        prevx = x;
        prevy = y;		
        for (int i = 0; i < list_obstacle.size(); i++)
        {
            obstacle o = list_obstacle.get(i);
            o.prevx = o.posx;
            o.prevy = o.posy;
        }

    }

    public void update_score_lvl()
    {
        if (!alive)
        {
            score = 0;
            lvl = 1;
            next_lvl = 5;
        }

        if (next_lvl == 0)
        {
            lvl += 1;
            next_lvl = 5;
        }
    }

    public void move_player()
    {
        // if ball is jumping, go upward
        if (on_jump)
            y -= 2*speed;

        // if ball finish jump, have to go downward until touch ground
        if (!on_ground && !on_jump)
            y += 2*speed;

        // touch ground
        if (y == 650)
            on_ground = true; 
        // finish jump
        else if (y < 515)
            on_jump = false;

    }

    public void move_obstacle_AND_check_dead()
    {
        //check if ball touch one of the obstacle
        for (int i = 0; i < list_obstacle.size(); i++)
        {
            obstacle o = list_obstacle.get(i);
            if (Math.abs((x + width) - 
                        (o.posx + o.width)) < o.width && Math.abs((y 
                                + width) - (o.posy + o.width)) < o.width)
            {
                alive = false;
            }
            o.posx -= 1;
        }
    }
    public void maj_obstacle()
    {
        for (int i = 0; i < list_obstacle.size(); i++)
        {
            obstacle o = list_obstacle.get(i);
            if (o.posx < -50)
            {
                list_obstacle.remove(i);
                score += 1;
                next_lvl -= 1;
            }
        }
        while (list_obstacle.size() < (5 + lvl))
        {
            int width = (int)(Math.random() * 20) + 10;
            obstacle new_o = new obstacle(800 + (int)(Math.random() * 3000),700 - width - ((int)(Math.random() * 2) * 60), width);
            list_obstacle.add(new_o);
        }

    }
    public void run() 
    {
        System.out.println("D�but de la m�thode run");
        while (!quit) 
        {
            if (alive)
            {
                move_player();

                move_obstacle_AND_check_dead();

                maj_obstacle();

                update_score_lvl();

                // call paintComponent to print everything
                this.repaint();

                try 
                {
                    // sleep allows smoothly move
                    Thread.sleep(8);
                } 
                catch (InterruptedException e) 
                {
                    e.printStackTrace();
                }
            }

        }
    }
}

