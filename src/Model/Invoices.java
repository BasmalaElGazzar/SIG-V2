package Model;

import java.util.ArrayList;

public class Invoices {
    
    private int number;
    private String date;
    private String customerName;
    private ArrayList<InvoiceItems> items;
    
    double invoiceTotal = getInvoiceTotal();

    public Invoices(int number, String date, String customerName) {
        this.number = number;
        this.date = date;
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<InvoiceItems> getItems() {
        if(items == null) 
        {
            items = new ArrayList();
        }
        return items;
    }

    @Override
    public String toString() {
        return "Invoices{" + "number=" + number + ", date=" + date + ", customerName=" + customerName + ", items=" + items + '}';
    }

    public double getInvoiceTotal()
    {
        double invoiceTotal = 0.0;
        
        for(InvoiceItems item : getItems())
        {
            invoiceTotal +=item.getTotal();
        }
        
        return invoiceTotal;
    }
    
    
    public String getAsCSV()
    {
        return number + "," + date + "," + customerName;
    }
}
