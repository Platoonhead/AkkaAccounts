package com.edu.knoldus

import akka.actor.ActorSystem
import akka.routing.FromConfig
import com.edu.knoldus.Actors.{AccountGeneratorActor, BillLinker, ReportActor, SalaryDepositServiceActor}
import com.edu.knoldus.models.{CaseAccount, CaseBill, ELECTRICITY, INTERNET}
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._
import scala.language.postfixOps

object Initializer extends App {

  val employees = ConfigFactory.parseString(
    """
      |akka.actor.deployment {
      | /poolRouter {
      |   router = round-robin-pool
      |   nr-of-instances = 8
      | }
      |}
    """.stripMargin
  )

  val system = ActorSystem("Banking", employees)
  val accountGeneratorActorRef = system.actorOf(FromConfig.props(AccountGeneratorActor.prop), "poolRouter")
  val BillLinkerRef = system.actorOf(BillLinker.prop)
  val salaryDepositServiceActorRef = system.actorOf(SalaryDepositServiceActor.prop)
  val reportGeneratorRef = system.actorOf(ReportActor.prop)

  for (i <- 1 to 10) {
    accountGeneratorActorRef ! CaseAccount("name" + i, "address" + i, "username" + i)
  }

  import system.dispatcher



  system.scheduler.scheduleOnce(200 milliseconds) {
    BillLinkerRef ! ("username10",List(CaseBill(INTERNET,"MTNL","30/12/2016",500,5,0,0),CaseBill(ELECTRICITY,"BSES","30/12/2016",400,5,0,0)))
    BillLinkerRef ! ("username5",List(CaseBill(INTERNET,"MTNL","30/12/2016",800,8,0,0)))
    BillLinkerRef ! ("username3",List(CaseBill(INTERNET,"MTNL","30/12/2016",600,6,0,0)))
    BillLinkerRef ! ("username1",List(CaseBill(INTERNET,"MTNL","30/12/2016",100,2,0,0)))

  }

  system.scheduler.scheduleOnce(250 milliseconds) {
    salaryDepositServiceActorRef ! ("username10",5000.0)
    salaryDepositServiceActorRef ! ("username10",2000.0)
  }


  reportGeneratorRef ! "getReport"



}