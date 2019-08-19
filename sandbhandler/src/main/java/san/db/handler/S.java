package san.db.handler;

public class S {

    public static String startWith(String data){
        return data+"%";
    }

    public static String endWith(String data){
        return "%"+data;
    }

    public static String contains(String data){
        return "%"+data+"%";
    }
}
