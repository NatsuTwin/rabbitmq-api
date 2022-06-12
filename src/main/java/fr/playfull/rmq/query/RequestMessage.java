package fr.playfull.rmq.query;

import fr.playfull.rmq.marshal.RMQMarshal;

public class RequestMessage implements RequestComponent {

    private final String message;
    private final String extra;
    private final RMQMarshal<?> marshal;

    private RequestMessage(Builder builder) {
        this.message = builder.message;
        this.extra = builder.extra;
        this.marshal = builder.marshal;
    }

    public String getMessage() {
        return message;
    }

    public String getExtra() {
        return extra;
    }

    public RMQMarshal<?> getMarshal() {
        return marshal;
    }

    public static class Builder implements RequestComponent.Builder<Builder> {
        private String message;
        private String extra = "";
        private RMQMarshal<?> marshal = RMQMarshal.DEFAULT_MARSHAL;

        public Builder message(String message) {
            this.message = message;
            return self();
        }

        public Builder extra(String extra) {
            this.extra = extra;
            return self();
        }

        public Builder marshal(RMQMarshal<?> marshal) {
            this.marshal = marshal;
            return self();
        }

        @Override
        public RequestMessage build() {
            return new RequestMessage(this);
        }

        @Override
        public Builder self() {
            return this;
        }
    }

}
