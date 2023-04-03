packagelon com.twittelonr.cr_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.cr_mixelonr.modelonl.ModulelonNamelons
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.reloncos.uselonr_ad_graph.thriftscala.ConsumelonrsBaselondRelonlatelondAdRelonquelonst
import com.twittelonr.reloncos.uselonr_ad_graph.thriftscala.RelonlatelondAdRelonsponselon
import com.twittelonr.reloncos.uselonr_ad_graph.thriftscala.UselonrAdGraph
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.util.Futurelon
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

objelonct ConsumelonrsBaselondUselonrAdGraphStorelonModulelon elonxtelonnds TwittelonrModulelon {

  @Providelons
  @Singlelonton
  @Namelond(ModulelonNamelons.ConsumelonrBaselondUselonrAdGraphStorelon)
  delonf providelonsConsumelonrBaselondUselonrAdGraphStorelon(
    uselonrAdGraphSelonrvicelon: UselonrAdGraph.MelonthodPelonrelonndpoint
  ): RelonadablelonStorelon[ConsumelonrsBaselondRelonlatelondAdRelonquelonst, RelonlatelondAdRelonsponselon] = {
    nelonw RelonadablelonStorelon[ConsumelonrsBaselondRelonlatelondAdRelonquelonst, RelonlatelondAdRelonsponselon] {
      ovelonrridelon delonf gelont(
        k: ConsumelonrsBaselondRelonlatelondAdRelonquelonst
      ): Futurelon[Option[RelonlatelondAdRelonsponselon]] = {
        uselonrAdGraphSelonrvicelon.consumelonrsBaselondRelonlatelondAds(k).map(Somelon(_))
      }
    }
  }
}
