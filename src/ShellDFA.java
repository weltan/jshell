import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: uKen
 * Date: 10/19/13
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShellDFA {
    private State currentState;
    private State state0;
    private State state1;
    private State state2;
    private State state3;

    public ShellDFA() {
        //here's where we hardcode the actual DFA for the shell
        state0 = new State(0, false);
        state1 = new State(1, false);
        state2 = new State(2, false);
        state3 = new State(3, true);

        state0.addStateLink(Symbols.SPACE, state0);
        state0.addStateLink(Symbols.QUOTE, state1);
        state0.addStateLink(Symbols.OTHER, state2);
        state0.addStateLink(Symbols.GREATER, state3);
        state0.addStateLink(Symbols.LESS, state3);

        state1.addStateLink(Symbols.GREATER, state1);
        state1.addStateLink(Symbols.LESS, state1);
        state1.addStateLink(Symbols.SPACE, state1);
        state1.addStateLink(Symbols.OTHER, state1);
        state1.addStateLink(Symbols.QUOTE, state3);

        state2.addStateLink(Symbols.OTHER, state2);
        state2.addStateLink(Symbols.QUOTE, state2);
        state2.addStateLink(Symbols.SPACE, state3);
        state2.addStateLink(Symbols.GREATER, state3);
        state2.addStateLink(Symbols.LESS, state3);

        currentState = state0;
    }

    //returns the previous state as an integer
    public int changeState(String c) {
        assert (c.length() == 1);
        Symbols symbol = convertToSymbol(c);

        // save the previous state for the return
        int prevState = currentState.getNumber();
        //change state
        currentState = currentState.getStateLinks().get(symbol);

        return prevState;
    }

    private Symbols convertToSymbol(String c) {
        if (c.equals(Symbols.GREATER.getString()) || c.equals(Symbols.LESS.getString()) ||
                c.equals(Symbols.QUOTE.getString()) || c.equals(Symbols.SPACE.getString())) {
            Symbols symbol = Symbols.convertToSymbol(c);
            assert(symbol != null);
            return symbol;
        } else {
            return Symbols.OTHER;
        }
    }

    public boolean isEndState(){
        return currentState.isEndState;
    }

    public void restartDFA (){
        currentState = state0;
    }

    public int getCurrentState(){
        return currentState.getNumber();
    }

    private class State {
        private int number;
        private HashMap<Symbols, State> stateLinks;
        public boolean isEndState;

        public State(int number, boolean isEndState) {
            this.number = number;
            this.isEndState = isEndState;
            stateLinks = new HashMap<Symbols,State>();
        }

        //current symbols are GREATER, LESS, QUOTE, SPACE, and OTHER (for everything else)
        public HashMap addStateLink(Symbols symbol, State nextState) {
            stateLinks.put(symbol, nextState);
            return stateLinks;
        }

        public int getNumber() {
            return number;
        }

        public HashMap<Symbols, State> getStateLinks() {
            return stateLinks;
        }
    }
}
