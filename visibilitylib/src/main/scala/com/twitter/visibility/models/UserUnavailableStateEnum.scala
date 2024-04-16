package com.tw ter.v s b l y.models

 mport com.tw ter.v s b l y.thr ftscala.UserV s b l yResult
 mport com.tw ter.v s b l y.ut l.Nam ngUt ls

sealed tra  UserUnava lableStateEnum {
  lazy val na : Str ng = Nam ngUt ls.getFr endlyNa (t )
}
object UserUnava lableStateEnum {
  case object Deleted extends UserUnava lableStateEnum
  case object BounceDeleted extends UserUnava lableStateEnum
  case object Deact vated extends UserUnava lableStateEnum
  case object Offboarded extends UserUnava lableStateEnum
  case object Erased extends UserUnava lableStateEnum
  case object Suspended extends UserUnava lableStateEnum
  case object Protected extends UserUnava lableStateEnum
  case object AuthorBlocksV e r extends UserUnava lableStateEnum
  case object V e rBlocksAuthor extends UserUnava lableStateEnum
  case object V e rMutesAuthor extends UserUnava lableStateEnum
  case class F ltered(result: UserV s b l yResult) extends UserUnava lableStateEnum
  case object Unava lable extends UserUnava lableStateEnum
}
