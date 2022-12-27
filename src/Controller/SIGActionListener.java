package Controller;


import View.AppGUI;
import Model.InvoiceItems;
import Model.Invoices;
import Model.InvoicesItemsTable;
import Model.InvoicesTable;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import  javax.swing.JTable;
import java.util.regex.Pattern;


public class SIGActionListener implements ActionListener {

    private AppGUI app;
    JFileChooser fileChoose = new JFileChooser();

    public SIGActionListener(AppGUI app) {
        this.app = app;
    }


    @Override
    public void actionPerformed(ActionEvent a) {

        switch (a.getActionCommand()) {
            // AppGUI Actions
            case "Load Invoice":
                loadInvoice();
                break;

            case "Save Invoice":
                saveInvoice();
                break;

            case "Exit":
                Exit();
                break;

            case "Create New Invoice":
                createNewInvoice();
                break;

            case "Delete Invoice":
                deleteInvoice();
                break;

            case "Add New Item":
                addNewItem();
                break;

            case "Delete Item":
                deleteItem();
                break;

            // AddNewItem Actions
            case "SaveItemButton":
               saveItemButton();
                break;

            case "CancelItemButton":
                cancelItemButton();
                break;

            // AddNewInvoiceActions
            case "saveInvocieButton":
                saveInvoiceButton();
                break;

            case "cancelInvoiceButton":
                cancelInvoiceButton();
                break;

            default:
                throw new AssertionError();
        }
    }

    private void loadInvoice(){

        try {
            int choice = fileChoose.showOpenDialog(app);
            ArrayList<Invoices> allInvoices = null;
            if (choice == JFileChooser.APPROVE_OPTION) {
                Path fileInvoicePath = Paths.get(fileChoose.getSelectedFile().getAbsolutePath());
                String invoicePath = fileInvoicePath.toString();
                if (invoicePath.endsWith(".csv")) {
                    List<String> readInvoices = Files.readAllLines(fileInvoicePath);
                    allInvoices = new ArrayList<>();
                    
                    for (String inv : readInvoices)
                    {
                        String[] invData = inv.split(",");
                        int invNum = Integer.parseInt(invData[0]);
                        String invDate = invData[1];
                        String customerName = invData[2];

                        Invoices invoice = new Invoices(invNum, invDate, customerName);
                        allInvoices.add(invoice);
                    }
                }
                else
                {
                    throw new RuntimeException();
                }
                    
            }
            
            choice = fileChoose.showOpenDialog(app);
            if (choice == JFileChooser.APPROVE_OPTION) {
            Path fileItemsPath = Paths.get(fileChoose.getSelectedFile().getAbsolutePath());
            String itemPath = fileItemsPath.toString();
            if (itemPath.endsWith(".csv")) {
                List<String> readItems = Files.readAllLines(fileItemsPath);

                for (String it : readItems)
                {
                    String[] itData = it.split(",");
                    int itNum = Integer.parseInt(itData[0]);
                    String itName = itData[1];
                    double itPrice = Double.parseDouble(itData[2]);
                    int itCount = Integer.parseInt(itData[3]);
                    Invoices invoice = null;
                    
                    for(Invoices invo : allInvoices)
                    {
                        if(invo.getNumber() == itNum)
                        {
                            invoice = invo;
                            break;
                        }
                    }
                    
                    InvoiceItems item = new InvoiceItems(itName, itPrice, itCount, invoice);
                    
                    invoice.getItems().add(item);
                    
                }
            }
            else
            {
                throw new RuntimeException();
            }
            
            app.setInvoice(allInvoices);
            InvoicesTable invTable = new InvoicesTable(allInvoices);
            app.setInvTable(invTable);
            app.getInvoiceTable().setModel(invTable);
            app.getInvTable().fireTableDataChanged();

            }

        } 
        catch (RuntimeException e) {
            JOptionPane.showMessageDialog(app, "Wrong File Format. Please Choose CSV file.");
        } 
        catch (IOException e) {
               JOptionPane.showMessageDialog(app, "File Not Found. Please Choose another file.");
               e.printStackTrace();
           }
            

    }

    private void saveInvoice() {
        ArrayList<Invoices> inv = app.getInvoice();
        String invFile = "";
        String itemFile = "";
        
        for(Invoices invo: inv)
        {
            String invoiceCSV = invo.getAsCSV();
            invFile+= invoiceCSV;
            invFile+="\n";
            
            for(InvoiceItems items: invo.getItems())
            {
                String itemCSV = items.getAsCSV();
                itemFile+= itemCSV;
                itemFile+="\n";
            }
        }
        
        
        
        try
        {
            int choice = fileChoose.showOpenDialog(app);
            if (choice == JFileChooser.APPROVE_OPTION) {
                File fileInv = fileChoose.getSelectedFile();
                FileWriter invWrite = new FileWriter(fileInv);
                invWrite.write(invFile);
                invWrite.flush();
                invWrite.close();
                
                choice = fileChoose.showOpenDialog(app);

                if(choice == JFileChooser.APPROVE_OPTION) {
                    File itemfile = fileChoose.getSelectedFile();
                    FileWriter itemWrite = new FileWriter(itemfile);
                    itemWrite.write(itemFile);
                    itemWrite.flush();
                    itemWrite.close();
                }
            }
        } 
        catch (IOException e)
        {
            
        }
    }

    private void Exit() {
        System.exit(0);
    }

    private void createNewInvoice() {
        app.showAddInvoice();
    }

    private void deleteInvoice() {
        int invoiceInd = app.getInvoiceTable().getSelectedRow();
        
        if(invoiceInd != -1)
        {
            app.getInvoice().remove(invoiceInd);
            app.getInvTable().fireTableDataChanged();
        }
    }

    private void addNewItem() {
        app.showAddItem();
    }

    private void deleteItem() {
        int invoiceInd = app.getInvoiceTable().getSelectedRow();
        int itemInd = app.getItemsTable().getSelectedRow();
        
        if(itemInd != -1 && invoiceInd != -1)
        {
            Invoices inv = app.getInvoice().get(invoiceInd);
            inv.getItems().remove(itemInd);
            InvoicesItemsTable invItem = new InvoicesItemsTable(inv.getItems());
            app.getItemsTable().setModel(invItem);
            invItem.fireTableDataChanged();
            app.getInvTable().fireTableDataChanged();
        }
        
    }

    private void saveItemButton() {

        String name = app.getItemNameText().getText();
        double price = Double.parseDouble(app.getItemPriceText().getText());
        int count = Integer.parseInt(app.getItemCountText().getText());
        app.hideAddItem();
        
        int selection = app.getInvoiceTable().getSelectedRow();
        if(selection != -1)
        {
            Invoices inv = app.getInvoice().get(selection);
            InvoiceItems invItems = new InvoiceItems(name, price, count, inv);
            inv.getItems().add(invItems);
            InvoicesItemsTable invItemTable = (InvoicesItemsTable) app.getItemsTable().getModel();
            invItemTable.fireTableDataChanged();
            app.getInvTable().fireTableDataChanged();
        }
    }

    private void cancelItemButton() {
        app.hideAddItem();
    }

    private void saveInvoiceButton() {

        String name = app.getNameText().getText();
        String invDate = app.getDateText().getText();
        int invNumber = app.getInvoiceNumber();

        boolean regex = invDate.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})");

        if(regex)
        {
            Invoices inv = new Invoices(invNumber, invDate, name);
            app.getInvoice().add(inv);
            app.getInvTable().fireTableDataChanged();
            app.hideAddInvoice();
        }
        else
        {
            JOptionPane.showMessageDialog(app, "Wrong date format. Please enter the date in the form DD-MM-YYYY.");
        }

        
    }

    private void cancelInvoiceButton() {
        app.hideAddInvoice();
    }

}
