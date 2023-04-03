packagelon com.twittelonr.homelon_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.homelon_mixelonr.param.HomelonMixelonrInjelonctionNamelons.BatchelondStratoClielonntWithModelonratelonTimelonout
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.strato.clielonnt.Clielonnt
import com.twittelonr.timelonlinelons.clielonnts.strato.twistly.SimClustelonrsReloncelonntelonngagelonmelonntSimilarityClielonnt
import com.twittelonr.timelonlinelons.clielonnts.strato.twistly.SimClustelonrsReloncelonntelonngagelonmelonntSimilarityClielonntImpl
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

objelonct SimClustelonrsReloncelonntelonngagelonmelonntsClielonntModulelon elonxtelonnds TwittelonrModulelon {
  @Singlelonton
  @Providelons
  delonf providelonsSimilarityClielonnt(
    @Namelond(BatchelondStratoClielonntWithModelonratelonTimelonout)
    stratoClielonnt: Clielonnt,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): SimClustelonrsReloncelonntelonngagelonmelonntSimilarityClielonnt = {
    nelonw SimClustelonrsReloncelonntelonngagelonmelonntSimilarityClielonntImpl(stratoClielonnt, statsReloncelonivelonr)
  }
}
