package com.tw ter.ann.hnsw

 mport com.tw ter.ann.common.Embedd ngType.Embedd ngVector
 mport com.tw ter.ann.common.{Cos ne, D stance,  nnerProduct,  tr c}

pr vate[hnsw] object D stanceFunct onGenerator {
  def apply[T, D <: D stance[D]](
     tr c:  tr c[D],
     dToEmbedd ngFn: (T) => Embedd ngVector
  ): D stanceFunct onGenerator[T] = {
    // Use  nnerProduct for cos ne and normal ze t  vectors before append ng and query ng.
    val updated tr c =  tr c match {
      case Cos ne =>  nnerProduct
      case _ =>  tr c
    }

    val d stFn ndex = new D stanceFunct on[T, T] {
      overr de def d stance( d1: T,  d2: T) =
        updated tr c.absoluteD stance(
           dToEmbedd ngFn( d1),
           dToEmbedd ngFn( d2)
        )
    }

    val d stFnQuery = new D stanceFunct on[Embedd ngVector, T] {
      overr de def d stance(embedd ng: Embedd ngVector,  d: T) =
        updated tr c.absoluteD stance(embedd ng,  dToEmbedd ngFn( d))
    }

    D stanceFunct onGenerator(d stFn ndex, d stFnQuery,  tr c == Cos ne)
  }
}

pr vate[hnsw] case class D stanceFunct onGenerator[T](
   ndex: D stanceFunct on[T, T],
  query: D stanceFunct on[Embedd ngVector, T],
  shouldNormal ze: Boolean)
