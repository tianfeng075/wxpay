package com.weixin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.WeixinService;

@Controller
public class WXServiceController {
	private static final Logger logger = LoggerFactory.getLogger(WXServiceController.class); 
	 /**
    * 微信消息接收和token验证
    * @param model
    * @param request
    * @param response
    * @throws IOException
    */
   @RequestMapping("checktoken.do")
   public void checktoken(Model model, HttpServletRequest request,HttpServletResponse response) {
       boolean isGet = request.getMethod().toLowerCase().equals("get");
       PrintWriter print;
       if (isGet) {
           String signature = request.getParameter("signature");// 微信加密签名
           String timestamp = request.getParameter("timestamp");// 时间戳
           String nonce = request.getParameter("nonce");// 随机数
           String echostr = request.getParameter("echostr");// 随机字符串
           logger.info("signature="+signature);
           logger.info("timestamp="+timestamp);
           logger.info("nonce="+nonce);
           logger.info("echostr="+echostr);
           // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
           if (signature != null && WeixinService.checkSignature(signature, timestamp, nonce)) {
               try {
                   print = response.getWriter();
                   print.write(echostr);
                   print.flush();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       } else {
    	   try {
	       		String strAccept = IOUtils.toString(request.getInputStream(),"utf-8");
	       		String strReceive = WeixinService.dealMsg(strAccept);
	       		response.setContentType("text/html;charset=UTF-8");
	       		PrintWriter pw = response.getWriter();
	       		pw.write(strReceive);
	       		pw.flush();
       		} catch (IOException e) {
				//e.printStackTrace();
				logger.error(e.toString());
			}
       }
   }
}

