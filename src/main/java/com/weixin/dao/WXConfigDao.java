package com.weixin.dao;

import com.weixin.entity.WXConfigNode;

public interface WXConfigDao {
	public WXConfigNode selectByPrimaryKey(Integer id);
	public int updateByPrimaryKey(WXConfigNode record);
}
