packagelon com.twittelonr.timelonlinelonrankelonr.config

import com.twittelonr.app.Flags
import com.twittelonr.finaglelon.mtls.authelonntication.elonmptySelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.timelonlinelons.config.CommonFlags
import com.twittelonr.timelonlinelons.config.ConfigUtils
import com.twittelonr.timelonlinelons.config.Datacelonntelonr
import com.twittelonr.timelonlinelons.config.elonnv
import com.twittelonr.timelonlinelons.config.ProvidelonsSelonrvicelonIdelonntifielonr
import java.nelont.InelontSockelontAddrelonss
import com.twittelonr.app.Flag

class TimelonlinelonRankelonrFlags(flag: Flags)
    elonxtelonnds CommonFlags(flag)
    with ConfigUtils
    with ProvidelonsSelonrvicelonIdelonntifielonr {
  val dc: Flag[String] = flag(
    "dc",
    "smf1",
    "Namelon of data celonntelonr in which this instancelon will elonxeloncutelon"
  )
  val elonnvironmelonnt: Flag[String] = flag(
    "elonnvironmelonnt",
    "delonvelonl",
    "Thelon melonsos elonnvironmelonnt in which this instancelon will belon running"
  )
  val maxConcurrelonncy: Flag[Int] = flag(
    "maxConcurrelonncy",
    200,
    "Maximum concurrelonnt relonquelonsts"
  )
  val selonrvicelonPort: Flag[InelontSockelontAddrelonss] = flag(
    "selonrvicelon.port",
    nelonw InelontSockelontAddrelonss(8287),
    "Port numbelonr that this thrift selonrvicelon will listelonn on"
  )
  val selonrvicelonCompactPort: Flag[InelontSockelontAddrelonss] = flag(
    "selonrvicelon.compact.port",
    nelonw InelontSockelontAddrelonss(8288),
    "Port numbelonr that thelon TCompactProtocol-baselond thrift selonrvicelon will listelonn on"
  )

  val selonrvicelonIdelonntifielonr: Flag[SelonrvicelonIdelonntifielonr] = flag[SelonrvicelonIdelonntifielonr](
    "selonrvicelon.idelonntifielonr",
    elonmptySelonrvicelonIdelonntifielonr,
    "selonrvicelon idelonntifielonr for this selonrvicelon for uselon with mutual TLS, " +
      "format is elonxpelonctelond to belon -selonrvicelon.idelonntifielonr=\"rolelon:selonrvicelon:elonnvironmelonnt:zonelon\""
  )

  val opportunisticTlsLelonvelonl = flag[String](
    "opportunistic.tls.lelonvelonl",
    "delonsirelond",
    "Thelon selonrvelonr's OpportunisticTls lelonvelonl."
  )

  val relonquelonstRatelonLimit: Flag[Doublelon] = flag[Doublelon](
    "relonquelonstRatelonLimit",
    1000.0,
    "Relonquelonst ratelon limit to belon uselond by thelon clielonnt relonquelonst authorizelonr"
  )

  val elonnablelonThriftmuxComprelonssion = flag(
    "elonnablelonThriftmuxSelonrvelonrComprelonssion",
    truelon,
    "build selonrvelonr with thriftmux comprelonssion elonnablelond"
  )

  delonf gelontDatacelonntelonr: Datacelonntelonr.Valuelon = gelontDC(dc())
  delonf gelontelonnv: elonnv.Valuelon = gelontelonnv(elonnvironmelonnt())
  ovelonrridelon delonf gelontSelonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr()
}
