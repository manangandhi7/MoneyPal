package com.MoneyPal.Inventory;

/**
 * Created by manan on 6/11/2017.
 */

import java.util.*;

public class Transaction {

    public int ID;

    public enum TYPE{
        SEND,
        RECEIVE,
        Paid_for_friend,
        Shared_bill,
        Trip,
        Other
    };

    public enum STATUS{
        Finished,
        Init,
        In_process,
        Failed
    }

    public TYPE type;

    public STATUS status;

    public String senderID;
    public String receiverID;

    public HashMap<String, Double> payers;
    public HashMap<String, Double> participantsVaried;
    public List<String> participants;

    public Transaction(){
        ID = 0;
        payers = new HashMap<>();
        participantsVaried = new HashMap<String, Double>();
        participants = new ArrayList<String>();
    }

}
