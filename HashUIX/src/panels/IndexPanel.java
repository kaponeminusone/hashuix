package panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import button.IndexButton;
import structures.LinkedList;

public class IndexPanel extends JPanel{
    
    LinkedList<IndexButton> ibtn;

    public IndexPanel(LinkedList<IndexButton> ibtn){
        super();

        setBounds(0, 0, 118, 10500 + 40);
        setPreferredSize(new Dimension(118, 10500 + 40));
        // setBorder(BorderFactory.createLineBorder(Color.RED));
        setOpaque(false);

        this.ibtn = ibtn;

        generateKeyButtons();
    }

    public void generateKeyButtons(){
        for(int x = 0; x < ibtn.getSize(); x++){
           add(ibtn.indexOf(x));
        }
    }
}
