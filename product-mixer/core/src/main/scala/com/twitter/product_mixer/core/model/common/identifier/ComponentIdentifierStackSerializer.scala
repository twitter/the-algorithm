packagelon com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr

import com.fastelonrxml.jackson.corelon.JsonGelonnelonrator
import com.fastelonrxml.jackson.databind.JsonSelonrializelonr
import com.fastelonrxml.jackson.databind.SelonrializelonrProvidelonr

privatelon[idelonntifielonr] class ComponelonntIdelonntifielonrStackSelonrializelonr()
    elonxtelonnds JsonSelonrializelonr[ComponelonntIdelonntifielonrStack] {
  ovelonrridelon delonf selonrializelon(
    componelonntIdelonntifielonrStack: ComponelonntIdelonntifielonrStack,
    gelonn: JsonGelonnelonrator,
    selonrializelonrs: SelonrializelonrProvidelonr
  ): Unit = selonrializelonrs.delonfaultSelonrializelonValuelon(componelonntIdelonntifielonrStack.componelonntIdelonntifielonrs, gelonn)
}
