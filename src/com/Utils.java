package com;

import com.fasterxml.jackson.core.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Utils {
    public static String getCurrentData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * JsonObject를 Map<String, String>으로 변환한다.
     *
     * @param jsonObj JSONObject.
     * @return Map<String, Object>.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromJsonObject(JSONObject jsonObj) {
        Map<String, Object> map = null;
        try {
            map = new ObjectMapper().readValue(jsonObj.toJSONString(), Map.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public static void printLotNumberRank(List<Map<String, Object>> list) {
        for (Map<String, Object> data : list) {
           if ("X".equals(data.get("lottoNumberGrade")) == false)
                System.out.println(data.get("drwNo") + "회 등수 : " + data.get("lottoNumberGrade"));
        }
    }

    public static void printLotNumberList(List<Map<String, Object>> list) {
		System.out.println(list.size());
        for (Map<String, Object> data : list) {
			System.out.println(data);
			Iterator<String> keys = data.keySet().iterator();
			while( keys.hasNext() ){
				String key = keys.next();
				String value = data.get(key).toString();
				System.out.println("키 : "+key+", 값 : "+value);
			}
			System.out.println();
//			System.out.println(data.get("drwNo")+"\t"+data.get("drwNoDate")+"\t"+data.get("lottoNumber")+"\t"+data.get("bnusNo")+"\t"+
//					data.get("lottoNumberSu")+"\t"+data.get("lottoNumberGrade"));
//			System.out.println();
//			System.out.println("BONUS : "+data.get("lottoNumberSu"));
//			System.out.println();
        }
    }

    /*{
        txtDrwtNo2 = 19
		, totSellamnt = 0
		, lottoNumberGrade = X
		, txtDrwtNo3 = 24
		, txtDrwtNo1 = 16
		, secondWinamnt = 0
		, drwNoDate = 2020-06-06
		, firstWinamnt = 0
		, txtDrwtNo6 = 44
		, txtDrwtNo4 = 33
		, txtDrwtNo5 = 42
		, drwtNo6 = 44
		, drwtNo4 = 33
		, firstPrzwnerCo = 0
		, totprzamnt = 0
		, drwtNo5 = 42
		, secondPrzwnerCo = 0
		, thirdAccumamnt = 0
		, drwNoLimit = 0
		, lastDrwNo = 0
		, forthWinamnt = 0
		, lottoNumber = 16, 19, 24, 33, 42, 44
		, fifthPrzwnerCo = 0
		, drwtNo2 = 19
		, txtBnusNo = 27
		, forthAccumamnt = 0
		, drwtNo3 = 24
		, gameNo = 0
		, drwtNo1 = 16
		, thirdPrzwnerCo = 0
		, fifthWinamnt = 0
		, totPrzwnerCo = 0
		, drwNoMax = 0
		, bnusNo = 27
		, firstAccumamnt = 0
		, fifthAccumamnt = 0
		, thirdWinamnt = 0
		, forthPrzwnerCo = 0
		, txtLottoNumberSu = 0
		, drwNo = 914
		, lottoNumberSu = 0
		, secondAccumamnt = 0
    }*/
}