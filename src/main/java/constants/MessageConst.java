package constants;

public enum MessageConst {

    I_LOGINED("ログインしました"),
    E_LOGINED("ログインに失敗しました。"),
    I_LOGOUT("ログアウトしました。"),

  //DB更新
    I_REGISTERED("登録が完了しました。"),
    I_UPDATED("更新が完了しました。"),
    I_DELETED("削除が完了しました。"),

  //いいね
    I_ADD_FAVORITE("日報にいいねしました。"),
    I_SUB_FAVORITE("いいねを取り消しました。"),

    I_FOLLOWED("フォローしました。"),
    I_UNFOLLOWED("フォローを外しました。"),

    //バリデーション
    E_NONAME("氏名を入力してください。"),
    E_NOPASSWORD("パスワードを入力してください。"),
    E_NOEMP_CODE("社員番号を入力してください。"),
    E_EMP_CODE_EXIST("入力された社員番号の情報は既に存在しています。"),
    E_NOTITLE("タイトルを入力してください。"),
    E_NOCONTENT("内容を入力してください。"),
    E_FOLLOW_EXIST("すでにフォローしています。");


    private final String text;

    private MessageConst(final String text) {
        this.text = text;
    }

    public String getMessage() {
        return this.text;
    }
}
