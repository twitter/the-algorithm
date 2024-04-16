package com.tw ter.product_m xer.core.model.marshall ng.response.urt

 mport com.tw ter.ut l.T  

tra  HasExp rat onT   {
  def exp rat onT  : Opt on[T  ] = None

  f nal def exp rat onT   nM ll s: Opt on[Long] = exp rat onT  .map(_. nM ll s)
}
