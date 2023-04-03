packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.flelonxiblelon_injelonction_pipelonlinelon

import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.GroupByKelony
import com.twittelonr.product_mixelonr.componelonnt_library.pipelonlinelon.candidatelon.flelonxiblelon_injelonction_pipelonlinelon.transformelonr.FlipPromptInjelonctionsFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.pipelonlinelon.candidatelon.flelonxiblelon_injelonction_pipelonlinelon.transformelonr.FlipPromptOffselontInModulelonFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

objelonct FlipPromptModulelonGrouping elonxtelonnds GroupByKelony[PipelonlinelonQuelonry, UnivelonrsalNoun[Any], Int] {
  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: UnivelonrsalNoun[Any],
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[Int] = {
    val injelonction = candidatelonFelonaturelons.gelont(FlipPromptInjelonctionsFelonaturelon)
    val offselontInModulelon = candidatelonFelonaturelons.gelontOrelonlselon(FlipPromptOffselontInModulelonFelonaturelon, Nonelon)

    // Welon relonturn Nonelon for any candidatelon that doelonsn't havelon an offselontInModulelon, so that thelony arelon lelonft as indelonpelonndelonnt itelonms.
    // Othelonrwiselon, welon relonturn a hash of thelon injelonction instancelon which will belon uselond to aggrelongatelon candidatelons with matching valuelons into a modulelon.
    offselontInModulelon.map(_ => injelonction.hashCodelon())
  }
}
