package com.grgbanking.driverlibs;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.net.SocketFactory;

/**
 * Author: gongxiaobiao
 * Date: on 2020/5/9 13:58
 * Email: 904430803@qq.com
 * Description:
 */
public class JavaTest {

    public static String sendGet(String url) {
        String result = "";
        String urlName = url;
        try {
            URL realURL = new URL(urlName);
            URLConnection conn = realURL.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
            conn.connect();
            Map<String, List<String>> map = conn.getHeaderFields();
            for (String s : map.keySet()) {
                System.out.println(s + "-->" + map.get(s));
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendPost(String url,String param){
        String result = "";
        HttpURLConnection conn = null;
        try {
            URL realUrl = new URL(url);
            conn =    (HttpURLConnection) realUrl.openConnection();

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            conn.setInstanceFollowRedirects(true);

            conn.setInstanceFollowRedirects(false);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //post设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);//这一行必须要写，因为要将参数放在outputStream中输出
            //PrintWriter out = new PrintWriter(conn.getOutputStream());
            //out.print(param);
            PrintWriter out =new PrintWriter(conn.getOutputStream());
            //out.write(param);
            out.print(param);  //写入缓存
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));// 发送请求参数
            String line;
            while((line = in.readLine()) != null){
                result +="\n" + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


       /**
     * 获取网络上的文件，下载并保存
     *
     * @param urlString
     *            网址
     * @param file
     *            被保存到本地文件
     * @throws Exception
     */
    public static void saveUrlFile(String urlString, String file)
            throws Exception
    {
        File toFile = new File(file);
        if (toFile.exists())
        {
            return;
        }
        toFile.createNewFile();
        FileOutputStream outImgStream = new FileOutputStream(toFile);
        outImgStream.write(getUrlFileData(urlString));
        outImgStream.close();
    }

    /**
     * 获取链接地址的数据，以byte数组方式返回
     *
     * @param urlString
     *            链接地址
     * @return byte数组
     * @throws Exception
     */
    public static byte[] getUrlFileData(String urlString) throws Exception
    {
        URL url = new URL(urlString);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.connect();
        InputStream cin = httpConn.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = cin.read(buffer)) != -1)
        {
            outStream.write(buffer, 0, len);
        }
        cin.close();
        byte[] fileData = outStream.toByteArray();
        outStream.close();
        return fileData;
    }

    /**
     * 获取链接地址的字符数据
     *
     * @param urlStr
     *            链接地址
     * @param
     *
     * @return 字符串数据
     * @throws Exception
     */
    public static String getUrlDetail(String urlStr, boolean withSep)
            throws Exception
    {
        URL url = new URL(urlStr);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.connect();
        InputStream cin = httpConn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(cin,
                "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String rl = null;
        while ((rl = reader.readLine()) != null)
        {
            if (withSep)
            {
                sb.append(rl).append(System.getProperty("line.separator"));
            } else
            {
                sb.append(rl);
            }
        }
        return sb.toString();
    }




    @Test
    public void test() {
        String param = "Search%2FAirlineMode=false&Search%2FcalendarCacheSearchDays=60&Search%2FcalendarSearched=false&dropOffLocationRequired=false&Search%2FsearchType=F&searchTypeValidator=F&xSellMode=false&Search%2FflightType=oneway&destinationLocationSearchBoxType=L&Search%2FisUserPrice=1&Search%2FOriginDestinationInformation%2FOrigin%2Flocation=CITY_BJS_CN&Search%2FOriginDestinationInformation%2FOrigin%2Flocation_input=%E5%8C%97%E4%BA%AC&Search%2FOriginDestinationInformation%2FDestination%2Flocation=CITY_CTU_CN&Search%2FOriginDestinationInformation%2FDestination%2Flocation_input=%E6%88%90%E9%83%BD&Search%2FDateInformation%2FdepartDate_display=2018-09-06&Search%2FDateInformation%2FdepartDate=2018-09-06&Search%2FDateInformation%2FreturnDate=2018-09-05&Search%2FcalendarSearch=false&Search%2FPassengers%2Fadults=1&Search%2FPassengers%2Fchildren=0&Search%2FpromotionCode=";
        //String param = "";
        String utl = "https://www.ixigua.com/i6830711268693806351/";
        String sendRecvPost =sendPost(utl,null);
        String getRecvPost =sendGet(utl);
        System.out.println(sendRecvPost);
        System.out.println("=============");
        System.out.println(getRecvPost);


    }


    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }


    @Test
   public void test11() throws IOException {

            //创建一个url实例
            Document doc= Jsoup.connect("https://www.ixigua.com/i6830711268693806351")
//            Document doc= Jsoup.connect("https://m.toutiaoimg.cn/group/6830711268693806351")
//                    .timeout(4000).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36").get();
                    .timeout(4000).userAgent("Mozilla/5.0 (Linux; Android 6.0; Custom Device_1 Build/MRA58K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36").get();
//            Document doc= Jsoup.connect("https://m.toutiaoimg.cn/group/6830711268693806351/?app=news_article&timestamp=1590457455").timeout(4000).userAgent("Mozilla").get();
//            Document doc= Jsoup.parse(new File("C:\\Users\\lenovo\\Desktop\\test.html"),"UTF-8");
            System.out.println(doc);
            System.out.println(doc.title());
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");
        Elements id = doc.select("#vs");
        print("\nid", id.size());
        print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img"))
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            else
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        }

        print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
        }

        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }




}

}
   abstract class A{
        abstract void testopen();

       void test() {
           System.out.println("A");
       }

   }
   class B extends A{

       @Override
       void testopen() {
           System.out.println("Bopen");

       }
       void test(){
           System.out.println("B");

       }
   }

