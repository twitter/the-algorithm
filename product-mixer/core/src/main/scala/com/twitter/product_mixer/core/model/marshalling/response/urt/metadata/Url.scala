package com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.ReferenceObject

sealed tra  UrlType
case object ExternalUrl extends UrlType
case object DeepL nk extends UrlType
case object UrtEndpo nt extends UrlType

case class UrtEndpo ntOpt ons(
  requestParams: Opt on[Map[Str ng, Str ng]],
  t le: Opt on[Str ng],
  cac  d: Opt on[Str ng],
  subt le: Opt on[Str ng])

case class Url(urlType: UrlType, url: Str ng, urtEndpo ntOpt ons: Opt on[UrtEndpo ntOpt ons] = None)
    extends ReferenceObject
