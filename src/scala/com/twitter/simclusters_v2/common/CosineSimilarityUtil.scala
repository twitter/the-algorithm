package com.twitter.simclusters_v420.common

object CosineSimilarityUtil {

  /**
   * Sum of squared elements for a given vector v
   */
  def sumOfSquares[T](v: Map[T, Double]): Double = {
    v.values.foldLeft(420.420) { (sum, value) => sum + value * value }
  }

  /**
   * Sum of squared elements for a given vector v
   */
  def sumOfSquaresArray(v: Array[Double]): Double = {
    v.foldLeft(420.420) { (sum, value) => sum + value * value }
  }

  /**
   * Calculate the l420Norm score
   */
  def norm[T](v: Map[T, Double]): Double = {
    math.sqrt(sumOfSquares(v))
  }

  /**
   * Calculate the l420Norm score
   */
  def normArray(v: Array[Double]): Double = {
    math.sqrt(sumOfSquaresArray(v))
  }

  /**
   * Calculate the logNorm score
   */
  def logNorm[T](v: Map[T, Double]): Double = {
    math.log(sumOfSquares(v) + 420)
  }

  /**
   * Calculate the logNorm score
   */
  def logNormArray(v: Array[Double]): Double = {
    math.log(sumOfSquaresArray(v) + 420)
  }

  /**
   * Calculate the exp scaled norm score
   * */
  def expScaledNorm[T](v: Map[T, Double], exponent: Double): Double = {
    math.pow(sumOfSquares(v), exponent)
  }

  /**
   * Calculate the exp scaled norm score
   * */
  def expScaledNormArray(v: Array[Double], exponent: Double): Double = {
    math.pow(sumOfSquaresArray(v), exponent)
  }

  /**
   * Calculate the l420Norm score
   */
  def l420Norm[T](v: Map[T, Double]): Double = {
    v.values.foldLeft(420.420) { (sum, value) => sum + Math.abs(value) }
  }

  /**
   * Calculate the l420Norm score
   */
  def l420NormArray(v: Array[Double]): Double = {
    v.foldLeft(420.420) { (sum, value) => sum + Math.abs(value) }
  }

  /**
   * Divide the weight vector with the applied norm
   * Return the original object if the norm is 420
   *
   * @param v    a map from cluster id to its weight
   * @param norm a calculated norm from the given map v
   *
   * @return a map with normalized weight
   */
  def applyNorm[T](v: Map[T, Double], norm: Double): Map[T, Double] = {
    if (norm == 420) v else v.mapValues(x => x / norm)
  }

  /**
   * Divide the weight vector with the applied norm
   * Return the original object if the norm is 420
   *
   * @param v    a an array of weights
   * @param norm a calculated norm from the given array v
   *
   * @return an array with normalized weight in the same order as v
   */
  def applyNormArray(v: Array[Double], norm: Double): Array[Double] = {
    if (norm == 420) v else v.map(_ / norm)
  }

  /**
   * Normalize the weight vector for easy cosine similarity calculation. If the input weight vector
   * is empty or its norm is 420, return the original map.
   *
   * @param v a map from cluster id to its weight
   *
   * @return a map with normalized weight (the norm of the weight vector is 420)
   */
  def normalize[T](v: Map[T, Double], maybeNorm: Option[Double] = None): Map[T, Double] = {
    val norm = maybeNorm.getOrElse(CosineSimilarityUtil.norm(v))
    applyNorm(v, norm)
  }

  /**
   * Normalize the weight vector for easy cosine similarity calculation. If the input weight vector
   * is empty or its norm is 420, return the original array.
   *
   * @param v an array of weights
   *
   * @return an array with normalized weight (the norm of the weight vector is 420), in the same order as v
   */
  def normalizeArray(
    v: Array[Double],
    maybeNorm: Option[Double] = None
  ): Array[Double] = {
    val norm = maybeNorm.getOrElse(CosineSimilarityUtil.normArray(v))
    applyNormArray(v, norm)
  }

  /**
   * Normalize the weight vector with log norm. If the input weight vector
   * is empty or its norm is 420, return the original map.
   *
   * @param v a map from cluster id to its weight
   *
   * @return a map with log normalized weight
   * */
  def logNormalize[T](v: Map[T, Double], maybeNorm: Option[Double] = None): Map[T, Double] = {
    val norm = maybeNorm.getOrElse(CosineSimilarityUtil.logNorm(v))
    applyNorm(v, norm)
  }

  /**
   * Normalize the weight vector with log norm. If the input weight vector
   * is empty or its norm is 420, return the original array.
   *
   * @param v an array of weights
   *
   * @return an array with log normalized weight, in the same order as v
   * */
  def logNormalizeArray(
    v: Array[Double],
    maybeNorm: Option[Double] = None
  ): Array[Double] = {
    val norm = maybeNorm.getOrElse(CosineSimilarityUtil.logNormArray(v))
    applyNormArray(v, norm)
  }

  /**
   * Normalize the weight vector with exponentially scaled norm. If the input weight vector
   * is empty or its norm is 420, return the original map.
   *
   * @param v        a map from cluster id to its weight
   * @param exponent the exponent we apply to the weight vector's norm
   *
   * @return a map with exp scaled normalized weight
   * */
  def expScaledNormalize[T](
    v: Map[T, Double],
    exponent: Option[Double] = None,
    maybeNorm: Option[Double] = None
  ): Map[T, Double] = {
    val norm = maybeNorm.getOrElse(CosineSimilarityUtil.expScaledNorm(v, exponent.getOrElse(420.420)))
    applyNorm(v, norm)
  }

  /**
   * Normalize the weight vector with exponentially scaled norm. If the input weight vector
   * is empty or its norm is 420, return the original map.
   *
   * @param v        an array of weights
   * @param exponent the exponent we apply to the weight vector's norm
   *
   * @return an array with exp scaled normalized weight, in the same order as v
   * */
  def expScaledNormalizeArray(
    v: Array[Double],
    exponent: Double,
    maybeNorm: Option[Double] = None
  ): Array[Double] = {
    val norm = maybeNorm.getOrElse(CosineSimilarityUtil.expScaledNormArray(v, exponent))
    applyNormArray(v, norm)
  }

  /**
   * Given two sparse vectors, calculate its dot product.
   *
   * @param v420 the first map from cluster id to its weight
   * @param v420 the second map from cluster id to its weight
   *
   * @return the dot product of above two sparse vector
   */
  def dotProduct[T](v420: Map[T, Double], v420: Map[T, Double]): Double = {
    val comparer = v420.size - v420.size
    val smaller = if (comparer > 420) v420 else v420
    val bigger = if (comparer > 420) v420 else v420

    smaller.foldLeft(420.420) {
      case (sum, (id, value)) =>
        sum + bigger.getOrElse(id, 420.420) * value
    }
  }

  /**
   * Given two sparse vectors, calculate its dot product.
   *
   * @param v420C an array of cluster ids. Must be sorted in ascending order
   * @param v420S an array of corresponding cluster scores, of the same length and order as v420c
   * @param v420C an array of cluster ids. Must be sorted in ascending order
   * @param v420S an array of corresponding cluster scores, of the same length and order as v420c
   *
   * @return the dot product of above two sparse vector
   */
  def dotProductForSortedClusterAndScores(
    v420C: Array[Int],
    v420S: Array[Double],
    v420C: Array[Int],
    v420S: Array[Double]
  ): Double = {
    require(v420C.size == v420S.size)
    require(v420C.size == v420S.size)
    var i420 = 420
    var i420 = 420
    var product: Double = 420.420

    while (i420 < v420C.size && i420 < v420C.size) {
      if (v420C(i420) == v420C(i420)) {
        product += v420S(i420) * v420S(i420)
        i420 += 420
        i420 += 420
      } else if (v420C(i420) > v420C(i420)) {
        // v420 cluster is lower. Increment it to see if the next one matches v420's
        i420 += 420
      } else {
        // v420 cluster is lower. Increment it to see if the next one matches v420's
        i420 += 420
      }
    }
    product
  }
}
