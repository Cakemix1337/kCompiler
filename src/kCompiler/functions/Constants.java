package kCompiler.functions;

import java.util.prefs.Preferences;

import kCompiler.gui.MigGUI;

public class Constants {
	// @formatter:off 
    // I do not like formating here.
    public static boolean DELETE_LOG  = true; // Delete log at start
    public static boolean DELETE_BIN  = true; // Delete /bin/ at start
    public static String  PATH_BIN    = "H:/testing/compiler/bin/"; // Path to the bin folder
    public static String  PATH_LIB    = "H:/workspace/RSBot/RSBot-4013.jar"; // Path to the lib
    public static String  PATH_JAVAC  = ""; // Path to the javac
    public static String  PATH_SOURCE = "H:\\testing\\compiler\\masterFamine"; // Path to the SRC directory
    public static String  PATH_LOG    = "log.txt"; // Path to the log file
    public static String  OS          = "unknown"; // OS from OS.java
    public static String  WINDOWS     = "windows"; // OS -> Windows
    public static String  MAC         = "mac";     // OS -> Mac
    public static String  LINUX       = "linux";   // OS -> Linux
    public static String  LOG         = "";
	public static Preferences PREFS   = Preferences.userNodeForPackage(MigGUI.class);
    
}
