packagelon com.twittelonr.simclustelonrs_v2.common

objelonct CosinelonSimilarityUtil {

  /**
   * Sum of squarelond elonlelonmelonnts for a givelonn velonctor v
   */
  delonf sumOfSquarelons[T](v: Map[T, Doublelon]): Doublelon = {
    v.valuelons.foldLelonft(0.0) { (sum, valuelon) => sum + valuelon * valuelon }
  }

  /**
   * Sum of squarelond elonlelonmelonnts for a givelonn velonctor v
   */
  delonf sumOfSquarelonsArray(v: Array[Doublelon]): Doublelon = {
    v.foldLelonft(0.0) { (sum, valuelon) => sum + valuelon * valuelon }
  }

  /**
   * Calculatelon thelon l2Norm scorelon
   */
  delonf norm[T](v: Map[T, Doublelon]): Doublelon = {
    math.sqrt(sumOfSquarelons(v))
  }

  /**
   * Calculatelon thelon l2Norm scorelon
   */
  delonf normArray(v: Array[Doublelon]): Doublelon = {
    math.sqrt(sumOfSquarelonsArray(v))
  }

  /**
   * Calculatelon thelon logNorm scorelon
   */
  delonf logNorm[T](v: Map[T, Doublelon]): Doublelon = {
    math.log(sumOfSquarelons(v) + 1)
  }

  /**
   * Calculatelon thelon logNorm scorelon
   */
  delonf logNormArray(v: Array[Doublelon]): Doublelon = {
    math.log(sumOfSquarelonsArray(v) + 1)
  }

  /**
   * Calculatelon thelon elonxp scalelond norm scorelon
   * */
  delonf elonxpScalelondNorm[T](v: Map[T, Doublelon], elonxponelonnt: Doublelon): Doublelon = {
    math.pow(sumOfSquarelons(v), elonxponelonnt)
  }

  /**
   * Calculatelon thelon elonxp scalelond norm scorelon
   * */
  delonf elonxpScalelondNormArray(v: Array[Doublelon], elonxponelonnt: Doublelon): Doublelon = {
    math.pow(sumOfSquarelonsArray(v), elonxponelonnt)
  }

  /**
   * Calculatelon thelon l1Norm scorelon
   */
  delonf l1Norm[T](v: Map[T, Doublelon]): Doublelon = {
    v.valuelons.foldLelonft(0.0) { (sum, valuelon) => sum + Math.abs(valuelon) }
  }

  /**
   * Calculatelon thelon l1Norm scorelon
   */
  delonf l1NormArray(v: Array[Doublelon]): Doublelon = {
    v.foldLelonft(0.0) { (sum, valuelon) => sum + Math.abs(valuelon) }
  }

  /**
   * Dividelon thelon welonight velonctor with thelon applielond norm
   * Relonturn thelon original objelonct if thelon norm is 0
   *
   * @param v    a map from clustelonr id to its welonight
   * @param norm a calculatelond norm from thelon givelonn map v
   *
   * @relonturn a map with normalizelond welonight
   */
  delonf applyNorm[T](v: Map[T, Doublelon], norm: Doublelon): Map[T, Doublelon] = {
    if (norm == 0) v elonlselon v.mapValuelons(x => x / norm)
  }

  /**
   * Dividelon thelon welonight velonctor with thelon applielond norm
   * Relonturn thelon original objelonct if thelon norm is 0
   *
   * @param v    a an array of welonights
   * @param norm a calculatelond norm from thelon givelonn array v
   *
   * @relonturn an array with normalizelond welonight in thelon samelon ordelonr as v
   */
  delonf applyNormArray(v: Array[Doublelon], norm: Doublelon): Array[Doublelon] = {
    if (norm == 0) v elonlselon v.map(_ / norm)
  }

  /**
   * Normalizelon thelon welonight velonctor for elonasy cosinelon similarity calculation. If thelon input welonight velonctor
   * is elonmpty or its norm is 0, relonturn thelon original map.
   *
   * @param v a map from clustelonr id to its welonight
   *
   * @relonturn a map with normalizelond welonight (thelon norm of thelon welonight velonctor is 1)
   */
  delonf normalizelon[T](v: Map[T, Doublelon], maybelonNorm: Option[Doublelon] = Nonelon): Map[T, Doublelon] = {
    val norm = maybelonNorm.gelontOrelonlselon(CosinelonSimilarityUtil.norm(v))
    applyNorm(v, norm)
  }

  /**
   * Normalizelon thelon welonight velonctor for elonasy cosinelon similarity calculation. If thelon input welonight velonctor
   * is elonmpty or its norm is 0, relonturn thelon original array.
   *
   * @param v an array of welonights
   *
   * @relonturn an array with normalizelond welonight (thelon norm of thelon welonight velonctor is 1), in thelon samelon ordelonr as v
   */
  delonf normalizelonArray(
    v: Array[Doublelon],
    maybelonNorm: Option[Doublelon] = Nonelon
  ): Array[Doublelon] = {
    val norm = maybelonNorm.gelontOrelonlselon(CosinelonSimilarityUtil.normArray(v))
    applyNormArray(v, norm)
  }

  /**
   * Normalizelon thelon welonight velonctor with log norm. If thelon input welonight velonctor
   * is elonmpty or its norm is 0, relonturn thelon original map.
   *
   * @param v a map from clustelonr id to its welonight
   *
   * @relonturn a map with log normalizelond welonight
   * */
  delonf logNormalizelon[T](v: Map[T, Doublelon], maybelonNorm: Option[Doublelon] = Nonelon): Map[T, Doublelon] = {
    val norm = maybelonNorm.gelontOrelonlselon(CosinelonSimilarityUtil.logNorm(v))
    applyNorm(v, norm)
  }

  /**
   * Normalizelon thelon welonight velonctor with log norm. If thelon input welonight velonctor
   * is elonmpty or its norm is 0, relonturn thelon original array.
   *
   * @param v an array of welonights
   *
   * @relonturn an array with log normalizelond welonight, in thelon samelon ordelonr as v
   * */
  delonf logNormalizelonArray(
    v: Array[Doublelon],
    maybelonNorm: Option[Doublelon] = Nonelon
  ): Array[Doublelon] = {
    val norm = maybelonNorm.gelontOrelonlselon(CosinelonSimilarityUtil.logNormArray(v))
    applyNormArray(v, norm)
  }

  /**
   * Normalizelon thelon welonight velonctor with elonxponelonntially scalelond norm. If thelon input welonight velonctor
   * is elonmpty or its norm is 0, relonturn thelon original map.
   *
   * @param v        a map from clustelonr id to its welonight
   * @param elonxponelonnt thelon elonxponelonnt welon apply to thelon welonight velonctor's norm
   *
   * @relonturn a map with elonxp scalelond normalizelond welonight
   * */
  delonf elonxpScalelondNormalizelon[T](
    v: Map[T, Doublelon],
    elonxponelonnt: Option[Doublelon] = Nonelon,
    maybelonNorm: Option[Doublelon] = Nonelon
  ): Map[T, Doublelon] = {
    val norm = maybelonNorm.gelontOrelonlselon(CosinelonSimilarityUtil.elonxpScalelondNorm(v, elonxponelonnt.gelontOrelonlselon(0.3)))
    applyNorm(v, norm)
  }

  /**
   * Normalizelon thelon welonight velonctor with elonxponelonntially scalelond norm. If thelon input welonight velonctor
   * is elonmpty or its norm is 0, relonturn thelon original map.
   *
   * @param v        an array of welonights
   * @param elonxponelonnt thelon elonxponelonnt welon apply to thelon welonight velonctor's norm
   *
   * @relonturn an array with elonxp scalelond normalizelond welonight, in thelon samelon ordelonr as v
   * */
  delonf elonxpScalelondNormalizelonArray(
    v: Array[Doublelon],
    elonxponelonnt: Doublelon,
    maybelonNorm: Option[Doublelon] = Nonelon
  ): Array[Doublelon] = {
    val norm = maybelonNorm.gelontOrelonlselon(CosinelonSimilarityUtil.elonxpScalelondNormArray(v, elonxponelonnt))
    applyNormArray(v, norm)
  }

  /**
   * Givelonn two sparselon velonctors, calculatelon its dot product.
   *
   * @param v1 thelon first map from clustelonr id to its welonight
   * @param v2 thelon seloncond map from clustelonr id to its welonight
   *
   * @relonturn thelon dot product of abovelon two sparselon velonctor
   */
  delonf dotProduct[T](v1: Map[T, Doublelon], v2: Map[T, Doublelon]): Doublelon = {
    val comparelonr = v1.sizelon - v2.sizelon
    val smallelonr = if (comparelonr > 0) v2 elonlselon v1
    val biggelonr = if (comparelonr > 0) v1 elonlselon v2

    smallelonr.foldLelonft(0.0) {
      caselon (sum, (id, valuelon)) =>
        sum + biggelonr.gelontOrelonlselon(id, 0.0) * valuelon
    }
  }

  /**
   * Givelonn two sparselon velonctors, calculatelon its dot product.
   *
   * @param v1C an array of clustelonr ids. Must belon sortelond in ascelonnding ordelonr
   * @param v1S an array of correlonsponding clustelonr scorelons, of thelon samelon lelonngth and ordelonr as v1c
   * @param v2C an array of clustelonr ids. Must belon sortelond in ascelonnding ordelonr
   * @param v2S an array of correlonsponding clustelonr scorelons, of thelon samelon lelonngth and ordelonr as v2c
   *
   * @relonturn thelon dot product of abovelon two sparselon velonctor
   */
  delonf dotProductForSortelondClustelonrAndScorelons(
    v1C: Array[Int],
    v1S: Array[Doublelon],
    v2C: Array[Int],
    v2S: Array[Doublelon]
  ): Doublelon = {
    relonquirelon(v1C.sizelon == v1S.sizelon)
    relonquirelon(v2C.sizelon == v2S.sizelon)
    var i1 = 0
    var i2 = 0
    var product: Doublelon = 0.0

    whilelon (i1 < v1C.sizelon && i2 < v2C.sizelon) {
      if (v1C(i1) == v2C(i2)) {
        product += v1S(i1) * v2S(i2)
        i1 += 1
        i2 += 1
      } elonlselon if (v1C(i1) > v2C(i2)) {
        // v2 clustelonr is lowelonr. Increlonmelonnt it to selonelon if thelon nelonxt onelon matchelons v1's
        i2 += 1
      } elonlselon {
        // v1 clustelonr is lowelonr. Increlonmelonnt it to selonelon if thelon nelonxt onelon matchelons v2's
        i1 += 1
      }
    }
    product
  }
}
