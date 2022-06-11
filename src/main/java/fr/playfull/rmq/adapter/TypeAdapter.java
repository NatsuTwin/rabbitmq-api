package fr.playfull.rmq.adapter;

import java.util.HashMap;
import java.util.function.Function;

public class TypeAdapter {

    private final HashMap<Class<?>, Function<Object, ?>> repository = new HashMap<>();

    public TypeAdapter(){
        register(String.class, String::valueOf);
        register(Integer.class, element -> {
            if(element instanceof String string)
                return Integer.parseInt(string);
            return (int)element;
        });
        register(Double.class, element -> {
            if(element instanceof String string)
                return Double.parseDouble(string);
            return (double)element;
        });
        register(Boolean.class, element -> {
            if(element instanceof String string)
                return Boolean.parseBoolean(string);
            return (boolean)element;
        });
        register(Void.class, element -> null);
    }

    public <T> void register(Class<T> tClass, Function<Object, T> adapter) {
        repository.put(tClass, adapter);
    }


    public <T> T get(Class<T> tClass, Object object){
        try {
            return tClass.cast(repository.get(tClass).apply(object));
        } catch(NullPointerException exception){
            exception.printStackTrace();
        }
        return null;
    }
}
