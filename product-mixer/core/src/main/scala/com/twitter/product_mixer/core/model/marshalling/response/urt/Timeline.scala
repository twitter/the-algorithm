package com.tw ter.product_m xer.core.model.marshall ng.response.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng

case class T  l ne(
   d: Str ng,
   nstruct ons: Seq[T  l ne nstruct on],
  // responseObjects::feedbackAct ons act ons are populated  mpl c ly, see UrtTransportMarshaller
   tadata: Opt on[T  l ne tadata] = None)
    extends HasMarshall ng
