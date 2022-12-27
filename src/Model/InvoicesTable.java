package Model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoicesTable extends AbstractTableModel{
    private ArrayList<Invoices> invoice;

    public InvoicesTable(ArrayList<Invoices> invoice) {
        this.invoice = invoice;
    }

    @Override
    public int getRowCount() {
        return invoice.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }
    
    public String getColumnName(int col) {
        switch(col)
        {
            case 0: return "Invoice No.";
            case 1: return "Date";
            case 2: return "Customer Name";
            case 3 : return "Total";
            default: return "";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Invoices inv = invoice.get(rowIndex);
        
        switch(columnIndex)
        {
            case 0: return inv.getNumber();
            case 1: return inv.getDate();
            case 2: return inv.getCustomerName();
            case 3: return inv.getInvoiceTotal();
            default: return "";
        }
    }
}