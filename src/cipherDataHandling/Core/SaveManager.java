package cipherDataHandling.Core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SaveManager {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static <T> void save(String path, T object) {
        try {
            File file = new File(path);
            file.getParentFile().mkdirs();

            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(object, writer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T load(String path, Class<T> clazz) {
        try (FileReader reader = new FileReader(path)) {
            return gson.fromJson(reader, clazz);
        } catch (IOException e) {
            return null;
        }
    }
}