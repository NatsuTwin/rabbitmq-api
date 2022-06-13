package fr.playfull.rmq.query;

public interface RequestComponent {

     interface Builder<T extends Builder<? super T>> {

        RequestComponent build();
    }

}
