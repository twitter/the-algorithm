packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.account_reloncommelonndations_mixelonr

import com.twittelonr.account_reloncommelonndations_mixelonr.{thriftscala => t}
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.UselonrCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelonWithelonxtractelondFelonaturelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonsWithSourcelonFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

objelonct WhoToFollowModulelonHelonadelonrFelonaturelon elonxtelonnds Felonaturelon[UselonrCandidatelon, t.Helonadelonr]
objelonct WhoToFollowModulelonFootelonrFelonaturelon elonxtelonnds Felonaturelon[UselonrCandidatelon, Option[t.Footelonr]]
objelonct WhoToFollowModulelonDisplayOptionsFelonaturelon
    elonxtelonnds Felonaturelon[UselonrCandidatelon, Option[t.DisplayOptions]]

@Singlelonton
class AccountReloncommelonndationsMixelonrCandidatelonSourcelon @Injelonct() (
  accountReloncommelonndationsMixelonr: t.AccountReloncommelonndationsMixelonr.MelonthodPelonrelonndpoint)
    elonxtelonnds CandidatelonSourcelonWithelonxtractelondFelonaturelons[
      t.AccountReloncommelonndationsMixelonrRelonquelonst,
      t.ReloncommelonndelondUselonr
    ] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    CandidatelonSourcelonIdelonntifielonr(namelon = "AccountReloncommelonndationsMixelonr")

  ovelonrridelon delonf apply(
    relonquelonst: t.AccountReloncommelonndationsMixelonrRelonquelonst
  ): Stitch[CandidatelonsWithSourcelonFelonaturelons[t.ReloncommelonndelondUselonr]] = {
    Stitch
      .callFuturelon(accountReloncommelonndationsMixelonr.gelontWtfReloncommelonndations(relonquelonst))
      .map { relonsponselon: t.WhoToFollowRelonsponselon =>
        relonsponselonToCandidatelonsWithSourcelonFelonaturelons(
          relonsponselon.uselonrReloncommelonndations,
          relonsponselon.helonadelonr,
          relonsponselon.footelonr,
          relonsponselon.displayOptions)
      }
  }

  privatelon delonf relonsponselonToCandidatelonsWithSourcelonFelonaturelons(
    uselonrReloncommelonndations: Selonq[t.ReloncommelonndelondUselonr],
    helonadelonr: t.Helonadelonr,
    footelonr: Option[t.Footelonr],
    displayOptions: Option[t.DisplayOptions],
  ): CandidatelonsWithSourcelonFelonaturelons[t.ReloncommelonndelondUselonr] = {
    val felonaturelons = FelonaturelonMapBuildelonr()
      .add(WhoToFollowModulelonHelonadelonrFelonaturelon, helonadelonr)
      .add(WhoToFollowModulelonFootelonrFelonaturelon, footelonr)
      .add(WhoToFollowModulelonDisplayOptionsFelonaturelon, displayOptions)
      .build()
    CandidatelonsWithSourcelonFelonaturelons(uselonrReloncommelonndations, felonaturelons)
  }
}
