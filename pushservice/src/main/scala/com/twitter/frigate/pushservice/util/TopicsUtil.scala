package com.tw ter.fr gate.pushserv ce.ut l

 mport com.tw ter.contentrecom nder.thr ftscala.D splayLocat on
 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.fr gate.common.base.TargetUser
 mport com.tw ter.fr gate.common.pred cate.CommonOutNetworkT etCand datesS cePred cates.authorNotBe ngFollo dPred cate
 mport com.tw ter.fr gate.common.store. nterests. nterestsLookupRequestW hContext
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes
 mport com.tw ter.fr gate.pushserv ce.store.UttEnt yHydrat onQuery
 mport com.tw ter.fr gate.pushserv ce.store.UttEnt yHydrat onStore
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter. rm .pred cate.soc algraph.Relat onEdge
 mport com.tw ter. nterests.thr ftscala. nterestRelat onType
 mport com.tw ter. nterests.thr ftscala. nterestRelat onsh p
 mport com.tw ter. nterests.thr ftscala. nterested n nterestLookupContext
 mport com.tw ter. nterests.thr ftscala. nterested n nterestModel
 mport com.tw ter. nterests.thr ftscala.Product d
 mport com.tw ter. nterests.thr ftscala.User nterest
 mport com.tw ter. nterests.thr ftscala.User nterestData
 mport com.tw ter. nterests.thr ftscala.User nterests
 mport com.tw ter. nterests.thr ftscala.{Top cL st ngV e rContext => Top cL st ngV e rContextCR}
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.top cl st ng.Top cL st ngV e rContext
 mport com.tw ter.top cl st ng.utt.Local zedEnt y
 mport com.tw ter.tsp.thr ftscala.Top cL st ngSett ng
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofRequest
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofResponse
 mport com.tw ter.tsp.thr ftscala.Top cW hScore
 mport com.tw ter.ut l.Future
 mport scala.collect on.Map

case class T etW hTop cProof(
  t et d: Long,
  top c d: Long,
  author d: Opt on[Long],
  score: Double,
  t etyP eResult: T etyP eResult,
  top cL st ngSett ng: Str ng,
  algor hmCR: Opt on[Str ng],
   sOON: Boolean)

object Top csUt l {

  /**
   * Obta ns t  Local zed Ent  es for t  prov ded SC Ent y  Ds
   * @param target                  T  target user for wh ch  're obta n ng cand dates
   * @param semant cCoreEnt y ds   T  seq. of ent y  ds for wh ch   would l ke to obta n t  Local zed Ent  es
   * @param uttEnt yHydrat onStore Store to query t  actual Local zedEnt  es
   * @return                        A Future Map cons st ng of t  ent y  d as t  key and Local zedEnt y as t  value
   */
  def getLocal zedEnt yMap(
    target: Target,
    semant cCoreEnt y ds: Set[Long],
    uttEnt yHydrat onStore: UttEnt yHydrat onStore
  ): Future[Map[Long, Local zedEnt y]] = {
    bu ldTop cL st ngV e rContext(target)
      .flatMap { top cL st ngV e rContext =>
        val query = UttEnt yHydrat onQuery(top cL st ngV e rContext, semant cCoreEnt y ds.toSeq)
        val local zedTop cEnt  esFut =
          uttEnt yHydrat onStore.getLocal zedTop cEnt  es(query).map(_.flatten)
        local zedTop cEnt  esFut.map { local zedTop cEnt  es =>
          local zedTop cEnt  es.map { local zedTop cEnt y =>
            local zedTop cEnt y.ent y d -> local zedTop cEnt y
          }.toMap
        }
      }
  }

  /**
   * Fetch expl ct follo d  nterests  .e Top cs for targetUser
   *
   * @param targetUser: [[Target]] object represent ng a user el g ble for Mag cRecs not f cat on
   * @return: l st of all Top cs( nterests) Follo d by targetUser
   */
  def getTop csFollo dByUser(
    targetUser: Target,
     nterestsW hLookupContextStore: ReadableStore[
       nterestsLookupRequestW hContext,
      User nterests
    ],
    follo dTop csStats: Stat
  ): Future[Opt on[Seq[User nterest]]] = {
    bu ldTop cL st ngV e rContext(targetUser).flatMap { top cL st ngV e rContext =>
      // expl c   nterests relat on query
      val expl c  nterestsLookupRequest =  nterestsLookupRequestW hContext(
        targetUser.target d,
        So (
           nterested n nterestLookupContext(
            expl c Context = None,
             nferredContext = None,
            product d = So (Product d.Followable),
            top cL st ngV e rContext = So (top cL st ngV e rContext.toThr ft),
            d sableExpl c  = None,
            d sable mpl c  = So (true)
          )
        )
      )

      // f lter expl c  follow relat onsh ps from response
       nterestsW hLookupContextStore.get(expl c  nterestsLookupRequest).map {
        _.flatMap { user nterests =>
          val follo dTop cs = user nterests. nterests.map {
            _.f lter {
              case User nterest(_, So ( nterestData)) =>
                 nterestData match {
                  case User nterestData. nterested n( nterested n) =>
                     nterested n.ex sts {
                      case  nterested n nterestModel.Expl c Model(expl c Model) =>
                        expl c Model match {
                          case  nterestRelat onsh p.V1(v1) =>
                            v1.relat on ==  nterestRelat onType.Follo d

                          case _ => false
                        }

                      case _ => false
                    }

                  case _ => false
                }

              case _ => false //  nterestData unava lable
            }
          }
          follo dTop csStats.add(follo dTop cs.getOrElse(Seq.empty[User nterest]).s ze)
          follo dTop cs
        }
      }
    }
  }

  /**
   *
   * @param target : [[Target]] object respresent ng Mag cRecs user
   *
   * @return: [[Top cL st ngV e rContext]] for query ng top cs
   */
  def bu ldTop cL st ngV e rContext(target: Target): Future[Top cL st ngV e rContext] = {
    Future.jo n(target. nferredUserDev ceLanguage, target.countryCode, target.targetUser).map {
      case ( nferredLanguage, countryCode, user nfo) =>
        Top cL st ngV e rContext(
          user d = So (target.target d),
          guest d = None,
          dev ce d = None,
          cl entAppl cat on d = None,
          userAgent = None,
          languageCode =  nferredLanguage,
          countryCode = countryCode,
          userRoles = user nfo.flatMap(_.roles.map(_.roles.toSet))
        )
    }
  }

  /**
   *
   * @param target : [[Target]] object respresent ng Mag cRecs user
   *
   * @return: [[Top cL st ngV e rContext]] for query ng top cs
   */
  def bu ldTop cL st ngV e rContextForCR(target: Target): Future[Top cL st ngV e rContextCR] = {
    Top csUt l.bu ldTop cL st ngV e rContext(target).map(_.toThr ft)
  }

  /**
   *
   * @param target : [[Target]] object respresent ng Mag cRecs user
   * @param t ets : [[Seq[T etyP eResult]]] object represent ng T ets to get TSP for
   * @param top cSoc alProofServ ceStore: [[ReadableStore[Top cSoc alProofRequest, Top cSoc alProofResponse]]]
   * @param edgeStore: [[ReadableStore[Relat onEdge, Boolean]]]]
   *
   * @return: [[Future[Seq[T etW hTop cProof]]]] T ets w h top c proof
   */
  def getTop cSoc alProofs(
     nputTarget: Target,
    t ets: Seq[T etyP eResult],
    top cSoc alProofServ ceStore: ReadableStore[Top cSoc alProofRequest, Top cSoc alProofResponse],
    edgeStore: ReadableStore[Relat onEdge, Boolean],
    scoreThresholdParam: Param[Double]
  ): Future[Seq[T etW hTop cProof]] = {
    bu ldTop cL st ngV e rContextForCR( nputTarget).flatMap { top cL st ngContext =>
      val t et ds: Set[Long] = t ets.map(_.t et. d).toSet
      val t et dsToT etyP e = t ets.map(tp => tp.t et. d -> tp).toMap
      val top cSoc alProofRequest =
        Top cSoc alProofRequest(
           nputTarget.target d,
          t et ds,
          D splayLocat on.Mag cRecsRecom ndTop cT ets,
          Top cL st ngSett ng.Followable,
          top cL st ngContext)

      top cSoc alProofServ ceStore
        .get(top cSoc alProofRequest).flatMap {
          case So (top cSoc alProofResponse) =>
            val top cProofCand dates = top cSoc alProofResponse.soc alProofs.collect {
              case (t et d, top csW hScore)
                   f top csW hScore.nonEmpty && top csW hScore
                    .maxBy(_.score).score >=  nputTarget
                    .params(scoreThresholdParam) =>
                // Get t  top c w h max score  f t re are any top cs returned
                val top cW hScore = top csW hScore.maxBy(_.score)
                T etW hTop cProof(
                  t et d,
                  top cW hScore.top c d,
                  t et dsToT etyP e(t et d).t et.coreData.map(_.user d),
                  top cW hScore.score,
                  t et dsToT etyP e(t et d),
                  top cW hScore.top cFollowType.map(_.na ).getOrElse(""),
                  top cW hScore.algor hmType.map(_.na ),
                   sOON = true
                )
            }.toSeq

            hydrateTop cProofCand datesW hEdgeStore( nputTarget, top cProofCand dates, edgeStore)
          case _ => Future.value(Seq.empty[T etW hTop cProof])
        }
    }
  }

  /**
   * Obta n Top cW hScores for prov ded t et cand dates and target
   * @param target   target user
   * @param T ets   t et cand dates represented  n a (t et d, T etyP eResult) map
   * @param top cSoc alProofServ ceStore store to query top c soc al proof
   * @param enableTop cAnnotat on w t r to enable top c annotat on
   * @param top cScoreThreshold  threshold for top c score
   * @return a (t et d, Top cW hScore) map w re t  top c w h h g st top c score ( f ex sts)  s chosen
   */
  def getTop csW hScoreMap(
    target: PushTypes.Target,
    T ets: Map[Long, Opt on[T etyP eResult]],
    top cSoc alProofServ ceStore: ReadableStore[Top cSoc alProofRequest, Top cSoc alProofResponse],
    enableTop cAnnotat on: Boolean,
    top cScoreThreshold: Double
  ): Future[Opt on[Map[Long, Top cW hScore]]] = {

     f (enableTop cAnnotat on) {
      Top csUt l
        .bu ldTop cL st ngV e rContextForCR(target).flatMap { top cL st ngContext =>
          val t et ds = T ets.keySet
          val top cSoc alProofRequest =
            Top cSoc alProofRequest(
              target.target d,
              t et ds,
              D splayLocat on.Mag cRecsRecom ndTop cT ets,
              Top cL st ngSett ng.Followable,
              top cL st ngContext)

          top cSoc alProofServ ceStore
            .get(top cSoc alProofRequest).map {
              _.map { top cSoc alProofResponse =>
                top cSoc alProofResponse.soc alProofs
                  .collect {
                    case (t et d, top csW hScore)
                         f top csW hScore.nonEmpty && T ets(t et d).nonEmpty
                          && top csW hScore.maxBy(_.score).score >= top cScoreThreshold =>
                      t et d -> top csW hScore.maxBy(_.score)
                  }

              }
            }
        }
    } else {
      Future.None
    }

  }

  /**
   * Obta n Local zedEnt  es for prov ded t et cand dates and target
   * @param target target user
   * @param T ets t et cand dates represented  n a (t et d, T etyP eResult) map
   * @param uttEnt yHydrat onStore store to query t  actual Local zedEnt  es
   * @param top cSoc alProofServ ceStore store to query top c soc al proof
   * @param enableTop cAnnotat on w t r to enable top c annotat on
   * @param top cScoreThreshold threshold for top c score
   * @return a (t et d, Local zedEnt y Opt on) Future map that stores Local zed Ent y (can be empty) for g ven t et d
   */
  def getT et dLocal zedEnt yMap(
    target: PushTypes.Target,
    T ets: Map[Long, Opt on[T etyP eResult]],
    uttEnt yHydrat onStore: UttEnt yHydrat onStore,
    top cSoc alProofServ ceStore: ReadableStore[Top cSoc alProofRequest, Top cSoc alProofResponse],
    enableTop cAnnotat on: Boolean,
    top cScoreThreshold: Double
  ): Future[Map[Long, Opt on[Local zedEnt y]]] = {

    val top cW hScoreMap = getTop csW hScoreMap(
      target,
      T ets,
      top cSoc alProofServ ceStore,
      enableTop cAnnotat on,
      top cScoreThreshold)

    top cW hScoreMap.flatMap { top cW hScores =>
      top cW hScores match {
        case So (top cs) =>
          val top c ds = top cs.collect { case (_, top c) => top c.top c d }.toSet
          val Local zedEnt yMapFut =
            getLocal zedEnt yMap(target, top c ds, uttEnt yHydrat onStore)

          Local zedEnt yMapFut.map { Local zedEnt yMap =>
            top cs.map {
              case (t et d, top c) =>
                t et d -> Local zedEnt yMap.get(top c.top c d)
            }
          }
        case _ => Future.value(Map[Long, Opt on[Local zedEnt y]]())
      }
    }

  }

  /**
   * Hydrate T etW hTop cProof cand dates w h  sOON f eld  nfo,
   * based on t  follow ng relat onsh p bet en target user and cand date author  n edgeStore
   * @return T etW hTop cProof cand dates w h  sOON f eld populated
   */
  def hydrateTop cProofCand datesW hEdgeStore(
     nputTarget: TargetUser,
    top cProofCand dates: Seq[T etW hTop cProof],
    edgeStore: ReadableStore[Relat onEdge, Boolean],
  ): Future[Seq[T etW hTop cProof]] = {
    //  Ds of all authors of Top cProof cand dates that are OON w h respect to  nputTarget
    val val dOONAuthor dsFut =
      Pred cate.f lter(
        top cProofCand dates.flatMap(_.author d).d st nct,
        authorNotBe ngFollo dPred cate( nputTarget, edgeStore))

    val dOONAuthor dsFut.map { val dOONAuthor ds =>
      top cProofCand dates.map(cand date => {
        cand date.copy( sOON =
          cand date.author d. sDef ned && val dOONAuthor ds.conta ns(cand date.author d.get))
      })
    }
  }

}
