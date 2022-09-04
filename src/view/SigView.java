package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import controller.FrameController;


public class SigView extends JFrame implements Runnable {

    private JPanel p1;
    private JPanel p2;
    private JPanel p3;
    private JPanel p4;
    private JPanel pb1;
    private JPanel pb3;
    private JPanel pb11;
    private JPanel pb33;
    private JTable HeaderTable;
    private JTable LinesTable;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JTextField text1;
    private JTextField text2;
    private JButton b1;//create new invoice
    private JButton b2;//delete invoice
    private JButton b3;//save
    private JButton b4;//cancel
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem loadFIle;
    private JMenuItem saveFile;
    public DefaultTableModel dtm1;
    public DefaultTableModel dtm2;

    private String[] cols1={"No.","Date","Customer","Total"};
    private String[] cols2={"No.","Item Name","Item Price","Count", "Item Total"};
    private String[][] rows={{"No.","Date","Customer","Total"}};
    private FrameController controller = new FrameController(this);


    //    private ArrayList<InvoiceHeader> datat = new ArrayList<>();
//    private ArrayList<InvoiceHeader> data;
    public SigView(){
        super("SIG");

        setLayout(new GridLayout(1,2));

        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p4 = new JPanel();
        pb1 = new JPanel();
        pb3 = new JPanel();
        pb11 = new JPanel();
        pb33 = new JPanel();

        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        label8 = new JLabel();

        text1 = new JTextField(10);// for Invoice Date
        text2 = new JTextField(10);// for Customer Name

        b1 = new JButton("Create New Invoice");
        b2 = new JButton("Delete Invoice");
        b3 = new JButton("Create");
        b4 = new JButton("Delete");

         menuBar = new JMenuBar();
         fileMenu = new JMenu("File");
         loadFIle = new JMenuItem("Load File",'l');
         saveFile = new JMenuItem("Save File",'s');

         loadFIle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));
         saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
         loadFIle.addActionListener(controller);
         saveFile.addActionListener(controller);
         loadFIle.setActionCommand("L"); // load file menu item
         saveFile.setActionCommand("S"); // save file menu item

        b1.addActionListener(controller);
        b2.addActionListener(controller);
        b1.setActionCommand("N"); // new invoice button
        b2.setActionCommand("D"); // delete button

        b3.addActionListener(controller);
        b4.addActionListener(controller);
        b3.setActionCommand("CL"); // save button
        b4.setActionCommand("DL"); // cancel button




        fileMenu.add(loadFIle);
        fileMenu.add(saveFile);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        p1.setLayout(new BoxLayout(p1,BoxLayout.Y_AXIS));// panel for Invoice Table and "Invoice Table" title
        p2.setLayout(new GridLayout(4,2,-200,10));// panel for the four required labels
        p3.setLayout(new BoxLayout(p3,BoxLayout.Y_AXIS));// panel for Invoice Items and "Invoice items" title
        p4.setLayout(new BoxLayout(p4,BoxLayout.Y_AXIS));// panel to contain panel2 and panel3, panel1 and panel4 are then added to the JFrame
        pb1.setLayout(new FlowLayout());//adds b1 & b2
        pb3.setLayout(new FlowLayout());// adds b3 & b4
        pb11.setLayout(new BoxLayout(pb11,BoxLayout.Y_AXIS));
        pb33.setLayout(new BoxLayout(pb33,BoxLayout.Y_AXIS));

        HeaderTable = new JTable(); // Invoice Table
        LinesTable = new JTable();  //Invoice Items

        dtm1 = new DefaultTableModel(0,0);
        dtm1.setColumnIdentifiers(cols1);
        HeaderTable.setModel(dtm1);
//        dtm1.addRow(new Object[]{});
//        dtm1.addRow(new Object[]{});
//        dtm1.addRow(new Object[]{});

        dtm2 = new DefaultTableModel(0,0);
        dtm2.setColumnIdentifiers(cols2);
        LinesTable.setModel(dtm2);
//        dtm2.addRow(new Object[]{});
//        dtm2.addRow(new Object[]{});
//        dtm2.addRow(new Object[]{});


        label1.setText("Invoice Table");
        label2.setText("Invoice Number");
        label3.setText("Invoice Date");
        label4.setText("Customer Name");
        label5.setText("Invoice Total");
        label6.setText("Invoice Items");
        label7.setText("");
        label8.setText("");


        //======= Below 2 lines to set table headers text to align left as they are centered by default =======//
        ((DefaultTableCellRenderer)HeaderTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
        ((DefaultTableCellRenderer)LinesTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        pb1.add(b1);
        pb1.add(b2);

        pb3.add(b3);
        pb3.add(b4);

        p1.add(label1);
        p1.add(new JScrollPane(HeaderTable));
        pb11.add(p1);
        pb11.add(pb1);

        p2.add(label2);
        p2.add(label7);
        p2.add(label3);
        p2.add(text1);
        p2.add(label4);
        p2.add(text2);
        p2.add(label5);
        p2.add(label8);

        p3.add(label6);
        p3.add(new JScrollPane(LinesTable));
        pb33.add(p3);
        pb33.add(pb3);

        p4.add(p2);
        p4.add(pb33);


        add(pb11);
        add(p4);
        setSize(1000,600);
        setLocation(120,40);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public JTable getHeaderTable(){
        return HeaderTable;
    }
    public JTable getLinesTable(){
        return LinesTable;
    }
    @Override
    public void run() {
        SigView GUI = new SigView();
        GUI.setVisible(true);
        GUI.update(GUI.getGraphics());
    }
    public ActionListener getListener(){
        return controller;
    }
}
