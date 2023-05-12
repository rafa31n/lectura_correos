import util.ReadProperties;

import javax.mail.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            String host = String.valueOf(ReadProperties.getProperty("mail.host"));
            String username = String.valueOf(ReadProperties.getProperty("mail.username"));
            String password = String.valueOf(ReadProperties.getProperty("mail.password"));
            String port = String.valueOf(ReadProperties.getProperty("mail.port"));
            String protocol = String.valueOf(ReadProperties.getProperty("mail.protocol"));
            String rutaDestino = String.valueOf(ReadProperties.getProperty("mail.rutaDestino"));

            Properties properties = new Properties();
            properties.put("mail.store.protocol", protocol);
            properties.put("mail.pop3s.host", host);
            properties.put("mail.pop3s.port", port);
            System.out.println(properties);
            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore(protocol);
            store.connect(host, username, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length: " + messages.length);
            String html = "";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            int indice = 1;
            System.out.println(formatter.format(date));

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                message.setFlag(Flags.Flag.SEEN, false);
                Multipart mp = (Multipart) message.getContent();
                BodyPart bp = mp.getBodyPart(0);
                html = "<div><p>" + bp.getContent() + "</p></div>";
                File file = new File(rutaDestino + formatter.format(date) + "_" + indice + ".html");
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                bufferedWriter.write(html);
                bufferedWriter.close();
                indice++;
            }
            emailFolder.close(true);
            store.close();
            System.out.println("Archivo guardado!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
