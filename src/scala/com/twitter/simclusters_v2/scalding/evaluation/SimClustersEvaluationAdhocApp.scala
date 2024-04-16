package com.tw ter.s mclusters_v2.scald ng.evaluat on

 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.ProcAtla
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.cand date_s ce.ClusterRanker
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdhocKeyValS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces.ClusterTopKT etsH lySuff xS ce
 mport com.tw ter.s mclusters_v2.hdfs_s ces.S mclustersV2 nterested nScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.T etEvaluat onT  l nesReferenceSetScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.thr ftscala.Cand dateT et
 mport com.tw ter.s mclusters_v2.thr ftscala.Cand dateT ets
 mport com.tw ter.s mclusters_v2.thr ftscala.ClusterTopKT etsW hScores
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport com.tw ter.s mclusters_v2.thr ftscala.D splayLocat on
 mport com.tw ter.s mclusters_v2.thr ftscala.ReferenceT ets
 mport com.tw ter.s mclusters_v2.scald ng.offl ne_job.Offl neRecConf g
 mport com.tw ter.s mclusters_v2.scald ng.offl ne_job.Offl neT etRecom ndat on
 mport java.ut l.T  Zone

/**
 * Do evaluat ons for S mClusters' t et recom ndat ons by us ng offl ne datasets.
 * T  job does t  follow ng:
 *   1. Take  n a test date range, for wh ch t  offl ne s mclusters rec w ll be evaluated
 *   2. For all users that had t et  mpress ons  n t  l nes dur ng t  per od, generate offl ne
 *      S mClusters cand date t ets for t se users
 *   3. Run offl ne evaluat on and return  tr cs

./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/evaluat on:s mcluster_offl ne_eval_adhoc

Note: Never spec fy reference date range across more than 1 day!
oscar hdfs --user cassowary --screen --screen-detac d --tee y _ldap/prod_percent le \
 --bundle s mcluster_offl ne_eval_adhoc \
 --tool com.tw ter.s mclusters_v2.scald ng.evaluat on.S mClustersEvaluat onAdhocApp \
 -- --cand_t et_date 2019-03-04T00 2019-03-04T23 \
 --ref_t et_date 2019-03-05T00 2019-03-05T01 \
 --t  l ne_t et rect et \
 --sample_rate 0.05 \
 --max_cand_t ets 16000000 \
 --m n_t et_score 0.0 \
 --user_ nterested_ n_d r /user/fr gate/y _ldap/ nterested_ n_cop edFromAtlaProc_20190228 \
 --cluster_top_k_d r /user/cassowary/y _ldap/offl ne_s mcluster_20190304/cluster_top_k_t ets \
 --output_d r /user/cassowary/y _ldap/prod_percent le \
 --toEma lAddress y _ldap@tw ter.com \
 --testRunNa  Test ngProdOn0305Data
 */
object S mClustersEvaluat onAdhocApp extends Tw terExecut onApp {
  pr vate val maxT etResults = 40
  pr vate val maxClustersToQuery = 20

  @Overr de
  def job: Execut on[Un ] = {
    Execut on.w hArgs { args =>
      Execut on.w h d {  mpl c  un que d =>
         mpl c  val tz: T  Zone = DateOps.UTC
         mpl c  val dateParser: DateParser = DateParser.default

        val candT etDateRange = DateRange.parse(args.l st("cand_t et_date"))
        val refT etDateRange = DateRange.parse(args.l st("ref_t et_date"))
        val toEma lAddressOpt = args.opt onal("toEma lAddress")
        val testRunNa  = args.opt onal("testRunNa ")

        pr ntln(
          s"Us ng S mClusters t ets from ${candT etDateRange.start} to ${candT etDateRange.end}")
        pr ntln(s"Us ng T  l nes t ets on t  day of ${refT etDateRange.start}")

        // separate t ets from d fferent d splay locat ons for now
        val t etType = args("t  l ne_t et") match {
          case "rect et" => D splayLocat on.T  l nesRect et
          case "recap" => D splayLocat on.T  l nesRecap
          case e =>
            throw new  llegalArgu ntExcept on(s"$e  sn't a val d t  l ne d splay locat on")
        }

        val sampleRate = args.double("sample_rate", 1.0)
        val val dRefP pe = getProdT  l neReference(t etType, refT etDateRange, sampleRate)
        val targetUserP pe = val dRefP pe.map { _.targetUser d }

        // Read a f xed-path  n atla  f prov ded, ot rw se read prod data from atla for date range
        val user nterest nP pe = args.opt onal("user_ nterested_ n_d r") match {
          case So (f xedPath) =>
            pr ntln(s"user_ nterested_ n_d r  s prov ded at: $f xedPath. Read ng f xed path data.")
            TypedP pe.from(AdhocKeyValS ces. nterested nS ce(f xedPath))
          case _ =>
            pr ntln(s"user_ nterested_ n_d r  sn't prov ded. Read ng prod data.")
             nterested nProdS ce(candT etDateRange)
        }

        // Offl ne s mulat on of t  dataset
        val clusterTopKD r = args("cluster_top_k_d r")
        pr ntln(s"cluster_top_k_d r  s def ned at: $clusterTopKD r")
        val clusterTopKP pe = TypedP pe.from(
          ClusterTopKT etsH lySuff xS ce(clusterTopKD r, candT etDateRange)
        )

        // Conf gs for offl ne s mcluster t et recom ndat on
        val maxT etRecs = args. nt("max_cand_t ets", 30000000)
        val m nT etScoreThreshold = args.double("m n_t et_score", 0.0)

        val offl neRecConf g = Offl neRecConf g(
          maxT etRecs,
          maxT etResults,
          maxClustersToQuery,
          m nT etScoreThreshold,
          ClusterRanker.RankByNormal zedFavScore
        )
        pr ntln("S mClusters offl ne conf g: " + offl neRecConf g)

        getVal dCand date(
          targetUserP pe,
          user nterest nP pe,
          clusterTopKP pe,
          offl neRecConf g,
          candT etDateRange
        ).flatMap { val dCandP pe =>
          val outputD r = args("output_d r")
          Evaluat on tr c lper.runAllEvaluat ons(val dRefP pe, val dCandP pe).map { results =>
            toEma lAddressOpt.foreach { address =>
              Ut l.sendEma l(
                results,
                "Results from t et evaluat on test bed " + testRunNa .getOrElse(""),
                address)
            }
            TypedP pe.from(Seq((results, ""))).wr eExecut on(TypedTsv[(Str ng, Str ng)](outputD r))
          }
        }
      }
    }
  }

  /**
   * G ven a p pe of raw t  l nes reference engage nt data, collect t  engage nts that took
   * place dur ng t  g ven date range, t n sample t se engage nts
   */
  pr vate def getProdT  l neReference(
    d splayLocat on: D splayLocat on,
    batchDateRange: DateRange,
    sampleRate: Double
  )(
     mpl c  tz: T  Zone
  ): TypedP pe[ReferenceT ets] = {
    // Snapshot data t  stamps  self w h t  last poss ble t   of t  day. +1 day to cover  
    val snapshotRange = DateRange(batchDateRange.start, batchDateRange.start + Days(1))
    val t  l nesRefP pe = DAL
      .readMostRecentSnapshot(T etEvaluat onT  l nesReferenceSetScalaDataset, snapshotRange)
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe

    t  l nesRefP pe
      .flatMap { refT ets =>
        val t ets = refT ets. mpressedT ets
          .f lter { refT et =>
            refT et.t  stamp >= batchDateRange.start.t  stamp &&
            refT et.t  stamp <= batchDateRange.end.t  stamp &&
            refT et.d splayLocat on == d splayLocat on
          }
         f (t ets.nonEmpty) {
          So (ReferenceT ets(refT ets.targetUser d, t ets))
        } else {
          None
        }
      }
      .sample(sampleRate)
  }

  /**
   * G ven a l st of target users, s mulate S mCluster's onl ne serv ng log c offl ne for t se
   * users, t n convert t m  nto [[Cand dateT ets]]
   */
  pr vate def getVal dCand date(
    targetUserP pe: TypedP pe[Long],
    user s nterested nP pe: TypedP pe[(Long, ClustersUser s nterested n)],
    clusterTopKT etsP pe: TypedP pe[ClusterTopKT etsW hScores],
    offl neConf g: Offl neRecConf g,
    batchDateRange: DateRange
  )(
     mpl c  un que D: Un que D
  ): Execut on[TypedP pe[Cand dateT ets]] = {
    Offl neT etRecom ndat on
      .getTopT ets(offl neConf g, targetUserP pe, user s nterested nP pe, clusterTopKT etsP pe)
      .map(_.map {
        case (user d, scoredT ets) =>
          val t ets = scoredT ets.map { t et =>
            Cand dateT et(t et.t et d, So (t et.score), So (batchDateRange.start.t  stamp))
          }
          Cand dateT ets(user d, t ets)
      })
  }

  /**
   * Read  nterested  n key-val store from atla-proc from t  g ven date range
   */
  pr vate def  nterested nProdS ce(
    dateRange: DateRange
  ): TypedP pe[(Long, ClustersUser s nterested n)] = {
     mpl c  val t  Zone: T  Zone = DateOps.UTC

    DAL
      .readMostRecentSnapshot(S mclustersV2 nterested nScalaDataset, dateRange.emb ggen( eks(1)))
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
      .map {
        case KeyVal(key, value) => (key, value)
      }
  }
}
