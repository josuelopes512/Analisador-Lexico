public class Token {
    public static final int TOKEN_NUMERO        = 0;
    public static final int TOKEN_PONTUACAO     = 1;
    public static final int TOKEN_OP_ARITM      = 2;
    public static final int TOKEN_COMENT        = 3;
    public static final int TOKEN_FINAL         = 4;
    public static final int TOKEN_DELIMITADORES = 5;

    public static final String TK_TEXT[] = {
        "NUMERO", "PONTUACAO", "OP_ARITM", "COMENT", "FINAL", "DELIMITADORES"
};

    private int type;
    private String text;
    private int line;
	private int column;

    public Token() {
        
    }

    public Token(int type, String text) {
        this.setText(text);
        this.setType(type);
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return "Token [type=" + getType() + ", text=" + getText() + "]";
    }

    public int getLine() {
        return this.line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return this.column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
