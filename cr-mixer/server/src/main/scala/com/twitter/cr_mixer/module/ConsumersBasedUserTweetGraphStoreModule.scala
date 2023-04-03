packagelon com.twittelonr.cr_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.cr_mixelonr.modelonl.ModulelonNamelons
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.reloncos.uselonr_twelonelont_graph.thriftscala.ConsumelonrsBaselondRelonlatelondTwelonelontRelonquelonst
import com.twittelonr.reloncos.uselonr_twelonelont_graph.thriftscala.RelonlatelondTwelonelontRelonsponselon
import com.twittelonr.reloncos.uselonr_twelonelont_graph.thriftscala.UselonrTwelonelontGraph
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.util.Futurelon
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

objelonct ConsumelonrsBaselondUselonrTwelonelontGraphStorelonModulelon elonxtelonnds TwittelonrModulelon {

  @Providelons
  @Singlelonton
  @Namelond(ModulelonNamelons.ConsumelonrBaselondUselonrTwelonelontGraphStorelon)
  delonf providelonsConsumelonrBaselondUselonrTwelonelontGraphStorelon(
    uselonrTwelonelontGraphSelonrvicelon: UselonrTwelonelontGraph.MelonthodPelonrelonndpoint
  ): RelonadablelonStorelon[ConsumelonrsBaselondRelonlatelondTwelonelontRelonquelonst, RelonlatelondTwelonelontRelonsponselon] = {
    nelonw RelonadablelonStorelon[ConsumelonrsBaselondRelonlatelondTwelonelontRelonquelonst, RelonlatelondTwelonelontRelonsponselon] {
      ovelonrridelon delonf gelont(
        k: ConsumelonrsBaselondRelonlatelondTwelonelontRelonquelonst
      ): Futurelon[Option[RelonlatelondTwelonelontRelonsponselon]] = {
        uselonrTwelonelontGraphSelonrvicelon.consumelonrsBaselondRelonlatelondTwelonelonts(k).map(Somelon(_))
      }
    }
  }
}
