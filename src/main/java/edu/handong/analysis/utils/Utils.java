package edu.handong.analysis.utils;

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
		
		if( removeHeader == true ) {
			String line = inputstream.nextLine();
		}
		
		while( inputstream.hasNextLine() ) {
			fileLines.add(inputstream.nextLine());
		}
		inputstream.close();
		
		return fileLines;
	}
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName){
		PrintWriter outputStream = null;
		File targetFile = new File(targetFileName);
		
		try {
			if( targetFileName == null ) {
				throw new NotEnoughArgumentException("No CLI argument Exception! Please put a file path.");
			}
			else if( !targetFile.exists() ) {
				targetFile.mkdirs();
				targetFile = new File(targetFileName);
				outputStream = new PrintWriter(targetFileName);
				
			}
			else {
				outputStream = new PrintWriter(targetFileName);
			}
		} catch ( NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit (0);
			//경로자체가 없는 경우 
		} catch (FileNotFoundException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit (0);
		}

		for(String str : lines) {
			outputStream.println( str );
		}
		
	}
}

