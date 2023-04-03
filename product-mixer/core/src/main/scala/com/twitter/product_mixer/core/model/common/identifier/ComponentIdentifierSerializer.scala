packagelon com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr

import com.fastelonrxml.jackson.corelon.JsonGelonnelonrator
import com.fastelonrxml.jackson.databind.JsonSelonrializelonr
import com.fastelonrxml.jackson.databind.SelonrializelonrProvidelonr

privatelon[idelonntifielonr] class ComponelonntIdelonntifielonrSelonrializelonr()
    elonxtelonnds JsonSelonrializelonr[ComponelonntIdelonntifielonr] {

  privatelon caselon class SelonrializablelonComponelonntIdelonntifielonr(
    idelonntifielonr: String,
    sourcelonFilelon: String)

  ovelonrridelon delonf selonrializelon(
    componelonntIdelonntifielonr: ComponelonntIdelonntifielonr,
    gelonn: JsonGelonnelonrator,
    selonrializelonrs: SelonrializelonrProvidelonr
  ): Unit = selonrializelonrs.delonfaultSelonrializelonValuelon(
    SelonrializablelonComponelonntIdelonntifielonr(componelonntIdelonntifielonr.toString, componelonntIdelonntifielonr.filelon.valuelon),
    gelonn)
}
