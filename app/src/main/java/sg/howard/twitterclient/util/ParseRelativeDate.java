package sg.howard.twitterclient.util;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ParseRelativeDate {
    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    private static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public static String getShortcutRelativeTimeAgo(String rawJsonDate) {
        String time = getRelativeTimeAgo(rawJsonDate);
        String[] spilit = time.split(" ");
        if (time.equals("Yesterday")) return "1d";
        if (spilit.length <= 1) return time;
        String split1 = spilit[0];
        String split2 = spilit[1];
        switch (split2) {
            case "second": case "seconds":
                return split1 + "s";
            case "minute": case "minutes":
                return split1 + "m";
            case "hour": case "hours":
                return split1 + "h";
            case "day": case "days":
                return split1 + "d";
            case "month": case "months":
                return split1 + "mo";
            case "year": case "years":
                return split1 + "y";
            default:
                return time;
        }
    }

    public static String getJoinTweetDate(String rawJsonDate) {
        String month = rawJsonDate.split(" ")[1];
        String year = rawJsonDate.split(" ")[5];
        return "Joined " + month + " " + year;
    }
}
