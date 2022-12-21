/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import com.google.gson.Gson;
import fisiutiles.facturacion.Item;
import fisiutiles.facturacion.Mensaje;
import fisiutiles.facturacion.Pedido;
import fisiutiles.facturacion.Productor;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.artemis.jms.client.ActiveMQConnection;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 *
 * @author juanp
 */
public class TProducer {

    public static void main(String[] args) {
        
        ArrayList<Item> lista = new ArrayList<>();
        
        Item it1 = new Item();
        it1.setDescripcion("1");
        it1.setCantidad(5);
        it1.setPrecioUnitario(12.3);
        
        Item it2 = new Item();
        it2.setDescripcion("2");
        it2.setCantidad(4);
        it2.setPrecioUnitario(8.7);
        
        Item it3 = new Item();
        it3.setDescripcion("3");
        it3.setCantidad(2);
        it3.setPrecioUnitario(11.6);
        
        lista.add(it1);
        lista.add(it2);
        lista.add(it3);
        
        Pedido p = new Pedido();
        p.setCodigoDeCliente("18200038");
        p.setNombreDeCliente("Machin Alberto");
        p.setRucDeCliente("ruc-555");
        p.setItems(lista);
        
        Mensaje msj = new Mensaje();
        msj.setEstado(0);
        msj.setContenido(new Gson().toJson(p));
        
        ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");

        try ( Connection con = cf.createConnection()) {
            con.start();

            Session ssn = con.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination dtn = ssn.createTopic("fisi_tiendautiles/mod_facturacion");

            MessageProducer mp = ssn.createProducer(dtn);

            String json_msj = new Gson().toJson(msj);

            TextMessage tm = ssn.createTextMessage(json_msj);

            mp.send(tm);
        } catch (JMSException ex) {
            Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}