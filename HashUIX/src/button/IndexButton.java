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
import panels.MarkPanel;
import structures.LinkedList;

public class IndexButton extends JLabel{
    
    // KEY - ASSIGMENT
    int index = 0;
    LinkedList<Human> indexHash;
    KeyBarPanel barPanel;
    //---------------

    Icon hover;
    Icon unhover;
    Icon click;
    Icon click_hover;

    boolean pressed = false;
    
    public boolean nonconflict = false;
    public boolean rehover = false;

    JLabel indexh;
    JLabel counter;

    Color[] infohover = new Color[2];
    Color[] infounhover = new Color[3];

    Font font = new Font("Dogica Pixel Bold", Font.PLAIN, 14);
    Font font_d = new Font("Dogica Pixel", Font.PLAIN, 10);

    public IndexButton(int index ,LinkedList<Human> indexHash, KeyBarPanel barPanel){ //Proofs

        super();
        this.setSize(118,36);
        this.setPreferredSize(new Dimension(118,36));
        initImages();
        initMouseListener();
        
        this.index = index;
        this.indexHash = indexHash;
        this.barPanel = barPanel;

        initIndexData();
    }

    private void initIndexData(){
        
        infohover[0] = new Color(238, 232, 213);
        infohover[1] = new Color(238, 232, 213);

        infounhover[0] = new Color(147, 161, 161);
        infounhover[1] = new Color(88, 110, 117);
        infounhover[2] = new Color(147, 161, 161);

        indexh = new JLabel();
        indexh.setBounds(2,3,52,28);
        indexh.setVerticalAlignment(SwingConstants.CENTER);
        indexh.setHorizontalAlignment(SwingConstants.CENTER);
        indexh.setFont(font);
        indexh.setForeground(infounhover[0]);
        indexh.setText(Integer.toString(index));

        add(indexh);

        counter = new JLabel("AA");
        counter.setBounds(65, 2,46, 24);
        counter.setVerticalAlignment(SwingConstants.CENTER);
        counter.setHorizontalAlignment(SwingConstants.CENTER);
        counter.setFont(font);
        String text = Integer.toString(indexHash.getSize()); 
        counter.setText("<html><div style='text-align: center; padding-top: 5px;'>" + text + "</div></html>");
        counter.setText(text);
        counter.setForeground(infounhover[1]);
        add(counter);

        // Border border = BorderFactory.createLineBorder(Color.BLUE); // crea un borde de línea simple azul
        // counter.setBorder(border);
        // indexh.setBorder(border);

    }

    public void updateCounter(){

        String text = Integer.toString(indexHash.getSize()); 
        counter.setText("<html><div style='text-align: center; padding-top: 5px;'>" + text + "</div></html>");
        counter.setText(text);
        barPanel.updateCount();
    }


    private void setInfoHover(){
        // indexh.setForeground(infohover[0]);
        counter.setForeground(infohover[1]);
    }

    private void setInfoUnhover(){
        // indexh.setForeground(infounhover[0]);
        counter.setForeground(infounhover[1]);
        if(pressed){
            counter.setForeground(infounhover[2]);
        }
    }


    private void initImages(){
        String path = "resources/index/";
        unhover = new ImageIcon(path + "unhover.png");
        hover = new ImageIcon(path + "hover.png");
        click = new ImageIcon(path + "click.png");
        click_hover = new ImageIcon(path + "click_hover.png");

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

    public void evokeKeyPanel(){
        barPanel.changeKeyPanel(new KeyPanel(indexHash));
    }

    public void destroyKeyPanel(){
        barPanel.changeKeyPanel(new KeyPanel(new LinkedList<Human>()));
    }

    public void addMark(){

        MarkPanel markp = barPanel.getMarkPanel();

        if(markp == null){
            evokeKeyPanel();
            return;
        }

        markp.addMarkButtons(this);
    }

    public void removeMark(){

        MarkPanel markp = barPanel.getMarkPanel();

        if(markp == null){
            return;
        }
        counter.setLocation(counter.getX(),counter.getY()-2);
        markp.removeMarkButton(this);

    }

    public void offHover(){
        counter.setLocation(counter.getX(),counter.getY()-2);
        pressed = false;
        mouseExit();
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

        if(!pressed){

            counter.setLocation(counter.getX(),counter.getY()+2);
            pressed = true;

            addMark();
            // evokeKeyPanel();

            this.setIcon(click);
            return;
        }
        pressed = false;

        this.setIcon(unhover);
        removeMark();

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

    public MarkPanel getMarkPanel(){

        return barPanel.getMarkPanel();

    }

}
