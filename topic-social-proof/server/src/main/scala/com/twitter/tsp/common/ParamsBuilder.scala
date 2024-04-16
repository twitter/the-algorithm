package com.tw ter.tsp.common

 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.abdec der.UserRec p ent
 mport com.tw ter.contentrecom nder.thr ftscala.D splayLocat on
 mport com.tw ter.d scovery.common.conf gap .FeatureContextBu lder
 mport com.tw ter.featuresw c s.FSRec p ent
 mport com.tw ter.featuresw c s.Rec p ent
 mport com.tw ter.featuresw c s.UserAgent
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nterests.thr ftscala.Top cL st ngV e rContext
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.t  l nes.conf gap .RequestContext
 mport com.tw ter.t  l nes.conf gap .abdec der.Logg ngABDec derExper  ntContext

case class ParamsBu lder(
  featureContextBu lder: FeatureContextBu lder,
  abDec der: Logg ngABDec der,
  overr desConf g: conf gap .Conf g,
  statsRece ver: StatsRece ver) {

  def bu ldFromTop cL st ngV e rContext(
    top cL st ngV e rContext: Opt on[Top cL st ngV e rContext],
    d splayLocat on: D splayLocat on,
    userRoleOverr de: Opt on[Set[Str ng]] = None
  ): Params = {

    top cL st ngV e rContext.flatMap(_.user d) match {
      case So (user d) =>
        val userRec p ent = ParamsBu lder.toFeatureSw chRec p entW hTop cContext(
          user d,
          userRoleOverr de,
          top cL st ngV e rContext,
          So (d splayLocat on)
        )

        overr desConf g(
          requestContext = RequestContext(
            user d = So (user d),
            exper  ntContext = Logg ngABDec derExper  ntContext(
              abDec der,
              So (UserRec p ent(user d, So (user d)))),
            featureContext = featureContextBu lder(
              So (user d),
              So (userRec p ent)
            )
          ),
          statsRece ver
        )
      case _ =>
        throw new  llegalArgu ntExcept on(
          s"${t .getClass.getS mpleNa } tr ed to bu ld Param for a request w hout a user d"
        )
    }
  }
}

object ParamsBu lder {

  def toFeatureSw chRec p entW hTop cContext(
    user d: Long,
    userRolesOverr de: Opt on[Set[Str ng]],
    context: Opt on[Top cL st ngV e rContext],
    d splayLocat onOpt: Opt on[D splayLocat on]
  ): Rec p ent = {
    val userRoles = userRolesOverr de match {
      case So (overr des) => So (overr des)
      case _ => context.flatMap(_.userRoles.map(_.toSet))
    }

    val rec p ent = FSRec p ent(
      user d = So (user d),
      userRoles = userRoles,
      dev ce d = context.flatMap(_.dev ce d),
      guest d = context.flatMap(_.guest d),
      languageCode = context.flatMap(_.languageCode),
      countryCode = context.flatMap(_.countryCode),
      userAgent = context.flatMap(_.userAgent).flatMap(UserAgent(_)),
       sVer f ed = None,
       sTwoff ce = None,
      tooCl ent = None,
      h ghWaterMark = None
    )
    d splayLocat onOpt match {
      case So (d splayLocat on) =>
        rec p ent.w hCustomF elds(d splayLocat onCustomF eldMap(d splayLocat on))
      case None =>
        rec p ent
    }
  }

  pr vate val D splayLocat onCustomF eld = "d splay_locat on"

  def d splayLocat onCustomF eldMap(d splayLocat on: D splayLocat on): (Str ng, Str ng) =
    D splayLocat onCustomF eld -> d splayLocat on.toStr ng

}
