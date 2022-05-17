package com.iot.messages

import akka.actor.Props
import com.iot.actors.IotManagerActor
import akka.util.Timeout

/* The manager should be able to add/remove an install, retrieve devices in an install

*/

object IotManager{

    def props() = Props(new IotManagerActor)

    //add new install
    case class NewInstall(installid: Int)

    //remove an install
    case class RemoveInstall(installid: Int)

    //retrieve devices from an install
    case class Retrieve(installid:Int)

    sealed trait InstallResponse


}