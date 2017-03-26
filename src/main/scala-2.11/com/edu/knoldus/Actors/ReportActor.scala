package com.edu.knoldus.Actors

import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.{Actor, ActorLogging, Props}
import com.edu.knoldus.storage.LocalDatabase

import scala.concurrent.duration._

class ReportActor  extends Actor with ActorLogging {


  override def receive = {

                            case "getReport" => {
                              context.system.scheduler.scheduleOnce(5.minutes, self, "getReport")

                              val users = LocalDatabase.userInfo
                              val bills = LocalDatabase.linkedBills

                              log.info("" + users)
                              log.info("" + bills)

                            }



  }

}

object ReportActor {

  def prop:Props = Props[ReportActor]

}


