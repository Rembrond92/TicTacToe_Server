package Server;

public class Player {
       
    private String name;
    private int victories;
    private int defeats;
    private int draws;
    private Symbol symbol;
    private int id;
    
    public Player(String name, Symbol s, int id) {
        this.name = name;
        this.symbol = s;
        this.id = id;
    }
    
    public Player(String name, int v, int def, int dr) {
        this.name = name;
        this.victories = v;
        this.defeats = def;
        this.draws = dr;
    }
    
    public Player(String name, int v, int def, int dr, Symbol s, int id) {
        this.name = name;
        this.victories = v;
        this.defeats = def;
        this.draws = dr;
        this.symbol = s;
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void addVictories() {
        this.victories += 1;
    }
    
    public int getVictories() {
        return this.victories;
    }
    
    public void addDefeats() {
        this.defeats += 1;
    }
    
    public int getDefeats() {
        return this.defeats;
    }
    
    public void addDraws() {
        this.draws += 1;
    }
    
    public int getDraws() {
        return this.draws;
    }
    
    public void setSymbol(Symbol s) {
        this.symbol = s;
    }
    
    public Symbol getSymbol() {
        return this.symbol;
    }
    
    public void setID(int i) {
        this.id = i;
    }
    
    public int getID() {
        return this.id;
    }
}