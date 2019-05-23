package edu.handong.analysis.utils;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.util.TreeMap;

import edu.handong.analysis.utils.Utils.*;
import edu.handong.analysis.*;
import edu.handong.analysis.datamodel.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;

public class Utils {

	public static ArrayList<String> getLines(String file, boolean removeHeader){
		String filename = file;
		Scanner inputstream = null;
		ArrayList<String> fileLines = new ArrayList<String>();
		
		try {
			inputstream = new Scanner(new File(filename));
		} catch ( FileNotFoundException e ) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit (0);
		}
		
		/*if( removeHeader == true ) {
			String line = inputstream.nextLine();
		}*/
		
		while( inputstream.hasNextLine() ) {
			fileLines.add(inputstream.nextLine());
		}
		inputstream.close();
		
		return fileLines;
	}
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
 		PrintWriter outputStream = null;
 		//private BufferedWriter writerb = null;
		File targetFile = new File(targetFileName);
		
		//path가 경로인지 파일 이름인지
		try {
			// 아예 값이 없으면 NO CLI 에러 
			if( targetFileName == null ) { 
				throw new NotEnoughArgumentException();
			}
			// 있는데 틀리면 file exception 에러 자동 발생  
			// 있는데,  파일, 폴더가 없는 경우 
			if( !targetFile.exists() ) { 
				//System.out.println("No file and folder");
				
				// path인경우 
				if( targetFileName.lastIndexOf("/") == 0 ) {
					targetFile.mkdirs();
					
					String fileName = targetFileName.substring(targetFileName.lastIndexOf("/")+1);
					outputStream = new PrintWriter(fileName);
				}
				// 파일인 경우 
				else {	outputStream = new PrintWriter(targetFileName);	}
			}
			// 파일, 경로가 존재하는 경우.
			else {
				//경로면 파일이름 추출. 
				if( targetFileName.lastIndexOf("/") == 0 ) {
					String fileName = targetFileName.substring(targetFileName.lastIndexOf("/")+1);
					outputStream = new PrintWriter(fileName);
				}
				else {	outputStream = new PrintWriter(targetFileName);	}
			}
		} catch ( NotEnoughArgumentException e) {
			System.exit (0);
			//경로자체가 없는 경우 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit (0);
		} 
		
		//파일에 저장.
		for(String str : lines) {
			outputStream.println( str );
			outputStream.flush();
		}
	}
}

