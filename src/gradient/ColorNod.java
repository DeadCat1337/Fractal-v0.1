package gradient;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;

public class ColorNod extends JButton {
    final Color SIDE = Color.BLACK, MIDLE = Color.LIGHT_GRAY, 
            SIDE_ACTIVE = Color.DARK_GRAY, MIDLE_ACTIVE = Color.GRAY, 
            BORDER_SIDE_ACTIVE = Color.WHITE, 
            BORDER_SIDE_INACTIVE = Color.WHITE,
            BORDER_MIDLE_ACTIVE = Color.BLACK, 
            BORDER_MIDLE_INACTIVE = Color.BLACK;
            
    final int size = 17;
    
    double pos;
    Color c;
    boolean side, active;

    public ColorNod(double pos, Color c) {
        super();
        if (pos > 1) {
            this.pos = 1;
        } else if (pos < 0) {
            this.pos = 0;
        } else {
            this.pos = pos;
        }
        this.c = c;
        
        setOpaque(true);
        setContentAreaFilled(false);
        setSize(size, 3*size/2);
        if(getParent() != null)
            setComponentZOrder(getParent(), 0);
    }
    
    @Override
    public void paint(Graphics g){
        if(side)
            if(active)
                g.setColor(SIDE_ACTIVE);
            else
                g.setColor(SIDE);
        else
            if(active)
                g.setColor(MIDLE_ACTIVE);
            else
                g.setColor(MIDLE);
        g.fillRect(0, size/2, size, size);
        g.fillPolygon(new int[]{0, size/2, size}, 
                new int[]{size/2, 0, size/2}, 3);
        
        g.setColor(c);
        g.fillRect(2, size/2 + 2, size-4, size-4);
        
        if(active){
            if(side)
                g.setColor(BORDER_SIDE_ACTIVE);
            else
                g.setColor(BORDER_MIDLE_ACTIVE);
            g.drawRect(1, size/2 + 1, size-3, size-3);
            g.fillPolygon(new int[]{size/4, size/2, size - size/4}, 
                    new int[]{size/4 + 1, 1, size/4 + 1}, 3);
        }else{
            if(side)
                g.setColor(BORDER_SIDE_INACTIVE);
            else
                g.setColor(BORDER_MIDLE_INACTIVE);
            g.drawRect(1, size/2 + 1, size-3, size-3);
        }
    }

    public void setPos(double pos) {
        this.pos = pos;
    }

    public double getPos() {
        return pos;
    }

    public void setColor(Color c) {
        this.c = c;
    }

    public Color getColor() {
        return c;
    }

    public void setSide(boolean side) {
        this.side = side;
        if(side && getParent() != null)
            getParent().setComponentZOrder(this, 2);
    }

    public boolean isSide() {
        return side;
    }

    public void setActive(boolean active) {
        this.active = active;
        if(getParent() != null){
            if (active) {
                getParent().setComponentZOrder(this, 0);
            } else if (side) {
                getParent().setComponentZOrder(this, 2);
            } else {
                getParent().setComponentZOrder(this, 1);
            }
        }
    }

    public boolean isActive() {
        return active;
    }
}
