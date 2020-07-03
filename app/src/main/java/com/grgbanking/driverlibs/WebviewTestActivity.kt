package com.grgbanking.driverlibs

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import com.grgbanking.baselibrary.util.SystemUtils
import com.grgbanking.huitong.driver_libs.util.FileUtil
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_webview_activity.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class WebviewTestActivity : AppCompatActivity() {

    var data: ArrayList<String>? = arrayListOf()
    var disposedCopyfIle: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_webview_activity)
        data = intent?.getStringArrayListExtra("data")
        print("size:")
        println(data!!.size)

        initView()


    }


    var index = 0
    private fun initView() {


        var webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(JsInterface(mHandler), "JsInterface");
        if (data!!.isNotEmpty()) {
            webview.loadUrl(data!!.get(index));
        } else {
            webview.loadUrl("https://m.toutiaoimg.cn/group/6833241905439100174/?app=news_article&timestamp=1590989502");
        }

        webview.setWebViewClient(object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                view?.loadUrl("javascript:window.JsInterface.showSource('<head>'+" + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");


                var js = "document.execCommand('selectall');" +
                        "var txt;" +
                        "if (window.getSelection) {" +
                        "txt = window.getSelection().toString();" +
                        "} else if (window.document.getSelection) {" +
                        "txt = window.document.getSelection().toString();" +
                        "} else if (window.document.selection) {" +
                        "txt = window.document.selection.createRange().text;" +
                        "}" +
                        "var charactersets = document.characterSet;" +
                        "JsInterface.getHtmlSource(txt,charactersets);";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webview.evaluateJavascript("javascript:" + js, null);
                    Log.i("gong", "evaluateJavascript-javascript");
                } else {
                    webview.loadUrl("javascript:" + js);
                    Log.i("gong", "loadUrl-javascript");
                }
                Log.i("gong", webview.title)
            }

        })

        println("useAgent${webview.settings.userAgentString}")
    }

      val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {

                1 -> {
                    index++
                    if (index<data!!.size)
                        webview.loadUrl(data!![index])
                        println("收到完成通知")

                }
            }

        }
    }
    internal class JsInterface(h:Handler) {

        var mHtml = ""
        var mCharactersets = ""
        var mHandler: Handler  ?=null
        @JavascriptInterface
        fun showSource(html: String?) {
            getHtmlContent(html!!)
        }

        init {
            this.mHandler = h
        }

        @JavascriptInterface
        fun getHtmlSource(html: String, charactersets: String) {
            Log.i("gong", "getHtmlSource==$html")
            mHtml = html
            mCharactersets = charactersets
             saveHtml(html,charactersets)
        }

        /**
         * 获取内容
         * @param html
         */
        private fun getHtmlContent(html: String) {
            Log.d("LOGCAT", "网页内容:$html")
            val document: Document = Jsoup.parse(html)

            val id = document.select("source").first()
            var titleh1 = document.select("h1")

//            val arr = id.attr("src")
            val title = titleh1.text()
            val src = id.attr("src")
            println("标题$title")
            println("链接$src")

            dowmload(title, src)
            //通过类名获取到一组Elements，获取一组中第一个element并设置其html
//                Elements elements = document.getElementsByClass("loadDesc");
//                elements.get(0).html("<p>test</p>");
//通过ID获取到element并设置其src属性
//                Element element = document.getElementById("imageView");
//                element.attr("src","file:///test/dragon.jpg");
//通过meta标签的name获得其内容
//            var pageDescription = document.select("meta[name=description]").get(0).attr("content")
//            Log.d("gong", "description:$pageDescription")
        }
        var f :File?= null
        fun dowmload(fileName: String, source: String) {

            if (f==null){
                f= File(
                "${Environment.getExternalStorageDirectory()}${File.separator}myvideos${File.separator}${SystemUtils.getSystemTime(
                    "yyyy:MM:dd hh:mm:ss"
                )}.txt"
            )}
            val res = FileUtil.write(f!!, "${fileName},${source}<<", true)

            if (res) {
                println("保存成功${f!!.absolutePath}")
                mHandler?.sendEmptyMessageDelayed(1, 1000L)
            }


        }





        @SuppressLint("SimpleDateFormat")
        fun saveHtml(html: String?, charactersets: String?): Boolean {
            println("SaveHtml======================")
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS")
            val name: String = simpleDateFormat.format(Date()).toString() + ".html"
            val file =
                File(Environment.getExternalStorageDirectory().toString() + File.separator + Environment.DIRECTORY_DOWNLOADS + File.separator + name)
            try {
                if (file.exists()) {
                    file.delete()
                }
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                var out: Writer? = null
                out = OutputStreamWriter(FileOutputStream(file.absolutePath, false), charactersets)
                out.write(html)
                out.close()
                Log.i("gong", "saveHtml==" + file.absolutePath)
            } catch (e: java.lang.Exception) {
                return false
            }
            return true
        }

    }
}