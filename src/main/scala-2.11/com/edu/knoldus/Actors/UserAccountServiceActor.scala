package com.edu.knoldus.Actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.edu.knoldus.models.CaseAccount

class UserAccountServiceActor extends Actor with ActorLogging {

  val dbProviderRepoRef: ActorRef = context.actorOf(DbRepoActor.prop)
  val salaryDepositServiceActorRef: ActorRef = context.actorOf(SalaryDepositServiceActor.prop)

  override def receive = {

    case user: CaseAccount => dbProviderRepoRef ! (user,"exist?")
    case msg:String => log.info(msg)

  }
}

object UserAccountServiceActor {

  def prop:Props = Props[UserAccountServiceActor]

}



