package button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import entity.Human;
import panels.KeyBarPanel;
import panels.KeyPanel;
import structures.LinkedList;

public class MarkButton extends JLabel{
    
    // KEY - ASSIGMENT
    int index = 0;
    IndexButton indexbutton;
    //---------------

    Icon hover;
    Icon unhover;
    Icon click;
    Icon click_hover;

    boolean pressed = false;
    
    public boolean nonconflict = false;
    public boolean rehover = false;

    JLabel indexh;

    Color[] infohover = new Color[2];
    Color[] infounhover = new Color[3];

    Font font = new Font("Dogica Pixel Bold", Font.PLAIN, 12);
    Font font_d = new Font("Dogica Pixel", Font.PLAIN, 10);

    public MarkButton(IndexButton indexbutton){ //Proofs

        super();
        this.setSize(48,24);
        this.setPreferredSize(new Dimension(48,24));
        initImages();
        initMouseListener();
        
        this.index = indexbutton.getIndex();
        this.indexbutton = indexbutton;

        initIndexData();
    }

    private void initIndexData(){
        
        infohover[0] = new Color(238, 232, 213);
        infohover[1] = new Color(238, 232, 213);

        infounhover[0] = new Color(147, 161, 161);
        infounhover[1] = new Color(88, 110, 117);
        infounhover[2] = new Color(147, 161, 161);

        indexh = new JLabel();
        indexh.setBounds(7,3,34,18);
        indexh.setVerticalAlignment(SwingConstants.CENTER);
        indexh.setHorizontalAlignment(SwingConstants.CENTER);
        indexh.setFont(font);
        indexh.setForeground(infounhover[0]);
        indexh.setText(Integer.toString(index));

        add(indexh);

        // Border border = BorderFactory.createLineBorder(Color.BLUE); // crea un borde de línea simple azul
        // counter.setBorder(border);
        // indexh.setBorder(border);

    }

    private void setInfoHover(){
        indexh.setForeground(infohover[0]);
    }

    private void setInfoUnhover(){
        indexh.setForeground(infounhover[0]);
        if(pressed){
            indexh.setForeground(infohover[0]);
        }
    }

    public void offhover(){
        pressed = false;
        // mouseEnter();
    }

    public void onhover(){
        pressed = true;
        mouseEnter();
        setIcon(click);
    }

    private void initImages(){
        String path = "resources/tabhorizon/";
        unhover = new ImageIcon(path + "unhover.png");
        hover = new ImageIcon(path + "delete.png");
        click = new ImageIcon(path + "hover.png");
        click_hover = new ImageIcon(path + "delete.png");

        this.setIcon(unhover);
    }

    public void setNonConflict(boolean state){
        this.nonconflict = state;
        if(state){
            this.rehover = state;
        }
    }

    public boolean isPressed(){
        return this.pressed;
    }

    private void evokeKeyPanel(){
        indexbutton.addMark();
    }

    private void initMouseListener(){

        MouseAdapter m1 = new MouseAdapter() {
            public void mouseClicked(MouseEvent evt){
                if(!nonconflict){
                    mouseClick(evt);

                }
            }
            public void mouseEntered(MouseEvent evt){
                if(!nonconflict){
                    mouseEnter();

                }
            }
            public void mouseExited(MouseEvent evt){
                if(!nonconflict){
                    mouseExit();
                }
            }

            public void mouseMoved(MouseEvent evt){
                if(!nonconflict || rehover){
                    mouseEnter();
                    rehover = false;
                }

            }

        };

        this.addMouseListener(m1);
        this.addMouseMotionListener(m1);
    }

    public void mouseClick(MouseEvent evt){

        int x = evt.getX();
        int y = evt.getY();

        if(x <= this.getWidth() && x >= this.getWidth()-10 &&
            y <= 10 && y >= 0){

            this.setVisible(false);
            this.setEnabled(false);
            indexbutton.pressed = false;
            indexbutton.offHover();
            indexbutton.destroyKeyPanel();

            return;
        }

        if(!pressed){

            pressed = true;
            evokeKeyPanel();

            this.setIcon(click);
            return;
        }
        
        pressed = false;
        this.setIcon(unhover);
        
    }

    public void mouseExit(){

        setInfoUnhover();

        if(!pressed){
            this.setIcon(unhover);
            return;
        }
        this.setIcon(click);
    }

    public void mouseEnter(){
        
        setInfoHover();
        
        if(!pressed){
            this.setIcon(hover);
            return;
        }

        this.setIcon(click_hover);
    }

    public int getIndex(){
    
        return this.index;
    }

    public IndexButton getIndexButton(){
        return this.indexbutton;
    }
}
