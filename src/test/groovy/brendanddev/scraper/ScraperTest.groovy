package brendanddev.scraper

import spock.lang.Specification
import spock.lang.Unroll


/**
 * Defines unit tests for the ScraperUtils class to test functionality,
 * ensure configurations are loaded, and validate utility methods.
 */
class ScraperTest extends Specification {

    // Verifies the USER_AGENT config constant is set and not empty
    @Unroll
    def "test USER_AGENT is not empty"() {
        expect:
        ScraperUtils.USER_AGENT?.trim()
        ScraperUtils.USER_AGENT.length() > 0
    }

    // Verifies the TIMEOUT config constant is a positive integer
    def "test TIMEOUT is a positive integer"() {
        expect:
        ScraperUtils.TIMEOUT > 0
        ScraperUtils.TIMEOUT <= 30000
    }

    // Verifies the DELAY_BETWEEN_REQUESTS config constant is a positive integer
    def "DELAY_BETWEEN_REQUESTS should be a positive integer"() {
        expect:
        ScraperUtils.DELAY_BETWEEN_REQUESTS > 0
        ScraperUtils.DELAY_BETWEEN_REQUESTS >= 1000
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