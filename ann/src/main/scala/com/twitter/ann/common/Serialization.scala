package com.tw ter.ann.common

 mport com.tw ter.search.common.f le.AbstractF le
 mport org.apac .beam.sdk. o.fs.Res ce d

/**
 *  nterface for wr  ng an Appendable to a d rectory.
 */
tra  Ser al zat on {
  def toD rectory(
    ser al zat onD rectory: AbstractF le
  ): Un 

  def toD rectory(
    ser al zat onD rectory: Res ce d
  ): Un 
}

/**
 *  nterface for read ng a Queryable from a d rectory
 * @tparam T t   d of t  embedd ngs
 * @tparam Q type of t  Queryable that  s deser al zed.
 */
tra  QueryableDeser al zat on[T, P <: Runt  Params, D <: D stance[D], Q <: Queryable[T, P, D]] {
  def fromD rectory(
    ser al zat onD rectory: AbstractF le
  ): Q
}
