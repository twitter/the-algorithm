package com.tw ter.follow_recom ndat ons.common.cand date_s ces.base

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.RealGraphExpans onRepos ory.DefaultScore
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.RealGraphExpans onRepos ory.MaxNum nter d ateNodesToKeep
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.RealGraphExpans onRepos ory.F rstDegreeCand datesT  out
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models._
 mport com.tw ter.onboard ng.relevance.features.ymb  .Expans onCand dateScores
 mport com.tw ter.onboard ng.relevance.features.ymb  .RawYMB  Cand dateFeatures
 mport com.tw ter.onboard ng.relevance.store.thr ftscala.Cand datesFollo dV1
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.ut l.Durat on
 mport scala.collect on. mmutable
 mport scala.ut l.control.NonFatal

pr vate f nal case class  nterestExpans onCand date(
  user D: Long,
  score: Double,
  features: RawYMB  Cand dateFeatures)

abstract class RealGraphExpans onRepos ory[Request](
  realgraphExpans onStore: Fetc r[
    Long,
    Un ,
    Cand datesFollo dV1
  ],
  overr de val  dent f er: Cand dateS ce dent f er,
  statsRece ver: StatsRece ver = NullStatsRece ver,
  maxUnderly ngCand datesToQuery:  nt = 50,
  maxCand datesToReturn:  nt = 40,
  overr deUnderly ngT  out: Opt on[Durat on] = None,
  appendSoc alProof: Boolean = false)
    extends Cand dateS ce[
      Request,
      Cand dateUser
    ] {

  val underly ngCand dateS ce: Seq[
    Cand dateS ce[
      Request,
      Cand dateUser
    ]
  ]

  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa ).scope( dent f er.na )
  pr vate val underly ngCand dateS ceFa lureStats =
    stats.scope("underly ng_cand date_s ce_fa lure")

  def apply(
    request: Request,
  ): St ch[Seq[Cand dateUser]] = {

    val cand datesFromUnderly ngS cesSt ch: Seq[St ch[Seq[Cand dateUser]]] =
      underly ngCand dateS ce.map { cand dateS ce =>
        cand dateS ce
          .apply(request)
          .w h n(overr deUnderly ngT  out.getOrElse(F rstDegreeCand datesT  out))(
            DefaultT  r
          )
          .handle {
            case NonFatal(e) =>
              underly ngCand dateS ceFa lureStats
                .counter(cand dateS ce. dent f er.na , e.getClass.getS mpleNa ). ncr()
              Seq.empty
          }
      }

    for {
      underly ngCand datesFromEachAlgo <- St ch.collect(cand datesFromUnderly ngS cesSt ch)
      // T  f rst algor hm  n t  l st has t  h g st pr or y. Depend ng on  f  s not
      // populated, fall back to ot r algor hms. Once a part cular algor hm  s chosen, only
      // take t  top few cand dates from t  underly ng store for expans on.
      underly ngCand datesTuple =
        underly ngCand datesFromEachAlgo
          .z p(underly ngCand dateS ce)
          .f nd(_._1.nonEmpty)

      underly ngAlgor hmUsed: Opt on[Cand dateS ce dent f er] = underly ngCand datesTuple.map {
        case (_, cand dateS ce) => cand dateS ce. dent f er
      }

      // Take maxUnderly ngCand datesToQuery to query realgraphExpans onStore
      underly ngCand dates =
        underly ngCand datesTuple
          .map {
            case (cand dates, cand dateS ce) =>
              stats
                .scope("underly ngAlgor hmUsedScope").counter(
                  cand dateS ce. dent f er.na ). ncr()
              cand dates
          }
          .getOrElse(Seq.empty)
          .sortBy(_.score.getOrElse(DefaultScore))(Order ng.Double.reverse)
          .take(maxUnderly ngCand datesToQuery)

      underly ngCand dateMap: Map[Long, Double] = underly ngCand dates.map { cand date =>
        (cand date. d, cand date.score.getOrElse(DefaultScore))
      }.toMap

      expans onCand dates <-
        St ch
          .traverse(underly ngCand dateMap.keySet.toSeq) { cand date d =>
            St ch.jo n(
              St ch.value(cand date d),
              realgraphExpans onStore.fetch(cand date d).map(_.v))

          }.map(_.toMap)

      rerankedCand dates: Seq[ nterestExpans onCand date] =
        rerankCand dateExpans ons(underly ngCand dateMap, expans onCand dates)

      rerankedCand datesF ltered = rerankedCand dates.take(maxCand datesToReturn)

    } y eld {
      rerankedCand datesF ltered.map { cand date =>
        val soc alProofReason =  f (appendSoc alProof) {
          val soc alProof ds = cand date.features.expans onCand dateScores
            .map(_. nter d ateCand date d)
          So (
            Reason(So (
              AccountProof(followProof = So (FollowProof(soc alProof ds, soc alProof ds.s ze))))))
        } else {
          None
        }
        Cand dateUser(
           d = cand date.user D,
          score = So (cand date.score),
          reason = soc alProofReason,
          userCand dateS ceDeta ls = So (
            UserCand dateS ceDeta ls(
              pr maryCand dateS ce = So ( dent f er),
              cand dateS ceFeatures = Map( dent f er -> Seq(cand date.features))
            ))
        ).addAddressBook tadata fAva lable(underly ngAlgor hmUsed.toSeq)
      }
    }
  }

  /**
   * Expands underly ng cand dates, return ng t m  n sorted order.
   *
   * @param underly ngCand datesMap A map from underly ng cand date  d to score
   * @param expans onCand dateMap A map from underly ng cand date  d to opt onal expans on cand dates
   * @return A sorted sequence of expans on cand dates and assoc ated scores
   */
  pr vate def rerankCand dateExpans ons(
    underly ngCand datesMap: Map[Long, Double],
    expans onCand dateMap: Map[Long, Opt on[Cand datesFollo dV1]]
  ): Seq[ nterestExpans onCand date] = {

    // extract features
    val cand dates: Seq[(Long, Expans onCand dateScores)] = for {
      (underly ngCand date d, underly ngCand dateScore) <- underly ngCand datesMap.toSeq
      expans onCand dates =
        expans onCand dateMap
          .get(underly ngCand date d)
          .flatten
          .map(_.cand datesFollo d)
          .getOrElse(Seq.empty)
      expans onCand date <- expans onCand dates
    } y eld expans onCand date.cand date D -> Expans onCand dateScores(
      underly ngCand date d,
      So (underly ngCand dateScore),
      So (expans onCand date.score)
    )

    //  rge  nter d ate nodes for t  sa  cand date
    val dedupedCand dates: Seq[(Long, Seq[Expans onCand dateScores])] =
      cand dates.groupBy(_._1).mapValues(_.map(_._2).sortBy(_. nter d ateCand date d)).toSeq

    // score t  cand date
    val cand datesW hTotalScore: Seq[((Long, Seq[Expans onCand dateScores]), Double)] =
      dedupedCand dates.map { cand date: (Long, Seq[Expans onCand dateScores]) =>
        (
          cand date,
          cand date._2.map {  eScore: Expans onCand dateScores =>
             eScore.scoreFromUserTo nter d ateCand date.getOrElse(DefaultScore) *
               eScore.scoreFrom nter d ateToExpans onCand date.getOrElse(DefaultScore)
          }.sum)
      }

    // sort cand date by score
    for {
      ((cand date, edges), score) <- cand datesW hTotalScore.sortBy(_._2)(Order ng[Double].reverse)
    } y eld  nterestExpans onCand date(
      cand date,
      score,
      RawYMB  Cand dateFeatures(
        edges.s ze,
        edges.take(MaxNum nter d ateNodesToKeep).to[ mmutable.Seq])
    )
  }

}

object RealGraphExpans onRepos ory {
  pr vate val F rstDegreeCand datesT  out: Durat on = 250.m ll seconds
  pr vate val MaxNum nter d ateNodesToKeep = 20
  pr vate val DefaultScore = 0.0d

}
