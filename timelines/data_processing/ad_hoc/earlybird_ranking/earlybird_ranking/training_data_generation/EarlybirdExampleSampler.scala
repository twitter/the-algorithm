package com.tw ter.t  l nes.data_process ng.ad_hoc.earlyb rd_rank ng.tra n ng_data_generat on

 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.ml.ap .DataSetP pe
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.t  l nes.data_process ng.ad_hoc.earlyb rd_rank ng.common.Label nfo
 mport com.tw ter.t  l nes.data_process ng.ad_hoc.earlyb rd_rank ng.common.Label nfoW hFeature
 mport com.tw ter.t  l nes.pred ct on.features.recap.RecapFeatures
 mport java.lang.{Double => JDouble}
 mport scala.ut l.Random

/**
 * Adds an  sGlobalEngage nt label to records conta n ng any recap label, and adjusts
 *   ghts accord ngly. See [[  ghtAndSample]] for deta ls on operat on.
 */
class Earlyb rdExampleSampler(
  random: Random,
  label nfos: L st[Label nfoW hFeature],
  negat ve nfo: Label nfo) {

   mport com.tw ter.ml.ap .ut l.FDsl._

  pr vate[t ] val  mportanceFeature: Feature[JDouble] =
    SharedFeatures.RECORD_WE GHT_FEATURE_BU LDER
      .extens onBu lder()
      .addExtens on("type", "earlyb rd")
      .bu ld()

  pr vate[t ] def un formSample(label nfo: Label nfo) =
    random.nextDouble() < label nfo.downsampleFract on

  pr vate[t ] def   ghted mportance(label nfo: Label nfo) =
    label nfo. mportance / label nfo.downsampleFract on

  /**
   * Generates a  sGlobalEngage nt label for records that conta n any
   * recap label. Adds an " mportance" value per recap label found
   *  n t  record. S multaneously, downsamples pos  ve and negat ve examples based on prov ded
   * downsample rates.
   */
  def   ghtAndSample(data: DataSetP pe): DataSetP pe = {
    val updatedRecords = data.records.flatMap { record =>
      val featuresOn = label nfos.f lter(label nfo => record.hasFeature(label nfo.feature))
       f (featuresOn.nonEmpty) {
        val sampled = featuresOn.map(_. nfo).f lter(un formSample)
         f (sampled.nonEmpty) {
          record.setFeatureValue(RecapFeatures. S_EARLYB RD_UN F ED_ENGAGEMENT, true)
          So (record.setFeatureValue( mportanceFeature, sampled.map(  ghted mportance).sum))
        } else {
          None
        }
      } else  f (un formSample(negat ve nfo)) {
        So (record.setFeatureValue( mportanceFeature,   ghted mportance(negat ve nfo)))
      } else {
        None
      }
    }

    DataSetP pe(
      updatedRecords,
      data.featureContext
        .addFeatures( mportanceFeature, RecapFeatures. S_EARLYB RD_UN F ED_ENGAGEMENT)
    )
  }
}
