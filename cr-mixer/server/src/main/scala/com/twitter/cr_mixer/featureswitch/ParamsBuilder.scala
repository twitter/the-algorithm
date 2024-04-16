package com.tw ter.cr_m xer.featuresw ch

 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.abdec der.UserRec p ent
 mport com.tw ter.cr_m xer.{thr ftscala => t}
 mport com.tw ter.core_workflows.user_model.thr ftscala.UserState
 mport com.tw ter.d scovery.common.conf gap .FeatureContextBu lder
 mport com.tw ter.featuresw c s.FSRec p ent
 mport com.tw ter.featuresw c s.UserAgent
 mport com.tw ter.featuresw c s.{Rec p ent => FeatureSw chRec p ent}
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.thr ftscala.Cl entContext
 mport com.tw ter.t  l nes.conf gap .Conf g
 mport com.tw ter.t  l nes.conf gap .FeatureValue
 mport com.tw ter.t  l nes.conf gap .ForcedFeatureContext
 mport com.tw ter.t  l nes.conf gap .OrElseFeatureContext
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.t  l nes.conf gap .RequestContext
 mport com.tw ter.t  l nes.conf gap .abdec der.Logg ngABDec derExper  ntContext
 mport javax. nject. nject
 mport javax. nject.S ngleton

/** S ngleton object for bu ld ng [[Params]] to overr de */
@S ngleton
class ParamsBu lder @ nject() (
  globalStats: StatsRece ver,
  abDec der: Logg ngABDec der,
  featureContextBu lder: FeatureContextBu lder,
  conf g: Conf g) {

  pr vate val stats = globalStats.scope("params")

  def bu ldFromCl entContext(
    cl entContext: Cl entContext,
    product: t.Product,
    userState: UserState,
    userRoleOverr de: Opt on[Set[Str ng]] = None,
    featureOverr des: Map[Str ng, FeatureValue] = Map.empty,
  ): Params = {
    cl entContext.user d match {
      case So (user d) =>
        val userRec p ent = bu ldFeatureSw chRec p ent(
          user d,
          userRoleOverr de,
          cl entContext,
          product,
          userState
        )

        val featureContext = OrElseFeatureContext(
          ForcedFeatureContext(featureOverr des),
          featureContextBu lder(
            So (user d),
            So (userRec p ent)
          ))

        conf g(
          requestContext = RequestContext(
            user d = So (user d),
            exper  ntContext = Logg ngABDec derExper  ntContext(
              abDec der,
              So (UserRec p ent(user d, So (user d)))),
            featureContext = featureContext
          ),
          stats
        )
      case None =>
        val guestRec p ent =
          bu ldFeatureSw chRec p entW hGuest d(cl entContext: Cl entContext, product, userState)

        val featureContext = OrElseFeatureContext(
          ForcedFeatureContext(featureOverr des),
          featureContextBu lder(
            cl entContext.user d,
            So (guestRec p ent)
          )
        ) //Exper  ntContext w h GuestRec p ent  s not supported  as t re  s no act ve use-cases yet  n CrM xer

        conf g(
          requestContext = RequestContext(
            user d = cl entContext.user d,
            featureContext = featureContext
          ),
          stats
        )
    }
  }

  pr vate def bu ldFeatureSw chRec p entW hGuest d(
    cl entContext: Cl entContext,
    product: t.Product,
    userState: UserState
  ): FeatureSw chRec p ent = {

    val rec p ent = FSRec p ent(
      user d = None,
      userRoles = None,
      dev ce d = cl entContext.dev ce d,
      guest d = cl entContext.guest d,
      languageCode = cl entContext.languageCode,
      countryCode = cl entContext.countryCode,
      userAgent = cl entContext.userAgent.flatMap(UserAgent(_)),
       sVer f ed = None,
       sTwoff ce = None,
      tooCl ent = None,
      h ghWaterMark = None
    )

    rec p ent.w hCustomF elds(
      (ParamsBu lder.ProductCustomF eld, product.toStr ng),
      (ParamsBu lder.UserStateCustomF eld, userState.toStr ng)
    )
  }

  pr vate def bu ldFeatureSw chRec p ent(
    user d: Long,
    userRolesOverr de: Opt on[Set[Str ng]],
    cl entContext: Cl entContext,
    product: t.Product,
    userState: UserState
  ): FeatureSw chRec p ent = {
    val userRoles = userRolesOverr de match {
      case So (overr des) => So (overr des)
      case _ => cl entContext.userRoles.map(_.toSet)
    }

    val rec p ent = FSRec p ent(
      user d = So (user d),
      userRoles = userRoles,
      dev ce d = cl entContext.dev ce d,
      guest d = cl entContext.guest d,
      languageCode = cl entContext.languageCode,
      countryCode = cl entContext.countryCode,
      userAgent = cl entContext.userAgent.flatMap(UserAgent(_)),
       sVer f ed = None,
       sTwoff ce = None,
      tooCl ent = None,
      h ghWaterMark = None
    )

    rec p ent.w hCustomF elds(
      (ParamsBu lder.ProductCustomF eld, product.toStr ng),
      (ParamsBu lder.UserStateCustomF eld, userState.toStr ng)
    )
  }
}

object ParamsBu lder {
  pr vate val ProductCustomF eld = "product_ d"
  pr vate val UserStateCustomF eld = "user_state"
}
