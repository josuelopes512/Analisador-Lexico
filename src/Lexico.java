import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Lexico {
    private char[] readContent;
    private int state;
    private int i;

    Lexico(String arquivo){
        try {
            String txt;
            txt = new String(Files.readAllBytes(Paths.get(arquivo)), StandardCharsets.UTF_8);
            System.out.println("TESTE ----------");
            System.out.println(txt);
            System.out.println("----------");
            readContent = txt.toCharArray();
            i = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void view(){
        for (int i = 0; i < readContent.length; i++) {
            System.out.println(readContent[i]);
        }
    }

    public Token nextToken(){
        Token tk;
        String t = "";
        char charAtual;
        if (isEOF()) {
            return null;
        }
        state = 0;
        while (true) {
            charAtual = nextChar();
            //System.out.println(charAtual);
            switch (state) {
                case 0: // teste se é um numero                    
                    if(isNumero(charAtual)){
                        t += charAtual;
                        state = 0;
                    }
                    else if(isPontuacao(charAtual)){
                        state = 1;
                    }
                    else if(isOperatorAritmetico(charAtual)){
                        state = 2;
                    }
                    else if(isComentario(charAtual)){
                        state = 3;
                    }
                    else if(isFinalizacao(charAtual)){
                        state = 4;
                    }
                    else if(isSpace(charAtual)){
                        state = 5;
                    }
                    else{
                        throw new LexicoException("C0 - Simbolo Nao Reconhecido");
                    }
                    break;
                case 1: // teste se é uma pontuação
                    if(isNumero(charAtual)){
                        state = 0;
                    }
                    else if(isPontuacao(charAtual)){           
                        t += charAtual;            
                        state = 1;
                    }
                    else if(isOperatorAritmetico(charAtual)){
                        state = 2;
                    }
                    else if(isComentario(charAtual)){
                        state = 3;
                    }
                    else if(isFinalizacao(charAtual)){
                        state = 4;
                    }
                    else if(isSpace(charAtual)){
                        state = 5;
                    }
                    else{
                        throw new LexicoException("C1 - Simbolo Nao Reconhecido");
                    }
                    break;
                case 2: // teste se é uma operador aritmetico
                    if(isNumero(charAtual)){
                        t += charAtual;
                        state = 0;
                    }
                    else if(isPontuacao(charAtual)){
                        state = 1;
                    }
                    else if(isOperatorAritmetico(charAtual)){
                        char tmp = backChar();
                        if (charAtual == '*' && tmp == '*') {
                            t += charAtual;
                            state = 2;
                        }
                        else{
                            System.err.println("ERRO , Não pode Repetir 2x o mesmo operador");
                        }
                    }
                    else if(isComentario(charAtual)){
                        state = 3;
                    }
                    else if(isFinalizacao(charAtual)){
                        state = 4;
                    }
                    else if(isSpace(charAtual)){
                        state = 5;
                    }
                    else{
                        throw new LexicoException("C2 - Simbolo Nao Reconhecido");
                    }
                    break;
                case 3: // teste se é um comentario
                    if(charAtual != '\n'){
                        t += charAtual;
                        state = 3;
                    }

                    else if (charAtual == '\n') {
                        t += charAtual;
                        

                        if(isNumero(charAtual)){
                            state = 0;
                        }
                        else if(isPontuacao(charAtual)){           
                            t += charAtual;            
                            state = 1;
                        }
                        else if(isOperatorAritmetico(charAtual)){
                            state = 2;
                        }
                        else if(isComentario(charAtual)){
                            state = 3;
                        }
                        else if(isFinalizacao(charAtual)){
                            state = 4;
                        }
                        else if(isSpace(charAtual)){
                            state = 5;
                        }
                        else{
                            throw new LexicoException("C3 - Simbolo Nao Reconhecido");
                        }
                    }
                    break;
                case 4:
                    if(isNumero(charAtual)){
                        state = 0;
                    }
                    else if(isPontuacao(charAtual)){           
                        t += charAtual;            
                        state = 1;
                    }
                    else if(isOperatorAritmetico(charAtual)){
                        state = 2;
                    }
                    else if(isComentario(charAtual)){
                        state = 3;
                    }
                    else if(isFinalizacao(charAtual)){
                        //state = 4;
                        tk = new Token(Token.TOKEN_FINAL, t);
                        return tk;
                    }
                    else if(isSpace(charAtual)){
                        state = 5;
                    }
                    else{
                        throw new LexicoException("C4 - Simbolo Nao Reconhecido");
                    }
                    break;
                case 5:
                    if(isNumero(charAtual)){
                        state = 0;
                    }
                    else if(isPontuacao(charAtual)){                       
                        state = 1;
                    }
                    else if(isOperatorAritmetico(charAtual)){
                        state = 2;
                    }
                    else if(isComentario(charAtual)){
                        state = 3;
                    }
                    else if(isFinalizacao(charAtual)){
                        state = 4;
                    }
                    else if(isSpace(charAtual)){
                        t += charAtual;
                        state = 5;
                    }
                default:
                    break;
            }
        }
    }

    private boolean isNumero(char c){
        return c >='0' && c <= '9';
    }

    private boolean isOperatorAritmetico(char c){
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean isPontuacao(char c){
        return c == '(' || c == ')';
    }

    private boolean isComentario(char c){
        return c == '@';
    }

    private boolean isFinalizacao(char c){
        return c == ';';
    }

    private boolean isSpace(char c){
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    private char nextChar(){
        return readContent[i++];
    }

    private char backChar(){
        return readContent[i--];
    }

    private boolean isEOF(){
        return i == readContent.length;
    }

    private void back() {
        i--;
    }

}
