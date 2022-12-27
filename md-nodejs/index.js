const { db, activemq } = require('./config.json');

async function main() {
    try {
        console.log("Inicia el Programa...!");
        await Connection.connect(db);
        console.log("Conexion a la Base de Datos OK!")
        //const client = new KafkaClient({ kafkaHost: bootstrap.servers });
        const producer = new Producer(client);
        const consumer = new Consumer(client, [{ topic: activemq.topics[0].name }], { autoCommit: true });
        consumer.on('message', recieveAccountReceivable(producer, activemq.topics[1].name));
    } catch (err) {
        console.log(err);
    }

}