package kCompiler;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import kCompiler.functions.Compile;
import kCompiler.functions.Constants;
import kCompiler.functions.FindJDK;
import kCompiler.functions.OS;
import kCompiler.gui.MigGUI;

public class Run {
	public static void main(String[] args) throws IOException {
		
		if (args.length > 1) {
			new OS(); // Find what OS user uses.
			new FindJDK(); // Find path to the JDK
			if (Constants.PATH_JAVAC.isEmpty()) {
				System.err.println("Could not find JDK.");
				return;
			}
			new Compile(); // Compile
		} else {
			String rsbotjar = Constants.PREFS.get("rsbotjar", "");
			String source = Constants.PREFS.get("source", "");
			String output = Constants.PREFS.get("output", "");
			new OS(); // Find what OS user uses.
			new FindJDK(); // Find path to the JDK
			new MigGUI(rsbotjar, source, output, Constants.PATH_JAVAC);
		}
	}
}
