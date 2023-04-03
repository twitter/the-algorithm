packagelon com.twittelonr.product_mixelonr.corelon.product.guicelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.product_mixelonr.corelon.product.guicelon.scopelon.ProductScopelond
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.Product
import javax.injelonct.Singlelonton

/**
 * Relongistelonrs thelon @ProductScopelond scopelon.
 *
 * Selonelon https://github.com/googlelon/guicelon/wiki/CustomScopelons#relongistelonring-thelon-scopelon
 */
@Singlelonton
class ProductScopelonModulelon elonxtelonnds TwittelonrModulelon {

  val productScopelon: ProductScopelon = nelonw ProductScopelon

  ovelonrridelon delonf configurelon(): Unit = {
    bindScopelon(classOf[ProductScopelond], productScopelon)

    bind[Product].toProvidelonr(SimplelonScopelon.SelonelonDelonD_KelonY_PROVIDelonR).in(classOf[ProductScopelond])
  }

  @Providelons
  delonf providelonsProductScopelon(): ProductScopelon = productScopelon
}
