packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.stringcelonntelonr

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonStr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.stringcelonntelonr.BaselonStringCelonntelonrPlacelonholdelonrBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stringcelonntelonr.clielonnt.StringCelonntelonr
import com.twittelonr.stringcelonntelonr.clielonnt.corelon.elonxtelonrnalString

caselon class StrStatic(
  telonxt: String)
    elonxtelonnds BaselonStr[PipelonlinelonQuelonry, UnivelonrsalNoun[Any]] {
  delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: UnivelonrsalNoun[Any],
    candidatelonFelonaturelons: FelonaturelonMap
  ): String = telonxt
}

caselon class Str[-Quelonry <: PipelonlinelonQuelonry, -Candidatelon <: UnivelonrsalNoun[Any]](
  telonxt: elonxtelonrnalString,
  stringCelonntelonr: StringCelonntelonr,
  stringCelonntelonrPlacelonholdelonrBuildelonr: Option[BaselonStringCelonntelonrPlacelonholdelonrBuildelonr[Quelonry, Candidatelon]] =
    Nonelon)
    elonxtelonnds BaselonStr[Quelonry, Candidatelon] {

  delonf apply(quelonry: Quelonry, candidatelon: Candidatelon, candidatelonFelonaturelons: FelonaturelonMap): String = {
    val placelonholdelonrMapOpt =
      stringCelonntelonrPlacelonholdelonrBuildelonr.map(_.apply(quelonry, candidatelon, candidatelonFelonaturelons))
    stringCelonntelonr.prelonparelon(
      elonxtelonrnalString = telonxt,
      placelonholdelonrs = placelonholdelonrMapOpt.gelontOrelonlselon(Map.elonmpty[String, Any])
    )
  }
}
