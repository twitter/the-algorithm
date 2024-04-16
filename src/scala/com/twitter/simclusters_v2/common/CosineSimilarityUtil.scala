package com.tw ter.s mclusters_v2.common

object Cos neS m lar yUt l {

  /**
   * Sum of squared ele nts for a g ven vector v
   */
  def sumOfSquares[T](v: Map[T, Double]): Double = {
    v.values.foldLeft(0.0) { (sum, value) => sum + value * value }
  }

  /**
   * Sum of squared ele nts for a g ven vector v
   */
  def sumOfSquaresArray(v: Array[Double]): Double = {
    v.foldLeft(0.0) { (sum, value) => sum + value * value }
  }

  /**
   * Calculate t  l2Norm score
   */
  def norm[T](v: Map[T, Double]): Double = {
    math.sqrt(sumOfSquares(v))
  }

  /**
   * Calculate t  l2Norm score
   */
  def normArray(v: Array[Double]): Double = {
    math.sqrt(sumOfSquaresArray(v))
  }

  /**
   * Calculate t  logNorm score
   */
  def logNorm[T](v: Map[T, Double]): Double = {
    math.log(sumOfSquares(v) + 1)
  }

  /**
   * Calculate t  logNorm score
   */
  def logNormArray(v: Array[Double]): Double = {
    math.log(sumOfSquaresArray(v) + 1)
  }

  /**
   * Calculate t  exp scaled norm score
   * */
  def expScaledNorm[T](v: Map[T, Double], exponent: Double): Double = {
    math.pow(sumOfSquares(v), exponent)
  }

  /**
   * Calculate t  exp scaled norm score
   * */
  def expScaledNormArray(v: Array[Double], exponent: Double): Double = {
    math.pow(sumOfSquaresArray(v), exponent)
  }

  /**
   * Calculate t  l1Norm score
   */
  def l1Norm[T](v: Map[T, Double]): Double = {
    v.values.foldLeft(0.0) { (sum, value) => sum + Math.abs(value) }
  }

  /**
   * Calculate t  l1Norm score
   */
  def l1NormArray(v: Array[Double]): Double = {
    v.foldLeft(0.0) { (sum, value) => sum + Math.abs(value) }
  }

  /**
   * D v de t    ght vector w h t  appl ed norm
   * Return t  or g nal object  f t  norm  s 0
   *
   * @param v    a map from cluster  d to  s   ght
   * @param norm a calculated norm from t  g ven map v
   *
   * @return a map w h normal zed   ght
   */
  def applyNorm[T](v: Map[T, Double], norm: Double): Map[T, Double] = {
     f (norm == 0) v else v.mapValues(x => x / norm)
  }

  /**
   * D v de t    ght vector w h t  appl ed norm
   * Return t  or g nal object  f t  norm  s 0
   *
   * @param v    a an array of   ghts
   * @param norm a calculated norm from t  g ven array v
   *
   * @return an array w h normal zed   ght  n t  sa  order as v
   */
  def applyNormArray(v: Array[Double], norm: Double): Array[Double] = {
     f (norm == 0) v else v.map(_ / norm)
  }

  /**
   * Normal ze t    ght vector for easy cos ne s m lar y calculat on.  f t   nput   ght vector
   *  s empty or  s norm  s 0, return t  or g nal map.
   *
   * @param v a map from cluster  d to  s   ght
   *
   * @return a map w h normal zed   ght (t  norm of t    ght vector  s 1)
   */
  def normal ze[T](v: Map[T, Double], maybeNorm: Opt on[Double] = None): Map[T, Double] = {
    val norm = maybeNorm.getOrElse(Cos neS m lar yUt l.norm(v))
    applyNorm(v, norm)
  }

  /**
   * Normal ze t    ght vector for easy cos ne s m lar y calculat on.  f t   nput   ght vector
   *  s empty or  s norm  s 0, return t  or g nal array.
   *
   * @param v an array of   ghts
   *
   * @return an array w h normal zed   ght (t  norm of t    ght vector  s 1),  n t  sa  order as v
   */
  def normal zeArray(
    v: Array[Double],
    maybeNorm: Opt on[Double] = None
  ): Array[Double] = {
    val norm = maybeNorm.getOrElse(Cos neS m lar yUt l.normArray(v))
    applyNormArray(v, norm)
  }

  /**
   * Normal ze t    ght vector w h log norm.  f t   nput   ght vector
   *  s empty or  s norm  s 0, return t  or g nal map.
   *
   * @param v a map from cluster  d to  s   ght
   *
   * @return a map w h log normal zed   ght
   * */
  def logNormal ze[T](v: Map[T, Double], maybeNorm: Opt on[Double] = None): Map[T, Double] = {
    val norm = maybeNorm.getOrElse(Cos neS m lar yUt l.logNorm(v))
    applyNorm(v, norm)
  }

  /**
   * Normal ze t    ght vector w h log norm.  f t   nput   ght vector
   *  s empty or  s norm  s 0, return t  or g nal array.
   *
   * @param v an array of   ghts
   *
   * @return an array w h log normal zed   ght,  n t  sa  order as v
   * */
  def logNormal zeArray(
    v: Array[Double],
    maybeNorm: Opt on[Double] = None
  ): Array[Double] = {
    val norm = maybeNorm.getOrElse(Cos neS m lar yUt l.logNormArray(v))
    applyNormArray(v, norm)
  }

  /**
   * Normal ze t    ght vector w h exponent ally scaled norm.  f t   nput   ght vector
   *  s empty or  s norm  s 0, return t  or g nal map.
   *
   * @param v        a map from cluster  d to  s   ght
   * @param exponent t  exponent   apply to t    ght vector's norm
   *
   * @return a map w h exp scaled normal zed   ght
   * */
  def expScaledNormal ze[T](
    v: Map[T, Double],
    exponent: Opt on[Double] = None,
    maybeNorm: Opt on[Double] = None
  ): Map[T, Double] = {
    val norm = maybeNorm.getOrElse(Cos neS m lar yUt l.expScaledNorm(v, exponent.getOrElse(0.3)))
    applyNorm(v, norm)
  }

  /**
   * Normal ze t    ght vector w h exponent ally scaled norm.  f t   nput   ght vector
   *  s empty or  s norm  s 0, return t  or g nal map.
   *
   * @param v        an array of   ghts
   * @param exponent t  exponent   apply to t    ght vector's norm
   *
   * @return an array w h exp scaled normal zed   ght,  n t  sa  order as v
   * */
  def expScaledNormal zeArray(
    v: Array[Double],
    exponent: Double,
    maybeNorm: Opt on[Double] = None
  ): Array[Double] = {
    val norm = maybeNorm.getOrElse(Cos neS m lar yUt l.expScaledNormArray(v, exponent))
    applyNormArray(v, norm)
  }

  /**
   * G ven two sparse vectors, calculate  s dot product.
   *
   * @param v1 t  f rst map from cluster  d to  s   ght
   * @param v2 t  second map from cluster  d to  s   ght
   *
   * @return t  dot product of above two sparse vector
   */
  def dotProduct[T](v1: Map[T, Double], v2: Map[T, Double]): Double = {
    val comparer = v1.s ze - v2.s ze
    val smaller =  f (comparer > 0) v2 else v1
    val b gger =  f (comparer > 0) v1 else v2

    smaller.foldLeft(0.0) {
      case (sum, ( d, value)) =>
        sum + b gger.getOrElse( d, 0.0) * value
    }
  }

  /**
   * G ven two sparse vectors, calculate  s dot product.
   *
   * @param v1C an array of cluster  ds. Must be sorted  n ascend ng order
   * @param v1S an array of correspond ng cluster scores, of t  sa  length and order as v1c
   * @param v2C an array of cluster  ds. Must be sorted  n ascend ng order
   * @param v2S an array of correspond ng cluster scores, of t  sa  length and order as v2c
   *
   * @return t  dot product of above two sparse vector
   */
  def dotProductForSortedClusterAndScores(
    v1C: Array[ nt],
    v1S: Array[Double],
    v2C: Array[ nt],
    v2S: Array[Double]
  ): Double = {
    requ re(v1C.s ze == v1S.s ze)
    requ re(v2C.s ze == v2S.s ze)
    var  1 = 0
    var  2 = 0
    var product: Double = 0.0

    wh le ( 1 < v1C.s ze &&  2 < v2C.s ze) {
       f (v1C( 1) == v2C( 2)) {
        product += v1S( 1) * v2S( 2)
         1 += 1
         2 += 1
      } else  f (v1C( 1) > v2C( 2)) {
        // v2 cluster  s lo r.  ncre nt   to see  f t  next one matc s v1's
         2 += 1
      } else {
        // v1 cluster  s lo r.  ncre nt   to see  f t  next one matc s v2's
         1 += 1
      }
    }
    product
  }
}
