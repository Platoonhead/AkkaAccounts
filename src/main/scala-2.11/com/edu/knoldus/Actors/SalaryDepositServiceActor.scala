package com.edu.knoldus.Actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.edu.knoldus.storage.LocalDatabase

class SalaryDepositServiceActor extends Actor  with ActorLogging{

  val dbProviderRepoRef: ActorRef = context.actorOf(DbRepoActor.prop)
  val billDispatcherRepoRef: ActorRef = context.actorOf(SalaryDepositActor.prop)

  override def receive = {

    case (username:String,salary:Double) =>Thread.sleep(100)
                                          val result = LocalDatabase.getAssociatedBill(username)
                                          if (result._1) {
                                          val billsToPay = result._2
                                            if (billsToPay.isEmpty) dbProviderRepoRef ! (username, salary, "deposit")
                                            else {
                                            dbProviderRepoRef ! (username, salary, "deposit")
                                            billDispatcherRepoRef ! (username, billsToPay)
                                            }
                                          }
                                          else {
                                          log.info(s"${username.toUpperCase} : NO SUCH USER ACCOUNT")
                                          }

    case msg:String=> log.info(msg)

  }
}

object SalaryDepositServiceActor {


  def prop:Props = Props[SalaryDepositServiceActor]

}
