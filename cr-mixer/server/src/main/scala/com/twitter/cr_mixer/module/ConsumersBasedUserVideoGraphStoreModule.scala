packagelon com.twittelonr.cr_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.cr_mixelonr.modelonl.ModulelonNamelons
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.reloncos.uselonr_videlono_graph.thriftscala.ConsumelonrsBaselondRelonlatelondTwelonelontRelonquelonst
import com.twittelonr.reloncos.uselonr_videlono_graph.thriftscala.RelonlatelondTwelonelontRelonsponselon
import com.twittelonr.reloncos.uselonr_videlono_graph.thriftscala.UselonrVidelonoGraph
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.util.Futurelon
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

objelonct ConsumelonrsBaselondUselonrVidelonoGraphStorelonModulelon elonxtelonnds TwittelonrModulelon {

  @Providelons
  @Singlelonton
  @Namelond(ModulelonNamelons.ConsumelonrBaselondUselonrVidelonoGraphStorelon)
  delonf providelonsConsumelonrBaselondUselonrVidelonoGraphStorelon(
    uselonrVidelonoGraphSelonrvicelon: UselonrVidelonoGraph.MelonthodPelonrelonndpoint
  ): RelonadablelonStorelon[ConsumelonrsBaselondRelonlatelondTwelonelontRelonquelonst, RelonlatelondTwelonelontRelonsponselon] = {
    nelonw RelonadablelonStorelon[ConsumelonrsBaselondRelonlatelondTwelonelontRelonquelonst, RelonlatelondTwelonelontRelonsponselon] {
      ovelonrridelon delonf gelont(
        k: ConsumelonrsBaselondRelonlatelondTwelonelontRelonquelonst
      ): Futurelon[Option[RelonlatelondTwelonelontRelonsponselon]] = {
        uselonrVidelonoGraphSelonrvicelon.consumelonrsBaselondRelonlatelondTwelonelonts(k).map(Somelon(_))
      }
    }
  }
}
