package com.ssw.entity.timer;

import com.ssw.entity.User;
import com.ssw.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: ssw
 * @Date: 2020/03/20/16:38
 * @Description:
 */
public class MyScheduledExecutor implements Runnable {

    private String jobName;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    @Autowired
    private IUserService userService;

    MyScheduledExecutor() {
    }

    public MyScheduledExecutor(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public void run() {
        System.out.println(jobName + "定时任务测试：" + dateFormat.format(new Date()));
        User user = userService.getUserById(1);
        System.out.println(user);
    }
}
