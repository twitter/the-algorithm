package com.tw ter.follow_recom ndat ons.common.cand date_s ces.base
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er

/**
 * A wrapper of Cand dateS ce to make   eas er to do exper  ntat on
 * on new cand date generat on algor hms
 *
 * @param baseS ce base cand date s ce
 * @param darkreadAlgor hmParam controls w t r or not to darkread cand dates (fetch t m even  f t y w ll not be  ncluded)
 * @param keepCand datesParam controls w t r or not to keep cand dates from t  base s ce
 * @param resultCountThresholdParam controls how many results t  s ce must return to bucket t  user and return results (greater-than-or-equal-to)
 * @tparam T request type.   must extend HasParams
 * @tparam V value type
 */
class Exper  ntalCand dateS ce[T <: HasParams, V](
  baseS ce: Cand dateS ce[T, V],
  darkreadAlgor hmParam: Param[Boolean],
  keepCand datesParam: Param[Boolean],
  resultCountThresholdParam: Param[ nt],
  baseStatsRece ver: StatsRece ver)
    extends Cand dateS ce[T, V] {

  overr de val  dent f er: Cand dateS ce dent f er = baseS ce. dent f er
  pr vate[base] val statsRece ver =
    baseStatsRece ver.scope(s"Exper  ntal/${ dent f er.na }")
  pr vate[base] val requestsCounter = statsRece ver.counter("requests")
  pr vate[base] val resultCountGreaterThanThresholdCounter =
    statsRece ver.counter("w h_results_at_or_above_count_threshold")
  pr vate[base] val keepResultsCounter = statsRece ver.counter("keep_results")
  pr vate[base] val d scardResultsCounter = statsRece ver.counter("d scard_results")

  overr de def apply(request: T): St ch[Seq[V]] = {
     f (request.params(darkreadAlgor hmParam)) {
      requestsCounter. ncr()
      fetchFromCand dateS ceAndProcessResults(request)
    } else {
      St ch.N l
    }
  }

  pr vate def fetchFromCand dateS ceAndProcessResults(request: T): St ch[Seq[V]] = {
    baseS ce(request).map { results =>
       f (results.length >= request.params(resultCountThresholdParam)) {
        processResults(results, request.params(keepCand datesParam))
      } else {
        N l
      }
    }
  }

  pr vate def processResults(results: Seq[V], keepResults: Boolean): Seq[V] = {
    resultCountGreaterThanThresholdCounter. ncr()
     f (keepResults) {
      keepResultsCounter. ncr()
      results
    } else {
      d scardResultsCounter. ncr()
      N l
    }
  }
}
