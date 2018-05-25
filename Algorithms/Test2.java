import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test2 {
    public static void main(String[] args) {

        try {
            System.out.println("Helllllll");

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS A");
            Date date = null;
            date = formatter.parse("2018-02-16T08:47:00 +01:00");
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}