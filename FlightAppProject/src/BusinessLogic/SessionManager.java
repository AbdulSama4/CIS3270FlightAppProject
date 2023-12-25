package BusinessLogic;


public class SessionManager {
    private static String currentUsername;
    private static String selectedFlightNum;

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static void setCurrentUsername(String username) {
        currentUsername = username;
    }

    public static String getSelectedFlightNum() {
        return selectedFlightNum;
    }

    public static void setSelectedFlightNum(String flightNum) {
        selectedFlightNum = flightNum;
    }

    public static void clearSession() {
        currentUsername = null;
        selectedFlightNum = null;
    }
}