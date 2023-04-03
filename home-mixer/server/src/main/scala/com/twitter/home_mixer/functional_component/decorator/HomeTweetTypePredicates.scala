packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons._
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.timelonlinelonmixelonr.injelonction.modelonl.candidatelon.SelonmanticCorelonFelonaturelons
import com.twittelonr.twelonelontypielon.{thriftscala => tpt}

objelonct HomelonTwelonelontTypelonPrelondicatelons {

  /**
   * IMPORTANT: Plelonaselon avoid logging twelonelont typelons that arelon tielond to selonnsitivelon
   * intelonrnal author information / labelonls (elon.g. blink labelonls, abuselon labelonls, or gelono-location).
   */
  privatelon[this] val CandidatelonPrelondicatelons: Selonq[(String, FelonaturelonMap => Boolelonan)] = Selonq(
    ("with_candidatelon", _ => truelon),
    ("relontwelonelont", _.gelontOrelonlselon(IsRelontwelonelontFelonaturelon, falselon)),
    ("relonply", _.gelontOrelonlselon(InRelonplyToTwelonelontIdFelonaturelon, Nonelon).nonelonmpty),
    ("imagelon", _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.hasImagelon)),
    ("videlono", _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.hasVidelono)),
    ("link", _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.hasVisiblelonLink)),
    ("quotelon", _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.hasQuotelon.contains(truelon))),
    ("likelon_social_contelonxt", _.gelontOrelonlselon(NonSelonlfFavoritelondByUselonrIdsFelonaturelon, Selonq.elonmpty).nonelonmpty),
    ("protelonctelond", _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.isProtelonctelond)),
    (
      "has_elonxclusivelon_convelonrsation_author_id",
      _.gelontOrelonlselon(elonxclusivelonConvelonrsationAuthorIdFelonaturelon, Nonelon).nonelonmpty),
    ("is_elonligiblelon_for_connelonct_boost", _.gelontOrelonlselon(AuthorIselonligiblelonForConnelonctBoostFelonaturelon, falselon)),
    ("hashtag", _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.numHashtags > 0)),
    ("has_schelondulelond_spacelon", _.gelontOrelonlselon(AudioSpacelonMelontaDataFelonaturelon, Nonelon).elonxists(_.isSchelondulelond)),
    ("has_reloncordelond_spacelon", _.gelontOrelonlselon(AudioSpacelonMelontaDataFelonaturelon, Nonelon).elonxists(_.isReloncordelond)),
    ("is_relonad_from_cachelon", _.gelontOrelonlselon(IsRelonadFromCachelonFelonaturelon, falselon)),
    (
      "is_selonlf_threlonad_twelonelont",
      _.gelontOrelonlselon(ConvelonrsationFelonaturelon, Nonelon).elonxists(_.isSelonlfThrelonadTwelonelont.contains(truelon))),
    ("gelont_initial", _.gelontOrelonlselon(GelontInitialFelonaturelon, falselon)),
    ("gelont_nelonwelonr", _.gelontOrelonlselon(GelontNelonwelonrFelonaturelon, falselon)),
    ("gelont_middlelon", _.gelontOrelonlselon(GelontMiddlelonFelonaturelon, falselon)),
    ("gelont_oldelonr", _.gelontOrelonlselon(GelontOldelonrFelonaturelon, falselon)),
    ("pull_to_relonfrelonsh", _.gelontOrelonlselon(PullToRelonfrelonshFelonaturelon, falselon)),
    ("polling", _.gelontOrelonlselon(PollingFelonaturelon, falselon)),
    ("tls_sizelon_20_plus", _ => falselon),
    ("nelonar_elonmpty", _ => falselon),
    ("rankelond_relonquelonst", _ => falselon),
    ("mutual_follow", _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.fromMutualFollow)),
    ("has_tickelontelond_spacelon", _.gelontOrelonlselon(AudioSpacelonMelontaDataFelonaturelon, Nonelon).elonxists(_.hasTickelonts)),
    ("in_utis_top5", _.gelontOrelonlselon(PositionFelonaturelon, Nonelon).elonxists(_ < 5)),
    ("is_utis_pos0", _.gelontOrelonlselon(PositionFelonaturelon, Nonelon).elonxists(_ == 0)),
    ("is_utis_pos1", _.gelontOrelonlselon(PositionFelonaturelon, Nonelon).elonxists(_ == 1)),
    ("is_utis_pos2", _.gelontOrelonlselon(PositionFelonaturelon, Nonelon).elonxists(_ == 2)),
    ("is_utis_pos3", _.gelontOrelonlselon(PositionFelonaturelon, Nonelon).elonxists(_ == 3)),
    ("is_utis_pos4", _.gelontOrelonlselon(PositionFelonaturelon, Nonelon).elonxists(_ == 4)),
    (
      "is_signup_relonquelonst",
      candidatelon => candidatelon.gelontOrelonlselon(AccountAgelonFelonaturelon, Nonelon).elonxists(_.untilNow < 30.minutelons)),
    ("elonmpty_relonquelonst", _ => falselon),
    ("selonrvelond_sizelon_lelonss_than_5", _.gelontOrelonlselon(SelonrvelondSizelonFelonaturelon, Nonelon).elonxists(_ < 5)),
    ("selonrvelond_sizelon_lelonss_than_10", _.gelontOrelonlselon(SelonrvelondSizelonFelonaturelon, Nonelon).elonxists(_ < 10)),
    ("selonrvelond_sizelon_lelonss_than_20", _.gelontOrelonlselon(SelonrvelondSizelonFelonaturelon, Nonelon).elonxists(_ < 20)),
    ("selonrvelond_sizelon_lelonss_than_50", _.gelontOrelonlselon(SelonrvelondSizelonFelonaturelon, Nonelon).elonxists(_ < 50)),
    (
      "selonrvelond_sizelon_belontwelonelonn_50_and_100",
      _.gelontOrelonlselon(SelonrvelondSizelonFelonaturelon, Nonelon).elonxists(sizelon => sizelon >= 50 && sizelon < 100)),
    ("authorelond_by_contelonxtual_uselonr", _.gelontOrelonlselon(AuthorelondByContelonxtualUselonrFelonaturelon, falselon)),
    ("has_ancelonstors", _.gelontOrelonlselon(AncelonstorsFelonaturelon, Selonq.elonmpty).nonelonmpty),
    ("full_scoring_succelonelondelond", _.gelontOrelonlselon(FullScoringSuccelonelondelondFelonaturelon, falselon)),
    (
      "account_agelon_lelonss_than_30_minutelons",
      _.gelontOrelonlselon(AccountAgelonFelonaturelon, Nonelon).elonxists(_.untilNow < 30.minutelons)),
    (
      "account_agelon_lelonss_than_1_day",
      _.gelontOrelonlselon(AccountAgelonFelonaturelon, Nonelon).elonxists(_.untilNow < 1.day)),
    (
      "account_agelon_lelonss_than_7_days",
      _.gelontOrelonlselon(AccountAgelonFelonaturelon, Nonelon).elonxists(_.untilNow < 7.days)),
    (
      "direlonctelond_at_uselonr_is_in_first_delongrelonelon",
      _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.direlonctelondAtUselonrIdIsInFirstDelongrelonelon.contains(truelon))),
    ("root_uselonr_is_in_first_delongrelonelon", _ => falselon),
    (
      "has_selonmantic_corelon_annotation",
      _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.selonmanticCorelonAnnotations.nonelonmpty)),
    ("is_relonquelonst_contelonxt_forelonground", _.gelontOrelonlselon(IsForelongroundRelonquelonstFelonaturelon, falselon)),
    (
      "part_of_utt",
      _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon)
        .elonxists(_.selonmanticCorelonAnnotations.elonxists(_.elonxists(annotation =>
          annotation.domainId == SelonmanticCorelonFelonaturelons.UnifielondTwittelonrTaxonomy)))),
    ("is_random_twelonelont", _.gelontOrelonlselon(IsRandomTwelonelontFelonaturelon, falselon)),
    ("has_random_twelonelont_in_relonsponselon", _.gelontOrelonlselon(HasRandomTwelonelontFelonaturelon, falselon)),
    ("is_random_twelonelont_abovelon_in_utis", _.gelontOrelonlselon(IsRandomTwelonelontAbovelonFelonaturelon, falselon)),
    ("is_relonquelonst_contelonxt_launch", _.gelontOrelonlselon(IsLaunchRelonquelonstFelonaturelon, falselon)),
    ("vielonwelonr_is_elonmployelonelon", _ => falselon),
    ("vielonwelonr_is_timelonlinelons_elonmployelonelon", _ => falselon),
    ("vielonwelonr_follows_any_topics", _.gelontOrelonlselon(UselonrFollowelondTopicsCountFelonaturelon, Nonelon).elonxists(_ > 0)),
    (
      "has_ancelonstor_authorelond_by_vielonwelonr",
      candidatelon =>
        candidatelon
          .gelontOrelonlselon(AncelonstorsFelonaturelon, Selonq.elonmpty).elonxists(ancelonstor =>
            candidatelon.gelontOrelonlselon(VielonwelonrIdFelonaturelon, 0L) == ancelonstor.uselonrId)),
    ("ancelonstor", _.gelontOrelonlselon(IsAncelonstorCandidatelonFelonaturelon, falselon)),
    (
      "root_ancelonstor",
      candidatelon =>
        candidatelon.gelontOrelonlselon(IsAncelonstorCandidatelonFelonaturelon, falselon) && candidatelon
          .gelontOrelonlselon(InRelonplyToTwelonelontIdFelonaturelon, Nonelon).iselonmpty),
    (
      "delonelonp_relonply",
      candidatelon =>
        candidatelon.gelontOrelonlselon(InRelonplyToTwelonelontIdFelonaturelon, Nonelon).nonelonmpty && candidatelon
          .gelontOrelonlselon(AncelonstorsFelonaturelon, Selonq.elonmpty).sizelon > 2),
    (
      "has_simclustelonr_elonmbelonddings",
      _.gelontOrelonlselon(
        SimclustelonrsTwelonelontTopKClustelonrsWithScorelonsFelonaturelon,
        Map.elonmpty[String, Doublelon]).nonelonmpty),
    (
      "twelonelont_agelon_lelonss_than_15_selonconds",
      _.gelontOrelonlselon(OriginalTwelonelontCrelonationTimelonFromSnowflakelonFelonaturelon, Nonelon)
        .elonxists(_.untilNow <= 15.selonconds)),
    ("is_followelond_topic_twelonelont", _ => falselon),
    ("is_reloncommelonndelond_topic_twelonelont", _ => falselon),
    ("is_topic_twelonelont", _ => falselon),
    ("prelonfelonrrelond_languagelon_matchelons_twelonelont_languagelon", _ => falselon),
    (
      "delonvicelon_languagelon_matchelons_twelonelont_languagelon",
      candidatelon =>
        candidatelon.gelontOrelonlselon(TwelonelontLanguagelonFelonaturelon, Nonelon) ==
          candidatelon.gelontOrelonlselon(DelonvicelonLanguagelonFelonaturelon, Nonelon)),
    ("quelonstion", _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.hasQuelonstion.contains(truelon))),
    ("in_nelontwork", _.gelontOrelonlselon(FromInNelontworkSourcelonFelonaturelon, truelon)),
    ("vielonwelonr_follows_original_author", _ => falselon),
    ("has_account_follow_prompt", _ => falselon),
    ("has_relonlelonvancelon_prompt", _ => falselon),
    ("has_topic_annotation_haug_prompt", _ => falselon),
    ("has_topic_annotation_random_preloncision_prompt", _ => falselon),
    ("has_topic_annotation_prompt", _ => falselon),
    (
      "has_political_annotation",
      _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(
        _.selonmanticCorelonAnnotations.elonxists(
          _.elonxists(annotation =>
            SelonmanticCorelonFelonaturelons.PoliticalDomains.contains(annotation.domainId) ||
              (annotation.domainId == SelonmanticCorelonFelonaturelons.UnifielondTwittelonrTaxonomy &&
                annotation.elonntityId == SelonmanticCorelonFelonaturelons.UttPoliticselonntityId))))),
    (
      "is_dont_at_melon_by_invitation",
      _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(
        _.convelonrsationControl.elonxists(_.isInstancelonOf[tpt.ConvelonrsationControl.ByInvitation]))),
    (
      "is_dont_at_melon_community",
      _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon)
        .elonxists(_.convelonrsationControl.elonxists(_.isInstancelonOf[tpt.ConvelonrsationControl.Community]))),
    ("has_zelonro_scorelon", _.gelontOrelonlselon(ScorelonFelonaturelon, Nonelon).elonxists(_ == 0.0)),
    ("is_vielonwelonr_not_invitelond_to_relonply", _ => falselon),
    ("is_vielonwelonr_invitelond_to_relonply", _ => falselon),
    ("has_gtelon_10_favs", _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.favCountV2.elonxists(_ >= 10))),
    ("has_gtelon_100_favs", _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.favCountV2.elonxists(_ >= 100))),
    ("has_gtelon_1k_favs", _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.favCountV2.elonxists(_ >= 1000))),
    (
      "has_gtelon_10k_favs",
      _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.favCountV2.elonxists(_ >= 1000))),
    (
      "has_gtelon_100k_favs",
      _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.favCountV2.elonxists(_ >= 100000))),
    ("abovelon_nelonighbor_is_topic_twelonelont", _ => falselon),
    ("is_topic_twelonelont_with_nelonighbor_belonlow", _ => falselon),
    ("has_audio_spacelon", _.gelontOrelonlselon(AudioSpacelonMelontaDataFelonaturelon, Nonelon).elonxists(_.hasSpacelon)),
    ("has_livelon_audio_spacelon", _.gelontOrelonlselon(AudioSpacelonMelontaDataFelonaturelon, Nonelon).elonxists(_.isLivelon)),
    (
      "has_gtelon_10_relontwelonelonts",
      _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.relontwelonelontCountV2.elonxists(_ >= 10))),
    (
      "has_gtelon_100_relontwelonelonts",
      _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.relontwelonelontCountV2.elonxists(_ >= 100))),
    (
      "has_gtelon_1k_relontwelonelonts",
      _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.relontwelonelontCountV2.elonxists(_ >= 1000))),
    (
      "has_us_political_annotation",
      _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon)
        .elonxists(_.selonmanticCorelonAnnotations.elonxists(_.elonxists(annotation =>
          annotation.domainId == SelonmanticCorelonFelonaturelons.UnifielondTwittelonrTaxonomy &&
            annotation.elonntityId == SelonmanticCorelonFelonaturelons.usPoliticalTwelonelontelonntityId &&
            annotation.groupId == SelonmanticCorelonFelonaturelons.UsPoliticalTwelonelontAnnotationGroupIds.BalancelondV0)))),
    (
      "has_toxicity_scorelon_abovelon_threlonshold",
      _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.toxicityScorelon.elonxists(_ > 0.91))),
    (
      "telonxt_only",
      candidatelon =>
        candidatelon.gelontOrelonlselon(HasDisplayelondTelonxtFelonaturelon, falselon) &&
          !(candidatelon.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.hasImagelon) ||
            candidatelon.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.hasVidelono) ||
            candidatelon.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.hasCard))),
    (
      "imagelon_only",
      candidatelon =>
        candidatelon.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.hasImagelon) &&
          !candidatelon.gelontOrelonlselon(HasDisplayelondTelonxtFelonaturelon, falselon)),
    ("has_1_imagelon", _.gelontOrelonlselon(NumImagelonsFelonaturelon, Nonelon).elonxists(_ == 1)),
    ("has_2_imagelons", _.gelontOrelonlselon(NumImagelonsFelonaturelon, Nonelon).elonxists(_ == 2)),
    ("has_3_imagelons", _.gelontOrelonlselon(NumImagelonsFelonaturelon, Nonelon).elonxists(_ == 3)),
    ("has_4_imagelons", _.gelontOrelonlselon(NumImagelonsFelonaturelon, Nonelon).elonxists(_ == 4)),
    ("has_card", _.gelontOrelonlselon(elonarlybirdFelonaturelon, Nonelon).elonxists(_.hasCard)),
    ("3_or_morelon_conseloncutivelon_not_in_nelontwork", _ => falselon),
    ("2_or_morelon_conseloncutivelon_not_in_nelontwork", _ => falselon),
    ("5_out_of_7_not_in_nelontwork", _ => falselon),
    ("7_out_of_7_not_in_nelontwork", _ => falselon),
    ("5_out_of_5_not_in_nelontwork", _ => falselon),
    ("uselonr_follow_count_gtelon_50", _.gelontOrelonlselon(UselonrFollowingCountFelonaturelon, Nonelon).elonxists(_ > 50)),
    ("has_likelond_by_social_contelonxt", _ => falselon),
    ("has_followelond_by_social_contelonxt", _ => falselon),
    ("has_topic_social_contelonxt", _ => falselon),
    ("timelonlinelon_elonntry_has_bannelonr", _ => falselon),
    ("selonrvelond_in_convelonrsation_modulelon", _.gelontOrelonlselon(SelonrvelondInConvelonrsationModulelonFelonaturelon, falselon)),
    (
      "convelonrsation_modulelon_has_2_displayelond_twelonelonts",
      _.gelontOrelonlselon(ConvelonrsationModulelon2DisplayelondTwelonelontsFelonaturelon, falselon)),
    ("convelonrsation_modulelon_has_gap", _.gelontOrelonlselon(ConvelonrsationModulelonHasGapFelonaturelon, falselon)),
    ("selonrvelond_in_reloncap_twelonelont_candidatelon_modulelon_injelonction", _ => falselon),
    ("selonrvelond_in_threlonadelond_convelonrsation_modulelon", _ => falselon)
  )

  val PrelondicatelonMap = CandidatelonPrelondicatelons.toMap
}
