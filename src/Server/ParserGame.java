package Server;

interface ParserGame {
    
    void setPlayers(String one, String two);
    
    void setStep(int count, int id, String text);
    
    void gameResult(Player player);
    
    void write();
    
    void replay();
    
    void read(String name);
    
    void readNames();
    
}
