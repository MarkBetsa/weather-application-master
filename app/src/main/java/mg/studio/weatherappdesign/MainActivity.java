package mg.studio.weatherappdesign;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DownloadUpdate().execute();
    }

    public void btnClick(View view) {
        new DownloadUpdate().execute();
    }


    private class DownloadUpdate extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = "https://api.seniverse.com/v3/weather/daily.json?key=3ikioj4erodm6pdz&location=chongqing&language=en&unit=c&start=0&days=5";
            HttpURLConnection urlConnection = null;
            BufferedReader reader;

            try {
                URL url = new URL(stringUrl);

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                    Log.d("TAG", line);
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                //The temperature
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String JSON) {
            //Update the temperature displayed

            String D = (new NowDate()).ND();
            int intIndex = JSON.indexOf(D);
            intIndex = JSON.indexOf("high",intIndex);
            char Tmp1 = JSON.charAt(intIndex+7);
            char Tmp2 = JSON.charAt(intIndex+8);
            String T1= new String(new char[]{Tmp1});
            String T2= new String(new char[]{Tmp2});
            if(Tmp2!='"')T1=T1+T2;

            intIndex = JSON.indexOf("name");
            int intIndex2;
            intIndex2 =  JSON.indexOf("\"",intIndex+7);
            String N = JSON.substring(intIndex+7,intIndex2);

            ((TextView) findViewById(R.id.temperature_of_the_day)).setText(T1);
            ((TextView) findViewById(R.id.tv_location)).setText(N);
            ((TextView) findViewById(R.id.tv_date)).setText(D);
            String WeekofDay="";
            if(getWeekOfDate(0).equals("Sun"))WeekofDay="Sunday";
            if(getWeekOfDate(0).equals("Mon"))WeekofDay="Monday";
            if(getWeekOfDate(0).equals("Tues"))WeekofDay="Tuesday";
            if(getWeekOfDate(0).equals("Wed"))WeekofDay="Wednesday";
            if(getWeekOfDate(0).equals("Thur"))WeekofDay="Thursday";
            if(getWeekOfDate(0).equals("Fri"))WeekofDay="Friday";
            if(getWeekOfDate(0).equals("Sat"))WeekofDay="Saturday";
            ((TextView) findViewById(R.id.WeekDay)).setText(WeekofDay);
            ((TextView) findViewById(R.id.day1)).setText(getWeekOfDate(1));
            ((TextView) findViewById(R.id.day2)).setText(getWeekOfDate(2));
            ((TextView) findViewById(R.id.day3)).setText(getWeekOfDate(3));
            ((TextView) findViewById(R.id.day4)).setText(getWeekOfDate(4));

            intIndex = JSON.indexOf("code_day");
            char WT01 = JSON.charAt(intIndex+11);
            char WT02 = JSON.charAt(intIndex+12);
            String W01= new String(new char[]{WT01});
            String W02= new String(new char[]{WT02});
            if(WT02!='"')W01=W01+W02;
            int W = Integer.parseInt(W01);
            if(31<W&&W<37) ((ImageView) findViewById(R.id.img_weather_condition)).setImageDrawable(getResources().getDrawable(R.drawable.windy_small));
                else if(9<W&&W<26)((ImageView) findViewById(R.id.img_weather_condition)).setImageDrawable(getResources().getDrawable(R.drawable.rainy_small));
                     else if((3<W&&W<10)||(25<W&&W<32))((ImageView) findViewById(R.id.img_weather_condition)).setImageDrawable(getResources().getDrawable(R.drawable.partly_sunny_small));
                     else ((ImageView) findViewById(R.id.img_weather_condition)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));

            intIndex = JSON.indexOf("code_day",intIndex);
            char WT11 = JSON.charAt(intIndex+11);
            char WT12 = JSON.charAt(intIndex+12);
            String W11= new String(new char[]{WT11});
            String W12= new String(new char[]{WT12});
            if(WT12!='"')W11=W11+W12;
            W = Integer.parseInt(W11);
            if(31<W&&W<37) ((ImageView) findViewById(R.id.img_weather_condition1)).setImageDrawable(getResources().getDrawable(R.drawable.windy_small));
            else if(9<W&&W<26)((ImageView) findViewById(R.id.img_weather_condition1)).setImageDrawable(getResources().getDrawable(R.drawable.rainy_small));
            else if((3<W&&W<10)||(25<W&&W<32))((ImageView) findViewById(R.id.img_weather_condition1)).setImageDrawable(getResources().getDrawable(R.drawable.partly_sunny_small));
            else ((ImageView) findViewById(R.id.img_weather_condition1)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));

            intIndex = JSON.indexOf("code_day",intIndex);
            char WT21 = JSON.charAt(intIndex+11);
            char WT22 = JSON.charAt(intIndex+12);
            String W21= new String(new char[]{WT21});
            String W22= new String(new char[]{WT22});
            if(WT22!='"')W21=W21+W22;
            W = Integer.parseInt(W21);
            if(31<W&&W<37) ((ImageView) findViewById(R.id.img_weather_condition2)).setImageDrawable(getResources().getDrawable(R.drawable.windy_small));
            else if(9<W&&W<26)((ImageView) findViewById(R.id.img_weather_condition2)).setImageDrawable(getResources().getDrawable(R.drawable.rainy_small));
            else if((3<W&&W<10)||(25<W&&W<32))((ImageView) findViewById(R.id.img_weather_condition2)).setImageDrawable(getResources().getDrawable(R.drawable.partly_sunny_small));
            else ((ImageView) findViewById(R.id.img_weather_condition2)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));

            intIndex = JSON.indexOf("code_day",intIndex);
            char WT31 = JSON.charAt(intIndex+11);
            char WT32 = JSON.charAt(intIndex+12);
            String W31= new String(new char[]{WT31});
            String W32= new String(new char[]{WT32});
            if(WT32!='"')W31=W31+W32;
            W = Integer.parseInt(W31);
            if(31<W&&W<37) ((ImageView) findViewById(R.id.img_weather_condition3)).setImageDrawable(getResources().getDrawable(R.drawable.windy_small));
            else if(9<W&&W<26)((ImageView) findViewById(R.id.img_weather_condition3)).setImageDrawable(getResources().getDrawable(R.drawable.rainy_small));
            else if((3<W&&W<10)||(25<W&&W<32))((ImageView) findViewById(R.id.img_weather_condition3)).setImageDrawable(getResources().getDrawable(R.drawable.partly_sunny_small));
            else ((ImageView) findViewById(R.id.img_weather_condition3)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));

            intIndex = JSON.indexOf("code_day",intIndex);
            char WT41 = JSON.charAt(intIndex+11);
            char WT42 = JSON.charAt(intIndex+12);
            String W41= new String(new char[]{WT41});
            String W42= new String(new char[]{WT42});
            if(WT42!='"')W41=W41+W42;
            W = Integer.parseInt(W41);
            if(31<W&&W<37) ((ImageView) findViewById(R.id.img_weather_condition4)).setImageDrawable(getResources().getDrawable(R.drawable.windy_small));
            else if(9<W&&W<26)((ImageView) findViewById(R.id.img_weather_condition4)).setImageDrawable(getResources().getDrawable(R.drawable.rainy_small));
            else if((3<W&&W<10)||(25<W&&W<32))((ImageView) findViewById(R.id.img_weather_condition4)).setImageDrawable(getResources().getDrawable(R.drawable.partly_sunny_small));
            else ((ImageView) findViewById(R.id.img_weather_condition4)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));

            Toast.makeText(MainActivity.this,"Update success!",Toast.LENGTH_LONG).show();


        }
    }


    public String getWeekOfDate(int k) {
        String[] weekDays = { "Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        w = w+k;
        if(w>6)w= w-7;
        return weekDays[w];
    }

}

