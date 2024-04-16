package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.add  onalf elds.Add  onalF elds
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.thr ftscala._

/**
 * A hydrator that scrubs t et f elds that  ren't requested. Those f elds m ght be
 * present because t y  re prev ously requested and  re cac d w h t  t et.
 */
tra  UnrequestedF eldScrubber {
  def scrub(t etResult: T etResult): T etResult
  def scrub(t etData: T etData): T etData
  def scrub(t et: T et): T et
}

object UnrequestedF eldScrubber {
  def apply(opt ons: T etQuery.Opt ons): UnrequestedF eldScrubber =
     f (!opt ons.scrubUnrequestedF elds) NullScrubber
    else new Scrubber mpl(opt ons. nclude)

  pr vate object NullScrubber extends UnrequestedF eldScrubber {
    def scrub(t etResult: T etResult): T etResult = t etResult
    def scrub(t etData: T etData): T etData = t etData
    def scrub(t et: T et): T et = t et
  }

  class Scrubber mpl( : T etQuery. nclude) extends UnrequestedF eldScrubber {
    def scrub(t etResult: T etResult): T etResult =
      t etResult.map(scrub(_))

    def scrub(t etData: T etData): T etData =
      t etData.copy(
        t et = scrub(t etData.t et),
        s ceT etResult = t etData.s ceT etResult.map(scrub(_)),
        quotedT etResult =
           f (! .quotedT et) None
          else t etData.quotedT etResult.map(qtr => qtr.map(scrub))
      )

    def scrub(t et: T et): T et = {
      val t et2 = scrubKnownF elds(t et)

      val unhandledF elds =  .t etF elds -- Add  onalF elds.Comp ledF eld ds

       f (unhandledF elds. sEmpty) {
        t et2
      } else {
        t et2.unsetF elds(unhandledF elds)
      }
    }

    def scrubKnownF elds(t et: T et): T et = {
      @ nl ne
      def f lter[A](f eld d: F eld d, value: Opt on[A]): Opt on[A] =
         f ( .t etF elds.conta ns(f eld d)) value else None

      t et.copy(
        coreData = f lter(T et.CoreDataF eld. d, t et.coreData),
        urls = f lter(T et.UrlsF eld. d, t et.urls),
         nt ons = f lter(T et. nt onsF eld. d, t et. nt ons),
        hashtags = f lter(T et.HashtagsF eld. d, t et.hashtags),
        cashtags = f lter(T et.CashtagsF eld. d, t et.cashtags),
         d a = f lter(T et. d aF eld. d, t et. d a),
        place = f lter(T et.PlaceF eld. d, t et.place),
        quotedT et = f lter(T et.QuotedT etF eld. d, t et.quotedT et),
        takedownCountryCodes =
          f lter(T et.TakedownCountryCodesF eld. d, t et.takedownCountryCodes),
        counts = f lter(T et.CountsF eld. d, t et.counts.map(scrub)),
        dev ceS ce = f lter(T et.Dev ceS ceF eld. d, t et.dev ceS ce),
        perspect ve = f lter(T et.Perspect veF eld. d, t et.perspect ve),
        cards = f lter(T et.CardsF eld. d, t et.cards),
        card2 = f lter(T et.Card2F eld. d, t et.card2),
        language = f lter(T et.LanguageF eld. d, t et.language),
        spamLabels = None, // unused
        contr butor = f lter(T et.Contr butorF eld. d, t et.contr butor),
        prof leGeoEnr ch nt =
          f lter(T et.Prof leGeoEnr ch ntF eld. d, t et.prof leGeoEnr ch nt),
        conversat onMuted = f lter(T et.Conversat onMutedF eld. d, t et.conversat onMuted),
        takedownReasons = f lter(T et.TakedownReasonsF eld. d, t et.takedownReasons),
        selfThread nfo = f lter(T et.SelfThread nfoF eld. d, t et.selfThread nfo),
        // add  onal f elds
         d aTags = f lter(T et. d aTagsF eld. d, t et. d aTags),
        sc dul ng nfo = f lter(T et.Sc dul ng nfoF eld. d, t et.sc dul ng nfo),
        b nd ngValues = f lter(T et.B nd ngValuesF eld. d, t et.b nd ngValues),
        replyAddresses = None, // unused
        obsoleteTw terSuggest nfo = None, // unused
        esc rb rdEnt yAnnotat ons =
          f lter(T et.Esc rb rdEnt yAnnotat onsF eld. d, t et.esc rb rdEnt yAnnotat ons),
        spamLabel = f lter(T et.SpamLabelF eld. d, t et.spamLabel),
        abus veLabel = f lter(T et.Abus veLabelF eld. d, t et.abus veLabel),
        lowQual yLabel = f lter(T et.LowQual yLabelF eld. d, t et.lowQual yLabel),
        nsfwH ghPrec s onLabel =
          f lter(T et.NsfwH ghPrec s onLabelF eld. d, t et.nsfwH ghPrec s onLabel),
        nsfwH ghRecallLabel = f lter(T et.NsfwH ghRecallLabelF eld. d, t et.nsfwH ghRecallLabel),
        abus veH ghRecallLabel =
          f lter(T et.Abus veH ghRecallLabelF eld. d, t et.abus veH ghRecallLabel),
        lowQual yH ghRecallLabel =
          f lter(T et.LowQual yH ghRecallLabelF eld. d, t et.lowQual yH ghRecallLabel),
        personaNonGrataLabel =
          f lter(T et.PersonaNonGrataLabelF eld. d, t et.personaNonGrataLabel),
        recom ndat onsLowQual yLabel = f lter(
          T et.Recom ndat onsLowQual yLabelF eld. d,
          t et.recom ndat onsLowQual yLabel
        ),
        exper  ntat onLabel =
          f lter(T et.Exper  ntat onLabelF eld. d, t et.exper  ntat onLabel),
        t etLocat on nfo = f lter(T et.T etLocat on nfoF eld. d, t et.t etLocat on nfo),
        cardReference = f lter(T et.CardReferenceF eld. d, t et.cardReference),
        supple ntalLanguage =
          f lter(T et.Supple ntalLanguageF eld. d, t et.supple ntalLanguage),
        selfPermal nk = f lter(T et.SelfPermal nkF eld. d, t et.selfPermal nk),
        extendedT et tadata =
          f lter(T et.ExtendedT et tadataF eld. d, t et.extendedT et tadata),
        commun  es = f lter(T et.Commun  esF eld. d, t et.commun  es),
        v s bleTextRange = f lter(T et.V s bleTextRangeF eld. d, t et.v s bleTextRange),
        spamH ghRecallLabel = f lter(T et.SpamH ghRecallLabelF eld. d, t et.spamH ghRecallLabel),
        dupl cateContentLabel =
          f lter(T et.Dupl cateContentLabelF eld. d, t et.dupl cateContentLabel),
        l veLowQual yLabel = f lter(T et.L veLowQual yLabelF eld. d, t et.l veLowQual yLabel),
        nsfaH ghRecallLabel = f lter(T et.NsfaH ghRecallLabelF eld. d, t et.nsfaH ghRecallLabel),
        pdnaLabel = f lter(T et.PdnaLabelF eld. d, t et.pdnaLabel),
        searchBlackl stLabel =
          f lter(T et.SearchBlackl stLabelF eld. d, t et.searchBlackl stLabel),
        lowQual y nt onLabel =
          f lter(T et.LowQual y nt onLabelF eld. d, t et.lowQual y nt onLabel),
        bystanderAbus veLabel =
          f lter(T et.BystanderAbus veLabelF eld. d, t et.bystanderAbus veLabel),
        automat onH ghRecallLabel =
          f lter(T et.Automat onH ghRecallLabelF eld. d, t et.automat onH ghRecallLabel),
        goreAndV olenceLabel =
          f lter(T et.GoreAndV olenceLabelF eld. d, t et.goreAndV olenceLabel),
        untrustedUrlLabel = f lter(T et.UntrustedUrlLabelF eld. d, t et.untrustedUrlLabel),
        goreAndV olenceH ghRecallLabel = f lter(
          T et.GoreAndV olenceH ghRecallLabelF eld. d,
          t et.goreAndV olenceH ghRecallLabel
        ),
        nsfwV deoLabel = f lter(T et.NsfwV deoLabelF eld. d, t et.nsfwV deoLabel),
        nsfwNearPerfectLabel =
          f lter(T et.NsfwNearPerfectLabelF eld. d, t et.nsfwNearPerfectLabel),
        automat onLabel = f lter(T et.Automat onLabelF eld. d, t et.automat onLabel),
        nsfwCard mageLabel = f lter(T et.NsfwCard mageLabelF eld. d, t et.nsfwCard mageLabel),
        dupl cate nt onLabel =
          f lter(T et.Dupl cate nt onLabelF eld. d, t et.dupl cate nt onLabel),
        bounceLabel = f lter(T et.BounceLabelF eld. d, t et.bounceLabel),
        selfThread tadata = f lter(T et.SelfThread tadataF eld. d, t et.selfThread tadata),
        composerS ce = f lter(T et.ComposerS ceF eld. d, t et.composerS ce),
        ed Control = f lter(T et.Ed ControlF eld. d, t et.ed Control),
        developerBu ltCard d = f lter(
          T et.DeveloperBu ltCard dF eld. d,
          t et.developerBu ltCard d
        ),
        creat veEnt yEnr ch ntsForT et = f lter(
          T et.Creat veEnt yEnr ch ntsForT etF eld. d,
          t et.creat veEnt yEnr ch ntsForT et
        ),
        prev ousCounts = f lter(T et.Prev ousCountsF eld. d, t et.prev ousCounts),
         d aRefs = f lter(T et. d aRefsF eld. d, t et. d aRefs),
         sCreat vesConta nerBackendT et = f lter(
          T et. sCreat vesConta nerBackendT etF eld. d,
          t et. sCreat vesConta nerBackendT et),
        ed Perspect ve = f lter(T et.Ed Perspect veF eld. d, t et.ed Perspect ve),
        noteT et = f lter(T et.NoteT etF eld. d, t et.noteT et),

        // t etyp e- nternal  tadata
        d rectedAtUser tadata =
          f lter(T et.D rectedAtUser tadataF eld. d, t et.d rectedAtUser tadata),
        t etyp eOnlyTakedownReasons =
          f lter(T et.T etyp eOnlyTakedownReasonsF eld. d, t et.t etyp eOnlyTakedownReasons),
         d aKeys = f lter(T et. d aKeysF eld. d, t et. d aKeys),
        t etyp eOnlyTakedownCountryCodes = f lter(
          T et.T etyp eOnlyTakedownCountryCodesF eld. d,
          t et.t etyp eOnlyTakedownCountryCodes
        ),
        underly ngCreat vesConta ner d = f lter(
          T et.Underly ngCreat vesConta ner dF eld. d,
          t et.underly ngCreat vesConta ner d),
        un nt onData = f lter(T et.Un nt onDataF eld. d, t et.un nt onData),
        block ngUn nt ons = f lter(T et.Block ngUn nt onsF eld. d, t et.block ngUn nt ons),
        sett ngsUn nt ons = f lter(T et.Sett ngsUn nt onsF eld. d, t et.sett ngsUn nt ons)
      )
    }

    def scrub(counts: StatusCounts): StatusCounts = {
      @ nl ne
      def f lter[A](f eld d: F eld d, value: Opt on[A]): Opt on[A] =
         f ( .countsF elds.conta ns(f eld d)) value else None

      StatusCounts(
        replyCount = f lter(StatusCounts.ReplyCountF eld. d, counts.replyCount),
        favor eCount = f lter(StatusCounts.Favor eCountF eld. d, counts.favor eCount),
        ret etCount = f lter(StatusCounts.Ret etCountF eld. d, counts.ret etCount),
        quoteCount = f lter(StatusCounts.QuoteCountF eld. d, counts.quoteCount),
        bookmarkCount = f lter(StatusCounts.BookmarkCountF eld. d, counts.bookmarkCount)
      )
    }

    def scrub( d a:  d aEnt y):  d aEnt y = {
      @ nl ne
      def f lter[A](f eld d: F eld d, value: Opt on[A]): Opt on[A] =
         f ( . d aF elds.conta ns(f eld d)) value else None

       d a.copy(
        add  onal tadata =
          f lter( d aEnt y.Add  onal tadataF eld. d,  d a.add  onal tadata)
      )
    }
  }
}
