import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length > 1){
            System.out.println("only takes 1 config file");
        }
        else if (args.length == 1) {
            Path configFile = Paths.get(args[0]);
            try {
                System.out.println("Executing commands in file...\n");
                List<String> commands = Files.readAllLines(configFile, Charset.defaultCharset());
                Iterator iCommands = commands.iterator();

                while (iCommands.hasNext()){
                    String command = (String) iCommands.next();
                    List<Token> tokens = tokenize(command);

                    for(Token token: tokens) {
                        //System.out.println(token.tokenName.toString() + " --> " + token.tokenValue);
                        switch(token.tokenName){
                            case cd:
                            case pushb:
                            case popb:
                            case TEXT:
                            case FILENAME:
                            case GREATER:
                            case LESSER:
                            default:
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Lexical Analysis with DFSA
    public static List<Token> tokenize(String command) {
        int previousState = 0;

        List<Token> tokens = new ArrayList<Token>();
        ShellDFA dfa = new ShellDFA();

        //go through each character in command
        int baseIndex = 0;
        for (int i = 0; i < command.length(); i++){
            String c = command.substring(i, i + 1);

            previousState = dfa.changeState(c);
            //System.out.println(previousState);
            //System.out.println(dfa.getCurrentState());

            if(dfa.isEndState()){
                //we're in an end state, so create a new token
                String stringToken = command.substring(baseIndex, i);
                stringToken = stringToken.trim();
                stringToken = stringToken.replace( "\"" , "" );

                if (previousState == 0) {
                    //GREATER or LESS
                    if(c.equals(">")){
                        tokens.add(new Token("GREATER",">"));
                        baseIndex = i+1;
                    }
                    else if(c.equals("<")){
                        tokens.add(new Token("LESS","<"));
                        baseIndex = i+1;
                    }
                }
                else if (previousState == 1) {
                    //QUOTE
                    tokens.add(new Token("TEXT", stringToken));
                }
                else if (previousState == 2) {
                    //TEXT
                    tokens.add(new Token("FILENAME", stringToken));
                    //GREATER or LESS
                    if(c.equals(">")){
                        tokens.add(new Token("GREATER",">"));
                        baseIndex = i+1;
                    }
                    else if(c.equals("<")){
                        tokens.add(new Token("LESS","<"));
                        baseIndex = i+1;
                    }
                }
                baseIndex = i+1;
                dfa.restartDFA();
            }
        }
        if(baseIndex < command.length()){
            if (previousState == 1) {
                System.out.println("SYNTAX ERROR");
            }
            else {
                tokens.add(new Token("TEXT", command.substring(baseIndex,command.length()).trim().replace("\"", "")));
            }
        }

        for(Token t: tokens) {
            commandFilter(t);
        }

        return tokens;
    }

    private static void commandFilter(Token t){
        //currently checks for cd, pushb, and popb
        //also, these are CASE SENSITIVE! (for now...too lazy)
        if (t.tokenValue.equals("cd")) {
            t.tokenName = Tokens.cd;
        }
        else if (t.tokenValue.equals("pushb")) {
            t.tokenName = Tokens.pushb;
        }
        else if (t.tokenValue.equals("popb")) {
            t.tokenName = Tokens.popb;
        }
    }
}
