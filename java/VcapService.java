import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

public class VcapService {
        //test comment
	private static Logger logger =  Logger.getLogger(VcapService.class.getName());

	public VcapService() {
		processVCAP_Services("alchemy_api");
	}
	
	
	private JSONObject getVcapServices(){
		String envServices = System.getenv("VCAP_SERVICES");
		if (envServices == null)
			return null;
		
		JSONObject sysEnv = null;
		try{
			sysEnv = JSONObject.parse(envServices);
		} catch(IOException e){
			// Do nothing, fall through to defaults
			logger.log(Level.SEVERE, "Error parsing VCAP_SERVICES: " + e.getMessage(), e);
		}
		
		return sysEnv;
		
	}
	
	private void processVCAP_Services(String serviceName){

		String uri;
		
		logger.info("Processing VCAP_SERVICES");
		JSONObject sysEnv = getVcapServices();
		
		if (sysEnv == null) 
			return;
		
		logger.info("Looking for: " + serviceName);
		
		for(Object key : sysEnv.keySet()){
			String keyString = (String) key;
			logger.info("found key: " + key);
			if(keyString.startsWith(serviceName)){
				JSONArray services = (JSONArray)sysEnv.get(key);
				JSONObject service = (JSONObject)services.get(0);
				JSONObject credentials = (JSONObject)service.get("credentials");
				
				logger.info(credentials.toString());

				uri = (String) credentials.get("uri");
				logger.info("uri = " + uri);
				
				//will vary depending on the type of service
//                              uri = (String) credentials.get("uri");
//				apiKey =  (String) credentials.get("apikey");
//				baseURL = (String) credentials.get("url");
//				username = (String) credentials.get("username");
//				password = (String) credentials.get("password");
				
			}
			else{
				logger.info("Doesn't match /^" + serviceName);
			}
		}
	}

}
