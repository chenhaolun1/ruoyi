package com.ruoyi.common.websocket;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("webSocketApi")
public class WebSocketApiController {

    @Autowired
    private WebSocket webSocket;

    @PostMapping("/sendAll")
    public AjaxResult sendAll(@RequestBody String msg) {
    	JSONObject message = JSONObject.parseObject(msg);
    	JSONObject obj = new JSONObject();
    	obj.put("cmd", "topic");
		obj.put("msgId", "M0001");
		obj.put("msgTxt", message);

    	webSocket.sendAllMessage(obj.toJSONString());
        return AjaxResult.success("群发");
    }

    @PostMapping("/sendUser")
    public AjaxResult sendUser(@RequestBody JSONObject jsonObject) {

    	String userId = jsonObject.getString("userId");
    	String message = jsonObject.getString("message");
    	JSONObject obj = new JSONObject();
    	obj.put("cmd", "user");
    	obj.put("userId", userId);
		obj.put("msgId", "M0001");
		obj.put("msgTxt", message);
        webSocket.sendOneMessage(userId, obj.toJSONString());
        return AjaxResult.success("单发");
    }

}