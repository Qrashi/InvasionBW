package me.qrashi.plugins.bedwars.Utils;

import me.qrashi.plugins.bedwars.BedWars;

import java.io.*;

public final class FileManager {

    public void saveToFile(Object toSave, String name) throws IOException {
        String filePath = BedWars.getPlugin(BedWars.class).getDataFolder().getPath() + name;
        File file = new File(filePath);
        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream s = new ObjectOutputStream(f);
        s.writeObject(toSave);
        s.close();
    }

    public Object loadFromFile(String name) throws IOException, ClassNotFoundException {
        String filePath = BedWars.getPlugin(BedWars.class).getDataFolder().getPath() + name;
        File file = new File(filePath);
        FileInputStream f = new FileInputStream(file);
        ObjectInputStream s = new ObjectInputStream(f);
        Object toReturn = s.readObject();
        s.close();
        return toReturn;
    }

}
