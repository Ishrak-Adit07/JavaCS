import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    
    public static void main(String[] args) {
        
        try{
            System.out.println("Client started");
            Socket socket = new Socket("localhost", 12345);
            if(socket != null) System.out.println("Client connected");

            Client client = new Client("Potter", "Seeker");

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            
            Object clientObject = (Object)client;
            oos.writeObject(clientObject);
            Object welcomeObject = ois.readObject();
            if(welcomeObject != null) System.out.println((String)welcomeObject);

            while(true){
                
                    Scanner sc = new Scanner(System.in);
                    String message = sc.nextLine();

                    if(message.equalsIgnoreCase("exit")) break;

                    if(message != null){

                        oos.writeObject(message);

                    }
                Object serverMessage = ois.readObject();
                if(serverMessage != null) System.out.println((String)serverMessage);
                else System.out.println("Server message null");

            }

            socket.close();

        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

}

//javac ClientMain.java
//java ClientMain
