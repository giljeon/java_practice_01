package com.lotto;


import com.Utils;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class LottoMain {

    final static int MAGIC_NUMBER_ONE = 1;
    final static int MIN_NUMBER_ROLLING = 180;

    // 중복 카운트 변수
    static int overCount = 0;

    // 통계에 필요한 번호 VO
    static NumberVO voClass = new NumberVO();

    /**
     * Main
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 번호별 통계정보 업데이트를 위한 정보를 가져옴
        //getStatisticsByNumber();

        // 역대 정보를 가져옴
        //getWinNumber();

        // 예상 로또 번호를 출력함
        createLottoNumbers(5);
        //getOneYearsMatchingNumberList("16,19,24,33,42,44");
    }

    private static void getOneYearsMatchingNumberList(String strNumbers) {

        String targetUrl = "https://www.dhlottery.co.kr/gameResult.do?method=myWinNumberList2";
        String[] targetNumber = strNumbers.split(",");

        for (String strNumber : targetNumber) {
            targetUrl += "&txtNo_1=" + strNumber.trim();
        }

        for (int i = 1; i <= 5; i++) {
            targetUrl+= "&nowPage=" + i;

            try {
                URL url = new URL(targetUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("GET"); // optional default is GET
                con.setRequestProperty("User-Agent", "Mozilla/5.0"); // add request header

                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                //System.out.println("HTTP 응답 코드 : " + responseCode);
                // System.out.println("HTTP body : " + response.toString());

                // response 응답 데이터 String 변환
                String strJson = response.toString();

                // String Data Parse Json
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(strJson);
                JSONObject jsonObj = (JSONObject) obj;

                // JsonObject Convert Map
                Map<String, Object> map = Utils.getMapFromJsonObject(jsonObj);
                List<Map<String, Object>> list = new ArrayList<>();
                list.addAll((List<Map<String, Object>>)map.get("arr"));
                if(i == 1) {
                    System.out.println("=============== "+ map.get("txtNo1") +" =================");
                }
                //map.put("arr", "");
                //System.out.println(map);
                //System.out.println(list.toString());
                //Utils.printLotNumberList(list);
                Utils.printLotNumberRank(list);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        System.out.println("====================================================");
    }

    private static void getStatisticsByNumber() {
        // 번호별 통계
        ArrayList<Integer> nArray = new ArrayList<>();

        // 1. 접속 URL 선언
        String connectURl = "https://www.dhlottery.co.kr/gameResult.do?method=statByNumber";

        try {

            System.out.println(" Start Date : " + Utils.getCurrentData());

            // 2. HTML 가져오기
            Document docGet = Jsoup.connect(connectURl).get();
            Elements elGet = docGet.select("#printTarget tbody tr");

            for (Element item : elGet) {
                nArray.add(Integer.parseInt(item.select("td").get(2).text()));
            }

            System.out.println(" End Date  : " + Utils.getCurrentData());

            int cnt = 1;
            for (int item : nArray) {
                if (cnt % 10 == 1)
                    System.out.println();

                System.out.println(item + ",	//" + cnt++);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getWinNumber() {
        // 1. 접속 URL 선언
        int startNum = 982;
        int endNum = 983;

        // 개행 카운터
        int count = 0;

        System.out.println(" Start Date : " + Utils.getCurrentData());
        System.out.println("==============");

        for (int i = startNum; i < endNum + 1; i++) {

            String connectURl = "https://dhlottery.co.kr/gameResult.do?method=byWin&drwNo=" + i;

            try {

                // 2. HTML 가져오기
                Document docGet = Jsoup.connect(connectURl).get();
                Elements elGet = docGet.select(".win_result .win p");
                System.out.print("\"" + elGet.text() + "\",");

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (count > 5) {
                System.out.println();
                count = 0;
            }

            count++;
        }
        System.out.println("==============");
        System.out.println(" End Date  : " + Utils.getCurrentData());
    }

    /**
     * 번호 생성 매소드
     *
     * @param gameCount : gameCount 만큼 로또 게임 개수를 가져옴
     */
    private static void createLottoNumbers(int gameCount) {
        System.out.println("======== GAME NUMBER Generator START ========");
        ArrayList<String> strArray = new ArrayList<>();
        for (int cnt = 0; cnt < gameCount; cnt++) {
            String strLotNumber = getLottoNumber();
            System.out.println(strLotNumber);
            String number = strLotNumber.replaceAll("[\\[\\]]", "");
            strArray.add(number);
        }

        System.out.println();
        System.out.println();

        for (String temp : strArray) {
            getOneYearsMatchingNumberList(temp);
        }

        System.out.println("========= GAME NUMBER Generator END =========");
        System.out.println("=========[ OVERLABCOUNT ] : " + overCount);

    }

    /**
     * 번호를 가져옴
     */
    private static String getLottoNumber() {
        // 번호별 당청 통계 정보 배열을 가져옴
        int[] lotNumberArray = voClass.getLotNumberArray();

        // 번호별 확률 통계를 통해 1 ~ 45의 숫자를 넣을 List 선언
        List<Integer> chanceNumbertList = new ArrayList<Integer>();

        // NUMBER_LOT_ARRAY 배열의 각 index == 로또번호, index의 value == 해당 index 번호의 반복 횟수
        for (int index = 0; index < lotNumberArray.length; index++) {

            // Min : 많이 안나온 숫자의 확률을 더 높임
            //for (int valCnt = 0; valCnt < (MIN_NUMBER_ROLLING - lotNumberArray[index]) ; valCnt++) {

            // Max : 많이 나온 숫자의 확률을 더 높임
            for (int valCnt = 0; valCnt < lotNumberArray[index]; valCnt++) {
                chanceNumbertList.add(index + MAGIC_NUMBER_ONE);
            }
        }

        // 번호별 확률 통계에 의해 삽입한 List의 value를 무작위로 배치(섞기)
        Collections.shuffle(chanceNumbertList);
        List<Integer> lottoNumberList = getLotSixNumber(chanceNumbertList);

        boolean flag = true;

        while (flag) {

            /* 비교 중복 체크 */
            if (compareGetNumberWithLastNumber(lottoNumberList)) {
                overCount++;
                Collections.shuffle(chanceNumbertList);
                lottoNumberList = getLotSixNumber(chanceNumbertList);
            } else {
                flag = false;
            }
        }

        String tempStr = lottoNumberList.toString();
        return tempStr;
        //System.out.println(tempStr);
		/*System.out.println();
		System.out.println("-------복사 용이-------");
		System.out.println(tempStr.replace(",", "\t"));
		System.out.println();*/
    }

    /**
     * 6개의 번호를 무작위로 가져옴
     *
     * @param chanceNumberList
     * @return
     */
    private static List<Integer> getLotSixNumber(List<Integer> chanceNumberList) {

        int lot = 0;
        Set<Integer> set = new HashSet<Integer>();

        // 6개의 번호를 중복을 제거하여 set collections 에 대입
        while (set.size() < 6) {
            int sel = chanceNumberList.get(lot);
            set.add(sel);

            lot++;
        }

        // set -> int형 List로 대입
        List<Integer> list = new ArrayList<Integer>(set);
        // 6개의 번호 정렬 (오름차순)
        Collections.sort(list);

        return list;
    }

    /**
     * 뽑힌 번호를 불러와 중복 시 제거를 위해 중복여부 반환
     *
     * @param list
     * @return
     */
    private static boolean compareGetNumberWithLastNumber(List<Integer> list) {
        String strLotNumber = "";
        String[] lastArray = voClass.getLastNumberArray();
        int[] todayRemoveArray = voClass.getTodayRemoveNumber();

        for (int j = 0; j < list.size(); j++) {
            strLotNumber += list.get(j).toString();
        }

        for (int i = 0; i < lastArray.length; i++) {
            String strTemp1 = lastArray[i];
            if (strLotNumber.equals(strTemp1)) {
                return true;
            }
        }

        for (int j = 0; j < todayRemoveArray.length; j++) {
            if (list.indexOf(todayRemoveArray[j]) != -1) {
                return true;
            }
        }

        return false;
    }
}
