package com.tw ter.v s b l y.rules

 mport com.tw ter.content alth.sens  ve d asett ngs.thr ftscala.Sens  ve d aSett ngsLevel
 mport com.tw ter.content alth.tox creplyf lter.thr ftscala.F lterState
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.g zmoduck.thr ftscala.Label
 mport com.tw ter.g zmoduck.thr ftscala.MuteSurface
 mport com.tw ter. alth.platform_man pulat on.stcm_t et_holdback.StcmT etHoldback
 mport com.tw ter.search.common.constants.thr ftscala.Thr ftQueryS ce
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.takedown.ut l.TakedownReasons
 mport com.tw ter.takedown.ut l.{TakedownReasons => TakedownReasonsUt l}
 mport com.tw ter.t  l nes.conf gap .EnumParam
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.tseng.w hhold ng.thr ftscala.TakedownReason
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport com.tw ter.v s b l y.conf gap .params.RuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams
 mport com.tw ter.v s b l y.features.Author sSuspended
 mport com.tw ter.v s b l y.features.Card sPoll
 mport com.tw ter.v s b l y.features.CardUr Host
 mport com.tw ter.v s b l y.features.SearchQueryS ce
 mport com.tw ter.v s b l y.features._
 mport com.tw ter.v s b l y.features.{AuthorBlocksOuterAuthor => AuthorBlocksOuterAuthorFeature}
 mport com.tw ter.v s b l y.features.{AuthorBlocksV e r => AuthorBlocksV e rFeature}
 mport com.tw ter.v s b l y.features.{
  Commun yT etAuthor sRemoved => Commun yT etAuthor sRemovedFeature
}
 mport com.tw ter.v s b l y.features.{
  Commun yT etCommun yNotFound => Commun yT etCommun yNotFoundFeature
}
 mport com.tw ter.v s b l y.features.{
  Commun yT etCommun yDeleted => Commun yT etCommun yDeletedFeature
}
 mport com.tw ter.v s b l y.features.{
  Commun yT etCommun ySuspended => Commun yT etCommun ySuspendedFeature
}
 mport com.tw ter.v s b l y.features.{
  Commun yT etCommun yV s ble => Commun yT etCommun yV s bleFeature
}
 mport com.tw ter.v s b l y.features.{Commun yT et sH dden => Commun yT et sH ddenFeature}
 mport com.tw ter.v s b l y.features.{
  Not f cat on sOnCommun yT et => Not f cat on sOnCommun yT etFeature
}
 mport com.tw ter.v s b l y.features.{OuterAuthorFollowsAuthor => OuterAuthorFollowsAuthorFeature}
 mport com.tw ter.v s b l y.features.{OuterAuthor s nnerAuthor => OuterAuthor s nnerAuthorFeature}
 mport com.tw ter.v s b l y.features.{T etHasCard => T etHasCardFeature}
 mport com.tw ter.v s b l y.features.{T etHas d a => T etHas d aFeature}
 mport com.tw ter.v s b l y.features.{T et sCommun yT et => T et sCommun yT etFeature}
 mport com.tw ter.v s b l y.features.{T et sEd T et => T et sEd T etFeature}
 mport com.tw ter.v s b l y.features.{T et sStaleT et => T et sStaleT etFeature}
 mport com.tw ter.v s b l y.features.{V e rBlocksAuthor => V e rBlocksAuthorFeature}
 mport com.tw ter.v s b l y.features.{V e r sCommun yAdm n => V e r sCommun yAdm nFeature}
 mport com.tw ter.v s b l y.features.{V e r sCommun y mber => V e r sCommun y mberFeature}
 mport com.tw ter.v s b l y.features.{
  V e r sCommun yModerator => V e r sCommun yModeratorFeature
}
 mport com.tw ter.v s b l y.features.{
  V e r s nternalCommun  esAdm n => V e r s nternalCommun  esAdm nFeature
}
 mport com.tw ter.v s b l y.features.{V e rMutesAuthor => V e rMutesAuthorFeature}
 mport com.tw ter.v s b l y.features.{
  V e rMutesRet etsFromAuthor => V e rMutesRet etsFromAuthorFeature
}
 mport com.tw ter.v s b l y.models.V olat onLevel
 mport com.tw ter.v s b l y.models._
 mport com.tw ter.v s b l y.rules.Result.FoundCardUr RootDoma n
 mport com.tw ter.v s b l y.rules.Result.Found d aLabel
 mport com.tw ter.v s b l y.rules.Result.FoundSpaceLabel
 mport com.tw ter.v s b l y.rules.Result.FoundSpaceLabelW hScoreAboveThreshold
 mport com.tw ter.v s b l y.rules.Result.FoundT etLabel
 mport com.tw ter.v s b l y.rules.Result.FoundT etLabelForPerspect valUser
 mport com.tw ter.v s b l y.rules.Result.FoundT etLabelW hLanguage n
 mport com.tw ter.v s b l y.rules.Result.FoundT etLabelW hLanguageScoreAboveThreshold
 mport com.tw ter.v s b l y.rules.Result.FoundT etLabelW hScoreAboveThreshold
 mport com.tw ter.v s b l y.rules.Result.FoundT etV olat onOfLevel
 mport com.tw ter.v s b l y.rules.Result.FoundT etV olat onOfSo Level
 mport com.tw ter.v s b l y.rules.Result.FoundUserLabel
 mport com.tw ter.v s b l y.rules.Result.FoundUserRole
 mport com.tw ter.v s b l y.rules.Result.HasQueryS ce
 mport com.tw ter.v s b l y.rules.Result.HasT etT  stampAfterCutoff
 mport com.tw ter.v s b l y.rules.Result.HasT etT  stampAfterOffset
 mport com.tw ter.v s b l y.rules.Result.HasT etT  stampBeforeCutoff
 mport com.tw ter.v s b l y.rules.Result.ParamWasTrue
 mport com.tw ter.v s b l y.rules.Result.Result
 mport com.tw ter.v s b l y.rules.Result.Sat sf ed
 mport com.tw ter.v s b l y.rules.Result.Unsat sf ed
 mport com.tw ter.v s b l y.ut l.Nam ngUt ls
 mport com.tw ter.v s b l y.{features => feats}

sealed tra  PreF lterResult
case object F ltered extends PreF lterResult
case object NeedsFullEvaluat on extends PreF lterResult
case object NotF ltered extends PreF lterResult

sealed tra  Cond  on {
  lazy val na : Str ng = Nam ngUt ls.getFr endlyNa (t )
  def features: Set[Feature[_]]
  def opt onalFeatures: Set[Feature[_]]

  def preF lter(
    evaluat onContext: Evaluat onContext,
    featureMap: Map[Feature[_], _]
  ): PreF lterResult = {
     f (features.forall(featureMap.conta ns)) {
       f (apply(evaluat onContext, featureMap).asBoolean) {
        NotF ltered
      } else {
        F ltered
      }
    } else {
      NeedsFullEvaluat on
    }
  }

  def apply(evaluat onContext: Evaluat onContext, featureMap: Map[Feature[_], _]): Result
}

tra  PreF lterOnOpt onalFeatures extends Cond  on {
  overr de def preF lter(
    evaluat onContext: Evaluat onContext,
    featureMap: Map[Feature[_], _]
  ): PreF lterResult =
     f ((features ++ opt onalFeatures).forall(featureMap.conta ns)) {
       f (apply(evaluat onContext, featureMap).asBoolean) {
        NotF ltered
      } else {
        F ltered
      }
    } else {
      NeedsFullEvaluat on
    }
}

tra  HasSafetyLabelType {
  val labelTypes: Set[SafetyLabelType]
  def hasLabelType(labelType: SafetyLabelType): Boolean = labelTypes.conta ns(labelType)
}

sealed tra  HasNestedCond  ons extends HasSafetyLabelType {
  val cond  ons: Seq[Cond  on]
  overr de lazy val labelTypes: Set[SafetyLabelType] = cond  ons
    .collect {
      case lt: HasSafetyLabelType => lt.labelTypes
    }.flatten.toSet
}

object Result {
  sealed tra  Cond  onReason
  case object Found nnerQuotedT et extends Cond  onReason
  case object FoundT etV olat onOfSo Level extends Cond  onReason
  case class FoundT etV olat onOfLevel(level: V olat onLevel) extends Cond  onReason
  case class FoundT etLabel(label: T etSafetyLabelType) extends Cond  onReason
  case class FoundSpaceLabel(label: SpaceSafetyLabelType) extends Cond  onReason
  case class Found d aLabel(label:  d aSafetyLabelType) extends Cond  onReason
  case class FoundT etLabelForPerspect valUser(label: T etSafetyLabelType) extends Cond  onReason
  case class FoundT etLabelW hLanguageScoreAboveThreshold(
    label: T etSafetyLabelType,
    languagesToScoreThresholds: Map[Str ng, Double])
      extends Cond  onReason
  case class FoundT etLabelW hScoreAboveThreshold(label: T etSafetyLabelType, threshold: Double)
      extends Cond  onReason
  case class FoundT etLabelW hLanguage n(
    safetyLabelType: T etSafetyLabelType,
    languages: Set[Str ng])
      extends Cond  onReason
  case class FoundT etSafetyLabelW hPred cate(safetyLabelType: T etSafetyLabelType, na : Str ng)
      extends Cond  onReason
  case class FoundUserLabel(label: UserLabelValue) extends Cond  onReason
  case class FoundMutedKeyword(keyword: Str ng) extends Cond  onReason
  case object HasT etT  stampAfterCutoff extends Cond  onReason
  case object HasT etT  stampAfterOffset extends Cond  onReason
  case object HasT etT  stampBeforeCutoff extends Cond  onReason
  case class  sT etReplyToParentT etBeforeDurat on(durat on: Durat on) extends Cond  onReason
  case class  sT etReplyToRootT etBeforeDurat on(durat on: Durat on) extends Cond  onReason
  case class HasQueryS ce(queryS ce: Thr ftQueryS ce) extends Cond  onReason
  case class FoundUserRole(role: Str ng) extends Cond  onReason
  case class V e r nHrcj(jur sd ct on: Str ng) extends Cond  onReason
  case class V e rOrRequest nCountry(country: Str ng) extends Cond  onReason
  case class V e rAge nYears(age nYears:  nt) extends Cond  onReason
  case object NoV e rAge extends Cond  onReason
  case class ParamWasTrue(param: Param[Boolean]) extends Cond  onReason
  case class FoundCardUr RootDoma n(doma n: Str ng) extends Cond  onReason
  case object Unknown extends Cond  onReason

  sealed tra  Result {
    def asBoolean: Boolean
  }

  val Sat sf edResult: Result = Sat sf ed()

  case class Sat sf ed(reason: Cond  onReason = Unknown) extends Result {
    overr de val asBoolean: Boolean = true
  }

  case class Unsat sf ed(cond  on: Cond  on) extends Result {
    overr de val asBoolean: Boolean = false
  }

  def fromMutedKeyword(mutedKeyword: MutedKeyword, unsat sf ed: Unsat sf ed): Result = {
    mutedKeyword match {
      case MutedKeyword(So (keyword)) => Sat sf ed(FoundMutedKeyword(keyword))
      case _ => unsat sf ed
    }
  }

  case class FoundSpaceLabelW hScoreAboveThreshold(label: SpaceSafetyLabelType, threshold: Double)
      extends Cond  onReason
}

object Cond  on {

  abstract class BooleanFeatureCond  on(feature: Feature[Boolean]) extends Cond  on {
    overr de val features: Set[Feature[_]] = Set(feature)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result =
       f (featureMap(feature).as nstanceOf[Boolean]) {
        Result.Sat sf edResult
      } else {
        Unsat sf edResult
      }
  }

  case class Param sTrue(param: Param[Boolean]) extends Cond  on w h HasParams {
    overr de lazy val na : Str ng = s"Param sTrue(${Nam ngUt ls.getFr endlyNa (param)})"
    overr de val features: Set[Feature[_]] = Set.empty
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult = Unsat sf ed(t )
    pr vate val Sat sf edResult = Sat sf ed(ParamWasTrue(param))

    overr de val params: Set[Param[_]] = Set(param)

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result =
       f (evaluat onContext.params(param)) {
        Sat sf edResult
      } else {
        Unsat sf edResult
      }
  }

  case object Never extends Cond  on {
    overr de lazy val na : Str ng = s"""Never"""
    overr de val features: Set[Feature[_]] = Set.empty
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def preF lter(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): PreF lterResult = {
      NeedsFullEvaluat on
    }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result =
      Unsat sf edResult
  }

  class BooleanCond  on(value: Boolean) extends Cond  on {
    overr de lazy val na : Str ng = s"""${ f (value) "True" else "False"}"""
    overr de val features: Set[Feature[_]] = Set.empty
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result =
      value match {
        case true => Result.Sat sf edResult
        case false => Unsat sf edResult
      }
  }

  case object True extends BooleanCond  on(true)
  case object False extends BooleanCond  on(false)

  abstract class ContentTakendown nV e rCountry(takedownFeature: Feature[Seq[TakedownReason]])
      extends Cond  on {
    overr de val features: Set[Feature[_]] = Set(takedownFeature)
    overr de val opt onalFeatures: Set[Feature[_]] = Set(RequestCountryCode)

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val requestCountryCode = featureMap.get(RequestCountryCode).as nstanceOf[Opt on[Str ng]]
      val takedownReasons = featureMap(takedownFeature).as nstanceOf[Seq[TakedownReason]]
       f (TakedownReasonsUt l. sTakenDown n(requestCountryCode, takedownReasons)) {
        Result.Sat sf edResult
      } else {
        Unsat sf edResult
      }
    }
  }

  case object T etTakendown nV e rCountry
      extends ContentTakendown nV e rCountry(T etTakedownReasons)

  case object AuthorTakendown nV e rCountry
      extends ContentTakendown nV e rCountry(AuthorTakedownReasons)

  case object SuspendedAuthor extends BooleanFeatureCond  on(Author sSuspended)

  case object SuspendedV e r extends BooleanFeatureCond  on(V e r sSuspended)

  case object Deact vatedV e r extends BooleanFeatureCond  on(V e r sDeact vated)

  case object Unava lableAuthor extends BooleanFeatureCond  on(Author sUnava lable)

  case object  sVer f edCrawlerV e r extends BooleanFeatureCond  on(Request sVer f edCrawler)

  case object LoggedOutV e r extends Cond  on {
    overr de val features: Set[Feature[_]] = Set.empty
    overr de val opt onalFeatures: Set[Feature[_]] = Set(V e r d)

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result =
       f (featureMap.conta ns(V e r d)) Unsat sf edResult else Result.Sat sf edResult
  }

  case object  sSelfQuote extends Cond  on {
    overr de val features: Set[Feature[_]] = Set(Author d, OuterAuthor d)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val author ds = featureMap(Author d).as nstanceOf[Set[Long]]
      val outerAuthor d = featureMap(OuterAuthor d).as nstanceOf[Long]
       f (author ds.conta ns(outerAuthor d)) {
        Result.Sat sf edResult
      } else {
        Unsat sf edResult
      }
    }
  }

  case object V e r sAuthor extends Cond  on {
    overr de val features: Set[Feature[_]] = Set(Author d)
    overr de val opt onalFeatures: Set[Feature[_]] = Set(V e r d)

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result =
       f (featureMap.conta ns(V e r d)) {
        val author ds = featureMap(Author d).as nstanceOf[Set[Long]]
        val v e r d = featureMap(V e r d).as nstanceOf[Long]
         f (author ds.conta ns(v e r d)) {
          Result.Sat sf edResult
        } else {
          Unsat sf edResult
        }
      } else {
        Unsat sf edResult
      }
  }

  case object NonAuthorV e r extends Cond  on {
    overr de val features: Set[Feature[_]] = Set(Author d)
    overr de val opt onalFeatures: Set[Feature[_]] = Set(V e r d)

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result =
       f (featureMap.conta ns(V e r d)) {
        val author ds = featureMap(Author d).as nstanceOf[Set[Long]]
        val v e r d = featureMap(V e r d).as nstanceOf[Long]
         f (author ds.conta ns(v e r d)) {
          Unsat sf edResult
        } else {
          Result.Sat sf edResult
        }
      } else {
        Result.Sat sf edResult
      }
  }

  case object V e rFollowsAuthorOfFosnrV olat ngT et
      extends BooleanFeatureCond  on(V e rFollowsAuthorOfV olat ngT et)

  case object V e rDoesNotFollowAuthorOfFosnrV olat ngT et
      extends BooleanFeatureCond  on(V e rDoesNotFollowAuthorOfV olat ngT et)

  case object V e rDoesFollowAuthor extends BooleanFeatureCond  on(V e rFollowsAuthor)

  case object AuthorDoesFollowV e r extends BooleanFeatureCond  on(AuthorFollowsV e r)

  case object AuthorBlocksV e r extends BooleanFeatureCond  on(AuthorBlocksV e rFeature)

  case object V e rBlocksAuthor extends BooleanFeatureCond  on(V e rBlocksAuthorFeature)

  case object V e r sUn nt oned extends BooleanFeatureCond  on(Not f cat on sOnUn nt onedV e r)

  case object AuthorBlocksOuterAuthor
      extends BooleanFeatureCond  on(AuthorBlocksOuterAuthorFeature)

  case object OuterAuthorFollowsAuthor
      extends BooleanFeatureCond  on(OuterAuthorFollowsAuthorFeature)

  case object OuterAuthor s nnerAuthor
      extends BooleanFeatureCond  on(OuterAuthor s nnerAuthorFeature)

  case object V e rMutesAuthor extends BooleanFeatureCond  on(V e rMutesAuthorFeature)

  case object V e rReportsAuthor extends BooleanFeatureCond  on(V e rReportsAuthorAsSpam)
  case object V e rReportsT et extends BooleanFeatureCond  on(V e rReportedT et)

  case object  sQuoted nnerT et extends BooleanFeatureCond  on(T et s nnerQuotedT et)

  case object  sS ceT et extends BooleanFeatureCond  on(T et sS ceT et)

  case object V e rMutesRet etsFromAuthor
      extends BooleanFeatureCond  on(V e rMutesRet etsFromAuthorFeature)

  case object Conversat onRootAuthorDoesFollowV e r
      extends BooleanFeatureCond  on(Conversat onRootAuthorFollowsV e r)

  case object V e rDoesFollowConversat onRootAuthor
      extends BooleanFeatureCond  on(V e rFollowsConversat onRootAuthor)

  case object T et sCommun yT et extends BooleanFeatureCond  on(T et sCommun yT etFeature)

  case object Not f cat on sOnCommun yT et
      extends BooleanFeatureCond  on(Not f cat on sOnCommun yT etFeature)

  sealed tra  Commun yT etCommun yUnava lable extends Cond  on

  case object Commun yT etCommun yNotFound
      extends BooleanFeatureCond  on(Commun yT etCommun yNotFoundFeature)
      w h Commun yT etCommun yUnava lable

  case object Commun yT etCommun yDeleted
      extends BooleanFeatureCond  on(Commun yT etCommun yDeletedFeature)
      w h Commun yT etCommun yUnava lable

  case object Commun yT etCommun ySuspended
      extends BooleanFeatureCond  on(Commun yT etCommun ySuspendedFeature)
      w h Commun yT etCommun yUnava lable

  case object Commun yT etCommun yV s ble
      extends BooleanFeatureCond  on(Commun yT etCommun yV s bleFeature)

  case object V e r s nternalCommun  esAdm n
      extends BooleanFeatureCond  on(V e r s nternalCommun  esAdm nFeature)

  case object V e r sCommun yAdm n extends BooleanFeatureCond  on(V e r sCommun yAdm nFeature)

  case object V e r sCommun yModerator
      extends BooleanFeatureCond  on(V e r sCommun yModeratorFeature)

  case object V e r sCommun y mber
      extends BooleanFeatureCond  on(V e r sCommun y mberFeature)

  sealed tra  Commun yT et sModerated extends Cond  on

  case object Commun yT et sH dden
      extends BooleanFeatureCond  on(Commun yT et sH ddenFeature)
      w h Commun yT et sModerated

  case object Commun yT etAuthor sRemoved
      extends BooleanFeatureCond  on(Commun yT etAuthor sRemovedFeature)
      w h Commun yT et sModerated

  case object DoesHave nnerC rcleOfFr endsRelat onsh p
      extends BooleanFeatureCond  on(Has nnerC rcleOfFr endsRelat onsh p)

  case object T et sCommun yConversat on
      extends BooleanFeatureCond  on(T etHasCommun yConversat onControl)

  case object T et sBy nv at onConversat on
      extends BooleanFeatureCond  on(T etHasBy nv at onConversat onControl)

  case object T et sFollo rsConversat on
      extends BooleanFeatureCond  on(T etHasFollo rsConversat onControl)

  case object V e r sT etConversat onRootAuthor
      extends BooleanFeatureCond  on(T etConversat onV e r sRootAuthor)

  pr vate case object V e r s nv edToT etConversat onBy nt on
      extends BooleanFeatureCond  on(T etConversat onV e r s nv ed)

  pr vate case object V e r s nv edToT etConversat onByReply nt on
      extends BooleanFeatureCond  on(T etConversat onV e r s nv edV aReply nt on)

  object V e r s nv edToT etConversat on
      extends Or(
        V e r s nv edToT etConversat onBy nt on,
        V e r s nv edToT etConversat onByReply nt on)

  object T et sExclus veContent extends BooleanFeatureCond  on(T et sExclus veT et)
  object V e r sExclus veT etAuthor
      extends BooleanFeatureCond  on(V e r sExclus veT etRootAuthor)
  object V e rSuperFollowsExclus veT etAuthor
      extends BooleanFeatureCond  on(V e rSuperFollowsExclus veT etRootAuthor)

  object T et sTrustedFr endsContent extends BooleanFeatureCond  on(T et sTrustedFr endT et)
  object V e r sTrustedFr endsT etAuthor
      extends BooleanFeatureCond  on(V e r sTrustedFr endT etAuthor)
  object V e r sTrustedFr end extends BooleanFeatureCond  on(V e r sTrustedFr endOfT etAuthor)

  object T et sCollab nv at onContent
      extends BooleanFeatureCond  on(T et sCollab nv at onT et)

  case class T etHasLabelForPerspect valUser(safetyLabel: T etSafetyLabelType)
      extends Cond  on
      w h HasSafetyLabelType {
    overr de lazy val na : Str ng = s"T etHasLabelForPerspect valUser(${safetyLabel.na })"
    overr de val features: Set[Feature[_]] = Set(T etSafetyLabels)
    overr de val opt onalFeatures: Set[Feature[_]] = Set(V e r d)
    overr de val labelTypes: Set[SafetyLabelType] = Set(safetyLabel)

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(
      FoundT etLabelForPerspect valUser(safetyLabel)
    )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
       f (!featureMap.conta ns(V e r d)) {
        Unsat sf edResult
      } else {
        val v e r d = featureMap(V e r d).as nstanceOf[Long]
        val labels = featureMap(T etSafetyLabels).as nstanceOf[Seq[T etSafetyLabel]]
        labels
          .collectF rst {
            case label
                 f label.labelType == safetyLabel && label.appl cableUsers.conta ns(v e r d)
                  && Exper  ntBase.shouldF lterForS ce(evaluat onContext.params, label.s ce) =>
              Sat sf edResult
          }.getOrElse(Unsat sf edResult)
      }
    }
  }

  case class T etHasLabel(
    safetyLabel: T etSafetyLabelType,
    labelS ceExper  ntPred cate: Opt on[(Params, Opt on[LabelS ce]) => Boolean] = None)
      extends Cond  on
      w h HasSafetyLabelType {
    overr de lazy val na : Str ng = s"T etHasLabel(${safetyLabel.na })"
    overr de val features: Set[Feature[_]] = Set(T etSafetyLabels)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    overr de val labelTypes: Set[SafetyLabelType] = Set(safetyLabel)

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(FoundT etLabel(safetyLabel))

    pr vate val labelS cePred cate: (Params, Opt on[LabelS ce]) => Boolean =
      labelS ceExper  ntPred cate match {
        case So (pred cate) => pred cate
        case _ => Exper  ntBase.shouldF lterForS ce
      }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(T etSafetyLabels).as nstanceOf[Seq[T etSafetyLabel]]
      labels
        .collectF rst {
          case label
               f label.labelType == safetyLabel
                && labelS cePred cate(evaluat onContext.params, label.s ce) =>
            Sat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  case class SpaceHasLabel(
    safetyLabelType: SpaceSafetyLabelType)
      extends Cond  on
      w h HasSafetyLabelType {
    overr de lazy val na : Str ng = s"SpaceHasLabel(${safetyLabelType.na })"
    overr de val features: Set[Feature[_]] = Set(SpaceSafetyLabels)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    overr de val labelTypes: Set[SafetyLabelType] = Set(safetyLabelType)

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(FoundSpaceLabel(safetyLabelType))

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(SpaceSafetyLabels).as nstanceOf[Seq[SpaceSafetyLabel]]
      labels
        .collectF rst {
          case label  f label.safetyLabelType == safetyLabelType =>
            Sat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  case class  d aHasLabel(
    safetyLabelType:  d aSafetyLabelType)
      extends Cond  on
      w h HasSafetyLabelType {
    overr de lazy val na : Str ng = s" d aHasLabel(${safetyLabelType.na })"
    overr de val features: Set[Feature[_]] = Set( d aSafetyLabels)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    overr de val labelTypes: Set[SafetyLabelType] = Set(safetyLabelType)

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(Found d aLabel(safetyLabelType))

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap( d aSafetyLabels).as nstanceOf[Seq[ d aSafetyLabel]]
      labels
        .collectF rst {
          case label  f label.safetyLabelType == safetyLabelType =>
            Sat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  case class T etHasLabelW hLanguageScoreAboveThreshold(
    safetyLabel: T etSafetyLabelType,
    languagesToScoreThresholds: Map[Str ng, Double])
      extends Cond  on
      w h HasSafetyLabelType {

    overr de lazy val na : Str ng =
      s"T etHasLabelW hLanguageScoreAboveThreshold(${safetyLabel.na }, ${languagesToScoreThresholds.toStr ng})"
    overr de val features: Set[Feature[_]] = Set(T etSafetyLabels)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    overr de val labelTypes: Set[SafetyLabelType] = Set(safetyLabel)

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed =
      Sat sf ed(
        FoundT etLabelW hLanguageScoreAboveThreshold(safetyLabel, languagesToScoreThresholds))

    pr vate[t ] def  sAboveThreshold(label: T etSafetyLabel) = {
      val  sAboveThresholdOpt = for {
        model tadata <- label.model tadata
        cal bratedLanguage <- model tadata.cal bratedLanguage
        threshold <- languagesToScoreThresholds.get(cal bratedLanguage)
        score <- label.score
      } y eld score >= threshold

       sAboveThresholdOpt.getOrElse(false)
    }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(T etSafetyLabels).as nstanceOf[Seq[T etSafetyLabel]]
      labels
        .collectF rst {
          case label
               f label.labelType == safetyLabel
                &&  sAboveThreshold(label) =>
            Sat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  case class T etHasLabelW hScoreAboveThreshold(
    safetyLabel: T etSafetyLabelType,
    threshold: Double)
      extends Cond  on
      w h HasSafetyLabelType {

    overr de lazy val na : Str ng =
      s"T etHasLabelW hScoreAboveThreshold(${safetyLabel.na }, $threshold)"
    overr de val features: Set[Feature[_]] = Set(T etSafetyLabels)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    overr de val labelTypes: Set[SafetyLabelType] = Set(safetyLabel)

    pr vate val Unsat sf edResult = Unsat sf ed(t )
    pr vate val Sat sf edResult =
      Sat sf ed(FoundT etLabelW hScoreAboveThreshold(safetyLabel, threshold))

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(T etSafetyLabels).as nstanceOf[Seq[T etSafetyLabel]]
      labels
        .collectF rst {
          case label
               f label.labelType == safetyLabel
                && label.score.ex sts(_ >= threshold) =>
            Sat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  case class T etHasLabelW hScoreAboveThresholdW hParam(
    safetyLabel: T etSafetyLabelType,
    thresholdParam: Param[Double])
      extends Cond  on
      w h HasSafetyLabelType
      w h HasParams {
    overr de lazy val na : Str ng =
      s"T etHasLabelW hScoreAboveThreshold(${safetyLabel.na }, ${Nam ngUt ls.getFr endlyNa (thresholdParam)})"
    overr de val features: Set[Feature[_]] = Set(T etSafetyLabels)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    overr de val labelTypes: Set[SafetyLabelType] = Set(safetyLabel)
    pr vate val Unsat sf edResult = Unsat sf ed(t )
    overr de val params: Set[Param[_]] = Set(thresholdParam)

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(T etSafetyLabels).as nstanceOf[Seq[T etSafetyLabel]]
      val threshold = evaluat onContext.params(thresholdParam)
      val Sat sf edResult =
        Sat sf ed(FoundT etLabelW hScoreAboveThreshold(safetyLabel, threshold))
      labels
        .collectF rst {
          case label
               f label.labelType == safetyLabel
                && label.score.ex sts(_ >= threshold) =>
            Sat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  case class T etHasLabelW hLanguage n(
    safetyLabelType: T etSafetyLabelType,
    languages: Set[Str ng])
      extends Cond  on
      w h HasSafetyLabelType {

    overr de lazy val na : Str ng =
      s"T etHasLabelW hLanguage n($safetyLabelType, $languages)"
    overr de val features: Set[Feature[_]] = Set(T etSafetyLabels)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    overr de val labelTypes: Set[SafetyLabelType] = Set(safetyLabelType)

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed =
      Sat sf ed(FoundT etLabelW hLanguage n(safetyLabelType, languages))

    pr vate[t ] def hasLanguageMatch(label: T etSafetyLabel): Boolean = {
      val  sMatch ngLanguageOpt = for {
         tadata <- label.model tadata
        language <-  tadata.cal bratedLanguage
      } y eld languages.conta ns(language)
       sMatch ngLanguageOpt.getOrElse(false)
    }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap(T etSafetyLabels)
        .as nstanceOf[Seq[T etSafetyLabel]]
        .collectF rst {
          case label  f label.labelType == safetyLabelType && hasLanguageMatch(label) =>
            Sat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  case class T etHasLabelW hLanguagesW hParam(
    safetyLabelType: T etSafetyLabelType,
    languageParam: Param[Seq[Str ng]])
      extends Cond  on
      w h HasSafetyLabelType
      w h HasParams {
    overr de lazy val na : Str ng =
      s"T etHasLabelW hLanguage n($safetyLabelType, ${Nam ngUt ls.getFr endlyNa (languageParam)})"
    overr de val features: Set[Feature[_]] = Set(T etSafetyLabels)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    overr de val labelTypes: Set[SafetyLabelType] = Set(safetyLabelType)
    overr de val params: Set[Param[_]] = Set(languageParam)

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )

    pr vate[t ] def hasLanguageMatch(label: T etSafetyLabel, languages: Set[Str ng]): Boolean = {
      val  sMatch ngLanguageOpt = for {
         tadata <- label.model tadata
        language <-  tadata.cal bratedLanguage
      } y eld languages.conta ns(language)
       sMatch ngLanguageOpt.getOrElse(false)
    }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val languages = evaluat onContext.params(languageParam).toSet
      val Sat sf edResult: Sat sf ed =
        Sat sf ed(FoundT etLabelW hLanguage n(safetyLabelType, languages))
      featureMap(T etSafetyLabels)
        .as nstanceOf[Seq[T etSafetyLabel]]
        .collectF rst {
          case label  f label.labelType == safetyLabelType && hasLanguageMatch(label, languages) =>
            Sat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  type T etSafetyLabelPred cateFn = (T etSafetyLabel) => Boolean
  abstract class Na dT etSafetyLabelPred cate(
    pr vate[rules] val fn: T etSafetyLabelPred cateFn,
    pr vate[rules] val na : Str ng)

  abstract class T etHasSafetyLabelW hPred cate(
    pr vate[rules] val safetyLabelType: T etSafetyLabelType,
    pr vate[rules] val pred cate: Na dT etSafetyLabelPred cate)
      extends Cond  on
      w h HasSafetyLabelType {

    overr de lazy val na : Str ng =
      s"T etHasSafetyLabelW hPred cate(${pred cate.na }($safetyLabelType))"
    overr de val features: Set[Feature[_]] = Set(T etSafetyLabels)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    overr de val labelTypes: Set[SafetyLabelType] = Set(safetyLabelType)

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed =
      Sat sf ed(Result.FoundT etSafetyLabelW hPred cate(safetyLabelType, pred cate.na ))

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap(T etSafetyLabels)
        .as nstanceOf[Seq[T etSafetyLabel]]
        .collectF rst {
          case label  f label.labelType == safetyLabelType && pred cate.fn(label) =>
            Sat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  object T etHasSafetyLabelW hPred cate {
    def unapply(
      cond  on: T etHasSafetyLabelW hPred cate
    ): Opt on[(T etSafetyLabelType, Na dT etSafetyLabelPred cate)] =
      So ((cond  on.safetyLabelType, cond  on.pred cate))
  }

  case class W hScoreEq nt(score:  nt)
      extends Na dT etSafetyLabelPred cate(
        fn = t etSafetyLabel => t etSafetyLabel.score.ex sts(s => s. ntValue() == score),
        na  = "W hScoreEq nt"
      )
  case class T etHasSafetyLabelW hScoreEq nt(
    overr de val safetyLabelType: T etSafetyLabelType,
    score:  nt)
      extends T etHasSafetyLabelW hPred cate(
        safetyLabelType,
        pred cate = W hScoreEq nt(score)
      )

  case class T etReplyToParentT etBeforeDurat on(durat on: Durat on) extends Cond  on {
    overr de val features: Set[Feature[_]] = Set(T etParent d, T etT  stamp)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(
      Result. sT etReplyToParentT etBeforeDurat on(durat on))

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap
        .get(T etParent d).collect {
          case t etParent d: Long =>
            featureMap
              .get(T etT  stamp).collect {
                case t etT  stamp: T  
                     f t etT  stamp.d ff(Snowflake d.t  From d(t etParent d)) < durat on =>
                  Sat sf edResult
              }.getOrElse(Unsat sf edResult)
        }.getOrElse(Unsat sf edResult)
    }
  }

  case class T etReplyToRootT etBeforeDurat on(durat on: Durat on) extends Cond  on {
    overr de val features: Set[Feature[_]] = Set(T etConversat on d, T etT  stamp)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(
      Result. sT etReplyToRootT etBeforeDurat on(durat on))

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap
        .get(T etConversat on d).collect {
          case t etConversat on d: Long =>
            featureMap
              .get(T etT  stamp).collect {
                case t etT  stamp: T  
                     f t etT  stamp.d ff(
                      Snowflake d.t  From d(t etConversat on d)) < durat on =>
                  Sat sf edResult
              }.getOrElse(Unsat sf edResult)
        }.getOrElse(Unsat sf edResult)
    }
  }

  case class T etComposedBefore(cutoffT  stamp: T  ) extends Cond  on {
    assert(cutoffT  stamp. nM ll seconds > Snowflake d.F rstSnowflake dUn xT  )

    overr de val features: Set[Feature[_]] = Set(T etT  stamp)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(HasT etT  stampBeforeCutoff)

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap(T etT  stamp) match {
        case t  stamp: T    f t  stamp > cutoffT  stamp => Unsat sf edResult
        case _ => Sat sf edResult
      }
    }
  }

  case class T etComposedAfter(cutoffT  stamp: T  ) extends Cond  on {
    assert(cutoffT  stamp. nM ll seconds > Snowflake d.F rstSnowflake dUn xT  )

    overr de val features: Set[Feature[_]] = Set(T etT  stamp)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(HasT etT  stampAfterCutoff)

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap(T etT  stamp) match {
        case t  stamp: T    f t  stamp > cutoffT  stamp => Sat sf edResult
        case _ => Unsat sf edResult
      }
    }
  }

  case class T etComposedAfterOffset(offset: Durat on) extends Cond  on {
    overr de val features: Set[Feature[_]] = Set(T etT  stamp)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(HasT etT  stampAfterOffset)

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap(T etT  stamp) match {
        case t  stamp: T    f t  stamp > T  .now.m nus(offset) => Sat sf edResult
        case _ => Unsat sf edResult
      }
    }
  }

  case class T etComposedAfterW hParam(cutoffT  Param: Param[T  ])
      extends Cond  on
      w h HasParams {
    overr de val features: Set[Feature[_]] = Set(T etT  stamp)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    overr de val params: Set[Param[_]] = Set(cutoffT  Param)
    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(HasT etT  stampAfterCutoff)

    overr de def preF lter(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): PreF lterResult = {
      val cutoffT  stamp = evaluat onContext.params(cutoffT  Param)
       f (cutoffT  stamp. nM ll seconds < Snowflake d.F rstSnowflake dUn xT  ) {
        F ltered
      } else {
        super.preF lter(evaluat onContext, featureMap)
      }
    }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val cutoffT  stamp = evaluat onContext.params(cutoffT  Param)
      featureMap(T etT  stamp) match {
        case _: T    f cutoffT  stamp. nM ll seconds < Snowflake d.F rstSnowflake dUn xT   =>
          Unsat sf edResult
        case t  stamp: T    f t  stamp > cutoffT  stamp => Sat sf edResult
        case _ => Unsat sf edResult
      }
    }
  }

  case class AuthorHasLabel(labelValue: UserLabelValue, shortC rcu able: Boolean = true)
      extends Cond  on
      w h HasSafetyLabelType {
    overr de lazy val na : Str ng = s"AuthorHasLabel(${labelValue.na })"
    overr de val features: Set[Feature[_]] = Set(AuthorUserLabels)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    overr de val labelTypes: Set[SafetyLabelType] = Set(labelValue)

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(FoundUserLabel(labelValue))

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(AuthorUserLabels).as nstanceOf[Seq[Label]].map(UserLabel.fromThr ft)
      labels
        .collectF rst {
          case label
               f label.labelValue == labelValue
                && Exper  ntBase.shouldF lterForS ce(evaluat onContext.params, label.s ce) =>
            Sat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  abstract class V e rHasRole(role: Str ng) extends Cond  on {
    overr de lazy val na : Str ng = s"V e rHasRole(${role})"
    overr de val features: Set[Feature[_]] = Set(V e rRoles)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(FoundUserRole(role))

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val roles = featureMap(V e rRoles).as nstanceOf[Seq[Str ng]]
       f (roles.conta ns(role)) {
        Sat sf edResult
      } else {
        Unsat sf edResult
      }
    }
  }

  case object V e r sEmployee extends V e rHasRole(V e rRoles.EmployeeRole)

  case class V e rHasLabel(labelValue: UserLabelValue) extends Cond  on w h HasSafetyLabelType {
    overr de lazy val na : Str ng = s"V e rHasLabel(${labelValue.na })"
    overr de val features: Set[Feature[_]] = Set(V e rUserLabels)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    overr de val labelTypes: Set[SafetyLabelType] = Set(labelValue)

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(FoundUserLabel(labelValue))

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(V e rUserLabels).as nstanceOf[Seq[Label]].map(UserLabel.fromThr ft)
      labels
        .collectF rst {
          case label
               f label.labelValue == labelValue
                && Exper  ntBase.shouldF lterForS ce(evaluat onContext.params, label.s ce) =>
            Sat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  case object Deact vatedAuthor extends BooleanFeatureCond  on(Author sDeact vated)
  case object ErasedAuthor extends BooleanFeatureCond  on(Author sErased)
  case object OffboardedAuthor extends BooleanFeatureCond  on(Author sOffboarded)
  case object ProtectedAuthor extends BooleanFeatureCond  on(Author sProtected)
  case object Ver f edAuthor extends BooleanFeatureCond  on(Author sVer f ed)
  case object NsfwUserAuthor extends BooleanFeatureCond  on(Author sNsfwUser)
  case object NsfwAdm nAuthor extends BooleanFeatureCond  on(Author sNsfwAdm n)
  case object T etHasNsfwUserAuthor extends BooleanFeatureCond  on(T etHasNsfwUser)
  case object T etHasNsfwAdm nAuthor extends BooleanFeatureCond  on(T etHasNsfwAdm n)
  case object T etHas d a extends BooleanFeatureCond  on(T etHas d aFeature)
  case object T etHasDmca d a extends BooleanFeatureCond  on(HasDmca d aFeature)
  case object T etHasCard extends BooleanFeatureCond  on(T etHasCardFeature)
  case object  sPollCard extends BooleanFeatureCond  on(Card sPoll)

  case object ProtectedV e r extends BooleanFeatureCond  on(V e r sProtected)
  case object SoftV e r extends BooleanFeatureCond  on(V e r sSoftUser)

  case object V e rHasUqfEnabled
      extends BooleanFeatureCond  on(V e rHasUn versalQual yF lterEnabled)

  abstract class V e rHasMatch ngKeywordFor(muteSurface: MuteSurface) extends Cond  on {
    overr de def features: Set[Feature[_]] = Set(feature)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    pr vate val feature: Feature[MutedKeyword] = muteSurface match {
      case MuteSurface.Ho T  l ne => V e rMutesKeyword nT etForHo T  l ne
      case MuteSurface.Not f cat ons => V e rMutesKeyword nT etForNot f cat ons
      case MuteSurface.T etRepl es => V e rMutesKeyword nT etForT etRepl es

      case _ => throw new NoSuchEle ntExcept on(muteSurface.toStr ng)
    }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val mutedKeyword = featureMap(feature)
        .as nstanceOf[MutedKeyword]
      Result.fromMutedKeyword(mutedKeyword, Unsat sf edResult)
    }
  }

  case object V e rHasMatch ngKeywordForHo T  l ne
      extends V e rHasMatch ngKeywordFor(MuteSurface.Ho T  l ne)

  case object V e rHasMatch ngKeywordForNot f cat ons
      extends V e rHasMatch ngKeywordFor(MuteSurface.Not f cat ons)

  case object V e rHasMatch ngKeywordForT etRepl es
      extends V e rHasMatch ngKeywordFor(MuteSurface.T etRepl es)

  case object V e rHasMatch ngKeywordForAllSurfaces extends Cond  on {
    overr de def features: Set[Feature[_]] = Set(V e rMutesKeyword nT etForAllSurfaces)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val mutedKeyword = featureMap(V e rMutesKeyword nT etForAllSurfaces)
        .as nstanceOf[MutedKeyword]
      Result.fromMutedKeyword(mutedKeyword, Unsat sf edResult)
    }
  }

  abstract class V e rHasMatch ngKeyword nSpaceT leFor(muteSurface: MuteSurface)
      extends Cond  on {
    overr de def features: Set[Feature[_]] = Set(feature)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    pr vate val feature: Feature[MutedKeyword] = muteSurface match {
      case MuteSurface.Not f cat ons => V e rMutesKeyword nSpaceT leForNot f cat ons
      case _ => throw new NoSuchEle ntExcept on(muteSurface.toStr ng)
    }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val mutedKeyword = featureMap(feature)
        .as nstanceOf[MutedKeyword]
      Result.fromMutedKeyword(mutedKeyword, Unsat sf edResult)
    }
  }

  case object V e rHasMatch ngKeyword nSpaceT leForNot f cat ons
      extends V e rHasMatch ngKeyword nSpaceT leFor(MuteSurface.Not f cat ons)

  case object V e rF ltersNoConf r dEma l
      extends BooleanFeatureCond  on(
        com.tw ter.v s b l y.features.V e rF ltersNoConf r dEma l
      )

  case object V e rF ltersNoConf r dPhone
      extends BooleanFeatureCond  on(
        com.tw ter.v s b l y.features.V e rF ltersNoConf r dPhone
      )

  case object V e rF ltersDefaultProf le mage
      extends BooleanFeatureCond  on(
        com.tw ter.v s b l y.features.V e rF ltersDefaultProf le mage
      )

  case object V e rF ltersNewUsers
      extends BooleanFeatureCond  on(
        com.tw ter.v s b l y.features.V e rF ltersNewUsers
      )

  case object V e rF ltersNotFollo dBy
      extends BooleanFeatureCond  on(
        com.tw ter.v s b l y.features.V e rF ltersNotFollo dBy
      )

  case object AuthorHasConf r dEma l
      extends BooleanFeatureCond  on(
        com.tw ter.v s b l y.features.AuthorHasConf r dEma l
      )

  case object AuthorHasVer f edPhone
      extends BooleanFeatureCond  on(
        com.tw ter.v s b l y.features.AuthorHasVer f edPhone
      )

  case object AuthorHasDefaultProf le mage
      extends BooleanFeatureCond  on(
        com.tw ter.v s b l y.features.AuthorHasDefaultProf le mage
      )

  case object Author sNewAccount extends Cond  on {
    overr de val features: Set[Feature[_]] = Set(AuthorAccountAge)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val age = featureMap(AuthorAccountAge).as nstanceOf[Durat on]

       f (age < 72.h s) {
        Result.Sat sf edResult
      } else {
        Unsat sf edResult
      }
    }
  }

  abstract class V e r nJur sd ct on extends Cond  on {
    overr de def features: Set[Feature[_]] = Set.empty
    overr de val opt onalFeatures: Set[Feature[_]] = Set(RequestCountryCode, V e rCountryCode)

    protected val unsat sf edResult = Unsat sf ed(t )

    protected case class CountryFeatures(
      requestCountryCode: Opt on[Str ng],
      v e rCountryCode: Opt on[Str ng])

    def getCountryFeatures(featureMap: Map[Feature[_], _]): CountryFeatures = {
      val requestCountryCodeOpt = featureMap
        .get(RequestCountryCode)
        .map(_.as nstanceOf[Str ng])
      val v e rCountryCodeOpt = featureMap
        .get(V e rCountryCode)
        .map(_.as nstanceOf[Str ng])

      CountryFeatures(requestCountryCodeOpt, v e rCountryCodeOpt)
    }
  }

  case class V e r nHrcj(jur sd ct ons: Set[Str ng]) extends V e r nJur sd ct on {

    overr de def preF lter(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): PreF lterResult =
      featureMap
        .get(RequestCountryCode)
        .map(_.as nstanceOf[Str ng])
        .collectF rst {
          case rcc  f jur sd ct ons.conta ns(rcc) => NeedsFullEvaluat on
        }
        .getOrElse(F ltered)

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val countryFeatures = getCountryFeatures(featureMap)

      countryFeatures match {
        case CountryFeatures(So (rcc), So (vcc))
             f jur sd ct ons.conta ns(rcc) && vcc.equals(rcc) =>
          Sat sf ed(Result.V e r nHrcj(rcc))
        case _ => unsat sf edResult
      }
    }
  }

  case class V e rOrRequest nJur sd ct on(enabledCountr esParam: Param[Seq[Str ng]])
      extends V e r nJur sd ct on
      w h HasParams
      w h PreF lterOnOpt onalFeatures {

    overr de val params: Set[Param[_]] = Set(enabledCountr esParam)

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val countr es: Seq[Str ng] =
        evaluat onContext.params(enabledCountr esParam).map(c => c.toLo rCase)
      val countryFeatures = getCountryFeatures(featureMap)

      val countryCodeOpt =
        countryFeatures.v e rCountryCode.orElse(countryFeatures.requestCountryCode)

      countryCodeOpt match {
        case So (countryCode)  f countr es.conta ns(countryCode) =>
          Sat sf ed(Result.V e rOrRequest nCountry(countryCode))
        case _ => unsat sf edResult
      }
    }
  }

  case class V e rAge nYearsGte(ageToCompare:  nt,  gnoreEmptyAge: Boolean = false)
      extends Cond  on
      w h PreF lterOnOpt onalFeatures {
    overr de def features: Set[Feature[_]] = Set.empty
    overr de def opt onalFeatures: Set[Feature[_]] = Set(V e rAge)

    pr vate val unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result =
      featureMap
        .get(V e rAge)
        .map(_.as nstanceOf[UserAge])
        .collectF rst {
          case UserAge(So (age))  f age >= ageToCompare =>
            Sat sf ed(Result.V e rAge nYears(age))
          case UserAge(None)  f  gnoreEmptyAge =>
            Sat sf ed(Result.NoV e rAge)
        }
        .getOrElse(unsat sf edResult)
  }

  case class UnderageV e r(ageToCompare:  nt) extends Cond  on w h PreF lterOnOpt onalFeatures {
    overr de def features: Set[Feature[_]] = Set.empty
    overr de def opt onalFeatures: Set[Feature[_]] = Set(V e rAge)

    pr vate val unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result =
      featureMap
        .get(V e rAge)
        .map(_.as nstanceOf[UserAge])
        .collectF rst {
          case UserAge(So (age))  f age < ageToCompare => Sat sf ed(Result.V e rAge nYears(age))
        }
        .getOrElse(unsat sf edResult)
  }

  case object V e rM ss ngAge extends Cond  on w h PreF lterOnOpt onalFeatures {
    overr de def features: Set[Feature[_]] = Set.empty
    overr de def opt onalFeatures: Set[Feature[_]] = Set(V e rAge)

    pr vate val unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result =
      featureMap
        .get(V e rAge)
        .map(_.as nstanceOf[UserAge])
        .collectF rst {
          case UserAge(None) => Sat sf ed(Result.NoV e rAge)
        }
        .getOrElse(unsat sf edResult)
  }

  case object V e rOpt nBlock ngOnSearch extends BooleanFeatureCond  on(V e rOpt nBlock ng)
  case object V e rOpt nF lter ngOnSearch extends BooleanFeatureCond  on(V e rOpt nF lter ng)
  case object SelfReply extends BooleanFeatureCond  on(T et sSelfReply)
  case object Nullcast extends BooleanFeatureCond  on(T et sNullcast)
  case object Moderated extends BooleanFeatureCond  on(T et sModerated)
  case object Ret et extends BooleanFeatureCond  on(T et sRet et)

  case object  sF rstPageSearchResult extends Cond  on {
    overr de val features: Set[Feature[_]] = Set(SearchResultsPageNumber)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val searchResultsPageNumber = featureMap(SearchResultsPageNumber).as nstanceOf[ nt]
       f (searchResultsPageNumber == 1) {
        Result.Sat sf edResult
      } else {
        Unsat sf edResult
      }
    }
  }

  case object HasSearchCand dateCountGreaterThan45 extends Cond  on {
    overr de val features: Set[Feature[_]] = Set(SearchCand dateCount)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val searchCand dateCount = featureMap(SearchCand dateCount).as nstanceOf[ nt]
       f (searchCand dateCount > 45) {
        Result.Sat sf edResult
      } else {
        Unsat sf edResult
      }
    }
  }

  abstract class HasSearchQueryS ce(queryS ceToMatch: Thr ftQueryS ce) extends Cond  on {
    overr de lazy val na : Str ng = s"HasSearchQueryS ce(${queryS ceToMatch})"
    overr de val features: Set[Feature[_]] = Set(SearchQueryS ce)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    pr vate val Unsat sf edResult = Unsat sf ed(t )
    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(HasQueryS ce(queryS ceToMatch))

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val queryS ce = featureMap(SearchQueryS ce).as nstanceOf[Thr ftQueryS ce]
       f (queryS ceToMatch.equals(queryS ce)) {
        Sat sf edResult
      } else {
        Unsat sf edResult
      }
    }
  }

  case object  sTrendCl ckS ceSearchResult extends Cond  on {
    overr de val features: Set[Feature[_]] = Set(SearchQueryS ce)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    pr vate val Unsat sf edResult = Unsat sf ed(t )

    pr vate def c ckQueryS ce[T](
      featureMap: Map[Feature[_], _],
      nonTrendS ceResult: T,
      trendS ceResult: T
    ): T = {
      val searchResultsPageNumber = featureMap(SearchQueryS ce).as nstanceOf[Thr ftQueryS ce]
       f (searchResultsPageNumber == Thr ftQueryS ce.TrendCl ck) {
        trendS ceResult
      } else {
        nonTrendS ceResult
      }
    }

    overr de def preF lter(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): PreF lterResult =
      c ckQueryS ce(featureMap, F ltered, NotF ltered)

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result =
      c ckQueryS ce(featureMap, Unsat sf edResult, Result.Sat sf edResult)
  }
  case object  sSearchHashtagCl ck extends HasSearchQueryS ce(Thr ftQueryS ce.HashtagCl ck)
  case object  sSearchTrendCl ck extends HasSearchQueryS ce(Thr ftQueryS ce.TrendCl ck)

  case object SearchQueryHasUser
      extends BooleanFeatureCond  on(com.tw ter.v s b l y.features.SearchQueryHasUser)

  case class Equals[T](feature: Feature[T], value: T) extends Cond  on {

    overr de def features: Set[Feature[_]] = Set(feature)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Sat sf edResult: Result = Sat sf ed()
    pr vate val Unsat sf edResult: Result = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val featureValue = featureMap(feature).as nstanceOf[T]
       f (featureValue.equals(value)) Sat sf edResult else Unsat sf edResult
    }
  }

  case class FeatureEquals[T](left: Feature[T], r ght: Feature[T]) extends Cond  on {

    overr de def features: Set[Feature[_]] = Set.empty
    overr de val opt onalFeatures: Set[Feature[_]] = Set(left, r ght)

    pr vate val Sat sf edResult: Result = Sat sf ed()
    pr vate val Unsat sf edResult: Result = Unsat sf ed(t )

    overr de def preF lter(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): PreF lterResult = {
       f (featureMap.conta ns(left) && featureMap.conta ns(r ght)) {
         f (apply(evaluat onContext, featureMap).asBoolean) {
          NotF ltered
        } else {
          F ltered
        }
      } else {
        NeedsFullEvaluat on
      }
    }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
       f (featureMap.conta ns(left) && featureMap.conta ns(r ght)) {
        val leftValue = featureMap(left).as nstanceOf[T]
        val r ghtValue = featureMap(r ght).as nstanceOf[T]
         f (leftValue.equals(r ghtValue)) Sat sf edResult else Unsat sf edResult
      } else {
        Unsat sf edResult
      }
    }
  }

  case class And(overr de val cond  ons: Cond  on*)
      extends Cond  on
      w h HasNestedCond  ons
      w h HasParams {
    overr de lazy val na : Str ng = s"(${cond  ons.map(_.na ).mkStr ng(" And ")})"
    overr de val features: Set[Feature[_]] = cond  ons.flatMap(_.features).toSet
    overr de val opt onalFeatures: Set[Feature[_]] = cond  ons.flatMap(_.opt onalFeatures).toSet
    overr de val params: Set[Param[_]] =
      cond  ons.collect { case p: HasParams => p.params }.flatten.toSet

    overr de def preF lter(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): PreF lterResult = {
      cond  ons.foldLeft(NotF ltered: PreF lterResult) {
        case (NotF ltered, cond  on) => cond  on.preF lter(evaluat onContext, featureMap)
        case (F ltered, _) => F ltered
        case (NeedsFullEvaluat on, cond  on) => {
          cond  on.preF lter(evaluat onContext, featureMap) match {
            case F ltered => F ltered
            case _ => NeedsFullEvaluat on
          }
        }
      }
    }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      cond  ons.foldLeft(Result.Sat sf edResult) {
        case (result @ Unsat sf ed(_), _) => result
        case (Result.Sat sf edResult, cond  on) => cond  on.apply(evaluat onContext, featureMap)
        case (result @ Sat sf ed(_), cond  on) => {
          cond  on.apply(evaluat onContext, featureMap) match {
            case r @ Unsat sf ed(_) => r
            case _ => result
          }
        }
      }
    }
  }

  case class Or(overr de val cond  ons: Cond  on*)
      extends Cond  on
      w h HasNestedCond  ons
      w h HasParams {
    overr de lazy val na : Str ng = s"(${cond  ons.map(_.na ).mkStr ng(" Or ")})"
    overr de val features: Set[Feature[_]] = cond  ons.flatMap(_.features).toSet
    overr de val opt onalFeatures: Set[Feature[_]] = cond  ons.flatMap(_.opt onalFeatures).toSet
    overr de val params: Set[Param[_]] =
      cond  ons.collect { case p: HasParams => p.params }.flatten.toSet

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def preF lter(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): PreF lterResult = {
      cond  ons.foldLeft(F ltered: PreF lterResult) {
        case (F ltered, c) => c.preF lter(evaluat onContext, featureMap)
        case (NotF ltered, _) => NotF ltered
        case (NeedsFullEvaluat on, c) => {
          c.preF lter(evaluat onContext, featureMap) match {
            case NotF ltered => NotF ltered
            case _ => NeedsFullEvaluat on
          }
        }
      }
    }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val foundSat sf edCond  on =
        cond  ons.f nd(_.apply(evaluat onContext, featureMap).asBoolean)
       f (foundSat sf edCond  on. sDef ned) {
        Result.Sat sf edResult
      } else {
        Unsat sf edResult
      }
    }
  }

  case class Not(cond  on: Cond  on) extends Cond  on w h HasNestedCond  ons w h HasParams {
    overr de lazy val na : Str ng = s"Not(${cond  on.na })"
    overr de val features: Set[Feature[_]] = cond  on.features
    overr de val opt onalFeatures: Set[Feature[_]] = cond  on.opt onalFeatures
    overr de val cond  ons: Seq[Cond  on] = Seq(cond  on)
    overr de val params: Set[Param[_]] =
      cond  ons.collect { case p: HasParams => p.params }.flatten.toSet

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def preF lter(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): PreF lterResult =
      cond  on.preF lter(evaluat onContext, featureMap) match {
        case F ltered => NotF ltered
        case NotF ltered => F ltered
        case _ => NeedsFullEvaluat on
      }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result =
       f (cond  on(evaluat onContext, featureMap).asBoolean) {
        Unsat sf edResult
      } else {
        Result.Sat sf edResult
      }
  }

  val LoggedOutOrV e rNotFollow ngAuthor: And =
    And(NonAuthorV e r, Or(LoggedOutV e r, Not(V e rDoesFollowAuthor)))

  val LoggedOutOrV e rOpt nF lter ng: Or =
    Or(LoggedOutV e r, V e rOpt nF lter ngOnSearch)

  val Logged nV e r: Not = Not(LoggedOutV e r)

  val OuterAuthorNotFollow ngAuthor: And =
    And(Not(OuterAuthor s nnerAuthor), Not(OuterAuthorFollowsAuthor))

  val  sFocalT et: FeatureEquals[Long] = FeatureEquals(T et d, FocalT et d)

  val NonHydrat ngCond  ons: Set[Class[_]] = Set(
    LoggedOutV e r,
    NonAuthorV e r,
    True,
    T etComposedAfter(T  .now),
    T etComposedBefore(T  .now)
  ).map(_.getClass)

  tra  HasParams {
    val params: Set[Param[_]]
  }

  def hasLabelCond  on(cond  on: Cond  on, t etSafetyLabelType: T etSafetyLabelType): Boolean =
    cond  on match {
      case lt: HasSafetyLabelType =>
        lt.hasLabelType(t etSafetyLabelType)
      case _ => false
    }

  def hasLabelCond  on(cond  on: Cond  on, userLabelValue: UserLabelValue): Boolean =
    cond  on match {
      case lt: HasSafetyLabelType =>
        lt.hasLabelType(userLabelValue)
      case _ => false
    }

  def hasLabelCond  on(cond  on: Cond  on, spaceSafetyLabelType: SpaceSafetyLabelType): Boolean =
    cond  on match {
      case lt: HasSafetyLabelType =>
        lt.hasLabelType(spaceSafetyLabelType)
      case _ => false
    }

  def hasLabelCond  on(cond  on: Cond  on,  d aSafetyLabelType:  d aSafetyLabelType): Boolean =
    cond  on match {
      case lt: HasSafetyLabelType =>
        lt.hasLabelType( d aSafetyLabelType)
      case _ => false
    }

  case class Choose[T](
    cond  onMap: Map[T, Cond  on],
    defaultCond  on: Cond  on,
    cho ceParam: Param[T])
      extends Cond  on
      w h HasNestedCond  ons
      w h HasParams {
    overr de lazy val na : Str ng =
      s"(E  r ${cond  onMap.values.map(_.na ).mkStr ng(", ")} or ${defaultCond  on.na })"
    overr de val features: Set[Feature[_]] =
      cond  onMap.values.flatMap(_.features).toSet ++ defaultCond  on.features
    overr de val opt onalFeatures: Set[Feature[_]] =
      cond  onMap.values.flatMap(_.opt onalFeatures).toSet ++ defaultCond  on.opt onalFeatures
    overr de val cond  ons: Seq[Cond  on] = cond  onMap.values.toSeq :+ defaultCond  on
    overr de val params: Set[Param[_]] =
      cond  ons.collect { case p: HasParams => p.params }.flatten.toSet

    pr vate[t ] def getCond  on(evaluat onContext: Evaluat onContext): Cond  on =
      cond  onMap.getOrElse(evaluat onContext.params(cho ceParam), defaultCond  on)

    overr de def preF lter(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): PreF lterResult =
      getCond  on(evaluat onContext).preF lter(evaluat onContext, featureMap)

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result =
      getCond  on(evaluat onContext)(evaluat onContext, featureMap)
  }

  case class  fElse(
    branch ngCond  on: Cond  on,
     fTrueCond  on: Cond  on,
     fFalseCond  on: Cond  on)
      extends Cond  on
      w h HasNestedCond  ons
      w h HasParams {
    overr de lazy val na : Str ng =
      s"( f ${branch ngCond  on.na } T n ${ fTrueCond  on.na } Else ${ fFalseCond  on.na })"
    overr de val features: Set[Feature[_]] =
      branch ngCond  on.features ++  fTrueCond  on.features ++  fFalseCond  on.features
    overr de val opt onalFeatures: Set[Feature[_]] =
      branch ngCond  on.opt onalFeatures ++  fTrueCond  on.opt onalFeatures ++  fFalseCond  on.opt onalFeatures
    overr de val cond  ons: Seq[Cond  on] =
      Seq(branch ngCond  on,  fTrueCond  on,  fFalseCond  on)
    overr de val params: Set[Param[_]] =
      cond  ons.collect { case p: HasParams => p.params }.flatten.toSet

    overr de def preF lter(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): PreF lterResult =
      branch ngCond  on.preF lter(evaluat onContext, featureMap) match {
        case F ltered =>
           fFalseCond  on.preF lter(evaluat onContext, featureMap)
        case NotF ltered =>
           fTrueCond  on.preF lter(evaluat onContext, featureMap)
        case _ =>
          NeedsFullEvaluat on
      }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result =
       f (branch ngCond  on(evaluat onContext, featureMap).asBoolean) {
         fTrueCond  on(evaluat onContext, featureMap)
      } else {
         fFalseCond  on(evaluat onContext, featureMap)
      }
  }

  case class GatedAlternate[T](
    defaultCond  on: Cond  on,
    alternateCond  ons: Map[T, Cond  on],
    bucket dent f erToUseOnD sagree ntParam: Param[Opt on[T]])
      extends Cond  on
      w h HasNestedCond  ons
      w h HasParams {

    overr de lazy val na : Str ng =
      s"(${defaultCond  on.na } or so t  s ${alternateCond  ons.values.map(_.na ).mkStr ng(" or ")})"

    overr de val features: Set[Feature[_]] =
      defaultCond  on.features ++ alternateCond  ons.values.flatMap(_.features)

    overr de val opt onalFeatures: Set[Feature[_]] =
      defaultCond  on.opt onalFeatures ++ alternateCond  ons.values.flatMap(_.opt onalFeatures)

    overr de val cond  ons: Seq[Cond  on] = Seq(defaultCond  on) ++ alternateCond  ons.values

    overr de val params: Set[Param[_]] =
      cond  ons.collect { case p: HasParams => p.params }.flatten.toSet

    overr de def preF lter(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): PreF lterResult =
       f (defaultCond  on.preF lter(evaluat onContext, featureMap) == F ltered &&
        alternateCond  ons.values.forall(_.preF lter(evaluat onContext, featureMap) == F ltered)) {
        F ltered
      } else  f (defaultCond  on.preF lter(evaluat onContext, featureMap) == NotF ltered &&
        alternateCond  ons.values.forall(
          _.preF lter(evaluat onContext, featureMap) == NotF ltered)) {
        NotF ltered
      } else {
        NeedsFullEvaluat on
      }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val defaultCond  onResult: Result = defaultCond  on(evaluat onContext, featureMap)
      val alternateCond  onResult: Map[T, Result] =
        alternateCond  ons.mapValues(_(evaluat onContext, featureMap))

       f (alternateCond  onResult.values.ex sts(_.asBoolean != defaultCond  onResult.asBoolean)) {
        evaluat onContext.params(bucket dent f erToUseOnD sagree ntParam) match {
          case So (bucket)  f alternateCond  onResult.conta ns(bucket) =>
            alternateCond  onResult(bucket)
          case _ =>
            defaultCond  onResult
        }
      } else {
        defaultCond  onResult
      }
    }
  }

  case class EnumGatedAlternate[E <: Enu rat on](
    defaultCond  on: Cond  on,
    alternateCond  ons: Map[E#Value, Cond  on],
    bucket dent f erToUseOnD sagree ntParam: EnumParam[E])
      extends Cond  on
      w h HasNestedCond  ons
      w h HasParams {

    overr de lazy val na : Str ng =
      s"(${defaultCond  on.na } or so t  s ${alternateCond  ons.values.map(_.na ).mkStr ng(" or ")})"

    overr de val features: Set[Feature[_]] =
      defaultCond  on.features ++ alternateCond  ons.values.flatMap(_.features)

    overr de val opt onalFeatures: Set[Feature[_]] =
      defaultCond  on.opt onalFeatures ++ alternateCond  ons.values.flatMap(_.opt onalFeatures)

    overr de val cond  ons: Seq[Cond  on] = Seq(defaultCond  on) ++ alternateCond  ons.values

    overr de val params: Set[Param[_]] =
      cond  ons
        .collect {
          case p: HasParams => p.params
        }.flatten.toSet + bucket dent f erToUseOnD sagree ntParam

    overr de def preF lter(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): PreF lterResult =
       f (defaultCond  on.preF lter(evaluat onContext, featureMap) == F ltered &&
        alternateCond  ons.values.forall(_.preF lter(evaluat onContext, featureMap) == F ltered)) {
        F ltered
      } else  f (defaultCond  on.preF lter(evaluat onContext, featureMap) == NotF ltered &&
        alternateCond  ons.values.forall(
          _.preF lter(evaluat onContext, featureMap) == NotF ltered)) {
        NotF ltered
      } else {
        NeedsFullEvaluat on
      }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val defaultCond  onResult: Result = defaultCond  on(evaluat onContext, featureMap)
      val alternateCond  onResult: Map[E#Value, Result] =
        alternateCond  ons.mapValues(_(evaluat onContext, featureMap))

       f (alternateCond  onResult.values.ex sts(_.asBoolean != defaultCond  onResult.asBoolean)) {
        evaluat onContext.params(bucket dent f erToUseOnD sagree ntParam) match {
          case bucket  f alternateCond  onResult.conta ns(bucket) =>
            alternateCond  onResult(bucket)
          case _ =>
            defaultCond  onResult
        }
      } else {
        defaultCond  onResult
      }
    }
  }

  case object  sTestT et extends Cond  on {
    overr de val features: Set[Feature[_]] = Set(T et d)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
       f (!featureMap.conta ns(T et d)) {
        Unsat sf edResult
      } else {
        Result.Sat sf edResult
      }
    }
  }

  case object  sT et nT etLevelStcmHoldback extends Cond  on {
    overr de val features: Set[Feature[_]] = Set(T et d)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val t et d: Long = featureMap(T et d).as nstanceOf[Long]
       f (StcmT etHoldback. sT et nHoldback(t et d)) {
        Result.Sat sf edResult
      } else {
        Unsat sf edResult
      }
    }
  }

  case object  d aRestr cted nV e rCountry extends Cond  on {
    overr de val features: Set[Feature[_]] =
      Set( d aGeoRestr ct onsAllowL st,  d aGeoRestr ct onsDenyL st)
    overr de val opt onalFeatures: Set[Feature[_]] = Set(RequestCountryCode)

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val requestCountryCode = TakedownReasons.normal zeCountryCodeOpt on(
        featureMap.get(RequestCountryCode).as nstanceOf[Opt on[Str ng]])
      val allowl stCountryCodes =
        featureMap( d aGeoRestr ct onsAllowL st).as nstanceOf[Seq[Str ng]]
      val denyl stCountryCodes =
        featureMap( d aGeoRestr ct onsDenyL st).as nstanceOf[Seq[Str ng]]
       f ((allowl stCountryCodes.nonEmpty && !allowl stCountryCodes.conta ns(requestCountryCode))
        || denyl stCountryCodes.conta ns(requestCountryCode)) {
        Result.Sat sf edResult
      } else {
        Unsat sf edResult
      }
    }
  }

  case object OneToOneDmConversat on
      extends BooleanFeatureCond  on(DmConversat on sOneToOneConversat on)

  case object DmConversat onT  l ne sEmpty
      extends BooleanFeatureCond  on(DmConversat onHasEmptyT  l ne)

  case object DmConversat onLastReadableEvent d sVal d
      extends BooleanFeatureCond  on(DmConversat onHasVal dLastReadableEvent d)

  case object V e r sDmConversat onPart c pant
      extends BooleanFeatureCond  on(feats.V e r sDmConversat onPart c pant)

  case object DmConversat on nfoEx sts
      extends BooleanFeatureCond  on(feats.DmConversat on nfoEx sts)

  case object DmConversat onT  l neEx sts
      extends BooleanFeatureCond  on(feats.DmConversat onT  l neEx sts)

  case object DmEvent sBeforeLastClearedEvent
      extends BooleanFeatureCond  on(DmEventOccurredBeforeLastClearedEvent)

  case object DmEvent sBeforeJo nConversat onEvent
      extends BooleanFeatureCond  on(DmEventOccurredBeforeJo nConversat onEvent)

  case object DmEvent sDeleted extends BooleanFeatureCond  on(feats.DmEvent sDeleted)

  case object DmEvent sH dden extends BooleanFeatureCond  on(feats.DmEvent sH dden)

  case object V e r sDmEvent n  at ngUser
      extends BooleanFeatureCond  on(feats.V e r sDmEvent n  at ngUser)

  case object DmEvent nOneToOneConversat onW hUnava lableUser
      extends BooleanFeatureCond  on(feats.DmEvent nOneToOneConversat onW hUnava lableUser)

  case object DmEvent nOneToOneConversat on
      extends BooleanFeatureCond  on(feats.DmEvent nOneToOneConversat on)

  case object  ssageCreateDmEvent extends BooleanFeatureCond  on(DmEvent s ssageCreateEvent)

  case object  lco  ssageCreateDmEvent
      extends BooleanFeatureCond  on(DmEvent s lco  ssageCreateEvent)

  case object Last ssageReadUpdateDmEvent
      extends BooleanFeatureCond  on(DmEvent sLast ssageReadUpdateEvent)

  case object Jo nConversat onDmEvent
      extends BooleanFeatureCond  on(DmEvent sJo nConversat onEvent)

  case object Conversat onCreateDmEvent
      extends BooleanFeatureCond  on(DmEvent sConversat onCreateEvent)

  case object TrustConversat onDmEvent
      extends BooleanFeatureCond  on(DmEvent sTrustConversat onEvent)

  case object CsFeedbackSubm tedDmEvent
      extends BooleanFeatureCond  on(DmEvent sCsFeedbackSubm ted)

  case object CsFeedbackD sm ssedDmEvent
      extends BooleanFeatureCond  on(DmEvent sCsFeedbackD sm ssed)

  case object Perspect valJo nConversat onDmEvent
      extends BooleanFeatureCond  on(feats.DmEvent sPerspect valJo nConversat onEvent)


  case class SpaceHasLabelW hScoreAboveThresholdW hParam(
    spaceSafetyLabelType: SpaceSafetyLabelType,
    thresholdParam: Param[Double])
      extends Cond  on
      w h HasParams {
    overr de lazy val na : Str ng =
      s"SpaceHasLabelW hScoreAboveThreshold(${spaceSafetyLabelType.na }, ${Nam ngUt ls.getFr endlyNa (thresholdParam)})"
    overr de val features: Set[Feature[_]] = Set(SpaceSafetyLabels)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    pr vate val Unsat sf edResult = Unsat sf ed(t )
    overr de val params: Set[Param[_]] = Set(thresholdParam)

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(SpaceSafetyLabels).as nstanceOf[Seq[SpaceSafetyLabel]]
      val threshold = evaluat onContext.params(thresholdParam)
      val Sat sf edResult =
        Sat sf ed(FoundSpaceLabelW hScoreAboveThreshold(spaceSafetyLabelType, threshold))
      labels
        .collectF rst {
          case label
               f label.safetyLabelType == spaceSafetyLabelType
                && label.safetyLabel.score.ex sts(_ >= threshold) =>
            Sat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  case class CardUr HasRootDoma n(rootDoma nParam: Param[Seq[Str ng]])
      extends Cond  on
      w h HasParams {
    overr de lazy val na : Str ng =
      s"CardUr HasRootDoma n(${Nam ngUt ls.getFr endlyNa (rootDoma nParam)})"
    overr de val features: Set[Feature[_]] = Set(CardUr Host)
    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty
    pr vate val Unsat sf edResult = Unsat sf ed(t )
    overr de val params: Set[Param[_]] = Set(rootDoma nParam)

    pr vate[t ] def  sHostDoma nOrSubdoma n(doma n: Str ng, host: Str ng): Boolean =
      host == doma n || host.endsW h("." + doma n)

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val cardUr Host = featureMap(CardUr Host).as nstanceOf[Str ng]
      val rootDoma ns = evaluat onContext.params(rootDoma nParam)

       f (rootDoma ns.ex sts( sHostDoma nOrSubdoma n(_, cardUr Host))) {
        Sat sf ed(FoundCardUr RootDoma n(cardUr Host))
      } else {
        Unsat sf edResult
      }
    }
  }

  case class T etHasV olat onOfLevel(level: V olat onLevel)
      extends Cond  on
      w h HasSafetyLabelType {

    overr de lazy val na : Str ng = s"t etHasV olat onOfLevel(${level})"

    overr de val features: Set[Feature[_]] = Set(T etSafetyLabels)
    overr de def opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )

    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(FoundT etV olat onOfLevel(level))

    overr de val labelTypes: Set[SafetyLabelType] =
      V olat onLevel.v olat onLevelToSafetyLabels
        .getOrElse(level, Set.empty)
        .map(_.as nstanceOf[SafetyLabelType])

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(T etSafetyLabels).as nstanceOf[Seq[T etSafetyLabel]]
       f (labels.map(V olat onLevel.fromT etSafetyLabel).conta ns(level)) {
        Sat sf edResult
      } else {
        Unsat sf edResult
      }
    }
  }

  case object T etHasV olat onOfAnyLevel extends Cond  on w h HasSafetyLabelType {

    overr de lazy val na : Str ng = s"t etHasV olat onOfAnyLevel"

    overr de val features: Set[Feature[_]] = Set(T etSafetyLabels)

    overr de def opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )

    pr vate val Sat sf edResult: Sat sf ed = Sat sf ed(FoundT etV olat onOfSo Level)

    overr de val labelTypes: Set[SafetyLabelType] =
      V olat onLevel.v olat onLevelToSafetyLabels.values
        .reduceLeft(_ ++ _)
        .map(_.as nstanceOf[SafetyLabelType])

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap(T etSafetyLabels).as nstanceOf[Seq[T etSafetyLabel]]
       f (labels
          .map(V olat onLevel.fromT etSafetyLabelOpt).collect {
            case So (level) => level
          }.nonEmpty) {
        Sat sf edResult
      } else {
        Unsat sf edResult
      }
    }
  }

  case object T et sEd T et extends BooleanFeatureCond  on(T et sEd T etFeature)
  case object T et sStaleT et extends BooleanFeatureCond  on(T et sStaleT etFeature)


  case class V e rHasAdult d aSett ngLevel(sett ngLevelToCompare: Sens  ve d aSett ngsLevel)
      extends Cond  on {
    overr de def features: Set[Feature[_]] = Set(V e rSens  ve d aSett ngs)

    overr de def opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      featureMap
        .get(V e rSens  ve d aSett ngs)
        .map(_.as nstanceOf[UserSens  ve d aSett ngs])
        .collectF rst {
          case UserSens  ve d aSett ngs(So (sett ng))
               f (sett ng.v ewAdultContent == sett ngLevelToCompare) =>
            Result.Sat sf edResult
          case UserSens  ve d aSett ngs(None) => Unsat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  case class V e rHasV olent d aSett ngLevel(sett ngLevelToCompare: Sens  ve d aSett ngsLevel)
      extends Cond  on {
    overr de def features: Set[Feature[_]] = Set(V e rSens  ve d aSett ngs)

    overr de def opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {

      featureMap
        .get(V e rSens  ve d aSett ngs)
        .map(_.as nstanceOf[UserSens  ve d aSett ngs])
        .collectF rst {
          case UserSens  ve d aSett ngs(So (sett ng))
               f (sett ng.v ewV olentContent == sett ngLevelToCompare) =>
            Result.Sat sf edResult
          case UserSens  ve d aSett ngs(None) => Unsat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  case class V e rHasOt rSens  ve d aSett ngLevel(
    sett ngLevelToCompare: Sens  ve d aSett ngsLevel)
      extends Cond  on {
    overr de def features: Set[Feature[_]] = Set(V e rSens  ve d aSett ngs)

    overr de def opt onalFeatures: Set[Feature[_]] = Set.empty

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {

      featureMap
        .get(V e rSens  ve d aSett ngs)
        .map(_.as nstanceOf[UserSens  ve d aSett ngs])
        .collectF rst {
          case UserSens  ve d aSett ngs(So (sett ng))
               f (sett ng.v ewOt rContent == sett ngLevelToCompare) =>
            Result.Sat sf edResult
          case UserSens  ve d aSett ngs(None) => Unsat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  pr vate[rules] val ToxrfT etF lteredForAuthor =
    Equals(Tox cReplyF lterState, F lterState.F lteredFromAuthor)

  pr vate[rules] case object ToxrfV e r sConversat onAuthor
      extends BooleanFeatureCond  on(Tox cReplyF lterConversat onAuthor sV e r)

  val ToxrfF lteredFromAuthorV e r =
    And(Logged nV e r, ToxrfT etF lteredForAuthor, ToxrfV e r sConversat onAuthor)

  case object SearchQueryMatc sScreenNa  extends Cond  on {
    overr de def features: Set[Feature[_]] = Set.empty

    overr de def opt onalFeatures: Set[Feature[_]] = Set(RawQuery, AuthorScreenNa )

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
       f (featureMap.conta ns(RawQuery) && featureMap.conta ns(AuthorScreenNa )) {
        val rawQuery = featureMap(RawQuery).as nstanceOf[Str ng]
        val authorScreenNa  = featureMap(AuthorScreenNa ).as nstanceOf[Str ng]
         f (rawQuery.equals gnoreCase(authorScreenNa )) {
          Result.Sat sf edResult
        } else {
          Unsat sf edResult
        }
      } else {
        Unsat sf edResult
      }
    }
  }

  object SearchQueryDoesNotMatchScreenNa Cond  onBu lder {
    def apply(cond  on: Cond  on, ruleParam: RuleParam[Boolean]): Choose[Boolean] = {
      Choose(
        cond  onMap =
          Map(true -> And(cond  on, Not(SearchQueryMatc sScreenNa )), false -> cond  on),
        defaultCond  on = cond  on,
        cho ceParam = ruleParam
      )
    }
  }

  val SearchQueryDoesNotMatchScreenNa DefaultTrueCond  on: Choose[Boolean] =
    SearchQueryDoesNotMatchScreenNa Cond  onBu lder(Cond  on.True, RuleParams.False)

  case object Opt onalNonAuthorV e r extends Cond  on {
    overr de val features: Set[Feature[_]] = Set()
    overr de val opt onalFeatures: Set[Feature[_]] = Set(Author d, V e r d)

    pr vate val Unsat sf edResult = Unsat sf ed(t )

    overr de def preF lter(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): PreF lterResult = {
      NeedsFullEvaluat on
    }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val author dsOpt = featureMap.get(Author d).as nstanceOf[Opt on[Set[Long]]]
      val v e r dOpt = featureMap.get(V e r d).as nstanceOf[Opt on[Long]]

      (for {
        author ds <- author dsOpt
        v e r d <- v e r dOpt
      } y eld {
         f (author ds.conta ns(v e r d)) Unsat sf edResult
        else Result.Sat sf edResult
      }) getOrElse {
        Result.Sat sf edResult
      }
    }
  }

  case class V e rLocated nAppl cableCountr esOf d aW hhold ngLabel(
    safetyLabelType:  d aSafetyLabelType)
      extends V e r nJur sd ct on
      w h HasSafetyLabelType {

    overr de lazy val na : Str ng =
      s"V e rLocated nAppl cableCountr esOf d aLabel(${safetyLabelType.na })"
    overr de val features: Set[Feature[_]] = Set( d aSafetyLabels)
    overr de val opt onalFeatures: Set[Feature[_]] = Set(V e rCountryCode, RequestCountryCode)
    overr de val labelTypes: Set[SafetyLabelType] = Set(safetyLabelType)

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )

    pr vate[t ] def  s nAppl cableCountr es(
      countryCodeOpt: Opt on[Str ng],
      label: SafetyLabel
    ): Boolean = {
      val  nAppl cableCountry = (for {
        appl cableCountr es <- label.appl cableCountr es
        countryCode <- countryCodeOpt
      } y eld {
        appl cableCountr es.conta ns(countryCode)
      }) getOrElse (false)
       nAppl cableCountry
    }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap( d aSafetyLabels).as nstanceOf[Seq[ d aSafetyLabel]]

      val countryFeatures = getCountryFeatures(featureMap)
      val countryCodeOpt = countryFeatures.requestCountryCode
        .orElse(countryFeatures.v e rCountryCode)

      labels
        .collectF rst {
          case label
               f label.safetyLabelType == safetyLabelType
                &&
                   s nAppl cableCountr es(countryCodeOpt, label.safetyLabel) =>
            Result.Sat sf edResult
        }.getOrElse(Unsat sf edResult)
    }
  }

  case class  d aHasLabelW hWorldw deW hhold ng(safetyLabelType:  d aSafetyLabelType)
      extends Cond  on
      w h HasSafetyLabelType {

    overr de lazy val na : Str ng =
      s" d aHasLabelW hWorldw deW hhold ng(${safetyLabelType.na })"

    overr de val features: Set[Feature[_]] = Set( d aSafetyLabels)

    overr de val opt onalFeatures: Set[Feature[_]] = Set.empty

    overr de val labelTypes: Set[SafetyLabelType] = Set(safetyLabelType)

    pr vate val Unsat sf edResult: Unsat sf ed = Unsat sf ed(t )

    pr vate[t ] def  sW h ldWorldw de(label: SafetyLabel): Boolean = {
      label.appl cableCountr es.map(_.conta ns("xx")).getOrElse(false)
    }

    overr de def apply(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): Result = {
      val labels = featureMap( d aSafetyLabels).as nstanceOf[Seq[ d aSafetyLabel]]

      labels
        .collectF rst {
          case label
               f label.safetyLabelType == safetyLabelType
                &&  sW h ldWorldw de(label.safetyLabel) =>
            Result.Sat sf edResult
        }.getOrElse(Unsat sf edResult)
    }

  }
}
