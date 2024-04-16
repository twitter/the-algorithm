package com.tw ter.t  l nes.data_process ng.ad_hoc.earlyb rd_rank ng.tra n ng_data_generat on

 mport com.tw ter.ml.ap .analyt cs.DataSetAnalyt csPlug n
 mport com.tw ter.ml.ap .matc r.FeatureMatc r
 mport com.tw ter.ml.ap .ut l.FDsl
 mport com.tw ter.ml.ap .Da lySuff xFeatureS ce
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .DataSetP pe
 mport com.tw ter.ml.ap .FeatureStats
 mport com.tw ter.ml.ap . Matc r
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.TypedJson
 mport com.tw ter.scald ng_ nternal.job.Tw terExecut onApp
 mport com.tw ter.t  l nes.data_process ng.ut l.execut on.UTCDateRangeFromArgs
 mport com.tw ter.t  l nes.data_process ng.ad_hoc.earlyb rd_rank ng.common.Earlyb rdTra n ngConf gurat on
 mport com.tw ter.t  l nes.data_process ng.ad_hoc.earlyb rd_rank ng.common.Earlyb rdTra n ngRecapConf gurat on
 mport com.tw ter.t  l nes.pred ct on.features.recap.RecapFeatures
 mport scala.collect on.JavaConverters._

/**
 * Compute counts and fract ons for all labels  n a Recap data s ce.
 *
 * Argu nts:
 * -- nput   recap data s ce (conta n ng all labels)
 * --output  path to output JSON f le conta n ng stats
 */
object Earlyb rdStatsJob extends Tw terExecut onApp w h UTCDateRangeFromArgs {

   mport DataSetAnalyt csPlug n._
   mport FDsl._
   mport RecapFeatures. S_EARLYB RD_UN F ED_ENGAGEMENT

  lazy val constants: Earlyb rdTra n ngConf gurat on = new Earlyb rdTra n ngRecapConf gurat on
  pr vate[t ] def addGlobalEngage ntLabel(record: DataRecord) = {
     f (constants.Label nfos.ex sts { label nfo => record.hasFeature(label nfo.feature) }) {
      record.setFeatureValue( S_EARLYB RD_UN F ED_ENGAGEMENT, true)
    }
    record
  }

  pr vate[t ] def labelFeatureMatc r:  Matc r = {
    val allLabels =
      ( S_EARLYB RD_UN F ED_ENGAGEMENT :: constants.Label nfos.map(_.feature)).map(_.getFeatureNa )
    FeatureMatc r.na s(allLabels.asJava)
  }

  pr vate[t ] def computeStats(data: DataSetP pe): TypedP pe[FeatureStats] = {
    data
      .v aRecords { _.map(addGlobalEngage ntLabel) }
      .project(labelFeatureMatc r)
      .collectFeatureStats()
  }

  overr de def job: Execut on[Un ] = {
    for {
      args <- Execut on.getArgs
      dateRange <- dateRangeEx
      data = Da lySuff xFeatureS ce(args(" nput"))(dateRange).read
      _ <- computeStats(data).wr eExecut on(TypedJson(args("output")))
    } y eld ()
  }
}
