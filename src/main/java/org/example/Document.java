package org.example;

public class Document {
    Document(DocumentType doc_type, int print_duration, PaperSize papersize) {
        this.doc_type = doc_type;
        this.print_duration = print_duration;
        this.papersize = papersize;
    }

    // продолжительность печати (секунды)
    int print_duration;

    enum DocumentType {
        Letter, SimpleA4, SmallLetter, BigLetter
    }
    // наименование типа документа
    DocumentType doc_type;

    // размер бумаги
    enum PaperSize {
        A4, A5, A6
    }

    PaperSize papersize;
}
