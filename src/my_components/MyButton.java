package my_components;

import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class MyButton extends JButton implements MouseListener{
    enum states{
        NORMAL,
        PRESSED,
        AIMED
    }
    
    states st;
    
    public MyButton(String s){
        super(s);
        setMargin(new Insets(0, 0, 0, 0));
    }

    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {
        st = states.PRESSED;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        st = states.NORMAL;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        st = states.AIMED;
    }

    @Override
    public void mouseExited(MouseEvent me) {
        st = states.NORMAL;
    }
}
