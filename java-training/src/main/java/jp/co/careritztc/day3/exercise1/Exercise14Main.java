package jp.co.careritztc.day3.exercise1;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Map;
import jp.co.careritztc.day3.exercise1.data.ExampleTypeMap;
import jp.co.careritztc.day3.exercise1.pojo.Staff;

/**
 * Javaの基礎５日間コース ３日目.
 *
 * <pre>
 * 演習問題１-４プログラム起動クラス
 * </pre>
 */
public class Exercise14Main {

  /**
   * main method.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    // key:氏名, value:Staff
    Map<String, Staff> data = ExampleTypeMap.getSampleData();

    // TODO 全員の誕生日を年を除いたMM月dd日形式で遅い順に重複を排除した形で標準出力へ表示して下さい。
    DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("MM月dd日");

    data.entrySet().stream()
    .map(entry -> entry.getValue().getBirthday().format(formatter))
    .sorted(Comparator.reverseOrder())
    .distinct()
    .forEach(entry -> System.out.println(entry));

  }
}
