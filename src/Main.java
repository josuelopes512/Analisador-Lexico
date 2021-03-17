public class Main {
    public static void main(String[] args) {
        try {
            Lexico a = new Lexico("src/input.txt");
            Token token = null;
            //a.view();
            
            do {
                token = a.nextToken();
                if(token != null){
                    System.out.println(token);
                }
               
            } while (token != null); 
        } catch (LexicoException e) {
            System.err.println("Lexico Erro!!"+ e.getMessage());
        }
        catch(Exception e){
            System.err.println("Erro JAVA System!"+ e.getMessage());
        }        
    }
}
