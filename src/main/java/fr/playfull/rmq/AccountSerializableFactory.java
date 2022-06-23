package fr.playfull.rmq;

import fr.playfull.rmq.serializer.factory.SerializableFactory;

import java.util.Map;

public class AccountSerializableFactory implements SerializableFactory<Account> {

    @Override
    public Account create(Map<String, Object> data) {
        System.out.println("Looping data:");

        double value = (double)data.get("age");
        double articlePrice = (double)((Map<String,Object>)data.get("article")).get("price");
        return new Account((String)data.get("name"), (int)value, Article.of((int)articlePrice));
    }
}
