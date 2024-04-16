package com.tw ter.s mclusters_v2.scald ng.embedd ng.tfg

 mport com.tw ter.dal.cl ent.dataset.{KeyValDALDataset, SnapshotDALDatasetBase}
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Ent yEmbedd ngsS ces
 mport com.tw ter.s mclusters_v2.thr ftscala.{
  Embedd ngType,
  ModelVers on,
  S mClustersEmbedd ng d,
  TfgTop cEmbedd ngs,
  UserTo nterested nClusterScores,
  S mClustersEmbedd ng => Thr ftS mClustersEmbedd ng
}
 mport com.tw ter.wtf.scald ng.jobs.common.{AdhocExecut onApp, Sc duledExecut onApp}

/**
 * Jobs to generate Logfav-based Top c-Follow-Graph (TFG) top c embedd ngs
 * A top c's logfav-based TFG embedd ng  s t  sum of  s follo rs' logfav-based  nterested n
 */

/**
./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tfg:logfav_tfg_top c_embedd ngs-adhoc
 scald ng remote run \
  --user cassowary \
  --keytab /var/l b/tss/keys/fluffy/keytabs/cl ent/cassowary.keytab \
  --pr nc pal serv ce_acoount@TW TTER.B Z \
  --cluster blueb rd-qus1 \
  --ma n-class com.tw ter.s mclusters_v2.scald ng.embedd ng.tfg.LogFavTfgTop cEmbedd ngsAdhocApp \
  --target src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tfg:logfav_tfg_top c_embedd ngs-adhoc \
  --hadoop-propert es "scald ng.w h.reducers.set.expl c ly=true mapreduce.job.reduces=4000" \
  -- --date 2020-12-08
 */
object LogFavTfgTop cEmbedd ngsAdhocApp
    extends TfgBasedTop cEmbedd ngsBaseApp
    w h AdhocExecut onApp {
  overr de val  sAdhoc: Boolean = true
  overr de val embedd ngType: Embedd ngType = Embedd ngType.LogFavTfgTop c
  overr de val embedd ngS ce: KeyValDALDataset[
    KeyVal[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng]
  ] = Ent yEmbedd ngsS ces.LogFavTfgTop cEmbedd ngsDataset
  overr de val pathSuff x: Str ng = "logfav_tfg_top c_embedd ng"
  overr de val modelVers on: ModelVers on = ModelVers on.Model20m145kUpdated
  overr de val parquetDataS ce: SnapshotDALDatasetBase[TfgTop cEmbedd ngs] =
    Ent yEmbedd ngsS ces.LogFavTfgTop cEmbedd ngsParquetDataset
  overr de def scoreExtractor: UserTo nterested nClusterScores => Double = scores =>
    scores.logFavScore.getOrElse(0.0)
}

/**
./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tfg:logfav_tfg_top c_embedd ngs
capesospy-v2 update --bu ld_locally --start_cron logfav_tfg_top c_embedd ngs src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object LogFavTfgTop cEmbedd ngsSc duledApp
    extends TfgBasedTop cEmbedd ngsBaseApp
    w h Sc duledExecut onApp {
  overr de val  sAdhoc: Boolean = false
  overr de val embedd ngType: Embedd ngType = Embedd ngType.LogFavTfgTop c
  overr de val embedd ngS ce: KeyValDALDataset[
    KeyVal[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng]
  ] = Ent yEmbedd ngsS ces.LogFavTfgTop cEmbedd ngsDataset
  overr de val pathSuff x: Str ng = "logfav_tfg_top c_embedd ng"
  overr de val modelVers on: ModelVers on = ModelVers on.Model20m145kUpdated
  overr de def scoreExtractor: UserTo nterested nClusterScores => Double = scores =>
    scores.logFavScore.getOrElse(0.0)
  overr de val parquetDataS ce: SnapshotDALDatasetBase[TfgTop cEmbedd ngs] =
    Ent yEmbedd ngsS ces.LogFavTfgTop cEmbedd ngsParquetDataset
  overr de val f rstT  : R chDate = R chDate("2020-05-25")
  overr de val batch ncre nt: Durat on = Days(1)
}
