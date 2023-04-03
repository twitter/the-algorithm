packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.melontadata

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonUrlBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Url
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.UrlTypelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon class StaticUrlBuildelonr(url: String, urlTypelon: UrlTypelon)
    elonxtelonnds BaselonUrlBuildelonr[PipelonlinelonQuelonry, UnivelonrsalNoun[Any]] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: UnivelonrsalNoun[Any],
    candidatelonFelonaturelons: FelonaturelonMap
  ): Url = Url(url = url, urlTypelon = urlTypelon)
}
