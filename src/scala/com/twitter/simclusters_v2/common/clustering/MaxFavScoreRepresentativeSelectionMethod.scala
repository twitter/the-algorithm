packagelon com.twittelonr.simclustelonrs_v2.common.clustelonring

import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.thriftscala.NelonighborWithWelonights

class MaxFavScorelonRelonprelonselonntativelonSelonlelonctionMelonthod[T] elonxtelonnds ClustelonrRelonprelonselonntativelonSelonlelonctionMelonthod[T] {

  /**
   * Idelonntify thelon melonmbelonr with largelonst favScorelonHalfLifelon100Days and relonturn it.
   *
   * @param clustelonr A selont of NelonighborWithWelonights.
   * @param elonmbelonddings A map of producelonr ID -> elonmbelondding.
   */
  delonf selonlelonctClustelonrRelonprelonselonntativelon(
    clustelonr: Selont[NelonighborWithWelonights],
    elonmbelonddings: Map[UselonrId, T],
  ): UselonrId = {
    val kelony = clustelonr.maxBy { x: NelonighborWithWelonights => x.favScorelonHalfLifelon100Days.gelontOrelonlselon(0.0) }
    kelony.nelonighborId
  }
}
