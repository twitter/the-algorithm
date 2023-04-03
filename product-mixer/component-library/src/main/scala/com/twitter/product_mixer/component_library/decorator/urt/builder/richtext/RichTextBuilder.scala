packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.richtelonxt

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonStr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.richtelonxt.BaselonRichTelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.UrlTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtAlignmelonnt
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class RichTelonxtBuildelonr[-Quelonry <: PipelonlinelonQuelonry, -Candidatelon <: UnivelonrsalNoun[Any]](
  telonxtBuildelonr: BaselonStr[Quelonry, Candidatelon],
  linkMap: Map[String, String],
  rtl: Option[Boolelonan],
  alignmelonnt: Option[RichTelonxtAlignmelonnt],
  linkTypelonMap: Map[String, UrlTypelon] = Map.elonmpty)
    elonxtelonnds BaselonRichTelonxtBuildelonr[Quelonry, Candidatelon] {

  delonf apply(quelonry: Quelonry, candidatelon: Candidatelon, candidatelonFelonaturelons: FelonaturelonMap): RichTelonxt = {
    RichTelonxtMarkupUtil.richTelonxtFromMarkup(
      telonxt = telonxtBuildelonr(quelonry, candidatelon, candidatelonFelonaturelons),
      linkMap = linkMap,
      rtl = rtl,
      alignmelonnt = alignmelonnt,
      linkTypelonMap = linkTypelonMap)
  }
}
