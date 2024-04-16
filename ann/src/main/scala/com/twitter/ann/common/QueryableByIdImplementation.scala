package com.tw ter.ann.common

 mport com.tw ter.st ch.St ch

/**
 *  mple ntat on of QueryableBy d that composes an Embedd ngProducer and a Queryable so that  
 * can get nearest ne ghbors g ven an  d of type T1
 * @param embedd ngProducer prov des an embedd ng g ven an  d.
 * @param queryable prov des a l st of ne ghbors g ven an embedd ng.
 * @tparam T1 type of t  query.
 * @tparam T2 type of t  result.
 * @tparam P runt   para ters supported by t   ndex.
 * @tparam D d stance funct on used  n t   ndex.
 */
class QueryableBy d mple ntat on[T1, T2, P <: Runt  Params, D <: D stance[D]](
  embedd ngProducer: Embedd ngProducer[T1],
  queryable: Queryable[T2, P, D])
    extends QueryableBy d[T1, T2, P, D] {
  overr de def queryBy d(
     d: T1,
    numOfNe ghbors:  nt,
    runt  Params: P
  ): St ch[L st[T2]] = {
    embedd ngProducer.produceEmbedd ng( d).flatMap { embedd ngOpt on =>
      embedd ngOpt on
        .map { embedd ng =>
          St ch.callFuture(queryable.query(embedd ng, numOfNe ghbors, runt  Params))
        }.getOrElse {
          St ch.value(L st.empty)
        }
    }
  }

  overr de def queryBy dW hD stance(
     d: T1,
    numOfNe ghbors:  nt,
    runt  Params: P
  ): St ch[L st[Ne ghborW hD stance[T2, D]]] = {
    embedd ngProducer.produceEmbedd ng( d).flatMap { embedd ngOpt on =>
      embedd ngOpt on
        .map { embedd ng =>
          St ch.callFuture(queryable.queryW hD stance(embedd ng, numOfNe ghbors, runt  Params))
        }.getOrElse {
          St ch.value(L st.empty)
        }
    }
  }

  overr de def batchQueryBy d(
     ds: Seq[T1],
    numOfNe ghbors:  nt,
    runt  Params: P
  ): St ch[L st[Ne ghborW hSeed[T1, T2]]] = {
    St ch
      .traverse( ds) {  d =>
        embedd ngProducer.produceEmbedd ng( d).flatMap { embedd ngOpt on =>
          embedd ngOpt on
            .map { embedd ng =>
              St ch
                .callFuture(queryable.query(embedd ng, numOfNe ghbors, runt  Params)).map(
                  _.map(ne ghbor => Ne ghborW hSeed( d, ne ghbor)))
            }.getOrElse {
              St ch.value(L st.empty)
            }.handle { case _ => L st.empty }
        }
      }.map { _.toL st.flatten }
  }

  overr de def batchQueryW hD stanceBy d(
     ds: Seq[T1],
    numOfNe ghbors:  nt,
    runt  Params: P
  ): St ch[L st[Ne ghborW hD stanceW hSeed[T1, T2, D]]] = {
    St ch
      .traverse( ds) {  d =>
        embedd ngProducer.produceEmbedd ng( d).flatMap { embedd ngOpt on =>
          embedd ngOpt on
            .map { embedd ng =>
              St ch
                .callFuture(queryable.queryW hD stance(embedd ng, numOfNe ghbors, runt  Params))
                .map(_.map(ne ghbor =>
                  Ne ghborW hD stanceW hSeed( d, ne ghbor.ne ghbor, ne ghbor.d stance)))
            }.getOrElse {
              St ch.value(L st.empty)
            }.handle { case _ => L st.empty }
        }
      }.map {
        _.toL st.flatten
      }
  }
}
