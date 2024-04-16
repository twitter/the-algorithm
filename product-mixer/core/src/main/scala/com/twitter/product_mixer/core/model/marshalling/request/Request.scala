package com.tw ter.product_m xer.core.model.marshall ng.request

tra  Request
    extends HasCl entContext
    w h HasProduct
    w h HasProductContext
    w h HasSer al zedRequestCursor {
  def maxResults: Opt on[ nt]
  def debugParams: Opt on[DebugParams]
}
