const Stomp = require("stomp-client");
const stompClient = new Stomp("127.0.0.1", 61613);

stompClient.connect(function (sessionId) {
  console.log("consumer connected");

  stompClient.subscribe("/queue/notifications", function (body, headers) {
    console.log(body);
    console.log(headers);
    //stompClient.disconnect();
  });
  setTimeout(function(){
    //stompClient.publish("/queue/notifications", 'oh herrow!');
     stompClient.disconnect();
  }, 90000000);

});
