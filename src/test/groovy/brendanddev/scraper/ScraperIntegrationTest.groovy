package brendanddev.scraper

import spock.lang.Specification

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



}