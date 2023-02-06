package jp.co.careritztc.sample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jp.co.careritztc.sample.dto.HolidaysDto;

/**
 * Data Access Object for holidays table.
 */
public class HolidaysDao {
  // 演習問題１-１
  private static final String DDL_CREATE_HOLIDAYS = """
      CREATE TABLE holidays(
          id         int          NOT NULL AUTO_INCREMENT
        , date_at    date         NOT NULL
        , name       varchar(100) NOT NULL
        , CONSTRAINT PK_HOLIDAYS PRIMARY KEY (id)
      ) ENGINE=InnoDB ROW_FORMAT=DYNAMIC DEFAULT CHARSET=utf8mb4;
      """;
  private static final String DDL_DROP_HOLIDAYS = "DROP TABLE IF EXISTS holidays;";

  private static final String INS_HOLIDAY = "insert into holidays(date_at, name) values (?, ?);";

  private static final List<String> DATA_HOLIDAYS = List.of(
      "2023/01/01,元日", "2023/01/02,休日", "2023/01/09,成人の日", "2023/02/11,建国記念の日", "2023/02/23,天皇誕生日", "2023/03/21,春分の日",
      "2023/04/29,昭和の日", "2023/05/03,憲法記念日", "2023/05/04,みどりの日", "2023/05/05,こどもの日", "2023/07/17,海の日", "2023/08/11,山の日",
      "2023/09/18,敬老の日", "2023/09/23,秋分の日", "2023/10/09,スポーツの日", "2023/11/03,文化の日", "2023/11/23,勤労感謝の日",
      "2024/01/01,元日", "2024/01/08,成人の日", "2024/02/11,建国記念の日", "2024/02/12,振替休日", "2024/02/23,天皇誕生日", "2024/03/20,春分の日",
      "2024/04/29,昭和の日", "2024/05/03,憲法記念日", "2024/05/04,みどりの日", "2024/05/5,こどもの日", "2024/05/06,振替休日", "2024/07/15,海の日",
      "2024/08/11,山の日", "2024/08/12,振替休日", "2024/09/16,敬老の日", "2024/09/22,秋分の日", "2024/09/23,振替休日", "2024/10/14,スポーツの日",
      "2024/11/03,文化の日", "2024/11/04,振替休日", "2024/11/23,勤労感謝の日");

  private Connection con;

  /**
   * コンストラクタ.
   *
   * @param con Connection
   */
  public HolidaysDao(Connection con) {
    if (con == null) {
      throw new IllegalArgumentException("Connection must not be null.");
    }
    this.con = con;
  }

  /**
   * 呼び出し元でもトランザクション制御用に設定されたConnectionを返す.
   *
   * @return Connection
   */
  public Connection getConnection() {
    return this.con;
  }

  /**
   * 祝日情報テーブルを作成/再作成し、初期データを登録する.
   *
   * @throws SQLException SQLエラー情報
   */
  public void initialize() throws SQLException {

    // PreparedStatementの後始末として確実にcloseする
    // ステートメントクラスにSQL文を格納
    try (PreparedStatement psDropHolidays = con.prepareStatement(DDL_DROP_HOLIDAYS);
        PreparedStatement psCreateHolidays = con.prepareStatement(DDL_CREATE_HOLIDAYS);
        PreparedStatement psInsHoliday = con.prepareStatement(INS_HOLIDAY)) {

      psDropHolidays.execute();
      psCreateHolidays.execute();

      for (String row : DATA_HOLIDAYS) {
        String[] arrRow = row.split(",");
        psInsHoliday.setString(1, arrRow[0]);
        psInsHoliday.setString(2, arrRow[1]);

        // SQLを実行
        psInsHoliday.executeUpdate();
      }
    }
  }

  /**
   * データベースからメッセージを取得する.
   *
   * @return メッセージを格納したリスト
   *
   * @throws SQLException SQLエラー情報
   */
  public List<HolidaysDto> selectAll() throws SQLException {

    List<HolidaysDto> list = new ArrayList<>();

    // PreparedStatementとResultSetの後始末として確実にcloseする
    try (
        // ステートメントクラスにSQL文を格納
        PreparedStatement ps = con.prepareStatement("SELECT id, date_at, name FROM holidays ORDER BY id");
        // SQLを実行して取得結果をリザルトセットに格納
        ResultSet rs = ps.executeQuery()) {

      // リザルトセットから1レコードずつデータを取り出す
      while (rs.next()) {
        // 取得結果を格納するDtoをインスタンス化
        HolidaysDto dto = new HolidaysDto();
        // Dtoに取得結果を格納
        dto.setId(rs.getObject("id", Integer.class));
        dto.setDateAt(rs.getDate("date_at")
                        .toLocalDate());
        dto.setName(rs.getString("name"));
        // Dtoに格納された1レコード分のデータをリストに詰める
        list.add(dto);
      }
    }

    // 呼び出し元に取得結果を返却
    return list;
  }
}
