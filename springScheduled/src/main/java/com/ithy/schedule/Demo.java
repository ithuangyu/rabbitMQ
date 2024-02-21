package com.ithy.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class Demo {


    @Scheduled()
    private void demo(){
        System.out.printf("在"+new Date() +"执行了一次");
    }


    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("这是定时任务+"+new Date());
            }
        };
        timer.schedule(timerTask,0,2000);
    }
}
