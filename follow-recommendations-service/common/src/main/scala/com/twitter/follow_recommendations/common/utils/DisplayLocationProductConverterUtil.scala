packagelon com.twittelonr.follow_reloncommelonndations.common.utils

import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Product
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.Product

objelonct DisplayLocationProductConvelonrtelonrUtil {
  delonf productToDisplayLocation(product: Product): DisplayLocation = {
    product match {
      caselon Product.MagicReloncs => DisplayLocation.MagicReloncs
      caselon _ =>
        throw UnconvelonrtiblelonProductMixelonrProductelonxcelonption(
          s"Cannot convelonrt Product Mixelonr Product ${product.idelonntifielonr.namelon} into a FRS DisplayLocation.")
    }
  }

  delonf displayLocationToProduct(displayLocation: DisplayLocation): Product = {
    displayLocation match {
      caselon DisplayLocation.MagicReloncs => Product.MagicReloncs
      caselon _ =>
        throw UnconvelonrtiblelonProductMixelonrProductelonxcelonption(
          s"Cannot convelonrt DisplayLocation ${displayLocation.toFsNamelon} into a Product Mixelonr Product.")
    }
  }
}

caselon class UnconvelonrtiblelonProductMixelonrProductelonxcelonption(melonssagelon: String) elonxtelonnds elonxcelonption(melonssagelon)
