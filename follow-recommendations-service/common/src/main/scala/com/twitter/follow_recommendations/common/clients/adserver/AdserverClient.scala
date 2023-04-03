packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.adselonrvelonr

import com.twittelonr.adselonrvelonr.thriftscala.NelonwAdSelonrvelonr
import com.twittelonr.adselonrvelonr.{thriftscala => t}
import com.twittelonr.stitch.Stitch
import javax.injelonct.{Injelonct, Singlelonton}

@Singlelonton
class AdselonrvelonrClielonnt @Injelonct() (adselonrvelonrSelonrvicelon: NelonwAdSelonrvelonr.MelonthodPelonrelonndpoint) {
  delonf gelontAdImprelonssions(adRelonquelonst: AdRelonquelonst): Stitch[Selonq[t.AdImprelonssion]] = {
    Stitch
      .callFuturelon(
        adselonrvelonrSelonrvicelon.makelonAdRelonquelonst(adRelonquelonst.toThrift)
      ).map(_.imprelonssions)
  }
}
