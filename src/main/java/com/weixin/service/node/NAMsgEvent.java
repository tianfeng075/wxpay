package com.weixin.service.node;

import com.util.ComUtil;

public class NAMsgEvent extends NMsgBase{

	private String Event;
	private String EventKey;
	public NAMsgEvent() {
		super(true);
	}
	
	
	public String getEvent() {
		return Event;
	}


	public void setEvent(String event) {
		Event = event;
	}


	public String getEventKey() {
		return EventKey;
	}


	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}


	public boolean toCD() {
		if (!isCD) {
			String tmpEvent = ComUtil.toCDATA(Event);
			String tmpEventKey = ComUtil.toCDATA(EventKey);
			if (tmpEvent==null || tmpEventKey==null)
				return false;
			if (!super.toCD())
				return false;
			Event = tmpEvent;
			EventKey = tmpEventKey;
		}
		return true;
	}
	public boolean fromCD() {
		if (isCD) {
			String tmpEvent = ComUtil.fromCDATA(Event);
			String tmpEventKey = ComUtil.fromCDATA(EventKey);
			if (tmpEvent==null || tmpEventKey==null)
				return false;
			if (!super.fromCD())
				return false;
			Event = tmpEvent;
			EventKey = tmpEventKey;
		}
		return true;
	}
}
