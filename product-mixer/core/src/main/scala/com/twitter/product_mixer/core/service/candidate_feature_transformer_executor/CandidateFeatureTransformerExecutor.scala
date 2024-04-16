package com.tw ter.product_m xer.core.serv ce.cand date_feature_transfor r_executor

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.core.serv ce.Executor._
 mport com.tw ter.st ch.Arrow
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Cand dateFeatureTransfor rExecutor @ nject() (overr de val statsRece ver: StatsRece ver)
    extends Executor {
  def arrow[Result](
    transfor rs: Seq[Cand dateFeatureTransfor r[Result]],
    context: Executor.Context
  ): Arrow[Seq[Result], Cand dateFeatureTransfor rExecutorResult] = {
     f (transfor rs. sEmpty) {
      // must always return a Seq of FeatureMaps, even  f t re are no Transfor rs
      Arrow.map[Seq[Result], Cand dateFeatureTransfor rExecutorResult] { cand dates =>
        Cand dateFeatureTransfor rExecutorResult(cand dates.map(_ => FeatureMap.empty), Seq.empty)
      }
    } else {
      val transfor rArrows: Seq[Arrow[Seq[Result], Seq[(Transfor r dent f er, FeatureMap)]]] =
        transfor rs.map { transfor r =>
          val transfor rContext = context.pushToComponentStack(transfor r. dent f er)

          val l ftNonVal dat onFa luresToFa ledFeatures =
            Arrow.handle[FeatureMap, FeatureMap] {
              case NotAM sconf guredFeatureMapFa lure(e) =>
                featureMapW hFa luresForFeatures(transfor r.features, e, transfor rContext)
            }

          val underly ngArrow = Arrow
            .map(transfor r.transform)
            .map(val dateFeatureMap(transfor r.features, _, transfor rContext))

          val observedArrowW houtTrac ng =
            wrapPerCand dateComponentW hExecutorBookkeep ngW houtTrac ng(
              context,
              transfor r. dent f er)(underly ngArrow)

          val seqArrow =
            Arrow.sequence(
              observedArrowW houtTrac ng
                .andT n(l ftNonVal dat onFa luresToFa ledFeatures)
                .map(transfor r. dent f er -> _)
            )

          wrapComponentsW hTrac ngOnly(context, transfor r. dent f er)(seqArrow)
        }

      Arrow.collect(transfor rArrows).map { results =>
        /**
         *  nner Seqs are a g ven Transfor r appl ed to all t  cand dates
         *
         *   want to  rge t  FeatureMaps for each cand date
         * from all t  Transfor rs.   do t  by  rg ng all t  FeatureMaps at
         * each  ndex ` ` of each Seq  n `results` by `transpose`- ng t  `results`
         * so t   nner Seq beco s all t  FeatureMaps for Cand date
         * at  ndex ` `  n t   nput Seq.
         *
         * {{{
         *  Seq(
         *    Seq(transfor r1FeatureMapCand date1, ..., transfor r1FeatureMapCand dateN),
         *    ...,
         *    Seq(transfor rMFeatureMapCand date1, ..., transfor rMFeatureMapCand dateN)
         *  ).transpose == Seq(
         *    Seq(transfor r1FeatureMapCand date1, ..., transfor rMFeatureMapCand date1),
         *    ...,
         *    Seq(transfor r1FeatureMapCand dateN, ..., transfor rMFeatureMapCand dateN)
         *  )
         * }}}
         *
         *   could avo d t  transpose  f   ran each cand date through all t  transfor rs
         * one-after-t -ot r, but t n   couldn't have a s ngle trac ng span for all appl cat ons
         * of a Transfor r, so  nstead   apply each transfor r to all cand dates toget r, t n
         * move onto t  next transfor r.
         *
         *  's worth not ng that t  outer Seq  s bounded by t  number of Transfor rs that are
         * appl ed wh ch w ll typ cally be small.
         */
        val transposed = results.transpose
        val comb nedMaps = transposed.map(featureMapsForS ngleCand date =>
          FeatureMap. rge(featureMapsForS ngleCand date.map { case (_, maps) => maps }))

        Cand dateFeatureTransfor rExecutorResult(comb nedMaps, transposed.map(_.toMap))
      }
    }
  }
}
