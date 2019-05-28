package edu.handong.analysis;

import java.util.ArrayList;
import java.util.Formatter;

import org.apache.commons.cli.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Iterator;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer {
	
	private String dataPath; // csv file to be analyzed
	private String resultPath; // the file path where the results are saved.
	private String courseCode;
	private int startYear;
	private int endYear;
	private int analOpt;
	private boolean help ,value=false;
	private String courseName;
	private Map<String, Student> students = new HashMap<String, Student>();
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		ArrayList<String> linesToBeSaved;
		Options options = createOption();
		if ( parseOption(options, args) ) {
			if (help) {
				printHelp(options);
				System.out.println( "<<This is printed since you want>>");
				//return;
			}
			//parse가 된 이후에 진행되어야 함.
			
			ArrayList<Course> courses = Utils.getLines(dataPath, true);
			 
			students = loadStudentCourseRecords(courses);
			
			// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
			Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
			linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
			Utils.writeAFile(linesToBeSaved, resultPath);
		}
		
	}
	
	//cmd창에 입력된거 option에 따라 값 넣어주기, 오류발생시 exception 발생.
	private boolean parseOption(Options options, String[] args) {
		
		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine cmd = parser.parse(options, args);
			
			dataPath = cmd.getOptionValue("i");
			resultPath = cmd.getOptionValue("o");
			analOpt = Integer.parseInt( cmd.getOptionValue("a") );
			if(analOpt == 1) { 
				value=false;
				//courseCode = cmd.getOptionValue("c");
			}
			else { 
				value=true; 
				courseCode = cmd.getOptionValue("c");
			}
			startYear =  Integer.parseInt( cmd.getOptionValue("s") );
			endYear =  Integer.parseInt( cmd.getOptionValue("e") );
			help = cmd.hasOption("h");
			
		} catch(Exception e) { //최상위 클래스 넣어서 한번에 처리, exception 나오면 도움말 출력  
			System.out.println("<<This is option problem>>");
			System.out.println(e.getMessage() + "\n");
			printHelp(options);
		 	System.exit(0);
		}
		return true;
	}

	private void printHelp(Options options) {
		// TODO Auto-generated method stub
		HelpFormatter Formatter = new HelpFormatter(); // 도움말 자동으로 만들어주는 클래
		String header = "HGU Course Analyzer";
		String footer = "";
		Formatter.printHelp("HGUCourseCounter", header, options, footer, true);
	}

	// DEFINITION
	private Options createOption() {
		Options options = new Options();
		
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set a input file path") // description
				.hasArg() //값받아야 하니
				.argName("Input file name") //argument name이 어떤 걸 의미하는지 보여주는 역
				.required()
				.build()); //반드시 필요하다는 걸 의미, 안들어오면 exception 발생.
		
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path") // description
				.hasArg() //값받아야 하니
				.argName("Output file name") //argument name이 어떤 걸 의미하는지 보여주는 역
				.required()
				.build());
		
		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year\r\n") // description
				.hasArg() //값받아야 하니
				.argName("Analysis option") //argument name이 어떤 걸 의미하는지 보여주는 역
				.required()
				.build());
		
		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for '-a 2' option") // description
				.hasArg()//값받아야 하니
				.argName("course code") //argument name이 어떤 걸 의미하는지 보여주는 역
				.required(value)
				.build());
		
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002") // description
				.hasArg() //값받아야 하니
				.argName("Start year for analysis") //argument name이 어떤 걸 의미하는지 보여주는 역
				.required()
				.build());
		
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("Set the end year for analysis e.g., -e 2005") // description
				.hasArg() //값받아야 하니
				.argName("End year for analysis") //argument name이 어떤 걸 의미하는지 보여주는 역
				.required()
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page") // description
				//.hasArg() //값받아야 하니
				.argName("Help") //argument name이 어떤 걸 의미하는지 보여주는 역       
				.build());
		
		return options;
	}

	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private Map<String,Student> loadStudentCourseRecords(ArrayList<Course> courses) {
		
		HashMap<String, Student> students_thash = new HashMap<String, Student>();
		
		for( int i=1; i<courses.size(); i++ ) {
					// 해쉬맵에 같은 키값을 가진 해쉬값이 없을 경우 새로 만들어서 해쉬값으로 넣어준다. 
					if( students_thash.containsKey(courses.get(i).getStudentId()) ) {
						students_thash.get(courses.get(i).getStudentId()).addCourse(courses.get(i));
					}
					else {
						Student newStudent = new Student(courses.get(i).getStudentId());
						newStudent.addCourse( courses.get(i) );	
							
						students_thash.put ( courses.get(i).getStudentId(), newStudent );
					}
		}
		
		return students_thash;
	}


	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		ArrayList<String> stringForFinal = new ArrayList<String>();
		HashMap<String, Integer> infoPerCourseCode = new HashMap<String, Integer>();
		HashMap<String, Integer> infoPerYearSem = new HashMap<String, Integer>();
		String tempString;
		int totalNumSemester = 0;
		Student stud = new Student();
		Integer a = 0;
		
		switch(analOpt) {
			case 1:
				// 1. 해쉬맵 바탕으로 학생 돌리기
				// 2. 학생당) 총 몇학긴지 계산바탕으로 반복문 돌려서 학기마다 getnum~ 함수써서 값 계산
				// 3. 그때마다 스트링 만들어서 arraylist에 저장 
				stringForFinal.add("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester");
				
				java.util.Iterator iter = sortedStudents.keySet().iterator();
				for(Map.Entry<String, Student> entry: sortedStudents.entrySet()) {
					stud = entry.getValue(); // 학생인스턴 1명 
					
					HashMap<String, Integer> temp = stud.getSemestersByYearAndSemester();
					//size 필요해서!
					Map<String, Integer> Treetemp = new TreeMap<String, Integer>(temp);
					//totalNumSemester = Treetemp.size();
					
					for(int j=1; j<=Treetemp.size(); j++) {
						tempString = stud.getName() + "," + Treetemp.size() + "," + j + "," + stud.getNumCourseInNthSemester(j);
						if (tempString == null) { break; }
						stringForFinal.add( tempString );
					}
				}
				break;
			
			//해당 수업을 수강한 학생의 비율을 년별로 보여주는 결과가 저장된 파일을 output file로 저장 
			case 2:
				stringForFinal.add("Year, Semester, CouseCode, CourseName, TotalStudents, StudentsTaken, Rate");
				
				for(Map.Entry<String, Student> entry: sortedStudents.entrySet()) {
					stud = entry.getValue(); // 학생 1명당 생각해주기  
					
					// 1.courseTaken받아오기.
					ArrayList<Course> coursesPerStudent = stud.getCourseTaken(); 
					TreeMap<String, Integer> A = new TreeMap<String, Integer>( stud.getSemestersByYearAndSemester() );
					
					// 2. 학생 1명당 getSemestersByYearAndSemester로 받아온 해쉬맵 돌리기 -> infoPerYearSemeste랑 값이 같고 기존목록에 있으면
					// +1 해주고, 기존목록에 없으면 추가.
					for( Map.Entry<String, Integer> elem : A.entrySet()) { // String, Integer을 가진다면 
						if ( Integer.parseInt( elem.getKey().split("-")[0] )>= startYear && Integer.parseInt( elem.getKey().split("-")[0] ) <= endYear ) {
							if( infoPerYearSem.containsKey( elem.getKey()) ) { // 학생이 학교를 다닌 년-학기와 같은게 있으면 거기에 value + 1
								int k = infoPerYearSem.get(elem.getKey());
								infoPerYearSem.put(elem.getKey(), ++k);
							}
							else {	infoPerYearSem.put( elem.getKey(), 1);	}
						}
					}
					
					// 3. courseTaken돌려서 courseCode랑 같으면 그에 해당하는 년도-학기 만들고, 
					for( Course cour : coursesPerStudent ) {
						if ( cour.getYearTaken() >= startYear && cour.getYearTaken() <= endYear ) {
							String temp = cour.getYearTaken() + "-" + cour.getSemester();
					 
							// 같은게 있을 경우 해당년도-학기 목록이 있다면 학생수 +1
							// 없다면 학생수=1 해서 새로 넣어주기.
							if(cour.getCourseCode().equals( courseCode )) {
								courseName = cour.getCourseName();
								
								if( infoPerCourseCode.containsKey(temp) ) { 
									int k = infoPerCourseCode.get(temp); // ex) 2019-1이 목록에 있으면 2019-1에 그 수업을 들은 학생들의 수를 받아온다.
									infoPerCourseCode.put(temp, ++k);
								}
								else {	
									infoPerCourseCode.put( temp, 1 );
								}
							}
							// 같은거 없을 경우 그냥 넘어가기.  
						}
					}
				}
				
				
				/////////출력단계////////////////////
				//TreeMap<String, Integer> t = new TreeMap<String, Integer>(infoPerCourseCode);
				TreeMap<String, Integer> p = new TreeMap<String, Integer>(infoPerCourseCode);
				
				for (Map.Entry<String, Integer> elem : p.entrySet() ) {
					Integer year = Integer.parseInt( elem.getKey().split("-")[0] );
					Integer sem = Integer.parseInt( elem.getKey().split("-")[1] );
					
					a = infoPerYearSem.get( elem.getKey() );
					int b = infoPerCourseCode.get(elem.getKey());
					double rate = b / (double) a * 100; 
					tempString = year+","+sem +","+ courseCode+","+courseName+","+a+","+p.get(elem.getKey())+","+String.format("%.1f", rate)+"%";
					stringForFinal.add( tempString );
				}
				break;
		}
		return stringForFinal;
	}
}
	