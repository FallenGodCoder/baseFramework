package com.tc.controller;

import com.tc.common.BaseConstant;
import com.tc.common.ResultStatusEntity;
import com.tc.common.form.WebInput;
import com.tc.common.form.WebOutput;
import com.tc.common.utils.CheckCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录页面相关
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/index.do")
    public ModelAndView index(){
        return new ModelAndView("login.index.houtai");
    }

    /**
     * 用户登录
     * @return
     */
    @RequestMapping("/login.do")
    @ResponseBody
    public Map<String, Object> login(WebInput input){
        HttpServletRequest request = input.getRequest();
        HttpSession session = input.getRequest().getSession();

        String userName = request.getParameter("userName");//用户名
        String userPwd = request.getParameter("userPwd");//用户密码
        String checkCode = request.getParameter("checkCode");//验证码

        Map<String, Object> resultMap = new HashMap<String, Object>();
        //验证码验证
        String sessionCheckCode = (String)(session.getAttribute(String.valueOf(BaseConstant.CHECK_CODE_SESSION_KEY)));
        if(!(null != checkCode && checkCode.equals(sessionCheckCode))){
            resultMap.putAll(ResultStatusEntity.createInstance(BaseConstant.FAILURE_STRING, "验证码不正确！"));
            return resultMap;
        }

        //用户验证
        //todo:使用数据库进行验证
        //1、进行简单的与数据库比较
        //2.成功加入加密概念，采用密文比较

        return resultMap;
    }

    /**
     * 验证验证码是否正确
     * @return
     */
    @RequestMapping("/isValidcheckCode.do")
    @ResponseBody
    public Map<String, Object> isValidcheckCode(WebInput input,String checkCode){
        HttpSession session = input.getRequest().getSession();
        //验证码验证
        String attribute = (String)(session.getAttribute(BaseConstant.CHECK_CODE_SESSION_KEY));
        if(attribute == null || !attribute.equals(checkCode)){
            //验证失败
            return ResultStatusEntity.createInstance(BaseConstant.FAILURE_STRING, "验证码不正确！");
        }
        //验证通过
        return ResultStatusEntity.createInstance();
    }

    /**
     * 获取验证码图片
     * @param webInput
     * @param webOutput
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/getCheckCodeImage.do",method = RequestMethod.GET)
    public
    @ResponseBody
    HashMap<String,Object> createCheckCodeImage(WebInput webInput,WebOutput webOutput) throws IOException {
        //获取验证码
        Map<String, BufferedImage> map = CheckCodeUtil.createImage();
        //获取验证码字符串
        String code = map.keySet().iterator().next();
        //存储验证码
        webInput.getRequest().getSession().setAttribute(
                String.valueOf(
                        BaseConstant.CHECK_CODE_SESSION_KEY), code);
        //获取验证码图片
        BufferedImage code_image = map.get(code);
        //输出验证码图片
        webOutput.setContentType("jpg/jpeg");
        ImageIO.write(code_image, "jpeg", webOutput.getResponse().getOutputStream());

        return null;
    }
}
