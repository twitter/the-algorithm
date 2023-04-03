packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.timelonlinelon_modulelon

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.timelonlinelon_modulelon.BaselonModulelonDisplayTypelonBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.VelonrticalConvelonrsation
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class FelonaturelonModulelonDisplayTypelonBuildelonr(
  displayTypelonFelonaturelon: Felonaturelon[_, Option[ModulelonDisplayTypelon]],
  delonfaultDisplayTypelon: ModulelonDisplayTypelon = VelonrticalConvelonrsation)
    elonxtelonnds BaselonModulelonDisplayTypelonBuildelonr[PipelonlinelonQuelonry, UnivelonrsalNoun[Any]] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[UnivelonrsalNoun[Any]]]
  ): ModulelonDisplayTypelon = candidatelons.helonadOption
    .flatMap(_.felonaturelons.gelontOrelonlselon(displayTypelonFelonaturelon, Nonelon))
    .gelontOrelonlselon(delonfaultDisplayTypelon)
}
