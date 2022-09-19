import java.sql.Date;

public class test {
    public static void main(String[] args){
        System.out.println("Date:" + new Date(15449849919L));
        System.out.println("String: " + new String("String"));
        System.out.println("Integer:" + new Integer(123));
//        System.out.println(Integer.parseInt("abc"));
        System.out.println(new Integer(123) instanceof Integer);
    }
}
