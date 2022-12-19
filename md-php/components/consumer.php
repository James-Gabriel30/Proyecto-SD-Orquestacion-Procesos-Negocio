<?php

require __DIR__ . '/../vendor/autoload.php';
include_once("connectDatabase.php");
include_once("connectMiddleware.php");


function recibirMensaje($topic, $stomp)
{
  $stomp->subscribe(
    $topic,
    null,
    'auto',
    array(
      "message-id"=>"id","priority"=>9
    )
  );

  //Listener
  while(true){
    $mensaje = $stomp->read();
    if ($mensaje !=null) {
      $msj=json_decode($mensaje->body,true);
      echo "Mensaje recibido: ";
      echo "<pre>";
      var_dump($mensaje);
      echo "</pre>";
      var_dump($msj);
      break;
      // $stomp->ack($mensaje);
    } else {
      echo "Fallo al recibir un mensaje\n";
      break;
    }
  }
  $stomp->unsubscribe();
}

// $conn_md = new ConnectMiddleware();
// $stomp = $conn_md->connect();
// recibirMensaje('ordenes/lista_articulos', $stomp);


