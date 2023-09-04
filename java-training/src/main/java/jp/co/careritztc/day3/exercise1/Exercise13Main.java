package jp.co.careritztc.day3.exercise1;

import java.time.chrono.JapaneseChronology;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.Calendar;

import jp.co.careritztc.day3.exercise1.data.ExampleTypeMap;
import jp.co.careritztc.day3.exercise1.pojo.Staff;

/**
 * Javaの基礎５日間コース ３日目.
 *
 * <pre>
 * 演習問題１-３プログラム起動クラス
 * </pre>
 */
public class Exercise13Main {

  /**
   * main method.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    // key:氏名, value:Staff
    Map<String, Staff> data = ExampleTypeMap.getSampleData();

    // TODO 全員の氏名と、今年の誕生日に加え、その曜日を標準出力へ表示して下さい。
    Comparator<Entry<String, Staff>> totalnameComp = Comparator.comparing(e -> e.getValue().getLastName().length() + e.getValue().getFirstName().length());

    Locale locale = new Locale("ja", "JP", "JP");
    DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("Gy年M月d日EEEE", locale)
			.withChronology(JapaneseChronology.INSTANCE);

    int MaxTotalnameLength = data.entrySet().stream()
    .max(totalnameComp)
    .map(e -> e.getValue().getFirstName() + e.getValue().getLastName()).get().length();

    Function<LocalDate, LocalDate> birthdayOfThisYear = (d) -> {return d.plusYears(LocalDateTime.now().getYear() - d.getYear()) ; };

    data.entrySet().stream()
    .forEach(entry -> 
    System.out.println(String.format("%-"+(MaxTotalnameLength+1)+"s",entry.getValue().getLastName() + " " + entry.getValue().getFirstName()) 
    + " | " 
    +  birthdayOfThisYear.apply(entry.getValue().getBirthday()).format(formatter) ));
  }
}
