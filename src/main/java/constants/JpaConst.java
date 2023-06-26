package constants;

/**
 * DB関連の項目値を定義するインターフェース
 * ※インターフェイスに定義した変数は public static final 修飾子がついているとみなされる
 */
public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "daily_report_system";

    //データ取得件数の最大値
    int ROW_PER_PAGE = 15; //1ページに表示するレコードの数

    //従業員テーブル
    String TABLE_EMP = "employees"; //テーブル名
    //従業員テーブルカラム
    String EMP_COL_ID = "id"; //id
    String EMP_COL_CODE = "code"; //社員番号
    String EMP_COL_NAME = "name"; //氏名
    String EMP_COL_PASS = "password"; //パスワード
    String EMP_COL_ADMIN_FLAG = "admin_flag"; //管理者権限
    String EMP_COL_CREATED_AT = "created_at"; //登録日時
    String EMP_COL_UPDATED_AT = "updated_at"; //更新日時
    String EMP_COL_DELETE_FLAG = "delete_flag"; //削除フラグ

    int ROLE_ADMIN = 1; //管理者権限ON(管理者)
    int ROLE_GENERAL = 0; //管理者権限OFF(一般)
    int EMP_DEL_TRUE = 1; //削除フラグON(削除済み)
    int EMP_DEL_FALSE = 0; //削除フラグOFF(現役)
    int FAV_REP_TRUE = 1; //日報のいいねフラグon
    int FAV_REP_FALSE = 0; //日報のいいねフラグoff


    //日報テーブル
    String TABLE_REP = "reports"; //テーブル名
    //日報テーブルカラム
    String REP_COL_ID = "id"; //id
    String REP_COL_EMP = "employee_id"; //日報を作成した従業員のid
    String REP_COL_REP_DATE = "report_date"; //いつの日報かを示す日付
    String REP_COL_TITLE = "title"; //日報のタイトル
    String REP_COL_CONTENT = "content"; //日報の内容
    String REP_COL_CREATED_AT = "created_at"; //登録日時
    String REP_COL_UPDATED_AT = "updated_at"; //更新日時

    String TABLE_FAV = "favorites"; //テーブル名
    //お気に入りテーブルカラム
    String FAV_COL_ID = "id"; //id
    String FAV_COL_REP = "report_id"; //お気に入りをした日報
    String FAV_COL_EMP = "employee_id"; //お気に入りをした従業員
    String FAV_COL_FAVORITE_FLAG = "favorite_flag"; //お気に入りしているかどうか
    String FAV_COL_FAVORITE_AT = "favorite_at"; //お気に入りした時間

    //Entity名
    String ENTITY_EMP = "employee"; //従業員
    String ENTITY_REP = "report"; //日報
    String ENTITY_FAV = "favorite"; //お気に入り

    //JPQL内パラメータ
    String JPQL_PARM_CODE = "code"; //社員番号
    String JPQL_PARM_PASSWORD = "password"; //パスワード
    String JPQL_PARM_EMPLOYEE = "employee"; //従業員
    String JPQL_PARM_REPORT = "report"; //日報

    //NamedQueryの nameとquery
    //全ての従業員をidの降順に取得する
    String Q_EMP_GET_ALL = ENTITY_EMP + ".getAll"; //name
    String Q_EMP_GET_ALL_DEF = "SELECT e FROM Employee AS e ORDER BY e.id DESC"; //query
    //全ての従業員の件数を取得する
    String Q_EMP_COUNT = ENTITY_EMP + ".count";
    String Q_EMP_COUNT_DEF = "SELECT COUNT(e) FROM Employee AS e";
    //社員番号とハッシュ化済パスワードを条件に未削除の従業員を取得する
    String Q_EMP_GET_BY_CODE_AND_PASS = ENTITY_EMP + ".getByCodeAndPass";
    String Q_EMP_GET_BY_CODE_AND_PASS_DEF = "SELECT e FROM Employee AS e WHERE e.deleteFlag = 0 AND e.code = :" + JPQL_PARM_CODE + " AND e.password = :" + JPQL_PARM_PASSWORD;
    //指定した社員番号を保持する従業員の件数を取得する
    String Q_EMP_COUNT_REGISTERED_BY_CODE = ENTITY_EMP + ".countRegisteredByCode";
    String Q_EMP_COUNT_REGISTERED_BY_CODE_DEF = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :" + JPQL_PARM_CODE;
    //全ての日報をidの降順に取得する
    String Q_REP_GET_ALL = ENTITY_REP + ".getAll";
    String Q_REP_GET_ALL_DEF = "SELECT r FROM Report AS r ORDER BY r.id DESC";
    //全ての日報の件数を取得する
    String Q_REP_COUNT = ENTITY_REP + ".count";
    String Q_REP_COUNT_DEF = "SELECT COUNT(r) FROM Report AS r";
    //指定した従業員が作成した日報を全件idの降順で取得する
    String Q_REP_GET_ALL_MINE = ENTITY_REP + ".getAllMine";
    String Q_REP_GET_ALL_MINE_DEF = "SELECT r FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE + " ORDER BY r.id DESC";
    //指定した従業員が作成した日報の件数を取得する
    String Q_REP_COUNT_ALL_MINE = ENTITY_REP + ".countAllMine";
    String Q_REP_COUNT_ALL_MINE_DEF = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE;

  //指定した日報についたいいねの件数を取得する
    String Q_FAV_ALL_FAVORITE_COUNT_TO_REPORT = ENTITY_FAV + ".countAllFavoriteToReport";
    String Q_FAV_ALL_FAVORITE_COUNT_TO_REPORT_DEF = "SELECT COUNT(f) FROM Favorite As f WHERE f.report = :" + JPQL_PARM_REPORT + " AND f.favoriteFlag = 1";
    //指定した日報に紐づく従業員のいいねデータの件数を取得する
    String Q_FAV_COUNT_CREATED_MINE_FAVORITE_DATA_TO_REPORT = ENTITY_FAV + ".countCreatedMineFavoriteDataToReport";
    String Q_FAV_COUNT_CREATED_MINE_FAVORITE_DATA_TO_REPORT_DEF = "SELECT COUNT(f) FROM Favorite As f WHERE f.report = :" + JPQL_PARM_REPORT + " AND f.employee = :" + JPQL_PARM_EMPLOYEE;
    //指定した日報に従業員がつけたいいねの件数を取得する
    String Q_FAV_COUNT_MINE_FAVORITE_TO_REPORT = ENTITY_FAV + ".countMineFavoriteToReport";
    String Q_FAV_COUNT_MINE_FAVORITE_TO_REPORT_DEF = "SELECT COUNT(f) FROM Favorite As f WHERE f.report = :" + JPQL_PARM_REPORT + " AND f.employee = :" + JPQL_PARM_EMPLOYEE + " AND f.favoriteFlag = 1";
    //指定した日報に従業員がつけたいいね情報を取得する
    String Q_FAV_GET_MINE_FAVORITE_TO_REPORT = ENTITY_FAV + ".getMineFavoriteToReport";
    String Q_FAV_GET_MINE_FAVORITE_TO_REPORT_DEF = "SELECT f FROM Favorite As f WHERE f.report = :" + JPQL_PARM_REPORT + " AND f.employee = :" + JPQL_PARM_EMPLOYEE;
}

