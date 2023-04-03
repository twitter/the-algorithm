packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.baselon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.timelonlinelons.configapi.Param
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr

/**
 * A wrappelonr of CandidatelonSourcelon to makelon it elonasielonr to do elonxpelonrimelonntation
 * on nelonw candidatelon gelonnelonration algorithms
 *
 * @param baselonSourcelon baselon candidatelon sourcelon
 * @param darkrelonadAlgorithmParam controls whelonthelonr or not to darkrelonad candidatelons (felontch thelonm elonvelonn if thelony will not belon includelond)
 * @param kelonelonpCandidatelonsParam controls whelonthelonr or not to kelonelonp candidatelons from thelon baselon sourcelon
 * @param relonsultCountThrelonsholdParam controls how many relonsults thelon sourcelon must relonturn to buckelont thelon uselonr and relonturn relonsults (grelonatelonr-than-or-elonqual-to)
 * @tparam T relonquelonst typelon. it must elonxtelonnd HasParams
 * @tparam V valuelon typelon
 */
class elonxpelonrimelonntalCandidatelonSourcelon[T <: HasParams, V](
  baselonSourcelon: CandidatelonSourcelon[T, V],
  darkrelonadAlgorithmParam: Param[Boolelonan],
  kelonelonpCandidatelonsParam: Param[Boolelonan],
  relonsultCountThrelonsholdParam: Param[Int],
  baselonStatsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds CandidatelonSourcelon[T, V] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = baselonSourcelon.idelonntifielonr
  privatelon[baselon] val statsReloncelonivelonr =
    baselonStatsReloncelonivelonr.scopelon(s"elonxpelonrimelonntal/${idelonntifielonr.namelon}")
  privatelon[baselon] val relonquelonstsCountelonr = statsReloncelonivelonr.countelonr("relonquelonsts")
  privatelon[baselon] val relonsultCountGrelonatelonrThanThrelonsholdCountelonr =
    statsReloncelonivelonr.countelonr("with_relonsults_at_or_abovelon_count_threlonshold")
  privatelon[baselon] val kelonelonpRelonsultsCountelonr = statsReloncelonivelonr.countelonr("kelonelonp_relonsults")
  privatelon[baselon] val discardRelonsultsCountelonr = statsReloncelonivelonr.countelonr("discard_relonsults")

  ovelonrridelon delonf apply(relonquelonst: T): Stitch[Selonq[V]] = {
    if (relonquelonst.params(darkrelonadAlgorithmParam)) {
      relonquelonstsCountelonr.incr()
      felontchFromCandidatelonSourcelonAndProcelonssRelonsults(relonquelonst)
    } elonlselon {
      Stitch.Nil
    }
  }

  privatelon delonf felontchFromCandidatelonSourcelonAndProcelonssRelonsults(relonquelonst: T): Stitch[Selonq[V]] = {
    baselonSourcelon(relonquelonst).map { relonsults =>
      if (relonsults.lelonngth >= relonquelonst.params(relonsultCountThrelonsholdParam)) {
        procelonssRelonsults(relonsults, relonquelonst.params(kelonelonpCandidatelonsParam))
      } elonlselon {
        Nil
      }
    }
  }

  privatelon delonf procelonssRelonsults(relonsults: Selonq[V], kelonelonpRelonsults: Boolelonan): Selonq[V] = {
    relonsultCountGrelonatelonrThanThrelonsholdCountelonr.incr()
    if (kelonelonpRelonsults) {
      kelonelonpRelonsultsCountelonr.incr()
      relonsults
    } elonlselon {
      discardRelonsultsCountelonr.incr()
      Nil
    }
  }
}
