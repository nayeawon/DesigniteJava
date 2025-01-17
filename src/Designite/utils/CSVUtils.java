package Designite.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CSVUtils {
	private static final List<String> types = Arrays.asList("Abstract Function Call From Constructor", "Complex Conditional",
			"Complex Method", "Empty catch clause", "Long Identifier", "Long Method", "Long Parameter List", "Long Statement",
			"Magic Number", "Missing default", "Deficient Encapsulation", "Unexploited Encapsulation",
			"Broken Modularization", "Cyclic-Dependent Modularization", "Hub-like Modularization", "Insufficient Modularization",
			"Broken Hierarchy", "Cyclic Hierarchy", "Deep Hierarchy", "Missing Hierarchy", "Multipath Hierarchy", "Rebellious Hierarchy",
			"Wide Hierarchy"
	);

	//TODO create an integration test for checking the exporting feature
	public static void initializeCSVDirectory(String projectName, String dirPath) {
		File dir = new File(dirPath);
		createDirIfNotExists(dir);
//		cleanup(dir);
		initializeNeededFiles(dir);
	}
	
	private static void createDirIfNotExists(File dir) {
		if (!dir.exists()) {
			try {
				//The program is failing here. It couldn't create the directory.
				//I see we are providing relative path; that could be the reason
				//We may prepare the absolute path (by combining it with the output path)
				//and try again.
				if(dir.mkdirs()==false)
					System.out.print("oops, couldn't create the directory " + dir);
			} catch (Exception e) {
				e.printStackTrace();
				Logger.log(e.getMessage());
			}
		}
	}
	
	private static void cleanup(File dir) {
		if (dir.listFiles() != null) {
			for (File file : dir.listFiles()) {
				file.delete();
			}
		}
	}
	
	private static void initializeNeededFiles(File dir) {
		//createCSVFile(dir.getPath() + File.separator + Constants.TYPE_METRICS_PATH_SUFFIX, Constants.TYPE_METRICS_HEADER);
//		createCSVFile(dir.getPath() + File.separator + Constants.METHOD_METRICS_PATH_SUFFIX, Constants.METHOD_METRICS_HEADER);
//		createCSVFile(dir.getPath() + File.separator + Constants.DESIGN_CODE_SMELLS_PATH_SUFFIX, Constants.DESIGN_CODE_SMELLS_HEADER);
		//createCSVFile(dir.getPath() + File.separator + Constants.IMPLEMENTATION_CODE_SMELLS_PATH_SUFFIX, Constants.IMPLEMENTATION_CODE_SMELLS_HEADER);
		createCSVFile(dir.getPath() + File.separator + Constants.METRICS_PATH_SUFFIX, Constants.METRICS_HEADER);
	}
	
	private static void createCSVFile(String path, String header) {
		try {
			File file = new File(path);
	        if (file.exists()) return;
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.append(header);
			bufferedWriter.close();
		} catch(IOException e) {
			e.printStackTrace();
			Logger.log(e.getMessage());
		}
	}
	
	public static void addToCSVFile(String path, String row) {
		try {
			File file = new File(path);
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.append(row);
			bufferedWriter.close();
		} catch(IOException e) {
			e.printStackTrace();
			Logger.log(e.getMessage());
		}
	}
	
	public static void addAllToCSVFile(String path, List collection) {
		try {
			File file = new File(path);
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (Object obj : collection) {
				String row = (obj instanceof String) ? (String) obj : obj.toString();
				bufferedWriter.append(row);
			}
			bufferedWriter.close();
		} catch(IOException e) {
			e.printStackTrace();
			Logger.log(e.getMessage());
		}
	}

	public static void addTotalToCSVFile(String path, String metrics, List collection, List designCodeSmells) {
		try {
			File file = new File(path);
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.append(metrics);
			ArrayList<String> smells = new ArrayList<>();
			for (Object obj : collection) {
				String row = (obj instanceof String) ? (String) obj : obj.toString();
				smells.add(row);
			}
			for (Object obj : designCodeSmells) {
				String row = (obj instanceof String) ? (String) obj : obj.toString();
				smells.add(row);
			}
			for (String smellTypes : types) {
				if (smells.contains(smellTypes)) {
					bufferedWriter.append(",1");
				}
				else bufferedWriter.append(",0");
			}
			bufferedWriter.append("\n");
			bufferedWriter.close();
		} catch(IOException e) {
			e.printStackTrace();
			Logger.log(e.getMessage());
		}
	}

}
