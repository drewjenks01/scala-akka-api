package com.iot.actors

import akka.actor._
import akka.pattern.{ask, pipe}
import akka.util.Timeout
import com.iot.messages.IotManager._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

//create the Device Actor
class IotManagerActor extends Actor {
    import com.iot.messages.Install

    //create Installation child
    def createInstallActor(id:Int):ActorRef = {
        context.actorOf(Install.props(id),id.toString)
    }

    //how to handle different messages
    def receive ={

        // add new install
        case NewInstall(installid) =>
            val install = createInstallActor(installid)

       // remove an install
       // ??? -> how are list of installs stored

       case Retrieve(installid) =>
           def get(child: ActorRef) = child forward Install.RetrieveDevices

           context.child(installid.toString).get


        




    }

}
