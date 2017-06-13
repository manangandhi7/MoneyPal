package com.MoneyPal.Inventory;

/**
 * Created by manan on 6/11/2017.
 */

import android.support.test.espresso.core.deps.dagger.internal.DoubleCheckLazy;

import java.util.*;

import static com.MoneyPal.Inventory.Transaction.STATUS.Init;

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
    public HashSet<String> participants;

    public Transaction(){
        ID = 0;
        payers = new HashMap<>();
        participantsVaried = new HashMap<String, Double>();
        participants = new HashSet<>();
        status = Init;

    }

    public void addPayer(String payer, double amount){
        payers.put(payer, amount);
    }

    public void addParticipant(String participant, double amount){
        participantsVaried.put(participant, amount);
        addParticipant(participant);
    }
    public void addParticipant(String participant){
        participants.add (participant);
    }

    public boolean calculateEverything(){
        HashMap<String, Double> payersBalance = new HashMap<String, Double>();
        HashMap<String, HashMap<String, Double> > settlement = new HashMap<>();

        double totalPaid = 0.0;
        for(String user : payers.keySet()){
            totalPaid += (Double) payers.get(user);
        }

        HashMap<String, Double> expenses = new HashMap<String, Double>();

        double totalExpense = 0.0;
        for(String user : participantsVaried.keySet()){
            expenses.put(user, (Double) participantsVaried.get(user));
            totalExpense += (Double) participantsVaried.get(user);
        }

        if (participantsVaried.size() == participants.size()){
            if( totalPaid - totalExpense != 0){
                return false;
            }
        } else if (totalPaid - totalExpense < 0) {
            return false;
        }

        double bakiPerHead = (totalPaid - totalExpense) / (participantsVaried.size() - participants.size());

        for(Object userObj : participants.toArray()){
            String user = userObj.toString();
            if(!participantsVaried.containsKey(user)){
                expenses.put(user, new Double(bakiPerHead));
            }
        }

        for (String user : payers.keySet()){
            payersBalance.put(user, (Double) payers.get(user));
        }

        for (String user : payersBalance.keySet()){ //chalu ma value update thay che, joi le
            if(!expenses.containsKey(user)){
                continue;
            }

            double expense = (Double) expenses.get(user);
            double paid = (Double) payersBalance.get(user);

            if(paid > expense){
                payersBalance.put(user, (Double) paid - expense);
                expenses.remove(user);
            } else if (expense > paid){
                payersBalance.remove(user);
                expenses.put(user, (Double) expense - paid);
            } else {
                expenses.remove(user);
                payersBalance.remove(user);
            }
        }

//        Set <String> expenseSet = expenses.keySet();

        Iterator<Map.Entry<String,Double>> dendarIter = expenses.entrySet().iterator();

        while (dendarIter.hasNext()){
            Map.Entry<String,Double> dendarPair = dendarIter.next();
            double aapvana = (Double) dendarPair.getValue();
            String dendar = dendarPair.getKey();

            Iterator<Map.Entry<String,Double>> lendarIter  =payersBalance.entrySet().iterator();

            while (lendarIter.hasNext()){
                Map.Entry<String,Double> lendarPair = lendarIter.next();

                double levana = (Double) lendarPair.getValue();
                String lendar = lendarPair.getKey();

                if(levana > aapvana){
                    payersBalance.put(lendar, (Double) levana - aapvana);
                    //expenses.remove(dendar);
                    dendarIter.remove();

                    if (!settlement.containsKey(lendar)){
                        settlement.put (lendar, new HashMap<String, Double>());
                    }

                    HashMap<String, Double> temp = (HashMap<String, Double>)settlement.get(lendar);
                    temp.put(dendar, (Double) aapvana);
                    break;
                } else if (aapvana > levana){
                    //payersBalance.remove(lendar);
                    lendarIter.remove();

                    expenses.put(dendar, (Double) aapvana - levana);

                    if (!settlement.containsKey(lendar)){
                        settlement.put (lendar, new HashMap<String, Double>());
                    }

                    HashMap<String, Double> temp = (HashMap<String, Double>)settlement.get(lendar);
                    temp.put(dendar, (Double) levana);

                } else {
                    //expenses.remove(dendar);
                    dendarIter.remove();
                    //payersBalance.remove(lendar);
                    lendarIter.remove();


                    if (!settlement.containsKey(lendar)){
                        settlement.put (lendar, new HashMap<String, Double>());
                    }

                    HashMap<String, Double> temp = (HashMap<String, Double>)settlement.get(lendar);
                    temp.put(dendar, (Double) aapvana);
                    break;
                }
            }
        }

        return true;
    }

}
