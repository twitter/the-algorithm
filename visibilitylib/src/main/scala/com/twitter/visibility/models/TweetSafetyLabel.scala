package com.twitter.visibility.models

import com.twitter.spam.rtf.thriftscala.SafetyLabelSource
import com.twitter.spam.rtf.{thriftscala => s}
import com.twitter.util.Time
import com.twitter.visibility.util.NamingUtils

sealed trait TweetSafetyLabelType etwittertends SafetyLabelType with Product with Serializable {
  lazy val name: String = NamingUtils.getFriendlyName(this)
}

case class TweetSafetyLabel(
  labelType: TweetSafetyLabelType,
  source: Option[LabelSource] = None,
  applicableUsers: Set[Long] = Set.empty,
  modelMetadata: Option[TweetModelMetadata] = None,
  score: Option[Double] = None,
  safetyLabelSource: Option[SafetyLabelSource] = None)

object TweetSafetyLabelType etwittertends SafetyLabelType {

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
    s.SafetyLabelType.BrandSafetyEtwitterperimental1 -> BrandSafetyEtwitterperimental1,
    s.SafetyLabelType.BrandSafetyEtwitterperimental2 -> BrandSafetyEtwitterperimental2,
    s.SafetyLabelType.BrandSafetyEtwitterperimental3 -> BrandSafetyEtwitterperimental3,
    s.SafetyLabelType.BrandSafetyEtwitterperimental4 -> BrandSafetyEtwitterperimental4,
    s.SafetyLabelType.BystanderAbusive -> BystanderAbusive,
    s.SafetyLabelType.CopypastaSpam -> CopypastaSpam,
    s.SafetyLabelType.DoNotAmplify -> DoNotAmplify,
    s.SafetyLabelType.DownrankSpamReply -> DownrankSpamReply,
    s.SafetyLabelType.DuplicateContent -> DuplicateContent,
    s.SafetyLabelType.DuplicateMention -> DuplicateMention,
    s.SafetyLabelType.DynamicProductAd -> DynamicProductAd,
    s.SafetyLabelType.EdiDevelopmentOnly -> EdiDevelopmentOnly,
    s.SafetyLabelType.EtwitterperimentalNudge -> EtwitterperimentalNudge,
    s.SafetyLabelType.EtwitterperimentalSensitiveIllegal2 -> EtwitterperimentalSensitiveIllegal2,
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
    s.SafetyLabelType.HighTotwittericityScore -> HighTotwittericityScore,
    s.SafetyLabelType.HighlyReportedAndMidhighTotwittericityScore -> HighlyReportedAndMidhighTotwittericityScore,
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
    s.SafetyLabelType.NsfwTetwittert -> NsfwTetwittert,
    s.SafetyLabelType.NsfwTetwittertHighPrecision -> NsfwTetwittertHighPrecision,
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
    s.SafetyLabelType.Etwitterperimental10Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental11Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental12Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental13Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental14Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental15Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental16Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental17Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental18Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental19Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental1Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental20Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental21Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental22Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental23Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental24Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental25Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental2Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental3Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental4Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental5Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental6Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental7Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental8Seh -> Deprecated,
    s.SafetyLabelType.Etwitterperimental9Seh -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalHighHealthModelScore1 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalHighHealthModelScore10 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalHighHealthModelScore2 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalHighHealthModelScore3 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalHighHealthModelScore4 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalHighHealthModelScore5 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalHighHealthModelScore6 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalHighHealthModelScore7 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalHighHealthModelScore8 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalHighHealthModelScore9 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalSensitiveIllegal1 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalSensitiveIllegal3 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalSensitiveIllegal4 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalSensitiveIllegal5 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalSensitiveIllegal6 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalSpam1 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalSpam2 -> Deprecated,
    s.SafetyLabelType.EtwitterperimentalSpam3 -> Deprecated,
    s.SafetyLabelType.Etwitterperimentation -> Deprecated,
    s.SafetyLabelType.Etwitterperimentation2 -> Deprecated,
    s.SafetyLabelType.Etwitterperimentation3 -> Deprecated,
    s.SafetyLabelType.HighlyReportedImage -> Deprecated,
    s.SafetyLabelType.HighTotwittericityHoldbackModelScore -> Deprecated,
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
    s.SafetyLabelType.PoliticalTweetEtwitterperimental1 -> Deprecated,
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

  case object Abusive etwittertends TweetSafetyLabelType
  case object AbusiveBehavior etwittertends TweetSafetyLabelType
  case object AbusiveBehaviorInsults etwittertends TweetSafetyLabelType
  case object AbusiveBehaviorViolentThreat etwittertends TweetSafetyLabelType
  case object AbusiveBehaviorMajorAbuse etwittertends TweetSafetyLabelType
  case object AbusiveHighRecall etwittertends TweetSafetyLabelType
  case object Automation etwittertends TweetSafetyLabelType
  case object AutomationHighRecall etwittertends TweetSafetyLabelType
  case object Bounce etwittertends TweetSafetyLabelType
  case object BystanderAbusive etwittertends TweetSafetyLabelType
  case object NsfaHighRecall etwittertends TweetSafetyLabelType
  case object DuplicateContent etwittertends TweetSafetyLabelType
  case object DuplicateMention etwittertends TweetSafetyLabelType
  case object GoreAndViolence etwittertends TweetSafetyLabelType {

    val DeprecatedAt: Time = Time.at("2019-09-12 00:00:00 UTC")
  }
  case object GoreAndViolenceHighRecall etwittertends TweetSafetyLabelType
  case object LiveLowQuality etwittertends TweetSafetyLabelType
  case object LowQuality etwittertends TweetSafetyLabelType
  case object LowQualityMention etwittertends TweetSafetyLabelType
  case object NsfwCardImage etwittertends TweetSafetyLabelType
  case object NsfwHighRecall etwittertends TweetSafetyLabelType
  case object NsfwHighPrecision etwittertends TweetSafetyLabelType
  case object NsfwVideo etwittertends TweetSafetyLabelType
  case object Pdna etwittertends TweetSafetyLabelType

  case object RecommendationsLowQuality etwittertends TweetSafetyLabelType
  case object SearchBlacklist etwittertends TweetSafetyLabelType
  case object Spam etwittertends TweetSafetyLabelType
  case object SpamHighRecall etwittertends TweetSafetyLabelType
  case object UntrustedUrl etwittertends TweetSafetyLabelType
  case object HighTotwittericityScore etwittertends TweetSafetyLabelType
  case object HighPblockScore etwittertends TweetSafetyLabelType
  case object SearchBlacklistHighRecall etwittertends TweetSafetyLabelType
  case object ForEmergencyUseOnly etwittertends TweetSafetyLabelType
  case object HighProactiveTosScore etwittertends TweetSafetyLabelType
  case object SafetyCrisis etwittertends TweetSafetyLabelType
  case object MisinfoCivic etwittertends TweetSafetyLabelType
  case object MisinfoCrisis etwittertends TweetSafetyLabelType
  case object MisinfoGeneric etwittertends TweetSafetyLabelType
  case object MisinfoMedical etwittertends TweetSafetyLabelType
  case object AdsManagerDenyList etwittertends TweetSafetyLabelType
  case object GoreAndViolenceHighPrecision etwittertends TweetSafetyLabelType
  case object NsfwReportedHeuristics etwittertends TweetSafetyLabelType
  case object GoreAndViolenceReportedHeuristics etwittertends TweetSafetyLabelType
  case object HighPSpammyTweetScore etwittertends TweetSafetyLabelType
  case object DoNotAmplify etwittertends TweetSafetyLabelType
  case object HighlyReportedTweet etwittertends TweetSafetyLabelType
  case object AgathaSpam etwittertends TweetSafetyLabelType
  case object SmyteSpamTweet etwittertends TweetSafetyLabelType
  case object SemanticCoreMisinformation etwittertends TweetSafetyLabelType
  case object HighPReportedTweetScore etwittertends TweetSafetyLabelType
  case object HighSpammyTweetContentScore etwittertends TweetSafetyLabelType
  case object GoreAndViolenceTopicHighRecall etwittertends TweetSafetyLabelType
  case object CopypastaSpam etwittertends TweetSafetyLabelType
  case object EtwitterperimentalSensitiveIllegal2 etwittertends TweetSafetyLabelType
  case object DownrankSpamReply etwittertends TweetSafetyLabelType
  case object NsfwTetwittert etwittertends TweetSafetyLabelType
  case object HighlyReportedAndMidhighTotwittericityScore etwittertends TweetSafetyLabelType
  case object DynamicProductAd etwittertends TweetSafetyLabelType
  case object TombstoneDevelopmentOnly etwittertends TweetSafetyLabelType
  case object TweetContainsHatefulConductSlurHighSeverity etwittertends TweetSafetyLabelType
  case object TweetContainsHatefulConductSlurMediumSeverity etwittertends TweetSafetyLabelType
  case object TweetContainsHatefulConductSlurLowSeverity etwittertends TweetSafetyLabelType
  case object RitoActionedTweet etwittertends TweetSafetyLabelType
  case object EtwitterperimentalNudge etwittertends TweetSafetyLabelType
  case object PNegMultimodalHighPrecision etwittertends TweetSafetyLabelType
  case object PNegMultimodalHighRecall etwittertends TweetSafetyLabelType
  case object BrandSafetyNsfaAggregate etwittertends TweetSafetyLabelType
  case object HighCryptospamScore etwittertends TweetSafetyLabelType
  case object IpiDevelopmentOnly etwittertends TweetSafetyLabelType
  case object BounceEdits etwittertends TweetSafetyLabelType
  case object UnsafeUrl etwittertends TweetSafetyLabelType
  case object InterstitialDevelopmentOnly etwittertends TweetSafetyLabelType
  case object EdiDevelopmentOnly etwittertends TweetSafetyLabelType
  case object NsfwTetwittertHighPrecision etwittertends TweetSafetyLabelType
  case object HatefulConduct etwittertends TweetSafetyLabelType
  case object HatefulConductViolentThreat etwittertends TweetSafetyLabelType
  case object NsfaHighPrecision etwittertends TweetSafetyLabelType
  case object BrandSafetyEtwitterperimental1 etwittertends TweetSafetyLabelType
  case object BrandSafetyEtwitterperimental2 etwittertends TweetSafetyLabelType
  case object BrandSafetyEtwitterperimental3 etwittertends TweetSafetyLabelType
  case object BrandSafetyEtwitterperimental4 etwittertends TweetSafetyLabelType

  case object FosnrHatefulConduct etwittertends TweetSafetyLabelType
  case object FosnrHatefulConductLowSeveritySlur etwittertends TweetSafetyLabelType

  case object Deprecated etwittertends TweetSafetyLabelType
  case object Unknown etwittertends TweetSafetyLabelType

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
