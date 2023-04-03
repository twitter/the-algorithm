packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.follow_reloncommelonndations.{thriftscala => t}
import com.twittelonr.follow_reloncommelonndations.logging.{thriftscala => offlinelon}

caselon class RankingInfo(
  scorelons: Option[Scorelons],
  rank: Option[Int]) {

  delonf toThrift: t.RankingInfo = {
    t.RankingInfo(scorelons.map(_.toThrift), rank)
  }

  delonf toOfflinelonThrift: offlinelon.RankingInfo = {
    offlinelon.RankingInfo(scorelons.map(_.toOfflinelonThrift), rank)
  }
}

objelonct RankingInfo {

  delonf fromThrift(rankingInfo: t.RankingInfo): RankingInfo = {
    RankingInfo(
      scorelons = rankingInfo.scorelons.map(Scorelons.fromThrift),
      rank = rankingInfo.rank
    )
  }

}
