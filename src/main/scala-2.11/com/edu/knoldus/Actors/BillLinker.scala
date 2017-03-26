package com.edu.knoldus.Actors
import akka.actor.{Actor, ActorLogging, Props}
import com.edu.knoldus.models.CaseBill
import com.edu.knoldus.storage.LocalDatabase


class  BillLinker extends Actor with ActorLogging{


  override def receive = {

    case (username:String,bills:List[CaseBill]) =>if(LocalDatabase.billLinker(username,bills)) {}
    else log.info(s"${username.toUpperCase()} : NO SUCH ACCOUNT")

  }
}

object  BillLinker {


  def prop:Props = Props[BillLinker]

}



