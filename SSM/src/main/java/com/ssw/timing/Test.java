package com.ssw.timing;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: ssw
 * @Date: 2020/03/20/15:42
 * @Description:
 */
public class Test {

    public static void test(){
        Timer timer = new Timer();

        //前一次执行程序结束后 2000ms 后开始执行下一次程序
        timer.schedule(new TimerTask() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            @Override
            public void run() {
                System.out.println("schedule IMP 当前时间" + dateFormat.format(new Date()));
            }
        }, 0,2000);     //两秒执行一次

        //前一次程序执行开始 后 2000ms后开始执行下一次程序
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("scheduleAtFixedRate IMP 当前时间" + this.scheduledExecutionTime());
//            }
//        },0,2000);
    }

    public void timerOneTime(){
        Timer timer = new Timer();

        //延迟1000ms执行程序
        timer.schedule(new TimerTask() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            @Override
            public void run() {
                System.out.println("IMP 当前时间" + dateFormat.format(new Date()));
            }
        }, 1000);
//        //延迟10000ms执行程序
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("IMP 当前时间" + this.scheduledExecutionTime());
//            }
//        }, new Date(System.currentTimeMillis() + 10000));
    }

    public static void main(String[] args) {
        test();
    }

}
