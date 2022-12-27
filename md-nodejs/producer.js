const Stomp = require("stomp-client");
const stompClient = new Stomp("127.0.0.1", 61613);

console.log("Enviando Mensaje ...")
    const message = {
        estado: 1,
        mensaje: `Su pedido se ha registrado con exito!.\n La fecha del pago esta programada para el dia `
    }
    const data = [
        { topic: topicName, messages: JSON.stringify(message) }
    ];
    console.log(data);
    producer.send(data, function (err, resp) {
        console.log(resp);
    });
    producer.on('error', function (err) {
        console.log(err);
    });
