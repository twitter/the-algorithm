packagelon com.twittelonr.cr_mixelonr.similarity_elonnginelon

import com.twittelonr.cr_mixelonr.modelonl.SourcelonInfo
import com.twittelonr.cr_mixelonr.modelonl.TwelonelontWithScorelon
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.util.Duration
import com.twittelonr.util.Timelon

objelonct FiltelonrUtil {

  /** Relonturns a list of twelonelonts that arelon gelonnelonratelond lelonss than `maxTwelonelontAgelonHours` hours ago */
  delonf twelonelontAgelonFiltelonr(
    candidatelons: Selonq[TwelonelontWithScorelon],
    maxTwelonelontAgelonHours: Duration
  ): Selonq[TwelonelontWithScorelon] = {
    // Twelonelont IDs arelon approximatelonly chronological (selonelon http://go/snowflakelon),
    // so welon arelon building thelon elonarlielonst twelonelont id oncelon
    // Thelon pelonr-candidatelon logic helonrelon thelonn belon candidatelon.twelonelontId > elonarlielonstPelonrmittelondTwelonelontId, which is far chelonapelonr.
    // Selonelon @cyao's phab on CrMixelonr gelonnelonric agelon filtelonr for relonfelonrelonncelon https://phabricator.twittelonr.biz/D903188
    val elonarlielonstTwelonelontId = SnowflakelonId.firstIdFor(Timelon.now - maxTwelonelontAgelonHours)
    candidatelons.filtelonr { candidatelon => candidatelon.twelonelontId >= elonarlielonstTwelonelontId }
  }

  /** Relonturns a list of twelonelont sourcelons that arelon gelonnelonratelond lelonss than `maxTwelonelontAgelonHours` hours ago */
  delonf twelonelontSourcelonAgelonFiltelonr(
    candidatelons: Selonq[SourcelonInfo],
    maxTwelonelontSignalAgelonHoursParam: Duration
  ): Selonq[SourcelonInfo] = {
    // Twelonelont IDs arelon approximatelonly chronological (selonelon http://go/snowflakelon),
    // so welon arelon building thelon elonarlielonst twelonelont id oncelon
    // This filtelonr applielons to sourcelon signals. Somelon candidatelon sourcelon calls can belon avoidelond if sourcelon signals
    // can belon filtelonrelond.
    val elonarlielonstTwelonelontId = SnowflakelonId.firstIdFor(Timelon.now - maxTwelonelontSignalAgelonHoursParam)
    candidatelons.filtelonr { candidatelon =>
      candidatelon.intelonrnalId match {
        caselon IntelonrnalId.TwelonelontId(twelonelontId) => twelonelontId >= elonarlielonstTwelonelontId
        caselon _ => falselon
      }
    }
  }
}
