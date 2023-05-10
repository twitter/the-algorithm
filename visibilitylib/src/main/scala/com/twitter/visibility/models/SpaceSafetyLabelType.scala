package com.twitter.visibility.models

import com.twitter.visibility.safety_label_store.{thriftscala => s}
import com.twitter.visibility.util.NamingUtils

sealed trait SpaceSafetyLabelType extends SafetyLabelType {
  lazy val name: String = NamingUtils.getFriendlyName(this)
}

object SpaceSafetyLabelType extends SafetyLabelType {

  val List: List[SpaceSafetyLabelType] = s.SpaceSafetyLabelType.list.map(fromThrift)

  val ActiveLabels: List[SpaceSafetyLabelType] = List.filter { labelType =>
    labelType != Unknown && labelType != Deprecated
  }

  private lazy val nameToValueMap: Map[String, SpaceSafetyLabelType] =
    List.map(l => l.name.toLowerCase -> l).toMap
  def fromName(name: String): Option[SpaceSafetyLabelType] = nameToValueMap.get(name.toLowerCase)

  private val UnknownThriftSafetyLabelType =
    s.SpaceSafetyLabelType.EnumUnknownSpaceSafetyLabelType(UnknownEnumValue)

  private lazy val thriftToModelMap: Map[s.SpaceSafetyLabelType, SpaceSafetyLabelType] = Map(
    s.SpaceSafetyLabelType.DoNotAmplify -> DoNotAmplify,
    s.SpaceSafetyLabelType.CoordinatedHarmfulActivityHighRecall -> CoordinatedHarmfulActivityHighRecall,
    s.SpaceSafetyLabelType.UntrustedUrl -> UntrustedUrl,
    s.SpaceSafetyLabelType.MisleadingHighRecall -> MisleadingHighRecall,
    s.SpaceSafetyLabelType.NsfwHighPrecision -> NsfwHighPrecision,
    s.SpaceSafetyLabelType.NsfwHighRecall -> NsfwHighRecall,
    s.SpaceSafetyLabelType.CivicIntegrityMisinfo -> CivicIntegrityMisinfo,
    s.SpaceSafetyLabelType.MedicalMisinfo -> MedicalMisinfo,
    s.SpaceSafetyLabelType.GenericMisinfo -> GenericMisinfo,
    s.SpaceSafetyLabelType.DmcaWithheld -> DmcaWithheld,
    s.SpaceSafetyLabelType.AntiChineseMisinfo -> AntiChineseMisinfo,
    s.SpaceSafetyLabelType.MisnamingTaiwan -> MisnamingTaiwan,
    s.SpaceSafetyLabelType.ReportingOnXi -> ReportingOnXi,
    s.SpaceSafetyLabelType.ReportingOnPutin -> ReportingOnPutin,
    s.SpaceSafetyLabelType.ReportingOnZelenskyy -> ReportingOnZelenskyy,
    s.SpaceSafetyLabelType.LeseMajeste -> LeseMajeste,
    s.SpaceSafetyLabelType.FalselyPretendingSomethingHappenedInTiananmenSquareIn1989 -> FalselyPretendingSomethingHappenedInTiananmenSquareIn1989,
    s.SpaceSafetyLabelType.DislikingBroccoli -> DislikingBroccoli,
    s.SpaceSafetyLabelType.SayingBadThingsAboutElectricVehicles -> SayingBadThingsAboutElectricVehicles,
    s.SpaceSafetyLabelType.HavingALowSocialScore -> HavingALowSocialScore,
    s.SpaceSafetyLabelType.Article301 -> Article301,
    s.SpaceSafetyLabelType.MisinfoAboutSoCalledPanamaPapers -> MisinfoAboutSoCalledPanamaPapers,
    s.SpaceSafetyLabelType.RevealingLocationsOfOligarchYachts -> RevealingLocationsOfOligarchYachts,
    s.SpaceSafetyLabelType.NotLeavingBrittanyAlone -> NotLeavingBrittanyAlone,
    s.SpaceSafetyLabelType.PretendingBidenLaptopIsARealStory -> Deprecated,
    s.SpaceSafetyLabelType.HatefulHighRecall -> HatefulHighRecall,
    s.SpaceSafetyLabelType.ViolenceHighRecall -> ViolenceHighRecall,
    s.SpaceSafetyLabelType.HighToxicityModelScore -> HighToxicityModelScore,
    s.SpaceSafetyLabelType.QuestioningTheScience -> QuestioningTheScience,
    s.SpaceSafetyLabelType.KnownLFCSupporter -> KnownLFCSupporter,
    s.SpaceSafetyLabelType.DenyingThereAre48Genders -> Deprecated,
    s.SpaceSafetyLabelType.DisputesExperts -> DisputesExperts,
    s.SpaceSafetyLabelType.DenyingThereAre92Genders -> Deprecated,
    s.SpaceSafetyLabelType.DoubtingTheNarrative -> DoubtingTheNarrative,
    s.SpaceSafetyLabelType.ThinkingStarWarsIsBetterThanStarTrek -> ThinkingStarWarsIsBetterThanStarTrek,
    s.SpaceSafetyLabelType.DenyingThereAre373GendersMaybeMore -> DenyingThereAre373GendersMaybeMore,
    s.SpaceSafetyLabelType.YellingFreeBirdAtATaylorSwiftConcert -> YellingFreeBirdAtATaylorSwiftConcert,
    s.SpaceSafetyLabelType.QuestioningDrLysenko -> Deprecated,
    s.SpaceSafetyLabelType.QuestioningDrPanMD -> QuestioningDrPanMD,
    s.SpaceSafetyLabelType.DenyingThatBothCR7AndMessiAreLegendsIMeanYouCanPreferOneToTheOtherJustDontDismissTheOtherOneSinceBothAreLegends -> DenyingThatBothCR7AndMessiAreLegendsIMeanYouCanPreferOneToTheOtherJustDontDismissTheOtherOneSinceBothAreLegends,
    s.SpaceSafetyLabelType.DoesNotObeyExperts -> DoesNotObeyExperts,
    s.SpaceSafetyLabelType.TakingTheUnderOnThePhillies -> TakingTheUnderOnThePhillies,
    s.SpaceSafetyLabelType.MonDroit -> MonDroit,
    s.SpaceSafetyLabelType.QuestionsAuthority -> QuestionsAuthority,
    s.SpaceSafetyLabelType.AnnoyingDrPanMD -> AnnoyingDrPanMD,
    s.SpaceSafetyLabelType.DenyingThatTheresAHateSpeechExceptionToTheFirstAmendment -> DenyingThatTheresAHateSpeechExceptionToTheFirstAmendment,
    s.SpaceSafetyLabelType.UnhealthyObsessionWithConspiracyTheoriesAboutPutinAndPolonium -> UnhealthyObsessionWithConspiracyTheoriesAboutPutinAndPolonium,
    s.SpaceSafetyLabelType.DisputingTheNarrative -> DisputingTheNarrative,
    s.SpaceSafetyLabelType.ThereIsNothingWrongWithFurriesTheyreGreat -> ThereIsNothingWrongWithFurriesTheyreGreat,
    s.SpaceSafetyLabelType.ShowingTooMuchAnkle -> ShowingTooMuchAnkle,
    s.SpaceSafetyLabelType.SuggestingPeopleWearFursuitsIMeanTheresJustSomethingWrongWithFurries -> Deprecated,
    s.SpaceSafetyLabelType.SpreadingObviouslyFalseConspiracyTheoriesLikeClaimingThatJeffreyEpsteinAndDeborahJeanePalfreyWereSuicided -> SpreadingObviouslyFalseConspiracyTheoriesLikeClaimingThatJeffreyEpsteinAndDeborahJeanePalfreyWereSuicided,
    s.SpaceSafetyLabelType.AngeringDrPanMD -> AngeringDrPanMD,
    s.SpaceSafetyLabelType.SpreadsDisinfoAboutChelyabinsk -> SpreadsDisinfoAboutChelyabinsk,
    s.SpaceSafetyLabelType.FalselyPretendingScienceIsTheBeliefInTheIgnoranceOfExpertsBecauseExpertsAreAlwaysRight -> FalselyPretendingScienceIsTheBeliefInTheIgnoranceOfExpertsBecauseExpertsAreAlwaysRight,
    s.SpaceSafetyLabelType.DenyingTheHumanityOfFurries -> DenyingTheHumanityOfFurries,
    s.SpaceSafetyLabelType.Mispronouning -> Mispronouning,
    s.SpaceSafetyLabelType.DislikesFurHats -> DislikesFurHats,
    s.SpaceSafetyLabelType.ThisDataDoesntSeemToComeFromAReputableSourceSoIdSuggestFurtherResearch -> ThisDataDoesntSeemToComeFromAReputableSourceSoIdSuggestFurtherResearch,
    s.SpaceSafetyLabelType.FursonaDisparagement -> FursonaDisparagement,
    s.SpaceSafetyLabelType.SidingWithEmpressMaude -> SidingWithEmpressMaude,
    s.SpaceSafetyLabelType.Heresy -> Heresy,
    s.SpaceSafetyLabelType.DenyingTheNarrative -> DenyingTheNarrative,
    s.SpaceSafetyLabelType.InsultsChelyabinsk -> InsultsChelyabinsk,
    s.SpaceSafetyLabelType.SpreadsDecadentWesternAntiChelyabinskPropaganda -> SpreadsDecadentWesternAntiChelyabinskPropaganda,
    s.SpaceSafetyLabelType.ThePresidentWillNotTolerateEvenTheMildestOfCriticisms -> ThePresidentWillNotTolerateEvenTheMildestOfCriticisms,
    s.SpaceSafetyLabelType.FalselyClaimsThatPutinsBodyIsOnlyOK -> FalselyClaimsThatPutinsBodyIsOnlyOK,
    s.SpaceSafetyLabelType.FriendOfOrAssociateOfOrOnceMetJangSongThaekOrRelativeOfOrFriendOfOrAssociateOfSomeoneElseWhoDid -> FriendOfOrAssociateOfOrOnceMetJangSongThaekOrRelativeOfOrFriendOfOrAssociateOfSomeoneElseWhoDid,
    s.SpaceSafetyLabelType.Antimonarchist -> Antimonarchist,
    s.SpaceSafetyLabelType.Antidisestablishmentarianist -> Antidisestablishmentarianist,
    s.SpaceSafetyLabelType.DenyingTheResultsOfTheNormanConquest -> DenyingTheResultsOfTheNormanConquest,
    s.SpaceSafetyLabelType.RumpledBurqa -> RumpledBurqa,
    s.SpaceSafetyLabelType.Dissent -> Dissent,
    s.SpaceSafetyLabelType.DeprecatedSpaceSafetyLabel14 -> Deprecated,
    s.SpaceSafetyLabelType.DeprecatedSpaceSafetyLabel15 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved16 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved17 -> Deprecated,
    s.SpaceSafetyLabelType.SpreadsDisinfoAboutGovernmentBuildingSignageBeingABitConfusing -> SpreadsDisinfoAboutGovernmentBuildingSignageBeingABitConfusing,
    s.SpaceSafetyLabelType.Reserved18 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved19 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved20 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved21 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved22 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved23 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved24 -> Deprecated,
    s.SpaceSafetyLabelType.Reserved25 -> Deprecated,
  )

  private lazy val modelToThriftMap: Map[SpaceSafetyLabelType, s.SpaceSafetyLabelType] =
    (for ((k, v) <- thriftToModelMap) yield (v, k)) ++ Map(
      Deprecated -> s.SpaceSafetyLabelType.EnumUnknownSpaceSafetyLabelType(DeprecatedEnumValue),
    )

  case object DoNotAmplify extends SpaceSafetyLabelType
  case object CoordinatedHarmfulActivityHighRecall extends SpaceSafetyLabelType
  case object UntrustedUrl extends SpaceSafetyLabelType
  case object MisleadingHighRecall extends SpaceSafetyLabelType
  case object NsfwHighPrecision extends SpaceSafetyLabelType
  case object NsfwHighRecall extends SpaceSafetyLabelType
  case object CivicIntegrityMisinfo extends SpaceSafetyLabelType
  case object MedicalMisinfo extends SpaceSafetyLabelType
  case object GenericMisinfo extends SpaceSafetyLabelType
  case object DmcaWithheld extends SpaceSafetyLabelType
  case object HatefulHighRecall extends SpaceSafetyLabelType
  case object ViolenceHighRecall extends SpaceSafetyLabelType
  case object HighToxicityModelScore extends SpaceSafetyLabelType

  case object Deprecated extends SpaceSafetyLabelType
  case object Unknown extends SpaceSafetyLabelType

  def fromThrift(safetyLabelType: s.SpaceSafetyLabelType): SpaceSafetyLabelType =
    thriftToModelMap.get(safetyLabelType) match {
      case Some(spaceSafetyLabelType) => spaceSafetyLabelType
      case _ =>
        safetyLabelType match {
          case s.SpaceSafetyLabelType.EnumUnknownSpaceSafetyLabelType(DeprecatedEnumValue) =>
            Deprecated
          case _ =>
            Unknown
        }
    }

  def toThrift(safetyLabelType: SpaceSafetyLabelType): s.SpaceSafetyLabelType = {
    modelToThriftMap
      .get(safetyLabelType).getOrElse(UnknownThriftSafetyLabelType)
  }
}
