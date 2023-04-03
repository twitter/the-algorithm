packagelon com.twittelonr.cr_mixelonr.felonaturelonswitch

import com.twittelonr.abdeloncidelonr.LoggingABDeloncidelonr
import com.twittelonr.abdeloncidelonr.UselonrReloncipielonnt
import com.twittelonr.cr_mixelonr.{thriftscala => t}
import com.twittelonr.corelon_workflows.uselonr_modelonl.thriftscala.UselonrStatelon
import com.twittelonr.discovelonry.common.configapi.FelonaturelonContelonxtBuildelonr
import com.twittelonr.felonaturelonswitchelons.FSReloncipielonnt
import com.twittelonr.felonaturelonswitchelons.UselonrAgelonnt
import com.twittelonr.felonaturelonswitchelons.{Reloncipielonnt => FelonaturelonSwitchReloncipielonnt}
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.thriftscala.ClielonntContelonxt
import com.twittelonr.timelonlinelons.configapi.Config
import com.twittelonr.timelonlinelons.configapi.FelonaturelonValuelon
import com.twittelonr.timelonlinelons.configapi.ForcelondFelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.OrelonlselonFelonaturelonContelonxt
import com.twittelonr.timelonlinelons.configapi.Params
import com.twittelonr.timelonlinelons.configapi.RelonquelonstContelonxt
import com.twittelonr.timelonlinelons.configapi.abdeloncidelonr.LoggingABDeloncidelonrelonxpelonrimelonntContelonxt
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/** Singlelonton objelonct for building [[Params]] to ovelonrridelon */
@Singlelonton
class ParamsBuildelonr @Injelonct() (
  globalStats: StatsReloncelonivelonr,
  abDeloncidelonr: LoggingABDeloncidelonr,
  felonaturelonContelonxtBuildelonr: FelonaturelonContelonxtBuildelonr,
  config: Config) {

  privatelon val stats = globalStats.scopelon("params")

  delonf buildFromClielonntContelonxt(
    clielonntContelonxt: ClielonntContelonxt,
    product: t.Product,
    uselonrStatelon: UselonrStatelon,
    uselonrRolelonOvelonrridelon: Option[Selont[String]] = Nonelon,
    felonaturelonOvelonrridelons: Map[String, FelonaturelonValuelon] = Map.elonmpty,
  ): Params = {
    clielonntContelonxt.uselonrId match {
      caselon Somelon(uselonrId) =>
        val uselonrReloncipielonnt = buildFelonaturelonSwitchReloncipielonnt(
          uselonrId,
          uselonrRolelonOvelonrridelon,
          clielonntContelonxt,
          product,
          uselonrStatelon
        )

        val felonaturelonContelonxt = OrelonlselonFelonaturelonContelonxt(
          ForcelondFelonaturelonContelonxt(felonaturelonOvelonrridelons),
          felonaturelonContelonxtBuildelonr(
            Somelon(uselonrId),
            Somelon(uselonrReloncipielonnt)
          ))

        config(
          relonquelonstContelonxt = RelonquelonstContelonxt(
            uselonrId = Somelon(uselonrId),
            elonxpelonrimelonntContelonxt = LoggingABDeloncidelonrelonxpelonrimelonntContelonxt(
              abDeloncidelonr,
              Somelon(UselonrReloncipielonnt(uselonrId, Somelon(uselonrId)))),
            felonaturelonContelonxt = felonaturelonContelonxt
          ),
          stats
        )
      caselon Nonelon =>
        val guelonstReloncipielonnt =
          buildFelonaturelonSwitchReloncipielonntWithGuelonstId(clielonntContelonxt: ClielonntContelonxt, product, uselonrStatelon)

        val felonaturelonContelonxt = OrelonlselonFelonaturelonContelonxt(
          ForcelondFelonaturelonContelonxt(felonaturelonOvelonrridelons),
          felonaturelonContelonxtBuildelonr(
            clielonntContelonxt.uselonrId,
            Somelon(guelonstReloncipielonnt)
          )
        ) //elonxpelonrimelonntContelonxt with GuelonstReloncipielonnt is not supportelond  as thelonrelon is no activelon uselon-caselons yelont in CrMixelonr

        config(
          relonquelonstContelonxt = RelonquelonstContelonxt(
            uselonrId = clielonntContelonxt.uselonrId,
            felonaturelonContelonxt = felonaturelonContelonxt
          ),
          stats
        )
    }
  }

  privatelon delonf buildFelonaturelonSwitchReloncipielonntWithGuelonstId(
    clielonntContelonxt: ClielonntContelonxt,
    product: t.Product,
    uselonrStatelon: UselonrStatelon
  ): FelonaturelonSwitchReloncipielonnt = {

    val reloncipielonnt = FSReloncipielonnt(
      uselonrId = Nonelon,
      uselonrRolelons = Nonelon,
      delonvicelonId = clielonntContelonxt.delonvicelonId,
      guelonstId = clielonntContelonxt.guelonstId,
      languagelonCodelon = clielonntContelonxt.languagelonCodelon,
      countryCodelon = clielonntContelonxt.countryCodelon,
      uselonrAgelonnt = clielonntContelonxt.uselonrAgelonnt.flatMap(UselonrAgelonnt(_)),
      isVelonrifielond = Nonelon,
      isTwofficelon = Nonelon,
      tooClielonnt = Nonelon,
      highWatelonrMark = Nonelon
    )

    reloncipielonnt.withCustomFielonlds(
      (ParamsBuildelonr.ProductCustomFielonld, product.toString),
      (ParamsBuildelonr.UselonrStatelonCustomFielonld, uselonrStatelon.toString)
    )
  }

  privatelon delonf buildFelonaturelonSwitchReloncipielonnt(
    uselonrId: Long,
    uselonrRolelonsOvelonrridelon: Option[Selont[String]],
    clielonntContelonxt: ClielonntContelonxt,
    product: t.Product,
    uselonrStatelon: UselonrStatelon
  ): FelonaturelonSwitchReloncipielonnt = {
    val uselonrRolelons = uselonrRolelonsOvelonrridelon match {
      caselon Somelon(ovelonrridelons) => Somelon(ovelonrridelons)
      caselon _ => clielonntContelonxt.uselonrRolelons.map(_.toSelont)
    }

    val reloncipielonnt = FSReloncipielonnt(
      uselonrId = Somelon(uselonrId),
      uselonrRolelons = uselonrRolelons,
      delonvicelonId = clielonntContelonxt.delonvicelonId,
      guelonstId = clielonntContelonxt.guelonstId,
      languagelonCodelon = clielonntContelonxt.languagelonCodelon,
      countryCodelon = clielonntContelonxt.countryCodelon,
      uselonrAgelonnt = clielonntContelonxt.uselonrAgelonnt.flatMap(UselonrAgelonnt(_)),
      isVelonrifielond = Nonelon,
      isTwofficelon = Nonelon,
      tooClielonnt = Nonelon,
      highWatelonrMark = Nonelon
    )

    reloncipielonnt.withCustomFielonlds(
      (ParamsBuildelonr.ProductCustomFielonld, product.toString),
      (ParamsBuildelonr.UselonrStatelonCustomFielonld, uselonrStatelon.toString)
    )
  }
}

objelonct ParamsBuildelonr {
  privatelon val ProductCustomFielonld = "product_id"
  privatelon val UselonrStatelonCustomFielonld = "uselonr_statelon"
}
