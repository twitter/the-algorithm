packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.imprelonssion_storelon

import com.googlelon.injelonct.Providelons
import com.googlelon.injelonct.Singlelonton
import com.twittelonr.follow_reloncommelonndations.thriftscala.DisplayLocation
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.strato.catalog.Scan.Slicelon
import com.twittelonr.strato.clielonnt.Clielonnt
import com.twittelonr.strato.thrift.ScroogelonConvImplicits._

objelonct ImprelonssionStorelonModulelon elonxtelonnds TwittelonrModulelon {

  val columnPath: String = "onboarding/uselonrreloncs/wtfImprelonssionCountsStorelon"

  typelon PKelony = (Long, DisplayLocation)
  typelon LKelony = Long
  typelon Valuelon = (Long, Int)

  @Providelons
  @Singlelonton
  delonf providelonsImprelonssionStorelon(stratoClielonnt: Clielonnt): WtfImprelonssionStorelon = {
    nelonw WtfImprelonssionStorelon(
      stratoClielonnt.scannelonr[
        (PKelony, Slicelon[LKelony]),
        Unit,
        (PKelony, LKelony),
        Valuelon
      ](columnPath)
    )
  }
}
