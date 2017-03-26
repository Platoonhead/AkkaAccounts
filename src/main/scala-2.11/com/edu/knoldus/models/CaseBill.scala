package com.edu.knoldus.models

abstract class Bills

case object PHONE extends  Bills
case object ELECTRICITY extends  Bills
case object INTERNET extends Bills

case class CaseBill (
                      billCategory:Bills,
                      billName:String,
                      TransactionDate:String,
                      amount:Double,
                      totalIterations:Int,
                      executedIterations:Int,
                      paidAmount:Double
                    )
