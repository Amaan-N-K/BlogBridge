package org.project.data_access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.project.use_case.login.LoginDataAccessInterface;
import org.project.use_case.signup.SignupDataAccessInterface;
import org.springframework.stereotype.Repository;

@Repository
public class SignupDataAccessObject implements SignupDataAccessInterface, LoginDataAccessInterface {
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

    @Override
    public boolean doesUsernameExist(String username) {
        return usernameExists(username);
    }

    @Override
    public boolean isPasswordCorrect(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
