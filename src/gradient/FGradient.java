package gradient;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class FGradient {
    public static final int LINEAR = 1, CIRCLE = 2, LOG = 3, ROOT = 4;
    private int mdl;
    private int root = 2;
    private ArrayList<Nod> nods;
    private Color inside;
    
    public FGradient(){
        mdl = CIRCLE;
        inside = Color.BLACK;
        nods = new ArrayList<>();
        //nods.add(new Nod(0, Color.BLACK));
        //nods.add(new Nod(f(0.25), Color.RED));
        //nods.add(new Nod(f(0.5), Color.RED));
        //nods.add(new Nod(f(0.75), Color.BLUE));
        //nods.add(new Nod(0.5, Color.RED));
        //nods.add(new Nod(1, Color.WHITE));
    }
    
    public void changeNod(double pos, Color c, int n){
        if(n < 0 || n >= nods.size())
            return;
        nods.get(n).setColor(c);
        nods.get(n).setPos(pos);
        Collections.sort(nods, new Comparator<Nod>(){
            @Override
            public int compare(Nod n1, Nod n2){
                if(n1.getPos() > n2.getPos())
                    return 1;
                else
                    return -1;
            }
        });
    }
    
    public void addNod(double pos, Color c){
        for(int i = 0; i < nods.size(); i++){
            if(nods.get(i).getPos() == pos){
                nods.get(i).setColor(c);
                return;
            }
        }
        nods.add(new Nod(pos, c));
        Collections.sort(nods, new Comparator<Nod>(){
            @Override
            public int compare(Nod n1, Nod n2){
                if(n1.getPos() > n2.getPos())
                    return 1;
                else
                    return -1;
            }
        });
        
        
    }
    
    public int getModel(){
        return mdl;
    }
    
    public void setModel(int model){
        if(model > 0 && model <= ROOT)
            this.mdl = model;
    }
    
    public int getRootPow(){
        return root;
    }
    
    public void setRootPow(int pow){
        if(pow < 1)
            root = 1;
        else if(pow > 10)
            root = 10;
        else
            root = pow;
    }
    
    public Color getNodColor(int i){
        return nods.get(i).getColor();
    }
    
    public double getNodPos(int i){
        return nods.get(i).getPos();
    }
    
    public Color getInsideColor(){
        return inside;
    }
    
    public void setInsideColor(Color c){
        inside = c;
    }
    
    public int getSize(){
        return nods.size();
    }
    
    public Color getColor(int i, int max) {
        
        if (i == -1)
            return inside;
        else {
            double x = (double)i/max;
            x = f(x);
            for(int n = 0; n < nods.size() - 1; n++){
                if(x <= nods.get(n+1).getPos())
                    return getNodsColor(n, x);
            }
        }
        return inside;
    }
    
    Color getNodsColor(int n, double x){
        /*x = (x - reF(nods.get(n).getPos()))/
                (reF(nods.get(n+1).getPos()) - reF(nods.get(n).getPos()));
        x = f(x);*/
        x = (x - nods.get(n).getPos())/
                (nods.get(n+1).getPos() - nods.get(n).getPos());
        
        return new Color((int)((1-x)*nods.get(n).getColor().getRed() 
                + x*nods.get(n+1).getColor().getRed()),
                (int)((1-x)*nods.get(n).getColor().getGreen()
                + x*nods.get(n+1).getColor().getGreen()),
                (int)((1-x)*nods.get(n).getColor().getBlue()
                + x*nods.get(n+1).getColor().getBlue()));
    }
    
    double f(double x) {
        
        if(0 == mdl)
            return x;
        else switch (mdl) {
            case LINEAR:
                return x;
            case CIRCLE:
                return Math.sqrt(1.0 - Math.pow(x-1, 2));
            case LOG:
                return Math.log1p(x*100)/Math.log1p(100);
            case ROOT:
                return Math.pow(x, 1.0/root);
            default:
                return x;
        }
    }
    
    double reF(double x){
        if(0 == mdl)
            return x;
        else switch (mdl) {
            case LINEAR:
                return x;
            case CIRCLE:
                return -Math.sqrt(1.0 - Math.pow(x, 2)) + 1;
            case LOG:
                return (101.0*Math.pow(Math.E, x) - 1)/100;
            case ROOT:
                return Math.pow(x, root);
            default:
                return x;
        }
    }
    
    static double f(double x, int model, int root){
        if(0 == model)
            return x;
        else switch (model) {
            case LINEAR:
                return x;
            case CIRCLE:
                return Math.sqrt(1.0 - Math.pow(x-1, 2));
            case LOG:
                return Math.log1p(x*100)/Math.log1p(100);
            case ROOT:
                return Math.pow(x, 1.0/root);
            default:
                return x;
        }
    }
    
    class Nod{
        private double pos;
        private Color c;
        
        public Nod(double pos, Color c) {
            if(pos > 1)
                this.pos = 1;
            else if(pos < 0)
                this.pos = 0;
            else
                this.pos = pos;
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
}
