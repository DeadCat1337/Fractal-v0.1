package my_components;

import java.awt.Color;
import javax.swing.JSlider;

public class ColorSlider extends JSlider{

    public ColorSlider(int value) {
        super();
        setMaximum(0);
        setMaximum(255);
        setValue(value);
        setMinorTickSpacing(32);
        setPaintTicks(true);
        setForeground(Color.BLACK);
    }
    
}
