package fr.playfull.rmq.serializer;

public enum SerializableType {

    INSTANCE("INSTANCE"),
    INTEGER("INTEGER"),
    STRING("STRING"),
    MAP("MAP"),
    DOUBLE("DOUBLE"),
    LONG("LONG"),
    FLOAT("FLOAT"),
    LIST("LIST"),
    BOOLEAN("BOOLEAN"),
    CUSTOM("CUSTOM"),
    SHORT("SHORT");

    private final String identifier;

    SerializableType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }
}
