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
		try {
			// 아예 값이 없으면 NO CLI 에러 
			/*if( targetFileName == null ) { 
				throw new NotEnoughArgumentException();
			}*/
			// 있는데 틀리면 file exception 에러 자동 발생  
			// 있는데,  파일, 폴더가 없는 경우 
			if( !targetFile.exists() ) { 
				//System.out.println("No file and folder");
				
				// path인경우 
				if( targetFileName.indexOf("/") == 0 ) {
					targetFile = new File(targetFileName.substring(0,targetFileName.lastIndexOf("/")));
					targetFile.mkdirs();
					
					outputStream = new PrintWriter(targetFileName);
				}
				// 파일인 경우 
				else {	outputStream = new PrintWriter(targetFileName);	}
			}
			// 파일, 경로가 존재하는 경우.
			else {
				outputStream = new PrintWriter(targetFileName);
			}
		} /*catch ( NotEnoughArgumentException e) {
			System.exit (0);
			//경로자체가 없는 경우 
		} */catch (FileNotFoundException e) {
			System.out.println("WE CAN'T MAKE THIS ROOT\n");
			e.printStackTrace();
			System.exit (0);
		} 
		
		//파일에 저장.
		for(String str : lines) {
			outputStream.println( str );
			outputStream.flush();
		}
		
		System.out.println("파일만들기 성공 짝짜라라랄꿍꿍꿍><");
	}
} 
