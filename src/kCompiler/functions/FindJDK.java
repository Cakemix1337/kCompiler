package kCompiler.functions;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class FindJDK {
	public FindJDK() {
		if (Constants.OS == Constants.WINDOWS) {
			String[] directories = { "ProgramFiles", "ProgramFiles(x86)" };
			for (String directory : directories) {
				if (System.getenv(directory) != null) {
					directory = System.getenv(directory);
					if (!new File(directory).exists()
							&& !new File(directory).isDirectory())
						continue;

					directory = Methods.fixPath(directory);
					directory += "Java\\";

					if (!new File(directory).exists())
						continue;

					File check = new File(directory);

					String[] myFiles;

					FilenameFilter filter = new FilenameFilter() {
						public boolean accept(File dir, String fileName) {
							return fileName.contains("jdk");
						}
					};

					myFiles = check.list(filter);
					if (Arrays.asList(myFiles).size() == 0)
						continue;

					for (String newDir : myFiles) {
						newDir = directory + newDir;

						newDir = Methods.fixPath(newDir);

						newDir += "bin\\javac.exe";

						if (!new File(newDir).exists())
							continue;

						System.out.println("Found javac.exe: " + newDir);
						Constants.PATH_JAVAC = newDir;
					}

				}
			}
		} else {
			System.err.println("Currently only supports windows.");
		}
	}
}
