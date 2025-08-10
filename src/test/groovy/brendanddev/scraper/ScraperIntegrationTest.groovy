package brendanddev.scraper

import spock.lang.Specification

/**
 * Defines the integration tests for the ScraperUtils class to validate and test 
 * methods that perform actual HTTP requests and interactions with web pages.
 */
class ScraperIntegrationTest extends Specification {

    // Tests the checkRobotsTxt method to ensure it can fetch and print robots.txt content
    def "should successfully check robots.txt"() {
        when: "checking robots.txt for educational site"
        ScraperUtils.checkRobotsTxt("http://quotes.toscrape.com")
        
        then: "should complete without throwing exception"
        notThrown(Exception)
    }

    // Tests the checkRobotsTxt method for a site without robots.txt
    def "should handle non-existent robots.txt gracefully"() {
        when: "checking robots.txt for site without one"
        ScraperUtils.checkRobotsTxt("https://httpbin.org")
        
        then: "should handle gracefully"
        notThrown(Exception)
    }

    // Tests scraping elements from a page
    def "should scrape elements successfully"() {
        when: "scraping elements from quotes.toscrape.com"
        ScraperUtils.scrapeElements("http://quotes.toscrape.com", ".quote")
        
        then: "should complete without error"
        notThrown(Exception)
    }

    // Tests scraping json elements from a page
    def "should scrape JSON data successfully"() {
        when: "scraping JSON from httpbin"
        ScraperUtils.scrapeJsonData()
        
        then: "should complete without error"
        notThrown(Exception)
    }


}