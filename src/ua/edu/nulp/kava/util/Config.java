package ua.edu.nulp.kava.util;

import ua.edu.nulp.kava.data.DbConfig;

import java.io.File;
import java.util.List;

public class Config {
    private static final File DB_CONFIG_FILE = FileOps.getDbConfigFile();

    public static DbConfig getDbConfig() {
        List<String> lines = FileOps.getFileLines(DB_CONFIG_FILE);

        if (lines == null || lines.size() < 5) {
            return null;
        }

        String host = lines.get(0);
        int port = Utils.strToInt(lines.get(1));
        String username = lines.get(2);
        String password = lines.get(3);
        String dbName = lines.get(4);

        return new DbConfig(host, port, username, password, dbName);
    }

    public static boolean saveDbConfig(DbConfig dbConfig) {
        String content = dbConfig.getHost() + "\n";
        content += dbConfig.getPort() + "\n";
        content += dbConfig.getUsername() + "\n";
        content += dbConfig.getPassword() + "\n";
        content += dbConfig.getDbName() + "\n";

        return FileOps.writeFile(DB_CONFIG_FILE, content, false);
    }
}
