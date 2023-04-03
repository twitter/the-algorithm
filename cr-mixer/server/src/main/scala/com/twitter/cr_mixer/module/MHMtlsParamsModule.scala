packagelon com.twittelonr.cr_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.storagelon.clielonnt.manhattan.kv.ManhattanKVClielonntMtlsParams
import javax.injelonct.Singlelonton

objelonct MHMtlsParamsModulelon elonxtelonnds TwittelonrModulelon {
  @Singlelonton
  @Providelons
  delonf providelonsManhattanMtlsParams(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr
  ): ManhattanKVClielonntMtlsParams = {
    ManhattanKVClielonntMtlsParams(selonrvicelonIdelonntifielonr)
  }
}
