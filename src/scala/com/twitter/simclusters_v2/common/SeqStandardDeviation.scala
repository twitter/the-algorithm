package com.tw ter.s mclusters_v2.common

object SeqStandardDev at on {

  def apply[T](t: Seq[T])( mpl c  mapper: T => Double): Double = {
     f (t. sEmpty) {
      0.0
    } else {
      val sum = t.foldLeft(0.0) {
        case (temp, score) =>
          temp + score
      }
      val  an = sum / t.s ze
      val var ance = t.foldLeft(0.0) { (sum, score) =>
        val v = score -  an
        sum + v * v
      } / t.s ze
      math.sqrt(var ance)
    }
  }

}
