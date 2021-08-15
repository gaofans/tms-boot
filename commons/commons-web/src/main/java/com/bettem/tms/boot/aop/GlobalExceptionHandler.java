package com.bettem.tms.boot.aop;

import com.bettem.tms.boot.commons.utils.MapperUtils;
import com.bettem.tms.boot.commons.utils.exception.AuthorityException;
import com.bettem.tms.boot.commons.utils.exception.BusinessException;
import com.bettem.tms.boot.commons.utils.exception.constant.BizExceptionEnum;
import com.bettem.tms.boot.commons.dto.RR;
import com.bettem.tms.boot.exception.RepeatFormException;
import com.bettem.tms.boot.utils.HttpKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 * @author GaoFans
 */
@Configuration
@ControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截各个服务的具体异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ModelAndView businessException(BusinessException e) {
        log.error("服务具体异常:", e);
        RR error = new RR();
        error.setCode(e.getCode());
        error.setMessage(e.getMessage());
        return returnMsg(error,HttpStatus.BAD_REQUEST.value());
    }

    /**
     * 拦截权限异常
     */
    @ExceptionHandler(AuthorityException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ModelAndView authorityException(AuthorityException e) {
        log.error("权限异常:", e);
        RR error = new RR();
        error.setCode(e.getCode());
        error.setMessage(e.getMessage());
        return returnMsg(error,HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * 重复提交的异常
     * @param e
     * @return
     */
    @ExceptionHandler(RepeatFormException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ModelAndView repeatFormException(RepeatFormException e){
        log.info("重复提交:", e);
        RR error = new RR();
        error.setCode(e.getCode());
        error.setMessage(e.getMessage());
        return returnMsg(error,HttpStatus.BAD_REQUEST.value());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView runtimeException(RuntimeException e) {
        Throwable coreException = findCoreException(e);
        log.error("运行时异常:", coreException);
        RR error = new RR();
        error.setCode(BizExceptionEnum.SERVER_ERROR.getCode());
        error.setMessage(e.getMessage());
        return returnMsg(error,HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public Throwable findCoreException(Throwable e) {
        if (Objects.nonNull(e.getCause())) {
            return findCoreException(e.getCause());
        } else {
            return e;
        }
    }

    /**
     * 根据请求类型返回数据
     * @param error
     * @param code
     */
    private ModelAndView returnMsg(RR<Object> error, int code) {
        HttpServletRequest request = HttpKit.getRequest();
        HttpServletResponse response = HttpKit.getResponse();

        try {
            if(HttpKit.isAcceptHtmlRequest(request)){
                String basePath = HttpKit.getBasePath(request);
                request.setAttribute("_base_path", basePath);
                return new ModelAndView("/error/" + code,"error" , error.getMessage());
            }else {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().write(MapperUtils.obj2json(error));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
