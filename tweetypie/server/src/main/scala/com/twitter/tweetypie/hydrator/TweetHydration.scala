package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.expandodo.thr ftscala.Card
 mport com.tw ter.expandodo.thr ftscala.Card2
 mport com.tw ter.servo.cac .Cac d
 mport com.tw ter.servo.cac .Cac dValueStatus
 mport com.tw ter.servo.cac .Lock ngCac 
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e. d a.thr ftscala. d aRef
 mport com.tw ter.t etyp e.repos ory.Pasted d a
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.repos ory.T etRepoCac P cker
 mport com.tw ter.t etyp e.repos ory.T etResultRepos ory
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.ut l.Takedowns
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw

object T etHydrat on {

  /**
   * W res up a set of hydrators that  nclude those whose results are cac d on t  t et,
   * and so  whose results are not cac d but depend upon t  results of t  for r.
   */
  def apply(
    hydratorStats: StatsRece ver,
    hydrateFeatureSw chResults: T etDataValueHydrator,
    hydrate nt ons:  nt onEnt  esHydrator.Type,
    hydrateLanguage: LanguageHydrator.Type,
    hydrateUrls: UrlEnt  esHydrator.Type,
    hydrateQuotedT etRef: QuotedT etRefHydrator.Type,
    hydrateQuotedT etRefUrls: QuotedT etRefUrlsHydrator.Type,
    hydrate d aCac able:  d aEnt  esHydrator.Cac able.Type,
    hydrateReplyScreenNa : ReplyScreenNa Hydrator.Type,
    hydrateConvo d: Conversat on dHydrator.Type,
    hydratePerspect ve: Perspect veHydrator.Type,
    hydrateEd Perspect ve: Ed Perspect veHydrator.Type,
    hydrateConversat onMuted: Conversat onMutedHydrator.Type,
    hydrateContr butor: Contr butorHydrator.Type,
    hydrateTakedowns: TakedownHydrator.Type,
    hydrateD rectedAt: D rectedAtHydrator.Type,
    hydrateGeoScrub: GeoScrubHydrator.Type,
    hydrateCac ableRepa rs: T etDataValueHydrator,
    hydrate d aUncac able:  d aEnt  esHydrator.Uncac able.Type,
    hydratePostCac Repa rs: T etDataValueHydrator,
    hydrateT etLegacyFormat: T etDataValueHydrator,
    hydrateQuoteT etV s b l y: QuoteT etV s b l yHydrator.Type,
    hydrateQuotedT et: QuotedT etHydrator.Type,
    hydratePasted d a: Pasted d aHydrator.Type,
    hydrate d aRefs:  d aRefsHydrator.Type,
    hydrate d aTags:  d aTagsHydrator.Type,
    hydrateClass cCards: CardHydrator.Type,
    hydrateCard2: Card2Hydrator.Type,
    hydrateContr butorV s b l y: Contr butorV s b l yF lter.Type,
    hydrateHas d a: Has d aHydrator.Type,
    hydrateT etCounts: T etCountsHydrator.Type,
    hydratePrev ousT etCounts: Prev ousT etCountsHydrator.Type,
    hydratePlace: PlaceHydrator.Type,
    hydrateDev ceS ce: Dev ceS ceHydrator.Type,
    hydrateProf leGeo: Prof leGeoHydrator.Type,
    hydrateS ceT et: S ceT etHydrator.Type,
    hydrate M1837State:  M1837F lterHydrator.Type,
    hydrate M2884State:  M2884F lterHydrator.Type,
    hydrate M3433State:  M3433F lterHydrator.Type,
    hydrateT etAuthorV s b l y: T etAuthorV s b l yHydrator.Type,
    hydrateReportedT etV s b l y: ReportedT etF lter.Type,
    scrubSuperfluousUrlEnt  es: T etDataValueHydrator,
    copyFromS ceT et: T etDataValueHydrator,
    hydrateT etV s b l y: T etV s b l yHydrator.Type,
    hydrateEsc rb rdAnnotat ons: Esc rb rdAnnotat onHydrator.Type,
    hydrateScrubEngage nts: ScrubEngage ntHydrator.Type,
    hydrateConversat onControl: Conversat onControlHydrator.Type,
    hydrateEd Control: Ed ControlHydrator.Type,
    hydrateUn nt onData: Un nt onDataHydrator.Type,
    hydrateNoteT etSuff x: T etDataValueHydrator
  ): T etDataValueHydrator = {
    val scrubCac dT et: T etDataValueHydrator =
      ValueHydrator
        .fromMutat on[T et, T etQuery.Opt ons](
          ScrubUncac able.t etMutat on.countMutat ons(hydratorStats.counter("scrub_cac d_t et"))
        )
        .lensed(T etData.Lenses.t et)
        .only f((td, opts) => opts.cause.read ng(td.t et. d))

    //   perform  ndependent hydrat ons of  nd v dual b s of
    // data and pack t  results  nto tuples  nstead of updat ng
    // t  t et for each one  n order to avo d mak ng lots of
    // cop es of t  t et.

    val hydratePr maryCac ableF elds: T etDataValueHydrator =
      ValueHydrator[T etData, T etQuery.Opt ons] { (td, opts) =>
        val ctx = T etCtx.from(td, opts)
        val t et = td.t et

        val urls d aQuoteT et: St ch[
          ValueState[(Seq[UrlEnt y], Seq[ d aEnt y], Opt on[QuotedT et])]
        ] =
          for {
            urls <- hydrateUrls(getUrls(t et), ctx)
            ( d a, quotedT et) <- St ch.jo n(
              hydrate d aCac able(
                get d a(t et),
                 d aEnt yHydrator.Cac able.Ctx(urls.value, ctx)
              ),
              for {
                qtRef <- hydrateQuotedT etRef(
                  t et.quotedT et,
                  QuotedT etRefHydrator.Ctx(urls.value, ctx)
                )
                qtRefW hUrls <- hydrateQuotedT etRefUrls(qtRef.value, ctx)
              } y eld {
                ValueState(qtRefW hUrls.value, qtRef.state ++ qtRefW hUrls.state)
              }
            )
          } y eld {
            ValueState.jo n(urls,  d a, quotedT et)
          }

        val conversat on d: St ch[ValueState[Opt on[Conversat on d]]] =
          hydrateConvo d(getConversat on d(t et), ctx)

        val  nt ons: St ch[ValueState[Seq[ nt onEnt y]]] =
          hydrate nt ons(get nt ons(t et), ctx)

        val replyScreenNa : St ch[ValueState[Opt on[Reply]]] =
          hydrateReplyScreenNa (getReply(t et), ctx)

        val d rectedAt: St ch[ValueState[Opt on[D rectedAtUser]]] =
          hydrateD rectedAt(
            getD rectedAtUser(t et),
            D rectedAtHydrator.Ctx(
               nt ons = get nt ons(t et),
               tadata = t et.d rectedAtUser tadata,
              underly ngT etCtx = ctx
            )
          )

        val language: St ch[ValueState[Opt on[Language]]] =
          hydrateLanguage(t et.language, ctx)

        val contr butor: St ch[ValueState[Opt on[Contr butor]]] =
          hydrateContr butor(t et.contr butor, ctx)

        val geoScrub: St ch[ValueState[(Opt on[GeoCoord nates], Opt on[Place d])]] =
          hydrateGeoScrub(
            (T etLenses.geoCoord nates(t et), T etLenses.place d(t et)),
            ctx
          )

        St ch
          .jo nMap(
            urls d aQuoteT et,
            conversat on d,
             nt ons,
            replyScreenNa ,
            d rectedAt,
            language,
            contr butor,
            geoScrub
          )(ValueState.jo n(_, _, _, _, _, _, _, _))
          .map { values =>
             f (values.state. sEmpty) {
              ValueState.unmod f ed(td)
            } else {
              values.map {
                case (
                      (urls,  d a, quotedT et),
                      conversat on d,
                       nt ons,
                      reply,
                      d rectedAt,
                      language,
                      contr butor,
                      coreGeo
                    ) =>
                  val (coord nates, place d) = coreGeo
                  td.copy(
                    t et = t et.copy(
                      coreData = t et.coreData.map(
                        _.copy(
                          reply = reply,
                          conversat on d = conversat on d,
                          d rectedAtUser = d rectedAt,
                          coord nates = coord nates,
                          place d = place d
                        )
                      ),
                      urls = So (urls),
                       d a = So ( d a),
                       nt ons = So ( nt ons),
                      language = language,
                      quotedT et = quotedT et,
                      contr butor = contr butor
                    )
                  )
              }
            }
          }
      }

    val assertNotScrubbed: T etDataValueHydrator =
      ValueHydrator.fromMutat on[T etData, T etQuery.Opt ons](
        ScrubUncac able
          .assertNotScrubbed(
            "output of t  cac able t et hydrator should not requ re scrubb ng"
          )
          .lensed(T etData.Lenses.t et)
      )

    val hydrateDependentUncac ableF elds: T etDataValueHydrator =
      ValueHydrator[T etData, T etQuery.Opt ons] { (td, opts) =>
        val ctx = T etCtx.from(td, opts)
        val t et = td.t et

        val quotedT etResult: St ch[ValueState[Opt on[QuotedT etResult]]] =
          for {
            qtF lterState <- hydrateQuoteT etV s b l y(None, ctx)
            quotedT et <- hydrateQuotedT et(
              td.quotedT etResult,
              QuotedT etHydrator.Ctx(qtF lterState.value, ctx)
            )
          } y eld {
            ValueState.jo n(qtF lterState, quotedT et).map(_._2)
          }

        val pasted d a: St ch[ValueState[Pasted d a]] =
          hydratePasted d a(
            Pasted d aHydrator.getPasted d a(t et),
            Pasted d aHydrator.Ctx(getUrls(t et), ctx)
          )

        val  d aTags: St ch[ValueState[Opt on[T et d aTags]]] =
          hydrate d aTags(t et. d aTags, ctx)

        val class cCards: St ch[ValueState[Opt on[Seq[Card]]]] =
          hydrateClass cCards(
            t et.cards,
            CardHydrator.Ctx(getUrls(t et), get d a(t et), ctx)
          )

        val card2: St ch[ValueState[Opt on[Card2]]] =
          hydrateCard2(
            t et.card2,
            Card2Hydrator.Ctx(
              getUrls(t et),
              get d a(t et),
              getCardReference(t et),
              ctx,
              td.featureSw chResults
            )
          )

        val contr butorV s b l y: St ch[ValueState[Opt on[Contr butor]]] =
          hydrateContr butorV s b l y(t et.contr butor, ctx)

        val takedowns: St ch[ValueState[Opt on[Takedowns]]] =
          hydrateTakedowns(
            None, // None because uncac able hydrator doesn't depend on prev ous value
            TakedownHydrator.Ctx(Takedowns.fromT et(t et), ctx)
          )

        val conversat onControl: St ch[ValueState[Opt on[Conversat onControl]]] =
          hydrateConversat onControl(
            t et.conversat onControl,
            Conversat onControlHydrator.Ctx(getConversat on d(t et), ctx)
          )

        // Prev ousT etCounts and Perspect ve hydrat on depends on t et.ed Control.ed _control_ n  al
        // hav ng been hydrated  n Ed ControlHydrator; thus   are cha n ng t m toget r.
        val ed ControlW hDependenc es: St ch[
          ValueState[
            (
              Opt on[Ed Control],
              Opt on[StatusPerspect ve],
              Opt on[StatusCounts],
              Opt on[T etPerspect ve]
            )
          ]
        ] =
          for {
            (ed , perspect ve) <- St ch.jo n(
              hydrateEd Control(t et.ed Control, ctx),
              hydratePerspect ve(
                t et.perspect ve,
                Perspect veHydrator.Ctx(td.featureSw chResults, ctx))
            )
            (counts, ed Perspect ve) <- St ch.jo n(
              hydratePrev ousT etCounts(
                t et.prev ousCounts,
                Prev ousT etCountsHydrator.Ctx(ed .value, td.featureSw chResults, ctx)),
              hydrateEd Perspect ve(
                t et.ed Perspect ve,
                Ed Perspect veHydrator
                  .Ctx(perspect ve.value, ed .value, td.featureSw chResults, ctx))
            )
          } y eld {
            ValueState.jo n(ed , perspect ve, counts, ed Perspect ve)
          }

        St ch
          .jo nMap(
            quotedT etResult,
            pasted d a,
             d aTags,
            class cCards,
            card2,
            contr butorV s b l y,
            takedowns,
            conversat onControl,
            ed ControlW hDependenc es
          )(ValueState.jo n(_, _, _, _, _, _, _, _, _))
          .map { values =>
             f (values.state. sEmpty) {
              ValueState.unmod f ed(td)
            } else {
              values.map {
                case (
                      quotedT etResult,
                      pasted d a,
                      owned d aTags,
                      cards,
                      card2,
                      contr butor,
                      takedowns,
                      conversat onControl,
                      (ed Control, perspect ve, prev ousCounts, ed Perspect ve)
                    ) =>
                  td.copy(
                    t et = t et.copy(
                       d a = So (pasted d a. d aEnt  es),
                       d aTags = pasted d a. rgeT et d aTags(owned d aTags),
                      cards = cards,
                      card2 = card2,
                      contr butor = contr butor,
                      takedownCountryCodes = takedowns.map(_.countryCodes.toSeq),
                      takedownReasons = takedowns.map(_.reasons.toSeq),
                      conversat onControl = conversat onControl,
                      ed Control = ed Control,
                      prev ousCounts = prev ousCounts,
                      perspect ve = perspect ve,
                      ed Perspect ve = ed Perspect ve,
                    ),
                    quotedT etResult = quotedT etResult
                  )
              }
            }
          }
      }

    val hydrate ndependentUncac ableF elds: T etDataEd Hydrator =
      Ed Hydrator[T etData, T etQuery.Opt ons] { (td, opts) =>
        val ctx = T etCtx.from(td, opts)
        val t et = td.t et

        // Group toget r t  results of hydrators that don't perform
        // f lter ng, because   don't care about t  precedence of
        // except ons from t se hydrators, because t  except ons all
        //  nd cate fa lures, and p ck ng any fa lure w ll be
        // f ne. (All of t  ot r hydrators m ght throw f lter ng
        // except ons, so   need to make sure that   g ve precedence
        // to t  r fa lures.)
        val hydratorsW houtF lter ng =
          St ch.jo nMap(
            hydrateT etCounts(t et.counts, T etCountsHydrator.Ctx(td.featureSw chResults, ctx)),
            // Note: Place  s cac d  n  mcac ,    s just not cac d on t  T et.
            hydratePlace(t et.place, ctx),
            hydrateDev ceS ce(t et.dev ceS ce, ctx),
            hydrateProf leGeo(t et.prof leGeoEnr ch nt, ctx)
          )(ValueState.jo n(_, _, _, _))

        /**
         * Mult ple hydrators throw v s b l y f lter ng except ons so spec fy an order to ach eve
         * a determ n st c hydrat on result wh le ensur ng that any ret et has a s ce t et:
         * 1. hydrateS ceT et throws S ceT etNotFound, t   s a detac d-ret et so treat
         *      t  ret et hydrat on as  f    re not found
         * 2. hydrateT etAuthorV s b l y
         * 3. hydrateS ceT et (ot r than S ceT etNotFound already handled above)
         * 4. hydrate M1837State
         * 5. hydrate M2884State
         * 6. hydrate M3433State
         * 7. hydratorsW houtF lter ng m scellaneous except ons (any v s b l y f lter ng
         *      except ons should w n over fa lure of a hydrator)
         */
        val s ceT etAndT etAuthorResult =
          St ch
            .jo nMap(
              hydrateS ceT et(td.s ceT etResult, ctx).l ftToTry,
              hydrateT etAuthorV s b l y((), ctx).l ftToTry,
              hydrate M1837State((), ctx).l ftToTry,
              hydrate M2884State((), ctx).l ftToTry,
              hydrate M3433State((), ctx).l ftToTry
            ) {
              case (Throw(t @ F lteredState.Unava lable.S ceT etNotFound(_)), _, _, _, _) =>
                Throw(t)
              case (_, Throw(t), _, _, _) => Throw(t) // T etAuthorV s b l y
              case (Throw(t), _, _, _, _) => Throw(t) // S ceT et
              case (_, _, Throw(t), _, _) => Throw(t) //  M1837State
              case (_, _, _, Throw(t), _) => Throw(t) //  M2884State
              case (_, _, _, _, Throw(t)) => Throw(t) //  M3433State
              case (
                    Return(s ceT etResultValue),
                    Return(authorV s b l yValue),
                    Return( m1837Value),
                    Return( m2884Value),
                    Return( m3433Value)
                  ) =>
                Return(
                  ValueState
                    .jo n(
                      s ceT etResultValue,
                      authorV s b l yValue,
                       m1837Value,
                       m2884Value,
                       m3433Value
                    )
                )
            }.lo rFromTry

        St chExcept onPrecedence(s ceT etAndT etAuthorResult)
          .jo nW h(hydratorsW houtF lter ng)(ValueState.jo n(_, _))
          .toSt ch
          .map { values =>
             f (values.state. sEmpty) {
              Ed State.un [T etData]
            } else {
              Ed State[T etData] { t etData =>
                val t et = t etData.t et
                values.map {
                  case (
                        (s ceT etResult, _, _, _, _),
                        (counts, place, dev ceS ce, prof leGeo)
                      ) =>
                    t etData.copy(
                      t et = t et.copy(
                        counts = counts,
                        place = place,
                        dev ceS ce = dev ceS ce,
                        prof leGeoEnr ch nt = prof leGeo
                      ),
                      s ceT etResult = s ceT etResult
                    )
                }
              }
            }
          }
      }

    val hydrateUn nt onDataToT etData: T etDataValueHydrator =
      T etHydrat on.setOnT etData(
        T etData.Lenses.t et.andT n(T etLenses.un nt onData),
        (td: T etData, opts: T etQuery.Opt ons) =>
          Un nt onDataHydrator
            .Ctx(getConversat on d(td.t et), get nt ons(td.t et), T etCtx.from(td, opts)),
        hydrateUn nt onData
      )

    val hydrateCac ableF elds: T etDataValueHydrator =
      ValueHydrator. nSequence(
        scrubCac dT et,
        hydratePr maryCac ableF elds,
        // Rel es on  nt ons be ng hydrated  n hydratePr maryCac ableF elds
        hydrateUn nt onDataToT etData,
        assertNotScrubbed,
        hydrateCac ableRepa rs
      )

    // T  conversat on muted hydrator needs t  conversat on  d,
    // wh ch co s from t  pr mary cac able f elds, and t   d a hydrator
    // needs t  cac able  d a ent  es.
    val hydrateUncac able d a: T etDataValueHydrator =
      ValueHydrator[T etData, T etQuery.Opt ons] { (td, opts) =>
        val ctx = T etCtx.from(td, opts)
        val t et = td.t et

        val  d aCtx =
           d aEnt yHydrator.Uncac able.Ctx(td.t et. d aKeys, ctx)

        val  d a: St ch[ValueState[Opt on[Seq[ d aEnt y]]]] =
          hydrate d aUncac able.l ftOpt on.apply(td.t et. d a,  d aCtx)

        val conversat onMuted: St ch[ValueState[Opt on[Boolean]]] =
          hydrateConversat onMuted(
            t et.conversat onMuted,
            Conversat onMutedHydrator.Ctx(getConversat on d(t et), ctx)
          )

        //  d aRefs need to be hydrated at t  phase because t y rely on t   d a f eld
        // on t  T et, wh ch can get unset by later hydrators.
        val  d aRefs: St ch[ValueState[Opt on[Seq[ d aRef]]]] =
          hydrate d aRefs(
            t et. d aRefs,
             d aRefsHydrator.Ctx(get d a(t et), get d aKeys(t et), getUrls(t et), ctx)
          )

        St ch
          .jo nMap(
             d a,
            conversat onMuted,
             d aRefs
          )(ValueState.jo n(_, _, _))
          .map { values =>
             f (values.state. sEmpty) {
              ValueState.unmod f ed(td)
            } else {
              val t et = td.t et
              values.map {
                case ( d a, conversat onMuted,  d aRefs) =>
                  td.copy(
                    t et = t et.copy(
                       d a =  d a,
                      conversat onMuted = conversat onMuted,
                       d aRefs =  d aRefs
                    )
                  )
              }
            }
          }
      }

    val hydrateHas d aToT etData: T etDataValueHydrator =
      T etHydrat on.setOnT etData(
        T etData.Lenses.t et.andT n(T etLenses.has d a),
        (td: T etData, opts: T etQuery.Opt ons) => td.t et,
        hydrateHas d a
      )

    val hydrateReportedT etV s b l yToT etData: T etDataValueHydrator = {
      // Create a T etDataValueHydrator that calls hydrateReportedT etV s b l y, wh ch
      // e  r throws a F lteredState.Unava lable or returns Un .
      ValueHydrator[T etData, T etQuery.Opt ons] { (td, opts) =>
        val ctx = ReportedT etF lter.Ctx(td.t et.perspect ve, T etCtx.from(td, opts))
        hydrateReportedT etV s b l y((), ctx).map { _ =>
          ValueState.unmod f ed(td)
        }
      }
    }

    val hydrateT etV s b l yToT etData: T etDataValueHydrator =
      T etHydrat on.setOnT etData(
        T etData.Lenses.suppress,
        (td: T etData, opts: T etQuery.Opt ons) =>
          T etV s b l yHydrator.Ctx(td.t et, T etCtx.from(td, opts)),
        hydrateT etV s b l y
      )

    val hydrateEsc rb rdAnnotat onsToT etAndCac dT et: T etDataValueHydrator =
      T etHydrat on.setOnT etAndCac dT et(
        T etLenses.esc rb rdEnt yAnnotat ons,
        (td: T etData, _: T etQuery.Opt ons) => td.t et,
        hydrateEsc rb rdAnnotat ons
      )

    val scrubEngage nts: T etDataValueHydrator =
      T etHydrat on.setOnT etData(
        T etData.Lenses.t etCounts,
        (td: T etData, _: T etQuery.Opt ons) => ScrubEngage ntHydrator.Ctx(td.suppress),
        hydrateScrubEngage nts
      )

    /**
     * T   s w re   w re up all t  separate hydrators  nto a s ngle [[T etDataValueHydrator]].
     *
     * Each hydrator  re  s e  r a [[T etDataValueHydrator]] or a [[T etDataEd Hydrator]].
     *   use [[Ed Hydrator]]s for anyth ng that needs to run  n parallel ([[ValueHydrator]]s can
     * only be run  n sequence).
     */
    ValueHydrator. nSequence(
      // Hydrate FeatureSw chResults f rst, so t y can be used by ot r hydrators  f needed
      hydrateFeatureSw chResults,
      Ed Hydrator
        . nParallel(
          ValueHydrator
            . nSequence(
              // T  result of runn ng t se hydrators  s saved as `cac ableT etResult` and
              // wr ten back to cac  v a `cac ChangesEffect`  n `hydrateRepo`
              T etHydrat on.captureCac ableT etResult(
                hydrateCac ableF elds
              ),
              // Uncac able hydrators that depend only on t  cac able f elds
              hydrateUncac able d a,
              // clean-up part ally hydrated ent  es before any of t  hydrators that look at
              // url and  d a ent  es run, so that t y never see bad ent  es.
              hydratePostCac Repa rs,
              // T se hydrators are all dependent on each ot r and/or t  prev ous hydrators
              hydrateDependentUncac ableF elds,
              // Sets `has d a`. Co s after Pasted d aHydrator  n order to  nclude pasted
              // p cs as  ll as ot r  d a & urls.
              hydrateHas d aToT etData
            )
            .toEd Hydrator,
          // T se hydrators do not rely on any ot r hydrators and so can be run  n parallel
          // w h t  above hydrators (and w h each ot r)
          hydrate ndependentUncac ableF elds
        )
        .toValueHydrator,
      // Depends on reported perspect val hav ng been hydrated  n Perspect veHydrator
      hydrateReportedT etV s b l yToT etData,
      // Remove superfluous urls ent  es w n t re  s a correspond ng  d aEnt y for t  sa  url
      scrubSuperfluousUrlEnt  es,
      // T  copyFromS ceT et hydrator needs to be located after t  hydrators that produce t 
      // f elds to copy.   must be located after Part alEnt yCleaner (part of postCac Repa rs),
      // wh ch removes fa led  d aEnt  es.   also depends on takedownCountryCodes hav ng been
      // hydrated  n TakedownHydrator.
      copyFromS ceT et,
      // depends on Add  onalF eldsHydrator and CopyFromS ceT et to copy safety labels
      hydrateT etV s b l yToT etData,
      // for  P 'd t ets,   want to d sable t et engage nt counts from be ng returned
      // StatusCounts for replyCount, ret etCount.
      // scrubEngage nts hydrator must co  after t et v s b l y hydrator.
      // t et v s b l y hydrator em s t  suppressed F lteredState needed for scrubb ng.
      scrubEngage nts,
      // t  hydrator runs w n wr  ng t  current t et
      // Esc rb rd co s last  n order to consu  a t et that's as close as poss ble
      // to t  t et wr ten to t et_events
      hydrateEsc rb rdAnnotat onsToT etAndCac dT et
        .only f((td, opts) => opts.cause.wr  ng(td.t et. d)),
      // Add an ell ps s to t  end of t  text for a T et that has a NoteT et assoc ated.
      // T   s so that t  T et  s d splayed on t  ho  t  l ne w h an ell ps s, lett ng
      // t  User know that t re's more to see.
      hydrateNoteT etSuff x,
      /**
       * Post-cac  repa r of QT text and ent  es to support render ng on all cl ents
       * Mov ng t  to end of t  p pel ne to avo d/m n m ze chance of follow ng hydrators
       * depend ng on mod f ed t et text or ent  es.
       * W n   start pers st ng shortUrl  n MH - permal nk won't be empty. t refore,
       *   won't run QuotedT etRefHydrator and just hydrate expanded and d splay
       * us ng QuotedT etRefUrlsHydrator.   w ll use hydrated permal nk to repa r
       * QT text and ent  es for non-upgraded cl ents  n t  step.
       * */
      hydrateT etLegacyFormat
    )
  }

  /**
   * Returns a new hydrator that takes t  produced result, and captures t  result value
   *  n t  `cac ableT etResult` f eld of t  enclosed `T etData`.
   */
  def captureCac ableT etResult(h: T etDataValueHydrator): T etDataValueHydrator =
    ValueHydrator[T etData, T etQuery.Opt ons] { (td, opts) =>
      h(td, opts).map { v =>
        //  n add  on to sav ng off a copy of ValueState, make sure that t  T etData  ns de
        // t  ValueState has  s "completedHydrat ons" set to t  ValueState.Hydrat onStates's
        // completedHydrat ons.  T   s used w n convert ng to a Cac dT et.
        v.map { td =>
          td.copy(
            cac ableT etResult = So (v.map(_.addHydrated(v.state.completedHydrat ons)))
          )
        }
      }
    }

  /**
   * Takes a ValueHydrator and a Lens and returns a `T etDataValueHydrator` that does three th ngs:
   *
   * 1. Runs t  ValueHydrator on t  lensed value
   * 2. Saves t  result back to t  ma n t et us ng t  lens
   * 3. Saves t  result back to t  t et  n cac ableT etResult us ng t  lens
   */
  def setOnT etAndCac dT et[A, C](
    l: Lens[T et, A],
    mkCtx: (T etData, T etQuery.Opt ons) => C,
    h: ValueHydrator[A, C]
  ): T etDataValueHydrator = {
    // A lens that goes from T etData -> t et -> l
    val t etDataLens = T etData.Lenses.t et.andT n(l)

    // A lens that goes from T etData -> cac ableT etResult -> t et -> l
    val cac dT etLens =
      T etLenses
        .requ reSo (T etData.Lenses.cac ableT etResult)
        .andT n(T etResult.Lenses.t et)
        .andT n(l)

    ValueHydrator[T etData, T etQuery.Opt ons] { (td, opts) =>
      h.run(t etDataLens.get(td), mkCtx(td, opts)).map { r =>
         f (r.state. sEmpty) {
          ValueState.unmod f ed(td)
        } else {
          r.map { v => Lens.setAll(td, t etDataLens -> v, cac dT etLens -> v) }
        }
      }
    }
  }

  /**
   * Creates a `T etDataValueHydrator` that hydrates a lensed value, overwr  ng
   * t  ex st ng value.
   */
  def setOnT etData[A, C](
    lens: Lens[T etData, A],
    mkCtx: (T etData, T etQuery.Opt ons) => C,
    h: ValueHydrator[A, C]
  ): T etDataValueHydrator =
    ValueHydrator[T etData, T etQuery.Opt ons] { (td, opts) =>
      h.run(lens.get(td), mkCtx(td, opts)).map { r =>
         f (r.state. sEmpty) ValueState.unmod f ed(td) else r.map(lens.set(td, _))
      }
    }

  /**
   * Produces an [[Effect]] that can be appl ed to a [[T etDataValueHydrator]] to wr e updated
   * values back to cac .
   */
  def cac Changes(
    cac : Lock ngCac [T et d, Cac d[T etData]],
    stats: StatsRece ver
  ): Effect[ValueState[T etData]] = {
    val updatedCounter = stats.counter("updated")
    val unchangedCounter = stats.counter("unchanged")
    val p cker = new T etRepoCac P cker[T etData](_.cac dAt)
    val cac ErrorCounter = stats.counter("cac _error")
    val m ss ngCac ableResultCounter = stats.counter("m ss ng_cac able_result")

    Effect[T etResult] { result =>
      // cac ErrorEncountered w ll never be set on `cac ableT etResult`, so   need to
      // look at t  outer t et state.
      val cac ErrorEncountered = result.state.cac ErrorEncountered

      result.value.cac ableT etResult match {
        case So (ValueState(td, state))  f state.mod f ed && !cac ErrorEncountered =>
          val t etData = td.addHydrated(state.completedHydrat ons)
          val now = T  .now
          val cac d = Cac d(So (t etData), Cac dValueStatus.Found, now, So (now))
          val handler = Lock ngCac .P ck ngHandler(cac d, p cker)

          updatedCounter. ncr()
          cac .lockAndSet(t etData.t et. d, handler)

        case So (ValueState(_, _))  f cac ErrorEncountered =>
          cac ErrorCounter. ncr()

        case None =>
          m ss ngCac ableResultCounter. ncr()

        case _ =>
          unchangedCounter. ncr()
      }
    }
  }

  /**
   * Wraps a hydrator w h a c ck such that   only executes t  hydrator  f `queryF lter`
   * returns true for t  `T etQuery.Opt on`  n t  `Ctx` value, and t  spec f ed
   * `Hydrat onType`  s not already marked as hav ng been completed  n
   * `ctx.t etData.completedHydrat ons`.   f t se cond  ons pass, and t  underly ng
   * hydrator  s executed, and t  result does not conta n a f eld-level or total fa lure,
   * t n t  result ng `Hydrat onState`  s updated to  nd cate that t  spec f ed
   * `Hydrat onType` has been completed.
   */
  def completeOnlyOnce[A, C <: T etCtx](
    queryF lter: T etQuery.Opt ons => Boolean = _ => true,
    hydrat onType: Hydrat onType,
    dependsOn: Set[Hydrat onType] = Set.empty,
    hydrator: ValueHydrator[A, C]
  ): ValueHydrator[A, C] = {
    val completedState = Hydrat onState.mod f ed(hydrat onType)

    ValueHydrator[A, C] { (a, ctx) =>
      hydrator(a, ctx).map { res =>
         f (res.state.fa ledF elds. sEmpty &&
          dependsOn.forall(ctx.completedHydrat ons.conta ns)) {
          // successful result!
           f (!ctx.completedHydrat ons.conta ns(hydrat onType)) {
            res.copy(state = res.state ++ completedState)
          } else {
            // forced rehydrat on - don't add hydrat onType or change mod f ed flag
            res
          }
        } else {
          // hydrat on fa led or not all dependenc es sat sf ed so don't mark as complete
          res
        }
      }
    }.only f { (a, ctx) =>
      queryF lter(ctx.opts) &&
      (!ctx.completedHydrat ons.conta ns(hydrat onType))
    }
  }

  /**
   * Appl es a `T etDataValueHydrator` to a `T etRepos ory.Type`-typed repos ory.
   * T   ncom ng `T etQuery.Opt ons` are f rst expanded us ng `opt onsExpander`, and t 
   * result ng opt ons passed to `repo` and `hydrator`.  T  result ng t et result
   * objects are passed to `cac ChangesEffect` for poss ble wr e-back to cac .  F nally,
   * t  t ets are scrubbed accord ng to t  or g nal  nput `T etQuery.Opt ons`.
   */
  def hydrateRepo(
    hydrator: T etDataValueHydrator,
    cac ChangesEffect: Effect[T etResult],
    opt onsExpander: T etQueryOpt onsExpander.Type
  )(
    repo: T etResultRepos ory.Type
  ): T etResultRepos ory.Type =
    (t et d: T et d, or g nalOpts: T etQuery.Opt ons) => {
      val expandedOpts = opt onsExpander(or g nalOpts)

      for {
        repoResult <- repo(t et d, expandedOpts)
        hydratorResult <- hydrator(repoResult.value, expandedOpts)
      } y eld {
        val hydrat ngRepoResult =
          T etResult(hydratorResult.value, repoResult.state ++ hydratorResult.state)

         f (or g nalOpts.cac Control.wr eToCac ) {
          cac ChangesEffect(hydrat ngRepoResult)
        }

        UnrequestedF eldScrubber(or g nalOpts).scrub(hydrat ngRepoResult)
      }
    }

  /**
   * A tr v al wrapper around a St ch[_] to prov de a `jo nW h`
   *  thod that lets us choose t  precedence of except ons.
   *
   * T  wrapper  s useful for t  case  n wh ch  's  mportant that
   *   spec fy wh ch of t  two except ons w ns (such as v s b l y
   * f lter ng).
   *
   * S nce t   s an [[AnyVal]], us ng t   s no more expens ve than
   *  nl n ng t  jo nW h  thod.
   */
  // exposed for test ng
  case class St chExcept onPrecedence[A](toSt ch: St ch[A]) extends AnyVal {

    /**
     * Concurrently evaluate two St ch[_] values. T   s d fferent
     * from St ch.jo n  n that any except on from t  express on on
     * t  left hand s de w ll take precedence over an except on on
     * t  r ght hand s de. T   ans that an except on from t 
     * r ght-hand s de w ll not short-c rcu  evaluat on, but an
     * except on on t  left-hand s de *w ll* short-c rcu . T   s
     * des rable because   allows us to return t  fa lure w h as
     * l tle latency as poss ble. (Compare to l ft ng *both* to Try,
     * wh ch would force us to wa  for both computat ons to complete
     * before return ng, even  f t  one w h t  h g r precedence  s
     * already known to be an except on.)
     */
    def jo nW h[B, C](rhs: St ch[B])(f: (A, B) => C): St chExcept onPrecedence[C] =
      St chExcept onPrecedence {
        St ch
          .jo nMap(toSt ch, rhs.l ftToTry) { (a, tryB) => tryB.map(b => f(a, b)) }
          .lo rFromTry
      }
  }
}
