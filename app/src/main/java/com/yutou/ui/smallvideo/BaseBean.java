package com.yutou.ui.smallvideo;

import java.io.Serializable;

/**
 * 描    述：视频实体类
 * 创 建 人：ZJY
 * 创建日期：2017/6/22 10:21
 * 修订历史：
 * 修 改 人：
 */

public class BaseBean implements Serializable {
    private String url;
    private boolean isvideo;
    private String videosize;
    private String videotime;

    public String getVideotime() {
        return videotime;
    }

    public void setVideotime(String videotime) {
        this.videotime = videotime;
    }

    private boolean isCheckBox = false;

    public boolean isCheckBox() {
        return isCheckBox;
    }

    public void setCheckBox(boolean checkBox) {
        isCheckBox = checkBox;
    }

    public String getVideosize() {
        return videosize;
    }

    public void setVideosize(String videosize) {
        this.videosize = videosize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isvideo() {
        return isvideo;
    }

    public void setIsvideo(boolean isvideo) {
        this.isvideo = isvideo;
    }
}
