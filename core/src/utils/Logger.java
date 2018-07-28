package utils;

public class Logger {
	
	public static final int error 			= 0x000001;
	public static final int warning 		= 0x000010;
	public static final int info			= 0x000100;
	public static final int datahandling 	= 0x001000;
	
	private static final  boolean log_error = false;
	private static final  boolean log_warning = false;
	private static final  boolean log_info = false;
	private static final  boolean log_datahandling = false;
	
	private static final int flags			= 0x000111;
	
	public static void log(String text, int logFlags) {
		if((logFlags & flags) == logFlags) {
			System.out.println(text);
		}
	}
}
