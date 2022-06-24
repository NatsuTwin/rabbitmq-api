package fr.playfull.rmq.query;

public class RPCRequest extends Request {

    private final RequestTimeout requestTimeout;
    private final RequestAnswer requestAnswer;
    protected RPCRequest(Builder builder) {
        super(builder);

        this.requestAnswer = new RequestAnswer.Builder()
                .await(builder.consumer)
                .build();

        this.requestTimeout = new RequestTimeout.Builder()
                .timeout(builder.timeout)
                .timeUnit(builder.timeUnit)
                .build();
    }

    public RequestAnswer getRequestAnswer() {
        return this.requestAnswer;
    }

    public RequestTimeout getRequestTimeout() {
        return this.requestTimeout;
    }
}
