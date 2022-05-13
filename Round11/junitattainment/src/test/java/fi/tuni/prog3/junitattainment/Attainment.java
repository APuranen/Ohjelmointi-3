package fi.tuni.prog3.junitattainment;

import java.util.Comparator;

public class Attainment implements Comparable<Attainment>{

    private String code;
    private String number;
    private int grade;

    public static final Comparator<Attainment> CODE_STUDENT_CMP = new Comparator<>()
    { 
        @Override
        public int compare(Attainment a, Attainment b){
            int cmp = a.code.compareTo(b.code);
            if(cmp == 0){cmp = a.number.compareTo(b.number);}
            return cmp;
        }
    };

    public static final Comparator<Attainment> CODE_GRADE_CMP = new Comparator<Attainment>()
    {
            @Override
            public int compare(Attainment a, Attainment b){
                int cmp = a.code.compareTo(b.code);
                if(cmp == 0){cmp = Integer.valueOf(b.grade).compareTo(Integer.valueOf(a.grade));}
                return cmp;
            }

    };

    public Attainment(String courseCode, String studentNumber, int grade) throws IllegalArgumentException{
        this.code = courseCode;
        this.number = studentNumber;
        this.grade = grade;
    }
    public String toString(){return String.format("%s %s %d\n", code, number, grade);}
    public String getCourseCode(){return code;}
    public String getStudentNumber(){return number;}
    public int getGrade(){return grade;}
    
    @Override
    public int compareTo(Attainment o) 
    {
        int cmp = number.compareTo(o.number);
        if(cmp == 0){
            cmp = code.compareTo(o.code);
        }
        return cmp;
    }
    
}



