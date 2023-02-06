package jp.co.careritztc.sample;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import jp.co.careritztc.sample.dao.HolidaysDao;
import jp.co.careritztc.sample.dto.HolidaysDto;

/**
 * 演習問題解答例.
 */
public class AnswerExample {

  /*-----------------------------*
  * データベースへの接続情報
  *-----------------------------*/
  // 接続するユーザー名
  // ※ユーザー名が「training」でない場合は該当箇所を変更してください
  private static final String USER_ID = "training";

  // 接続するユーザーのパスワード
  // ※パスワードが「passwd」でない場合は該当箇所を変更してください
  private static final String USER_PASS = "passwd";

  // 接続先DB
  private static final String JDBC_URL;

  static {
    StringBuilder builder = new StringBuilder();
    // ※PORT番号「3306」、データベース名が「training」でない場合は該当の箇所を変更してください
    builder.append("jdbc:mysql://localhost:3306/training")
           .append("?characterEncoding=UTF-8")
           .append("&connectionTimeZone=SERVER");
    JDBC_URL = builder.toString();
  }

  // 7days/week
  private static final int DAYS_IN_A_WEEK = 7;

  /**
   * main.
   */
  public static void main(String[] args) {

    try (Connection con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS)) {

      HolidaysDao dao = new HolidaysDao(con);
      // Daoクラスの機能呼び出し
      List<HolidaysDto> list = dao.selectAll();

      // 演習問題１-２
      // exercise12(list); // 2024年分も表示
      exercise12(list.stream()
                     .filter(p -> p.getDateAt()
                                   .getYear() == 2023)
                     .toList());

      // 演習問題１-５
      int[] targetYears = new int[] { 2023, 2024 };
      List<Month> months = Arrays.asList(Month.values());
      List<YearMonth> target = new ArrayList<>();
      for (int year : targetYears) {
        for (Month month : months) {
          target.add(YearMonth.of(year, month));
        }
      }
      exercise15(target, list);

      System.out.println("演習問題解答例プログラム終了。");

    } catch (Exception e) {
      e.printStackTrace();

      System.err.println("演習問題解答例プログラム異常終了。");
    }
  }

  /**
   * 演習問題１-２解答例メソッド.
   *
   * @param target 処理対象年月
   * @param list   祝日情報
   */
  private static void exercise12(List<HolidaysDto> list) {
    // 拡張for文で1行ずつ取り出して出力
    String template = """
        | 日付 | 名称 | 曜日 |
        | --- | --- | --- |
        %s
        """;
    String templateRow = """
        | %s | %s | %s |
        """;

    StringBuilder sbRow = new StringBuilder();

    // 年月日フォーマッタ
    DateTimeFormatter fmtDate = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    // 曜日フォーマッタ
    DateTimeFormatter fmtDayOfWeek = DateTimeFormatter.ofPattern("E", Locale.JAPANESE);

    for (HolidaysDto dto : list) {
      LocalDate dateAt = dto.getDateAt();
      sbRow.append(templateRow.formatted(dateAt.format(fmtDate), dto.getName(), dateAt.format(fmtDayOfWeek)));
    }
    var result = template.formatted(sbRow.toString());
    System.out.println(result);
    // 確認用にファイル出力
    try {
      Files.writeString(Paths.get("ans-exercise12.md"), result, CREATE, TRUNCATE_EXISTING, WRITE);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }

  }

  /**
   * 演習問題１-５解答例メソッド.
   *
   * @param target 処理対象年月
   * @param list   祝日情報
   */
  private static void exercise15(List<YearMonth> target, List<HolidaysDto> list) {
    // Map<YearMonth, List<HolidaysDto>> groupingHolidays
    var groupingHolidays = list.stream()
                               .collect(Collectors.groupingBy(
                                   holiday -> YearMonth.from(holiday.getDateAt())));

    var builder = new StringBuilder();
    for (YearMonth yearMonth : target) {
      var holidays = Optional.ofNullable(groupingHolidays.get(yearMonth))
                             .orElse(Collections.emptyList());
      builder.append(generateCalendar(yearMonth, holidays))
             .append("\n");
    }
    var result = builder.toString();
    System.out.println(result);
    // 確認用にファイル出力
    try {
      Files.writeString(Paths.get("ans-exercise15.md"), result, CREATE, TRUNCATE_EXISTING, WRITE);
    } catch (IOException e) {
      throw new UncheckedIOException("ファイルの書き込みに失敗しました", e);
    }

  }

  /**
   * カレンダーセル表示文字列を返す.
   *
   * @param map  祝日情報
   * @param date カレンダーセルへ出力するLocalDate
   *
   * @return カレンダーセル表示文字列
   */
  private static String getCellString(Map<LocalDate, HolidaysDto> map, LocalDate date) {
    BiFunction<Map<LocalDate, HolidaysDto>, LocalDate, String> toHolidayString = (m, d) -> {
      return "%s(%d)".formatted(m.get(d)
                                 .getName(),
          d.getDayOfMonth());
    };
    return map.containsKey(date) ? toHolidayString.apply(map, date) : String.valueOf(date.getDayOfMonth());
  }

  /**
   * 初週のカレンダー表示情報を返す.
   *
   * @param yearMonth 年月
   * @param list      祝日情報
   * @return 初週のカレンダー表示情報
   */
  private static List<String> getDaysOfFirstWeek(YearMonth yearMonth, List<HolidaysDto> list) {
    List<String> result = new ArrayList<>(DAYS_IN_A_WEEK);
    LocalDate firstDay = yearMonth.atDay(1);
    // 最初の週の開始日（日曜日）
    LocalDate beginningDateOfFirstWeek = DayOfWeek.SUNDAY == firstDay.getDayOfWeek() ? firstDay
        : firstDay.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
    // Map<LocalDate, HolidaysDto> holidaysMap
    var holidaysMap = list.stream()
                          .collect(Collectors.toMap(HolidaysDto::getDateAt, dto -> dto));

    LocalDate tempDate = beginningDateOfFirstWeek;

    if (beginningDateOfFirstWeek.isBefore(firstDay)) { // 週の開始日（日曜日）が前月度の日付の場合
      while (tempDate.isBefore(firstDay)) {
        result.add("X");
        tempDate = tempDate.plusDays(1);
      }
      while (result.size() <= DAYS_IN_A_WEEK) {
        result.add(getCellString(holidaysMap, tempDate));
        tempDate = tempDate.plusDays(1);
      }
    } else {
      while (result.size() <= DAYS_IN_A_WEEK) {
        result.add(getCellString(holidaysMap, tempDate));
        tempDate = tempDate.plusDays(1);
      }
    }
    return result;
  }

  /**
   * 最終週のカレンダー表示情報を返す.
   *
   * @param yearMonth 年月
   * @param list      祝日情報
   * @return 最終週のカレンダー表示情報
   */
  private static List<String> getDaysOfLastWeek(YearMonth yearMonth, List<HolidaysDto> list) {
    List<String> result = new ArrayList<>(DAYS_IN_A_WEEK);
    LocalDate lastDay = yearMonth.atEndOfMonth();
    LocalDate beginningDateOfLastWeek = DayOfWeek.SUNDAY == lastDay.getDayOfWeek() ? lastDay
        : lastDay.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
    LocalDate endDateOfLastWeek = DayOfWeek.SATURDAY == lastDay.getDayOfWeek() ? lastDay
        : lastDay.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
    // Map<LocalDate, HolidaysDto> holidaysMap
    var holidaysMap = list.stream()
                          .collect(Collectors.toMap(HolidaysDto::getDateAt, dto -> dto));

    LocalDate tempDate = beginningDateOfLastWeek;

    if (lastDay.isBefore(endDateOfLastWeek)) { // 週の最終日（土曜日）が次月度の日付の場合
      while (tempDate.isBefore(lastDay.plusDays(1))) { // 月末日当日を含めてループ処理
        result.add(getCellString(holidaysMap, tempDate));
        tempDate = tempDate.plusDays(1);
      }
      while (result.size() <= DAYS_IN_A_WEEK) {
        result.add("X");
      }
    } else {
      while (result.size() <= DAYS_IN_A_WEEK) {
        result.add(getCellString(holidaysMap, tempDate));
        tempDate = tempDate.plusDays(1);
      }
    }
    return result;
  }

  /**
   * 指定年月における初週と最終週を除いた日曜日のリストを返す.
   *
   * @param yearMonth 年月
   * @return 指定年月における初週と最終週を除いた日曜日のリスト
   */
  private static List<LocalDate> getSundaysOfTheMonthWithoutFirstAndLastWeek(YearMonth yearMonth) {
    List<LocalDate> result = new ArrayList<>();
    LocalDate firstDay = yearMonth.atDay(1);
    LocalDate beginningDateOfSecondWeek = firstDay.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
    LocalDate lastSundayInMonth = firstDay.with(TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY));

    LocalDate tempSunday = beginningDateOfSecondWeek;
    while (tempSunday.isBefore(lastSundayInMonth)) {
      result.add(tempSunday);
      tempSunday = tempSunday.plusWeeks(1);
    }

    return result;
  }

  /**
   * 指定日の週におけるカレンダー表示情報を返す.
   *
   * @param beginningDateOfTheWeek 週の開始日
   * @param list                   祝日情報
   * @return 指定日の週におけるカレンダー表示情報
   */
  private static List<String> getDaysOfTheWeek(LocalDate beginningDateOfTheWeek, List<HolidaysDto> list) {
    List<String> result = new ArrayList<>(DAYS_IN_A_WEEK);
    LocalDate tempDate = beginningDateOfTheWeek;
    // Map<LocalDate, HolidaysDto> holidaysMap
    var holidaysMap = list.stream()
                          .collect(Collectors.toMap(HolidaysDto::getDateAt, dto -> dto));

    while (result.size() <= DAYS_IN_A_WEEK) {
      result.add(getCellString(holidaysMap, tempDate));
      tempDate = tempDate.plusDays(1);
    }

    return result;
  }

  /**
   * 週の開始を日曜からとしての表示カレンダー文字列を生成する.
   *
   * @param yearMonth 年月
   * @param list      祝日情報
   *
   * @return 週の開始を日曜からとしての表示カレンダー文字列
   */
  private static String generateCalendar(YearMonth yearMonth, List<HolidaysDto> list) {
    StringBuilder builder = new StringBuilder();

    List<List<String>> weeks = new ArrayList<>();
    weeks.add(getDaysOfFirstWeek(yearMonth, list));
    List<LocalDate> sundays = getSundaysOfTheMonthWithoutFirstAndLastWeek(yearMonth);
    for (LocalDate sunday : sundays) {
      weeks.add(getDaysOfTheWeek(sunday, list));
    }
    weeks.add(getDaysOfLastWeek(yearMonth, list));

    String templateWeek = """
        | %s | %s | %s | %s | %s | %s | %s |
        """;
    for (List<String> week : weeks) {
      builder.append(templateWeek.formatted(week.toArray()));
    }

    // 年月日フォーマッタ
    var fmtYearMonth = DateTimeFormatter.ofPattern("yyyy年M月");
    String template = """
        %s
        | 日 | 月 | 火 | 水 | 木 | 金 | 土 |
        | --- | --- | --- | --- | --- | --- | --- |
        %s
        """;

    return template.formatted(yearMonth.format(fmtYearMonth), builder.toString());
  }
}
