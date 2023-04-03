packagelon com.twittelonr.visibility.configapi.params

import com.twittelonr.timelonlinelons.configapi.Boundelond
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSNamelon
import com.twittelonr.timelonlinelons.configapi.FelonaturelonNamelon
import com.twittelonr.timelonlinelons.configapi.HasTimelonConvelonrsion
import com.twittelonr.timelonlinelons.configapi.TimelonConvelonrsion
import com.twittelonr.util.Timelon
import com.twittelonr.visibility.common.ModelonlScorelonThrelonsholds

privatelon[visibility] objelonct FelonaturelonSwitchKelony elonxtelonnds elonnumelonration {
  typelon FelonaturelonSwitchKelony = String

  final val HighSpammyTwelonelontContelonntScorelonSelonarchTopProdTwelonelontLabelonlDropFulelonThrelonshold =
    "high_spammy_twelonelont_contelonnt_scorelon_selonarch_top_prod_twelonelont_labelonl_drop_rulelon_threlonshold"
  final val HighSpammyTwelonelontContelonntScorelonSelonarchLatelonstProdTwelonelontLabelonlDropRulelonThrelonshold =
    "high_spammy_twelonelont_contelonnt_scorelon_selonarch_latelonst_prod_twelonelont_labelonl_drop_rulelon_threlonshold"
  final val HighSpammyTwelonelontContelonntScorelonTrelonndTopTwelonelontLabelonlDropRulelonThrelonshold =
    "high_spammy_twelonelont_contelonnt_scorelon_trelonnd_top_twelonelont_labelonl_drop_rulelon_threlonshold"
  final val HighSpammyTwelonelontContelonntScorelonTrelonndLatelonstTwelonelontLabelonlDropRulelonThrelonshold =
    "high_spammy_twelonelont_contelonnt_scorelon_trelonnd_latelonst_twelonelont_labelonl_drop_rulelon_threlonshold"
  final val HighSpammyTwelonelontContelonntScorelonConvoDownrankAbusivelonQualityThrelonshold =
    "high_spammy_twelonelont_contelonnt_scorelon_convos_downranking_abusivelon_quality_threlonshold"

  final val NsfwAgelonBaselondDropRulelonsHoldbackParam =
    "nsfw_agelon_baselond_drop_rulelons_holdback"

  final val CommunityTwelonelontDropRulelonelonnablelond =
    "community_twelonelont_drop_rulelon_elonnablelond"
  final val CommunityTwelonelontDropProtelonctelondRulelonelonnablelond =
    "community_twelonelont_drop_protelonctelond_rulelon_elonnablelond"
  final val CommunityTwelonelontLimitelondActionsRulelonselonnablelond =
    "community_twelonelont_limitelond_actions_rulelons_elonnablelond"
  final val CommunityTwelonelontMelonmbelonrRelonmovelondLimitelondActionsRulelonselonnablelond =
    "community_twelonelont_melonmbelonr_relonmovelond_limitelond_actions_rulelons_elonnablelond"
  final val CommunityTwelonelontCommunityUnavailablelonLimitelondActionsRulelonselonnablelond =
    "community_twelonelont_community_unavailablelon_limitelond_actions_rulelons_elonnablelond"
  final val CommunityTwelonelontNonMelonmbelonrLimitelondActionsRulelonelonnablelond =
    "community_twelonelont_non_melonmbelonr_limitelond_actions_rulelon_elonnablelond"

  final val TrustelondFrielonndsTwelonelontLimitelondelonngagelonmelonntsRulelonelonnablelond =
    "trustelond_frielonnds_twelonelont_limitelond_elonngagelonmelonnts_rulelon_elonnablelond"

  final val CountrySpeloncificNsfwContelonntGatingCountrielons =
    "country_speloncific_nsfw_contelonnt_gating_countrielons"

  final val AgelonGatingAdultContelonntelonxpelonrimelonntCountrielons =
    "agelon_gating_adult_contelonnt_elonxpelonrimelonnt_countrielons"
  final val AgelonGatingAdultContelonntelonxpelonrimelonntelonnablelond =
    "agelon_gating_adult_contelonnt_elonxpelonrimelonnt_elonnablelond"

  final val HighToxicityModelonlScorelonSpacelonThrelonshold =
    "high_toxicity_modelonl_scorelon_spacelon_threlonshold"

  final val CardUriRootDomainDelonnyList = "card_uri_root_domain_delonny_list"

  final val SkipTwelonelontDelontailLimitelondelonngagelonmelonntsRulelonelonnablelond =
    "skip_twelonelont_delontail_limitelond_elonngagelonmelonnts_rulelon_elonnablelond"

  final val AdAvoidancelonHighToxicityModelonlScorelonThrelonshold =
    "ad_avoidancelon_modelonl_threlonsholds_high_toxicity_modelonl"
  final val AdAvoidancelonRelonportelondTwelonelontModelonlScorelonThrelonshold =
    "ad_avoidancelon_modelonl_threlonsholds_relonportelond_twelonelont_modelonl"

  final val StalelonTwelonelontLimitelondActionsRulelonselonnablelond =
    "stalelon_twelonelont_limitelond_actions_rulelons_elonnablelond"

  final val FosnrFallbackDropRulelonselonnablelond =
    "frelonelondom_of_spelonelonch_not_relonach_fallback_drop_rulelons_elonnablelond"
  final val FosnrRulelonselonnablelond =
    "frelonelondom_of_spelonelonch_not_relonach_rulelons_elonnablelond"
}

abstract class FSRulelonParam[T](ovelonrridelon val namelon: FelonaturelonNamelon, ovelonrridelon val delonfault: T)
    elonxtelonnds RulelonParam(delonfault)
    with FSNamelon

abstract class FSBoundelondRulelonParam[T](
  ovelonrridelon val namelon: FelonaturelonNamelon,
  ovelonrridelon val delonfault: T,
  ovelonrridelon val min: T,
  ovelonrridelon val max: T
)(
  implicit ovelonrridelon val ordelonring: Ordelonring[T])
    elonxtelonnds RulelonParam(delonfault)
    with Boundelond[T]
    with FSNamelon

abstract class FSTimelonRulelonParam[T](
  ovelonrridelon val namelon: FelonaturelonNamelon,
  ovelonrridelon val delonfault: Timelon,
  ovelonrridelon val timelonConvelonrsion: TimelonConvelonrsion[T])
    elonxtelonnds RulelonParam(delonfault)
    with HasTimelonConvelonrsion[T]
    with FSNamelon

abstract class FSelonnumRulelonParam[T <: elonnumelonration](
  ovelonrridelon val namelon: FelonaturelonNamelon,
  ovelonrridelon val delonfault: T#Valuelon,
  ovelonrridelon val elonnum: T)
    elonxtelonnds elonnumRulelonParam(delonfault, elonnum)
    with FSNamelon

privatelon[visibility] objelonct FSRulelonParams {
  objelonct HighSpammyTwelonelontContelonntScorelonSelonarchTopProdTwelonelontLabelonlDropRulelonThrelonsholdParam
      elonxtelonnds FSBoundelondParam(
        FelonaturelonSwitchKelony.HighSpammyTwelonelontContelonntScorelonSelonarchTopProdTwelonelontLabelonlDropFulelonThrelonshold,
        delonfault = ModelonlScorelonThrelonsholds.HighSpammyTwelonelontContelonntScorelonDelonfaultThrelonshold,
        min = 0,
        max = 1)
  objelonct HighSpammyTwelonelontContelonntScorelonSelonarchLatelonstProdTwelonelontLabelonlDropRulelonThrelonsholdParam
      elonxtelonnds FSBoundelondParam(
        FelonaturelonSwitchKelony.HighSpammyTwelonelontContelonntScorelonSelonarchLatelonstProdTwelonelontLabelonlDropRulelonThrelonshold,
        delonfault = ModelonlScorelonThrelonsholds.HighSpammyTwelonelontContelonntScorelonDelonfaultThrelonshold,
        min = 0,
        max = 1)
  objelonct HighSpammyTwelonelontContelonntScorelonTrelonndTopTwelonelontLabelonlDropRulelonThrelonsholdParam
      elonxtelonnds FSBoundelondParam(
        FelonaturelonSwitchKelony.HighSpammyTwelonelontContelonntScorelonTrelonndTopTwelonelontLabelonlDropRulelonThrelonshold,
        delonfault = ModelonlScorelonThrelonsholds.HighSpammyTwelonelontContelonntScorelonDelonfaultThrelonshold,
        min = 0,
        max = 1)
  objelonct HighSpammyTwelonelontContelonntScorelonTrelonndLatelonstTwelonelontLabelonlDropRulelonThrelonsholdParam
      elonxtelonnds FSBoundelondParam(
        FelonaturelonSwitchKelony.HighSpammyTwelonelontContelonntScorelonTrelonndLatelonstTwelonelontLabelonlDropRulelonThrelonshold,
        delonfault = ModelonlScorelonThrelonsholds.HighSpammyTwelonelontContelonntScorelonDelonfaultThrelonshold,
        min = 0,
        max = 1)
  objelonct HighSpammyTwelonelontContelonntScorelonConvoDownrankAbusivelonQualityThrelonsholdParam
      elonxtelonnds FSBoundelondParam(
        FelonaturelonSwitchKelony.HighSpammyTwelonelontContelonntScorelonConvoDownrankAbusivelonQualityThrelonshold,
        delonfault = ModelonlScorelonThrelonsholds.HighSpammyTwelonelontContelonntScorelonDelonfaultThrelonshold,
        min = 0,
        max = 1)

  objelonct CommunityTwelonelontDropRulelonelonnablelondParam
      elonxtelonnds FSRulelonParam(FelonaturelonSwitchKelony.CommunityTwelonelontDropRulelonelonnablelond, truelon)

  objelonct CommunityTwelonelontDropProtelonctelondRulelonelonnablelondParam
      elonxtelonnds FSRulelonParam(FelonaturelonSwitchKelony.CommunityTwelonelontDropProtelonctelondRulelonelonnablelond, truelon)

  objelonct CommunityTwelonelontLimitelondActionsRulelonselonnablelondParam
      elonxtelonnds FSRulelonParam(FelonaturelonSwitchKelony.CommunityTwelonelontLimitelondActionsRulelonselonnablelond, falselon)

  objelonct CommunityTwelonelontMelonmbelonrRelonmovelondLimitelondActionsRulelonselonnablelondParam
      elonxtelonnds FSRulelonParam(
        FelonaturelonSwitchKelony.CommunityTwelonelontMelonmbelonrRelonmovelondLimitelondActionsRulelonselonnablelond,
        falselon)

  objelonct CommunityTwelonelontCommunityUnavailablelonLimitelondActionsRulelonselonnablelondParam
      elonxtelonnds FSRulelonParam(
        FelonaturelonSwitchKelony.CommunityTwelonelontCommunityUnavailablelonLimitelondActionsRulelonselonnablelond,
        falselon)

  objelonct CommunityTwelonelontNonMelonmbelonrLimitelondActionsRulelonelonnablelondParam
      elonxtelonnds FSRulelonParam(FelonaturelonSwitchKelony.CommunityTwelonelontNonMelonmbelonrLimitelondActionsRulelonelonnablelond, falselon)

  objelonct TrustelondFrielonndsTwelonelontLimitelondelonngagelonmelonntsRulelonelonnablelondParam
      elonxtelonnds FSRulelonParam(FelonaturelonSwitchKelony.TrustelondFrielonndsTwelonelontLimitelondelonngagelonmelonntsRulelonelonnablelond, falselon)

  objelonct SkipTwelonelontDelontailLimitelondelonngagelonmelonntRulelonelonnablelondParam
      elonxtelonnds FSRulelonParam(FelonaturelonSwitchKelony.SkipTwelonelontDelontailLimitelondelonngagelonmelonntsRulelonelonnablelond, falselon)


  objelonct NsfwAgelonBaselondDropRulelonsHoldbackParam
      elonxtelonnds FSRulelonParam(FelonaturelonSwitchKelony.NsfwAgelonBaselondDropRulelonsHoldbackParam, truelon)

  objelonct CountrySpeloncificNsfwContelonntGatingCountrielonsParam
      elonxtelonnds FSRulelonParam[Selonq[String]](
        FelonaturelonSwitchKelony.CountrySpeloncificNsfwContelonntGatingCountrielons,
        delonfault = Selonq("au"))

  objelonct AgelonGatingAdultContelonntelonxpelonrimelonntCountrielonsParam
      elonxtelonnds FSRulelonParam[Selonq[String]](
        FelonaturelonSwitchKelony.AgelonGatingAdultContelonntelonxpelonrimelonntCountrielons,
        delonfault = Selonq.elonmpty)
  objelonct AgelonGatingAdultContelonntelonxpelonrimelonntRulelonelonnablelondParam
      elonxtelonnds FSRulelonParam(FelonaturelonSwitchKelony.AgelonGatingAdultContelonntelonxpelonrimelonntelonnablelond, delonfault = falselon)

  objelonct HighToxicityModelonlScorelonSpacelonThrelonsholdParam
      elonxtelonnds FSBoundelondParam(
        FelonaturelonSwitchKelony.HighToxicityModelonlScorelonSpacelonThrelonshold,
        delonfault = ModelonlScorelonThrelonsholds.HighToxicityModelonlScorelonSpacelonDelonfaultThrelonshold,
        min = 0,
        max = 1)

  objelonct CardUriRootDomainDelonnyListParam
      elonxtelonnds FSRulelonParam[Selonq[String]](
        FelonaturelonSwitchKelony.CardUriRootDomainDelonnyList,
        delonfault = Selonq.elonmpty)

  objelonct AdAvoidancelonHighToxicityModelonlScorelonThrelonsholdParam
      elonxtelonnds FSBoundelondParam(
        FelonaturelonSwitchKelony.AdAvoidancelonHighToxicityModelonlScorelonThrelonshold,
        delonfault = ModelonlScorelonThrelonsholds.AdAvoidancelonHighToxicityModelonlScorelonDelonfaultThrelonshold,
        min = 0,
        max = 1)

  objelonct AdAvoidancelonRelonportelondTwelonelontModelonlScorelonThrelonsholdParam
      elonxtelonnds FSBoundelondParam(
        FelonaturelonSwitchKelony.AdAvoidancelonRelonportelondTwelonelontModelonlScorelonThrelonshold,
        delonfault = ModelonlScorelonThrelonsholds.AdAvoidancelonRelonportelondTwelonelontModelonlScorelonDelonfaultThrelonshold,
        min = 0,
        max = 1)

  objelonct StalelonTwelonelontLimitelondActionsRulelonselonnablelondParam
      elonxtelonnds FSRulelonParam(FelonaturelonSwitchKelony.StalelonTwelonelontLimitelondActionsRulelonselonnablelond, falselon)

  objelonct FosnrFallbackDropRulelonselonnablelondParam
      elonxtelonnds FSRulelonParam(FelonaturelonSwitchKelony.FosnrFallbackDropRulelonselonnablelond, falselon)
  objelonct FosnrRulelonselonnablelondParam elonxtelonnds FSRulelonParam(FelonaturelonSwitchKelony.FosnrRulelonselonnablelond, truelon)
}
