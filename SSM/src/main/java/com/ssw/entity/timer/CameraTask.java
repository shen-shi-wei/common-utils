package com.ssw.entity.timer;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: ssw
 * @Date: 2020/03/20/16:00
 * @Description:
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class CameraTask extends TimerTask {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    @Override
    public void run() {
        System.out.println("定时任务测试："+dateFormat.format(new Date()));
    }
}
