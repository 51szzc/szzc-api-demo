package demo;

import com.alibaba.fastjson.JSON;
import demo.util.SignHelper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lx on 17-8-2.
 * 本demo仅提示用户如何生成签名sign
 */
public class ApiDemo {

    public static void main(String[] args) throws Exception {
        String apiKey = "df48efc9d9184a61b4e54118207d25e7";
        String secretKey = "0d3e42c26fba41c9ac216736a76a9adf";
        Map<String,String> map = new TreeMap<>();
        map.put("api_key",apiKey);
        map.put("nonce",String.valueOf(System.currentTimeMillis()));
        String sign = SignHelper.getSign(map,secretKey);
        map.put("sign",sign);
        String result = post("https://www.51szzc.com/api/v1/account_info", JSON.toJSONString(map));
        System.out.println("请求结果：" + result);
    }

    public static String post(String url,String data) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(data);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        if(responseCode != 200){
            return "{\"result\":null,\"error\":"+responseCode+",\"id\":1}";
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        inputLine = in.readLine();
        response.append(inputLine);
        in.close();
        return response.toString();
    }
}
