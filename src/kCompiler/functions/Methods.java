package kCompiler.functions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

public class Methods {

	public static void execJavac(String args) {
		try {
			Process p = Runtime.getRuntime().exec(args);
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String fixPath(String path) {
		if (!path.endsWith(File.separator))
			path = path.concat(File.separator);
		return path;
	}

	public static void deleteLog() {
		if (!Constants.DELETE_LOG)
			return;

		if (new File(Constants.PATH_LOG).exists())
			new File(Constants.PATH_LOG).delete();
	}

	public static void fixBin() {
		if (!Constants.DELETE_BIN)
			return;
		/* Why do I think like this?
		 * if (new File(Constants.PATH_BIN).exists()) { new
		 * File(Constants.PATH_BIN).delete(); }
		 */
		new File(Constants.PATH_BIN).mkdir();
	}

	public static Collection<File> findFolders(final File start_Folder) {
		@SuppressWarnings("serial")
		Collection<File> folderArray = new ArrayList<File>() {
			{
				add(start_Folder);
			}
		};
		findFolders(start_Folder, folderArray);
		return folderArray;
	}

	public static void findFolders(File path, Collection<File> folderArray) {
		File[] children = path.listFiles();
		if (children != null) {
			for (File child : children) {
				folderArray.add(child);
				findFolders(child, folderArray);
			}
		}
	}

	public static void append(StringBuilder string_Builder, String string) {
		string_Builder.append(string + ' ');
	}

	public static File getSettingsDirectory() {
		String userHome = System.getProperty("user.home");
		if (userHome == null) {
			throw new IllegalStateException("user.home==null");
		}
		File home = new File(userHome);
		File settingsDirectory = new File(home, ".kCompiler");
		if (!settingsDirectory.exists()) {
			if (!settingsDirectory.mkdir()) {
				throw new IllegalStateException(settingsDirectory.toString());
			}
		}
		return settingsDirectory;
	}

	/**
	 * @author Paris
	 * @param sourceURL
	 *            inputURL
	 * @return String Parsed url
	 */
	public static String makeRaw(String sourceURL) {

		final HashMap<String, String> map = new HashMap<String, String>(5);

		map.put("pastebin\\.com/(\\w+)", "pastebin.com/raw.php?i=$1");
		map.put("pastie\\.org/(?:pastes/)?(\\d+)", "pastie.org/pastes/$1/text");
		map.put("pastebin\\.ca/(\\d+)", "pastebin.ca/raw/$1");
		map.put("codepad\\.org/(\\w+)", "codepad.org/$1/raw.txt");

		for (final Entry<String, String> entry : map.entrySet()) {
			sourceURL = sourceURL.replaceAll(entry.getKey(), entry.getValue());
		}

		return sourceURL;
	}
}
