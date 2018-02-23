package window;

import gradient.GradientWindow;
import java.awt.Insets;
import my_components.FractalPanel;
import my_components.MyButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Window extends JFrame {

    private FractalPanel fractal;
    private Listener l;
    private OptionPanel op;

    public Window() {
        super();
        initW();
        initComp();
        setVisible(true);
        repaint();
    }

    private void initW() {
        setTitle("Fractal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setVisible(true);
    }
    
    private void initComp(){
        l = new Listener();
        
        op = new OptionPanel(150, getContentPane().getHeight());
        op.setLocation(0, 0);
        add(op);

        fractal = new FractalPanel();
        fractal.setSize(getContentPane().getWidth() - op.getWidth(), 
                getContentPane().getHeight());
        fractal.setLocation(op.getWidth(), 1);
        add(fractal);

        op.refresh();

        this.addKeyListener(l);
        op.addKeyListener(l);
        fractal.addKeyListener(l);
        fractal.addMouseListener(l);
    }

    public class Listener implements KeyListener, MouseListener{

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            //System.out.println(e.getKeyCode());

            int i = e.getKeyCode();

            if (i == 61 || i == 107)//plus
            {
                fractal.zoomIn();
                op.refresh();
                fractal.repaint();
            }
            if (i == 45 || i == 109)//minus
            {
                fractal.zoomOut();
                op.refresh();
                fractal.repaint();
            }

            if (i == 37 || i == 65)//left
            {
                fractal.moveLeft();
                op.refresh();
                fractal.repaint();
            }

            if (i == 39 || i == 68)//right
            {
                fractal.moveRight();
                op.refresh();
                fractal.repaint();
            }

            if (i == 38 || i == 87)//up
            {
                fractal.moveUp();
                op.refresh();
                fractal.repaint();
            }

            if (i == 40 || i == 83)//down
            {
                fractal.moveDown();
                op.refresh();
                fractal.repaint();
            }

            if (i == 46 || i == 69)//>
            {
                fractal.incItr();
                op.refresh();
                fractal.repaint();
            }

            if (i == 44 || i == 81)//<
            {
                fractal.decItr();
                op.refresh();
                fractal.repaint();

            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void mouseClicked(MouseEvent me) {
            fractal.requestFocus();
        }

        @Override
        public void mousePressed(MouseEvent me) {}

        @Override
        public void mouseReleased(MouseEvent me) {}

        @Override
        public void mouseEntered(MouseEvent me) {}

        @Override
        public void mouseExited(MouseEvent me) {}
    }

    public class OptionPanel extends JPanel implements ActionListener {

        private MyButton b_show, b_ok, b_scP, b_scM, 
                b_up, b_down, b_left, b_right, 
                b_itrP, b_itrM, b_color;
        private JTextField t_scale, t_X, t_Y, t_iter;
        private JLabel l_loc, l_scale, l_X, l_Y, l_iter;
        private GradientWindow gw;

        private boolean on;

        public OptionPanel(int X, int Y) {
            if (X < 100) {
                X = 100;
            }

            setLayout(null);
            setSize(X, Y);

            on = true;

            initComponents();
            initPositions(X, Y);

        }

        private void initComponents() {
            b_show = new MyButton("<<<");
            b_show.addActionListener(this);

            l_scale = new JLabel("Scale:");

            b_scP = new MyButton("+");
            b_scP.addActionListener(this);

            b_scM = new MyButton("-");
            b_scM.addActionListener(this);

            t_scale = new JTextField();
            t_scale.addActionListener(this);

            l_loc = new JLabel("Location:");

            l_X = new JLabel("X:");

            l_Y = new JLabel("Y:");

            t_X = new JTextField();
            t_X.addActionListener(this);

            t_Y = new JTextField();
            t_Y.addActionListener(this);

            b_left = new MyButton("<");
            b_left.addActionListener(this);

            b_up = new MyButton("^");
            b_up.addActionListener(this);

            b_down = new MyButton("v");
            b_down.addActionListener(this);

            b_right = new MyButton(">");
            b_right.addActionListener(this);

            l_iter = new JLabel("Iterations:");

            b_itrP = new MyButton("+");
            b_itrP.addActionListener(this);

            b_itrM = new MyButton("-");
            b_itrM.addActionListener(this);

            t_iter = new JTextField("200");
            t_iter.addActionListener(this);
            
            b_ok = new MyButton("OK");
            b_ok.addActionListener(this);
            
            b_color = new MyButton("Gradient");
            b_color.addActionListener(this);
        }

        private void initPositions(int X, int Y) {

            b_show.setSize(X, 20);
            b_show.setLocation(0, 0);
            add(b_show);

            l_scale.setSize(X, 20);
            l_scale.setLocation(0, 25);
            add(l_scale);

            b_scM.setSize(25, 25);
            b_scM.setLocation(0, 45);
            add(b_scM);            

            b_scP.setSize(25, 25);
            b_scP.setLocation(X - 25, 45);
            add(b_scP);

            t_scale.setSize(X - 50, 26);
            t_scale.setLocation(25, 45);
            add(t_scale);

            l_loc.setSize(X, 20);
            l_loc.setLocation(0, 75);
            add(l_loc);

            l_X.setSize(20, 20);
            l_X.setLocation(0, 95);
            add(l_X);

            l_Y.setSize(20, 20);
            l_Y.setLocation(0, 115);
            add(l_Y);

            t_X.setSize(X - 20, 20);
            t_X.setLocation(20, 95);
            add(t_X);

            t_Y.setSize(X - 20, 20);
            t_Y.setLocation(20, 115);
            add(t_Y);

            b_left.setSize(30, 30);
            b_left.setLocation(0, 135);
            add(b_left);

            b_up.setSize(X - 60, 15);
            b_up.setLocation(30, 135);
            add(b_up);

            b_down.setSize(X - 60, 15);
            b_down.setLocation(30, 150);
            add(b_down);

            b_right.setSize(30, 30);
            b_right.setLocation(X - 30, 135);
            add(b_right);

            l_iter.setSize(X, 20);
            l_iter.setLocation(0, 170);
            add(l_iter);

            b_itrM.setSize(25, 25);
            b_itrM.setLocation(0, 190);
            add(b_itrM);

            b_itrP.setSize(25, 25);
            b_itrP.setLocation(X - 25, 190);
            add(b_itrP);

            t_iter.setSize(X - 50, 26);
            t_iter.setLocation(25, 190);
            add(t_iter);
            
            b_color.setSize(X, 30);
            b_color.setLocation(0, 230);
            add(b_color);

            b_ok.setSize(X, 30);
            b_ok.setLocation(0, Y - 30);
            add(b_ok);
        }

        public void refresh() {
            t_scale.setText("" + fractal.getScale());
            t_X.setText("" + fractal.getFX());
            t_Y.setText("" + fractal.getFY());
            t_iter.setText("" + fractal.getItr());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == b_show) {
                if (on) {
                    setLocation(-getWidth(), 0);
                    setSize(getWidth() + 20, getHeight());
                    b_show.setSize(20, getHeight());
                    b_show.setLocation(getWidth() - 20, 0);
                    b_show.setText("");
                    fractal.setLocation(20, 0);
                    fractal.setSize(fractal.getWidth() + getWidth() - 20, fractal.getHeight());
                    on = false;
                } else {
                    setLocation(0, 0);
                    setSize(getWidth() - 20, getHeight());
                    b_show.setSize(getWidth(), 20);
                    b_show.setLocation(0, 0);
                    b_show.setText("<<<");
                    fractal.setLocation(getWidth(), 0);
                    fractal.setSize(fractal.getWidth() - getWidth(), fractal.getHeight());
                    on = true;
                }
                fractal.repaint();
                fractal.requestFocus();
            }
            
            if (e.getSource() == b_scP) {
                fractal.zoomIn();
                t_scale.setText("" + fractal.getScale());
                fractal.requestFocus();
                fractal.repaint();
            }
            
            if (e.getSource() == b_scM) {
                fractal.zoomOut();
                t_scale.setText("" + fractal.getScale());
                fractal.requestFocus();
                fractal.repaint();
            }

            if (e.getSource() == b_left) {
                fractal.moveLeft();
                t_X.setText("" + fractal.getFX());
                fractal.requestFocus();
                fractal.repaint();
            }

            if (e.getSource() == b_right) {
                fractal.moveRight();
                t_X.setText("" + fractal.getFX());
                fractal.requestFocus();
                fractal.repaint();
            }

            if (e.getSource() == b_up) {
                fractal.moveUp();
                t_Y.setText("" + fractal.getFY());
                fractal.requestFocus();
                fractal.repaint();
            }

            if (e.getSource() == b_down) {
                fractal.moveDown();
                t_Y.setText("" + fractal.getFY());
                fractal.requestFocus();
                fractal.repaint();
            }

            if (e.getSource() == b_itrP) {
                fractal.incItr();
                t_iter.setText("" + fractal.getItr());
                fractal.requestFocus();
                fractal.repaint();
            }

            if (e.getSource() == b_itrM) {
                fractal.decItr();
                t_iter.setText("" + fractal.getItr());
                fractal.requestFocus();
                fractal.repaint();

            }
            
            if (e.getSource() == t_X) {
                fractal.setFX(Double.parseDouble(t_X.getText()));
                //fractal.requestFocus();
                fractal.repaint();
            }
            
            if (e.getSource() == t_Y) {
                fractal.setFY(Double.parseDouble(t_Y.getText()));
                //fractal.requestFocus();
                fractal.repaint();
            }
            
            if (e.getSource() == t_scale) {
                fractal.setScale(Double.parseDouble(t_scale.getText()));
                //fractal.requestFocus();
                fractal.repaint();
            }
            
            if (e.getSource() == t_iter) {
                fractal.setItr(Integer.parseInt(t_iter.getText()));
                //fractal.requestFocus();
                fractal.repaint();
            }
            
            if (e.getSource() == b_color) {
                fractal.setFX(Double.parseDouble(t_X.getText()));
                fractal.setFY(Double.parseDouble(t_Y.getText()));
                fractal.setScale(Double.parseDouble(t_scale.getText()));
                fractal.setItr(Integer.parseInt(t_iter.getText()));
                if(gw == null){
                    gw = new GradientWindow(fractal);
                } else {
                    gw.activate();
                }
                fractal.repaint();
            }

            if (e.getSource() == b_ok) {
                fractal.setFX(Double.parseDouble(t_X.getText()));
                fractal.setFY(Double.parseDouble(t_Y.getText()));
                fractal.setScale(Double.parseDouble(t_scale.getText()));
                fractal.setItr(Integer.parseInt(t_iter.getText()));
                fractal.requestFocus();
                fractal.repaint();
            }
        }
    }

    public static void main(String[] args) {
        new Window();
    }

}
