package fr.playfull.rmq;

public class Article {

    private final int price;
    public Article(int price) {
        this.price = price;
    }

    public static Article of(int price) {
        return new Article(price);
    }

    public int getPrice() {
        return price;
    }
}
