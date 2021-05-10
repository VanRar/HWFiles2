import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gamer1 = new GameProgress(100, 5, 10, 108);
        GameProgress gamer2 = new GameProgress(99, 3, 2, 10);
        GameProgress gamer3 = new GameProgress(10, 7, 100, 1500);
        saveGame("E:\\Games\\savegames\\gamer1.dat", gamer1);
        saveGame("E:\\Games\\savegames\\gamer2.dat", gamer2);
        saveGame("E:\\Games\\savegames\\gamer3.dat", gamer3);

    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

