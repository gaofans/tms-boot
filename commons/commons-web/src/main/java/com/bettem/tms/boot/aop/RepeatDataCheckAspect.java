package com.bettem.tms.boot.aop;

import com.bettem.tms.boot.annotation.RepeatDataCheck;
import com.bettem.tms.boot.commons.utils.MapperUtils;
import com.bettem.tms.boot.commons.utils.StringUtil;
import com.bettem.tms.boot.constant.WebExceptionEnum;
import com.bettem.tms.boot.exception.RepeatFormException;
import com.bettem.tms.boot.utils.HttpKit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 防重复提交切面
 */
@Aspect
@Configuration
public class RepeatDataCheckAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepeatDataCheckAspect.class);
    private static final String REPEAT_DATA = "_TMS_REPEAT_DATA";
    @Pointcut(value = "@annotation(com.bettem.tms.boot.annotation.RepeatDataCheck)")
    public void cutService() {}

    @Before("cutService()")
    public void checkRepeatData(JoinPoint point) throws Throwable {
        HttpSession session = null;
        try{
            session = HttpKit.getSession();
        }catch (Exception e){
            LOGGER.warn("无法获取session");
            return;
        }
        MethodSignature msig = (MethodSignature)point.getSignature();
        Object target = point.getTarget();
        Class<?> currentClass = target.getClass();
        Method currentMethod = currentClass.getMethod(msig.getName(), msig.getParameterTypes());
        RepeatDataCheck annotation = currentMethod.getAnnotation(RepeatDataCheck.class);
        String[] params = annotation.value();
        Stream<Object> stream = Arrays.stream(point.getArgs());
        //根据指定的参数获取
        if(params != null && params.length > 0){
            stream = stream.filter(o -> Arrays.asList(params).contains(o));
        }
        //拼接字符串
        String nowUrlParams = stream
                .map(s -> {
                    if(s instanceof MultipartFile){
                        return ((MultipartFile)s).getOriginalFilename();
                    }else if(s instanceof MultipartFile[]){
                        MultipartFile[] files = ((MultipartFile[])s);
                        return Arrays
                                .stream(files)
                                .map(MultipartFile::getOriginalFilename)
                                .collect(Collectors.joining());
                    }else{
                        try {
                            return MapperUtils.obj2json(s);
                        } catch (Exception e) {
                            return "";
                        }
                    }
                })
                .collect(Collectors.joining(","));
        if(!StringUtil.isEmpty(nowUrlParams)){
            nowUrlParams += HttpKit.getRequest().getRequestURI();
            Object preUrlParams = session.getAttribute(REPEAT_DATA);
            if (preUrlParams == null) {
                session.setAttribute(REPEAT_DATA, nowUrlParams);
            } else {// 否则，已经访问过页面
                // 如果上次url+数据和本次url+数据相同，则表示重复添加数据
                if (preUrlParams.toString().equals(nowUrlParams)) {
                    throw new RepeatFormException(WebExceptionEnum.REPEAT_FORM);
                } else {// 如果上次 url+数据 和本次url加数据不同，则不是重复提交
                    session.setAttribute(REPEAT_DATA, nowUrlParams);
                }

            }
            session.setAttribute(REPEAT_DATA, nowUrlParams);
        }
    }
}
