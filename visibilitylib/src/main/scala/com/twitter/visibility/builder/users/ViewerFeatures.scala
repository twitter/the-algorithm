package com.tw ter.v s b l y.bu lder.users

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.g zmoduck.thr ftscala.Label
 mport com.tw ter.g zmoduck.thr ftscala.Safety
 mport com.tw ter.g zmoduck.thr ftscala.Un versalQual yF lter ng
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.g zmoduck.thr ftscala.UserType
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.User d
 mport com.tw ter.v s b l y.common.UserS ce
 mport com.tw ter.v s b l y.features._
 mport com.tw ter.v s b l y. nterfaces.common.blender.BlenderVFRequestContext
 mport com.tw ter.v s b l y. nterfaces.common.search.SearchVFRequestContext
 mport com.tw ter.v s b l y.models.UserAge
 mport com.tw ter.v s b l y.models.V e rContext

class V e rFeatures(userS ce: UserS ce, statsRece ver: StatsRece ver) {
  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("v e r_features")

  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")

  pr vate[t ] val v e r dCount =
    scopedStatsRece ver.scope(V e r d.na ).counter("requests")
  pr vate[t ] val requestCountryCode =
    scopedStatsRece ver.scope(RequestCountryCode.na ).counter("requests")
  pr vate[t ] val request sVer f edCrawler =
    scopedStatsRece ver.scope(Request sVer f edCrawler.na ).counter("requests")
  pr vate[t ] val v e rUserLabels =
    scopedStatsRece ver.scope(V e rUserLabels.na ).counter("requests")
  pr vate[t ] val v e r sDeact vated =
    scopedStatsRece ver.scope(V e r sDeact vated.na ).counter("requests")
  pr vate[t ] val v e r sProtected =
    scopedStatsRece ver.scope(V e r sProtected.na ).counter("requests")
  pr vate[t ] val v e r sSuspended =
    scopedStatsRece ver.scope(V e r sSuspended.na ).counter("requests")
  pr vate[t ] val v e rRoles =
    scopedStatsRece ver.scope(V e rRoles.na ).counter("requests")
  pr vate[t ] val v e rCountryCode =
    scopedStatsRece ver.scope(V e rCountryCode.na ).counter("requests")
  pr vate[t ] val v e rAge =
    scopedStatsRece ver.scope(V e rAge.na ).counter("requests")
  pr vate[t ] val v e rHasUn versalQual yF lterEnabled =
    scopedStatsRece ver.scope(V e rHasUn versalQual yF lterEnabled.na ).counter("requests")
  pr vate[t ] val v e r sSoftUserCtr =
    scopedStatsRece ver.scope(V e r sSoftUser.na ).counter("requests")

  def forV e rBlenderContext(
    blenderContext: BlenderVFRequestContext,
    v e rContext: V e rContext
  ): FeatureMapBu lder => FeatureMapBu lder =
    forV e rContext(v e rContext)
      .andT n(
        _.w hConstantFeature(
          V e rOpt nBlock ng,
          blenderContext.userSearchSafetySett ngs.opt nBlock ng)
          .w hConstantFeature(
            V e rOpt nF lter ng,
            blenderContext.userSearchSafetySett ngs.opt nF lter ng)
      )

  def forV e rSearchContext(
    searchContext: SearchVFRequestContext,
    v e rContext: V e rContext
  ): FeatureMapBu lder => FeatureMapBu lder =
    forV e rContext(v e rContext)
      .andT n(
        _.w hConstantFeature(
          V e rOpt nBlock ng,
          searchContext.userSearchSafetySett ngs.opt nBlock ng)
          .w hConstantFeature(
            V e rOpt nF lter ng,
            searchContext.userSearchSafetySett ngs.opt nF lter ng)
      )

  def forV e rContext(v e rContext: V e rContext): FeatureMapBu lder => FeatureMapBu lder =
    forV e r d(v e rContext.user d)
      .andT n(
        _.w hConstantFeature(RequestCountryCode, requestCountryCode(v e rContext))
      ).andT n(
        _.w hConstantFeature(Request sVer f edCrawler, request sVer f edCrawler(v e rContext))
      )

  def forV e r d(v e r d: Opt on[User d]): FeatureMapBu lder => FeatureMapBu lder = { bu lder =>
    requests. ncr()

    val bu lderW hFeatures = bu lder
      .w hConstantFeature(V e r d, v e r d)
      .w hFeature(V e r sProtected, v e r sProtected(v e r d))
      .w hFeature(
        V e rHasUn versalQual yF lterEnabled,
        v e rHasUn versalQual yF lterEnabled(v e r d)
      )
      .w hFeature(V e r sSuspended, v e r sSuspended(v e r d))
      .w hFeature(V e r sDeact vated, v e r sDeact vated(v e r d))
      .w hFeature(V e rUserLabels, v e rUserLabels(v e r d))
      .w hFeature(V e rRoles, v e rRoles(v e r d))
      .w hFeature(V e rAge, v e rAge nYears(v e r d))
      .w hFeature(V e r sSoftUser, v e r sSoftUser(v e r d))

    v e r d match {
      case So (_) =>
        v e r dCount. ncr()
        bu lderW hFeatures
          .w hFeature(V e rCountryCode, v e rCountryCode(v e r d))

      case _ =>
        bu lderW hFeatures
    }
  }

  def forV e rNoDefaults(v e rOpt: Opt on[User]): FeatureMapBu lder => FeatureMapBu lder = {
    bu lder =>
      requests. ncr()

      v e rOpt match {
        case So (v e r) =>
          bu lder
            .w hConstantFeature(V e r d, v e r. d)
            .w hConstantFeature(V e r sProtected, v e r sProtectedOpt(v e r))
            .w hConstantFeature(V e r sSuspended, v e r sSuspendedOpt(v e r))
            .w hConstantFeature(V e r sDeact vated, v e r sDeact vatedOpt(v e r))
            .w hConstantFeature(V e rCountryCode, v e rCountryCode(v e r))
        case None =>
          bu lder
            .w hConstantFeature(V e r sProtected, false)
            .w hConstantFeature(V e r sSuspended, false)
            .w hConstantFeature(V e r sDeact vated, false)
      }
  }

  pr vate def c ckSafetyValue(
    v e r d: Opt on[User d],
    safetyC ck: Safety => Boolean,
    featureCounter: Counter
  ): St ch[Boolean] =
    v e r d match {
      case So ( d) =>
        userS ce.getSafety( d).map(safetyC ck).ensure {
          featureCounter. ncr()
        }
      case None => St ch.False
    }

  pr vate def c ckSafetyValue(
    v e r: User,
    safetyC ck: Safety => Boolean
  ): Boolean = {
    v e r.safety.ex sts(safetyC ck)
  }

  def v e r sProtected(v e r d: Opt on[User d]): St ch[Boolean] =
    c ckSafetyValue(v e r d, s => s. sProtected, v e r sProtected)

  def v e r sProtected(v e r: User): Boolean =
    c ckSafetyValue(v e r, s => s. sProtected)

  def v e r sProtectedOpt(v e r: User): Opt on[Boolean] =
    v e r.safety.map(_. sProtected)

  def v e r sDeact vated(v e r d: Opt on[User d]): St ch[Boolean] =
    c ckSafetyValue(v e r d, s => s.deact vated, v e r sDeact vated)

  def v e r sDeact vated(v e r: User): Boolean =
    c ckSafetyValue(v e r, s => s.deact vated)

  def v e r sDeact vatedOpt(v e r: User): Opt on[Boolean] =
    v e r.safety.map(_.deact vated)

  def v e rHasUn versalQual yF lterEnabled(v e r d: Opt on[User d]): St ch[Boolean] =
    c ckSafetyValue(
      v e r d,
      s => s.un versalQual yF lter ng == Un versalQual yF lter ng.Enabled,
      v e rHasUn versalQual yF lterEnabled
    )

  def v e rUserLabels(v e r dOpt: Opt on[User d]): St ch[Seq[Label]] =
    v e r dOpt match {
      case So (v e r d) =>
        userS ce
          .getLabels(v e r d).map(_.labels)
          .handle {
            case NotFound => Seq.empty
          }.ensure {
            v e rUserLabels. ncr()
          }
      case _ => St ch.value(Seq.empty)
    }

  def v e rAge nYears(v e r d: Opt on[User d]): St ch[UserAge] =
    (v e r d match {
      case So ( d) =>
        userS ce
          .getExtendedProf le( d).map(_.age nYears)
          .handle {
            case NotFound => None
          }.ensure {
            v e rAge. ncr()
          }
      case _ => St ch.value(None)
    }).map(UserAge)

  def v e r sSoftUser(v e r d: Opt on[User d]): St ch[Boolean] = {
    v e r d match {
      case So ( d) =>
        userS ce
          .getUserType( d).map { userType =>
            userType == UserType.Soft
          }.ensure {
            v e r sSoftUserCtr. ncr()
          }
      case _ => St ch.False
    }
  }

  def requestCountryCode(v e rContext: V e rContext): Opt on[Str ng] = {
    requestCountryCode. ncr()
    v e rContext.requestCountryCode
  }

  def request sVer f edCrawler(v e rContext: V e rContext): Boolean = {
    request sVer f edCrawler. ncr()
    v e rContext. sVer f edCrawler
  }

  def v e rCountryCode(v e r d: Opt on[User d]): St ch[Str ng] =
    v e r d match {
      case So ( d) =>
        userS ce
          .getAccount( d).map(_.countryCode).flatMap {
            case So (countryCode) => St ch.value(countryCode.toLo rCase)
            case _ => St ch.NotFound
          }.ensure {
            v e rCountryCode. ncr()
          }

      case _ => St ch.NotFound
    }

  def v e rCountryCode(v e r: User): Opt on[Str ng] =
    v e r.account.flatMap(_.countryCode)
}
