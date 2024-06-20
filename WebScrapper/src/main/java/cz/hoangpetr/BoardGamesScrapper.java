package cz.hoangpetr;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// Webscrapper
public class BoardGamesScrapper {

    public static void main(String[] args) {

        // Set base url to scrape from
        String baseUrl = "https://www.tlamagames.com/deskove-hry/strana-";
        // Set the maximum number of pages
        int maxPages = 9;
        int index = 0;

        // Save CSV file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("deskovky.csv"))) {
            System.out.println("======================================================================================");
            System.out.println("DESKOVKY");
            for (int j = 1; j <= maxPages; j++) {
                try {
                    // Combine base URL with the current page number
                    String url = baseUrl + j;
                    // Fetch data from url
                    Document document = Jsoup.connect(url).get();
                    Elements boardGames = document.select(".swap-images");


                    for (Element boardGame : boardGames) {
                        String name = boardGame.select(".name").text();
                        String price = boardGame.select(".price").text();
                        index++;

                        // Write data in CSV file
                        System.out.println(index + ". " + name + ": " + price);
                        bw.write(name + ";" + price);
                        bw.newLine();
                    }

                    // Add delay to respect the website's robots.txt policy and to avoid being blocked
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    break; // Stop the loop in case of an error, such as a 404 page
                }
            }
            System.out.println("======================================================================================");;
        } catch (IOException ex){

        }

    }
}
