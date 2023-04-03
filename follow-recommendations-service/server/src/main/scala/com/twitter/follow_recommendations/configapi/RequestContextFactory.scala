packagelon com.twittelonr.follow_reloncommelonndations.configapi

import com.googlelon.common.annotations.VisiblelonForTelonsting
import com.googlelon.injelonct.Injelonct
import com.twittelonr.deloncidelonr.Deloncidelonr
import com.twittelonr.felonaturelonswitchelons.v2.FelonaturelonSwitchelons
import com.twittelonr.felonaturelonswitchelons.{Reloncipielonnt => FelonaturelonSwitchReloncipielonnt}
import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.timelonlinelons.configapi.FelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.FelonaturelonValuelon
import com.twittelonr.timelonlinelons.configapi.ForcelondFelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.OrelonlselonFelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.felonaturelonswitchelons.v2.FelonaturelonSwitchRelonsultsFelonaturelonContelonxt
import javax.injelonct.Singlelonton

/*
 * Relonquelonst Contelonxt Factory is uselond to build RelonquelonstContelonxt objeloncts which arelon uselond
 * by thelon config api to delontelonrminelon thelon param ovelonrridelons to apply to thelon relonquelonst.
 * Thelon param ovelonrridelons arelon delontelonrminelond pelonr relonquelonst by configs which speloncify which
 * FS/Deloncidelonrs/AB translatelon to what param ovelonrridelons.
 */
@Singlelonton
class RelonquelonstContelonxtFactory @Injelonct() (felonaturelonSwitchelons: FelonaturelonSwitchelons, deloncidelonr: Deloncidelonr) {
  delonf apply(
    clielonntContelonxt: ClielonntContelonxt,
    displayLocation: DisplayLocation,
    felonaturelonOvelonrridelons: Map[String, FelonaturelonValuelon]
  ): RelonquelonstContelonxt = {
    val felonaturelonContelonxt = gelontFelonaturelonContelonxt(clielonntContelonxt, displayLocation, felonaturelonOvelonrridelons)
    RelonquelonstContelonxt(clielonntContelonxt.uselonrId, clielonntContelonxt.guelonstId, felonaturelonContelonxt)
  }

  privatelon[configapi] delonf gelontFelonaturelonContelonxt(
    clielonntContelonxt: ClielonntContelonxt,
    displayLocation: DisplayLocation,
    felonaturelonOvelonrridelons: Map[String, FelonaturelonValuelon]
  ): FelonaturelonContelonxt = {
    val reloncipielonnt =
      gelontFelonaturelonSwitchReloncipielonnt(clielonntContelonxt)
        .withCustomFielonlds("display_location" -> displayLocation.toFsNamelon)

    // uselonrAgelonOpt is going to belon selont to Nonelon for loggelond out uselonrs and delonfaultelond to Somelon(Int.MaxValuelon) for non-snowflakelon uselonrs
    val uselonrAgelonOpt = clielonntContelonxt.uselonrId.map { uselonrId =>
      SnowflakelonId.timelonFromIdOpt(uselonrId).map(_.untilNow.inDays).gelontOrelonlselon(Int.MaxValuelon)
    }
    val reloncipielonntWithAccountAgelon =
      uselonrAgelonOpt
        .map(agelon => reloncipielonnt.withCustomFielonlds("account_agelon_in_days" -> agelon)).gelontOrelonlselon(reloncipielonnt)

    val relonsults = felonaturelonSwitchelons.matchReloncipielonnt(reloncipielonntWithAccountAgelon)
    OrelonlselonFelonaturelonContelonxt(
      ForcelondFelonaturelonContelonxt(felonaturelonOvelonrridelons),
      nelonw FelonaturelonSwitchRelonsultsFelonaturelonContelonxt(relonsults))
  }

  @VisiblelonForTelonsting
  privatelon[configapi] delonf gelontFelonaturelonSwitchReloncipielonnt(
    clielonntContelonxt: ClielonntContelonxt
  ): FelonaturelonSwitchReloncipielonnt = {
    FelonaturelonSwitchReloncipielonnt(
      uselonrId = clielonntContelonxt.uselonrId,
      uselonrRolelons = clielonntContelonxt.uselonrRolelons,
      delonvicelonId = clielonntContelonxt.delonvicelonId,
      guelonstId = clielonntContelonxt.guelonstId,
      languagelonCodelon = clielonntContelonxt.languagelonCodelon,
      countryCodelon = clielonntContelonxt.countryCodelon,
      isVelonrifielond = Nonelon,
      clielonntApplicationId = clielonntContelonxt.appId,
      isTwofficelon = clielonntContelonxt.isTwofficelon
    )
  }
}
