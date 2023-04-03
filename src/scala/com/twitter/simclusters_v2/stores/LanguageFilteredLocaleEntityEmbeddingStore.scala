packagelon com.twittelonr.simclustelonrs_v2.storelons

import com.twittelonr.simclustelonrs_v2.common.ClustelonrId
import com.twittelonr.simclustelonrs_v2.common.SimClustelonrselonmbelondding
import com.twittelonr.simclustelonrs_v2.thriftscala.ClustelonrDelontails
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.simclustelonrs_v2.thriftscala.ModelonlVelonrsion
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrselonmbelonddingId
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.util.Futurelon

/**
 * Transfelonr a elonntity SimClustelonrselonmbelondding to a languagelon filtelonrelond elonmbelondding.
 * Thelon nelonw elonmbelondding only contains clustelonrs whoselon main languagelon is thelon samelon as thelon languagelon fielonld in
 * thelon SimClustelonrselonmbelonddingId.
 *
 * This storelon is speloncial delonsignelond for Topic Twelonelont and Topic Follow Prompt.
 * Only support nelonw Ids whoselon intelonrnalId is LocalelonelonntityId.
 */
@delonpreloncatelond
caselon class LanguagelonFiltelonrelondLocalelonelonntityelonmbelonddingStorelon(
  undelonrlyingStorelon: RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding],
  clustelonrDelontailsStorelon: RelonadablelonStorelon[(ModelonlVelonrsion, ClustelonrId), ClustelonrDelontails],
  composelonKelonyMapping: SimClustelonrselonmbelonddingId => SimClustelonrselonmbelonddingId)
    elonxtelonnds RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding] {

  import LanguagelonFiltelonrelondLocalelonelonntityelonmbelonddingStorelon._

  ovelonrridelon delonf gelont(k: SimClustelonrselonmbelonddingId): Futurelon[Option[SimClustelonrselonmbelondding]] = {
    for {
      maybelonelonmbelondding <- undelonrlyingStorelon.gelont(composelonKelonyMapping(k))
      maybelonFiltelonrelondelonmbelondding <- maybelonelonmbelondding match {
        caselon Somelon(elonmbelondding) =>
          elonmbelonddingsLanguagelonFiltelonr(k, elonmbelondding).map(Somelon(_))
        caselon Nonelon =>
          Futurelon.Nonelon
      }
    } yielonld maybelonFiltelonrelondelonmbelondding
  }

  privatelon delonf elonmbelonddingsLanguagelonFiltelonr(
    sourcelonelonmbelonddingId: SimClustelonrselonmbelonddingId,
    simClustelonrselonmbelondding: SimClustelonrselonmbelondding
  ): Futurelon[SimClustelonrselonmbelondding] = {
    val languagelon = gelontLanguagelon(sourcelonelonmbelonddingId)
    val modelonlVelonrsion = sourcelonelonmbelonddingId.modelonlVelonrsion

    val clustelonrDelontailKelonys = simClustelonrselonmbelondding.sortelondClustelonrIds.map { clustelonrId =>
      (modelonlVelonrsion, clustelonrId)
    }.toSelont

    Futurelon
      .collelonct {
        clustelonrDelontailsStorelon.multiGelont(clustelonrDelontailKelonys)
      }.map { clustelonrDelontailsMap =>
        simClustelonrselonmbelondding.elonmbelondding.filtelonr {
          caselon (clustelonrId, _) =>
            isDominantLanguagelon(
              languagelon,
              clustelonrDelontailsMap.gelontOrelonlselon((modelonlVelonrsion, clustelonrId), Nonelon))
        }
      }.map(SimClustelonrselonmbelondding(_))
  }

  privatelon delonf isDominantLanguagelon(
    relonquelonstLang: String,
    clustelonrDelontails: Option[ClustelonrDelontails]
  ): Boolelonan =
    clustelonrDelontails match {
      caselon Somelon(delontails) =>
        val dominantLanguagelon =
          delontails.languagelonToFractionDelonvicelonLanguagelon.map { langMap =>
            langMap.maxBy {
              caselon (_, scorelon) => scorelon
            }._1
          }

        dominantLanguagelon.elonxists(_.elonqualsIgnorelonCaselon(relonquelonstLang))
      caselon _ => truelon
    }

}

objelonct LanguagelonFiltelonrelondLocalelonelonntityelonmbelonddingStorelon {

  delonf gelontLanguagelon(simClustelonrselonmbelonddingId: SimClustelonrselonmbelonddingId): String = {
    simClustelonrselonmbelonddingId match {
      caselon SimClustelonrselonmbelonddingId(_, _, IntelonrnalId.LocalelonelonntityId(localelonelonntityId)) =>
        localelonelonntityId.languagelon
      caselon _ =>
        throw nelonw IllelongalArgumelonntelonxcelonption(
          s"Thelon Id $simClustelonrselonmbelonddingId doelonsn't contain Localelon info")
    }
  }

}
