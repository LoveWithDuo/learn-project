package com.dw.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;


/**
 *
 * @ClassName: PhoneUtil
 * @Description:手机号码归属地工具类
 */
public class PhoneUtil {


    private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    private static PhoneNumberToCarrierMapper carrierMapper = PhoneNumberToCarrierMapper.getInstance();

    private static PhoneNumberOfflineGeocoder geocoder = PhoneNumberOfflineGeocoder.getInstance();

    /**
     * 根据国家代码和手机号  判断手机号是否有效
     * @param phoneNumber
     * @param countryCode
     * @return
     */
    public static boolean checkPhoneNumber(String phoneNumber, String countryCode){
        int ccode = Integer.parseInt(countryCode);
        long phone = Long.parseLong(phoneNumber);
        PhoneNumber pn = new PhoneNumber();
        pn.setCountryCode(ccode);
        pn.setNationalNumber(phone);
        return phoneNumberUtil.isValidNumber(pn);

    }

    /**
     * 根据国家代码和手机号  判断手机运营商
     * @param phoneNumber
     * @param countryCode
     * @return
     */
    public static String getCarrier(String phoneNumber, String countryCode){
        int ccode = Integer.parseInt(countryCode);
        long phone = Long.parseLong(phoneNumber);
        PhoneNumber pn = new PhoneNumber();
        pn.setCountryCode(ccode);
        pn.setNationalNumber(phone);
        //返回结果只有英文，自己转成成中文
        String carrierEn = carrierMapper.getNameForNumber(pn, Locale.ENGLISH);
        String carrierZh = "";
//        carrierZh += geocoder.getDescriptionForNumber(pn, Locale.CHINESE);
        switch (carrierEn) {
            case "China Mobile":
                carrierZh = "移动";
                break;
            case "China Unicom":
                carrierZh = "联通";
                break;
            case "China Telecom":
                carrierZh = "电信";
                break;
            default:
                break;
        }
        return carrierZh;
    }


    /**
     *
     * @Description: 根据国家代码和手机号  手机归属地
     * @param @param phoneNumber
     * @param @param countryCode
     * @param @return    参数
     * @throws
     */
    public static String getGeo(String phoneNumber, String countryCode){
        int ccode = Integer.parseInt(countryCode);
        long phone = Long.parseLong(phoneNumber);
        PhoneNumber pn = new PhoneNumber();
        pn.setCountryCode(ccode);
        pn.setNationalNumber(phone);
        return geocoder.getDescriptionForNumber(pn, Locale.CHINESE);
    }


    /**
     *
     * @Title: getPhoneRegionCode
     * @Description: 得到手机的归宿地编码
     * @return String    返回类型
     * @throws
     */
    public static String getPhoneRegionCode(String phoneNumber, String countryCode){
        String areaName=getGeo(phoneNumber,countryCode);
        if(StringUtils.isEmpty(areaName)){
            return "";
        }
        if(areaName.length()<3){
            return "";
        }
        return areaName;
    }



    public static void main(String[] args) {
//        String location = getPhoneRegionCode("15212150017","86");
//        System.out.println(location);
//        String operator = getCarrier("15212150017", "86");
//        System.out.println(operator);
        String s = HttpUtil.sendGet("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm","tel=15212150017");
        String substring = s.substring(s.indexOf("{"));
        JSONObject json = JSONObject.parseObject(substring);
        System.out.println(json.toJSONString());
        String province = json.getString("province");
        String catName = json.getString("catName");
        if (StringUtils.isNotEmpty(catName)){
            catName = catName.substring(catName.length() - 2);
        }
        System.out.println(province+","+catName);
    }

}