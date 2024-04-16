package com.tw ter.follow_recom ndat ons.common.cl ents.user_state

 mport com.google. nject.na .Na d
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.core_workflows.user_model.thr ftscala.CondensedUserState
 mport com.tw ter.core_workflows.user_model.thr ftscala.UserState
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.RandomRec p ent
 mport com.tw ter.f nagle. mcac d.Cl ent
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter.follow_recom ndat ons.common.base.StatsUt l
 mport com.tw ter.follow_recom ndat ons.common.cl ents.cac . mcac Cl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.cac .Thr ftEnumOpt onB ject on
 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants
 mport com.tw ter.follow_recom ndat ons.conf gap .dec ders.Dec derKey
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.ut l.Durat on
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport java.lang.{Long => JLong}

@S ngleton
class UserStateCl ent @ nject() (
  @Na d(Gu ceNa dConstants.USER_STATE_FETCHER) userStateFetc r: Fetc r[
    Long,
    Un ,
    CondensedUserState
  ],
  cl ent: Cl ent,
  statsRece ver: StatsRece ver,
  dec der: Dec der = Dec der.False) {

  pr vate val stats: StatsRece ver = statsRece ver.scope("user_state_cl ent")

  // cl ent to  mcac  cluster
  val b ject on = new Thr ftEnumOpt onB ject on[UserState](UserState.apply)
  val  mcac Cl ent =  mcac Cl ent[Opt on[UserState]](
    cl ent = cl ent,
    dest = "/s/cac /follow_recos_serv ce:t mcac s",
    valueB ject on = b ject on,
    ttl = UserStateCl ent.Cac TTL,
    statsRece ver = stats.scope("t mcac ")
  )

  def getUserState(user d: Long): St ch[Opt on[UserState]] = {
    val dec derKey: Str ng = Dec derKey.EnableD str butedCach ng.toStr ng
    val enableD str butedCach ng: Boolean = dec der. sAva lable(dec derKey, So (RandomRec p ent))
    val userStateSt ch: St ch[Opt on[UserState]] = 
      enableD str butedCach ng match {
        // read from  mcac 
        case true =>  mcac Cl ent.readThrough(
          // add a key pref x to address cac  key coll s ons
          key = "UserStateCl ent" + user d.toStr ng,
          underly ngCall = () => fetchUserState(user d)
        )
        case false => fetchUserState(user d)
      }
    val userStateSt chW hT  out: St ch[Opt on[UserState]] = 
      userStateSt ch
        // set a 150ms t  out l m  for user state fetc s
        .w h n(150.m ll seconds)(DefaultT  r)
        .rescue {
          case e: Except on =>
            stats.scope("rescued").counter(e.getClass.getS mpleNa ). ncr()
            St ch(None)
        }
    // prof le t  latency of st ch call and return t  result
    StatsUt l.prof leSt ch(
      userStateSt chW hT  out,
      stats.scope("getUserState")
    )
  }

  def fetchUserState(user d: JLong): St ch[Opt on[UserState]] = {
    userStateFetc r.fetch(user d).map(_.v.flatMap(_.userState))
  }
}

object UserStateCl ent {
  val Cac TTL: Durat on = Durat on.fromH s(6)
}
