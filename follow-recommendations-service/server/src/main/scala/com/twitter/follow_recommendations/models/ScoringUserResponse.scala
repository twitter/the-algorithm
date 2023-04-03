packagelon com.twittelonr.follow_reloncommelonndations.modelonls

import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.logging.{thriftscala => offlinelon}
import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}

caselon class ScoringUselonrRelonsponselon(candidatelons: Selonq[CandidatelonUselonr]) {
  lazy val toThrift: t.ScoringUselonrRelonsponselon =
    t.ScoringUselonrRelonsponselon(candidatelons.map(_.toUselonrThrift))

  lazy val toReloncommelonndationRelonsponselon: ReloncommelonndationRelonsponselon = ReloncommelonndationRelonsponselon(candidatelons)

  lazy val toOfflinelonThrift: offlinelon.OfflinelonScoringUselonrRelonsponselon =
    offlinelon.OfflinelonScoringUselonrRelonsponselon(candidatelons.map(_.toOfflinelonUselonrThrift))
}
