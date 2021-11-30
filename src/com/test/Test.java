package com.test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.AttributedCharacterIterator;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws Exception {
        String groupseq = "2009";
        System.out.println(groupseq.substring(0,2));
        System.out.println(groupseq.substring(2,4));
        String code = "GRM_502";
        System.out.println(ApiReturnCode.GRM_501.getCode());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String firstStartedAt = sdf.format(new Date((Long.parseLong(String.valueOf("1582641825000")))));

        ArrayList<String> historyArray = new ArrayList<>();
        String temp = historyArray.toString();
        System.out.println("======history Array start");
        temp = temp.replaceAll("[\\[\\]]", "");
        System.out.println("======history Array end");

        String strPhone1 = "010-89650-0320";
        String strPhone2 = "01089650320";
        System.out.println(maskingPhoneNum(strPhone1));
        System.out.println(maskingPhoneNum(strPhone2));

        int firstPayAt = Integer.parseInt(firstStartedAt);
        int expiredAt = 20200107;

        if (expiredAt < firstPayAt) {
            System.out.println("만료됨");
        } else {
            System.out.println("사용가능");
        }

        System.out.println("==========="+firstStartedAt);
        Dog dog = new Dog("흰색", 1);

        System.out.println(dog.hungry());
        System.out.println(dog.barking());
        System.out.println(dog.barking());
        System.out.println(dog.barking());
        System.out.println(dog.barking());
        System.out.println(dog.hungry());

        Dog dog2 = new Dog("검정", 2);

        System.out.println(dog2.getTotal());
        System.out.println(dog2.sleeping());
        System.out.println(dog2.getFood());
        System.out.println(dog2.sleeping(4));
        System.out.println(dog2.getFood());

        System.out.println("=================== TEST DATE CONVERT ===================");
        String utcDate = "2020-03-30 11:33:32";
        String kstDate = convertUtcToKstStrDate(utcDate);

        System.out.println("KST 시간 : " + kstDate);
        System.out.println("UTC 시간 : " +convertKstToUtcStrDate(kstDate));


        String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString() + "_KIDS";
        System.out.println(formattedDate);

        // 암호화 테스트
        String keyEnc = "";
        try {
            keyEnc = encrypt_seed256_hash("teststunitasdailydaechi");
            System.out.println(keyEnc);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String key = "a274a3397fcd53b1819d2f286a018422fc236bbebb9c246ea24ce518d0aba787";
        key = key.substring(0,16);
        String encString = AES256Cipher.AES_Encode(key, "880320");
        System.out.println(AES256Cipher.AES_Decode(key, encString));

    }

    public static String maskingPhoneNum(String a) {
        String result = "";

        try {
            if (!"".equals(a)) {
                if (a.contains("-")) {
                    String[] arr = a.split("-");
                    result = arr[0] + "-****-" + arr[2];
                } else {
                    StringBuilder b = new StringBuilder(a);
                    b.setCharAt(3, '*');
                    b.setCharAt(4, '*');
                    b.setCharAt(5, '*');
                    b.setCharAt(6, '*');
                    result = b.toString();
                }
            }
        } catch (Exception e) {
            result = a;
        }

        return result;
    }

    public static String convertUtcToKstStrDate(String utcDate) {

        LocalDateTime utcDateTime = LocalDateTime.parse(utcDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ZonedDateTime convertedKstDate = utcDateTime.atZone(ZoneId.of("Asia/Seoul"));
        convertedKstDate = convertedKstDate.withZoneSameInstant(ZoneOffset.UTC);

        return convertedKstDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String convertKstToUtcStrDate(String kstDate) {

        LocalDateTime kstDateTime = LocalDateTime.parse(kstDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ZonedDateTime convertedUtcDate = kstDateTime.atZone(ZoneOffset.UTC);
        convertedUtcDate = convertedUtcDate.withZoneSameInstant(ZoneId.of("Asia/Seoul"));

        return convertedUtcDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String encrypt_seed256_hash(String input) throws NoSuchAlgorithmException {

        String output = "";

        StringBuffer sb = new StringBuffer();
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        md.update(input.getBytes());

        byte[] msgb = md.digest();

        for (int i = 0; i < msgb.length; i++) {
            byte temp = msgb[i];
            String str = Integer.toHexString(temp & 0xFF);
            while (str.length() < 2) {
                str = "0" + str;
            }
            str = str.substring(str.length() - 2);
            sb.append(str);
        }

        output = sb.toString();

        return output;
    }
}