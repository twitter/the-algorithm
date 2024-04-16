package com.tw ter.v s b l y.models

case class UserAge(age nYears: Opt on[ nt]) {
  def hasAge: Boolean = age nYears. sDef ned

  def  sGte(ageToCompare:  nt): Boolean =
    age nYears
      .collectF rst {
        case age  f age > ageToCompare => true
      }.getOrElse(false)

  def unapply(userAge: UserAge): Opt on[ nt] = {
    age nYears
  }
}
