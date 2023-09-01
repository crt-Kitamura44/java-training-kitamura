package jp.co.careritztc.day2.exercise1;

import java.math.BigDecimal;

/**
 * Javaの基礎５日間コース ２日目.
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
    // 初期値：0.0、増分：0.1、繰り返し回数：10回
    // 繰り返し回数int i毎にその時の値resultを標準出力へ表示すること。
    // なお、出力に用いるテキストフォーマットは以下とします。
    // System.out.println("i=%d result=".formatted(i) + result);

    // 変数resultの型は参照型の`BigDecimal`として実装して下さい。

      // 北村記載箇所開始
      BigDecimal result = BigDecimal.valueOf(0);
      BigDecimal addValue = BigDecimal.valueOf(0.1);

      for(int i = 0;i<10;i++){        
        result = result.add(addValue);
        System.out.println("i=%d result=".formatted(i) + result);
      }
      // 北村記載箇所終了
  }
}
