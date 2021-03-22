package com.ruoyi.common.websocket;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;


@Component
@ServerEndpoint("/websocket/{userId}")
//此注解相当于设置访问URL
public class WebSocket {
    
    private Session session;
    
    private static CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    private static Map<String,Session> sessionPool = new HashMap<String,Session>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value="userId")String userId) {
        try {
			this.session = session;
            webSockets.add(this);
            sessionPool.put(userId, session);
            System.out.println("【websocket消息】有新的连接，总数为:"+ webSockets.size());


		} catch (Exception e) {
		}
    }
    
    @OnClose
    public void onClose() {
        try {
			webSockets.remove(this);
            System.out.println("【websocket消息】连接断开，总数为:"+ webSockets.size());
		} catch (Exception e) {
		}
    }
    
    @OnMessage
    public void onMessage(String message) {
      try{

          System.out.println("【websocket消息】收到客户端消息:"+message);
          JSONObject recvJson = JSONObject.parseObject(message);

          String chartName = (String) recvJson.get("chartName");

          File jsonFile = new File("data/"+chartName+".json");
          FileReader fileReader = new FileReader(jsonFile);
          Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
          int ch = 0;
          StringBuffer sb = new StringBuffer();
          while ((ch = reader.read()) != -1) {
              sb.append((char) ch);
          }
          fileReader.close();
          reader.close();
          String chartJsonStr = sb.toString();
          JSONArray chartJson=(JSONArray) JSONObject.parse(chartJsonStr);
          recvJson.put("data",chartJson);

          System.out.println("【websocket消息】发送消息 :"+recvJson.toJSONString());
          this.sendAllMessage(recvJson.toJSONString());
      }catch (Exception e){

          System.out.println(e);
      }


    }
    
    // 此为广播消息
    public void sendAllMessage(String message) {
        System.out.println("【websocket消息】广播消息:"+message);
        System.out.println("wesocket客户端连接数为："+ webSockets.size());
        for(WebSocket webSocket : webSockets) {
            try {
            	if(webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
            	}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // 此为单点消息
    public void sendOneMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null&&session.isOpen()) {
            try {
                System.out.println("【websocket消息】 单点消息:"+message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // 此为单点消息(多人)
    public void sendMoreMessage(String[] userIds, String message) {
    	for(String userId:userIds) {
    		Session session = sessionPool.get(userId);
            if (session != null&&session.isOpen()) {
                try {
                    System.out.println("【websocket消息】 单点消息:"+message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    	}
        
    }
    
}