package com.iot.actors

import akka.actor._
import com.iot.messages.IotManager
import scala.collection.mutable.Map
import scala.collection.mutable.ArrayBuffer


class InstallActor(installid: Int) extends Actor {

    import com.iot.messages.Install._

    //list of devices in install mapping to their signals
    var devices=Map.empty[Int, ArrayBuffer[Int]]

    def receive = {

        //add a new device
        case AddDev(devid) =>
            devices += devid -> new ArrayBuffer[Int]()

        //add a signal to a device
        case AddSig(devid,signal) =>
            devices(devid)+= signal

        //remove a device from the installation
        case Remove(devid)=>
            devices=devices.-(devid)


        //retrieve list of devices in installation
        case RetrieveDevices =>
            sender() ! devices

    }



}
