package edu.handong.analysis.datamodel;

import java.util.*;

public class Student{
	private String studentID;
	private ArrayList<Course> coursesTaken = new ArrayList<Course>(); // 학생이 들은 수업 목록
	private HashMap<String,Integer> semestersByYearAndSemester =  new HashMap<String,Integer>(); // key: Year-Semester e.g., 2003-1, 

	public Student( String studentID ) {
		this.studentID = studentID;
	}
	public Student() {	}
	public String getName() {
		return studentID;
	} 
	public void addCourse(Course newRecord) {
		coursesTaken.add(newRecord);
	}
	public ArrayList<Course> getCourseTaken(){
		return coursesTaken;
	}
	
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		int semester = 0;
		
		for( Course course : coursesTaken ) {
			// 2019-1 형태로 만들어주기.
			String ys = new String( course.getYearTaken() + "-" + course.getSemester() );
			if( !semestersByYearAndSemester.containsKey(ys) ) { 
				semester++; 
				semestersByYearAndSemester.put( ys, semester);
			}
		}
		return semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSemester(int semester) {
		int count = 0;
		// 해쉬맵에 value값 저장시켜주기.
		semestersByYearAndSemester = getSemestersByYearAndSemester();
		
		// semester=3이면 courseTaken 돌려서 해쉬맵의 키값의 밸류이 3이랑 같을때마다 count증가시켜주기.
		for( Course cour: coursesTaken ) {
			if( semestersByYearAndSemester.get(cour.getYearTaken() + "-" + cour.getSemester()).equals(semester)) {
				count++;
			}
		}
		return count;
	}
}