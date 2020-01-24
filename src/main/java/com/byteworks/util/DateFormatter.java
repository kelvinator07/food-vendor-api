package com.byteworks.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    private static String formatDate() {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = simpleDateFormat.format(date);
        return strDate;
    }
}
