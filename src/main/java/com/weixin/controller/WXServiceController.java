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

import com.util.ComUtil;
import com.util.WeixinMsg;
import com.weixin.WeixinService;
import com.weixin.node.AcceptMsgNode;
import com.weixin.node.ReceiveMsgNode;

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
           // 微信加密签名
           String signature = request.getParameter("signature");
           // 时间戳
           String timestamp = request.getParameter("timestamp");
           // 随机数
           String nonce = request.getParameter("nonce");
           // 随机字符串
           String echostr = request.getParameter("echostr");
           logger.info("signature="+signature);
           logger.info("nonce="+nonce);
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
       		AcceptMsgNode acceptNode = ComUtil.fromXML(strAccept,AcceptMsgNode.class);
       		
       		String fromMsgType = ComUtil.fromCDATA(acceptNode.getMsgType());
       		String fromContent = ComUtil.fromCDATA(acceptNode.getContent());
       		WeixinMsg msg = WeixinService.dealMsg(new WeixinMsg(fromMsgType,fromContent));
       		String toMsgType;
       		String content;
       		if (msg==null || msg.getMsgType()==null || msg.getContent()==null) {
       			toMsgType = ComUtil.toCDATA("text");
       			content = "fail";
       		} else {
       			toMsgType = ComUtil.toCDATA(msg.getMsgType());
       			content = ComUtil.toCDATA(msg.getContent());
       		}

   		   ReceiveMsgNode receiveNode = new ReceiveMsgNode(acceptNode.getFromUserName(),acceptNode.getToUserName(),acceptNode.getCreateTime(),toMsgType,content);
   		   String strReceive = ComUtil.toXML(receiveNode,ReceiveMsgNode.class);
   		   
   		   response.setContentType("text/html;charset=UTF-8");
   		   PrintWriter pw = response.getWriter();
   		   pw.write(strReceive);
   		   pw.flush();
			} catch (InstantiationException | IllegalAccessException | IOException e) {
				e.printStackTrace();
			}
       }
   }
}

