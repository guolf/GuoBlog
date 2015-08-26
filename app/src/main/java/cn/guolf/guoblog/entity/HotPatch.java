package cn.guolf.guoblog.entity;

/**
 * Author：guolf on 8/26/15 17:32
 * Email ：guo@guolingfa.cn
 * 根据apk版本，从服务器上获取指定补丁apk
 */
public class HotPatch {

    private String apkVersion;
    private String flag;
    private String patchUrl;

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPatchUrl() {
        return patchUrl;
    }

    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }
}
