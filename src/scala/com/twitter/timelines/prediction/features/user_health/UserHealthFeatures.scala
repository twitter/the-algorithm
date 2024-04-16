package com.tw ter.t  l nes.pred ct on.features.user_ alth

 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.t  l nes.author_features.user_ alth.thr ftscala.UserState
 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType.{UserState => UserStatePDT}
 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._
 mport scala.collect on.JavaConverters._

object User althFeatures {
  val UserState = new Feature.D screte("user_ alth.user_state", Set(UserStatePDT, UserType).asJava)
  val  sL ghtM nusUser =
    new Feature.B nary("user_ alth. s_l ght_m nus_user", Set(UserStatePDT, UserType).asJava)
  val AuthorState =
    new Feature.D screte("user_ alth.author_state", Set(UserStatePDT, UserType).asJava)
  val NumAuthorFollo rs =
    new Feature.Cont nuous("author_ alth.num_follo rs", Set(CountOfFollo rsAndFollo es).asJava)
  val NumAuthorConnectDays = new Feature.Cont nuous("author_ alth.num_connect_days")
  val NumAuthorConnect = new Feature.Cont nuous("author_ alth.num_connect")

  val  sUserVer f edUn on = new Feature.B nary("user_account. s_user_ver f ed_un on")
}

case class User althFeatures( d: Long, userStateOpt: Opt on[UserState])
