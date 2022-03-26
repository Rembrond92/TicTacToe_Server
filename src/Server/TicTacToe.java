package Server;

public class TicTacToe {
    
    Step[] steps = new Step[9];
    static int countStep = 0;
    static int[][] map;
    static Player playerOne, playerTwo;
    static TicTacToe game;
    static boolean exit = false, end = false;
    
    public TicTacToe(Player one, Player two) {
        playerOne = one;
        playerTwo = two;
        
        game = this;
        map = new int[3][3];
        exit = false; end = false;
    }
    
    public static void start() throws Exception {
       
        try {
            
            if(game == null) {
                login();
            } 
        
            else {
                Server.println(
                "Хотите поменять игроков? \n"+
                "Да? Введите: «д»\n"+
                "Нет? Введите любой другой символ.");
                
                if(Server.readLine().equals("д")) {
                    playerOne = null;
                    playerTwo = null;
                    login();
                }
            }
            new TicTacToe(playerOne, playerTwo);
        
            Main.parser.setPlayers(playerOne.getName(), playerTwo.getName());
    
        } catch(Exception e) {e.printStackTrace();}
    }
    
    public static void login() {
          
        try {
            
            final String nameOne, nameTwo;
        
            Server.println("Введите имя первого игрока.");
            nameOne = Server.readLine();
            Server.println("Введите имя второго игрока.");
            nameTwo = Server.readLine();
        
            for(Player person: RatingGame.list) {
                if(person.getName().equals(nameOne)) 
                    playerOne = person;//new Player(nameOne, person.victories, person.defeats, person.draws);
                
                if(person.getName().equals(nameTwo))
                    playerTwo = person;//new Player(nameTwo, person.victories, person.defeats, person.draws);
            }
        
            if(playerOne == null) {
                playerOne = new Player(nameOne, 0, 0, 0);
                playerOne.setSymbol(Symbol.X);
                playerOne.setID(1);
                RatingGame.list.add(playerOne);
            }
            if(playerTwo == null) {
                playerTwo = new Player(nameTwo, 0, 0, 0);
                playerTwo.setSymbol(Symbol.O);
                playerTwo.setID(2);
                RatingGame.list.add(playerTwo);
            }
        }
        catch(Exception e) {e.printStackTrace();}
    }
    
    public static void game() throws Exception {
        
        showMap();
        countStep = 0;
        while(!end) {
            for(int i = 1; i <= 2 && !end; i++) {
                
                if(i == 1) {
                    Server.println("Первый игрок - " + playerOne.getName() + " делает ход. Введите номер свободной ячейки.");
                    move(playerOne);
                }
                else {
                    Server.println("Второй игрок - " + playerTwo.getName() + " делает ход. Введите номер свободной ячейки.");
                    move(playerTwo);
                }
                
                if(++countStep == 9 && !end) {
                    Server.println("Ничья!");
                    
                    playerOne.addDraws();
                    playerTwo.addDraws();
                    
                    Main.parser.gameResult(null);
                    
                    end = true;
                    break;
                }
            }
        }
    }
    
    public static void move(Player player) {
        
        setValue(player.getID());
        showMap();
        testMap(player.getID());
        if(end) {
            gameResult(player);
        }
    }
    
    public static void gameResult(Player player) {
        
        Server.println("Игрок " + player.getName() + ", играя за '" + player.getSymbol() + "', победил!");
        player.addVictories();
        player.addDefeats();
        
        
        Main.parser.gameResult(player);

    }
    
    public static void showMap() {
        
        int count = 1;
                
        for(int i = 0; i < 3; i++) {
            
            StringBuilder lineMap = new StringBuilder();

            for(int j = 0; j < 3; j++) {
                lineMap.append("[");
                if(map[i][j] == 0)
                    lineMap.append(count);
                if(map[i][j] == 1)
                    lineMap.append("X");
                if(map[i][j] == 2)
                    lineMap.append("O");
                lineMap.append("]");
                count++;
            }
            Server.print(lineMap);
        }
    }
    
    public static void setValue(int player) {
        
        try {
            
            int set = Integer.parseInt(Server.readLine());
            int count = 1;
            
            if(set > 9 || set < 1)
                Server.println("Неверный номер ячейки!");
            
            else {
                
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        if(set == count) {
                            if(map[i][j] == 0) {
                                map[i][j] = player;
                            
                                Main.parser.setStep(countStep + 1, player, String.valueOf(set));
                                return;
                            }
                            else {
                                Server.println("Ячейка уже занята!");
                                
                            }
                        }
                        count++;
                    }
                }
            }
            
        } catch (Exception e) {
            //e.printStackTrace();
            Server.println("Неверный номер ячейки!");
        }
        
        Server.println("Попробуйте ещё раз.");
        setValue(player);
    }
    
    public static void testMap(int player) {
        
        if(map[0][0] == player &&
           map[1][1] == player &&
           map[2][2] == player) {
                
            end = true;
            return;
        }
        
        if(map[0][2] == player &&
           map[1][1] == player &&
           map[2][0] == player) {
            
            end = true;
            return;
        }
        
        for(int i = 0; i < 3; i++) {
            if(map[0][i] == player &&
               map[1][i] == player &&
               map[2][i] == player) {
                    
                end = true;
                return;
            }
            
            if(map[i][0] == player &&
               map[i][1] == player &&
               map[i][2] == player) {
                    
                end = true;
                return;
            }
        }
    }
}