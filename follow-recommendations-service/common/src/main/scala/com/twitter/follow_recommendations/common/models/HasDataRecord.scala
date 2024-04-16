package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.follow_recom ndat ons.thr ftscala.DebugDataRecord
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ut l.Try
 mport com.tw ter.ut l.logg ng.Logg ng
 mport scala.collect on.convert. mpl c Convers ons._

// conta ns t  standard dataRecord struct, and t  debug vers on  f requ red
case class R chDataRecord(
  dataRecord: Opt on[DataRecord] = None,
  debugDataRecord: Opt on[DebugDataRecord] = None,
)

tra  HasDataRecord extends Logg ng {
  def dataRecord: Opt on[R chDataRecord]

  def toDebugDataRecord(dr: DataRecord, featureContext: FeatureContext): DebugDataRecord = {

    val b naryFeatures: Opt on[Set[Str ng]] =  f (dr. sSetB naryFeatures) {
      So (dr.getB naryFeatures.flatMap {  d =>
        Try(featureContext.getFeature( d).getFeatureNa ).toOpt on
      }.toSet)
    } else None

    val cont nuousFeatures: Opt on[Map[Str ng, Double]] =  f (dr. sSetCont nuousFeatures) {
      So (dr.getCont nuousFeatures.flatMap {
        case ( d, value) =>
          Try(featureContext.getFeature( d).getFeatureNa ).toOpt on.map {  d =>
             d -> value.toDouble
          }
      }.toMap)
    } else None

    val d screteFeatures: Opt on[Map[Str ng, Long]] =  f (dr. sSetD screteFeatures) {
      So (dr.getD screteFeatures.flatMap {
        case ( d, value) =>
          Try(featureContext.getFeature( d).getFeatureNa ).toOpt on.map {  d =>
             d -> value.toLong
          }
      }.toMap)
    } else None

    val str ngFeatures: Opt on[Map[Str ng, Str ng]] =  f (dr. sSetStr ngFeatures) {
      So (dr.getStr ngFeatures.flatMap {
        case ( d, value) =>
          Try(featureContext.getFeature( d).getFeatureNa ).toOpt on.map {  d =>
             d -> value
          }
      }.toMap)
    } else None

    val sparseB naryFeatures: Opt on[Map[Str ng, Set[Str ng]]] =  f (dr. sSetSparseB naryFeatures) {
      So (dr.getSparseB naryFeatures.flatMap {
        case ( d, values) =>
          Try(featureContext.getFeature( d).getFeatureNa ).toOpt on.map {  d =>
             d -> values.toSet
          }
      }.toMap)
    } else None

    val sparseCont nuousFeatures: Opt on[Map[Str ng, Map[Str ng, Double]]] =
       f (dr. sSetSparseCont nuousFeatures) {
        So (dr.getSparseCont nuousFeatures.flatMap {
          case ( d, values) =>
            Try(featureContext.getFeature( d).getFeatureNa ).toOpt on.map {  d =>
               d -> values.map {
                case (str, value) =>
                  str -> value.toDouble
              }.toMap
            }
        }.toMap)
      } else None

    DebugDataRecord(
      b naryFeatures = b naryFeatures,
      cont nuousFeatures = cont nuousFeatures,
      d screteFeatures = d screteFeatures,
      str ngFeatures = str ngFeatures,
      sparseB naryFeatures = sparseB naryFeatures,
      sparseCont nuousFeatures = sparseCont nuousFeatures,
    )
  }

}
