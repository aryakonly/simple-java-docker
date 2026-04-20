import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Time {

    public static void main(String[] args) throws Exception {
        // Time + Date
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("=== YOUR INFO ===");
        System.out.println("Date & Time : " + now.format(fmt));

        // Better IP location - ipinfo.io
        URL url = new URL("https://ipinfo.io/json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        Scanner sc = new Scanner(conn.getInputStream());
        StringBuilder json = new StringBuilder();
        while (sc.hasNext())
            json.append(sc.nextLine());
        sc.close();

        String data = json.toString();
        System.out.println("IP       : " + extract(data, "ip"));
        System.out.println("City     : " + extract(data, "city"));
        System.out.println("Region   : " + extract(data, "region"));
        System.out.println("Country  : " + extract(data, "country"));
        System.out.println("Postal   : " + extract(data, "postal"));
        System.out.println("Lat,Long : " + extract(data, "loc"));
        System.out.println("Timezone : " + extract(data, "timezone"));
        System.out.println("Org/ISP  : " + extract(data, "org"));
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