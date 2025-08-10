package brendanddev.scraper

import spock.lang.Specification
import spock.lang.Unroll



class ScraperTest extends Specification {

    @Unroll
    def "test USER_AGENT is not empty"() {
        expect:
        ScraperUtils.USER_AGENT?.trim()
        ScraperUtils.USER_AGENT.length() > 0
    }







}