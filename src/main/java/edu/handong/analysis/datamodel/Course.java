package edu.handong.analysis.datamodel;

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

	
	/*public Course(String name) {
		courseName = name;
	}*/
	//public Course() {	}
	public Course(String line){
		courseName = line.split(",")[5].trim();
		studentId = line.split(",")[0].trim();
		yearMonthGraduated = line.split(",")[1].trim();
		firstMajor = line.split(",")[2].trim();
		secondMajor = line.split(",")[3].trim();
		courseCode = line.split(",")[4].trim();
		courseCredit = line.split(",")[6].trim();
		yearTaken = Integer.parseInt( line.split(",")[7].trim() );
		semesterCourseTaken = Integer.parseInt( line.split(",")[8].trim() );
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
}