package com.tw ter.ann.scald ng.offl ne

 mport com.tw ter.ann.common. tr c
 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.ml.featurestore.l b.User d
 mport com.tw ter.ml.featurestore.l b.embedd ng.Embedd ngW hEnt y
 mport com.tw ter.cortex.ml.embedd ngs.common.Ent yK nd
 mport com.tw ter.ent yembedd ngs.ne ghbors.thr ftscala.{Ent yKey, NearestNe ghbors}
 mport com.tw ter.scald ng.commons.s ce.Vers onedKeyValS ce
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng.{Args, DateOps, DateParser, DateRange, Execut on, TypedTsv, Un que D}
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.search.common.f le.{AbstractF le, LocalF le}
 mport java.ut l.T  Zone

/**
 * Generates t  nearest ne ghb  for users and store t m  n Manhattan format  .e sequence f les.
 * See README for oscar usage.
 */
object KnnOffl neJob extends Tw terExecut onApp {
  overr de def job: Execut on[Un ] = Execut on.w h d {  mpl c  un que d =>
    Execut on.getArgs.flatMap { args: Args =>
      val knnD rectoryOpt: Opt on[Str ng] = args.opt onal("knn_d rectory")
      knnD rectoryOpt match {
        case So (knnD rectory) =>
          Execut on.w hCac dF le(knnD rectory) { d rectory =>
            execute(args, So (new LocalF le(d rectory.f le)))
          }
        case None =>
          execute(args, None)
      }
    }
  }

  /**
   * Execute KnnOffl neJob
   * @param args: T  args object for t  job
   * @param abstractF le: An opt onal of producer embedd ng path
   */
  def execute(
    args: Args,
    abstractF le: Opt on[AbstractF le]
  )(
     mpl c  un que D: Un que D
  ): Execut on[Un ] = {
     mpl c  val tz: T  Zone = T  Zone.getDefault()
     mpl c  val dp: DateParser = DateParser.default
     mpl c  val dateRange = DateRange.parse(args.l st("date"))(DateOps.UTC, DateParser.default)
     mpl c  val key nject = B naryScalaCodec(Ent yKey)
     mpl c  val value nject = B naryScalaCodec(NearestNe ghbors)

    val ent yK nd = Ent yK nd.getEnt yK nd(args("producer_ent y_k nd"))
    val  tr c =  tr c.fromStr ng(args(" tr c"))
    val outputPath: Str ng = args("output_path")
    val numNe ghbors:  nt = args("ne ghbors").to nt
    val ef = args.getOrElse("ef", numNe ghbors.toStr ng).to nt
    val reducers:  nt = args("reducers").to nt
    val knnD  ns on:  nt = args("d  ns on").to nt
    val debugOutputPath: Opt on[Str ng] = args.opt onal("debug_output_path")
    val f lterPath: Opt on[Str ng] = args.opt onal("users_f lter_path")
    val shards:  nt = args.getOrElse("shards", "100").to nt
    val useHashJo n: Boolean = args.getOrElse("use_hash_jo n", "false").toBoolean
    val mhOutput = Vers onedKeyValS ce[Ent yKey, NearestNe ghbors](
      path = outputPath,
      s ceVers on = None,
      s nkVers on = None,
      maxFa lures = 0,
      vers onsToKeep = 1
    )

    val consu rEmbedd ngs: TypedP pe[Embedd ngW hEnt y[User d]] =
      Knn lper.getF lteredUserEmbedd ngs(
        args,
        f lterPath,
        reducers,
        useHashJo n
      )

    val ne ghborsP pe: TypedP pe[(Ent yKey, NearestNe ghbors)] = Knn lper.getNe ghborsP pe(
      args,
      ent yK nd,
       tr c,
      ef,
      consu rEmbedd ngs,
      abstractF le,
      reducers,
      numNe ghbors,
      knnD  ns on
    )

    val ne ghborsExecut on: Execut on[Un ] = ne ghborsP pe
      .wr eExecut on(mhOutput)

    // Wr e manual  nspect on
    debugOutputPath match {
      case So (path: Str ng) =>
        val debugExecut on: Execut on[Un ] = KnnDebug
          .getDebugTable(
            ne ghborsP pe = ne ghborsP pe,
            shards = shards,
            reducers = reducers
          )
          .wr eExecut on(TypedTsv(path))
        Execut on.z p(debugExecut on, ne ghborsExecut on).un 
      case None => ne ghborsExecut on
    }
  }
}
