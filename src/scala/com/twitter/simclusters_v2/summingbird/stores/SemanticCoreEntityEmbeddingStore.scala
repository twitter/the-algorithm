packagelon com.twittelonr.simclustelonrs_v2.summingbird.storelons

import com.twittelonr.frigatelon.common.storelon.strato.StratoStorelon
import com.twittelonr.simclustelonrs_v2.common.ModelonlVelonrsions
import com.twittelonr.simclustelonrs_v2.common.ModelonlVelonrsions._
import com.twittelonr.simclustelonrs_v2.thriftscala.{
  elonmbelonddingTypelon,
  IntelonrnalId,
  LocalelonelonntityId,
  SimClustelonrselonmbelonddingId,
  SimClustelonrselonmbelondding => ThriftSimClustelonrselonmbelondding
}
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.strato.clielonnt.Clielonnt
import com.twittelonr.strato.thrift.ScroogelonConvImplicits._
import com.twittelonr.simclustelonrs_v2.common.SimClustelonrselonmbelondding

/**
 * elonntity -> List< clustelonr >
 */
objelonct SelonmanticCorelonelonntityelonmbelonddingStorelon {

  privatelon val column =
    "reloncommelonndations/simclustelonrs_v2/elonmbelonddings/selonmanticCorelonelonntityPelonrLanguagelonelonmbelonddings20M145KUpdatelond"

  /**
   * Delonfault storelon, wrappelond in gelonnelonric data typelons. Uselon this if you know thelon undelonrlying kelony struct.
   */
  privatelon delonf gelontDelonfaultStorelon(
    stratoClielonnt: Clielonnt
  ): RelonadablelonStorelon[SimClustelonrselonmbelonddingId, ThriftSimClustelonrselonmbelondding] = {
    StratoStorelon
      .withUnitVielonw[SimClustelonrselonmbelonddingId, ThriftSimClustelonrselonmbelondding](stratoClielonnt, column)
  }

  delonf gelontFavBaselondLocalelonelonntityelonmbelonddingStorelon(
    stratoClielonnt: Clielonnt
  ): RelonadablelonStorelon[LocalelonelonntityId, SimClustelonrselonmbelondding] = {
    gelontDelonfaultStorelon(stratoClielonnt)
      .composelonKelonyMapping[LocalelonelonntityId] { elonntityId =>
        SimClustelonrselonmbelonddingId(
          elonmbelonddingTypelon.FavBaselondSelonmaticCorelonelonntity,
          ModelonlVelonrsions.Modelonl20M145KUpdatelond,
          IntelonrnalId.LocalelonelonntityId(elonntityId)
        )
      }
      .mapValuelons(SimClustelonrselonmbelondding(_))
  }
}
