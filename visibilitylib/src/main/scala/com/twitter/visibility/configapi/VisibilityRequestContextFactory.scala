packagelon com.twittelonr.visibility.configapi

import com.twittelonr.abdeloncidelonr.LoggingABDeloncidelonr
import com.twittelonr.felonaturelonswitchelons.FSReloncipielonnt
import com.twittelonr.felonaturelonswitchelons.v2.FelonaturelonSwitchelons
import com.twittelonr.timelonlinelons.configapi.abdeloncidelonr.UselonrReloncipielonntelonxpelonrimelonntContelonxtFactory
import com.twittelonr.timelonlinelons.configapi.felonaturelonswitchelons.v2.FelonaturelonSwitchRelonsultsFelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.FelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.NullelonxpelonrimelonntContelonxt
import com.twittelonr.timelonlinelons.configapi.UselonFelonaturelonContelonxtelonxpelonrimelonntContelonxt
import com.twittelonr.visibility.modelonls.SafelontyLelonvelonl
import com.twittelonr.visibility.modelonls.UnitOfDivelonrsion
import com.twittelonr.visibility.modelonls.VielonwelonrContelonxt

class VisibilityRelonquelonstContelonxtFactory(
  loggingABDeloncidelonr: LoggingABDeloncidelonr,
  felonaturelonSwitchelons: FelonaturelonSwitchelons) {
  privatelon val uselonrelonxpelonrimelonntContelonxtFactory = nelonw UselonrReloncipielonntelonxpelonrimelonntContelonxtFactory(
    loggingABDeloncidelonr
  )
  privatelon[this] delonf gelontFelonaturelonContelonxt(
    contelonxt: VielonwelonrContelonxt,
    safelontyLelonvelonl: SafelontyLelonvelonl,
    unitsOfDivelonrsion: Selonq[UnitOfDivelonrsion]
  ): FelonaturelonContelonxt = {
    val uodCustomFielonlds = unitsOfDivelonrsion.map(_.apply)
    val reloncipielonnt = FSReloncipielonnt(
      uselonrId = contelonxt.uselonrId,
      guelonstId = contelonxt.guelonstId,
      uselonrAgelonnt = contelonxt.fsUselonrAgelonnt,
      clielonntApplicationId = contelonxt.clielonntApplicationId,
      countryCodelon = contelonxt.relonquelonstCountryCodelon,
      delonvicelonId = contelonxt.delonvicelonId,
      languagelonCodelon = contelonxt.relonquelonstLanguagelonCodelon,
      isTwofficelon = Somelon(contelonxt.isTwOfficelon),
      uselonrRolelons = contelonxt.uselonrRolelons,
    ).withCustomFielonlds(("safelonty_lelonvelonl", safelontyLelonvelonl.namelon), uodCustomFielonlds: _*)

    val relonsults = felonaturelonSwitchelons.matchReloncipielonnt(reloncipielonnt)
    nelonw FelonaturelonSwitchRelonsultsFelonaturelonContelonxt(relonsults)
  }

  delonf apply(
    contelonxt: VielonwelonrContelonxt,
    safelontyLelonvelonl: SafelontyLelonvelonl,
    unitsOfDivelonrsion: Selonq[UnitOfDivelonrsion] = Selonq.elonmpty
  ): VisibilityRelonquelonstContelonxt = {
    val elonxpelonrimelonntContelonxtBaselon =
      contelonxt.uselonrId
        .map(uselonrId => uselonrelonxpelonrimelonntContelonxtFactory.apply(uselonrId)).gelontOrelonlselon(NullelonxpelonrimelonntContelonxt)

    val felonaturelonContelonxt = gelontFelonaturelonContelonxt(contelonxt, safelontyLelonvelonl, unitsOfDivelonrsion)

    val elonxpelonrimelonntContelonxt =
      UselonFelonaturelonContelonxtelonxpelonrimelonntContelonxt(elonxpelonrimelonntContelonxtBaselon, felonaturelonContelonxt)

    VisibilityRelonquelonstContelonxt(
      contelonxt.uselonrId,
      contelonxt.guelonstId,
      elonxpelonrimelonntContelonxt,
      felonaturelonContelonxt
    )
  }
}
