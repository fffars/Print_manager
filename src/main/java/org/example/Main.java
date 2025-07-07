package org.example;

public class Main {
    public static void main(String[] args) {
        Print_manager printManager = new Print_manager();
        printManager.add_doc(new Document(Document.DocumentType.SimpleA4, 3, Document.PaperSize.A4));
        printManager.add_doc(new Document(Document.DocumentType.Letter, 2, Document.PaperSize.A5));
        printManager.add_doc(new Document(Document.DocumentType.BigLetter, 5, Document.PaperSize.A6));
        printManager.print();
    }
}