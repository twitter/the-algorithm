package com.tw ter.t  l neranker.common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.core.HydratedT ets
 mport com.tw ter.t  l neranker.ut l.T etF lters
 mport com.tw ter.t  l neranker.ut l.T etsPostF lter
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.ut l.Future

object HydratedT etsF lterTransform {
  val EmptyFollowGraphDataTuple: (Seq[User d], Seq[User d], Set[User d]) =
    (Seq.empty[User d], Seq.empty[User d], Set.empty[User d])
  val DefaultNumRet etsAllo d = 1

  // Number of dupl cate ret ets ( nclud ng t  f rst one) allo d.
  // For example,
  //  f t re are 7 ret ets of a g ven t et, t  follow ng value w ll cause 5 of t m
  // to be returned after f lter ng and t  add  onal 2 w ll be f ltered out.
  val NumDupl cateRet etsAllo d = 5
}

/**
 * Transform wh ch takes T etF lters ValueSets for  nner and outer t ets and uses
 * T etsPostF lter to f lter down t  HydratedT ets us ng t  suppl ed f lters
 *
 * @param useFollowGraphData - use follow graph for f lter ng; ot rw se only does f lter ng
 *                            ndependent of follow graph data
 * @param useS ceT ets - only needed w n f lter ng extended repl es
 * @param statsRece ver - scoped stats rece ver
 */
class HydratedT etsF lterTransform(
  outerF lters: T etF lters.ValueSet,
   nnerF lters: T etF lters.ValueSet,
  useFollowGraphData: Boolean,
  useS ceT ets: Boolean,
  statsRece ver: StatsRece ver,
  numRet etsAllo d:  nt = HydratedT etsF lterTransform.DefaultNumRet etsAllo d)
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {
   mport HydratedT etsF lterTransform._

  val logger: Logger = Logger.get(getClass.getS mpleNa )

  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
     f (outerF lters == T etF lters.None) {
      Future.value(envelope)
    } else {
      val t etsPostOuterF lter = new T etsPostF lter(outerF lters, logger, statsRece ver)
      val t etsPost nnerF lter = new T etsPostF lter( nnerF lters, logger, statsRece ver)

      val graphData =  f (useFollowGraphData) {
        Future.jo n(
          envelope.followGraphData.follo dUser dsFuture,
          envelope.followGraphData. nNetworkUser dsFuture,
          envelope.followGraphData.mutedUser dsFuture
        )
      } else {
        Future.value(EmptyFollowGraphDataTuple)
      }

      val s ceT ets =  f (useS ceT ets) {
        envelope.s ceHydratedT ets.outerT ets
      } else {
        N l
      }

      graphData.map {
        case (follo dUser ds,  nNetworkUser ds, mutedUser ds) =>
          val outerT ets = t etsPostOuterF lter(
            user d = envelope.query.user d,
            follo dUser ds = follo dUser ds,
             nNetworkUser ds =  nNetworkUser ds,
            mutedUser ds = mutedUser ds,
            t ets = envelope.hydratedT ets.outerT ets,
            numRet etsAllo d = numRet etsAllo d,
            s ceT ets = s ceT ets
          )
          val  nnerT ets = t etsPost nnerF lter(
            user d = envelope.query.user d,
            follo dUser ds = follo dUser ds,
             nNetworkUser ds =  nNetworkUser ds,
            mutedUser ds = mutedUser ds,
            //  nner t ets refers to quoted t ets not s ce t ets, and spec al rulesets
            //  n b rd rd handle v s b l y of v e r to  nner t et author for t se t ets.
            t ets = envelope.hydratedT ets. nnerT ets,
            numRet etsAllo d = numRet etsAllo d,
            s ceT ets = s ceT ets
          )

          envelope.copy(hydratedT ets = HydratedT ets(outerT ets,  nnerT ets))
      }
    }
  }
}
