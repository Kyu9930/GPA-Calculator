package me.ashkan.gpacalculator;

/**
 * A course class that stores information pertaining a course.
 * Created by Ashkan on 22/01/2015.
 */
public class Course {
    private String courseCode;
    private double gpaWeight;
    private double gpaValue;

    /**
     * Constructor for Course object that stores the given course code, gpa weight and gpa value.
     *
     * @param courseCode the course code
     * @param gpaWeight  the GPA weight
     * @param gpaValue   the GPA value
     */
    public Course(String courseCode, double gpaWeight, double gpaValue) {
        this.courseCode = courseCode;
        this.gpaWeight = gpaWeight;
        this.gpaValue = gpaValue;
    }

    /**
     * Get the course code
     *
     * @return the course code
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Get the gpa weight
     *
     * @return the gpa weight
     */
    public double getGpaWeight() {
        return gpaWeight;
    }

    /**
     * Get the gpa value
     *
     * @return the gpa value
     */
    public double getGpaValue() {
        return gpaValue;
    }
}
