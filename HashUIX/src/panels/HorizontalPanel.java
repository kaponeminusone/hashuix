package panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import button.IndexButton;
import button.MarkButton;

public class HorizontalPanel extends JPanel implements MouseWheelListener{
    
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

    Icon left;
    Icon right;
    JLabel dirRight;
    JLabel dirLeft;

    public HorizontalPanel(int x, int y, int width, int height, JPanel indexes,JFrame frame){
        super();

        JPanel auxiliar = new JPanel(null);
        auxiliar.setBounds(x-10, y, width+20, height+10);
        auxiliar.setOpaque(false);
        // auxiliar.setBorder(BorderFactory.createLineBorder(Color.green));
        auxiliar.add(this);
        frame.add(auxiliar);

        setLayout(null);
        setBounds(10, 0, width, height+10); // +10 to bar
        setOpaque(false);
        // setBorder(BorderFactory.createLineBorder(Color.RED));
        this.indexes = indexes;
        this.width = width;
        this.height = height;

        addMouseWheelListener(this);
        
        createScrollBar();

        if(indexes != null){
            add(this.indexes);
        }

        initImages(auxiliar);
    }

    private void initImages(JPanel auxiliar){

        String path = "resources/tabhorizon/";
        left = new ImageIcon(path + "left.png");
        right = new ImageIcon(path + "left.png");

        dirLeft = new JLabel(left);
        dirLeft.setBounds(5, 7, 10, 24);

        dirRight = new JLabel(right);
        dirRight.setBounds(width+5, 7, 10, 24);

        auxiliar.add(dirLeft);
        auxiliar.add(dirRight);

        dirLeft.setVisible(false);

        if(getMissingKeys() < 0){
            dirRight.setVisible(false);
        }
    }

    private void createScrollBar(){

        JPanel panelBar = new JPanel(null);
        panelBar.setBounds(0,0, this.getWidth(), this.getHeight());
        panelBar.setOpaque(false);

        bar = new JLabel();
        bar.setForeground(Color.white);

        bar.setBounds(0, 0,100, 10);

        initial = new Point((this.getWidth()/2)- (bar.getWidth()/2)-5 ,0);
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
                            ((MarkButton)i).nonconflict = true;
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
                        ((MarkButton)i).nonconflict = false;
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

                    if(width <= bar.getX() + e.getX() - dx + bar.getWidth()){

                        bar.setLocation(width-bar.getWidth(),bar.getY());

                    }else if( 0 >= bar.getX() + e.getX() - dx){

                        bar.setLocation(0,bar.getY());
                        
                    }else{

                        bar.setLocation(bar.getX() + e.getX() - dx, bar.getY());
                        repaint();
                    }

                    barTarget.setLocation(bar.getLocation());

                    int velocity = (((int)(barTarget.getX() + (barTarget.getWidth()/4) -35 - initial.getX()))*4)/232;

                    scrollbypixel = 53*Math.abs(velocity + 1);
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
                if(((MarkButton)i).isVisible()){
                    actives++;
                }
            }
        }
        if(((53*actives)) - (53*8) + indexes.getX() <= 0){
            return 0;
        }
        return ((53*actives)) - (53*8) + indexes.getX();
    } 

    int scrollcount = 0;
    int scrollbypixel = 53;

    public void scrollKeyPanel(int velocity){

        if(indexes == null){
            return;
        }  

        if(velocity < 0){
            scrollbypixel*=-1;
        }

        if(indexes.getX() - scrollbypixel >= 0 ) {
            indexes.setLocation(0,indexes.getY());
            scrollcount=0;
            dirLeft.setVisible(false);
            
        }else if(getMissingKeys() <= 0 && scrollbypixel > 0 && indexes.getX() < 0){
            System.out.println("aaa");
            indexes.setLocation(-((53*actives) - (53*8)),indexes.getY());
            dirLeft.setVisible(true);

        }else{
            
            if(getMissingKeys() != 0 && indexes.getX() < 0 || getMissingKeys() > 0 && velocity >= 1){
                
                indexes.setLocation(indexes.getX()-scrollbypixel,indexes.getY());
                scrollcount+=scrollbypixel;
                dirLeft.setVisible(true);
                dirRight.setVisible(true);

            }else if(indexes.getX() < 0 && velocity <= 1){

                indexes.setLocation(indexes.getX()-scrollbypixel,indexes.getY());
                scrollcount+=scrollbypixel;
                dirLeft.setVisible(true);
                dirRight.setVisible(true);
            }
        }

        if(getMissingKeys() == -53){
            dirRight.setVisible(false);
        }

        System.out.println(scrollcount + "  " +  indexes.getX() + "  " + getMissingKeys());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollbypixel = 53;
        scrollKeyPanel(e.getUnitsToScroll()*10);
    }

}

