package fr.playfull.rmq.serializer;

public enum SerializableType {

    INSTANCE(-1),
    INTEGER(1),
    STRING(2),
    MAP(3),
    DOUBLE(4),
    LONG(5),
    FLOAT(6),
    LIST(7),
    BOOLEAN(8),
    CUSTOM(9);

    private final int id;
    SerializableType(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
}
