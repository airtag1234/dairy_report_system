package constants;

public enum PropertyConst {
    
  //ペッパー文字列
    PEPPER("pepper");

    private final String text;
    private PropertyConst(final String text) {
        this.text = text;
    }

    public String getValue() {
//        セル単体の値取得
        return this.text;
    }

}
