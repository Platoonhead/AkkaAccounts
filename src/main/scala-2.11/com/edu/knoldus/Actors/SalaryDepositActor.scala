package com.edu.knoldus.Actors
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.edu.knoldus.models._

class SalaryDepositActor extends Actor with ActorLogging{

  val phoneBillRef: ActorRef = context.actorOf(PhoneBillProcessor.prop)
  val electricityBillRef: ActorRef =context.actorOf(ElectricityBillProcessor.prop)
  val internetBillRef: ActorRef =context.actorOf(InternetBillProcesser.prop)

  override def receive = {


    case (username:String,billstoPay:List[CaseBill]) =>

      for(bill <- billstoPay){

           bill.billCategory match {

             case INTERNET  => internetBillRef ! (username,bill)
             case ELECTRICITY  => electricityBillRef ! (username,bill)
             case PHONE  => phoneBillRef ! (username,bill)
             case _ => sender ! "sorry unable to process bill"

           }

      }



    case msg:String =>log.info(msg)

  }

}

object SalaryDepositActor {

  def prop:Props = Props[SalaryDepositActor]

}
