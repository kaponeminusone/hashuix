package panels;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import button.IndexButton;
import button.MarkButton;
import structures.LinkedList;
import structures.Node;

public class MarkPanel extends JPanel{
    
    LinkedList<MarkButton> marks;
    IndexBarPanel barPanel;

    public MarkPanel(IndexBarPanel barPanel){

        super();

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBounds(0, 0, 1200,24+10);
        setOpaque(false);
        // setBorder(BorderFactory.createLineBorder(Color.BLUE));
        setOpaque(false);

        marks = new LinkedList<MarkButton>(){

            public int comparableTo(MarkButton b1, MarkButton b2){

                if(b1.getIndex() == b2.getIndex()){
                    return 0;
                }
                return 1;
            }
        };

        this.barPanel = barPanel;

    }

    public void addMarkButtons(IndexButton ibtn){
        
        MarkButton auxiliarbtn = new MarkButton(ibtn);

        Node<MarkButton> node = marks.getNodeElement(auxiliarbtn);

        MarkButton mark;

        if(node == null){
            mark = auxiliarbtn;
            marks.add(mark);
            add(mark);
        }else{
            // System.out.println("AAAAA");
            mark = node.getValue();
        }

        for(int x = 0; x < marks.getSize(); x++){
            marks.indexOf(x).offhover();
        }
        
        mark.setVisible(true);
        mark.onhover();
        ibtn.evokeKeyPanel();
        
    }

    public void removeMarkButton(IndexButton ibtn){

        // System.out.println("AAAAAA");
        MarkButton auxiliarbtn = new MarkButton(ibtn);

        Node<MarkButton> node = marks.getNodeElement(auxiliarbtn);

        MarkButton mark;
        if(node == null){
            // System.out.println("Wtf");
            return;
        }else{
            mark = node.getValue();
        }
        
        mark.setVisible(false);
        ibtn.destroyKeyPanel();
        // remove(mark);
        // marks.deleteElement(mark);

    }

    public void removeAllMarks(){

        MarkButton aux = marks.indexOf(0);

        for(int x = 0; x < marks.getSize(); x++){

            aux = marks.indexOf(x);
            aux.setVisible(false);
            aux.offhover();
            aux.getIndexButton().offHover();
            remove(aux);
        }

        marks = new LinkedList<MarkButton>(){

            public int comparableTo(MarkButton b1, MarkButton b2){

                if(b1.getIndex() == b2.getIndex()){
                    return 0;
                }
                return 1;
            }
        };

        aux.getIndexButton().destroyKeyPanel();
    }

}