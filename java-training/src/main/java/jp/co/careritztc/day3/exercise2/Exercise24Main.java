package jp.co.careritztc.day3.exercise2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.co.careritztc.day3.exercise2.pojo.Employee;

/**
 * Javaの基礎５日間コース ３日目.
 *
 * <pre>
 * 演習問題２-４プログラム起動クラス
 * </pre>
 */
public class Exercise24Main {

  /** 最初と最後の「"」にマッチする正規表現*/
  static final String REGEX_SURROUND_DOUBLEQUATATION = "^\"|\"$";

  /**
   * main.
   */
  public static void main(String[] args) {
    // 演習問題２-１で読み取ったcsvデータを使用して下さい。
    // 「郎」の一文字を含む名を持つ人の人数とLast Nameを標準出力へ表示して下さい。
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

    Supplier<Stream<Employee>> sts = () -> employees.stream().filter(e -> e.jaFirstName.contains("郎"));
    System.out.println(sts.get().collect(Collectors.toCollection(ArrayDeque::new)).size() + "人");
    sts.get().forEach(e -> System.out.println(e.lastName));
  }
}
