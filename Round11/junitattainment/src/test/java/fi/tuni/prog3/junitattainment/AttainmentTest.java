package fi.tuni.prog3.junitattainment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AttainmentTest {

    @Test
    public void testGetCourseCode(){
        String code = "KAS.KAY.420";
        Attainment instance = new Attainment("KAS.KAY.420","195358416" , 3);

        assertEquals(code, instance.getCourseCode());
    }

    @Test
    public void testGetStudentNumber(){
        String number = "195358416";
        Attainment instance = new Attainment("KAS.KAY.420","195358416" , 3);

        assertEquals(number, instance.getStudentNumber());
    }

    @Test
    public void testGetGrade(){
        int grade = 3;
        Attainment instance = new Attainment("KAS.KAY.420","195358416" , 3);

        assertEquals(grade, instance.getGrade());
    }

    @Test
    public void testToString(){
        Attainment instance = new Attainment("KAS.KAY.420","195358416" , 3);

        String code = "KAS.KAY.420";
        String number = "195358416";
        int grade = 3;

        String message = String.format("%s %s %d", code, number, grade);
        assertEquals(message, instance.toString());
    }

    @Test
    public void testBuilder(){
        assertThrows(IllegalArgumentException.class, () -> new Attainment(null, null, 3));
    }

    @Test
    public void testCompareTo(){
        Attainment i1 = new Attainment("KAS.KAY.420","195358416" , 3);
        Attainment i2 = new Attainment("JO.310", "195358416", 3);

        int cmp = i1.compareTo(i2);
        assertTrue( cmp > 0);

    }
}