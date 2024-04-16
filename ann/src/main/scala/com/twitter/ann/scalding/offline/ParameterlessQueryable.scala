package com.tw ter.ann.scald ng.offl ne

 mport com.tw ter.ann.common.Embedd ngType.Embedd ngVector
 mport com.tw ter.ann.common.{D stance, Ne ghborW hD stance, Queryable, Runt  Params}
 mport com.tw ter.ut l.Future

pr vate[offl ne] case class Para terlessQueryable[T, P <: Runt  Params, D <: D stance[D]](
  queryable: Queryable[T, P, D],
  runt  ParamsForAllQuer es: P) {

  /**
   * ANN query for  ds w h d stance.
   *
   * @param embedd ng      : Embedd ng/Vector to be quer ed w h.
   * @param numOfNe ghbors : Number of ne ghb s to be quer ed for.
   *
   * @return L st of approx mate nearest ne ghb   ds w h d stance from t  query embedd ng.
   */
  def queryW hD stance(
    embedd ng: Embedd ngVector,
    numOfNe ghbors:  nt
  ): Future[L st[Ne ghborW hD stance[T, D]]] =
    queryable.queryW hD stance(embedd ng, numOfNe ghbors, runt  ParamsForAllQuer es)
}
