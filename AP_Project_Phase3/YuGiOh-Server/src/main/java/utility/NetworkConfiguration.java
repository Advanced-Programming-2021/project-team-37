package utility;

import org.ini4j.Ini;

import java.io.File;
import java.io.FileReader;

public class NetworkConfiguration {

    private static int port = 8001;
    private static String host = "localhost";


    public static void loadFromIni() {

        try {
            Ini ini = new Ini(new FileReader(new File("src/main/java/utility/config.ini")));
            Ini.Section section = ini.get("server");

            String port = section.get("port");
            String host = section.get("host");

            setPort(port);
            setHost(host);

        } catch (Exception e) {
            port = 8001;
            host = "localhost";
        }


    }


    public static int getPort() {
        return port;
    }

    public static void setPort(String port) {
        NetworkConfiguration.port = Integer.parseInt(port);
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        if (host.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))|localhost$")) {
            NetworkConfiguration.host = host;
        }
    }
}
