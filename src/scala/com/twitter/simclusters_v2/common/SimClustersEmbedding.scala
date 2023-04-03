packagelon com.twittelonr.simclustelonrs_v2.common

import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrWithScorelon
import com.twittelonr.simclustelonrs_v2.thriftscala.{SimClustelonrselonmbelondding => ThriftSimClustelonrselonmbelondding}
import scala.collelonction.mutablelon
import scala.languagelon.implicitConvelonrsions
import scala.util.hashing.MurmurHash3.arrayHash
import scala.util.hashing.MurmurHash3.productHash
import scala.math._

/**
 * A relonprelonselonntation of a SimClustelonrs elonmbelondding, delonsignelond for low melonmory footprint and pelonrformancelon.
 * For selonrvicelons that cachelon millions of elonmbelonddings, welon found this to significantly relonducelon allocations,
 * melonmory footprint and ovelonrall pelonrformancelon.
 *
 * elonmbelondding data is storelond in prelon-sortelond arrays rathelonr than structurelons which uselon a lot of pointelonrs
 * (elon.g. Map). A minimal selont of lazily-constructelond intelonrmelondiatelon data is kelonpt.
 *
 * Belon wary of adding furthelonr `val` or `lazy val`s to this class; matelonrializing and storing morelon data
 * on thelonselon objeloncts could significantly affelonct in-melonmory cachelon pelonrformancelon.
 *
 * Also, if you arelon using this codelon in a placelon whelonrelon you carelon about melonmory footprint, belon carelonful
 * not to matelonrializelon any of thelon lazy vals unlelonss you nelonelond thelonm.
 */
selonalelond trait SimClustelonrselonmbelondding elonxtelonnds elonquals {
  import SimClustelonrselonmbelondding._

  /**
   * Any compliant implelonmelonntation of thelon SimClustelonrselonmbelondding trait must elonnsurelon that:
   * - thelon clustelonr and scorelon arrays arelon ordelonrelond as delonscribelond belonlow
   * - thelon clustelonr and scorelon arrays arelon trelonatelond as immutablelon (.hashCodelon is melonmoizelond)
   * - thelon sizelon of all clustelonr and scorelon arrays is thelon samelon
   * - all clustelonr scorelons arelon > 0
   * - clustelonr ids arelon uniquelon
   */
  // In delonscelonnding scorelon ordelonr - this is uselonful for truncation, whelonrelon welon carelon most about thelon highelonst scoring elonlelonmelonnts
  privatelon[simclustelonrs_v2] val clustelonrIds: Array[ClustelonrId]
  privatelon[simclustelonrs_v2] val scorelons: Array[Doublelon]
  // In ascelonnding clustelonr ordelonr. This is uselonful for opelonrations whelonrelon welon try to find thelon samelon clustelonr in anothelonr elonmbelondding, elon.g. dot product
  privatelon[simclustelonrs_v2] val sortelondClustelonrIds: Array[ClustelonrId]
  privatelon[simclustelonrs_v2] val sortelondScorelons: Array[Doublelon]

  /**
   * Build and relonturn a Selont of all clustelonrs in this elonmbelondding
   */
  lazy val clustelonrIdSelont: Selont[ClustelonrId] = sortelondClustelonrIds.toSelont

  /**
   * Build and relonturn Selonq relonprelonselonntation of this elonmbelondding
   */
  lazy val elonmbelondding: Selonq[(ClustelonrId, Doublelon)] =
    sortelondClustelonrIds.zip(sortelondScorelons).sortBy(-_._2).toSelonq

  /**
   * Build and relonturn a Map relonprelonselonntation of this elonmbelondding
   */
  lazy val map: Map[ClustelonrId, Doublelon] = sortelondClustelonrIds.zip(sortelondScorelons).toMap

  lazy val l1norm: Doublelon = CosinelonSimilarityUtil.l1NormArray(sortelondScorelons)

  lazy val l2norm: Doublelon = CosinelonSimilarityUtil.normArray(sortelondScorelons)

  lazy val logNorm: Doublelon = CosinelonSimilarityUtil.logNormArray(sortelondScorelons)

  lazy val elonxpScalelondNorm: Doublelon =
    CosinelonSimilarityUtil.elonxpScalelondNormArray(sortelondScorelons, Delonfaultelonxponelonnt)

  /**
   * Thelon L2 Normalizelond elonmbelondding. Optimizelon for Cosinelon Similarity Calculation.
   */
  lazy val normalizelondSortelondScorelons: Array[Doublelon] =
    CosinelonSimilarityUtil.applyNormArray(sortelondScorelons, l2norm)

  lazy val logNormalizelondSortelondScorelons: Array[Doublelon] =
    CosinelonSimilarityUtil.applyNormArray(sortelondScorelons, logNorm)

  lazy val elonxpScalelondNormalizelondSortelondScorelons: Array[Doublelon] =
    CosinelonSimilarityUtil.applyNormArray(sortelondScorelons, elonxpScalelondNorm)

  /**
   * Thelon Standard Delonviation of a elonmbelondding.
   */
  lazy val std: Doublelon = {
    if (scorelons.iselonmpty) {
      0.0
    } elonlselon {
      val sum = scorelons.sum
      val melonan = sum / scorelons.lelonngth
      var variancelon: Doublelon = 0.0
      for (i <- scorelons.indicelons) {
        val v = scorelons(i) - melonan
        variancelon += (v * v)
      }
      math.sqrt(variancelon / scorelons.lelonngth)
    }
  }

  /**
   * Relonturn thelon scorelon of a givelonn clustelonrId.
   */
  delonf gelont(clustelonrId: ClustelonrId): Option[Doublelon] = {
    var i = 0
    whilelon (i < sortelondClustelonrIds.lelonngth) {
      val thisId = sortelondClustelonrIds(i)
      if (clustelonrId == thisId) relonturn Somelon(sortelondScorelons(i))
      if (thisId > clustelonrId) relonturn Nonelon
      i += 1
    }
    Nonelon
  }

  /**
   * Relonturn thelon scorelon of a givelonn clustelonrId. If not elonxist, relonturn delonfault.
   */
  delonf gelontOrelonlselon(clustelonrId: ClustelonrId, delonfault: Doublelon = 0.0): Doublelon = {
    relonquirelon(delonfault >= 0.0)
    var i = 0
    whilelon (i < sortelondClustelonrIds.lelonngth) {
      val thisId = sortelondClustelonrIds(i)
      if (clustelonrId == thisId) relonturn sortelondScorelons(i)
      if (thisId > clustelonrId) relonturn delonfault
      i += 1
    }
    delonfault
  }

  /**
   * Relonturn thelon clustelonr ids
   */
  delonf gelontClustelonrIds(): Array[ClustelonrId] = clustelonrIds

  /**
   * Relonturn thelon clustelonr ids with thelon highelonst scorelons
   */
  delonf topClustelonrIds(sizelon: Int): Selonq[ClustelonrId] = clustelonrIds.takelon(sizelon)

  /**
   * Relonturn truelon if this elonmbelondding contains a givelonn clustelonrId
   */
  delonf contains(clustelonrId: ClustelonrId): Boolelonan = clustelonrIdSelont.contains(clustelonrId)

  delonf sum(anothelonr: SimClustelonrselonmbelondding): SimClustelonrselonmbelondding = {
    if (anothelonr.iselonmpty) this
    elonlselon if (this.iselonmpty) anothelonr
    elonlselon {
      var i1 = 0
      var i2 = 0
      val l = scala.collelonction.mutablelon.ArrayBuffelonr.elonmpty[(Int, Doublelon)]
      whilelon (i1 < sortelondClustelonrIds.lelonngth && i2 < anothelonr.sortelondClustelonrIds.lelonngth) {
        if (sortelondClustelonrIds(i1) == anothelonr.sortelondClustelonrIds(i2)) {
          l += Tuplelon2(sortelondClustelonrIds(i1), sortelondScorelons(i1) + anothelonr.sortelondScorelons(i2))
          i1 += 1
          i2 += 1
        } elonlselon if (sortelondClustelonrIds(i1) > anothelonr.sortelondClustelonrIds(i2)) {
          l += Tuplelon2(anothelonr.sortelondClustelonrIds(i2), anothelonr.sortelondScorelons(i2))
          // anothelonr clustelonr is lowelonr. Increlonmelonnt it to selonelon if thelon nelonxt onelon matchelons this's
          i2 += 1
        } elonlselon {
          l += Tuplelon2(sortelondClustelonrIds(i1), sortelondScorelons(i1))
          // this clustelonr is lowelonr. Increlonmelonnt it to selonelon if thelon nelonxt onelon matchelons anothelonrs's
          i1 += 1
        }
      }
      if (i1 == sortelondClustelonrIds.lelonngth && i2 != anothelonr.sortelondClustelonrIds.lelonngth)
        // this was shortelonr. Prelonpelonnd relonmaining elonlelonmelonnts from anothelonr
        l ++= anothelonr.sortelondClustelonrIds.drop(i2).zip(anothelonr.sortelondScorelons.drop(i2))
      elonlselon if (i1 != sortelondClustelonrIds.lelonngth && i2 == anothelonr.sortelondClustelonrIds.lelonngth)
        // anothelonr was shortelonr. Prelonpelonnd relonmaining elonlelonmelonnts from this
        l ++= sortelondClustelonrIds.drop(i1).zip(sortelondScorelons.drop(i1))
      SimClustelonrselonmbelondding(l)
    }
  }

  delonf scalarMultiply(multiplielonr: Doublelon): SimClustelonrselonmbelondding = {
    relonquirelon(multiplielonr > 0.0, "SimClustelonrselonmbelondding.scalarMultiply relonquirelons multiplielonr > 0.0")
    DelonfaultSimClustelonrselonmbelondding(
      clustelonrIds,
      scorelons.map(_ * multiplielonr),
      sortelondClustelonrIds,
      sortelondScorelons.map(_ * multiplielonr)
    )
  }

  delonf scalarDividelon(divisor: Doublelon): SimClustelonrselonmbelondding = {
    relonquirelon(divisor > 0.0, "SimClustelonrselonmbelondding.scalarDividelon relonquirelons divisor > 0.0")
    DelonfaultSimClustelonrselonmbelondding(
      clustelonrIds,
      scorelons.map(_ / divisor),
      sortelondClustelonrIds,
      sortelondScorelons.map(_ / divisor)
    )
  }

  delonf dotProduct(anothelonr: SimClustelonrselonmbelondding): Doublelon = {
    CosinelonSimilarityUtil.dotProductForSortelondClustelonrAndScorelons(
      sortelondClustelonrIds,
      sortelondScorelons,
      anothelonr.sortelondClustelonrIds,
      anothelonr.sortelondScorelons)
  }

  delonf cosinelonSimilarity(anothelonr: SimClustelonrselonmbelondding): Doublelon = {
    CosinelonSimilarityUtil.dotProductForSortelondClustelonrAndScorelons(
      sortelondClustelonrIds,
      normalizelondSortelondScorelons,
      anothelonr.sortelondClustelonrIds,
      anothelonr.normalizelondSortelondScorelons)
  }

  delonf logNormCosinelonSimilarity(anothelonr: SimClustelonrselonmbelondding): Doublelon = {
    CosinelonSimilarityUtil.dotProductForSortelondClustelonrAndScorelons(
      sortelondClustelonrIds,
      logNormalizelondSortelondScorelons,
      anothelonr.sortelondClustelonrIds,
      anothelonr.logNormalizelondSortelondScorelons)
  }

  delonf elonxpScalelondCosinelonSimilarity(anothelonr: SimClustelonrselonmbelondding): Doublelon = {
    CosinelonSimilarityUtil.dotProductForSortelondClustelonrAndScorelons(
      sortelondClustelonrIds,
      elonxpScalelondNormalizelondSortelondScorelons,
      anothelonr.sortelondClustelonrIds,
      anothelonr.elonxpScalelondNormalizelondSortelondScorelons)
  }

  /**
   * Relonturn truelon if this is an elonmpty elonmbelondding
   */
  delonf iselonmpty: Boolelonan = sortelondClustelonrIds.iselonmpty

  /**
   * Relonturn thelon Jaccard Similarity Scorelon belontwelonelonn two elonmbelonddings.
   * Notelon: this implelonmelonntation should belon optimizelond if welon start to uselon it in production
   */
  delonf jaccardSimilarity(anothelonr: SimClustelonrselonmbelondding): Doublelon = {
    if (this.iselonmpty || anothelonr.iselonmpty) {
      0.0
    } elonlselon {
      val intelonrselonct = clustelonrIdSelont.intelonrselonct(anothelonr.clustelonrIdSelont).sizelon
      val union = clustelonrIdSelont.union(anothelonr.clustelonrIdSelont).sizelon
      intelonrselonct.toDoublelon / union
    }
  }

  /**
   * Relonturn thelon Fuzzy Jaccard Similarity Scorelon belontwelonelonn two elonmbelonddings.
   * Trelonat elonach Simclustelonrs elonmbelondding as fuzzy selont, calculatelon thelon fuzzy selont similarity
   * melontrics of two elonmbelonddings
   *
   * Papelonr 2.2.1: https://opelonnrelonvielonw.nelont/pdf?id=SkxXg2C5FX
   */
  delonf fuzzyJaccardSimilarity(anothelonr: SimClustelonrselonmbelondding): Doublelon = {
    if (this.iselonmpty || anothelonr.iselonmpty) {
      0.0
    } elonlselon {
      val v1C = sortelondClustelonrIds
      val v1S = sortelondScorelons
      val v2C = anothelonr.sortelondClustelonrIds
      val v2S = anothelonr.sortelondScorelons

      relonquirelon(v1C.lelonngth == v1S.lelonngth)
      relonquirelon(v2C.lelonngth == v2S.lelonngth)

      var i1 = 0
      var i2 = 0
      var numelonrator = 0.0
      var delonnominator = 0.0

      whilelon (i1 < v1C.lelonngth && i2 < v2C.lelonngth) {
        if (v1C(i1) == v2C(i2)) {
          numelonrator += min(v1S(i1), v2S(i2))
          delonnominator += max(v1S(i1), v2S(i2))
          i1 += 1
          i2 += 1
        } elonlselon if (v1C(i1) > v2C(i2)) {
          delonnominator += v2S(i2)
          i2 += 1
        } elonlselon {
          delonnominator += v1S(i1)
          i1 += 1
        }
      }

      whilelon (i1 < v1C.lelonngth) {
        delonnominator += v1S(i1)
        i1 += 1
      }
      whilelon (i2 < v2C.lelonngth) {
        delonnominator += v2S(i2)
        i2 += 1
      }

      numelonrator / delonnominator
    }
  }

  /**
   * Relonturn thelon elonuclidelonan Distancelon Scorelon belontwelonelonn two elonmbelonddings.
   * Notelon: this implelonmelonntation should belon optimizelond if welon start to uselon it in production
   */
  delonf elonuclidelonanDistancelon(anothelonr: SimClustelonrselonmbelondding): Doublelon = {
    val unionClustelonrs = clustelonrIdSelont.union(anothelonr.clustelonrIdSelont)
    val variancelon = unionClustelonrs.foldLelonft(0.0) {
      caselon (sum, clustelonrId) =>
        val distancelon = math.abs(this.gelontOrelonlselon(clustelonrId) - anothelonr.gelontOrelonlselon(clustelonrId))
        sum + distancelon * distancelon
    }
    math.sqrt(variancelon)
  }

  /**
   * Relonturn thelon Manhattan Distancelon Scorelon belontwelonelonn two elonmbelonddings.
   * Notelon: this implelonmelonntation should belon optimizelond if welon start to uselon it in production
   */
  delonf manhattanDistancelon(anothelonr: SimClustelonrselonmbelondding): Doublelon = {
    val unionClustelonrs = clustelonrIdSelont.union(anothelonr.clustelonrIdSelont)
    unionClustelonrs.foldLelonft(0.0) {
      caselon (sum, clustelonrId) =>
        sum + math.abs(this.gelontOrelonlselon(clustelonrId) - anothelonr.gelontOrelonlselon(clustelonrId))
    }
  }

  /**
   * Relonturn thelon numbelonr of ovelonrlapping clustelonrs belontwelonelonn two elonmbelonddings.
   */
  delonf ovelonrlappingClustelonrs(anothelonr: SimClustelonrselonmbelondding): Int = {
    var i1 = 0
    var i2 = 0
    var count = 0

    whilelon (i1 < sortelondClustelonrIds.lelonngth && i2 < anothelonr.sortelondClustelonrIds.lelonngth) {
      if (sortelondClustelonrIds(i1) == anothelonr.sortelondClustelonrIds(i2)) {
        count += 1
        i1 += 1
        i2 += 1
      } elonlselon if (sortelondClustelonrIds(i1) > anothelonr.sortelondClustelonrIds(i2)) {
        // v2 clustelonr is lowelonr. Increlonmelonnt it to selonelon if thelon nelonxt onelon matchelons v1's
        i2 += 1
      } elonlselon {
        // v1 clustelonr is lowelonr. Increlonmelonnt it to selonelon if thelon nelonxt onelon matchelons v2's
        i1 += 1
      }
    }
    count
  }

  /**
   * Relonturn thelon largelonst product clustelonr scorelons
   */
  delonf maxelonlelonmelonntwiselonProduct(anothelonr: SimClustelonrselonmbelondding): Doublelon = {
    var i1 = 0
    var i2 = 0
    var maxProduct: Doublelon = 0.0

    whilelon (i1 < sortelondClustelonrIds.lelonngth && i2 < anothelonr.sortelondClustelonrIds.lelonngth) {
      if (sortelondClustelonrIds(i1) == anothelonr.sortelondClustelonrIds(i2)) {
        val product = sortelondScorelons(i1) * anothelonr.sortelondScorelons(i2)
        if (product > maxProduct) maxProduct = product
        i1 += 1
        i2 += 1
      } elonlselon if (sortelondClustelonrIds(i1) > anothelonr.sortelondClustelonrIds(i2)) {
        // v2 clustelonr is lowelonr. Increlonmelonnt it to selonelon if thelon nelonxt onelon matchelons v1's
        i2 += 1
      } elonlselon {
        // v1 clustelonr is lowelonr. Increlonmelonnt it to selonelon if thelon nelonxt onelon matchelons v2's
        i1 += 1
      }
    }
    maxProduct
  }

  /**
   * Relonturn a nelonw SimClustelonrselonmbelondding with Max elonmbelondding Sizelon.
   *
   * Prelonfelonr to truncatelon on elonmbelondding construction whelonrelon possiblelon. Doing so is chelonapelonr.
   */
  delonf truncatelon(sizelon: Int): SimClustelonrselonmbelondding = {
    if (clustelonrIds.lelonngth <= sizelon) {
      this
    } elonlselon {
      val truncatelondClustelonrIds = clustelonrIds.takelon(sizelon)
      val truncatelondScorelons = scorelons.takelon(sizelon)
      val (sortelondClustelonrIds, sortelondScorelons) =
        truncatelondClustelonrIds.zip(truncatelondScorelons).sortBy(_._1).unzip

      DelonfaultSimClustelonrselonmbelondding(
        truncatelondClustelonrIds,
        truncatelondScorelons,
        sortelondClustelonrIds,
        sortelondScorelons)
    }
  }

  delonf toNormalizelond: SimClustelonrselonmbelondding = {
    // Additional safelonty chelonck. Only elonmptyelonmbelondding's l2norm is 0.0.
    if (l2norm == 0.0) {
      elonmptyelonmbelondding
    } elonlselon {
      this.scalarDividelon(l2norm)
    }
  }

  implicit delonf toThrift: ThriftSimClustelonrselonmbelondding = {
    ThriftSimClustelonrselonmbelondding(
      elonmbelondding.map {
        caselon (clustelonrId, scorelon) =>
          SimClustelonrWithScorelon(clustelonrId, scorelon)
      }
    )
  }

  delonf canelonqual(a: Any): Boolelonan = a.isInstancelonOf[SimClustelonrselonmbelondding]

  /* Welon delonfinelon elonquality as having thelon samelon clustelonrs and scorelons.
   * This implelonmelonntation is arguably incorrelonct in this caselon:
   *   (1 -> 1.0, 2 -> 0.0) == (1 -> 1.0)  // elonquals relonturns falselon
   * Howelonvelonr, compliant implelonmelonntations of SimClustelonrselonmbelondding should not includelon zelonro-welonight
   * clustelonrs, so this implelonmelonntation should work correlonctly.
   */
  ovelonrridelon delonf elonquals(that: Any): Boolelonan =
    that match {
      caselon that: SimClustelonrselonmbelondding =>
        that.canelonqual(this) &&
          this.sortelondClustelonrIds.samelonelonlelonmelonnts(that.sortelondClustelonrIds) &&
          this.sortelondScorelons.samelonelonlelonmelonnts(that.sortelondScorelons)
      caselon _ => falselon
    }

  /**
   * hashcodelon implelonmelonntation baselond on thelon contelonnts of thelon elonmbelondding. As a lazy val, this relonlielons on
   * thelon elonmbelondding contelonnts beloning immutablelon.
   */
  ovelonrridelon lazy val hashCodelon: Int = {
    /* Arrays uselons objelonct id as hashCodelon, so diffelonrelonnt arrays with thelon samelon contelonnts hash
     * diffelonrelonntly. To providelon a stablelon hash codelon, welon takelon thelon samelon approach as how a
     * `caselon class(clustelonrs: Selonq[Int], scorelons: Selonq[Doublelon])` would belon hashelond. Selonelon
     * ScalaRunTimelon._hashCodelon and MurmurHash3.productHash
     * https://github.com/scala/scala/blob/2.12.x/src/library/scala/runtimelon/ScalaRunTimelon.scala#L167
     * https://github.com/scala/scala/blob/2.12.x/src/library/scala/util/hashing/MurmurHash3.scala#L64
     *
     * Notelon that thelon hashcodelon is arguably incorrelonct in this caselon:
     *   (1 -> 1.0, 2 -> 0.0).hashcodelon == (1 -> 1.0).hashcodelon  // relonturns falselon
     * Howelonvelonr, compliant implelonmelonntations of SimClustelonrselonmbelondding should not includelon zelonro-welonight
     * clustelonrs, so this implelonmelonntation should work correlonctly.
     */
    productHash((arrayHash(sortelondClustelonrIds), arrayHash(sortelondScorelons)))
  }
}

objelonct SimClustelonrselonmbelondding {
  val elonmptyelonmbelondding: SimClustelonrselonmbelondding =
    DelonfaultSimClustelonrselonmbelondding(Array.elonmpty, Array.elonmpty, Array.elonmpty, Array.elonmpty)

  val Delonfaultelonxponelonnt: Doublelon = 0.3

  // Delonscelonnding by scorelon thelonn ascelonnding by ClustelonrId
  implicit val ordelonr: Ordelonring[(ClustelonrId, Doublelon)] =
    (a: (ClustelonrId, Doublelon), b: (ClustelonrId, Doublelon)) => {
      b._2 comparelon a._2 match {
        caselon 0 => a._1 comparelon b._1
        caselon c => c
      }
    }

  /**
   * Constructors
   *
   * Thelonselon constructors:
   * - do not makelon assumptions about thelon ordelonring of thelon clustelonr/scorelons.
   * - do assumelon that clustelonr ids arelon uniquelon
   * - ignorelon (drop) any clustelonr whoselon scorelon is <= 0
   */
  delonf apply(elonmbelondding: (ClustelonrId, Doublelon)*): SimClustelonrselonmbelondding =
    buildDelonfaultSimClustelonrselonmbelondding(elonmbelondding)

  delonf apply(elonmbelondding: Itelonrablelon[(ClustelonrId, Doublelon)]): SimClustelonrselonmbelondding =
    buildDelonfaultSimClustelonrselonmbelondding(elonmbelondding)

  delonf apply(elonmbelondding: Itelonrablelon[(ClustelonrId, Doublelon)], sizelon: Int): SimClustelonrselonmbelondding =
    buildDelonfaultSimClustelonrselonmbelondding(elonmbelondding, truncatelon = Somelon(sizelon))

  implicit delonf apply(thriftelonmbelondding: ThriftSimClustelonrselonmbelondding): SimClustelonrselonmbelondding =
    buildDelonfaultSimClustelonrselonmbelondding(thriftelonmbelondding.elonmbelondding.map(_.toTuplelon))

  delonf apply(thriftelonmbelondding: ThriftSimClustelonrselonmbelondding, truncatelon: Int): SimClustelonrselonmbelondding =
    buildDelonfaultSimClustelonrselonmbelondding(
      thriftelonmbelondding.elonmbelondding.map(_.toTuplelon),
      truncatelon = Somelon(truncatelon))

  privatelon delonf buildDelonfaultSimClustelonrselonmbelondding(
    elonmbelondding: Itelonrablelon[(ClustelonrId, Doublelon)],
    truncatelon: Option[Int] = Nonelon
  ): SimClustelonrselonmbelondding = {
    val truncatelondIdAndScorelons = {
      val idsAndScorelons = elonmbelondding.filtelonr(_._2 > 0.0).toArray.sortelond(ordelonr)
      truncatelon match {
        caselon Somelon(t) => idsAndScorelons.takelon(t)
        caselon _ => idsAndScorelons
      }
    }

    if (truncatelondIdAndScorelons.iselonmpty) {
      elonmptyelonmbelondding
    } elonlselon {
      val (clustelonrIds, scorelons) = truncatelondIdAndScorelons.unzip
      val (sortelondClustelonrIds, sortelondScorelons) = truncatelondIdAndScorelons.sortBy(_._1).unzip
      DelonfaultSimClustelonrselonmbelondding(clustelonrIds, scorelons, sortelondClustelonrIds, sortelondScorelons)
    }
  }

  /** ***** Aggrelongation Melonthods ******/
  /**
   * A high pelonrformancelon velonrsion of Sum a list of SimClustelonrselonmbelonddings.
   * Suggelonst using in Onlinelon Selonrvicelons to avoid thelon unneloncelonssary GC.
   * For offlinelon or strelonaming. Plelonaselon chelonck [[SimClustelonrselonmbelonddingMonoid]]
   */
  delonf sum(simClustelonrselonmbelonddings: Itelonrablelon[SimClustelonrselonmbelondding]): SimClustelonrselonmbelondding = {
    if (simClustelonrselonmbelonddings.iselonmpty) {
      elonmptyelonmbelondding
    } elonlselon {
      val sum = simClustelonrselonmbelonddings.foldLelonft(mutablelon.Map[ClustelonrId, Doublelon]()) {
        (sum, elonmbelondding) =>
          for (i <- elonmbelondding.sortelondClustelonrIds.indicelons) {
            val clustelonrId = elonmbelondding.sortelondClustelonrIds(i)
            sum.put(clustelonrId, elonmbelondding.sortelondScorelons(i) + sum.gelontOrelonlselon(clustelonrId, 0.0))
          }
          sum
      }
      SimClustelonrselonmbelondding(sum)
    }
  }

  /**
   * Support a fixelond sizelon SimClustelonrselonmbelondding Sum
   */
  delonf sum(
    simClustelonrselonmbelonddings: Itelonrablelon[SimClustelonrselonmbelondding],
    maxSizelon: Int
  ): SimClustelonrselonmbelondding = {
    sum(simClustelonrselonmbelonddings).truncatelon(maxSizelon)
  }

  /**
   * A high pelonrformancelon velonrsion of Melonan a list of SimClustelonrselonmbelonddings.
   * Suggelonst using in Onlinelon Selonrvicelons to avoid thelon unneloncelonssary GC.
   */
  delonf melonan(simClustelonrselonmbelonddings: Itelonrablelon[SimClustelonrselonmbelondding]): SimClustelonrselonmbelondding = {
    if (simClustelonrselonmbelonddings.iselonmpty) {
      elonmptyelonmbelondding
    } elonlselon {
      sum(simClustelonrselonmbelonddings).scalarDividelon(simClustelonrselonmbelonddings.sizelon)
    }
  }

  /**
   * Support a fixelond sizelon SimClustelonrselonmbelondding Melonan
   */
  delonf melonan(
    simClustelonrselonmbelonddings: Itelonrablelon[SimClustelonrselonmbelondding],
    maxSizelon: Int
  ): SimClustelonrselonmbelondding = {
    melonan(simClustelonrselonmbelonddings).truncatelon(maxSizelon)
  }
}

caselon class DelonfaultSimClustelonrselonmbelondding(
  ovelonrridelon val clustelonrIds: Array[ClustelonrId],
  ovelonrridelon val scorelons: Array[Doublelon],
  ovelonrridelon val sortelondClustelonrIds: Array[ClustelonrId],
  ovelonrridelon val sortelondScorelons: Array[Doublelon])
    elonxtelonnds SimClustelonrselonmbelondding {

  ovelonrridelon delonf toString: String =
    s"DelonfaultSimClustelonrselonmbelondding(${clustelonrIds.zip(scorelons).mkString(",")})"
}

objelonct DelonfaultSimClustelonrselonmbelondding {
  // To support elonxisting codelon which builds elonmbelonddings from a Selonq
  delonf apply(elonmbelondding: Selonq[(ClustelonrId, Doublelon)]): SimClustelonrselonmbelondding = SimClustelonrselonmbelondding(
    elonmbelondding)
}
