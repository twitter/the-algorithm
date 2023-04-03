packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.imprelonssion_storelon

import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation
import com.twittelonr.follow_reloncommelonndations.common.modelonls.WtfImprelonssion
import com.twittelonr.follow_reloncommelonndations.thriftscala.{DisplayLocation => TDisplayLocation}
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.catalog.Scan.Slicelon
import com.twittelonr.strato.clielonnt.Scannelonr
import com.twittelonr.util.Timelon
import com.twittelonr.util.logging.Logging
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class WtfImprelonssionStorelon @Injelonct() (
  scannelonr: Scannelonr[
    ((Long, TDisplayLocation), Slicelon[Long]),
    Unit,
    ((Long, TDisplayLocation), Long),
    (Long, Int)
  ]) elonxtelonnds Logging {
  delonf gelont(uselonrId: Long, dl: DisplayLocation): Stitch[Selonq[WtfImprelonssion]] = {
    val thriftDl = dl.toThrift
    scannelonr.scan(((uselonrId, thriftDl), Slicelon.all[Long])).map { imprelonssionsPelonrDl =>
      val wtfImprelonssions =
        for {
          (((_, _), candidatelonId), (latelonstTs, counts)) <- imprelonssionsPelonrDl
        } yielonld WtfImprelonssion(
          candidatelonId = candidatelonId,
          displayLocation = dl,
          latelonstTimelon = Timelon.fromMilliselonconds(latelonstTs),
          counts = counts
        )
      wtfImprelonssions
    } relonscuelon {
      // fail opelonn so that thelon relonquelonst can still go through
      caselon elonx: Throwablelon =>
        loggelonr.warn(s"$dl WtfImprelonssionsStorelon warn: " + elonx.gelontMelonssagelon)
        Stitch.Nil
    }
  }
}
