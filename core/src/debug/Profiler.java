package debug;

import java.util.HashMap;
import java.util.Map;

public class Profiler {

	private static Map<String, Long> method_starts = new HashMap<>();
	private static Map<Integer, String> idx_names = new HashMap<>();

	@Deprecated
	public static void start() {
		String method_name = Thread.getAllStackTraces().get(Thread.currentThread())[4].getMethodName();
		long start = System.currentTimeMillis();
		method_starts.put(method_name, start);
	}
	
	public static void start(String affix, int id) {
		StackTraceElement[] methods = Thread.getAllStackTraces().get(Thread.currentThread());
		String method_name = Thread.getAllStackTraces().get(Thread.currentThread())[4].getMethodName();
		idx_names.put(id, affix);
		long start = System.currentTimeMillis();
		method_starts.put(method_name + id, start);
	}

	@Deprecated
	public static void end() {
		long end = System.currentTimeMillis();
		String method_name = Thread.getAllStackTraces().get(Thread.currentThread())[4].getMethodName();
		System.out.println(method_name + ": " + (end-method_starts.get(method_name)));
		method_starts.remove(method_name);
	}

	public static void end(int id) {
		long end = System.currentTimeMillis();
		String method_name = Thread.getAllStackTraces().get(Thread.currentThread())[4].getMethodName();
		StackTraceElement[] methods = Thread.getAllStackTraces().get(Thread.currentThread());
		if(idx_names.containsKey(id) && method_starts.containsKey(method_name+id)) {
			System.out.println(id + method_name + " " + idx_names.get(id) + ": " + (end-method_starts.get(method_name+id)));
			method_starts.remove(method_name + id);
			idx_names.remove(id);
		}
		
	}
}
