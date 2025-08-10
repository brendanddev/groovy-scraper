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
    }

    // Verifies the DELAY_BETWEEN_REQUESTS config constant is a positive integer
    def "test DELAY_BETWEEN_REQUESTS is a positive integer"() {
        expect:
        ScraperUtils.DELAY_BETWEEN_REQUESTS > 0
    }






}