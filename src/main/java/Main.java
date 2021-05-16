import java.io.*;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gamer1 = new GameProgress(100, 5, 10, 108);
        GameProgress gamer2 = new GameProgress(99, 3, 2, 10);
        GameProgress gamer3 = new GameProgress(10, 7, 100, 1500);
        saveGame("E:\\Games\\savegames\\gamer1.dat", gamer1);
        saveGame("E:\\Games\\savegames\\gamer2.dat", gamer2);
        saveGame("E:\\Games\\savegames\\gamer3.dat", gamer3);

        //по хорошему нарна надо бы подцепить соседнюю домашку
        File savegames = new File("E:\\Games\\savegames");
        //директорию назовем, но создавать не будем, она уже там есть

        //вообще куда удобнее передать директорию с сохранениями, что бы спокойно дергать и путь и название и удалить потом файлы
        zipFiles("E:\\Games\\zip.zip", savegames);
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String pathZip, File savegames) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathZip))//путь будет лежать .зип
        ) {
            for (File file : Objects.requireNonNull(savegames.listFiles()))
                //читаем файлы в директории
                try (FileInputStream fis = new FileInputStream(file.getAbsoluteFile())) {
                    //записываем файл в архив, с новым названием, в данном случае таким же, как и старое
                    ZipEntry entry = new ZipEntry(file.getName());
                    zout.putNextEntry(entry);
                    // считываем содержимое файла в массив byte
                    byte[] buffer = new byte[fis.available()];
                    if (fis.read(buffer) == -1)
                        System.out.println("где-то косяк");
                    // добавляем содержимое к архиву
                    zout.write(buffer);
                    // закрываем текущую запись для новой записи
                    zout.closeEntry();
                }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        //пробегаемся по директории и удаляем все файлы
        for (File item : Objects.requireNonNull(savegames.listFiles())) {
            if (!item.delete())
                System.out.println("Файл " + item.getName() + " не был удален");
        }
    }
}

