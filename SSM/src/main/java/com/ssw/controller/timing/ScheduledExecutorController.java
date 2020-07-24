package com.ssw.controller.timing;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: ssw
 * @Date: 2020/03/20/15:57
 * @Description:
 */

import com.ssw.constant.ApiResultEntity;
import com.ssw.controller.BaseAction;
import com.ssw.entity.timer.MyScheduledExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/sce")
public class ScheduledExecutorController extends BaseAction {

    public ScheduledExecutorService service = null;
    public MyScheduledExecutor schedule = null;
    public static boolean status = false;
    private static DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    private static DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");

    @RequestMapping("/schedule")
    public String schedule() {
        return "/timing/schedule";
    }

    @RequestMapping("startCamera")
    public void startCamera(HttpServletRequest request, HttpServletResponse response) {
        if (!status) {
            if (service == null) {
                service = Executors.newScheduledThreadPool(10);
            }

            if (schedule == null) {
                schedule = new MyScheduledExecutor("job1");
            }

            long oneDay = 24 * 60 * 60 * 1000;
            long initDelay = getTimeMillis("00:00:01") - System.currentTimeMillis();
            initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
            service.scheduleAtFixedRate(
                    schedule,
                    initDelay,
                    oneDay,
                    TimeUnit.MILLISECONDS);
//            service.scheduleAtFixedRate(schedule, 1, 1, TimeUnit.SECONDS);
            status = true;
        }
        sendBackData(response, request, new ApiResultEntity().setSuccessCodeAndData("schedule is starting!"));
    }

    /**
     * 获取给定时间对应的毫秒数
     * //     * @param string "HH:mm:ss"
     *
     * @return
     */
    private static long getTimeMillis(String time) {
        try {
            Date currentDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return currentDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @RequestMapping("cancelCamera")
    public void cancelCamera(HttpServletRequest request, HttpServletResponse response) {
        if (status) {
            if (service != null) {
                service.shutdownNow();
                service = null;
            }

            if (schedule != null) {
                schedule = null;
            }
            status = false;
        }
        sendBackData(response, request, new ApiResultEntity().setSuccessCodeAndData("schedule is over!"));
    }
}
