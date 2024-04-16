package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ForwardEma lBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ForwardPhoneBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ReverseEma lBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.addressbook.ReversePhoneBookS ce
 mport com.tw ter.follow_recom ndat ons.common.cl ents.real_t  _real_graph.RealT  RealGraphCl ent
 mport com.tw ter.follow_recom ndat ons.common.models.HasRecentFollo dUser ds
 mport com.tw ter.follow_recom ndat ons.common.models.Potent alF rstDegreeEdge
 mport com.tw ter.follow_recom ndat ons.common.stores.LowT epCredFollowStore
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter. rm .model.Algor hm.Algor hm
 mport com.tw ter. nject.Logg ng
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  r
 mport com.tw ter.wtf.scald ng.jobs.strong_t e_pred ct on.F rstDegreeEdge
 mport com.tw ter.wtf.scald ng.jobs.strong_t e_pred ct on.F rstDegreeEdge nfo
 mport com.tw ter.wtf.scald ng.jobs.strong_t e_pred ct on.F rstDegreeEdge nfoMono d
 mport javax. nject. nject
 mport javax. nject.S ngleton

// Grabs F rstDegreeNodes from Cand date S ces
@S ngleton
class STPF rstDegreeFetc r @ nject() (
  realT  GraphCl ent: RealT  RealGraphCl ent,
  reversePhoneBookS ce: ReversePhoneBookS ce,
  reverseEma lBookS ce: ReverseEma lBookS ce,
  forwardEma lBookS ce: ForwardEma lBookS ce,
  forwardPhoneBookS ce: ForwardPhoneBookS ce,
  mutualFollowStrongT ePred ct onS ce: MutualFollowStrongT ePred ct onS ce,
  lowT epCredFollowStore: LowT epCredFollowStore,
  t  r: T  r,
  statsRece ver: StatsRece ver)
    extends Logg ng {

  pr vate val stats = statsRece ver.scope("STPF rstDegreeFetc r")
  pr vate val st chRequests = stats.scope("st chRequests")
  pr vate val allSt chRequests = st chRequests.counter("all")
  pr vate val t  outSt chRequests = st chRequests.counter("t  out")
  pr vate val successSt chRequests = st chRequests.counter("success")

  pr vate  mpl c  val f rstDegreeEdge nfoMono d: F rstDegreeEdge nfoMono d =
    new F rstDegreeEdge nfoMono d

  /**
   * Used to map from algor hm to t  correct fetc r and f rstDegreeEdge nfo.
   * Afterward, uses fetc r to get cand dates and construct t  correct F rstDegreeEdge nfo.
   * */
  pr vate def getPotent alF rstEdgesFromFetc r(
    user d: Long,
    target: HasCl entContext w h HasParams w h HasRecentFollo dUser ds,
    algor hm: Algor hm,
      ght: Double
  ): St ch[Seq[Potent alF rstDegreeEdge]] = {
    val (cand dates, edge nfo) = algor hm match {
      case Algor hm.MutualFollowSTP =>
        (
          mutualFollowStrongT ePred ct onS ce(target),
          So (F rstDegreeEdge nfo(mutualFollow = true)))
      case Algor hm.ReverseEma lBook b s =>
        (reverseEma lBookS ce(target), So (F rstDegreeEdge nfo(reverseEma l = true)))
      case Algor hm.ReversePhoneBook =>
        (reversePhoneBookS ce(target), So (F rstDegreeEdge nfo(reversePhone = true)))
      case Algor hm.ForwardEma lBook =>
        (forwardEma lBookS ce(target), So (F rstDegreeEdge nfo(forwardEma l = true)))
      case Algor hm.ForwardPhoneBook =>
        (forwardPhoneBookS ce(target), So (F rstDegreeEdge nfo(forwardPhone = true)))
      case Algor hm.LowT epcredFollow =>
        (
          lowT epCredFollowStore.getLowT epCredUsers(target),
          So (F rstDegreeEdge nfo(lowT epcredFollow = true)))
      case _ =>
        (St ch.N l, None)
    }
    cand dates.map(_.flatMap { cand date =>
      edge nfo.map(Potent alF rstDegreeEdge(user d, cand date. d, algor hm,   ght, _))
    })
  }

  /**
   * Us ng t  DefaultMap (Algor hmToScore)    erate through algor hm/  ghts to get
   * cand dates w h a set   ght. T n, g ven repeat ng cand dates (by cand date  d).
   * G ven those cand dates   group by t  cand date d and sum all below   ghts and comb ne
   * t  edge nfos of  nto one. T n   choose t  cand dates w h most   ght. F nally,
   *   attach t  realGraph  ght score to those cand dates.
   * */
  def getF rstDegreeEdges(
    target: HasCl entContext w h HasParams w h HasRecentFollo dUser ds
  ): St ch[Seq[F rstDegreeEdge]] = {
    target.getOpt onalUser d
      .map { user d =>
        allSt chRequests. ncr()
        val f rstEdgesQuerySt ch = St ch
          .collect(STPF rstDegreeFetc r.DefaultGraphBu lderAlgor hmToScore.map {
            case (algor hm, cand date  ght) =>
              getPotent alF rstEdgesFromFetc r(user d, target, algor hm, cand date  ght)
          }.toSeq)
          .map(_.flatten)

        val dest nat on dsToEdges = f rstEdgesQuerySt ch
          .map(_.groupBy(_.connect ng d).map {
            case (dest nat on d: Long, edges: Seq[Potent alF rstDegreeEdge]) =>
              val comb nedDestScore = edges.map(_.score).sum
              val comb nedEdge nfo: F rstDegreeEdge nfo =
                edges.map(_.edge nfo).fold(f rstDegreeEdge nfoMono d.zero) {
                  (aggregated nfo, current nfo) =>
                    f rstDegreeEdge nfoMono d.plus(aggregated nfo, current nfo)
                }
              (dest nat on d, comb nedEdge nfo, comb nedDestScore)
          }).map(_.toSeq)

        val topDest nat onEdges = dest nat on dsToEdges.map(_.sortBy {
          case (_, _, comb nedDestScore) => -comb nedDestScore
        }.take(STPF rstDegreeFetc r.MaxNumF rstDegreeEdges))

        St ch
          .jo n(realT  GraphCl ent.getRealGraph  ghts(user d), topDest nat onEdges).map {
            case (realGraph  ghts, topDest nat onEdges) =>
              successSt chRequests. ncr()
              topDest nat onEdges.map {
                case (dest nat on d, comb nedEdge nfo, _) =>
                  val updatedEdge nfo = comb nedEdge nfo.copy(
                    realGraph  ght = realGraph  ghts.getOrElse(dest nat on d, 0.0),
                    lowT epcredFollow =
                      !comb nedEdge nfo.mutualFollow && comb nedEdge nfo.lowT epcredFollow
                  )
                  F rstDegreeEdge(user d, dest nat on d, updatedEdge nfo)
              }
          }.w h n(STPF rstDegreeFetc r.LongT  outFetc r)(t  r).rescue {
            case ex =>
              t  outSt chRequests. ncr()
              logger.error("Except on wh le load ng d rect edges  n Onl neSTP: ", ex)
              St ch.N l
          }
      }.getOrElse(St ch.N l)
  }
}

object STPF rstDegreeFetc r {
  val MaxNumF rstDegreeEdges = 200
  val DefaultGraphBu lderAlgor hmToScore = Map(
    Algor hm.MutualFollowSTP -> 10.0,
    Algor hm.LowT epcredFollow -> 6.0,
    Algor hm.ForwardEma lBook -> 7.0,
    Algor hm.ForwardPhoneBook -> 9.0,
    Algor hm.ReverseEma lBook b s -> 5.0,
    Algor hm.ReversePhoneBook -> 8.0
  )
  val LongT  outFetc r: Durat on = 300.m ll s
}
