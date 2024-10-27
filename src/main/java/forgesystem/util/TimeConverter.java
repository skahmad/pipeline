package forgesystem.util;

public class TimeConverter {
    public static String convertMillisToReadableFormat(long milliseconds) {
        long seconds = milliseconds / 1000;
        long remainingMillis = milliseconds % 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        // Remaining values
        seconds %= 60;
        minutes %= 60;

        // Build a readable format
        StringBuilder readableFormat = new StringBuilder();
        if (hours > 0) {
            readableFormat.append(hours).append(" hour").append(hours > 1 ? "s" : "").append(", ");
        }
        if (minutes > 0) {
            readableFormat.append(minutes).append(" min").append(", ");
        }
        if (seconds > 0) {
            readableFormat.append(seconds).append(" sec");
            // If there are remaining milliseconds, add them to the output
            if (remainingMillis > 0) {
                readableFormat.append(", ").append(remainingMillis).append(" ms");
            }
        } else if (remainingMillis > 0) {
            readableFormat.append(remainingMillis).append(" ms").append(remainingMillis > 1 ? "s" : "");
        }

        // Remove trailing comma and space if necessary
        String result = readableFormat.toString();
        if (result.endsWith(", ")) {
            result = result.substring(0, result.length() - 2);
        }

        return result.isEmpty() ? "0 ms" : result;
    }

    public static void main(String[] args) {
        long executionTimeInMillis = 1500; // Example: 1500 milliseconds
        String readableTime = convertMillisToReadableFormat(executionTimeInMillis);
        System.out.println("Execution Time: " + readableTime);
    }
}