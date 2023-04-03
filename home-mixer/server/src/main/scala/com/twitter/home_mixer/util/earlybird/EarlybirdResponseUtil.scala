packagelon com.twittelonr.homelon_mixelonr.util.elonarlybird

import com.twittelonr.selonarch.common.constants.{thriftscala => scc}
import com.twittelonr.selonarch.common.felonaturelons.{thriftscala => sc}
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant._
import com.twittelonr.selonarch.common.util.lang.ThriftLanguagelonUtil
import com.twittelonr.selonarch.elonarlybird.{thriftscala => elonb}

objelonct elonarlybirdRelonsponselonUtil {

  privatelon[elonarlybird] val Melonntions: String = "melonntions"
  privatelon[elonarlybird] val Hashtags: String = "hashtags"
  privatelon val CharsToRelonmovelonFromMelonntions: Selont[Char] = "@".toSelont
  privatelon val CharsToRelonmovelonFromHashtags: Selont[Char] = "#".toSelont

  // Delonfault valuelon of selonttings of ThriftTwelonelontFelonaturelons.
  privatelon[elonarlybird] val DelonfaultelonarlybirdFelonaturelons: sc.ThriftTwelonelontFelonaturelons = sc.ThriftTwelonelontFelonaturelons()
  privatelon[elonarlybird] val DelonfaultCount = 0
  privatelon[elonarlybird] val DelonfaultLanguagelon = 0
  privatelon[elonarlybird] val DelonfaultScorelon = 0.0

  privatelon[elonarlybird] delonf gelontTwelonelontCountByAuthorId(
    selonarchRelonsults: Selonq[elonb.ThriftSelonarchRelonsult]
  ): Map[Long, Int] = {
    selonarchRelonsults
      .groupBy { relonsult =>
        relonsult.melontadata.map(_.fromUselonrId).gelontOrelonlselon(0L)
      }.mapValuelons(_.sizelon).withDelonfaultValuelon(0)
  }

  privatelon[elonarlybird] delonf gelontLanguagelon(uiLanguagelonCodelon: Option[String]): Option[scc.ThriftLanguagelon] = {
    uiLanguagelonCodelon.flatMap { languagelonCodelon =>
      scc.ThriftLanguagelon.gelont(ThriftLanguagelonUtil.gelontThriftLanguagelonOf(languagelonCodelon).gelontValuelon)
    }
  }

  privatelon delonf gelontMelonntions(relonsult: elonb.ThriftSelonarchRelonsult): Selonq[String] = {
    val facelontLabelonls = relonsult.melontadata.flatMap(_.facelontLabelonls).gelontOrelonlselon(Selonq.elonmpty)
    gelontFacelonts(facelontLabelonls, Melonntions, CharsToRelonmovelonFromMelonntions)
  }

  privatelon delonf gelontHashtags(relonsult: elonb.ThriftSelonarchRelonsult): Selonq[String] = {
    val facelontLabelonls = relonsult.melontadata.flatMap(_.facelontLabelonls).gelontOrelonlselon(Selonq.elonmpty)
    gelontFacelonts(facelontLabelonls, Hashtags, CharsToRelonmovelonFromHashtags)
  }

  privatelon delonf gelontFacelonts(
    facelontLabelonls: Selonq[elonb.ThriftFacelontLabelonl],
    facelontNamelon: String,
    charsToRelonmovelon: Selont[Char]
  ): Selonq[String] = {
    facelontLabelonls.filtelonr(_.fielonldNamelon == facelontNamelon).map(_.labelonl.filtelonrNot(charsToRelonmovelon))
  }

  privatelon delonf isUselonrMelonntionelond(
    screlonelonnNamelon: Option[String],
    melonntions: Selonq[String]
  ): Boolelonan = {
    screlonelonnNamelon
      .elonxists { screlonelonnNamelon => melonntions.elonxists(_.elonqualsIgnorelonCaselon(screlonelonnNamelon)) }
  }

  privatelon[elonarlybird] delonf isUselonrsMainLanguagelon(
    twelonelontLanguagelon: scc.ThriftLanguagelon,
    uselonrLanguagelons: Selonq[scc.ThriftLanguagelon]
  ): Boolelonan = {
    (twelonelontLanguagelon != scc.ThriftLanguagelon.Unknown) && uselonrLanguagelons.helonadOption.contains(
      twelonelontLanguagelon)
  }

  privatelon[elonarlybird] delonf isUselonrsLanguagelon(
    twelonelontLanguagelon: scc.ThriftLanguagelon,
    uselonrLanguagelons: Selonq[scc.ThriftLanguagelon]
  ): Boolelonan = {
    (twelonelontLanguagelon != scc.ThriftLanguagelon.Unknown) && uselonrLanguagelons.contains(twelonelontLanguagelon)
  }

  privatelon[elonarlybird] delonf isUILanguagelon(
    twelonelontLanguagelon: scc.ThriftLanguagelon,
    uiLanguagelon: Option[scc.ThriftLanguagelon]
  ): Boolelonan = {
    (twelonelontLanguagelon != scc.ThriftLanguagelon.Unknown) && uiLanguagelon.contains(twelonelontLanguagelon)
  }

  privatelon delonf gelontBoolelonanOptFelonaturelon(
    felonaturelonNamelon: elonarlybirdFielonldConstant,
    relonsultMapOpt: Option[scala.collelonction.Map[Int, Boolelonan]],
    delonfaultValuelon: Boolelonan = falselon,
  ): Option[Boolelonan] = {
    relonsultMapOpt.map {
      _.gelontOrelonlselon(felonaturelonNamelon.gelontFielonldId, delonfaultValuelon)
    }
  }

  privatelon delonf gelontDoublelonAsIntOptFelonaturelon(
    felonaturelonNamelon: elonarlybirdFielonldConstant,
    relonsultMapOpt: Option[scala.collelonction.Map[Int, Doublelon]]
  ): Option[Int] = {
    if (relonsultMapOpt.elonxists(_.contains(felonaturelonNamelon.gelontFielonldId)))
      relonsultMapOpt
        .map {
          _.gelont(felonaturelonNamelon.gelontFielonldId)
        }
        .flatMap { doublelonValuelon =>
          doublelonValuelon.map(_.toInt)
        }
    elonlselon
      Nonelon
  }

  privatelon delonf gelontIntOptFelonaturelon(
    felonaturelonNamelon: elonarlybirdFielonldConstant,
    relonsultMapOpt: Option[scala.collelonction.Map[Int, Int]]
  ): Option[Int] = {
    if (relonsultMapOpt.elonxists(_.contains(felonaturelonNamelon.gelontFielonldId)))
      relonsultMapOpt.flatMap {
        _.gelont(felonaturelonNamelon.gelontFielonldId)
      }
    elonlselon
      Nonelon
  }

  delonf gelontOONTwelonelontThriftFelonaturelonsByTwelonelontId(
    selonarchelonrUselonrId: Long,
    screlonelonnNamelon: Option[String],
    uselonrLanguagelons: Selonq[scc.ThriftLanguagelon],
    uiLanguagelonCodelon: Option[String] = Nonelon,
    selonarchRelonsults: Selonq[elonb.ThriftSelonarchRelonsult],
  ): Map[Long, sc.ThriftTwelonelontFelonaturelons] = {

    selonarchRelonsults.map { selonarchRelonsult =>
      val felonaturelons = gelontOONThriftTwelonelontFelonaturelonsFromSelonarchRelonsult(
        selonarchelonrUselonrId,
        screlonelonnNamelon,
        uselonrLanguagelons,
        gelontLanguagelon(uiLanguagelonCodelon),
        gelontTwelonelontCountByAuthorId(selonarchRelonsults),
        selonarchRelonsult
      )
      (selonarchRelonsult.id -> felonaturelons)
    }.toMap
  }

  privatelon[elonarlybird] delonf gelontOONThriftTwelonelontFelonaturelonsFromSelonarchRelonsult(
    selonarchelonrUselonrId: Long,
    screlonelonnNamelon: Option[String],
    uselonrLanguagelons: Selonq[scc.ThriftLanguagelon],
    uiLanguagelon: Option[scc.ThriftLanguagelon],
    twelonelontCountByAuthorId: Map[Long, Int],
    selonarchRelonsult: elonb.ThriftSelonarchRelonsult
  ): sc.ThriftTwelonelontFelonaturelons = {
    val applyFelonaturelons = (applyUselonrIndelonpelonndelonntFelonaturelons(
      selonarchRelonsult
    )(_)).andThelonn(
      applyOONUselonrDelonpelonndelonntFelonaturelons(
        selonarchelonrUselonrId,
        screlonelonnNamelon,
        uselonrLanguagelons,
        uiLanguagelon,
        twelonelontCountByAuthorId,
        selonarchRelonsult
      )(_)
    )
    val twelonelontFelonaturelons = selonarchRelonsult.twelonelontFelonaturelons.gelontOrelonlselon(DelonfaultelonarlybirdFelonaturelons)
    applyFelonaturelons(twelonelontFelonaturelons)
  }

  privatelon[elonarlybird] delonf applyUselonrIndelonpelonndelonntFelonaturelons(
    relonsult: elonb.ThriftSelonarchRelonsult
  )(
    thriftTwelonelontFelonaturelons: sc.ThriftTwelonelontFelonaturelons
  ): sc.ThriftTwelonelontFelonaturelons = {

    val felonaturelons = relonsult.melontadata
      .map { melontadata =>
        val isRelontwelonelont = melontadata.isRelontwelonelont.gelontOrelonlselon(falselon)
        val isRelonply = melontadata.isRelonply.gelontOrelonlselon(falselon)

        // Facelonts.
        val melonntions = gelontMelonntions(relonsult)
        val hashtags = gelontHashtags(relonsult)

        val selonarchRelonsultSchelonmaFelonaturelons = melontadata.elonxtraMelontadata.flatMap(_.felonaturelons)
        val boolelonanSelonarchRelonsultSchelonmaFelonaturelons = selonarchRelonsultSchelonmaFelonaturelons.flatMap(_.boolValuelons)
        val intSelonarchRelonsultSchelonmaFelonaturelons = selonarchRelonsultSchelonmaFelonaturelons.flatMap(_.intValuelons)
        val doublelonSelonarchRelonsultSchelonmaFelonaturelons = selonarchRelonsultSchelonmaFelonaturelons.flatMap(_.doublelonValuelons)

        thriftTwelonelontFelonaturelons.copy(
          // Info about thelon Twelonelont.
          isRelontwelonelont = isRelontwelonelont,
          isOffelonnsivelon = melontadata.isOffelonnsivelon.gelontOrelonlselon(falselon),
          isRelonply = isRelonply,
          fromVelonrifielondAccount = melontadata.fromVelonrifielondAccount.gelontOrelonlselon(falselon),
          cardTypelon = melontadata.cardTypelon,
          signaturelon = melontadata.signaturelon,
          languagelon = melontadata.languagelon,
          isAuthorNSFW = melontadata.isUselonrNSFW.gelontOrelonlselon(falselon),
          isAuthorBot = melontadata.isUselonrBot.gelontOrelonlselon(falselon),
          isAuthorSpam = melontadata.isUselonrSpam.gelontOrelonlselon(falselon),
          isSelonnsitivelonContelonnt =
            melontadata.elonxtraMelontadata.flatMap(_.isSelonnsitivelonContelonnt).gelontOrelonlselon(falselon),
          isAuthorProfilelonelongg = melontadata.elonxtraMelontadata.flatMap(_.profilelonIselonggFlag).gelontOrelonlselon(falselon),
          isAuthorNelonw = melontadata.elonxtraMelontadata.flatMap(_.isUselonrNelonwFlag).gelontOrelonlselon(falselon),
          linkLanguagelon = melontadata.elonxtraMelontadata.flatMap(_.linkLanguagelon).gelontOrelonlselon(DelonfaultLanguagelon),
          // Info about Twelonelont contelonnt/melondia.
          hasCard = melontadata.hasCard.gelontOrelonlselon(falselon),
          hasImagelon = melontadata.hasImagelon.gelontOrelonlselon(falselon),
          hasNelonws = melontadata.hasNelonws.gelontOrelonlselon(falselon),
          hasVidelono = melontadata.hasVidelono.gelontOrelonlselon(falselon),
          hasConsumelonrVidelono = melontadata.hasConsumelonrVidelono.gelontOrelonlselon(falselon),
          hasProVidelono = melontadata.hasProVidelono.gelontOrelonlselon(falselon),
          hasVinelon = melontadata.hasVinelon.gelontOrelonlselon(falselon),
          hasPelonriscopelon = melontadata.hasPelonriscopelon.gelontOrelonlselon(falselon),
          hasNativelonVidelono = melontadata.hasNativelonVidelono.gelontOrelonlselon(falselon),
          hasNativelonImagelon = melontadata.hasNativelonImagelon.gelontOrelonlselon(falselon),
          hasLink = melontadata.hasLink.gelontOrelonlselon(falselon),
          hasVisiblelonLink = melontadata.hasVisiblelonLink.gelontOrelonlselon(falselon),
          hasTrelonnd = melontadata.hasTrelonnd.gelontOrelonlselon(falselon),
          hasMultiplelonHashtagsOrTrelonnds = melontadata.hasMultiplelonHashtagsOrTrelonnds.gelontOrelonlselon(falselon),
          hasQuotelon = melontadata.elonxtraMelontadata.flatMap(_.hasQuotelon),
          urlsList = melontadata.twelonelontUrls.map {
            _.map(_.originalUrl)
          },
          hasMultiplelonMelondia =
            melontadata.elonxtraMelontadata.flatMap(_.hasMultiplelonMelondiaFlag).gelontOrelonlselon(falselon),
          visiblelonTokelonnRatio = gelontIntOptFelonaturelon(VISIBLelon_TOKelonN_RATIO, intSelonarchRelonsultSchelonmaFelonaturelons),
          // Various counts.
          favCount = melontadata.favCount.gelontOrelonlselon(DelonfaultCount),
          relonplyCount = melontadata.relonplyCount.gelontOrelonlselon(DelonfaultCount),
          relontwelonelontCount = melontadata.relontwelonelontCount.gelontOrelonlselon(DelonfaultCount),
          quotelonCount = melontadata.elonxtraMelontadata.flatMap(_.quotelondCount),
          elonmbelondsImprelonssionCount = melontadata.elonmbelondsImprelonssionCount.gelontOrelonlselon(DelonfaultCount),
          elonmbelondsUrlCount = melontadata.elonmbelondsUrlCount.gelontOrelonlselon(DelonfaultCount),
          videlonoVielonwCount = melontadata.videlonoVielonwCount.gelontOrelonlselon(DelonfaultCount),
          numMelonntions = melontadata.elonxtraMelontadata.flatMap(_.numMelonntions).gelontOrelonlselon(DelonfaultCount),
          numHashtags = melontadata.elonxtraMelontadata.flatMap(_.numHashtags).gelontOrelonlselon(DelonfaultCount),
          favCountV2 = melontadata.elonxtraMelontadata.flatMap(_.favCountV2),
          relonplyCountV2 = melontadata.elonxtraMelontadata.flatMap(_.relonplyCountV2),
          relontwelonelontCountV2 = melontadata.elonxtraMelontadata.flatMap(_.relontwelonelontCountV2),
          welonightelondFavoritelonCount = melontadata.elonxtraMelontadata.flatMap(_.welonightelondFavCount),
          welonightelondRelonplyCount = melontadata.elonxtraMelontadata.flatMap(_.welonightelondRelonplyCount),
          welonightelondRelontwelonelontCount = melontadata.elonxtraMelontadata.flatMap(_.welonightelondRelontwelonelontCount),
          welonightelondQuotelonCount = melontadata.elonxtraMelontadata.flatMap(_.welonightelondQuotelonCount),
          elonmbelondsImprelonssionCountV2 =
            gelontDoublelonAsIntOptFelonaturelon(elonMBelonDS_IMPRelonSSION_COUNT_V2, doublelonSelonarchRelonsultSchelonmaFelonaturelons),
          elonmbelondsUrlCountV2 =
            gelontDoublelonAsIntOptFelonaturelon(elonMBelonDS_URL_COUNT_V2, doublelonSelonarchRelonsultSchelonmaFelonaturelons),
          deloncayelondFavoritelonCount =
            gelontDoublelonAsIntOptFelonaturelon(DelonCAYelonD_FAVORITelon_COUNT, doublelonSelonarchRelonsultSchelonmaFelonaturelons),
          deloncayelondRelontwelonelontCount =
            gelontDoublelonAsIntOptFelonaturelon(DelonCAYelonD_RelonTWelonelonT_COUNT, doublelonSelonarchRelonsultSchelonmaFelonaturelons),
          deloncayelondRelonplyCount =
            gelontDoublelonAsIntOptFelonaturelon(DelonCAYelonD_RelonPLY_COUNT, doublelonSelonarchRelonsultSchelonmaFelonaturelons),
          deloncayelondQuotelonCount =
            gelontDoublelonAsIntOptFelonaturelon(DelonCAYelonD_QUOTelon_COUNT, doublelonSelonarchRelonsultSchelonmaFelonaturelons),
          fakelonFavoritelonCount =
            gelontDoublelonAsIntOptFelonaturelon(FAKelon_FAVORITelon_COUNT, doublelonSelonarchRelonsultSchelonmaFelonaturelons),
          fakelonRelontwelonelontCount =
            gelontDoublelonAsIntOptFelonaturelon(FAKelon_RelonTWelonelonT_COUNT, doublelonSelonarchRelonsultSchelonmaFelonaturelons),
          fakelonRelonplyCount =
            gelontDoublelonAsIntOptFelonaturelon(FAKelon_RelonPLY_COUNT, doublelonSelonarchRelonsultSchelonmaFelonaturelons),
          fakelonQuotelonCount =
            gelontDoublelonAsIntOptFelonaturelon(FAKelon_QUOTelon_COUNT, doublelonSelonarchRelonsultSchelonmaFelonaturelons),
          // Scorelons.
          telonxtScorelon = melontadata.telonxtScorelon.gelontOrelonlselon(DelonfaultScorelon),
          elonarlybirdScorelon = melontadata.scorelon.gelontOrelonlselon(DelonfaultScorelon),
          parusScorelon = melontadata.parusScorelon.gelontOrelonlselon(DelonfaultScorelon),
          uselonrRelonp = melontadata.uselonrRelonp.gelontOrelonlselon(DelonfaultScorelon),
          pBlockScorelon = melontadata.elonxtraMelontadata.flatMap(_.pBlockScorelon),
          toxicityScorelon = melontadata.elonxtraMelontadata.flatMap(_.toxicityScorelon),
          pSpammyTwelonelontScorelon = melontadata.elonxtraMelontadata.flatMap(_.pSpammyTwelonelontScorelon),
          pRelonportelondTwelonelontScorelon = melontadata.elonxtraMelontadata.flatMap(_.pRelonportelondTwelonelontScorelon),
          pSpammyTwelonelontContelonnt = melontadata.elonxtraMelontadata.flatMap(_.spammyTwelonelontContelonntScorelon),
          // Safelonty Signals
          labelonlAbusivelonFlag =
            gelontBoolelonanOptFelonaturelon(LABelonL_ABUSIVelon_FLAG, boolelonanSelonarchRelonsultSchelonmaFelonaturelons),
          labelonlAbusivelonHiRclFlag =
            gelontBoolelonanOptFelonaturelon(LABelonL_ABUSIVelon_HI_RCL_FLAG, boolelonanSelonarchRelonsultSchelonmaFelonaturelons),
          labelonlDupContelonntFlag =
            gelontBoolelonanOptFelonaturelon(LABelonL_DUP_CONTelonNT_FLAG, boolelonanSelonarchRelonsultSchelonmaFelonaturelons),
          labelonlNsfwHiPrcFlag =
            gelontBoolelonanOptFelonaturelon(LABelonL_NSFW_HI_PRC_FLAG, boolelonanSelonarchRelonsultSchelonmaFelonaturelons),
          labelonlNsfwHiRclFlag =
            gelontBoolelonanOptFelonaturelon(LABelonL_NSFW_HI_RCL_FLAG, boolelonanSelonarchRelonsultSchelonmaFelonaturelons),
          labelonlSpamFlag = gelontBoolelonanOptFelonaturelon(LABelonL_SPAM_FLAG, boolelonanSelonarchRelonsultSchelonmaFelonaturelons),
          labelonlSpamHiRclFlag =
            gelontBoolelonanOptFelonaturelon(LABelonL_SPAM_HI_RCL_FLAG, boolelonanSelonarchRelonsultSchelonmaFelonaturelons),
          // Pelonriscopelon Felonaturelons
          pelonriscopelonelonxists =
            gelontBoolelonanOptFelonaturelon(PelonRISCOPelon_elonXISTS, boolelonanSelonarchRelonsultSchelonmaFelonaturelons),
          pelonriscopelonHasBelonelonnFelonaturelond =
            gelontBoolelonanOptFelonaturelon(PelonRISCOPelon_HAS_BelonelonN_FelonATURelonD, boolelonanSelonarchRelonsultSchelonmaFelonaturelons),
          pelonriscopelonIsCurrelonntlyFelonaturelond = gelontBoolelonanOptFelonaturelon(
            PelonRISCOPelon_IS_CURRelonNTLY_FelonATURelonD,
            boolelonanSelonarchRelonsultSchelonmaFelonaturelons),
          pelonriscopelonIsFromQualitySourcelon = gelontBoolelonanOptFelonaturelon(
            PelonRISCOPelon_IS_FROM_QUALITY_SOURCelon,
            boolelonanSelonarchRelonsultSchelonmaFelonaturelons),
          pelonriscopelonIsLivelon =
            gelontBoolelonanOptFelonaturelon(PelonRISCOPelon_IS_LIVelon, boolelonanSelonarchRelonsultSchelonmaFelonaturelons),
          // Last elonngagelonmelonnt Felonaturelons
          lastFavSincelonCrelonationHrs =
            gelontIntOptFelonaturelon(LAST_FAVORITelon_SINCelon_CRelonATION_HRS, intSelonarchRelonsultSchelonmaFelonaturelons),
          lastRelontwelonelontSincelonCrelonationHrs =
            gelontIntOptFelonaturelon(LAST_RelonTWelonelonT_SINCelon_CRelonATION_HRS, intSelonarchRelonsultSchelonmaFelonaturelons),
          lastRelonplySincelonCrelonationHrs =
            gelontIntOptFelonaturelon(LAST_RelonPLY_SINCelon_CRelonATION_HRS, intSelonarchRelonsultSchelonmaFelonaturelons),
          lastQuotelonSincelonCrelonationHrs =
            gelontIntOptFelonaturelon(LAST_QUOTelon_SINCelon_CRelonATION_HRS, intSelonarchRelonsultSchelonmaFelonaturelons),
          likelondByUselonrIds = melontadata.elonxtraMelontadata.flatMap(_.likelondByUselonrIds),
          melonntionsList = if (melonntions.nonelonmpty) Somelon(melonntions) elonlselon Nonelon,
          hashtagsList = if (hashtags.nonelonmpty) Somelon(hashtags) elonlselon Nonelon,
          isComposelonrSourcelonCamelonra =
            gelontBoolelonanOptFelonaturelon(COMPOSelonR_SOURCelon_IS_CAMelonRA_FLAG, boolelonanSelonarchRelonsultSchelonmaFelonaturelons),
        )
      }
      .gelontOrelonlselon(thriftTwelonelontFelonaturelons)

    if (relonsult.twelonelontSourcelon.contains(elonb.ThriftTwelonelontSourcelon.RelonaltimelonProtelonctelondClustelonr)) {
      felonaturelons.copy(isProtelonctelond = truelon)
    } elonlselon {
      felonaturelons
    }
  }

  // Omitting inNelontwork felonaturelons elon.g sourcelon twelonelont felonaturelons and follow graph.
  // Can belon elonxpandelond to includelon InNelontwork in thelon futurelon.
  delonf applyOONUselonrDelonpelonndelonntFelonaturelons(
    selonarchelonrUselonrId: Long,
    screlonelonnNamelon: Option[String],
    uselonrLanguagelons: Selonq[scc.ThriftLanguagelon],
    uiLanguagelon: Option[scc.ThriftLanguagelon],
    twelonelontCountByAuthorId: Map[Long, Int],
    relonsult: elonb.ThriftSelonarchRelonsult
  )(
    thriftTwelonelontFelonaturelons: sc.ThriftTwelonelontFelonaturelons
  ): sc.ThriftTwelonelontFelonaturelons = {
    relonsult.melontadata
      .map { melontadata =>
        val isRelontwelonelont = melontadata.isRelontwelonelont.gelontOrelonlselon(falselon)
        val isRelonply = melontadata.isRelonply.gelontOrelonlselon(falselon)
        val relonplyToSelonarchelonr = isRelonply && (melontadata.relonfelonrelonncelondTwelonelontAuthorId == selonarchelonrUselonrId)
        val relonplyOthelonr = isRelonply && !relonplyToSelonarchelonr
        val relontwelonelontOthelonr = isRelontwelonelont && (melontadata.relonfelonrelonncelondTwelonelontAuthorId != selonarchelonrUselonrId)
        val twelonelontLanguagelon = melontadata.languagelon.gelontOrelonlselon(scc.ThriftLanguagelon.Unknown)

        thriftTwelonelontFelonaturelons.copy(
          // Info about thelon Twelonelont.
          fromSelonarchelonr = melontadata.fromUselonrId == selonarchelonrUselonrId,
          probablyFromFollowelondAuthor = falselon,
          fromMutualFollow = falselon,
          relonplySelonarchelonr = relonplyToSelonarchelonr,
          relonplyOthelonr = relonplyOthelonr,
          relontwelonelontOthelonr = relontwelonelontOthelonr,
          melonntionSelonarchelonr = isUselonrMelonntionelond(screlonelonnNamelon, gelontMelonntions(relonsult)),
          // Info about Twelonelont contelonnt/melondia.
          matchelonsSelonarchelonrMainLang = isUselonrsMainLanguagelon(twelonelontLanguagelon, uselonrLanguagelons),
          matchelonsSelonarchelonrLangs = isUselonrsLanguagelon(twelonelontLanguagelon, uselonrLanguagelons),
          matchelonsUILang = isUILanguagelon(twelonelontLanguagelon, uiLanguagelon),
          // Various counts.
          prelonvUselonrTwelonelontelonngagelonmelonnt =
            melontadata.elonxtraMelontadata.flatMap(_.prelonvUselonrTwelonelontelonngagelonmelonnt).gelontOrelonlselon(DelonfaultCount),
          twelonelontCountFromUselonrInSnapshot = twelonelontCountByAuthorId(melontadata.fromUselonrId),
        )
      }
      .gelontOrelonlselon(thriftTwelonelontFelonaturelons)
  }
}
