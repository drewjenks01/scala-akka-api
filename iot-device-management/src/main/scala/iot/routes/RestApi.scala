package com.iot.routes

import akka.actor.{ActorRef, ActorSystem}
import akka.util.Timeout
import akka.pattern.ask
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.iot.messages.IotManager._
import com.iot.messages.IotMarshaller._
import com.iot.messages._


import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import StatusCodes._



class RestApi(system: ActorSystem, timeout: Timeout) extends RestRoutes {
    implicit val requestTimeout: Timeout = timeout
    implicit def executionContext: ExecutionContextExecutor = system.dispatcher

    def createManager():ActorRef = system.actorOf(IotManager.props)
}

trait RestRoutes extends ManagerApi with IotMarshaller {
    val service="iot-manager"
    val version = "v1"


    //endpoint for creating an installation with devices
    protected val createInstallRoute: Route = {
        pathPrefix(service / version / "installations" / Segment) {install =>
            post {
                pathEndOrSingleSlash {
                    // POST iot-manager/v1/installs/install_id
                    entity(as[InstallRequest]){ ir =>
                        onSuccess(createInstall(ir.id)){ num =>
                        complete(OK)
                        }
                     }
            
                    }
                }
            
            }
            
        }
        
    
        val routes: Route = createInstallRoute




    }


trait ManagerApi {

    def createManager(): ActorRef

    implicit def executionContext: ExecutionContext
    implicit def requestTimeout: Timeout

    lazy val manager: ActorRef = createManager()

    def createInstall(id:Int): Future[InstallResponse]={ 
        manager.ask(NewInstall(id)).mapTo[InstallResponse]}
}