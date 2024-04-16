package com.tw ter.ho _m xer.ut l.earlyb rd

 mport com.tw ter.search.common.constants.{thr ftscala => scc}
 mport com.tw ter.search.common.features.{thr ftscala => sc}
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant._
 mport com.tw ter.search.common.ut l.lang.Thr ftLanguageUt l
 mport com.tw ter.search.earlyb rd.{thr ftscala => eb}
 mport com.tw ter.t  l nes.earlyb rd.common.ut ls. nNetworkEngage nt

object Earlyb rdResponseUt l {

  pr vate[earlyb rd] val  nt ons: Str ng = " nt ons"
  pr vate[earlyb rd] val Hashtags: Str ng = "hashtags"
  pr vate val CharsToRemoveFrom nt ons: Set[Char] = "@".toSet
  pr vate val CharsToRemoveFromHashtags: Set[Char] = "#".toSet

  // Default value of sett ngs of Thr ftT etFeatures.
  pr vate[earlyb rd] val DefaultEarlyb rdFeatures: sc.Thr ftT etFeatures = sc.Thr ftT etFeatures()
  pr vate[earlyb rd] val DefaultCount = 0
  pr vate[earlyb rd] val DefaultLanguage = 0
  pr vate[earlyb rd] val DefaultScore = 0.0

  pr vate[earlyb rd] def getT etCountByAuthor d(
    searchResults: Seq[eb.Thr ftSearchResult]
  ): Map[Long,  nt] = {
    searchResults
      .groupBy { result =>
        result. tadata.map(_.fromUser d).getOrElse(0L)
      }.mapValues(_.s ze).w hDefaultValue(0)
  }

  pr vate[earlyb rd] def getLanguage(u LanguageCode: Opt on[Str ng]): Opt on[scc.Thr ftLanguage] = {
    u LanguageCode.flatMap { languageCode =>
      scc.Thr ftLanguage.get(Thr ftLanguageUt l.getThr ftLanguageOf(languageCode).getValue)
    }
  }

  pr vate def get nt ons(result: eb.Thr ftSearchResult): Seq[Str ng] = {
    val facetLabels = result. tadata.flatMap(_.facetLabels).getOrElse(Seq.empty)
    getFacets(facetLabels,  nt ons, CharsToRemoveFrom nt ons)
  }

  pr vate def getHashtags(result: eb.Thr ftSearchResult): Seq[Str ng] = {
    val facetLabels = result. tadata.flatMap(_.facetLabels).getOrElse(Seq.empty)
    getFacets(facetLabels, Hashtags, CharsToRemoveFromHashtags)
  }

  pr vate def getFacets(
    facetLabels: Seq[eb.Thr ftFacetLabel],
    facetNa : Str ng,
    charsToRemove: Set[Char]
  ): Seq[Str ng] = {
    facetLabels.f lter(_.f eldNa  == facetNa ).map(_.label.f lterNot(charsToRemove))
  }

  pr vate def  sUser nt oned(
    screenNa : Opt on[Str ng],
     nt ons: Seq[Str ng],
     nt ons nS ceT et: Seq[Str ng]
  ): Boolean =
     sUser nt oned(screenNa ,  nt ons) ||  sUser nt oned(screenNa ,  nt ons nS ceT et)

  pr vate def  sUser nt oned(
    screenNa : Opt on[Str ng],
     nt ons: Seq[Str ng]
  ): Boolean = {
    screenNa 
      .ex sts { screenNa  =>  nt ons.ex sts(_.equals gnoreCase(screenNa )) }
  }

  pr vate[earlyb rd] def  sUsersMa nLanguage(
    t etLanguage: scc.Thr ftLanguage,
    userLanguages: Seq[scc.Thr ftLanguage]
  ): Boolean = {
    (t etLanguage != scc.Thr ftLanguage.Unknown) && userLanguages. adOpt on.conta ns(
      t etLanguage)
  }

  pr vate[earlyb rd] def  sUsersLanguage(
    t etLanguage: scc.Thr ftLanguage,
    userLanguages: Seq[scc.Thr ftLanguage]
  ): Boolean = {
    (t etLanguage != scc.Thr ftLanguage.Unknown) && userLanguages.conta ns(t etLanguage)
  }

  pr vate[earlyb rd] def  sU Language(
    t etLanguage: scc.Thr ftLanguage,
    u Language: Opt on[scc.Thr ftLanguage]
  ): Boolean = {
    (t etLanguage != scc.Thr ftLanguage.Unknown) && u Language.conta ns(t etLanguage)
  }

  pr vate def getBooleanOptFeature(
    featureNa : Earlyb rdF eldConstant,
    resultMapOpt: Opt on[scala.collect on.Map[ nt, Boolean]],
    defaultValue: Boolean = false,
  ): Opt on[Boolean] = {
    resultMapOpt.map {
      _.getOrElse(featureNa .getF eld d, defaultValue)
    }
  }

  pr vate def getDoubleAs ntOptFeature(
    featureNa : Earlyb rdF eldConstant,
    resultMapOpt: Opt on[scala.collect on.Map[ nt, Double]]
  ): Opt on[ nt] = {
     f (resultMapOpt.ex sts(_.conta ns(featureNa .getF eld d)))
      resultMapOpt
        .map {
          _.get(featureNa .getF eld d)
        }
        .flatMap { doubleValue =>
          doubleValue.map(_.to nt)
        }
    else
      None
  }

  pr vate def get ntOptFeature(
    featureNa : Earlyb rdF eldConstant,
    resultMapOpt: Opt on[scala.collect on.Map[ nt,  nt]]
  ): Opt on[ nt] = {
     f (resultMapOpt.ex sts(_.conta ns(featureNa .getF eld d)))
      resultMapOpt.flatMap {
        _.get(featureNa .getF eld d)
      }
    else
      None
  }

  def getT etThr ftFeaturesByT et d(
    searc rUser d: Long,
    screenNa : Opt on[Str ng],
    userLanguages: Seq[scc.Thr ftLanguage],
    u LanguageCode: Opt on[Str ng] = None,
    follo dUser ds: Set[Long],
    mutuallyFollow ngUser ds: Set[Long],
    searchResults: Seq[eb.Thr ftSearchResult],
    s ceT etSearchResults: Seq[eb.Thr ftSearchResult],
  ): Map[Long, sc.Thr ftT etFeatures] = {

    val allSearchResults = searchResults ++ s ceT etSearchResults
    val s ceT etSearchResultBy d =
      s ceT etSearchResults.map(result => (result. d -> result)).toMap
    val  nNetworkEngage nt =
       nNetworkEngage nt(follo dUser ds.toSeq, mutuallyFollow ngUser ds, allSearchResults)
    searchResults.map { searchResult =>
      val features = getThr ftT etFeaturesFromSearchResult(
        searc rUser d,
        screenNa ,
        userLanguages,
        getLanguage(u LanguageCode),
        getT etCountByAuthor d(searchResults),
        follo dUser ds,
        mutuallyFollow ngUser ds,
        s ceT etSearchResultBy d,
         nNetworkEngage nt,
        searchResult
      )
      (searchResult. d -> features)
    }.toMap
  }

  pr vate[earlyb rd] def getThr ftT etFeaturesFromSearchResult(
    searc rUser d: Long,
    screenNa : Opt on[Str ng],
    userLanguages: Seq[scc.Thr ftLanguage],
    u Language: Opt on[scc.Thr ftLanguage],
    t etCountByAuthor d: Map[Long,  nt],
    follo dUser ds: Set[Long],
    mutuallyFollow ngUser ds: Set[Long],
    s ceT etSearchResultBy d: Map[Long, eb.Thr ftSearchResult],
     nNetworkEngage nt:  nNetworkEngage nt,
    searchResult: eb.Thr ftSearchResult,
  ): sc.Thr ftT etFeatures = {
    val applyFeatures = (applyUser ndependentFeatures(
      searchResult
    )(_)).andT n(
      applyUserDependentFeatures(
        searc rUser d,
        screenNa ,
        userLanguages,
        u Language,
        t etCountByAuthor d,
        follo dUser ds,
        mutuallyFollow ngUser ds,
        s ceT etSearchResultBy d,
         nNetworkEngage nt,
        searchResult
      )(_)
    )
    val t etFeatures = searchResult.t etFeatures.getOrElse(DefaultEarlyb rdFeatures)
    applyFeatures(t etFeatures)
  }

  pr vate[earlyb rd] def applyUser ndependentFeatures(
    result: eb.Thr ftSearchResult
  )(
    thr ftT etFeatures: sc.Thr ftT etFeatures
  ): sc.Thr ftT etFeatures = {

    val features = result. tadata
      .map {  tadata =>
        val  sRet et =  tadata. sRet et.getOrElse(false)
        val  sReply =  tadata. sReply.getOrElse(false)

        // Facets.
        val  nt ons = get nt ons(result)
        val hashtags = getHashtags(result)

        val searchResultSc maFeatures =  tadata.extra tadata.flatMap(_.features)
        val booleanSearchResultSc maFeatures = searchResultSc maFeatures.flatMap(_.boolValues)
        val  ntSearchResultSc maFeatures = searchResultSc maFeatures.flatMap(_. ntValues)
        val doubleSearchResultSc maFeatures = searchResultSc maFeatures.flatMap(_.doubleValues)

        thr ftT etFeatures.copy(
          //  nfo about t  T et.
           sRet et =  sRet et,
           sOffens ve =  tadata. sOffens ve.getOrElse(false),
           sReply =  sReply,
          fromVer f edAccount =  tadata.fromVer f edAccount.getOrElse(false),
          cardType =  tadata.cardType,
          s gnature =  tadata.s gnature,
          language =  tadata.language,
           sAuthorNSFW =  tadata. sUserNSFW.getOrElse(false),
           sAuthorBot =  tadata. sUserBot.getOrElse(false),
           sAuthorSpam =  tadata. sUserSpam.getOrElse(false),
           sSens  veContent =
             tadata.extra tadata.flatMap(_. sSens  veContent).getOrElse(false),
           sAuthorProf leEgg =  tadata.extra tadata.flatMap(_.prof le sEggFlag).getOrElse(false),
           sAuthorNew =  tadata.extra tadata.flatMap(_. sUserNewFlag).getOrElse(false),
          l nkLanguage =  tadata.extra tadata.flatMap(_.l nkLanguage).getOrElse(DefaultLanguage),
          //  nfo about T et content/ d a.
          hasCard =  tadata.hasCard.getOrElse(false),
          has mage =  tadata.has mage.getOrElse(false),
          hasNews =  tadata.hasNews.getOrElse(false),
          hasV deo =  tadata.hasV deo.getOrElse(false),
          hasConsu rV deo =  tadata.hasConsu rV deo.getOrElse(false),
          hasProV deo =  tadata.hasProV deo.getOrElse(false),
          hasV ne =  tadata.hasV ne.getOrElse(false),
          hasPer scope =  tadata.hasPer scope.getOrElse(false),
          hasNat veV deo =  tadata.hasNat veV deo.getOrElse(false),
          hasNat ve mage =  tadata.hasNat ve mage.getOrElse(false),
          hasL nk =  tadata.hasL nk.getOrElse(false),
          hasV s bleL nk =  tadata.hasV s bleL nk.getOrElse(false),
          hasTrend =  tadata.hasTrend.getOrElse(false),
          hasMult pleHashtagsOrTrends =  tadata.hasMult pleHashtagsOrTrends.getOrElse(false),
          hasQuote =  tadata.extra tadata.flatMap(_.hasQuote),
          urlsL st =  tadata.t etUrls.map {
            _.map(_.or g nalUrl)
          },
          hasMult ple d a =
             tadata.extra tadata.flatMap(_.hasMult ple d aFlag).getOrElse(false),
          v s bleTokenRat o = get ntOptFeature(V S BLE_TOKEN_RAT O,  ntSearchResultSc maFeatures),
          // Var ous counts.
          favCount =  tadata.favCount.getOrElse(DefaultCount),
          replyCount =  tadata.replyCount.getOrElse(DefaultCount),
          ret etCount =  tadata.ret etCount.getOrElse(DefaultCount),
          quoteCount =  tadata.extra tadata.flatMap(_.quotedCount),
          embeds mpress onCount =  tadata.embeds mpress onCount.getOrElse(DefaultCount),
          embedsUrlCount =  tadata.embedsUrlCount.getOrElse(DefaultCount),
          v deoV ewCount =  tadata.v deoV ewCount.getOrElse(DefaultCount),
          num nt ons =  tadata.extra tadata.flatMap(_.num nt ons).getOrElse(DefaultCount),
          numHashtags =  tadata.extra tadata.flatMap(_.numHashtags).getOrElse(DefaultCount),
          favCountV2 =  tadata.extra tadata.flatMap(_.favCountV2),
          replyCountV2 =  tadata.extra tadata.flatMap(_.replyCountV2),
          ret etCountV2 =  tadata.extra tadata.flatMap(_.ret etCountV2),
            ghtedFavor eCount =  tadata.extra tadata.flatMap(_.  ghtedFavCount),
            ghtedReplyCount =  tadata.extra tadata.flatMap(_.  ghtedReplyCount),
            ghtedRet etCount =  tadata.extra tadata.flatMap(_.  ghtedRet etCount),
            ghtedQuoteCount =  tadata.extra tadata.flatMap(_.  ghtedQuoteCount),
          embeds mpress onCountV2 =
            getDoubleAs ntOptFeature(EMBEDS_ MPRESS ON_COUNT_V2, doubleSearchResultSc maFeatures),
          embedsUrlCountV2 =
            getDoubleAs ntOptFeature(EMBEDS_URL_COUNT_V2, doubleSearchResultSc maFeatures),
          decayedFavor eCount =
            getDoubleAs ntOptFeature(DECAYED_FAVOR TE_COUNT, doubleSearchResultSc maFeatures),
          decayedRet etCount =
            getDoubleAs ntOptFeature(DECAYED_RETWEET_COUNT, doubleSearchResultSc maFeatures),
          decayedReplyCount =
            getDoubleAs ntOptFeature(DECAYED_REPLY_COUNT, doubleSearchResultSc maFeatures),
          decayedQuoteCount =
            getDoubleAs ntOptFeature(DECAYED_QUOTE_COUNT, doubleSearchResultSc maFeatures),
          fakeFavor eCount =
            getDoubleAs ntOptFeature(FAKE_FAVOR TE_COUNT, doubleSearchResultSc maFeatures),
          fakeRet etCount =
            getDoubleAs ntOptFeature(FAKE_RETWEET_COUNT, doubleSearchResultSc maFeatures),
          fakeReplyCount =
            getDoubleAs ntOptFeature(FAKE_REPLY_COUNT, doubleSearchResultSc maFeatures),
          fakeQuoteCount =
            getDoubleAs ntOptFeature(FAKE_QUOTE_COUNT, doubleSearchResultSc maFeatures),
          // Scores.
          textScore =  tadata.textScore.getOrElse(DefaultScore),
          earlyb rdScore =  tadata.score.getOrElse(DefaultScore),
          parusScore =  tadata.parusScore.getOrElse(DefaultScore),
          userRep =  tadata.userRep.getOrElse(DefaultScore),
          pBlockScore =  tadata.extra tadata.flatMap(_.pBlockScore),
          tox c yScore =  tadata.extra tadata.flatMap(_.tox c yScore),
          pSpam T etScore =  tadata.extra tadata.flatMap(_.pSpam T etScore),
          pReportedT etScore =  tadata.extra tadata.flatMap(_.pReportedT etScore),
          pSpam T etContent =  tadata.extra tadata.flatMap(_.spam T etContentScore),
          // Safety S gnals
          labelAbus veFlag =
            getBooleanOptFeature(LABEL_ABUS VE_FLAG, booleanSearchResultSc maFeatures),
          labelAbus veH RclFlag =
            getBooleanOptFeature(LABEL_ABUS VE_H _RCL_FLAG, booleanSearchResultSc maFeatures),
          labelDupContentFlag =
            getBooleanOptFeature(LABEL_DUP_CONTENT_FLAG, booleanSearchResultSc maFeatures),
          labelNsfwH PrcFlag =
            getBooleanOptFeature(LABEL_NSFW_H _PRC_FLAG, booleanSearchResultSc maFeatures),
          labelNsfwH RclFlag =
            getBooleanOptFeature(LABEL_NSFW_H _RCL_FLAG, booleanSearchResultSc maFeatures),
          labelSpamFlag = getBooleanOptFeature(LABEL_SPAM_FLAG, booleanSearchResultSc maFeatures),
          labelSpamH RclFlag =
            getBooleanOptFeature(LABEL_SPAM_H _RCL_FLAG, booleanSearchResultSc maFeatures),
          // Per scope Features
          per scopeEx sts =
            getBooleanOptFeature(PER SCOPE_EX STS, booleanSearchResultSc maFeatures),
          per scopeHasBeenFeatured =
            getBooleanOptFeature(PER SCOPE_HAS_BEEN_FEATURED, booleanSearchResultSc maFeatures),
          per scope sCurrentlyFeatured = getBooleanOptFeature(
            PER SCOPE_ S_CURRENTLY_FEATURED,
            booleanSearchResultSc maFeatures),
          per scope sFromQual yS ce = getBooleanOptFeature(
            PER SCOPE_ S_FROM_QUAL TY_SOURCE,
            booleanSearchResultSc maFeatures),
          per scope sL ve =
            getBooleanOptFeature(PER SCOPE_ S_L VE, booleanSearchResultSc maFeatures),
          // Last Engage nt Features
          lastFavS nceCreat onHrs =
            get ntOptFeature(LAST_FAVOR TE_S NCE_CREAT ON_HRS,  ntSearchResultSc maFeatures),
          lastRet etS nceCreat onHrs =
            get ntOptFeature(LAST_RETWEET_S NCE_CREAT ON_HRS,  ntSearchResultSc maFeatures),
          lastReplyS nceCreat onHrs =
            get ntOptFeature(LAST_REPLY_S NCE_CREAT ON_HRS,  ntSearchResultSc maFeatures),
          lastQuoteS nceCreat onHrs =
            get ntOptFeature(LAST_QUOTE_S NCE_CREAT ON_HRS,  ntSearchResultSc maFeatures),
          l kedByUser ds =  tadata.extra tadata.flatMap(_.l kedByUser ds),
           nt onsL st =  f ( nt ons.nonEmpty) So ( nt ons) else None,
          hashtagsL st =  f (hashtags.nonEmpty) So (hashtags) else None,
           sComposerS ceCa ra =
            getBooleanOptFeature(COMPOSER_SOURCE_ S_CAMERA_FLAG, booleanSearchResultSc maFeatures),
        )
      }
      .getOrElse(thr ftT etFeatures)

    features
  }

  pr vate def applyUserDependentFeatures(
    searc rUser d: Long,
    screenNa : Opt on[Str ng],
    userLanguages: Seq[scc.Thr ftLanguage],
    u Language: Opt on[scc.Thr ftLanguage],
    t etCountByAuthor d: Map[Long,  nt],
    follo dUser ds: Set[Long],
    mutuallyFollow ngUser ds: Set[Long],
    s ceT etSearchResultBy d: Map[Long, eb.Thr ftSearchResult],
     nNetworkEngage nt:  nNetworkEngage nt,
    result: eb.Thr ftSearchResult
  )(
    thr ftT etFeatures: sc.Thr ftT etFeatures
  ): sc.Thr ftT etFeatures = {
    result. tadata
      .map {  tadata =>
        val  sRet et =  tadata. sRet et.getOrElse(false)
        val s ceT et =
           f ( sRet et) s ceT etSearchResultBy d.get( tadata.sharedStatus d)
          else None
        val  nt ons nS ceT et = s ceT et.map(get nt ons).getOrElse(Seq.empty)

        val  sReply =  tadata. sReply.getOrElse(false)
        val replyToSearc r =  sReply && ( tadata.referencedT etAuthor d == searc rUser d)
        val replyOt r =  sReply && !replyToSearc r
        val ret etOt r =  sRet et && ( tadata.referencedT etAuthor d != searc rUser d)
        val t etLanguage =  tadata.language.getOrElse(scc.Thr ftLanguage.Unknown)

        val referencedT etAuthor d =
           f ( tadata.referencedT etAuthor d > 0) So ( tadata.referencedT etAuthor d) else None
        val  nReplyToUser d =  f (! sRet et) referencedT etAuthor d else None

        thr ftT etFeatures.copy(
          //  nfo about t  T et.
          fromSearc r =  tadata.fromUser d == searc rUser d,
          probablyFromFollo dAuthor = follo dUser ds.conta ns( tadata.fromUser d),
          fromMutualFollow = mutuallyFollow ngUser ds.conta ns( tadata.fromUser d),
          replySearc r = replyToSearc r,
          replyOt r = replyOt r,
          ret etOt r = ret etOt r,
           nt onSearc r =  sUser nt oned(screenNa , get nt ons(result),  nt ons nS ceT et),
          //  nfo about T et content/ d a.
          matc sSearc rMa nLang =  sUsersMa nLanguage(t etLanguage, userLanguages),
          matc sSearc rLangs =  sUsersLanguage(t etLanguage, userLanguages),
          matc sU Lang =  sU Language(t etLanguage, u Language),
          // Var ous counts.
          prevUserT etEngage nt =
             tadata.extra tadata.flatMap(_.prevUserT etEngage nt).getOrElse(DefaultCount),
          t etCountFromUser nSnapshot = t etCountByAuthor d( tadata.fromUser d),
          b d rect onalReplyCount =  nNetworkEngage nt.b D rect onalReplyCounts(result. d),
          un d rect onalReplyCount =  nNetworkEngage nt.un D rect onalReplyCounts(result. d),
          b d rect onalRet etCount =  nNetworkEngage nt.b D rect onalRet etCounts(result. d),
          un d rect onalRet etCount =  nNetworkEngage nt.un D rect onalRet etCounts(result. d),
          conversat onCount =  nNetworkEngage nt.descendantReplyCounts(result. d),
          d rectedAtUser d s nF rstDegree =
             f ( sReply)  nReplyToUser d.map(follo dUser ds.conta ns) else None,
        )
      }
      .getOrElse(thr ftT etFeatures)
  }
}
