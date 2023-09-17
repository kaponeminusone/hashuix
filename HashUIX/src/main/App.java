/**
 * 
 *            ||    //-+-
 *         ---||   //+
 *            ||  -|
 *    ----- @ |++/ apOneMinusOne ----
 *           ||  \\.
 *         | ||   ´\\
 *          [ ]    [ ]
 * 
 * 
 *  ~ Gloom Risen
 */ 

package main;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import button.IndexButton;
import entity.Human;
import panels.HorizontalPanel;
import panels.IndexBarPanel;
import panels.IndexPanel;
import panels.KeyBarPanel;
import panels.KeyPanel;
import panels.MarkPanel;
import structures.HashTable;
import structures.LinkedList;
import structures.Node;

public class App {

    public static HashTable<Human> hashtb;

    public static LinkedList<IndexButton> ibtn;
    public static JTextField textField;
    public static JTextField textField2;
    public static KeyBarPanel kbp;

    public static File selectedFile;
    public static Font font;
    public static JFrame a;
    public static int MODE = HashTable.MULTY;

    public static JLabel colissions = new JLabel();
    public static JLabel counter = new JLabel();

    public static void main(String[] args) throws Exception {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            System.out.println(" LookAndFeel Loaded");
        } catch (Exception e) {
            e.printStackTrace();
        }

        utilities.FontLoader.loadFont("TTF/dogicapixelbold.ttf");
        utilities.FontLoader.loadFont("TTF/dogicapixel.ttf");

        font = new Font("Dogica Pixel", Font.PLAIN, 10);

        hashtb = new HashTable<>( HashTable.MULTY){
            public int comparableToTable(Human ob1, Human ob2) {
                if(ob1.getId().equals(ob2.getId())){
                    return 0;
                }
                return 1;
            }
        };

        

        // a = new JFrame();

        // a.setBounds(0,0, 610, 800);

        reloadData();
        //================================================
    }

    public static void reloadData(){

        // ------------ BEST IDEA FOR FEW TIME -----------
        a = new JFrame();
        a.setBounds(0,0, 610, 650);
        a.setLocationRelativeTo(null);
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        a.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                
                a.dispose(); // Cerrar el JFrame
                System.exit(0); // Cerrar la aplicación
                
            }
        });
        //-----------------------------------------------
        ibtn = new LinkedList<IndexButton>(){

            public int comparableTo(IndexButton i1,IndexButton i2){
                
                if(i1.getIndex() == i2.getIndex()){
                    return 0;
                }
                return 1;
            }
        };

        //===============  KeyPanel ===================

        // App.readDataFile("resources/data/MOCK_DATA.csv",hashtb);

        System.out.println(hashtb.getColission());

        KeyPanel keyspanel = new KeyPanel(new LinkedList<Human>());

        kbp = new KeyBarPanel(151-3, 79, 425, 466-10,keyspanel);
        a.add(kbp);
    
        //----- IndexButton -------
        
        for(int i = 0; i < 256; i++){
            IndexButton buttonx = new IndexButton(i,hashtb.table[i], kbp);
            ibtn.add(buttonx);
        }

        IndexPanel indexpanel = new IndexPanel(ibtn);
        IndexBarPanel ibp = new IndexBarPanel(15, 67+82+15, 118, 41*9,indexpanel);
        a.add(ibp);

        //=============================Horizontal Panel===============================

        MarkPanel markpanel = new MarkPanel(ibp);
        kbp.setMarkPanel(markpanel);
        HorizontalPanel horpanel = new HorizontalPanel(146, 39,432,24,markpanel,a);

        //---------------- HARDCODED--------------------------------------
        //================ WITHOUT TME =================
        File f1 = new File("");

        Color bg = new Color(0,43,54);
        Color bghover = new Color(40,43+40,54+40);
        Color selected = new Color(20,43+20,54+20);

        Color colortext = new Color(238, 232, 213);
        textField = new JTextField(20);
        textField.setText(f1.getAbsolutePath() + "/resources/data/");
        textField.setBounds(560-10-400-5, 5, 400, 30);
        textField.setFont(font);
        // textField.setOpaque(false);
        textField.setBackground(bg);
        textField.setCaretColor(new Color(88, 110, 117));
        textField.setForeground(colortext);
        textField.setBorder(BorderFactory.createLineBorder(new Color(0,43-10,54-10)));
        a.add(textField);

        JLabel button = new JLabel();
        button.setBounds(560-10, 5, 30, 30);
        button.setOpaque(false);
        button.setIcon(new ImageIcon("resources/others/save.png"));
    
        a.add(button);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                
                fileChooser.setCurrentDirectory(new File(f1.getAbsolutePath() + "/resources/data/"));
                int result = fileChooser.showSaveDialog(a);
                
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    textField.setText(selectedFile.getAbsolutePath());
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(new ImageIcon("resources/others/savehover.png"));
                textField.setBorder(BorderFactory.createLineBorder(new Color(238, 232, 213)));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(new ImageIcon("resources/others/save.png"));
                textField.setBorder(BorderFactory.createLineBorder(new Color(0,43-10,54-10)));
            }
        });

        JLabel button2 = new JLabel();
        button2.setBounds(7, 5, 60, 30);
        button2.setOpaque(true);
        button2.setHorizontalAlignment(SwingConstants.CENTER);
        button2.setText("Save");
        button2.setFont(font);
        button2.setForeground(colortext);
        button2.setBackground(bg);
        // button2.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        a.add(button2);

        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(saveToFile(hashtb,textField.getText())){
                    JOptionPane.showMessageDialog(a, "File saved", "Save", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(a, "Error while save", "Save", JOptionPane.ERROR_MESSAGE);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                button2.setBackground(bghover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button2.setBackground(bg);
            }
        });

        JLabel button3 = new JLabel();
        button3.setBounds(7+60+5, 5, 60, 30);
        button3.setOpaque(true);
        button3.setHorizontalAlignment(SwingConstants.CENTER);
        button3.setText("Load");
        button3.setFont(font);
        button3.setForeground(colortext);
        button3.setBackground(bg);
        // button2.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        a.add(button3);

        button3.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {

                HashTable<Human> aux = new HashTable<>( HashTable.MULTY){
                    public int comparableToTable(Human ob1, Human ob2) {
                        if(ob1.getId().equals(ob2.getId())){
                            return 0;
                        }
                        return 1;
                    }
                };

                if(readDataFile(textField.getText(),aux)){
                    JOptionPane.showMessageDialog(a, "File Loaded - Starting Reload", "Load", JOptionPane.INFORMATION_MESSAGE);
                    hashtb = aux;

                    ibtn = new LinkedList<IndexButton>();

                    for(int i = 0; i < 256; i++){
                        IndexButton buttonx = new IndexButton(i,hashtb.table[i], kbp);
                        ibtn.add(buttonx);
                    }

                    a.dispose();
                    reloadData();

                }else{
                    JOptionPane.showMessageDialog(a, "Error while load", "Load", JOptionPane.ERROR_MESSAGE);
                }

                

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                button3.setBackground(bghover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button3.setBackground(bg);
            }
        });

        JLabel button4 = new JLabel();
        button4.setBounds(7+60+5, 45, 60, 30);
        button4.setOpaque(true);
        button4.setHorizontalAlignment(SwingConstants.CENTER);
        button4.setText("Multy");
        button4.setFont(font);
        button4.setForeground(colortext);

        button4.setBackground(bg);
        if(MODE==0){
            button4.setBackground(selected);
        }

        // button4.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        a.add(button4);

        button4.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {

                MODE = HashTable.MULTY;
                HashTable<Human> aux = new HashTable<>(MODE){
                    public int comparableToTable(Human ob1, Human ob2) {
                        if(ob1.getId().equals(ob2.getId())){
                            return 0;
                        }
                        return 1;
                    }
                };

                for(int x = 0; x < 256; x++) {

                    for(int y = 0; y < hashtb.getHashIndex(x).getSize(); y++){
                        
                        Human auxhuman =  hashtb.getHashIndex(x).indexOf(y);
    
                        aux.add(auxhuman.getId(),auxhuman);
                    }
                }
                
                JOptionPane.showMessageDialog(a, "Operation Change - Starting Reload", "Load", JOptionPane.INFORMATION_MESSAGE);
                hashtb = aux;

                ibtn = new LinkedList<IndexButton>();

                for(int i = 0; i < 256; i++){
                    IndexButton buttonx = new IndexButton(i,hashtb.table[i], kbp);
                    ibtn.add(buttonx);
                }

                a.dispose();
                reloadData();

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                button4.setBackground(bghover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button4.setBackground(bg);
                if(MODE==0){
                    button4.setBackground(selected);
                }
            }
        });

        JLabel button5 = new JLabel();
        button5.setBounds(7, 45, 60, 30);
        button5.setOpaque(true);
        button5.setHorizontalAlignment(SwingConstants.CENTER);
        button5.setText("Div");
        button5.setFont(font);
        button5.setForeground(colortext);

        button5.setBackground(bg);
        if(MODE==1){
            button5.setBackground(selected);
        }

        // button5.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        a.add(button5);

        button5.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                
                MODE = HashTable.DIV;
                HashTable<Human> aux = new HashTable<>(MODE){
                    public int comparableToTable(Human ob1, Human ob2) {
                        if(ob1.getId().equals(ob2.getId())){
                            return 0;
                        }
                        return 1;
                    }
                };

                for(int x = 0; x < 256; x++) {

                    for(int y = 0; y < hashtb.getHashIndex(x).getSize(); y++){
                        
                        Human auxhuman =  hashtb.getHashIndex(x).indexOf(y);
    
                        aux.add(auxhuman.getId(),auxhuman);
                    }
                }
                
                JOptionPane.showMessageDialog(a, "Operation Change - Starting Reload", "Load", JOptionPane.INFORMATION_MESSAGE);
                hashtb = aux;

                ibtn = new LinkedList<IndexButton>();

                for(int i = 0; i < 256; i++){
                    IndexButton buttonx = new IndexButton(i,hashtb.table[i], kbp);
                    ibtn.add(buttonx);
                }

                a.dispose();
                reloadData();

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                button5.setBackground(bghover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button5.setBackground(bg);
                if(MODE==1){
                    button5.setBackground(selected);
                }
            }
        });

        Font font2 = new Font("Dogica Pixel Bold", Font.PLAIN, 14);

        JLabel text = new JLabel();
        text.setBounds(30, 45+40, 90, 30);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setText("Collisions");
        text.setFont(font);
        text.setForeground(new Color(147,161,161));
        a.add(text);
        // counter = new JLabel();
        counter.setBounds(0,0, 60, 34);
        counter.setText(Integer.toString(hashtb.getColission()));
        counter.setFont(font2);
        counter.setHorizontalAlignment(SwingConstants.CENTER);
        counter.setOpaque(false);
        counter.setForeground(bg);
        colissions.setBounds(45, 45+60+5, 60, 34);
        colissions.setOpaque(false);
        colissions.setIcon(new ImageIcon("resources/others/colission.png"));
        colissions.add(counter);
        // colissions.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        a.add(colissions);

    
        textField2 = new JTextField(20);
        textField2.setText(" - Id' Human - ");
        textField2.setHorizontalAlignment(SwingConstants.CENTER);
        textField2.setBounds(10, 550, 400, 30);
        textField2.setFont(font);
        // textField.setOpaque(false);
        textField2.setBackground(bg);
        textField2.setCaretColor(new Color(88, 110, 117));
        textField2.setForeground(colortext);
        textField2.setBorder(BorderFactory.createLineBorder(new Color(0,43-10,54-10)));
        a.add(textField2);

        
        JLabel filtered = new JLabel();
        filtered.setBounds(10+400+10, 550, 60, 30);
        filtered.setOpaque(true);
        filtered.setHorizontalAlignment(SwingConstants.CENTER);
        filtered.setText("Seek");
        filtered.setFont(font);
        filtered.setForeground(colortext);

        filtered.setBackground(bg);
        // filtered.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        a.add(filtered);

        filtered.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                
                Node<Human> node = hashtb.getHashIndex(textField2.getText().trim()).getNodeElement(new Human(textField2.getText().trim()));

                if(node == null){
                    JOptionPane.showMessageDialog(a, "The Human does not exist", "Seek", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Human human = node.getValue();

                LinkedList<Human> lh = new LinkedList<>();
                lh.add(human);

                KeyPanel k1 = new KeyPanel(lh);

                int indice = 0;
                
                indice = hashtb.hashFunctionOne(hashtb.asciiReduction(human.getId()));
                
                if(MODE == 1){
                    indice = hashtb.hashFunctionTwo(hashtb.asciiReduction(human.getId()));
                }

                for(int u = 0; u < 256; u++){

                    if(main.App.ibtn.indexOf(u).getIndex() != indice){
                        main.App.ibtn.indexOf(u).setVisible(false);
                    }

                }

                kbp.changeKeyPanel(k1); 
                
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                filtered.setBackground(bghover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                filtered.setBackground(bg);
            }
        });

        JLabel filtered2 = new JLabel();
        filtered2.setBounds(10+400+10+60+10, 550, 60, 30);
        filtered2.setOpaque(true);
        filtered2.setHorizontalAlignment(SwingConstants.CENTER);
        filtered2.setText("Reset");
        filtered2.setFont(font);
        filtered2.setForeground(colortext);

        filtered2.setBackground(bg);
        // filtered2.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        a.add(filtered2);

        filtered2.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                
                for(int u = 0; u < 256; u++){

                    main.App.ibtn.indexOf(u).setVisible(true);
                  

                }

                kbp.changeKeyPanel(new KeyPanel(new LinkedList<Human>())); 
                
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                filtered2.setBackground(bghover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                filtered2.setBackground(bg);
            }
        });

        //==============================================
        //=========== Backgrounds ============================
        JPanel backgroundkey = new JPanel();
        backgroundkey.setBounds(144, 69, 437,466);
        backgroundkey.setBackground(new Color(0,43,54));

        Icon keyp = new ImageIcon("resources/key/keyback.png");
        JLabel keyplabel = new JLabel(keyp);
        backgroundkey.add(keyplabel);
        a.add(backgroundkey);

        JPanel backgroundindex = new JPanel();
        backgroundindex.setBounds(14-5, 66, 119+10,471);
        backgroundindex.setBackground(new Color(0,43,54));
        a.add(backgroundindex);

        JPanel backgroundHorizontalBar = new JPanel();
        backgroundHorizontalBar.setBounds(143, 39,438,24);
        backgroundHorizontalBar.setBackground(new Color(0,43,54));
        a.add(backgroundHorizontalBar);

        JPanel background = new JPanel();
        background.setBounds(0, 0, 600, 600);
        background.setBackground(new Color(7,54,66));
        a.add(background);

        // =====================================================
        // kbp.changeKeyPanel(new KeyPanel(hashtb.tableOne[70]));

        // App.saveToFile(hashtb, "resources/data/MOCK_DATA2.csv");

        a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        a.setVisible(true);

    }

    public static boolean readDataFile(String path, HashTable<Human> tables){

        String line = "";

        try {

            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {

                String[] data = line.split(";");

                // Obtener los valores de los campos
                String id = data[0];
                String name = data[1];
                String lastname = data[2];
                String gender = data[3];

                // Imprimir los valores de los campos
                // System.out.println(" ID: " + id + " | Nombre: " + name + " | Apellido: " + lastname + " | Género: " + gender);
                tables.add(id,new Human(id,name,lastname,gender));
            }

            System.out.println(" Archivo leido con exito.");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean saveToFile(HashTable<Human> hash, String filename) {

        if(filename.isEmpty()){
            System.out.println("Ruta no valida");
        }


        try {
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            
            for(int x = 0; x < 256; x++) {

                for(int y = 0; y < hash.getHashIndex(x).getSize(); y++){
                    
                    Human auxhuman =  hash.getHashIndex(x).indexOf(y);

                    writer.write(   auxhuman.getId() + ";" + 
                                    auxhuman.getName() + ";" + 
                                    auxhuman.getLastname() + ";" +
                                    auxhuman.getGender());
                    writer.newLine();
                }
            }

            writer.close();
            System.out.println(" Guardado exitosamente en el archivo " + filename);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo " + filename + ": " + e.getMessage());
            return false;
        }
    }
    
}
