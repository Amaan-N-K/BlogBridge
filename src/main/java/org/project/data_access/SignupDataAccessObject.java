package org.project.data_access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.project.use_case.SignupDataAccessInterface;
import org.springframework.stereotype.Repository;

@Repository
public class SignupDataAccessObject implements SignupDataAccessInterface {
    private static final String CSV_FILE_PATH = "users.csv";

    public SignupDataAccessObject() {
        try {
            // Check if the file exists, if not create it
            Path path = Paths.get(CSV_FILE_PATH);
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.split(",")[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
