package com.tw ter.v s b l y.models

 mport com.tw ter.g zmoduck.{thr ftscala => t}
 mport com.tw ter.ut l.T  
 mport com.tw ter.v s b l y.ut l.Nam ngUt ls

sealed tra  UserLabelValue extends SafetyLabelType {
  lazy val na : Str ng = Nam ngUt ls.getFr endlyNa (t )
}

case class UserLabel(
   d: Long,
  createdAt: T  ,
  createdBy: Str ng,
  labelValue: UserLabelValue,
  s ce: Opt on[LabelS ce] = None)

object UserLabelValue extends SafetyLabelType {

  pr vate lazy val na ToValueMap: Map[Str ng, UserLabelValue] =
    L st.map(l => l.na .toLo rCase -> l).toMap
  def fromNa (na : Str ng): Opt on[UserLabelValue] = na ToValueMap.get(na .toLo rCase)

  pr vate val UnknownThr ftUserLabelValue =
    t.LabelValue.EnumUnknownLabelValue(UnknownEnumValue)

  pr vate lazy val thr ftToModelMap: Map[t.LabelValue, UserLabelValue] = Map(
    t.LabelValue.Abus ve -> Abus ve,
    t.LabelValue.Abus veH ghRecall -> Abus veH ghRecall,
    t.LabelValue.AgathaSpamTopUser -> AgathaSpamTopUser,
    t.LabelValue.B rdwatchD sabled -> B rdwatchD sabled,
    t.LabelValue.Bl nkBad -> Bl nkBad,
    t.LabelValue.Bl nkQuest onable -> Bl nkQuest onable,
    t.LabelValue.Bl nkWorst -> Bl nkWorst,
    t.LabelValue.Comprom sed -> Comprom sed,
    t.LabelValue.DelayedRe d at on -> DelayedRe d at on,
    t.LabelValue.DoNotCharge -> DoNotCharge,
    t.LabelValue.DoNotAmpl fy -> DoNotAmpl fy,
    t.LabelValue.DownrankSpamReply -> DownrankSpamReply,
    t.LabelValue.Dupl cateContent -> Dupl cateContent,
    t.LabelValue.Engage ntSpam r -> Engage ntSpam r,
    t.LabelValue.Engage ntSpam rH ghRecall -> Engage ntSpam rH ghRecall,
    t.LabelValue.Exper  ntalPfmUser1 -> Exper  ntalPfmUser1,
    t.LabelValue.Exper  ntalPfmUser2 -> Exper  ntalPfmUser2,
    t.LabelValue.Exper  ntalPfmUser3 -> Exper  ntalPfmUser3,
    t.LabelValue.Exper  ntalPfmUser4 -> Exper  ntalPfmUser4,
    t.LabelValue.Exper  ntalSeh1 -> Exper  ntalSeh1,
    t.LabelValue.Exper  ntalSeh2 -> Exper  ntalSeh2,
    t.LabelValue.Exper  ntalSeh3 -> Exper  ntalSeh3,
    t.LabelValue.Exper  ntalSehUser4 -> Exper  ntalSehUser4,
    t.LabelValue.Exper  ntalSehUser5 -> Exper  ntalSehUser5,
    t.LabelValue.Exper  ntalSens  ve llegal1 -> Exper  ntalSens  ve llegal1,
    t.LabelValue.Exper  ntalSens  ve llegal2 -> Exper  ntalSens  ve llegal2,
    t.LabelValue.FakeS gnupDeferredRe d at on -> FakeS gnupDeferredRe d at on,
    t.LabelValue.FakeS gnupHoldback -> FakeS gnupHoldback,
    t.LabelValue.GoreAndV olenceH ghPrec s on -> GoreAndV olenceH ghPrec s on,
    t.LabelValue.GoreAndV olenceReported ur st cs -> GoreAndV olenceReported ur st cs,
    t.LabelValue. althExper  ntat on1 ->  althExper  ntat on1,
    t.LabelValue. althExper  ntat on2 ->  althExper  ntat on2,
    t.LabelValue.H ghR skVer f cat on -> H ghR skVer f cat on,
    t.LabelValue.L kely vs -> L kely vs,
    t.LabelValue.L veLowQual y -> L veLowQual y,
    t.LabelValue.LowQual y -> LowQual y,
    t.LabelValue.LowQual yH ghRecall -> LowQual yH ghRecall,
    t.LabelValue.NotGraduated -> NotGraduated,
    t.LabelValue.Not f cat onSpam ur st cs -> Not f cat onSpam ur st cs,
    t.LabelValue.NsfwAvatar mage -> NsfwAvatar mage,
    t.LabelValue.NsfwBanner mage -> NsfwBanner mage,
    t.LabelValue.NsfwH ghPrec s on -> NsfwH ghPrec s on,
    t.LabelValue.NsfwH ghRecall -> NsfwH ghRecall,
    t.LabelValue.NsfwNearPerfect -> NsfwNearPerfect,
    t.LabelValue.NsfwReported ur st cs -> NsfwReported ur st cs,
    t.LabelValue.NsfwSens  ve -> NsfwSens  ve,
    t.LabelValue.NsfwText -> NsfwText,
    t.LabelValue.ReadOnly -> ReadOnly,
    t.LabelValue.RecentAbuseStr ke -> RecentAbuseStr ke,
    t.LabelValue.RecentM s nfoStr ke -> RecentM s nfoStr ke,
    t.LabelValue.RecentProf leMod f cat on -> RecentProf leMod f cat on,
    t.LabelValue.RecentSuspens on -> RecentSuspens on,
    t.LabelValue.Recom ndat onsBlackl st -> Recom ndat onsBlackl st,
    t.LabelValue.SearchBlackl st -> SearchBlackl st,
    t.LabelValue.SoftReadOnly -> SoftReadOnly,
    t.LabelValue.SpamH ghRecall -> SpamH ghRecall,
    t.LabelValue.Spam UserModelH ghPrec s on -> Spam UserModelH ghPrec s on,
    t.LabelValue.State d aAccount -> State d aAccount,
    t.LabelValue.TsV olat on -> TsV olat on,
    t.LabelValue.Unconf r dEma lS gnup -> Unconf r dEma lS gnup,
    t.LabelValue.LegalOpsCase -> LegalOpsCase,
    t.LabelValue.Automat onH ghRecall -> Deprecated,
    t.LabelValue.Automat onH ghRecallHoldback -> Deprecated,
    t.LabelValue.BouncerUserF ltered -> Deprecated,
    t.LabelValue.DeprecatedL stBannerPdna -> Deprecated,
    t.LabelValue.DeprecatedM grat on50 -> Deprecated,
    t.LabelValue.DmSpam r -> Deprecated,
    t.LabelValue.Dupl cateContentHoldback -> Deprecated,
    t.LabelValue.FakeAccountExper  nt -> Deprecated,
    t.LabelValue.FakeAccountReadonly -> Deprecated,
    t.LabelValue.FakeAccountRecaptcha -> Deprecated,
    t.LabelValue.FakeAccountSspc -> Deprecated,
    t.LabelValue.FakeAccountVo ceReadonly -> Deprecated,
    t.LabelValue.FakeEngage nt -> Deprecated,
    t.LabelValue.HasBeenSuspended -> Deprecated,
    t.LabelValue.H ghProf le -> Deprecated,
    t.LabelValue.Not f cat onsSp ke -> Deprecated,
    t.LabelValue.NsfaProf leH ghRecall -> Deprecated,
    t.LabelValue.NsfwUserNa  -> Deprecated,
    t.LabelValue.Potent allyComprom sed -> Deprecated,
    t.LabelValue.Prof leAdsBlackl st -> Deprecated,
    t.LabelValue.Ratel m Dms -> Deprecated,
    t.LabelValue.Ratel m Favor es -> Deprecated,
    t.LabelValue.Ratel m Follows -> Deprecated,
    t.LabelValue.Ratel m Ret ets -> Deprecated,
    t.LabelValue.Ratel m T ets -> Deprecated,
    t.LabelValue.RecentComprom sed -> Deprecated,
    t.LabelValue.RevenueOnlyHsS gnal -> Deprecated,
    t.LabelValue.SearchBlackl stHoldback -> Deprecated,
    t.LabelValue.SpamH ghRecallHoldback -> Deprecated,
    t.LabelValue.SpamRepeatOffender -> Deprecated,
    t.LabelValue.Spam rExper  nt -> Deprecated,
    t.LabelValue.TrendBlackl st -> Deprecated,
    t.LabelValue.Ver f edDecept ve dent y -> Deprecated,
    t.LabelValue.BrandSafetyNsfaAggregate -> Deprecated,
    t.LabelValue.Pcf -> Deprecated,
    t.LabelValue.Reserved97 -> Deprecated,
    t.LabelValue.Reserved98 -> Deprecated,
    t.LabelValue.Reserved99 -> Deprecated,
    t.LabelValue.Reserved100 -> Deprecated,
    t.LabelValue.Reserved101 -> Deprecated,
    t.LabelValue.Reserved102 -> Deprecated,
    t.LabelValue.Reserved103 -> Deprecated,
    t.LabelValue.Reserved104 -> Deprecated,
    t.LabelValue.Reserved105 -> Deprecated,
    t.LabelValue.Reserved106 -> Deprecated
  )

  pr vate lazy val modelToThr ftMap: Map[UserLabelValue, t.LabelValue] =
    (for ((k, v) <- thr ftToModelMap) y eld (v, k)) ++ Map(
      Deprecated -> t.LabelValue.EnumUnknownLabelValue(DeprecatedEnumValue),
    )

  case object Abus ve extends UserLabelValue
  case object Abus veH ghRecall extends UserLabelValue
  case object AgathaSpamTopUser extends UserLabelValue
  case object B rdwatchD sabled extends UserLabelValue
  case object Bl nkBad extends UserLabelValue
  case object Bl nkQuest onable extends UserLabelValue
  case object Bl nkWorst extends UserLabelValue
  case object Comprom sed extends UserLabelValue
  case object DelayedRe d at on extends UserLabelValue
  case object DoNotAmpl fy extends UserLabelValue
  case object DoNotCharge extends UserLabelValue
  case object DownrankSpamReply extends UserLabelValue
  case object Dupl cateContent extends UserLabelValue
  case object Engage ntSpam r extends UserLabelValue
  case object Engage ntSpam rH ghRecall extends UserLabelValue
  case object Exper  ntalPfmUser1 extends UserLabelValue
  case object Exper  ntalPfmUser2 extends UserLabelValue
  case object Exper  ntalPfmUser3 extends UserLabelValue
  case object Exper  ntalPfmUser4 extends UserLabelValue
  case object Exper  ntalSeh1 extends UserLabelValue
  case object Exper  ntalSeh2 extends UserLabelValue
  case object Exper  ntalSeh3 extends UserLabelValue
  case object Exper  ntalSehUser4 extends UserLabelValue
  case object Exper  ntalSehUser5 extends UserLabelValue
  case object Exper  ntalSens  ve llegal1 extends UserLabelValue
  case object Exper  ntalSens  ve llegal2 extends UserLabelValue
  case object FakeS gnupDeferredRe d at on extends UserLabelValue
  case object FakeS gnupHoldback extends UserLabelValue
  case object GoreAndV olenceH ghPrec s on extends UserLabelValue
  case object GoreAndV olenceReported ur st cs extends UserLabelValue
  case object  althExper  ntat on1 extends UserLabelValue
  case object  althExper  ntat on2 extends UserLabelValue
  case object H ghR skVer f cat on extends UserLabelValue
  case object LegalOpsCase extends UserLabelValue
  case object L kely vs extends UserLabelValue
  case object L veLowQual y extends UserLabelValue
  case object LowQual y extends UserLabelValue
  case object LowQual yH ghRecall extends UserLabelValue
  case object Not f cat onSpam ur st cs extends UserLabelValue
  case object NotGraduated extends UserLabelValue
  case object NsfwAvatar mage extends UserLabelValue
  case object NsfwBanner mage extends UserLabelValue
  case object NsfwH ghPrec s on extends UserLabelValue
  case object NsfwH ghRecall extends UserLabelValue
  case object NsfwNearPerfect extends UserLabelValue
  case object NsfwReported ur st cs extends UserLabelValue
  case object NsfwSens  ve extends UserLabelValue
  case object NsfwText extends UserLabelValue
  case object ReadOnly extends UserLabelValue
  case object RecentAbuseStr ke extends UserLabelValue
  case object RecentProf leMod f cat on extends UserLabelValue
  case object RecentM s nfoStr ke extends UserLabelValue
  case object RecentSuspens on extends UserLabelValue
  case object Recom ndat onsBlackl st extends UserLabelValue
  case object SearchBlackl st extends UserLabelValue
  case object SoftReadOnly extends UserLabelValue
  case object SpamH ghRecall extends UserLabelValue
  case object Spam UserModelH ghPrec s on extends UserLabelValue
  case object State d aAccount extends UserLabelValue
  case object TsV olat on extends UserLabelValue
  case object Unconf r dEma lS gnup extends UserLabelValue

  case object Deprecated extends UserLabelValue
  case object Unknown extends UserLabelValue

  def fromThr ft(userLabelValue: t.LabelValue): UserLabelValue = {
    thr ftToModelMap.get(userLabelValue) match {
      case So (safetyLabelType) => safetyLabelType
      case _ =>
        userLabelValue match {
          case t.LabelValue.EnumUnknownLabelValue(DeprecatedEnumValue) => Deprecated
          case _ =>
            Unknown
        }
    }
  }

  def toThr ft(userLabelValue: UserLabelValue): t.LabelValue =
    modelToThr ftMap.get((userLabelValue)).getOrElse(UnknownThr ftUserLabelValue)

  val L st: L st[UserLabelValue] = t.LabelValue.l st.map(fromThr ft)
}

object UserLabel {
  def fromThr ft(userLabel: t.Label): UserLabel = {
    UserLabel(
      userLabel. d,
      T  .fromM ll seconds(userLabel.createdAtMsec),
      userLabel.byUser,
      UserLabelValue.fromThr ft(userLabel.labelValue),
      userLabel.s ce.flatMap(LabelS ce.fromStr ng)
    )
  }

  def toThr ft(userLabel: UserLabel): t.Label = {
    t.Label(
      userLabel. d,
      UserLabelValue.toThr ft(userLabel.labelValue),
      userLabel.createdAt. nM ll s,
      byUser = userLabel.createdBy,
      s ce = userLabel.s ce.map(_.na )
    )
  }
}
