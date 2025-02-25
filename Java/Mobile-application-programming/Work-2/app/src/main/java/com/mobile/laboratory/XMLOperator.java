package com.mobile.laboratory;

import android.content.Context;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class XMLOperator {
    public static ArrayList<Planner> parseXml(Context context) {
        ArrayList<Planner> plannerList = new ArrayList<>();
        try {
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            try (FileInputStream fileInputStream = context.openFileInput("data.xml")) {
                parser.setInput(fileInputStream, null);
                processParsing(parser, plannerList);
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return plannerList;
    }

    private static void processParsing(XmlPullParser parser, ArrayList<Planner> plannerList) throws IOException, XmlPullParserException {
        int eventType = parser.getEventType();
        Planner currentPlanner = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                String tagName = parser.getName();
                if ("doc".equalsIgnoreCase(tagName)) {
                    currentPlanner = new Planner();
                } else if (currentPlanner != null) {
                    switch (tagName) {
                        case "title":
                            currentPlanner.title = parser.nextText();
                            break;
                        case "date":
                            currentPlanner.date = parser.nextText();
                            break;
                        case "time":
                            currentPlanner.time = parser.nextText();
                            break;
                        case "priority":
                            currentPlanner.priority = parser.nextText();
                            break;
                    }
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                String tagName = parser.getName();
                if ("doc".equalsIgnoreCase(tagName) && currentPlanner != null) {
                    plannerList.add(currentPlanner);
                }
            }
            eventType = parser.next();
        }
    }

    public static void writeXml(Context context, ArrayList<Planner> plannerList) {
        try {
            try (FileOutputStream fileOutputStream = context.openFileOutput("data.xml", Context.MODE_PRIVATE)) {
                XmlSerializer xmlSerializer = Xml.newSerializer();
                StringWriter writer = new StringWriter();
                xmlSerializer.setOutput(writer);

                xmlSerializer.startDocument("UTF-8", true);
                xmlSerializer.startTag("", "planners");

                for (Planner planner : plannerList) {
                    xmlSerializer.startTag("", "doc");
                    xmlSerializer.startTag("", "title");
                    xmlSerializer.text(planner.title != null ? planner.title : "No Title");
                    xmlSerializer.endTag("", "title");
                    xmlSerializer.startTag("", "date");
                    xmlSerializer.text(planner.date != null ? planner.date : "No Date");
                    xmlSerializer.endTag("", "date");
                    xmlSerializer.startTag("", "time");
                    xmlSerializer.text(planner.time != null ? planner.time : "No Time");
                    xmlSerializer.endTag("", "time");
                    xmlSerializer.startTag("", "priority");
                    xmlSerializer.text(planner.priority != null ? planner.priority : "Medium");
                    xmlSerializer.endTag("", "priority");
                    xmlSerializer.endTag("", "doc");
                }

                xmlSerializer.endTag("", "planners");
                xmlSerializer.endDocument();
                xmlSerializer.flush();

                String dataWrite = writer.toString();
                fileOutputStream.write(dataWrite.getBytes());
            }
        } catch (IllegalArgumentException | IOException | IllegalStateException e) {
            e.printStackTrace();
        }
    }
}