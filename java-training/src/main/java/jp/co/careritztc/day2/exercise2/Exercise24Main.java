package jp.co.careritztc.day2.exercise2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import jp.co.careritztc.day2.exercise2.data.ExampleTypeMap;

/**
 * Javaの基礎５日間コース ２日目.
 *
 * <pre>
 * 演習問題２-４プログラム起動クラス
 * </pre>
 */
public class Exercise24Main {
  // 136円45銭/1ドル
  private static double FIX_RATE_YEN_DOLLAR = 136.45d;

  /**
   * main method.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    // 出力に用いるテキストフォーマットは以下とします。
    // System.out.println("氏名:[%s] 給与：[%d]円".formatted(戻り値MapのKey値, 戻り値MapのStaff型ValueからgetSalary()メソッドにより返される値));

    // サンプルデータ５名全員を対象とした平均給与額を円およびドル換算し、それぞれ標準出力へ表示して下さい。
    // なお、ドル換算に使用するレートは下記の通りとし、BigDecimalを使用して計算して下さい。
    BigDecimal averageIncomeDollar = BigDecimal.valueOf(0.00);
    Integer averageIncomeYen = 0;
    Integer totalIncomeYen = 0;

    for(var entry: ExampleTypeMap.getSampleData().entrySet()){
        totalIncomeYen = totalIncomeYen + entry.getValue().getSalary();
    };

    /* IntStream使用版
    ArrayList<Integer> salaryList = new ArrayList<Integer>();
    ExampleTypeMap.getSampleData().entrySet().forEach(entry -> {
      salaryList.add(entry.getValue().getSalary());
    });
    averageIncomeYen = (int)salaryList.stream().mapToInt(Integer::intValue).average().getAsDouble();
    */

    averageIncomeYen = totalIncomeYen / ExampleTypeMap.getSampleData().entrySet().size();
    averageIncomeDollar = BigDecimal.valueOf(averageIncomeYen).divide(BigDecimal.valueOf(FIX_RATE_YEN_DOLLAR),3, RoundingMode.DOWN);

    System.out.println(String.format("平均給与は%d円です。",averageIncomeYen));
    System.out.println(String.format("平均給与は%.2fドルです。",averageIncomeDollar));

  }
}
