package jp.co.careritztc.day3.exercise2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.careritztc.day3.exercise2.pojo.Employee;

/**
 * Javaの基礎５日間コース ３日目.
 *
 * <pre>
 * 演習問題２-２プログラム起動クラス
 * </pre>
 */
public class Exercise22Main {

  /** 最初と最後の「"」にマッチする正規表現*/
  static final String REGEX_SURROUND_DOUBLEQUATATION = "^\"|\"$";

  /**
   * main.
   */
  public static void main(String[] args) {
    // 演習問題２-１で読み取ったcsvデータを使用して下さい。
    // 演習問題２-１と同じ結果を標準出力へ表示して下さい。
    // ただし、`System.out.println()`の実行呼び出しは１度切りとし、StringBuilderクラスを使用して下さい。
    List<Employee> employees = new ArrayList<Employee>();

    try{
      FileReader fr = new FileReader("java-training/src/main/java/jp/co/careritztc/day3/exercise2/data/employees.csv");
      BufferedReader br = new BufferedReader(fr);

      //読み込んだファイルを１行ずつ処理する
      String line;
      StringTokenizer token;
      while ((line = br.readLine()) != null) {
        List<String> employeeParts = new ArrayList<String>();

        //区切り文字","で分割する
        token = new StringTokenizer(line, ",");

        //分割した文字を画面出力する
        while (token.hasMoreTokens()) {
          String col = "";

          Pattern sdqPattern = Pattern.compile(REGEX_SURROUND_DOUBLEQUATATION); 
          Matcher matcher = sdqPattern.matcher(token.nextToken());  
          col = matcher.replaceAll("");
          employeeParts.add(col);
        }

        if(employeeParts.size() == 4)
          employees.add(new Employee(employeeParts.get(0),employeeParts.get(1), employeeParts.get(2), employeeParts.get(3)));
      }

      //終了処理
      br.close();
      
    }catch (IOException ex) {
      //例外発生時処理
      ex.printStackTrace();
    }

    StringBuilder sb = new StringBuilder();

    sb.append("｜First Name｜Last Name｜名｜姓｜\n");
    employees.forEach(employee -> sb.append("｜" + employee.firstName +"｜" + employee.lastName+"｜" + employee.jaFirstName+"｜" + employee.jaLastName + "｜\n"));

    System.out.println(sb);
  }
}
