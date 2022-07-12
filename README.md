# RMQ API
Lightweight API which simplifies communication between two separate entities.

# Maven dependency
```xml
<dependency>
    <groupId>fr.playfull.rmq</groupId>
    <artifactId>rabbitmq-api</artifactId>
    <version>3.4.1</version>
    <scope>compile</scope>
</dependency>
```

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

A builder can be created by using the static access `Request.Builder(ProtocolType protocolType)`. It contains a lot of parameters that can be filled in.

### Parameters

```java
Builder queueName(String queueName)
```
Allows you to define the queue name. **Must be filled.**

```java
Builder payload(Object payload)
```
Allows you to set the payload (message) that will be sent. **Must be filled.**

```java
Builder timeout(int timeout)
```
Allows you to define a custom timeout duration. The timeout matches to the duration after which the waiting consumer will die if you don't receive any data. Is needed only for **RPC**.

```java
Builder timeUnit(TimeUnit timeUnit)
```
Allows you to set the TimeUnit for the timeout (SECONDS, ...). Is needed only for **RPC**.

```java
Builder await(Consumer<Object> consumer)
```
Allows you to perform actions on the data once you received it. Is needed only for **RPC**. 
*You can cast the data if needed.*


## Sending/Receiving the request

### Sending a build request
To send a request, you can do as following :
```java
RabbitMQAPI.getForwarder().send(Request request);
```


To listen to a specific queue :
```java
RabbitMQAPI.getForwarder().send(ProtocolType protocolType, String queueName);
```


### Passing objects dynamically
You can now transfer objects through your queue. Nevertheless, every object must respect some rules to be able 
to be transferred. We will make for example an Account class :

```java
public class Account implements RMQSerializable {

    private final String name;
    private final int age;

    public Account(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

}
```
As you can see, the class must implement the `RMQSerializable` interface.

#### Registering
To register your new object as serializable, you also need to provide a `SerializableFactory`
interface :

```java
public class AccountFactory implements SerializableFactory<Account> {
    
    @Override
    public Account create(Map<String, Object> data) {
        return new Account((String)data.get("name"), (int)data.get("age"));
    }
    
}
```
**The `data` map keys are the same as your class field names.**

#### Registering the factory
Finally, you need to register your factory as following :
```java
RabbitMQAPI.getBufferManager().addFactory(Account.class, new AccountFactory());
```

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
