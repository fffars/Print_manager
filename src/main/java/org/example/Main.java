package org.example;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Print_manager printManager = new Print_manager();
        printManager.add_doc(new Document(Document.DocumentType.SimpleA4, 3, Document.PaperSize.A4));
//        printManager.stop();
    }
}

class Print_manager {
    // список документов
    LinkedList<Document> documents;
    LinkedList<Document> printedDocuments;
    boolean isPrinting = false;

    Print_manager ()
    {
        documents = new LinkedList<>();
        printedDocuments = new LinkedList<>();
    }

    void stop() {
        // TODO Остановка диспетчера
        // Печать документов в очереди отменяется. На выходе должен быть список ненапечатанных документов.
        System.out.println("Диспетчер остановлен");
        System.out.println("Список ненапечатанных документов:");
        for (Document doc : documents)
        {
            System.out.println(doc.doc_type);
        }
    }

    void add_doc(Document doc)
    {
        // TODO Принять документ на печать
        // Метод не должен блокировать выполнение программы.
        documents.add(doc);
        System.out.println("Документ \"" + doc.doc_type + "\" добавлен в очередь на печать");
        print();
    }

    void cancel_current_doc()
    {
        // TODO Отменить печать принятого документа, если он еще не был напечатан.
        if (isPrinting)
        {
            try
            {
                System.out.println("Печать документа \"" + documents.get(documents.size() - 1).doc_type + "\" отменена");
                documents.remove(documents.size() - 1);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    List<Document> get_sort_list()
    {
        // TODO Получить отсортированный список
        // напечатанных документов. Список может
        // быть отсортирован на выбор: по порядку
        // печати, по типу документов, по продолжительности печати, по размеру бумаги.

        // по продолжительности печати
        LinkedList<Document> tmp = (LinkedList<Document>) printedDocuments.clone();
        tmp.sort(new Comparator<Document>() {
            @Override
            public int compare(Document o1, Document o2) {
                if (o1.print_duration > o2.print_duration)
                    return 1;
                else if (o1.print_duration < o2.print_duration)
                    return -1;
                return 0;
            }
        });
        return tmp;
    }

    void calc_middle_print_time()
    {
        // TODO Рассчитать среднюю продолжительность печати напечатанных документов
        double sum = 0;
        for (Document doc : printedDocuments)
        {
            sum += doc.print_duration;
        }
        System.out.println("Средняя продолжительность печати напечатанных документов: " + sum / printedDocuments.size());
    }

    void print()
    {
        // TODO Печать документов
        if (!documents.isEmpty())
        {
            for (Document doc : documents)
            {
                isPrinting = true;
                // печать документа
                System.out.println("Печать документа: \"" + doc.doc_type + "\", размер бумаги: \"" + doc.papersize + "\", продолжительность печати: \"" + doc.print_duration + "\"");
                // подождать продолжительность печати
                try {
                    Thread.sleep(doc.print_duration * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Документ \"" + doc.doc_type + "\" напечатан");
                isPrinting = false;
                printedDocuments.add(doc);
//                documents.remove(doc);
            }
        }
    }
}

class Document {
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