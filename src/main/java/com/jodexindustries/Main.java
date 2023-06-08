package com.jodexindustries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static String webhook = "";
    public static void main(String[] args) {
        try {
            for (int i = 0; i < args.length; i++) {
                if(args[i].equalsIgnoreCase("-help")) {
                    System.out.println("-webhook [link] - set webhook link");
                    System.exit(0);
                }
                if (args[i].equalsIgnoreCase("-webhook")) {
                    webhook = args[i + 1];
                }
            }
            if(webhook.equals("")) {
                System.out.println("WebHook link is null! Set it: java -jar MCmdLoggerRuntime.jar -webhook \"link\" ");
                System.exit(0);
            }
            File file = new File("mcmdlogger.log");
            if(!file.exists()) {
                file.createNewFile();
            }
            FileReader fileReader = new FileReader(file);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            DiscordWebhook discordWebhook;
            discordWebhook = new DiscordWebhook(webhook);
            while (true) {
                if (bufferedReader.ready()) {
                    while ((line = bufferedReader.readLine()) != null) {
                        if(line.toLowerCase().contains("player") || line.toLowerCase().contains("chat")) {
                            discordWebhook.setUsername("MCmdLogger");
                            discordWebhook.setContent(line);
                            discordWebhook.execute();
                        }
                    }
                } else {
                    Thread.sleep(1000);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
