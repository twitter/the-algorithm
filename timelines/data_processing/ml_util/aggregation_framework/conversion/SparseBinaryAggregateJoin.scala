package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.convers on

 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng.typed.UnsortedGrouped
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.TypedAggregateGroup
 mport java.ut l.{Set => JSet}
 mport scala.collect on.JavaConverters._

object SparseB naryAggregateJo n {
   mport TypedAggregateGroup._

  def makeKey(record: DataRecord, jo nKeyL st: L st[Feature[_]]): Str ng = {
    jo nKeyL st.map {
      case sparseKey: Feature.SparseB nary =>
        SR chDataRecord(record).getFeatureValue(sparseFeature(sparseKey))
      case nonSparseKey: Feature[_] =>
        SR chDataRecord(record).getFeatureValue(nonSparseKey)
    }.toStr ng
  }

  /**
   * @param record Data record to get all poss ble sparse aggregate keys from
   * @param L st of jo n key features (so  can be sparse and so  non-sparse)
   * @return A l st of str ng keys to use for jo n ng
   */
  def makeKeyPermutat ons(record: DataRecord, jo nKeyL st: L st[Feature[_]]): L st[Str ng] = {
    val all dValues = jo nKeyL st.flatMap {
      case sparseKey: Feature.SparseB nary => {
        val  d = sparseKey.getDenseFeature d
        val valuesOpt = Opt on(SR chDataRecord(record).getFeatureValue(sparseKey))
          .map(_.as nstanceOf[JSet[Str ng]].asScala.toSet)
        valuesOpt.map { ( d, _) }
      }
      case nonSparseKey: Feature[_] => {
        val  d = nonSparseKey.getDenseFeature d
        Opt on(SR chDataRecord(record).getFeatureValue(nonSparseKey)).map { value =>
          ( d, Set(value.toStr ng))
        }
      }
    }
    sparseB naryPermutat ons(all dValues).toL st.map {  dValues =>
      jo nKeyL st.map { key =>  dValues.getOrElse(key.getDenseFeature d, "") }.toStr ng
    }
  }

  pr vate[t ] def mkKey ndexedAggregates(
    jo nFeaturesDataSet: DataSetP pe,
    jo nKeyL st: L st[Feature[_]]
  ): TypedP pe[(Str ng, DataRecord)] =
    jo nFeaturesDataSet.records
      .map { record => (makeKey(record, jo nKeyL st), record) }

  pr vate[t ] def mkKey ndexed nput(
     nputDataSet: DataSetP pe,
    jo nKeyL st: L st[Feature[_]]
  ): TypedP pe[(Str ng, DataRecord)] =
     nputDataSet.records
      .flatMap { record =>
        for {
          key <- makeKeyPermutat ons(record, jo nKeyL st)
        } y eld { (key, record) }
      }

  pr vate[t ] def mkKey ndexed nputW hUn que d(
     nputDataSet: DataSetP pe,
    jo nKeyL st: L st[Feature[_]],
    un que dFeatureL st: L st[Feature[_]]
  ): TypedP pe[(Str ng, Str ng)] =
     nputDataSet.records
      .flatMap { record =>
        for {
          key <- makeKeyPermutat ons(record, jo nKeyL st)
        } y eld { (key, makeKey(record, un que dFeatureL st)) }
      }

  pr vate[t ] def mkRecord ndexedAggregates(
    key ndexed nput: TypedP pe[(Str ng, DataRecord)],
    key ndexedAggregates: TypedP pe[(Str ng, DataRecord)]
  ): UnsortedGrouped[DataRecord, L st[DataRecord]] =
    key ndexed nput
      .jo n(key ndexedAggregates)
      .map { case (_, ( nputRecord, aggregateRecord)) => ( nputRecord, aggregateRecord) }
      .group
      .toL st

  pr vate[t ] def mkRecord ndexedAggregatesW hUn que d(
    key ndexed nput: TypedP pe[(Str ng, Str ng)],
    key ndexedAggregates: TypedP pe[(Str ng, DataRecord)]
  ): UnsortedGrouped[Str ng, L st[DataRecord]] =
    key ndexed nput
      .jo n(key ndexedAggregates)
      .map { case (_, ( nput d, aggregateRecord)) => ( nput d, aggregateRecord) }
      .group
      .toL st

  def mkJo nedDataSet(
     nputDataSet: DataSetP pe,
    jo nFeaturesDataSet: DataSetP pe,
    record ndexedAggregates: UnsortedGrouped[DataRecord, L st[DataRecord]],
     rgePol cy: SparseB nary rgePol cy
  ): TypedP pe[DataRecord] =
     nputDataSet.records
      .map(record => (record, ()))
      .leftJo n(record ndexedAggregates)
      .map {
        case ( nputRecord, (_, aggregateRecordsOpt)) =>
          aggregateRecordsOpt
            .map { aggregateRecords =>
               rgePol cy. rgeRecord(
                 nputRecord,
                aggregateRecords,
                jo nFeaturesDataSet.featureContext
              )
               nputRecord
            }
            .getOrElse( nputRecord)
      }

  def mkJo nedDataSetW hUn que d(
     nputDataSet: DataSetP pe,
    jo nFeaturesDataSet: DataSetP pe,
    record ndexedAggregates: UnsortedGrouped[Str ng, L st[DataRecord]],
     rgePol cy: SparseB nary rgePol cy,
    un que dFeatureL st: L st[Feature[_]]
  ): TypedP pe[DataRecord] =
     nputDataSet.records
      .map(record => (makeKey(record, un que dFeatureL st), record))
      .leftJo n(record ndexedAggregates)
      .map {
        case (_, ( nputRecord, aggregateRecordsOpt)) =>
          aggregateRecordsOpt
            .map { aggregateRecords =>
               rgePol cy. rgeRecord(
                 nputRecord,
                aggregateRecords,
                jo nFeaturesDataSet.featureContext
              )
               nputRecord
            }
            .getOrElse( nputRecord)
      }

  /**
   *  f un que dFeatures  s non-empty and t  jo n keys  nclude a sparse b nary
   * key, t  jo n w ll use t  set of keys as a un que  d to reduce
   *  mory consumpt on.   should need t  opt on only for
   *  mory- ntens ve jo ns to avo d OOM errors.
   */
  def apply(
     nputDataSet: DataSetP pe,
    jo nKeys: Product,
    jo nFeaturesDataSet: DataSetP pe,
     rgePol cy: SparseB nary rgePol cy = P ckF rstRecordPol cy,
    un que dFeaturesOpt: Opt on[Product] = None
  ): DataSetP pe = {
    val jo nKeyL st = jo nKeys.product erator.toL st.as nstanceOf[L st[Feature[_]]]
    val sparseB naryJo nKeySet =
      jo nKeyL st.toSet.f lter(_.getFeatureType() == FeatureType.SPARSE_B NARY)
    val conta nsSparseB naryKey = !sparseB naryJo nKeySet. sEmpty
     f (conta nsSparseB naryKey) {
      val un que dFeatureL st = un que dFeaturesOpt
        .map(un que dFeatures =>
          un que dFeatures.product erator.toL st.as nstanceOf[L st[Feature[_]]])
        .getOrElse(L st.empty[Feature[_]])
      val key ndexedAggregates = mkKey ndexedAggregates(jo nFeaturesDataSet, jo nKeyL st)
      val jo nedDataSet =  f (un que dFeatureL st. sEmpty) {
        val key ndexed nput = mkKey ndexed nput( nputDataSet, jo nKeyL st)
        val record ndexedAggregates =
          mkRecord ndexedAggregates(key ndexed nput, key ndexedAggregates)
        mkJo nedDataSet( nputDataSet, jo nFeaturesDataSet, record ndexedAggregates,  rgePol cy)
      } else {
        val key ndexed nput =
          mkKey ndexed nputW hUn que d( nputDataSet, jo nKeyL st, un que dFeatureL st)
        val record ndexedAggregates =
          mkRecord ndexedAggregatesW hUn que d(key ndexed nput, key ndexedAggregates)
        mkJo nedDataSetW hUn que d(
           nputDataSet,
          jo nFeaturesDataSet,
          record ndexedAggregates,
           rgePol cy,
          un que dFeatureL st
        )
      }

      DataSetP pe(
        jo nedDataSet,
         rgePol cy. rgeContext(
           nputDataSet.featureContext,
          jo nFeaturesDataSet.featureContext
        )
      )
    } else {
       nputDataSet.jo nW hSmaller(jo nKeys, jo nFeaturesDataSet) { _.pass }
    }
  }
}
