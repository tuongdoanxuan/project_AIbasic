package Model;

import java.io.*;
import java.util.*;

public class EmailStorage {
    private static final String FOLDER = "data";

    public static void saveEmail(String receiver, Email email) {
        try {
            File folder = new File(FOLDER);
            if (!folder.exists()) folder.mkdir();
            File file = new File(folder, receiver + "_inbox.csv");
            FileWriter fw = new FileWriter(file, true);
            fw.write(email.getFrom() + "," + email.getSubject().replace(",", ";") + "," +
                     email.getBody().replace(",", ";") + "," + email.isSpam() + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Email> loadEmails(String user) {
        List<Email> list = new ArrayList<>();
        File file = new File(FOLDER + "/" + user + "_inbox.csv");
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", 4);
                if (p.length == 4)
                    list.add(new Email(p[0], user, p[1], p[2], Boolean.parseBoolean(p[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
