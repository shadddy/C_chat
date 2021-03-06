package com.bao.controller;

import com.bao.entity.JsonMsg;
import com.bao.entity.Uau;
import com.bao.entity.User;
import com.bao.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

/*主页面获取内容*/
@Controller
@RequestMapping("/getinfo")
public class GetInfo {
    @Autowired
    /* userService不用在applicationContext配了，因为这里是控制器，
    变成控制器的同时已经将本身加入了ioc中
    所以再在applicationcontex配会运行出错*/
    private IUserService userService;
    
    //聊天界面获取用户名，用modelandview跳转
    @RequestMapping("context/{username}")
    public ModelAndView getcontext(@PathVariable("username")String name){

        ModelAndView mv=new ModelAndView("my");
        User user=userService.queryUserByName(name);
        mv.addObject("users",user);

        return mv;
    }
    
    
    
    //我的消息
    @RequestMapping("/mymessage")
    @ResponseBody//（写到这里要引入jackson-databind包，spring一定是要4.3.9.RELEASE，json一定是2.8.3，否则报错）
    public JsonMsg myMessage(HttpServletRequest request){
        String username=request.getParameter("username");
        
        //查找被@的数据
        List<Uau> uaulist=userService.queryUauByTouser(username);
        return JsonMsg.setuaulist(uaulist);
        
    }
    
    //admin管理员
    @RequestMapping("/admin")
    @ResponseBody//（写到这里要引入jackson-databind包，spring一定是要4.3.9.RELEASE，json一定是2.8.3，否则报错）
    public JsonMsg admin(HttpServletRequest request){
        List<User> user=userService.queryUser();
        //System.out.println(user);
        return JsonMsg.adminlist(user);
    }



}
