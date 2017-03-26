package com.edu.knoldus.Actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.edu.knoldus.models.CaseBill

class  ElectricityBillProcessor extends Actor with ActorLogging{


  val dbRef: ActorRef = context.actorOf(DbRepoActor.prop)

  override def receive = {

    case (username:String,billstoPay:CaseBill) =>

      if(billstoPay.amount!=billstoPay.paidAmount) {
        val amountToPay = billstoPay.amount / billstoPay.totalIterations
        val amountDeducted = 0 - amountToPay
        val exeIteration = billstoPay.executedIterations + 1
        val paidAmt= billstoPay.paidAmount + amountToPay
        val updatedBill = billstoPay.copy(executedIterations = exeIteration,paidAmount = paidAmt)
        dbRef ! (username, amountDeducted, billstoPay,updatedBill,"Electricity")
      }


    case msg:String => log.info(msg)

  }

}

object  ElectricityBillProcessor {

  def prop:Props = Props[ElectricityBillProcessor]

}
