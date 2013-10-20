/**
 * Created with IntelliJ IDEA.
 * User: uKen
 * Date: 10/12/13
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Token {
    public Tokens tokenName;
    public String tokenValue;

    public Token (String tokenName){
        this.tokenName = Tokens.valueOf(tokenName);
        this.tokenValue = tokenName;
    }

    public Token (String tokenName, String tokenValue) {
        this.tokenName = Tokens.valueOf(tokenName);
        this.tokenValue = tokenValue;
    }
}
