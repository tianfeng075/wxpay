package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class XmlUtil {
	private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);
	
	private static Gson gson = new Gson();
	
	public static <T> T fromJson(String str, Class<T> c) {
		return gson.fromJson(str, c);
	}
	
	public static <T> String toJson(T t) {
		return gson.toJson(t);
	}
	
	/**
	* 将xml转换为Map
	* @param xml
	* * @return
	* @throws Exception
	*/
	public static Map<String, Object> xml2Map(String xml) {
		Map<String, Object> mapRs = null;
		try {
			Document xmlDoc = DocumentHelper.parseText(xml);
			mapRs = new HashMap<String, Object>();
			if (xmlDoc == null)
				return mapRs;

			Element root = xmlDoc.getRootElement();
			for (Iterator<?> iterator = root.elementIterator(); iterator.hasNext();) {
				Element e = (Element) iterator.next();
				List<?> list = e.elements();
				if (list.size() > 0) {
					mapRs.put(e.getName(), Dom2Map(e,mapRs));
				} else {
					mapRs.put(e.getName(), e.getText());
				}
			}
		} catch (DocumentException e1) {
			//e1.printStackTrace();
			logger.error(e1.toString());
		}
		
		return mapRs;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> Dom2Map(Element e,Map<String, Object> map){
		List<?> list = e.elements();
		if(list.size() > 0){
			for (int i = 0;i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List<Object> mapList = new ArrayList<Object>();
				if (iter.elements().size() > 0) {
					Map<String, ?> m = Dom2Map(iter,map);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = new ArrayList<Object>();
							mapList.add(obj);
							mapList.add(m);
						}
						
						if (obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = (List<Object>) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else {
						map.putAll(m);
					}
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = new ArrayList<Object>();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = (List<Object>) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else {
						map.put(iter.getName(), iter.getText());
					}
				}
			}
		} else {
			map.put(e.getName(), e.getText());
		}
		return map;
	}
}
