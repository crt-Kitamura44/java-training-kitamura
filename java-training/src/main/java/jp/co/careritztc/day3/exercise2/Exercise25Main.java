package jp.co.careritztc.day3.exercise2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import jp.co.careritztc.day3.exercise2.pojo.Employee;

/**
 * Javaの基礎５日間コース ３日目.
 *
 * <pre>
 * 演習問題２-５プログラム起動クラス
 * </pre>
 */
public class Exercise25Main {

  /**
   * main.
   */
  public static void main(String[] args) {
    // 演習問題２-１で読み取ったcsvデータを使用して下さい。
    // Last Nameの昇順に並べ替えて`employees2.csv`へ出力して下さい。
    // この時、各項目値はダブルクォーテーション`"`で括るものとします。
    // また、同時に`健`を`堅`に書き換えて出力して下さい。
    // なお、`employees2.csv`の出力先ディレクトリは問いません。
    List<Employee> employees = new ArrayList<Employee>();

    try(      
      FileReader fr = new FileReader("java-training/src/main/java/jp/co/careritztc/day3/exercise2/data/employees.csv");
      BufferedReader br = new BufferedReader(fr);
      ){


      //読み込んだファイルを１行ずつ処理する
      String line;
      StringTokenizer token;
      while ((line = br.readLine()) != null) {
        List<String> employeeParts = new ArrayList<String>();

        //区切り文字","で分割する
        token = new StringTokenizer(line, ",");

        //分割した文字を画面出力する
        while (token.hasMoreTokens()) {
          employeeParts.add(token.nextToken());
        }

        // 正常時のみ格納
        if(employeeParts.size() == 4)
          employees.add(new Employee(employeeParts.get(0),employeeParts.get(1), employeeParts.get(2), employeeParts.get(3)));
      }      
    }catch (IOException ex) {
      //例外発生時処理
      ex.printStackTrace();
    }

    Comparator<Employee> lastNameComp = Comparator.comparing(e -> e.lastName);
    
    try(
      // 出力ファイルの作成
      FileWriter fw = new FileWriter("java-training/src/main/java/jp/co/careritztc/day3/exercise2/data/employees2.csv", false);
      // PrintWriterクラスのオブジェクトを生成
      PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
    ){

      employees.stream()
      .sorted(lastNameComp)
      .forEach(employee -> pw.println(employee.firstName +"," + employee.lastName + "," + (employee.jaFirstName.contains("健") ? employee.jaFirstName.replace("健","堅") : employee.jaFirstName) + "," + employee.jaLastName));

    }catch(IOException ex){
      //例外発生時処理
      ex.printStackTrace();
    }
  }
}
