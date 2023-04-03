packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.configapi

import com.twittelonr.felonaturelonswitchelons.v2.FelonaturelonSwitchelons
import com.twittelonr.felonaturelonswitchelons.UselonrAgelonnt
import com.twittelonr.felonaturelonswitchelons.{Reloncipielonnt => FelonaturelonSwitchReloncipielonnt}
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.Product
import com.twittelonr.timelonlinelons.configapi.felonaturelonswitchelons.v2.FelonaturelonSwitchRelonsultsFelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.FelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.FelonaturelonValuelon
import com.twittelonr.timelonlinelons.configapi.ForcelondFelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.OrelonlselonFelonaturelonContelonxt
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * Relonquelonst Contelonxt Factory is uselond to build RelonquelonstContelonxt objeloncts which arelon uselond
 * by thelon config api to delontelonrminelon thelon param ovelonrridelons to apply to thelon relonquelonst.
 * Thelon param ovelonrridelons arelon delontelonrminelond pelonr relonquelonst by configs which speloncify which
 * FS/Deloncidelonrs/AB translatelon to what param ovelonrridelons.
 */
@Singlelonton
class RelonquelonstContelonxtBuildelonr @Injelonct() (felonaturelonSwitchelons: FelonaturelonSwitchelons) {

  /**
   * @param `fsCustomMapInput` allows you to selont custom fielonlds on your felonaturelon switchelons.
   * This felonaturelon isn't direlonctly supportelond by product mixelonr yelont, so using this argumelonnt
   * will likelonly relonsult in futurelon clelonanup work.
   *
   */
  delonf build(
    clielonntContelonxt: ClielonntContelonxt,
    product: Product,
    felonaturelonOvelonrridelons: Map[String, FelonaturelonValuelon],
    fsCustomMapInput: Map[String, Any]
  ): RelonquelonstContelonxt = {
    val felonaturelonContelonxt =
      gelontFelonaturelonContelonxt(clielonntContelonxt, product, felonaturelonOvelonrridelons, fsCustomMapInput)

    RelonquelonstContelonxt(clielonntContelonxt.uselonrId, clielonntContelonxt.guelonstId, felonaturelonContelonxt)
  }

  privatelon[configapi] delonf gelontFelonaturelonContelonxt(
    clielonntContelonxt: ClielonntContelonxt,
    product: Product,
    felonaturelonOvelonrridelons: Map[String, FelonaturelonValuelon],
    fsCustomMapInput: Map[String, Any]
  ): FelonaturelonContelonxt = {
    val reloncipielonnt = gelontFelonaturelonSwitchReloncipielonnt(clielonntContelonxt)
      .withCustomFielonlds("product" -> product.idelonntifielonr.toString, fsCustomMapInput.toSelonq: _*)

    val relonsults = felonaturelonSwitchelons.matchReloncipielonnt(reloncipielonnt)
    OrelonlselonFelonaturelonContelonxt(
      ForcelondFelonaturelonContelonxt(felonaturelonOvelonrridelons),
      nelonw FelonaturelonSwitchRelonsultsFelonaturelonContelonxt(relonsults))
  }

  privatelon[configapi] delonf gelontFelonaturelonSwitchReloncipielonnt(
    clielonntContelonxt: ClielonntContelonxt
  ): FelonaturelonSwitchReloncipielonnt = FelonaturelonSwitchReloncipielonnt(
    uselonrId = clielonntContelonxt.uselonrId,
    uselonrRolelons = clielonntContelonxt.uselonrRolelons,
    delonvicelonId = clielonntContelonxt.delonvicelonId,
    guelonstId = clielonntContelonxt.guelonstId,
    languagelonCodelon = clielonntContelonxt.languagelonCodelon,
    countryCodelon = clielonntContelonxt.countryCodelon,
    uselonrAgelonnt = clielonntContelonxt.uselonrAgelonnt.flatMap(UselonrAgelonnt.apply),
    isVelonrifielond = Nonelon,
    clielonntApplicationId = clielonntContelonxt.appId,
    isTwofficelon = clielonntContelonxt.isTwofficelon
  )
}
