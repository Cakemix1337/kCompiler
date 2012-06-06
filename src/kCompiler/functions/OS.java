package kCompiler.functions;

public class OS {
	public OS() {
		String osname = System.getProperty("os.name", "unkown").toLowerCase();
		if (osname.startsWith("windows")) {
			Constants.OS = "windows";
		} else if (osname.startsWith("linux")) {
			Constants.OS = "linux";
		} else if (osname.startsWith("mac") || osname.startsWith("darwin")) {
			Constants.OS = "mac";
		} else {
			Constants.OS = "unkown";
		}

		System.out.println("Found OS: " + Constants.OS);
	}
}
