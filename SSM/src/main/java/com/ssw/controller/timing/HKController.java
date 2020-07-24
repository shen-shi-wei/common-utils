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
import com.ssw.entity.timer.CameraTask;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Timer;

@Controller
@RequestMapping("/tim")
public class HKController extends BaseAction {

    public Timer timer = null;
    public CameraTask mCameraTask = null;
    public static boolean status = false;

    @RequestMapping("/timer")
    public String timer() {
        return "/timing/timer";
    }

    @RequestMapping("startCamera")
    public void startCamera(HttpServletRequest request, HttpServletResponse response) {
        if (!status) {
            if (timer == null) {
                timer = new Timer();
            }

            if (mCameraTask == null) {
                mCameraTask = new CameraTask();
            }

            timer.schedule(mCameraTask, 0, 1000);
            status = true;
        }
        sendBackData(response, request, new ApiResultEntity().setSuccessCodeAndData("timer is starting!"));
    }

    @RequestMapping("cancelCamera")
    public void cancelCamera(HttpServletRequest request, HttpServletResponse response) {
        if (status) {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }

            if (mCameraTask != null) {
                mCameraTask.cancel();
                mCameraTask = null;
            }
            status = false;
        }
        sendBackData(response, request, new ApiResultEntity().setSuccessCodeAndData("timer is over!"));
    }
}
