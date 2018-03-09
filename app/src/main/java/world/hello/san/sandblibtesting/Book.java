package world.hello.san.sandblibtesting;


import san.db.handler.SanDbResult;

/**
 * Created by sanyatihan on 31-Aug-17.
 */

public class Book extends SanDbResult<Book> {

    int id;
    String title;
    String price;

    public Book() {
    }

    public Book(int id, String title, String price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public Book(String title, String price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
