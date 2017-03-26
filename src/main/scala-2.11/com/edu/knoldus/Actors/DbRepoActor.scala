package com.edu.knoldus.Actors

import akka.actor.{Actor, Props}
import com.edu.knoldus.models.{CaseAccount, CaseBill}
import com.edu.knoldus.storage.LocalDatabase

class DbRepoActor extends Actor {

  override def receive = {

  case (user:CaseAccount,"exist?") => if(LocalDatabase.getAllUsers.contains(user.userName))
                                        sender ! "username already exist try again with unique username"
                                        else {LocalDatabase.insert(user)
                                        sender ! "inserted with "+user.userName}

  case (username:String,salary:Double,"deposit") => if(LocalDatabase.getAllUsers.contains(username)) {
                                                      LocalDatabase.creditSalary(username,salary)
                                                      sender ! s"${username.toUpperCase} : thanks, amount deposited successfully\n"}
                                                      else sender ! s"invalid username '$username',\n" +
                                                      s"Salary credit process Aborted\n"


  case (username:String,amountDeducted:Double,oldBill:CaseBill,updatedBill:CaseBill,catagory:String) =>

    if(LocalDatabase.getAllUsers.contains(username)) {
    LocalDatabase.deductAmount(username,amountDeducted,oldBill,updatedBill,catagory)
    sender ! s"${username.toUpperCase} : thanks, $catagory Bill Processed successfully"}
    else sender ! s"invalid username '$username',\n" +
    s"${username.toUpperCase} : $catagory Bill processing Aborted\n"



  }

}

object DbRepoActor {

  def prop:Props = Props[DbRepoActor]

}
