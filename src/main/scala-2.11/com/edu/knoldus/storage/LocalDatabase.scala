package com.edu.knoldus.storage

import com.edu.knoldus.models.{CaseAccount, CaseBill}

import scala.collection.{Set, mutable}

object LocalDatabase {

  val userInfo: mutable.Map[String, CaseAccount] = scala.collection.mutable.Map[String,CaseAccount]()
  val linkedBills: mutable.Map[String,List[CaseBill]] = scala.collection.mutable.Map[String,List[CaseBill]]()
  var accountNum = 1000

   def insert(value:CaseAccount):Boolean={
     accountNum += 1
     val currentUser = value.copy(accountNumber = accountNum)
     userInfo += value.userName -> currentUser
     true
   }

  def creditSalary(username:String,salary:Double):Boolean={

     val user = userInfo(username)
     val initialBalance = user.initialAmount
     val newBalance = user.initialAmount + salary
     val updatedAccount = user.copy(initialAmount = newBalance)
     insert(updatedAccount)
     println(s"--------------------------------------\nUSERNAME : $username\nINITIAL BALANCE : $initialBalance\n" +
      raw"SALARY CREDITED : $$$salary"+s"\nCURRENT BALANCE : $newBalance \n--------------------------------------\n")
     true
  }

  def getAllUsers:Set[String]={
    userInfo.keySet
  }

  def deductAmount (username:String,amount:Double,oldBill:CaseBill,updatedBill:CaseBill,msg:String):Boolean={

    val user = userInfo(username)
    val initialBalance = user.initialAmount
    val newBalance = user.initialAmount + amount
    val updatedAccount = user.copy(initialAmount = newBalance)
    insert(updatedAccount)

    val userBills = linkedBills(username)
    val updatedBills = userBills.filter(x => x!=oldBill)
    val updatedBillList = updatedBills:+updatedBill
    linkedBills += (username -> updatedBillList)
    println(s"--------------------------------------\nUSERNAME : $username\nINITIAL BALANCE : $initialBalance\n" +
    s"$msg BILL PAYMENT : $amount \nCURRENT BALANCE : $newBalance \n--------------------------------------\n")
    true
  }

  def billLinker(username:String,bills:List[CaseBill]):Boolean={

      if(getAllUsers.contains(username)){
            if(linkedBills.keySet.contains(username)){

              val currentBills = linkedBills(username)
              val updatedBills = currentBills ::: bills
              linkedBills += (username -> updatedBills)
            }
            else linkedBills += (username -> bills)
      true
      }
      else false
  }

  def getAssociatedBill(user:String):(Boolean,List[CaseBill])={

     if(getAllUsers.contains(user) && linkedBills.keySet.contains(user)) (true,linkedBills(user))
     else if(getAllUsers.contains(user) && !linkedBills.keySet.contains(user)) (true,Nil)
     else (false,Nil)
  }

}
