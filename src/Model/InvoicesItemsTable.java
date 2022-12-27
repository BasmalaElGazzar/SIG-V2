package Model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoicesItemsTable  extends AbstractTableModel{
    private ArrayList<InvoiceItems> item;
    
    public InvoicesItemsTable(ArrayList<InvoiceItems> item) {
        this.item = item;
    }

    @Override
    public int getRowCount() {
        return item.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }
    
    public String getColumnName(int col) {
        switch(col)
        {
            case 0: return "Invoice No.";
            case 1: return "Item Name";
            case 2: return "Price";
            case 3: return "Count";
            case 4: return "Total";
            default: return "";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceItems it = item.get(rowIndex);
     
        switch(columnIndex)
        {
            case 0: return it.getInvoice().getNumber();
            case 1: return it.getItemName();
            case 2: return it.getPrice();
            case 3: return it.getCount();
            case 4: return it.getTotal();
            default: return "";
        }
    }
}