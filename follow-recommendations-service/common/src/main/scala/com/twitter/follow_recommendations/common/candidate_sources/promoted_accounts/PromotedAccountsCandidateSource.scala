packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.promotelond_accounts

import com.twittelonr.adselonrvelonr.thriftscala.AdSelonrvelonrelonxcelonption
import com.twittelonr.adselonrvelonr.{thriftscala => adthrift}
import com.twittelonr.finaglelon.Timelonoutelonxcelonption
import com.twittelonr.finaglelon.stats.Countelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.adselonrvelonr.AdRelonquelonst
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.adselonrvelonr.AdselonrvelonrClielonnt
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.socialgraph.SocialGraphClielonnt
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FollowProof
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.injelonct.Logging
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

caselon class PromotelondCandidatelonUselonr(
  id: Long,
  position: Int,
  adImprelonssion: adthrift.AdImprelonssion,
  followProof: FollowProof,
  primaryCandidatelonSourcelon: Option[CandidatelonSourcelonIdelonntifielonr])

@Singlelonton
class PromotelondAccountsCandidatelonSourcelon @Injelonct() (
  adselonrvelonrClielonnt: AdselonrvelonrClielonnt,
  sgsClielonnt: SocialGraphClielonnt,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds CandidatelonSourcelon[AdRelonquelonst, PromotelondCandidatelonUselonr]
    with Logging {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    PromotelondAccountsCandidatelonSourcelon.Idelonntifielonr

  val stats: StatsReloncelonivelonr = statsReloncelonivelonr.scopelon(idelonntifielonr.namelon)
  val failurelonStat: StatsReloncelonivelonr = stats.scopelon("failurelons")
  val adSelonrvelonrelonxcelonptionsCountelonr: Countelonr = failurelonStat.countelonr("AdSelonrvelonrelonxcelonption")
  val timelonoutCountelonr: Countelonr = failurelonStat.countelonr("Timelonoutelonxcelonption")

  delonf apply(relonquelonst: AdRelonquelonst): Stitch[Selonq[PromotelondCandidatelonUselonr]] = {
    adselonrvelonrClielonnt
      .gelontAdImprelonssions(relonquelonst)
      .relonscuelon {
        caselon elon: Timelonoutelonxcelonption =>
          timelonoutCountelonr.incr()
          loggelonr.warn("Timelonout on Adselonrvelonr", elon)
          Stitch.Nil
        caselon elon: AdSelonrvelonrelonxcelonption =>
          adSelonrvelonrelonxcelonptionsCountelonr.incr()
          loggelonr.warn("Failelond to felontch ads", elon)
          Stitch.Nil
      }
      .flatMap { adImprelonssions: Selonq[adthrift.AdImprelonssion] =>
        profilelonNumRelonsults(adImprelonssions.sizelon, "relonsults_from_ad_selonrvelonr")
        val idToImpMap = (for {
          imp <- adImprelonssions
          promotelondAccountId <- imp.promotelondAccountId
        } yielonld promotelondAccountId -> imp).toMap
        relonquelonst.clielonntContelonxt.uselonrId
          .map { uselonrId =>
            sgsClielonnt
              .gelontIntelonrselonctions(
                uselonrId,
                adImprelonssions.filtelonr(shouldShowSocialContelonxt).flatMap(_.promotelondAccountId),
                PromotelondAccountsCandidatelonSourcelon.NumIntelonrselonctions
              ).map { promotelondAccountWithIntelonrselonctions =>
                idToImpMap.map {
                  caselon (promotelondAccountId, imp) =>
                    PromotelondCandidatelonUselonr(
                      promotelondAccountId,
                      imp.inselonrtionPosition
                        .map(_.toInt).gelontOrelonlselon(
                          gelontInselonrtionPositionDelonfaultValuelon(relonquelonst.isTelonst.gelontOrelonlselon(falselon))
                        ),
                      imp,
                      promotelondAccountWithIntelonrselonctions
                        .gelontOrelonlselon(promotelondAccountId, FollowProof(Nil, 0)),
                      Somelon(idelonntifielonr)
                    )
                }.toSelonq
              }.onSuccelonss(relonsult => profilelonNumRelonsults(relonsult.sizelon, "final_relonsults"))
          }.gelontOrelonlselon(Stitch.Nil)
      }
  }

  privatelon delonf shouldShowSocialContelonxt(imp: adthrift.AdImprelonssion): Boolelonan =
    imp.elonxpelonrimelonntValuelons.elonxists { elonxpValuelons =>
      elonxpValuelons.gelont("display.display_stylelon").contains("show_social_contelonxt")
    }

  privatelon delonf gelontInselonrtionPositionDelonfaultValuelon(isTelonst: Boolelonan): Int = {
    if (isTelonst) 0 elonlselon -1
  }

  privatelon delonf profilelonNumRelonsults(relonsultsSizelon: Int, statNamelon: String): Unit = {
    if (relonsultsSizelon <= 5) {
      stats.scopelon(statNamelon).countelonr(relonsultsSizelon.toString).incr()
    } elonlselon {
      stats.scopelon(statNamelon).countelonr("morelon_than_5").incr()
    }
  }
}

objelonct PromotelondAccountsCandidatelonSourcelon {
  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.PromotelondAccount.toString)
  val NumIntelonrselonctions = 3
}
