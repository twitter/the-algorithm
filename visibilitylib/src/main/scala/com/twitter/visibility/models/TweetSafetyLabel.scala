package com.twitter.visibility.models

import com.twitter.spam.rtf.thriftscala.SafetyLabelSource
import com.twitter.spam.rtf.{thriftscala => s}
import com.twitter.util.Time
import com.twitter.visibility.util.NamingUtils

sealed trait TweetSafetyLabelType extends SafetyLabelType with Product with Serializable {
  lazy val name: String = NamingUtils.getFriendlyName(this)
}

case class TweetSafetyLabel(
  labelType: TweetSafetyLabelType,
  source: Option[LabelSource] = None,
  applicableUsers: Set[Long] = Set.empty,
  modelMetadata: Option[TweetModelMetadata] = None,
  score: Option[Double] = None,
  safetyLabelSource: Option[SafetyLabelSource] = None)

object TweetSafetyLabelType extends SafetyLabelType {

  val List: List[TweetSafetyLabelType] = s.SafetyLabelType.list.map(fromThrift)

  val ActiveLabels: List[TweetSafetyLabelType] = List.filter { labelType =>
    labelType != Unknown && labelType != Deprecated
  }

  private lazy val nameToValueMap: Map[String, TweetSafetyLabelType] =
    List.map(l => l.name.toLowerCase -> l).toMap
  def fromName(name: String): Option[TweetSafetyLabelType] = nameToValueMap.get(name.toLowerCase)

  private val UnknownThriftSafetyLabelType =
    s.SafetyLabelType.EnumUnknownSafetyLabelType(UnknownEnumValue)

  private lazy val thriftToModelMap: Map[s.SafetyLabelType, TweetSafetyLabelType] = Map(
    s.SafetyLabelType.Abusive -> Abusive,
    s.SafetyLabelType.AbusiveBehavior -> AbusiveBehavior,
    s.SafetyLabelType.AbusiveBehaviorInsults -> AbusiveBehaviorInsults,
    s.SafetyLabelType.AbusiveBehaviorViolentThreat -> AbusiveBehaviorViolentThreat,
    s.SafetyLabelType.AbusiveBehaviorMajorAbuse -> AbusiveBehaviorMajorAbuse,
    s.SafetyLabelType.AbusiveHighRecall -> AbusiveHighRecall,
    s.SafetyLabelType.AdsManagerDenyList -> AdsManagerDenyList,
    s.SafetyLabelType.AgathaSpam -> AgathaSpam,
    s.SafetyLabelType.Automation -> Automation,
    s.SafetyLabelType.AutomationHighRecall -> AutomationHighRecall,
    s.SafetyLabelType.Bounce -> Bounce,
    s.SafetyLabelType.BounceEdits -> BounceEdits,
    s.SafetyLabelType.BrandSafetyNsfaAggregate -> BrandSafetyNsfaAggregate,
    s.SafetyLabelType.BrandSafetyExperimental1 -> BrandSafetyExperimental1,
    s.SafetyLabelType.BrandSafetyExperimental2 -> BrandSafetyExperimental2,
    s.SafetyLabelType.BrandSafetyExperimental3 -> BrandSafetyExperimental3,
    s.SafetyLabelType.BrandSafetyExperimental4 -> BrandSafetyExperimental4,
    s.SafetyLabelType.BystanderAbusive -> BystanderAbusive,
    s.SafetyLabelType.CopypastaSpam -> CopypastaSpam,
    s.SafetyLabelType.DoNotAmplify -> DoNotAmplify,
    s.SafetyLabelType.DownrankSpamReply -> DownrankSpamReply,
    s.SafetyLabelType.DuplicateContent -> DuplicateContent,
    s.SafetyLabelType.DuplicateMention -> DuplicateMention,
    s.SafetyLabelType.DynamicProductAd -> DynamicProductAd,
    s.SafetyLabelType.EdiDevelopmentOnly -> EdiDevelopmentOnly,
    s.SafetyLabelType.ExperimentalNudge -> ExperimentalNudge,
    s.SafetyLabelType.ExperimentalSensitiveIllegal2 -> ExperimentalSensitiveIllegal2,
    s.SafetyLabelType.ForEmergencyUseOnly -> ForEmergencyUseOnly,
    s.SafetyLabelType.GoreAndViolence -> GoreAndViolence,
    s.SafetyLabelType.GoreAndViolenceHighPrecision -> GoreAndViolenceHighPrecision,
    s.SafetyLabelType.GoreAndViolenceHighRecall -> GoreAndViolenceHighRecall,
    s.SafetyLabelType.GoreAndViolenceReportedHeuristics -> GoreAndViolenceReportedHeuristics,
    s.SafetyLabelType.GoreAndViolenceTopicHighRecall -> GoreAndViolenceTopicHighRecall,
    s.SafetyLabelType.HatefulConduct -> HatefulConduct,
    s.SafetyLabelType.HatefulConductViolentThreat -> HatefulConductViolentThreat,
    s.SafetyLabelType.HighCryptospamScore -> HighCryptospamScore,
    s.SafetyLabelType.HighPReportedTweetScore -> HighPReportedTweetScore,
    s.SafetyLabelType.HighPSpammyTweetScore -> HighPSpammyTweetScore,
    s.SafetyLabelType.HighPblockScore -> HighPblockScore,
    s.SafetyLabelType.HighProactiveTosScore -> HighProactiveTosScore,
    s.SafetyLabelType.HighSpammyTweetContentScore -> HighSpammyTweetContentScore,
    s.SafetyLabelType.HighToxicityScore -> HighToxicityScore,
    s.SafetyLabelType.HighlyReportedAndMidhighToxicityScore -> HighlyReportedAndMidhighToxicityScore,
    s.SafetyLabelType.HighlyReportedTweet -> HighlyReportedTweet,
    s.SafetyLabelType.InterstitialDevelopmentOnly -> InterstitialDevelopmentOnly,
    s.SafetyLabelType.IpiDevelopmentOnly -> IpiDevelopmentOnly,
    s.SafetyLabelType.LiveLowQuality -> LiveLowQuality,
    s.SafetyLabelType.LowQuality -> LowQuality,
    s.SafetyLabelType.LowQualityMention -> LowQualityMention,
    s.SafetyLabelType.MisinfoCivic -> MisinfoCivic,
    s.SafetyLabelType.MisinfoCrisis -> MisinfoCrisis,
    s.SafetyLabelType.MisinfoGeneric -> MisinfoGeneric,
    s.SafetyLabelType.MisinfoMedical -> MisinfoMedical,
    s.SafetyLabelType.NsfaHighPrecision -> NsfaHighPrecision,
    s.SafetyLabelType.NsfaHighRecall -> NsfaHighRecall,
    s.SafetyLabelType.NsfwCardImage -> NsfwCardImage,
    s.SafetyLabelType.NsfwHighPrecision -> NsfwHighPrecision,
    s.SafetyLabelType.NsfwHighRecall -> NsfwHighRecall,
    s.SafetyLabelType.NsfwReportedHeuristics -> NsfwReportedHeuristics,
    s.SafetyLabelType.NsfwText -> NsfwText,
    s.SafetyLabelType.NsfwTextHighPrecision -> NsfwTextHighPrecision,
    s.SafetyLabelType.NsfwVideo -> NsfwVideo,
    s.SafetyLabelType.PNegMultimodalHighPrecision -> PNegMultimodalHighPrecision,
    s.SafetyLabelType.PNegMultimodalHighRecall -> PNegMultimodalHighRecall,
    s.SafetyLabelType.Pdna -> Pdna,
    s.SafetyLabelType.RecommendationsLowQuality -> RecommendationsLowQuality,
    s.SafetyLabelType.RitoActionedTweet -> RitoActionedTweet,
    s.SafetyLabelType.SafetyCrisis -> SafetyCrisis,
    s.SafetyLabelType.SearchBlacklist -> SearchBlacklist,
    s.SafetyLabelType.SearchBlacklistHighRecall -> SearchBlacklistHighRecall,
    s.SafetyLabelType.SemanticCoreMisinformation -> SemanticCoreMisinformation,
    s.SafetyLabelType.SmyteSpamTweet -> SmyteSpamTweet,
    s.SafetyLabelType.Spam -> Spam,
    s.SafetyLabelType.SpamHighRecall -> SpamHighRecall,
    s.SafetyLabelType.TombstoneDevelopmentOnly -> TombstoneDevelopmentOnly,
    s.SafetyLabelType.TweetContainsHatefulConductSlurHighSeverity -> TweetContainsHatefulConductSlurHighSeverity,
    s.SafetyLabelType.TweetContainsHatefulConductSlurMediumSeverity -> TweetContainsHatefulConductSlurMediumSeverity,
    s.SafetyLabelType.TweetContainsHatefulConductSlurLowSeverity -> TweetContainsHatefulConductSlurLowSeverity,
    s.SafetyLabelType.UnsafeUrl -> UnsafeUrl,
    s.SafetyLabelType.UntrustedUrl -> UntrustedUrl,
    s.SafetyLabelType.FosnrHatefulConduct -> FosnrHatefulConduct,
    s.SafetyLabelType.FosnrHatefulConductLowSeveritySlur -> FosnrHatefulConductLowSeveritySlur,
    s.SafetyLabelType.AbusiveHighRecall2 -> Deprecated,
    s.SafetyLabelType.AbusiveHighRecall3 -> Deprecated,
    s.SafetyLabelType.BrazilianPoliticalTweet -> Deprecated,
    s.SafetyLabelType.BystanderAbusive2 -> Deprecated,
    s.SafetyLabelType.BystanderAbusive3 -> Deprecated,
    s.SafetyLabelType.DeprecatedLabel144 -> Deprecated,
    s.SafetyLabelType.Experimental10Seh -> Deprecated,
    s.SafetyLabelType.Experimental11Seh -> Deprecated,
    s.SafetyLabelType.Experimental12Seh -> Deprecated,
    s.SafetyLabelType.Experimental13Seh -> Deprecated,
    s.SafetyLabelType.Experimental14Seh -> Deprecated,
    s.SafetyLabelType.Experimental15Seh -> Deprecated,
    s.SafetyLabelType.Experimental16Seh -> Deprecated,
    s.SafetyLabelType.Experimental17Seh -> Deprecated,
    s.SafetyLabelType.Experimental18Seh -> Deprecated,
    s.SafetyLabelType.Experimental19Seh -> Deprecated,
    s.SafetyLabelType.Experimental1Seh -> Deprecated,
    s.SafetyLabelType.Experimental20Seh -> Deprecated,
    s.SafetyLabelType.Experimental21Seh -> Deprecated,
    s.SafetyLabelType.Experimental22Seh -> Deprecated,
    s.SafetyLabelType.Experimental23Seh -> Deprecated,
    s.SafetyLabelType.Experimental24Seh -> Deprecated,
    s.SafetyLabelType.Experimental25Seh -> Deprecated,
    s.SafetyLabelType.Experimental2Seh -> Deprecated,
    s.SafetyLabelType.Experimental3Seh -> Deprecated,
    s.SafetyLabelType.Experimental4Seh -> Deprecated,
    s.SafetyLabelType.Experimental5Seh -> Deprecated,
    s.SafetyLabelType.Experimental6Seh -> Deprecated,
    s.SafetyLabelType.Experimental7Seh -> Deprecated,
    s.SafetyLabelType.Experimental8Seh -> Deprecated,
    s.SafetyLabelType.Experimental9Seh -> Deprecated,
    s.SafetyLabelType.ExperimentalHighHealthModelScore1 -> Deprecated,
    s.SafetyLabelType.ExperimentalHighHealthModelScore10 -> Deprecated,
    s.SafetyLabelType.ExperimentalHighHealthModelScore2 -> Deprecated,
    s.SafetyLabelType.ExperimentalHighHealthModelScore3 -> Deprecated,
    s.SafetyLabelType.ExperimentalHighHealthModelScore4 -> Deprecated,
    s.SafetyLabelType.ExperimentalHighHealthModelScore5 -> Deprecated,
    s.SafetyLabelType.ExperimentalHighHealthModelScore6 -> Deprecated,
    s.SafetyLabelType.ExperimentalHighHealthModelScore7 -> Deprecated,
    s.SafetyLabelType.ExperimentalHighHealthModelScore8 -> Deprecated,
    s.SafetyLabelType.ExperimentalHighHealthModelScore9 -> Deprecated,
    s.SafetyLabelType.ExperimentalSensitiveIllegal1 -> Deprecated,
    s.SafetyLabelType.ExperimentalSensitiveIllegal3 -> Deprecated,
    s.SafetyLabelType.ExperimentalSensitiveIllegal4 -> Deprecated,
    s.SafetyLabelType.ExperimentalSensitiveIllegal5 -> Deprecated,
    s.SafetyLabelType.ExperimentalSensitiveIllegal6 -> Deprecated,
    s.SafetyLabelType.ExperimentalSpam1 -> Deprecated,
    s.SafetyLabelType.ExperimentalSpam2 -> Deprecated,
    s.SafetyLabelType.ExperimentalSpam3 -> Deprecated,
    s.SafetyLabelType.Experimentation -> Deprecated,
    s.SafetyLabelType.Experimentation2 -> Deprecated,
    s.SafetyLabelType.Experimentation3 -> Deprecated,
    s.SafetyLabelType.HighlyReportedImage -> Deprecated,
    s.SafetyLabelType.HighToxicityHoldbackModelScore -> Deprecated,
    s.SafetyLabelType.LowQualityHighRecall -> Deprecated,
    s.SafetyLabelType.MagicRecsDenylist -> Deprecated,
    s.SafetyLabelType.MisinfoCovid19 -> Deprecated,
    s.SafetyLabelType.MsnfoBrazilianElection -> Deprecated,
    s.SafetyLabelType.MsnfoCovid19Vaccine -> Deprecated,
    s.SafetyLabelType.MsnfoFrenchElection -> Deprecated,
    s.SafetyLabelType.MsnfoPhilippineElection -> Deprecated,
    s.SafetyLabelType.MsnfoUsElection -> Deprecated,
    s.SafetyLabelType.NsfwNearPerfect -> Deprecated,
    s.SafetyLabelType.PersonaNonGrata -> Deprecated,
    s.SafetyLabelType.PMisinfoCombined15 -> Deprecated,
    s.SafetyLabelType.PMisinfoCombined30 -> Deprecated,
    s.SafetyLabelType.PMisinfoCombined50 -> Deprecated,
    s.SafetyLabelType.PMisinfoDenylist -> Deprecated,
    s.SafetyLabelType.PMisinfoPVeracityNudge -> Deprecated,
    s.SafetyLabelType.PoliticalTweetExperimental1 -> Deprecated,
    s.SafetyLabelType.ProactiveTosHighRecall -> Deprecated,
    s.SafetyLabelType.ProactiveTosHighRecallContainsSelfHarm -> Deprecated,
    s.SafetyLabelType.ProactiveTosHighRecallEncourageSelfHarm -> Deprecated,
    s.SafetyLabelType.ProactiveTosHighRecallEpisodic -> Deprecated,
    s.SafetyLabelType.ProactiveTosHighRecallEpisodicHatefulConduct -> Deprecated,
    s.SafetyLabelType.ProactiveTosHighRecallOtherAbusePolicy -> Deprecated,
    s.SafetyLabelType.ProjectLibra -> Deprecated,
    s.SafetyLabelType.SearchHighVisibilityDenylist -> Deprecated,
    s.SafetyLabelType.SearchHighVisibilityHighRecallDenylist -> Deprecated,
    s.SafetyLabelType.Reserved162 -> Deprecated,
    s.SafetyLabelType.Reserved163 -> Deprecated,
    s.SafetyLabelType.Reserved164 -> Deprecated,
    s.SafetyLabelType.Reserved165 -> Deprecated,
    s.SafetyLabelType.Reserved166 -> Deprecated,
    s.SafetyLabelType.Reserved167 -> Deprecated,
    s.SafetyLabelType.Reserved168 -> Deprecated,
    s.SafetyLabelType.Reserved169 -> Deprecated,
    s.SafetyLabelType.Reserved170 -> Deprecated,
  )

  private lazy val modelToThriftMap: Map[TweetSafetyLabelType, s.SafetyLabelType] =
    (for ((k, v) <- thriftToModelMap) yield (v, k)) ++ Map(
      Deprecated -> s.SafetyLabelType.EnumUnknownSafetyLabelType(DeprecatedEnumValue),
    )

  case object Abusive extends TweetSafetyLabelType
  case object AbusiveBehavior extends TweetSafetyLabelType
  case object AbusiveBehaviorInsults extends TweetSafetyLabelType
  case object AbusiveBehaviorViolentThreat extends TweetSafetyLabelType
  case object AbusiveBehaviorMajorAbuse extends TweetSafetyLabelType
  case object AbusiveHighRecall extends TweetSafetyLabelType
  case object Automation extends TweetSafetyLabelType
  case object AutomationHighRecall extends TweetSafetyLabelType
  case object Bounce extends TweetSafetyLabelType
  case object BystanderAbusive extends TweetSafetyLabelType
  case object NsfaHighRecall extends TweetSafetyLabelType
  case object DuplicateContent extends TweetSafetyLabelType
  case object DuplicateMention extends TweetSafetyLabelType
  case object GoreAndViolence extends TweetSafetyLabelType {

    val DeprecatedAt: Time = Time.at("2019-09-12 00:00:00 UTC")
  }
  case object GoreAndViolenceHighRecall extends TweetSafetyLabelType
  case object LiveLowQuality extends TweetSafetyLabelType
  case object LowQuality extends TweetSafetyLabelType
  case object LowQualityMention extends TweetSafetyLabelType
  case object NsfwCardImage extends TweetSafetyLabelType
  case object NsfwHighRecall extends TweetSafetyLabelType
  case object NsfwHighPrecision extends TweetSafetyLabelType
  case object NsfwVideo extends TweetSafetyLabelType
  case object Pdna extends TweetSafetyLabelType

  case object RecommendationsLowQuality extends TweetSafetyLabelType
  case object SearchBlacklist extends TweetSafetyLabelType
  case object Spam extends TweetSafetyLabelType
  case object SpamHighRecall extends TweetSafetyLabelType
  case object UntrustedUrl extends TweetSafetyLabelType
  case object HighToxicityScore extends TweetSafetyLabelType
  case object HighPblockScore extends TweetSafetyLabelType
  case object SearchBlacklistHighRecall extends TweetSafetyLabelType
  case object ForEmergencyUseOnly extends TweetSafetyLabelType
  case object HighProactiveTosScore extends TweetSafetyLabelType
  case object SafetyCrisis extends TweetSafetyLabelType
  case object MisinfoCivic extends TweetSafetyLabelType
  case object MisinfoCrisis extends TweetSafetyLabelType
  case object MisinfoGeneric extends TweetSafetyLabelType
  case object MisinfoMedical extends TweetSafetyLabelType
  case object AdsManagerDenyList extends TweetSafetyLabelType
  case object GoreAndViolenceHighPrecision extends TweetSafetyLabelType
  case object NsfwReportedHeuristics extends TweetSafetyLabelType
  case object GoreAndViolenceReportedHeuristics extends TweetSafetyLabelType
  case object HighPSpammyTweetScore extends TweetSafetyLabelType
  case object DoNotAmplify extends TweetSafetyLabelType
  case object HighlyReportedTweet extends TweetSafetyLabelType
  case object AgathaSpam extends TweetSafetyLabelType
  case object SmyteSpamTweet extends TweetSafetyLabelType
  case object SemanticCoreMisinformation extends TweetSafetyLabelType
  case object HighPReportedTweetScore extends TweetSafetyLabelType
  case object HighSpammyTweetContentScore extends TweetSafetyLabelType
  case object GoreAndViolenceTopicHighRecall extends TweetSafetyLabelType
  case object CopypastaSpam extends TweetSafetyLabelType
  case object ExperimentalSensitiveIllegal2 extends TweetSafetyLabelType
  case object DownrankSpamReply extends TweetSafetyLabelType
  case object NsfwText extends TweetSafetyLabelType
  case object HighlyReportedAndMidhighToxicityScore extends TweetSafetyLabelType
  case object DynamicProductAd extends TweetSafetyLabelType
  case object TombstoneDevelopmentOnly extends TweetSafetyLabelType
  case object TweetContainsHatefulConductSlurHighSeverity extends TweetSafetyLabelType
  case object TweetContainsHatefulConductSlurMediumSeverity extends TweetSafetyLabelType
  case object TweetContainsHatefulConductSlurLowSeverity extends TweetSafetyLabelType
  case object RitoActionedTweet extends TweetSafetyLabelType
  case object ExperimentalNudge extends TweetSafetyLabelType
  case object PNegMultimodalHighPrecision extends TweetSafetyLabelType
  case object PNegMultimodalHighRecall extends TweetSafetyLabelType
  case object BrandSafetyNsfaAggregate extends TweetSafetyLabelType
  case object HighCryptospamScore extends TweetSafetyLabelType
  case object IpiDevelopmentOnly extends TweetSafetyLabelType
  case object BounceEdits extends TweetSafetyLabelType
  case object UnsafeUrl extends TweetSafetyLabelType
  case object InterstitialDevelopmentOnly extends TweetSafetyLabelType
  case object EdiDevelopmentOnly extends TweetSafetyLabelType
  case object NsfwTextHighPrecision extends TweetSafetyLabelType
  case object HatefulConduct extends TweetSafetyLabelType
  case object HatefulConductViolentThreat extends TweetSafetyLabelType
  case object NsfaHighPrecision extends TweetSafetyLabelType
  case object BrandSafetyExperimental1 extends TweetSafetyLabelType
  case object BrandSafetyExperimental2 extends TweetSafetyLabelType
  case object BrandSafetyExperimental3 extends TweetSafetyLabelType
  case object BrandSafetyExperimental4 extends TweetSafetyLabelType

  case object FosnrHatefulConduct extends TweetSafetyLabelType
  case object FosnrHatefulConductLowSeveritySlur extends TweetSafetyLabelType

  case object Deprecated extends TweetSafetyLabelType
  case object Unknown extends TweetSafetyLabelType

  def fromThrift(safetyLabelType: s.SafetyLabelType): TweetSafetyLabelType =
    thriftToModelMap.get(safetyLabelType) match {
      case Some(tweetSafetyLabelType) => tweetSafetyLabelType
      case _ =>
        safetyLabelType match {
          case s.SafetyLabelType.EnumUnknownSafetyLabelType(DeprecatedEnumValue) => Deprecated
          case _ =>
            Unknown
        }
    }

  def toThrift(safetyLabelType: TweetSafetyLabelType): s.SafetyLabelType = {
    modelToThriftMap.getOrElse(safetyLabelType, UnknownThriftSafetyLabelType)
  }
}

object TweetSafetyLabel {
  def fromThrift(safetyLabelValue: s.SafetyLabelValue): TweetSafetyLabel =
    fromTuple(safetyLabelValue.labelType, safetyLabelValue.label)

  def fromTuple(
    safetyLabelType: s.SafetyLabelType,
    safetyLabel: s.SafetyLabel
  ): TweetSafetyLabel = {
    TweetSafetyLabel(
      labelType = TweetSafetyLabelType.fromThrift(safetyLabelType),
      source = safetyLabel.source.flatMap(LabelSource.fromString),
      safetyLabelSource = safetyLabel.safetyLabelSource,
      applicableUsers = safetyLabel.applicableUsers
        .map { perspectivalUsers =>
          (perspectivalUsers map {
            _.userId
          }).toSet
        }.getOrElse(Set.empty),
      score = safetyLabel.score,
      modelMetadata = safetyLabel.modelMetadata.flatMap(TweetModelMetadata.fromThrift)
    )
  }

  def toThrift(tweetSafetyLabel: TweetSafetyLabel): s.SafetyLabelValue = {
    s.SafetyLabelValue(
      labelType = TweetSafetyLabelType.toThrift(tweetSafetyLabel.labelType),
      label = s.SafetyLabel(
        applicableUsers = if (tweetSafetyLabel.applicableUsers.nonEmpty) {
          Some(tweetSafetyLabel.applicableUsers.toSeq.map {
            s.PerspectivalUser(_)
          })
        } else {
          None
        },
        source = tweetSafetyLabel.source.map(_.name),
        score = tweetSafetyLabel.score,
        modelMetadata = tweetSafetyLabel.modelMetadata.map(TweetModelMetadata.toThrift)
      )
    )
  }
}
