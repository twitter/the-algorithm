packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.visibility.configapi.params.RulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams
import com.twittelonr.visibility.modelonls.ContelonntId
import com.twittelonr.visibility.rulelons.ConvelonrsationControlRulelons._
import com.twittelonr.visibility.rulelons.FollowelonrRelonlations.AuthorMutelonsVielonwelonrRulelon
import com.twittelonr.visibility.rulelons.FollowelonrRelonlations.ProtelonctelondVielonwelonrRulelon
import com.twittelonr.visibility.rulelons.PolicyLelonvelonlRulelonParams.rulelonParams
import com.twittelonr.visibility.rulelons.PublicIntelonrelonstRulelons._
import com.twittelonr.visibility.rulelons.SafelonSelonarchTwelonelontRulelons._
import com.twittelonr.visibility.rulelons.SafelonSelonarchUselonrRulelons.SafelonSelonarchNsfwAvatarImagelonUselonrLabelonlRulelon
import com.twittelonr.visibility.rulelons.SafelonSelonarchUselonrRulelons._
import com.twittelonr.visibility.rulelons.SpacelonRulelons._
import com.twittelonr.visibility.rulelons.ToxicityRelonplyFiltelonrRulelons.ToxicityRelonplyFiltelonrDropNotificationRulelon
import com.twittelonr.visibility.rulelons.ToxicityRelonplyFiltelonrRulelons.ToxicityRelonplyFiltelonrRulelon
import com.twittelonr.visibility.rulelons.UnsafelonSelonarchTwelonelontRulelons._
import com.twittelonr.visibility.rulelons.UselonrUnavailablelonStatelonTombstonelonRulelons._

abstract class VisibilityPolicy(
  val twelonelontRulelons: Selonq[Rulelon] = Nil,
  val uselonrRulelons: Selonq[Rulelon] = Nil,
  val cardRulelons: Selonq[Rulelon] = Nil,
  val quotelondTwelonelontRulelons: Selonq[Rulelon] = Nil,
  val dmRulelons: Selonq[Rulelon] = Nil,
  val dmConvelonrsationRulelons: Selonq[Rulelon] = Nil,
  val dmelonvelonntRulelons: Selonq[Rulelon] = Nil,
  val spacelonRulelons: Selonq[Rulelon] = Nil,
  val uselonrUnavailablelonStatelonRulelons: Selonq[Rulelon] = Nil,
  val twittelonrArticlelonRulelons: Selonq[Rulelon] = Nil,
  val delonlelontelondTwelonelontRulelons: Selonq[Rulelon] = Nil,
  val melondiaRulelons: Selonq[Rulelon] = Nil,
  val communityRulelons: Selonq[Rulelon] = Nil,
  val policyRulelonParams: Map[Rulelon, PolicyLelonvelonlRulelonParams] = Map.elonmpty) {

  delonf forContelonntId(contelonntId: ContelonntId): Selonq[Rulelon] =
    contelonntId match {
      caselon ContelonntId.TwelonelontId(_) => twelonelontRulelons
      caselon ContelonntId.UselonrId(_) => uselonrRulelons
      caselon ContelonntId.CardId(_) => cardRulelons
      caselon ContelonntId.QuotelondTwelonelontRelonlationship(_, _) => quotelondTwelonelontRulelons
      caselon ContelonntId.NotificationId(_) => uselonrRulelons
      caselon ContelonntId.DmId(_) => dmRulelons
      caselon ContelonntId.BlelonndelonrTwelonelontId(_) => uselonrRulelons ++ twelonelontRulelons
      caselon ContelonntId.SpacelonId(_) => spacelonRulelons
      caselon ContelonntId.SpacelonPlusUselonrId(_) => spacelonRulelons ++ uselonrRulelons
      caselon ContelonntId.DmConvelonrsationId(_) => dmConvelonrsationRulelons
      caselon ContelonntId.DmelonvelonntId(_) => dmelonvelonntRulelons
      caselon ContelonntId.UselonrUnavailablelonStatelon(_) => uselonrUnavailablelonStatelonRulelons
      caselon ContelonntId.TwittelonrArticlelonId(_) => twittelonrArticlelonRulelons
      caselon ContelonntId.DelonlelontelonTwelonelontId(_) => delonlelontelondTwelonelontRulelons
      caselon ContelonntId.MelondiaId(_) => melondiaRulelons
      caselon ContelonntId.CommunityId(_) => communityRulelons
    }

  privatelon[visibility] delonf allRulelons: Selonq[Rulelon] =
    (twelonelontRulelons ++ uselonrRulelons ++ cardRulelons ++ quotelondTwelonelontRulelons ++ dmRulelons ++ spacelonRulelons ++ dmConvelonrsationRulelons ++ dmelonvelonntRulelons ++ twittelonrArticlelonRulelons ++ delonlelontelondTwelonelontRulelons ++ melondiaRulelons ++ communityRulelons)
}

objelonct VisibilityPolicy {
  val baselonTwelonelontRulelons = Selonq(
    DropCommunityTwelonelontsRulelon,
    DropCommunityTwelonelontCommunityNotVisiblelonRulelon,
    DropProtelonctelondCommunityTwelonelontsRulelon,
    DropHiddelonnCommunityTwelonelontsRulelon,
    DropAuthorRelonmovelondCommunityTwelonelontsRulelon,
    SpamTwelonelontLabelonlRulelon,
    PdnaTwelonelontLabelonlRulelon,
    BouncelonTwelonelontLabelonlRulelon,
    DropelonxclusivelonTwelonelontContelonntRulelon,
    DropTrustelondFrielonndsTwelonelontContelonntRulelon
  )

  val baselonTwelonelontTombstonelonRulelons = Selonq(
    TombstonelonCommunityTwelonelontsRulelon,
    TombstonelonCommunityTwelonelontCommunityNotVisiblelonRulelon,
    TombstonelonProtelonctelondCommunityTwelonelontsRulelon,
    TombstonelonHiddelonnCommunityTwelonelontsRulelon,
    TombstonelonAuthorRelonmovelondCommunityTwelonelontsRulelon,
    SpamTwelonelontLabelonlTombstonelonRulelon,
    PdnaTwelonelontLabelonlTombstonelonRulelon,
    BouncelonTwelonelontLabelonlTombstonelonRulelon,
    TombstonelonelonxclusivelonTwelonelontContelonntRulelon,
    TombstonelonTrustelondFrielonndsTwelonelontContelonntRulelon,
  )

  val baselonMelondiaRulelons = Selonq(
  )

  val baselonQuotelondTwelonelontTombstonelonRulelons = Selonq(
    BouncelonQuotelondTwelonelontTombstonelonRulelon
  )

  delonf union[T](rulelons: Selonq[Rulelon]*): Selonq[Rulelon] = {
    if (rulelons.iselonmpty) {
      Selonq.elonmpty[Rulelon]
    } elonlselon {
      rulelons.relonducelon((a, b) => a ++ b.filtelonrNot(a.contains))
    }
  }
}

caselon class PolicyLelonvelonlRulelonParams(
  rulelonParams: Selonq[RulelonParam[Boolelonan]],
  forcelon: Boolelonan = falselon) {}

objelonct PolicyLelonvelonlRulelonParams {
  delonf rulelonParams(rulelonParams: RulelonParam[Boolelonan]*): PolicyLelonvelonlRulelonParams = {
    PolicyLelonvelonlRulelonParams(rulelonParams)
  }

  delonf rulelonParams(forcelon: Boolelonan, rulelonParams: RulelonParam[Boolelonan]*): PolicyLelonvelonlRulelonParams = {
    PolicyLelonvelonlRulelonParams(rulelonParams, forcelon)
  }
}

caselon objelonct FiltelonrAllPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(DropAllRulelon),
      uselonrRulelons = Selonq(DropAllRulelon),
      cardRulelons = Selonq(DropAllRulelon),
      quotelondTwelonelontRulelons = Selonq(DropAllRulelon),
      dmRulelons = Selonq(DropAllRulelon),
      dmConvelonrsationRulelons = Selonq(DropAllRulelon),
      dmelonvelonntRulelons = Selonq(DropAllRulelon),
      spacelonRulelons = Selonq(DropAllRulelon),
      uselonrUnavailablelonStatelonRulelons = Selonq(DropAllRulelon),
      twittelonrArticlelonRulelons = Selonq(DropAllRulelon),
      delonlelontelondTwelonelontRulelons = Selonq(DropAllRulelon),
      melondiaRulelons = Selonq(DropAllRulelon),
      communityRulelons = Selonq(DropAllRulelon),
    )

caselon objelonct FiltelonrNonelonPolicy elonxtelonnds VisibilityPolicy()

objelonct ConvelonrsationsAdAvoidancelonRulelons {
  val twelonelontRulelons = Selonq(
    NsfwHighReloncallTwelonelontLabelonlAvoidRulelon,
    NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon,
    NsfwTelonxtTwelonelontLabelonlAvoidRulelon,
    AvoidHighToxicityModelonlScorelonRulelon,
    AvoidRelonportelondTwelonelontModelonlScorelonRulelon,
    NsfwHighPreloncisionUselonrLabelonlAvoidTwelonelontRulelon,
    TwelonelontNsfwUselonrAdminAvoidRulelon,
    DoNotAmplifyTwelonelontLabelonlAvoidRulelon,
    NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon,
  )

  val policyRulelonParams = Map[Rulelon, PolicyLelonvelonlRulelonParams](
    NsfwHighReloncallTwelonelontLabelonlAvoidRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwAdAvoidancelonRulelonsParam
    ),
    NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwAdAvoidancelonRulelonsParam
    ),
    NsfwTelonxtTwelonelontLabelonlAvoidRulelon -> rulelonParams(RulelonParams.elonnablelonNelonwAdAvoidancelonRulelonsParam),
    AvoidHighToxicityModelonlScorelonRulelon -> rulelonParams(RulelonParams.elonnablelonNelonwAdAvoidancelonRulelonsParam),
    AvoidRelonportelondTwelonelontModelonlScorelonRulelon -> rulelonParams(RulelonParams.elonnablelonNelonwAdAvoidancelonRulelonsParam),
    NsfwHighPreloncisionUselonrLabelonlAvoidTwelonelontRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwAdAvoidancelonRulelonsParam),
    TwelonelontNsfwUselonrAdminAvoidRulelon -> rulelonParams(RulelonParams.elonnablelonNelonwAdAvoidancelonRulelonsParam),
    DoNotAmplifyTwelonelontLabelonlAvoidRulelon -> rulelonParams(RulelonParams.elonnablelonNelonwAdAvoidancelonRulelonsParam),
    NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon -> rulelonParams(RulelonParams.elonnablelonNelonwAdAvoidancelonRulelonsParam),
  )
}

caselon objelonct FiltelonrDelonfaultPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon
        )
    )

caselon objelonct LimitelondelonngagelonmelonntBaselonRulelons
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        StalelonTwelonelontLimitelondActionsRulelon,
        LimitRelonplielonsByInvitationConvelonrsationRulelon,
        LimitRelonplielonsCommunityConvelonrsationRulelon,
        LimitRelonplielonsFollowelonrsConvelonrsationRulelon,
        CommunityTwelonelontCommunityNotFoundLimitelondActionsRulelon,
        CommunityTwelonelontCommunityDelonlelontelondLimitelondActionsRulelon,
        CommunityTwelonelontCommunitySuspelonndelondLimitelondActionsRulelon,
        CommunityTwelonelontMelonmbelonrRelonmovelondLimitelondActionsRulelon,
        CommunityTwelonelontHiddelonnLimitelondActionsRulelon,
        CommunityTwelonelontMelonmbelonrLimitelondActionsRulelon,
        CommunityTwelonelontNonMelonmbelonrLimitelondActionsRulelon,
        DynamicProductAdLimitelondelonngagelonmelonntTwelonelontLabelonlRulelon,
        TrustelondFrielonndsTwelonelontLimitelondelonngagelonmelonntsRulelon
      )
    )

caselon objelonct WritelonPathLimitelondActionselonnforcelonmelonntPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
        elonmelonrgelonncyDynamicIntelonrstitialRulelon
      ) ++
        LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct TelonstPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        TelonstRulelon
      )
    )

caselon objelonct CardsSelonrvicelonPolicy
    elonxtelonnds VisibilityPolicy(
      cardRulelons = Selonq(
        DropProtelonctelondAuthorPollCardRulelon,
        DropCardUriRootDomainDelonnylistRulelon
      ),
      spacelonRulelons = Selonq(
        SpacelonHighToxicityScorelonNonFollowelonrDropRulelon,
        SpacelonHatelonfulHighReloncallAllUselonrsDropRulelon,
        SpacelonViolelonncelonHighReloncallAllUselonrsDropRulelon,
        VielonwelonrIsSoftUselonrDropRulelon
      ),
    )

caselon objelonct CardPollVotingPolicy
    elonxtelonnds VisibilityPolicy(
      cardRulelons = Selonq(
        DropProtelonctelondAuthorPollCardRulelon,
        DropCommunityNonMelonmbelonrPollCardRulelon
      )
    )

caselon objelonct UselonrTimelonlinelonRulelons {
  val UselonrRulelons = Selonq(
    AuthorBlocksVielonwelonrDropRulelon,
    ProtelonctelondAuthorDropRulelon,
    SuspelonndelondAuthorRulelon
  )
}

caselon objelonct TimelonlinelonLikelondByRulelons {
  val UselonrRulelons = Selonq(
    CompromiselondNonFollowelonrWithUqfRulelon,
    elonngagelonmelonntSpammelonrNonFollowelonrWithUqfRulelon,
    LowQualityNonFollowelonrWithUqfRulelon,
    RelonadOnlyNonFollowelonrWithUqfRulelon,
    SpamHighReloncallNonFollowelonrWithUqfRulelon
  )
}

caselon objelonct FollowingAndFollowelonrsUselonrListPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = UselonrTimelonlinelonRulelons.UselonrRulelons
    )

caselon objelonct FrielonndsFollowingListPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = UselonrTimelonlinelonRulelons.UselonrRulelons
    )

caselon objelonct ListOwnelonrshipsPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = UselonrTimelonlinelonRulelons.UselonrRulelons
    )

caselon objelonct ListReloncommelonndationsPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = ReloncommelonndationsPolicy.uselonrRulelons ++ Selonq(
        DropNsfwUselonrAuthorRulelon,
        NsfwHighReloncallRulelon,
        SelonarchBlacklistRulelon,
        SelonarchNsfwTelonxtRulelon,
        VielonwelonrBlocksAuthorRulelon,
        VielonwelonrMutelonsAuthorRulelon
      )
    )

caselon objelonct ListSelonarchBaselonRulelons {

  val NonelonxpelonrimelonntalSafelonSelonarchMinimalPolicyUselonrRulelons: Selonq[Rulelon] =
    SafelonSelonarchMinimalPolicy.uselonrRulelons.filtelonrNot(_.iselonxpelonrimelonntal)

  val MinimalPolicyUselonrRulelons: Selonq[Rulelon] = NonelonxpelonrimelonntalSafelonSelonarchMinimalPolicyUselonrRulelons

  val BlockMutelonPolicyUselonrRulelons = Selonq(
    VielonwelonrBlocksAuthorVielonwelonrOptInBlockingOnSelonarchRulelon,
    VielonwelonrMutelonsAuthorVielonwelonrOptInBlockingOnSelonarchRulelon
  )

  val StrictPolicyUselonrRulelons = Selonq(
    SafelonSelonarchAbusivelonUselonrLabelonlRulelon,
    SafelonSelonarchAbusivelonHighReloncallUselonrLabelonlRulelon,
    SafelonSelonarchCompromiselondUselonrLabelonlRulelon,
    SafelonSelonarchDoNotAmplifyNonFollowelonrsUselonrLabelonlRulelon,
    SafelonSelonarchDuplicatelonContelonntUselonrLabelonlRulelon,
    SafelonSelonarchLowQualityUselonrLabelonlRulelon,
    SafelonSelonarchNotGraduatelondNonFollowelonrsUselonrLabelonlRulelon,
    SafelonSelonarchNsfwHighPreloncisionUselonrLabelonlRulelon,
    SafelonSelonarchNsfwAvatarImagelonUselonrLabelonlRulelon,
    SafelonSelonarchNsfwBannelonrImagelonUselonrLabelonlRulelon,
    SafelonSelonarchRelonadOnlyUselonrLabelonlRulelon,
    SafelonSelonarchSelonarchBlacklistUselonrLabelonlRulelon,
    SafelonSelonarchNsfwTelonxtUselonrLabelonlRulelon,
    SafelonSelonarchSpamHighReloncallUselonrLabelonlRulelon,
    SafelonSelonarchDownrankSpamRelonplyAuthorLabelonlRulelon,
    SafelonSelonarchNsfwTelonxtAuthorLabelonlRulelon,
    DropNsfwAdminAuthorVielonwelonrOptInFiltelonringOnSelonarchRulelon,
    DropNsfwUselonrAuthorVielonwelonrOptInFiltelonringOnSelonarchRulelon,
  )
}

objelonct SelonnsitivelonMelondiaSelonttingsTimelonlinelonHomelonBaselonRulelons {
  val policyRulelonParams = Map[Rulelon, PolicyLelonvelonlRulelonParams](
    NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaHomelonTimelonlinelonRulelonsParam),
    GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaHomelonTimelonlinelonRulelonsParam),
    NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaHomelonTimelonlinelonRulelonsParam),
    GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaHomelonTimelonlinelonRulelonsParam),
    NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaHomelonTimelonlinelonRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsHomelonTimelonlinelonRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsHomelonTimelonlinelonRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsHomelonTimelonlinelonRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsHomelonTimelonlinelonRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsHomelonTimelonlinelonRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsHomelonTimelonlinelonRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsHomelonTimelonlinelonRulelonsParam)
  )
}

objelonct SelonnsitivelonMelondiaSelonttingsConvelonrsationBaselonRulelons {
  val policyRulelonParams = Map[Rulelon, PolicyLelonvelonlRulelonParams](
    NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaConvelonrsationRulelonsParam),
    GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaConvelonrsationRulelonsParam),
    NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaConvelonrsationRulelonsParam),
    GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaConvelonrsationRulelonsParam),
    NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaConvelonrsationRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsConvelonrsationRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsConvelonrsationRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsConvelonrsationRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsConvelonrsationRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsConvelonrsationRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsConvelonrsationRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsConvelonrsationRulelonsParam)
  )
}

objelonct SelonnsitivelonMelondiaSelonttingsProfilelonTimelonlinelonBaselonRulelons {
  val policyRulelonParams = Map[Rulelon, PolicyLelonvelonlRulelonParams](
    NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaProfilelonTimelonlinelonRulelonsParam),
    GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaProfilelonTimelonlinelonRulelonsParam),
    NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaProfilelonTimelonlinelonRulelonsParam),
    GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaProfilelonTimelonlinelonRulelonsParam),
    NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaProfilelonTimelonlinelonRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsProfilelonTimelonlinelonRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsProfilelonTimelonlinelonRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsProfilelonTimelonlinelonRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsProfilelonTimelonlinelonRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsProfilelonTimelonlinelonRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsProfilelonTimelonlinelonRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsProfilelonTimelonlinelonRulelonsParam)
  )
}

objelonct SelonnsitivelonMelondiaSelonttingsTwelonelontDelontailBaselonRulelons {
  val policyRulelonParams = Map[Rulelon, PolicyLelonvelonlRulelonParams](
    NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaTwelonelontDelontailRulelonsParam),
    GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaTwelonelontDelontailRulelonsParam),
    NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaTwelonelontDelontailRulelonsParam),
    GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaTwelonelontDelontailRulelonsParam),
    NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon -> rulelonParams(
      RulelonParams.elonnablelonLelongacySelonnsitivelonMelondiaTwelonelontDelontailRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsTwelonelontDelontailRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsTwelonelontDelontailRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsTwelonelontDelontailRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsTwelonelontDelontailRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsTwelonelontDelontailRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsTwelonelontDelontailRulelonsParam),
    SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagIntelonrstitialRulelon -> rulelonParams(
      RulelonParams.elonnablelonNelonwSelonnsitivelonMelondiaSelonttingsIntelonrstitialsTwelonelontDelontailRulelonsParam)
  )
}

caselon objelonct ListSelonarchPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = ListSelonarchBaselonRulelons.MinimalPolicyUselonrRulelons ++
        ListSelonarchBaselonRulelons.BlockMutelonPolicyUselonrRulelons ++
        ListSelonarchBaselonRulelons.StrictPolicyUselonrRulelons
    )

caselon objelonct ListSubscriptionsPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = UselonrTimelonlinelonRulelons.UselonrRulelons
    )

caselon objelonct ListMelonmbelonrshipsPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = UselonrTimelonlinelonRulelons.UselonrRulelons
    )

caselon objelonct AllSubscribelondListsPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = UselonrTimelonlinelonRulelons.UselonrRulelons
    )

caselon objelonct ListHelonadelonrPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = Selonq(
        AuthorBlocksVielonwelonrDropRulelon,
        ProtelonctelondAuthorDropRulelon,
        SuspelonndelondAuthorRulelon
      )
    )

caselon objelonct NelonwUselonrelonxpelonrielonncelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        AbusivelonTwelonelontLabelonlRulelon,
        LowQualityTwelonelontLabelonlDropRulelon,
        NsfaHighReloncallTwelonelontLabelonlRulelon,
        NsfwHighPreloncisionTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon,
        NsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        NsfwCardImagelonTwelonelontLabelonlRulelon,
        NsfwHighReloncallTwelonelontLabelonlRulelon,
        NsfwVidelonoTwelonelontLabelonlDropRulelon,
        NsfwTelonxtTwelonelontLabelonlDropRulelon,
        SpamHighReloncallTwelonelontLabelonlDropRulelon,
        DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
        GorelonAndViolelonncelonTwelonelontLabelonlRulelon,
        UntrustelondUrlTwelonelontLabelonlRulelon,
        DownrankSpamRelonplyTwelonelontLabelonlRulelon,
        SelonarchBlacklistTwelonelontLabelonlRulelon,
        AutomationTwelonelontLabelonlRulelon,
        DuplicatelonMelonntionTwelonelontLabelonlRulelon,
        BystandelonrAbusivelonTwelonelontLabelonlRulelon,
        SafelontyCrisisLelonvelonl3DropRulelon,
        SafelontyCrisisLelonvelonl4DropRulelon,
        DoNotAmplifyDropRulelon,
        SmytelonSpamTwelonelontLabelonlDropRulelon,
      ),
      uselonrRulelons = Selonq(
        AbusivelonRulelon,
        LowQualityRulelon,
        RelonadOnlyRulelon,
        SelonarchBlacklistRulelon,
        SelonarchNsfwTelonxtRulelon,
        CompromiselondRulelon,
        SpamHighReloncallRulelon,
        DuplicatelonContelonntRulelon,
        NsfwHighPreloncisionRulelon,
        NsfwAvatarImagelonRulelon,
        NsfwBannelonrImagelonRulelon,
        AbusivelonHighReloncallRulelon,
        DoNotAmplifyNonFollowelonrRulelon,
        NotGraduatelondNonFollowelonrRulelon,
        LikelonlyIvsLabelonlNonFollowelonrDropUselonrRulelon,
        DownrankSpamRelonplyNonAuthorRulelon,
        NsfwTelonxtNonAuthorDropRulelon
      )
    )

caselon objelonct DelonSHomelonTimelonlinelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        DropStalelonTwelonelontsRulelon,
        DropAllelonxclusivelonTwelonelontsRulelon,
        DropAllTrustelondFrielonndsTwelonelontsRulelon,
        DropAllCommunityTwelonelontsRulelon
      ) ++
        VisibilityPolicy.baselonTwelonelontRulelons,
      uselonrRulelons = UselonrTimelonlinelonRulelons.UselonrRulelons
    )

caselon objelonct DelonsQuotelonTwelonelontTimelonlinelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        DropStalelonTwelonelontsRulelon,
        DropAllelonxclusivelonTwelonelontsRulelon,
        DropAllTrustelondFrielonndsTwelonelontsRulelon
      ) ++ elonlelonvatelondQuotelonTwelonelontTimelonlinelonPolicy.twelonelontRulelons.diff(Selonq(DropStalelonTwelonelontsRulelon)),
      uselonrRulelons = Selonq(
        ProtelonctelondAuthorDropRulelon
      ),
      policyRulelonParams = elonlelonvatelondQuotelonTwelonelontTimelonlinelonPolicy.policyRulelonParams
    )

caselon objelonct DelonSRelonaltimelonSpamelonnrichmelonntPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        LowQualityTwelonelontLabelonlDropRulelon,
        SpamHighReloncallTwelonelontLabelonlDropRulelon,
        DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
        SelonarchBlacklistTwelonelontLabelonlRulelon,
        SmytelonSpamTwelonelontLabelonlDropRulelon,
        DropAllCommunityTwelonelontsRulelon,
        DropAllelonxclusivelonTwelonelontsRulelon,
        DropAllTrustelondFrielonndsTwelonelontsRulelon,
        NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
        NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
        NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon
      )
    )

caselon objelonct DelonSRelonaltimelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        DropAllCommunityTwelonelontsRulelon,
        DropAllelonxclusivelonTwelonelontsRulelon,
        DropAllTrustelondFrielonndsTwelonelontsRulelon,
        DropAllCollabInvitationTwelonelontsRulelon
      ),
      uselonrRulelons = Selonq(
        DropAllProtelonctelondAuthorRulelon,
        DropProtelonctelondVielonwelonrIfPrelonselonntRulelon
      )
    )

caselon objelonct DelonSRelontwelonelontingUselonrsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        DropAllelonxclusivelonTwelonelontsRulelon,
        DropAllTrustelondFrielonndsTwelonelontsRulelon,
      ),
      uselonrRulelons = Selonq(
        AuthorBlocksVielonwelonrDropRulelon,
        VielonwelonrBlocksAuthorRulelon,
        ProtelonctelondAuthorDropRulelon,
        SuspelonndelondAuthorRulelon
      )
    )

caselon objelonct DelonSTwelonelontLikingUselonrsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        DropAllelonxclusivelonTwelonelontsRulelon,
        DropAllTrustelondFrielonndsTwelonelontsRulelon,
      ),
      uselonrRulelons = TimelonlinelonLikelondByRulelons.UselonrRulelons
    )

caselon objelonct DelonSUselonrBookmarksPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        DropAllelonxclusivelonTwelonelontsRulelon,
        DropAllTrustelondFrielonndsTwelonelontsRulelon,
      ) ++
        (VisibilityPolicy.baselonTwelonelontRulelons
          ++ Selonq(DropAllCommunityTwelonelontsRulelon)
          ++ TimelonlinelonProfilelonRulelons.twelonelontRulelons),
      uselonrRulelons = UselonrTimelonlinelonRulelons.UselonrRulelons
    )

caselon objelonct DelonSUselonrLikelondTwelonelontsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        DropStalelonTwelonelontsRulelon,
        DropAllelonxclusivelonTwelonelontsRulelon,
        DropAllTrustelondFrielonndsTwelonelontsRulelon,
      ) ++
        (
          VisibilityPolicy.baselonTwelonelontRulelons ++
            Selonq(
              DropAllCommunityTwelonelontsRulelon,
              AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
              elonmelonrgelonncyDynamicIntelonrstitialRulelon,
              RelonportelondTwelonelontIntelonrstitialRulelon,
              NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
              GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
              NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
              GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
              NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
              NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon,
              NsfwHighReloncallTwelonelontLabelonlAvoidRulelon,
              GorelonAndViolelonncelonHighPreloncisionAvoidAllUselonrsTwelonelontLabelonlRulelon,
              NsfwRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
              GorelonAndViolelonncelonRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
              NsfwCardImagelonAvoidAllUselonrsTwelonelontLabelonlRulelon,
              DoNotAmplifyTwelonelontLabelonlAvoidRulelon,
              NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon,
            ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
        ),
      uselonrRulelons = UselonrTimelonlinelonRulelons.UselonrRulelons
    )

caselon objelonct DelonSUselonrMelonntionsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        DropAllCommunityTwelonelontsRulelon,
        AuthorBlocksVielonwelonrDropRulelon,
        ProtelonctelondAuthorDropRulelon,
        DropAllelonxclusivelonTwelonelontsRulelon,
        DropAllTrustelondFrielonndsTwelonelontsRulelon,
        AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
        elonmelonrgelonncyDynamicIntelonrstitialRulelon,
        NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
        NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
        NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
      ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      uselonrRulelons = Selonq(
        SuspelonndelondAuthorRulelon
      )
    )

caselon objelonct DelonSUselonrTwelonelontsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        DropStalelonTwelonelontsRulelon,
        DropAllelonxclusivelonTwelonelontsRulelon,
        DropAllTrustelondFrielonndsTwelonelontsRulelon,
      ) ++
        (VisibilityPolicy.baselonTwelonelontRulelons
          ++ Selonq(DropAllCommunityTwelonelontsRulelon)
          ++ TimelonlinelonProfilelonRulelons.twelonelontRulelons),
      uselonrRulelons = UselonrTimelonlinelonRulelons.UselonrRulelons
    )

caselon objelonct DelonvPlatformCompliancelonStrelonamPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        SpamAllUselonrsTwelonelontLabelonlRulelon,
        PdnaAllUselonrsTwelonelontLabelonlRulelon,
        BouncelonAllUselonrsTwelonelontLabelonlRulelon,
        AbuselonPolicyelonpisodicTwelonelontLabelonlCompliancelonTwelonelontNoticelonRulelon,
      )
    )

caselon objelonct DelonsTwelonelontDelontailPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        DropAllelonxclusivelonTwelonelontsRulelon,
        DropAllTrustelondFrielonndsTwelonelontsRulelon,
      ) ++ BaselonTwelonelontDelontailPolicy.twelonelontRulelons
    )

caselon objelonct DelonvPlatformGelontListTwelonelontsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(DropStalelonTwelonelontsRulelon) ++ DelonsTwelonelontDelontailPolicy.twelonelontRulelons
    )

caselon objelonct FollowelonrConnelonctionsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons,
      uselonrRulelons = Selonq(
        SpammyFollowelonrRulelon
      )
    )

caselon objelonct SupelonrFollowelonrConnelonctionsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons,
      uselonrRulelons = Selonq(
        SpammyFollowelonrRulelon
      )
    )

caselon objelonct LivelonPipelonlinelonelonngagelonmelonntCountsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
        elonmelonrgelonncyDynamicIntelonrstitialRulelon,
      ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct LivelonVidelonoTimelonlinelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        AbusivelonTwelonelontLabelonlRulelon,
        AbusivelonHighReloncallTwelonelontLabelonlRulelon,
        LowQualityTwelonelontLabelonlDropRulelon,
        NsfwHighPreloncisionTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon,
        NsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        NsfwCardImagelonTwelonelontLabelonlRulelon,
        NsfwHighReloncallTwelonelontLabelonlRulelon,
        NsfwVidelonoTwelonelontLabelonlDropRulelon,
        NsfwTelonxtTwelonelontLabelonlDropRulelon,
        LivelonLowQualityTwelonelontLabelonlRulelon,
        SpamHighReloncallTwelonelontLabelonlDropRulelon,
        DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
        SelonarchBlacklistTwelonelontLabelonlRulelon,
        BystandelonrAbusivelonTwelonelontLabelonlRulelon,
        SafelontyCrisisLelonvelonl3DropRulelon,
        SafelontyCrisisLelonvelonl4DropRulelon,
        DoNotAmplifyDropRulelon,
        SmytelonSpamTwelonelontLabelonlDropRulelon,
        AbuselonPolicyelonpisodicTwelonelontLabelonlDropRulelon,
        elonmelonrgelonncyDropRulelon,
      ),
      uselonrRulelons = Selonq(
        AbusivelonRulelon,
        LowQualityRulelon,
        RelonadOnlyRulelon,
        SelonarchBlacklistRulelon,
        SelonarchNsfwTelonxtRulelon,
        CompromiselondRulelon,
        NsfwHighPreloncisionRulelon,
        NsfwHighReloncallRulelon,
        NsfwAvatarImagelonRulelon,
        NsfwBannelonrImagelonRulelon,
        SpamHighReloncallRulelon,
        DuplicatelonContelonntRulelon,
        LivelonLowQualityRulelon,
        elonngagelonmelonntSpammelonrRulelon,
        elonngagelonmelonntSpammelonrHighReloncallRulelon,
        AbusivelonHighReloncallRulelon,
        DoNotAmplifyNonFollowelonrRulelon,
        NotGraduatelondNonFollowelonrRulelon,
        LikelonlyIvsLabelonlNonFollowelonrDropUselonrRulelon,
        NsfwTelonxtNonAuthorDropRulelon
      )
    )

caselon objelonct MagicReloncsPolicyOvelonrridelons {
  val relonplacelonmelonnts: Map[Rulelon, Rulelon] = Map()
  delonf union(rulelons: Selonq[Rulelon]*): Selonq[Rulelon] = rulelons
    .map(ar => ar.map(x => relonplacelonmelonnts.gelontOrelonlselon(x, x)))
    .relonducelon((a, b) => a ++ b.filtelonrNot(a.contains))
}

caselon objelonct MagicReloncsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = MagicReloncsPolicyOvelonrridelons.union(
        ReloncommelonndationsPolicy.twelonelontRulelons.filtelonrNot(_ == SafelontyCrisisLelonvelonl3DropRulelon),
        NotificationsIbisPolicy.twelonelontRulelons,
        Selonq(NsfaHighReloncallTwelonelontLabelonlRulelon, NsfwHighReloncallTwelonelontLabelonlRulelon),
        Selonq(
          AuthorBlocksVielonwelonrDropRulelon,
          VielonwelonrBlocksAuthorRulelon,
          VielonwelonrMutelonsAuthorRulelon
        ),
        Selonq(
          DelonactivatelondAuthorRulelon,
          SuspelonndelondAuthorRulelon,
          TwelonelontNsfwUselonrDropRulelon,
          TwelonelontNsfwAdminDropRulelon
        )
      ),
      uselonrRulelons = MagicReloncsPolicyOvelonrridelons.union(
        ReloncommelonndationsPolicy.uselonrRulelons,
        NotificationsRulelons.uselonrRulelons
      )
    )

caselon objelonct MagicReloncsV2Policy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = MagicReloncsPolicyOvelonrridelons.union(
        MagicReloncsPolicy.twelonelontRulelons,
        NotificationsWritelonrTwelonelontHydratorPolicy.twelonelontRulelons
      ),
      uselonrRulelons = MagicReloncsPolicyOvelonrridelons.union(
        MagicReloncsPolicy.uselonrRulelons,
        NotificationsWritelonrV2Policy.uselonrRulelons
      )
    )

caselon objelonct MagicReloncsAggrelonssivelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = MagicReloncsPolicy.twelonelontRulelons,
      uselonrRulelons = MagicReloncsPolicy.uselonrRulelons
    )

caselon objelonct MagicReloncsAggrelonssivelonV2Policy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = MagicReloncsV2Policy.twelonelontRulelons,
      uselonrRulelons = MagicReloncsV2Policy.uselonrRulelons
    )

caselon objelonct MinimalPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons,
      uselonrRulelons = Selonq(
        TsViolationRulelon
      )
    )

caselon objelonct ModelonratelondTwelonelontsTimelonlinelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = TwelonelontDelontailPolicy.twelonelontRulelons.diff(
        Selonq(
          AuthorBlocksVielonwelonrDropRulelon,
          MutelondKelonywordForTwelonelontRelonplielonsIntelonrstitialRulelon,
          RelonportelondTwelonelontIntelonrstitialRulelon)),
      policyRulelonParams = TwelonelontDelontailPolicy.policyRulelonParams
    )

caselon objelonct MomelonntsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          AuthorBlocksVielonwelonrUnspeloncifielondRulelon,
          AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
          elonmelonrgelonncyDynamicIntelonrstitialRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
        ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct NelonarbyTimelonlinelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = SelonarchBlelonndelonrRulelons.twelonelontRelonlelonvancelonRulelons,
      uselonrRulelons = SelonarchBlelonndelonrRulelons.uselonrBaselonRulelons
    )

privatelon objelonct NotificationsRulelons {
  val twelonelontRulelons: Selonq[Rulelon] =
    DropStalelonTwelonelontsRulelon +: VisibilityPolicy.baselonTwelonelontRulelons

  val uselonrRulelons: Selonq[Rulelon] = Selonq(
    AbusivelonRulelon,
    LowQualityRulelon,
    RelonadOnlyRulelon,
    CompromiselondRulelon,
    SpamHighReloncallRulelon,
    DuplicatelonContelonntRulelon,
    AbusivelonHighReloncallRulelon,
    elonngagelonmelonntSpammelonrNonFollowelonrWithUqfRulelon,
    elonngagelonmelonntSpammelonrHighReloncallNonFollowelonrWithUqfRulelon,
    DownrankSpamRelonplyNonFollowelonrWithUqfRulelon
  )
}

caselon objelonct NotificationsIbisPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons =
          VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
          AbusivelonUqfNonFollowelonrTwelonelontLabelonlRulelon,
          LowQualityTwelonelontLabelonlDropRulelon,
          ToxicityRelonplyFiltelonrDropNotificationRulelon,
          NsfwHighPreloncisionTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
          NsfwCardImagelonTwelonelontLabelonlRulelon,
          NsfwVidelonoTwelonelontLabelonlDropRulelon,
          NsfwTelonxtTwelonelontLabelonlDropRulelon,
          SpamHighReloncallTwelonelontLabelonlDropRulelon,
          DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
          DuplicatelonMelonntionTwelonelontLabelonlRulelon,
          LowQualityMelonntionTwelonelontLabelonlRulelon,
          UntrustelondUrlUqfNonFollowelonrTwelonelontLabelonlRulelon,
          DownrankSpamRelonplyUqfNonFollowelonrTwelonelontLabelonlRulelon,
          SafelontyCrisisAnyLelonvelonlDropRulelon,
          DoNotAmplifyDropRulelon,
          SmytelonSpamTwelonelontLabelonlDropRulelon,
          AbuselonPolicyelonpisodicTwelonelontLabelonlDropRulelon,
          elonmelonrgelonncyDropRulelon,
        ),
      uselonrRulelons = NotificationsRulelons.uselonrRulelons ++ Selonq(
        DoNotAmplifyNonFollowelonrRulelon,
        LikelonlyIvsLabelonlNonFollowelonrDropUselonrRulelon,
        NsfwTelonxtNonAuthorDropRulelon
      )
    )

caselon objelonct NotificationsRelonadPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = NotificationsRulelons.twelonelontRulelons,
      uselonrRulelons = NotificationsRulelons.uselonrRulelons
    )

caselon objelonct NotificationsTimelonlinelonDelonvicelonFollowPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons,
      uselonrRulelons = Selonq(
        AuthorBlocksVielonwelonrDropRulelon,
        VielonwelonrBlocksAuthorRulelon,
        CompromiselondRulelon
      )
    )

caselon objelonct NotificationsWritelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = NotificationsRulelons.twelonelontRulelons,
      uselonrRulelons = NotificationsRulelons.uselonrRulelons
    )

caselon objelonct NotificationsWritelonrV2Policy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons =
        Selonq(
          AuthorBlocksVielonwelonrDropRulelon,
          DelonactivatelondAuthorRulelon,
          elonraselondAuthorRulelon,
          ProtelonctelondAuthorDropRulelon,
          SuspelonndelondAuthorRulelon,
          DelonactivatelondVielonwelonrRulelon,
          SuspelonndelondVielonwelonrRulelon,
          VielonwelonrBlocksAuthorRulelon,
          VielonwelonrMutelonsAndDoelonsNotFollowAuthorRulelon,
          VielonwelonrIsUnmelonntionelondRulelon,
          NoConfirmelondelonmailRulelon,
          NoConfirmelondPhonelonRulelon,
          NoDelonfaultProfilelonImagelonRulelon,
          NoNelonwUselonrsRulelon,
          NoNotFollowelondByRulelon,
          OnlyPelonoplelonIFollowRulelon
        ) ++
          NotificationsRulelons.uselonrRulelons
    )

caselon objelonct NotificationsWritelonrTwelonelontHydratorPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = NotificationsRulelons.twelonelontRulelons ++
        Selonq(
          LowQualityTwelonelontLabelonlDropRulelon,
          SpamHighReloncallTwelonelontLabelonlDropRulelon,
          DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
          DuplicatelonMelonntionUqfTwelonelontLabelonlRulelon,
          LowQualityMelonntionTwelonelontLabelonlRulelon,
          SmytelonSpamTwelonelontLabelonlDropRulelon,
          ToxicityRelonplyFiltelonrDropNotificationRulelon,
          AbusivelonUqfNonFollowelonrTwelonelontLabelonlRulelon,
          UntrustelondUrlUqfNonFollowelonrTwelonelontLabelonlRulelon,
          DownrankSpamRelonplyUqfNonFollowelonrTwelonelontLabelonlRulelon,
          VielonwelonrHasMatchingMutelondKelonywordForNotificationsRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
        ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct NotificationsPlatformPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = NotificationsWritelonrTwelonelontHydratorPolicy.twelonelontRulelons,
      uselonrRulelons = NotificationsWritelonrV2Policy.uselonrRulelons
    )

caselon objelonct NotificationsPlatformPushPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = NotificationsIbisPolicy.twelonelontRulelons,
      uselonrRulelons = Selonq(VielonwelonrMutelonsAuthorRulelon)
        ++ NotificationsIbisPolicy.uselonrRulelons
    )

caselon objelonct QuotelonTwelonelontTimelonlinelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        DropStalelonTwelonelontsRulelon,
        AbusivelonTwelonelontLabelonlRulelon,
        LowQualityTwelonelontLabelonlDropRulelon,
        NsfwHighPreloncisionTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon,
        NsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        NsfwCardImagelonTwelonelontLabelonlRulelon,
        NsfwHighReloncallTwelonelontLabelonlRulelon,
        NsfwVidelonoTwelonelontLabelonlDropRulelon,
        NsfwTelonxtTwelonelontLabelonlDropRulelon,
        SpamHighReloncallTwelonelontLabelonlDropRulelon,
        DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
        GorelonAndViolelonncelonTwelonelontLabelonlRulelon,
        UntrustelondUrlTwelonelontLabelonlRulelon,
        DownrankSpamRelonplyTwelonelontLabelonlRulelon,
        SelonarchBlacklistTwelonelontLabelonlRulelon,
        AutomationTwelonelontLabelonlRulelon,
        DuplicatelonMelonntionTwelonelontLabelonlRulelon,
        BystandelonrAbusivelonTwelonelontLabelonlRulelon,
        SmytelonSpamTwelonelontLabelonlDropRulelon,
        AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
        elonmelonrgelonncyDynamicIntelonrstitialRulelon,
      ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      uselonrRulelons = Selonq(
        AbusivelonRulelon,
        LowQualityRulelon,
        RelonadOnlyRulelon,
        SelonarchBlacklistRulelon,
        SelonarchNsfwTelonxtRulelon,
        CompromiselondRulelon,
        SpamHighReloncallRulelon,
        DuplicatelonContelonntRulelon,
        NsfwHighPreloncisionRulelon,
        NsfwAvatarImagelonRulelon,
        NsfwBannelonrImagelonRulelon,
        AbusivelonHighReloncallRulelon,
        DownrankSpamRelonplyNonAuthorRulelon,
        NsfwTelonxtNonAuthorDropRulelon
      )
    )

caselon objelonct elonlelonvatelondQuotelonTwelonelontTimelonlinelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons =
          TwelonelontDelontailPolicy.twelonelontRulelons.diff(
            Selonq(
              MutelondKelonywordForQuotelondTwelonelontTwelonelontDelontailIntelonrstitialRulelon,
              RelonportelondTwelonelontIntelonrstitialRulelon)),
      policyRulelonParams = TwelonelontDelontailPolicy.policyRulelonParams
    )

caselon objelonct elonmbelondsPublicIntelonrelonstNoticelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
        elonmelonrgelonncyDynamicIntelonrstitialRulelon,
      )
    )

caselon objelonct ReloncommelonndationsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          AbusivelonTwelonelontLabelonlRulelon,
          LowQualityTwelonelontLabelonlDropRulelon,
          NsfwHighPreloncisionTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
          NsfwCardImagelonTwelonelontLabelonlRulelon,
          NsfwVidelonoTwelonelontLabelonlDropRulelon,
          NsfwTelonxtTwelonelontLabelonlDropRulelon,
          SpamHighReloncallTwelonelontLabelonlDropRulelon,
          DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
          GorelonAndViolelonncelonTwelonelontLabelonlRulelon,
          BystandelonrAbusivelonTwelonelontLabelonlRulelon,
          DoNotAmplifyDropRulelon,
          SafelontyCrisisLelonvelonl3DropRulelon,
          SmytelonSpamTwelonelontLabelonlDropRulelon,
          AbuselonPolicyelonpisodicTwelonelontLabelonlDropRulelon,
          elonmelonrgelonncyDropRulelon,
        ),
      uselonrRulelons = Selonq(
        DropNsfwAdminAuthorRulelon,
        AbusivelonRulelon,
        LowQualityRulelon,
        RelonadOnlyRulelon,
        CompromiselondRulelon,
        ReloncommelonndationsBlacklistRulelon,
        SpamHighReloncallRulelon,
        DuplicatelonContelonntRulelon,
        NsfwHighPreloncisionRulelon,
        NsfwNelonarPelonrfelonctAuthorRulelon,
        NsfwBannelonrImagelonRulelon,
        NsfwAvatarImagelonRulelon,
        elonngagelonmelonntSpammelonrRulelon,
        elonngagelonmelonntSpammelonrHighReloncallRulelon,
        AbusivelonHighReloncallRulelon,
        DoNotAmplifyNonFollowelonrRulelon,
        NotGraduatelondNonFollowelonrRulelon,
        LikelonlyIvsLabelonlNonFollowelonrDropUselonrRulelon,
        NsfwTelonxtNonAuthorDropRulelon
      )
    )

caselon objelonct ReloncosVidelonoPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        AbusivelonTwelonelontLabelonlRulelon,
        LowQualityTwelonelontLabelonlDropRulelon,
        NsfwHighPreloncisionTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon,
        NsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        NsfwCardImagelonTwelonelontLabelonlRulelon,
        NsfwHighReloncallTwelonelontLabelonlRulelon,
        NsfwVidelonoTwelonelontLabelonlDropRulelon,
        NsfwTelonxtTwelonelontLabelonlDropRulelon,
        SpamHighReloncallTwelonelontLabelonlDropRulelon,
        DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
        BystandelonrAbusivelonTwelonelontLabelonlRulelon,
        SmytelonSpamTwelonelontLabelonlDropRulelon,
      ),
      uselonrRulelons = Selonq(NsfwTelonxtNonAuthorDropRulelon)
    )

caselon objelonct RelonplielonsGroupingPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          LowQualityTwelonelontLabelonlDropRulelon,
          SpamHighReloncallTwelonelontLabelonlDropRulelon,
          DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
          DeloncidelonrablelonSpamHighReloncallAuthorLabelonlDropRulelon,
          SmytelonSpamTwelonelontLabelonlDropRulelon,
          AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
          elonmelonrgelonncyDynamicIntelonrstitialRulelon,
          MutelondKelonywordForTwelonelontRelonplielonsIntelonrstitialRulelon,
          RelonportelondTwelonelontIntelonrstitialRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
          NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon,
          NsfwHighReloncallTwelonelontLabelonlAvoidRulelon,
          GorelonAndViolelonncelonHighPreloncisionAvoidAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAvoidAdPlacelonmelonntAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAvoidAdPlacelonmelonntAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAvoidAdPlacelonmelonntAllUselonrsTwelonelontLabelonlRulelon,
          DoNotAmplifyTwelonelontLabelonlAvoidRulelon,
          NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon,
        ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      uselonrRulelons = Selonq(
        LowQualityRulelon,
        RelonadOnlyRulelon,
        LowQualityHighReloncallRulelon,
        CompromiselondRulelon,
        DeloncidelonrablelonSpamHighReloncallRulelon
      )
    )

caselon objelonct RelonturningUselonrelonxpelonrielonncelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        AbusivelonTwelonelontLabelonlRulelon,
        LowQualityTwelonelontLabelonlDropRulelon,
        NsfaHighReloncallTwelonelontLabelonlRulelon,
        NsfwHighPreloncisionTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon,
        NsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        NsfwCardImagelonTwelonelontLabelonlRulelon,
        NsfwHighReloncallTwelonelontLabelonlRulelon,
        NsfwVidelonoTwelonelontLabelonlDropRulelon,
        NsfwTelonxtTwelonelontLabelonlDropRulelon,
        NsfwTelonxtTwelonelontLabelonlTopicsDropRulelon,
        SpamHighReloncallTwelonelontLabelonlDropRulelon,
        DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
        GorelonAndViolelonncelonTwelonelontLabelonlRulelon,
        UntrustelondUrlTwelonelontLabelonlRulelon,
        DownrankSpamRelonplyTwelonelontLabelonlRulelon,
        SelonarchBlacklistTwelonelontLabelonlRulelon,
        AutomationTwelonelontLabelonlRulelon,
        DuplicatelonMelonntionTwelonelontLabelonlRulelon,
        BystandelonrAbusivelonTwelonelontLabelonlRulelon,
        SmytelonSpamTwelonelontLabelonlDropRulelon,
        SafelontyCrisisLelonvelonl3DropRulelon,
        SafelontyCrisisLelonvelonl4DropRulelon,
        DoNotAmplifyDropRulelon,
        AbuselonPolicyelonpisodicTwelonelontLabelonlDropRulelon,
        elonmelonrgelonncyDropRulelon,
      ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      uselonrRulelons = Selonq(
        AbusivelonRulelon,
        LowQualityRulelon,
        RelonadOnlyRulelon,
        SelonarchBlacklistRulelon,
        SelonarchNsfwTelonxtRulelon,
        CompromiselondRulelon,
        SpamHighReloncallRulelon,
        DuplicatelonContelonntRulelon,
        NsfwHighPreloncisionRulelon,
        NsfwAvatarImagelonRulelon,
        NsfwBannelonrImagelonRulelon,
        AbusivelonHighReloncallRulelon,
        DoNotAmplifyNonFollowelonrRulelon,
        NotGraduatelondNonFollowelonrRulelon,
        LikelonlyIvsLabelonlNonFollowelonrDropUselonrRulelon,
        DownrankSpamRelonplyNonAuthorRulelon,
        NsfwTelonxtNonAuthorDropRulelon,
        DropNsfwUselonrAuthorRulelon,
        NsfwHighReloncallRulelon
      )
    )

caselon objelonct RelonturningUselonrelonxpelonrielonncelonFocalTwelonelontPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          AuthorBlocksVielonwelonrDropRulelon,
          AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
          elonmelonrgelonncyDynamicIntelonrstitialRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
          MutelondKelonywordForTwelonelontRelonplielonsIntelonrstitialRulelon,
          VielonwelonrMutelonsAuthorIntelonrstitialRulelon,
          RelonportelondTwelonelontIntelonrstitialRulelon,
        ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct RelonvelonnuelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          AbusivelonTwelonelontLabelonlRulelon,
          BystandelonrAbusivelonTwelonelontLabelonlRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon
        )
    )

caselon objelonct SafelonSelonarchMinimalPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        DropOutelonrCommunityTwelonelontsRulelon,
      ) ++ VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        LowQualityTwelonelontLabelonlDropRulelon,
        HighProactivelonTosScorelonTwelonelontLabelonlDropSelonarchRulelon,
        SpamHighReloncallTwelonelontLabelonlDropRulelon,
        DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
        SelonarchBlacklistTwelonelontLabelonlRulelon,
        SelonarchBlacklistHighReloncallTwelonelontLabelonlDropRulelon,
        SafelontyCrisisLelonvelonl3DropRulelon,
        SafelontyCrisisLelonvelonl4DropRulelon,
        DoNotAmplifyDropRulelon,
        SmytelonSpamTwelonelontLabelonlDropRulelon,
      ) ++
        Selonq(
          AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
          elonmelonrgelonncyDynamicIntelonrstitialRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
        ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
        ++ SelonarchBlelonndelonrRulelons.twelonelontAvoidRulelons,
      uselonrRulelons = Selonq(
        LowQualityRulelon,
        RelonadOnlyRulelon,
        CompromiselondRulelon,
        SpamHighReloncallRulelon,
        SelonarchBlacklistRulelon,
        SelonarchNsfwTelonxtRulelon,
        DuplicatelonContelonntRulelon,
        DoNotAmplifyNonFollowelonrRulelon,
        SelonarchLikelonlyIvsLabelonlNonFollowelonrDropUselonrRulelon
      )
    )

caselon objelonct SelonarchHydrationPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
        elonmelonrgelonncyDynamicIntelonrstitialRulelon,
        RelonportelondTwelonelontIntelonrstitialSelonarchRulelon,
        NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
        NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
        NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
      ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct SelonarchBlelonndelonrRulelons {
  val limitelondelonngagelonmelonntBaselonRulelons: Selonq[Rulelon] = LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons

  val twelonelontAvoidRulelons: Selonq[Rulelon] =
    Selonq(
      NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon,
      NsfwHighReloncallTwelonelontLabelonlAvoidRulelon,
      GorelonAndViolelonncelonHighPreloncisionAvoidAllUselonrsTwelonelontLabelonlRulelon,
      NsfwRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
      GorelonAndViolelonncelonRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
      NsfwCardImagelonAvoidAllUselonrsTwelonelontLabelonlRulelon,
      SelonarchAvoidTwelonelontNsfwAdminRulelon,
      SelonarchAvoidTwelonelontNsfwUselonrRulelon,
      DoNotAmplifyTwelonelontLabelonlAvoidRulelon,
      NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon,
    )

  val basicBlockMutelonRulelons: Selonq[Rulelon] = Selonq(
    AuthorBlocksVielonwelonrDropRulelon,
    VielonwelonrBlocksAuthorVielonwelonrOptInBlockingOnSelonarchRulelon,
    VielonwelonrMutelonsAuthorVielonwelonrOptInBlockingOnSelonarchRulelon
  )

  val twelonelontRelonlelonvancelonRulelons: Selonq[Rulelon] =
    Selonq(
      DropOutelonrCommunityTwelonelontsRulelon,
      DropStalelonTwelonelontsRulelon,
    ) ++ VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
      SafelonSelonarchAbusivelonTwelonelontLabelonlRulelon,
      LowQualityTwelonelontLabelonlDropRulelon,
      HighProactivelonTosScorelonTwelonelontLabelonlDropSelonarchRulelon,
      HighPSpammyTwelonelontScorelonSelonarchTwelonelontLabelonlDropRulelon,
      HighSpammyTwelonelontContelonntScorelonSelonarchTopTwelonelontLabelonlDropRulelon,
      HighSpammyTwelonelontContelonntScorelonTrelonndsTopTwelonelontLabelonlDropRulelon,
      SafelonSelonarchNsfwHighPreloncisionTwelonelontLabelonlRulelon,
      SafelonSelonarchGorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon,
      SafelonSelonarchNsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
      SafelonSelonarchGorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
      SafelonSelonarchNsfwCardImagelonTwelonelontLabelonlRulelon,
      SafelonSelonarchNsfwHighReloncallTwelonelontLabelonlRulelon,
      SafelonSelonarchNsfwVidelonoTwelonelontLabelonlRulelon,
      SafelonSelonarchNsfwTelonxtTwelonelontLabelonlRulelon,
      SpamHighReloncallTwelonelontLabelonlDropRulelon,
      DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
      SafelonSelonarchGorelonAndViolelonncelonTwelonelontLabelonlRulelon,
      SafelonSelonarchUntrustelondUrlTwelonelontLabelonlRulelon,
      SafelonSelonarchDownrankSpamRelonplyTwelonelontLabelonlRulelon,
      SelonarchBlacklistTwelonelontLabelonlRulelon,
      SelonarchBlacklistHighReloncallTwelonelontLabelonlDropRulelon,
      SmytelonSpamTwelonelontLabelonlDropSelonarchRulelon,
      CopypastaSpamAllVielonwelonrsSelonarchTwelonelontLabelonlRulelon,
    ) ++ basicBlockMutelonRulelons ++
      Selonq(
        SafelonSelonarchAutomationNonFollowelonrTwelonelontLabelonlRulelon,
        SafelonSelonarchDuplicatelonMelonntionNonFollowelonrTwelonelontLabelonlRulelon,
        SafelonSelonarchBystandelonrAbusivelonTwelonelontLabelonlRulelon,
        SafelontyCrisisLelonvelonl3DropRulelon,
        SafelontyCrisisLelonvelonl4DropRulelon,
        DoNotAmplifyDropRulelon,
        SelonarchIpiSafelonSelonarchWithoutUselonrInQuelonryDropRulelon,
        SelonarchelondiSafelonSelonarchWithoutUselonrInQuelonryDropRulelon,
        AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
        elonmelonrgelonncyDynamicIntelonrstitialRulelon,
        UnsafelonSelonarchNsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
        UnsafelonSelonarchGorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
        UnsafelonSelonarchNsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
        UnsafelonSelonarchGorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
        UnsafelonSelonarchNsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
      ) ++
      limitelondelonngagelonmelonntBaselonRulelons ++
      twelonelontAvoidRulelons

    VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
    SafelonSelonarchAbusivelonTwelonelontLabelonlRulelon,
    LowQualityTwelonelontLabelonlDropRulelon,
    HighProactivelonTosScorelonTwelonelontLabelonlDropSelonarchRulelon,
    HighSpammyTwelonelontContelonntScorelonSelonarchLatelonstTwelonelontLabelonlDropRulelon,
    HighSpammyTwelonelontContelonntScorelonTrelonndsLatelonstTwelonelontLabelonlDropRulelon,
    SafelonSelonarchNsfwHighPreloncisionTwelonelontLabelonlRulelon,
    SafelonSelonarchGorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon,
    SafelonSelonarchNsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
    SafelonSelonarchGorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
    SafelonSelonarchNsfwCardImagelonTwelonelontLabelonlRulelon,
    SafelonSelonarchNsfwHighReloncallTwelonelontLabelonlRulelon,
    SafelonSelonarchNsfwVidelonoTwelonelontLabelonlRulelon,
    SafelonSelonarchNsfwTelonxtTwelonelontLabelonlRulelon,
    SpamHighReloncallTwelonelontLabelonlDropRulelon,
    DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
    SafelonSelonarchGorelonAndViolelonncelonTwelonelontLabelonlRulelon,
    SafelonSelonarchUntrustelondUrlTwelonelontLabelonlRulelon,
    SafelonSelonarchDownrankSpamRelonplyTwelonelontLabelonlRulelon,
    SelonarchBlacklistTwelonelontLabelonlRulelon,
    SelonarchBlacklistHighReloncallTwelonelontLabelonlDropRulelon,
    SmytelonSpamTwelonelontLabelonlDropSelonarchRulelon,
    CopypastaSpamNonFollowelonrSelonarchTwelonelontLabelonlRulelon,
  ) ++
    basicBlockMutelonRulelons ++
    Selonq(
      SafelonSelonarchAutomationNonFollowelonrTwelonelontLabelonlRulelon,
      SafelonSelonarchDuplicatelonMelonntionNonFollowelonrTwelonelontLabelonlRulelon,
      SafelonSelonarchBystandelonrAbusivelonTwelonelontLabelonlRulelon,
      SafelontyCrisisLelonvelonl3DropRulelon,
      SafelontyCrisisLelonvelonl4DropRulelon,
      SelonarchIpiSafelonSelonarchWithoutUselonrInQuelonryDropRulelon,
      SelonarchelondiSafelonSelonarchWithoutUselonrInQuelonryDropRulelon,
      AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
      elonmelonrgelonncyDynamicIntelonrstitialRulelon,
      UnsafelonSelonarchNsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
      UnsafelonSelonarchGorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
      UnsafelonSelonarchNsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
      UnsafelonSelonarchGorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
      UnsafelonSelonarchNsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
    ) ++ limitelondelonngagelonmelonntBaselonRulelons ++ twelonelontAvoidRulelons

  val uselonrBaselonRulelons: Selonq[ConditionWithUselonrLabelonlRulelon] = Selonq(
    SafelonSelonarchAbusivelonUselonrLabelonlRulelon,
    LowQualityRulelon,
    RelonadOnlyRulelon,
    SelonarchBlacklistRulelon,
    CompromiselondRulelon,
    SpamHighReloncallRulelon,
    DuplicatelonContelonntRulelon,
    DoNotAmplifyNonFollowelonrRulelon,
    SelonarchLikelonlyIvsLabelonlNonFollowelonrDropUselonrRulelon,
    SafelonSelonarchNsfwHighPreloncisionUselonrLabelonlRulelon,
    SafelonSelonarchNsfwAvatarImagelonUselonrLabelonlRulelon,
    SafelonSelonarchNsfwBannelonrImagelonUselonrLabelonlRulelon,
    SafelonSelonarchAbusivelonHighReloncallUselonrLabelonlRulelon,
    SafelonSelonarchDownrankSpamRelonplyAuthorLabelonlRulelon,
    SafelonSelonarchNotGraduatelondNonFollowelonrsUselonrLabelonlRulelon,
    SafelonSelonarchNsfwTelonxtAuthorLabelonlRulelon
  )

  val uselonrRulelons: Selonq[ConditionWithUselonrLabelonlRulelon] = uselonrBaselonRulelons

  val uselonrRelonlelonvancelonBaselonRulelons = uselonrBaselonRulelons ++ basicBlockMutelonRulelons

  val uselonrRelonlelonvancelonRulelons = uselonrRelonlelonvancelonBaselonRulelons

  val uselonrReloncelonncyBaselonRulelons = uselonrBaselonRulelons.filtelonrNot(
    Selonq(DoNotAmplifyNonFollowelonrRulelon, SelonarchLikelonlyIvsLabelonlNonFollowelonrDropUselonrRulelon).contains
  ) ++ basicBlockMutelonRulelons

  val selonarchQuelonryMatchelonsTwelonelontAuthorRulelons: Selonq[ConditionWithUselonrLabelonlRulelon] =
    uselonrBaselonRulelons

  val basicBlockMutelonPolicyRulelonParam: Map[Rulelon, PolicyLelonvelonlRulelonParams] =
    SelonarchBlelonndelonrRulelons.basicBlockMutelonRulelons
      .map(rulelon => rulelon -> rulelonParams(RulelonParams.elonnablelonSelonarchBasicBlockMutelonRulelonsParam)).toMap
}

caselon objelonct SelonarchBlelonndelonrUselonrRulelonsPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = SelonarchBlelonndelonrRulelons.uselonrRulelons
    )

caselon objelonct SelonarchLatelonstUselonrRulelonsPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = SelonarchLatelonstPolicy.uselonrRulelons
    )

caselon objelonct UselonrSelonarchSrpPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = Selonq(
        AuthorBlocksVielonwelonrDropRulelon,
        VielonwelonrBlocksAuthorVielonwelonrOptInBlockingOnSelonarchRulelon,
        VielonwelonrMutelonsAuthorVielonwelonrOptInBlockingOnSelonarchRulelon,
        DropNsfwAdminAuthorVielonwelonrOptInFiltelonringOnSelonarchRulelon,
        SafelonSelonarchAbusivelonUselonrLabelonlRulelon,
        SafelonSelonarchHighReloncallUselonrLabelonlRulelon,
        SafelonSelonarchNsfwNelonarPelonrfelonctAuthorRulelon,
        SafelonSelonarchNsfwHighPreloncisionUselonrLabelonlRulelon,
        SafelonSelonarchNsfwAvatarImagelonUselonrLabelonlRulelon,
        SafelonSelonarchNsfwBannelonrImagelonUselonrLabelonlRulelon,
        SafelonSelonarchAbusivelonHighReloncallUselonrLabelonlRulelon,
        SafelonSelonarchNsfwTelonxtAuthorLabelonlRulelon
      )
    )

caselon objelonct UselonrSelonarchTypelonahelonadPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = Selonq(
        SafelonSelonarchAbusivelonUselonrLabelonlRulelon,
        SafelonSelonarchHighReloncallUselonrLabelonlRulelon,
        SafelonSelonarchNsfwNelonarPelonrfelonctAuthorRulelon,
        SafelonSelonarchNsfwHighPreloncisionUselonrLabelonlRulelon,
        SafelonSelonarchNsfwAvatarImagelonUselonrLabelonlRulelon,
        SafelonSelonarchNsfwBannelonrImagelonUselonrLabelonlRulelon,
        SafelonSelonarchAbusivelonHighReloncallUselonrLabelonlRulelon,
        SafelonSelonarchNsfwTelonxtAuthorLabelonlRulelon
      ),
      twelonelontRulelons = Selonq(DropAllRulelon)
    )

caselon objelonct SelonarchMixelonrSrpMinimalPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = Selonq(
        AuthorBlocksVielonwelonrDropRulelon,
        VielonwelonrBlocksAuthorVielonwelonrOptInBlockingOnSelonarchRulelon,
        VielonwelonrMutelonsAuthorVielonwelonrOptInBlockingOnSelonarchRulelon
      )
    )

caselon objelonct SelonarchMixelonrSrpStrictPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = Selonq(
        AuthorBlocksVielonwelonrDropRulelon,
        VielonwelonrBlocksAuthorVielonwelonrOptInBlockingOnSelonarchRulelon,
        VielonwelonrMutelonsAuthorVielonwelonrOptInBlockingOnSelonarchRulelon,
        DropNsfwAdminAuthorVielonwelonrOptInFiltelonringOnSelonarchRulelon,
        NsfwNelonarPelonrfelonctAuthorRulelon,
        NsfwHighPreloncisionRulelon,
        NsfwHighReloncallRulelon,
        NsfwSelonnsitivelonRulelon,
        NsfwAvatarImagelonRulelon,
        NsfwBannelonrImagelonRulelon
      ) ++ SelonarchBlelonndelonrRulelons.selonarchQuelonryMatchelonsTwelonelontAuthorRulelons
        .diff(Selonq(SafelonSelonarchNotGraduatelondNonFollowelonrsUselonrLabelonlRulelon))
    )

caselon objelonct SelonarchPelonoplelonSrpPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = SelonarchBlelonndelonrRulelons.selonarchQuelonryMatchelonsTwelonelontAuthorRulelons
    )

caselon objelonct SelonarchPelonoplelonTypelonahelonadPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = SelonarchBlelonndelonrRulelons.selonarchQuelonryMatchelonsTwelonelontAuthorRulelons
        .diff(
          Selonq(
            SafelonSelonarchNotGraduatelondNonFollowelonrsUselonrLabelonlRulelon
          )),
      twelonelontRulelons = Selonq(DropAllRulelon)
    )

caselon objelonct SelonarchPhotoPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = SelonarchBlelonndelonrRulelons.twelonelontRelonlelonvancelonRulelons,
      uselonrRulelons = SelonarchBlelonndelonrRulelons.uselonrRelonlelonvancelonRulelons,
      policyRulelonParams = SelonarchBlelonndelonrRulelons.basicBlockMutelonPolicyRulelonParam
    )

caselon objelonct SelonarchTrelonndTakelonovelonrPromotelondTwelonelontPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons
    )

caselon objelonct SelonarchVidelonoPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = SelonarchBlelonndelonrRulelons.twelonelontRelonlelonvancelonRulelons,
      uselonrRulelons = SelonarchBlelonndelonrRulelons.uselonrRelonlelonvancelonRulelons,
      policyRulelonParams = SelonarchBlelonndelonrRulelons.basicBlockMutelonPolicyRulelonParam
    )

caselon objelonct SelonarchLatelonstPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = SelonarchBlelonndelonrRulelons.twelonelontReloncelonncyRulelons,
      uselonrRulelons = SelonarchBlelonndelonrRulelons.uselonrReloncelonncyBaselonRulelons,
      policyRulelonParams = SelonarchBlelonndelonrRulelons.basicBlockMutelonPolicyRulelonParam
    )

caselon objelonct SelonarchTopPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = SelonarchBlelonndelonrRulelons.twelonelontRelonlelonvancelonRulelons,
      uselonrRulelons = Selonq(SpammyUselonrModelonlHighPreloncisionDropTwelonelontRulelon) ++
        SelonarchBlelonndelonrRulelons.basicBlockMutelonRulelons ++
        SelonarchBlelonndelonrRulelons.selonarchQuelonryMatchelonsTwelonelontAuthorRulelons,
      policyRulelonParams = SelonarchBlelonndelonrRulelons.basicBlockMutelonPolicyRulelonParam
    )

caselon objelonct SelonarchTopQigPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = BaselonQigPolicy.twelonelontRulelons ++
        Selonq(
          UnsafelonSelonarchGorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlDropRulelon,
          UnsafelonSelonarchGorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlDropRulelon,
          UnsafelonSelonarchNsfwCardImagelonAllUselonrsTwelonelontLabelonlDropRulelon,
          UnsafelonSelonarchNsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlDropRulelon,
          UnsafelonSelonarchNsfwHighPreloncisionAllUselonrsTwelonelontLabelonlDropRulelon
        ) ++
        SelonarchTopPolicy.twelonelontRulelons.diff(
          Selonq(
            SelonarchIpiSafelonSelonarchWithoutUselonrInQuelonryDropRulelon,
            SelonarchelondiSafelonSelonarchWithoutUselonrInQuelonryDropRulelon,
            HighSpammyTwelonelontContelonntScorelonTrelonndsTopTwelonelontLabelonlDropRulelon,
            UnsafelonSelonarchNsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
            UnsafelonSelonarchGorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
            UnsafelonSelonarchGorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
            UnsafelonSelonarchNsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
            UnsafelonSelonarchNsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon
          ) ++
            SelonarchTopPolicy.twelonelontRulelons.intelonrselonct(BaselonQigPolicy.twelonelontRulelons)),
      uselonrRulelons = BaselonQigPolicy.uselonrRulelons ++ Selonq(
        DropNsfwAdminAuthorVielonwelonrOptInFiltelonringOnSelonarchRulelon,
        NsfwNelonarPelonrfelonctAuthorRulelon,
      ) ++ SelonarchTopPolicy.uselonrRulelons.diff(
        SelonarchTopPolicy.uselonrRulelons.intelonrselonct(BaselonQigPolicy.uselonrRulelons)),
      policyRulelonParams = SelonarchBlelonndelonrRulelons.basicBlockMutelonPolicyRulelonParam
    )

caselon objelonct SafelonSelonarchStrictPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        DropOutelonrCommunityTwelonelontsRulelon,
      ) ++ VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        AbusivelonTwelonelontLabelonlRulelon,
        LowQualityTwelonelontLabelonlDropRulelon,
        HighProactivelonTosScorelonTwelonelontLabelonlDropSelonarchRulelon,
        NsfwHighPreloncisionTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon,
        NsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        NsfwCardImagelonTwelonelontLabelonlRulelon,
        NsfwHighReloncallTwelonelontLabelonlRulelon,
        NsfwVidelonoTwelonelontLabelonlDropRulelon,
        NsfwTelonxtTwelonelontLabelonlDropRulelon,
        SpamHighReloncallTwelonelontLabelonlDropRulelon,
        DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
        GorelonAndViolelonncelonTwelonelontLabelonlRulelon,
        UntrustelondUrlTwelonelontLabelonlRulelon,
        DownrankSpamRelonplyTwelonelontLabelonlRulelon,
        SelonarchBlacklistTwelonelontLabelonlRulelon,
        SelonarchBlacklistHighReloncallTwelonelontLabelonlDropRulelon,
        AutomationTwelonelontLabelonlRulelon,
        DuplicatelonMelonntionTwelonelontLabelonlRulelon,
        BystandelonrAbusivelonTwelonelontLabelonlRulelon,
        SafelontyCrisisLelonvelonl3DropRulelon,
        SafelontyCrisisLelonvelonl4DropRulelon,
        DoNotAmplifyDropRulelon,
        SmytelonSpamTwelonelontLabelonlDropRulelon,
        AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
        elonmelonrgelonncyDynamicIntelonrstitialRulelon,
      ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
        ++ SelonarchBlelonndelonrRulelons.twelonelontAvoidRulelons,
      uselonrRulelons = Selonq(
        AbusivelonRulelon,
        LowQualityRulelon,
        RelonadOnlyRulelon,
        SelonarchBlacklistRulelon,
        SelonarchNsfwTelonxtRulelon,
        CompromiselondRulelon,
        SpamHighReloncallRulelon,
        DuplicatelonContelonntRulelon,
        NsfwHighPreloncisionRulelon,
        NsfwAvatarImagelonRulelon,
        NsfwBannelonrImagelonRulelon,
        AbusivelonHighReloncallRulelon,
        DoNotAmplifyNonFollowelonrRulelon,
        NotGraduatelondNonFollowelonrRulelon,
        SelonarchLikelonlyIvsLabelonlNonFollowelonrDropUselonrRulelon,
        DownrankSpamRelonplyNonAuthorRulelon,
        NsfwTelonxtNonAuthorDropRulelon,
      )
    )

caselon objelonct StickelonrsTimelonlinelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons,
      uselonrRulelons = Selonq(
        AbusivelonRulelon,
        LowQualityRulelon,
        RelonadOnlyRulelon,
        CompromiselondRulelon,
        SelonarchBlacklistRulelon,
        SelonarchNsfwTelonxtRulelon,
        DuplicatelonContelonntRulelon,
        elonngagelonmelonntSpammelonrRulelon,
        elonngagelonmelonntSpammelonrHighReloncallRulelon,
        NsfwSelonnsitivelonRulelon,
        SpamHighReloncallRulelon,
        AbusivelonHighReloncallRulelon
      )
    )

caselon objelonct StratoelonxtLimitelondelonngagelonmelonntsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons =
        VisibilityPolicy.baselonTwelonelontRulelons ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct IntelonrnalPromotelondContelonntPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons
    )

caselon objelonct StrelonamSelonrvicelonsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        AbusivelonTwelonelontLabelonlRulelon,
        LowQualityTwelonelontLabelonlDropRulelon,
        NsfwHighPreloncisionTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon,
        NsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        NsfwCardImagelonTwelonelontLabelonlRulelon,
        NsfwVidelonoTwelonelontLabelonlDropRulelon,
        NsfwTelonxtTwelonelontLabelonlDropRulelon,
        SpamHighReloncallTwelonelontLabelonlDropRulelon,
        DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
        BystandelonrAbusivelonTwelonelontLabelonlRulelon,
        SmytelonSpamTwelonelontLabelonlDropRulelon
      ),
      uselonrRulelons = Selonq(NsfwTelonxtNonAuthorDropRulelon)
    )

caselon objelonct SupelonrLikelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        AbuselonPolicyelonpisodicTwelonelontLabelonlDropRulelon,
        elonmelonrgelonncyDropRulelon,
        NsfwHighPreloncisionTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon,
        NsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        NsfwCardImagelonTwelonelontLabelonlRulelon,
        NsfwVidelonoTwelonelontLabelonlDropRulelon,
        NsfwTelonxtTwelonelontLabelonlDropRulelon
      ),
      uselonrRulelons = Selonq(NsfwTelonxtNonAuthorDropRulelon)
    )

caselon objelonct TimelonlinelonFocalTwelonelontPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
        elonmelonrgelonncyDynamicIntelonrstitialRulelon,
      ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct TimelonlinelonBookmarkPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons =
        Selonq(
          DropCommunityTwelonelontsRulelon,
          DropCommunityTwelonelontCommunityNotVisiblelonRulelon,
          DropProtelonctelondCommunityTwelonelontsRulelon,
          DropHiddelonnCommunityTwelonelontsRulelon,
          DropAuthorRelonmovelondCommunityTwelonelontsRulelon,
          SpamTwelonelontLabelonlRulelon,
          PdnaTwelonelontLabelonlRulelon,
          BouncelonOutelonrTwelonelontTombstonelonRulelon,
          BouncelonQuotelondTwelonelontTombstonelonRulelon,
          DropelonxclusivelonTwelonelontContelonntRulelon,
          DropTrustelondFrielonndsTwelonelontContelonntRulelon,
        ) ++
          Selonq(
            AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
            elonmelonrgelonncyDynamicIntelonrstitialRulelon,
            NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
            GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
            NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
            GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
            VielonwelonrBlocksAuthorInnelonrQuotelondTwelonelontIntelonrstitialRulelon,
            VielonwelonrMutelonsAuthorInnelonrQuotelondTwelonelontIntelonrstitialRulelon,
            NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
          ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      delonlelontelondTwelonelontRulelons = Selonq(
        TombstonelonBouncelonDelonlelontelondTwelonelontRulelon,
        TombstonelonDelonlelontelondQuotelondTwelonelontRulelon
      ),
      uselonrUnavailablelonStatelonRulelons = Selonq(
        SuspelonndelondUselonrUnavailablelonTwelonelontTombstonelonRulelon,
        DelonactivatelondUselonrUnavailablelonTwelonelontTombstonelonRulelon,
        OffBoardelondUselonrUnavailablelonTwelonelontTombstonelonRulelon,
        elonraselondUselonrUnavailablelonTwelonelontTombstonelonRulelon,
        ProtelonctelondUselonrUnavailablelonTwelonelontTombstonelonRulelon,
        AuthorBlocksVielonwelonrUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        UselonrUnavailablelonTwelonelontTombstonelonRulelon,
        VielonwelonrBlocksAuthorUselonrUnavailablelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon,
        VielonwelonrMutelonsAuthorUselonrUnavailablelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon
      ),
    )

caselon objelonct TimelonlinelonListsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons =
        Selonq(
          DropOutelonrCommunityTwelonelontsRulelon,
          DropStalelonTwelonelontsRulelon,
        ) ++
          VisibilityPolicy.baselonTwelonelontRulelons ++
          Selonq(
            AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
            elonmelonrgelonncyDynamicIntelonrstitialRulelon,
            NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
            GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
            NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
            GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
            NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
          ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct TimelonlinelonFavoritelonsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons =
        Selonq(
          DropOutelonrCommunityTwelonelontsRulelon,
          DropStalelonTwelonelontsRulelon,
        )
          ++ TimelonlinelonProfilelonRulelons.baselonTwelonelontRulelons
          ++ Selonq(
            DynamicProductAdDropTwelonelontLabelonlRulelon,
            NsfwHighPreloncisionTombstonelonInnelonrQuotelondTwelonelontLabelonlRulelon,
            SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon,
            SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionDropSelonttingLelonvelonTombstonelonRulelon,
            SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon,
            SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsDropSelonttingLelonvelonlTombstonelonRulelon,
            SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon,
            SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagDropSelonttingLelonvelonlTombstonelonRulelon,
            SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagDropSelonttingLelonvelonlTombstonelonRulelon,
            AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
            elonmelonrgelonncyDynamicIntelonrstitialRulelon,
            RelonportelondTwelonelontIntelonrstitialRulelon,
            VielonwelonrMutelonsAuthorIntelonrstitialRulelon,
            VielonwelonrBlocksAuthorIntelonrstitialRulelon,
            NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
            GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
            NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
            GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
            NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagIntelonrstitialRulelon,
            NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon,
            NsfwHighReloncallTwelonelontLabelonlAvoidRulelon,
            GorelonAndViolelonncelonHighPreloncisionAvoidAllUselonrsTwelonelontLabelonlRulelon,
            NsfwRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
            GorelonAndViolelonncelonRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
            NsfwCardImagelonAvoidAllUselonrsTwelonelontLabelonlRulelon,
            DoNotAmplifyTwelonelontLabelonlAvoidRulelon,
            NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon,
          ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      delonlelontelondTwelonelontRulelons = Selonq(
        TombstonelonDelonlelontelondQuotelondTwelonelontRulelon,
        TombstonelonBouncelonDelonlelontelondQuotelondTwelonelontRulelon
      ),
      uselonrUnavailablelonStatelonRulelons = Selonq(
        SuspelonndelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        DelonactivatelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        OffBoardelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        elonraselondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        ProtelonctelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        AuthorBlocksVielonwelonrUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        VielonwelonrBlocksAuthorUselonrUnavailablelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon,
        VielonwelonrMutelonsAuthorUselonrUnavailablelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon
      ),
      policyRulelonParams = SelonnsitivelonMelondiaSelonttingsProfilelonTimelonlinelonBaselonRulelons.policyRulelonParams
    )

caselon objelonct ProfilelonMixelonrFavoritelonsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        DropStalelonTwelonelontsRulelon,
        DropelonxclusivelonTwelonelontContelonntRulelon,
        DropOutelonrCommunityTwelonelontsRulelon,
      ),
      delonlelontelondTwelonelontRulelons = Selonq(
        TombstonelonDelonlelontelondQuotelondTwelonelontRulelon,
        TombstonelonBouncelonDelonlelontelondQuotelondTwelonelontRulelon
      )
    )

caselon objelonct TimelonlinelonMelondiaPolicy
    elonxtelonnds VisibilityPolicy(
        TimelonlinelonProfilelonRulelons.baselonTwelonelontRulelons
        ++ Selonq(
          NsfwHighPreloncisionTombstonelonInnelonrQuotelondTwelonelontLabelonlRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionDropSelonttingLelonvelonTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsDropSelonttingLelonvelonlTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagDropSelonttingLelonvelonlTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagDropSelonttingLelonvelonlTombstonelonRulelon,
          AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
          elonmelonrgelonncyDynamicIntelonrstitialRulelon,
          RelonportelondTwelonelontIntelonrstitialRulelon,
          VielonwelonrMutelonsAuthorInnelonrQuotelondTwelonelontIntelonrstitialRulelon,
          VielonwelonrBlocksAuthorInnelonrQuotelondTwelonelontIntelonrstitialRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagIntelonrstitialRulelon,
          NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon,
          NsfwHighReloncallTwelonelontLabelonlAvoidRulelon,
          GorelonAndViolelonncelonHighPreloncisionAvoidAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAvoidAllUselonrsTwelonelontLabelonlRulelon,
          DoNotAmplifyTwelonelontLabelonlAvoidRulelon,
          NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon,
        ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      delonlelontelondTwelonelontRulelons = Selonq(
        TombstonelonBouncelonDelonlelontelondOutelonrTwelonelontRulelon,
        TombstonelonDelonlelontelondQuotelondTwelonelontRulelon,
        TombstonelonBouncelonDelonlelontelondQuotelondTwelonelontRulelon
      ),
      uselonrUnavailablelonStatelonRulelons = Selonq(
        SuspelonndelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        DelonactivatelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        OffBoardelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        elonraselondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        ProtelonctelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        AuthorBlocksVielonwelonrUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        VielonwelonrBlocksAuthorUselonrUnavailablelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon,
        VielonwelonrMutelonsAuthorUselonrUnavailablelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon
      ),
      policyRulelonParams = SelonnsitivelonMelondiaSelonttingsProfilelonTimelonlinelonBaselonRulelons.policyRulelonParams
    )

caselon objelonct ProfilelonMixelonrMelondiaPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        DropStalelonTwelonelontsRulelon,
        DropelonxclusivelonTwelonelontContelonntRulelon
      ),
      delonlelontelondTwelonelontRulelons = Selonq(
        TombstonelonBouncelonDelonlelontelondOutelonrTwelonelontRulelon,
        TombstonelonDelonlelontelondQuotelondTwelonelontRulelon,
        TombstonelonBouncelonDelonlelontelondQuotelondTwelonelontRulelon
      )
    )

objelonct TimelonlinelonProfilelonRulelons {

  val baselonTwelonelontRulelons: Selonq[Rulelon] = Selonq(
    TombstonelonCommunityTwelonelontsRulelon,
    TombstonelonCommunityTwelonelontCommunityNotVisiblelonRulelon,
    TombstonelonProtelonctelondCommunityTwelonelontsRulelon,
    TombstonelonHiddelonnCommunityTwelonelontsRulelon,
    TombstonelonAuthorRelonmovelondCommunityTwelonelontsRulelon,
    SpamQuotelondTwelonelontLabelonlTombstonelonRulelon,
    SpamTwelonelontLabelonlRulelon,
    PdnaQuotelondTwelonelontLabelonlTombstonelonRulelon,
    PdnaTwelonelontLabelonlRulelon,
    BouncelonTwelonelontLabelonlTombstonelonRulelon,
    TombstonelonelonxclusivelonQuotelondTwelonelontContelonntRulelon,
    DropelonxclusivelonTwelonelontContelonntRulelon,
    DropTrustelondFrielonndsTwelonelontContelonntRulelon
  )

  val twelonelontRulelons: Selonq[Rulelon] =
    Selonq(
      DynamicProductAdDropTwelonelontLabelonlRulelon,
      AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
      elonmelonrgelonncyDynamicIntelonrstitialRulelon,
      RelonportelondTwelonelontIntelonrstitialRulelon,
      NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
      GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
      NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
      GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
      NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
      NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon,
      NsfwHighReloncallTwelonelontLabelonlAvoidRulelon,
      GorelonAndViolelonncelonHighPreloncisionAvoidAllUselonrsTwelonelontLabelonlRulelon,
      NsfwRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
      GorelonAndViolelonncelonRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
      NsfwCardImagelonAvoidAllUselonrsTwelonelontLabelonlRulelon,
      NsfwTelonxtTwelonelontLabelonlAvoidRulelon,
      DoNotAmplifyTwelonelontLabelonlAvoidRulelon,
      NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon,
    ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons

  val twelonelontTombstonelonRulelons: Selonq[Rulelon] =
    Selonq(
      DynamicProductAdDropTwelonelontLabelonlRulelon,
      NsfwHighPreloncisionInnelonrQuotelondTwelonelontLabelonlRulelon,
      SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon,
      SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionDropSelonttingLelonvelonTombstonelonRulelon,
      SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon,
      SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsDropSelonttingLelonvelonlTombstonelonRulelon,
      SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon,
      SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagDropSelonttingLelonvelonlTombstonelonRulelon,
      SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagDropSelonttingLelonvelonlTombstonelonRulelon,
      AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
      elonmelonrgelonncyDynamicIntelonrstitialRulelon,
      RelonportelondTwelonelontIntelonrstitialRulelon,
      VielonwelonrMutelonsAuthorInnelonrQuotelondTwelonelontIntelonrstitialRulelon,
      VielonwelonrBlocksAuthorInnelonrQuotelondTwelonelontIntelonrstitialRulelon,
      NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
      GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
      NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
      GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
      NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
      SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlIntelonrstitialRulelon,
      SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionIntelonrstitialRulelon,
      SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlIntelonrstitialRulelon,
      SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsIntelonrstitialRulelon,
      SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlIntelonrstitialRulelon,
      SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagIntelonrstitialRulelon,
      SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagIntelonrstitialRulelon,
      NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon,
      NsfwHighReloncallTwelonelontLabelonlAvoidRulelon,
      GorelonAndViolelonncelonHighPreloncisionAvoidAllUselonrsTwelonelontLabelonlRulelon,
      NsfwRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
      GorelonAndViolelonncelonRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
      NsfwCardImagelonAvoidAllUselonrsTwelonelontLabelonlRulelon,
      DoNotAmplifyTwelonelontLabelonlAvoidRulelon,
      NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon,
    ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
}

caselon objelonct TimelonlinelonProfilelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons =
        Selonq(
          DropOutelonrCommunityTwelonelontsRulelon,
          DropStalelonTwelonelontsRulelon,
        )
          ++ TimelonlinelonProfilelonRulelons.baselonTwelonelontRulelons
          ++ TimelonlinelonProfilelonRulelons.twelonelontTombstonelonRulelons,
      delonlelontelondTwelonelontRulelons = Selonq(
        TombstonelonBouncelonDelonlelontelondOutelonrTwelonelontRulelon,
        TombstonelonDelonlelontelondQuotelondTwelonelontRulelon,
        TombstonelonBouncelonDelonlelontelondQuotelondTwelonelontRulelon,
      ),
      uselonrUnavailablelonStatelonRulelons = Selonq(
        SuspelonndelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        DelonactivatelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        OffBoardelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        elonraselondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        ProtelonctelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        AuthorBlocksVielonwelonrUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        VielonwelonrBlocksAuthorUselonrUnavailablelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon,
        VielonwelonrMutelonsAuthorUselonrUnavailablelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon
      ),
      policyRulelonParams = SelonnsitivelonMelondiaSelonttingsProfilelonTimelonlinelonBaselonRulelons.policyRulelonParams
    )

caselon objelonct TimelonlinelonProfilelonAllPolicy
    elonxtelonnds VisibilityPolicy(
        TimelonlinelonProfilelonRulelons.baselonTwelonelontRulelons
        ++ TimelonlinelonProfilelonRulelons.twelonelontTombstonelonRulelons,
      delonlelontelondTwelonelontRulelons = Selonq(
        TombstonelonBouncelonDelonlelontelondOutelonrTwelonelontRulelon,
        TombstonelonDelonlelontelondQuotelondTwelonelontRulelon,
        TombstonelonBouncelonDelonlelontelondQuotelondTwelonelontRulelon,
      ),
      uselonrUnavailablelonStatelonRulelons = Selonq(
        SuspelonndelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        DelonactivatelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        OffBoardelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        elonraselondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        ProtelonctelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        AuthorBlocksVielonwelonrUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        VielonwelonrBlocksAuthorUselonrUnavailablelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon,
        VielonwelonrMutelonsAuthorUselonrUnavailablelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon
      ),
      policyRulelonParams = SelonnsitivelonMelondiaSelonttingsProfilelonTimelonlinelonBaselonRulelons.policyRulelonParams
    )

caselon objelonct TimelonlinelonProfilelonSupelonrFollowsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons =
        Selonq(
          DropOutelonrCommunityTwelonelontsRulelon
        ) ++
          VisibilityPolicy.baselonTwelonelontRulelons ++
          TimelonlinelonProfilelonRulelons.twelonelontRulelons
    )

caselon objelonct TimelonlinelonRelonactivelonBlelonndingPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          VielonwelonrHasMatchingMutelondKelonywordForHomelonTimelonlinelonRulelon,
          AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
          elonmelonrgelonncyDynamicIntelonrstitialRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
        ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct TimelonlinelonHomelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonQuotelondTwelonelontTombstonelonRulelons ++
        VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          NullcastelondTwelonelontRulelon,
          DropOutelonrCommunityTwelonelontsRulelon,
          DynamicProductAdDropTwelonelontLabelonlRulelon,
          MutelondRelontwelonelontsRulelon,
          DropAllAuthorRelonmovelondCommunityTwelonelontsRulelon,
          DropAllHiddelonnCommunityTwelonelontsRulelon,
          AbuselonPolicyelonpisodicTwelonelontLabelonlDropRulelon,
          elonmelonrgelonncyDropRulelon,
          SafelontyCrisisLelonvelonl4DropRulelon,
          VielonwelonrHasMatchingMutelondKelonywordForHomelonTimelonlinelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlDropRulelon,
          SelonnsitivelonMelondiaTwelonelontDropRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionDropRulelon,
          SelonnsitivelonMelondiaTwelonelontDropRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlDropRulelon,
          SelonnsitivelonMelondiaTwelonelontDropRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsDropRulelon,
          SelonnsitivelonMelondiaTwelonelontDropRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlDropRulelon,
          SelonnsitivelonMelondiaTwelonelontDropRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagDropRulelon,
          SelonnsitivelonMelondiaTwelonelontDropRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagDropRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagIntelonrstitialRulelon,
          NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon,
          NsfwHighReloncallTwelonelontLabelonlAvoidRulelon,
          GorelonAndViolelonncelonHighPreloncisionAvoidAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAvoidAllUselonrsTwelonelontLabelonlRulelon,
          DoNotAmplifyTwelonelontLabelonlAvoidRulelon,
          NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon,
        )
        ++
          LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      uselonrRulelons = Selonq(
        VielonwelonrMutelonsAuthorRulelon,
        VielonwelonrBlocksAuthorRulelon,
        DeloncidelonrablelonAuthorBlocksVielonwelonrDropRulelon
      ),
      policyRulelonParams = SelonnsitivelonMelondiaSelonttingsTimelonlinelonHomelonBaselonRulelons.policyRulelonParams
    )

caselon objelonct BaselonTimelonlinelonHomelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonQuotelondTwelonelontTombstonelonRulelons ++
        VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          NullcastelondTwelonelontRulelon,
          DropOutelonrCommunityTwelonelontsRulelon,
          DynamicProductAdDropTwelonelontLabelonlRulelon,
          MutelondRelontwelonelontsRulelon,
          DropAllAuthorRelonmovelondCommunityTwelonelontsRulelon,
          DropAllHiddelonnCommunityTwelonelontsRulelon,
          AbuselonPolicyelonpisodicTwelonelontLabelonlDropRulelon,
          elonmelonrgelonncyDropRulelon,
          SafelontyCrisisLelonvelonl4DropRulelon,
          VielonwelonrHasMatchingMutelondKelonywordForHomelonTimelonlinelonRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
          NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon,
          NsfwHighReloncallTwelonelontLabelonlAvoidRulelon,
          GorelonAndViolelonncelonHighPreloncisionAvoidAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAvoidAllUselonrsTwelonelontLabelonlRulelon,
          DoNotAmplifyTwelonelontLabelonlAvoidRulelon,
          NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon,
        )
        ++
          LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      uselonrRulelons = Selonq(
        VielonwelonrMutelonsAuthorRulelon,
        VielonwelonrBlocksAuthorRulelon,
        DeloncidelonrablelonAuthorBlocksVielonwelonrDropRulelon
      )
    )

caselon objelonct TimelonlinelonHomelonHydrationPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons =
          VisibilityPolicy.baselonQuotelondTwelonelontTombstonelonRulelons ++
          VisibilityPolicy.baselonTwelonelontRulelons ++
          Selonq(
            SelonnsitivelonMelondiaTwelonelontDropRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlDropRulelon,
            SelonnsitivelonMelondiaTwelonelontDropRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionDropRulelon,
            SelonnsitivelonMelondiaTwelonelontDropRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlDropRulelon,
            SelonnsitivelonMelondiaTwelonelontDropRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsDropRulelon,
            SelonnsitivelonMelondiaTwelonelontDropRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlDropRulelon,
            SelonnsitivelonMelondiaTwelonelontDropRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagDropRulelon,
            SelonnsitivelonMelondiaTwelonelontDropRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagDropRulelon,
            AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
            elonmelonrgelonncyDynamicIntelonrstitialRulelon,
            NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
            GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
            NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
            GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
            NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagIntelonrstitialRulelon,
            NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon,
            NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon,
            NsfwHighReloncallTwelonelontLabelonlAvoidRulelon,
          ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      policyRulelonParams = SelonnsitivelonMelondiaSelonttingsTimelonlinelonHomelonBaselonRulelons.policyRulelonParams
    )

caselon objelonct TimelonlinelonHomelonLatelonstPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons =
          VisibilityPolicy.baselonQuotelondTwelonelontTombstonelonRulelons ++
          VisibilityPolicy.baselonTwelonelontRulelons ++
          Selonq(
            NullcastelondTwelonelontRulelon,
            DropOutelonrCommunityTwelonelontsRulelon,
            DynamicProductAdDropTwelonelontLabelonlRulelon,
            MutelondRelontwelonelontsRulelon,
            VielonwelonrHasMatchingMutelondKelonywordForHomelonTimelonlinelonRulelon,
            SelonnsitivelonMelondiaTwelonelontDropRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlDropRulelon,
            SelonnsitivelonMelondiaTwelonelontDropRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionDropRulelon,
            SelonnsitivelonMelondiaTwelonelontDropRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlDropRulelon,
            SelonnsitivelonMelondiaTwelonelontDropRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsDropRulelon,
            SelonnsitivelonMelondiaTwelonelontDropRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlDropRulelon,
            SelonnsitivelonMelondiaTwelonelontDropRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagDropRulelon,
            SelonnsitivelonMelondiaTwelonelontDropRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagDropRulelon,
            AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
            elonmelonrgelonncyDynamicIntelonrstitialRulelon,
            NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
            GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
            NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
            GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
            NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagIntelonrstitialRulelon,
            SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagIntelonrstitialRulelon,
            NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon,
            NsfwHighReloncallTwelonelontLabelonlAvoidRulelon,
            GorelonAndViolelonncelonHighPreloncisionAvoidAllUselonrsTwelonelontLabelonlRulelon,
            NsfwRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
            GorelonAndViolelonncelonRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon,
            NsfwCardImagelonAvoidAllUselonrsTwelonelontLabelonlRulelon,
            DoNotAmplifyTwelonelontLabelonlAvoidRulelon,
            NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon,
          )
          ++
            LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      uselonrRulelons = Selonq(
        VielonwelonrMutelonsAuthorRulelon,
        VielonwelonrBlocksAuthorRulelon,
        DeloncidelonrablelonAuthorBlocksVielonwelonrDropRulelon
      ),
      policyRulelonParams = SelonnsitivelonMelondiaSelonttingsTimelonlinelonHomelonBaselonRulelons.policyRulelonParams
    )

caselon objelonct TimelonlinelonModelonratelondTwelonelontsHydrationPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
        ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct SignalsRelonactionsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        AuthorBlocksVielonwelonrDropRulelon
      ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct SignalsTwelonelontRelonactingUselonrsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons :+
        NsfwVidelonoTwelonelontLabelonlDropRulelon :+
        NsfwTelonxtAllUselonrsTwelonelontLabelonlDropRulelon,
      uselonrRulelons = Selonq(
        CompromiselondNonFollowelonrWithUqfRulelon,
        elonngagelonmelonntSpammelonrNonFollowelonrWithUqfRulelon,
        LowQualityNonFollowelonrWithUqfRulelon,
        RelonadOnlyNonFollowelonrWithUqfRulelon,
        SpamHighReloncallNonFollowelonrWithUqfRulelon,
        AuthorBlocksVielonwelonrDropRulelon,
        ProtelonctelondAuthorDropRulelon,
        SuspelonndelondAuthorRulelon,
        NsfwTelonxtNonAuthorDropRulelon
      )
    )

caselon objelonct SocialProofPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = FiltelonrDelonfaultPolicy.twelonelontRulelons,
      uselonrRulelons = Selonq(
        ProtelonctelondAuthorDropRulelon,
        SuspelonndelondAuthorRulelon,
        AuthorBlocksVielonwelonrDropRulelon,
        VielonwelonrBlocksAuthorRulelon
      )
    )

caselon objelonct TimelonlinelonLikelondByPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons :+
        NsfwVidelonoTwelonelontLabelonlDropRulelon :+
        NsfwTelonxtAllUselonrsTwelonelontLabelonlDropRulelon,
      uselonrRulelons = TimelonlinelonLikelondByRulelons.UselonrRulelons :+ NsfwTelonxtNonAuthorDropRulelon
    )

caselon objelonct TimelonlinelonRelontwelonelontelondByPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons :+
        NsfwVidelonoTwelonelontLabelonlDropRulelon :+
        NsfwTelonxtAllUselonrsTwelonelontLabelonlDropRulelon,
      uselonrRulelons = Selonq(
        CompromiselondNonFollowelonrWithUqfRulelon,
        elonngagelonmelonntSpammelonrNonFollowelonrWithUqfRulelon,
        LowQualityNonFollowelonrWithUqfRulelon,
        RelonadOnlyNonFollowelonrWithUqfRulelon,
        SpamHighReloncallNonFollowelonrWithUqfRulelon,
        NsfwTelonxtNonAuthorDropRulelon
      )
    )

caselon objelonct TimelonlinelonSupelonrLikelondByPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons :+
        NsfwVidelonoTwelonelontLabelonlDropRulelon :+
        NsfwTelonxtAllUselonrsTwelonelontLabelonlDropRulelon,
      uselonrRulelons = Selonq(
        CompromiselondNonFollowelonrWithUqfRulelon,
        elonngagelonmelonntSpammelonrNonFollowelonrWithUqfRulelon,
        LowQualityNonFollowelonrWithUqfRulelon,
        RelonadOnlyNonFollowelonrWithUqfRulelon,
        SpamHighReloncallNonFollowelonrWithUqfRulelon,
        NsfwTelonxtNonAuthorDropRulelon
      )
    )

caselon objelonct TimelonlinelonContelonntControlsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = TopicsLandingPagelonTopicReloncommelonndationsPolicy.twelonelontRulelons,
      uselonrRulelons = TopicsLandingPagelonTopicReloncommelonndationsPolicy.uselonrRulelons
    )

caselon objelonct TimelonlinelonConvelonrsationsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          AbusivelonNonFollowelonrTwelonelontLabelonlRulelon,
          LowQualityTwelonelontLabelonlDropRulelon,
          SpamHighReloncallTwelonelontLabelonlDropRulelon,
          DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
          BystandelonrAbusivelonNonFollowelonrTwelonelontLabelonlRulelon,
          UntrustelondUrlAllVielonwelonrsTwelonelontLabelonlRulelon,
          DownrankSpamRelonplyAllVielonwelonrsTwelonelontLabelonlRulelon,
          SmytelonSpamTwelonelontLabelonlDropRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionDropSelonttingLelonvelonTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsDropSelonttingLelonvelonlTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagDropSelonttingLelonvelonlTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagDropSelonttingLelonvelonlTombstonelonRulelon,
          MutelondKelonywordForTwelonelontRelonplielonsIntelonrstitialRulelon,
          RelonportelondTwelonelontIntelonrstitialRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagIntelonrstitialRulelon,
          AbusivelonHighReloncallNonFollowelonrTwelonelontLabelonlRulelon,
        ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      uselonrRulelons = Selonq(
        AbusivelonRulelon,
        LowQualityRulelon,
        RelonadOnlyRulelon,
        LowQualityHighReloncallRulelon,
        CompromiselondRulelon,
        SpamHighReloncallRulelon,
        AbusivelonHighReloncallRulelon,
        DownrankSpamRelonplyAllVielonwelonrsRulelon,
      ),
      policyRulelonParams = SelonnsitivelonMelondiaSelonttingsConvelonrsationBaselonRulelons.policyRulelonParams
    )

caselon objelonct TimelonlinelonFollowingActivityPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          AbusivelonTwelonelontLabelonlRulelon,
          BystandelonrAbusivelonTwelonelontLabelonlRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
        ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct TimelonlinelonInjelonctionPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        NsfwHighPreloncisionTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon,
        NsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        NsfwCardImagelonTwelonelontLabelonlRulelon,
        NsfwHighReloncallTwelonelontLabelonlRulelon,
        NsfwVidelonoTwelonelontLabelonlDropRulelon,
        NsfwTelonxtTwelonelontLabelonlDropRulelon,
        SafelontyCrisisLelonvelonl2DropRulelon,
        SafelontyCrisisLelonvelonl3DropRulelon,
        SafelontyCrisisLelonvelonl4DropRulelon,
        DoNotAmplifyDropRulelon,
        HighProactivelonTosScorelonTwelonelontLabelonlDropRulelon
      ),
      uselonrRulelons = Selonq(
        DoNotAmplifyNonFollowelonrRulelon,
        NotGraduatelondNonFollowelonrRulelon,
        LikelonlyIvsLabelonlNonFollowelonrDropUselonrRulelon,
        NsfwTelonxtNonAuthorDropRulelon
      )
    )

caselon objelonct TimelonlinelonMelonntionsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          LowQualityTwelonelontLabelonlDropRulelon,
          SpamHighReloncallTwelonelontLabelonlDropRulelon,
          DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
          DuplicatelonMelonntionUqfTwelonelontLabelonlRulelon,
          LowQualityMelonntionTwelonelontLabelonlRulelon,
          SmytelonSpamTwelonelontLabelonlDropRulelon,
          ToxicityRelonplyFiltelonrDropNotificationRulelon,
          AbusivelonUqfNonFollowelonrTwelonelontLabelonlRulelon,
          UntrustelondUrlUqfNonFollowelonrTwelonelontLabelonlRulelon,
          DownrankSpamRelonplyUqfNonFollowelonrTwelonelontLabelonlRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
        ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      uselonrRulelons = Selonq(
        AbusivelonRulelon,
        LowQualityRulelon,
        RelonadOnlyRulelon,
        CompromiselondRulelon,
        SpamHighReloncallRulelon,
        DuplicatelonContelonntRulelon,
        AbusivelonHighReloncallRulelon,
        elonngagelonmelonntSpammelonrNonFollowelonrWithUqfRulelon,
        elonngagelonmelonntSpammelonrHighReloncallNonFollowelonrWithUqfRulelon,
        DownrankSpamRelonplyNonFollowelonrWithUqfRulelon
      )
    )

caselon objelonct TwelonelontelonngagelonrsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons,
      uselonrRulelons = Selonq(
        CompromiselondNonFollowelonrWithUqfRulelon,
        elonngagelonmelonntSpammelonrNonFollowelonrWithUqfRulelon,
        LowQualityNonFollowelonrWithUqfRulelon,
        RelonadOnlyNonFollowelonrWithUqfRulelon,
        SpamHighReloncallNonFollowelonrWithUqfRulelon
      )
    )

caselon objelonct TwelonelontWritelonsApiPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
        elonmelonrgelonncyDynamicIntelonrstitialRulelon,
      ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct QuotelondTwelonelontRulelonsPolicy
    elonxtelonnds VisibilityPolicy(
      quotelondTwelonelontRulelons = Selonq(
        DelonactivatelondAuthorRulelon,
        elonraselondAuthorRulelon,
        OffboardelondAuthorRulelon,
        SuspelonndelondAuthorRulelon,
        AuthorBlocksOutelonrAuthorRulelon,
        VielonwelonrBlocksAuthorRulelon,
        AuthorBlocksVielonwelonrDropRulelon,
        VielonwelonrMutelonsAndDoelonsNotFollowAuthorRulelon,
        ProtelonctelondQuotelonTwelonelontAuthorRulelon
      )
    )

caselon objelonct TwelonelontDelontailPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          AuthorBlocksVielonwelonrDropRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionDropSelonttingLelonvelonTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsDropSelonttingLelonvelonlTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagDropSelonttingLelonvelonlTombstonelonRulelon,
          SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagDropSelonttingLelonvelonlTombstonelonRulelon,
          AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
          elonmelonrgelonncyDynamicIntelonrstitialRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.AdultMelondiaNsfwCardImagelonTwelonelontLabelonlIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagIntelonrstitialRulelon,
          SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons.OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagIntelonrstitialRulelon,
          NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon,
          NsfwHighReloncallTwelonelontLabelonlAvoidRulelon,
          GorelonAndViolelonncelonHighPreloncisionAvoidAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAvoidAdPlacelonmelonntAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAvoidAdPlacelonmelonntAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAvoidAdPlacelonmelonntAllUselonrsTwelonelontLabelonlRulelon,
          DoNotAmplifyTwelonelontLabelonlAvoidRulelon,
          NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon,
          MutelondKelonywordForQuotelondTwelonelontTwelonelontDelontailIntelonrstitialRulelon,
        )
        ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      policyRulelonParams = SelonnsitivelonMelondiaSelonttingsTwelonelontDelontailBaselonRulelons.policyRulelonParams
    )

caselon objelonct BaselonTwelonelontDelontailPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          AuthorBlocksVielonwelonrDropRulelon,
          AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
          elonmelonrgelonncyDynamicIntelonrstitialRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
          NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon,
          NsfwHighReloncallTwelonelontLabelonlAvoidRulelon,
          GorelonAndViolelonncelonHighPreloncisionAvoidAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAvoidAdPlacelonmelonntAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAvoidAdPlacelonmelonntAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAvoidAdPlacelonmelonntAllUselonrsTwelonelontLabelonlRulelon,
          DoNotAmplifyTwelonelontLabelonlAvoidRulelon,
          NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon,
          MutelondKelonywordForQuotelondTwelonelontTwelonelontDelontailIntelonrstitialRulelon,
        )
        ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct TwelonelontDelontailWithInjelonctionsHydrationPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
          elonmelonrgelonncyDynamicIntelonrstitialRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
          MutelondKelonywordForQuotelondTwelonelontTwelonelontDelontailIntelonrstitialRulelon,
          RelonportelondTwelonelontIntelonrstitialRulelon,
        ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      uselonrRulelons = UselonrTimelonlinelonRulelons.UselonrRulelons
    )

caselon objelonct TwelonelontDelontailNonTooPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        DropAllelonxclusivelonTwelonelontsRulelon,
        DropAllTrustelondFrielonndsTwelonelontsRulelon,
      ) ++ BaselonTwelonelontDelontailPolicy.twelonelontRulelons
    )

caselon objelonct ReloncosWritelonPathPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        AbusivelonTwelonelontLabelonlRulelon,
        LowQualityTwelonelontLabelonlDropRulelon,
        NsfwHighPreloncisionTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon,
        NsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon,
        NsfwCardImagelonTwelonelontLabelonlRulelon,
        NsfwVidelonoTwelonelontLabelonlDropRulelon,
        NsfwTelonxtTwelonelontLabelonlDropRulelon,
        SpamHighReloncallTwelonelontLabelonlDropRulelon,
        DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
        BystandelonrAbusivelonTwelonelontLabelonlRulelon,
        SmytelonSpamTwelonelontLabelonlDropRulelon
      ),
      uselonrRulelons = Selonq(NsfwTelonxtNonAuthorDropRulelon)
    )

caselon objelonct BrandSafelontyPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        NsfwVidelonoTwelonelontLabelonlDropRulelon,
        NsfwTelonxtTwelonelontLabelonlDropRulelon,
        NsfaHighReloncallTwelonelontLabelonlIntelonrstitialRulelon
      ),
      uselonrRulelons = Selonq(NsfwTelonxtNonAuthorDropRulelon)
    )

caselon objelonct VidelonoAdsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons
    )

caselon objelonct AppelonalsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
        )
    )

caselon objelonct TimelonlinelonConvelonrsationsDownrankingPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        HighToxicityScorelonDownrankAbusivelonQualitySelonctionRulelon,
        UntrustelondUrlConvelonrsationsTwelonelontLabelonlRulelon,
        DownrankSpamRelonplyConvelonrsationsTwelonelontLabelonlRulelon,
        DownrankSpamRelonplyConvelonrsationsAuthorLabelonlRulelon,
        HighProactivelonTosScorelonTwelonelontLabelonlDownrankingRulelon,
        SafelontyCrisisLelonvelonl3SelonctionRulelon,
        SafelontyCrisisLelonvelonl4SelonctionRulelon,
        DoNotAmplifySelonctionRulelon,
        DoNotAmplifySelonctionUselonrRulelon,
        NotGraduatelondConvelonrsationsAuthorLabelonlRulelon,
        HighSpammyTwelonelontContelonntScorelonConvoDownrankAbusivelonQualityRulelon,
        HighCryptospamScorelonConvoDownrankAbusivelonQualityRulelon,
        CopypastaSpamAbusivelonQualityTwelonelontLabelonlRulelon,
        HighToxicityScorelonDownrankLowQualitySelonctionRulelon,
        HighPSpammyTwelonelontScorelonDownrankLowQualitySelonctionRulelon,
        RitoActionelondTwelonelontDownrankLowQualitySelonctionRulelon,
        HighToxicityScorelonDownrankHighQualitySelonctionRulelon,
      )
    )

caselon objelonct TimelonlinelonConvelonrsationsDownrankingMinimalPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        HighProactivelonTosScorelonTwelonelontLabelonlDownrankingRulelon
      )
    )

caselon objelonct TimelonlinelonHomelonReloncommelonndationsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.union(
        ReloncommelonndationsPolicy.twelonelontRulelons.filtelonr(
          _ != NsfwHighPreloncisionTwelonelontLabelonlRulelon
        ),
        Selonq(
          SafelontyCrisisLelonvelonl2DropRulelon,
          SafelontyCrisisLelonvelonl3DropRulelon,
          SafelontyCrisisLelonvelonl4DropRulelon,
          HighProactivelonTosScorelonTwelonelontLabelonlDropRulelon,
          NsfwHighReloncallTwelonelontLabelonlRulelon,
        ),
        BaselonTimelonlinelonHomelonPolicy.twelonelontRulelons,
      ),
      uselonrRulelons = VisibilityPolicy.union(
        ReloncommelonndationsPolicy.uselonrRulelons,
        BaselonTimelonlinelonHomelonPolicy.uselonrRulelons
      )
    )

caselon objelonct TimelonlinelonHomelonTopicFollowReloncommelonndationsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.union(
        Selonq(
          SelonarchBlacklistTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonTopicHighReloncallTwelonelontLabelonlRulelon,
          NsfwHighReloncallTwelonelontLabelonlRulelon,
        ),
        ReloncommelonndationsPolicy.twelonelontRulelons
          .filtelonrNot(
            Selonq(
              NsfwHighPreloncisionTwelonelontLabelonlRulelon,
            ).contains),
        BaselonTimelonlinelonHomelonPolicy.twelonelontRulelons
      ),
      uselonrRulelons = VisibilityPolicy.union(
        ReloncommelonndationsPolicy.uselonrRulelons,
        BaselonTimelonlinelonHomelonPolicy.uselonrRulelons
      )
    )

caselon objelonct TimelonlinelonScorelonrPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        AllowAllRulelon
      )
    )

caselon objelonct FollowelondTopicsTimelonlinelonPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = Selonq(
        AuthorBlocksVielonwelonrDropRulelon,
        ProtelonctelondAuthorDropRulelon,
        SuspelonndelondAuthorRulelon
      )
    )

caselon objelonct TopicsLandingPagelonTopicReloncommelonndationsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.union(
        Selonq(
          SelonarchBlacklistTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonTopicHighReloncallTwelonelontLabelonlRulelon,
          NsfwHighReloncallTwelonelontLabelonlRulelon
        ),
        ReloncommelonndationsPolicy.twelonelontRulelons,
        BaselonTimelonlinelonHomelonPolicy.twelonelontRulelons,
      ),
      uselonrRulelons = VisibilityPolicy.union(
        ReloncommelonndationsPolicy.uselonrRulelons,
        BaselonTimelonlinelonHomelonPolicy.uselonrRulelons
      ) ++ Selonq(
        AuthorBlocksVielonwelonrDropRulelon
      )
    )

caselon objelonct elonxplorelonReloncommelonndationsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        DropOutelonrCommunityTwelonelontsRulelon,
        SelonarchBlacklistTwelonelontLabelonlRulelon,
        GorelonAndViolelonncelonTopicHighReloncallTwelonelontLabelonlRulelon,
        NsfwHighReloncallTwelonelontLabelonlRulelon,
        DropTwelonelontsWithGelonoRelonstrictelondMelondiaRulelon,
        TwelonelontNsfwUselonrDropRulelon,
        TwelonelontNsfwAdminDropRulelon,
        VielonwelonrHasMatchingMutelondKelonywordForHomelonTimelonlinelonRulelon,
        VielonwelonrHasMatchingMutelondKelonywordForNotificationsRulelon,
      ) ++ VisibilityPolicy.union(
        ReloncommelonndationsPolicy.twelonelontRulelons
      ),
      uselonrRulelons = VisibilityPolicy.union(
        ReloncommelonndationsPolicy.uselonrRulelons
      ) ++ Selonq(
        AuthorBlocksVielonwelonrDropRulelon,
        VielonwelonrMutelonsAuthorRulelon,
        VielonwelonrBlocksAuthorRulelon
      )
    )

caselon objelonct TombstoningPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        TombstonelonIf.VielonwelonrIsBlockelondByAuthor,
        TombstonelonIf.AuthorIsProtelonctelond,
        TombstonelonIf.RelonplyIsModelonratelondByRootAuthor,
        TombstonelonIf.AuthorIsSuspelonndelond,
        TombstonelonIf.AuthorIsDelonactivatelond,
        IntelonrstitialIf.VielonwelonrHardMutelondAuthor
      )
    )

caselon objelonct TwelonelontRelonplyNudgelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        SpamAllUselonrsTwelonelontLabelonlRulelon,
        PdnaAllUselonrsTwelonelontLabelonlRulelon,
        BouncelonAllUselonrsTwelonelontLabelonlRulelon,
        TwelonelontNsfwAdminDropRulelon,
        TwelonelontNsfwUselonrDropRulelon,
        NsfwHighReloncallAllUselonrsTwelonelontLabelonlDropRulelon,
        NsfwHighPreloncisionAllUselonrsTwelonelontLabelonlDropRulelon,
        GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlDropRulelon,
        NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlDropRulelon,
        GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlDropRulelon,
        NsfwCardImagelonAllUselonrsTwelonelontLabelonlDropRulelon,
        NsfwVidelonoAllUselonrsTwelonelontLabelonlDropRulelon,
        NsfwTelonxtAllUselonrsTwelonelontLabelonlDropRulelon,
      ),
      uselonrRulelons = Selonq(
        DropNsfwUselonrAuthorRulelon,
        DropNsfwAdminAuthorRulelon,
        NsfwTelonxtAllUselonrsDropRulelon
      )
    )

caselon objelonct HumanizationNudgelonPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = UselonrTimelonlinelonRulelons.UselonrRulelons
    )

caselon objelonct TrelonndsRelonprelonselonntativelonTwelonelontPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.union(
        ReloncommelonndationsPolicy.twelonelontRulelons,
        Selonq(
          AbusivelonHighReloncallTwelonelontLabelonlRulelon,
          BystandelonrAbusivelonTwelonelontLabelonlRulelon,
          DuplicatelonContelonntTwelonelontLabelonlDropRulelon,
          LowQualityTwelonelontLabelonlDropRulelon,
          HighProactivelonTosScorelonTwelonelontLabelonlDropRulelon,
          NsfaHighReloncallTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlDropRulelon,
          NsfwHighPreloncisionTwelonelontLabelonlRulelon,
          NsfwHighReloncallAllUselonrsTwelonelontLabelonlDropRulelon,
          NsfwVidelonoTwelonelontLabelonlDropRulelon,
          NsfwTelonxtTwelonelontLabelonlDropRulelon,
          PdnaAllUselonrsTwelonelontLabelonlRulelon,
          SelonarchBlacklistTwelonelontLabelonlRulelon,
          SpamHighReloncallTwelonelontLabelonlDropRulelon,
          UntrustelondUrlAllVielonwelonrsTwelonelontLabelonlRulelon,
          DownrankSpamRelonplyAllVielonwelonrsTwelonelontLabelonlRulelon,
          HighPSpammyScorelonAllVielonwelonrDropRulelon,
          DoNotAmplifyAllVielonwelonrsDropRulelon,
          SmytelonSpamTwelonelontLabelonlDropRulelon,
          AuthorBlocksVielonwelonrDropRulelon,
          VielonwelonrBlocksAuthorRulelon,
          VielonwelonrMutelonsAuthorRulelon,
          CopypastaSpamAllVielonwelonrsTwelonelontLabelonlRulelon,
        )
      ),
      uselonrRulelons = VisibilityPolicy.union(
        ReloncommelonndationsPolicy.uselonrRulelons,
        Selonq(
          AbusivelonRulelon,
          LowQualityRulelon,
          RelonadOnlyRulelon,
          CompromiselondRulelon,
          ReloncommelonndationsBlacklistRulelon,
          SpamHighReloncallRulelon,
          DuplicatelonContelonntRulelon,
          NsfwHighPreloncisionRulelon,
          NsfwNelonarPelonrfelonctAuthorRulelon,
          NsfwBannelonrImagelonRulelon,
          NsfwAvatarImagelonRulelon,
          elonngagelonmelonntSpammelonrRulelon,
          elonngagelonmelonntSpammelonrHighReloncallRulelon,
          AbusivelonHighReloncallRulelon,
          SelonarchBlacklistRulelon,
          SelonarchNsfwTelonxtRulelon,
          NsfwHighReloncallRulelon,
          TsViolationRulelon,
          DownrankSpamRelonplyAllVielonwelonrsRulelon,
          NsfwTelonxtNonAuthorDropRulelon
        )
      )
    )

caselon objelonct AdsCampaignPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = Selonq(SuspelonndelondAuthorRulelon),
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons
    )

caselon objelonct AdsManagelonrPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++ Selonq(
        AdsManagelonrDelonnyListAllUselonrsTwelonelontLabelonlRulelon,
      )
    )

caselon objelonct AdsRelonportingDashboardPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = AdsManagelonrPolicy.twelonelontRulelons,
      uselonrRulelons = AdsCampaignPolicy.uselonrRulelons
    )

caselon objelonct BirdwatchNotelonAuthorPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = Selonq(
        SuspelonndelondAuthorRulelon,
        AuthorBlocksVielonwelonrDropRulelon,
        VielonwelonrBlocksAuthorRulelon,
        VielonwelonrMutelonsAuthorRulelon
      )
    )

caselon objelonct BirdwatchNotelonTwelonelontsTimelonlinelonPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          MutelondRelontwelonelontsRulelon,
          AuthorBlocksVielonwelonrDropRulelon,
          VielonwelonrMutelonsAuthorRulelon,
          AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
          elonmelonrgelonncyDynamicIntelonrstitialRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
        ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct BirdwatchNelonelondsYourHelonlpNotificationsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          AuthorBlocksVielonwelonrDropRulelon,
          VielonwelonrBlocksAuthorRulelon,
          VielonwelonrMutelonsAuthorRulelon,
          VielonwelonrHasMatchingMutelondKelonywordForHomelonTimelonlinelonRulelon,
          VielonwelonrHasMatchingMutelondKelonywordForNotificationsRulelon,
        )
    )

caselon objelonct ForDelonvelonlopmelonntOnlyPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = Selonq.elonmpty,
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons
    )

caselon objelonct UselonrProfilelonHelonadelonrPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = Selonq.elonmpty,
      twelonelontRulelons = Selonq(DropAllRulelon)
    )

caselon objelonct UselonrScopelondTimelonlinelonPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = UselonrTimelonlinelonRulelons.UselonrRulelons,
      twelonelontRulelons = Selonq(DropAllRulelon)
    )

caselon objelonct TwelonelontScopelondTimelonlinelonPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = UselonrTimelonlinelonRulelons.UselonrRulelons,
      twelonelontRulelons = Selonq.elonmpty
    )

caselon objelonct SoftIntelonrvelonntionPivotPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons
    )

caselon objelonct CuratelondTrelonndsRelonprelonselonntativelonTwelonelontPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = Selonq(
        SuspelonndelondAuthorRulelon,
        AuthorBlocksVielonwelonrDropRulelon,
        VielonwelonrBlocksAuthorRulelon,
        VielonwelonrMutelonsAndDoelonsNotFollowAuthorRulelon
      )
    )

caselon objelonct CommunitielonsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons ++
        Selonq(
          RelontwelonelontDropRulelon,
          AbuselonPolicyelonpisodicTwelonelontLabelonlDropRulelon,
          elonmelonrgelonncyDropRulelon,
          SafelontyCrisisLelonvelonl4DropRulelon,
          RelonportelondTwelonelontIntelonrstitialRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
        ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons
    )

caselon objelonct TimelonlinelonHomelonCommunitielonsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.union(
        Selonq(
          DropAllAuthorRelonmovelondCommunityTwelonelontsRulelon,
          DropAllHiddelonnCommunityTwelonelontsRulelon,
          VielonwelonrHasMatchingMutelondKelonywordForHomelonTimelonlinelonRulelon,
        ),
        VisibilityPolicy.baselonQuotelondTwelonelontTombstonelonRulelons,
        CommunitielonsPolicy.twelonelontRulelons,
      ),
      uselonrRulelons = Selonq(
        VielonwelonrMutelonsAuthorRulelon,
        VielonwelonrBlocksAuthorRulelon,
      )
    )

caselon objelonct TimelonlinelonHomelonPromotelondHydrationPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(
        VielonwelonrHasMatchingMutelondKelonywordForHomelonTimelonlinelonPromotelondTwelonelontRulelon,
        VielonwelonrMutelonsAuthorHomelonTimelonlinelonPromotelondTwelonelontRulelon,
        VielonwelonrBlocksAuthorHomelonTimelonlinelonPromotelondTwelonelontRulelon
      ) ++ TimelonlinelonHomelonHydrationPolicy.twelonelontRulelons,
      policyRulelonParams = TimelonlinelonHomelonHydrationPolicy.policyRulelonParams
    )

caselon objelonct SpacelonsPolicy
    elonxtelonnds VisibilityPolicy(
        SpacelonDoNotAmplifyAllUselonrsDropRulelon,
        SpacelonNsfwHighPreloncisionNonFollowelonrDropRulelon),
      uselonrRulelons = Selonq(
        AuthorBlocksVielonwelonrDropRulelon
      )
    )

caselon objelonct SpacelonsSelonllelonrApplicationStatusPolicy
    elonxtelonnds VisibilityPolicy(
      uselonrRulelons = Selonq(
        VielonwelonrIsNotAuthorDropRulelon
      )
    )

caselon objelonct SpacelonsParticipantsPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = Selonq(DropAllRulelon),
      uselonrRulelons = Selonq(
        AuthorBlocksVielonwelonrDropRulelon,
        SuspelonndelondAuthorRulelon
      )
    )

caselon objelonct SpacelonsSharingPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = TwelonelontDelontailPolicy.twelonelontRulelons,
      uselonrRulelons = Selonq(
        AuthorBlocksVielonwelonrDropRulelon,
        ProtelonctelondAuthorDropRulelon,
        SuspelonndelondAuthorRulelon
      ),
      policyRulelonParams = TwelonelontDelontailPolicy.policyRulelonParams
    )

caselon objelonct SpacelonFlelonelontlinelonPolicy
    elonxtelonnds VisibilityPolicy(
      spacelonRulelons = Selonq(
        SpacelonDoNotAmplifyNonFollowelonrDropRulelon,
        SpacelonCoordHarmfulActivityHighReloncallNonFollowelonrDropRulelon,
        SpacelonUntrustelondUrlNonFollowelonrDropRulelon,
        SpacelonMislelonadingHighReloncallNonFollowelonrDropRulelon,
        SpacelonNsfwHighPreloncisionAllUselonrsIntelonrstitialRulelon
      ),
      uselonrRulelons = Selonq(
        TsViolationRulelon,
        DoNotAmplifyNonFollowelonrRulelon,
        NotGraduatelondNonFollowelonrRulelon,
        LikelonlyIvsLabelonlNonFollowelonrDropUselonrRulelon,
        UselonrAbusivelonNonFollowelonrDropRulelon
      )
    )

caselon objelonct SpacelonNotificationsPolicy
    elonxtelonnds VisibilityPolicy(
      spacelonRulelons = Selonq(
        SpacelonHatelonfulHighReloncallAllUselonrsDropRulelon,
        SpacelonViolelonncelonHighReloncallAllUselonrsDropRulelon,
        SpacelonDoNotAmplifyAllUselonrsDropRulelon,
        SpacelonCoordHarmfulActivityHighReloncallAllUselonrsDropRulelon,
        SpacelonUntrustelondUrlNonFollowelonrDropRulelon,
        SpacelonMislelonadingHighReloncallNonFollowelonrDropRulelon,
        SpacelonNsfwHighPreloncisionAllUselonrsDropRulelon,
        SpacelonNsfwHighReloncallAllUselonrsDropRulelon,
        VielonwelonrHasMatchingMutelondKelonywordInSpacelonTitlelonForNotificationsRulelon
      ),
      uselonrRulelons = Selonq(
        VielonwelonrMutelonsAuthorRulelon,
        VielonwelonrBlocksAuthorRulelon,
        AuthorBlocksVielonwelonrDropRulelon,
        TsViolationRulelon,
        DoNotAmplifyUselonrRulelon,
        AbusivelonRulelon,
        SelonarchBlacklistRulelon,
        SelonarchNsfwTelonxtRulelon,
        ReloncommelonndationsBlacklistRulelon,
        NotGraduatelondRulelon,
        SpamHighReloncallRulelon,
        AbusivelonHighReloncallRulelon,
        UselonrBlinkWorstAllUselonrsDropRulelon,
        UselonrNsfwNelonarPelonrfelonctNonFollowelonrDropRulelon,
        SpacelonNsfwHighPreloncisionNonFollowelonrDropRulelon,
        UselonrNsfwAvatarImagelonNonFollowelonrDropRulelon,
        UselonrNsfwBannelonrImagelonNonFollowelonrDropRulelon
      )
    )

caselon objelonct SpacelonTwelonelontAvatarHomelonTimelonlinelonPolicy
    elonxtelonnds VisibilityPolicy(
      spacelonRulelons = Selonq(
        SpacelonDoNotAmplifyNonFollowelonrDropRulelon,
        SpacelonCoordHarmfulActivityHighReloncallNonFollowelonrDropRulelon,
        SpacelonUntrustelondUrlNonFollowelonrDropRulelon,
        SpacelonMislelonadingHighReloncallNonFollowelonrDropRulelon,
        SpacelonNsfwHighPreloncisionAllUselonrsDropRulelon,
        SpacelonNsfwHighPreloncisionAllUselonrsIntelonrstitialRulelon
      ),
      uselonrRulelons = Selonq(
        TsViolationRulelon,
        DoNotAmplifyUselonrRulelon,
        NotGraduatelondNonFollowelonrRulelon,
        AbusivelonRulelon,
        SelonarchBlacklistRulelon,
        SelonarchNsfwTelonxtRulelon,
        ReloncommelonndationsBlacklistRulelon,
        SpamHighReloncallRulelon,
        AbusivelonHighReloncallRulelon,
        UselonrBlinkWorstAllUselonrsDropRulelon,
        UselonrNsfwNelonarPelonrfelonctNonFollowelonrDropRulelon,
        SpacelonNsfwHighPreloncisionNonFollowelonrDropRulelon,
        UselonrNsfwAvatarImagelonNonFollowelonrDropRulelon,
        UselonrNsfwBannelonrImagelonNonFollowelonrDropRulelon
      )
    )

caselon objelonct SpacelonHomelonTimelonlinelonUprankingPolicy
    elonxtelonnds VisibilityPolicy(
      spacelonRulelons = Selonq(
        SpacelonDoNotAmplifyNonFollowelonrDropRulelon,
        SpacelonCoordHarmfulActivityHighReloncallNonFollowelonrDropRulelon,
        SpacelonUntrustelondUrlNonFollowelonrDropRulelon,
        SpacelonMislelonadingHighReloncallNonFollowelonrDropRulelon,
        SpacelonNsfwHighPreloncisionNonFollowelonrDropRulelon,
        SpacelonNsfwHighPreloncisionSafelonSelonarchNonFollowelonrDropRulelon,
        SpacelonNsfwHighReloncallSafelonSelonarchNonFollowelonrDropRulelon
      ),
      uselonrRulelons = Selonq(
        TsViolationRulelon,
        DoNotAmplifyUselonrRulelon,
        NotGraduatelondRulelon,
        AbusivelonRulelon,
        SelonarchBlacklistRulelon,
        SelonarchNsfwTelonxtRulelon,
        ReloncommelonndationsBlacklistRulelon,
        SpamHighReloncallRulelon,
        AbusivelonHighReloncallRulelon,
        UselonrBlinkWorstAllUselonrsDropRulelon,
        UselonrNsfwNelonarPelonrfelonctNonFollowelonrDropRulelon,
        UselonrNsfwAvatarImagelonNonFollowelonrDropRulelon,
        UselonrNsfwBannelonrImagelonNonFollowelonrDropRulelon
      )
    )

caselon objelonct SpacelonJoinScrelonelonnPolicy
    elonxtelonnds VisibilityPolicy(
      spacelonRulelons = Selonq(
        SpacelonNsfwHighPreloncisionAllUselonrsIntelonrstitialRulelon
      )
    )

caselon objelonct KitchelonnSinkDelonvelonlopmelonntPolicy
    elonxtelonnds VisibilityPolicy(
      twelonelontRulelons = VisibilityPolicy.baselonTwelonelontRulelons.diff(
        Selonq(
          BouncelonTwelonelontLabelonlRulelon,
          DropelonxclusivelonTwelonelontContelonntRulelon,
          DropTrustelondFrielonndsTwelonelontContelonntRulelon
        )
      ) ++ Selonq(
        BouncelonTwelonelontLabelonlTombstonelonRulelon,
        TombstonelonelonxclusivelonTwelonelontContelonntRulelon,
        TombstonelonTrustelondFrielonndsTwelonelontContelonntRulelon)
        ++ Selonq(
          AbuselonPolicyelonpisodicTwelonelontLabelonlIntelonrstitialRulelon,
          elonmelonrgelonncyDynamicIntelonrstitialRulelon,
          VielonwelonrRelonportsAuthorIntelonrstitialRulelon,
          VielonwelonrMutelonsAuthorIntelonrstitialRulelon,
          VielonwelonrBlocksAuthorIntelonrstitialRulelon,
          MutelondKelonywordForTwelonelontRelonplielonsIntelonrstitialRulelon,
          RelonportelondTwelonelontIntelonrstitialRulelon,
          NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon,
          NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon,
          NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon,
          elonxpelonrimelonntalNudgelonLabelonlRulelon,
        ) ++ LimitelondelonngagelonmelonntBaselonRulelons.twelonelontRulelons,
      uselonrRulelons = Selonq(
        AuthorBlocksVielonwelonrDropRulelon,
        ProtelonctelondAuthorTombstonelonRulelon,
        SuspelonndelondAuthorRulelon
      ),
      uselonrUnavailablelonStatelonRulelons = Selonq(
        SuspelonndelondUselonrUnavailablelonRelontwelonelontTombstonelonRulelon,
        DelonactivatelondUselonrUnavailablelonRelontwelonelontTombstonelonRulelon,
        OffBoardelondUselonrUnavailablelonRelontwelonelontTombstonelonRulelon,
        elonraselondUselonrUnavailablelonRelontwelonelontTombstonelonRulelon,
        ProtelonctelondUselonrUnavailablelonRelontwelonelontTombstonelonRulelon,
        AuthorBlocksVielonwelonrUselonrUnavailablelonRelontwelonelontTombstonelonRulelon,
        VielonwelonrBlocksAuthorUselonrUnavailablelonRelontwelonelontTombstonelonRulelon,
        VielonwelonrMutelonsAuthorUselonrUnavailablelonRelontwelonelontTombstonelonRulelon,
        SuspelonndelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        DelonactivatelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        OffBoardelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        elonraselondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        ProtelonctelondUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        AuthorBlocksVielonwelonrUselonrUnavailablelonInnelonrQuotelondTwelonelontTombstonelonRulelon,
        SuspelonndelondUselonrUnavailablelonTwelonelontTombstonelonRulelon,
        DelonactivatelondUselonrUnavailablelonTwelonelontTombstonelonRulelon,
        OffBoardelondUselonrUnavailablelonTwelonelontTombstonelonRulelon,
        elonraselondUselonrUnavailablelonTwelonelontTombstonelonRulelon,
        ProtelonctelondUselonrUnavailablelonTwelonelontTombstonelonRulelon,
        AuthorBlocksVielonwelonrUselonrUnavailablelonTwelonelontTombstonelonRulelon,
        VielonwelonrBlocksAuthorUselonrUnavailablelonInnelonrQuotelondTwelonelontIntelonrstitialRulelon,
        VielonwelonrMutelonsAuthorUselonrUnavailablelonInnelonrQuotelondTwelonelontInterstitialRule
      ),
      deletedTweetRules = Seq(
        TombstoneDeletedOuterTweetRule,
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule
      ),
      mediaRules = VisibilityPolicy.baseMediaRules
    )

case object CurationPolicyViolationsPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++ Seq(
        DoNotAmplifyAllViewersDropRule,
      ),
      userRules = Seq(
        DoNotAmplifyUserRule,
        TsViolationRule
      )
    )

case object GraphqlDefaultPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        ) ++ LimitedEngagementBaseRules.tweetRules
    )

case object GryphonDecksAndColumnsSharingPolicy
    extends VisibilityPolicy(
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      ),
      tweetRules = Seq(DropAllRule)
    )

case object UserSettingsPolicy
    extends VisibilityPolicy(
      userRules = Seq(ViewerIsNotAuthorDropRule),
      tweetRules = Seq(DropAllRule)
    )

case object BlockMuteUsersTimelinePolicy
    extends VisibilityPolicy(
      userRules = Seq(SuspendedAuthorRule),
      tweetRules = Seq(DropAllRule)
    )

case object TopicRecommendationsPolicy
    extends VisibilityPolicy(
      tweetRules =
        Seq(
          NsfwHighRecallTweetLabelRule,
          NsfwTextTweetLabelTopicsDropRule
        )
          ++ RecommendationsPolicy.tweetRules,
      userRules = RecommendationsPolicy.userRules
    )

case object RitoActionedTweetTimelinePolicy
    extends VisibilityPolicy(
      tweetRules =
        VisibilityPolicy.baseTweetTombstoneRules
          ++ Seq(
            AuthorBlocksViewerTombstoneRule,
            ProtectedAuthorTombstoneRule
          ),
      deletedTweetRules = Seq(
        TombstoneDeletedOuterTweetRule,
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
      ),
    )

case object EmbeddedTweetsPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetTombstoneRules
        ++ Seq(
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        )
        ++ LimitedEngagementBaseRules.tweetRules,
      deletedTweetRules = Seq(
        TombstoneDeletedOuterTweetRule,
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
      ),
      userUnavailableStateRules = Seq(
        SuspendedUserUnavailableTweetTombstoneRule,
        DeactivatedUserUnavailableTweetTombstoneRule,
        OffBoardedUserUnavailableTweetTombstoneRule,
        ErasedUserUnavailableTweetTombstoneRule,
        ProtectedUserUnavailableTweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule,
      )
    )

case object EmbedTweetMarkupPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(DropStaleTweetsRule) ++
        VisibilityPolicy.baseTweetTombstoneRules
        ++ Seq(
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        )
        ++ LimitedEngagementBaseRules.tweetRules,
      deletedTweetRules = Seq(
        TombstoneDeletedOuterTweetRule,
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
      ),
    )

case object ArticleTweetTimelinePolicy
    extends VisibilityPolicy(
      tweetRules =
          VisibilityPolicy.baseTweetRules ++
          Seq(
            ViewerHasMatchingMutedKeywordForHomeTimelineRule,
            AbusePolicyEpisodicTweetLabelInterstitialRule,
            EmergencyDynamicInterstitialRule,
            NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
            GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
            NsfwReportedHeuristicsAllUsersTweetLabelRule,
            GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
            NsfwCardImageAllUsersTweetLabelRule,
          ) ++ LimitedEngagementBaseRules.tweetRules,
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ViewerBlocksAuthorRule,
        ViewerMutesAuthorRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule
      )
    )

case object ConversationFocalPrehydrationPolicy
    extends VisibilityPolicy(
      deletedTweetRules = Seq(
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
      )
    )

case object ConversationFocalTweetPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetTombstoneRules
        ++ Seq(
          DynamicProductAdDropTweetLabelRule,
          AuthorBlocksViewerTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwHighPrecisionTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceHighPrecisionDropSettingLeveTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwReportedHeuristicsTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceReportedHeuristicsDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwCardImageTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwUserTweetFlagDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwAdminTweetFlagDropSettingLevelTombstoneRule,
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          ReportedTweetInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule,
          GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule,
          NsfwCardImageAvoidAdPlacementAllUsersTweetLabelRule,
          MutedKeywordForQuotedTweetTweetDetailInterstitialRule,
          ViewerMutesAuthorInnerQuotedTweetInterstitialRule,
          ViewerBlocksAuthorInnerQuotedTweetInterstitialRule,
        )
        ++ LimitedEngagementBaseRules.tweetRules
        ++ ConversationsAdAvoidanceRules.tweetRules,
      deletedTweetRules = Seq(
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
      ),
      userUnavailableStateRules = Seq(
        SuspendedUserUnavailableTweetTombstoneRule,
        DeactivatedUserUnavailableTweetTombstoneRule,
        OffBoardedUserUnavailableTweetTombstoneRule,
        ErasedUserUnavailableTweetTombstoneRule,
        ProtectedUserUnavailableTweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule,
        UserUnavailableTweetTombstoneRule,
        ViewerBlocksAuthorUserUnavailableInnerQuotedTweetInterstitialRule,
        ViewerMutesAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      ),
      policyRuleParams = ConversationsAdAvoidanceRules.policyRuleParams
        ++ SensitiveMediaSettingsConversationBaseRules.policyRuleParams
    )

case object ConversationReplyPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetTombstoneRules
        ++ Seq(
          LowQualityTweetLabelTombstoneRule,
          SpamHighRecallTweetLabelTombstoneRule,
          DuplicateContentTweetLabelTombstoneRule,
          DeciderableSpamHighRecallAuthorLabelTombstoneRule,
          SmyteSpamTweetLabelTombstoneRule,
          AuthorBlocksViewerTombstoneRule,
          ToxicityReplyFilterRule,
          DynamicProductAdDropTweetLabelRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwHighPrecisionTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceHighPrecisionDropSettingLeveTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwReportedHeuristicsTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.ViolentMediaGoreAndViolenceReportedHeuristicsDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.AdultMediaNsfwCardImageTweetLabelDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwUserTweetFlagDropSettingLevelTombstoneRule,
          SensitiveMediaTweetDropSettingLevelTombstoneRules.OtherSensitiveMediaNsfwAdminTweetFlagDropSettingLevelTombstoneRule,
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          MutedKeywordForTweetRepliesInterstitialRule,
          ReportedTweetInterstitialRule,
          ViewerBlocksAuthorInterstitialRule,
          ViewerMutesAuthorInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwHighPrecisionTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceHighPrecisionInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwReportedHeuristicsTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.ViolentMediaGoreAndViolenceReportedHeuristicsInterstitialRule,
          SensitiveMediaTweetInterstitialRules.AdultMediaNsfwCardImageTweetLabelInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwUserTweetFlagInterstitialRule,
          SensitiveMediaTweetInterstitialRules.OtherSensitiveMediaNsfwAdminTweetFlagInterstitialRule,
          GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule,
          NsfwCardImageAvoidAdPlacementAllUsersTweetLabelRule,
        )
        ++ LimitedEngagementBaseRules.tweetRules
        ++ ConversationsAdAvoidanceRules.tweetRules,
      userRules = Seq(
        LowQualityRule,
        ReadOnlyRule,
        LowQualityHighRecallRule,
        CompromisedRule,
        DeciderableSpamHighRecallRule
      ),
      deletedTweetRules = Seq(
        TombstoneDeletedOuterTweetRule,
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
      ),
      userUnavailableStateRules = Seq(
        SuspendedUserUnavailableTweetTombstoneRule,
        DeactivatedUserUnavailableTweetTombstoneRule,
        OffBoardedUserUnavailableTweetTombstoneRule,
        ErasedUserUnavailableTweetTombstoneRule,
        ProtectedUserUnavailableTweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule,
        UserUnavailableTweetTombstoneRule,
        ViewerBlocksAuthorUserUnavailableInnerQuotedTweetInterstitialRule,
        ViewerMutesAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      ),
      policyRuleParams = ConversationsAdAvoidanceRules.policyRuleParams
        ++ SensitiveMediaSettingsConversationBaseRules.policyRuleParams
    )

case object AdsBusinessSettingsPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(DropAllRule)
    )

case object UserMilestoneRecommendationPolicy
    extends VisibilityPolicy(
      userRules = RecommendationsPolicy.userRules ++ Seq(
      )
    )

case object TrustedFriendsUserListPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(DropAllRule),
      userRules = Seq(
        ViewerBlocksAuthorRule
      )
    )

case object QuickPromoteTweetEligibilityPolicy
    extends VisibilityPolicy(
      tweetRules = TweetDetailPolicy.tweetRules,
      userRules = UserTimelineRules.UserRules,
      policyRuleParams = TweetDetailPolicy.policyRuleParams
    )

case object ReportCenterPolicy
    extends VisibilityPolicy(
      tweetRules = ConversationFocalTweetPolicy.tweetRules.diff(
        ConversationsAdAvoidanceRules.tweetRules
      ),
      deletedTweetRules = Seq(
        TombstoneBounceDeletedOuterTweetRule,
        TombstoneDeletedQuotedTweetRule,
        TombstoneBounceDeletedQuotedTweetRule,
        TombstoneDeletedOuterTweetRule,
      ),
      userUnavailableStateRules = Seq(
        SuspendedUserUnavailableTweetTombstoneRule,
        DeactivatedUserUnavailableTweetTombstoneRule,
        OffBoardedUserUnavailableTweetTombstoneRule,
        ErasedUserUnavailableTweetTombstoneRule,
        ProtectedUserUnavailableTweetTombstoneRule,
        AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule,
        UserUnavailableTweetTombstoneRule,
        ViewerBlocksAuthorUserUnavailableInnerQuotedTweetInterstitialRule,
        ViewerMutesAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      ),
      policyRuleParams = ConversationFocalTweetPolicy.policyRuleParams
    )

case object ConversationInjectedTweetPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetRules ++
        Seq(
          AbusePolicyEpisodicTweetLabelInterstitialRule,
          EmergencyDynamicInterstitialRule,
          NsfwHighPrecisionInterstitialAllUsersTweetLabelRule,
          GoreAndViolenceHighPrecisionAllUsersTweetLabelRule,
          NsfwReportedHeuristicsAllUsersTweetLabelRule,
          GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule,
          NsfwCardImageAllUsersTweetLabelRule,
        ) ++
        LimitedEngagementBaseRules.tweetRules ++ Seq(
        SkipTweetDetailLimitedEngagementTweetLabelRule
      )
    )

case object EditHistoryTimelinePolicy
    extends VisibilityPolicy(
      tweetRules = ConversationReplyPolicy.tweetRules,
      policyRuleParams = ConversationReplyPolicy.policyRuleParams,
      deletedTweetRules = ConversationReplyPolicy.deletedTweetRules,
      userUnavailableStateRules = ConversationReplyPolicy.userUnavailableStateRules)

case object UserSelfViewOnlyPolicy
    extends VisibilityPolicy(
      userRules = Seq(ViewerIsNotAuthorDropRule),
      tweetRules = Seq(DropAllRule)
    )

case object TwitterArticleComposePolicy
    extends VisibilityPolicy(
      twitterArticleRules = Seq(
        ViewerIsNotAuthorDropRule
      )
    )

case object TwitterArticleProfileTabPolicy
    extends VisibilityPolicy(
      twitterArticleRules = Seq(
        AuthorBlocksViewerDropRule
      )
    )

case object TwitterArticleReadPolicy
    extends VisibilityPolicy(
      twitterArticleRules = Seq(
        AuthorBlocksViewerDropRule,
      )
    )

case object ContentControlToolInstallPolicy
    extends VisibilityPolicy(
      userRules = UserProfileHeaderPolicy.userRules,
      tweetRules = UserProfileHeaderPolicy.tweetRules
    )

case object TimelineProfileSpacesPolicy
    extends VisibilityPolicy(
      userRules = UserProfileHeaderPolicy.userRules,
      tweetRules = UserProfileHeaderPolicy.tweetRules
    )

case object TimelineFavoritesSelfViewPolicy
    extends VisibilityPolicy(
      tweetRules = TimelineFavoritesPolicy.tweetRules.diff(Seq(DropStaleTweetsRule)),
      policyRuleParams = TimelineFavoritesPolicy.policyRuleParams,
      deletedTweetRules = TimelineFavoritesPolicy.deletedTweetRules,
      userUnavailableStateRules = TimelineFavoritesPolicy.userUnavailableStateRules
    )

case object BaseQigPolicy
    extends VisibilityPolicy(
      tweetRules = Seq(
        AbusePolicyEpisodicTweetLabelDropRule,
        AutomationTweetLabelRule,
        DoNotAmplifyDropRule,
        DownrankSpamReplyTweetLabelRule,
        DuplicateContentTweetLabelDropRule,
        DuplicateMentionTweetLabelRule,
        NsfwHighPrecisionTweetLabelRule,
        GoreAndViolenceHighPrecisionTweetLabelRule,
        GoreAndViolenceReportedHeuristicsTweetLabelRule,
        LikelyIvsLabelNonFollowerDropUserRule,
        NsfwCardImageTweetLabelRule,
        NsfwHighRecallTweetLabelRule,
        NsfwReportedHeuristicsTweetLabelRule,
        NsfwTextTweetLabelDropRule,
        NsfwVideoTweetLabelDropRule,
        PdnaTweetLabelRule,
        SafetyCrisisLevel3DropRule,
        SafetyCrisisLevel4DropRule,
        SearchBlacklistHighRecallTweetLabelDropRule,
        SearchBlacklistTweetLabelRule,
        SmyteSpamTweetLabelDropRule,
        SpamHighRecallTweetLabelDropRule,
      ),
      userRules = Seq(
        DuplicateContentRule,
        EngagementSpammerHighRecallRule,
        EngagementSpammerRule,
        NsfwAvatarImageRule,
        NsfwBannerImageRule,
        NsfwHighPrecisionRule,
        NsfwHighRecallRule,
        NsfwSensitiveRule,
        ReadOnlyRule,
        RecommendationsBlacklistRule,
        SearchBlacklistRule,
        SpamHighRecallRule
      ))

case object NotificationsQigPolicy
    extends VisibilityPolicy(
      tweetRules = BaseQigPolicy.tweetRules ++ Seq(
        DropAllCommunityTweetsRule,
        DropNsfwAdminAuthorViewerOptInFilteringOnSearchRule,
        HighProactiveTosScoreTweetLabelDropSearchRule,
        LowQualityTweetLabelDropRule,
        NsfwHighPrecisionRule,
        NsfwHighRecallRule,
        NsfwNearPerfectAuthorRule,
        NsfwSensitiveRule,
      ),
      userRules = BaseQigPolicy.userRules ++ Seq(
        AbusiveRule,
        LowQualityRule,
        CompromisedRule,
        ViewerBlocksAuthorViewerOptInBlockingOnSearchRule,
        ViewerMutesAuthorViewerOptInBlockingOnSearchRule,
        DropNsfwAdminAuthorViewerOptInFilteringOnSearchRule,
        NsfwNearPerfectAuthorRule
      )
    )

case object ShoppingManagerSpyModePolicy
    extends VisibilityPolicy(
      tweetRules = Seq(
        DropAllRule
      ),
      userRules = Seq(
        SuspendedAuthorRule,
        DeactivatedAuthorRule,
        ErasedAuthorRule,
        OffboardedAuthorRule
      )
    )

case object ZipbirdConsumerArchivesPolicy
    extends VisibilityPolicy(
      tweetRules = VisibilityPolicy.baseTweetTombstoneRules,
      userRules = Seq(
        AuthorBlocksViewerDropRule,
        ProtectedAuthorDropRule,
        SuspendedAuthorRule,
      ),
      userUnavailableStateRules = Seq(
        AuthorBlocksViewerUserUnavailableTweetTombstoneRule,
        ProtectedUserUnavailableTweetTombstoneRule,
        SuspendedUserUnavailableTweetTombstoneRule,
      ),
      deletedTweetRules = Seq(
        TombstoneDeletedTweetRule,
        TombstoneBounceDeletedTweetRule,
      )
    )

case class MixedVisibilityPolicy(
  originalPolicy: VisibilityPolicy,
  additionalTweetRules: Seq[Rule])
    extends VisibilityPolicy(
      tweetRules = (additionalTweetRules ++ originalPolicy.tweetRules)
        .sortWith(_.actionBuilder.actionSeverity > _.actionBuilder.actionSeverity),
      userRules = originalPolicy.userRules,
      cardRules = originalPolicy.cardRules,
      quotedTweetRules = originalPolicy.quotedTweetRules,
      dmRules = originalPolicy.dmRules,
      dmConversationRules = originalPolicy.dmConversationRules,
      dmEventRules = originalPolicy.dmEventRules,
      spaceRules = originalPolicy.spaceRules,
      userUnavailableStateRules = originalPolicy.userUnavailableStateRules,
      twitterArticleRules = originalPolicy.twitterArticleRules,
      deletedTweetRules = originalPolicy.deletedTweetRules,
      mediaRules = originalPolicy.mediaRules,
      communityRules = originalPolicy.communityRules,
      policyRuleParams = originalPolicy.policyRuleParams
    )

case object TweetAwardPolicy
    extends VisibilityPolicy(
      userRules = Seq.empty,
      tweetRules =
        VisibilityPolicy.baseTweetRules ++ Seq(
          EmergencyDropRule,
          NsfwHighPrecisionTweetLabelRule,
          NsfwHighRecallTweetLabelRule,
          NsfwReportedHeuristicsTweetLabelRule,
          NsfwCardImageTweetLabelRule,
          NsfwVideoTweetLabelDropRule,
          NsfwTextTweetLabelDropRule,
          GoreAndViolenceHighPrecisionTweetLabelRule,
          GoreAndViolenceReportedHeuristicsTweetLabelRule,
          GoreAndViolenceTweetLabelRule,
          AbusePolicyEpisodicTweetLabelDropRule,
          AbusiveTweetLabelRule,
          BystanderAbusiveTweetLabelRule
        )
    )
