package controller;

import model.InvoiceHeader;
import model.InvoiceLine;
import view.NewInvoice;
import view.NewInvoiceLine;
import view.SigView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;


public class FrameController extends JDialog implements ActionListener {
    private static String pathHeader="InvoiceHeader.csv";
    private static String pathLine="InvoiceLine.csv";

    private SigView view;
    private ArrayList<InvoiceHeader> data;

    private NewInvoice NV;
    private NewInvoiceLine NL;

    public FrameController(SigView view){
        this.view = view;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "L":
                loadFIle();
                break;
            case "S":
                saveFile();
                break;
            case "N":
                newInvoice();
                break;
            case "D":
                deleteInvoice();
                break;
            case "CL":
                newLine();
                break;
            case "DL":
                deleteLine();
                break;
            case "newInvoiceOK": {
                if ((NV.getCustName().getText().trim().isEmpty()) || (NV.getInvDate().getText().trim().isEmpty())) {
                    JOptionPane.showMessageDialog(NV, "Please Enter Customer Name and Date in the following format:(DD-MM-YYYY)", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    newInvoiceDialogOKBtn();
                }
                break;
            }
            case "newInvoiceCancel": {
                newInvoiceDialogCancelB();
                break;
            }
            case "newLineOK": {
                if ((NL.getItemCount().getText().trim().isEmpty()) || (NL.getItemName().getText().trim().isEmpty()) || (NL.getItemPrice().getText().trim().isEmpty())) {
                    JOptionPane.showMessageDialog(NV, "Please Enter Customer Name and Date in the following format:(DD-MM-YYYY)", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    newLineDialogOKBtn();
                }
                break;
            }
            case "newLineCancel": {
                newLineDialogCancelB();
                break;
            }
            default:
                break;
        }
    }

    private void loadFIle() {
        data = new ArrayList<>();
        JOptionPane.showMessageDialog(view, "Choose Headers file to load","Load Headers File", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(view);
        String absPath="";// = fc.get(path.getAbsolutePath());
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getPath();
            absPath = path.replace(pathHeader,"");
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(new File(path)); //creating file object
                int size = fis.available();
                byte[] b = new byte[size];
                fis.read(b); //read file as bytes

                String bs = new String(b); //convert bytes read to string for parsing

                String[] sentences = bs.split(",|\\r\\n", 0); // convert string data to array of stings
                if (path.contains(pathHeader)) {

                    for (int i = 0; i < sentences.length; i++) {
                        if (i % 3 == 2) {
                            try {
                                data.add(new InvoiceHeader(sentences[i - 1], sentences[i], Integer.parseInt(sentences[i - 2])));
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(view, "Wrong Formatted data in File","Cannot open file", JOptionPane.ERROR_MESSAGE);
                                data.clear();
                            }
                        }
                    }
                }
            } catch (
                    IOException e) {
                e.printStackTrace();
            } finally {
                try { // file close throws exception
                    if (fis != null) { // check if not null to avoid null pointer exception
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        JOptionPane.showMessageDialog(view, "Choose Lines file to load","Load Lines File", JOptionPane.INFORMATION_MESSAGE);
        fc = new JFileChooser();
        fc.setCurrentDirectory(new File(absPath));
        result = fc.showOpenDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getPath();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(new File(path)); //creating file object
                int size = fis.available();
                byte[] b = new byte[size];
                fis.read(b); //read file as bytes

                String bs = new String(b); //convert bytes read to string for parsing

                String[] sentences = bs.split(",|\\r\\n", 0); // convert string data to array of stings
                if (path.contains(pathLine)) {
                    if (data.size() != 0)
                        for (int i = 0; i < sentences.length; i++) {
                            if (i % 4 == 3) {
                                for (int j = 0; j < data.size(); j++) {
                                    try {
                                        if (Integer.parseInt(sentences[i - 3]) == data.get(j).getInvoiceNum()) {

                                            data.get(j).lines.add(new InvoiceLine(sentences[i - 2], Integer.parseInt(sentences[i - 1]),
                                                    Integer.parseInt(sentences[i]), Integer.parseInt(sentences[i - 3])));
                                        }
                                    } catch (Exception e) {
                                        data.clear();
                                        JOptionPane.showMessageDialog(view, "Wrong Formatted data in File","Cannot open file", JOptionPane.ERROR_MESSAGE);

                                    }
                                }
                            }
                        }
                    else {
                        JOptionPane.showMessageDialog(view, "Choose Headers file first","Invalid Selection", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Invalid File","Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (
                    IOException e) {
                e.printStackTrace();
            } finally {
                try { // file close throws exception
                    if (fis != null) { // check if not null to avoid null pointer exception
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        renderTable();

    }

    private void refreshData(){
        readInvHeadersFromTable();
        readInvLinesFromTable();
        renderTable();
    }
    private void renderTable(){

        for(int i=0 ;i< view.dtm1.getRowCount();i++)
        {
            view.dtm1.removeRow(i);
        }
        for(int i=0 ;i< view.dtm2.getRowCount();i++)
        {
            view.dtm2.removeRow(i);
        }
        boolean checkLines=false;
        for (int i=0;i<data.size();i++){
            if (data.get(i).lines.size() !=0)
            {
                checkLines=true;
            }
        }
    if (checkLines=true) {
        view.dtm2.setRowCount(0);
        for (InvoiceHeader datum : data) {
            for (int j = 0; j < datum.lines.size(); j++) {
                view.dtm2.addRow(new String[]{String.valueOf(datum.getInvoiceNum()), datum.lines.get(j).getItemName(), String.valueOf(datum.lines.get(j).getItemPrice()),
                        String.valueOf(datum.lines.get(j).getCount()),String.valueOf(datum.lines.get(j).getItemPrice()*datum.lines.get(j).getCount())});
            }
        }
    }
    if (data != null) {
        int sum=0;
        if (data.size() != 0) {
            view.dtm1.setRowCount(0);
            for (InvoiceHeader datum : data) {

                for(InvoiceLine x:datum.lines){
                    sum+=x.getCount()*x.getItemPrice();
                }
                view.dtm1.addRow(new String[]{String.valueOf(datum.getInvoiceNum()), datum.getInvoiceDate(), datum.getCustomerName(),String.valueOf(sum)}); sum=0;
            }
        }

    }

//System.out.println("data==========================");
//    for(int i=0;i<data.size();i++){
//            System.out.println(data.get(i).getInvoiceNum());
//            System.out.println("{");
//            System.out.print(data.get(i).getInvoiceDate());
//            System.out.print(", ");
//            System.out.println(data.get(i).getCustomerName());
//            for(int j=0;j<data.get(i).lines.size();j++)
//                {
//                    System.out.print(data.get(i).lines.get(j).getItemName());
//                    System.out.print(", ");
//                    System.out.print(data.get(i).lines.get(j).getItemPrice());
//                    System.out.print(", ");
//                    System.out.println(data.get(i).lines.get(j).getCount());
//                }
//            System.out.println("}");
//            System.out.println();
//        }
}
    private void readInvHeadersFromTable(){
        if(data!=null){
            data.clear();
        }
        else{
            data = new ArrayList<>();
        }

        int invR = view.getHeaderTable().getRowCount();
        String[] sentences = new String[3*invR];
        int j=0;
        for(int i=0;i<invR*3;i+=3){
            sentences[i]=(String) view.getHeaderTable().getValueAt(j,0);
            sentences[i+1]=(String) view.getHeaderTable().getValueAt(j,1);
            sentences[i+2]=(String) view.getHeaderTable().getValueAt(j,2);
            j++;
        }
        for (int i = 0; i < sentences.length; i++) {
            if (i % 3 == 2) {
                try {
                    data.add(new InvoiceHeader(sentences[i - 1], sentences[i], Integer.parseInt(sentences[i - 2])));
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(view, "Wrong formatted data entered, clearing recent changes","Error", JOptionPane.ERROR_MESSAGE);
                    data.clear();
                }
            }
        }
    }

    private void readInvLinesFromTable(){

        int invR = view.getLinesTable().getRowCount();
        String[] sentences = new String[4*invR];
        int j=0;
        for(int i=0;i<invR*4;i+=4){
            sentences[i]=(String) view.getLinesTable().getValueAt(j,0);
            sentences[i+1]=(String) view.getLinesTable().getValueAt(j,1);
            sentences[i+2]=(String) view.getLinesTable().getValueAt(j,2);
            sentences[i+3]=(String) view.getLinesTable().getValueAt(j,3);
            j++;
        }
        if (data.size() != 0) {
            try {
                for (int i = 0; i < sentences.length; i++) {
                    if (i % 4 == 3) {

                        for (j = 0; j < data.size(); j++) {

                            if (Integer.parseInt(sentences[i - 3]) == data.get(j).getInvoiceNum()) {

                                data.get(j).lines.add(new InvoiceLine(sentences[i - 2], Integer.parseInt(sentences[i - 1]),
                                        Integer.parseInt(sentences[i]), Integer.parseInt(sentences[i - 3])));
                            }
                        }
                        data = data;
                    }
                }
            } catch (Exception e) {
//                        data.clear();
                JOptionPane.showMessageDialog(view, "Wrong formatted data entered, clearing recent changes", "Error", JOptionPane.ERROR_MESSAGE);

            }
        }
        else {
        }
    }
    private void saveFile(){
        //Save Headers
        if(data!=null){
        JOptionPane.showMessageDialog(view, "Choose file to save invoice headers","Headers File", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser fc = new JFileChooser();
        FileWriter fw = null;
        try {
            int response = fc.showSaveDialog(view);
            if (response == JFileChooser.APPROVE_OPTION){
                File file = fc.getSelectedFile();
                fw = new FileWriter(file);
                String headers = "";


                for (InvoiceHeader header: data){
                    headers += String.valueOf(header.getInvoiceNum())+','+header.getInvoiceDate()+','+header.getCustomerName()+"\r\n";
                }
                System.out.println(headers);
                fw.write(headers);
                fw.close();

                //Save Lines
                JOptionPane.showMessageDialog(view, "Choose file to save invoice lines","Lines File", JOptionPane.INFORMATION_MESSAGE);
                response = fc.showSaveDialog(view);
                file= fc.getSelectedFile();
                fw = new FileWriter(file);
                String lines = "";
                for(int i=0;i<data.size();i++){
                    for(int j=0;j<data.get(i).lines.size();j++)
                    {
                       lines+= String.valueOf(data.get(i).lines.get(j).getInvoiceNum())+","+data.get(i).lines.get(j).getItemName()+","+
                               String.valueOf(data.get(i).lines.get(j).getItemPrice())+ String.valueOf(data.get(i).lines.get(j).getCount())+"\r\n";
                    }
                }
                fw.write(lines);
                fw.close();


                JOptionPane.showMessageDialog(view, "Files saved successfully","Info message", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch (IOException exception) {
            JOptionPane.showMessageDialog(view, "File must be CSV type ", "Invalid File", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            try {
                assert fw != null;
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
        else{
            JOptionPane.showMessageDialog(view, "No invoices to save", "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void newInvoice(){
        NV = new NewInvoice(view);
        NV.setVisible(true);
        NV.setTitle("Create New Invoice");
    }
    private void newLine(){
        NL = new NewInvoiceLine(view);
        NL.setVisible(true);
        NL.setTitle("Create New Invoice");
    }
    private void newInvoiceDialogOKBtn() {
        NV.setVisible(false);
        String customerName = NV.getCustName().getText();
        String date = NV.getInvDate().getText();
        int invR = view.getHeaderTable().getRowCount()+1;
        if(isValidDate("dd-MM-yyyy",date)) {
            view.dtm1.addRow(new String[]{String.valueOf(invR), date, customerName});
        }
        else {
            JOptionPane.showMessageDialog(view, "Date must be in the following format dd-mm-yyyy format ", "Invalid date format", JOptionPane.ERROR_MESSAGE);
        }
        NV.dispose();
        NV = null;
        refreshData();    }

    private void newInvoiceDialogCancelB() {
        NV.setVisible(false);
        NV.dispose();
        NV = null;
    }
    private void newLineDialogOKBtn() {
        NL.setVisible(false);
        String ItemName = NL.getItemName().getText();
        String ItemPrice = NL.getItemPrice().getText();
        String ItemCount = NL.getItemCount().getText();
        try {
            if (view.getHeaderTable().getRowCount() != 0)
            {
                String invR = (String) view.getHeaderTable().getValueAt(view.getHeaderTable().getRowCount() - 1, 0);
                view.dtm2.addRow(new String[]{String.valueOf(invR), ItemName, ItemPrice, ItemCount});
                NL.dispose();
                NL = null;
            }
            else{
                JOptionPane.showMessageDialog(view, "Cannot create line before header","Warning", JOptionPane.WARNING_MESSAGE);
            }
        }catch (Exception e){
            e.printStackTrace();
//            JOptionPane.showMessageDialog(view, "Cannot create line before header","Warning", JOptionPane.WARNING_MESSAGE);
        }
        refreshData();
    }

    private void newLineDialogCancelB() {
        NL.setVisible(false);
        NL.dispose();
        NL = null;
    }

    private void deleteInvoice(){
        int i=0;
        try {
            if(view.getHeaderTable().getSelectedRow()!=-1) {
                String headerRowInvNum = (String) view.getHeaderTable().getValueAt(view.getHeaderTable().getSelectedRow(), 0);
                try {
                    String x = (String) view.getLinesTable().getValueAt(0, 0);
                    boolean matched = false;

                    while (i < view.getLinesTable().getRowCount()) {
                        matched = false;
                        x = (String) view.getLinesTable().getValueAt(i, 0);
                        if (headerRowInvNum.equals(x)) {
                            view.dtm2.removeRow(i);
                            matched = true;
                        }
                        i++;
                        if (matched) {
                            i = 0;
                        }
                    }
                    view.dtm1.removeRow(view.getHeaderTable().getSelectedRow());
                    //JOptionPane.showMessageDialog(null, "Invoice Deleted", "Info message", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    view.dtm1.removeRow(view.getHeaderTable().getSelectedRow());
                }
            }
            }
        catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Invoice Table row not selected", "Info message",JOptionPane.INFORMATION_MESSAGE);
            if (view.getLinesTable().getRowCount()==0){
//                data.clear();
                view.dtm1.removeRow(i);
            }
            }
        refreshData();
        }
    private void deleteLine(){
        try {
            if(view.getLinesTable().getSelectedRow()!=-1) {
                view.dtm2.removeRow(view.getLinesTable().getSelectedRow());
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Invoice Line row not selected", "Info message",JOptionPane.INFORMATION_MESSAGE);
        }
        refreshData();
    }


    public static boolean isValidDate(String format, String value ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            LocalDate ld = LocalDate.parse(value, formatter);
            String result = ld.format(formatter);
            return result.equals(value);
        } catch (DateTimeParseException exp) {
            exp.printStackTrace();
        }
        return false;
    }

}
