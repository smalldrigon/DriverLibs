package com.grgbanking.huitong.driver_libs.gate_machine;

/**
 * Author: gongxiaobiao
 * Date: on 2019/12/20 9:40
 * Email: 904430803@qq.com
 * Description:
 */
public class PassTimeOutBean {
    private long leftTimeOut = -1;
    private long rightTimeOut = -1;
    private boolean isLeftopened = false;

    public boolean isLeftopened() {
        return isLeftopened;
    }

    public void setLeftopened(boolean leftopened) {
        isLeftopened = leftopened;
    }

    public boolean isRightopened() {
        return isRightopened;
    }

    public void setRightopened(boolean rightopened) {
        isRightopened = rightopened;
    }

    private boolean isRightopened = false;
    private boolean isRightopenSuccess = false;

    public boolean isLeftopenSuccess() {
        return isLeftopenSuccess;
    }

    public void setLeftopenSuccess(boolean leftopenSuccess) {
        isLeftopenSuccess = leftopenSuccess;
    }

    public boolean isRightopenSuccess() {
        return isRightopenSuccess;
    }

    public void setRightopenSuccess(boolean rightopenSuccess) {
        isRightopenSuccess = rightopenSuccess;
    }

    private boolean isLeftopenSuccess = false;

    public long getLeftTimeOut() {
        return leftTimeOut;
    }

    public void setLeftTimeOut(long leftTimeOut) {
        this.leftTimeOut = leftTimeOut;
    }

    public long getRightTimeOut() {
        return rightTimeOut;
    }

    public void setRightTimeOut(long rightTimeOut) {
        this.rightTimeOut = rightTimeOut;
    }
}