package edu.handong.analysis.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.TreeMap;

import edu.handong.analysis.utils.Utils.*;
import edu.handong.analysis.*;
import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Utils {

	public static ArrayList<Course> getLines(String file, boolean removeHeader){
		ArrayList<Course> courses = new ArrayList<Course>();
		BufferedReader reader;
		 
		try {
			reader = Files.newBufferedReader( Paths.get(file) );
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
			
			for (CSVRecord csvRecord: csvParser) {
	            // Accessing Values by Column Index
	            courses.add( new Course(csvRecord.get(0), csvRecord.get(1), csvRecord.get(2), csvRecord.get(3), csvRecord.get(4), csvRecord.get(5), Integer.parseInt( csvRecord.get(7)), Integer.parseInt( csvRecord.get(8))));
			}
		} catch ( Exception e ) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit (0);
		}

		return courses;
	}
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
 		PrintWriter outputStream = null;
		File targetFile = new File(targetFileName);
		
		//path가 경로인지 파일 이름인지
		if( !targetFile.exists() ) { 
			if(targetFile.getParent() != null ) {
				targetFile.getParentFile().mkdirs();
			}
		}
		
		try {
			outputStream = new PrintWriter(targetFile);
		} catch (Exception e) {
			System.out.println("This is wrong root. Write again please.");
			System.exit(0);
		}
		//파일에 저장.
		for(String str : lines) {
			outputStream.println( str );
			outputStream.flush();
		}
		
		System.out.println("<< The file is saved>>");
	}
} 
