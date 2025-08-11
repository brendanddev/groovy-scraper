package brendanddev.scraper

/**
 * Defines a utility class for ANSI styles, colors, and common formatting helpers.
 */
class TerminalStyles {

    // Reset & styles
    static final String RESET = "\u001B[0m"
    static final String BOLD = "\u001B[1m"
    static final String UNDERLINE = "\u001B[4m"
    static final String ITALIC = "\u001B[3m"
    static final String DIM = "\u001B[2m"
    static final String BLINK = "\u001B[5m"

    // Colors
    static final String CYAN    = "\u001B[36m"
    static final String YELLOW  = "\u001B[33m"
    static final String GREEN   = "\u001B[32m"
    static final String RED     = "\u001B[31m"
    static final String MAGENTA = "\u001B[35m"
    static final String BLUE    = "\u001B[34m"
    static final String WHITE   = "\u001B[37m"
    static final String BLACK   = "\u001B[30m"

    // Rainbow gradient colors for banner
    static final List<String> RAINBOW = [
        "\u001B[38;5;196m", // Red
        "\u001B[38;5;202m", // Orange
        "\u001B[38;5;226m", // Yellow
        "\u001B[38;5;46m",  // Green
        "\u001B[38;5;21m",  // Blue
        "\u001B[38;5;93m",  // Purple
        "\u001B[38;5;201m"  // Pink
    ]

    // Common symbols with colors
    static String success(String msg) { return "${GREEN}✅${RESET} ${msg}" }
    static String error(String msg)   { return "${RED}❌${RESET} ${msg}" }
    static String info(String msg)    { return "${CYAN}ℹ️${RESET} ${msg}" }
    static String warn(String msg)    { return "${YELLOW}⚠️${RESET} ${msg}" }

    // Center text in given width
    static String center(String text, int width) {
        int padding = Math.max(0, (width - text.length()) / 2)
        return " ".repeat(padding) + text
    }

    // Rainbow text effect
    static String rainbow(String text) {
        StringBuilder sb = new StringBuilder()
        for (int i = 0; i < text.length(); i++) {
            sb.append(RAINBOW[i % RAINBOW.size()])
              .append(text.charAt(i))
        }
        sb.append(RESET)
        return sb.toString()
    }

}