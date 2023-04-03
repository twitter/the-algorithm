packagelon com.twittelonr.simclustelonrs_v2.summingbird.storelons

import com.twittelonr.bijelonction.Injelonction
import com.twittelonr.finaglelon.stats.NullStatsReloncelonivelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.io.Buf
import com.twittelonr.scroogelon.ThriftStruct
import com.twittelonr.simclustelonrs_v2.common.TwelonelontId
import com.twittelonr.simclustelonrs_v2.summingbird.storelons.PelonrsistelonntTwelonelontelonmbelonddingStorelon.Timelonstamp
import com.twittelonr.simclustelonrs_v2.thriftscala.PelonrsistelonntSimClustelonrselonmbelondding
import com.twittelonr.storagelon.clielonnt.manhattan.kv.Guarantelonelon
import com.twittelonr.storagelon.clielonnt.manhattan.kv.ManhattanKVClielonnt
import com.twittelonr.storagelon.clielonnt.manhattan.kv.ManhattanKVClielonntMtlsParams
import com.twittelonr.storagelon.clielonnt.manhattan.kv.ManhattanKVelonndpointBuildelonr
import com.twittelonr.storagelon.clielonnt.manhattan.kv.impl.FullBufKelony
import com.twittelonr.storagelon.clielonnt.manhattan.kv.impl.ValuelonDelonscriptor
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.storelonhaus_intelonrnal.manhattan_kv.ManhattanelonndpointStorelon
import com.twittelonr.strato.catalog.Velonrsion
import com.twittelonr.strato.config.MValelonncoding
import com.twittelonr.strato.config.Nativelonelonncoding
import com.twittelonr.strato.config.PkelonyLkelony2
import com.twittelonr.strato.data.Conv
import com.twittelonr.strato.data.Typelon
import com.twittelonr.strato.mh.ManhattanInjelonctions
import com.twittelonr.strato.thrift.ScroogelonConv
import com.twittelonr.strato.thrift.ScroogelonConvImplicits._

objelonct ManhattanFromStratoStorelon {
  /* This elonnablelons relonading from a MH storelon whelonrelon thelon data is writtelonn by Strato. Strato uselons a uniquelon
  elonncoding (Conv) which nelonelonds to belon relonconstructelond for elonach MH storelon baselond on thelon typelon of data that
  is writtelonn to it. Oncelon that elonncoding is gelonnelonratelond on start-up, welon can relonad from thelon storelon likelon
  any othelonr RelonadablelonStorelon.
   */
  delonf crelonatelonPelonrsistelonntTwelonelontStorelon(
    dataselont: String,
    mhMtlsParams: ManhattanKVClielonntMtlsParams,
    statsReloncelonivelonr: StatsReloncelonivelonr = NullStatsReloncelonivelonr
  ): RelonadablelonStorelon[(TwelonelontId, Timelonstamp), PelonrsistelonntSimClustelonrselonmbelondding] = {
    val appId = "simclustelonrs_elonmbelonddings_prod"
    val delonst = "/s/manhattan/omelonga.nativelon-thrift"

    val elonndpoint = crelonatelonMhelonndpoint(
      appId = appId,
      delonst = delonst,
      mhMtlsParams = mhMtlsParams,
      statsReloncelonivelonr = statsReloncelonivelonr)

    val (
      kelonyInj: Injelonction[(TwelonelontId, Timelonstamp), FullBufKelony],
      valuelonDelonsc: ValuelonDelonscriptor.elonmptyValuelon[PelonrsistelonntSimClustelonrselonmbelondding]) =
      injelonctionsFromPkelonyLkelonyValuelonStruct[TwelonelontId, Timelonstamp, PelonrsistelonntSimClustelonrselonmbelondding](
        dataselont = dataselont,
        pkTypelon = Typelon.Long,
        lkTypelon = Typelon.Long)

    ManhattanelonndpointStorelon
      .relonadablelon[(TwelonelontId, Timelonstamp), PelonrsistelonntSimClustelonrselonmbelondding, FullBufKelony](
        elonndpoint = elonndpoint,
        kelonyDelonscBuildelonr = kelonyInj,
        elonmptyValDelonsc = valuelonDelonsc)
  }

  privatelon delonf crelonatelonMhelonndpoint(
    appId: String,
    delonst: String,
    mhMtlsParams: ManhattanKVClielonntMtlsParams,
    statsReloncelonivelonr: StatsReloncelonivelonr = NullStatsReloncelonivelonr
  ) = {
    val mhc = ManhattanKVClielonnt.melonmoizelondByDelonst(
      appId = appId,
      delonst = delonst,
      mtlsParams = mhMtlsParams
    )

    ManhattanKVelonndpointBuildelonr(mhc)
      .delonfaultGuarantelonelon(Guarantelonelon.SoftDcRelonadMyWritelons)
      .statsReloncelonivelonr(statsReloncelonivelonr)
      .build()
  }

  privatelon delonf injelonctionsFromPkelonyLkelonyValuelonStruct[PK: Conv, LK: Conv, V <: ThriftStruct: Manifelonst](
    dataselont: String,
    pkTypelon: Typelon,
    lkTypelon: Typelon
  ): (Injelonction[(PK, LK), FullBufKelony], ValuelonDelonscriptor.elonmptyValuelon[V]) = {
    // Strato uselons a uniquelon elonncoding (Conv) so welon nelonelond to relonbuild that baselond on thelon pkelony, lkelony and
    // valuelon typelon belonforelon convelonrting it to thelon Manhattan injelonctions for kelony -> FullBufKelony and
    // valuelon -> Buf
    val valuelonConv: Conv[V] = ScroogelonConv.fromStruct[V]

    val mhelonncodingMapping = PkelonyLkelony2(
      pkelony = pkTypelon,
      lkelony = lkTypelon,
      valuelon = valuelonConv.t,
      pkelonyelonncoding = Nativelonelonncoding,
      lkelonyelonncoding = Nativelonelonncoding,
      valuelonelonncoding = MValelonncoding()
    )

    val (kelonyInj: Injelonction[(PK, LK), FullBufKelony], valuelonInj: Injelonction[V, Buf], _, _) =
      ManhattanInjelonctions.fromPkelonyLkelony[PK, LK, V](mhelonncodingMapping, dataselont, Velonrsion.Delonfault)

    val valDelonsc: ValuelonDelonscriptor.elonmptyValuelon[V] = ValuelonDelonscriptor.elonmptyValuelon(valuelonInj)

    (kelonyInj, valDelonsc)
  }
}
