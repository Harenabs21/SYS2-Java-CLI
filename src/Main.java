import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Invalid command. Usage: java -jar app.jar <command> <value>");
            return;
        }
        String command = args[0];
        String value = args[1];
    
        if (command.equals("readconnection")) {
            try {
                executeCommand("psql", "-h", "localhost", "-U", "postgres", "-d", "sys2db", "-c",
                        "SELECT * FROM connection LIMIT " + value + ";");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else if (command.equals("newconnection")) {
            try {
                executeCommand("psql", "-h", "localhost", "-U", "postgres", "-d", "sys2db", "-c",
                        "INSERT INTO connection (first_name) VALUES ('" + value + "');");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid command. Available commands: readconnection, setconnection");
        }
    }
    

    private static void executeCommand(String... command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.environment().put("PGPASSWORD","psql");
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }

            int exitCode;
            try {
                exitCode = process.waitFor();
            } catch (InterruptedException e) {
                exitCode = -1;
            }

            if (exitCode == 0)
                System.out.println(output);
            else
                System.out.println("Command execution failed with exit code: " + exitCode);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
