package panels;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import button.KeyButton;
import entity.Human;
import structures.LinkedList;

public class KeyPanel extends JPanel{
    
    LinkedList<Human> keyvalue;

    public KeyPanel(LinkedList<Human> keyvalue){

        super(new FlowLayout(FlowLayout.LEFT));

        this.keyvalue = keyvalue;
        setBounds(-2, 0, 424+10, 1200);
        setOpaque(false);
        setPreferredSize(new Dimension(424,800));

        generateKeyButtons();
    }

    private void generateKeyButtons(){

        for(int x = 0; x < keyvalue.getSize(); x++){
            add(new KeyButton(keyvalue.indexOf(x),this));
        }

    }

    public void removeHumanKeyValue(String id, KeyButton kb){
        keyvalue.deleteElement(new Human(id));
        this.remove(kb);
    }

}
