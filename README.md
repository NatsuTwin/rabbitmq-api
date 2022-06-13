# RMQ API
Lightweight API which simplifies communication between two separate entities.

# How to use it ?

## Hooking
First, you need to hook to the RMQ API by providing a path in which the credentials file will be stored.
```java
RabbitMQAPI.hook(String filePath);
```

## Request

### General information
The request can be created using the inner class [RequestBuilder](src/main/java/fr/playfull/rmq/query/Request.java).
You do not have to provide all the elements that the Builder contains. Nevertheless, some of them are crucial to the correct working of the API.

A builder can be created by using the static access `Request.Builder<>()`. It contains a lot of parameters that can be filled in.

### Parameters

```java
Builder<T> message(String message)
```
**Must be filled !** Allows you to set the message that will be sent through the queue.

```java
Builder<T> queueName(String queueName)
```
**Must be filled !** Allows you to set the queue name.

```java
Builder<T> type(Class<T> type)
```
**Must be filled for RPC !** Allows you to set the type of data you shall receive as an answer to your RPC message.

```java
Builder<T> extra(String extra)
```
Allows you to add an extra information (e.g a specific username)

```java
Builder<T> marshal(RMQMarshal<?> marshal)
```
Allows you to define a custom marshal that will be used for serializing/deserializing the data.

```java
Builder<T> timeout(int timeout)
```
Allows you to define a custom timeout duration. The timeout matches to the duration after which the waiting consumer will die if you don't receive any data. Is needed only for **RPC**.

```java
Builder<T> timeUnit(TimeUnit timeUnit)
```
Allows you to set the TimeUnit for the timeout (SECONDS, ...). Is needed only for **RPC**.

```java
Builder<T> await(Consumer<T> consumer)
```
Allows you to perform actions on the data once you received it. Is needed only for **RPC**.


## Sending/Receiving the request

### Sending a build request
To send a request, you can do as following :
```java
RabbitMQAPI.getForwarder().send(ProtocolType protocolType, Request<?> request);
```
When you receive an answer

To listen to a specific queue :
```java
RabbitMQAPI.getForwarder().send(ProtocolType protocolType, String queueName, RMQMarshal marshal);
```
If you do not want to provide a specific marshal, you can use the default marshal : `RMQMarshal.DEFAULT_MARSHAL`


## Event system

### Different events
There are different events :

 • [TCPMessageReceivedEvent](src/main/java/fr/playfull/rmq/event/protocol/TCPMessageReceivedEvent.java) - It allows you to get the message and the extra, but you cannot set an answer.

 • [RPCMessageReceivedEvent](src/main/java/fr/playfull/rmq/event/protocol/RPCMessageReceivedEvent.java) - It allows you to set an answer, as well as to get information.

### ProtocolListener
You have to implement the [ProtocolListener](src/main/java/fr/playfull/rmq/event/protocol/ProtocolListener.java) class.
```java
public class DefaultProtocolListener implements ProtocolListener<TCPMessageReceivedEvent> {

    @Override
    public void on(TCPMessageReceivedEvent event) {
        System.out.println("It works !");
    }

}
```

### Registering to an event
You can register to an event by using : 
```java
RabbitMQAPI.getEventBus().subscribe(Class<E extends ProtocolEvent> eventClass, ProtocolListener<E extends ProtocolEvent> protocolListener);
```
