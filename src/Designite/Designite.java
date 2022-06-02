package Designite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import Designite.SourceModel.*;
import Designite.utils.Constants;
import Designite.utils.Logger;

import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.commons.cli.*;

/**
 * 
 * This is the start of the project
 */
public class Designite {
	public static void main(String[] args) throws IOException {
		CLIUtils inputArgs = parseCLI(args);
		try {
			for (String inputPath : inputArgs.getInputFolderPaths()) {
				String designiteInput = "-i " + inputPath + " -o " + inputArgs.getOutputFolderPath();
				Designite.run(designiteInput.split(" "));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static void run(String[] args) {
		InputArgs argsObj = parseArguments(args);
		SM_Project project = new SM_Project(argsObj);
		//Logger.logFile = getlogFileName(argsObj);
		//TODO: log the version number
		project.parse();
		project.resolve();
		project.computeMetrics();
//		project.detectCodeSmells();
		if (Constants.DEBUG)
			writeDebugLog(argsObj, project);
//		Logger.log("Done.");
	}

	private static CLIUtils parseCLI(String[] args) {
		Options argOptions = new Options();

		Option input = new Option("i", "Input", true, "Input source folder path");
		input.setRequired(true);
		argOptions.addOption(input);

		Option output = new Option("o", "Output", true, "Path to the output folder");
		output.setRequired(true);
		argOptions.addOption(output);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(argOptions, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("Designite", argOptions);
			Logger.log("Quitting..");
			System.exit(1);
		}
		if(cmd==null)
		{
			System.out.println("Couldn't parse the command line arguments.");
			formatter.printHelp("Designite", argOptions);
			Logger.log("Quitting..");
			System.exit(2);
		}

		String inputFolderPath = cmd.getOptionValue("Input");
		ArrayList<String> inputPaths = new ArrayList<>();
		if (inputFolderPath.endsWith(".csv")) {
			inputPaths = CLIUtils.csvReader(inputFolderPath);
		} else {
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(inputFolderPath))) {
				for (Path path : stream) {
					if (path.getFileName().toString().startsWith(".")) continue;
					if (Files.isDirectory(path)) {
						inputPaths.add(inputFolderPath + "/" + path.getFileName());
					}
				}
			} catch (IOException e) { }
		}

		String outputFolderPath = cmd.getOptionValue("Output");
		CLIUtils inputArgs= null;
		try
		{
			inputArgs = new CLIUtils(inputPaths, outputFolderPath);
		}
		catch(IllegalArgumentException ex)
		{
			Logger.log(ex.getMessage());
			Logger.log("Quitting..");
			System.exit(3);
		}
		return inputArgs;
	}

	private static void writeDebugLog(InputArgs argsObj, SM_Project project) {
		PrintWriter writer = getDebugLogStream(argsObj);
		project.printDebugLog(writer);
		if (writer != null)
			writer.close();
	}

	private static InputArgs parseArguments(String[] args) {
		Options argOptions = new Options();

        Option input = new Option("i", "Input", true, "Input source folder path");
        input.setRequired(true);
        argOptions.addOption(input);

        Option output = new Option("o", "Output", true, "Path to the output folder");
        output.setRequired(true);
        argOptions.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(argOptions, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Designite", argOptions);
            Logger.log("Quitting..");
            System.exit(1);
        }
        if(cmd==null)
        {
        	System.out.println("Couldn't parse the command line arguments.");
        	formatter.printHelp("Designite", argOptions);
        	Logger.log("Quitting..");
        	System.exit(2);
        }
        
		String inputFolderPath = cmd.getOptionValue("Input");
        String outputFolderPath = cmd.getOptionValue("Output");
        
        InputArgs inputArgs= null;
        try
        {
        	inputArgs = new InputArgs(inputFolderPath, outputFolderPath);
        }
        catch(IllegalArgumentException ex)
        {
        		Logger.log(ex.getMessage());
        		Logger.log("Quitting..");
        		System.exit(3);
        }
        return inputArgs;
	}

	private static String getlogFileName(InputArgs argsObj) {
		String file = null;
		String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(Calendar.getInstance().getTime());
		file = argsObj.getOutputFolder() + File.separator + "DesigniteLog" + timeStamp + ".txt";
		ensureOutputFolderExists(argsObj.getOutputFolder());
		return file;
	}

	private static void ensureOutputFolderExists(String outputFolder) {
		if (outputFolder == null)
			return;
		File folder = new File(outputFolder);
		
		if (folder.exists() && folder.isDirectory())
			return;
		
		try
		{
			boolean isCreated = folder.mkdirs();
			if (!isCreated)
			{
				System.out.println("Couldn't create output folder.");
			}
		}
		catch (Exception ex)
		{
			System.out.println("Couldn't create output folder. " + ex.getMessage());
		}
	}
	private static PrintWriter getDebugLogStream(InputArgs argsObj) {
		PrintWriter writer = null;
		if (!argsObj.getOutputFolder().equals("")) {
			String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(Calendar.getInstance().getTime());
			String filename = argsObj.getOutputFolder() + "DesigniteDebugLog" + timeStamp + ".txt";
			try {
				writer = new PrintWriter(filename);
			} catch (FileNotFoundException ex) {
				Logger.log(ex.getMessage());
			}
		}
		return writer;
	}
}
