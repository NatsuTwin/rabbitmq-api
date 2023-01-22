package fr.erarealms.rmq.connect;

public class Credentials {

    private final String userName;
    private final String password;
    private final int port;
    private final String host;

    public Credentials(String host, int port, String user, String password){
        this.host = host;
        this.port = port;
        this.userName = user;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }
}
