package com.amazon.websocket.client;

import java.net.URI;
// import com.alibaba.fastjson.JSONObject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * websocket客户端监听类
 * 
 * @author 。
 */
public class BaseWebsocketClient extends WebSocketClient {

	private static Logger logger = LoggerFactory.getLogger(BaseWebsocketClient.class);

	public BaseWebsocketClient(URI serverUri) {
		super(serverUri);
	}

	@Override
	public void onOpen(ServerHandshake serverHandshake) {
		logger.info(">>>>>>>>>>>websocket open");

	}

	@Override
	public void onMessage(String s) {
		logger.info(">>>>>>>>>> websocket message");
		System.out.println(s);

	}

	@Override
	public void onClose(int i, String s, boolean b) {
		logger.info(">>>>>>>>>>>websocket close");
	}

	@Override
	public void onError(Exception e) {
		logger.error(">>>>>>>>>websocket error {}", e);
	}

	public static void main(String[] args) {
		try {
			BaseWebsocketClient myClient = new BaseWebsocketClient(new URI("wss://4s06qc4m3j.execute-api.ap-northeast-1.amazonaws.com/production?userId=123"));
			myClient.connect();
			while (!myClient.getReadyState().equals(ReadyState.OPEN)) {
				System.out.println("连接中。。。");
				Thread.sleep(1000);
			}
			System.out.println("Connected");
			// 连接成功往websocket服务端发送数据
			JSONObject object = new JSONObject();
			object.put("action", "send");
			object.put("userId", "123");
			object.put("name", "jxl");
			myClient.send(object.toString());
			System.out.println("...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
