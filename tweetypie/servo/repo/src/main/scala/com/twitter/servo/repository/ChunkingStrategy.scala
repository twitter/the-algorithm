package com.tw ter.servo.repos ory

object Chunk ngStrategy {

  /**
   * A chunk ng strategy for break ng a query  nto f xed s ze chunks, w h t  last
   * chunk poss bly be ng any s ze bet en 1 and chunkS ze.
   */
  def f xedS ze[K](chunkS ze:  nt): Seq[K] => Seq[Seq[K]] = {
    f xedS ze(chunkS ze, keysAsQuery[K])
  }

  /**
   * A chunk ng strategy for break ng a query  nto f xed s ze chunks, w h t  last
   * chunk poss bly be ng any s ze bet en 1 and chunkS ze.
   */
  def f xedS ze[Q <: Seq[K], K](
    chunkS ze:  nt,
    newQuery: SubqueryBu lder[Q, K]
  ): Q => Seq[Q] = { query =>
    query.d st nct.grouped(chunkS ze) map { newQuery(_, query) } toSeq
  }

  /**
   * A chunk ng strategy for break ng a query  nto roughly equal s zed chunks no
   * larger than maxS ze.  T  last chunk may be sl ghtly smaller due to round ng.
   */
  def equalS ze[K](maxS ze:  nt): Seq[K] => Seq[Seq[K]] = {
    equalS ze(maxS ze, keysAsQuery[K])
  }

  /**
   * A chunk ng strategy for break ng a query  nto roughly equal s zed chunks no
   * larger than maxS ze.  T  last chunk may be sl ghtly smaller due to round ng.
   */
  def equalS ze[Q <: Seq[K], K](
    maxS ze:  nt,
    newQuery: SubqueryBu lder[Q, K]
  ): Q => Seq[Q] = { query =>
    {
       f (query.s ze <= maxS ze) {
        Seq(query)
      } else {
        val chunkCount = math.ce l(query.s ze / maxS ze.toDouble)
        val chunkS ze = math.ce l(query.s ze / chunkCount).to nt
        query.d st nct.grouped(chunkS ze) map { newQuery(_, query) } toSeq
      }
    }
  }
}
