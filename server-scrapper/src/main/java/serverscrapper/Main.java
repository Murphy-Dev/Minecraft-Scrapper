package serverscrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main
{;
    
    public static void Fetch(int index) throws IOException, JSONException
    {
        try (FileWriter out = new FileWriter("./servers.json"); FileReader in = new FileReader("./servers.json")) {
            JSONArray data = new JSONArray(in.read());
            Document doc = Jsoup.connect(String.format("https://minecraft-server-list.com/page/%d", index)).get();
            doc.getElementsByClass("input").select("*").forEach((Element s) -> {

                if (Integer.parseInt(s.attr("id").substring(2)) == Float.NaN) {
                    return;
                }
                
                try {
                    for (int i = 0; data.get(i) != null; ++i) {
                        if (data.get(i) == s.attr("value")) {
                            return;
                        }
                    }
                } catch (Exception __) {}

                try {
                    data.put(s.attr("value"));
                } catch (Exception err) {
                    throw err;
                }
            });
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("""
            ███╗░░░███╗██╗███╗░░██╗███████╗░█████╗░██████╗░░█████╗░███████╗████████╗
            ████╗░████║██║████╗░██║██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔════╝╚══██╔══╝
            ██╔████╔██║██║██╔██╗██║█████╗░░██║░░╚═╝██████╔╝███████║█████╗░░░░░██║░░░
            ██║╚██╔╝██║██║██║╚████║██╔══╝░░██║░░██╗██╔══██╗██╔══██║██╔══╝░░░░░██║░░░
            ██║░╚═╝░██║██║██║░╚███║███████╗╚█████╔╝██║░░██║██║░░██║██║░░░░░░░░██║░░░
            ╚═╝░░░░░╚═╝╚═╝╚═╝░░╚══╝╚══════╝░╚════╝░╚═╝░░╚═╝╚═╝░░╚═╝╚═╝░░░░░░░░╚═╝░░░
            ------------------------------------------------------------------------
            ░█▀▀▀█ ░█▀▀▀ ░█▀▀█ ░█──░█ ░█▀▀▀ ░█▀▀█ 　 ░█▀▀▀█ ░█▀▀█ ░█▀▀█ ─█▀▀█ ░█▀▀█ ░█▀▀█ ░█▀▀▀ ░█▀▀█ 
            ─▀▀▀▄▄ ░█▀▀▀ ░█▄▄▀ ─░█░█─ ░█▀▀▀ ░█▄▄▀ 　 ─▀▀▀▄▄ ░█─── ░█▄▄▀ ░█▄▄█ ░█▄▄█ ░█▄▄█ ░█▀▀▀ ░█▄▄▀ 
            ░█▄▄▄█ ░█▄▄▄ ░█─░█ ──▀▄▀─ ░█▄▄▄ ░█─░█ 　 ░█▄▄▄█ ░█▄▄█ ░█─░█ ░█─░█ ░█─── ░█─── ░█▄▄▄ ░█─░█
            A simple minecraft server scrapper made by Vyn
            Press ^C to quit or any key to start
        """);
        Scanner stdin = new Scanner(System.in);
        stdin.nextLine();
        stdin.close();

        while (true) {
            int rand = 1;
            try {
                Document doc = Jsoup.connect("https://minecraft-servers.org/page/1000000").get();
                rand = (int)(Math.random() * (Integer.parseInt(doc.getElementsByClass("selected").first().text())));
            } catch (IOException e) { System.err.println(e.getMessage()); }

            try {
                Fetch(rand);
            } catch (Exception err) { System.err.println(err.getMessage()); }
            TimeUnit.SECONDS.sleep(10);
        }
    }


}