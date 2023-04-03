packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.transformelonr_elonxeloncutor

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr.Transformelonr
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.stitch.Arrow
import com.twittelonr.util.Try
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * For wrapping [[Transformelonr]]s that arelon applielond pelonr-candidatelon
 *
 * Reloncords a singlelon span for running all thelon componelonnts,
 * but stats pelonr-componelonnt.
 */
@Singlelonton
class PelonrCandidatelonTransformelonrelonxeloncutor @Injelonct() (ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds elonxeloncutor {

  delonf arrow[In, Out](
    transformelonr: Transformelonr[In, Out],
    contelonxt: elonxeloncutor.Contelonxt,
  ): Arrow[Selonq[In], Selonq[Try[Out]]] = {
    val pelonrCandidatelonArrow = wrapPelonrCandidatelonComponelonntWithelonxeloncutorBookkelonelonpingWithoutTracing(
      contelonxt,
      transformelonr.idelonntifielonr
    )(Arrow.map(transformelonr.transform)).liftToTry

    wrapComponelonntsWithTracingOnly(
      contelonxt,
      transformelonr.idelonntifielonr
    )(Arrow.selonquelonncelon(pelonrCandidatelonArrow))
  }
}
