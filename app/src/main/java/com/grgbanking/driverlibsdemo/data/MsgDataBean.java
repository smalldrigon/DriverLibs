package com.grgbanking.driverlibsdemo.data;


import com.xuhao.didi.core.iocore.interfaces.ISendable;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * Created by Tony on 2017/10/24.
 */

public class MsgDataBean implements ISendable {
    private String content = "";

    public MsgDataBean(String content) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", 14);
            jsonObject.put("data", content);
            content = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.content = content.toString();
     }

    @Override
    public byte[] parse() {
        byte[] body = content.getBytes(Charset.defaultCharset());
        ByteBuffer bb = ByteBuffer.allocate(4 + body.length);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putInt(body.length);
        bb.put(body);
        return bb.array();
    }
}
