package com.bettem.tms.boot.auth.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.bettem.tms.boot.auth.config.WebAuthProperties;
import com.bettem.tms.boot.auth.config.WebAuthConfig;
import com.bettem.tms.boot.auth.contants.WebConstant;
import com.bettem.tms.boot.auth.handler.UserHandler;
import com.bettem.tms.boot.auth.jwt.JwtTokenUtil;
import com.bettem.tms.boot.auth.jwt.payload.JwtPayLoad;
import com.bettem.tms.boot.auth.model.BaseUser;
import com.bettem.tms.boot.auth.utils.CurrentUserKit;
import com.bettem.tms.boot.base.controller.BaseController;
import com.bettem.tms.boot.commons.dto.RR;
import com.bettem.tms.boot.commons.utils.StringUtil;
import com.bettem.tms.boot.commons.utils.exception.BusinessException;
import com.bettem.tms.boot.commons.utils.exception.constant.BizExceptionEnum;
import com.bettem.tms.boot.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * 登录控制器
 * @author GaoFans
 */
@Controller
@Configuration
@AutoConfigureAfter({WebAuthConfig.class, WebAuthProperties.class})
public class LoginController extends BaseController {
    
    private UserHandler userHandler;

    private WebAuthProperties webAuthProperties;

    private CurrentUserKit currentUserKit;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    public LoginController(@Lazy UserHandler userHandler,
                           WebAuthProperties webAuthProperties,
                           @Lazy CurrentUserKit currentUserKit) {
        this.userHandler = userHandler;
        this.webAuthProperties = webAuthProperties;
        this.currentUserKit = currentUserKit;
    }

    private boolean checkLogin(HttpServletRequest request, HttpServletResponse response) {
        BaseUser user = currentUserKit.currentUser();
        if(user != null){
            return true;
        }
        return false;
    }

    @GetMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response, String url){
        if(checkLogin(request,response)){
            if (checkLogin(request,response)) return StringUtil.isEmpty(url) ? REDIRECT + webAuthProperties.getIndexUrl() : REDIRECT + url;
        }
        return "login";
    }

    /**
     * 验证码
     * @throws IOException
     */
    @GetMapping("captcha")
    @ResponseBody
    public void captcha() throws IOException {
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100);
        currentUserKit.setSessionAttribute(WebConstant.VERIFY_CODE,captcha.getCode());
        captcha.write(getHttpServletResponse().getOutputStream());
    }

    /**
     * 登录
     * @param username
     * @param password
     * @param verifyCode
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("login")
    @ResponseBody
    public Object login(@RequestParam(required = false) String username,
                        @RequestParam(required = false) String password,
                        @RequestParam(required = false) String verifyCode,
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        if(StringUtils.isAnyBlank(username,password,verifyCode)) throw new BusinessException(BizExceptionEnum.REQUEST_NULL);
        if (checkLogin(request,response)) return null;
        if(!verifyCode.equals(currentUserKit.getSessionAttribute(WebConstant.VERIFY_CODE))) throw new BusinessException(BizExceptionEnum.VERIFY_CODE_ERROR);
        //查询用户
        BaseUser user = userHandler.loadUserByName(username);
        if(Objects.nonNull(user)){
            throw new BusinessException(BizExceptionEnum.USER_NOT_EXISTED);
        }
        //验证密码
        if(DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
            //设置用户信息
            currentUserKit.setUserInfo(user);
            //创建token
            JwtPayLoad payLoad = new JwtPayLoad(user.getId(),user.getUsername());
            String token = JwtTokenUtil.generateToken(payLoad, webAuthProperties.getExpireTime(), webAuthProperties.getPrivateKey());
            CookieUtils.setCookie(request,response,WebConstant.COOKIE_NAME,token);
            LOGGER.debug("用户[{}]登录",payLoad.getUsername());
            HashMap<String, String> data = new HashMap<>();
            data.put(WebConstant.COOKIE_NAME,token);
            return new RR<>(data);
        }else{
            throw new BusinessException(BizExceptionEnum.PWD_NOT_RIGHT);
        }
    }


    /**
     * 获取当前用户信息
     * @return
     */
    @GetMapping("userInfo")
    @ResponseBody
    public Object userInfo(){
        BaseUser baseUser = currentUserKit.currentUser();
        return new RR<>(baseUser);
    }

    /**
     * 退出
     * @param request
     * @param response
     * @return
     */
    @PostMapping("logout")
    @ResponseBody
    public Object logout(HttpServletRequest request,
                         HttpServletResponse response){
        try {
            LOGGER.debug("用户[{}]退出登录",currentUserKit.get().getUsername());
            currentUserKit.invalidateSession();
            CookieUtils.deleteCookie(request,response,WebConstant.COOKIE_NAME);
        }catch (Exception e){
            e.printStackTrace();
            return FAIL_RESULT;
        }
        return SUCCESS_RESULT;
    }

    /**
     * token续租
     * @return
     */
    @PostMapping("reset")
    @ResponseBody
    public Object resetToken(HttpServletRequest request){
        String token = request.getHeader(WebConstant.COOKIE_NAME);
        if(StringUtil.isNotEmpty(token)){
            String publicKey = webAuthProperties.getPublicKey();
            if(JwtTokenUtil.isTokenExpired(token, publicKey)){
                try {
                    //如果还没过token超时时间,拿出其中的信息续上
                    JwtPayLoad jwtPayLoad = JwtTokenUtil.getJwtPayLoad(token, publicKey);
                    LOGGER.debug("user[{}]续租",jwtPayLoad.getUsername());
                    token = JwtTokenUtil.generateToken(jwtPayLoad, webAuthProperties.getExpireTime(), webAuthProperties.getPrivateKey());
                    HashMap<String, String> data = new HashMap<>();
                    data.put(WebConstant.COOKIE_NAME,token);
                    return new RR<>(data);
                }catch (Exception e){}
            }
        }
        return new RR<>(RR.CodeStatus.FAIL);
    }

}
