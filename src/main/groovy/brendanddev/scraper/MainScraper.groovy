package brendanddev.scraper

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import groovy.json.JsonOutput
import groovy.json.JsonSlurper


class MainScraper {


    static void main(String[] args) {
        println "Starting the scraper..."

        // Test scraping quotes
        ScraperUtils.scrapeQuotes()

        // Test checking robots.txt
        ScraperUtils.checkRobotsTxt("http://quotes.toscrape.com")

        // Test saving to file
        String sampleContent = "Hello from scraping!"
        ScraperUtils.saveToFile(sampleContent, "output.txt")

        println "Scraping tests completed."
    }

}
