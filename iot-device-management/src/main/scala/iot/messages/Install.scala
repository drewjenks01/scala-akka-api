package com.iot.messages

import akka.actor.Props
import com.iot.actors.InstallActor
import akka.util.Timeout

/* An install should be able to retrieve its device ids and
add/remove devices
*/

object Install{

    //method to create the actor
    def props(id:Int)=Props(new InstallActor(id))

    // message to add a device
    case class AddDev(id:Int)

    // message to add a signal to a device
    case class AddSig(id:Int, sig: Int)

    //message to remove a device from an install
    case class Remove(id:Int)

    // message to retrieve devices from specific installl
    case object RetrieveDevices

    //a device
    case class Device(id:Int, signals: Vector[Int]=Vector.empty[Int])



}