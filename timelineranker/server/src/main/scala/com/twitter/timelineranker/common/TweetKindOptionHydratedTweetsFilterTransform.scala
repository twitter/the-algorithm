package com.tw ter.t  l neranker.common

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.t  l neranker.para ters.recap.RecapParams
 mport com.tw ter.t  l neranker.para ters.uteg_l ked_by_t ets.UtegL kedByT etsParams
 mport com.tw ter.t  l neranker.ut l.T etF lters
 mport com.tw ter.t  l nes.common.model.T etK ndOpt on
 mport com.tw ter.ut l.Future
 mport scala.collect on.mutable

object T etK ndOpt onHydratedT etsF lterTransform {
  pr vate[common] val enableExpandedExtendedRepl esGate: Gate[RecapQuery] =
    RecapQuery.paramGate(RecapParams.EnableExpandedExtendedRepl esF lterParam)

  pr vate[common] val excludeRecom ndedRepl esToNonFollo dUsersGate: Gate[RecapQuery] =
    RecapQuery.paramGate(
      UtegL kedByT etsParams.UTEGRecom ndat onsF lter.ExcludeRecom ndedRepl esToNonFollo dUsersParam)
}

/**
 * F lter hydrated t ets dynam cally based on T etK ndOpt ons  n t  query.
 */
class T etK ndOpt onHydratedT etsF lterTransform(
  useFollowGraphData: Boolean,
  useS ceT ets: Boolean,
  statsRece ver: StatsRece ver)
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {
   mport T etK ndOpt onHydratedT etsF lterTransform._
  overr de def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val f lters = convertToF lters(envelope)

    val f lterTransform =  f (f lters == T etF lters.ValueSet.empty) {
      FutureArrow. dent y[Cand dateEnvelope]
    } else {
      new HydratedT etsF lterTransform(
        outerF lters = f lters,
         nnerF lters = T etF lters.None,
        useFollowGraphData = useFollowGraphData,
        useS ceT ets = useS ceT ets,
        statsRece ver = statsRece ver,
        numRet etsAllo d = HydratedT etsF lterTransform.NumDupl cateRet etsAllo d
      )
    }

    f lterTransform.apply(envelope)
  }

  /**
   * Converts t  g ven query opt ons to equ valent T etF lter values.
   *
   * Note:
   * -- T  semant c of T etK ndOpt on  s oppos e of that of T etF lters.
   *    T etK ndOpt on values are of t  form  ncludeX. That  s, t y result  n X be ng added.
   *    T etF lters values spec fy what to exclude.
   * --  ncludeExtendedRepl es requ res  ncludeRepl es to be also spec f ed to be effect ve.
   */
  pr vate[common] def convertToF lters(envelope: Cand dateEnvelope): T etF lters.ValueSet = {
    val queryOpt ons = envelope.query.opt ons
    val f lters = mutable.Set.empty[T etF lters.Value]
     f (queryOpt ons.conta ns(T etK ndOpt on. ncludeRepl es)) {
       f (excludeRecom ndedRepl esToNonFollo dUsersGate(
          envelope.query) && envelope.query.utegL kedByT etsOpt ons. sDef ned) {
        f lters += T etF lters.Recom ndedRepl esToNotFollo dUsers
      } else  f (queryOpt ons.conta ns(T etK ndOpt on. ncludeExtendedRepl es)) {
         f (enableExpandedExtendedRepl esGate(envelope.query)) {
          f lters += T etF lters.NotVal dExpandedExtendedRepl es
        } else {
          f lters += T etF lters.NotQual f edExtendedRepl es
        }
      } else {
        f lters += T etF lters.ExtendedRepl es
      }
    } else {
      f lters += T etF lters.Repl es
    }
     f (!queryOpt ons.conta ns(T etK ndOpt on. ncludeRet ets)) {
      f lters += T etF lters.Ret ets
    }
    T etF lters.ValueSet.empty ++ f lters
  }
}
