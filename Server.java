import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{

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

            while(true){

                Object clientMessage = ois.readObject();
                if(clientMessage != null) System.out.println((String)clientMessage);
                else System.out.println("Client message is null");

                String serverMessage = (String)clientMessage;
                serverMessage = serverMessage.toUpperCase();

                oos.writeObject(serverMessage);

            }

        }catch(ClassNotFoundException | IOException e){
            e.printStackTrace();
        }

        try{
            clientSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

}