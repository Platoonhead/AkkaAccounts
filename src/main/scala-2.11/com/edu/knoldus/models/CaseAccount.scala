package com.edu.knoldus.models

case class CaseAccount(
                         holderName: String,
                         address: String,
                         userName: String,
                         initialAmount: Double = 0,
                         accountNumber: Int = 0
                       )

