import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain{

    public static void main(String[] args) {
        
        try{

            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server started");

            while(true){

                Socket socket = serverSocket.accept();
                System.out.println("Client accepted");

                //new server thread start
                new ServerThread(socket);
            }

        }catch(IOException e){
            e.printStackTrace();
        }



    }

}


class ServerThread implements Runnable{

    Socket clientSocket;
    Thread thread;
    Client client;

    ServerThread(Socket clientS){
        clientSocket = clientS;
        
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run(){

        try{

            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

            Object newClient = ois.readObject();
            client = (Client)newClient;

            System.out.println(client.getName()+" accepted");
            String welcomeMessage = "Welcome "+client.getName();
            oos.writeObject(welcomeMessage);

            Boolean clientConnected = true;
            while(clientConnected){

                Object clientMessage = ois.readObject();
                if(clientMessage != null) System.out.println((String)clientMessage);
                else clientConnected = false;

                String serverMessage = (String)clientMessage;
                serverMessage = serverMessage.toUpperCase();

                oos.writeObject(serverMessage);

            }

        }catch(ClassNotFoundException | IOException e){
            //e.printStackTrace();
        }

        try{
            clientSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

}

//javac ServerMain.java
//java ServerMain