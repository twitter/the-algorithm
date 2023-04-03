packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ProductIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.{Product => ProductMixelonrProduct}

objelonct Product {
  caselon objelonct MagicReloncs elonxtelonnds ProductMixelonrProduct {
    ovelonrridelon val idelonntifielonr: ProductIdelonntifielonr = ProductIdelonntifielonr("MagicReloncs")
    ovelonrridelon val stringCelonntelonrProjelonct: Option[String] = Somelon("pelonoplelon-discovelonry")
  }

  caselon objelonct PlacelonholdelonrProductMixelonrProduct elonxtelonnds ProductMixelonrProduct {
    ovelonrridelon val idelonntifielonr: ProductIdelonntifielonr = ProductIdelonntifielonr("PlacelonholdelonrProductMixelonrProduct")
  }
}
