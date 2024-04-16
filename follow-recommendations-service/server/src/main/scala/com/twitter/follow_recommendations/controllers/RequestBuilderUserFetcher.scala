package com.tw ter.follow_recom ndat ons.controllers

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.S mpleRec p ent
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.StatsUt l
 mport com.tw ter.follow_recom ndat ons.conf gap .dec ders.Dec derKey
 mport com.tw ter.g zmoduck.thr ftscala.LookupContext
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.g zmoduck.G zmoduck
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class RequestBu lderUserFetc r @ nject() (
  g zmoduck: G zmoduck,
  statsRece ver: StatsRece ver,
  dec der: Dec der) {
  pr vate val scopedStats = statsRece ver.scope(t .getClass.getS mpleNa )

  def fetchUser(user dOpt: Opt on[Long]): St ch[Opt on[User]] = {
    user dOpt match {
      case So (user d)  f enableDec der(user d) =>
        val st ch = g zmoduck
          .getUserBy d(
            user d = user d,
            context = LookupContext(
              forUser d = So (user d),
               ncludeProtected = true,
               ncludeSoftUsers = true
            )
          ).map(user => So (user))
        StatsUt l
          .prof leSt ch(st ch, scopedStats)
          .handle {
            case _: Throwable => None
          }
      case _ => St ch.None
    }
  }

  pr vate def enableDec der(user d: Long): Boolean = {
    dec der. sAva lable(
      Dec derKey.EnableFetchUser nRequestBu lder.toStr ng,
      So (S mpleRec p ent(user d)))
  }
}
