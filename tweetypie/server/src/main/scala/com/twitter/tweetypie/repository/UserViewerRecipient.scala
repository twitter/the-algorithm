package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.context.thr ftscala.V e r
 mport com.tw ter.featuresw c s.Rec p ent
 mport com.tw ter.featuresw c s.TOOCl ent
 mport com.tw ter.featuresw c s.UserAgent
 mport com.tw ter.t etyp e.StatsRece ver
 mport com.tw ter.t etyp e.User
 mport com.tw ter.t etyp e.User d
 mport com.tw ter.t etyp e.cl ent_ d.Cl ent d lper
 mport com.tw ter.t etyp e.repos ory.UserV e rRec p ent.User dM smatchExcept on

/**
 * Prov des a Rec p ent backed by a G zmoduck User and Tw terContext V e r for
 * use  n FeatureSw ch val dat on.
 */
object UserV e rRec p ent {
  object User dM smatchExcept on extends Except on

  def apply(user: User, v e r: V e r, stats: StatsRece ver): Opt on[Rec p ent] = {
    // T   s a workaround for thr ft AP  cl ents that allow users to T et on behalf
    // of ot r Tw ter users. T   s s m lar to go/contr butors, ho ver so  platforms
    // have enabled workflows that don't use t  go/contr butors auth platform, and
    // t refore t  Tw terContext V e r  sn't set up correctly for contr butor requests.
     f (v e r.user d.conta ns(user. d)) {
      So (new UserV e rRec p ent(user, v e r))
    } else {
      val m smatchScope = stats.scope(s"user_v e r_m smatch")
      Cl ent d lper.default.effect veCl ent dRoot.foreach { cl ent d =>
        m smatchScope.scope("cl ent").counter(cl ent d). ncr()
      }
      m smatchScope.counter("total"). ncr()
      None
    }
  }
}

class UserV e rRec p ent(
  user: User,
  v e r: V e r)
    extends Rec p ent {

   f (!v e r.user d.conta ns(user. d)) {
    throw User dM smatchExcept on
  }

  overr de def user d: Opt on[User d] = v e r.user d

  overr de def userRoles: Opt on[Set[Str ng]] = user.roles.map(_.roles.toSet)

  overr de def dev ce d: Opt on[Str ng] = v e r.dev ce d

  overr de def guest d: Opt on[Long] = v e r.guest d

  overr de def languageCode: Opt on[Str ng] = v e r.requestLanguageCode

  overr de def s gnupCountryCode: Opt on[Str ng] = user.safety.flatMap(_.s gnupCountryCode)

  overr de def countryCode: Opt on[Str ng] = v e r.requestCountryCode

  overr de def userAgent: Opt on[UserAgent] = v e r.userAgent.flatMap(UserAgent(_))

  overr de def  sMan fest: Boolean = false

  overr de def  sVer f ed: Opt on[Boolean] = user.safety.map(_.ver f ed)

  overr de def cl entAppl cat on d: Opt on[Long] = v e r.cl entAppl cat on d

  @Deprecated
  overr de def  sTwoff ce: Opt on[Boolean] = None

  @Deprecated
  overr de def tooCl ent: Opt on[TOOCl ent] = None

  @Deprecated
  overr de def h ghWaterMark: Opt on[Long] = None
}
