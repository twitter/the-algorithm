package com.tw ter.s mclusters_v2.scald ng.common.matr x

 mport com.tw ter.algeb rd.{ArrayMono d, BloomF lterMono d, Mono d, Sem group}
 mport com.tw ter.algeb rd.Sem group._
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.scald ng.{TypedP pe, ValueP pe}

/**
 * A class that represents a row- ndexed dense matr x, backed by a TypedP pe[(R, Array[Double])].
 * For each row of t  TypedP pe,   save an array of values.
 * Only use t  class w n t  number of columns  s small (say, <100K).
 *
 * @param p pe underly ng p pe
 * @param rowOrd order ng funct on for row type
 * @param row nj  nject on funct on for t  row type
 * @tparam R Type for rows
 */
case class DenseRowMatr x[R](
  p pe: TypedP pe[(R, Array[Double])],
)(
   mpl c  val rowOrd: Order ng[R],
  val row nj:  nject on[R, Array[Byte]]) {

  lazy val sem groupArrayV: Sem group[Array[Double]] = new ArrayMono d[Double]()

  // convert to a SparseMatr x
  lazy val toSparseMatr x: SparseMatr x[R,  nt, Double] = {
    t .toSparseRowMatr x.toSparseMatr x
  }

  // convert to a SparseRowMatr x
  lazy val toSparseRowMatr x: SparseRowMatr x[R,  nt, Double] = {
    SparseRowMatr x(
      t .p pe.map {
        case ( , values) =>
          ( , values.z pW h ndex.collect { case (value, j)  f value != 0.0 => (j, value) }.toMap)
      },
       sSk nnyMatr x = true)
  }

  // convert to a TypedP pe
  lazy val toTypedP pe: TypedP pe[(R, Array[Double])] = {
    t .p pe
  }

  // f lter t  matr x based on a subset of rows
  def f lterRows(rows: TypedP pe[R]): DenseRowMatr x[R] = {
    DenseRowMatr x(t .p pe.jo n(rows.asKeys).mapValues(_._1))
  }

  // get t  l2 norms for all rows. t  does not tr gger a shuffle.
  lazy val rowL2Norms: TypedP pe[(R, Double)] = {
    t .p pe.map {
      case (row, values) =>
        row -> math.sqrt(values.map(a => a * a).sum)
    }
  }

  // normal ze t  matr x to make sure each row has un  norm
  lazy val rowL2Normal ze: DenseRowMatr x[R] = {

    DenseRowMatr x(t .p pe.map {
      case (row, values) =>
        val norm = math.sqrt(values.map(v => v * v).sum)
         f (norm == 0.0) {
          row -> values
        } else {
          row -> values.map(v => v / norm)
        }
    })
  }

}
