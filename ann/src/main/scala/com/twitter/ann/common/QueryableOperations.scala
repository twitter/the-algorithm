package com.tw ter.ann.common

 mport com.tw ter.ann.common.Embedd ngType.Embedd ngVector
 mport com.tw ter.ut l.Future

object QueryableOperat ons {
   mpl c  class Map[T, P <: Runt  Params, D <: D stance[D]](
    val q: Queryable[T, P, D]) {
    def mapRunt  Para ters(f: P => P): Queryable[T, P, D] = {
      new Queryable[T, P, D] {
        def query(
          embedd ng: Embedd ngVector,
          numOfNe ghbors:  nt,
          runt  Params: P
        ): Future[L st[T]] = q.query(embedd ng, numOfNe ghbors, f(runt  Params))

        def queryW hD stance(
          embedd ng: Embedd ngVector,
          numOfNe ghbors:  nt,
          runt  Params: P
        ): Future[L st[Ne ghborW hD stance[T, D]]] =
          q.queryW hD stance(embedd ng, numOfNe ghbors, f(runt  Params))
      }
    }
  }
}
