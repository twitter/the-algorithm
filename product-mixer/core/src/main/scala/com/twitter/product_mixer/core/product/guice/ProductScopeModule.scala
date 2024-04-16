package com.tw ter.product_m xer.core.product.gu ce

 mport com.google. nject.Prov des
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Product
 mport javax. nject.S ngleton

/**
 * Reg sters t  @ProductScoped scope.
 *
 * See https://g hub.com/google/gu ce/w k /CustomScopes#reg ster ng-t -scope
 */
@S ngleton
class ProductScopeModule extends Tw terModule {

  val productScope: ProductScope = new ProductScope

  overr de def conf gure(): Un  = {
    b ndScope(classOf[ProductScoped], productScope)

    b nd[Product].toProv der(S mpleScope.SEEDED_KEY_PROV DER). n(classOf[ProductScoped])
  }

  @Prov des
  def prov desProductScope(): ProductScope = productScope
}
