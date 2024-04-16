package com.tw ter.follow_recom ndat ons.common.base

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Recom ndat onP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.recom ndat on.Recom ndat onP pel neResult
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorObserver
 mport com.tw ter.st ch.St ch

/**
 * conf gs for results generated from t  recom ndat on flow
 *
 * @param des redCand dateCount num of des red cand dates to return
 * @param batchForCand datesC ck batch s ze for cand dates c ck
 */
case class Recom ndat onResultsConf g(des redCand dateCount:  nt, batchForCand datesC ck:  nt)

tra  BaseRecom ndat onFlow[Target, Cand date <: Un versalNoun[Long]] {
  val  dent f er = Recom ndat onP pel ne dent f er("Recom ndat onFlow")

  def process(
    p pel neRequest: Target
  ): St ch[Recom ndat onP pel neResult[Cand date, Seq[Cand date]]]

  def mapKey[Target2](fn: Target2 => Target): BaseRecom ndat onFlow[Target2, Cand date] = {
    val or g nal = t 
    new BaseRecom ndat onFlow[Target2, Cand date] {
      overr de def process(
        p pel neRequest: Target2
      ): St ch[Recom ndat onP pel neResult[Cand date, Seq[Cand date]]] =
        or g nal.process(fn(p pel neRequest))
    }
  }
}

/**
 * Def nes a typ cal recom ndat on flow to fetch, f lter, rank and transform cand dates.
 *
 * 1. targetEl g b l y: determ ne t  el g b l y of target request
 * 2. cand dateS ces: fetch cand dates from cand date s ces based on target type
 * 3. preRankerCand dateF lter: l ght f lter ng of cand dates
 * 4. ranker: rank ng of cand dates (could be composed of mult ple stages, l ght rank ng,  avy rank ng and etc)
 * 5. postRankerTransform: dedup ng, group ng, rule based promot on / demot ons and etc
 * 6. val dateCand dates:  avy f lters to determ ne t  el g b l y of t  cand dates.
 *    w ll only be appl ed to cand dates that   expect to return.
 * 7. transformResults: transform t   nd v dual cand dates  nto des red format (e.g. hydrate soc al proof)
 *
 * Note that t  actual  mple ntat ons may not need to  mple nt all t  steps  f not needed
 * (could just leave to  dent yRanker  f rank ng  s not needed).
 *
 * T oret cally, t  actual  mple ntat on could overr de t  above flow to add
 * more steps (e.g. add a transform step before rank ng).
 * But    s recom nded to add t  add  onal steps  nto t  base flow  f t  step proves
 * to have s gn f cant just f cat on, or  rge    nto an ex st ng step  f    s a m nor change.
 *
 * @tparam Target type of target request
 * @tparam Cand date type of cand date to return
 */
tra  Recom ndat onFlow[Target, Cand date <: Un versalNoun[Long]]
    extends BaseRecom ndat onFlow[Target, Cand date]
    w h S deEffectsUt l[Target, Cand date] {

  /**
   * opt onally update or enr ch t  request before execut ng t  flows
   */
  protected def updateTarget(target: Target): St ch[Target] = St ch.value(target)

  /**
   *  c ck  f t  target  s el g ble for t  flow
   */
  protected def targetEl g b l y: Pred cate[Target]

  /**
   *  def ne t  cand date s ces that should be used for t  g ven target
   */
  protected def cand dateS ces(target: Target): Seq[Cand dateS ce[Target, Cand date]]

  /**
   *  f lter  nval d cand dates before t  rank ng phase.
   */
  protected def preRankerCand dateF lter: Pred cate[(Target, Cand date)]

  /**
   * rank t  cand dates
   */
  protected def selectRanker(target: Target): Ranker[Target, Cand date]

  /**
   * transform t  cand dates after rank ng (e.g. dedupp ng, group ng and etc)
   */
  protected def postRankerTransform: Transform[Target, Cand date]

  /**
   *  f lter  nval d cand dates before return ng t  results.
   *
   *  So   avy f lters e.g. SGS f lter could be appl ed  n t  step
   */
  protected def val dateCand dates: Pred cate[(Target, Cand date)]

  /**
   * transform t  cand dates  nto results and return
   */
  protected def transformResults: Transform[Target, Cand date]

  /**
   *  conf gurat on for recom ndat on results
   */
  protected def resultsConf g(target: Target): Recom ndat onResultsConf g

  /**
   * track t  qual y factor t  recom ndat on p pel ne
   */
  protected def qual yFactorObserver: Opt on[Qual yFactorObserver] = None

  def statsRece ver: StatsRece ver

  /**
   * h gh level mon or ng for t  whole flow
   * (make sure to add mon or ng for each  nd v dual component by y self)
   *
   * add  onal cand dates: count, stats, non_empty_count
   * target el g b l y: latency, success, fa lures, request, count, val d_count,  nval d_count,  nval d_reasons
   * cand date generat on: latency, success, fa lures, request, count, non_empty_count, results_stat
   * pre ranker f lter: latency, success, fa lures, request, count, non_empty_count, results_stat
   * ranker: latency, success, fa lures, request, count, non_empty_count, results_stat
   * post ranker: latency, success, fa lures, request, count, non_empty_count, results_stat
   * f lter and take: latency, success, fa lures, request, count, non_empty_count, results_stat, batch count
   * transform results: latency, success, fa lures, request, count, non_empty_count, results_stat
   */
   mport Recom ndat onFlow._
  lazy val add  onalCand datesStats = statsRece ver.scope(Add  onalCand datesStats)
  lazy val targetEl g b l yStats = statsRece ver.scope(TargetEl g b l yStats)
  lazy val cand dateGenerat onStats = statsRece ver.scope(Cand dateGenerat onStats)
  lazy val preRankerF lterStats = statsRece ver.scope(PreRankerF lterStats)
  lazy val rankerStats = statsRece ver.scope(RankerStats)
  lazy val postRankerTransformStats = statsRece ver.scope(PostRankerTransformStats)
  lazy val f lterAndTakeStats = statsRece ver.scope(F lterAndTakeStats)
  lazy val transformResultsStats = statsRece ver.scope(TransformResultsStats)

  lazy val overallStats = statsRece ver.scope(OverallStats)

   mport StatsUt l._

  overr de def process(
    p pel neRequest: Target
  ): St ch[Recom ndat onP pel neResult[Cand date, Seq[Cand date]]] = {

    observeSt chQual yFactor(
      prof leSt chSeqResults(
        updateTarget(p pel neRequest).flatMap { target =>
          prof lePred cateResult(targetEl g b l y(target), targetEl g b l yStats).flatMap {
            case Pred cateResult.Val d => processVal dTarget(target, Seq.empty)
            case Pred cateResult. nval d(_) => St ch.N l
          }
        },
        overallStats
      ).map { cand dates =>
        Recom ndat onP pel neResult.empty.w hResult(cand dates)
      },
      qual yFactorObserver,
      overallStats
    )
  }

  protected def processVal dTarget(
    target: Target,
    add  onalCand dates: Seq[Cand date]
  ): St ch[Seq[Cand date]] = {

    /**
     * A bas c recom ndat on flow looks l ke t :
     *
     * 1. fetch cand dates from cand date s ces
     * 2. blend cand dates w h ex st ng cand dates
     * 3. f lter t  cand dates (l ght f lters) before rank ng
     * 4. rank ng
     * 5. f lter and truncate t  cand dates us ng postRankerCand dateF lter
     * 6. transform t  cand dates based on product requ re nt
     */
    val cand dateS cesToFetch = cand dateS ces(target)
    for {
      cand dates <- prof leSt chSeqResults(
        St ch.traverse(cand dateS cesToFetch)(_(target)).map(_.flatten),
        cand dateGenerat onStats
      )
       rgedCand dates =
        prof leSeqResults(add  onalCand dates, add  onalCand datesStats) ++
          cand dates
      f lteredCand dates <- prof leSt chSeqResults(
        Pred cate.f lter(target,  rgedCand dates, preRankerCand dateF lter),
        preRankerF lterStats
      )
      rankedCand dates <- prof leSt chSeqResults(
        selectRanker(target).rank(target, f lteredCand dates),
        rankerStats
      )
      transfor d <- prof leSt chSeqResults(
        postRankerTransform.transform(target, rankedCand dates),
        postRankerTransformStats
      )
      truncated <- prof leSt chSeqResults(
        take(target, transfor d, resultsConf g(target)),
        f lterAndTakeStats
      )
      results <- prof leSt chSeqResults(
        transformResults.transform(target, truncated),
        transformResultsStats
      )
      _ <- applyS deEffects(
        target,
        cand dateS cesToFetch,
        cand dates,
         rgedCand dates,
        f lteredCand dates,
        rankedCand dates,
        transfor d,
        truncated,
        results)
    } y eld results
  }

  pr vate[t ] def take(
    target: Target,
    cand dates: Seq[Cand date],
    conf g: Recom ndat onResultsConf g
  ): St ch[Seq[Cand date]] = {
    Pred cate
      .batchF lterTake(
        cand dates.map(c => (target, c)),
        val dateCand dates,
        conf g.batchForCand datesC ck,
        conf g.des redCand dateCount,
        statsRece ver
      ).map(_.map(_._2))
  }
}

object Recom ndat onFlow {

  val Add  onalCand datesStats = "add  onal_cand dates"
  val TargetEl g b l yStats = "target_el g b l y"
  val Cand dateGenerat onStats = "cand date_generat on"
  val PreRankerF lterStats = "pre_ranker_f lter"
  val RankerStats = "ranker"
  val PostRankerTransformStats = "post_ranker_transform"
  val F lterAndTakeStats = "f lter_and_take"
  val TransformResultsStats = "transform_results"
  val OverallStats = "overall"
}
