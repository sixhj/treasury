package com.treasury;

import org.junit.Test;
import org.junit.jupiter.api.io.TempDir;

import java.util.Date;

public class MyTest {

    @Test
    public void  GetTime(){
        Date date = new Date();
        long time = date.getTime();
        System.out.println(time);
    }
}
