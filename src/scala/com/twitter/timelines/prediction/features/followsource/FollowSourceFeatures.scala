package com.tw ter.t  l nes.pred ct on.features.follows ce

 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._
 mport scala.collect on.JavaConverters._

object FollowS ceFeatures {

  // Corresponds to an algor hm constant from com.tw ter. rm .prof le. rm Prof leConstants
  val FollowS ceAlgor hm = new Feature.Text("follow_s ce.algor hm")

  // Type of follow act on: one of "unfollow", "follow", "follow_back", "follow_many", "follow_all"
  val FollowAct on = new Feature.Text(
    "follow_s ce.act on",
    Set(Follow, Pr vateAccountsFollo dBy, Publ cAccountsFollo dBy).asJava)

  // M ll second t  stamp w n follow occurred
  val FollowT  stamp =
    new Feature.D screte("follow_s ce.follow_t  stamp", Set(Follow, Pr vateT  stamp).asJava)

  // Age of follow ( n m nutes)
  val FollowAgeM nutes =
    new Feature.Cont nuous("follow_s ce.follow_age_m nutes", Set(Follow).asJava)

  // T et  D of t et deta ls page from w re follow happened ( f appl cable)
  val FollowCauseT et d = new Feature.D screte("follow_s ce.cause_t et_ d", Set(T et d).asJava)

  // Str ng representat on of follow cl ent (andro d,  b,  phone, etc). Der ved from "cl ent"
  // port on of cl ent event na space.
  val FollowCl ent d = new Feature.Text("follow_s ce.cl ent_ d", Set(Cl entType).asJava)

  //  f t  follow happens v a a prof le's Follow ng or Follo rs,
  // t   d of t  prof le owner  s recorded  re.
  val FollowAssoc at on d =
    new Feature.D screte("follow_s ce.assoc at on_ d", Set(Follow, User d).asJava)

  // T  "fr endly na "  re  s computed us ng FollowS ceUt l.getS ce.   represents
  // a group ng on a few cl ent events that reflect w re t  event occurred. For example,
  // events on t  t et deta ls page are grouped us ng "t etDeta ls":
  //   case (So (" b"), So ("permal nk"), _, _, _) => "t etDeta ls"
  //   case (So (" phone"), So ("t et"), _, _, _) => "t etDeta ls"
  //   case (So ("andro d"), So ("t et"), _, _, _) => "t etDeta ls"
  val FollowS ceFr endlyNa  = new Feature.Text("follow_s ce.fr endly_na ", Set(Follow).asJava)

  // Up to two s ces and act ons that preceded t  follow (for example, a prof le v s 
  // through a  nt on cl ck, wh ch  self was on a t et deta l page reac d through a t et
  // cl ck  n t  Ho  tab). See go/follows ce for more deta ls and examples.
  // T  "s ce"  re  s computed us ng FollowS ceUt l.getS ce
  val PreFollowAct on1 = new Feature.Text("follow_s ce.pre_follow_act on_1", Set(Follow).asJava)
  val PreFollowAct on2 = new Feature.Text("follow_s ce.pre_follow_act on_2", Set(Follow).asJava)
  val PreFollowS ce1 = new Feature.Text("follow_s ce.pre_follow_s ce_1", Set(Follow).asJava)
  val PreFollowS ce2 = new Feature.Text("follow_s ce.pre_follow_s ce_2", Set(Follow).asJava)
}
