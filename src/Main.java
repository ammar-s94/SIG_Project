import model.FileOperations;
import model.InvoiceHeader;
import view.SigView;

import java.util.ArrayList;

//testing here
public class Main {

    public static void main(String[] args) {
    Runnable task = new SigView();
    Thread worker1 = new Thread(task);
    worker1.start();
    }
}
