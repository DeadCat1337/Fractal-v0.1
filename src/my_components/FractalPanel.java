package my_components;

import gradient.FGradient;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class FractalPanel extends JPanel{
    
    double changeLocation = 100, changeScale = 2;
    int changeItr = 100;
    
    private double X=0.75,Y=-0,scale=200;    
    private int N=200;
    private FGradient fg; 
    private final FGradient defaultFG;
    BufferedImage bi;
    
    public FractalPanel(){
        defaultFG = new FGradient();
        defaultFG.addNod(0, Color.BLACK);
        defaultFG.addNod(0.5, Color.RED);
        defaultFG.addNod(1.0, Color.WHITE);
        defaultFG.addNod(0.75, Color.ORANGE);
        defaultFG.setInsideColor(Color.BLACK);
        fg = defaultFG;
        
        bi = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
    }
    
    public int isIn(double X, double Y) {
        double a=X,b=Y,j;
        for(int i=0;i<N;i++)
        {
            j=a;
            
            a = a*a - b*b + X;
            b = 2 * j*b + Y;
            
            if(a*a +b*b >4)return i;
        }
        
        return -1;
    }
    
    public void reCalculate(){
        
    }
    
    @Override
    public void paint(Graphics g)
    {
        
        int w=getWidth(),h=getHeight();
        /*int i;
        for(int x=0;x<w;x++)
        {
            for(int y=0;y<h;y++)
            {
                //i = isIn(x / scale - X -w/2/scale, y / scale - Y -h/2/scale);
                
                //if(i==-1)g.setColor(new Color(0,0,0));
                //else g.setColor(new Color(255,255,255));
                
                //if (i == -1)g.setColor(new Color(255,255,255));
		//else color ? glColor3d((double)(n*itr - i) / (n*itr), 0.5, (double)i / (n*itr)) : glColor3d(1, 1, 1);
                //else g.setColor(new Color((int)(255*(double)i / N), (int)(255*(double)i / N), (int)(255*(double)0.5*i / N)));
                //g.setColor(fg.getColor(i, N-1));
                g.setColor(matrix[x][y]);
                g.drawRect(x, y, 1, 1);
            }
        }*/
        g.drawImage(bi, 0, 0, this);
        
        g.setColor(Color.WHITE);
        g.fillRect(w/2-1, h/2-1, 3, 3);
        g.setColor(Color.RED);
        g.fillRect(w/2, h/2, 1, 1);
        g.setColor(Color.BLACK);
        g.fillRect(w/2+1, h/2, 1, 1);
        g.fillRect(w/2-1, h/2, 1, 1);
        g.fillRect(w/2, h/2+1, 1, 1);
        g.fillRect(w/2, h/2-1, 1, 1);
    }
    
    @Override
    public void repaint(){
        super.repaint();
        reCalculate();
    }
    
    public FGradient getGradient(){
        return fg;
    }
    
    public void setGradient(FGradient fgr){
        fg = fgr;
    }
    
    public void moveRight(){
        setFX(getFX() - changeLocation / getScale());
    }
    
    public void moveLeft(){
        setFX(getFX() + changeLocation / getScale());
    }
    
    public void moveUp(){
        setFY(getFY() + changeLocation / getScale());
    }
    
    public void moveDown(){
        setFY(getFY() - changeLocation / getScale());
    }
    
    public void incItr(){
        setItr(getItr() + changeItr);
    }
    
    public void decItr(){
        if (getItr() > changeItr) {
                    setItr(getItr() - changeItr);
                }
    }
    
    public void zoomIn(){
        setScale(getScale()*changeScale);
    }
    
    public void zoomOut(){
        setScale(getScale()/changeScale);
    }
    
    public int getItr() {
        return N;
    }
    
    public void setItr(int n) {
        N=n;
    }
    
    public double getFX() {
        return X;
    }
    
    public void setFX(double x) {
        X=x;
    }
    
    public double getFY() {
        return Y;
    }
    
    public void setFY(double y) {
        Y=y;
    }
    
    public double getScale() {
        return scale;
    }
    
    public void setScale(double sc) {
        scale=sc;
    }
}
