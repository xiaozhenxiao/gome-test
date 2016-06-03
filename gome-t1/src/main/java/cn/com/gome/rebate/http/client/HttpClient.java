package cn.com.gome.rebate.http.client;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpClient {

	public static void main(String[] args) {
		CloseableHttpClient client = HttpClientBuilder.create().build();
         
         
         try {
              
             String param = "{\"accountId\":\"22222\",\"accountNick\":\"账户昵称自测\",\"date\":\"2015-08-17 15:29:58\",\"merchantId\":\"11111\",\"merchantName\":\"商家名称自测\",\"recommendCode\":\"123456\"}";
              
            StringEntity entity = new StringEntity(param, "UTF-8");
            HttpPost request = new HttpPost("http://10.128.31.5:9090/man/merchant/settled");
            request.addHeader(HttpHeaders.ACCEPT,"application/json");
            request.setEntity(entity);
            HttpResponse response = client.execute(request);
             
            int status = response.getStatusLine().getStatusCode();
            assert status == 200;
            String bodyAsString = EntityUtils.toString(response.getEntity());
            System.out.println("bodyAsString:" + bodyAsString);
             
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
