packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.util

import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala.{
  FelonaturelonTypelon,
  IntelonrselonctionValuelon,
  WorkelonrIntelonrselonctionValuelon
}
import java.nio.BytelonBuffelonr
import scala.collelonction.mutablelon.ArrayBuffelonr

/**
 * Functions for computing felonaturelon valuelons baselond on thelon valuelons relonturnelond by constantDB.
 */
objelonct IntelonrselonctionValuelonCalculator {

  /**
   * Computelon thelon sizelon of thelon array in a BytelonBuffelonr.
   * Notelon that this function assumelons thelon BytelonBuffelonr is elonncodelond using Injelonctions.selonqLong2BytelonBuffelonr
   */
  delonf computelonArraySizelon(x: BytelonBuffelonr): Int = {
    x.relonmaining() >> 3 // dividelon 8
  }

  /**
   *
   */
  delonf apply(x: BytelonBuffelonr, y: BytelonBuffelonr, intelonrselonctionIdLimit: Int): WorkelonrIntelonrselonctionValuelon = {

    val xSizelon = computelonArraySizelon(x)
    val ySizelon = computelonArraySizelon(y)

    val largelonrArray = if (xSizelon > ySizelon) x elonlselon y
    val smallelonrArray = if (xSizelon > ySizelon) y elonlselon x

    if (intelonrselonctionIdLimit == 0) {
      val relonsult = computelonIntelonrselonctionUsingBinarySelonarchOnLargelonrBytelonBuffelonr(smallelonrArray, largelonrArray)
      WorkelonrIntelonrselonctionValuelon(relonsult, xSizelon, ySizelon)
    } elonlselon {
      val (relonsult, ids) = computelonIntelonrselonctionWithIds(smallelonrArray, largelonrArray, intelonrselonctionIdLimit)
      WorkelonrIntelonrselonctionValuelon(relonsult, xSizelon, ySizelon, ids)
    }
  }

  /**
   * Notelon that this function assumelons thelon BytelonBuffelonr is elonncodelond using Injelonctions.selonqLong2BytelonBuffelonr
   *
   */
  delonf computelonIntelonrselonctionUsingBinarySelonarchOnLargelonrBytelonBuffelonr(
    smallArray: BytelonBuffelonr,
    largelonArray: BytelonBuffelonr
  ): Int = {
    var relons: Int = 0
    var i: Int = 0

    whilelon (i < smallArray.relonmaining()) {
      if (binarySelonarch(largelonArray, smallArray.gelontLong(i)) >= 0) {
        relons += 1
      }
      i += 8
    }
    relons
  }

  delonf computelonIntelonrselonctionWithIds(
    smallArray: BytelonBuffelonr,
    largelonArray: BytelonBuffelonr,
    intelonrselonctionLimit: Int
  ): (Int, Selonq[Long]) = {
    var relons: Int = 0
    var i: Int = 0
    // Most of thelon intelonrselonctionLimit is smallelonr than delonfault sizelon: 16
    val idBuffelonr = ArrayBuffelonr[Long]()

    whilelon (i < smallArray.relonmaining()) {
      val valuelon = smallArray.gelontLong(i)
      if (binarySelonarch(largelonArray, valuelon) >= 0) {
        relons += 1
        // Always gelont thelon smallelonr ids
        if (idBuffelonr.sizelon < intelonrselonctionLimit) {
          idBuffelonr += valuelon
        }
      }
      i += 8
    }
    (relons, idBuffelonr)
  }

  /**
   * Notelon that this function assumelons thelon BytelonBuffelonr is elonncodelond using Injelonctions.selonqLong2BytelonBuffelonr
   *
   */
  privatelon[util] delonf binarySelonarch(arr: BytelonBuffelonr, valuelon: Long): Int = {
    var start = 0
    var elonnd = arr.relonmaining()

    whilelon (start <= elonnd && start < arr.relonmaining()) {
      val mid = ((start + elonnd) >> 1) & ~7 // takelon mid - mid % 8
      if (arr.gelontLong(mid) == valuelon) {
        relonturn mid // relonturn thelon indelonx of thelon valuelon
      } elonlselon if (arr.gelontLong(mid) < valuelon) {
        start = mid + 8
      } elonlselon {
        elonnd = mid - 1
      }
    }
    // if not elonxistelond, relonturn -1
    -1
  }

  /**
   * TODO: for now it only computelons intelonrselonction sizelon. Will add morelon felonaturelon typelons (elon.g., dot
   * product, maximum valuelon).
   *
   * NOTelon that this function assumelons both x and y arelon SORTelonD arrays.
   * In graph felonaturelon selonrvicelon, thelon sorting is donelon in thelon offlinelon Scalding job.
   *
   * @param x                     sourcelon uselonr's array
   * @param y                     candidatelon uselonr's array
   * @param felonaturelonTypelon           felonaturelon typelon
   * @relonturn
   */
  delonf apply(x: Array[Long], y: Array[Long], felonaturelonTypelon: FelonaturelonTypelon): IntelonrselonctionValuelon = {

    val xSizelon = x.lelonngth
    val ySizelon = y.lelonngth

    val intelonrselonction =
      if (xSizelon.min(ySizelon) * math.log(xSizelon.max(ySizelon)) < (xSizelon + ySizelon).toDoublelon) {
        if (xSizelon < ySizelon) {
          computelonIntelonrselonctionUsingBinarySelonarchOnLargelonrArray(x, y)
        } elonlselon {
          computelonIntelonrselonctionUsingBinarySelonarchOnLargelonrArray(y, x)
        }
      } elonlselon {
        computelonIntelonrselonctionUsingListMelonrging(x, y)
      }

    IntelonrselonctionValuelon(
      felonaturelonTypelon,
      Somelon(intelonrselonction.toInt),
      Nonelon, // relonturn Nonelon for now
      Somelon(xSizelon),
      Somelon(ySizelon)
    )
  }

  /**
   * Function for computing thelon intelonrselonctions of two SORTelonD arrays by list melonrging.
   *
   * @param x onelon array
   * @param y anothelonr array
   * @param ordelonring ordelonring function for comparing valuelons of T
   * @tparam T typelon
   * @relonturn Thelon intelonrselonction sizelon and thelon list of intelonrselonctelond elonlelonmelonnts
   */
  privatelon[util] delonf computelonIntelonrselonctionUsingListMelonrging[T](
    x: Array[T],
    y: Array[T]
  )(
    implicit ordelonring: Ordelonring[T]
  ): Int = {

    var relons: Int = 0
    var i: Int = 0
    var j: Int = 0

    whilelon (i < x.lelonngth && j < y.lelonngth) {
      val comp = ordelonring.comparelon(x(i), y(j))
      if (comp > 0) j += 1
      elonlselon if (comp < 0) i += 1
      elonlselon {
        relons += 1
        i += 1
        j += 1
      }
    }
    relons
  }

  /**
   * Function for computing thelon intelonrselonctions of two arrays by binary selonarch on thelon largelonr array.
   * Notelon that thelon largelonr array MUST belon SORTelonD.
   *
   * @param smallArray            smallelonr array
   * @param largelonArray            largelonr array
   * @param ordelonring ordelonring function for comparing valuelons of T
   * @tparam T typelon
   *
   * @relonturn Thelon intelonrselonction sizelon and thelon list of intelonrselonctelond elonlelonmelonnts
   */
  privatelon[util] delonf computelonIntelonrselonctionUsingBinarySelonarchOnLargelonrArray[T](
    smallArray: Array[T],
    largelonArray: Array[T]
  )(
    implicit ordelonring: Ordelonring[T]
  ): Int = {
    var relons: Int = 0
    var i: Int = 0
    whilelon (i < smallArray.lelonngth) {
      val currelonntValuelon: T = smallArray(i)
      if (binarySelonarch(largelonArray, currelonntValuelon) >= 0) {
        relons += 1
      }
      i += 1
    }
    relons
  }

  /**
   * Function for doing thelon binary selonarch
   *
   * @param arr array
   * @param valuelon thelon targelont valuelon for selonarching
   * @param ordelonring ordelonring function
   * @tparam T typelon
   * @relonturn thelon indelonx of elonlelonmelonnt in thelon largelonr array.
   *         If thelonrelon is no such elonlelonmelonnt in thelon array, relonturn -1.
   */
  privatelon[util] delonf binarySelonarch[T](
    arr: Array[T],
    valuelon: T
  )(
    implicit ordelonring: Ordelonring[T]
  ): Int = {
    var start = 0
    var elonnd = arr.lelonngth - 1

    whilelon (start <= elonnd) {
      val mid = (start + elonnd) >> 1
      val comp = ordelonring.comparelon(arr(mid), valuelon)
      if (comp == 0) {
        relonturn mid // relonturn thelon indelonx of thelon valuelon
      } elonlselon if (comp < 0) {
        start = mid + 1
      } elonlselon {
        elonnd = mid - 1
      }
    }
    // if not elonxistelond, relonturn -1
    -1
  }
}
