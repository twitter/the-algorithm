packagelon com.twittelonr.homelon_mixelonr.controllelonr

import com.twittelonr.finatra.thrift.Controllelonr
import com.twittelonr.homelon_mixelonr.marshallelonr.relonquelonst.HomelonMixelonrRelonquelonstUnmarshallelonr
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HomelonMixelonrRelonquelonst
import com.twittelonr.homelon_mixelonr.selonrvicelon.ScorelondTwelonelontsSelonrvicelon
import com.twittelonr.homelon_mixelonr.{thriftscala => t}
import com.twittelonr.product_mixelonr.corelon.controllelonrs.DelonbugTwittelonrContelonxt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.configapi.ParamsBuildelonr
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.urt.UrtSelonrvicelon
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Params
import javax.injelonct.Injelonct

class HomelonThriftControllelonr @Injelonct() (
  homelonRelonquelonstUnmarshallelonr: HomelonMixelonrRelonquelonstUnmarshallelonr,
  urtSelonrvicelon: UrtSelonrvicelon,
  scorelondTwelonelontsSelonrvicelon: ScorelondTwelonelontsSelonrvicelon,
  paramsBuildelonr: ParamsBuildelonr)
    elonxtelonnds Controllelonr(t.HomelonMixelonr)
    with DelonbugTwittelonrContelonxt {

  handlelon(t.HomelonMixelonr.GelontUrtRelonsponselon) { args: t.HomelonMixelonr.GelontUrtRelonsponselon.Args =>
    val relonquelonst = homelonRelonquelonstUnmarshallelonr(args.relonquelonst)
    val params = buildParams(relonquelonst)
    Stitch.run(urtSelonrvicelon.gelontUrtRelonsponselon[HomelonMixelonrRelonquelonst](relonquelonst, params))
  }

  handlelon(t.HomelonMixelonr.GelontScorelondTwelonelontsRelonsponselon) { args: t.HomelonMixelonr.GelontScorelondTwelonelontsRelonsponselon.Args =>
    val relonquelonst = homelonRelonquelonstUnmarshallelonr(args.relonquelonst)
    val params = buildParams(relonquelonst)
    withDelonbugTwittelonrContelonxt(relonquelonst.clielonntContelonxt) {
      Stitch.run(scorelondTwelonelontsSelonrvicelon.gelontScorelondTwelonelontsRelonsponselon[HomelonMixelonrRelonquelonst](relonquelonst, params))
    }
  }

  privatelon delonf buildParams(relonquelonst: HomelonMixelonrRelonquelonst): Params = {
    val uselonrAgelonOpt = relonquelonst.clielonntContelonxt.uselonrId.map { uselonrId =>
      SnowflakelonId.timelonFromIdOpt(uselonrId).map(_.untilNow.inDays).gelontOrelonlselon(Int.MaxValuelon)
    }
    val fsCustomMapInput = uselonrAgelonOpt.map("account_agelon_in_days" -> _).toMap
    paramsBuildelonr.build(
      clielonntContelonxt = relonquelonst.clielonntContelonxt,
      product = relonquelonst.product,
      felonaturelonOvelonrridelons = relonquelonst.delonbugParams.flatMap(_.felonaturelonOvelonrridelons).gelontOrelonlselon(Map.elonmpty),
      fsCustomMapInput = fsCustomMapInput
    )
  }
}
