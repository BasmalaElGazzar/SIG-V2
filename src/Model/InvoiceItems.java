package Model;

public class InvoiceItems {
    
    private Invoices invoice;
    private String ItemName;
    private double price;
    private int count;
    
    double itemTotal = getTotal();

    
    public InvoiceItems(String ItemName, double price, int count, Invoices invoice) {
        this.ItemName = ItemName;
        this.price = price;
        this.count = count;
        this.invoice = invoice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "InvoiceItems{" + "invoiceNum=" + invoice.getNumber() + "ItemName=" + ItemName + ", price=" + price + ", count=" + count + ", itemTotal=" + itemTotal + '}';
    }

    public Invoices getInvoice() {
        return invoice;
    }
    
    public double getTotal()
    {
        return count*price;
    }
    
    public String getAsCSV()
    {
        return invoice.getNumber() + "," + ItemName + "," + price + "," + count + "," + itemTotal;
    }
}
