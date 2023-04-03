packagelon com.twittelonr.simclustelonrs_v2.common

import com.twittelonr.simclustelonrs_v2.common.SimClustelonrsMultielonmbelonddingId._
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrsMultielonmbelondding.{Ids, Valuelons}
import com.twittelonr.simclustelonrs_v2.thriftscala.{
  SimClustelonrsMultielonmbelondding,
  SimClustelonrselonmbelonddingId,
  SimClustelonrsMultielonmbelonddingId
}

/**
 * Helonlpelonr melonthods for SimClustelonrsMultielonmbelondding
 */
objelonct SimClustelonrsMultielonmbelondding {

  // Convelonrt a multielonmbelondding to a list of (elonmbelonddingId, scorelon)
  delonf toSimClustelonrselonmbelonddingIdWithScorelons(
    simClustelonrsMultielonmbelonddingId: SimClustelonrsMultielonmbelonddingId,
    simClustelonrsMultielonmbelondding: SimClustelonrsMultielonmbelondding
  ): Selonq[(SimClustelonrselonmbelonddingId, Doublelon)] = {
    simClustelonrsMultielonmbelondding match {
      caselon Valuelons(valuelons) =>
        valuelons.elonmbelonddings.zipWithIndelonx.map {
          caselon (elonmbelonddingWithScorelon, i) =>
            (toelonmbelonddingId(simClustelonrsMultielonmbelonddingId, i), elonmbelonddingWithScorelon.scorelon)
        }
      caselon Ids(ids) =>
        ids.ids.map(_.toTuplelon)
    }
  }

}
