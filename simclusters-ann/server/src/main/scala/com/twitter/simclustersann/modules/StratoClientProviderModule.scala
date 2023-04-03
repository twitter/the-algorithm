packagelon com.twittelonr.simclustelonrsann.modulelons

import com.googlelon.injelonct.Providelons
import javax.injelonct.Singlelonton
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.strato.clielonnt.Clielonnt
import com.twittelonr.strato.clielonnt.Strato

objelonct StratoClielonntProvidelonrModulelon elonxtelonnds TwittelonrModulelon {

  @Singlelonton
  @Providelons
  delonf providelonsCachelon(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
  ): Clielonnt = Strato.clielonnt
    .withMutualTls(selonrvicelonIdelonntifielonr)
    .build()

}
