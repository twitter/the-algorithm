package com.tw ter.s mclusters_v2.t et_s m lar y

 mport com.tw ter.ml.ap .Feature.{B nary, Cont nuous, D screte, SparseCont nuous}
 mport com.tw ter.ml.ap .ut l.FDsl._
 mport com.tw ter.ml.ap .{DataRecord, FeatureContext,  RecordOneToOneAdapter}
 mport com.tw ter.ml.featurestore.catalog.features.recom ndat ons.ProducerS mClustersEmbedd ng
 mport com.tw ter.ml.featurestore.l b.User d
 mport com.tw ter.ml.featurestore.l b.data.{Pred ct onRecord, Pred ct onRecordAdapter}
 mport com.tw ter.ml.featurestore.l b.ent y.Ent y
 mport com.tw ter.ml.featurestore.l b.feature.BoundFeatureSet

object T etS m lar yFeatures {
  val QueryT et d = new D screte("query_t et. d")
  val Cand dateT et d = new D screte("cand date_t et. d")
  val QueryT etEmbedd ng = new SparseCont nuous("query_t et.s mclusters_embedd ng")
  val Cand dateT etEmbedd ng = new SparseCont nuous("cand date_t et.s mclusters_embedd ng")
  val QueryT etEmbedd ngNorm = new Cont nuous("query_t et.embedd ng_norm")
  val Cand dateT etEmbedd ngNorm = new Cont nuous("cand date_t et.embedd ng_norm")
  val QueryT etT  stamp = new D screte("query_t et.t  stamp")
  val Cand dateT etT  stamp = new D screte("cand date_t et.t  stamp")
  val T etPa rCount = new D screte("popular y_count.t et_pa r")
  val QueryT etCount = new D screte("popular y_count.query_t et")
  val Cos neS m lar y = new Cont nuous(" ta.cos ne_s m lar y")
  val Label = new B nary("co-engage nt.label")

  val FeatureContext: FeatureContext = new FeatureContext(
    QueryT et d,
    Cand dateT et d,
    QueryT etEmbedd ng,
    Cand dateT etEmbedd ng,
    QueryT etEmbedd ngNorm,
    Cand dateT etEmbedd ngNorm,
    QueryT etT  stamp,
    Cand dateT etT  stamp,
    T etPa rCount,
    QueryT etCount,
    Cos neS m lar y,
    Label
  )

  def  sCoengaged(dataRecord: DataRecord): Boolean = {
    dataRecord.getFeatureValue(Label)
  }
}

class T etS m lar yFeaturesStoreConf g( dent f er: Str ng) {
  val b nd ng dent f er: Ent y[User d] = Ent y[User d]( dent f er)

  val featureStoreBoundFeatureSet: BoundFeatureSet = BoundFeatureSet(
    ProducerS mClustersEmbedd ng.FavBasedEmbedd ng20m145kUpdated.b nd(b nd ng dent f er))

  val pred ct onRecordAdapter:  RecordOneToOneAdapter[Pred ct onRecord] =
    Pred ct onRecordAdapter.oneToOne(featureStoreBoundFeatureSet)
}
