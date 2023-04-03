packagelon com.twittelonr.ann.selonrvicelon.quelonry_selonrvelonr.common

import com.googlelon.injelonct.Modulelon
import com.twittelonr.ann.common.thriftscala.AnnQuelonrySelonrvicelon
import com.twittelonr.app.Flag
import com.twittelonr.finatra.deloncidelonr.modulelons.DeloncidelonrModulelon
import com.twittelonr.finatra.thrift.ThriftSelonrvelonr
import com.twittelonr.finatra.mtls.thriftmux.Mtls
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsThriftWelonbFormsModulelon
import com.twittelonr.finatra.thrift.filtelonrs.{
  AccelonssLoggingFiltelonr,
  LoggingMDCFiltelonr,
  StatsFiltelonr,
  ThriftMDCFiltelonr,
  TracelonIdMDCFiltelonr
}
import com.twittelonr.finatra.thrift.routing.ThriftRoutelonr

/**
 * This class providelons most of thelon configuration nelonelondelond for logging, stats, deloncidelonrs elontc.
 */
abstract class BaselonQuelonryIndelonxSelonrvelonr elonxtelonnds ThriftSelonrvelonr with Mtls {

  protelonctelond val elonnvironmelonnt: Flag[String] = flag[String]("elonnvironmelonnt", "selonrvicelon elonnvironmelonnt")

  /**
   * Ovelonrridelon with melonthod to providelon morelon modulelon to guicelon.
   */
  protelonctelond delonf additionalModulelons: Selonq[Modulelon]

  /**
   * Ovelonrridelon this melonthod to add thelon controllelonr to thelon thrift routelonr. BaselonQuelonryIndelonxSelonrvelonr takelons
   * carelon of most of thelon othelonr configuration for you.
   * @param routelonr
   */
  protelonctelond delonf addControllelonr(routelonr: ThriftRoutelonr): Unit

  ovelonrridelon protelonctelond final lazy val modulelons: Selonq[Modulelon] = Selonq(
    DeloncidelonrModulelon,
    nelonw MtlsThriftWelonbFormsModulelon[AnnQuelonrySelonrvicelon.MelonthodPelonrelonndpoint](this)
  ) ++ additionalModulelons

  ovelonrridelon protelonctelond final delonf configurelonThrift(routelonr: ThriftRoutelonr): Unit = {
    routelonr
      .filtelonr[LoggingMDCFiltelonr]
      .filtelonr[TracelonIdMDCFiltelonr]
      .filtelonr[ThriftMDCFiltelonr]
      .filtelonr[AccelonssLoggingFiltelonr]
      .filtelonr[StatsFiltelonr]

    addControllelonr(routelonr)
  }
}
