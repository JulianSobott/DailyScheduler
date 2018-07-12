package com.dailyscheduler.dailyscheduler.debug;

import java.util.HashMap;
import java.util.Map;

public class Profiler {

	private static Map<String, Long> method_starts = new HashMap<>();
	private static Map<Integer, String> idx_names = new HashMap<>();
	
	public static void start() {
		String method_name = Thread.getAllStackTraces().get(Thread.currentThread())[3].getMethodName();
		long start = System.currentTimeMillis();
		method_starts.put(method_name, start);
	}
	
	public static void start(String affix, int id) {
		String method_name = Thread.getAllStackTraces().get(Thread.currentThread())[3].getMethodName();
		idx_names.put(id, affix);
		long start = System.currentTimeMillis();
		method_starts.put(method_name + id, start);
	}
	
	public static void end() {
		long end = System.currentTimeMillis();
		String method_name = Thread.getAllStackTraces().get(Thread.currentThread())[3].getMethodName();
		System.out.println(method_name + ": " + (end-method_starts.get(method_name)));
		method_starts.remove(method_name);
	}

	public static void end(int id) {
		long end = System.currentTimeMillis();
		String method_name = Thread.getAllStackTraces().get(Thread.currentThread())[3].getMethodName();
		System.out.println(method_name + " " + idx_names.get(id) + ": " + (end-method_starts.get(method_name+id)));
		method_starts.remove(method_name + id);
		idx_names.remove(id);
	}
}
