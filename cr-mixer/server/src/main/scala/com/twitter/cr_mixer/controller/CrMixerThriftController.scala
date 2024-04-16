package com.tw ter.cr_m xer.controller

 mport com.tw ter.core_workflows.user_model.thr ftscala.UserState
 mport com.tw ter.cr_m xer.cand date_generat on.AdsCand dateGenerator
 mport com.tw ter.cr_m xer.cand date_generat on.CrCand dateGenerator
 mport com.tw ter.cr_m xer.cand date_generat on.FrsT etCand dateGenerator
 mport com.tw ter.cr_m xer.cand date_generat on.RelatedT etCand dateGenerator
 mport com.tw ter.cr_m xer.cand date_generat on.RelatedV deoT etCand dateGenerator
 mport com.tw ter.cr_m xer.cand date_generat on.Top cT etCand dateGenerator
 mport com.tw ter.cr_m xer.cand date_generat on.UtegT etCand dateGenerator
 mport com.tw ter.cr_m xer.featuresw ch.ParamsBu lder
 mport com.tw ter.cr_m xer.logg ng.CrM xerScr beLogger
 mport com.tw ter.cr_m xer.logg ng.RelatedT etScr beLogger
 mport com.tw ter.cr_m xer.logg ng.AdsRecom ndat onsScr beLogger
 mport com.tw ter.cr_m xer.logg ng.RelatedT etScr be tadata
 mport com.tw ter.cr_m xer.logg ng.Scr be tadata
 mport com.tw ter.cr_m xer.logg ng.UtegT etScr beLogger
 mport com.tw ter.cr_m xer.model.AdsCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.CrCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.FrsT etCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.cr_m xer.model.RankedAdsCand date
 mport com.tw ter.cr_m xer.model.RankedCand date
 mport com.tw ter.cr_m xer.model.RelatedT etCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.RelatedV deoT etCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.Top cT etCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.T etW hScoreAndSoc alProof
 mport com.tw ter.cr_m xer.model.UtegT etCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.param.AdsParams
 mport com.tw ter.cr_m xer.param.FrsParams.FrsBasedCand dateGenerat onMaxCand datesNumParam
 mport com.tw ter.cr_m xer.param.GlobalParams
 mport com.tw ter.cr_m xer.param.RelatedT etGlobalParams
 mport com.tw ter.cr_m xer.param.RelatedV deoT etGlobalParams
 mport com.tw ter.cr_m xer.param.Top cT etParams
 mport com.tw ter.cr_m xer.param.dec der.CrM xerDec der
 mport com.tw ter.cr_m xer.param.dec der.Dec derConstants
 mport com.tw ter.cr_m xer.param.dec der.Endpo ntLoadS dder
 mport com.tw ter.cr_m xer.thr ftscala.AdT etRecom ndat on
 mport com.tw ter.cr_m xer.thr ftscala.AdsRequest
 mport com.tw ter.cr_m xer.thr ftscala.AdsResponse
 mport com.tw ter.cr_m xer.thr ftscala.CrM xerT etRequest
 mport com.tw ter.cr_m xer.thr ftscala.CrM xerT etResponse
 mport com.tw ter.cr_m xer.thr ftscala.FrsT etRequest
 mport com.tw ter.cr_m xer.thr ftscala.FrsT etResponse
 mport com.tw ter.cr_m xer.thr ftscala.RelatedT et
 mport com.tw ter.cr_m xer.thr ftscala.RelatedT etRequest
 mport com.tw ter.cr_m xer.thr ftscala.RelatedT etResponse
 mport com.tw ter.cr_m xer.thr ftscala.RelatedV deoT et
 mport com.tw ter.cr_m xer.thr ftscala.RelatedV deoT etRequest
 mport com.tw ter.cr_m xer.thr ftscala.RelatedV deoT etResponse
 mport com.tw ter.cr_m xer.thr ftscala.Top cT et
 mport com.tw ter.cr_m xer.thr ftscala.Top cT etRequest
 mport com.tw ter.cr_m xer.thr ftscala.Top cT etResponse
 mport com.tw ter.cr_m xer.thr ftscala.T etRecom ndat on
 mport com.tw ter.cr_m xer.thr ftscala.UtegT et
 mport com.tw ter.cr_m xer.thr ftscala.UtegT etRequest
 mport com.tw ter.cr_m xer.thr ftscala.UtegT etResponse
 mport com.tw ter.cr_m xer.ut l. tr cTagUt l
 mport com.tw ter.cr_m xer.ut l.S gnalT  stampStatsUt l
 mport com.tw ter.cr_m xer.{thr ftscala => t}
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.thr ft.Controller
 mport com.tw ter. rm .store.common.ReadableWr ableStore
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.thr ftscala.Top c d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.t  l ne_logg ng.{thr ftscala => thr ftlog}
 mport com.tw ter.t  l nes.trac ng.lensv ew.funnelser es.T etScoreFunnelSer es
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport java.ut l.UU D
 mport javax. nject. nject
 mport org.apac .commons.lang.except on.Except onUt ls

class CrM xerThr ftController @ nject() (
  crCand dateGenerator: CrCand dateGenerator,
  relatedT etCand dateGenerator: RelatedT etCand dateGenerator,
  relatedV deoT etCand dateGenerator: RelatedV deoT etCand dateGenerator,
  utegT etCand dateGenerator: UtegT etCand dateGenerator,
  frsT etCand dateGenerator: FrsT etCand dateGenerator,
  top cT etCand dateGenerator: Top cT etCand dateGenerator,
  crM xerScr beLogger: CrM xerScr beLogger,
  relatedT etScr beLogger: RelatedT etScr beLogger,
  utegT etScr beLogger: UtegT etScr beLogger,
  adsRecom ndat onsScr beLogger: AdsRecom ndat onsScr beLogger,
  adsCand dateGenerator: AdsCand dateGenerator,
  dec der: CrM xerDec der,
  paramsBu lder: ParamsBu lder,
  endpo ntLoadS dder: Endpo ntLoadS dder,
  s gnalT  stampStatsUt l: S gnalT  stampStatsUt l,
  t etRecom ndat onResultsStore: ReadableWr ableStore[User d, CrM xerT etResponse],
  userStateStore: ReadableStore[User d, UserState],
  statsRece ver: StatsRece ver)
    extends Controller(t.CrM xer) {

  lazy pr vate val t etScoreFunnelSer es = new T etScoreFunnelSer es(statsRece ver)

  pr vate def logErr ssage(endpo nt: Str ng, e: Throwable): Un  = {
    val msg = Seq(
      s"Fa led endpo nt $endpo nt: ${e.getLocal zed ssage}",
      Except onUt ls.getStackTrace(e)
    ).mkStr ng("\n")

    /** *
     *   chose logger. nfo()  re to pr nt  ssage  nstead of logger.error s nce that
     * logger.error so t  s suppresses deta led stacktrace.
     */
    logger. nfo(msg)
  }

  pr vate def generateRequestUU D(): Long = {

    /** *
     *   generate un que UU D v a b w se operat ons. See t  below l nk for more:
     * https://stackoverflow.com/quest ons/15184820/how-to-generate-un que-pos  ve-long-us ng-uu d
     */
    UU D.randomUU D().getMostS gn f cantB s & Long.MaxValue
  }

  handle(t.CrM xer.GetT etRecom ndat ons) { args: t.CrM xer.GetT etRecom ndat ons.Args =>
    val endpo ntNa  = "getT etRecom ndat ons"

    val requestUU D = generateRequestUU D()
    val startT   = T  .now. nM ll seconds
    val user d = args.request.cl entContext.user d.getOrElse(
      throw new  llegalArgu ntExcept on("user d must be present  n t  Thr ft cl entContext")
    )
    val queryFut = bu ldCrCand dateGeneratorQuery(args.request, requestUU D, user d)
    queryFut.flatMap { query =>
      val scr be tadata = Scr be tadata.from(query)
      endpo ntLoadS dder(endpo ntNa , query.product.or g nalNa ) {

        val response = crCand dateGenerator.get(query)

        val blueVer f edScr bedResponse = response.flatMap { rankedCand dates =>
          val hasBlueVer f edCand date = rankedCand dates.ex sts { t et =>
            t et.t et nfo.hasBlueVer f edAnnotat on.conta ns(true)
          }

           f (hasBlueVer f edCand date) {
            crM xerScr beLogger.scr beGetT etRecom ndat onsForBlueVer f ed(
              scr be tadata,
              response)
          } else {
            response
          }
        }

        val thr ftResponse = blueVer f edScr bedResponse.map { cand dates =>
           f (query.product == t.Product.Ho ) {
            scr beT etScoreFunnelSer es(cand dates)
          }
          bu ldThr ftResponse(cand dates)
        }

        cac T etRecom ndat onResults(args.request, thr ftResponse)

        crM xerScr beLogger.scr beGetT etRecom ndat ons(
          args.request,
          startT  ,
          scr be tadata,
          thr ftResponse)
      }.rescue {
        case Endpo ntLoadS dder.LoadS dd ngExcept on =>
          Future(CrM xerT etResponse(Seq.empty))
        case e =>
          logErr ssage(endpo ntNa , e)
          Future(CrM xerT etResponse(Seq.empty))
      }
    }

  }

  /** *
   * GetRelatedT etsForQueryT et and GetRelatedT etsForQueryAuthor are essent ally
   * do ng very s m lar th ngs, except that one passes  n T et d wh ch calls T etBased eng ne,
   * and t  ot r passes  n Author d wh ch calls ProducerBased eng ne.
   */
  handle(t.CrM xer.GetRelatedT etsForQueryT et) {
    args: t.CrM xer.GetRelatedT etsForQueryT et.Args =>
      val endpo ntNa  = "getRelatedT etsForQueryT et"
      getRelatedT ets(endpo ntNa , args.request)
  }

  handle(t.CrM xer.GetRelatedV deoT etsForQueryT et) {
    args: t.CrM xer.GetRelatedV deoT etsForQueryT et.Args =>
      val endpo ntNa  = "getRelatedV deoT etsForQueryV deoT et"
      getRelatedV deoT ets(endpo ntNa , args.request)

  }

  handle(t.CrM xer.GetRelatedT etsForQueryAuthor) {
    args: t.CrM xer.GetRelatedT etsForQueryAuthor.Args =>
      val endpo ntNa  = "getRelatedT etsForQueryAuthor"
      getRelatedT ets(endpo ntNa , args.request)
  }

  pr vate def getRelatedT ets(
    endpo ntNa : Str ng,
    request: RelatedT etRequest
  ): Future[RelatedT etResponse] = {
    val requestUU D = generateRequestUU D()
    val startT   = T  .now. nM ll seconds
    val queryFut = bu ldRelatedT etQuery(request, requestUU D)

    queryFut.flatMap { query =>
      val relatedT etScr be tadata = RelatedT etScr be tadata.from(query)
      endpo ntLoadS dder(endpo ntNa , query.product.or g nalNa ) {
        relatedT etScr beLogger.scr beGetRelatedT ets(
          request,
          startT  ,
          relatedT etScr be tadata,
          relatedT etCand dateGenerator
            .get(query)
            .map(bu ldRelatedT etResponse))
      }.rescue {
        case Endpo ntLoadS dder.LoadS dd ngExcept on =>
          Future(RelatedT etResponse(Seq.empty))
        case e =>
          logErr ssage(endpo ntNa , e)
          Future(RelatedT etResponse(Seq.empty))
      }
    }

  }

  pr vate def getRelatedV deoT ets(
    endpo ntNa : Str ng,
    request: RelatedV deoT etRequest
  ): Future[RelatedV deoT etResponse] = {
    val requestUU D = generateRequestUU D()
    val queryFut = bu ldRelatedV deoT etQuery(request, requestUU D)

    queryFut.flatMap { query =>
      endpo ntLoadS dder(endpo ntNa , query.product.or g nalNa ) {
        relatedV deoT etCand dateGenerator.get(query).map {  n  alCand dateSeq =>
          bu ldRelatedV deoT etResponse( n  alCand dateSeq)
        }
      }.rescue {
        case Endpo ntLoadS dder.LoadS dd ngExcept on =>
          Future(RelatedV deoT etResponse(Seq.empty))
        case e =>
          logErr ssage(endpo ntNa , e)
          Future(RelatedV deoT etResponse(Seq.empty))
      }
    }
  }

  handle(t.CrM xer.GetFrsBasedT etRecom ndat ons) {
    args: t.CrM xer.GetFrsBasedT etRecom ndat ons.Args =>
      val endpo ntNa  = "getFrsBasedT etRecom ndat ons"

      val requestUU D = generateRequestUU D()
      val queryFut = bu ldFrsBasedT etQuery(args.request, requestUU D)
      queryFut.flatMap { query =>
        endpo ntLoadS dder(endpo ntNa , query.product.or g nalNa ) {
          frsT etCand dateGenerator.get(query).map(FrsT etResponse(_))
        }.rescue {
          case e =>
            logErr ssage(endpo ntNa , e)
            Future(FrsT etResponse(Seq.empty))
        }
      }
  }

  handle(t.CrM xer.GetTop cT etRecom ndat ons) {
    args: t.CrM xer.GetTop cT etRecom ndat ons.Args =>
      val endpo ntNa  = "getTop cT etRecom ndat ons"

      val requestUU D = generateRequestUU D()
      val query = bu ldTop cT etQuery(args.request, requestUU D)

      endpo ntLoadS dder(endpo ntNa , query.product.or g nalNa ) {
        top cT etCand dateGenerator.get(query).map(Top cT etResponse(_))
      }.rescue {
        case e =>
          logErr ssage(endpo ntNa , e)
          Future(Top cT etResponse(Map.empty[Long, Seq[Top cT et]]))
      }
  }

  handle(t.CrM xer.GetUtegT etRecom ndat ons) {
    args: t.CrM xer.GetUtegT etRecom ndat ons.Args =>
      val endpo ntNa  = "getUtegT etRecom ndat ons"

      val requestUU D = generateRequestUU D()
      val startT   = T  .now. nM ll seconds
      val queryFut = bu ldUtegT etQuery(args.request, requestUU D)
      queryFut
        .flatMap { query =>
          val scr be tadata = Scr be tadata.from(query)
          endpo ntLoadS dder(endpo ntNa , query.product.or g nalNa ) {
            utegT etScr beLogger.scr beGetUtegT etRecom ndat ons(
              args.request,
              startT  ,
              scr be tadata,
              utegT etCand dateGenerator
                .get(query)
                .map(bu ldUtegT etResponse)
            )
          }.rescue {
            case e =>
              logErr ssage(endpo ntNa , e)
              Future(UtegT etResponse(Seq.empty))
          }
        }
  }

  handle(t.CrM xer.GetAdsRecom ndat ons) { args: t.CrM xer.GetAdsRecom ndat ons.Args =>
    val endpo ntNa  = "getAdsRecom ndat ons"
    val queryFut = bu ldAdsCand dateGeneratorQuery(args.request)
    val startT   = T  .now. nM ll seconds
    queryFut.flatMap { query =>
      {
        val scr be tadata = Scr be tadata.from(query)
        val response = adsCand dateGenerator
          .get(query).map { cand dates =>
            bu ldAdsResponse(cand dates)
          }
        adsRecom ndat onsScr beLogger.scr beGetAdsRecom ndat ons(
          args.request,
          startT  ,
          scr be tadata,
          response,
          query.params(AdsParams.EnableScr be)
        )
      }.rescue {
        case e =>
          logErr ssage(endpo ntNa , e)
          Future(AdsResponse(Seq.empty))
      }
    }

  }

  pr vate def bu ldCrCand dateGeneratorQuery(
    thr ftRequest: CrM xerT etRequest,
    requestUU D: Long,
    user d: Long
  ): Future[CrCand dateGeneratorQuery] = {

    val product = thr ftRequest.product
    val productContext = thr ftRequest.productContext
    val scopedStats = statsRece ver
      .scope(product.toStr ng).scope("CrM xerT etRequest")

    userStateStore
      .get(user d).map { userStateOpt =>
        val userState = userStateOpt
          .getOrElse(UserState.EnumUnknownUserState(100))
        scopedStats.scope("UserState").counter(userState.toStr ng). ncr()

        val params =
          paramsBu lder.bu ldFromCl entContext(
            thr ftRequest.cl entContext,
            thr ftRequest.product,
            userState
          )

        // Spec fy product-spec f c behav or mapp ng  re
        val maxNumResults = (product, productContext) match {
          case (t.Product.Ho , So (t.ProductContext.Ho Context(ho Context))) =>
            ho Context.maxResults.getOrElse(9999)
          case (t.Product.Not f cat ons, So (t.ProductContext.Not f cat onsContext(cxt))) =>
            params(GlobalParams.MaxCand datesPerRequestParam)
          case (t.Product.Ema l, None) =>
            params(GlobalParams.MaxCand datesPerRequestParam)
          case (t.Product. m rs ve d aV e r, None) =>
            params(GlobalParams.MaxCand datesPerRequestParam)
          case (t.Product.V deoCarousel, None) =>
            params(GlobalParams.MaxCand datesPerRequestParam)
          case _ =>
            throw new  llegalArgu ntExcept on(
              s"Product ${product} and ProductContext ${productContext} are not allo d  n CrM xer"
            )
        }

        CrCand dateGeneratorQuery(
          user d = user d,
          product = product,
          userState = userState,
          maxNumResults = maxNumResults,
           mpressedT etL st = thr ftRequest.excludedT et ds.getOrElse(N l).toSet,
          params = params,
          requestUU D = requestUU D,
          languageCode = thr ftRequest.cl entContext.languageCode
        )
      }
  }

  pr vate def bu ldRelatedT etQuery(
    thr ftRequest: RelatedT etRequest,
    requestUU D: Long
  ): Future[RelatedT etCand dateGeneratorQuery] = {

    val product = thr ftRequest.product
    val scopedStats = statsRece ver
      .scope(product.toStr ng).scope("RelatedT etRequest")
    val userStateFut: Future[UserState] = (thr ftRequest.cl entContext.user d match {
      case So (user d) => userStateStore.get(user d)
      case None => Future.value(So (UserState.EnumUnknownUserState(100)))
    }).map(_.getOrElse(UserState.EnumUnknownUserState(100)))

    userStateFut.map { userState =>
      scopedStats.scope("UserState").counter(userState.toStr ng). ncr()
      val params =
        paramsBu lder.bu ldFromCl entContext(
          thr ftRequest.cl entContext,
          thr ftRequest.product,
          userState)

      // Spec fy product-spec f c behav or mapp ng  re
      // Currently, Ho  takes 10, and RUX takes 100
      val maxNumResults = params(RelatedT etGlobalParams.MaxCand datesPerRequestParam)

      RelatedT etCand dateGeneratorQuery(
         nternal d = thr ftRequest. nternal d,
        cl entContext = thr ftRequest.cl entContext,
        product = product,
        maxNumResults = maxNumResults,
         mpressedT etL st = thr ftRequest.excludedT et ds.getOrElse(N l).toSet,
        params = params,
        requestUU D = requestUU D
      )
    }
  }

  pr vate def bu ldAdsCand dateGeneratorQuery(
    thr ftRequest: AdsRequest
  ): Future[AdsCand dateGeneratorQuery] = {
    val user d = thr ftRequest.cl entContext.user d.getOrElse(
      throw new  llegalArgu ntExcept on("user d must be present  n t  Thr ft cl entContext")
    )
    val product = thr ftRequest.product
    val requestUU D = generateRequestUU D()
    userStateStore
      .get(user d).map { userStateOpt =>
        val userState = userStateOpt
          .getOrElse(UserState.EnumUnknownUserState(100))
        val params =
          paramsBu lder.bu ldFromCl entContext(
            thr ftRequest.cl entContext,
            thr ftRequest.product,
            userState)
        val maxNumResults = params(AdsParams.AdsCand dateGenerat onMaxCand datesNumParam)
        AdsCand dateGeneratorQuery(
          user d = user d,
          product = product,
          userState = userState,
          params = params,
          maxNumResults = maxNumResults,
          requestUU D = requestUU D
        )
      }
  }

  pr vate def bu ldRelatedV deoT etQuery(
    thr ftRequest: RelatedV deoT etRequest,
    requestUU D: Long
  ): Future[RelatedV deoT etCand dateGeneratorQuery] = {

    val product = thr ftRequest.product
    val scopedStats = statsRece ver
      .scope(product.toStr ng).scope("RelatedV deoT etRequest")
    val userStateFut: Future[UserState] = (thr ftRequest.cl entContext.user d match {
      case So (user d) => userStateStore.get(user d)
      case None => Future.value(So (UserState.EnumUnknownUserState(100)))
    }).map(_.getOrElse(UserState.EnumUnknownUserState(100)))

    userStateFut.map { userState =>
      scopedStats.scope("UserState").counter(userState.toStr ng). ncr()
      val params =
        paramsBu lder.bu ldFromCl entContext(
          thr ftRequest.cl entContext,
          thr ftRequest.product,
          userState)

      val maxNumResults = params(RelatedV deoT etGlobalParams.MaxCand datesPerRequestParam)

      RelatedV deoT etCand dateGeneratorQuery(
         nternal d = thr ftRequest. nternal d,
        cl entContext = thr ftRequest.cl entContext,
        product = product,
        maxNumResults = maxNumResults,
         mpressedT etL st = thr ftRequest.excludedT et ds.getOrElse(N l).toSet,
        params = params,
        requestUU D = requestUU D
      )
    }

  }

  pr vate def bu ldUtegT etQuery(
    thr ftRequest: UtegT etRequest,
    requestUU D: Long
  ): Future[UtegT etCand dateGeneratorQuery] = {

    val user d = thr ftRequest.cl entContext.user d.getOrElse(
      throw new  llegalArgu ntExcept on("user d must be present  n t  Thr ft cl entContext")
    )
    val product = thr ftRequest.product
    val productContext = thr ftRequest.productContext
    val scopedStats = statsRece ver
      .scope(product.toStr ng).scope("UtegT etRequest")

    userStateStore
      .get(user d).map { userStateOpt =>
        val userState = userStateOpt
          .getOrElse(UserState.EnumUnknownUserState(100))
        scopedStats.scope("UserState").counter(userState.toStr ng). ncr()

        val params =
          paramsBu lder.bu ldFromCl entContext(
            thr ftRequest.cl entContext,
            thr ftRequest.product,
            userState
          )

        // Spec fy product-spec f c behav or mapp ng  re
        val maxNumResults = (product, productContext) match {
          case (t.Product.Ho , So (t.ProductContext.Ho Context(ho Context))) =>
            ho Context.maxResults.getOrElse(9999)
          case _ =>
            throw new  llegalArgu ntExcept on(
              s"Product ${product} and ProductContext ${productContext} are not allo d  n CrM xer"
            )
        }

        UtegT etCand dateGeneratorQuery(
          user d = user d,
          product = product,
          userState = userState,
          maxNumResults = maxNumResults,
           mpressedT etL st = thr ftRequest.excludedT et ds.getOrElse(N l).toSet,
          params = params,
          requestUU D = requestUU D
        )
      }

  }

  pr vate def bu ldTop cT etQuery(
    thr ftRequest: Top cT etRequest,
    requestUU D: Long
  ): Top cT etCand dateGeneratorQuery = {
    val user d = thr ftRequest.cl entContext.user d.getOrElse(
      throw new  llegalArgu ntExcept on(
        "user d must be present  n t  Top cT etRequest cl entContext")
    )
    val product = thr ftRequest.product
    val productContext = thr ftRequest.productContext

    // Spec fy product-spec f c behav or mapp ng  re
    val  sV deoOnly = (product, productContext) match {
      case (t.Product.ExploreTop cs, So (t.ProductContext.ExploreContext(context))) =>
        context. sV deoOnly
      case (t.Product.Top cLand ngPage, None) =>
        false
      case (t.Product.Ho Top csBackf ll, None) =>
        false
      case (t.Product.Top cT etsStrato, None) =>
        false
      case _ =>
        throw new  llegalArgu ntExcept on(
          s"Product ${product} and ProductContext ${productContext} are not allo d  n CrM xer"
        )
    }

    statsRece ver.scope(product.toStr ng).counter(Top cT etRequest.toStr ng). ncr()

    val params =
      paramsBu lder.bu ldFromCl entContext(
        thr ftRequest.cl entContext,
        product,
        UserState.EnumUnknownUserState(100)
      )

    val top c ds = thr ftRequest.top c ds.map { top c d =>
      Top c d(
        ent y d = top c d,
        language = thr ftRequest.cl entContext.languageCode,
        country = None
      )
    }.toSet

    Top cT etCand dateGeneratorQuery(
      user d = user d,
      top c ds = top c ds,
      product = product,
      maxNumResults = params(Top cT etParams.MaxTop cT etCand datesParam),
       mpressedT etL st = thr ftRequest.excludedT et ds.getOrElse(N l).toSet,
      params = params,
      requestUU D = requestUU D,
       sV deoOnly =  sV deoOnly
    )
  }

  pr vate def bu ldFrsBasedT etQuery(
    thr ftRequest: FrsT etRequest,
    requestUU D: Long
  ): Future[FrsT etCand dateGeneratorQuery] = {
    val user d = thr ftRequest.cl entContext.user d.getOrElse(
      throw new  llegalArgu ntExcept on(
        "user d must be present  n t  FrsT etRequest cl entContext")
    )
    val product = thr ftRequest.product
    val productContext = thr ftRequest.productContext

    val scopedStats = statsRece ver
      .scope(product.toStr ng).scope("FrsT etRequest")

    userStateStore
      .get(user d).map { userStateOpt =>
        val userState = userStateOpt
          .getOrElse(UserState.EnumUnknownUserState(100))
        scopedStats.scope("UserState").counter(userState.toStr ng). ncr()

        val params =
          paramsBu lder.bu ldFromCl entContext(
            thr ftRequest.cl entContext,
            thr ftRequest.product,
            userState
          )
        val maxNumResults = (product, productContext) match {
          case (t.Product.Ho , So (t.ProductContext.Ho Context(ho Context))) =>
            ho Context.maxResults.getOrElse(
              params(FrsBasedCand dateGenerat onMaxCand datesNumParam))
          case _ =>
            params(FrsBasedCand dateGenerat onMaxCand datesNumParam)
        }

        FrsT etCand dateGeneratorQuery(
          user d = user d,
          product = product,
          maxNumResults = maxNumResults,
           mpressedT etL st = thr ftRequest.excludedT et ds.getOrElse(N l).toSet,
           mpressedUserL st = thr ftRequest.excludedUser ds.getOrElse(N l).toSet,
          params = params,
          languageCodeOpt = thr ftRequest.cl entContext.languageCode,
          countryCodeOpt = thr ftRequest.cl entContext.countryCode,
          requestUU D = requestUU D
        )
      }
  }

  pr vate def bu ldThr ftResponse(
    cand dates: Seq[RankedCand date]
  ): CrM xerT etResponse = {

    val t ets = cand dates.map { cand date =>
      T etRecom ndat on(
        t et d = cand date.t et d,
        score = cand date.pred ct onScore,
         tr cTags = So ( tr cTagUt l.bu ld tr cTags(cand date)),
        latestS ceS gnalT  stamp nM ll s =
          S gnalT  stampStatsUt l.bu ldLatestS ceS gnalT  stamp(cand date)
      )
    }
    s gnalT  stampStatsUt l.statsS gnalT  stamp(t ets)
    CrM xerT etResponse(t ets)
  }

  pr vate def scr beT etScoreFunnelSer es(
    cand dates: Seq[RankedCand date]
  ): Seq[RankedCand date] = {
    // 202210210901  s a random number for code search of Lensv ew
    t etScoreFunnelSer es.startNewSpan(
      na  = "GetT etRecom ndat onsTopLevelT etS m lar yEng neType",
      codePtr = 202210210901L) {
      (
        cand dates,
        cand dates.map { cand date =>
          thr ftlog.T etD  ns on asure(
            d  ns on = So (
              thr ftlog
                .RequestT etD  ns on(
                  cand date.t et d,
                  cand date.reasonChosen.s m lar yEng ne nfo.s m lar yEng neType.value)),
             asure = So (thr ftlog.RequestT et asure(cand date.pred ct onScore))
          )
        }
      )
    }
  }

  pr vate def bu ldRelatedT etResponse(cand dates: Seq[ n  alCand date]): RelatedT etResponse = {
    val t ets = cand dates.map { cand date =>
      RelatedT et(
        t et d = cand date.t et d,
        score = So (cand date.getS m lar yScore),
        author d = So (cand date.t et nfo.author d)
      )
    }
    RelatedT etResponse(t ets)
  }

  pr vate def bu ldRelatedV deoT etResponse(
    cand dates: Seq[ n  alCand date]
  ): RelatedV deoT etResponse = {
    val t ets = cand dates.map { cand date =>
      RelatedV deoT et(
        t et d = cand date.t et d,
        score = So (cand date.getS m lar yScore)
      )
    }
    RelatedV deoT etResponse(t ets)
  }

  pr vate def bu ldUtegT etResponse(
    cand dates: Seq[T etW hScoreAndSoc alProof]
  ): UtegT etResponse = {
    val t ets = cand dates.map { cand date =>
      UtegT et(
        t et d = cand date.t et d,
        score = cand date.score,
        soc alProofByType = cand date.soc alProofByType
      )
    }
    UtegT etResponse(t ets)
  }

  pr vate def bu ldAdsResponse(
    cand dates: Seq[RankedAdsCand date]
  ): AdsResponse = {
    AdsResponse(ads = cand dates.map { cand date =>
      AdT etRecom ndat on(
        t et d = cand date.t et d,
        score = cand date.pred ct onScore,
        l ne ems = So (cand date.l ne em nfo))
    })
  }

  pr vate def cac T etRecom ndat onResults(
    request: CrM xerT etRequest,
    response: Future[CrM xerT etResponse]
  ): Un  = {

    val user d = request.cl entContext.user d.getOrElse(
      throw new  llegalArgu ntExcept on(
        "user d must be present  n getT etRecom ndat ons() Thr ft cl entContext"))

     f (dec der. sAva lableFor d(user d, Dec derConstants.getT etRecom ndat onsCac Rate)) {
      response.map { crM xerT etResponse =>
        {
          (
            request.product,
            request.cl entContext.user d,
            crM xerT etResponse.t ets.nonEmpty) match {
            case (t.Product.Ho , So (user d), true) =>
              t etRecom ndat onResultsStore.put((user d, crM xerT etResponse))
            case _ => Future.value(Un )
          }
        }
      }
    }
  }
}
