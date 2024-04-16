package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.convers on

 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.TypedAggregateGroup
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Aggregat on tr cCommon
 mport java.lang.{Boolean => JBoolean}
 mport java.lang.{Double => JDouble}

case class CtrDescr ptor(
  engage ntFeature: Feature[JDouble],
   mpress onFeature: Feature[JDouble],
  outputFeature: Feature[JDouble])

object P ckTopCtrBu lder lper {

  def createCtrDescr ptors(
    aggregatePref x: Str ng,
    engage ntLabels: Set[Feature[JBoolean]],
    aggregatesToCompute: Set[TypedAggregateGroup[_]],
    outputSuff x: Str ng
  ): Set[CtrDescr ptor] = {
    val aggregateFeatures = aggregatesToCompute
      .f lter(_.aggregatePref x == aggregatePref x)

    val  mpress onFeature = aggregateFeatures
      .flatMap { group =>
        group. nd v dualAggregateDescr ptors
          .f lter(_.query.feature == None)
          .f lter(_.query.label == None)
          .flatMap(_.outputFeatures)
      }
      . ad
      .as nstanceOf[Feature[JDouble]]

    val aggregateEngage ntFeatures =
      aggregateFeatures
        .flatMap { group =>
          group. nd v dualAggregateDescr ptors
            .f lter(_.query.feature == None)
            .f lter { descr ptor =>
              //TODO:   should remove t  need to pass around engage ntLabels and just use all t  labels ava lable.
              descr ptor.query.label.ex sts(engage ntLabels.conta ns(_))
            }
            .flatMap(_.outputFeatures)
        }
        .map(_.as nstanceOf[Feature[JDouble]])

    aggregateEngage ntFeatures
      .map { aggregateEngage ntFeature =>
        CtrDescr ptor(
          engage ntFeature = aggregateEngage ntFeature,
           mpress onFeature =  mpress onFeature,
          outputFeature = new Feature.Cont nuous(
            aggregateEngage ntFeature.getDenseFeatureNa  + "." + outputSuff x,
            Aggregat on tr cCommon.der vePersonalDataTypes(
              So (aggregateEngage ntFeature),
              So ( mpress onFeature)
            )
          )
        )
      }
  }
}

object P ckTopCtrPol cy {
  def bu ld(
    aggregatePref x: Str ng,
    engage ntLabels: Set[Feature[JBoolean]],
    aggregatesToCompute: Set[TypedAggregateGroup[_]],
    smooth ng: Double = 1.0,
    outputSuff x: Str ng = "rat o"
  ): P ckTopCtrPol cy = {
    val ctrDescr ptors = P ckTopCtrBu lder lper.createCtrDescr ptors(
      aggregatePref x = aggregatePref x,
      engage ntLabels = engage ntLabels,
      aggregatesToCompute = aggregatesToCompute,
      outputSuff x = outputSuff x
    )
    P ckTopCtrPol cy(
      ctrDescr ptors = ctrDescr ptors,
      smooth ng = smooth ng
    )
  }
}

object Comb nedTopNCtrsByW lsonConf dence ntervalPol cy {
  def bu ld(
    aggregatePref x: Str ng,
    engage ntLabels: Set[Feature[JBoolean]],
    aggregatesToCompute: Set[TypedAggregateGroup[_]],
    outputSuff x: Str ng = "rat oW hWC ",
    z: Double = 1.96,
    topN:  nt = 1
  ): Comb nedTopNCtrsByW lsonConf dence ntervalPol cy = {
    val ctrDescr ptors = P ckTopCtrBu lder lper.createCtrDescr ptors(
      aggregatePref x = aggregatePref x,
      engage ntLabels = engage ntLabels,
      aggregatesToCompute = aggregatesToCompute,
      outputSuff x = outputSuff x
    )
    Comb nedTopNCtrsByW lsonConf dence ntervalPol cy(
      ctrDescr ptors = ctrDescr ptors,
      z = z,
      topN = topN
    )
  }
}

/*
 * A  rge pol cy that p cks t  aggregate features correspond ng to
 * t  sparse key value w h t  h g st engage nt rate (def ned
 * as t  rat o of two spec f ed features, represent ng engage nts
 * and  mpress ons). Also outputs t  engage nt rate to t  spec f ed
 * outputFeature.
 *
 * T   s an abstract class.   can make var ants of t  pol cy by overr d ng
 * t  calculateCtr  thod.
 */

abstract class P ckTopCtrPol cyBase(ctrDescr ptors: Set[CtrDescr ptor])
    extends SparseB nary rgePol cy {

  pr vate def getCont nuousFeature(
    aggregateRecord: DataRecord,
    feature: Feature[JDouble]
  ): Double = {
    Opt on(SR chDataRecord(aggregateRecord).getFeatureValue(feature))
      .map(_.as nstanceOf[JDouble].toDouble)
      .getOrElse(0.0)
  }

  /**
   * For every prov ded descr ptor, compute t  correspond ng CTR feature
   * and only hydrate t  result to t  prov ded  nput record.
   */
  overr de def  rgeRecord(
    mutable nputRecord: DataRecord,
    aggregateRecords: L st[DataRecord],
    aggregateContext: FeatureContext
  ): Un  = {
    ctrDescr ptors
      .foreach {
        case CtrDescr ptor(engage ntFeature,  mpress onFeature, outputFeature) =>
          val sortedCtrs =
            aggregateRecords
              .map { aggregateRecord =>
                val  mpress ons = getCont nuousFeature(aggregateRecord,  mpress onFeature)
                val engage nts = getCont nuousFeature(aggregateRecord, engage ntFeature)
                calculateCtr( mpress ons, engage nts)
              }
              .sortBy { ctr => -ctr }
          comb neTopNCtrsToS ngleScore(sortedCtrs)
            .foreach { score =>
              SR chDataRecord(mutable nputRecord).setFeatureValue(outputFeature, score)
            }
      }
  }

  protected def calculateCtr( mpress ons: Double, engage nts: Double): Double

  protected def comb neTopNCtrsToS ngleScore(sortedCtrs: Seq[Double]): Opt on[Double]

  overr de def aggregateFeaturesPost rge(aggregateContext: FeatureContext): Set[Feature[_]] =
    ctrDescr ptors
      .map(_.outputFeature)
      .toSet
}

case class P ckTopCtrPol cy(ctrDescr ptors: Set[CtrDescr ptor], smooth ng: Double = 1.0)
    extends P ckTopCtrPol cyBase(ctrDescr ptors) {
  requ re(smooth ng > 0.0)

  overr de def calculateCtr( mpress ons: Double, engage nts: Double): Double =
    (1.0 * engage nts) / (smooth ng +  mpress ons)

  overr de def comb neTopNCtrsToS ngleScore(sortedCtrs: Seq[Double]): Opt on[Double] =
    sortedCtrs. adOpt on
}

case class Comb nedTopNCtrsByW lsonConf dence ntervalPol cy(
  ctrDescr ptors: Set[CtrDescr ptor],
  z: Double = 1.96,
  topN:  nt = 1)
    extends P ckTopCtrPol cyBase(ctrDescr ptors) {

  pr vate val zSquared = z * z
  pr vate val zSquaredD v2 = zSquared / 2.0
  pr vate val zSquaredD v4 = zSquared / 4.0

  /**
   * calculates t  lo r bound of w lson score  nterval. wh ch roughly says "t  actual engage nt
   * rate  s at least t  value" w h conf dence des gnated by t  z-score:
   * https://en.w k ped a.org/w k /B nom al_proport on_conf dence_ nterval#W lson_score_ nterval
   */
  overr de def calculateCtr(raw mpress ons: Double, engage nts: Double): Double = {
    // just  n case engage nts happens to be more than  mpress ons...
    val  mpress ons = Math.max(raw mpress ons, engage nts)

     f ( mpress ons > 0.0) {
      val p = engage nts /  mpress ons
      (p
        + zSquaredD v2 /  mpress ons
        - z * Math.sqrt(
          (p * (1.0 - p) + zSquaredD v4 /  mpress ons) /  mpress ons)) / (1.0 + zSquared /  mpress ons)

    } else 0.0
  }

  /**
   * takes t  topN engage nt rates, and returns t  jo nt probab l y as {1.0 - Π(1.0 - p)}
   *
   * e.g. let's say   have 0.6 chance of cl ck ng on a t et shared by t  user A.
   *   also have 0.3 chance of cl ck ng on a t et shared by t  user B.
   * see ng a t et shared by both A and B w ll not lead to 0.9 chance of   cl ck ng on  .
   * but   could say that   have 0.4*0.7 chance of NOT cl ck ng on that t et.
   */
  overr de def comb neTopNCtrsToS ngleScore(sortedCtrs: Seq[Double]): Opt on[Double] =
     f (sortedCtrs.nonEmpty) {
      val  nverseLogP = sortedCtrs
        .take(topN).map { p => Math.log(1.0 - p) }.sum
      So (1.0 - Math.exp( nverseLogP))
    } else None

}
