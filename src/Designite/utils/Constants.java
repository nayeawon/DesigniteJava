package Designite.utils;

import java.io.File;

public class Constants {
	
	/*public static final String PATH_OF_THRESHOLDS = System.getProperty("user.dir") 
			+ File.separator
			+ "thresholds.txt";*/
	
	//public static String CSV_DIRECTORY_PATH = System.getProperty("user.dir") 
	//		+ File.separator
	//			+ "default_csv";
	
	public static final String TYPE_METRICS_PATH_SUFFIX = "typeMetrics.csv";
	public static final String METRICS_PATH_SUFFIX = "metrics.csv";
//	public static final String METHOD_METRICS_PATH_SUFFIX = "methodMetrics.csv";
//	public static final String DESIGN_CODE_SMELLS_PATH_SUFFIX = "designCodeSmells.csv";
	public static final String IMPLEMENTATION_CODE_SMELLS_PATH_SUFFIX = "implementationCodeSmells.csv";

	public static final String METRICS_HEADER = "Project.Package Name"
			+ ",Type Name"
			+ ",Method Name"
			+ ",MLOC"
			+ ",CC"
			+ ",PC"
			+ ",NOF"
			+ ",NOPF"
			+ ",NOM"
			+ ",NOPM"
			+ ",LOC"
			+ ",WMC"
			+ ",NC"
			+ ",DIT"
			+ ",LCOM"
			+ ",FANIN"
			+ ",FANOUT"
			+ ",Abstract Function Call From Constructor"
			+ ",Complex Conditional"
			+ ",Complex Method"
			+ ",Empty catch clause"
			+ ",Long Identifier"
			+ ",Long Method"
			+ ",Long Parameter List"
			+ ",Long Statement"
			+ ",Magic Number"
			+ ",Missing default"
			+ ",Deficient encapsulation"
			+ ",Unexploited modularization"
			+ ",Broken modularization"
			+ ",Cyclically-dependent modularization"
			+ ",Hub-like modularization"
			+ ",Insufficient modularization"
			+ ",Broken hierarchy"
			+ ",Cyclic hierarchy"
			+ ",Deep hierarchy"
			+ ",Missing hierarchy"
			+ ",Multipath hierarchy"
			+ ",Rebellious hierarchy"
			+ ",Wide hierarchy"
			+ "\n";
	
	public static final String TYPE_METRICS_HEADER = "Project Name"
			+ ",Package Name"
			+ ",Type Name"
			+ ",NOF"
			+ ",NOPF"
			+ ",NOM"
			+ ",NOPM"
			+ ",LOC"
			+ ",WMC"
			+ ",NC"
			+ ",DIT"
			+ ",LCOM"
			+ ",FANIN"
			+ ",FANOUT"
			+ "\n";
	
	public static final String METHOD_METRICS_HEADER = "Project Name"
			+ ",Package Name"
			+ ",Type Name"
			+ ",MethodName"
			+ ",LOC"
			+ ",CC"
			+ ",PC"
			+ "\n";
	
	public static final String DESIGN_CODE_SMELLS_HEADER = "Project Name"
			+ ",Package Name"
			+ ",Type Name"
			+ ",Code Smell"
			+ "\n";
	
	public static final String IMPLEMENTATION_CODE_SMELLS_HEADER = "Project Name"
			+ ",Package Name"
			+ ",Type Name"
			+ ",Method Name"
			+ ",Code Smell"
			+ "\n";
	public static final boolean DEBUG = false;
}
