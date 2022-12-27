package Controller;

import Model.Invoices;
import Model.InvoicesItemsTable;
import View.AppGUI;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class SIGTableSelection implements ListSelectionListener{
    private AppGUI app;

    public SIGTableSelection(AppGUI app) {
        this.app = app;
    }
    
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selection = app.getInvoiceTable().getSelectedRow();
        
        if(selection != -1)
        {
            Invoices inv = app.getInvoice().get(selection);
        
            app.getInvoiceNumberText().setText("" + inv.getNumber());
            app.getInvoiceDateText().setText(inv.getDate());
            app.getCustomerNameText().setText(inv.getCustomerName());
            app.getInvoiceTotalText().setText("" + inv.getInvoiceTotal());

            InvoicesItemsTable invItem = new InvoicesItemsTable(inv.getItems());
            app.getItemsTable().setModel(invItem);
            invItem.fireTableDataChanged();   
        }
    }
    
}
