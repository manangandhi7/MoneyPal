package com.MoneyPal.Common;

/**
 * Created by manan on 6/11/2017.
 */

import java.util.Random;

public class IDGenerator {

    public static void main(String[] args) {
        System.out.print(generateUniqueID());
    }

    public static String generateUniqueID() {
        String uniqueID = "";
        Random r = new Random();
        //while (true){
        for (int i = 0; i < 3; ++i) {
            int index = r.nextInt(arr.length);
            uniqueID += arr[index].toUpperCase();
            uniqueID += "_";
        }
//            if (not allotted){
//                break;
//            } else {
//                continue;
//            }
        //}

        return uniqueID;
    }

    public static String arr[] = {
            "hello",
            "apple",
            "samsung",
            "boy",
            "man",
            "India",
            "China",
            "Toy",
            "Honey",
            "android",
            "rich",
            "physics",
            "maths",
            "sbi",
            "hdfc",
            "earth",
            "gujarat",
            "Goa",
            "love",
            "money",
            "Mumbai",
            "tata",
            "wipro",
            "Google",
            "Amazon",
            "facebook",
            "cat",
            "lion",
            "Surat",
            "Baroda",
            "Java",
            "Python",
            "dance",
            "music",
            "Sun",
            "Jupiter",
            "Mars",
            "Pluto",
            "Delhi",
            "Athens",
            "Cairo",
            "1947",
            "Gandhi",
            "NASA",
            "ISRO",
            "Hindi",
            "Tamil",
            "Airtel",
            "freedom",
            "idea",
            "reliance",
            "suzlon",
            "Ola",
            "Uber",
            "ebay",
            "Birla",
            "king",
            "Karma",
            "Yoga",
            "Holi",
            "Diwali",
            "Ganga",
            "rupee",
            "Cricket",
            "Guru",
            "Silk",
            "Mango"
    };
}