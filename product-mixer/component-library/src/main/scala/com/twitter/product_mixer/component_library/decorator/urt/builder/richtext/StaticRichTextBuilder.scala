packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.richtelonxt

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.richtelonxt.BaselonRichTelonxtBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxt
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class StaticRichTelonxtBuildelonr(richTelonxt: RichTelonxt)
    elonxtelonnds BaselonRichTelonxtBuildelonr[PipelonlinelonQuelonry, UnivelonrsalNoun[Any]] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: UnivelonrsalNoun[Any],
    candidatelonFelonaturelons: FelonaturelonMap
  ): RichTelonxt = richTelonxt
}
