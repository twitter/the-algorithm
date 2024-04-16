package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.convers on

 mport com.google.common.annotat ons.V s bleForTest ng
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap ._
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Aggregat on tr cCommon
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.TypedCount tr c
 mport java.lang.{Double => JDouble}
 mport scala.collect on.JavaConverters._

case class Comb nedFeatures(
  sum: Feature[JDouble],
  nonzero: Feature[JDouble],
   an: Feature[JDouble],
  topK: Seq[Feature[JDouble]])

tra  Comb neCountsBase {
  val SparseSum = "sparse_sum"
  val SparseNonzero = "sparse_nonzero"
  val Sparse an = "sparse_ an"
  val SparseTop = "sparse_top"

  def topK:  nt
  def hardL m : Opt on[ nt]
  def precomputedCountFeatures: Seq[Feature[_]]

  lazy val precomputedFeaturesMap: Map[Feature[_], Comb nedFeatures] =
    precomputedCountFeatures.map { countFeature =>
      val der vedPersonalDataTypes =
        Aggregat on tr cCommon.der vePersonalDataTypes(So (countFeature))
      val sum = new Feature.Cont nuous(
        countFeature.getDenseFeatureNa  + "." + SparseSum,
        der vedPersonalDataTypes)
      val nonzero = new Feature.Cont nuous(
        countFeature.getDenseFeatureNa  + "." + SparseNonzero,
        der vedPersonalDataTypes)
      val  an = new Feature.Cont nuous(
        countFeature.getDenseFeatureNa  + "." + Sparse an,
        der vedPersonalDataTypes)
      val topKFeatures = (1 to topK).map { k =>
        new Feature.Cont nuous(
          countFeature.getDenseFeatureNa  + "." + SparseTop + k,
          der vedPersonalDataTypes)
      }
      (countFeature, Comb nedFeatures(sum, nonzero,  an, topKFeatures))
    }.toMap

  lazy val outputFeaturesPost rge: Set[Feature[JDouble]] =
    precomputedFeaturesMap.values.flatMap { comb nedFeatures: Comb nedFeatures =>
      Seq(
        comb nedFeatures.sum,
        comb nedFeatures.nonzero,
        comb nedFeatures. an
      ) ++ comb nedFeatures.topK
    }.toSet

  pr vate case class ComputedStats(sum: Double, nonzero: Double,  an: Double)

  pr vate def preComputeStats(featureValues: Seq[Double]): ComputedStats = {
    val (sum, nonzero) = featureValues.foldLeft((0.0, 0.0)) {
      case ((accSum, accNonzero), value) =>
        (accSum + value,  f (value > 0.0) accNonzero + 1.0 else accNonzero)
    }
    ComputedStats(sum, nonzero,  f (nonzero > 0.0) sum / nonzero else 0.0)
  }

  pr vate def computeSortedFeatureValues(featureValues: L st[Double]): L st[Double] =
    featureValues.sortBy(-_)

  pr vate def extractKth(sortedFeatureValues: Seq[Double], k:  nt): Double =
    sortedFeatureValues
      .l ft(k - 1)
      .getOrElse(0.0)

  pr vate def setCont nuousFeature fNonZero(
    record: SR chDataRecord,
    feature: Feature[JDouble],
    value: Double
  ): Un  =
     f (value != 0.0) {
      record.setFeatureValue(feature, value)
    }

  def hydrateCountFeatures(
    r chRecord: SR chDataRecord,
    features: Seq[Feature[_]],
    featureValuesMap: Map[Feature[_], L st[Double]]
  ): Un  =
    for {
      feature <- features
      featureValues <- featureValuesMap.get(feature)
    } {
       rgeRecordFromCountFeature(
        countFeature = feature,
        featureValues = featureValues,
        r ch nputRecord = r chRecord
      )
    }

  def  rgeRecordFromCountFeature(
    r ch nputRecord: SR chDataRecord,
    countFeature: Feature[_],
    featureValues: L st[Double]
  ): Un  = {
    //  n major y of calls to t   thod from t  l ne scorer
    // t  featureValues l st  s empty.
    // Wh le w h empty l st each operat on w ll be not that expens ve, t se
    // small th ngs do add up. By add ng early stop  re   can avo d sort ng
    // empty l st, allocat ng several opt ons and mak ng mult ple funct on
    // calls.  n add  on to that,   won't  erate over [1, topK].
     f (featureValues.nonEmpty) {
      val sortedFeatureValues = hardL m 
        .map { l m  =>
          computeSortedFeatureValues(featureValues).take(l m )
        }.getOrElse(computeSortedFeatureValues(featureValues)).to ndexedSeq
      val computed = preComputeStats(sortedFeatureValues)

      val comb nedFeatures = precomputedFeaturesMap(countFeature)
      setCont nuousFeature fNonZero(
        r ch nputRecord,
        comb nedFeatures.sum,
        computed.sum
      )
      setCont nuousFeature fNonZero(
        r ch nputRecord,
        comb nedFeatures.nonzero,
        computed.nonzero
      )
      setCont nuousFeature fNonZero(
        r ch nputRecord,
        comb nedFeatures. an,
        computed. an
      )
      (1 to topK).foreach { k =>
        setCont nuousFeature fNonZero(
          r ch nputRecord,
          comb nedFeatures.topK(k - 1),
          extractKth(sortedFeatureValues, k)
        )
      }
    }
  }
}

object Comb neCountsPol cy {
  def getCountFeatures(aggregateContext: FeatureContext): Seq[Feature[_]] =
    aggregateContext.getAllFeatures.asScala.toSeq
      .f lter { feature =>
        feature.getFeatureType == FeatureType.CONT NUOUS &&
        feature.getDenseFeatureNa .endsW h(TypedCount tr c[JDouble]().operatorNa )
      }

  @V s bleForTest ng
  pr vate[convers on] def getFeatureValues(
    dataRecordsW hCounts: L st[DataRecord],
    countFeature: Feature[_]
  ): L st[Double] =
    dataRecordsW hCounts.map(new SR chDataRecord(_)).flatMap { record =>
      Opt on(record.getFeatureValue(countFeature)).map(_.as nstanceOf[JDouble].toDouble)
    }
}

/**
 * A  rge pol cy that works w never all aggregate features are
 * counts (computed us ng Count tr c), and typ cally represent
 * e  r  mpress ons or engage nts. For each such  nput count
 * feature, t  pol cy outputs t  follow ng (3+k) der ved features
 *  nto t  output data record:
 *
 * Sum of t  feature's value across all aggregate records
 * Number of aggregate records that have t  feature set to non-zero
 *  an of t  feature's value across all aggregate records
 * topK values of t  feature across all aggregate records
 *
 * @param topK topK values to compute
 * @param hardL m  w n set, records are sorted and only t  top values w ll be used for aggregat on  f
 *                  t  number of records are h g r than t  hard l m .
 */
case class Comb neCountsPol cy(
  overr de val topK:  nt,
  aggregateContextToPrecompute: FeatureContext,
  overr de val hardL m : Opt on[ nt] = None)
    extends SparseB nary rgePol cy
    w h Comb neCountsBase {
   mport Comb neCountsPol cy._
  overr de val precomputedCountFeatures: Seq[Feature[_]] = getCountFeatures(
    aggregateContextToPrecompute)

  overr de def  rgeRecord(
    mutable nputRecord: DataRecord,
    aggregateRecords: L st[DataRecord],
    aggregateContext: FeatureContext
  ): Un  = {
    // Assu s aggregateContext === aggregateContextToPrecompute
     rgeRecordFromCountFeatures(mutable nputRecord, aggregateRecords, precomputedCountFeatures)
  }

  def default rgeRecord(
    mutable nputRecord: DataRecord,
    aggregateRecords: L st[DataRecord]
  ): Un  = {
     rgeRecordFromCountFeatures(mutable nputRecord, aggregateRecords, precomputedCountFeatures)
  }

  def  rgeRecordFromCountFeatures(
    mutable nputRecord: DataRecord,
    aggregateRecords: L st[DataRecord],
    countFeatures: Seq[Feature[_]]
  ): Un  = {
    val r ch nputRecord = new SR chDataRecord(mutable nputRecord)
    countFeatures.foreach { countFeature =>
       rgeRecordFromCountFeature(
        r ch nputRecord = r ch nputRecord,
        countFeature = countFeature,
        featureValues = getFeatureValues(aggregateRecords, countFeature)
      )
    }
  }

  overr de def aggregateFeaturesPost rge(aggregateContext: FeatureContext): Set[Feature[_]] =
    outputFeaturesPost rge.map(_.as nstanceOf[Feature[_]])
}
