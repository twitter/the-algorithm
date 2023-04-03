packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.stringcelonntelonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonModulelonStr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.stringcelonntelonr.BaselonModulelonStringCelonntelonrPlacelonholdelonrBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr
import com.twittelonr.stringcelonntelonr.clielonnt.corelon.elonxtelonrnalString

/**
 * This class works thelon samelon as [[Str]] but passelons in a list of candidatelons to thelon
 * [[BaselonModulelonStringCelonntelonrPlacelonholdelonrBuildelonr]] whelonn building thelon placelonholdelonrs.
 */
caselon class ModulelonStr[-Quelonry <: PipelonlinelonQuelonry, -Candidatelon <: UnivelonrsalNoun[Any]](
  telonxt: elonxtelonrnalString,
  stringCelonntelonr: StringCelonntelonr,
  stringCelonntelonrPlacelonholdelonrBuildelonr: Option[
    BaselonModulelonStringCelonntelonrPlacelonholdelonrBuildelonr[Quelonry, Candidatelon]
  ] = Nonelon)
    elonxtelonnds BaselonModulelonStr[Quelonry, Candidatelon] {

  delonf apply(quelonry: Quelonry, candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]): String = {
    val placelonholdelonrMapOpt =
      stringCelonntelonrPlacelonholdelonrBuildelonr.map(_.apply(quelonry, candidatelons))
    stringCelonntelonr.prelonparelon(
      elonxtelonrnalString = telonxt,
      placelonholdelonrs = placelonholdelonrMapOpt.gelontOrelonlselon(Map.elonmpty[String, Any])
    )
  }
}
