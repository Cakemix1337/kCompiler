package kCompiler.functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

public class Compile {
	public Compile() throws IOException {

		final PrintStream original = System.out;
		final PrintStream originalerr = System.err;

		System.setOut(new PrintStream("programlog.txt") {
			public void println(String str) {
				original.println(str);
				Constants.LOG += str + "\n";
			}
		});

		System.setErr(new PrintStream("programerr.txt") {
			public void println(String str) {
				originalerr.println(str);
				Constants.LOG += "Error: " + str + "\n";
			}
		});

		StringBuilder run = new StringBuilder();

		Methods.deleteLog();
		Methods.fixBin();

		Methods.append(run, Constants.PATH_JAVAC);

		Methods.append(run, "-Xstdout");

		Methods.append(run, Constants.PATH_LOG);

		Methods.append(run, "-d \"" + Constants.PATH_BIN + "\"");

		Methods.append(run, "-classpath");

		Methods.append(run, Constants.PATH_LIB);

		Collection<File> files = Methods.findFolders(new File(
				Constants.PATH_SOURCE));

		if (files.size() == 0) {
			System.err.println("No files found.");
			return;
		}

		for (File file : files) {
			if (!file.isDirectory())
				continue;

			String path_NAME = file.getPath();

			if (!path_NAME.endsWith(File.separator))
				path_NAME = path_NAME.concat(File.separator);

			boolean hasRealFiles = false;
			for (File string : Arrays.asList(file.listFiles())) {
				/* Yew javac doesn't accept everything... */
				if (string.isDirectory())
					continue;
				if (string.getName().contains(".java")) {
					hasRealFiles = true;
					break;
				}
			}
			
			if (!hasRealFiles)
				continue;

			path_NAME = path_NAME.concat("*.java");

			Methods.append(run, path_NAME);
		}

		Methods.execJavac(run.toString());

		if (new File(Constants.PATH_LOG).length() == 0) {
			System.out.println("Compiling went fine.");
		} else {

			BufferedReader in = new BufferedReader(new FileReader(
					Constants.PATH_LOG));

			String str;

			while ((str = in.readLine()) != null) {
				if (str.contains("does not exist")) {
					System.err.println("Missing the RSBot-*.jar libary.");
					break;
				} else if (str.contains("is not recognized command")) {
					System.err.println("JDK is not existing.");
					break;
				} else if (str.contains("no source files")) {
					System.err.println("No SRC folder found");
					break;
				}
				System.err.println(str);
			}
			in.close();
		}
	}

}
