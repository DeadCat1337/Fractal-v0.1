package gradient;

import java.awt.Color;
import javax.swing.JButton;

public class ColorNod extends JButton {
    final Color SIDE = Color.BLACK, MIDLE = Color.WHITE, 
            SIDE_ACTIVE = Color.DARK_GRAY, MIDLE_ACTIVE = Color.GRAY;
    
    double pos;
    Color c;
    boolean sidde, active;

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
    
    
}
