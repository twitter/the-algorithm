package com.tw ter.fr gate.pushserv ce.adaptor

 mport com.tw ter.contentrecom nder.thr ftscala. tr cTag
 mport com.tw ter.cr_m xer.thr ftscala.CrM xerT etRequest
 mport com.tw ter.cr_m xer.thr ftscala.Not f cat onsContext
 mport com.tw ter.cr_m xer.thr ftscala.Product
 mport com.tw ter.cr_m xer.thr ftscala.ProductContext
 mport com.tw ter.cr_m xer.thr ftscala.{ tr cTag => CrM xer tr cTag}
 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Algor hmScore
 mport com.tw ter.fr gate.common.base.Cand dateS ce
 mport com.tw ter.fr gate.common.base.Cand dateS ceEl g ble
 mport com.tw ter.fr gate.common.base.CrM xerCand date
 mport com.tw ter.fr gate.common.base.Top cCand date
 mport com.tw ter.fr gate.common.base.Top cProofT etCand date
 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.common.pred cate.CommonOutNetworkT etCand datesS cePred cates.f lterOut nNetworkT ets
 mport com.tw ter.fr gate.common.pred cate.CommonOutNetworkT etCand datesS cePred cates.f lterOutReplyT et
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.pushserv ce.store.CrM xerT etStore
 mport com.tw ter.fr gate.pushserv ce.store.UttEnt yHydrat onStore
 mport com.tw ter.fr gate.pushserv ce.ut l.AdaptorUt ls
 mport com.tw ter.fr gate.pushserv ce.ut l.PushDev ceUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.Top csUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.T etW hTop cProof
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter. rm .pred cate.soc algraph.Relat onEdge
 mport com.tw ter.product_m xer.core.thr ftscala.Cl entContext
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.top cl st ng.utt.Local zedEnt y
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofRequest
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofResponse
 mport com.tw ter.ut l.Future
 mport scala.collect on.Map

case class ContentRecom nderM xerAdaptor(
  crM xerT etStore: CrM xerT etStore,
  t etyP eStore: ReadableStore[Long, T etyP eResult],
  edgeStore: ReadableStore[Relat onEdge, Boolean],
  top cSoc alProofServ ceStore: ReadableStore[Top cSoc alProofRequest, Top cSoc alProofResponse],
  uttEnt yHydrat onStore: UttEnt yHydrat onStore,
  globalStats: StatsRece ver)
    extends Cand dateS ce[Target, RawCand date]
    w h Cand dateS ceEl g ble[Target, RawCand date] {

  overr de val na : Str ng = t .getClass.getS mpleNa 

  pr vate[t ] val stats = globalStats.scope("ContentRecom nderM xerAdaptor")
  pr vate[t ] val numOfVal dAuthors = stats.stat("num_of_val d_authors")
  pr vate[t ] val numOutOfMax mumDropped = stats.stat("dropped_due_out_of_max mum")
  pr vate[t ] val total nputRecs = stats.counter(" nput_recs")
  pr vate[t ] val totalOutputRecs = stats.stat("output_recs")
  pr vate[t ] val totalRequests = stats.counter("total_requests")
  pr vate[t ] val nonReplyT etsCounter = stats.counter("non_reply_t ets")
  pr vate[t ] val totalOutNetworkRecs = stats.counter("out_network_t ets")
  pr vate[t ] val total nNetworkRecs = stats.counter(" n_network_t ets")

  /**
   * Bu lds OON raw cand dates based on  nput OON T ets
   */
  def bu ldOONRawCand dates(
     nputTarget: Target,
    oonT ets: Seq[T etyP eResult],
    t etScoreMap: Map[Long, Double],
    t et dToTagsMap: Map[Long, Seq[CrM xer tr cTag]],
    maxNumOfCand dates:  nt
  ): Opt on[Seq[RawCand date]] = {
    val cands = oonT ets.flatMap { t etResult =>
      val t et d = t etResult.t et. d
      generateOONRawCand date(
         nputTarget,
        t et d,
        So (t etResult),
        t etScoreMap,
        t et dToTagsMap
      )
    }

    val cand dates = restr ct(
      maxNumOfCand dates,
      cands,
      numOutOfMax mumDropped,
      totalOutputRecs
    )

    So (cand dates)
  }

  /**
   * Bu lds a s ngle RawCand date W h Top cProofT etCand date
   */
  def bu ldTop cT etRawCand date(
     nputTarget: Target,
    t etW hTop cProof: T etW hTop cProof,
    local zedEnt y: Local zedEnt y,
    tags: Opt on[Seq[ tr cTag]],
  ): RawCand date w h Top cProofT etCand date = {
    new RawCand date w h Top cProofT etCand date {
      overr de def target: Target =  nputTarget
      overr de def top cL st ngSett ng: Opt on[Str ng] = So (
        t etW hTop cProof.top cL st ngSett ng)
      overr de def t et d: Long = t etW hTop cProof.t et d
      overr de def t etyP eResult: Opt on[T etyP eResult] = So (
        t etW hTop cProof.t etyP eResult)
      overr de def semant cCoreEnt y d: Opt on[Long] = So (t etW hTop cProof.top c d)
      overr de def local zedUttEnt y: Opt on[Local zedEnt y] = So (local zedEnt y)
      overr de def algor hmCR: Opt on[Str ng] = t etW hTop cProof.algor hmCR
      overr de def tagsCR: Opt on[Seq[ tr cTag]] = tags
      overr de def  sOutOfNetwork: Boolean = t etW hTop cProof. sOON
    }
  }

  /**
   * Takes a group of Top cT ets and transforms t m  nto RawCand dates
   */
  def bu ldTop cT etRawCand dates(
     nputTarget: Target,
    top cProofCand dates: Seq[T etW hTop cProof],
    t et dToTagsMap: Map[Long, Seq[CrM xer tr cTag]],
    maxNumberOfCands:  nt
  ): Future[Opt on[Seq[RawCand date]]] = {
    val semant cCoreEnt y ds = top cProofCand dates
      .map(_.top c d)
      .toSet

    Top csUt l
      .getLocal zedEnt yMap( nputTarget, semant cCoreEnt y ds, uttEnt yHydrat onStore)
      .map { local zedEnt yMap =>
        val rawCand dates = top cProofCand dates.collect {
          case top cSoc alProof: T etW hTop cProof
               f local zedEnt yMap.conta ns(top cSoc alProof.top c d) =>
            // Once   deprecate CR calls,   should replace t  code to use t  CrM xer tr cTag
            val tags = t et dToTagsMap.get(top cSoc alProof.t et d).map {
              _.flatMap { tag =>  tr cTag.get(tag.value) }
            }
            bu ldTop cT etRawCand date(
               nputTarget,
              top cSoc alProof,
              local zedEnt yMap(top cSoc alProof.top c d),
              tags
            )
        }

        val candResult = restr ct(
          maxNumberOfCands,
          rawCand dates,
          numOutOfMax mumDropped,
          totalOutputRecs
        )

        So (candResult)
      }
  }

  pr vate def generateOONRawCand date(
     nputTarget: Target,
     d: Long,
    result: Opt on[T etyP eResult],
    t etScoreMap: Map[Long, Double],
    t et dToTagsMap: Map[Long, Seq[CrM xer tr cTag]]
  ): Opt on[RawCand date w h T etCand date] = {
    val tagsFromCR = t et dToTagsMap.get( d).map { _.flatMap { tag =>  tr cTag.get(tag.value) } }
    val cand date = new RawCand date w h CrM xerCand date w h Top cCand date w h Algor hmScore {
      overr de val t et d =  d
      overr de val target =  nputTarget
      overr de val t etyP eResult = result
      overr de val local zedUttEnt y = None
      overr de val semant cCoreEnt y d = None
      overr de def commonRecType =
        get d aBasedCRT(
          CommonRecom ndat onType.Tw stlyT et,
          CommonRecom ndat onType.Tw stlyPhoto,
          CommonRecom ndat onType.Tw stlyV deo)
      overr de def tagsCR = tagsFromCR
      overr de def algor hmScore = t etScoreMap.get( d)
      overr de def algor hmCR = None
    }
    So (cand date)
  }

  pr vate def restr ct(
    maxNumToReturn:  nt,
    cand dates: Seq[RawCand date],
    numOutOfMax mumDropped: Stat,
    totalOutputRecs: Stat
  ): Seq[RawCand date] = {
    val newCand dates = cand dates.take(maxNumToReturn)
    val numDropped = cand dates.length - newCand dates.length
    numOutOfMax mumDropped.add(numDropped)
    totalOutputRecs.add(newCand dates.s ze)
    newCand dates
  }

  pr vate def bu ldCrM xerRequest(
    target: Target,
    countryCode: Opt on[Str ng],
    language: Opt on[Str ng],
    seenT ets: Seq[Long]
  ): CrM xerT etRequest = {
    CrM xerT etRequest(
      cl entContext = Cl entContext(
        user d = So (target.target d),
        countryCode = countryCode,
        languageCode = language
      ),
      product = Product.Not f cat ons,
      productContext = So (ProductContext.Not f cat onsContext(Not f cat onsContext())),
      excludedT et ds = So (seenT ets)
    )
  }

  pr vate def selectCand datesToSendBasedOnSett ngs(
     sRecom ndat onsEl g ble: Boolean,
     sTop csEl g ble: Boolean,
    oonRawCand dates: Opt on[Seq[RawCand date]],
    top cT etCand dates: Opt on[Seq[RawCand date]]
  ): Opt on[Seq[RawCand date]] = {
     f ( sRecom ndat onsEl g ble &&  sTop csEl g ble) {
      So (top cT etCand dates.getOrElse(Seq.empty) ++ oonRawCand dates.getOrElse(Seq.empty))
    } else  f ( sRecom ndat onsEl g ble) {
      oonRawCand dates
    } else  f ( sTop csEl g ble) {
      top cT etCand dates
    } else None
  }

  overr de def get(target: Target): Future[Opt on[Seq[RawCand date]]] = {
    Future
      .jo n(
        target.seenT et ds,
        target.countryCode,
        target. nferredUserDev ceLanguage,
        PushDev ceUt l. sTop csEl g ble(target),
        PushDev ceUt l. sRecom ndat onsEl g ble(target)
      ).flatMap {
        case (seenT ets, countryCode, language,  sTop csEl g ble,  sRecom ndat onsEl g ble) =>
          val request = bu ldCrM xerRequest(target, countryCode, language, seenT ets)
          crM xerT etStore.getT etRecom ndat ons(request).flatMap {
            case So (response) =>
              total nputRecs. ncr(response.t ets.s ze)
              totalRequests. ncr()
              AdaptorUt ls
                .getT etyP eResults(
                  response.t ets.map(_.t et d).toSet,
                  t etyP eStore).flatMap { t etyP eResultMap =>
                  f lterOut nNetworkT ets(
                    target,
                    f lterOutReplyT et(t etyP eResultMap.toMap, nonReplyT etsCounter),
                    edgeStore,
                    numOfVal dAuthors).flatMap {
                    outNetworkT etsW h d: Seq[(Long, T etyP eResult)] =>
                      totalOutNetworkRecs. ncr(outNetworkT etsW h d.s ze)
                      total nNetworkRecs. ncr(response.t ets.s ze - outNetworkT etsW h d.s ze)
                      val outNetworkT ets: Seq[T etyP eResult] = outNetworkT etsW h d.map {
                        case (_, t etyP eResult) => t etyP eResult
                      }

                      val t et dToTagsMap = response.t ets.map { t et =>
                        t et.t et d -> t et. tr cTags.getOrElse(Seq.empty)
                      }.toMap

                      val t etScoreMap = response.t ets.map { t et =>
                        t et.t et d -> t et.score
                      }.toMap

                      val maxNumOfCand dates =
                        target.params(PushFeatureSw chParams.NumberOfMaxCrM xerCand datesParam)

                      val oonRawCand dates =
                        bu ldOONRawCand dates(
                          target,
                          outNetworkT ets,
                          t etScoreMap,
                          t et dToTagsMap,
                          maxNumOfCand dates)

                      Top csUt l
                        .getTop cSoc alProofs(
                          target,
                          outNetworkT ets,
                          top cSoc alProofServ ceStore,
                          edgeStore,
                          PushFeatureSw chParams.Top cProofT etCand datesTop cScoreThreshold).flatMap {
                          t etsW hTop cProof =>
                            bu ldTop cT etRawCand dates(
                              target,
                              t etsW hTop cProof,
                              t et dToTagsMap,
                              maxNumOfCand dates)
                        }.map { top cT etCand dates =>
                          selectCand datesToSendBasedOnSett ngs(
                             sRecom ndat onsEl g ble,
                             sTop csEl g ble,
                            oonRawCand dates,
                            top cT etCand dates)
                        }
                  }
                }
            case _ => Future.None
          }
      }
  }

  /**
   * For a user to be ava lable t  follow ng news to happen
   */
  overr de def  sCand dateS ceAva lable(target: Target): Future[Boolean] = {
    Future
      .jo n(
        PushDev ceUt l. sRecom ndat onsEl g ble(target),
        PushDev ceUt l. sTop csEl g ble(target)
      ).map {
        case ( sRecom ndat onsEl g ble,  sTop csEl g ble) =>
          ( sRecom ndat onsEl g ble ||  sTop csEl g ble) &&
            target.params(PushParams.ContentRecom nderM xerAdaptorDec der)
      }
  }
}
