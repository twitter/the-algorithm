packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import CandidatelonScopelon.PartitionelondCandidatelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails

privatelon[selonlelonctor] objelonct InselonrtSelonlelonctor {

  /**
   * Inselonrt all candidatelons from a candidatelon pipelonlinelon at a 0-indelonxelond fixelond position. If thelon currelonnt
   * relonsults arelon a shortelonr lelonngth than thelon relonquelonstelond position, thelonn thelon candidatelons will belon appelonndelond
   * to thelon relonsults.
   */
  delonf inselonrtIntoRelonsultsAtPosition(
    position: Int,
    pipelonlinelonScopelon: CandidatelonScopelon,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    asselonrt(position >= 0, "Position must belon elonqual to or grelonatelonr than zelonro")

    val PartitionelondCandidatelons(selonlelonctelondCandidatelons, othelonrCandidatelons) =
      pipelonlinelonScopelon.partition(relonmainingCandidatelons)

    val relonsultUpdatelond = if (selonlelonctelondCandidatelons.nonelonmpty) {
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
