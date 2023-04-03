packagelon com.twittelonr.follow_reloncommelonndations.modelonls

import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ProductContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.{Relonquelonst => ProductMixelonrRelonquelonst}

caselon class Relonquelonst(
  ovelonrridelon val maxRelonsults: Option[Int],
  ovelonrridelon val delonbugParams: Option[relonquelonst.DelonbugParams],
  ovelonrridelon val productContelonxt: Option[ProductContelonxt],
  ovelonrridelon val product: relonquelonst.Product,
  ovelonrridelon val clielonntContelonxt: ClielonntContelonxt,
  ovelonrridelon val selonrializelondRelonquelonstCursor: Option[String],
  ovelonrridelon val frsDelonbugParams: Option[DelonbugParams],
  displayLocation: DisplayLocation,
  elonxcludelondIds: Option[Selonq[Long]],
  felontchPromotelondContelonnt: Option[Boolelonan],
  uselonrLocationStatelon: Option[String] = Nonelon)
    elonxtelonnds ProductMixelonrRelonquelonst
    with HasFrsDelonbugParams {}
