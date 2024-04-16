package com.tw ter.recos njector.f lters

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.g zmoduck.thr ftscala.{LabelValue, User}
 mport com.tw ter.recos njector.cl ents.G zmoduck
 mport com.tw ter.ut l.Future

class UserF lter(
  g zmoduck: G zmoduck
)(
   mpl c  statsRece ver: StatsRece ver) {
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val requests = stats.counter("requests")
  pr vate val f ltered = stats.counter("f ltered")

  pr vate def  sUnsafe(user: User): Boolean =
    user.safety.ex sts { s =>
      s.deact vated || s.suspended || s.restr cted || s.nsfwUser || s.nsfwAdm n || s. sProtected
    }

  pr vate def hasNsfwH ghPrec s onLabel(user: User): Boolean =
    user.labels.ex sts {
      _.labels.ex sts(_.labelValue == LabelValue.NsfwH ghPrec s on)
    }

  /**
   * NOTE: T  w ll by-pass G zmoduck's safety level, and m ght allow  nval d users to pass f lter.
   * Cons der us ng f lterByUser d  nstead.
   * Return true  f t  user  s val d, ot rw se return false.
   *   w ll f rst attempt to use t  user object prov ded by t  caller, and w ll call G zmoduck
   * to back f ll  f t  caller does not prov de  . T   lps reduce G zmoduck traff c.
   */
  def f lterByUser(
    user d: Long,
    userOpt: Opt on[User] = None
  ): Future[Boolean] = {
    requests. ncr()
    val userFut = userOpt match {
      case So (user) => Future(So (user))
      case _ => g zmoduck.getUser(user d)
    }

    userFut.map(_.ex sts { user =>
      val  sVal dUser = ! sUnsafe(user) && !hasNsfwH ghPrec s onLabel(user)
       f (! sVal dUser) f ltered. ncr()
       sVal dUser
    })
  }

  /**
   * G ven a user d, return true  f t  user  s val d. T   d done  n 2 steps:
   * 1. Apply ng G zmoduck's safety level wh le query ng for t  user from G zmoduck
   * 2.  f a user passes G zmoduck's safety level, c ck  s spec f c user status
   */
  def f lterByUser d(user d: Long): Future[Boolean] = {
    requests. ncr()
    g zmoduck
      .getUser(user d)
      .map { userOpt =>
        val  sVal dUser = userOpt.ex sts { user =>
          !( sUnsafe(user) || hasNsfwH ghPrec s onLabel(user))
        }
         f (! sVal dUser) {
          f ltered. ncr()
        }
         sVal dUser
      }
  }
}
