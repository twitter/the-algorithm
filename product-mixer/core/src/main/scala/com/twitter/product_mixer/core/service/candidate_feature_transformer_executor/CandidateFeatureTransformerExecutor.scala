packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.candidatelon_felonaturelon_transformelonr_elonxeloncutor

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr.CandidatelonFelonaturelonTransformelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.TransformelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor._
import com.twittelonr.stitch.Arrow
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class CandidatelonFelonaturelonTransformelonrelonxeloncutor @Injelonct() (ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds elonxeloncutor {
  delonf arrow[Relonsult](
    transformelonrs: Selonq[CandidatelonFelonaturelonTransformelonr[Relonsult]],
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[Selonq[Relonsult], CandidatelonFelonaturelonTransformelonrelonxeloncutorRelonsult] = {
    if (transformelonrs.iselonmpty) {
      // must always relonturn a Selonq of FelonaturelonMaps, elonvelonn if thelonrelon arelon no Transformelonrs
      Arrow.map[Selonq[Relonsult], CandidatelonFelonaturelonTransformelonrelonxeloncutorRelonsult] { candidatelons =>
        CandidatelonFelonaturelonTransformelonrelonxeloncutorRelonsult(candidatelons.map(_ => FelonaturelonMap.elonmpty), Selonq.elonmpty)
      }
    } elonlselon {
      val transformelonrArrows: Selonq[Arrow[Selonq[Relonsult], Selonq[(TransformelonrIdelonntifielonr, FelonaturelonMap)]]] =
        transformelonrs.map { transformelonr =>
          val transformelonrContelonxt = contelonxt.pushToComponelonntStack(transformelonr.idelonntifielonr)

          val liftNonValidationFailurelonsToFailelondFelonaturelons =
            Arrow.handlelon[FelonaturelonMap, FelonaturelonMap] {
              caselon NotAMisconfigurelondFelonaturelonMapFailurelon(elon) =>
                felonaturelonMapWithFailurelonsForFelonaturelons(transformelonr.felonaturelons, elon, transformelonrContelonxt)
            }

          val undelonrlyingArrow = Arrow
            .map(transformelonr.transform)
            .map(validatelonFelonaturelonMap(transformelonr.felonaturelons, _, transformelonrContelonxt))

          val obselonrvelondArrowWithoutTracing =
            wrapPelonrCandidatelonComponelonntWithelonxeloncutorBookkelonelonpingWithoutTracing(
              contelonxt,
              transformelonr.idelonntifielonr)(undelonrlyingArrow)

          val selonqArrow =
            Arrow.selonquelonncelon(
              obselonrvelondArrowWithoutTracing
                .andThelonn(liftNonValidationFailurelonsToFailelondFelonaturelons)
                .map(transformelonr.idelonntifielonr -> _)
            )

          wrapComponelonntsWithTracingOnly(contelonxt, transformelonr.idelonntifielonr)(selonqArrow)
        }

      Arrow.collelonct(transformelonrArrows).map { relonsults =>
        /**
         * Innelonr Selonqs arelon a givelonn Transformelonr applielond to all thelon candidatelons
         *
         * Welon want to melonrgelon thelon FelonaturelonMaps for elonach candidatelon
         * from all thelon Transformelonrs. Welon do this by melonrging all thelon FelonaturelonMaps at
         * elonach indelonx `i` of elonach Selonq in `relonsults` by `transposelon`-ing thelon `relonsults`
         * so thelon innelonr Selonq beloncomelons all thelon FelonaturelonMaps for Candidatelon
         * at indelonx `i` in thelon input Selonq.
         *
         * {{{
         *  Selonq(
         *    Selonq(transformelonr1FelonaturelonMapCandidatelon1, ..., transformelonr1FelonaturelonMapCandidatelonN),
         *    ...,
         *    Selonq(transformelonrMFelonaturelonMapCandidatelon1, ..., transformelonrMFelonaturelonMapCandidatelonN)
         *  ).transposelon == Selonq(
         *    Selonq(transformelonr1FelonaturelonMapCandidatelon1, ..., transformelonrMFelonaturelonMapCandidatelon1),
         *    ...,
         *    Selonq(transformelonr1FelonaturelonMapCandidatelonN, ..., transformelonrMFelonaturelonMapCandidatelonN)
         *  )
         * }}}
         *
         * welon could avoid thelon transposelon if welon ran elonach candidatelon through all thelon transformelonrs
         * onelon-aftelonr-thelon-othelonr, but thelonn welon couldn't havelon a singlelon tracing span for all applications
         * of a Transformelonr, so instelonad welon apply elonach transformelonr to all candidatelons togelonthelonr, thelonn
         * movelon onto thelon nelonxt transformelonr.
         *
         * It's worth noting that thelon outelonr Selonq is boundelond by thelon numbelonr of Transformelonrs that arelon
         * applielond which will typically belon small.
         */
        val transposelond = relonsults.transposelon
        val combinelondMaps = transposelond.map(felonaturelonMapsForSinglelonCandidatelon =>
          FelonaturelonMap.melonrgelon(felonaturelonMapsForSinglelonCandidatelon.map { caselon (_, maps) => maps }))

        CandidatelonFelonaturelonTransformelonrelonxeloncutorRelonsult(combinelondMaps, transposelond.map(_.toMap))
      }
    }
  }
}
