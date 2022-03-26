package Server;

/*
 * Игра крестики-нолики
 * Шаги:
 * Приветствие;
 * Логин игроков;
 * Ход игрока + вывод поля;
 * Объявление победителя;
 * Занесение в файл;
 * Предложение сыграть ещё раз;
 * Запрос на изменение имён игроков;
 */

public class Main {
    
    //static Parser parser = new XmlFile();
    static ParserGame parser;
        
    static boolean exit;
    
    public static void main() {
        
        parser = new JsonFile();
        exit = false;
        try {
                
            Server.println("Приветствуем вас в игре крестики-нолики!");
            String choice;
            RatingGame.readFile();
            
            while(!exit) {
                Server.println(
                "Начать новую игру? Введите: «н»\n"+
                "Посмотреть записи игр? Введите «з»\n"+
                "Посмотреть рейтинг игроков? Введите: «р»\n"+
                "Выйти из игры? Введите: «в»");
                
                choice = Server.readLine();
                
                switch (choice) {
                    
                    case "н":
                        TicTacToe.start();
                        TicTacToe.game();
                        RatingGame.writeFile();
                        parser.write();
                        break;
                
                    case "р":
                        RatingGame.show();
                        Server.println(
                        "Удалить игрока? Введите: «у»\n"+
                        "Обнулить рейтинг? Введите: «о»\n"+
                        "Выйти в меню? Введите любой другой символ.");
                   
                        choice = Server.readLine();

                        if(choice.equals("у"))
                            RatingGame.deletePlayer();
                        else if(choice.equals("о"))
                            RatingGame.reset();
                        break;
                
                    case "з":
                        parser.replay();
                        break;
                    
                    case "в":
                        Server.println("Выход из игры.");
                        TicTacToe.game = null;
                        exit = true;
                        break;
                    
                    default:
                        Server.println("Неверный выбор! Попробуйте ещё раз.");
                        break;
                } 
            }
            
        } catch(Exception e) { 
            e.printStackTrace();
            Server.println("Упс! Что-то пошло не так!");
        }
    }
}