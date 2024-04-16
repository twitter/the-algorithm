package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.conf gap .params.RuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams
 mport com.tw ter.v s b l y.models.Content d
 mport com.tw ter.v s b l y.rules.Conversat onControlRules._
 mport com.tw ter.v s b l y.rules.Follo rRelat ons.AuthorMutesV e rRule
 mport com.tw ter.v s b l y.rules.Follo rRelat ons.ProtectedV e rRule
 mport com.tw ter.v s b l y.rules.Pol cyLevelRuleParams.ruleParams
 mport com.tw ter.v s b l y.rules.Publ c nterestRules._
 mport com.tw ter.v s b l y.rules.SafeSearchT etRules._
 mport com.tw ter.v s b l y.rules.SafeSearchUserRules.SafeSearchNsfwAvatar mageUserLabelRule
 mport com.tw ter.v s b l y.rules.SafeSearchUserRules._
 mport com.tw ter.v s b l y.rules.SpaceRules._
 mport com.tw ter.v s b l y.rules.Tox c yReplyF lterRules.Tox c yReplyF lterDropNot f cat onRule
 mport com.tw ter.v s b l y.rules.Tox c yReplyF lterRules.Tox c yReplyF lterRule
 mport com.tw ter.v s b l y.rules.UnsafeSearchT etRules._
 mport com.tw ter.v s b l y.rules.UserUnava lableStateTombstoneRules._

abstract class V s b l yPol cy(
  val t etRules: Seq[Rule] = N l,
  val userRules: Seq[Rule] = N l,
  val cardRules: Seq[Rule] = N l,
  val quotedT etRules: Seq[Rule] = N l,
  val dmRules: Seq[Rule] = N l,
  val dmConversat onRules: Seq[Rule] = N l,
  val dmEventRules: Seq[Rule] = N l,
  val spaceRules: Seq[Rule] = N l,
  val userUnava lableStateRules: Seq[Rule] = N l,
  val tw terArt cleRules: Seq[Rule] = N l,
  val deletedT etRules: Seq[Rule] = N l,
  val  d aRules: Seq[Rule] = N l,
  val commun yRules: Seq[Rule] = N l,
  val pol cyRuleParams: Map[Rule, Pol cyLevelRuleParams] = Map.empty) {

  def forContent d(content d: Content d): Seq[Rule] =
    content d match {
      case Content d.T et d(_) => t etRules
      case Content d.User d(_) => userRules
      case Content d.Card d(_) => cardRules
      case Content d.QuotedT etRelat onsh p(_, _) => quotedT etRules
      case Content d.Not f cat on d(_) => userRules
      case Content d.Dm d(_) => dmRules
      case Content d.BlenderT et d(_) => userRules ++ t etRules
      case Content d.Space d(_) => spaceRules
      case Content d.SpacePlusUser d(_) => spaceRules ++ userRules
      case Content d.DmConversat on d(_) => dmConversat onRules
      case Content d.DmEvent d(_) => dmEventRules
      case Content d.UserUnava lableState(_) => userUnava lableStateRules
      case Content d.Tw terArt cle d(_) => tw terArt cleRules
      case Content d.DeleteT et d(_) => deletedT etRules
      case Content d. d a d(_) =>  d aRules
      case Content d.Commun y d(_) => commun yRules
    }

  pr vate[v s b l y] def allRules: Seq[Rule] =
    (t etRules ++ userRules ++ cardRules ++ quotedT etRules ++ dmRules ++ spaceRules ++ dmConversat onRules ++ dmEventRules ++ tw terArt cleRules ++ deletedT etRules ++  d aRules ++ commun yRules)
}

object V s b l yPol cy {
  val baseT etRules = Seq(
    DropCommun yT etsRule,
    DropCommun yT etCommun yNotV s bleRule,
    DropProtectedCommun yT etsRule,
    DropH ddenCommun yT etsRule,
    DropAuthorRemovedCommun yT etsRule,
    SpamT etLabelRule,
    PdnaT etLabelRule,
    BounceT etLabelRule,
    DropExclus veT etContentRule,
    DropTrustedFr endsT etContentRule
  )

  val baseT etTombstoneRules = Seq(
    TombstoneCommun yT etsRule,
    TombstoneCommun yT etCommun yNotV s bleRule,
    TombstoneProtectedCommun yT etsRule,
    TombstoneH ddenCommun yT etsRule,
    TombstoneAuthorRemovedCommun yT etsRule,
    SpamT etLabelTombstoneRule,
    PdnaT etLabelTombstoneRule,
    BounceT etLabelTombstoneRule,
    TombstoneExclus veT etContentRule,
    TombstoneTrustedFr endsT etContentRule,
  )

  val base d aRules = Seq(
  )

  val baseQuotedT etTombstoneRules = Seq(
    BounceQuotedT etTombstoneRule
  )

  def un on[T](rules: Seq[Rule]*): Seq[Rule] = {
     f (rules. sEmpty) {
      Seq.empty[Rule]
    } else {
      rules.reduce((a, b) => a ++ b.f lterNot(a.conta ns))
    }
  }
}

case class Pol cyLevelRuleParams(
  ruleParams: Seq[RuleParam[Boolean]],
  force: Boolean = false) {}

object Pol cyLevelRuleParams {
  def ruleParams(ruleParams: RuleParam[Boolean]*): Pol cyLevelRuleParams = {
    Pol cyLevelRuleParams(ruleParams)
  }

  def ruleParams(force: Boolean, ruleParams: RuleParam[Boolean]*): Pol cyLevelRuleParams = {
    Pol cyLevelRuleParams(ruleParams, force)
  }
}

case object F lterAllPol cy
    extends V s b l yPol cy(
      t etRules = Seq(DropAllRule),
      userRules = Seq(DropAllRule),
      cardRules = Seq(DropAllRule),
      quotedT etRules = Seq(DropAllRule),
      dmRules = Seq(DropAllRule),
      dmConversat onRules = Seq(DropAllRule),
      dmEventRules = Seq(DropAllRule),
      spaceRules = Seq(DropAllRule),
      userUnava lableStateRules = Seq(DropAllRule),
      tw terArt cleRules = Seq(DropAllRule),
      deletedT etRules = Seq(DropAllRule),
       d aRules = Seq(DropAllRule),
      commun yRules = Seq(DropAllRule),
    )

case object F lterNonePol cy extends V s b l yPol cy()

object Conversat onsAdAvo danceRules {
  val t etRules = Seq(
    NsfwH ghRecallT etLabelAvo dRule,
    NsfwH ghPrec s onT etLabelAvo dRule,
    NsfwTextT etLabelAvo dRule,
    Avo dH ghTox c yModelScoreRule,
    Avo dReportedT etModelScoreRule,
    NsfwH ghPrec s onUserLabelAvo dT etRule,
    T etNsfwUserAdm nAvo dRule,
    DoNotAmpl fyT etLabelAvo dRule,
    NsfaH ghPrec s onT etLabelAvo dRule,
  )

  val pol cyRuleParams = Map[Rule, Pol cyLevelRuleParams](
    NsfwH ghRecallT etLabelAvo dRule -> ruleParams(
      RuleParams.EnableNewAdAvo danceRulesParam
    ),
    NsfwH ghPrec s onT etLabelAvo dRule -> ruleParams(
      RuleParams.EnableNewAdAvo danceRulesParam
    ),
    NsfwTextT etLabelAvo dRule -> ruleParams(RuleParams.EnableNewAdAvo danceRulesParam),
    Avo dH ghTox c yModelScoreRule -> ruleParams(RuleParams.EnableNewAdAvo danceRulesParam),
    Avo dReportedT etModelScoreRule -> ruleParams(RuleParams.EnableNewAdAvo danceRulesParam),
    NsfwH ghPrec s onUserLabelAvo dT etRule -> ruleParams(
      RuleParams.EnableNewAdAvo danceRulesParam),
    T etNsfwUserAdm nAvo dRule -> ruleParams(RuleParams.EnableNewAdAvo danceRulesParam),
    DoNotAmpl fyT etLabelAvo dRule -> ruleParams(RuleParams.EnableNewAdAvo danceRulesParam),
    NsfaH ghPrec s onT etLabelAvo dRule -> ruleParams(RuleParams.EnableNewAdAvo danceRulesParam),
  )
}

case object F lterDefaultPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule
        )
    )

case object L m edEngage ntBaseRules
    extends V s b l yPol cy(
      t etRules = Seq(
        StaleT etL m edAct onsRule,
        L m Repl esBy nv at onConversat onRule,
        L m Repl esCommun yConversat onRule,
        L m Repl esFollo rsConversat onRule,
        Commun yT etCommun yNotFoundL m edAct onsRule,
        Commun yT etCommun yDeletedL m edAct onsRule,
        Commun yT etCommun ySuspendedL m edAct onsRule,
        Commun yT et mberRemovedL m edAct onsRule,
        Commun yT etH ddenL m edAct onsRule,
        Commun yT et mberL m edAct onsRule,
        Commun yT etNon mberL m edAct onsRule,
        Dynam cProductAdL m edEngage ntT etLabelRule,
        TrustedFr endsT etL m edEngage ntsRule
      )
    )

case object Wr ePathL m edAct onsEnforce ntPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        AbusePol cyEp sod cT etLabel nterst  alRule,
        E rgencyDynam c nterst  alRule
      ) ++
        L m edEngage ntBaseRules.t etRules
    )

case object TestPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        TestRule
      )
    )

case object CardsServ cePol cy
    extends V s b l yPol cy(
      cardRules = Seq(
        DropProtectedAuthorPollCardRule,
        DropCardUr RootDoma nDenyl stRule
      ),
      spaceRules = Seq(
        SpaceH ghTox c yScoreNonFollo rDropRule,
        SpaceHatefulH ghRecallAllUsersDropRule,
        SpaceV olenceH ghRecallAllUsersDropRule,
        V e r sSoftUserDropRule
      ),
    )

case object CardPollVot ngPol cy
    extends V s b l yPol cy(
      cardRules = Seq(
        DropProtectedAuthorPollCardRule,
        DropCommun yNon mberPollCardRule
      )
    )

case object UserT  l neRules {
  val UserRules = Seq(
    AuthorBlocksV e rDropRule,
    ProtectedAuthorDropRule,
    SuspendedAuthorRule
  )
}

case object T  l neL kedByRules {
  val UserRules = Seq(
    Comprom sedNonFollo rW hUqfRule,
    Engage ntSpam rNonFollo rW hUqfRule,
    LowQual yNonFollo rW hUqfRule,
    ReadOnlyNonFollo rW hUqfRule,
    SpamH ghRecallNonFollo rW hUqfRule
  )
}

case object Follow ngAndFollo rsUserL stPol cy
    extends V s b l yPol cy(
      userRules = UserT  l neRules.UserRules
    )

case object Fr endsFollow ngL stPol cy
    extends V s b l yPol cy(
      userRules = UserT  l neRules.UserRules
    )

case object L stOwnersh psPol cy
    extends V s b l yPol cy(
      userRules = UserT  l neRules.UserRules
    )

case object L stRecom ndat onsPol cy
    extends V s b l yPol cy(
      userRules = Recom ndat onsPol cy.userRules ++ Seq(
        DropNsfwUserAuthorRule,
        NsfwH ghRecallRule,
        SearchBlackl stRule,
        SearchNsfwTextRule,
        V e rBlocksAuthorRule,
        V e rMutesAuthorRule
      )
    )

case object L stSearchBaseRules {

  val NonExper  ntalSafeSearchM n malPol cyUserRules: Seq[Rule] =
    SafeSearchM n malPol cy.userRules.f lterNot(_. sExper  ntal)

  val M n malPol cyUserRules: Seq[Rule] = NonExper  ntalSafeSearchM n malPol cyUserRules

  val BlockMutePol cyUserRules = Seq(
    V e rBlocksAuthorV e rOpt nBlock ngOnSearchRule,
    V e rMutesAuthorV e rOpt nBlock ngOnSearchRule
  )

  val Str ctPol cyUserRules = Seq(
    SafeSearchAbus veUserLabelRule,
    SafeSearchAbus veH ghRecallUserLabelRule,
    SafeSearchComprom sedUserLabelRule,
    SafeSearchDoNotAmpl fyNonFollo rsUserLabelRule,
    SafeSearchDupl cateContentUserLabelRule,
    SafeSearchLowQual yUserLabelRule,
    SafeSearchNotGraduatedNonFollo rsUserLabelRule,
    SafeSearchNsfwH ghPrec s onUserLabelRule,
    SafeSearchNsfwAvatar mageUserLabelRule,
    SafeSearchNsfwBanner mageUserLabelRule,
    SafeSearchReadOnlyUserLabelRule,
    SafeSearchSearchBlackl stUserLabelRule,
    SafeSearchNsfwTextUserLabelRule,
    SafeSearchSpamH ghRecallUserLabelRule,
    SafeSearchDownrankSpamReplyAuthorLabelRule,
    SafeSearchNsfwTextAuthorLabelRule,
    DropNsfwAdm nAuthorV e rOpt nF lter ngOnSearchRule,
    DropNsfwUserAuthorV e rOpt nF lter ngOnSearchRule,
  )
}

object Sens  ve d aSett ngsT  l neHo BaseRules {
  val pol cyRuleParams = Map[Rule, Pol cyLevelRuleParams](
    NsfwH ghPrec s on nterst  alAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aHo T  l neRulesParam),
    GoreAndV olenceH ghPrec s onAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aHo T  l neRulesParam),
    NsfwReported ur st csAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aHo T  l neRulesParam),
    GoreAndV olenceReported ur st csAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aHo T  l neRulesParam),
    NsfwCard mageAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aHo T  l neRulesParam),
    Sens  ve d aT et nterst  alRules.Adult d aNsfwH ghPrec s onT etLabel nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsHo T  l neRulesParam),
    Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceH ghPrec s on nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsHo T  l neRulesParam),
    Sens  ve d aT et nterst  alRules.Adult d aNsfwReported ur st csT etLabel nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsHo T  l neRulesParam),
    Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceReported ur st cs nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsHo T  l neRulesParam),
    Sens  ve d aT et nterst  alRules.Adult d aNsfwCard mageT etLabel nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsHo T  l neRulesParam),
    Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwUserT etFlag nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsHo T  l neRulesParam),
    Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwAdm nT etFlag nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsHo T  l neRulesParam)
  )
}

object Sens  ve d aSett ngsConversat onBaseRules {
  val pol cyRuleParams = Map[Rule, Pol cyLevelRuleParams](
    NsfwH ghPrec s on nterst  alAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aConversat onRulesParam),
    GoreAndV olenceH ghPrec s onAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aConversat onRulesParam),
    NsfwReported ur st csAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aConversat onRulesParam),
    GoreAndV olenceReported ur st csAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aConversat onRulesParam),
    NsfwCard mageAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aConversat onRulesParam),
    Sens  ve d aT et nterst  alRules.Adult d aNsfwH ghPrec s onT etLabel nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsConversat onRulesParam),
    Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceH ghPrec s on nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsConversat onRulesParam),
    Sens  ve d aT et nterst  alRules.Adult d aNsfwReported ur st csT etLabel nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsConversat onRulesParam),
    Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceReported ur st cs nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsConversat onRulesParam),
    Sens  ve d aT et nterst  alRules.Adult d aNsfwCard mageT etLabel nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsConversat onRulesParam),
    Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwUserT etFlag nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsConversat onRulesParam),
    Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwAdm nT etFlag nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsConversat onRulesParam)
  )
}

object Sens  ve d aSett ngsProf leT  l neBaseRules {
  val pol cyRuleParams = Map[Rule, Pol cyLevelRuleParams](
    NsfwH ghPrec s on nterst  alAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aProf leT  l neRulesParam),
    GoreAndV olenceH ghPrec s onAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aProf leT  l neRulesParam),
    NsfwReported ur st csAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aProf leT  l neRulesParam),
    GoreAndV olenceReported ur st csAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aProf leT  l neRulesParam),
    NsfwCard mageAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aProf leT  l neRulesParam),
    Sens  ve d aT et nterst  alRules.Adult d aNsfwH ghPrec s onT etLabel nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsProf leT  l neRulesParam),
    Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceH ghPrec s on nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsProf leT  l neRulesParam),
    Sens  ve d aT et nterst  alRules.Adult d aNsfwReported ur st csT etLabel nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsProf leT  l neRulesParam),
    Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceReported ur st cs nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsProf leT  l neRulesParam),
    Sens  ve d aT et nterst  alRules.Adult d aNsfwCard mageT etLabel nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsProf leT  l neRulesParam),
    Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwUserT etFlag nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsProf leT  l neRulesParam),
    Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwAdm nT etFlag nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsProf leT  l neRulesParam)
  )
}

object Sens  ve d aSett ngsT etDeta lBaseRules {
  val pol cyRuleParams = Map[Rule, Pol cyLevelRuleParams](
    NsfwH ghPrec s on nterst  alAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aT etDeta lRulesParam),
    GoreAndV olenceH ghPrec s onAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aT etDeta lRulesParam),
    NsfwReported ur st csAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aT etDeta lRulesParam),
    GoreAndV olenceReported ur st csAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aT etDeta lRulesParam),
    NsfwCard mageAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aT etDeta lRulesParam),
    Sens  ve d aT et nterst  alRules.Adult d aNsfwH ghPrec s onT etLabel nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsT etDeta lRulesParam),
    Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceH ghPrec s on nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsT etDeta lRulesParam),
    Sens  ve d aT et nterst  alRules.Adult d aNsfwReported ur st csT etLabel nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsT etDeta lRulesParam),
    Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceReported ur st cs nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsT etDeta lRulesParam),
    Sens  ve d aT et nterst  alRules.Adult d aNsfwCard mageT etLabel nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsT etDeta lRulesParam),
    Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwUserT etFlag nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsT etDeta lRulesParam),
    Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwAdm nT etFlag nterst  alRule -> ruleParams(
      RuleParams.EnableNewSens  ve d aSett ngs nterst  alsT etDeta lRulesParam)
  )
}

case object L stSearchPol cy
    extends V s b l yPol cy(
      userRules = L stSearchBaseRules.M n malPol cyUserRules ++
        L stSearchBaseRules.BlockMutePol cyUserRules ++
        L stSearchBaseRules.Str ctPol cyUserRules
    )

case object L stSubscr pt onsPol cy
    extends V s b l yPol cy(
      userRules = UserT  l neRules.UserRules
    )

case object L st mbersh psPol cy
    extends V s b l yPol cy(
      userRules = UserT  l neRules.UserRules
    )

case object AllSubscr bedL stsPol cy
    extends V s b l yPol cy(
      userRules = UserT  l neRules.UserRules
    )

case object L st aderPol cy
    extends V s b l yPol cy(
      userRules = Seq(
        AuthorBlocksV e rDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      )
    )

case object NewUserExper encePol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        Abus veT etLabelRule,
        LowQual yT etLabelDropRule,
        NsfaH ghRecallT etLabelRule,
        NsfwH ghPrec s onT etLabelRule,
        GoreAndV olenceH ghPrec s onT etLabelRule,
        NsfwReported ur st csT etLabelRule,
        GoreAndV olenceReported ur st csT etLabelRule,
        NsfwCard mageT etLabelRule,
        NsfwH ghRecallT etLabelRule,
        NsfwV deoT etLabelDropRule,
        NsfwTextT etLabelDropRule,
        SpamH ghRecallT etLabelDropRule,
        Dupl cateContentT etLabelDropRule,
        GoreAndV olenceT etLabelRule,
        UntrustedUrlT etLabelRule,
        DownrankSpamReplyT etLabelRule,
        SearchBlackl stT etLabelRule,
        Automat onT etLabelRule,
        Dupl cate nt onT etLabelRule,
        BystanderAbus veT etLabelRule,
        SafetyCr s sLevel3DropRule,
        SafetyCr s sLevel4DropRule,
        DoNotAmpl fyDropRule,
        S teSpamT etLabelDropRule,
      ),
      userRules = Seq(
        Abus veRule,
        LowQual yRule,
        ReadOnlyRule,
        SearchBlackl stRule,
        SearchNsfwTextRule,
        Comprom sedRule,
        SpamH ghRecallRule,
        Dupl cateContentRule,
        NsfwH ghPrec s onRule,
        NsfwAvatar mageRule,
        NsfwBanner mageRule,
        Abus veH ghRecallRule,
        DoNotAmpl fyNonFollo rRule,
        NotGraduatedNonFollo rRule,
        L kely vsLabelNonFollo rDropUserRule,
        DownrankSpamReplyNonAuthorRule,
        NsfwTextNonAuthorDropRule
      )
    )

case object DESHo T  l nePol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        DropStaleT etsRule,
        DropAllExclus veT etsRule,
        DropAllTrustedFr endsT etsRule,
        DropAllCommun yT etsRule
      ) ++
        V s b l yPol cy.baseT etRules,
      userRules = UserT  l neRules.UserRules
    )

case object DesQuoteT etT  l nePol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        DropStaleT etsRule,
        DropAllExclus veT etsRule,
        DropAllTrustedFr endsT etsRule
      ) ++ ElevatedQuoteT etT  l nePol cy.t etRules.d ff(Seq(DropStaleT etsRule)),
      userRules = Seq(
        ProtectedAuthorDropRule
      ),
      pol cyRuleParams = ElevatedQuoteT etT  l nePol cy.pol cyRuleParams
    )

case object DESRealt  SpamEnr ch ntPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        LowQual yT etLabelDropRule,
        SpamH ghRecallT etLabelDropRule,
        Dupl cateContentT etLabelDropRule,
        SearchBlackl stT etLabelRule,
        S teSpamT etLabelDropRule,
        DropAllCommun yT etsRule,
        DropAllExclus veT etsRule,
        DropAllTrustedFr endsT etsRule,
        NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
        GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
        NsfwReported ur st csAllUsersT etLabelRule,
        GoreAndV olenceReported ur st csAllUsersT etLabelRule,
        NsfwCard mageAllUsersT etLabelRule
      )
    )

case object DESRealt  Pol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        DropAllCommun yT etsRule,
        DropAllExclus veT etsRule,
        DropAllTrustedFr endsT etsRule,
        DropAllCollab nv at onT etsRule
      ),
      userRules = Seq(
        DropAllProtectedAuthorRule,
        DropProtectedV e r fPresentRule
      )
    )

case object DESRet et ngUsersPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        DropAllExclus veT etsRule,
        DropAllTrustedFr endsT etsRule,
      ),
      userRules = Seq(
        AuthorBlocksV e rDropRule,
        V e rBlocksAuthorRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      )
    )

case object DEST etL k ngUsersPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        DropAllExclus veT etsRule,
        DropAllTrustedFr endsT etsRule,
      ),
      userRules = T  l neL kedByRules.UserRules
    )

case object DESUserBookmarksPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        DropAllExclus veT etsRule,
        DropAllTrustedFr endsT etsRule,
      ) ++
        (V s b l yPol cy.baseT etRules
          ++ Seq(DropAllCommun yT etsRule)
          ++ T  l neProf leRules.t etRules),
      userRules = UserT  l neRules.UserRules
    )

case object DESUserL kedT etsPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        DropStaleT etsRule,
        DropAllExclus veT etsRule,
        DropAllTrustedFr endsT etsRule,
      ) ++
        (
          V s b l yPol cy.baseT etRules ++
            Seq(
              DropAllCommun yT etsRule,
              AbusePol cyEp sod cT etLabel nterst  alRule,
              E rgencyDynam c nterst  alRule,
              ReportedT et nterst  alRule,
              NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
              GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
              NsfwReported ur st csAllUsersT etLabelRule,
              GoreAndV olenceReported ur st csAllUsersT etLabelRule,
              NsfwCard mageAllUsersT etLabelRule,
              NsfwH ghPrec s onT etLabelAvo dRule,
              NsfwH ghRecallT etLabelAvo dRule,
              GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule,
              NsfwReported ur st csAvo dAllUsersT etLabelRule,
              GoreAndV olenceReported ur st csAvo dAllUsersT etLabelRule,
              NsfwCard mageAvo dAllUsersT etLabelRule,
              DoNotAmpl fyT etLabelAvo dRule,
              NsfaH ghPrec s onT etLabelAvo dRule,
            ) ++ L m edEngage ntBaseRules.t etRules
        ),
      userRules = UserT  l neRules.UserRules
    )

case object DESUser nt onsPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        DropAllCommun yT etsRule,
        AuthorBlocksV e rDropRule,
        ProtectedAuthorDropRule,
        DropAllExclus veT etsRule,
        DropAllTrustedFr endsT etsRule,
        AbusePol cyEp sod cT etLabel nterst  alRule,
        E rgencyDynam c nterst  alRule,
        NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
        GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
        NsfwReported ur st csAllUsersT etLabelRule,
        GoreAndV olenceReported ur st csAllUsersT etLabelRule,
        NsfwCard mageAllUsersT etLabelRule,
      ) ++ L m edEngage ntBaseRules.t etRules,
      userRules = Seq(
        SuspendedAuthorRule
      )
    )

case object DESUserT etsPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        DropStaleT etsRule,
        DropAllExclus veT etsRule,
        DropAllTrustedFr endsT etsRule,
      ) ++
        (V s b l yPol cy.baseT etRules
          ++ Seq(DropAllCommun yT etsRule)
          ++ T  l neProf leRules.t etRules),
      userRules = UserT  l neRules.UserRules
    )

case object DevPlatformCompl anceStreamPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        SpamAllUsersT etLabelRule,
        PdnaAllUsersT etLabelRule,
        BounceAllUsersT etLabelRule,
        AbusePol cyEp sod cT etLabelCompl anceT etNot ceRule,
      )
    )

case object DesT etDeta lPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        DropAllExclus veT etsRule,
        DropAllTrustedFr endsT etsRule,
      ) ++ BaseT etDeta lPol cy.t etRules
    )

case object DevPlatformGetL stT etsPol cy
    extends V s b l yPol cy(
      t etRules = Seq(DropStaleT etsRule) ++ DesT etDeta lPol cy.t etRules
    )

case object Follo rConnect onsPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules,
      userRules = Seq(
        Spam Follo rRule
      )
    )

case object SuperFollo rConnect onsPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules,
      userRules = Seq(
        Spam Follo rRule
      )
    )

case object L veP pel neEngage ntCountsPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        AbusePol cyEp sod cT etLabel nterst  alRule,
        E rgencyDynam c nterst  alRule,
      ) ++ L m edEngage ntBaseRules.t etRules
    )

case object L veV deoT  l nePol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        Abus veT etLabelRule,
        Abus veH ghRecallT etLabelRule,
        LowQual yT etLabelDropRule,
        NsfwH ghPrec s onT etLabelRule,
        GoreAndV olenceH ghPrec s onT etLabelRule,
        NsfwReported ur st csT etLabelRule,
        GoreAndV olenceReported ur st csT etLabelRule,
        NsfwCard mageT etLabelRule,
        NsfwH ghRecallT etLabelRule,
        NsfwV deoT etLabelDropRule,
        NsfwTextT etLabelDropRule,
        L veLowQual yT etLabelRule,
        SpamH ghRecallT etLabelDropRule,
        Dupl cateContentT etLabelDropRule,
        SearchBlackl stT etLabelRule,
        BystanderAbus veT etLabelRule,
        SafetyCr s sLevel3DropRule,
        SafetyCr s sLevel4DropRule,
        DoNotAmpl fyDropRule,
        S teSpamT etLabelDropRule,
        AbusePol cyEp sod cT etLabelDropRule,
        E rgencyDropRule,
      ),
      userRules = Seq(
        Abus veRule,
        LowQual yRule,
        ReadOnlyRule,
        SearchBlackl stRule,
        SearchNsfwTextRule,
        Comprom sedRule,
        NsfwH ghPrec s onRule,
        NsfwH ghRecallRule,
        NsfwAvatar mageRule,
        NsfwBanner mageRule,
        SpamH ghRecallRule,
        Dupl cateContentRule,
        L veLowQual yRule,
        Engage ntSpam rRule,
        Engage ntSpam rH ghRecallRule,
        Abus veH ghRecallRule,
        DoNotAmpl fyNonFollo rRule,
        NotGraduatedNonFollo rRule,
        L kely vsLabelNonFollo rDropUserRule,
        NsfwTextNonAuthorDropRule
      )
    )

case object Mag cRecsPol cyOverr des {
  val replace nts: Map[Rule, Rule] = Map()
  def un on(rules: Seq[Rule]*): Seq[Rule] = rules
    .map(ar => ar.map(x => replace nts.getOrElse(x, x)))
    .reduce((a, b) => a ++ b.f lterNot(a.conta ns))
}

case object Mag cRecsPol cy
    extends V s b l yPol cy(
      t etRules = Mag cRecsPol cyOverr des.un on(
        Recom ndat onsPol cy.t etRules.f lterNot(_ == SafetyCr s sLevel3DropRule),
        Not f cat ons b sPol cy.t etRules,
        Seq(
          NsfaH ghRecallT etLabelRule,
          NsfwH ghRecallT etLabelRule,
          NsfwTextH ghPrec s onT etLabelDropRule),
        Seq(
          AuthorBlocksV e rDropRule,
          V e rBlocksAuthorRule,
          V e rMutesAuthorRule
        ),
        Seq(
          Deact vatedAuthorRule,
          SuspendedAuthorRule,
          T etNsfwUserDropRule,
          T etNsfwAdm nDropRule
        )
      ),
      userRules = Mag cRecsPol cyOverr des.un on(
        Recom ndat onsPol cy.userRules,
        Not f cat onsRules.userRules
      )
    )

case object Mag cRecsV2Pol cy
    extends V s b l yPol cy(
      t etRules = Mag cRecsPol cyOverr des.un on(
        Mag cRecsPol cy.t etRules,
        Not f cat onsWr erT etHydratorPol cy.t etRules
      ),
      userRules = Mag cRecsPol cyOverr des.un on(
        Mag cRecsPol cy.userRules,
        Not f cat onsWr erV2Pol cy.userRules
      )
    )

case object Mag cRecsAggress vePol cy
    extends V s b l yPol cy(
      t etRules = Mag cRecsPol cy.t etRules,
      userRules = Mag cRecsPol cy.userRules
    )

case object Mag cRecsAggress veV2Pol cy
    extends V s b l yPol cy(
      t etRules = Mag cRecsV2Pol cy.t etRules,
      userRules = Mag cRecsV2Pol cy.userRules
    )

case object M n malPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules,
      userRules = Seq(
        TsV olat onRule
      )
    )

case object ModeratedT etsT  l nePol cy
    extends V s b l yPol cy(
      t etRules = T etDeta lPol cy.t etRules.d ff(
        Seq(
          AuthorBlocksV e rDropRule,
          MutedKeywordForT etRepl es nterst  alRule,
          ReportedT et nterst  alRule)),
      pol cyRuleParams = T etDeta lPol cy.pol cyRuleParams
    )

case object Mo ntsPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          AuthorBlocksV e rUnspec f edRule,
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
        ) ++ L m edEngage ntBaseRules.t etRules
    )

case object NearbyT  l nePol cy
    extends V s b l yPol cy(
      t etRules = SearchBlenderRules.t etRelevanceRules,
      userRules = SearchBlenderRules.userBaseRules
    )

pr vate object Not f cat onsRules {
  val t etRules: Seq[Rule] =
    DropStaleT etsRule +: V s b l yPol cy.baseT etRules

  val userRules: Seq[Rule] = Seq(
    Abus veRule,
    LowQual yRule,
    ReadOnlyRule,
    Comprom sedRule,
    SpamH ghRecallRule,
    Dupl cateContentRule,
    Abus veH ghRecallRule,
    Engage ntSpam rNonFollo rW hUqfRule,
    Engage ntSpam rH ghRecallNonFollo rW hUqfRule,
    DownrankSpamReplyNonFollo rW hUqfRule
  )
}

case object Not f cat ons b sPol cy
    extends V s b l yPol cy(
      t etRules =
          V s b l yPol cy.baseT etRules ++ Seq(
          Abus veUqfNonFollo rT etLabelRule,
          LowQual yT etLabelDropRule,
          Tox c yReplyF lterDropNot f cat onRule,
          NsfwH ghPrec s onT etLabelRule,
          GoreAndV olenceH ghPrec s onT etLabelRule,
          NsfwReported ur st csT etLabelRule,
          GoreAndV olenceReported ur st csT etLabelRule,
          NsfwCard mageT etLabelRule,
          NsfwV deoT etLabelDropRule,
          NsfwTextT etLabelDropRule,
          SpamH ghRecallT etLabelDropRule,
          Dupl cateContentT etLabelDropRule,
          Dupl cate nt onT etLabelRule,
          LowQual y nt onT etLabelRule,
          UntrustedUrlUqfNonFollo rT etLabelRule,
          DownrankSpamReplyUqfNonFollo rT etLabelRule,
          SafetyCr s sAnyLevelDropRule,
          DoNotAmpl fyDropRule,
          S teSpamT etLabelDropRule,
          AbusePol cyEp sod cT etLabelDropRule,
          E rgencyDropRule,
        ),
      userRules = Not f cat onsRules.userRules ++ Seq(
        DoNotAmpl fyNonFollo rRule,
        L kely vsLabelNonFollo rDropUserRule,
        NsfwTextNonAuthorDropRule
      )
    )

case object Not f cat onsReadPol cy
    extends V s b l yPol cy(
      t etRules = Not f cat onsRules.t etRules,
      userRules = Not f cat onsRules.userRules
    )

case object Not f cat onsT  l neDev ceFollowPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules,
      userRules = Seq(
        AuthorBlocksV e rDropRule,
        V e rBlocksAuthorRule,
        Comprom sedRule
      )
    )

case object Not f cat onsWr ePol cy
    extends V s b l yPol cy(
      t etRules = Not f cat onsRules.t etRules,
      userRules = Not f cat onsRules.userRules
    )

case object Not f cat onsWr erV2Pol cy
    extends V s b l yPol cy(
      userRules =
        Seq(
          AuthorBlocksV e rDropRule,
          Deact vatedAuthorRule,
          ErasedAuthorRule,
          ProtectedAuthorDropRule,
          SuspendedAuthorRule,
          Deact vatedV e rRule,
          SuspendedV e rRule,
          V e rBlocksAuthorRule,
          V e rMutesAndDoesNotFollowAuthorRule,
          V e r sUn nt onedRule,
          NoConf r dEma lRule,
          NoConf r dPhoneRule,
          NoDefaultProf le mageRule,
          NoNewUsersRule,
          NoNotFollo dByRule,
          OnlyPeople FollowRule
        ) ++
          Not f cat onsRules.userRules
    )

case object Not f cat onsWr erT etHydratorPol cy
    extends V s b l yPol cy(
      t etRules = Not f cat onsRules.t etRules ++
        Seq(
          LowQual yT etLabelDropRule,
          SpamH ghRecallT etLabelDropRule,
          Dupl cateContentT etLabelDropRule,
          Dupl cate nt onUqfT etLabelRule,
          LowQual y nt onT etLabelRule,
          S teSpamT etLabelDropRule,
          Tox c yReplyF lterDropNot f cat onRule,
          Abus veUqfNonFollo rT etLabelRule,
          UntrustedUrlUqfNonFollo rT etLabelRule,
          DownrankSpamReplyUqfNonFollo rT etLabelRule,
          V e rHasMatch ngMutedKeywordForNot f cat onsRule,
          NsfwCard mageAllUsersT etLabelRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
        ) ++ L m edEngage ntBaseRules.t etRules
    )

case object Not f cat onsPlatformPol cy
    extends V s b l yPol cy(
      t etRules = Not f cat onsWr erT etHydratorPol cy.t etRules,
      userRules = Not f cat onsWr erV2Pol cy.userRules
    )

case object Not f cat onsPlatformPushPol cy
    extends V s b l yPol cy(
      t etRules = Not f cat ons b sPol cy.t etRules,
      userRules = Seq(V e rMutesAuthorRule)
        ++ Not f cat ons b sPol cy.userRules
    )

case object QuoteT etT  l nePol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        DropStaleT etsRule,
        Abus veT etLabelRule,
        LowQual yT etLabelDropRule,
        NsfwH ghPrec s onT etLabelRule,
        GoreAndV olenceH ghPrec s onT etLabelRule,
        NsfwReported ur st csT etLabelRule,
        GoreAndV olenceReported ur st csT etLabelRule,
        NsfwCard mageT etLabelRule,
        NsfwH ghRecallT etLabelRule,
        NsfwV deoT etLabelDropRule,
        NsfwTextT etLabelDropRule,
        SpamH ghRecallT etLabelDropRule,
        Dupl cateContentT etLabelDropRule,
        GoreAndV olenceT etLabelRule,
        UntrustedUrlT etLabelRule,
        DownrankSpamReplyT etLabelRule,
        SearchBlackl stT etLabelRule,
        Automat onT etLabelRule,
        Dupl cate nt onT etLabelRule,
        BystanderAbus veT etLabelRule,
        S teSpamT etLabelDropRule,
        AbusePol cyEp sod cT etLabel nterst  alRule,
        E rgencyDynam c nterst  alRule,
      ) ++ L m edEngage ntBaseRules.t etRules,
      userRules = Seq(
        Abus veRule,
        LowQual yRule,
        ReadOnlyRule,
        SearchBlackl stRule,
        SearchNsfwTextRule,
        Comprom sedRule,
        SpamH ghRecallRule,
        Dupl cateContentRule,
        NsfwH ghPrec s onRule,
        NsfwAvatar mageRule,
        NsfwBanner mageRule,
        Abus veH ghRecallRule,
        DownrankSpamReplyNonAuthorRule,
        NsfwTextNonAuthorDropRule
      )
    )

case object ElevatedQuoteT etT  l nePol cy
    extends V s b l yPol cy(
      t etRules =
          T etDeta lPol cy.t etRules.d ff(
            Seq(
              MutedKeywordForQuotedT etT etDeta l nterst  alRule,
              ReportedT et nterst  alRule)),
      pol cyRuleParams = T etDeta lPol cy.pol cyRuleParams
    )

case object EmbedsPubl c nterestNot cePol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        AbusePol cyEp sod cT etLabel nterst  alRule,
        E rgencyDynam c nterst  alRule,
      )
    )

case object Recom ndat onsPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          Abus veT etLabelRule,
          LowQual yT etLabelDropRule,
          NsfwH ghPrec s onT etLabelRule,
          GoreAndV olenceH ghPrec s onT etLabelRule,
          NsfwReported ur st csT etLabelRule,
          GoreAndV olenceReported ur st csT etLabelRule,
          NsfwCard mageT etLabelRule,
          NsfwV deoT etLabelDropRule,
          NsfwTextT etLabelDropRule,
          SpamH ghRecallT etLabelDropRule,
          Dupl cateContentT etLabelDropRule,
          GoreAndV olenceT etLabelRule,
          BystanderAbus veT etLabelRule,
          DoNotAmpl fyDropRule,
          SafetyCr s sLevel3DropRule,
          S teSpamT etLabelDropRule,
          AbusePol cyEp sod cT etLabelDropRule,
          E rgencyDropRule,
        ),
      userRules = Seq(
        DropNsfwAdm nAuthorRule,
        Abus veRule,
        LowQual yRule,
        ReadOnlyRule,
        Comprom sedRule,
        Recom ndat onsBlackl stRule,
        SpamH ghRecallRule,
        Dupl cateContentRule,
        NsfwH ghPrec s onRule,
        NsfwNearPerfectAuthorRule,
        NsfwBanner mageRule,
        NsfwAvatar mageRule,
        Engage ntSpam rRule,
        Engage ntSpam rH ghRecallRule,
        Abus veH ghRecallRule,
        DoNotAmpl fyNonFollo rRule,
        NotGraduatedNonFollo rRule,
        L kely vsLabelNonFollo rDropUserRule,
        NsfwTextNonAuthorDropRule
      )
    )

case object RecosV deoPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        Abus veT etLabelRule,
        LowQual yT etLabelDropRule,
        NsfwH ghPrec s onT etLabelRule,
        GoreAndV olenceH ghPrec s onT etLabelRule,
        NsfwReported ur st csT etLabelRule,
        GoreAndV olenceReported ur st csT etLabelRule,
        NsfwCard mageT etLabelRule,
        NsfwH ghRecallT etLabelRule,
        NsfwV deoT etLabelDropRule,
        NsfwTextT etLabelDropRule,
        SpamH ghRecallT etLabelDropRule,
        Dupl cateContentT etLabelDropRule,
        BystanderAbus veT etLabelRule,
        S teSpamT etLabelDropRule,
      ),
      userRules = Seq(NsfwTextNonAuthorDropRule)
    )

case object Repl esGroup ngPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          LowQual yT etLabelDropRule,
          SpamH ghRecallT etLabelDropRule,
          Dupl cateContentT etLabelDropRule,
          Dec derableSpamH ghRecallAuthorLabelDropRule,
          S teSpamT etLabelDropRule,
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          MutedKeywordForT etRepl es nterst  alRule,
          ReportedT et nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
          NsfwH ghPrec s onT etLabelAvo dRule,
          NsfwH ghRecallT etLabelAvo dRule,
          GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule,
          NsfwReported ur st csAvo dAdPlace ntAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAvo dAdPlace ntAllUsersT etLabelRule,
          NsfwCard mageAvo dAdPlace ntAllUsersT etLabelRule,
          DoNotAmpl fyT etLabelAvo dRule,
          NsfaH ghPrec s onT etLabelAvo dRule,
        ) ++ L m edEngage ntBaseRules.t etRules,
      userRules = Seq(
        LowQual yRule,
        ReadOnlyRule,
        LowQual yH ghRecallRule,
        Comprom sedRule,
        Dec derableSpamH ghRecallRule
      )
    )

case object Return ngUserExper encePol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        Abus veT etLabelRule,
        LowQual yT etLabelDropRule,
        NsfaH ghRecallT etLabelRule,
        NsfwH ghPrec s onT etLabelRule,
        GoreAndV olenceH ghPrec s onT etLabelRule,
        NsfwReported ur st csT etLabelRule,
        GoreAndV olenceReported ur st csT etLabelRule,
        NsfwCard mageT etLabelRule,
        NsfwH ghRecallT etLabelRule,
        NsfwV deoT etLabelDropRule,
        NsfwTextT etLabelDropRule,
        NsfwTextH ghPrec s onT etLabelDropRule,
        SpamH ghRecallT etLabelDropRule,
        Dupl cateContentT etLabelDropRule,
        GoreAndV olenceT etLabelRule,
        UntrustedUrlT etLabelRule,
        DownrankSpamReplyT etLabelRule,
        SearchBlackl stT etLabelRule,
        Automat onT etLabelRule,
        Dupl cate nt onT etLabelRule,
        BystanderAbus veT etLabelRule,
        S teSpamT etLabelDropRule,
        SafetyCr s sLevel3DropRule,
        SafetyCr s sLevel4DropRule,
        DoNotAmpl fyDropRule,
        AbusePol cyEp sod cT etLabelDropRule,
        E rgencyDropRule,
      ) ++ L m edEngage ntBaseRules.t etRules,
      userRules = Seq(
        Abus veRule,
        LowQual yRule,
        ReadOnlyRule,
        SearchBlackl stRule,
        SearchNsfwTextRule,
        Comprom sedRule,
        SpamH ghRecallRule,
        Dupl cateContentRule,
        NsfwH ghPrec s onRule,
        NsfwAvatar mageRule,
        NsfwBanner mageRule,
        Abus veH ghRecallRule,
        DoNotAmpl fyNonFollo rRule,
        NotGraduatedNonFollo rRule,
        L kely vsLabelNonFollo rDropUserRule,
        DownrankSpamReplyNonAuthorRule,
        NsfwTextNonAuthorDropRule,
        DropNsfwUserAuthorRule,
        NsfwH ghRecallRule
      )
    )

case object Return ngUserExper enceFocalT etPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          AuthorBlocksV e rDropRule,
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
          MutedKeywordForT etRepl es nterst  alRule,
          V e rMutesAuthor nterst  alRule,
          ReportedT et nterst  alRule,
        ) ++ L m edEngage ntBaseRules.t etRules
    )

case object RevenuePol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          Abus veT etLabelRule,
          BystanderAbus veT etLabelRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule
        )
    )

case object SafeSearchM n malPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        DropOuterCommun yT etsRule,
      ) ++ V s b l yPol cy.baseT etRules ++ Seq(
        LowQual yT etLabelDropRule,
        H ghProact veTosScoreT etLabelDropSearchRule,
        SpamH ghRecallT etLabelDropRule,
        Dupl cateContentT etLabelDropRule,
        SearchBlackl stT etLabelRule,
        SearchBlackl stH ghRecallT etLabelDropRule,
        SafetyCr s sLevel3DropRule,
        SafetyCr s sLevel4DropRule,
        DoNotAmpl fyDropRule,
        S teSpamT etLabelDropRule,
      ) ++
        Seq(
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
        ) ++ L m edEngage ntBaseRules.t etRules
        ++ SearchBlenderRules.t etAvo dRules,
      userRules = Seq(
        LowQual yRule,
        ReadOnlyRule,
        Comprom sedRule,
        SpamH ghRecallRule,
        SearchBlackl stRule,
        SearchNsfwTextRule,
        Dupl cateContentRule,
        DoNotAmpl fyNonFollo rRule,
        SearchL kely vsLabelNonFollo rDropUserRule
      )
    )

case object SearchHydrat onPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        AbusePol cyEp sod cT etLabel nterst  alRule,
        E rgencyDynam c nterst  alRule,
        ReportedT et nterst  alSearchRule,
        NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
        GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
        NsfwReported ur st csAllUsersT etLabelRule,
        GoreAndV olenceReported ur st csAllUsersT etLabelRule,
        NsfwCard mageAllUsersT etLabelRule,
      ) ++ L m edEngage ntBaseRules.t etRules
    )

case object SearchBlenderRules {
  val l m edEngage ntBaseRules: Seq[Rule] = L m edEngage ntBaseRules.t etRules

  val t etAvo dRules: Seq[Rule] =
    Seq(
      NsfwH ghPrec s onT etLabelAvo dRule,
      NsfwH ghRecallT etLabelAvo dRule,
      GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule,
      NsfwReported ur st csAvo dAllUsersT etLabelRule,
      GoreAndV olenceReported ur st csAvo dAllUsersT etLabelRule,
      NsfwCard mageAvo dAllUsersT etLabelRule,
      SearchAvo dT etNsfwAdm nRule,
      SearchAvo dT etNsfwUserRule,
      DoNotAmpl fyT etLabelAvo dRule,
      NsfaH ghPrec s onT etLabelAvo dRule,
    )

  val bas cBlockMuteRules: Seq[Rule] = Seq(
    AuthorBlocksV e rDropRule,
    V e rBlocksAuthorV e rOpt nBlock ngOnSearchRule,
    V e rMutesAuthorV e rOpt nBlock ngOnSearchRule
  )

  val t etRelevanceRules: Seq[Rule] =
    Seq(
      DropOuterCommun yT etsRule,
      DropStaleT etsRule,
    ) ++ V s b l yPol cy.baseT etRules ++ Seq(
      SafeSearchAbus veT etLabelRule,
      LowQual yT etLabelDropRule,
      H ghProact veTosScoreT etLabelDropSearchRule,
      H ghPSpam T etScoreSearchT etLabelDropRule,
      H ghSpam T etContentScoreSearchTopT etLabelDropRule,
      H ghSpam T etContentScoreTrendsTopT etLabelDropRule,
      SafeSearchNsfwH ghPrec s onT etLabelRule,
      SafeSearchGoreAndV olenceH ghPrec s onT etLabelRule,
      SafeSearchNsfwReported ur st csT etLabelRule,
      SafeSearchGoreAndV olenceReported ur st csT etLabelRule,
      SafeSearchNsfwCard mageT etLabelRule,
      SafeSearchNsfwH ghRecallT etLabelRule,
      SafeSearchNsfwV deoT etLabelRule,
      SafeSearchNsfwTextT etLabelRule,
      SpamH ghRecallT etLabelDropRule,
      Dupl cateContentT etLabelDropRule,
      SafeSearchGoreAndV olenceT etLabelRule,
      SafeSearchUntrustedUrlT etLabelRule,
      SafeSearchDownrankSpamReplyT etLabelRule,
      SearchBlackl stT etLabelRule,
      SearchBlackl stH ghRecallT etLabelDropRule,
      S teSpamT etLabelDropSearchRule,
      CopypastaSpamAllV e rsSearchT etLabelRule,
    ) ++ bas cBlockMuteRules ++
      Seq(
        SafeSearchAutomat onNonFollo rT etLabelRule,
        SafeSearchDupl cate nt onNonFollo rT etLabelRule,
        SafeSearchBystanderAbus veT etLabelRule,
        SafetyCr s sLevel3DropRule,
        SafetyCr s sLevel4DropRule,
        DoNotAmpl fyDropRule,
        Search p SafeSearchW houtUser nQueryDropRule,
        SearchEd SafeSearchW houtUser nQueryDropRule,
        AbusePol cyEp sod cT etLabel nterst  alRule,
        E rgencyDynam c nterst  alRule,
        UnsafeSearchNsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
        UnsafeSearchGoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
        UnsafeSearchNsfwReported ur st csAllUsersT etLabelRule,
        UnsafeSearchGoreAndV olenceReported ur st csAllUsersT etLabelRule,
        UnsafeSearchNsfwCard mageAllUsersT etLabelRule,
      ) ++
      l m edEngage ntBaseRules ++
      t etAvo dRules

    V s b l yPol cy.baseT etRules ++ Seq(
    SafeSearchAbus veT etLabelRule,
    LowQual yT etLabelDropRule,
    H ghProact veTosScoreT etLabelDropSearchRule,
    H ghSpam T etContentScoreSearchLatestT etLabelDropRule,
    H ghSpam T etContentScoreTrendsLatestT etLabelDropRule,
    SafeSearchNsfwH ghPrec s onT etLabelRule,
    SafeSearchGoreAndV olenceH ghPrec s onT etLabelRule,
    SafeSearchNsfwReported ur st csT etLabelRule,
    SafeSearchGoreAndV olenceReported ur st csT etLabelRule,
    SafeSearchNsfwCard mageT etLabelRule,
    SafeSearchNsfwH ghRecallT etLabelRule,
    SafeSearchNsfwV deoT etLabelRule,
    SafeSearchNsfwTextT etLabelRule,
    SpamH ghRecallT etLabelDropRule,
    Dupl cateContentT etLabelDropRule,
    SafeSearchGoreAndV olenceT etLabelRule,
    SafeSearchUntrustedUrlT etLabelRule,
    SafeSearchDownrankSpamReplyT etLabelRule,
    SearchBlackl stT etLabelRule,
    SearchBlackl stH ghRecallT etLabelDropRule,
    S teSpamT etLabelDropSearchRule,
    CopypastaSpamNonFollo rSearchT etLabelRule,
  ) ++
    bas cBlockMuteRules ++
    Seq(
      SafeSearchAutomat onNonFollo rT etLabelRule,
      SafeSearchDupl cate nt onNonFollo rT etLabelRule,
      SafeSearchBystanderAbus veT etLabelRule,
      SafetyCr s sLevel3DropRule,
      SafetyCr s sLevel4DropRule,
      Search p SafeSearchW houtUser nQueryDropRule,
      SearchEd SafeSearchW houtUser nQueryDropRule,
      AbusePol cyEp sod cT etLabel nterst  alRule,
      E rgencyDynam c nterst  alRule,
      UnsafeSearchNsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
      UnsafeSearchGoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
      UnsafeSearchNsfwReported ur st csAllUsersT etLabelRule,
      UnsafeSearchGoreAndV olenceReported ur st csAllUsersT etLabelRule,
      UnsafeSearchNsfwCard mageAllUsersT etLabelRule,
    ) ++ l m edEngage ntBaseRules ++ t etAvo dRules

  val userBaseRules: Seq[Cond  onW hUserLabelRule] = Seq(
    SafeSearchAbus veUserLabelRule,
    LowQual yRule,
    ReadOnlyRule,
    SearchBlackl stRule,
    Comprom sedRule,
    SpamH ghRecallRule,
    Dupl cateContentRule,
    DoNotAmpl fyNonFollo rRule,
    SearchL kely vsLabelNonFollo rDropUserRule,
    SafeSearchNsfwH ghPrec s onUserLabelRule,
    SafeSearchNsfwAvatar mageUserLabelRule,
    SafeSearchNsfwBanner mageUserLabelRule,
    SafeSearchAbus veH ghRecallUserLabelRule,
    SafeSearchDownrankSpamReplyAuthorLabelRule,
    SafeSearchNotGraduatedNonFollo rsUserLabelRule,
    SafeSearchNsfwTextAuthorLabelRule
  )

  val userRules: Seq[Cond  onW hUserLabelRule] = userBaseRules

  val userRelevanceBaseRules = userBaseRules ++ bas cBlockMuteRules

  val userRelevanceRules = userRelevanceBaseRules

  val userRecencyBaseRules = userBaseRules.f lterNot(
    Seq(DoNotAmpl fyNonFollo rRule, SearchL kely vsLabelNonFollo rDropUserRule).conta ns
  ) ++ bas cBlockMuteRules

  val searchQueryMatc sT etAuthorRules: Seq[Cond  onW hUserLabelRule] =
    userBaseRules

  val bas cBlockMutePol cyRuleParam: Map[Rule, Pol cyLevelRuleParams] =
    SearchBlenderRules.bas cBlockMuteRules
      .map(rule => rule -> ruleParams(RuleParams.EnableSearchBas cBlockMuteRulesParam)).toMap
}

case object SearchBlenderUserRulesPol cy
    extends V s b l yPol cy(
      userRules = SearchBlenderRules.userRules
    )

case object SearchLatestUserRulesPol cy
    extends V s b l yPol cy(
      userRules = SearchLatestPol cy.userRules
    )

case object UserSearchSrpPol cy
    extends V s b l yPol cy(
      userRules = Seq(
        AuthorBlocksV e rDropRule,
        V e rBlocksAuthorV e rOpt nBlock ngOnSearchRule,
        V e rMutesAuthorV e rOpt nBlock ngOnSearchRule,
        DropNsfwAdm nAuthorV e rOpt nF lter ngOnSearchRule,
        SafeSearchAbus veUserLabelRule,
        SafeSearchH ghRecallUserLabelRule,
        SafeSearchNsfwNearPerfectAuthorRule,
        SafeSearchNsfwH ghPrec s onUserLabelRule,
        SafeSearchNsfwAvatar mageUserLabelRule,
        SafeSearchNsfwBanner mageUserLabelRule,
        SafeSearchAbus veH ghRecallUserLabelRule,
        SafeSearchNsfwTextAuthorLabelRule
      )
    )

case object UserSearchTypea adPol cy
    extends V s b l yPol cy(
      userRules = Seq(
        SafeSearchAbus veUserLabelRule,
        SafeSearchH ghRecallUserLabelRule,
        SafeSearchNsfwNearPerfectAuthorRule,
        SafeSearchNsfwH ghPrec s onUserLabelRule,
        SafeSearchNsfwAvatar mageUserLabelRule,
        SafeSearchNsfwBanner mageUserLabelRule,
        SafeSearchAbus veH ghRecallUserLabelRule,
        SafeSearchNsfwTextAuthorLabelRule
      ),
      t etRules = Seq(DropAllRule)
    )

case object SearchM xerSrpM n malPol cy
    extends V s b l yPol cy(
      userRules = Seq(
        AuthorBlocksV e rDropRule,
        V e rBlocksAuthorV e rOpt nBlock ngOnSearchRule,
        V e rMutesAuthorV e rOpt nBlock ngOnSearchRule
      )
    )

case object SearchM xerSrpStr ctPol cy
    extends V s b l yPol cy(
      userRules = Seq(
        AuthorBlocksV e rDropRule,
        V e rBlocksAuthorV e rOpt nBlock ngOnSearchRule,
        V e rMutesAuthorV e rOpt nBlock ngOnSearchRule,
        DropNsfwAdm nAuthorV e rOpt nF lter ngOnSearchRule,
        NsfwNearPerfectAuthorRule,
        NsfwH ghPrec s onRule,
        NsfwH ghRecallRule,
        NsfwSens  veRule,
        NsfwAvatar mageRule,
        NsfwBanner mageRule
      ) ++ SearchBlenderRules.searchQueryMatc sT etAuthorRules
        .d ff(Seq(SafeSearchNotGraduatedNonFollo rsUserLabelRule))
    )

case object SearchPeopleSrpPol cy
    extends V s b l yPol cy(
      userRules = SearchBlenderRules.searchQueryMatc sT etAuthorRules
    )

case object SearchPeopleTypea adPol cy
    extends V s b l yPol cy(
      userRules = SearchBlenderRules.searchQueryMatc sT etAuthorRules
        .d ff(
          Seq(
            SafeSearchNotGraduatedNonFollo rsUserLabelRule
          )),
      t etRules = Seq(DropAllRule)
    )

case object SearchPhotoPol cy
    extends V s b l yPol cy(
      t etRules = SearchBlenderRules.t etRelevanceRules,
      userRules = SearchBlenderRules.userRelevanceRules,
      pol cyRuleParams = SearchBlenderRules.bas cBlockMutePol cyRuleParam
    )

case object SearchTrendTakeoverPromotedT etPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules
    )

case object SearchV deoPol cy
    extends V s b l yPol cy(
      t etRules = SearchBlenderRules.t etRelevanceRules,
      userRules = SearchBlenderRules.userRelevanceRules,
      pol cyRuleParams = SearchBlenderRules.bas cBlockMutePol cyRuleParam
    )

case object SearchLatestPol cy
    extends V s b l yPol cy(
      t etRules = SearchBlenderRules.t etRecencyRules,
      userRules = SearchBlenderRules.userRecencyBaseRules,
      pol cyRuleParams = SearchBlenderRules.bas cBlockMutePol cyRuleParam
    )

case object SearchTopPol cy
    extends V s b l yPol cy(
      t etRules = SearchBlenderRules.t etRelevanceRules,
      userRules = Seq(Spam UserModelH ghPrec s onDropT etRule) ++
        SearchBlenderRules.bas cBlockMuteRules ++
        SearchBlenderRules.searchQueryMatc sT etAuthorRules,
      pol cyRuleParams = SearchBlenderRules.bas cBlockMutePol cyRuleParam
    )

case object SearchTopQ gPol cy
    extends V s b l yPol cy(
      t etRules = BaseQ gPol cy.t etRules ++
        Seq(
          UnsafeSearchGoreAndV olenceH ghPrec s onAllUsersT etLabelDropRule,
          UnsafeSearchGoreAndV olenceReported ur st csAllUsersT etLabelDropRule,
          UnsafeSearchNsfwCard mageAllUsersT etLabelDropRule,
          UnsafeSearchNsfwReported ur st csAllUsersT etLabelDropRule,
          UnsafeSearchNsfwH ghPrec s onAllUsersT etLabelDropRule
        ) ++
        SearchTopPol cy.t etRules.d ff(
          Seq(
            Search p SafeSearchW houtUser nQueryDropRule,
            SearchEd SafeSearchW houtUser nQueryDropRule,
            H ghSpam T etContentScoreTrendsTopT etLabelDropRule,
            UnsafeSearchNsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
            UnsafeSearchGoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
            UnsafeSearchGoreAndV olenceReported ur st csAllUsersT etLabelRule,
            UnsafeSearchNsfwCard mageAllUsersT etLabelRule,
            UnsafeSearchNsfwReported ur st csAllUsersT etLabelRule
          ) ++
            SearchTopPol cy.t etRules. ntersect(BaseQ gPol cy.t etRules)),
      userRules = BaseQ gPol cy.userRules ++ Seq(
        DropNsfwAdm nAuthorV e rOpt nF lter ngOnSearchRule,
        NsfwNearPerfectAuthorRule,
      ) ++ SearchTopPol cy.userRules.d ff(
        SearchTopPol cy.userRules. ntersect(BaseQ gPol cy.userRules)),
      pol cyRuleParams = SearchBlenderRules.bas cBlockMutePol cyRuleParam
    )

case object SafeSearchStr ctPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        DropOuterCommun yT etsRule,
      ) ++ V s b l yPol cy.baseT etRules ++ Seq(
        Abus veT etLabelRule,
        LowQual yT etLabelDropRule,
        H ghProact veTosScoreT etLabelDropSearchRule,
        NsfwH ghPrec s onT etLabelRule,
        GoreAndV olenceH ghPrec s onT etLabelRule,
        NsfwReported ur st csT etLabelRule,
        GoreAndV olenceReported ur st csT etLabelRule,
        NsfwCard mageT etLabelRule,
        NsfwH ghRecallT etLabelRule,
        NsfwV deoT etLabelDropRule,
        NsfwTextT etLabelDropRule,
        SpamH ghRecallT etLabelDropRule,
        Dupl cateContentT etLabelDropRule,
        GoreAndV olenceT etLabelRule,
        UntrustedUrlT etLabelRule,
        DownrankSpamReplyT etLabelRule,
        SearchBlackl stT etLabelRule,
        SearchBlackl stH ghRecallT etLabelDropRule,
        Automat onT etLabelRule,
        Dupl cate nt onT etLabelRule,
        BystanderAbus veT etLabelRule,
        SafetyCr s sLevel3DropRule,
        SafetyCr s sLevel4DropRule,
        DoNotAmpl fyDropRule,
        S teSpamT etLabelDropRule,
        AbusePol cyEp sod cT etLabel nterst  alRule,
        E rgencyDynam c nterst  alRule,
      ) ++ L m edEngage ntBaseRules.t etRules
        ++ SearchBlenderRules.t etAvo dRules,
      userRules = Seq(
        Abus veRule,
        LowQual yRule,
        ReadOnlyRule,
        SearchBlackl stRule,
        SearchNsfwTextRule,
        Comprom sedRule,
        SpamH ghRecallRule,
        Dupl cateContentRule,
        NsfwH ghPrec s onRule,
        NsfwAvatar mageRule,
        NsfwBanner mageRule,
        Abus veH ghRecallRule,
        DoNotAmpl fyNonFollo rRule,
        NotGraduatedNonFollo rRule,
        SearchL kely vsLabelNonFollo rDropUserRule,
        DownrankSpamReplyNonAuthorRule,
        NsfwTextNonAuthorDropRule,
      )
    )

case object St ckersT  l nePol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules,
      userRules = Seq(
        Abus veRule,
        LowQual yRule,
        ReadOnlyRule,
        Comprom sedRule,
        SearchBlackl stRule,
        SearchNsfwTextRule,
        Dupl cateContentRule,
        Engage ntSpam rRule,
        Engage ntSpam rH ghRecallRule,
        NsfwSens  veRule,
        SpamH ghRecallRule,
        Abus veH ghRecallRule
      )
    )

case object StratoExtL m edEngage ntsPol cy
    extends V s b l yPol cy(
      t etRules =
        V s b l yPol cy.baseT etRules ++ L m edEngage ntBaseRules.t etRules
    )

case object  nternalPromotedContentPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules
    )

case object StreamServ cesPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        Abus veT etLabelRule,
        LowQual yT etLabelDropRule,
        NsfwH ghPrec s onT etLabelRule,
        GoreAndV olenceH ghPrec s onT etLabelRule,
        NsfwReported ur st csT etLabelRule,
        GoreAndV olenceReported ur st csT etLabelRule,
        NsfwCard mageT etLabelRule,
        NsfwV deoT etLabelDropRule,
        NsfwTextT etLabelDropRule,
        SpamH ghRecallT etLabelDropRule,
        Dupl cateContentT etLabelDropRule,
        BystanderAbus veT etLabelRule,
        S teSpamT etLabelDropRule
      ),
      userRules = Seq(NsfwTextNonAuthorDropRule)
    )

case object SuperL kePol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        AbusePol cyEp sod cT etLabelDropRule,
        E rgencyDropRule,
        NsfwH ghPrec s onT etLabelRule,
        GoreAndV olenceH ghPrec s onT etLabelRule,
        NsfwReported ur st csT etLabelRule,
        GoreAndV olenceReported ur st csT etLabelRule,
        NsfwCard mageT etLabelRule,
        NsfwV deoT etLabelDropRule,
        NsfwTextT etLabelDropRule
      ),
      userRules = Seq(NsfwTextNonAuthorDropRule)
    )

case object T  l neFocalT etPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        AbusePol cyEp sod cT etLabel nterst  alRule,
        E rgencyDynam c nterst  alRule,
      ) ++ L m edEngage ntBaseRules.t etRules
    )

case object T  l neBookmarkPol cy
    extends V s b l yPol cy(
      t etRules =
        Seq(
          DropCommun yT etsRule,
          DropCommun yT etCommun yNotV s bleRule,
          DropProtectedCommun yT etsRule,
          DropH ddenCommun yT etsRule,
          DropAuthorRemovedCommun yT etsRule,
          SpamT etLabelRule,
          PdnaT etLabelRule,
          BounceOuterT etTombstoneRule,
          BounceQuotedT etTombstoneRule,
          DropExclus veT etContentRule,
          DropTrustedFr endsT etContentRule,
        ) ++
          Seq(
            AbusePol cyEp sod cT etLabel nterst  alRule,
            E rgencyDynam c nterst  alRule,
            NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
            GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
            NsfwReported ur st csAllUsersT etLabelRule,
            GoreAndV olenceReported ur st csAllUsersT etLabelRule,
            V e rBlocksAuthor nnerQuotedT et nterst  alRule,
            V e rMutesAuthor nnerQuotedT et nterst  alRule,
            NsfwCard mageAllUsersT etLabelRule,
          ) ++ L m edEngage ntBaseRules.t etRules,
      deletedT etRules = Seq(
        TombstoneBounceDeletedT etRule,
        TombstoneDeletedQuotedT etRule
      ),
      userUnava lableStateRules = Seq(
        SuspendedUserUnava lableT etTombstoneRule,
        Deact vatedUserUnava lableT etTombstoneRule,
        OffBoardedUserUnava lableT etTombstoneRule,
        ErasedUserUnava lableT etTombstoneRule,
        ProtectedUserUnava lableT etTombstoneRule,
        AuthorBlocksV e rUserUnava lable nnerQuotedT etTombstoneRule,
        UserUnava lableT etTombstoneRule,
        V e rBlocksAuthorUserUnava lable nnerQuotedT et nterst  alRule,
        V e rMutesAuthorUserUnava lable nnerQuotedT et nterst  alRule
      ),
    )

case object T  l neL stsPol cy
    extends V s b l yPol cy(
      t etRules =
        Seq(
          DropOuterCommun yT etsRule,
          DropStaleT etsRule,
        ) ++
          V s b l yPol cy.baseT etRules ++
          Seq(
            AbusePol cyEp sod cT etLabel nterst  alRule,
            E rgencyDynam c nterst  alRule,
            NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
            GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
            NsfwReported ur st csAllUsersT etLabelRule,
            GoreAndV olenceReported ur st csAllUsersT etLabelRule,
            NsfwCard mageAllUsersT etLabelRule,
            NsfwH ghPrec s onT etLabelAvo dRule,
            NsfwH ghRecallT etLabelAvo dRule,
            GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule,
            NsfwReported ur st csAvo dAllUsersT etLabelRule,
            GoreAndV olenceReported ur st csAvo dAllUsersT etLabelRule,
            NsfwCard mageAvo dAllUsersT etLabelRule,
            DoNotAmpl fyT etLabelAvo dRule,
            NsfaH ghPrec s onT etLabelAvo dRule,
          ) ++ L m edEngage ntBaseRules.t etRules
    )

case object T  l neFavor esPol cy
    extends V s b l yPol cy(
      t etRules =
        Seq(
          DropOuterCommun yT etsRule,
          DropStaleT etsRule,
        )
          ++ T  l neProf leRules.baseT etRules
          ++ Seq(
            Dynam cProductAdDropT etLabelRule,
            NsfwH ghPrec s onTombstone nnerQuotedT etLabelRule,
            Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwH ghPrec s onT etLabelDropSett ngLevelTombstoneRule,
            Sens  ve d aT etDropSett ngLevelTombstoneRules.V olent d aGoreAndV olenceH ghPrec s onDropSett ngLeveTombstoneRule,
            Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwReported ur st csT etLabelDropSett ngLevelTombstoneRule,
            Sens  ve d aT etDropSett ngLevelTombstoneRules.V olent d aGoreAndV olenceReported ur st csDropSett ngLevelTombstoneRule,
            Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwCard mageT etLabelDropSett ngLevelTombstoneRule,
            Sens  ve d aT etDropSett ngLevelTombstoneRules.Ot rSens  ve d aNsfwUserT etFlagDropSett ngLevelTombstoneRule,
            Sens  ve d aT etDropSett ngLevelTombstoneRules.Ot rSens  ve d aNsfwAdm nT etFlagDropSett ngLevelTombstoneRule,
            AbusePol cyEp sod cT etLabel nterst  alRule,
            E rgencyDynam c nterst  alRule,
            ReportedT et nterst  alRule,
            V e rMutesAuthor nterst  alRule,
            V e rBlocksAuthor nterst  alRule,
            NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
            GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
            NsfwReported ur st csAllUsersT etLabelRule,
            GoreAndV olenceReported ur st csAllUsersT etLabelRule,
            NsfwCard mageAllUsersT etLabelRule,
            Sens  ve d aT et nterst  alRules.Adult d aNsfwH ghPrec s onT etLabel nterst  alRule,
            Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceH ghPrec s on nterst  alRule,
            Sens  ve d aT et nterst  alRules.Adult d aNsfwReported ur st csT etLabel nterst  alRule,
            Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceReported ur st cs nterst  alRule,
            Sens  ve d aT et nterst  alRules.Adult d aNsfwCard mageT etLabel nterst  alRule,
            Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwUserT etFlag nterst  alRule,
            Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwAdm nT etFlag nterst  alRule,
            NsfwH ghPrec s onT etLabelAvo dRule,
            NsfwH ghRecallT etLabelAvo dRule,
            GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule,
            NsfwReported ur st csAvo dAllUsersT etLabelRule,
            GoreAndV olenceReported ur st csAvo dAllUsersT etLabelRule,
            NsfwCard mageAvo dAllUsersT etLabelRule,
            DoNotAmpl fyT etLabelAvo dRule,
            NsfaH ghPrec s onT etLabelAvo dRule,
          ) ++ L m edEngage ntBaseRules.t etRules,
      deletedT etRules = Seq(
        TombstoneDeletedQuotedT etRule,
        TombstoneBounceDeletedQuotedT etRule
      ),
      userUnava lableStateRules = Seq(
        SuspendedUserUnava lable nnerQuotedT etTombstoneRule,
        Deact vatedUserUnava lable nnerQuotedT etTombstoneRule,
        OffBoardedUserUnava lable nnerQuotedT etTombstoneRule,
        ErasedUserUnava lable nnerQuotedT etTombstoneRule,
        ProtectedUserUnava lable nnerQuotedT etTombstoneRule,
        AuthorBlocksV e rUserUnava lable nnerQuotedT etTombstoneRule,
        V e rBlocksAuthorUserUnava lable nnerQuotedT et nterst  alRule,
        V e rMutesAuthorUserUnava lable nnerQuotedT et nterst  alRule
      ),
      pol cyRuleParams = Sens  ve d aSett ngsProf leT  l neBaseRules.pol cyRuleParams
    )

case object Prof leM xerFavor esPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        DropStaleT etsRule,
        DropExclus veT etContentRule,
        DropOuterCommun yT etsRule,
      ),
      deletedT etRules = Seq(
        TombstoneDeletedQuotedT etRule,
        TombstoneBounceDeletedQuotedT etRule
      )
    )

case object T  l ne d aPol cy
    extends V s b l yPol cy(
        T  l neProf leRules.baseT etRules
        ++ Seq(
          NsfwH ghPrec s onTombstone nnerQuotedT etLabelRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwH ghPrec s onT etLabelDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.V olent d aGoreAndV olenceH ghPrec s onDropSett ngLeveTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwReported ur st csT etLabelDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.V olent d aGoreAndV olenceReported ur st csDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwCard mageT etLabelDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Ot rSens  ve d aNsfwUserT etFlagDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Ot rSens  ve d aNsfwAdm nT etFlagDropSett ngLevelTombstoneRule,
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          ReportedT et nterst  alRule,
          V e rMutesAuthor nnerQuotedT et nterst  alRule,
          V e rBlocksAuthor nnerQuotedT et nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwH ghPrec s onT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceH ghPrec s on nterst  alRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwReported ur st csT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceReported ur st cs nterst  alRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwCard mageT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwUserT etFlag nterst  alRule,
          Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwAdm nT etFlag nterst  alRule,
          NsfwH ghPrec s onT etLabelAvo dRule,
          NsfwH ghRecallT etLabelAvo dRule,
          GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule,
          NsfwReported ur st csAvo dAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAvo dAllUsersT etLabelRule,
          NsfwCard mageAvo dAllUsersT etLabelRule,
          DoNotAmpl fyT etLabelAvo dRule,
          NsfaH ghPrec s onT etLabelAvo dRule,
        ) ++ L m edEngage ntBaseRules.t etRules,
      deletedT etRules = Seq(
        TombstoneBounceDeletedOuterT etRule,
        TombstoneDeletedQuotedT etRule,
        TombstoneBounceDeletedQuotedT etRule
      ),
      userUnava lableStateRules = Seq(
        SuspendedUserUnava lable nnerQuotedT etTombstoneRule,
        Deact vatedUserUnava lable nnerQuotedT etTombstoneRule,
        OffBoardedUserUnava lable nnerQuotedT etTombstoneRule,
        ErasedUserUnava lable nnerQuotedT etTombstoneRule,
        ProtectedUserUnava lable nnerQuotedT etTombstoneRule,
        AuthorBlocksV e rUserUnava lable nnerQuotedT etTombstoneRule,
        V e rBlocksAuthorUserUnava lable nnerQuotedT et nterst  alRule,
        V e rMutesAuthorUserUnava lable nnerQuotedT et nterst  alRule
      ),
      pol cyRuleParams = Sens  ve d aSett ngsProf leT  l neBaseRules.pol cyRuleParams
    )

case object Prof leM xer d aPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        DropStaleT etsRule,
        DropExclus veT etContentRule
      ),
      deletedT etRules = Seq(
        TombstoneBounceDeletedOuterT etRule,
        TombstoneDeletedQuotedT etRule,
        TombstoneBounceDeletedQuotedT etRule
      )
    )

object T  l neProf leRules {

  val baseT etRules: Seq[Rule] = Seq(
    TombstoneCommun yT etsRule,
    TombstoneCommun yT etCommun yNotV s bleRule,
    TombstoneProtectedCommun yT etsRule,
    TombstoneH ddenCommun yT etsRule,
    TombstoneAuthorRemovedCommun yT etsRule,
    SpamQuotedT etLabelTombstoneRule,
    SpamT etLabelRule,
    PdnaQuotedT etLabelTombstoneRule,
    PdnaT etLabelRule,
    BounceT etLabelTombstoneRule,
    TombstoneExclus veQuotedT etContentRule,
    DropExclus veT etContentRule,
    DropTrustedFr endsT etContentRule
  )

  val t etRules: Seq[Rule] =
    Seq(
      Dynam cProductAdDropT etLabelRule,
      AbusePol cyEp sod cT etLabel nterst  alRule,
      E rgencyDynam c nterst  alRule,
      ReportedT et nterst  alRule,
      NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
      GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
      NsfwReported ur st csAllUsersT etLabelRule,
      GoreAndV olenceReported ur st csAllUsersT etLabelRule,
      NsfwCard mageAllUsersT etLabelRule,
      NsfwH ghPrec s onT etLabelAvo dRule,
      NsfwH ghRecallT etLabelAvo dRule,
      GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule,
      NsfwReported ur st csAvo dAllUsersT etLabelRule,
      GoreAndV olenceReported ur st csAvo dAllUsersT etLabelRule,
      NsfwCard mageAvo dAllUsersT etLabelRule,
      NsfwTextT etLabelAvo dRule,
      DoNotAmpl fyT etLabelAvo dRule,
      NsfaH ghPrec s onT etLabelAvo dRule,
    ) ++ L m edEngage ntBaseRules.t etRules

  val t etTombstoneRules: Seq[Rule] =
    Seq(
      Dynam cProductAdDropT etLabelRule,
      NsfwH ghPrec s on nnerQuotedT etLabelRule,
      Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwH ghPrec s onT etLabelDropSett ngLevelTombstoneRule,
      Sens  ve d aT etDropSett ngLevelTombstoneRules.V olent d aGoreAndV olenceH ghPrec s onDropSett ngLeveTombstoneRule,
      Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwReported ur st csT etLabelDropSett ngLevelTombstoneRule,
      Sens  ve d aT etDropSett ngLevelTombstoneRules.V olent d aGoreAndV olenceReported ur st csDropSett ngLevelTombstoneRule,
      Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwCard mageT etLabelDropSett ngLevelTombstoneRule,
      Sens  ve d aT etDropSett ngLevelTombstoneRules.Ot rSens  ve d aNsfwUserT etFlagDropSett ngLevelTombstoneRule,
      Sens  ve d aT etDropSett ngLevelTombstoneRules.Ot rSens  ve d aNsfwAdm nT etFlagDropSett ngLevelTombstoneRule,
      AbusePol cyEp sod cT etLabel nterst  alRule,
      E rgencyDynam c nterst  alRule,
      ReportedT et nterst  alRule,
      V e rMutesAuthor nnerQuotedT et nterst  alRule,
      V e rBlocksAuthor nnerQuotedT et nterst  alRule,
      NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
      GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
      NsfwReported ur st csAllUsersT etLabelRule,
      GoreAndV olenceReported ur st csAllUsersT etLabelRule,
      NsfwCard mageAllUsersT etLabelRule,
      Sens  ve d aT et nterst  alRules.Adult d aNsfwH ghPrec s onT etLabel nterst  alRule,
      Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceH ghPrec s on nterst  alRule,
      Sens  ve d aT et nterst  alRules.Adult d aNsfwReported ur st csT etLabel nterst  alRule,
      Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceReported ur st cs nterst  alRule,
      Sens  ve d aT et nterst  alRules.Adult d aNsfwCard mageT etLabel nterst  alRule,
      Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwUserT etFlag nterst  alRule,
      Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwAdm nT etFlag nterst  alRule,
      NsfwH ghPrec s onT etLabelAvo dRule,
      NsfwH ghRecallT etLabelAvo dRule,
      GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule,
      NsfwReported ur st csAvo dAllUsersT etLabelRule,
      GoreAndV olenceReported ur st csAvo dAllUsersT etLabelRule,
      NsfwCard mageAvo dAllUsersT etLabelRule,
      DoNotAmpl fyT etLabelAvo dRule,
      NsfaH ghPrec s onT etLabelAvo dRule,
    ) ++ L m edEngage ntBaseRules.t etRules
}

case object T  l neProf lePol cy
    extends V s b l yPol cy(
      t etRules =
        Seq(
          DropOuterCommun yT etsRule,
          DropStaleT etsRule,
        )
          ++ T  l neProf leRules.baseT etRules
          ++ T  l neProf leRules.t etTombstoneRules,
      deletedT etRules = Seq(
        TombstoneBounceDeletedOuterT etRule,
        TombstoneDeletedQuotedT etRule,
        TombstoneBounceDeletedQuotedT etRule,
      ),
      userUnava lableStateRules = Seq(
        SuspendedUserUnava lable nnerQuotedT etTombstoneRule,
        Deact vatedUserUnava lable nnerQuotedT etTombstoneRule,
        OffBoardedUserUnava lable nnerQuotedT etTombstoneRule,
        ErasedUserUnava lable nnerQuotedT etTombstoneRule,
        ProtectedUserUnava lable nnerQuotedT etTombstoneRule,
        AuthorBlocksV e rUserUnava lable nnerQuotedT etTombstoneRule,
        V e rBlocksAuthorUserUnava lable nnerQuotedT et nterst  alRule,
        V e rMutesAuthorUserUnava lable nnerQuotedT et nterst  alRule
      ),
      pol cyRuleParams = Sens  ve d aSett ngsProf leT  l neBaseRules.pol cyRuleParams
    )

case object T  l neProf leAllPol cy
    extends V s b l yPol cy(
        T  l neProf leRules.baseT etRules
        ++ T  l neProf leRules.t etTombstoneRules,
      deletedT etRules = Seq(
        TombstoneBounceDeletedOuterT etRule,
        TombstoneDeletedQuotedT etRule,
        TombstoneBounceDeletedQuotedT etRule,
      ),
      userUnava lableStateRules = Seq(
        SuspendedUserUnava lable nnerQuotedT etTombstoneRule,
        Deact vatedUserUnava lable nnerQuotedT etTombstoneRule,
        OffBoardedUserUnava lable nnerQuotedT etTombstoneRule,
        ErasedUserUnava lable nnerQuotedT etTombstoneRule,
        ProtectedUserUnava lable nnerQuotedT etTombstoneRule,
        AuthorBlocksV e rUserUnava lable nnerQuotedT etTombstoneRule,
        V e rBlocksAuthorUserUnava lable nnerQuotedT et nterst  alRule,
        V e rMutesAuthorUserUnava lable nnerQuotedT et nterst  alRule
      ),
      pol cyRuleParams = Sens  ve d aSett ngsProf leT  l neBaseRules.pol cyRuleParams
    )

case object T  l neProf leSuperFollowsPol cy
    extends V s b l yPol cy(
      t etRules =
        Seq(
          DropOuterCommun yT etsRule
        ) ++
          V s b l yPol cy.baseT etRules ++
          T  l neProf leRules.t etRules
    )

case object T  l neReact veBlend ngPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          V e rHasMatch ngMutedKeywordForHo T  l neRule,
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
        ) ++ L m edEngage ntBaseRules.t etRules
    )

case object T  l neHo Pol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseQuotedT etTombstoneRules ++
        V s b l yPol cy.baseT etRules ++
        Seq(
          NullcastedT etRule,
          DropOuterCommun yT etsRule,
          Dynam cProductAdDropT etLabelRule,
          MutedRet etsRule,
          DropAllAuthorRemovedCommun yT etsRule,
          DropAllH ddenCommun yT etsRule,
          AbusePol cyEp sod cT etLabelDropRule,
          E rgencyDropRule,
          SafetyCr s sLevel4DropRule,
          V e rHasMatch ngMutedKeywordForHo T  l neRule,
          Sens  ve d aT etDropRules.Adult d aNsfwH ghPrec s onT etLabelDropRule,
          Sens  ve d aT etDropRules.V olent d aGoreAndV olenceH ghPrec s onDropRule,
          Sens  ve d aT etDropRules.Adult d aNsfwReported ur st csT etLabelDropRule,
          Sens  ve d aT etDropRules.V olent d aGoreAndV olenceReported ur st csDropRule,
          Sens  ve d aT etDropRules.Adult d aNsfwCard mageT etLabelDropRule,
          Sens  ve d aT etDropRules.Ot rSens  ve d aNsfwUserT etFlagDropRule,
          Sens  ve d aT etDropRules.Ot rSens  ve d aNsfwAdm nT etFlagDropRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwH ghPrec s onT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceH ghPrec s on nterst  alRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwReported ur st csT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceReported ur st cs nterst  alRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwCard mageT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwUserT etFlag nterst  alRule,
          Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwAdm nT etFlag nterst  alRule,
          NsfwH ghPrec s onT etLabelAvo dRule,
          NsfwH ghRecallT etLabelAvo dRule,
          GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule,
          NsfwReported ur st csAvo dAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAvo dAllUsersT etLabelRule,
          NsfwCard mageAvo dAllUsersT etLabelRule,
          DoNotAmpl fyT etLabelAvo dRule,
          NsfaH ghPrec s onT etLabelAvo dRule,
        )
        ++
          L m edEngage ntBaseRules.t etRules,
      userRules = Seq(
        V e rMutesAuthorRule,
        V e rBlocksAuthorRule,
        Dec derableAuthorBlocksV e rDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule,
        Deact vatedAuthorRule,
        ErasedAuthorRule,
        OffboardedAuthorRule,
        DropTakendownUserRule
      ),
      pol cyRuleParams = Sens  ve d aSett ngsT  l neHo BaseRules.pol cyRuleParams
    )

case object BaseT  l neHo Pol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseQuotedT etTombstoneRules ++
        V s b l yPol cy.baseT etRules ++
        Seq(
          NullcastedT etRule,
          DropOuterCommun yT etsRule,
          Dynam cProductAdDropT etLabelRule,
          MutedRet etsRule,
          DropAllAuthorRemovedCommun yT etsRule,
          DropAllH ddenCommun yT etsRule,
          AbusePol cyEp sod cT etLabelDropRule,
          E rgencyDropRule,
          SafetyCr s sLevel4DropRule,
          V e rHasMatch ngMutedKeywordForHo T  l neRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
          NsfwH ghPrec s onT etLabelAvo dRule,
          NsfwH ghRecallT etLabelAvo dRule,
          GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule,
          NsfwReported ur st csAvo dAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAvo dAllUsersT etLabelRule,
          NsfwCard mageAvo dAllUsersT etLabelRule,
          DoNotAmpl fyT etLabelAvo dRule,
          NsfaH ghPrec s onT etLabelAvo dRule,
        )
        ++
          L m edEngage ntBaseRules.t etRules,
      userRules = Seq(
        V e rMutesAuthorRule,
        V e rBlocksAuthorRule,
        Dec derableAuthorBlocksV e rDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule,
        Deact vatedAuthorRule,
        ErasedAuthorRule,
        OffboardedAuthorRule,
        DropTakendownUserRule
      )
    )

case object T  l neHo Hydrat onPol cy
    extends V s b l yPol cy(
      t etRules =
          V s b l yPol cy.baseQuotedT etTombstoneRules ++
          V s b l yPol cy.baseT etRules ++
          Seq(
            Sens  ve d aT etDropRules.Adult d aNsfwH ghPrec s onT etLabelDropRule,
            Sens  ve d aT etDropRules.V olent d aGoreAndV olenceH ghPrec s onDropRule,
            Sens  ve d aT etDropRules.Adult d aNsfwReported ur st csT etLabelDropRule,
            Sens  ve d aT etDropRules.V olent d aGoreAndV olenceReported ur st csDropRule,
            Sens  ve d aT etDropRules.Adult d aNsfwCard mageT etLabelDropRule,
            Sens  ve d aT etDropRules.Ot rSens  ve d aNsfwUserT etFlagDropRule,
            Sens  ve d aT etDropRules.Ot rSens  ve d aNsfwAdm nT etFlagDropRule,
            AbusePol cyEp sod cT etLabel nterst  alRule,
            E rgencyDynam c nterst  alRule,
            NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
            GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
            NsfwReported ur st csAllUsersT etLabelRule,
            GoreAndV olenceReported ur st csAllUsersT etLabelRule,
            NsfwCard mageAllUsersT etLabelRule,
            Sens  ve d aT et nterst  alRules.Adult d aNsfwH ghPrec s onT etLabel nterst  alRule,
            Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceH ghPrec s on nterst  alRule,
            Sens  ve d aT et nterst  alRules.Adult d aNsfwReported ur st csT etLabel nterst  alRule,
            Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceReported ur st cs nterst  alRule,
            Sens  ve d aT et nterst  alRules.Adult d aNsfwCard mageT etLabel nterst  alRule,
            Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwUserT etFlag nterst  alRule,
            Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwAdm nT etFlag nterst  alRule,
            NsfaH ghPrec s onT etLabelAvo dRule,
            NsfwH ghPrec s onT etLabelAvo dRule,
            NsfwH ghRecallT etLabelAvo dRule,
          ) ++ L m edEngage ntBaseRules.t etRules,
      pol cyRuleParams = Sens  ve d aSett ngsT  l neHo BaseRules.pol cyRuleParams
    )

case object T  l neHo LatestPol cy
    extends V s b l yPol cy(
      t etRules =
          V s b l yPol cy.baseQuotedT etTombstoneRules ++
          V s b l yPol cy.baseT etRules ++
          Seq(
            NullcastedT etRule,
            DropOuterCommun yT etsRule,
            Dynam cProductAdDropT etLabelRule,
            MutedRet etsRule,
            V e rHasMatch ngMutedKeywordForHo T  l neRule,
            Sens  ve d aT etDropRules.Adult d aNsfwH ghPrec s onT etLabelDropRule,
            Sens  ve d aT etDropRules.V olent d aGoreAndV olenceH ghPrec s onDropRule,
            Sens  ve d aT etDropRules.Adult d aNsfwReported ur st csT etLabelDropRule,
            Sens  ve d aT etDropRules.V olent d aGoreAndV olenceReported ur st csDropRule,
            Sens  ve d aT etDropRules.Adult d aNsfwCard mageT etLabelDropRule,
            Sens  ve d aT etDropRules.Ot rSens  ve d aNsfwUserT etFlagDropRule,
            Sens  ve d aT etDropRules.Ot rSens  ve d aNsfwAdm nT etFlagDropRule,
            AbusePol cyEp sod cT etLabel nterst  alRule,
            E rgencyDynam c nterst  alRule,
            NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
            GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
            NsfwReported ur st csAllUsersT etLabelRule,
            GoreAndV olenceReported ur st csAllUsersT etLabelRule,
            NsfwCard mageAllUsersT etLabelRule,
            Sens  ve d aT et nterst  alRules.Adult d aNsfwH ghPrec s onT etLabel nterst  alRule,
            Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceH ghPrec s on nterst  alRule,
            Sens  ve d aT et nterst  alRules.Adult d aNsfwReported ur st csT etLabel nterst  alRule,
            Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceReported ur st cs nterst  alRule,
            Sens  ve d aT et nterst  alRules.Adult d aNsfwCard mageT etLabel nterst  alRule,
            Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwUserT etFlag nterst  alRule,
            Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwAdm nT etFlag nterst  alRule,
            NsfwH ghPrec s onT etLabelAvo dRule,
            NsfwH ghRecallT etLabelAvo dRule,
            GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule,
            NsfwReported ur st csAvo dAllUsersT etLabelRule,
            GoreAndV olenceReported ur st csAvo dAllUsersT etLabelRule,
            NsfwCard mageAvo dAllUsersT etLabelRule,
            DoNotAmpl fyT etLabelAvo dRule,
            NsfaH ghPrec s onT etLabelAvo dRule,
          )
          ++
            L m edEngage ntBaseRules.t etRules,
      userRules = Seq(
        V e rMutesAuthorRule,
        V e rBlocksAuthorRule,
        Dec derableAuthorBlocksV e rDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule,
        Deact vatedAuthorRule,
        ErasedAuthorRule,
        OffboardedAuthorRule,
        DropTakendownUserRule
      ),
      pol cyRuleParams = Sens  ve d aSett ngsT  l neHo BaseRules.pol cyRuleParams
    )

case object T  l neModeratedT etsHydrat onPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
        ) ++ L m edEngage ntBaseRules.t etRules
    )

case object S gnalsReact onsPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        AuthorBlocksV e rDropRule
      ) ++ L m edEngage ntBaseRules.t etRules
    )

case object S gnalsT etReact ngUsersPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules :+
        NsfwV deoT etLabelDropRule :+
        NsfwTextAllUsersT etLabelDropRule,
      userRules = Seq(
        Comprom sedNonFollo rW hUqfRule,
        Engage ntSpam rNonFollo rW hUqfRule,
        LowQual yNonFollo rW hUqfRule,
        ReadOnlyNonFollo rW hUqfRule,
        SpamH ghRecallNonFollo rW hUqfRule,
        AuthorBlocksV e rDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule,
        NsfwTextNonAuthorDropRule
      )
    )

case object Soc alProofPol cy
    extends V s b l yPol cy(
      t etRules = F lterDefaultPol cy.t etRules,
      userRules = Seq(
        ProtectedAuthorDropRule,
        SuspendedAuthorRule,
        AuthorBlocksV e rDropRule,
        V e rBlocksAuthorRule
      )
    )

case object T  l neL kedByPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules :+
        NsfwV deoT etLabelDropRule :+
        NsfwTextAllUsersT etLabelDropRule,
      userRules = T  l neL kedByRules.UserRules :+ NsfwTextNonAuthorDropRule
    )

case object T  l neRet etedByPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules :+
        NsfwV deoT etLabelDropRule :+
        NsfwTextAllUsersT etLabelDropRule,
      userRules = Seq(
        Comprom sedNonFollo rW hUqfRule,
        Engage ntSpam rNonFollo rW hUqfRule,
        LowQual yNonFollo rW hUqfRule,
        ReadOnlyNonFollo rW hUqfRule,
        SpamH ghRecallNonFollo rW hUqfRule,
        NsfwTextNonAuthorDropRule
      )
    )

case object T  l neSuperL kedByPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules :+
        NsfwV deoT etLabelDropRule :+
        NsfwTextAllUsersT etLabelDropRule,
      userRules = Seq(
        Comprom sedNonFollo rW hUqfRule,
        Engage ntSpam rNonFollo rW hUqfRule,
        LowQual yNonFollo rW hUqfRule,
        ReadOnlyNonFollo rW hUqfRule,
        SpamH ghRecallNonFollo rW hUqfRule,
        NsfwTextNonAuthorDropRule
      )
    )

case object T  l neContentControlsPol cy
    extends V s b l yPol cy(
      t etRules = Top csLand ngPageTop cRecom ndat onsPol cy.t etRules,
      userRules = Top csLand ngPageTop cRecom ndat onsPol cy.userRules
    )

case object T  l neConversat onsPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          Abus veNonFollo rT etLabelRule,
          LowQual yT etLabelDropRule,
          SpamH ghRecallT etLabelDropRule,
          Dupl cateContentT etLabelDropRule,
          BystanderAbus veNonFollo rT etLabelRule,
          UntrustedUrlAllV e rsT etLabelRule,
          DownrankSpamReplyAllV e rsT etLabelRule,
          S teSpamT etLabelDropRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwH ghPrec s onT etLabelDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.V olent d aGoreAndV olenceH ghPrec s onDropSett ngLeveTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwReported ur st csT etLabelDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.V olent d aGoreAndV olenceReported ur st csDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwCard mageT etLabelDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Ot rSens  ve d aNsfwUserT etFlagDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Ot rSens  ve d aNsfwAdm nT etFlagDropSett ngLevelTombstoneRule,
          MutedKeywordForT etRepl es nterst  alRule,
          ReportedT et nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwH ghPrec s onT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceH ghPrec s on nterst  alRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwReported ur st csT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceReported ur st cs nterst  alRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwCard mageT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwUserT etFlag nterst  alRule,
          Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwAdm nT etFlag nterst  alRule,
          Abus veH ghRecallNonFollo rT etLabelRule,
        ) ++ L m edEngage ntBaseRules.t etRules,
      userRules = Seq(
        Abus veRule,
        LowQual yRule,
        ReadOnlyRule,
        LowQual yH ghRecallRule,
        Comprom sedRule,
        SpamH ghRecallRule,
        Abus veH ghRecallRule,
        DownrankSpamReplyAllV e rsRule,
      ),
      pol cyRuleParams = Sens  ve d aSett ngsConversat onBaseRules.pol cyRuleParams
    )

case object T  l neFollow ngAct v yPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          Abus veT etLabelRule,
          BystanderAbus veT etLabelRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
        ) ++ L m edEngage ntBaseRules.t etRules
    )

case object T  l ne nject onPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        NsfwH ghPrec s onT etLabelRule,
        GoreAndV olenceH ghPrec s onT etLabelRule,
        NsfwReported ur st csT etLabelRule,
        GoreAndV olenceReported ur st csT etLabelRule,
        NsfwCard mageT etLabelRule,
        NsfwH ghRecallT etLabelRule,
        NsfwV deoT etLabelDropRule,
        NsfwTextT etLabelDropRule,
        SafetyCr s sLevel2DropRule,
        SafetyCr s sLevel3DropRule,
        SafetyCr s sLevel4DropRule,
        DoNotAmpl fyDropRule,
        H ghProact veTosScoreT etLabelDropRule
      ),
      userRules = Seq(
        DoNotAmpl fyNonFollo rRule,
        NotGraduatedNonFollo rRule,
        L kely vsLabelNonFollo rDropUserRule,
        NsfwTextNonAuthorDropRule
      )
    )

case object T  l ne nt onsPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          LowQual yT etLabelDropRule,
          SpamH ghRecallT etLabelDropRule,
          Dupl cateContentT etLabelDropRule,
          Dupl cate nt onUqfT etLabelRule,
          LowQual y nt onT etLabelRule,
          S teSpamT etLabelDropRule,
          Tox c yReplyF lterDropNot f cat onRule,
          Abus veUqfNonFollo rT etLabelRule,
          UntrustedUrlUqfNonFollo rT etLabelRule,
          DownrankSpamReplyUqfNonFollo rT etLabelRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
        ) ++ L m edEngage ntBaseRules.t etRules,
      userRules = Seq(
        Abus veRule,
        LowQual yRule,
        ReadOnlyRule,
        Comprom sedRule,
        SpamH ghRecallRule,
        Dupl cateContentRule,
        Abus veH ghRecallRule,
        Engage ntSpam rNonFollo rW hUqfRule,
        Engage ntSpam rH ghRecallNonFollo rW hUqfRule,
        DownrankSpamReplyNonFollo rW hUqfRule
      )
    )

case object T etEngagersPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules,
      userRules = Seq(
        Comprom sedNonFollo rW hUqfRule,
        Engage ntSpam rNonFollo rW hUqfRule,
        LowQual yNonFollo rW hUqfRule,
        ReadOnlyNonFollo rW hUqfRule,
        SpamH ghRecallNonFollo rW hUqfRule
      )
    )

case object T etWr esAp Pol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        AbusePol cyEp sod cT etLabel nterst  alRule,
        E rgencyDynam c nterst  alRule,
      ) ++ L m edEngage ntBaseRules.t etRules
    )

case object QuotedT etRulesPol cy
    extends V s b l yPol cy(
      quotedT etRules = Seq(
        Deact vatedAuthorRule,
        ErasedAuthorRule,
        OffboardedAuthorRule,
        SuspendedAuthorRule,
        AuthorBlocksOuterAuthorRule,
        V e rBlocksAuthorRule,
        AuthorBlocksV e rDropRule,
        V e rMutesAndDoesNotFollowAuthorRule,
        ProtectedQuoteT etAuthorRule
      )
    )

case object T etDeta lPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          AuthorBlocksV e rDropRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwH ghPrec s onT etLabelDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.V olent d aGoreAndV olenceH ghPrec s onDropSett ngLeveTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwReported ur st csT etLabelDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.V olent d aGoreAndV olenceReported ur st csDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwCard mageT etLabelDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Ot rSens  ve d aNsfwUserT etFlagDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Ot rSens  ve d aNsfwAdm nT etFlagDropSett ngLevelTombstoneRule,
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwH ghPrec s onT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceH ghPrec s on nterst  alRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwReported ur st csT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceReported ur st cs nterst  alRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwCard mageT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwUserT etFlag nterst  alRule,
          Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwAdm nT etFlag nterst  alRule,
          NsfwH ghPrec s onT etLabelAvo dRule,
          NsfwH ghRecallT etLabelAvo dRule,
          GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule,
          NsfwReported ur st csAvo dAdPlace ntAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAvo dAdPlace ntAllUsersT etLabelRule,
          NsfwCard mageAvo dAdPlace ntAllUsersT etLabelRule,
          DoNotAmpl fyT etLabelAvo dRule,
          NsfaH ghPrec s onT etLabelAvo dRule,
          MutedKeywordForQuotedT etT etDeta l nterst  alRule,
        )
        ++ L m edEngage ntBaseRules.t etRules,
      pol cyRuleParams = Sens  ve d aSett ngsT etDeta lBaseRules.pol cyRuleParams
    )

case object BaseT etDeta lPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          AuthorBlocksV e rDropRule,
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
          NsfwH ghPrec s onT etLabelAvo dRule,
          NsfwH ghRecallT etLabelAvo dRule,
          GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule,
          NsfwReported ur st csAvo dAdPlace ntAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAvo dAdPlace ntAllUsersT etLabelRule,
          NsfwCard mageAvo dAdPlace ntAllUsersT etLabelRule,
          DoNotAmpl fyT etLabelAvo dRule,
          NsfaH ghPrec s onT etLabelAvo dRule,
          MutedKeywordForQuotedT etT etDeta l nterst  alRule,
        )
        ++ L m edEngage ntBaseRules.t etRules
    )

case object T etDeta lW h nject onsHydrat onPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
          MutedKeywordForQuotedT etT etDeta l nterst  alRule,
          ReportedT et nterst  alRule,
        ) ++ L m edEngage ntBaseRules.t etRules,
      userRules = UserT  l neRules.UserRules
    )

case object T etDeta lNonTooPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        DropAllExclus veT etsRule,
        DropAllTrustedFr endsT etsRule,
      ) ++ BaseT etDeta lPol cy.t etRules
    )

case object RecosWr ePathPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        Abus veT etLabelRule,
        LowQual yT etLabelDropRule,
        NsfwH ghPrec s onT etLabelRule,
        GoreAndV olenceH ghPrec s onT etLabelRule,
        NsfwReported ur st csT etLabelRule,
        GoreAndV olenceReported ur st csT etLabelRule,
        NsfwCard mageT etLabelRule,
        NsfwV deoT etLabelDropRule,
        NsfwTextT etLabelDropRule,
        SpamH ghRecallT etLabelDropRule,
        Dupl cateContentT etLabelDropRule,
        BystanderAbus veT etLabelRule,
        S teSpamT etLabelDropRule
      ),
      userRules = Seq(NsfwTextNonAuthorDropRule)
    )

case object BrandSafetyPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        NsfwV deoT etLabelDropRule,
        NsfwTextT etLabelDropRule,
        NsfaH ghRecallT etLabel nterst  alRule
      ),
      userRules = Seq(NsfwTextNonAuthorDropRule)
    )

case object V deoAdsPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules
    )

case object AppealsPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          NsfwCard mageAllUsersT etLabelRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
        )
    )

case object T  l neConversat onsDownrank ngPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        H ghTox c yScoreDownrankAbus veQual ySect onRule,
        UntrustedUrlConversat onsT etLabelRule,
        DownrankSpamReplyConversat onsT etLabelRule,
        DownrankSpamReplyConversat onsAuthorLabelRule,
        H ghProact veTosScoreT etLabelDownrank ngRule,
        SafetyCr s sLevel3Sect onRule,
        SafetyCr s sLevel4Sect onRule,
        DoNotAmpl fySect onRule,
        DoNotAmpl fySect onUserRule,
        NotGraduatedConversat onsAuthorLabelRule,
        H ghSpam T etContentScoreConvoDownrankAbus veQual yRule,
        H ghCryptospamScoreConvoDownrankAbus veQual yRule,
        CopypastaSpamAbus veQual yT etLabelRule,
        H ghTox c yScoreDownrankLowQual ySect onRule,
        H ghPSpam T etScoreDownrankLowQual ySect onRule,
        R oAct onedT etDownrankLowQual ySect onRule,
        H ghTox c yScoreDownrankH ghQual ySect onRule,
      )
    )

case object T  l neConversat onsDownrank ngM n malPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        H ghProact veTosScoreT etLabelDownrank ngRule
      )
    )

case object T  l neHo Recom ndat onsPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.un on(
        Recom ndat onsPol cy.t etRules.f lter(
          _ != NsfwH ghPrec s onT etLabelRule
        ),
        Seq(
          SafetyCr s sLevel2DropRule,
          SafetyCr s sLevel3DropRule,
          SafetyCr s sLevel4DropRule,
          H ghProact veTosScoreT etLabelDropRule,
          NsfwH ghRecallT etLabelRule,
        ),
        BaseT  l neHo Pol cy.t etRules,
      ),
      userRules = V s b l yPol cy.un on(
        Recom ndat onsPol cy.userRules,
        BaseT  l neHo Pol cy.userRules
      )
    )

case object T  l neHo Top cFollowRecom ndat onsPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.un on(
        Seq(
          SearchBlackl stT etLabelRule,
          GoreAndV olenceTop cH ghRecallT etLabelRule,
          NsfwH ghRecallT etLabelRule,
        ),
        Recom ndat onsPol cy.t etRules
          .f lterNot(
            Seq(
              NsfwH ghPrec s onT etLabelRule,
            ).conta ns),
        BaseT  l neHo Pol cy.t etRules
      ),
      userRules = V s b l yPol cy.un on(
        Recom ndat onsPol cy.userRules,
        BaseT  l neHo Pol cy.userRules
      )
    )

case object T  l neScorerPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        AllowAllRule
      )
    )

case object Follo dTop csT  l nePol cy
    extends V s b l yPol cy(
      userRules = Seq(
        AuthorBlocksV e rDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      )
    )

case object Top csLand ngPageTop cRecom ndat onsPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.un on(
        Seq(
          SearchBlackl stT etLabelRule,
          GoreAndV olenceTop cH ghRecallT etLabelRule,
          NsfwH ghRecallT etLabelRule
        ),
        Recom ndat onsPol cy.t etRules,
        BaseT  l neHo Pol cy.t etRules,
      ),
      userRules = V s b l yPol cy.un on(
        Recom ndat onsPol cy.userRules,
        BaseT  l neHo Pol cy.userRules
      ) ++ Seq(
        AuthorBlocksV e rDropRule
      )
    )

case object ExploreRecom ndat onsPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        DropOuterCommun yT etsRule,
        SearchBlackl stT etLabelRule,
        GoreAndV olenceTop cH ghRecallT etLabelRule,
        NsfwH ghRecallT etLabelRule,
        DropT etsW hGeoRestr cted d aRule,
        T etNsfwUserDropRule,
        T etNsfwAdm nDropRule,
        V e rHasMatch ngMutedKeywordForHo T  l neRule,
        V e rHasMatch ngMutedKeywordForNot f cat onsRule,
      ) ++ V s b l yPol cy.un on(
        Recom ndat onsPol cy.t etRules
      ),
      userRules = V s b l yPol cy.un on(
        Recom ndat onsPol cy.userRules
      ) ++ Seq(
        AuthorBlocksV e rDropRule,
        V e rMutesAuthorRule,
        V e rBlocksAuthorRule
      )
    )

case object Tombston ngPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        Tombstone f.V e r sBlockedByAuthor,
        Tombstone f.Author sProtected,
        Tombstone f.Reply sModeratedByRootAuthor,
        Tombstone f.Author sSuspended,
        Tombstone f.Author sDeact vated,
         nterst  al f.V e rHardMutedAuthor
      )
    )

case object T etReplyNudgePol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        SpamAllUsersT etLabelRule,
        PdnaAllUsersT etLabelRule,
        BounceAllUsersT etLabelRule,
        T etNsfwAdm nDropRule,
        T etNsfwUserDropRule,
        NsfwH ghRecallAllUsersT etLabelDropRule,
        NsfwH ghPrec s onAllUsersT etLabelDropRule,
        GoreAndV olenceH ghPrec s onAllUsersT etLabelDropRule,
        NsfwReported ur st csAllUsersT etLabelDropRule,
        GoreAndV olenceReported ur st csAllUsersT etLabelDropRule,
        NsfwCard mageAllUsersT etLabelDropRule,
        NsfwV deoAllUsersT etLabelDropRule,
        NsfwTextAllUsersT etLabelDropRule,
      ),
      userRules = Seq(
        DropNsfwUserAuthorRule,
        DropNsfwAdm nAuthorRule,
        NsfwTextAllUsersDropRule
      )
    )

case object Human zat onNudgePol cy
    extends V s b l yPol cy(
      userRules = UserT  l neRules.UserRules
    )

case object TrendsRepresentat veT etPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.un on(
        Recom ndat onsPol cy.t etRules,
        Seq(
          Abus veH ghRecallT etLabelRule,
          BystanderAbus veT etLabelRule,
          Dupl cateContentT etLabelDropRule,
          LowQual yT etLabelDropRule,
          H ghProact veTosScoreT etLabelDropRule,
          NsfaH ghRecallT etLabelRule,
          NsfwCard mageAllUsersT etLabelDropRule,
          NsfwH ghPrec s onT etLabelRule,
          NsfwH ghRecallAllUsersT etLabelDropRule,
          NsfwV deoT etLabelDropRule,
          NsfwTextT etLabelDropRule,
          PdnaAllUsersT etLabelRule,
          SearchBlackl stT etLabelRule,
          SpamH ghRecallT etLabelDropRule,
          UntrustedUrlAllV e rsT etLabelRule,
          DownrankSpamReplyAllV e rsT etLabelRule,
          H ghPSpam ScoreAllV e rDropRule,
          DoNotAmpl fyAllV e rsDropRule,
          S teSpamT etLabelDropRule,
          AuthorBlocksV e rDropRule,
          V e rBlocksAuthorRule,
          V e rMutesAuthorRule,
          CopypastaSpamAllV e rsT etLabelRule,
        )
      ),
      userRules = V s b l yPol cy.un on(
        Recom ndat onsPol cy.userRules,
        Seq(
          Abus veRule,
          LowQual yRule,
          ReadOnlyRule,
          Comprom sedRule,
          Recom ndat onsBlackl stRule,
          SpamH ghRecallRule,
          Dupl cateContentRule,
          NsfwH ghPrec s onRule,
          NsfwNearPerfectAuthorRule,
          NsfwBanner mageRule,
          NsfwAvatar mageRule,
          Engage ntSpam rRule,
          Engage ntSpam rH ghRecallRule,
          Abus veH ghRecallRule,
          SearchBlackl stRule,
          SearchNsfwTextRule,
          NsfwH ghRecallRule,
          TsV olat onRule,
          DownrankSpamReplyAllV e rsRule,
          NsfwTextNonAuthorDropRule
        )
      )
    )

case object AdsCampa gnPol cy
    extends V s b l yPol cy(
      userRules = Seq(SuspendedAuthorRule),
      t etRules = V s b l yPol cy.baseT etRules
    )

case object AdsManagerPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        AdsManagerDenyL stAllUsersT etLabelRule,
      )
    )

case object AdsReport ngDashboardPol cy
    extends V s b l yPol cy(
      t etRules = AdsManagerPol cy.t etRules,
      userRules = AdsCampa gnPol cy.userRules
    )

case object B rdwatchNoteAuthorPol cy
    extends V s b l yPol cy(
      userRules = Seq(
        SuspendedAuthorRule,
        AuthorBlocksV e rDropRule,
        V e rBlocksAuthorRule,
        V e rMutesAuthorRule
      )
    )

case object B rdwatchNoteT etsT  l nePol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          MutedRet etsRule,
          AuthorBlocksV e rDropRule,
          V e rMutesAuthorRule,
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
        ) ++ L m edEngage ntBaseRules.t etRules
    )

case object B rdwatchNeedsY  lpNot f cat onsPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          AuthorBlocksV e rDropRule,
          V e rBlocksAuthorRule,
          V e rMutesAuthorRule,
          V e rHasMatch ngMutedKeywordForHo T  l neRule,
          V e rHasMatch ngMutedKeywordForNot f cat onsRule,
        )
    )

case object ForDevelop ntOnlyPol cy
    extends V s b l yPol cy(
      userRules = Seq.empty,
      t etRules = V s b l yPol cy.baseT etRules
    )

case object UserProf le aderPol cy
    extends V s b l yPol cy(
      userRules = Seq.empty,
      t etRules = Seq(DropAllRule)
    )

case object UserScopedT  l nePol cy
    extends V s b l yPol cy(
      userRules = UserT  l neRules.UserRules,
      t etRules = Seq(DropAllRule)
    )

case object T etScopedT  l nePol cy
    extends V s b l yPol cy(
      userRules = UserT  l neRules.UserRules,
      t etRules = Seq.empty
    )

case object Soft ntervent onP votPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules
    )

case object CuratedTrendsRepresentat veT etPol cy
    extends V s b l yPol cy(
      userRules = Seq(
        SuspendedAuthorRule,
        AuthorBlocksV e rDropRule,
        V e rBlocksAuthorRule,
        V e rMutesAndDoesNotFollowAuthorRule
      )
    )

case object Commun  esPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          Ret etDropRule,
          AbusePol cyEp sod cT etLabelDropRule,
          E rgencyDropRule,
          SafetyCr s sLevel4DropRule,
          ReportedT et nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
        ) ++ L m edEngage ntBaseRules.t etRules
    )

case object T  l neHo Commun  esPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.un on(
        Seq(
          DropAllAuthorRemovedCommun yT etsRule,
          DropAllH ddenCommun yT etsRule,
          V e rHasMatch ngMutedKeywordForHo T  l neRule,
        ),
        V s b l yPol cy.baseQuotedT etTombstoneRules,
        Commun  esPol cy.t etRules,
      ),
      userRules = Seq(
        V e rMutesAuthorRule,
        V e rBlocksAuthorRule,
      )
    )

case object T  l neHo PromotedHydrat onPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        V e rHasMatch ngMutedKeywordForHo T  l nePromotedT etRule,
        V e rMutesAuthorHo T  l nePromotedT etRule,
        V e rBlocksAuthorHo T  l nePromotedT etRule
      ) ++ T  l neHo Hydrat onPol cy.t etRules,
      pol cyRuleParams = T  l neHo Hydrat onPol cy.pol cyRuleParams
    )

case object SpacesPol cy
    extends V s b l yPol cy(
        SpaceDoNotAmpl fyAllUsersDropRule,
        SpaceNsfwH ghPrec s onNonFollo rDropRule),
      userRules = Seq(
        AuthorBlocksV e rDropRule
      )
    )

case object SpacesSellerAppl cat onStatusPol cy
    extends V s b l yPol cy(
      userRules = Seq(
        V e r sNotAuthorDropRule
      )
    )

case object SpacesPart c pantsPol cy
    extends V s b l yPol cy(
      t etRules = Seq(DropAllRule),
      userRules = Seq(
        AuthorBlocksV e rDropRule,
        SuspendedAuthorRule
      )
    )

case object SpacesShar ngPol cy
    extends V s b l yPol cy(
      t etRules = T etDeta lPol cy.t etRules,
      userRules = Seq(
        AuthorBlocksV e rDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      ),
      pol cyRuleParams = T etDeta lPol cy.pol cyRuleParams
    )

case object SpaceFleetl nePol cy
    extends V s b l yPol cy(
      spaceRules = Seq(
        SpaceDoNotAmpl fyNonFollo rDropRule,
        SpaceCoordHarmfulAct v yH ghRecallNonFollo rDropRule,
        SpaceUntrustedUrlNonFollo rDropRule,
        SpaceM slead ngH ghRecallNonFollo rDropRule,
        SpaceNsfwH ghPrec s onAllUsers nterst  alRule
      ),
      userRules = Seq(
        TsV olat onRule,
        DoNotAmpl fyNonFollo rRule,
        NotGraduatedNonFollo rRule,
        L kely vsLabelNonFollo rDropUserRule,
        UserAbus veNonFollo rDropRule
      )
    )

case object SpaceNot f cat onsPol cy
    extends V s b l yPol cy(
      spaceRules = Seq(
        SpaceHatefulH ghRecallAllUsersDropRule,
        SpaceV olenceH ghRecallAllUsersDropRule,
        SpaceDoNotAmpl fyAllUsersDropRule,
        SpaceCoordHarmfulAct v yH ghRecallAllUsersDropRule,
        SpaceUntrustedUrlNonFollo rDropRule,
        SpaceM slead ngH ghRecallNonFollo rDropRule,
        SpaceNsfwH ghPrec s onAllUsersDropRule,
        SpaceNsfwH ghRecallAllUsersDropRule,
        V e rHasMatch ngMutedKeyword nSpaceT leForNot f cat onsRule
      ),
      userRules = Seq(
        V e rMutesAuthorRule,
        V e rBlocksAuthorRule,
        AuthorBlocksV e rDropRule,
        TsV olat onRule,
        DoNotAmpl fyUserRule,
        Abus veRule,
        SearchBlackl stRule,
        SearchNsfwTextRule,
        Recom ndat onsBlackl stRule,
        NotGraduatedRule,
        SpamH ghRecallRule,
        Abus veH ghRecallRule,
        UserBl nkWorstAllUsersDropRule,
        UserNsfwNearPerfectNonFollo rDropRule,
        SpaceNsfwH ghPrec s onNonFollo rDropRule,
        UserNsfwAvatar mageNonFollo rDropRule,
        UserNsfwBanner mageNonFollo rDropRule
      )
    )

case object SpaceT etAvatarHo T  l nePol cy
    extends V s b l yPol cy(
      spaceRules = Seq(
        SpaceDoNotAmpl fyNonFollo rDropRule,
        SpaceCoordHarmfulAct v yH ghRecallNonFollo rDropRule,
        SpaceUntrustedUrlNonFollo rDropRule,
        SpaceM slead ngH ghRecallNonFollo rDropRule,
        SpaceNsfwH ghPrec s onAllUsersDropRule,
        SpaceNsfwH ghPrec s onAllUsers nterst  alRule
      ),
      userRules = Seq(
        TsV olat onRule,
        DoNotAmpl fyUserRule,
        NotGraduatedNonFollo rRule,
        Abus veRule,
        SearchBlackl stRule,
        SearchNsfwTextRule,
        Recom ndat onsBlackl stRule,
        SpamH ghRecallRule,
        Abus veH ghRecallRule,
        UserBl nkWorstAllUsersDropRule,
        UserNsfwNearPerfectNonFollo rDropRule,
        SpaceNsfwH ghPrec s onNonFollo rDropRule,
        UserNsfwAvatar mageNonFollo rDropRule,
        UserNsfwBanner mageNonFollo rDropRule
      )
    )

case object SpaceHo T  l neUprank ngPol cy
    extends V s b l yPol cy(
      spaceRules = Seq(
        SpaceDoNotAmpl fyNonFollo rDropRule,
        SpaceCoordHarmfulAct v yH ghRecallNonFollo rDropRule,
        SpaceUntrustedUrlNonFollo rDropRule,
        SpaceM slead ngH ghRecallNonFollo rDropRule,
        SpaceNsfwH ghPrec s onNonFollo rDropRule,
        SpaceNsfwH ghPrec s onSafeSearchNonFollo rDropRule,
        SpaceNsfwH ghRecallSafeSearchNonFollo rDropRule
      ),
      userRules = Seq(
        TsV olat onRule,
        DoNotAmpl fyUserRule,
        NotGraduatedRule,
        Abus veRule,
        SearchBlackl stRule,
        SearchNsfwTextRule,
        Recom ndat onsBlackl stRule,
        SpamH ghRecallRule,
        Abus veH ghRecallRule,
        UserBl nkWorstAllUsersDropRule,
        UserNsfwNearPerfectNonFollo rDropRule,
        UserNsfwAvatar mageNonFollo rDropRule,
        UserNsfwBanner mageNonFollo rDropRule
      )
    )

case object SpaceJo nScreenPol cy
    extends V s b l yPol cy(
      spaceRules = Seq(
        SpaceNsfwH ghPrec s onAllUsers nterst  alRule
      )
    )

case object K c nS nkDevelop ntPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules.d ff(
        Seq(
          BounceT etLabelRule,
          DropExclus veT etContentRule,
          DropTrustedFr endsT etContentRule
        )
      ) ++ Seq(
        BounceT etLabelTombstoneRule,
        TombstoneExclus veT etContentRule,
        TombstoneTrustedFr endsT etContentRule)
        ++ Seq(
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          V e rReportsAuthor nterst  alRule,
          V e rMutesAuthor nterst  alRule,
          V e rBlocksAuthor nterst  alRule,
          MutedKeywordForT etRepl es nterst  alRule,
          ReportedT et nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
          Exper  ntalNudgeLabelRule,
        ) ++ L m edEngage ntBaseRules.t etRules,
      userRules = Seq(
        AuthorBlocksV e rDropRule,
        ProtectedAuthorTombstoneRule,
        SuspendedAuthorRule
      ),
      userUnava lableStateRules = Seq(
        SuspendedUserUnava lableRet etTombstoneRule,
        Deact vatedUserUnava lableRet etTombstoneRule,
        OffBoardedUserUnava lableRet etTombstoneRule,
        ErasedUserUnava lableRet etTombstoneRule,
        ProtectedUserUnava lableRet etTombstoneRule,
        AuthorBlocksV e rUserUnava lableRet etTombstoneRule,
        V e rBlocksAuthorUserUnava lableRet etTombstoneRule,
        V e rMutesAuthorUserUnava lableRet etTombstoneRule,
        SuspendedUserUnava lable nnerQuotedT etTombstoneRule,
        Deact vatedUserUnava lable nnerQuotedT etTombstoneRule,
        OffBoardedUserUnava lable nnerQuotedT etTombstoneRule,
        ErasedUserUnava lable nnerQuotedT etTombstoneRule,
        ProtectedUserUnava lable nnerQuotedT etTombstoneRule,
        AuthorBlocksV e rUserUnava lable nnerQuotedT etTombstoneRule,
        SuspendedUserUnava lableT etTombstoneRule,
        Deact vatedUserUnava lableT etTombstoneRule,
        OffBoardedUserUnava lableT etTombstoneRule,
        ErasedUserUnava lableT etTombstoneRule,
        ProtectedUserUnava lableT etTombstoneRule,
        AuthorBlocksV e rUserUnava lableT etTombstoneRule,
        V e rBlocksAuthorUserUnava lable nnerQuotedT et nterst  alRule,
        V e rMutesAuthorUserUnava lable nnerQuotedT et nterst  alRule
      ),
      deletedT etRules = Seq(
        TombstoneDeletedOuterT etRule,
        TombstoneBounceDeletedOuterT etRule,
        TombstoneDeletedQuotedT etRule,
        TombstoneBounceDeletedQuotedT etRule
      ),
       d aRules = V s b l yPol cy.base d aRules
    )

case object Curat onPol cyV olat onsPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++ Seq(
        DoNotAmpl fyAllV e rsDropRule,
      ),
      userRules = Seq(
        DoNotAmpl fyUserRule,
        TsV olat onRule
      )
    )

case object GraphqlDefaultPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
        ) ++ L m edEngage ntBaseRules.t etRules
    )

case object GryphonDecksAndColumnsShar ngPol cy
    extends V s b l yPol cy(
      userRules = Seq(
        AuthorBlocksV e rDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      ),
      t etRules = Seq(DropAllRule)
    )

case object UserSett ngsPol cy
    extends V s b l yPol cy(
      userRules = Seq(V e r sNotAuthorDropRule),
      t etRules = Seq(DropAllRule)
    )

case object BlockMuteUsersT  l nePol cy
    extends V s b l yPol cy(
      userRules = Seq(SuspendedAuthorRule),
      t etRules = Seq(DropAllRule)
    )

case object Top cRecom ndat onsPol cy
    extends V s b l yPol cy(
      t etRules =
        Seq(
          NsfwH ghRecallT etLabelRule,
          NsfwTextH ghPrec s onT etLabelDropRule
        )
          ++ Recom ndat onsPol cy.t etRules,
      userRules = Recom ndat onsPol cy.userRules
    )

case object R oAct onedT etT  l nePol cy
    extends V s b l yPol cy(
      t etRules =
        V s b l yPol cy.baseT etTombstoneRules
          ++ Seq(
            AuthorBlocksV e rTombstoneRule,
            ProtectedAuthorTombstoneRule
          ),
      deletedT etRules = Seq(
        TombstoneDeletedOuterT etRule,
        TombstoneBounceDeletedOuterT etRule,
        TombstoneDeletedQuotedT etRule,
        TombstoneBounceDeletedQuotedT etRule,
      ),
    )

case object EmbeddedT etsPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etTombstoneRules
        ++ Seq(
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
        )
        ++ L m edEngage ntBaseRules.t etRules,
      deletedT etRules = Seq(
        TombstoneDeletedOuterT etRule,
        TombstoneBounceDeletedOuterT etRule,
        TombstoneDeletedQuotedT etRule,
        TombstoneBounceDeletedQuotedT etRule,
      ),
      userUnava lableStateRules = Seq(
        SuspendedUserUnava lableT etTombstoneRule,
        Deact vatedUserUnava lableT etTombstoneRule,
        OffBoardedUserUnava lableT etTombstoneRule,
        ErasedUserUnava lableT etTombstoneRule,
        ProtectedUserUnava lableT etTombstoneRule,
        AuthorBlocksV e rUserUnava lable nnerQuotedT etTombstoneRule,
      )
    )

case object EmbedT etMarkupPol cy
    extends V s b l yPol cy(
      t etRules = Seq(DropStaleT etsRule) ++
        V s b l yPol cy.baseT etTombstoneRules
        ++ Seq(
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
        )
        ++ L m edEngage ntBaseRules.t etRules,
      deletedT etRules = Seq(
        TombstoneDeletedOuterT etRule,
        TombstoneBounceDeletedOuterT etRule,
        TombstoneDeletedQuotedT etRule,
        TombstoneBounceDeletedQuotedT etRule,
      ),
    )

case object Art cleT etT  l nePol cy
    extends V s b l yPol cy(
      t etRules =
          V s b l yPol cy.baseT etRules ++
          Seq(
            V e rHasMatch ngMutedKeywordForHo T  l neRule,
            AbusePol cyEp sod cT etLabel nterst  alRule,
            E rgencyDynam c nterst  alRule,
            NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
            GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
            NsfwReported ur st csAllUsersT etLabelRule,
            GoreAndV olenceReported ur st csAllUsersT etLabelRule,
            NsfwCard mageAllUsersT etLabelRule,
          ) ++ L m edEngage ntBaseRules.t etRules,
      userRules = Seq(
        AuthorBlocksV e rDropRule,
        V e rBlocksAuthorRule,
        V e rMutesAuthorRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      )
    )

case object Conversat onFocalPrehydrat onPol cy
    extends V s b l yPol cy(
      deletedT etRules = Seq(
        TombstoneBounceDeletedOuterT etRule,
        TombstoneBounceDeletedQuotedT etRule,
      )
    )

case object Conversat onFocalT etPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etTombstoneRules
        ++ Seq(
          Dynam cProductAdDropT etLabelRule,
          AuthorBlocksV e rTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwH ghPrec s onT etLabelDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.V olent d aGoreAndV olenceH ghPrec s onDropSett ngLeveTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwReported ur st csT etLabelDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.V olent d aGoreAndV olenceReported ur st csDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwCard mageT etLabelDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Ot rSens  ve d aNsfwUserT etFlagDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Ot rSens  ve d aNsfwAdm nT etFlagDropSett ngLevelTombstoneRule,
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          ReportedT et nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwH ghPrec s onT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceH ghPrec s on nterst  alRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwReported ur st csT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceReported ur st cs nterst  alRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwCard mageT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwUserT etFlag nterst  alRule,
          Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwAdm nT etFlag nterst  alRule,
          GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule,
          NsfwReported ur st csAvo dAdPlace ntAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAvo dAdPlace ntAllUsersT etLabelRule,
          NsfwCard mageAvo dAdPlace ntAllUsersT etLabelRule,
          MutedKeywordForQuotedT etT etDeta l nterst  alRule,
          V e rMutesAuthor nnerQuotedT et nterst  alRule,
          V e rBlocksAuthor nnerQuotedT et nterst  alRule,
        )
        ++ L m edEngage ntBaseRules.t etRules
        ++ Conversat onsAdAvo danceRules.t etRules,
      deletedT etRules = Seq(
        TombstoneBounceDeletedOuterT etRule,
        TombstoneDeletedQuotedT etRule,
        TombstoneBounceDeletedQuotedT etRule,
      ),
      userUnava lableStateRules = Seq(
        SuspendedUserUnava lableT etTombstoneRule,
        Deact vatedUserUnava lableT etTombstoneRule,
        OffBoardedUserUnava lableT etTombstoneRule,
        ErasedUserUnava lableT etTombstoneRule,
        ProtectedUserUnava lableT etTombstoneRule,
        AuthorBlocksV e rUserUnava lable nnerQuotedT etTombstoneRule,
        UserUnava lableT etTombstoneRule,
        V e rBlocksAuthorUserUnava lable nnerQuotedT et nterst  alRule,
        V e rMutesAuthorUserUnava lable nnerQuotedT et nterst  alRule
      ),
      pol cyRuleParams = Conversat onsAdAvo danceRules.pol cyRuleParams
        ++ Sens  ve d aSett ngsConversat onBaseRules.pol cyRuleParams
    )

case object Conversat onReplyPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etTombstoneRules
        ++ Seq(
          LowQual yT etLabelTombstoneRule,
          SpamH ghRecallT etLabelTombstoneRule,
          Dupl cateContentT etLabelTombstoneRule,
          Dec derableSpamH ghRecallAuthorLabelTombstoneRule,
          S teSpamT etLabelTombstoneRule,
          AuthorBlocksV e rTombstoneRule,
          Tox c yReplyF lterRule,
          Dynam cProductAdDropT etLabelRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwH ghPrec s onT etLabelDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.V olent d aGoreAndV olenceH ghPrec s onDropSett ngLeveTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwReported ur st csT etLabelDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.V olent d aGoreAndV olenceReported ur st csDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Adult d aNsfwCard mageT etLabelDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Ot rSens  ve d aNsfwUserT etFlagDropSett ngLevelTombstoneRule,
          Sens  ve d aT etDropSett ngLevelTombstoneRules.Ot rSens  ve d aNsfwAdm nT etFlagDropSett ngLevelTombstoneRule,
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          MutedKeywordForT etRepl es nterst  alRule,
          ReportedT et nterst  alRule,
          V e rBlocksAuthor nterst  alRule,
          V e rMutesAuthor nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwH ghPrec s onT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceH ghPrec s on nterst  alRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwReported ur st csT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.V olent d aGoreAndV olenceReported ur st cs nterst  alRule,
          Sens  ve d aT et nterst  alRules.Adult d aNsfwCard mageT etLabel nterst  alRule,
          Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwUserT etFlag nterst  alRule,
          Sens  ve d aT et nterst  alRules.Ot rSens  ve d aNsfwAdm nT etFlag nterst  alRule,
          GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule,
          NsfwReported ur st csAvo dAdPlace ntAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAvo dAdPlace ntAllUsersT etLabelRule,
          NsfwCard mageAvo dAdPlace ntAllUsersT etLabelRule,
        )
        ++ L m edEngage ntBaseRules.t etRules
        ++ Conversat onsAdAvo danceRules.t etRules,
      userRules = Seq(
        LowQual yRule,
        ReadOnlyRule,
        LowQual yH ghRecallRule,
        Comprom sedRule,
        Dec derableSpamH ghRecallRule
      ),
      deletedT etRules = Seq(
        TombstoneDeletedOuterT etRule,
        TombstoneBounceDeletedOuterT etRule,
        TombstoneDeletedQuotedT etRule,
        TombstoneBounceDeletedQuotedT etRule,
      ),
      userUnava lableStateRules = Seq(
        SuspendedUserUnava lableT etTombstoneRule,
        Deact vatedUserUnava lableT etTombstoneRule,
        OffBoardedUserUnava lableT etTombstoneRule,
        ErasedUserUnava lableT etTombstoneRule,
        ProtectedUserUnava lableT etTombstoneRule,
        AuthorBlocksV e rUserUnava lable nnerQuotedT etTombstoneRule,
        UserUnava lableT etTombstoneRule,
        V e rBlocksAuthorUserUnava lable nnerQuotedT et nterst  alRule,
        V e rMutesAuthorUserUnava lable nnerQuotedT et nterst  alRule
      ),
      pol cyRuleParams = Conversat onsAdAvo danceRules.pol cyRuleParams
        ++ Sens  ve d aSett ngsConversat onBaseRules.pol cyRuleParams
    )

case object AdsBus nessSett ngsPol cy
    extends V s b l yPol cy(
      t etRules = Seq(DropAllRule)
    )

case object UserM lestoneRecom ndat onPol cy
    extends V s b l yPol cy(
      userRules = Recom ndat onsPol cy.userRules ++ Seq(
      )
    )

case object TrustedFr endsUserL stPol cy
    extends V s b l yPol cy(
      t etRules = Seq(DropAllRule),
      userRules = Seq(
        V e rBlocksAuthorRule
      )
    )

case object Tw terDelegateUserL stPol cy
    extends V s b l yPol cy(
      userRules = Seq(
        V e rBlocksAuthorRule,
        V e r sAuthorDropRule,
        Deact vatedAuthorRule,
        AuthorBlocksV e rDropRule
      ),
      t etRules = Seq(DropAllRule)
    )

case object Qu ckPromoteT etEl g b l yPol cy
    extends V s b l yPol cy(
      t etRules = T etDeta lPol cy.t etRules,
      userRules = UserT  l neRules.UserRules,
      pol cyRuleParams = T etDeta lPol cy.pol cyRuleParams
    )

case object ReportCenterPol cy
    extends V s b l yPol cy(
      t etRules = Conversat onFocalT etPol cy.t etRules.d ff(
        Conversat onsAdAvo danceRules.t etRules
      ),
      deletedT etRules = Seq(
        TombstoneBounceDeletedOuterT etRule,
        TombstoneDeletedQuotedT etRule,
        TombstoneBounceDeletedQuotedT etRule,
        TombstoneDeletedOuterT etRule,
      ),
      userUnava lableStateRules = Seq(
        SuspendedUserUnava lableT etTombstoneRule,
        Deact vatedUserUnava lableT etTombstoneRule,
        OffBoardedUserUnava lableT etTombstoneRule,
        ErasedUserUnava lableT etTombstoneRule,
        ProtectedUserUnava lableT etTombstoneRule,
        AuthorBlocksV e rUserUnava lable nnerQuotedT etTombstoneRule,
        UserUnava lableT etTombstoneRule,
        V e rBlocksAuthorUserUnava lable nnerQuotedT et nterst  alRule,
        V e rMutesAuthorUserUnava lable nnerQuotedT et nterst  alRule
      ),
      pol cyRuleParams = Conversat onFocalT etPol cy.pol cyRuleParams
    )

case object Conversat on njectedT etPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etRules ++
        Seq(
          AbusePol cyEp sod cT etLabel nterst  alRule,
          E rgencyDynam c nterst  alRule,
          NsfwH ghPrec s on nterst  alAllUsersT etLabelRule,
          GoreAndV olenceH ghPrec s onAllUsersT etLabelRule,
          NsfwReported ur st csAllUsersT etLabelRule,
          GoreAndV olenceReported ur st csAllUsersT etLabelRule,
          NsfwCard mageAllUsersT etLabelRule,
        ) ++
        L m edEngage ntBaseRules.t etRules ++ Seq(
        Sk pT etDeta lL m edEngage ntT etLabelRule
      )
    )

case object Ed  toryT  l nePol cy
    extends V s b l yPol cy(
      t etRules = Conversat onReplyPol cy.t etRules,
      pol cyRuleParams = Conversat onReplyPol cy.pol cyRuleParams,
      deletedT etRules = Conversat onReplyPol cy.deletedT etRules,
      userUnava lableStateRules = Conversat onReplyPol cy.userUnava lableStateRules)

case object UserSelfV ewOnlyPol cy
    extends V s b l yPol cy(
      userRules = Seq(V e r sNotAuthorDropRule),
      t etRules = Seq(DropAllRule)
    )

case object Tw terArt cleComposePol cy
    extends V s b l yPol cy(
      tw terArt cleRules = Seq(
        V e r sNotAuthorDropRule
      )
    )

case object Tw terArt cleProf leTabPol cy
    extends V s b l yPol cy(
      tw terArt cleRules = Seq(
        AuthorBlocksV e rDropRule
      )
    )

case object Tw terArt cleReadPol cy
    extends V s b l yPol cy(
      tw terArt cleRules = Seq(
        AuthorBlocksV e rDropRule,
      )
    )

case object ContentControlTool nstallPol cy
    extends V s b l yPol cy(
      userRules = UserProf le aderPol cy.userRules,
      t etRules = UserProf le aderPol cy.t etRules
    )

case object T  l neProf leSpacesPol cy
    extends V s b l yPol cy(
      userRules = UserProf le aderPol cy.userRules,
      t etRules = UserProf le aderPol cy.t etRules
    )

case object T  l neFavor esSelfV ewPol cy
    extends V s b l yPol cy(
      t etRules = T  l neFavor esPol cy.t etRules.d ff(Seq(DropStaleT etsRule)),
      pol cyRuleParams = T  l neFavor esPol cy.pol cyRuleParams,
      deletedT etRules = T  l neFavor esPol cy.deletedT etRules,
      userUnava lableStateRules = T  l neFavor esPol cy.userUnava lableStateRules
    )

case object BaseQ gPol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        AbusePol cyEp sod cT etLabelDropRule,
        Automat onT etLabelRule,
        DoNotAmpl fyDropRule,
        DownrankSpamReplyT etLabelRule,
        Dupl cateContentT etLabelDropRule,
        Dupl cate nt onT etLabelRule,
        NsfwH ghPrec s onT etLabelRule,
        GoreAndV olenceH ghPrec s onT etLabelRule,
        GoreAndV olenceReported ur st csT etLabelRule,
        L kely vsLabelNonFollo rDropUserRule,
        NsfwCard mageT etLabelRule,
        NsfwH ghRecallT etLabelRule,
        NsfwReported ur st csT etLabelRule,
        NsfwTextT etLabelDropRule,
        NsfwV deoT etLabelDropRule,
        PdnaT etLabelRule,
        SafetyCr s sLevel3DropRule,
        SafetyCr s sLevel4DropRule,
        SearchBlackl stH ghRecallT etLabelDropRule,
        SearchBlackl stT etLabelRule,
        S teSpamT etLabelDropRule,
        SpamH ghRecallT etLabelDropRule,
      ),
      userRules = Seq(
        Dupl cateContentRule,
        Engage ntSpam rH ghRecallRule,
        Engage ntSpam rRule,
        NsfwAvatar mageRule,
        NsfwBanner mageRule,
        NsfwH ghPrec s onRule,
        NsfwH ghRecallRule,
        NsfwSens  veRule,
        ReadOnlyRule,
        Recom ndat onsBlackl stRule,
        SearchBlackl stRule,
        SpamH ghRecallRule
      ))

case object Not f cat onsQ gPol cy
    extends V s b l yPol cy(
      t etRules = BaseQ gPol cy.t etRules ++ Seq(
        DropAllCommun yT etsRule,
        DropNsfwAdm nAuthorV e rOpt nF lter ngOnSearchRule,
        H ghProact veTosScoreT etLabelDropSearchRule,
        LowQual yT etLabelDropRule,
        NsfwH ghPrec s onRule,
        NsfwH ghRecallRule,
        NsfwNearPerfectAuthorRule,
        NsfwSens  veRule,
      ),
      userRules = BaseQ gPol cy.userRules ++ Seq(
        Abus veRule,
        LowQual yRule,
        Comprom sedRule,
        V e rBlocksAuthorV e rOpt nBlock ngOnSearchRule,
        V e rMutesAuthorV e rOpt nBlock ngOnSearchRule,
        DropNsfwAdm nAuthorV e rOpt nF lter ngOnSearchRule,
        NsfwNearPerfectAuthorRule
      )
    )

case object Shopp ngManagerSpyModePol cy
    extends V s b l yPol cy(
      t etRules = Seq(
        DropAllRule
      ),
      userRules = Seq(
        SuspendedAuthorRule,
        Deact vatedAuthorRule,
        ErasedAuthorRule,
        OffboardedAuthorRule
      )
    )

case object Z pb rdConsu rArch vesPol cy
    extends V s b l yPol cy(
      t etRules = V s b l yPol cy.baseT etTombstoneRules,
      userRules = Seq(
        AuthorBlocksV e rDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule,
      ),
      userUnava lableStateRules = Seq(
        AuthorBlocksV e rUserUnava lableT etTombstoneRule,
        ProtectedUserUnava lableT etTombstoneRule,
        SuspendedUserUnava lableT etTombstoneRule,
      ),
      deletedT etRules = Seq(
        TombstoneDeletedT etRule,
        TombstoneBounceDeletedT etRule,
      )
    )

case class M xedV s b l yPol cy(
  or g nalPol cy: V s b l yPol cy,
  add  onalT etRules: Seq[Rule])
    extends V s b l yPol cy(
      t etRules = (add  onalT etRules ++ or g nalPol cy.t etRules)
        .sortW h(_.act onBu lder.act onSever y > _.act onBu lder.act onSever y),
      userRules = or g nalPol cy.userRules,
      cardRules = or g nalPol cy.cardRules,
      quotedT etRules = or g nalPol cy.quotedT etRules,
      dmRules = or g nalPol cy.dmRules,
      dmConversat onRules = or g nalPol cy.dmConversat onRules,
      dmEventRules = or g nalPol cy.dmEventRules,
      spaceRules = or g nalPol cy.spaceRules,
      userUnava lableStateRules = or g nalPol cy.userUnava lableStateRules,
      tw terArt cleRules = or g nalPol cy.tw terArt cleRules,
      deletedT etRules = or g nalPol cy.deletedT etRules,
       d aRules = or g nalPol cy. d aRules,
      commun yRules = or g nalPol cy.commun yRules,
      pol cyRuleParams = or g nalPol cy.pol cyRuleParams
    )

case object T etAwardPol cy
    extends V s b l yPol cy(
      userRules = Seq.empty,
      t etRules =
        V s b l yPol cy.baseT etRules ++ Seq(
          E rgencyDropRule,
          NsfwH ghPrec s onT etLabelRule,
          NsfwH ghRecallT etLabelRule,
          NsfwReported ur st csT etLabelRule,
          NsfwCard mageT etLabelRule,
          NsfwV deoT etLabelDropRule,
          NsfwTextT etLabelDropRule,
          GoreAndV olenceH ghPrec s onT etLabelRule,
          GoreAndV olenceReported ur st csT etLabelRule,
          GoreAndV olenceT etLabelRule,
          AbusePol cyEp sod cT etLabelDropRule,
          Abus veT etLabelRule,
          BystanderAbus veT etLabelRule
        )
    )
