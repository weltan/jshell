/**
 * Created with IntelliJ IDEA.
 * User: uKen
 * Date: 10/19/13
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Symbols {
    GREATER(">"),LESS("<"),QUOTE("\""),SPACE(" "),OTHER("");

    private String value;

    private Symbols(String value)
    {
        this.value = value;
    }

    public String getString()
    {
        return this.value; //will return , or ' instead of COMMA or APOSTROPHE
    }

    public static Symbols convertToSymbol(String c){
        if(c.equals(Symbols.GREATER.getString())){
            return Symbols.GREATER;
        }
        else if (c.equals(Symbols.LESS.getString())){
            return Symbols.LESS;
        }
        else if (c.equals(Symbols.QUOTE.getString())) {
            return Symbols.QUOTE;
        }
        else if (c.equals(Symbols.SPACE.getString())) {
            return Symbols.SPACE;
        }
        else {
            return null;
        }
    }
}
