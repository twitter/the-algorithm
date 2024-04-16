package com.tw ter.t etyp e
package conf g

 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.servo.cac .Cac d
 mport com.tw ter.servo.cac .Lock ngCac 
 mport com.tw ter.servo.ut l.Except onCategor zer
 mport com.tw ter.servo.ut l.Except onCounter
 mport com.tw ter.servo.ut l.FutureEffect
 mport com.tw ter.servo.ut l.Scr be
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.t etyp e.core.F lteredState
 mport com.tw ter.t etyp e.core.T etData
 mport com.tw ter.t etyp e.core.ValueState
 mport com.tw ter.t etyp e.hydrator._
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.serverut l.{Except onCounter => TpExcept onCounter}
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.cl ent_ d.Cl ent d lper

tra  T etHydrators {

  /**
   * Hydrator that has all t  T et hydrators (ent re "p pel ne") conf gured
   * and w red up.
   * T  hydrator  s used both on t  read and wr e path and  s
   * custom zed by d fferent T etQuery.Opt ons.
   * Mod f cat ons are not automat cally wr ten back to cac .
   * `cac Changes` must be used for that.
   */
  def hydrator: T etDataValueHydrator

  /**
   * T  `Effect` to use to wr e mod f ed t ets back to cac .
   */
  def cac ChangesEffect: Effect[ValueState[T etData]]
}

object T etHydrators {

  /**
   * Creates all t  hydrators and calls T etHydrat on to w re t m up.
   */
  def apply(
    stats: StatsRece ver,
    dec derGates: T etyp eDec derGates,
    repos: Log calRepos or es,
    t etDataCac : Lock ngCac [T et d, Cac d[T etData]],
    has d a: T et => Boolean,
    featureSw c sW houtExper  nts: FeatureSw c s,
    cl ent d lper: Cl ent d lper
  ): T etHydrators = {
     mport repos._

    val repa rStats = stats.scope("repa rs")
    val hydratorStats = stats.scope("hydrators")

    def scoped[A](stats: StatsRece ver, na : Str ng)(f: StatsRece ver => A): A = {
      val scopedStats = stats.scope(na )
      f(scopedStats)
    }

    val  sFa lureExcept on: Throwable => Boolean = {
      case _: F lteredState => false
      case NotFound => false
      case _ => true
    }

    def hydratorExcept onCategor zer(fa lureScope: Str ng) =
      Except onCategor zer.const("f ltered").only f(_. s nstanceOf[F lteredState]) ++
        Except onCategor zer.const("not_found").only f(_ == NotFound) ++
        TpExcept onCounter.defaultCategor zer(fa lureScope).only f( sFa lureExcept on)

    val hydratorExcept onCounter: (StatsRece ver, Str ng) => Except onCounter =
      (stats, scope) => TpExcept onCounter(stats, hydratorExcept onCategor zer(scope))

    val t etHydrator =
      T etHydrat on(
        hydratorStats = hydratorStats,
        hydrateFeatureSw chResults =
          FeatureSw chResultsHydrator(featureSw c sW houtExper  nts, cl ent d lper),
        hydrate nt ons =  nt onEnt  esHydrator
          .once( nt onEnt yHydrator(user dent yRepo))
          .observe(hydratorStats.scope(" nt ons"), hydratorExcept onCounter),
        hydrateLanguage = LanguageHydrator(languageRepo)
          .observe(hydratorStats.scope("language"), hydratorExcept onCounter),
        hydrateUrls = scoped(hydratorStats, "url") { stats =>
          UrlEnt  esHydrator
            .once(UrlEnt yHydrator(urlRepo, stats))
            .observe(stats, hydratorExcept onCounter)
        },
        hydrateQuotedT etRef = QuotedT etRefHydrator
          .once(
            QuotedT etRefHydrator(t etRepo)
          )
          .observe(hydratorStats.scope("quoted_t et_ref"), hydratorExcept onCounter),
        hydrateQuotedT etRefUrls = QuotedT etRefUrlsHydrator(user dent yRepo)
          .observe(hydratorStats.scope("quoted_t et_ref_urls"), hydratorExcept onCounter),
        hydrate d aCac able =  d aEnt  esHydrator.Cac able
          .once(
             d aEnt yHydrator.Cac able(
              hydrate d aUrls =  d aUrlF eldsHydrator()
                .observe(hydratorStats.scope(" d a_urls"), hydratorExcept onCounter),
              hydrate d a sProtected =  d a sProtectedHydrator(userProtect onRepo)
                .observe(hydratorStats.scope(" d a_ s_protected"), hydratorExcept onCounter)
            )
          )
          .observe(hydratorStats.scope(" d a_cac able"), hydratorExcept onCounter)
          . fEnabled(dec derGates.hydrate d a),
        hydrateReplyScreenNa  = ReplyScreenNa Hydrator
          .once(ReplyScreenNa Hydrator(user dent yRepo))
          .observe(hydratorStats.scope(" n_reply_to_screen_na "), hydratorExcept onCounter),
        hydrateConvo d = Conversat on dHydrator(conversat on dRepo)
          .observe(hydratorStats.scope("conversat on_ d"), hydratorExcept onCounter),
        hydratePerspect ve = // Don't cac  w h t  t et because   depends on t  request
          Perspect veHydrator(
            repo = perspect veRepo,
            shouldHydrateBookmarksPerspect ve = dec derGates.hydrateBookmarksPerspect ve,
            stats = hydratorStats.scope("perspect ve_by_safety_label")
          ).observe(hydratorStats.scope("perspect ve"), hydratorExcept onCounter)
            . fEnabled(dec derGates.hydratePerspect ves),
        hydrateEd Perspect ve = Ed Perspect veHydrator(
          repo = perspect veRepo,
          t  l nesGate = dec derGates.hydratePerspect vesEd sForT  l nes,
          t etDeta lsGate = dec derGates.hydratePerspect vesEd sForT etDeta l,
          ot rSafetyLevelsGate = dec derGates.hydratePerspect vesEd sForOt rSafetyLevels,
          bookmarksGate = dec derGates.hydrateBookmarksPerspect ve,
          stats = hydratorStats
        ).observe(hydratorStats.scope("ed _perspect ve"), hydratorExcept onCounter),
        hydrateConversat onMuted = // Don't cac  because   depends on t  request.   f
          // poss ble, t  hydrator should be  n t  sa  stage as
          // Perspect veHydrator, so that t  calls can be batc d
          // toget r.
          Conversat onMutedHydrator(conversat onMutedRepo)
            .observe(hydratorStats.scope("conversat on_muted"), hydratorExcept onCounter)
            . fEnabled(dec derGates.hydrateConversat onMuted),
        hydrateContr butor = Contr butorHydrator
          .once(Contr butorHydrator(user dent yRepo))
          .observe(hydratorStats.scope("contr butors"), hydratorExcept onCounter),
        hydrateTakedowns = TakedownHydrator(takedownRepo)
          .observe(hydratorStats.scope("takedowns"), hydratorExcept onCounter),
        hydrateD rectedAt = scoped(hydratorStats, "d rected_at") { stats =>
          D rectedAtHydrator
            .once(D rectedAtHydrator(user dent yRepo, stats))
            .observe(stats, hydratorExcept onCounter)
        },
        hydrateGeoScrub = GeoScrubHydrator(
          geoScrubT  stampRepo,
          Scr be("test_t etyp e_read_t  _geo_scrubs")
            .contramap[T et d](_.toStr ng)
        ).observe(hydratorStats.scope("geo_scrub"), hydratorExcept onCounter),
        hydrateCac ableRepa rs = ValueHydrator
          .fromMutat on[T et, T etQuery.Opt ons](
            Repa rMutat on(
              repa rStats.scope("on_read"),
              "created_at" ->
                new CreatedAtRepa rer(Scr be("test_t etyp e_bad_created_at")),
              "ret et_ d a" -> Ret et d aRepa rer,
              "parent_status_ d" -> Ret etParentStatus dRepa rer.t etMutat on,
              "v s ble_text_range" -> Negat veV s bleTextRangeRepa rer.t etMutat on
            )
          )
          .lensed(T etData.Lenses.t et)
          .only f((td, opts) => opts.cause.read ng(td.t et. d)),
        hydrate d aUncac able =  d aEnt yHydrator
          .Uncac able(
            hydrate d aKey =  d aKeyHydrator()
              .observe(hydratorStats.scope(" d a_key"), hydratorExcept onCounter),
            hydrate d a nfo = scoped(hydratorStats, " d a_ nfo") { stats =>
               d a nfoHydrator( d a tadataRepo, stats)
                .observe(stats, hydratorExcept onCounter)
            }
          )
          .observe(hydratorStats.scope(" d a_uncac able"), hydratorExcept onCounter)
          .l ftSeq
          . fEnabled(dec derGates.hydrate d a),
        hydratePostCac Repa rs =
          // clean-up part ally hydrated ent  es before any of t  hydrators that look at
          // url and  d a ent  es run, so that t y never see bad ent  es.
          ValueHydrator.fromMutat on[T etData, T etQuery.Opt ons](
            Repa rMutat on(
              repa rStats.scope("on_read"),
              "part al_ent y_cleanup" -> Part alEnt yCleaner(repa rStats),
              "str p_not_d splay_coords" -> Str pH ddenGeoCoord nates
            ).lensed(T etData.Lenses.t et)
          ),
        hydrateT etLegacyFormat = scoped(hydratorStats, "t et_legacy_formatter") { stats =>
          T etLegacyFormatter(stats)
            .observe(stats, hydratorExcept onCounter)
            .only f((td, opts) => opts.cause.read ng(td.t et. d))
        },
        hydrateQuoteT etV s b l y = QuoteT etV s b l yHydrator(quotedT etV s b l yRepo)
          .observe(hydratorStats.scope("quote_t et_v s b l y"), hydratorExcept onCounter),
        hydrateQuotedT et = QuotedT etHydrator(t etResultRepo)
          .observe(hydratorStats.scope("quoted_t et"), hydratorExcept onCounter),
        hydratePasted d a =
          // Don't cac  w h t  t et because   want to automat cally drop t   d a  f
          // t  referenced t et  s deleted or beco s non-publ c.
          Pasted d aHydrator(pasted d aRepo)
            .observe(hydratorStats.scope("pasted_ d a"))
            . fEnabled(dec derGates.hydratePasted d a),
        hydrate d aRefs =  d aRefsHydrator(
          opt onalT etRepo,
          dec derGates. d aRefsHydrator ncludePasted d a
        ).observe(hydratorStats.scope(" d a_refs"))
          . fEnabled(dec derGates.hydrate d aRefs),
        hydrate d aTags = // depends on Add  onalF eldsHydrator
           d aTagsHydrator(userV ewRepo)
            .observe(hydratorStats.scope(" d a_tags"), hydratorExcept onCounter)
            . fEnabled(dec derGates.hydrate d aTags),
        hydrateClass cCards = CardHydrator(cardRepo)
          .observe(hydratorStats.scope("cards"), hydratorExcept onCounter),
        hydrateCard2 = Card2Hydrator(card2Repo)
          .observe(hydratorStats.scope("card2")),
        hydrateContr butorV s b l y =
          // F lter out contr butors f eld for all but t  user who owns t  t et
          Contr butorV s b l yF lter()
            .observe(hydratorStats.scope("contr butor_v s b l y"), hydratorExcept onCounter),
        hydrateHas d a =
          // Sets has d a. Co s after Pasted d aHydrator  n order to  nclude pasted
          // p cs as  ll as ot r  d a & urls.
          Has d aHydrator(has d a)
            .observe(hydratorStats.scope("has_ d a"), hydratorExcept onCounter)
            . fEnabled(dec derGates.hydrateHas d a),
        hydrateT etCounts = // Don't cac  counts w h t  t et because   has  s own cac  w h
          // a d fferent TTL
          T etCountsHydrator(t etCountsRepo, dec derGates.hydrateBookmarksCount)
            .observe(hydratorStats.scope("t et_counts"), hydratorExcept onCounter)
            . fEnabled(dec derGates.hydrateCounts),
        hydratePrev ousT etCounts = // prev ous counts are not cac d
          scoped(hydratorStats, "prev ous_counts") { stats =>
            Prev ousT etCountsHydrator(t etCountsRepo, dec derGates.hydrateBookmarksCount)
              .observe(stats, hydratorExcept onCounter)
              . fEnabled(dec derGates.hydratePrev ousCounts)
          },
        hydratePlace =
          // Don't cac  w h t  t et because Place has  s own t etyp e cac  keyspace
          // w h a d fferent TTL, and  's more eff c ent to store separately.
          // See com.tw ter.t etyp e.repos ory.PlaceKey
          PlaceHydrator(placeRepo)
            .observe(hydratorStats.scope("place"), hydratorExcept onCounter)
            . fEnabled(dec derGates.hydratePlaces),
        hydrateDev ceS ce = // Don't cac  w h t  t et because   has  s own cac ,
          // and  's more eff c ent to cac    separately
          Dev ceS ceHydrator(dev ceS ceRepo)
            .observe(hydratorStats.scope("dev ce_s ce"), hydratorExcept onCounter)
            . fEnabled(dec derGates.hydrateDev ceS ces),
        hydrateProf leGeo =
          // Don't cac  gn p prof le geo as read request volu   s expected to be low
          Prof leGeoHydrator(prof leGeoRepo)
            .observe(hydratorStats.scope("prof le_geo"), hydratorExcept onCounter)
            . fEnabled(dec derGates.hydrateGn pProf leGeoEnr ch nt),
        hydrateS ceT et = scoped(hydratorStats, "s ce_t et") { stats =>
          S ceT etHydrator(
            t etResultRepo,
            stats,
            FutureEffect
              . nParallel(
                Scr be(Detac dRet et, "t etyp e_detac d_ret ets"),
                Scr be(Detac dRet et, "test_t etyp e_detac d_ret ets"),
              )
          ).observe(stats, hydratorExcept onCounter)
        },
        hydrate M1837State =  M1837F lterHydrator()
          .observe(hydratorStats.scope(" m1837_f lter"), hydratorExcept onCounter)
          .only f { (_, ctx) =>
            ctx.opts.forExternalConsumpt on && ctx.opts.cause.read ng(ctx.t et d)
          },
        hydrate M2884State = scoped(hydratorStats, " m2884_f lter") { stats =>
           M2884F lterHydrator(stats)
            .observe(stats, hydratorExcept onCounter)
            .only f { (_, ctx) =>
              ctx.opts.forExternalConsumpt on && ctx.opts.cause.read ng(ctx.t et d)
            }
        },
        hydrate M3433State = scoped(hydratorStats, " m3433_f lter") { stats =>
           M3433F lterHydrator(stats)
            .observe(stats, hydratorExcept onCounter)
            .only f { (_, ctx) =>
              ctx.opts.forExternalConsumpt on && ctx.opts.cause.read ng(ctx.t et d)
            }
        },
        hydrateT etAuthorV s b l y = T etAuthorV s b l yHydrator(userV s b l yRepo)
          .observe(hydratorStats.scope("t et_author_v s b l y"), hydratorExcept onCounter)
          .only f((_, ctx) => ctx.opts.cause.read ng(ctx.t et d)),
        hydrateReportedT etV s b l y = ReportedT etF lter()
          .observe(hydratorStats.scope("reported_t et_f lter"), hydratorExcept onCounter),
        scrubSuperfluousUrlEnt  es = ValueHydrator
          .fromMutat on[T et, T etQuery.Opt ons](SuperfluousUrlEnt yScrubber.mutat on)
          .lensed(T etData.Lenses.t et),
        copyFromS ceT et = CopyFromS ceT et.hydrator
          .observe(hydratorStats.scope("copy_from_s ce_t et"), hydratorExcept onCounter),
        hydrateT etV s b l y = scoped(hydratorStats, "t et_v s b l y") { stats =>
          T etV s b l yHydrator(
            t etV s b l yRepo,
            dec derGates.fa lClosed nVF,
            stats
          ).observe(stats, hydratorExcept onCounter)
        },
        hydrateEsc rb rdAnnotat ons = Esc rb rdAnnotat onHydrator(esc rb rdAnnotat onRepo)
          .observe(hydratorStats.scope("esc rb rd_annotat ons"), hydratorExcept onCounter)
          . fEnabled(dec derGates.hydrateEsc rb rdAnnotat ons),
        hydrateScrubEngage nts = ScrubEngage ntHydrator()
          .observe(hydratorStats.scope("scrub_engage nts"), hydratorExcept onCounter)
          . fEnabled(dec derGates.hydrateScrubEngage nts),
        hydrateConversat onControl = scoped(hydratorStats, "t et_conversat on_control") { stats =>
          Conversat onControlHydrator(
            conversat onControlRepo,
            dec derGates.d sable nv eV a nt on,
            stats
          ).observe(stats, hydratorExcept onCounter)
        },
        hydrateEd Control = scoped(hydratorStats, "t et_ed _control") { stats =>
          Ed ControlHydrator(
            t etRepo,
            dec derGates.setEd T  W ndowToS xtyM nutes,
            stats
          ).observe(stats, hydratorExcept onCounter)
        },
        hydrateUn nt onData = Un nt onDataHydrator(),
        hydrateNoteT etSuff x = NoteT etSuff xHydrator().observe(stats, hydratorExcept onCounter)
      )

    new T etHydrators {
      val hydrator: T etDataValueHydrator =
        t etHydrator.only f { (t etData, opts) =>
          // W n t  caller requests fetchStoredT ets and T ets are fetc d from Manhattan
          //  rrespect ve of state, t  stored data for so  T ets may be  ncomplete.
          //   sk p t  hydrat on of those T ets.
          !opts.fetchStoredT ets ||
          t etData.storedT etResult.ex sts(_.canHydrate)
        }

      val cac ChangesEffect: Effect[ValueState[T etData]] =
        T etHydrat on.cac Changes(
          t etDataCac ,
          hydratorStats.scope("t et_cach ng")
        )
    }
  }
}
