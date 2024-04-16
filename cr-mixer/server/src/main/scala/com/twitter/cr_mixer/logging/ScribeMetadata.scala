package com.tw ter.cr_m xer.logg ng

 mport com.tw ter.cr_m xer.model.AdsCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.CrCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.RelatedT etCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.UtegT etCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.thr ftscala.Product
 mport com.tw ter.product_m xer.core.thr ftscala.Cl entContext
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d

case class Scr be tadata(
  requestUU D: Long,
  user d: User d,
  product: Product)

object Scr be tadata {
  def from(query: CrCand dateGeneratorQuery): Scr be tadata = {
    Scr be tadata(query.requestUU D, query.user d, query.product)
  }

  def from(query: UtegT etCand dateGeneratorQuery): Scr be tadata = {
    Scr be tadata(query.requestUU D, query.user d, query.product)
  }

  def from(query: AdsCand dateGeneratorQuery): Scr be tadata = {
    Scr be tadata(query.requestUU D, query.user d, query.product)
  }
}

case class RelatedT etScr be tadata(
  requestUU D: Long,
   nternal d:  nternal d,
  cl entContext: Cl entContext,
  product: Product)

object RelatedT etScr be tadata {
  def from(query: RelatedT etCand dateGeneratorQuery): RelatedT etScr be tadata = {
    RelatedT etScr be tadata(
      query.requestUU D,
      query. nternal d,
      query.cl entContext,
      query.product)
  }
}
