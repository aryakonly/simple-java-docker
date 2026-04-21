import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        // Basic date like your format
        Date currentDate = new Date();
        System.out.println("Hello, Docker! Current date: " + currentDate);

        // Bonus - location via IP
        URL url = new URL("https://ipinfo.io/json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        Scanner sc = new Scanner(conn.getInputStream());
        StringBuilder json = new StringBuilder();
        while (sc.hasNext())
            json.append(sc.nextLine());
        sc.close();

        String data = json.toString();
        System.out.println("City     : " + extract(data, "city"));
        System.out.println("Region   : " + extract(data, "region"));
        System.out.println("Timezone : " + extract(data, "timezone"));
    }

    static String extract(String json, String key) {
        String search = "\"" + key + "\": \"";
        int start = json.indexOf(search);
        if (start == -1) {
            search = "\"" + key + "\":\"";
            start = json.indexOf(search);
        }
        start += search.length();
        int end = json.indexOf("\"", start);
        return (start > search.length() - 1) ? json.substring(start, end) : "N/A";
    }
}