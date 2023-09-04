package jp.co.careritztc.day3.exercise1;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;
import jp.co.careritztc.day3.exercise1.data.ExampleTypeMap;
import jp.co.careritztc.day3.exercise1.pojo.Staff;

/**
 * Javaの基礎５日間コース ３日目.
 *
 * <pre>
 * 演習問題１-５プログラム起動クラス
 * </pre>
 */
public class Exercise15Main {

  /**
   * main method.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    // key:氏名, value:Staff
    Map<String, Staff> data = ExampleTypeMap.getSampleData();

    try (var scanner = new Scanner(System.in)) {
      String line = scanner.nextLine();
      System.out.println("入力値[%s]".formatted(line));

      // TODO
      // 標準入力から年月日指定し、1995-02-14生まれの人の**人数**を標準出力へ表示して下さい。
      // また、標準入力から本日または無効な年月日を指定した場合は、**0**（ゼロ）が表示されるようにして下さい。
      DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate inputDate = LocalDate.parse(line, inputFormatter);
        
      if(inputDate.equals(LocalDate.now()))
        defaultNGAction();
      else
        System.out.println(data.entrySet().stream().filter(entry -> entry.getValue().getBirthday().equals(inputDate)).count());

    } catch(DateTimeParseException dtp){
      //dtp.printStackTrace();
      defaultNGAction();
    }catch (Exception e) {
      e.printStackTrace();    
      defaultNGAction();
    } 
  }

  /** 件数をカウントしない場合のメッセージ
   */
  private static void defaultNGAction(){
    String ngMessage = "0";
    System.out.println(ngMessage);
  }
}
