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

        int index = r.nextInt(UniqueIDs.length);
        uniqueID += UniqueIDs[index].toUpperCase();
        uniqueID += "_";

        index = r.nextInt(UniqueIDs.length);
        uniqueID += UniqueIDs[index].toUpperCase();
        uniqueID += "_";

        index = r.nextInt(UniqueIDs.length);
        uniqueID += UniqueIDs[index].toUpperCase();

        return uniqueID;
    }

    public static String UniqueIDs[] = {
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
