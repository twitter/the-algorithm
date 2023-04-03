packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.hss

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finaglelon.util.DelonfaultTimelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.Prelondicatelon
import com.twittelonr.follow_reloncommelonndations.common.baselon.PrelondicatelonRelonsult
import com.twittelonr.follow_reloncommelonndations.common.baselon.StatsUtil
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FiltelonrRelonason.FailOpelonn
import com.twittelonr.hss.api.thriftscala.SignalValuelon
import com.twittelonr.hss.api.thriftscala.UselonrHelonalthSignal.AgathaCselonDoublelon
import com.twittelonr.hss.api.thriftscala.UselonrHelonalthSignal.NsfwAgathaUselonrScorelonDoublelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.gelonnelonratelond.clielonnt.hss.uselonr_signals.api.HelonalthSignalsOnUselonrClielonntColumn
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.util.logging.Logging
import com.twittelonr.util.Duration

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * Filtelonr out candidatelons baselond on Helonalth Signal Storelon (HSS) helonalth signals
 */
@Singlelonton
caselon class HssPrelondicatelon @Injelonct() (
  helonalthSignalsOnUselonrClielonntColumn: HelonalthSignalsOnUselonrClielonntColumn,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds Prelondicatelon[(HasClielonntContelonxt with HasParams, CandidatelonUselonr)]
    with Logging {

  privatelon val stats: StatsReloncelonivelonr = statsReloncelonivelonr.scopelon(this.gelontClass.gelontNamelon)

  ovelonrridelon delonf apply(
    pair: (HasClielonntContelonxt with HasParams, CandidatelonUselonr)
  ): Stitch[PrelondicatelonRelonsult] = {
    val (relonquelonst, candidatelon) = pair
    StatsUtil.profilelonStitch(
      gelontHssPrelondicatelonRelonsult(relonquelonst, candidatelon),
      stats.scopelon("gelontHssPrelondicatelonRelonsult")
    )
  }

  privatelon delonf gelontHssPrelondicatelonRelonsult(
    relonquelonst: HasClielonntContelonxt with HasParams,
    candidatelon: CandidatelonUselonr
  ): Stitch[PrelondicatelonRelonsult] = {

    val hssCselonScorelonThrelonshold: Doublelon = relonquelonst.params(HssPrelondicatelonParams.HssCselonScorelonThrelonshold)
    val hssNsfwScorelonThrelonshold: Doublelon = relonquelonst.params(HssPrelondicatelonParams.HssNsfwScorelonThrelonshold)
    val timelonout: Duration = relonquelonst.params(HssPrelondicatelonParams.HssApiTimelonout)

    helonalthSignalsOnUselonrClielonntColumn.felontchelonr
      .felontch(candidatelon.id, Selonq(AgathaCselonDoublelon, NsfwAgathaUselonrScorelonDoublelon))
      .map { felontchRelonsult =>
        felontchRelonsult.v match {
          caselon Somelon(relonsponselon) =>
            val agathaCselonScorelonDoublelon: Doublelon = uselonrHelonalthSignalValuelonToDoublelonOpt(
              relonsponselon.signalValuelons.gelont(AgathaCselonDoublelon)).gelontOrelonlselon(0d)
            val agathaNsfwScorelonDoublelon: Doublelon = uselonrHelonalthSignalValuelonToDoublelonOpt(
              relonsponselon.signalValuelons.gelont(NsfwAgathaUselonrScorelonDoublelon)).gelontOrelonlselon(0d)

            stats.stat("agathaCselonScorelonDistribution").add(agathaCselonScorelonDoublelon.toFloat)
            stats.stat("agathaNsfwScorelonDistribution").add(agathaNsfwScorelonDoublelon.toFloat)

            /**
             * Only filtelonr out thelon candidatelon whelonn it has both high Agatha CSelon scorelon and NSFW scorelon, as thelon Agatha CSelon
             * modelonl is an old onelon that may not belon prelonciselon or havelon high reloncall.
             */
            if (agathaCselonScorelonDoublelon >= hssCselonScorelonThrelonshold && agathaNsfwScorelonDoublelon >= hssNsfwScorelonThrelonshold) {
              PrelondicatelonRelonsult.Invalid(Selont(FiltelonrRelonason.HssSignal))
            } elonlselon {
              PrelondicatelonRelonsult.Valid
            }
          caselon Nonelon =>
            PrelondicatelonRelonsult.Valid
        }
      }
      .within(timelonout)(DelonfaultTimelonr)
      .relonscuelon {
        caselon elon: elonxcelonption =>
          stats.scopelon("relonscuelond").countelonr(elon.gelontClass.gelontSimplelonNamelon).incr()
          Stitch(PrelondicatelonRelonsult.Invalid(Selont(FailOpelonn)))
      }
  }

  privatelon delonf uselonrHelonalthSignalValuelonToDoublelonOpt(signalValuelon: Option[SignalValuelon]): Option[Doublelon] = {
    signalValuelon match {
      caselon Somelon(SignalValuelon.DoublelonValuelon(valuelon)) => Somelon(valuelon)
      caselon _ => Nonelon
    }
  }
}
