package com.tw ter.cr_m xer
package logg ng

 mport com.tw ter.cr_m xer.thr ftscala.CrM xerT etRequest
 mport com.tw ter.cr_m xer.thr ftscala.Product

case class TopLevelDdg tr cs tadata(
  user d: Opt on[Long],
  product: Product,
  cl entAppl cat on d: Opt on[Long],
  countryCode: Opt on[Str ng])

object TopLevelDdg tr cs tadata {
  def from(request: CrM xerT etRequest): TopLevelDdg tr cs tadata = {
    TopLevelDdg tr cs tadata(
      user d = request.cl entContext.user d,
      product = request.product,
      cl entAppl cat on d = request.cl entContext.app d,
      countryCode = request.cl entContext.countryCode
    )
  }
}
