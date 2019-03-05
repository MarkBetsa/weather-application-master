package mg.studio.weatherappdesign;

import java.util.Date;
import java.util.Calendar;

import java.text.SimpleDateFormat;

public class NowDate {
    public static String ND(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式

        String hehe = dateFormat.format( now );
        System.out.println(hehe);

        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改


        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        month++;
        int date = c.get(Calendar.DATE);
        //date++;
        String M,D;
        if(month<10)M="-0";else M="-";
        if(date<10)D="-0";else D="-";
        return year + M + month + D + date;
    }
}
