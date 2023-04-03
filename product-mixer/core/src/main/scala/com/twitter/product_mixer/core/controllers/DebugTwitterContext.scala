packagelon com.twittelonr.product_mixelonr.corelon.controllelonrs

import com.twittelonr.contelonxt.TwittelonrContelonxt
import com.twittelonr.contelonxt.thriftscala.Vielonwelonr
import com.twittelonr.product_mixelonr.TwittelonrContelonxtPelonrmit
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt

/**
 * Mixelons in support to forgelon thelon UselonrIds in TwittelonrContelonxt for delonbug purposelons.
 *
 * A thrift controllelonr can elonxtelonnd DelonbugTwittelonrContelonxt and wrap it's elonxeloncution logic:
 *
 * {{{
 * withDelonbugTwittelonrContelonxt(relonquelonst.clielonntContelonxt) {
 *   Stitch.run(...)
 * }
 * }}}
 */
trait DelonbugTwittelonrContelonxt {

  privatelon val ctx = TwittelonrContelonxt(TwittelonrContelonxtPelonrmit)

  /**
   * Wrap somelon function in a delonbug TwittelonrContelonxt with hardcodelond uselonrIds
   * to thelon ClielonntContelonxt.uselonrId.
   *
   * @param clielonntContelonxt - A product mixelonr relonquelonst clielonnt contelonxt
   * @param f Thelon function to wrap
   */
  delonf withDelonbugTwittelonrContelonxt[T](clielonntContelonxt: ClielonntContelonxt)(f: => T): T = {
    ctx.lelont(
      forgelonTwittelonrContelonxt(
        clielonntContelonxt.uselonrId
          .gelontOrelonlselon(throw nelonw IllelongalArgumelonntelonxcelonption("missing relonquirelond fielonld: uselonr id")))
    )(f)
  }

  // Gelonnelonratelon a fakelon Twittelonr Contelonxt for delonbug usagelon.
  // Gelonnelonrally thelon TwittelonrContelonxt is crelonatelond by thelon API selonrvicelon, and Strato uselons it for pelonrmission control.
  // Whelonn welon uselon our delonbug elonndpoint, welon instelonad crelonatelon our own contelonxt so that Strato finds somelonthing uselonful.
  // Welon elonnforcelon ACLs direlonctly via Thrift Welonb Forms' pelonrmission systelonm.
  privatelon delonf forgelonTwittelonrContelonxt(uselonrId: Long): Vielonwelonr = {
    Vielonwelonr(
      auditIp = Nonelon,
      ipTags = Selont.elonmpty,
      uselonrId = Somelon(uselonrId),
      guelonstId = Nonelon,
      clielonntApplicationId = Nonelon,
      uselonrAgelonnt = Nonelon,
      locationTokelonn = Nonelon,
      authelonnticatelondUselonrId = Somelon(uselonrId),
      guelonstTokelonn = Nonelon
    )
  }
}
