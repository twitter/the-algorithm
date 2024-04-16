package com.tw ter.s mclusters_v2.scald ng.common

 mport com.tw ter.algeb rd._

/**
 * T  reason of creat ng t  class  s that   need mult ple percent les and current
 *  mple ntat ons need one QTree per percent le wh ch  s unnecessary. T  class gets mult ple
 * percent les from t  sa  QTree.
 */
case class QTreeMult Aggregator[T](percent les: Seq[Double])( mpl c  val num: Nu r c[T])
    extends Aggregator[T, QTree[Un ], Map[Str ng, Double]]
    w h QTreeAggregatorL ke[T] {

  requ re(
    percent les.forall(p => p >= 0.0 && p <= 1.0),
    "T  g ven percent le must be of t  form 0 <= p <= 1.0"
  )

  overr de def percent le: Double = 0.0 // Useless but needed for t  base class

  overr de def k:  nt = QTreeAggregator.DefaultK

  pr vate def getPercent le(qt: QTree[Un ], p: Double): Double = {
    val (lo r, upper) = qt.quant leBounds(p)
    (lo r + upper) / 2
  }

  def present(qt: QTree[Un ]): Map[Str ng, Double] =
    percent les.map { p => p.toStr ng -> getPercent le(qt, p) }.toMap
}
