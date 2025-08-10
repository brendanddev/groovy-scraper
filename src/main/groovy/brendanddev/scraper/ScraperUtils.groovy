package brendanddev.scraper

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class ScraperUtils {

    static final JsonSlurper jsonSlurper = new JsonSlurper()
    static final String USER_AGENT = "Mozilla/5.0"
    static final int TIMEOUT = 5000
    static final int DELAY_BETWEEN_REQUESTS = 1000


    /**
     * Scrapes elements from a web page based on the provided CSS selector.
     *
     * @param url The URL of the page to scrape.
     * @param cssSelector The CSS selector to find elements.
     * @param attribute Optional. If provided, prints the specified attribute value of each element.
     *                  If not provided, prints the text content of each element.
     */
    static void scrapeElements(String url, String cssSelector, String attribute = null) {
        try {
            // Connect to the URL and fetch the HTML document
            Document doc = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .timeout(TIMEOUT)
                .get()
            
            // Select elements based on the provided CSS selector
            Elements elements = doc.select(cssSelector)
            if (elements.isEmpty()) {
                println "No elements found for selector '${cssSelector}' at ${url}"
                return
            }
            elements.each { Element element ->
                if (attribute) {
                    println element.attr(attribute)
                } else {
                    println element.text()
                }
            }
            // Add delay after scraping
            respectfulDelay()
        } catch (Exception e) {
            println "Error during scraping: ${e.message}"
        }
    }

    /**
     * Scrapes the first HTML table from the specified URL and prints its headers and row data.
     * 
     * param url The URL of the web page containing the table to scrape.
     *           Defaults to "https://webscraper.io/test-sites/tables" if not provided.
     */
    static void scrapeTableData(String url = ":https//webscraper.io/test-sites/tables") {
        println "Starting table scrape for URL: ${url}"
        try {
            // Connect to the URL and fetch the HTML document
            Document doc = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .timeout(TIMEOUT)
                .get()
            
            // Select the first table element
            Element table = doc.select("table").first()
            if (!table) {
                println "No table found at ${url}"
                return
            }

            // Extract table headers as list of strings
            List<String> headers = table.select("thead tr th").collect { it.text() }
            println "Table Headers: ${headers.join(', ')}"

            // Extract rows and print cell data
            Elements rows = table.select("tbody tr")
            rows.each { Element row ->
                List<String> cellData = row.select("td").collect { it.text() }
                println "Row Data: ${cellData.join(', ')}"
            }

            // Add delay after scraping
            respectfulDelay()
            println "-" * 60 

        } catch (Exception e) {
            println "Error during table scraping: ${e.message}"
        }
    }

    /**
     * Scrapes JSON data from the specified URL and prints it to the console.
     *
     * @param url The URL to fetch JSON data from. 
     *            Defaults to "https://httpbin.org/json" if not provided.
     */
    static void scrapeJsonData(String url = "https://httpbin.org/json") {
        try {
            // Connect to the URL and fetch raw JSON content as a string
            String jsonContent = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .timeout(TIMEOUT)
                .ignoreContentType(true)
                .execute()
                .body()
            
            // Parse json content
            def jsonData = jsonSlurper.parseText(jsonContent)

            println "JSON Data from ${url}:"
            jsonData.each { key, value ->
                println "${key}: ${value}"
            }
        } catch (Exception e) {
            println "Error during JSON scraping: ${e.message}"
        }
    }

    /**
     * Scrapes form metadata from the specified URL and prints form details.
     *
     * @param url The URL of the web page containing the form to scrape.
     *            Defaults to "https://httpbin.org/forms/post" if not provided.
     */
    static void scrapeWithFormData(String url = "https://httpbin.org/forms/post") {
        try {
            // Connect to the URL and fetch the HTML document
            Document doc = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .timeout(TIMEOUT)
                .get()

            // Extract form info
            Elements forms = doc.select("form")

            // Print form details
            forms.each { form ->
                println "Form action: ${form.attr('action')}"
                println "Form method: ${form.attr('method')}"
                
                Elements inputs = form.select("input, textarea, select")
                inputs.each { input ->
                    println "Input: ${input.attr('name')} (type: ${input.attr('type')})"
                }
                println "-" * 30
            }
        } catch (Exception e) {
            println "Error during form data scraping: ${e.message}"
        }
    }

    /**
     * Scrapes quotes from the website 'http://quotes.toscrape.com/'.
     *
     * This method connects to the site, parses the HTML to find the quote blocks,
     * extracts the quote text, author, and tags, and prints them to the console.
     * It includes a delay between requests to be respectful of the server load.
     */
    static void scrapeQuotes() {
        try {
            String url = "http://quotes.toscrape.com/"

            Document doc = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .timeout(TIMEOUT)
                .get()
            
            Elements quotes = doc.select("div.quote")

            quotes.each { Element quote ->

                String text = quote.select("span.text").text()
                String author = quote.select("small.author").text()
                Elements tags = quote.select("div.tags a.tag")
                
                println "Quote: ${text}"
                println "Author: ${author}"
                println "Tags: ${tags.collect { it.text() }.join(', ')}"
                println "-" * 50
                
                Thread.sleep(100)
            }
        } catch (Exception e) {
            println "Error during scraping: ${e.message}"
        }
    }


    /**
     * Saves the given string content to a file at the specified path.
     *
     * @param content The text content to write to the file.
     * @param filePath The path (including filename) where the content should be saved.
     */
    static void saveToFile(String content, String filePath) {
        try {
            new File(filePath).text = content
            println "Content saved to ${filePath}"
        } catch (IOException e) {
            println "Error saving content to file: ${e.message}"
        }
    }

    /**
     * Fetches the robots.txt file for a given base URL.
     * Prints the content of the robots.txt file if it exists.
     *
     * @param baseUrl The base URL of the website to check.
     */
    static void checkRobotsTxt(String baseUrl) {
        try {
            String robotsUrl = "${baseUrl}/robots.txt"
            String robotsContent = Jsoup.connect(robotsUrl)
                .userAgent(USER_AGENT)
                .timeout(TIMEOUT)
                .ignoreContentType(true)
                .execute()
                .body()
            
            if (robotsContent) {
                println "Robots.txt content for ${baseUrl}:"
                println robotsContent
            }

        } catch (Exception e) {
            println "Could not fetch robots.txt: ${e.message}"
            println "Site may not have a robots.txt or it's inaccessible."
        }
    }

    /**
     * Pauses the execution for a delay between HTTP requests to avoid overwhelming the target server
     */
    static void respectfulDelay() {
        try {
            Thread.sleep(DELAY_BETWEEN_REQUESTS)
        } catch (InterruptedException e) {
            println "Delay interrupted: ${e.message}"
        }
    }




}