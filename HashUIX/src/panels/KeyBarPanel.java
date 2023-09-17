package panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import button.KeyButton;

public class KeyBarPanel extends JPanel implements MouseWheelListener{
    
    MarkPanel markpanel;
    
    int height;
    int width;

    int dx = 0;
    int dy = 0;
    boolean movement = false;

    int ix = 0;
    int iy = 0;

    Rectangle barTarget;
    JLabel bar;
    JPanel keys = null;
    boolean barActive = true;
    Point initial;

    public KeyBarPanel(int x, int y, int width, int height, KeyPanel keys){
        super();
        setLayout(null);
        setBounds(x, y, width+10, height); // +10 to bar
        setOpaque(false);

        this.keys = keys;
        this.width = width;
        this.height = height;

        addMouseWheelListener(this);

        createScrollBar();
        add(this.keys);
    }

    
    public void changeKeyPanel(KeyPanel keychange){

        keys.setVisible(false);
        keys.setEnabled(false);
        remove(keys);
        keys = keychange;
        add(keys);
    }

    public void updateCount(){
        main.App.counter.setText(Integer.toString(main.App.hashtb.getColission()));
    }

    private void createScrollBar(){

        JPanel panelBar = new JPanel(null);
        panelBar.setBounds(0,0, this.getWidth(), this.getHeight());
        panelBar.setOpaque(false);

        bar = new JLabel();
        bar.setForeground(Color.white);

        bar.setBounds(this.getWidth()-10, 10,10, 100);

        initial = new Point(this.getWidth()-10,(this.height/2) - (bar.getHeight()/2)-5);
        bar.setLocation(initial);
        barTarget = new Rectangle(bar.getBounds());

        panelBar.add(bar);

        MouseAdapter ma = new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
        
                if(barTarget.contains(barTarget.getX() + e.getX(),barTarget.getY() + e.getY()) && barActive == true){
        
                    movement = true;
                    dx = e.getX();
                    dy = e.getY();
        
                    if(keys != null){
                        for(Component i : keys.getComponents()){
                            ((KeyButton)i).nonconflict = true;
                        }
                    }
        
                }
            }
        
            @Override
            public void mouseReleased(MouseEvent e) {
                movement = false;
                bar.setLocation(initial);
                barTarget.setLocation(initial);
                if(keys != null){
                    for(Component i : keys.getComponents()){
                        ((KeyButton)i).nonconflict = false;
                    }
                }
            }
        
            @Override
            public void mouseEntered(MouseEvent e) {
               
            }
        
            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        
            @Override
            public void mouseDragged(MouseEvent e) {
                if(movement){
                    // bar.setLocation(bar.getX() + e.getX() - dx, bar.getY() + e.getY() - dy);

                    if(height <= bar.getY()+e.getY() - dy + bar.getHeight()){
                        bar.setLocation(bar.getX(), height - bar.getHeight());
                        barTarget.setLocation(bar.getLocation());
                    }else if(0 >= bar.getY()+e.getY() - dy){
                        bar.setLocation(bar.getX(),0);
                        barTarget.setLocation(bar.getLocation());
                    }else{
                        bar.setLocation(bar.getX(), bar.getY() + e.getY() - dy);
                        barTarget.setLocation(bar.getLocation());
                        repaint();
                    }

                    int velocity = (((int)(barTarget.getY() + (barTarget.getHeight()/4) -35 - initial.getY()))*10)/232;
                    
                    scrollKeyPanel(velocity);

                    return;
                }
        
            }
        
            @Override
            public void mouseMoved(MouseEvent e) {
                // System.out.println("Move");
            }

        };

        bar.addMouseListener(ma);
        bar.addMouseMotionListener(ma);
        add(panelBar);
        repaint();
    }

    public int getMissingKeys(){

        int n = 0;
        int pressed = 0;

        if(keys != null){
            for(Component i : keys.getComponents()){
                if(((KeyButton)i).isVisible()){
                    n++;
                }
                if(((KeyButton)i).isPressed()){
                    pressed++;
                }
            }
        }

        int missing = 466 - (n*66) - ((n-1)*16) - (pressed*93);
        if(missing  < 0){
            return missing*-1;
        }
        return 0;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g.create();

        if(barActive){
            
            g2d.setColor(new Color((157f/255f)+0.3f,(161f/255f)+0.3f,(161f/255f)+0.3f,0.4f));
            if(movement){
                g2d.setColor(new Color((157f/255f)+0.3f,(161f/255f)+0.3f,(161f/255f)+0.3f,0.5f));
            }
            // g2d.setColor(new Color((157f/255f)+0.3f,(161f/255f)+0.3f,(161f/255f)+0.3f,0.4f));
            g2d.fillRect((int)barTarget.getX(),(int)barTarget.getY(),(int)barTarget.getWidth(),(int)barTarget.getHeight());
        }
    }

    int scrollcount = 0;

    public void scrollKeyPanel(int velocity){

        if(keys == null){
            return;
        }

        if(keys.getY()-velocity >= 0 ) {
            keys.setLocation(keys.getX(),0);
            scrollcount=0;
            
        }else if(keys.getY() + getMissingKeys()-velocity <= 0 && velocity > 0){
            //FIXEAR CREO QUE ES CON EL MISSING EL PROBLEMA..
        }else{
            keys.setLocation(keys.getX(),keys.getY()-velocity);
            scrollcount+=velocity;
        }
        // System.out.println("Y: " + keys.getY());
        // System.out.println("Missing: " + getMissingKeys());
        // System.out.println("Scroll count: " + scrollcount);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollKeyPanel(e.getUnitsToScroll()*10);
    }

    public MarkPanel getMarkPanel(){

        return markpanel;

    }

    public void setMarkPanel(MarkPanel markPanel){
        this.markpanel = markPanel;
    }

}
