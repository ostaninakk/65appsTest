package ostanina.kk.appstest.utils;

import android.util.SparseArray;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ostanina.kk.appstest.R;


public class Utils {
    public static <C> List<C> asList(SparseArray<C> sparseArray) {
        if (sparseArray == null) return null;
        List<C> arrayList = new ArrayList<C>(sparseArray.size());
        for (int i = 0; i < sparseArray.size(); i++)
            arrayList.add(sparseArray.valueAt(i));
        return arrayList;
    }

    // Форматировать слово: первая буква заглавная, остальные - прописные
    public static String firstUpperCaseInWord(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    // По дате рождения вычислить возраст на текущий день
    public static int calculateAge(final Date birthday)
    {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(birthday);
        // include day of birth
        dob.add(Calendar.DAY_OF_MONTH, -1);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    // Форматировать дату по формату "dd.mm.yyyy"
    public static String reformatDate(Date date) {
        if (null !=date) {
            DateFormat format = new SimpleDateFormat("dd.mm.yyyy", Locale.getDefault());
            return format.format(date);
        }
        return "";
    }

    /* На основе двух форматов дат: "yyyy-mm-dd" и "dd-mm-yyyy",
     * распарсить дату из строки. Форматы дат выбраны на основе предоставленных тестовых данных
     */
    public static Date getDateFromString(String dateString) {
        if (null != dateString && !dateString.isEmpty()) {
            DateFormat format1 = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
            DateFormat format2 = new SimpleDateFormat("dd-mm-yyyy", Locale.getDefault());
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = format1.parse(dateString);
                if (date1.getTime() > 0) {
                    return date1;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                date2 = format2.parse(dateString);
                if (date2.getTime() > 0) {
                    return date2;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }
}
