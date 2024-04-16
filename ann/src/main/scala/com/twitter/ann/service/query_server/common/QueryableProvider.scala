package com.tw ter.ann.serv ce.query_server.common

 mport com.tw ter.ann.common.{D stance, Queryable, Runt  Params}
 mport com.tw ter.search.common.f le.AbstractF le

tra  QueryableProv der[T, P <: Runt  Params, D <: D stance[D]] {
  def prov deQueryable( ndexD r: AbstractF le): Queryable[T, P, D]
}
