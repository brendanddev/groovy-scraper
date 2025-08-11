package brendanddev.scraper

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Defines a utility class for displaying, formatting, and saving scraping results.
 * It provides methods to display results to the console and save results to files in various formats (text, JSON, CSV).
 */
class OutputFormatter {

    /** 
     * Displays a list of scraping results in the console
     *
     * @param results The list of results to display
     */
    private static void displayResults(List<String> results) {
        println "\nFound ${results.size()} result(s):"
        println "-" * 50
        
        results.eachWithIndex { result, index ->
            println "${index + 1}: ${result}"
            if (index >= 9 && results.size() > 10) {
                println "... and ${results.size() - 10} more results"
                return
            }
        }
    }
    
    /**
     * Saves the scraping results to a file in the specified format
     *
     * @param results  The list of scraped data items
     * @param filename The name of the file to save the results
     * @param format   The output format: 'text', 'json', or 'csv'
     * @param url      The URL that was scraped (for metadata)
     * @param selector The CSS selector used during scraping (for metadata)
     */
    static void saveResults(List<String> results, String filename, String format, String url, String selector) {
        String content = generateOutput(results, format, url, selector)
        ScraperUtils.saveToFile(content, filename)
        println "Results saved to ${filename}"
    }

    /**
     * Generates the output content from scraping results in the specified format
     * 
     * @param results  The list of scraped data items
     * @param format   The output format requested 
     * @param url      The URL that was scraped 
     * @param selector The CSS selector used during scraping
     * @return A formatted string representing the scraping results
     */
    private static String generateOutput(List<String> results, String format, String url, String selector) {
        switch (format.toLowerCase()) {
            case 'json':
                return generateJsonOutput(results, url, selector)
            case 'csv':
                return generateCsvOutput(results, selector)
            // Text
            default:
                return generateTextOutput(results, url, selector)
        }
    }

    /**
     * Generates JSON formatted output of the scraping results, including metadata about the session
     *
     * @param results  The list of scraped data items
     * @param url The URL that was scraped
     * @param selector The CSS selector used during scraping
     * @return A pretty-printed JSON string
     */
    private static String generateJsonOutput(List<String> results, String url, String selector) {
        def data = [
            scraping_info: [
                url: url,
                selector: selector,
                timestamp: getCurrentTimestamp(),
                count: results.size()
            ],
            results: results
        ]
        return groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(data))
    }

    /**
     * Generates CSV formatted output of the scraping results
     *
     * @param results  The list of scraped data items.
     * @param selector The CSS selector used (used as CSV header).
     * @return A CSV formatted string with index and results.
     */
    private static String generateCsvOutput(List<String> results, String selector) {
        StringBuilder csv = new StringBuilder()
        csv.append("index,${selector}\n")
        results.eachWithIndex { result, index ->
            String escapedResult = result.replace('"', '""')
            csv.append("${index + 1},\"${escapedResult}\"\n")
        }
        return csv.toString()
    }
    
    /**
     * Generates plain text formatted output of the scraping results, including metadata about the session
     */
    private static String generateTextOutput(List<String> results, String url, String selector) {
        StringBuilder text = new StringBuilder()
        text.append("Web Scraping Results\n")
        text.append("===================\n")
        text.append("URL: ${url}\n")
        text.append("Selector: ${selector}\n")
        text.append("Timestamp: ${getCurrentTimestamp()}\n")
        text.append("Count: ${results.size()}\n\n")
        text.append("Results:\n")
        text.append("-" * 30 + "\n")
        
        results.eachWithIndex { result, index ->
            text.append("${index + 1}: ${result}\n")
        }
        return text.toString()
    }

    /**
     * Gets the current timestamp
     */
    static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }

}