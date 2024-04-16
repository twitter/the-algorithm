package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. tadata

 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModule tadataBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Gr dCarousel tadata
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Module tadata
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

object Top csToFollowModule tadataBu lder {

  val Top csPerRow = 7

  /*
   * rows = m n(MAX_NUM_ROWS, # top cs / TOP CS_PER_ROW)
   * w re TOP CS_PER_ROW = 7
   */
  def getCarouselRowCount(top csCount:  nt, maxCarouselRows:  nt):  nt =
    Math.m n(maxCarouselRows, (top csCount / Top csPerRow) + 1)
}

case class Top csToFollowModule tadataBu lder(maxCarouselRowsParam: Param[ nt])
    extends BaseModule tadataBu lder[P pel neQuery, Un versalNoun[Any]] {

   mport Top csToFollowModule tadataBu lder._

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[Un versalNoun[Any]]]
  ): Module tadata = {
    val rowCount = getCarouselRowCount(cand dates.s ze, query.params(maxCarouselRowsParam))
    Module tadata(
      ads tadata = None,
      conversat on tadata = None,
      gr dCarousel tadata = So (Gr dCarousel tadata(numRows = So (rowCount)))
    )
  }
}
