
package pruebas;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class TConsumer {

    private static String subject = "fisi_tiendautiles/mod_facturacion";
    // private static String subject = "fisi_tiendautiles/mod_facturacion";

    public static void main(String[] args) {
        while (true) {

            try {
                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                        "tcp://localhost:61616");
                Connection myConnection = connectionFactory.createConnection();
                myConnection.start();

                Session session = myConnection.createSession(false,
                        Session.AUTO_ACKNOWLEDGE);

                Destination myDestination = session.createQueue(subject);
                // Destination myDestination = session.createTopic(subject);

                MessageConsumer sendToConsumer = session.createConsumer(myDestination);

                Message myMessage = sendToConsumer.receive();

                // System.out.println(myMessage);

                if (myMessage instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) myMessage;
                    // System.out.println("Received messageCOSUMER1 '" + textMessage.getText() +
                    // "'");
                }

                if (myMessage instanceof BytesMessage) {
                    BytesMessage bytesMessage = (BytesMessage) myMessage;
                    int length = (int) bytesMessage.getBodyLength();
                    byte[] body = new byte[length];
                    int readBytes = bytesMessage.readBytes(body);
                    String text = new String(body, StandardCharsets.UTF_8);
                    System.out.println("Received message: " + text);
                }
                myConnection.close();
            } catch (JMSException ex) {
            }
        }
    }
}
