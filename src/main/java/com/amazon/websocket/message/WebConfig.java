package com.amazon.websocket.message;

//import org.springframework.context.annotation.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.springframework.context.annotation.Bean;

//import java.net.URL;
//
//import javax.servlet.Filter;
//
//import com.amazonaws.xray.AWSXRay;
//import com.amazonaws.xray.AWSXRayRecorderBuilder;
//import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
//import com.amazonaws.xray.plugins.EKSPlugin;
//import com.amazonaws.xray.strategy.sampling.LocalizedSamplingStrategy;


//@Configuration
public class WebConfig {
	private static final Log log = LogFactory.getLog(WebConfig.class);
	static {
//		System.setProperty("com.amazonaws.xray.configFile", "/disco/xray-agent.json");
//		  System.setProperty("com.amazonaws.xray.strategy.tracingName", "HelloWolrd");
//		  System.setProperty("com.amazonaws.xray.emitters.daemonAddress", "udp:xray-service.default:2000 tcp:xray-service.default:3000");
//	    AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard().withPlugin(new EKSPlugin()).withPlugin(new EKSPlugin());
//
//	    URL ruleFile = WebConfig.class.getResource("sampling-rules.json");
//	    log.info("rule file path:"+ruleFile.getFile());
//	    builder.withSamplingStrategy(new LocalizedSamplingStrategy(ruleFile));
    
	    //AWSXRay.setGlobalRecorder(builder.build());
	  }

  public WebConfig() {
	  
	  
	  System.out.println("web config filter :  ttt");
  }
  //@Bean
//  public Filter TracingFilter() {
//
//	
//	  System.out.println("web config filter :  TracingFilter");
//     //return new AWSXRayServletFilter("HelloWorld");
 // }
}