package model;

public class InvoiceLine {
    // member variables
    private String itemName;
    private int itemPrice;
    private int count;
    private int invoiceNum;

    // no-arg constructor + arg constructor
    public InvoiceLine(){}
    public InvoiceLine(String itemName, int itemPrice, int count,int invoiceNum)
    {
        this.itemName=itemName;
        this.count=count;
        this.itemPrice=itemPrice;
        this.invoiceNum=invoiceNum;
    }

    // member variables setters
    public void setItemName(String itemName){
        this.itemName=itemName;
    }
    public void setItemPrice(int itemPrice){
        this.itemPrice=itemPrice;
    }
    public void setItemCount(int count){
        this.count=count;
    }
    public void setInvoiceNum(int invoiceNum){
        this.invoiceNum=invoiceNum;
    }

    // member variables getters
    public String getItemName(){
        return this.itemName;
    }
    public int getItemPrice(){
        return this.itemPrice;
    }
    public int getCount(){
        return this.count;
    }
    public int getInvoiceNum(){
        return this.invoiceNum;
    }
}
