package edu.handong.analysis;

import java.util.ArrayList;
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

	private Map<String, Student> students = new HashMap<String, Student>();
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String dataPath = args[0]; // csv file to be analyzed
		String resultPath = args[1]; // the file path where the results are saved.
		ArrayList<String> lines = Utils.getLines(dataPath, true);
		
		students = loadStudentCourseRecords(lines);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
		
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath);
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private Map<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		Map<String, Student> students_thash = new HashMap<String, Student>();
		
		String[] arr = new String[lines.size()];
		int size = 0;
			
		for(String student: lines) {
			arr[size++] = student;
		}
		
		Iterator<Map.Entry<String, Student>> entries = students_thash.entrySet().iterator(); 
		//students_thash.put(Integer.toString(1), student(course));
		for( int i=1; i<lines.size(); i++ ) {
			Course newCourse = new Course(arr[i]);
			if(i==1) {
				Student newStu = new Student(newCourse.getStudentId());
				newStu.addCourse(newCourse);
				students_thash.put( newCourse.getStudentId(), newStu);
			}
			else {
				while(entries.hasNext()) {
					if( !newCourse.getStudentId().equals(((Entry<String, Student>) entries).getValue().getName() )) {
						// 해쉬맵에 같은 키값을 가진 해쉬값이 없을 경우 새로 만들어서 해쉬값으로 넣어준다. 
						Student newStudent = new Student(newCourse.getStudentId());
						newStudent.addCourse(newCourse);
						
						students_thash.put ( newCourse.getStudentId(), newStudent );
					}
					((Entry<String, Student>) entries).getValue().addCourse(newCourse);
				}
			}
		}
		return students_thash;
	// do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		ArrayList<String> stringForFinal = new ArrayList<String>();
		String tempString;
		int totalNumSemester = 0;
		Student stud = new Student();
		

		// 1. 해쉬맵 바탕으로 학생 돌리기
		// 2. 학생당) 총 몇학긴지 계산바탕으로 반복문 돌려서 학기마다 getnum~ 함수써서 값 계산
		// 3. 그때마다 스트링 만들어서 arraylist에 저장 
		java.util.Iterator iter = sortedStudents.keySet().iterator();
		for(Map.Entry<String, Student> entry: sortedStudents.entrySet()) {
			stud = entry.getValue(); // 학생인스턴 1명 
			
			HashMap<String, Integer> temp = stud.getSemestersByYearAndSemester();
			Map<String, Integer> Treetemp = new TreeMap<String, Integer>(temp);
			totalNumSemester = Treetemp.size();
			
			for(int j=1; j<=totalNumSemester; j++) {
				tempString = stud.getName() + "," + totalNumSemester + "," + j + "," + stud.getNumCourseInNthSemester(j);
				stringForFinal.add( tempString );
			}
		}
		return stringForFinal;
	}
	
}
