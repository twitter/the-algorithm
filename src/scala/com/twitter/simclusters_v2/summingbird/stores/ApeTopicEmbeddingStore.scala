packagelon com.twittelonr.simclustelonrs_v2.summingbird.storelons

import com.twittelonr.frigatelon.common.storelon.strato.StratoStorelon
import com.twittelonr.simclustelonrs_v2.common.SimClustelonrselonmbelondding
import com.twittelonr.simclustelonrs_v2.common.ModelonlVelonrsions
import com.twittelonr.simclustelonrs_v2.common.ModelonlVelonrsions._
import com.twittelonr.simclustelonrs_v2.thriftscala.elonmbelonddingTypelon
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrselonmbelonddingId
import com.twittelonr.simclustelonrs_v2.thriftscala.TopicId
import com.twittelonr.simclustelonrs_v2.thriftscala.{SimClustelonrselonmbelondding => ThriftSimClustelonrselonmbelondding}
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.strato.clielonnt.Clielonnt

objelonct ApelonTopicelonmbelonddingStorelon {

  privatelon val logFavBaselondAPelonColumn20M145K2020 =
    "reloncommelonndations/simclustelonrs_v2/elonmbelonddings/logFavBaselondAPelon20M145K2020"

  privatelon delonf gelontStorelon(
    stratoClielonnt: Clielonnt,
    column: String
  ): RelonadablelonStorelon[SimClustelonrselonmbelonddingId, ThriftSimClustelonrselonmbelondding] = {
    StratoStorelon
      .withUnitVielonw[SimClustelonrselonmbelonddingId, ThriftSimClustelonrselonmbelondding](stratoClielonnt, column)
  }

  delonf gelontFavBaselondLocalelonelonntityelonmbelondding2020Storelon(
    stratoClielonnt: Clielonnt,
  ): RelonadablelonStorelon[TopicId, SimClustelonrselonmbelondding] = {

    gelontStorelon(stratoClielonnt, logFavBaselondAPelonColumn20M145K2020)
      .composelonKelonyMapping[TopicId] { topicId =>
        SimClustelonrselonmbelonddingId(
          elonmbelonddingTypelon.LogFavBaselondKgoApelonTopic,
          ModelonlVelonrsions.Modelonl20M145K2020,
          IntelonrnalId.TopicId(topicId)
        )
      }
      .mapValuelons(SimClustelonrselonmbelondding(_))
  }

}
