package edu.handong.analysis.datamodel;

import org.apache.commons.cli.Options;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVFormat;

public  class Course{
	private String courseName;
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;

	public Course(String studentId, String yearMonthGraduated, String firstMajor, String secondMajor, String courseCode, String courseName, int yearTaken, int semesterCourseTaken){
		this.courseName = courseName;
		this.studentId = studentId;
		this.yearMonthGraduated = yearMonthGraduated;
		this.firstMajor = firstMajor;
		this.secondMajor = secondMajor;
		this.courseCode =  courseCode;
		this.yearTaken= yearTaken;
		this.semesterCourseTaken = semesterCourseTaken;
		//line을 받아서 trim을 통해 필드들 저장하기.
	} 
	public String getStudentId() {
		return studentId;
	}
	public int getYearTaken() {
		return yearTaken;
	}
	public int getSemester() {
		return semesterCourseTaken;
	}
	public String getCourseName() {
		return courseName;
	}
	public String getCourseCode() {
		return courseCode;
	}
}