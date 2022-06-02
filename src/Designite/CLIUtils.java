package Designite;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CLIUtils {
    private static String filePath = "/data/CGYW/YW/CodeSmell/";
    //    private static String filePath = "/Users/nayeawon/Desktop/Exp/";
    private static ArrayList<String> inputFolderPaths;
    private static String outputFolderPath;

    public CLIUtils(ArrayList<String> inputFolderPaths, String outputFolderPath) {
        this.inputFolderPaths = inputFolderPaths;
        this.outputFolderPath = outputFolderPath;
    }
    public static ArrayList<String> getInputFolderPaths() { return inputFolderPaths; }
    public static String getOutputFolderPath() { return outputFolderPath; }

    public static ArrayList<String> csvReader(String inputPath) {
        ArrayList<String> URLList = new ArrayList<String>();
        try {
            int URLColumnNumber = 0;
            Reader in = new FileReader(inputPath);

            CSVParser parser = CSVFormat.EXCEL.parse(in);

            for (CSVRecord record : parser) {
                if(record.getRecordNumber() == 1) {
                    for(int i = 0; i < record.size(); i++) {
                        if(record.get(i).toLowerCase().contains("github")) {
                            URLColumnNumber = i;
                            System.out.println(URLColumnNumber);
                        }
                    }
                }
                else if(record.getRecordNumber() > 1) {
                    String data = record.get(URLColumnNumber);
                    String temp = gitClone(data);
                    System.out.println(temp);
                    URLList.add(gitClone(data));
                }
            }
        } catch( Exception e ){
            e.printStackTrace();
        }
        return URLList;
    }

    public static String gitClone(String inputURL) throws GitAPIException {
        Pattern pattern = Pattern.compile("(git@|ssh|https://)github.com/()(.*?)$");
        Matcher matcher = pattern.matcher(inputURL);
        if (matcher.find()) {
            File file = new File(filePath + matcher.group(3));
            if (file.exists()) return filePath + matcher.group(3);
            Git.cloneRepository().setURI(inputURL).setDirectory(file).call();
        }
        return filePath + matcher.group(3);
    }
}
