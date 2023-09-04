package jp.co.careritztc.day3.exercise1.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import jp.co.careritztc.day3.exercise1.pojo.Staff;
import jp.co.careritztc.day3.exercise1.pojo.Staff.Gender;

/**
 * ExampleTypeMapクラス.
 */
public class ExampleTypeMap {

  private static final Map<String, Staff> STAFFS;

  static {
    DateTimeFormatter  localdateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Map<String, Staff> map = new HashMap<>();
    map.put("Sato Taro", new Staff("Taro", "Sato", Gender.MALE, 50000, LocalDate.parse("1995-02-14", localdateFormatter)));
    map.put("Ito Hana", new Staff("Hana", "Ito", Gender.FEMALE, 60000,LocalDate.parse("1990-03-03", localdateFormatter)));
    map.put("Suzuki Ken", new Staff("Ken", "Suzuki", Gender.MALE, 55000,LocalDate.parse("1995-02-14", localdateFormatter)));
    map.put("Yamada Jiro", new Staff("Jiro", "Yamada", Gender.MALE, 60000,LocalDate.parse("1978-08-24", localdateFormatter)));
    map.put("Watanabe Mai", new Staff("Mai", "Watanabe", Gender.FEMALE, 100000,LocalDate.parse("1973-11-17", localdateFormatter)));

    STAFFS = Collections.unmodifiableMap(map);
  }

  private ExampleTypeMap() {
  }

  public static Map<String, Staff> getSampleData() {
    return STAFFS;
  }
}
