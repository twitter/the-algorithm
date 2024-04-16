package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs

 mport com.tw ter.algeb rd.DecayedValue
 mport com.tw ter.algeb rd.DecayedValueMono d
 mport com.tw ter.algeb rd.Mono d
 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType
 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.ut l.Durat on
 mport java.lang.{Long => JLong}
 mport java.ut l.{HashSet => JHashSet}
 mport java.ut l.{Set => JSet}

object Aggregat on tr cCommon {
  /* Shared def n  ons and ut ls that can be reused by ch ld classes */
  val Eps lon: Double = 1e-6
  val decayedValueMono d: Mono d[DecayedValue] = DecayedValueMono d(Eps lon)
  val T  stampHash: JLong = SharedFeatures.T MESTAMP.getDenseFeature d()

  def toDecayedValue(tv: T  dValue[Double], halfL fe: Durat on): DecayedValue = {
    DecayedValue.bu ld(
      tv.value,
      tv.t  stamp. nM ll seconds,
      halfL fe. nM ll seconds
    )
  }

  def getT  stamp(
    record: DataRecord,
    t  stampFeature: Feature[JLong] = SharedFeatures.T MESTAMP
  ): Long = {
    Opt on(
      SR chDataRecord(record)
        .getFeatureValue(t  stampFeature)
    ).map(_.toLong)
      .getOrElse(0L)
  }

  /*
   * Un on t  PDTs of t   nput featureOpts.
   * Return null  f empty, else t  JSet[PersonalDataType]
   */
  def der vePersonalDataTypes(features: Opt on[Feature[_]]*): JSet[PersonalDataType] = {
    val un onPersonalDataTypes = new JHashSet[PersonalDataType]()
    for {
      featureOpt <- features
      feature <- featureOpt
      pdtSetOpt onal = feature.getPersonalDataTypes
       f pdtSetOpt onal. sPresent
      pdtSet = pdtSetOpt onal.get
    } un onPersonalDataTypes.addAll(pdtSet)
     f (un onPersonalDataTypes. sEmpty) null else un onPersonalDataTypes
  }
}
