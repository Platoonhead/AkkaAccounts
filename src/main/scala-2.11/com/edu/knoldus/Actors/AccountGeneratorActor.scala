package com.edu.knoldus.Actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.dispatch.{BoundedMessageQueueSemantics, RequiresMessageQueue}
import com.edu.knoldus.models.CaseAccount


class AccountGeneratorActor  extends Actor with ActorLogging with RequiresMessageQueue[BoundedMessageQueueSemantics]{

  val userAccountServiceActorRef: ActorRef = context.actorOf(UserAccountServiceActor.prop)

  override def receive = {

    case user : CaseAccount => userAccountServiceActorRef ! user
    case msg:String => log.info(msg)
  }

}

object AccountGeneratorActor {

  def prop:Props = Props[AccountGeneratorActor]

}


