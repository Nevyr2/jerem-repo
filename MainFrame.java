import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener 
{
    private JPanel panel = new JPanel();
    private JPanel panelsud = new JPanel();
    Ball ball = new Ball();

    public MainFrame() 
    {
        this.setVisible(true);
        this.setTitle("Moving Balls");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(new BorderLayout());
        panelsud.setLayout(new BorderLayout());
        panel.add(panelsud, BorderLayout.SOUTH);
        this.setBackground(Color.WHITE);
        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }


            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == 32) {
                    panel.updateUI();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) 
            {
                int keyCode = e.getKeyCode();
                if (keyCode == 32) 
                {
                    //launch again
                    if (!ball.alive)
                    {
                        ball.x = 50;
                        ball.y = 650;
                        ball.alive = true;
                        Thread thread = new Thread(ball);
                        thread.start();
                        panel.updateUI();
                    }

                    // able to jump only if on ground
                    else if (ball.on_ground)
                    {
                        ball.on_jump = true;
                        ball.on_ground = false;
                        panel.updateUI();

                    }

                }
            }
        });

        this.setContentPane(panel);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);

        //add obstacle
        obstacle o1 = new obstacle(375,672,28);
        ball.list_obstacle.add(o1);
        obstacle o2 = new obstacle(600,680,20);
        ball.list_obstacle.add(o2);
        obstacle o3 = new obstacle(630,680,20);
        ball.list_obstacle.add(o3);

        panel.add(ball, BorderLayout.CENTER);
        Thread thread = new Thread(ball);
        thread.start();
        panel.updateUI();

    }

    public void actionPerformed(ActionEvent arg0) 
    {
        panel.add(ball, BorderLayout.CENTER);
        Thread thread = new Thread(ball);
        thread.start();
        panel.updateUI();
    }

    public static void main(String[] args) 
    {
        MainFrame mainFrame = new MainFrame();
    }
}
