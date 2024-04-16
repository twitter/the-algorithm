package com.tw ter.s mclusters_v2.scald ng.embedd ng.tfg

 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.dal.cl ent.dataset.SnapshotDALDatasetBase
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.D
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.Wr eExtens on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossClusterSa DC
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Ent yEmbedd ngsS ces
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.Embedd ngUt l
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.s mclusters_v2.thr ftscala.TfgTop cEmbedd ngs
 mport com.tw ter.s mclusters_v2.thr ftscala.UserTo nterested nClusterScores
 mport com.tw ter.s mclusters_v2.thr ftscala.{S mClustersEmbedd ng => Thr ftS mClustersEmbedd ng}
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone

/**
 * Jobs to generate Fav-based Top c-Follow-Graph (TFG) top c embedd ngs
 * A top c's fav-based TFG embedd ng  s t  sum of  s follo rs' fav-based  nterested n
 */

/**
./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tfg:fav_tfg_top c_embedd ngs-adhoc
 scald ng remote run \
  --user cassowary \
  --keytab /var/l b/tss/keys/fluffy/keytabs/cl ent/cassowary.keytab \
  --pr nc pal serv ce_acoount@TW TTER.B Z \
  --cluster blueb rd-qus1 \
  --ma n-class com.tw ter.s mclusters_v2.scald ng.embedd ng.tfg.FavTfgTop cEmbedd ngsAdhocApp \
  --target src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tfg:fav_tfg_top c_embedd ngs-adhoc \
  --hadoop-propert es "scald ng.w h.reducers.set.expl c ly=true mapreduce.job.reduces=4000" \
  -- --date 2020-12-08
 */
object FavTfgTop cEmbedd ngsAdhocApp extends TfgBasedTop cEmbedd ngsBaseApp w h AdhocExecut onApp {
  overr de val  sAdhoc: Boolean = true
  overr de val embedd ngType: Embedd ngType = Embedd ngType.FavTfgTop c
  overr de val embedd ngS ce: KeyValDALDataset[
    KeyVal[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng]
  ] = Ent yEmbedd ngsS ces.FavTfgTop cEmbedd ngsDataset
  overr de val pathSuff x: Str ng = "fav_tfg_top c_embedd ng"
  overr de val modelVers on: ModelVers on = ModelVers on.Model20m145kUpdated
  overr de val parquetDataS ce: SnapshotDALDatasetBase[TfgTop cEmbedd ngs] =
    Ent yEmbedd ngsS ces.FavTfgTop cEmbedd ngsParquetDataset
  overr de def scoreExtractor: UserTo nterested nClusterScores => Double = scores =>
    scores.favScore.getOrElse(0.0)
}

/**
./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tfg:fav_tfg_top c_embedd ngs
capesospy-v2 update --bu ld_locally --start_cron fav_tfg_top c_embedd ngs src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object FavTfgTop cEmbedd ngsSc duledApp
    extends TfgBasedTop cEmbedd ngsBaseApp
    w h Sc duledExecut onApp {
  overr de val  sAdhoc: Boolean = false
  overr de val embedd ngType: Embedd ngType = Embedd ngType.FavTfgTop c
  overr de val embedd ngS ce: KeyValDALDataset[
    KeyVal[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng]
  ] = Ent yEmbedd ngsS ces.FavTfgTop cEmbedd ngsDataset
  overr de val pathSuff x: Str ng = "fav_tfg_top c_embedd ng"
  overr de val modelVers on: ModelVers on = ModelVers on.Model20m145kUpdated
  overr de val parquetDataS ce: SnapshotDALDatasetBase[TfgTop cEmbedd ngs] =
    Ent yEmbedd ngsS ces.FavTfgTop cEmbedd ngsParquetDataset
  overr de def scoreExtractor: UserTo nterested nClusterScores => Double = scores =>
    scores.favScore.getOrElse(0.0)

  overr de val f rstT  : R chDate = R chDate("2020-05-25")
  overr de val batch ncre nt: Durat on = Days(1)
}

/**
./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tfg:fav_tfg_top c_embedd ngs_2020-adhoc
 scald ng remote run \
  --user cassowary \
  --keytab /var/l b/tss/keys/fluffy/keytabs/cl ent/cassowary.keytab \
  --pr nc pal serv ce_acoount@TW TTER.B Z \
  --cluster blueb rd-qus1 \
  --ma n-class com.tw ter.s mclusters_v2.scald ng.embedd ng.tfg.FavTfgTop cEmbedd ngs2020AdhocApp \
  --target src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tfg:fav_tfg_top c_embedd ngs_2020-adhoc \
  --hadoop-propert es "scald ng.w h.reducers.set.expl c ly=true mapreduce.job.reduces=4000" \
  -- --date 2020-12-08
 */
object FavTfgTop cEmbedd ngs2020AdhocApp
    extends TfgBasedTop cEmbedd ngsBaseApp
    w h AdhocExecut onApp {
  overr de val  sAdhoc: Boolean = true
  overr de val embedd ngType: Embedd ngType = Embedd ngType.FavTfgTop c
  overr de val embedd ngS ce: KeyValDALDataset[
    KeyVal[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng]
  ] = Ent yEmbedd ngsS ces.FavTfgTop cEmbedd ngs2020Dataset
  overr de val pathSuff x: Str ng = "fav_tfg_top c_embedd ng"
  overr de val modelVers on: ModelVers on = ModelVers on.Model20m145k2020
  overr de val parquetDataS ce: SnapshotDALDatasetBase[TfgTop cEmbedd ngs] =
    Ent yEmbedd ngsS ces.FavTfgTop cEmbedd ngs2020ParquetDataset
  overr de def scoreExtractor: UserTo nterested nClusterScores => Double = scores =>
    scores.favScore.getOrElse(0.0)
}

/**
./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tfg:fav_tfg_top c_embedd ngs_2020
capesospy-v2 update --bu ld_locally --start_cron fav_tfg_top c_embedd ngs_2020 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object FavTfgTop cEmbedd ngs2020Sc duledApp
    extends TfgBasedTop cEmbedd ngsBaseApp
    w h Sc duledExecut onApp {
  overr de val  sAdhoc: Boolean = false
  overr de val embedd ngType: Embedd ngType = Embedd ngType.FavTfgTop c
  overr de val embedd ngS ce: KeyValDALDataset[
    KeyVal[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng]
  ] = Ent yEmbedd ngsS ces.FavTfgTop cEmbedd ngs2020Dataset
  overr de val pathSuff x: Str ng = "fav_tfg_top c_embedd ng"
  overr de val modelVers on: ModelVers on = ModelVers on.Model20m145k2020
  overr de val parquetDataS ce: SnapshotDALDatasetBase[TfgTop cEmbedd ngs] =
    Ent yEmbedd ngsS ces.FavTfgTop cEmbedd ngs2020ParquetDataset
  overr de def scoreExtractor: UserTo nterested nClusterScores => Double = scores =>
    scores.favScore.getOrElse(0.0)

  overr de val f rstT  : R chDate = R chDate("2021-03-10")
  overr de val batch ncre nt: Durat on = Days(1)
}

/**
./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tfg:fav_tfg_top c_embedd ngs_2020_copy
scald ng scald ng remote run --target src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng/tfg:fav_tfg_top c_embedd ngs_2020_copy
 */

/**
 * T   s a copy job w re   copy t  prev ous vers on of TFG and wr e to a new one.
 * T  dependent dataset for TFG has been deleted.
 *  nstead of restart ng t  ent re job,   create t  temp hacky solut on to keep TFG dataset al ve unt l   deprecate top cs.
 * Hav ng a table TFG doesn't lead to a b g qual y concern b/c TFG  s bu lt from top c follows, wh ch  s relat ve stable
 * and   don't have new top cs anymore.
 */
object FavTfgTop cEmbedd ngs2020CopySc duledApp extends Sc duledExecut onApp {
  val  sAdhoc: Boolean = false
  val embedd ngType: Embedd ngType = Embedd ngType.FavTfgTop c
  val embedd ngS ce: KeyValDALDataset[
    KeyVal[S mClustersEmbedd ng d, Thr ftS mClustersEmbedd ng]
  ] = Ent yEmbedd ngsS ces.FavTfgTop cEmbedd ngs2020Dataset
  val pathSuff x: Str ng = "fav_tfg_top c_embedd ng"
  val modelVers on: ModelVers on = ModelVers on.Model20m145k2020

  overr de val f rstT  : R chDate = R chDate("2023-01-20")
  overr de val batch ncre nt: Durat on = Days(3)

  def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    DAL
      .readMostRecentSnapshotNoOlderThan(
        Ent yEmbedd ngsS ces.FavTfgTop cEmbedd ngs2020Dataset,
        Days(21))
      .w hRemoteReadPol cy(AllowCrossClusterSa DC)
      .toTypedP pe
      .wr eDALVers onedKeyValExecut on(
        Ent yEmbedd ngsS ces.FavTfgTop cEmbedd ngs2020Dataset,
        D.Suff x(
          Embedd ngUt l
            .getHdfsPath( sAdhoc =  sAdhoc,  sManhattanKeyVal = true, modelVers on, pathSuff x))
      )
  }
}
