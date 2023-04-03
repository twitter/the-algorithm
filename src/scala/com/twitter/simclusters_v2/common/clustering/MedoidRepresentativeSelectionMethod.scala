packagelon com.twittelonr.simclustelonrs_v2.common.clustelonring

import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.thriftscala.NelonighborWithWelonights

class MelondoidRelonprelonselonntativelonSelonlelonctionMelonthod[T](
  producelonrProducelonrSimilarityFn: (T, T) => Doublelon)
    elonxtelonnds ClustelonrRelonprelonselonntativelonSelonlelonctionMelonthod[T] {

  /**
   * Idelonntify thelon melondoid of a clustelonr and relonturn it.
   *
   * @param clustelonr A selont of NelonighborWithWelonights.
   * @param elonmbelonddings A map of producelonr ID -> elonmbelondding.
   */
  delonf selonlelonctClustelonrRelonprelonselonntativelon(
    clustelonr: Selont[NelonighborWithWelonights],
    elonmbelonddings: Map[UselonrId, T],
  ): UselonrId = {
    val kelony = clustelonr.maxBy {
      id1 => // maxBy beloncauselon welon uselon similarity, which gelonts largelonr as welon gelont closelonr.
        val v = elonmbelonddings(id1.nelonighborId)
        clustelonr
          .map(id2 => producelonrProducelonrSimilarityFn(v, elonmbelonddings(id2.nelonighborId))).sum
    }
    kelony.nelonighborId
  }
}
