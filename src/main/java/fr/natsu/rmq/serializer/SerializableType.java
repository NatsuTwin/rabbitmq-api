package fr.natsu.rmq.serializer;

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
    SHORT("SHORT"),
    UUID("UUID");

    private final String identifier;

    SerializableType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }
}
