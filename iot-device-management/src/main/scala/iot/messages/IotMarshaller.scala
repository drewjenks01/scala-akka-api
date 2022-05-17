package com.iot.messages

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import play.api.libs.json._
import com.iot.messages.Install._
import de.heikoseeberger.akkahttpplayjson._
import spray.json.DefaultJsonProtocol




//message containing an error
case class Error(message: String)

case class InstallRequest(id: Int)


trait IotMarshaller extends SprayJsonSupport with DefaultJsonProtocol {

    implicit val errorFormat = jsonFormat1(Error)

    //implicit val deviceFormat= jsonFormat1(Install.Device)

    implicit val installFormat= jsonFormat1(InstallRequest)
}

object IotMarshaller extends IotMarshaller