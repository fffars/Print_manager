package org.example;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Print_manager {
    // очередь печати
    LinkedList<Document> documents;

    LinkedList<Document> printedDocuments;
    boolean isPrinting = false;

    Print_manager() {
        documents = new LinkedList<>();
        printedDocuments = new LinkedList<>();
    }

    void stop() {
        //  Остановка диспетчера
        // Печать документов в очереди отменяется. На выходе должен быть список ненапечатанных документов.
        System.out.println("Диспетчер остановлен");
        isPrinting = false;
        System.out.println("Список ненапечатанных документов:");
        for (Document doc : documents) {
            System.out.println(doc.doc_type);
        }
    }

    void add_doc(Document doc) {
        //  Принять документ на печать
        // Метод не должен блокировать выполнение программы.
        // запустить в отдельном потоке
        Runnable tmp_runnable = new Runnable() {
            @Override
            public void run() {
                documents.add(doc);
                System.out.println("Документ \"" + doc.doc_type + "\" добавлен в очередь на печать");
            }
        };
        Thread thread = new Thread(tmp_runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void cancel_current_doc() {
        //  Отменить печать принятого документа, если он еще не был напечатан.
        if (isPrinting) {
            try {
                System.out.println("Печать документа \"" + documents.getLast().doc_type + "\" отменена");
                documents.removeLast();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * * Получить отсортированный список напечатанных документов
     * @param sort_type тип сортировки (1 - по порядку печати, 2 - по типу документов, 3 - по продолжительности печати,
     *                  4 - по размеру бумаги)
     * @return
     */
    List<Document> get_sort_list(int sort_type) {
        //  Получить отсортированный список
        // напечатанных документов. Список может
        // быть отсортирован на выбор: по порядку
        // печати, по типу документов, по продолжительности печати, по размеру бумаги.
        // по продолжительности печати


        LinkedList<Document> tmp = (LinkedList<Document>) printedDocuments.clone();


        switch (sort_type)
        {
            case 1:
                // по порядку печати
                break;
            case 2:
                tmp.sort(new Comparator<Document>() {
                    @Override
                    public int compare(Document o1, Document o2) {
                        return o1.doc_type.compareTo(o2.doc_type);
                    }
                });
                break;
            case 3:
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
                break;
            case 4:
                tmp.sort(new Comparator<Document>() {
                    @Override
                    public int compare(Document o1, Document o2) {
                        return o1.papersize.compareTo(o2.papersize);
                    }
                });
                break;
        }
        return tmp;
    }

    void calc_middle_print_time() {
        //  Рассчитать среднюю продолжительность печати напечатанных документов
        double sum = 0;
        for (Document doc : printedDocuments) {
            sum += doc.print_duration;
        }
        System.out.println("Средняя продолжительность печати напечатанных документов: " + sum / printedDocuments.size());
    }

    void print() {
        //  Печать документов
        while (!documents.isEmpty()) {
            isPrinting = true;
            if (isPrinting) {
                Document doc = documents.getFirst();
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
            }

            documents.removeFirst();
        }
    }
}
