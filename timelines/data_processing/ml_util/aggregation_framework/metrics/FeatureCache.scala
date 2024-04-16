package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs

 mport com.tw ter.ml.ap ._
 mport scala.collect on.mutable

tra  FeatureCac [T] {
  /*
   * Constructs feature na s from scratch g ven an aggregate query and an output
   * feature na . E.g. g ven  an operator and "sum". T  funct on  s slow and should
   * only be called at pre-computat on t  .
   *
   * @param query Deta ls of aggregate feature
   * @na  Na  of "output" feature for wh ch   want to construct feature na 
   * @return Full na  of output feature
   */
  pr vate def uncac dFullFeatureNa (query: AggregateFeature[T], na : Str ng): Str ng =
    L st(query.featurePref x, na ).mkStr ng(".")

  /*
   * A cac  from (aggregate query, output feature na ) -> fully qual f ed feature na 
   * lazy s nce   doesn't need to be ser al zed to t  mappers
   */
  pr vate lazy val featureNa Cac  = mutable.Map[(AggregateFeature[T], Str ng), Str ng]()

  /*
   * A cac  from (aggregate query, output feature na ) -> precomputed output feature
   * lazy s nce   doesn't need to be ser al zed to t  mappers
   */
  pr vate lazy val featureCac  = mutable.Map[(AggregateFeature[T], Str ng), Feature[_]]()

  /**
   * G ven an (aggregate query, output feature na , output feature type),
   * look   up us ng featureNa Cac  and featureCac , fall ng back to uncac dFullFeatureNa ()
   * as a last resort to construct a precomputed output feature. Should only be
   * called at pre-computat on t  .
   *
   * @param query Deta ls of aggregate feature
   * @na  Na  of "output" feature   want to precompute
   * @aggregateFeatureType type of "output" feature   want to precompute
   */
  def cac dFullFeature(
    query: AggregateFeature[T],
    na : Str ng,
    aggregateFeatureType: FeatureType
  ): Feature[_] = {
    lazy val cac dFeatureNa  = featureNa Cac .getOrElseUpdate(
      (query, na ),
      uncac dFullFeatureNa (query, na )
    )

    def uncac dFullFeature(): Feature[_] = {
      val personalDataTypes =
        Aggregat on tr cCommon.der vePersonalDataTypes(query.feature, query.label)

      aggregateFeatureType match {
        case FeatureType.B NARY => new Feature.B nary(cac dFeatureNa , personalDataTypes)
        case FeatureType.D SCRETE => new Feature.D screte(cac dFeatureNa , personalDataTypes)
        case FeatureType.STR NG => new Feature.Text(cac dFeatureNa , personalDataTypes)
        case FeatureType.CONT NUOUS => new Feature.Cont nuous(cac dFeatureNa , personalDataTypes)
        case FeatureType.SPARSE_B NARY =>
          new Feature.SparseB nary(cac dFeatureNa , personalDataTypes)
        case FeatureType.SPARSE_CONT NUOUS =>
          new Feature.SparseCont nuous(cac dFeatureNa , personalDataTypes)
      }
    }

    featureCac .getOrElseUpdate(
      (query, na ),
      uncac dFullFeature()
    )
  }
}
