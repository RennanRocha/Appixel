package br.com.trainning.pdv.domain.util;

import android.content.Context;
import android.provider.Settings;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by elcio on 05/12/15.
 */
public class Util {

    public static String getUniqueId(Context context){

        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        Date data = new Date();

        String dataFormatada = sdf.format(data);

        return android_id+dataFormatada;

    }

    public static String getData(){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date data = new Date();

        String dataFormatada = sdf.format(data);

        return dataFormatada;

    }

    public static String getCurrencyValue(double valor){
        DecimalFormat ptBR = new DecimalFormat("#,##0.00");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        ptBR.setDecimalFormatSymbols(symbols);
        if(valor>0.0d) {
            return " R$ " + ptBR.format(valor);
        }else{
            return "";
        }
    }
}
