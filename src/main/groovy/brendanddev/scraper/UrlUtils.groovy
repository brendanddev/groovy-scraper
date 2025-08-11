package brendanddev.scraper

/**
 * Defines a utility class for URL related operations.
 * Provides methods for validating URLs and extracting base URLs to support scraping tasks.
 */
class UrlUtils {

    /**
     * Validates the provided URL
     *
     * @param url The URL to validate
     * @return true if the URL is valid, false otherwise
     */
    static boolean isValidUrl(String url) {
        return url ==~ /^https?:\/\/.+/
    }

    /**
     * Attempts to extract the base URL from a full URL
     *
     * @param url The full URL
     * @return The base URL (protocol + host) or the original URL if extraction fails
     */
    static String extractBaseUrl(String url) {
        try {
            URL urlObj = new URL(url)
            return "${urlObj.protocol}://${urlObj.host}"

        } catch (Exception e) {
            println "Error extracting base URL: ${e.message}"
            return url
        }
    }
    
}