package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FileOperations {
    private static String pathHeader="InvoiceHeader.csv";
    private static String pathLine="InvoiceLine.csv";
    public FileOperations(){}
//    public static void main(String[] args) {
//
//        ArrayList<InvoiceHeader> emptyTable = new ArrayList<InvoiceHeader>(); //temp table
//        ArrayList<InvoiceHeader> table; // table to return temp table to after filling it with all data
//
//        table=readFile(pathHeader,emptyTable);//pass the invoice header file
//        table=readFile(pathLine,emptyTable);  //pass the invoice lines file
//
//
//        // external loop for printing the invoice headers, inner loop to print the invoice lines
//
//        for(int i=0;i<table.size();i++){
//            System.out.println(table.get(i).getInvoiceNum());
//            System.out.println("{");
//            System.out.print(table.get(i).getInvoiceDate());
//            System.out.print(", ");
//            System.out.println(table.get(i).getCustomerName());
//            for(int j=0;j<table.get(i).lines.size();j++)
//                {
//                    System.out.print(table.get(i).lines.get(j).getItemName());
//                    System.out.print(", ");
//                    System.out.print(table.get(i).lines.get(j).getItemPrice());
//                    System.out.print(", ");
//                    System.out.println(table.get(i).lines.get(j).getCount());
//                }
//            System.out.println("}");
//            System.out.println();
//        }
//    }

        public static ArrayList<InvoiceHeader> readFile(String file, ArrayList<InvoiceHeader> emptyTable) throws Exception{

            FileInputStream fis =null;
            try {
                fis = new FileInputStream(new File(file)); //creating file object
                int size = fis.available();
                byte[] b = new byte[size];
                fis.read(b); //read file as bytes

                String bs=new String(b); //convert bytes read to string for parsing

                String[] sentences = bs.split(",|\\r\\n",0); // convert string data to array of stings
                if (file.contains(pathHeader)) {

                    for (int i = 0; i < sentences.length; i++) {
                        if (i % 3 == 2) {
                            try {
                                emptyTable.add(new InvoiceHeader(sentences[i - 1], sentences[i], Integer.parseInt(sentences[i - 2])));
                            }catch(Exception e){
                                throw new Exception("Wrong InvoiceHeader file data format", new Throwable(e));
                            }
                        }
                    }
                }
                else if (file.contains(pathLine)) {
                    if(emptyTable.size()!=0)
                        for (int i = 0; i < sentences.length; i++) {
                            if (i % 4 == 3) {
                                for(int j=0;j<emptyTable.size();j++) {
                                    try{
                                        if (Integer.parseInt(sentences[i - 3]) == emptyTable.get(j).getInvoiceNum()) {

                                            emptyTable.get(j).lines.add(new InvoiceLine(sentences[i - 2], Integer.parseInt(sentences[i - 1]),
                                                    Integer.parseInt(sentences[i]), Integer.parseInt(sentences[i - 3])));
                                        }
                                    }
                                    catch(Exception e){
                                        throw new Exception("Wrong InvoiceLines file data format", new Throwable(e));
                                    }

                                }
                            }
                        }
                    else{
                        throw new Exception("Choose InvoiceHeader file first");
                    }
                }
                else{
                    throw new Exception("Invalid File");
                }
            }
            catch (FileNotFoundException e){e.printStackTrace();}
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try { // file close throws exception
                    if (fis != null) { // check if not null to avoid null pointer exception
                        fis.close();
                    }
                } catch (IOException e) {e.printStackTrace();}
            }
            return  emptyTable;

//            FileInputStream fis =null;
//            try {
//                fis = new FileInputStream(new File(file)); //creating file object
//                int size = fis.available();
//                byte[] b = new byte[size];
//                fis.read(b); //read file as bytes
//
//                String bs=new String(b); //convert bytes read to string for parsing
//
//                String[] sentences = bs.split(",|\\r\\n",0); // convert string data to array of stings
//                String pathHeader = "InvoiceHeader.csv";
//                if (file.contains(pathHeader)) {
//
//                    for (int i = 0; i < sentences.length; i++) {
//                        if (i % 3 == 2) {
//                            try {
//                                emptyTable.add(new InvoiceHeader(sentences[i - 1], sentences[i], Integer.parseInt(sentences[i - 2])));
//                            }catch(Exception e){
//                                throw new Exception("Wrong InvoiceHeader file data format", new Throwable(e));
//                            }
//                            }
//                    }
//                }
//                else{
//                    throw new Exception("Invalid File");
//                }
//            } catch (IOException e){e.printStackTrace();} finally {
//                try { // file close throws exception
//                    if (fis != null) { // check if not null to avoid null pointer exception
//                        fis.close();
//                    }
//                } catch (IOException e) {e.printStackTrace();}
//            }
//
//            Thread.sleep(500);
//
//
//            try {
//                file=file.replace("InvoiceHeader.csv", "InvoiceLine.csv");
//                fis = new FileInputStream(new File(file)); //creating file object
//                int size = fis.available();
//                byte[] b = new byte[size];
//                fis.read(b); //read file as bytes
//
//                String bs=new String(b); //convert bytes read to string for parsing
//
//                String[] sentences = bs.split(",|\\r\\n",0); // convert string data to array of stings
//                String pathLine = "InvoiceLine.csv";
//                if (file.contains(pathLine)) {
//
//                    if(emptyTable.size()!=0)
//                        for (int i = 0; i < sentences.length; i++) {
//                            if (i % 4 == 3) {
//                                for(int j=0;j<emptyTable.size();j++) {
//                                    try{
//                                        if (Integer.parseInt(sentences[i - 3]) == emptyTable.get(j).getInvoiceNum()) {
//
//                                            emptyTable.get(j).lines.add(new InvoiceLine(sentences[i - 2], Integer.parseInt(sentences[i - 1]),
//                                                    Integer.parseInt(sentences[i]), Integer.parseInt(sentences[i - 3])));
//                                        }
//                                    }
//                                    catch(Exception e){
//                                        throw new Exception("Wrong InvoiceLines file data format", new Throwable(e));
//                                    }
//
//                                }
//                            }
//                        }
//                    else{
//                        throw new Exception("Choose InvoiceHeader file first");
//                    }
//                }
//                else{
//                    throw new Exception("Invalid File");
//                }
//            } catch (IOException e){e.printStackTrace();} finally {
//                try { // file close throws exception
//                    if (fis != null) { // check if not null to avoid null pointer exception
//                        fis.close();
//                    }
//                } catch (IOException e) {e.printStackTrace();}
//            }
//
//            return  emptyTable;
        }
        public static void writeFile(ArrayList<InvoiceHeader> headers){

        }
    }

