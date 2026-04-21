# 🐳 simple-java-docker

A simple Java application containerized with Docker that displays the current **date**, **time**, and **location** (via IP geolocation using [ipinfo.io](https://ipinfo.io)).

---

## 📁 Project Structure

```
simple-java-docker/
├── src/
│   └── Main.java
├── Dockerfile
└── README.md
```

---

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/aryakonly/simple-java-docker.git
cd simple-java-docker
```

### 2. Build the Docker Image

```bash
docker build -t java-abc .
```

### 3. Run the Container

```bash
docker run --network host java-abc
```

---

## 📄 Sample Output

```
Hello, Docker! Current date: Tue Apr 21 06:16:59 GMT 2026
City     : Amravati
Region   : Maharashtra
Timezone : Asia/Kolkata
```

---

## 🛠️ Tech Stack

| Tool | Details |
|------|---------|
| Java | 17 |
| Base Image | `eclipse-temurin:17-jdk-alpine` |
| Docker | Latest |
| Location API | [ipinfo.io](https://ipinfo.io) (free, no key needed) |

---

## 📦 Dockerfile

```dockerfile
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY src/Main.java /app/Main.java

RUN javac Main.java

CMD ["java", "Main"]
```

---

## ☕ src/Main.java

```java
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        Date currentDate = new Date();
        System.out.println("Hello, Docker! Current date: " + currentDate);

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
```

---

## ⚠️ Notes

- Location = **IP-based**, not GPS. City/region level only.
- `--network host` required — without it, Docker uses container NAT IP, not real IP.
- No API key needed for ipinfo.io (free tier: 50k req/month).

---

## 🐛 Common Errors & Fixes

| Error | Cause | Fix |
|-------|-------|-----|
| `Could not find or load main class Main` | Wrong CMD in Dockerfile | Use `CMD ["java", "Main"]` |
| `wrong name: src/Main` | `package src;` at top of file | Remove `package` declaration |
| Location = N/A | No internet in container | Use `--network host` flag |
| Wrong city shown | Docker NAT IP used | Use `docker run --network host` |

---

## 👤 Author

**aryakonly** — [GitHub](https://github.com/aryakonly/simple-java-docker)

---

## 📜 License

Open source under the [MIT License](LICENSE).
