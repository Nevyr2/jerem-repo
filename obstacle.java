import javax.swing.*;

public class obstacle
{
    int posx;
    int posy;
    int width;
    public obstacle(int posx, int posy, int width)
    {
        this.posx = posx;
        this.posy = posy;
        this.width = width;
    }

    public void move(float speed)
    {
        this.posx -= 1*speed;
    }

    public void touch_obstacle(Ball ball)
    {
        if ( ( (ball.x < posx && (ball.x + ball.width) > (posx + width))
                    || (posx < ball.x && (posx + width) > (ball.x + ball.width)))
                    && ball.y == (posy + width))
            ball.alive = false;

        if ( (ball.x + ball.width) == posx 
                && (ball.y + ball.width) > posy
                &&  ball.y < (posy + width))
            ball.alive = false;
    }

}

