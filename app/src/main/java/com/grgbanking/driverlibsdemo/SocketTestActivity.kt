package com.grgbanking.driverlibsdemo

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.grgbanking.driverlibsdemo.data.HandShakeBean
import com.grgbanking.driverlibsdemo.data.MsgDataBean
import com.xuhao.didi.core.iocore.interfaces.IPulseSendable
import com.xuhao.didi.core.iocore.interfaces.ISendable
import com.xuhao.didi.core.pojo.OriginalData
import com.xuhao.didi.core.utils.SLog
import com.xuhao.didi.socket.client.impl.client.action.ActionDispatcher
import com.xuhao.didi.socket.client.sdk.OkSocket
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager
import com.xuhao.didi.socket.client.sdk.client.connection.NoneReconnect
import com.xuhao.didi.socket.common.interfaces.common_interfacies.server.*
import com.xuhao.didi.socket.server.action.ServerActionAdapter
import com.xuhao.didi.socket.server.impl.OkServerOptions
import kotlinx.android.synthetic.main.layout_sockettest_activity.*
import org.json.JSONObject
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import java.nio.charset.Charset
import java.util.*

class SocketTestActivity : AppCompatActivity(), IClientIOCallback {

var index = 0
    private var mServerManager: IServerManager<*>? = null
    private var mPort = 8080

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_sockettest_activity)
        initSocket()

        OkServerOptions.setIsDebug(true)
        OkSocketOptions.setIsDebug(true)
        SLog.setIsDebug(true)

        btn_openserver.setOnClickListener {
            if (!mServerManager!!.isLive) {
                mServerManager!!.listen()
            } else {
                mServerManager!!.shutdown()
            }
        }

        btn_connect.setOnClickListener {
            initManager()
            if (mManager == null) {
                return@setOnClickListener
            }
            if (!mManager!!.isConnect()) {
                initManager()
                mManager!!.connect()
                et_connect_ip.setEnabled(false)
                et_connect_port.setEnabled(false)
            } else {
                btn_connect.setText("Disconnecting")
                mManager!!.disconnect()
            }
        }


        btn_senddata.setOnClickListener {
            if (mManager == null) {
                return@setOnClickListener
            }
            if (!mManager!!.isConnect()) {
                Toast.makeText(applicationContext, "Unconnected", LENGTH_SHORT).show()
            } else {
                val msg ="123"
                if (TextUtils.isEmpty(msg.trim())) {
                    return@setOnClickListener
                }
                val msgDataBean = MsgDataBean(index++.toString())
                mManager!!.send(msgDataBean)
                et_senddata.setText("")
            }
        }






    }

    fun initSocket() {
        et_host_ip.setText("当前IP(Local Device IP):" + getIPAddress())

        mServerManager = OkSocket.server(mPort).registerReceiver(object : ServerActionAdapter() {
            override fun onServerListening(serverPort: Int) {
                Log.i("ServerCallback", Thread.currentThread().name + " onServerListening,serverPort:" + serverPort)
                flushServerText()
            }

            override fun onClientConnected(client: IClient?, serverPort: Int, clientPool: IClientPool<*, *>?) {
                Log.i(
                    "ServerCallback",
                    Thread.currentThread().name + " onClientConnected,serverPort:" + serverPort + "--ClientNums:" + clientPool!!.size() + "--ClientTag:" + client!!.uniqueTag
                )
                client.addIOCallback(this@SocketTestActivity)
            }

            override fun onClientDisconnected(client: IClient?, serverPort: Int, clientPool: IClientPool<*, *>?) {
                Log.i(
                    "ServerCallback",
                    Thread.currentThread().name + " onClientDisconnected,serverPort:" + serverPort + "--ClientNums:" + clientPool!!.size() + "--ClientTag:" + client!!.uniqueTag
                )
                client.removeIOCallback(this@SocketTestActivity)
            }

            override fun onServerWillBeShutdown(
                serverPort: Int,
                shutdown: IServerShutdown?,
                clientPool: IClientPool<*, *>?,
                throwable: Throwable?
            ) {
                Log.i(
                    "ServerCallback",
                    Thread.currentThread().name + " onServerWillBeShutdown,serverPort:" + serverPort + "--ClientNums:" + clientPool!!
                        .size()
                )
                shutdown!!.shutdown()
            }

            override fun onServerAlreadyShutdown(serverPort: Int) {
                Log.i(
                    "ServerCallback",
                    Thread.currentThread().name + " onServerAlreadyShutdown,serverPort:" + serverPort
                )
                flushServerText()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun flushServerText() {
        if (mServerManager!!.isLive()) {
            Handler(Looper.getMainLooper()).post {
                btn_openserver.text = "$mPort" +
                        "服务器关闭(Local Server Demo in $mPort Stop)"
            }
        } else {
            Handler(Looper.getMainLooper()).post {
                btn_openserver.text = "$mPort" + "服务器启动(Local Server Demo in " + mPort + " Start)"
            }
        }
    }


    override fun onClientRead(originalData: OriginalData, client: IClient, clientPool: IClientPool<IClient, String>) {
        val str = String(originalData.bodyBytes, Charset.forName("utf-8"))
        var jsonObject: JsonObject? = null
        try {
            jsonObject = JsonParser().parse(str).getAsJsonObject()
            val cmd = jsonObject!!.get("cmd").getAsInt()
            if (cmd == 54) {//登陆成功
                val handshake = jsonObject!!.get("handshake").getAsString()
                Log.i("onClientIOServer", Thread.currentThread().name + " 接收到:" + client.hostIp + " 握手信息:" + handshake)
            } else if (cmd == 14) {//心跳
                Log.i("onClientIOServer", Thread.currentThread().name + " 接收到:" + client.hostIp + " 收到心跳")
            } else {
                Log.i("onClientIOServer", Thread.currentThread().name + " 接收到:" + client.hostIp + " " + str)
            }
        } catch (e: Exception) {
            Log.i("onClientIOServer", Thread.currentThread().name + " 接收到:" + client.hostIp + " " + str)
        }

        val msgDataBean = MsgDataBean(str)
        clientPool.sendToAll(msgDataBean)
    }

    override fun onClientWrite(sendable: ISendable, client: IClient, clientPool: IClientPool<IClient, String>) {
        var bytes = sendable.parse()
        bytes = Arrays.copyOfRange(bytes, 4, bytes.size)
        val str = String(bytes, Charset.forName("utf-8"))
        var jsonObject: JsonObject? = null
        try {
            jsonObject = JsonParser().parse(str).getAsJsonObject()
            val cmd = jsonObject!!.get("cmd").getAsInt()
            when (cmd) {
                54 -> {
                    val handshake = jsonObject!!.get("handshake").getAsString()
                    Log.i(
                        "onClientIOServer",
                        Thread.currentThread().name + " 发送给:" + client.hostIp + " 握手数据:" + handshake
                    )
                }
                else -> Log.i("onClientIOServer", Thread.currentThread().name + " 发送给:" + client.hostIp + " " + str)
            }
        } catch (e: Exception) {
            Log.i("onClientIOServer", Thread.currentThread().name + " 发送给:" + client.hostIp + " " + str)
        }

    }

     @SuppressLint("SetTextI18n")
     override fun onResume() {
        super.onResume()
        flushServerText()
        et_host_ip.setText("当前IP(Local Device IP):" + getIPAddress())
        et_host_port.setText("端口$mPort")
    }

    fun getIPAddress(): String {
        val info =
            (applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        if (info != null && info.isConnected) {
            if (info.type == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    val en = NetworkInterface.getNetworkInterfaces()
                    while (en.hasMoreElements()) {
                        val intf = en.nextElement()
                        val enumIpAddr = intf.inetAddresses
                        while (enumIpAddr.hasMoreElements()) {
                            val inetAddress = enumIpAddr.nextElement()
                            if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                                return inetAddress.getHostAddress()
                            }
                        }
                    }
                } catch (e: SocketException) {
                    e.printStackTrace()
                }

            } else if (info.type == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo = wifiManager.connectionInfo
                val ipAddress = wifiInfo.ipAddress
                return if (ipAddress == 0) "未连接wifi" else
                    (ipAddress and 0xff).toString() + "." + (ipAddress shr 8 and 0xff) + "."+(ipAddress shr 16 and 0xff).toString() + "." + (ipAddress shr 24 and 0xff)
            }
        } else {
            //当前无网络连接,请在设置中打开网络
            return "当前无网络连接,请在设置中打开网络"
        }
        return "IP获取失败"
    }





    //==============================客户端设置
    private var mInfo: ConnectionInfo? = null
    private var mOkOptions: OkSocketOptions? = null
    private var mManager: IConnectionManager? = null


    private val adapter = object : SocketActionAdapter() {

        override fun onSocketConnectionSuccess(info: ConnectionInfo?, action: String?) {
            mManager!!.send(HandShakeBean())
            btn_connect.setText("DisConnect")
            et_connect_ip.setEnabled(false)
            et_connect_port.setEnabled(false)
        }

        override fun onSocketDisconnection(info: ConnectionInfo?, action: String?, e: Exception?) {
            if (e != null) {
                logSend("异常断开(Disconnected with exception):" + e.message)
            } else {
                logSend("正常断开(Disconnect Manually)")
            }
            btn_connect.setText("Connect")
            et_connect_ip.setEnabled(true)
            et_connect_port.setEnabled(true)
        }

        override fun onSocketConnectionFailed(info: ConnectionInfo?, action: String?, e: Exception?) {
            logSend("连接失败(Connecting Failed)")
            btn_connect.setText("Connect")
            et_connect_ip.setEnabled(true)
            et_connect_port.setEnabled(true)
        }

        override fun onSocketReadResponse(info: ConnectionInfo?, action: String?, data: OriginalData) {
            val str = String(data.bodyBytes, Charset.forName("utf-8"))
            Log.i("gong","服务器返回$str")
            logRece(str)
        }

        override fun onSocketWriteResponse(info: ConnectionInfo?, action: String?, data: ISendable) {
            val str = String(data.parse(), Charset.forName("utf-8"))
            logSend(str)
        }

        override fun onPulseSend(info: ConnectionInfo?, data: IPulseSendable) {
            val str = String(data.parse(), Charset.forName("utf-8"))
            logSend(str)
        }
    }


    private fun logSend(log: String) {
        setClientResponse(log)
//        if (Looper.myLooper() == Looper.getMainLooper()) {
//            val logBean = LogBean(System.currentTimeMillis(), log)
//            mSendLogAdapter.getDataList().add(0, logBean)
//            mSendLogAdapter.notifyDataSetChanged()
//        } else {
//            val threadName = Thread.currentThread().name
//            Handler(Looper.getMainLooper()).post { logSend("$threadName 线程打印(In Thread):$log") }
//        }
    }

    private fun initManager() {
        val handler = Handler()
        mInfo = ConnectionInfo(et_connect_ip.getText().toString(), Integer.parseInt(et_connect_port.getText().toString()))
        mOkOptions = OkSocketOptions.Builder()
            .setReconnectionManager(NoneReconnect())
            .setConnectTimeoutSecond(10)
            .setCallbackThreadModeToken(object : OkSocketOptions.ThreadModeToken() {
                override fun handleCallbackEvent(runnable: ActionDispatcher.ActionRunnable) {
                    handler.post(runnable)
                }
            })
            .build()
        mManager = OkSocket.open(mInfo).option(mOkOptions)
        Log.d("gong",(mManager==null).toString())
        mManager!!.registerReceiver(adapter)
    }


    private fun logRece(log: String) {
        setServerReceiver(log)
//        if (Looper.myLooper() == Looper.getMainLooper()) {
//            val logBean = LogBean(System.currentTimeMillis(), log)
//            mReceLogAdapter.getDataList().add(0, logBean)
//            mReceLogAdapter.notifyDataSetChanged()
//        } else {
//            val threadName = Thread.currentThread().name
//            Handler(Looper.getMainLooper()).post { logRece("$threadName 线程打印(In Thread):$log") }
//        }

    }

    fun setServerReceiver(str:String){
        tv_received_client.text = str
    }
    fun setClientResponse(str:String){
        tv_received_server.text = str
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mManager != null) {
            mManager!!.disconnect()
            mManager!!.unRegisterReceiver(adapter)
        }
    }
}
