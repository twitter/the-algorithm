package com.tw ter.s mclusters_v2.scald ng.common.matr x

 mport com.tw ter.algeb rd.{Aggregator, Sem group}
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.scald ng.{TypedP pe, ValueP pe}

/**
 * A matr x tra  for represent ng a matr x backed by TypedP pe
 *
 * @tparam R Type for rows
 * @tparam C Type for columns
 * @tparam V Type for ele nts of t  matr x
 */
abstract class TypedP peMatr x[R, C, @spec al zed(Double,  nt, Float, Long, Short) V] {
   mpl c  val sem groupV: Sem group[V]
   mpl c  val nu r cV: Nu r c[V]
   mpl c  val rowOrd: Order ng[R]
   mpl c  val colOrd: Order ng[C]
   mpl c  val row nj:  nject on[R, Array[Byte]]
   mpl c  val col nj:  nject on[C, Array[Byte]]

  // num of non-zero ele nts  n t  matr x
  val nnz: ValueP pe[Long]

  // l st of un que row ds  n t  matr x
  val un queRow ds: TypedP pe[R]

  // l st of un que un que  n t  matr x
  val un queCol ds: TypedP pe[C]

  // get a spec f c row of t  matr x
  def getRow(row d: R): TypedP pe[(C, V)]

  // get a spec f c column of t  matr x
  def getCol(col d: C): TypedP pe[(R, V)]

  // get t  value of an ele nt
  def get(row d: R, col d: C): ValueP pe[V]

  // number of un que row ds
  lazy val numUn queRows: ValueP pe[Long] = {
    t .un queRow ds.aggregate(Aggregator.s ze)
  }

  // number of un que un que
  lazy val numUn queCols: ValueP pe[Long] = {
    t .un queCol ds.aggregate(Aggregator.s ze)
  }
}
