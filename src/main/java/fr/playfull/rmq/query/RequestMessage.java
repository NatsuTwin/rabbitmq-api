package fr.playfull.rmq.query;

import fr.playfull.rmq.marshal.RMQMarshal;

import java.util.Objects;

public class RequestMessage implements RequestComponent {

    private final String message;
    private final String extra;
    private final RMQMarshal<?> marshal;

    private RequestMessage(RequestMessageBuilder builder) {
        this.message = Objects.requireNonNull(builder.message);
        this.extra = Objects.requireNonNullElse(builder.extra, "");
        this.marshal = Objects.requireNonNullElse(builder.marshal, RMQMarshal.DEFAULT_MARSHAL);
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

    public static class RequestMessageBuilder implements RequestComponent.Builder<RequestMessageBuilder> {
        private String message;
        private String extra;
        private RMQMarshal<?> marshal;

        public RequestMessageBuilder message(String message) {
            this.message = message;
            return self();
        }

        public RequestMessageBuilder extra(String extra) {
            this.extra = extra;
            return self();
        }

        public RequestMessageBuilder marshal(RMQMarshal<?> marshal) {
            this.marshal = marshal;
            return self();
        }

        @Override
        public RequestMessage build() {
            return new RequestMessage(this);
        }

        @Override
        public RequestMessageBuilder self() {
            return this;
        }
    }

}
