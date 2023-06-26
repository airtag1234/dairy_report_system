package constants;


public enum AttributeConst {

    FLUSH("flush"),

    MAX_ROW("maxRow"),
    PAGE("page"),

    TOKEN("_token"),
    ERR("errors"),

    LOGIN_ERR("loginError"),

    EMPLOYEE("employee"),
    EMPLOYEES("employees"),
    EMP_COUNT("employees_count"),
    EMP_ID("id"),
    EMP_CODE("code"),
    EMP_PASS("password"),
    EMP_NAME("name"),
    EMP_ADMIN_FLG("admin_flag"),

    //管理者フラグ
    ROLE_ADMIN(1),
    ROLE_GENERAL(0),

    //削除フラグ
    DEL_FLAG_TRUE(1),
    DEL_FLAG_FALSE(0),

  //閲覧済みフラグ
    READ_FLAG_TRUE(1),
    READ_FLAG_FALSE(0),

    //お気に入りフラグ
    FAV_FLAG_TRUE(1),
    FAV_FLAG_FALSE(0),

    //日報管理
    REPORT("report"),
    REPORTS("reports"),
    REP_COUNT("reports_count"),
    REP_ID("id"),
    REP_DATE("report_date"),
    REP_TITLE("title"),
    REP_CONTENT("content_msg"),
    LOGIN_EMP("login_employee"),

  //お気に入り
    FAVORITE("favorite"),
    FAVORITES("favorites"),
    FAV_FAVORITE_COUNT("favoriteCount"),
    FAV_MY_FAVORITE_COUNT("myFavoriteCount");



    private final String text;
    private final Integer i;

    private AttributeConst(final String text) {
        this.text = text;
        this.i = null;
    }

    private AttributeConst(final Integer i) {
        this.text = null;
        this.i = i;
    }

    public String getValue() {
        return this.text;
    }

    public Integer getIntegerValue() {
        return this.i;
    }
}

