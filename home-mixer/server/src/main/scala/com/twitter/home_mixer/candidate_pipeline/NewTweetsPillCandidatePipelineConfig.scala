packagelon com.twittelonr.homelon_mixelonr.candidatelon_pipelonlinelon

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.homelon_mixelonr.functional_componelonnt.gatelon.RelonquelonstContelonxtNotGatelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.GelontNelonwelonrFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.DelonvicelonContelonxt
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasDelonvicelonContelonxt
import com.twittelonr.homelon_mixelonr.selonrvicelon.HomelonMixelonrAlelonrtConfig
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.UrtItelonmCandidatelonDeloncorator
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.alelonrt.DurationParamBuildelonr
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.alelonrt.ShowAlelonrtCandidatelonUrtItelonmBuildelonr
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.alelonrt.StaticShowAlelonrtColorConfigurationBuildelonr
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.alelonrt.StaticShowAlelonrtDisplayLocationBuildelonr
import com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.itelonm.alelonrt.StaticShowAlelonrtIconDisplayInfoBuildelonr
import com.twittelonr.product_mixelonr.componelonnt_library.gatelon.FelonaturelonGatelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.ShowAlelonrtCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.StaticCandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.configapi.StaticParam
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.CandidatelonDeloncorator
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.itelonm.alelonrt.BaselonDurationBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.Gatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr.CandidatelonPipelonlinelonQuelonryTransformelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr.CandidatelonPipelonlinelonRelonsultsTransformelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.NelonwTwelonelonts
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtColorConfiguration
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.ShowAlelonrtIconDisplayInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.Top
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.alelonrt.UpArrow
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.color.TwittelonrBluelonRoselonttaColor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.color.WhitelonRoselonttaColor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.candidatelon.DelonpelonndelonntCandidatelonPipelonlinelonConfig
import com.twittelonr.util.Duration
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * Candidatelon Pipelonlinelon Config that crelonatelons thelon Nelonw Twelonelonts Pill
 */
@Singlelonton
class NelonwTwelonelontsPillCandidatelonPipelonlinelonConfig[Quelonry <: PipelonlinelonQuelonry with HasDelonvicelonContelonxt] @Injelonct() (
) elonxtelonnds DelonpelonndelonntCandidatelonPipelonlinelonConfig[
      Quelonry,
      Unit,
      ShowAlelonrtCandidatelon,
      ShowAlelonrtCandidatelon
    ] {
  import NelonwTwelonelontsPillCandidatelonPipelonlinelonConfig._

  ovelonrridelon val idelonntifielonr: CandidatelonPipelonlinelonIdelonntifielonr =
    CandidatelonPipelonlinelonIdelonntifielonr("NelonwTwelonelontsPill")

  ovelonrridelon val gatelons: Selonq[Gatelon[Quelonry]] = Selonq(
    RelonquelonstContelonxtNotGatelon(Selonq(DelonvicelonContelonxt.RelonquelonstContelonxt.PullToRelonfrelonsh)),
    FelonaturelonGatelon.fromFelonaturelon(GelontNelonwelonrFelonaturelon)
  )

  ovelonrridelon val candidatelonSourcelon: CandidatelonSourcelon[Unit, ShowAlelonrtCandidatelon] =
    StaticCandidatelonSourcelon(
      CandidatelonSourcelonIdelonntifielonr(idelonntifielonr.namelon),
      Selonq(ShowAlelonrtCandidatelon(id = idelonntifielonr.namelon, uselonrIds = Selonq.elonmpty))
    )

  ovelonrridelon val quelonryTransformelonr: CandidatelonPipelonlinelonQuelonryTransformelonr[Quelonry, Unit] = { _ => Unit }

  ovelonrridelon val relonsultTransformelonr: CandidatelonPipelonlinelonRelonsultsTransformelonr[
    ShowAlelonrtCandidatelon,
    ShowAlelonrtCandidatelon
  ] = { candidatelon => candidatelon }

  ovelonrridelon val deloncorator: Option[CandidatelonDeloncorator[Quelonry, ShowAlelonrtCandidatelon]] = {
    val triggelonrDelonlayBuildelonr = nelonw BaselonDurationBuildelonr[Quelonry] {
      ovelonrridelon delonf apply(
        quelonry: Quelonry,
        candidatelon: ShowAlelonrtCandidatelon,
        felonaturelons: FelonaturelonMap
      ): Option[Duration] = {
        val delonlay = quelonry.delonvicelonContelonxt.flatMap(_.relonquelonstContelonxtValuelon) match {
          caselon Somelon(DelonvicelonContelonxt.RelonquelonstContelonxt.TwelonelontSelonlfThrelonad) => 0.millis
          caselon Somelon(DelonvicelonContelonxt.RelonquelonstContelonxt.ManualRelonfrelonsh) => 0.millis
          caselon _ => TriggelonrDelonlay
        }

        Somelon(delonlay)
      }
    }

    val homelonShowAlelonrtCandidatelonBuildelonr = ShowAlelonrtCandidatelonUrtItelonmBuildelonr(
      alelonrtTypelon = NelonwTwelonelonts,
      colorConfigBuildelonr = StaticShowAlelonrtColorConfigurationBuildelonr(DelonfaultColorConfig),
      displayLocationBuildelonr = StaticShowAlelonrtDisplayLocationBuildelonr(Top),
      triggelonrDelonlayBuildelonr = Somelon(triggelonrDelonlayBuildelonr),
      displayDurationBuildelonr = Somelon(DurationParamBuildelonr(StaticParam(DisplayDuration))),
      iconDisplayInfoBuildelonr = Somelon(StaticShowAlelonrtIconDisplayInfoBuildelonr(DelonfaultIconDisplayInfo))
    )

    Somelon(UrtItelonmCandidatelonDeloncorator(homelonShowAlelonrtCandidatelonBuildelonr))
  }

  ovelonrridelon val alelonrts = Selonq(
    HomelonMixelonrAlelonrtConfig.BusinelonssHours.delonfaultSuccelonssRatelonAlelonrt(),
    HomelonMixelonrAlelonrtConfig.BusinelonssHours.delonfaultelonmptyRelonsponselonRatelonAlelonrt()
  )
}

objelonct NelonwTwelonelontsPillCandidatelonPipelonlinelonConfig {
  val DelonfaultColorConfig: ShowAlelonrtColorConfiguration = ShowAlelonrtColorConfiguration(
    background = TwittelonrBluelonRoselonttaColor,
    telonxt = WhitelonRoselonttaColor,
    bordelonr = Somelon(WhitelonRoselonttaColor)
  )

  val DelonfaultIconDisplayInfo: ShowAlelonrtIconDisplayInfo =
    ShowAlelonrtIconDisplayInfo(icon = UpArrow, tint = WhitelonRoselonttaColor)

  // Unlimitelond display timelon (until uselonr takelons action)
  val DisplayDuration = -1.milliseloncond
  val TriggelonrDelonlay = 4.minutelons
}
