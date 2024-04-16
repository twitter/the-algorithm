package com.tw ter.graph_feature_serv ce.scald ng.adhoc

 mport com.tw ter.b ject on. nject on
 mport com.tw ter.fr gate.common.constdb_ut l. nject ons
 mport com.tw ter.ml.ap .Feature.D screte
 mport com.tw ter.ml.ap .{Da lySuff xFeatureS ce, DataSetP pe, R chDataRecord}
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport java.n o.ByteBuffer
 mport java.ut l.T  Zone

object RandomRequestGenerat onJob {
   mpl c  val t  Zone: T  Zone = DateOps.UTC
   mpl c  val dateParser: DateParser = DateParser.default

  val t  l neRecapDataSetPath: Str ng =
    "/atla/proc2/user/t  l nes/processed/suggests/recap/data_records"

  val USER_ D = new D screte(" ta.user_ d")
  val AUTHOR_ D = new D screte(" ta.author_ d")

  val t  l neRecapOutPutPath: Str ng = "/user/cassowary/gfs/adhoc/t  l ne_data"

   mpl c  val  nj:  nject on[Long, ByteBuffer] =  nject ons.long2Var nt

  def run(
    dataSetPath: Str ng,
    outPutPath: Str ng,
    numOfPa rsToTake:  nt
  )(
     mpl c  dateRange: DateRange,
    un que D: Un que D
  ): Execut on[Un ] = {

    val NumUserAuthorPa rs = Stat("NumUserAuthorPa rs")

    val dataSet: DataSetP pe = Da lySuff xFeatureS ce(dataSetPath).read

    val userAuthorPa rs: TypedP pe[(Long, Long)] = dataSet.records.map { record =>
      val r chRecord = new R chDataRecord(record, dataSet.featureContext)

      val user d = r chRecord.getFeatureValue(USER_ D)
      val author d = r chRecord.getFeatureValue(AUTHOR_ D)
      NumUserAuthorPa rs. nc()
      (user d, author d)
    }

    userAuthorPa rs
      .l m (numOfPa rsToTake)
      .wr eExecut on(
        TypedTsv[(Long, Long)](outPutPath)
      )
  }
}

/**
 * ./bazel bundle graph-feature-serv ce/src/ma n/scald ng/com/tw ter/graph_feature_serv ce/scald ng/adhoc:all
 *
 * oscar hdfs --screen --user cassowary --tee gfs_log --bundle gfs_random_request-adhoc \
      --tool com.tw ter.graph_feature_serv ce.scald ng.adhoc.RandomRequestGenerat onApp \
      -- --date 2018-08-11  \
      -- nput /atla/proc2/user/t  l nes/processed/suggests/recap/data_records \
      --output /user/cassowary/gfs/adhoc/t  l ne_data
 */
object RandomRequestGenerat onApp extends Tw terExecut onApp {
   mport RandomRequestGenerat onJob._
  overr de def job: Execut on[Un ] = Execut on.w h d {  mpl c  un que d =>
    Execut on.getArgs.flatMap { args: Args =>
       mpl c  val dateRange: DateRange = DateRange.parse(args.l st("date"))(t  Zone, dateParser)
      run(
        args.opt onal(" nput").getOrElse(t  l neRecapDataSetPath),
        args.opt onal("output").getOrElse(t  l neRecapOutPutPath),
        args. nt("num_pa rs", 3000)
      )
    }
  }
}
