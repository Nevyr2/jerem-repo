import javax.swing.*;

public class obstacle
{
    int posx;
    int posy;
    int width;
    int prevx;
    int prevy;
    int initx;
    int inity;
    public obstacle(int posx, int posy, int width)
    {
        this.posx = posx;
        this.posy = posy;
        this.initx = posx;
        this.inity = posy;
        this.width = width;
    }
}
