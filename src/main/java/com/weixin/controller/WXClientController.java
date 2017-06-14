package com.weixin.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.util.ComUtil;
import com.weixin.Dao;
import com.weixin.WeixinClient;
import com.weixin.client.node.WeixinMenuClickNode;
import com.weixin.client.node.WeixinMenuNode;
import com.weixin.client.node.WeixinMenuViewNode;
import com.weixin.client.node.WeixinSubMenuNode;
import com.weixin.entity.WXConfigNode;

@Controller
public class WXClientController {
	private static final Logger logger = LoggerFactory.getLogger(WXClientController.class);
	
	/**
	 * 用来处理前台的login请求
	 * @param username
	 * @param password
	 * @return String
	 */
	@RequestMapping("login.do")
	public @ResponseBody String login(
			@RequestParam(value = "username", required = false)String username,
			@RequestParam(value = "password", required = false)String password
			){
		
		WXConfigNode node = Dao.getWXConfig().selectByPrimaryKey(1);
		System.out.println(node);
		logger.warn("username:"+username+",password:"+password);
		return "你好:"+username+",你的密码是:"+password;
	}
	
	/**
	 * 获取微信IP
	 * @return
	 */
	@RequestMapping("wxip.do")
	public @ResponseBody String wxip() {
		String result = WeixinClient.getWXIP();
		if (result==null)
			return "fail";
		return result;
	}

	/**
	 * 删除菜单
	 * @return
	 */
	@RequestMapping("delmenu.do")
	public @ResponseBody String delMenu() {
		if (!WeixinClient.menuDelete())
			return "fail";
		return "success";
	}
	
	/**
	 * 重置菜单
	 * @return
	 */
	@RequestMapping("addmenu.do")
	public @ResponseBody String addMenu() {
		if (!WeixinClient.menuDelete())
			return "fail";
		if (!WeixinClient.menuAdd(getData()))
			return "fail";
		return "success";
	}
	
	/**
	 * 获取新增菜单数据
	 * @return
	 */
	private static String getData() {
		WeixinMenuNode menu = new WeixinMenuNode();
		List<Object> list = new ArrayList<Object>();
		menu.setButton(list);
		
		WeixinMenuClickNode clickNode = new WeixinMenuClickNode();
		clickNode.setType("click");
		clickNode.setName("今日歌曲");
		clickNode.setKey("V1001_TODAY_MUSIC");
		list.add(clickNode);
		
		WeixinSubMenuNode subMenu = new WeixinSubMenuNode();
		list.add(subMenu);
		
		subMenu.setName("菜单");
		List<Object> subList = new ArrayList<Object>();
		subMenu.setSub_button(subList);
		
		
		WeixinMenuViewNode viewNode1 = new WeixinMenuViewNode();
		viewNode1.setType("view");
		viewNode1.setName("搜索");
		viewNode1.setUrl("http://www.soso.com/");
		subList.add(viewNode1);
		
		WeixinMenuViewNode viewNode2 = new WeixinMenuViewNode();
		viewNode2.setType("view");
		viewNode2.setName("测试");
		viewNode2.setUrl("http://test-user.ipaye.cn/wxpay/");
		subList.add(viewNode2);
		
		WeixinMenuClickNode clickNode2 = new WeixinMenuClickNode();
		clickNode2.setType("click");
		clickNode2.setName("赞一下我们");
		clickNode2.setKey("V1001_GOOD");
		subList.add(clickNode2);
		
		return ComUtil.toJson(menu);
	}
}
