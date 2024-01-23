import java.io.Serializable;

public class Client implements Serializable{

    String name;
    String fav;

    Client() {}
    Client(String Name, String Fav){
        name = Name; fav = Fav;
    }

    String getName() { return name; }
    String getFav() { return fav; }

}