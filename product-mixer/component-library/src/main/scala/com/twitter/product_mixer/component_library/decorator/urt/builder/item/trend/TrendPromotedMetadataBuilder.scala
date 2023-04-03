packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.trelonnd

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.trelonnds_elonvelonnts.PromotelondTrelonndDelonscriptionFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.trelonnds_elonvelonnts.PromotelondTrelonndDisclosurelonTypelonFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.trelonnds_elonvelonnts.PromotelondTrelonndIdFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.trelonnds_elonvelonnts.PromotelondTrelonndImprelonssionIdFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.trelonnds_elonvelonnts.PromotelondTrelonndNamelonFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.promotelond.BaselonPromotelondMelontadataBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.PromotelondMelontadata
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct TrelonndPromotelondMelontadataBuildelonr
    elonxtelonnds BaselonPromotelondMelontadataBuildelonr[PipelonlinelonQuelonry, UnivelonrsalNoun[Any]] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: UnivelonrsalNoun[Any],
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[PromotelondMelontadata] = {
    // If a promotelond trelonnd namelon elonxists, thelonn this is a promotelond trelonnd
    candidatelonFelonaturelons.gelontOrelonlselon(PromotelondTrelonndNamelonFelonaturelon, Nonelon).map { promotelondTrelonndNamelon =>
      PromotelondMelontadata(
        // This is thelon currelonnt product belonhavior that advelonrtiselonrId is always selont to 0L.
        // Correlonct advelonrtiselonr namelon comelons from Trelonnd's trelonndMelontadata.melontaDelonscription.
        advelonrtiselonrId = 0L,
        disclosurelonTypelon = candidatelonFelonaturelons.gelontOrelonlselon(PromotelondTrelonndDisclosurelonTypelonFelonaturelon, Nonelon),
        elonxpelonrimelonntValuelons = Nonelon,
        promotelondTrelonndId = candidatelonFelonaturelons.gelontOrelonlselon(PromotelondTrelonndIdFelonaturelon, Nonelon),
        promotelondTrelonndNamelon = Somelon(promotelondTrelonndNamelon),
        promotelondTrelonndQuelonryTelonrm = Nonelon,
        adMelontadataContainelonr = Nonelon,
        promotelondTrelonndDelonscription =
          candidatelonFelonaturelons.gelontOrelonlselon(PromotelondTrelonndDelonscriptionFelonaturelon, Nonelon),
        imprelonssionString = candidatelonFelonaturelons.gelontOrelonlselon(PromotelondTrelonndImprelonssionIdFelonaturelon, Nonelon),
        clickTrackingInfo = Nonelon
      )
    }
  }
}
