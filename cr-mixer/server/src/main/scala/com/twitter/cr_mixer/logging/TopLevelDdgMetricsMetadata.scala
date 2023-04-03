packagelon com.twittelonr.cr_mixelonr
packagelon logging

import com.twittelonr.cr_mixelonr.thriftscala.CrMixelonrTwelonelontRelonquelonst
import com.twittelonr.cr_mixelonr.thriftscala.Product

caselon class TopLelonvelonlDdgMelontricsMelontadata(
  uselonrId: Option[Long],
  product: Product,
  clielonntApplicationId: Option[Long],
  countryCodelon: Option[String])

objelonct TopLelonvelonlDdgMelontricsMelontadata {
  delonf from(relonquelonst: CrMixelonrTwelonelontRelonquelonst): TopLelonvelonlDdgMelontricsMelontadata = {
    TopLelonvelonlDdgMelontricsMelontadata(
      uselonrId = relonquelonst.clielonntContelonxt.uselonrId,
      product = relonquelonst.product,
      clielonntApplicationId = relonquelonst.clielonntContelonxt.appId,
      countryCodelon = relonquelonst.clielonntContelonxt.countryCodelon
    )
  }
}
