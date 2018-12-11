import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class projectleagueTest {

    @Test
    void getCharName(){
        projectleague pj = new projectleague();
        pj.getCharName(103);
        assertEquals("Ahri","Ahri");
    }

    @Test
    void getCharName2(){
        projectleague pj = new projectleague();
        pj.getCharName(12);
        assertEquals("Alistar","Alistar");
    }


    @Test
    void getCharName3(){
        projectleague pj = new projectleague();
        pj.getCharName(131);
        assertEquals("Diana","Diana");
    }


    @Test
    void getCharName4(){
        projectleague pj = new projectleague();
        pj.getCharName(150);
        assertEquals("Gnar","Gnar");
    }

    @Test
    void getCharName5(){
        projectleague pj = new projectleague();
        pj.getCharName(60);
        assertEquals("Elise","Elise");
    }

    @Test
    void convertCharName(){
        projectleague pj = new projectleague();
        pj.convertCharName("Ahri");
        assertEquals(103,103);
    }

    @Test
    void convertCharName2(){
        projectleague pj = new projectleague();
        pj.convertCharName("Corki");
        assertEquals(42,42);
    }

    @Test
    void convertCharName3(){
        projectleague pj = new projectleague();
        pj.convertCharName("Azir");
        assertEquals(268,268);
    }


    @Test
    void convertCharName4(){
        projectleague pj = new projectleague();
        pj.convertCharName("Zoe");
        assertEquals(142,142);
    }


    @Test
    void convertCharName5(){
        projectleague pj = new projectleague();
        pj.convertCharName("Zac");
        assertEquals(154,154);
    }

    @Test
    void calculateDiff(){
        projectleague pj = new projectleague();
        int[] a = {1,2,3};
        int[] b = {2,3,4};
        pj.calculateDiff(a,b);
        assertEquals(3,3);
    }
    
    @Test
    void calculateDiff2(){
    	projectleague pj = new projectleague();
        int[] a = {1,2,3};
        int[] b = {11,12,13};
        pj.calculateDiff(a,b);
        assertEquals(30,30);
    }
    
    @Test
    void calculateDiff3(){
    	projectleague pj = new projectleague();
        int[] a = {1,2,3,4};
        int[] b = {0,0,0,0};
        pj.calculateDiff(a,b);
        assertEquals(10,10);
    }
    
    @Test
    void calculateDiff4(){
    	projectleague pj = new projectleague();
        int[] a = {1,2};
        int[] b = {11,12};
        pj.calculateDiff(a,b);
        assertEquals(20,20);
    }
    
    @Test
     void calculateDiff5(){
    	 projectleague pj = new projectleague();
         int[] a = {10,20,30};
         int[] b = {10,20,30};
         pj.calculateDiff(a,b);
         assertEquals(0,0);
    }
    
}