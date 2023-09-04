package jp.co.careritztc.day3.exercise1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.chrono.JapaneseChronology;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.careritztc.day3.exercise1.data.ExampleTypeMap;
import jp.co.careritztc.day3.exercise1.pojo.Staff;

/**
 * Javaの基礎５日間コース ３日目.
 *
 * <pre>
 * 演習問題１-２プログラム起動クラス
 * </pre>
 */
public class Exercise12Main {

  /**
   * main method.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    // key:氏名, value:Staff
    Map<String, Staff> data = ExampleTypeMap.getSampleData();

    // TODO 生年月日の**早い順**に、姓と誕生日を**和暦**で標準出力へ表示して下さい。
    Comparator<Entry<String, Staff>> birthdayComp = Comparator.comparing(e -> e.getValue().getBirthday());
    Comparator<Entry<String, Staff>> lastnameComp = Comparator.comparing(e -> e.getValue().getLastName().length());

    Locale locale = new Locale("ja", "JP", "JP");
    DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("Gy年M月d日", locale)
			.withChronology(JapaneseChronology.INSTANCE);

    int MaxLastnameLength = data.entrySet().stream().max(lastnameComp).get().getValue().getLastName().length();

    data.entrySet().stream()
    .sorted(birthdayComp)
    .forEach(entry -> 
    System.out.println(String.format("%-"+MaxLastnameLength+"s",entry.getValue().getLastName()) 
    + " | " 
    + entry.getValue().getBirthday().format(formatter)));
  }
}
