package com.example.android.top10downloader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by ABhimsaria on 3/28/2016.
 */
public class ParseApplications {
    private String xmlData;
    private ArrayList<Application> mApplications;


    public ParseApplications(String xmlData) {
        this.xmlData = xmlData;
        mApplications = new ArrayList<Application>();

    }
    public ArrayList<Application> getApplications() {
        return mApplications;
    }
    public boolean process(){
        boolean status=true;
        Application currentRecord = null;
        boolean inEntry = false;
        String textValue="";
        try{
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp=factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));
            int evenType=xpp.getEventType();

            while (evenType != XmlPullParser.END_DOCUMENT){
               String tagName= xpp.getName();
                switch (evenType){
                    case XmlPullParser.START_TAG:
                    //    Log.d("ParseApplications","Starting Tag for:"+ tagName);
                        if(tagName.equalsIgnoreCase("entry")){
                            inEntry = true;
                            currentRecord = new Application();
                           }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if(tagName.equalsIgnoreCase("entry")){
                                mApplications.add(currentRecord);
                                inEntry = false;
                            }else if (tagName.equalsIgnoreCase("name")){
                                currentRecord.setName(textValue);
                            }else if (tagName.equalsIgnoreCase("artist")){
                                currentRecord.setArtist(textValue);
                            }else if(tagName.equalsIgnoreCase("releaseDate")){
                                currentRecord.setReleaseDate(textValue);
                            }
                        }
                //        Log.d("ParseApplications","Ending Tag for:"+ tagName);
                        break;
                    default:
                        //Nothing else to do.
                }
                evenType = xpp.next();
            }
        }catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        for(Application app: mApplications){
            Log.d("ParseApllications","************");
            Log.d("ParseApllications","Name: " + app.getName());
            Log.d("ParseApllications","Artist: " + app.getArtist());
            Log.d("ParseApllications","Realese Date: " + app.getReleaseDate());
        }
        return true;

    }
}
