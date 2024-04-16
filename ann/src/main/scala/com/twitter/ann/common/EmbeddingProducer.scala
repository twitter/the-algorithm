package com.tw ter.ann.common

 mport com.tw ter.st ch.St ch

tra  Embedd ngProducer[T] {

  /**
   * Produce an embedd ng from type T.  mple ntat ons of t  could do a lookup from an  d to an
   * embedd ng. Or t y could run a deep model on features that output and embedd ng.
   * @return An embedd ng St ch. See go/st ch for deta ls on how to use t  St ch AP .
   */
  def produceEmbedd ng( nput: T): St ch[Opt on[Embedd ngType.Embedd ngVector]]
}
