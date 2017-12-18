package com.noway.netutils.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;


/**
 * 描述:    TODO   网络判断 、运营商 、手机IP ---- 工具类
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2017/12/6
 * 版本:    V-1.0.0
 */
public class NetworkUtils {

    /**
     * 判断当前网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            return mNetworkInfo != null && mNetworkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 判断Wifi网络是否可用
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return mWiFiNetworkInfo != null && mWiFiNetworkInfo.isConnected();
        }

        return false;
    }

    /**
     * 判断Mobile网络是否可用
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return mMobileNetworkInfo != null && mMobileNetworkInfo.isConnected();
        }

        return false;
    }


    /**
     * 判断当前是否为GPRS网络环境
     */
    public static boolean isGPRSNetwork(Context context) {
        return isMobileConnected(context) && !isWifiConnected(context);
    }
    /**
     * @Title  getIntenetType
     * @Description 获取网络类型  1-3G；2-4G；3-wifi 4-其他
     * @param   context
     * @return  1-3G；2-4G；3-wifi 4-其他
     * @throw
     */
    public static int getIntenetType(Context context){
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();

        if (info==null){return 4;}
        if (info.getType()==ConnectivityManager.TYPE_WIFI){
            return 3;
        }else if (info.getType()==ConnectivityManager.TYPE_MOBILE){
            switch (info.getSubtype()){
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return 4;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return 1;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return 2;
                default:
                    return 4;
            }
        }

        return 4;
    }
    /**
     * @Title getProvideType
     * @Description 获取运营商类型
     * @param context
     * @return 1-移动、2-联通，3-电信 4-其他
     * @throw
     */
    @SuppressLint("MissingPermission")
    public static int getOperatorsType(Context context){
        TelephonyManager tmanager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI;// 返回唯一的用户ID;就是这张卡的编号神马的
        IMSI = tmanager.getSubscriberId();
        if (IMSI==null){
            return 4;
        }else{
            /**
             * IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信
             */
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                return 1;
            } else if (IMSI.startsWith("46001")) {
                return 2;
            } else if (IMSI.startsWith("46003")) {
                return 3;
            }
        }
        return 4;
    }
    /**
     * 获取手机IP
     * @return
     */
    public static  String getPhoneIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

