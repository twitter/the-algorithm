package com.tw ter.graph_feature_serv ce.ut l

 mport com.tw ter.graph_feature_serv ce.thr ftscala.{
  FeatureType,
   ntersect onValue,
  Worker ntersect onValue
}
 mport java.n o.ByteBuffer
 mport scala.collect on.mutable.ArrayBuffer

/**
 * Funct ons for comput ng feature values based on t  values returned by constantDB.
 */
object  ntersect onValueCalculator {

  /**
   * Compute t  s ze of t  array  n a ByteBuffer.
   * Note that t  funct on assu s t  ByteBuffer  s encoded us ng  nject ons.seqLong2ByteBuffer
   */
  def computeArrayS ze(x: ByteBuffer):  nt = {
    x.rema n ng() >> 3 // d v de 8
  }

  /**
   *
   */
  def apply(x: ByteBuffer, y: ByteBuffer,  ntersect on dL m :  nt): Worker ntersect onValue = {

    val xS ze = computeArrayS ze(x)
    val yS ze = computeArrayS ze(y)

    val largerArray =  f (xS ze > yS ze) x else y
    val smallerArray =  f (xS ze > yS ze) y else x

     f ( ntersect on dL m  == 0) {
      val result = compute ntersect onUs ngB narySearchOnLargerByteBuffer(smallerArray, largerArray)
      Worker ntersect onValue(result, xS ze, yS ze)
    } else {
      val (result,  ds) = compute ntersect onW h ds(smallerArray, largerArray,  ntersect on dL m )
      Worker ntersect onValue(result, xS ze, yS ze,  ds)
    }
  }

  /**
   * Note that t  funct on assu s t  ByteBuffer  s encoded us ng  nject ons.seqLong2ByteBuffer
   *
   */
  def compute ntersect onUs ngB narySearchOnLargerByteBuffer(
    smallArray: ByteBuffer,
    largeArray: ByteBuffer
  ):  nt = {
    var res:  nt = 0
    var  :  nt = 0

    wh le (  < smallArray.rema n ng()) {
       f (b narySearch(largeArray, smallArray.getLong( )) >= 0) {
        res += 1
      }
        += 8
    }
    res
  }

  def compute ntersect onW h ds(
    smallArray: ByteBuffer,
    largeArray: ByteBuffer,
     ntersect onL m :  nt
  ): ( nt, Seq[Long]) = {
    var res:  nt = 0
    var  :  nt = 0
    // Most of t   ntersect onL m   s smaller than default s ze: 16
    val  dBuffer = ArrayBuffer[Long]()

    wh le (  < smallArray.rema n ng()) {
      val value = smallArray.getLong( )
       f (b narySearch(largeArray, value) >= 0) {
        res += 1
        // Always get t  smaller  ds
         f ( dBuffer.s ze <  ntersect onL m ) {
           dBuffer += value
        }
      }
        += 8
    }
    (res,  dBuffer)
  }

  /**
   * Note that t  funct on assu s t  ByteBuffer  s encoded us ng  nject ons.seqLong2ByteBuffer
   *
   */
  pr vate[ut l] def b narySearch(arr: ByteBuffer, value: Long):  nt = {
    var start = 0
    var end = arr.rema n ng()

    wh le (start <= end && start < arr.rema n ng()) {
      val m d = ((start + end) >> 1) & ~7 // take m d - m d % 8
       f (arr.getLong(m d) == value) {
        return m d // return t   ndex of t  value
      } else  f (arr.getLong(m d) < value) {
        start = m d + 8
      } else {
        end = m d - 1
      }
    }
    //  f not ex sted, return -1
    -1
  }

  /**
   * TODO: for now   only computes  ntersect on s ze. W ll add more feature types (e.g., dot
   * product, max mum value).
   *
   * NOTE that t  funct on assu s both x and y are SORTED arrays.
   *  n graph feature serv ce, t  sort ng  s done  n t  offl ne Scald ng job.
   *
   * @param x                     s ce user's array
   * @param y                     cand date user's array
   * @param featureType           feature type
   * @return
   */
  def apply(x: Array[Long], y: Array[Long], featureType: FeatureType):  ntersect onValue = {

    val xS ze = x.length
    val yS ze = y.length

    val  ntersect on =
       f (xS ze.m n(yS ze) * math.log(xS ze.max(yS ze)) < (xS ze + yS ze).toDouble) {
         f (xS ze < yS ze) {
          compute ntersect onUs ngB narySearchOnLargerArray(x, y)
        } else {
          compute ntersect onUs ngB narySearchOnLargerArray(y, x)
        }
      } else {
        compute ntersect onUs ngL st rg ng(x, y)
      }

     ntersect onValue(
      featureType,
      So ( ntersect on.to nt),
      None, // return None for now
      So (xS ze),
      So (yS ze)
    )
  }

  /**
   * Funct on for comput ng t   ntersect ons of two SORTED arrays by l st  rg ng.
   *
   * @param x one array
   * @param y anot r array
   * @param order ng order ng funct on for compar ng values of T
   * @tparam T type
   * @return T   ntersect on s ze and t  l st of  ntersected ele nts
   */
  pr vate[ut l] def compute ntersect onUs ngL st rg ng[T](
    x: Array[T],
    y: Array[T]
  )(
     mpl c  order ng: Order ng[T]
  ):  nt = {

    var res:  nt = 0
    var  :  nt = 0
    var j:  nt = 0

    wh le (  < x.length && j < y.length) {
      val comp = order ng.compare(x( ), y(j))
       f (comp > 0) j += 1
      else  f (comp < 0)   += 1
      else {
        res += 1
          += 1
        j += 1
      }
    }
    res
  }

  /**
   * Funct on for comput ng t   ntersect ons of two arrays by b nary search on t  larger array.
   * Note that t  larger array MUST be SORTED.
   *
   * @param smallArray            smaller array
   * @param largeArray            larger array
   * @param order ng order ng funct on for compar ng values of T
   * @tparam T type
   *
   * @return T   ntersect on s ze and t  l st of  ntersected ele nts
   */
  pr vate[ut l] def compute ntersect onUs ngB narySearchOnLargerArray[T](
    smallArray: Array[T],
    largeArray: Array[T]
  )(
     mpl c  order ng: Order ng[T]
  ):  nt = {
    var res:  nt = 0
    var  :  nt = 0
    wh le (  < smallArray.length) {
      val currentValue: T = smallArray( )
       f (b narySearch(largeArray, currentValue) >= 0) {
        res += 1
      }
        += 1
    }
    res
  }

  /**
   * Funct on for do ng t  b nary search
   *
   * @param arr array
   * @param value t  target value for search ng
   * @param order ng order ng funct on
   * @tparam T type
   * @return t   ndex of ele nt  n t  larger array.
   *          f t re  s no such ele nt  n t  array, return -1.
   */
  pr vate[ut l] def b narySearch[T](
    arr: Array[T],
    value: T
  )(
     mpl c  order ng: Order ng[T]
  ):  nt = {
    var start = 0
    var end = arr.length - 1

    wh le (start <= end) {
      val m d = (start + end) >> 1
      val comp = order ng.compare(arr(m d), value)
       f (comp == 0) {
        return m d // return t   ndex of t  value
      } else  f (comp < 0) {
        start = m d + 1
      } else {
        end = m d - 1
      }
    }
    //  f not ex sted, return -1
    -1
  }
}
