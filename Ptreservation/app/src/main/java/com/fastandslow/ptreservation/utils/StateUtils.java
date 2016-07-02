package com.fastandslow.ptreservation.utils;

import android.content.Context;

/**
 * Created by zuby on 2016. 6. 28..
 */
public class StateUtils {

    public static int getUserId(Context context) {
        if(isTrainer(context))
            return SessionUtils.getInt(context,CodeDefinition.TRAINER_ID,0);
        else
            return SessionUtils.getInt(context,CodeDefinition.CUSTOMER_ID,0);
    }
    public static boolean isTrainer(Context context){
        String  state = SessionUtils.getString(context,CodeDefinition.USER_STATE,CodeDefinition.TRAINER);
        return state.equals(CodeDefinition.TRAINER);
    }
    public static boolean isCustomer(Context context){
        String  state = SessionUtils.getString(context,CodeDefinition.USER_STATE,CodeDefinition.TRAINER);
        return state.equals(CodeDefinition.CUSTOMER);
    }
}
