package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.tw ter.follow_recom ndat ons.common.models. nter d ateSecondDegreeEdge
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.onboard ng.userrecs.StrongT ePred ct onFeaturesOnUserCl entColumn
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.wtf.scald ng.jobs.strong_t e_pred ct on.F rstDegreeEdge
 mport com.tw ter.wtf.scald ng.jobs.strong_t e_pred ct on.SecondDegreeEdge
 mport com.tw ter.wtf.scald ng.jobs.strong_t e_pred ct on.SecondDegreeEdge nfo
 mport javax. nject. nject
 mport javax. nject.S ngleton

// L nk to code funct onal y  're m grat ng
@S ngleton
class STPSecondDegreeFetc r @ nject() (
  strongT ePred ct onFeaturesOnUserCl entColumn: StrongT ePred ct onFeaturesOnUserCl entColumn) {

  pr vate def scoreSecondDegreeEdge(edge: SecondDegreeEdge): ( nt,  nt,  nt) = {
    def bool2 nt(b: Boolean):  nt =  f (b) 1 else 0
    (
      -edge.edge nfo.numMutualFollowPath,
      -edge.edge nfo.numLowT epcredFollowPath,
      -(bool2 nt(edge.edge nfo.forwardEma lPath) + bool2 nt(edge.edge nfo.reverseEma lPath) +
        bool2 nt(edge.edge nfo.forwardPhonePath) + bool2 nt(edge.edge nfo.reversePhonePath))
    )
  }

  // Use each f rst-degree edge(w/ cand date d) to expand and f nd mutual follows.
  // T n, w h t  mutual follows, group-by cand date d and jo n edge  nformat on
  // to create secondDegree edges.
  def getSecondDegreeEdges(
    target: HasCl entContext w h HasParams,
    f rstDegreeEdges: Seq[F rstDegreeEdge]
  ): St ch[Seq[SecondDegreeEdge]] = {
    target.getOpt onalUser d
      .map { user d =>
        val f rstDegreeConnect ng ds = f rstDegreeEdges.map(_.dst d)
        val f rstDegreeEdge nfoMap = f rstDegreeEdges.map(e => (e.dst d, e.edge nfo)).toMap

        val  nter d ateSecondDegreeEdgesSt ch = St ch
          .traverse(f rstDegreeConnect ng ds) { connect ng d =>
            val stpFeaturesOptSt ch = strongT ePred ct onFeaturesOnUserCl entColumn.fetc r
              .fetch(connect ng d)
              .map(_.v)
            stpFeaturesOptSt ch.map { stpFeatureOpt =>
              val  nter d ateSecondDegreeEdges = for {
                edge nfo <- f rstDegreeEdge nfoMap.get(connect ng d)
                stpFeatures <- stpFeatureOpt
                topSecondDegreeUser ds =
                  stpFeatures.topMutualFollows
                    .getOrElse(N l)
                    .map(_.user d)
                    .take(STPSecondDegreeFetc r.MaxNumOfMutualFollows)
              } y eld topSecondDegreeUser ds.map(
                 nter d ateSecondDegreeEdge(connect ng d, _, edge nfo))
               nter d ateSecondDegreeEdges.getOrElse(N l)
            }
          }.map(_.flatten)

         nter d ateSecondDegreeEdgesSt ch.map {  nter d ateSecondDegreeEdges =>
          val secondaryDegreeEdges =  nter d ateSecondDegreeEdges.groupBy(_.cand date d).map {
            case (cand date d,  nter d ateEdges) =>
              SecondDegreeEdge(
                src d = user d,
                dst d = cand date d,
                edge nfo = SecondDegreeEdge nfo(
                  numMutualFollowPath =  nter d ateEdges.count(_.edge nfo.mutualFollow),
                  numLowT epcredFollowPath =
                     nter d ateEdges.count(_.edge nfo.lowT epcredFollow),
                  forwardEma lPath =  nter d ateEdges.ex sts(_.edge nfo.forwardEma l),
                  reverseEma lPath =  nter d ateEdges.ex sts(_.edge nfo.reverseEma l),
                  forwardPhonePath =  nter d ateEdges.ex sts(_.edge nfo.forwardPhone),
                  reversePhonePath =  nter d ateEdges.ex sts(_.edge nfo.reversePhone),
                  soc alProof =  nter d ateEdges
                    .f lter { e => e.edge nfo.mutualFollow || e.edge nfo.lowT epcredFollow }
                    .sortBy(-_.edge nfo.realGraph  ght)
                    .take(3)
                    .map { c => (c.connect ng d, c.edge nfo.realGraph  ght) }
                )
              )
          }
          secondaryDegreeEdges.toSeq
            .sortBy(scoreSecondDegreeEdge)
            .take(STPSecondDegreeFetc r.MaxNumSecondDegreeEdges)
        }
      }.getOrElse(St ch.N l)
  }
}

object STPSecondDegreeFetc r {
  val MaxNumSecondDegreeEdges = 200
  val MaxNumOfMutualFollows = 50
}
