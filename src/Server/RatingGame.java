package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class RatingGame {
    
    final static ArrayList<Player> list = new ArrayList<>();
    
    public static void show() {
        
        Server.println("Рейтинг игроков:");
        
        for(Player person: list)
            Server.print(createStatistics(person));
        
        Server.println("***");
    }
    
    public static void deletePlayer() {
        
        try {
            
            Server.println("Введите имя удаляемого игрока.");
            String name = Server.readLine();
            boolean ok = false;
        
        
            for(Iterator<Player> it = list.listIterator(); it.hasNext();) {
                if(it.next().getName().equals(name)) {
                    it.remove();
                    ok = true;
                    writeFile();
                
                    Server.println("Игрок успешно удален.");
                }
            }
        
            if(!ok) 
                Server.println("Игрок не найден.");
        } 
        catch (Exception e) {e.printStackTrace();}
    }
    
    public static void reset() {
        
        list.clear();
        writeFile();
        
        Server.println("Рейтинг очищен.");
    }
    
    public static void readFile() {
        
        list.clear();
        
        try(BufferedReader fileReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/rating.txt"))) {
            while(fileReader.ready()) {
                
                String[] line = fileReader.readLine().split(";");
                list.add(new Player(line[0].split("-")[1],
                                    Integer.parseInt(line[1].split("-")[1]),
                                    Integer.parseInt(line[2].split("-")[1]),
                                    Integer.parseInt(line[3].split("-")[1])));
        
            }
        } catch(Exception e) {
            Server.println("Файл рейтинга не был загружен.");
            //e.printStackTrace();
        }
    }
    
    public static void writeFile() {
        
        try(BufferedWriter fileWriter = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/rating.txt"))) {
            for(Player person: list)
                fileWriter.write(createStatistics(person));
        
        } catch(Exception e) {
            Server.println("Ошибка записи файла");
            e.printStackTrace();}
    }
    
    private static String createStatistics(Player person) {
        return "Имя-" + person.getName()
           + "; Побед-" + person.getVictories()
           + "; Поражений-" + person.getDefeats()
           + "; Ничьих-" + person.getDraws() + "\n";
    }
}