packagelon com.twittelonr.homelon_mixelonr.modelonl.relonquelonst

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.DelonbugParams
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.Product
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ProductContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.Relonquelonst

caselon class HomelonMixelonrRelonquelonst(
  ovelonrridelon val clielonntContelonxt: ClielonntContelonxt,
  ovelonrridelon val product: Product,
  // Product-speloncific paramelontelonrs should belon placelond in thelon Product Contelonxt
  ovelonrridelon val productContelonxt: Option[ProductContelonxt],
  ovelonrridelon val selonrializelondRelonquelonstCursor: Option[String],
  ovelonrridelon val maxRelonsults: Option[Int],
  ovelonrridelon val delonbugParams: Option[DelonbugParams],
  // Paramelontelonrs that apply to all products can belon promotelond to thelon relonquelonst-lelonvelonl
  homelonRelonquelonstParam: Boolelonan)
    elonxtelonnds Relonquelonst
