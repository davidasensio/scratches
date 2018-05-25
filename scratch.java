import com.blaskay.fiesta.modules.messages.MessagesActivity;

import java.io.File;
import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.Locale;

class Sample {

    static String filename = "/Users/davidasensio/fiestapp/fiesta-android/app/src/main/java/com/blaskay/fiesta/modules/messages/MessagesActivity.java";

    public static void main(String[] args) {
        //File file = new File(filename);
//        DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());
        DateFormatSymbols dfs = new DateFormatSymbols(new Locale("es", "ES"));

        String[] dayNames = dfs.getShortWeekdays();
        for (String s : dayNames) {
            System.out.print(s + " ");
        }

    }
}