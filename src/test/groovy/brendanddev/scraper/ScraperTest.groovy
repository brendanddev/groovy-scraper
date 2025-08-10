package brendanddev.scraper

import spock.lang.Specification
import spock.lang.Unroll


/**
 * Defines unit tests for the ScraperUtils class to test functionality,
 * ensure configurations are loaded, and validate utility methods.
 */
class ScraperTest extends Specification {

    // Verifies the USER_AGENT config constant is set and not empty
    // and is not a bot user agent
    @Unroll
        def "should have proper user agent string"() {
        expect: "user agent should be identifiable"
        ScraperUtils.USER_AGENT != null
        ScraperUtils.USER_AGENT.length() > 0
        !ScraperUtils.USER_AGENT.contains("bot")
    }

    // Verifies the TIMEOUT config constant is a positive integer
    def "test TIMEOUT is a positive integer"() {
        expect:
        ScraperUtils.TIMEOUT > 0
        ScraperUtils.TIMEOUT <= 30000
    }

    // Verifies the DELAY_BETWEEN_REQUESTS config constant is a positive integer
    def "test DELAY_BETWEEN_REQUESTS is a positive integer"() {
        expect:
        ScraperUtils.DELAY_BETWEEN_REQUESTS > 0
        ScraperUtils.DELAY_BETWEEN_REQUESTS >= 1000
    }

    // Tests the delay between requests to ensure it respects the configured delay
    def "should implement respectful delay"() {
        given: "start time"
        long startTime = System.currentTimeMillis()
        
        when: "calling respectful delay"
        ScraperUtils.respectfulDelay()
        
        then: "should take at least 1 second"
        long endTime = System.currentTimeMillis()
        (endTime - startTime) >= 1000
    }

    // Tests that timeout value is within reasonable bounds (1 - 30 seconds)
    @Unroll
    def "should validate timeout value is reasonable"() {
        expect: "timeout should be within reasonable bounds"
        ScraperUtils.TIMEOUT >= 1000
        ScraperUtils.TIMEOUT <= 30000
    }

    // Tests that the saveToFile method writes the expected content to a file
    def "test saveToFile writes content to file"() {
        given: "sample content and a temporary file path"
        String content = "Sample scraping content"
        String filePath = "test-output.txt"

        when: "saving content to the file"
        ScraperUtils.saveToFile(content, filePath)

        then: "the file should exist and contain the expected content"
        File testFile = new File(filePath)
        testFile.exists()
        testFile.text == content

        cleanup:
        testFile.delete()
    }

    // Tests that saveToFile handles invalid file paths gracefully without throwing exceptions
    def "should handle file save errors gracefully"() {
        given: "an invalid file path"
        String content = "Test content"
        String invalidPath = "/invalid/path/file.txt"

        when: "attempting to save content to an invalid path"
        ScraperUtils.saveToFile(content, invalidPath)

        then: "no exception should be thrown"
        notThrown(Exception)
    }







}