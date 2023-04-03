packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import CandidatelonScopelon.PartitionelondCandidatelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.SpeloncificPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * Inselonrt all candidatelons from a candidatelon pipelonlinelon at a position belonlow, relonlativelon to thelon last
 * selonlelonction of thelon relonlativelon to candidatelon pipelonlinelon. If thelon relonlativelon to candidatelon pipelonlinelon doelons not
 * contain candidatelons, thelonn thelon candidatelons will belon inselonrtelond with padding relonlativelon to position zelonro.
 * If thelon currelonnt relonsults arelon a shortelonr lelonngth than thelon relonquelonstelond padding, thelonn thelon candidatelons will
 * belon appelonndelond to thelon relonsults.
 */
caselon class InselonrtRelonlativelonPositionRelonsults(
  candidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
  relonlativelonToCandidatelonPipelonlinelon: CandidatelonPipelonlinelonIdelonntifielonr,
  paddingAbovelonParam: Param[Int])
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon = SpeloncificPipelonlinelons(candidatelonPipelonlinelon)

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {

    val paddingAbovelon = quelonry.params(paddingAbovelonParam)
    asselonrt(paddingAbovelon >= 0, "Padding abovelon must belon elonqual to or grelonatelonr than zelonro")

    val PartitionelondCandidatelons(selonlelonctelondCandidatelons, othelonrCandidatelons) =
      pipelonlinelonScopelon.partition(relonmainingCandidatelons)

    val relonsultUpdatelond = if (selonlelonctelondCandidatelons.nonelonmpty) {
      // If thelon relonlativelonToCandidatelonPipelonlinelon has zelonro candidatelons, lastIndelonxWhelonrelon will relonturn -1 which
      // will start padding from thelon zelonro position
      val relonlativelonPosition = relonsult.lastIndelonxWhelonrelon(_.sourcelon == relonlativelonToCandidatelonPipelonlinelon) + 1
      val position = relonlativelonPosition + paddingAbovelon

      if (position < relonsult.lelonngth) {
        val (lelonft, right) = relonsult.splitAt(position)
        lelonft ++ selonlelonctelondCandidatelons ++ right
      } elonlselon {
        relonsult ++ selonlelonctelondCandidatelons
      }
    } elonlselon {
      relonsult
    }

    SelonlelonctorRelonsult(relonmainingCandidatelons = othelonrCandidatelons, relonsult = relonsultUpdatelond)
  }
}
