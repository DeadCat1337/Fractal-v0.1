package gradient;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import my_components.FractalPanel;
import my_components.MyButton;
import my_components.ColorSlider;


public class GradientWindow extends JFrame implements WindowListener{
    private FractalPanel fp;
    
    private ArrayList<ColorNod> cnd;
    int active = 0;
    
    private JLabel l_R, l_G, l_B, l_pos;
    private JTextField t_R, t_G, t_B, t_pos;
    private MyButton b_ok, b_cm, b_ca;
    private JButton b_c;
    private GradientLine g_l;
    private ColorSlider s_R, s_G, s_B;
    private JPanel p_g, p_n, p_b;
    
    private Listener l;
    
    
    public GradientWindow(FractalPanel fp){
        
        super();
        this.fp = fp;
        cnd = new ArrayList<>();
        for(int i = 0; i < fp.getGradient().getSize(); i++){
            cnd.add(new ColorNod(
                    (double)Math.round(fp.getGradient().getNodPos(i)*1000)/1000, 
                    fp.getGradient().getNodColor(i)));
            //System.out.println("" + i);
        }
        cnd.get(active).setActive(true);
        cnd.get(0).setSide(true);
        cnd.get(cnd.size()-1).setSide(true);
        //activate();
        
        initW();
        initComp();
        initSizes();
        initPositions();
        
        addWindowListener(this);
    }
    
    private void initW() {
        setTitle("Gradient Window");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(300, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        l = new Listener();
    }
    
    private void initComp(){
        p_g = new JPanel(null);
        p_n = new JPanel(null);
        p_b = new JPanel(null);
        p_g.setLayout(null);
        p_n.setLayout(null);
        p_b.setLayout(null);
        
        l_pos = new JLabel("Position:");
        l_R = new JLabel("R:");
        l_G = new JLabel("G:");
        l_B = new JLabel("B:");
        
        t_pos = new JTextField("" + cnd.get(active).getPos());
        t_R = new JTextField("" + cnd.get(active).getColor().getRed());
        t_G = new JTextField("" + cnd.get(active).getColor().getGreen());
        t_B = new JTextField("" + cnd.get(active).getColor().getBlue());
        t_pos.addActionListener(l);
        t_R.addActionListener(l);
        t_G.addActionListener(l);
        t_B.addActionListener(l);
        
        s_R = new ColorSlider(cnd.get(active).getColor().getRed());
        s_G = new ColorSlider(cnd.get(active).getColor().getGreen());
        s_B = new ColorSlider(cnd.get(active).getColor().getBlue());
        s_R.addChangeListener(l);
        s_G.addChangeListener(l);
        s_B.addChangeListener(l);
        
        b_ok = new MyButton("OK");
        b_cm = new MyButton("Change Model");
        b_ca = new MyButton("Cancel");
        b_ok.addActionListener(l);
        b_cm.addActionListener(l);
        b_ca.addActionListener(l);
        
        b_c = new JButton(){
            @Override
            public void paint(Graphics g){
                g.setColor(cnd.get(active).getColor());
                g.fillRect(0, 0, b_c.getWidth(), b_c.getHeight());
            }
        };
        b_c.addActionListener(l);
        
        g_l = new GradientLine();
        g_l.addMouseListener(l);
        for(int i = 0; i < cnd.size(); i++){
            cnd.get(i).addMouseMotionListener(l);
            cnd.get(i).addActionListener(l);
        }
    }

    private void initSizes(){
        p_g.setSize(getContentPane().getWidth(), 80);
        
        g_l.setSize(p_g.getWidth() - 40, 30);
        
        p_n.setSize(getContentPane().getWidth(), 0);
        
        b_c.setSize(50, 50);
        l_pos.setSize(60, 20);
        t_pos.setSize(60, 25);
        
        t_R.setSize(50, 25);
        t_G.setSize(50, 25);
        t_B.setSize(50, 25);
        
        s_R.setSize(120, 40);
        s_G.setSize(120, 40);
        s_B.setSize(120, 40);
        
        l_R.setSize(20, 20);
        l_G.setSize(20, 20);
        l_B.setSize(20, 20);
        
        
        p_b.setSize(getContentPane().getWidth(), 0);
        
        b_ok.setSize(80, 25);
        b_cm.setSize(120, 25);
        b_ca.setSize(80, 25);
    }
    
    private void initPositions(){
        p_g.setLocation(0, 0);
        add(p_g);
        
        g_l.setLocation(20, 10);
        p_g.add(g_l);
        
        for(int i = 0; i < cnd.size(); i++){
            cnd.get(i).setLocation((int)(g_l.getX() + 
                    g_l.getWidth()*cnd.get(i).pos - cnd.get(i).getWidth()/2), 
                    g_l.getHeight()+g_l.getY());
            p_g.add(cnd.get(i));
        }
        
        
        p_n.setLocation(0, p_g.getHeight());
        add(p_n);
        
        b_c.setLocation(10, 10);
        l_pos.setLocation(10, b_c.getHeight() + b_c.getY());
        t_pos.setLocation(10, l_pos.getHeight() + l_pos.getY());
        
        t_R.setLocation(p_n.getWidth() - t_R.getWidth() - 10, 10);
        t_G.setLocation(p_n.getWidth() - t_G.getWidth() - 10, 
                t_R.getHeight() + t_R.getY() + 10);
        t_B.setLocation(p_n.getWidth() - t_B.getWidth() - 10, 
                t_G.getHeight() + t_G.getY() + 10);
        
        s_R.setLocation(t_R.getX() - s_R.getWidth(), t_R.getY()-5);
        s_G.setLocation(t_G.getX() - s_G.getWidth(), t_G.getY()-5);
        s_B.setLocation(t_R.getX() - s_B.getWidth(), t_B.getY()-5);
        
        l_R.setLocation(s_R.getX() - l_R.getWidth() - 10, t_R.getY());
        l_G.setLocation(s_G.getX() - l_G.getWidth() - 10, t_G.getY());
        l_B.setLocation(s_B.getX() - l_B.getWidth() - 10, t_B.getY());
        
        p_n.setSize(p_n.getWidth(), t_pos.getY() + t_pos.getHeight());
        p_n.add(b_c);
        p_n.add(l_pos);
        p_n.add(t_pos);
        p_n.add(t_R);
        p_n.add(t_G);
        p_n.add(t_B);
        p_n.add(s_R);
        p_n.add(s_G);
        p_n.add(s_B);
        p_n.add(l_R);
        p_n.add(l_G);
        p_n.add(l_B);
        
        
        p_b.setSize(getContentPane().getWidth(),
                getContentPane().getHeight() - p_n.getHeight() - p_n.getY());
        p_b.setLocation(0, p_n.getY() + p_n.getHeight());
        add(p_b);
        
        b_ok.setLocation(p_b.getWidth() - b_ok.getWidth() - 10, 
                p_b.getHeight() - b_ok.getHeight() - 10);
        b_ca.setLocation(p_b.getWidth() - b_ca.getWidth() - 10, 
                b_ok.getY() - b_ca.getHeight() - 10);
        b_cm.setLocation(10, p_b.getHeight() - b_cm.getHeight() - 10);
        
        p_b.add(b_ok);
        p_b.add(b_ca);
        p_b.add(b_cm);
    }
    
    public FGradient generateGradient(){
        /*Collections.sort(cnd, new Comparator<ColorNod>(){
            @Override
            public int compare(ColorNod cn1, ColorNod cn2){
                if(cn1.getPos() > cn2.getPos())
                    return 1;
                else
                    return -1;
            }
        });*/
        FGradient fg = new FGradient();
        fg.changeNod(cnd.get(0).getPos(), cnd.get(0).getColor(), 0);
        fg.changeNod(cnd.get(cnd.size()-1).getPos(), 
                cnd.get(cnd.size()-1).getColor(), 1);
        for(int i = 1; i < cnd.size() - 1; i++){
            fg.addNod(cnd.get(i).getPos(), cnd.get(i).getColor());
        }
        return fg;
    };
    
    public void activate(){
        setLocationRelativeTo(null);
        setExtendedState(JFrame.NORMAL);
        setVisible(true);
        requestFocus();
    }
    
    public void synch(){
        Color c = cnd.get(active).getColor();
        s_R.setValue(c.getRed());
        s_G.setValue(c.getGreen());
        s_B.setValue(c.getBlue());
        t_R.setText("" + c.getRed());
        t_G.setText("" + c.getGreen());
        t_B.setText("" + c.getBlue());
        t_pos.setText("" + cnd.get(active).getPos());
    }
    
    public class Listener implements 
            MouseListener, ActionListener, ChangeListener, MouseMotionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            for(int i = 0; i < cnd.size(); i++){
                if(e.getSource() == cnd.get(i)){
                    cnd.get(active).setActive(false);
                    active = i;
                    cnd.get(i).setActive(true);
                    synch();
                }
            }
            
            if(e.getSource() == t_R)
                s_R.setValue(Integer.parseInt(t_R.getText()));
            if(e.getSource() == t_G)
                s_G.setValue(Integer.parseInt(t_G.getText()));
            if(e.getSource() == t_B)
                s_B.setValue(Integer.parseInt(t_B.getText()));
            cnd.get(active).setColor(new Color(
                    s_R.getValue(), s_G.getValue(), s_B.getValue()));
            
            b_c.repaint();
            g_l.repaint();
            cnd.get(active).repaint();
            
            if(e.getSource() == b_ok){
                fp.setGradient(generateGradient());
                fp.repaint();
            }
            
            if(e.getSource() == b_ca) {
                p_g.removeAll();
                p_g.add(g_l);
                cnd = new ArrayList<>();
                for (int i = 0; i < fp.getGradient().getSize(); i++) {
                    cnd.add(new ColorNod((double) Math.round(fp.getGradient().
                            getNodPos(i) * 1000) / 1000,
                            fp.getGradient().getNodColor(i)));
                    cnd.get(i).addMouseMotionListener(l);
                    cnd.get(i).addActionListener(l);
                    cnd.get(i).setLocation((int) (g_l.getX()+ g_l.getWidth() * 
                            cnd.get(i).pos - cnd.get(i).getWidth() / 2), 
                            g_l.getHeight() + g_l.getY());
                    p_g.add(cnd.get(i));
                    cnd.get(i).repaint();
                }
                active = 0;
                cnd.get(active).setActive(true);
                cnd.get(0).setSide(true);
                cnd.get(cnd.size() - 1).setSide(true);
                synch();
                b_c.repaint();
                g_l.repaint();
            }
        }
        
        @Override
        public void stateChanged(ChangeEvent e) {
            if(e.getSource() == s_R)
                t_R.setText("" + s_R.getValue());
            if(e.getSource() == s_G)
                t_G.setText("" + s_G.getValue());
            if(e.getSource() == s_B)
                t_B.setText("" + s_B.getValue());
            cnd.get(active).setColor(new Color(
                    s_R.getValue(), s_G.getValue(), s_B.getValue()));
            b_c.repaint();
            g_l.repaint();
            cnd.get(active).repaint();
        }
        
        @Override
        public void mouseClicked(MouseEvent me) {}

        @Override
        public void mousePressed(MouseEvent me) {}

        @Override
        public void mouseReleased(MouseEvent me) {}

        @Override
        public void mouseEntered(MouseEvent me) {}

        @Override
        public void mouseExited(MouseEvent me) {}

        @Override
        public void mouseDragged(MouseEvent me) {
        }

        @Override
        public void mouseMoved(MouseEvent me) {
        }
    }

    public class GradientLine extends JButton{

        public GradientLine() {
            super();
        }
        
        @Override
        public void paint(Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            float[] ps = new float[cnd.size()];
            Color[] cs = new Color[cnd.size()];
            for (int i = 0; i < cnd.size(); i++) {
                ps[i] = (float) cnd.get(i).getPos();
                cs[i] = cnd.get(i).getColor();
            }
            g2d.setPaint(new LinearGradientPaint(0, 0,
                    g_l.getWidth(), 0, ps, cs,
                    MultipleGradientPaint.CycleMethod.NO_CYCLE));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        }
        
    }

    
    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosing(WindowEvent we) {
        fp.requestFocus();
        setVisible(false);
    }

    @Override
    public void windowClosed(WindowEvent we) {
    }

    @Override
    public void windowIconified(WindowEvent we) {
        fp.requestFocus();
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        //fp.requestFocus();
    }
}
