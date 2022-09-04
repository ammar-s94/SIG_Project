package view;

import javax.swing.*;
import java.awt.*;

public class NewInvoice extends JDialog {
    private JTextField custNameIn;
    private JTextField dateIn;
    public NewInvoice(SigView view) {
        setLayout(new GridLayout(1,2));

        JLabel custName = new JLabel("Customer Name");
        JLabel date = new JLabel("Date");


        custNameIn = new JTextField(10);// for Invoice Date
        dateIn = new JTextField(10);// for Customer Name


        JButton okB = new JButton();
        JButton cancelB = new JButton();

        okB.setText("Ok");
        cancelB.setText("Cancel");


        okB.setActionCommand("newInvoiceOK");
        cancelB.setActionCommand("newInvoiceCancel");

        okB.addActionListener(view.getListener());
        cancelB.addActionListener(view.getListener());
        getContentPane().setLayout(new FlowLayout());

        add(custName);
        add(custNameIn);
        add(date);
        add(dateIn);
        add(okB);
        add(cancelB);

        setSize(350,200);
        setLocation(400,400);

    }
    public JTextField getCustName() {

        return custNameIn;
    }

    public JTextField getInvDate() {
        return dateIn;
    }


}
