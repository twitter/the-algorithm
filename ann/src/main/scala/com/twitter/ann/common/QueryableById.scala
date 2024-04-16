package com.tw ter.ann.common

 mport com.tw ter.st ch.St ch

/**
 * T   s a tra  that allows   to query for nearest ne ghbors g ven an arb rary type T1. T   s
 *  n contrast to a regular com.tw ter.ann.common.Appendable, wh ch takes an embedd ng as t   nput
 * argu nt.
 *
 * T   nterface uses t  St ch AP  for batch ng. See go/st ch for deta ls on how to use  .
 *
 * @tparam T1 type of t  query.
 * @tparam T2 type of t  result.
 * @tparam P runt   para ters supported by t   ndex.
 * @tparam D d stance funct on used  n t   ndex.
 */
tra  QueryableBy d[T1, T2, P <: Runt  Params, D <: D stance[D]] {
  def queryBy d(
     d: T1,
    numOfNe ghbors:  nt,
    runt  Params: P
  ): St ch[L st[T2]]

  def queryBy dW hD stance(
     d: T1,
    numOfNe ghbors:  nt,
    runt  Params: P
  ): St ch[L st[Ne ghborW hD stance[T2, D]]]

  def batchQueryBy d(
     ds: Seq[T1],
    numOfNe ghbors:  nt,
    runt  Params: P
  ): St ch[L st[Ne ghborW hSeed[T1, T2]]]

  def batchQueryW hD stanceBy d(
     ds: Seq[T1],
    numOfNe ghbors:  nt,
    runt  Params: P
  ): St ch[L st[Ne ghborW hD stanceW hSeed[T1, T2, D]]]
}
