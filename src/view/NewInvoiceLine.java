package view;

import javax.swing.*;
import java.awt.*;

public class NewInvoiceLine extends JDialog {
    private JTextField ItemPriceIN;
    private JTextField ItemNameIN;
    private JTextField ItemCountIN;

    public NewInvoiceLine(SigView view) {
        setLayout(new GridLayout(1,2));

        JLabel ItemPrice = new JLabel("Item price");
        JLabel ItemName = new JLabel("Item name");
        JLabel ItemCount = new JLabel("Item count");


        ItemPriceIN = new JTextField(10);
        ItemNameIN = new JTextField(10);
        ItemCountIN = new JTextField(10);


        JButton okB = new JButton();
        JButton cancelB = new JButton();

        okB.setText("Ok");
        cancelB.setText("Cancel");


        okB.setActionCommand("newLineOK");
        cancelB.setActionCommand("newLineCancel");

        okB.addActionListener(view.getListener());
        cancelB.addActionListener(view.getListener());
        getContentPane().setLayout(new FlowLayout());

        add(ItemName);
        add(ItemNameIN);
        add(ItemPrice);
        add(ItemPriceIN);
        add(ItemCount);
        add(ItemCountIN);
        add(okB);
        add(cancelB);

        setSize(350,200);
        setLocation(400,400);

    }
    public JTextField getItemPrice() {return ItemPriceIN;}
    public JTextField getItemName() {
        return ItemNameIN;
    }
    public JTextField getItemCount() {
        return ItemCountIN;
    }
}
