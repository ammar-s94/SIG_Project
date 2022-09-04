package model;
import java.util.ArrayList; // import the ArrayList class

public class InvoiceHeader {
    // member variables
    private String invoiceDate;
    private String customerName;
    private int invoiceNum;
    public ArrayList<InvoiceLine> lines  = new ArrayList<>();

    // no-arg constructor + arg constructor
    public InvoiceHeader(){}
    public InvoiceHeader(String invoiceDate,String customerName, int invoiceNum)
    {
        this.invoiceDate=invoiceDate;
        this.customerName=customerName;
        this.invoiceNum=invoiceNum;
    }

    // member variables setters
    public void setItemName(String invoiceDate){
        this.invoiceDate=invoiceDate;
    }
    public void setItemPrice(String customerName){
        this.customerName=customerName;
    }
    public void setItemCount(int invoiceNum){
        this.invoiceNum=invoiceNum;
    }

    // member variables getters
    public String getInvoiceDate(){
       return this.invoiceDate;
    }
    public String getCustomerName(){
        return this.customerName;
    }
    public int getInvoiceNum(){
       return this.invoiceNum;
    }
}
