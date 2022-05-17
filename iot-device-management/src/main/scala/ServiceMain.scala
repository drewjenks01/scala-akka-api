package com.iot

import scala.concurrent.{ExecutionContextExecutor, Future}
import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.iot.routes.RestApi
import com.typesafe.config.{Config, ConfigFactory}

object ServiceMain extends App with RequestTimeout{

    // from application.conf
    val config = ConfigFactory.load()

    // gets host and port from the config
    val host = config.getString("http.host")
    val port = config.getInt("http.port")

    // what is an implicit val?

    //ActorMaterializer requires an implicit actorsystem
    implicit val system: ActorSystem = ActorSystem()
    
    // bindingFuture.map requires an implicit ExecutionContext
    implicit val ec : ExecutionContextExecutor = system.dispatcher

    //bindAndHandle requires an implicit materializer
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    val api=new RestApi(system,requestTimeout(config)).routes
    // val route =
    //   path("hello") {
    //     get {
    //       complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
    //     }
    //   }


    val bindingFuture:  Future[ServerBinding]=Http().bindAndHandle(api,host, port)

    val log=Logging(system.eventStream,"iot-manager")

    // try{
    //     bindingFuture.map { serverBinding =>
    //         log.info(s"RestApi bound to ${serverBinding.localAddress}")}
            
    // } 



}

trait RequestTimeout {
  import scala.concurrent.duration._
  def requestTimeout(config: Config): Timeout = {
    val t = config.getString("akka.http.server.request-timeout")
    val d = Duration(t)
    FiniteDuration(d.length, d.unit)
  }
}