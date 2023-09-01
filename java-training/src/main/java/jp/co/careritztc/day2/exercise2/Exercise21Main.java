package jp.co.careritztc.day2.exercise2;

import jp.co.careritztc.day2.exercise2.data.ExampleTypeMap;
import jp.co.careritztc.day2.exercise2.pojo.Staff.Gender;

/**
 * Javaの基礎５日間コース ２日目.
 *
 * <pre>
 * 演習問題２-１プログラム起動クラス
 * </pre>
 */
public class Exercise21Main {

  /**
   * main method.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    // 出力に用いるテキストフォーマットは以下とします。
    // System.out.println("氏名:[%s] 給与：[%d]円".formatted(戻り値MapのKey値, 戻り値MapのStaff型ValueからgetSalary()メソッドにより返される値));

    // サンプルデータ５名分の情報を標準出力へ表示して下さい。

    // 北村記載箇所開始

    ExampleTypeMap.getSampleData().entrySet().forEach(entry -> {
      if(entry.getValue().getGender() == Gender.FEMALE){
        System.out.println("氏名:[%s] 給与：[%d]円".formatted(entry.getKey() , entry.getValue().getSalary()));
      }
    });

    // 北村記載箇所終了
  }
}
