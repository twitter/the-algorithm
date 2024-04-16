package com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText

sealed tra  CoverCtaBehav or

case class CoverBehav orNav gate(url: Url) extends CoverCtaBehav or
case class CoverBehav orD sm ss(feedback ssage: Opt on[R chText]) extends CoverCtaBehav or
