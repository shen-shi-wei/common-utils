package com.ssw.controller.user;

import com.ssw.constant.ApiResultEntity;
import com.ssw.constant.AuthResult;
import com.ssw.controller.BaseAction;
import com.ssw.entity.User;
import com.ssw.service.ITokenAuthenticationService;
import com.ssw.service.IUserService;
import com.ssw.utils.HttpTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: ssw
 * @Date: 2020/03/13/11:39
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseAction {

    @Autowired
    private IUserService userService;
    @Autowired
    private ITokenAuthenticationService tokenAuthenticationService;

    @RequestMapping("/toLogin")
    public String toLogin(HttpServletRequest request, Model model){
        return "/login/login";
    }

    @RequestMapping("/doLogin")
    public String doLogin(HttpServletRequest request, Model model, HttpServletResponse response){
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = userService.getUserById(userId);
        if (user != null){
            model.addAttribute("user", user);
//            String token = HttpTokenUtil.getToken();
            AuthResult resultJson = tokenAuthenticationService.getAccessToken("21996072", "SDxjzllwijf0nz5zxczx");
            Cookie c = new Cookie("authentication",resultJson.getData());
            c.setPath("/");
            response.addCookie(c);
            return "user/showUser";
        } else {
            return "error";
        }
    }

    @RequestMapping("/showUser")
    public String toIndex(HttpServletRequest request, Model model){
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "user/showUser";
    }

    @RequestMapping("/sendBackData")
    public void sendBackData(HttpServletRequest request, HttpServletResponse response){
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = userService.getUserById(userId);
        sendBackData(response,request,new ApiResultEntity().setSuccessCodeAndData(user));
    }

    @RequestMapping("/params")
    public void params(HttpServletRequest request, HttpServletResponse response){
        sendBackData(response,request,new ApiResultEntity().setSuccessCodeAndData(request.getParameter("params")));
    }

    @RequestMapping("/ResponseBody")
    @ResponseBody
    public String ResponseBody(HttpServletRequest request, HttpServletResponse response){
        return request.getParameter("params");
    }

    @RequestMapping("download")
    public void download(HttpServletRequest request, HttpServletResponse response){
        String docPath = request.getSession().getServletContext().getRealPath("static/docs/doc");



    }





}
