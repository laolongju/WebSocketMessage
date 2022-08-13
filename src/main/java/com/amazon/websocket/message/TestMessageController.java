package com.amazon.websocket.message;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.apigateway.ApiGatewayClient;
import software.amazon.awssdk.services.apigatewaymanagementapi.ApiGatewayManagementApiClient;
import software.amazon.awssdk.services.apigatewaymanagementapi.model.PostToConnectionRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;



@RestController
public class TestMessageController {
	 private static final Log log = LogFactory.getLog(TestMessageController.class);
	
	@PostMapping(value = "/send")
	public String sendMessage(@RequestBody Map<String,String> body) {
		JSONObject response = new JSONObject();
		String name=body.get("name");
		response.put("statusCode", 200);
		
		try {
			log.info("body"+body);
			
			String userId = body.get("userId");
			//ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
		    Region region = Region.AP_NORTHEAST_1;
			DynamoDbClient ddb = DynamoDbClient.builder()				
			            .region(region)			            
			            .build();

			String connectionId = this.getConnectionId( ddb, "websocket-user-connection", "user_id", userId);
			String apiPostUri= "https://4s06qc4m3j.execute-api.ap-northeast-1.amazonaws.com/production";
			
			log.info("connection id:"+connectionId);
			ApiGatewayManagementApiClient aipgwClient = ApiGatewayManagementApiClient
					.builder()
					.region(region)
					.endpointOverride(URI.create(apiPostUri))
					.build();
			
			
			PostToConnectionRequest postToConnectionRequest =PostToConnectionRequest.builder()
					.connectionId(connectionId)
					.data(SdkBytes.fromByteArray(("Hello,welcome :"+userId).getBytes()))
					.build();	
			
			aipgwClient.postToConnection(postToConnectionRequest);
			
			log.info("Got hello request from "+name);
			log.debug("Debug get hello request from "+name);
			//log.info(headers);
			
			String localip=InetAddress.getLocalHost().getHostAddress();	
			
			response.put("body", "Hello, "+name+"!, you are visiting "+localip+" V10 version");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.put("body", "Hello:"+name);
		return response.toString();
		
	}
	 @RequestMapping(value = "/")
		public String defaultEcho() {

			JSONObject obj = new JSONObject();
			obj.put("statusCode", 200);
			try {
				//log.info("Got Ping request.");
				String localip=InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			obj.put("body", "Hello Default client");
			//log.info(obj.toString());
			return obj.toString();
			
		}
	 // snippet-start:[dynamodb.java2.get_item.main]
    public String getConnectionId(DynamoDbClient ddb,String tableName,String key,String keyVal ) {

        HashMap<String,AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put(key, AttributeValue.builder()
            .s(keyVal)
            .build());

        GetItemRequest request = GetItemRequest.builder()
            .key(keyToGet)
            .tableName(tableName)
            .build();
        String connectionId = "";

        try {
            Map<String,AttributeValue> returnedItem = ddb.getItem(request).item();
            if (returnedItem != null) {
                Set<String> keys = returnedItem.keySet();
                log.info("Amazon DynamoDB table attributes: \n");               
                for (String key1 : keys) {
                	if(key1.equalsIgnoreCase("connection_id")) {
                		connectionId = returnedItem.get(key1).s();
                		log.info(key1+":"+connectionId);
                	}
                   
                }
            } else {
                log.info("No item found with the key :"+ key);
            }

        } catch (DynamoDbException e) {
            e.printStackTrace();
        }
        return connectionId;
    }

}
