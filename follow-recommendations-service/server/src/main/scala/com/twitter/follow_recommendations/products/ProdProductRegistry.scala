package com.tw ter.follow_recom ndat ons.products

 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.follow_recom ndat ons.products.common.ProductReg stry
 mport com.tw ter.follow_recom ndat ons.products.explore_tab.ExploreTabProduct
 mport com.tw ter.follow_recom ndat ons.products.ho _t  l ne.Ho T  l neProduct
 mport com.tw ter.follow_recom ndat ons.products.ho _t  l ne_t et_recs.Ho T  l neT etRecsProduct
 mport com.tw ter.follow_recom ndat ons.products.s debar.S debarProduct

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ProdProductReg stry @ nject() (
  exploreTabProduct: ExploreTabProduct,
  ho T  l neProduct: Ho T  l neProduct,
  ho T  l neT etRecsProduct: Ho T  l neT etRecsProduct,
  s debarProduct: S debarProduct,
) extends ProductReg stry {

  overr de val products: Seq[common.Product] =
    Seq(
      exploreTabProduct,
      ho T  l neProduct,
      ho T  l neT etRecsProduct,
      s debarProduct
    )

  overr de val d splayLocat onProductMap: Map[D splayLocat on, common.Product] =
    products.groupBy(_.d splayLocat on).flatMap {
      case (loc, products) =>
        assert(products.s ze == 1, s"Found more than 1 Product for ${loc}")
        products. adOpt on.map { product => loc -> product }
    }

  overr de def getProductByD splayLocat on(d splayLocat on: D splayLocat on): common.Product = {
    d splayLocat onProductMap.getOrElse(
      d splayLocat on,
      throw new M ss ngProductExcept on(d splayLocat on))
  }
}

class M ss ngProductExcept on(d splayLocat on: D splayLocat on)
    extends Except on(s"No Product found for ${d splayLocat on}")
