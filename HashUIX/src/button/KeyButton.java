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
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import entity.Human;
import main.App;
import panels.KeyPanel;

public class KeyButton extends JLabel{
    
    // KEY - ASSIGMENT
    Human human;
    KeyPanel keyPanel;
    //---------------

    Icon hover;
    Icon press;
    Icon unhover;
    Icon click;
    Icon click_hover;

    boolean pressed = false;
    
    public boolean nonconflict = false;
    public boolean rehover = false;

    public boolean slidemode = true;
    public boolean into = false;
    public int slide = 3;

    JLabel key;
    JLabel description;

    Color[] infohover = new Color[2];
    Color[] infounhover = new Color[2];

    Font font = new Font("Dogica Pixel Bold", Font.PLAIN, 16);
    Font font_d = new Font("Dogica Pixel", Font.PLAIN, 10);

    public void ocultar(){

        for(int x = 0; x < 256; x++){

            if(x%2 == 0){
                main.App.ibtn.indexOf(x).setVisible(false);
            }

        }

    }

    public void updateIndexButtons(){

        for(int x = 0; x < 256; x++){
            main.App.ibtn.indexOf(x).updateCounter();
        }

    }

    public KeyButton(Human human, KeyPanel keyPanel){ //Proofs
        super();

        this.setSize(425,66);
        this.setPreferredSize(new Dimension(425+slide,66));
        initImages();
        initMouseListener();

        this.human = human;
        this.keyPanel = keyPanel;

        // human = new Human("05-9165610","Letitia","Maymand","Female");

        initKeyInformation();
    }

    public KeyButton(){
        super();

        this.setSize(425,66);
        this.setPreferredSize(new Dimension(425+slide,66));
        initImages();
        initMouseListener();

        human = new Human("05-9165610","Letitia","Maymand","Female");

        initKeyInformation();
    }

    private void initKeyInformation(){
        
        infohover[0] = new Color(238, 232, 213);
        infohover[1] = new Color(238, 232, 213);

        infounhover[0] = new Color(147, 161, 161);
        infounhover[1] = new Color(147+10, 161+10, 161+10);


        key = new JLabel();
        key.setBounds(50,16,200,30);
        key.setFont(font);
        key.setForeground(infounhover[0]);
        key.setText("~  " + human.getId());

        add(key);

        description = new JLabel("AA");
        description.setBounds(10, 85,400, 65);
        description.setVerticalAlignment(SwingConstants.TOP);
        description.setFont(font_d);
        String text =   " + Name: " + human.getName() + "<p><p>" +
                        " + Lastname: " + human.getLastname() + "<p><p>" +
                        " + Gender: " + human.getGender();
        description.setText("<html><div style='text-align: left; padding-top: 5px;'>" + text + "</div></html>");
        description.setForeground(infounhover[1]);

        JLabel typeclass = new JLabel("<class.Human>");
        typeclass.setBounds(290, 40, 120, 12);
        typeclass.setFont(font_d);
        typeclass.setForeground(new Color(0,43,54));
        description.add(typeclass);
        add(description);

        // Border border = BorderFactory.createLineBorder(Color.BLUE); // crea un borde de línea simple azul
        // description.setBorder(border);
        // typeclass.setBorder(border);

    }

    private void setInfoHover(){
        key.setForeground(infohover[0]);
        description.setForeground(infohover[1]);
    }

    private void setInfoUnhover(){
        key.setForeground(infounhover[0]);
        description.setForeground(infounhover[1]);
    }


    private void initImages(){
        String path = "resources/key/";
        unhover = new ImageIcon(path + "unhover.png");
        hover = new ImageIcon(path + "hover.png");
        press = new ImageIcon(path + "press.png");
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
        if(x <= this.getWidth() && x >= this.getWidth()-13 &&
            y <= 13 && y >= 0){
            
            JLabel label = new JLabel(new ImageIcon("resources/others/note.png"));
            // JOptionPane.showMessageDialog(App.a, label, "Mi JOptionPane con imagen", JOptionPane.PLAIN_MESSAGE);
            int confirmed = JOptionPane.showConfirmDialog(label, "¿Eliminate a Human?", "Note confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,new ImageIcon("resources/others/note.png"));
            
            if(confirmed != JOptionPane.YES_OPTION){
                return;
            }

            this.setVisible(false);
            this.setEnabled(false);
            keyPanel.removeHumanKeyValue(human.getId(),this);
            updateIndexButtons();
           
        }

        if(!pressed){
            pressed = true;

            this.setSize(425,159);
            this.setPreferredSize(new Dimension(425+slide,159));

            this.setIcon(click);

            return;
        }
        pressed = false;
        this.setSize(425,66);
        this.setPreferredSize(new Dimension(425+slide,66));
        

        this.setIcon(unhover);
        
    }

    public void mouseExit(){

        //HARDCODED -- Only visual Effect
        if(slidemode){
            into = false;
            this.setHorizontalAlignment(SwingConstants.LEFT);
            key.setLocation(key.getX()-slide,key.getY());
            description.setLocation(description.getX()-slide,description.getY());
        }
        //--------------------------------

        setInfoUnhover();

        if(!pressed){
            this.setIcon(unhover);
            return;
        }
        this.setIcon(click);
    }

    public void mouseEnter(){
        
        //HARDCODED -- Only visual Effect
        if(into == false && slidemode){ 
           into = true;
           this.setHorizontalAlignment(SwingConstants.RIGHT);
           key.setLocation(key.getX()+slide,key.getY());
           description.setLocation(description.getX()+slide,description.getY());
        }
        //--------------------------------
        setInfoHover();
        
        if(!pressed){
            this.setIcon(hover);
            return;
        }

        this.setIcon(click_hover);
    }

}
