package com.MoneyPal.Common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by manan on 6/10/2017.
 */

public class Utility {
    public static final String Account_List= "http://52.172.213.166:8080/sbi/Account_List/api/EnqBancsAccountsList/ ";
    public static final String Customer_Details= "http://52.172.213.166:8080/sbi/CustDet/api/EnqCustomerDetails ";
    public static final String Customer_Account= "http://52.172.213.166:8080/sbi/CustAcc/api/EnqCustomerAccount ";
    public static final String Mini_Account_Statement= "http://52.172.213.166:8080/sbi/Mini/api/EnqMiniStatement ";
    public static final String Detail_Account_Statement= "http://52.172.213.166:8080/sbi/Detail/api/EnqINBAccountStatement ";
    public static final String Fund_Transfer_through_NEFT= "http://52.172.213.166:8080/sbi/NEFT/api/TxnNEFT ";
    public static final String Fund_Transfer_through_RTGS= "http://52.172.213.166:8080/sbi/RTGS/api/TxnRTGS ";
    public static final String Customer_to_Customer_Fund_Transfer= "http://52.172.213.166:8080/sbi/C2C/api/TxnCustomerToCustomer ";
    public static final String Education_Loan_Details_Enquiry= "http://52.172.213.166:8080/sbi/EduLoan/api/EnqEducationalDetails ";
    public static final String Loan_Enquiry= "http://52.172.213.166:8080/sbi/Loan_Enquiry/api/EnqLoan ";
    public static final String Loan_Details= "http://52.172.213.166:8080/sbi/Loan_Details/api/EnqLoanDetails ";
    public static final String Loan_Account_Statement= "http://52.172.213.166:8080/sbi/Loan_Statement/api/EnqLoanAccountStatement ";
    public static final String Cheque_Issuance= "http://52.172.213.166:8080/sbi/Cheque_Issue/chequeBook/issuance ";
    public static final String Stop_Cheque= "http://52.172.213.166:8080/sbi/Cheque/stopCheque/transaction ";
    public static final String Branch_Details_from_IFSC_Code= "http://52.172.213.166:8080/sbi/IFSC/api/EnqIFSCInformation ";
    public static final String TDS_Enquiry_Facility= "http://52.172.213.166:8080/sbi/TDS/api/EnqTDS ";
    public static final String PAN_Validation= "http://52.172.213.166:8080/sbi/PAN/api/EnqPANValidation ";
    public static final String Customer_Locker_Enquiry= "http://52.172.213.166:8080/sbi/Locker/customerLocker/enquiry ";
    public static final String Branchwise_Locker_status_Enquiry= "http://52.172.213.166:8080/sbi/Status/branchwise-locker-status_enquiry/Locker_Enquiry ";

    public static final String[] FRIENDS = new String[] {
            "Mohit", "Turi", "Kabir", "Ali", "Priya"
    };

    public static final String SendToFCM = "SendToFCM";
    public static final String SendToSBI = "SendToSBI";
    public static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
    public static final String AUTHORIZATION_STRING = "Authorization";
    public static final String AUTHORIZATION_KEY = "key=AAAAbije9rk:APA91bHGo5e_N8WZuiriic-enefLqXDZkd2448wi7ttBOUGYZucaosm672SnsTOlm8Hme0XJnBnii8O6dmEbJnOvPudztW5mHrEWM1wqzwoNQhM_-Y3jAaObYC_2gPmntUi7bXsHJrl7";
    public static final String SBI_API_KEY_STRING = "apikey";
    public static final String SBI_API_KEY = "VP81nkyVLqrNgrf";

    public static final String GLOBAL_CATEGORY = "global";


    public static String getToken() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("token", refreshedToken);
        return refreshedToken;
    }

    public static void makeToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    //Authorization:key=AIzaSyZ-1u...0GBYzPu7Udno5aA


    //NEFT transfer strings
    /*
    "BENFACCTNO":"00000030001512992", "BENFNAME":"Mr.Sbi User1", "MOBNUMBER":"8452824672", "RECBNKIFSC":"HDFC0000001","REMNAME": "Mr.Sbi ExternalUser1","REMTACCTNO": "00000010001458660","SNDIFSC": "SBIN0000437","TXNAMT": "5000"
            "BENFACCTNO":"00000030001513000", "BENFNAME":"Mr.Sbi User2", "MOBNUMBER":"8452824600", "RECBNKIFSC":"ICIC0000002","REMNAME": "Mr.Sbi ExternalUser2","REMTACCTNO": "00000010001455550","SNDIFSC": "SBIN0000438","TXNAMT": "500"
            "BENFACCTNO":"00000030001513001", "BENFNAME":"Mr.Sbi User3", "MOBNUMBER":"8452824601", "RECBNKIFSC":"SBI0000003", "REMNAME":"Mr.Sbi ExternalUser3", "REMTACCTNO":"00000010001456660", "SNDIFSC":"SBIN0000439", "TXNAMT":"15002"
            "BENFACCTNO":"00000030001513002", "BENFNAME":"Mr.Sbi User4", "MOBNUMBER":"8452824602", "RECBNKIFSC":"PNB0000004", "REMNAME":"Mr.Sbi ExternalUser4", "REMTACCTNO":"00000010001458880", "SNDIFSC":"SBIN0000440", "TXNAMT":"25003"
            "BENFACCTNO":"00000030001513010", "BENFNAME":"Mr.Sbi User5", "MOBNUMBER":"8452824604", "RECBNKIFSC":"BOB0000005", "REMNAME":"Mr.Sbi ExternalUser5", "REMTACCTNO":"00000010000000000", "SNDIFSC":"SBIN0000441", "TXNAMT":"50045"
        */
}
