package jp.co.careritztc.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import jp.co.careritztc.sample.dao.HolidaysDao;

/**
 * 祝日情報初期化アプリケーション.
 */
public class InitializeDatabase {

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

  /**
   * main.
   */
  public static void main(String[] args) {

    try (Connection con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS)) {

      // トランザクションのAutoCommit制御を無効にする
      con.setAutoCommit(false);

      // Daoクラスの機能呼び出し
      execInit(new HolidaysDao(con));

      System.out.println("祝日情報初期化アプリケーション終了。");

    } catch (Exception e) {
      e.printStackTrace();

      System.err.println("祝日情報初期化アプリケーション異常終了。");
    }
  }

  /**
   * 祝日情報のDB初期化処理実行.
   *
   * @param holidaysDao
   *
   * @throws SQLException SQL実行エラー
   */
  private static void execInit(HolidaysDao holidaysDao) throws SQLException {
    try {
      holidaysDao.initialize();
      // 正常に実行出来たらcommitする
      holidaysDao.getConnection()
                 .commit();

      System.out.println("祝日情報を初期化しました。");

    } catch (SQLException e) {
      // SQL実行でエラーがあったケースなので、rollbackする
      holidaysDao.getConnection()
                 .rollback();

      System.out.println("祝日情報の初期化に失敗しました。");

      throw e;
    }

  }
}
