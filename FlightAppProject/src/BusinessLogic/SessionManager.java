package BusinessLogic;

// Class is used for managing the session information of the user

public class SessionManager {
// Username is stored for the user that logged in
    private static String currentUsername;
    
// Stores the flight number selected by the user during the session
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

 // Session is cleared when user logs out
    public static void clearSession() {
        currentUsername = null;
        selectedFlightNum = null;
    }
}