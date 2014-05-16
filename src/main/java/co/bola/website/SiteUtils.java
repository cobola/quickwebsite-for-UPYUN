package co.bola.website;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

/**
 * Created by xiangxiang on 14-5-15.
 */
public class SiteUtils {


    public final static String HUIHUI = "http://www.huihui.cn";


    @Test
    public void updateHuiHui() {


        //先拿到json

        UpYun upyun = new UpYun(Constants.UP_BUCKET_NAME, Constants.UP_USER_NAME, Constants.UP_USER_PWD);


        upyun.deleteFile("index.json");
        String jstr = upyun.readFile("index.json");



        JSONObject jsonObject = JSON.parseObject(jstr);

        HashSet<JSONObject> hs = new HashSet<JSONObject>();

        if (jsonObject != null) {

            JSONArray array = jsonObject.getJSONArray("items");

            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = array.getJSONObject(i);
                hs.add(obj);

            }

        }

        try {
            Document doc =
                    Jsoup.parse(new URL(HUIHUI), 3000);

            Elements list = doc.select(".hui-content a[href~=(http|\\/pro)]");

            for (Element e : list) {
                //这个element 创建一个文档

                String href = e.attr("href");
                String id = DigestUtils.md5Hex(href);

                JSONObject obj = new JSONObject();
                obj.put("id", id);
                obj.put("title", e.text());
                if (href.startsWith("http")) {
                    obj.put("href", href);
                } else {
                    obj.put("href", HUIHUI + e.attr("href"));
                }
                obj.put("content",e.text());
                hs.add(obj);

            }

            JSONArray result = new JSONArray();

            for (JSONObject o : hs) {
                result.add(o);
            }

            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("items", result);

            boolean de = upyun.writeFile("index.json", jsonObject1.toJSONString());
            System.out.println(de);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Test
    public void putFiles() {

        UpYun upyun = new UpYun(Constants.UP_BUCKET_NAME, Constants.UP_USER_NAME, Constants.UP_USER_PWD);

        try {
            upyun.writeFile("index.html", new File("/Users/xiangxiang/javacode/quickwebsite-for-UPYUN/src/main/resources/template/index.html"));
            upyun.writeFile("about.html", new File("/Users/xiangxiang/javacode/quickwebsite-for-UPYUN/src/main/resources/template/about.html"));

            upyun.writeFile("contact.html", new File("/Users/xiangxiang/javacode/quickwebsite-for-UPYUN/src/main/resources/template/contact.html"));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
