package com.annuity;

import com.Utils;
import com.annuity.NumberVO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class AnnuityMain {

    final static int MAGIC_NUMBER_ONE = 1;

    // 통계에 필요한 번호 VO
    static NumberVO voClass = new NumberVO();

    public static void main(String[] args) throws Exception {
        //getStatisticsByNumber();

        createLottoNumbers(5);
    }

    private static void createLottoNumbers(int gameCount) {
        System.out.println("======== GAME NUMBER Generator START ========");
        for (int cnt = 0; cnt < gameCount; cnt++) {
            String strLotNumber = getAnnuityNumber();
            System.out.println(strLotNumber);
        }
        
        System.out.println("========= GAME NUMBER Generator END =========");

    }

    private static String getAnnuityNumber() {
        List<Integer> annuityNumberList = new ArrayList<Integer>();

        int getPullNumber= 7;
        for (int i = 0 ; i < getPullNumber; i++) {
            int[] numberVoArray;
            switch (i) {
                case 0 : numberVoArray = voClass.getJoNumberArray();
                    annuityNumberList.add(getWinNumber(i, numberVoArray));
                    break;
                case 1 : numberVoArray = voClass.getOneNumberArray();
                    annuityNumberList.add(getWinNumber(i, numberVoArray));
                    break;
                case 2 : numberVoArray = voClass.getTowNumberArray();
                    annuityNumberList.add(getWinNumber(i, numberVoArray));
                    break;
                case 3 : numberVoArray = voClass.getThreeNumberArray();
                    annuityNumberList.add(getWinNumber(i, numberVoArray));
                    break;
                case 4 : numberVoArray = voClass.getFourNumberArray();
                    annuityNumberList.add(getWinNumber(i, numberVoArray));
                    break;
                case 5 : numberVoArray = voClass.getFiveNumberArray();
                    annuityNumberList.add(getWinNumber(i, numberVoArray));
                    break;
                case 6 : numberVoArray = voClass.getSixNumberArray();
                    annuityNumberList.add(getWinNumber(i, numberVoArray));
                    break;
            }
        }

        return annuityNumberList.toString();
    }

    private static Integer getWinNumber(int caseNumber, int[] numberVoArray) {

        // 번호별 확률 통계를 통해 숫자를 넣을 List 선언
        List<Integer> chanceNumberList = new ArrayList<Integer>();

        // NUMBER_LOT_ARRAY 배열의 각 index == 로또번호, index의 value == 해당 index 번호의 반복 횟수
        for (int index = 0; index < numberVoArray.length; index++) {

            if(numberVoArray[index] == 0) {
                if (caseNumber == 0) {
                    chanceNumberList.add(index + MAGIC_NUMBER_ONE);
                } else {
                    chanceNumberList.add(index);
                }
            } else {
                for (int valCnt = 0; valCnt < numberVoArray[index]; valCnt++) {
                    if (caseNumber == 0) {
                        chanceNumberList.add(index + MAGIC_NUMBER_ONE);
                    } else {
                        chanceNumberList.add(index);
                    }
                }
            }
        }

        Collections.shuffle(chanceNumberList);

        return chanceNumberList.get(0);
    }

    private static void getStatisticsByNumber() {
        // 번호별 통계
        ArrayList<Map<String, Object>> arrayListy = new ArrayList<>();

        // 1. 접속 URL 선언
        String connectURl = "https://www.dhlottery.co.kr/gameResult.do?method=index720&rank=1,21&drowOption=drowPeriod&sltPeriod=15";

        try {

            System.out.println(" Start Date : " + Utils.getCurrentData());

            // 2. HTML 가져오기
            Document docGet = Jsoup.connect(connectURl).get();
            Elements elGet = docGet.select("#printTarget tbody tr");

            for (Element item : elGet) {
                Map<String, Object> map = new HashMap<>();
                map.put("win_unit",Integer.parseInt(item.select("td").get(0).text()));
                map.put("win_number",Integer.parseInt(item.select("td").get(0).text()));
                map.put("win_count", Integer.parseInt(item.select("td").get(2).text()));
                arrayListy.add(map);
            }

            System.out.println(" End Date  : " + Utils.getCurrentData());

            /*int area = 7;
            int cnt = 1;
            for (int item : nArray) {
                if (cnt % 10 == 1)
                    System.out.println();

                System.out.println(item + ",	//" + cnt++);
            }*/
            for (Map<String, Object> temp : arrayListy) {
                System.out.println("번호 : " + temp.get("win_number") + ", 횟수 : " + temp.get("win_count"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
