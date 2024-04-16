package com.tw ter.ann.scald ng.offl ne.com.tw ter.ann.scald ng.benchmark

/*
T  job w ll generate KNN ground truth based user and  em embedd ngs.
 */

 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.D
 mport com.tw ter.ann.knn.thr ftscala.Knn
 mport com.tw ter.ann.knn.thr ftscala.Ne ghbor
 mport com.tw ter.ann.scald ng.offl ne. ndex ngStrategy
 mport com.tw ter.ann.scald ng.offl ne.Knn lper
 mport com.tw ter.ann.common.D stance
 mport com.tw ter.ml.featurestore.l b.embedd ng.Embedd ngW hEnt y
 mport com.tw ter.cortex.ml.embedd ngs.common.Embedd ngFormatArgsParser
 mport com.tw ter.cortex.ml.embedd ngs.common.Ent yK nd
 mport java.ut l.T  Zone
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.ann.scald ng.benchmark.User emKnnScalaDataset
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.User d

/**
 * T  job w ll take consu r and  em embedd ngs(e  r url or t et) and output Knn ent  es (user  d, (d stance,  em  d)).
 *
 * Example command to run t  adhoc job:
 *
 * scald ng remote run \
 * --target ann/src/ma n/scala/com/tw ter/ann/scald ng/benchmark:benchmark-adhoc \
 * --hadoop-propert es "mapreduce.map. mory.mb=8192 mapreduce.map.java.opts='-Xmx7618M' mapreduce.reduce. mory.mb=8192 mapreduce.reduce.java.opts='-Xmx7618M' mapred.task.t  out=0" \
 * --subm ter hadoopnest3.smf1.tw ter.com \
 * --user cortex-mlx \
 * --subm ter- mory 8000. gabyte \
 * --ma n-class com.tw ter.ann.scald ng.offl ne.com.tw ter.ann.scald ng.benchmark.KnnJob -- \
 * --dalEnv ron nt Prod \
 * --search_space_ent y_type user \
 * --user.feature_store_embedd ng Consu rFollowEmbedd ng300Dataset \
 * --user.feature_store_major_vers on 1569196895 \
 * --user.date_range 2019-10-23 \
 * --search_space.feature_store_embedd ng Consu rFollowEmbedd ng300Dataset \
 * --search_space.feature_store_major_vers on 1569196895 \
 * --search_space.date_range 2019-10-23 \
 * --date 2019-10-25 \
 * --vers on "consu r_follo r_test" \
 * --reducers 10000 \
 * --num_of_random_groups 20 \
 * --num_repl cas 1000 \
 * -- ndex ng_strategy. tr c  nnerProduct \
 * -- ndex ng_strategy.type hnsw \
 * -- ndex ng_strategy.d  ns on 300 \
 * -- ndex ng_strategy.ef_construct on 30 \
 * -- ndex ng_strategy.max_m 10 \
 * -- ndex ng_strategy.ef_query 50 \
 * --search_space_shards 3000 \
 * --query_shards 3000 \
 * --search_space.read_sample_rat o 0.038
 */
tra  KnnJobBase {
  val seed: Long = 123

  def getKnnDataset[B <: Ent y d, D <: D stance[D]](
    args: Args
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[Knn] = {

    val consu rP pe: TypedP pe[Embedd ngW hEnt y[User d]] = Embedd ngFormatArgsParser.User
      .getEmbedd ngFormat(args, "user")
      .getEmbedd ngs

    val  emP pe = Ent yK nd
      .getEnt yK nd(args("search_space_ent y_type"))
      .parser
      .getEmbedd ngFormat(args, "search_space")
      .getEmbedd ngs

    Knn lper
    // Refer to t  docu ntat on of f ndNearestNe ghb sW h ndex ngStrategy for more
    //  nformat on about how to set t se sett ngs.
      .f ndNearestNe ghb sW h ndex ngStrategy[User d, B, D](
        queryEmbedd ngs = consu rP pe,
        searchSpaceEmbedd ngs =  emP pe.as nstanceOf[TypedP pe[Embedd ngW hEnt y[B]]],
        numNe ghbors = args. nt("cand date_per_user", 20),
        reducersOpt on = args.opt onal("reducers").map(_.to nt),
        numOfSearchGroups = args. nt("num_of_random_groups"),
        numRepl cas = args. nt("num_repl cas"),
         ndex ngStrategy =  ndex ngStrategy.parse(args).as nstanceOf[ ndex ngStrategy[D]],
        queryShards = args.opt onal("query_shards").map(_.to nt),
        searchSpaceShards = args.opt onal("search_space_shards").map(_.to nt)
      )
      .map {
        case (user,  ems) =>
          val ne ghbors =  ems.map {
            case ( em, d stance) =>
              Ne ghbor(
                d stance.d stance,
                 em.toThr ft
              )
          }
          Knn(user.toThr ft, ne ghbors)
      }
  }
}

object KnnJob extends Tw terExecut onApp w h KnnJobBase {

  val KnnPathSuff x: Str ng = "/user/cortex-mlx/qualatat ve_analys s/knn_ground_truth/"
  val part  onKey: Str ng = "vers on"

  overr de def job: Execut on[Un ] = Execut on.w h d {  mpl c  un que d =>
    Execut on.getArgs.flatMap { args: Args =>
       mpl c  val t  Zone: T  Zone = T  Zone.getDefault
       mpl c  val dateParser: DateParser = DateParser.default
       mpl c  val dateRange: DateRange = DateRange.parse(args.l st("date"))(t  Zone, dateParser)

      getKnnDataset(args).wr eDALExecut on(
        User emKnnScalaDataset,
        D.Da ly,
        D.Suff x(KnnPathSuff x),
        D.Parquet,
        Set(D.Part  on(part  onKey, args("vers on"), D.Part  onType.Str ng))
      )
    }
  }

}
