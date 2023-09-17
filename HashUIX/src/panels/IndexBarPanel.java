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

import button.IndexButton;

public class IndexBarPanel extends JPanel implements MouseWheelListener{
    
    int height;
    int width;

    int dx = 0;
    int dy = 0;
    boolean movement = false;

    int ix = 0;
    int iy = 0;

    Rectangle barTarget;
    JLabel bar;
    JPanel indexes = null;
    boolean barActive = true;
    Point initial;

    public IndexBarPanel(int x, int y, int width, int height, JPanel indexes){
        super();
        setLayout(null);
        setBounds(x, y, width+10, height); // +10 to bar
        setOpaque(false);

        this.indexes = indexes;
        this.width = width;
        this.height = height;

        addMouseWheelListener(this);

        createScrollBar();
        add(this.indexes);
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
        
                    if(indexes != null){
                        for(Component i : indexes.getComponents()){
                            ((IndexButton)i).nonconflict = true;
                        }
                    }
        
                }
            }
        
            @Override
            public void mouseReleased(MouseEvent e) {
                movement = false;
                bar.setLocation(initial);
                barTarget.setLocation(initial);
                
                if(indexes != null){
                    for(Component i : indexes.getComponents()){
                        ((IndexButton)i).nonconflict = false;
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

                    int velocity = (((int)(barTarget.getY() + (barTarget.getHeight()/4) -35 - initial.getY()))*4)/200;
                    scrollbypixel = 41*Math.abs(velocity + 1);
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

    int actives = 0;

    public int getMissingKeys(){

        actives = 0;
        if(indexes != null){
            for(Component i : indexes.getComponents()){
                if(((IndexButton)i).isVisible()){
                    actives++;
                }
            }
        }

        return ((41*actives)) - (41*9) + indexes.getY();
    }

    int scrollcount = 0;
    int scrollbypixel = 41;

    public void scrollKeyPanel(int velocity){

        if(indexes == null){
            return;
        }  

        if(velocity < 0){
            scrollbypixel*=-1;
        }

        if(indexes.getY() - scrollbypixel >= 0 ) {
            indexes.setLocation(indexes.getX(),0);
            scrollcount=0;
            
        }else if(getMissingKeys() <= -41 && scrollbypixel > 0){

            indexes.setLocation(indexes.getX(),-41 - ((41*actives) - (41*9)));
            
        }else{
            indexes.setLocation(indexes.getX(),indexes.getY()-scrollbypixel);
            scrollcount+=scrollbypixel;
        }

        // System.out.println(scrollcount + "  " +  indexes.getY() + "  " + getMissingKeys());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollbypixel = 41;
        scrollKeyPanel(e.getUnitsToScroll()*10);
    }

}
