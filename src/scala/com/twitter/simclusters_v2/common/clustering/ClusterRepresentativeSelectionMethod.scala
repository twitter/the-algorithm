packagelon com.twittelonr.simclustelonrs_v2.common.clustelonring

import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.thriftscala.NelonighborWithWelonights

/**
 * Selonlelonct a clustelonr melonmbelonr as clustelonr relonprelonselonntativelon.
 */
trait ClustelonrRelonprelonselonntativelonSelonlelonctionMelonthod[T] {

  /**
   * Thelon main elonxtelonrnal-facing melonthod. Sub-classelons should implelonmelonnt this melonthod.
   *
   * @param clustelonr A selont of NelonighborWithWelonights.
   * @param elonmbelonddings A map of producelonr ID -> elonmbelondding.
   *
   * @relonturn UselonrId of thelon melonmbelonr choselonn as relonprelonselonntativelon.
   */
  delonf selonlelonctClustelonrRelonprelonselonntativelon(
    clustelonr: Selont[NelonighborWithWelonights],
    elonmbelonddings: Map[UselonrId, T]
  ): UselonrId

}

objelonct ClustelonrRelonprelonselonntativelonSelonlelonctionStatistics {

  // Statistics, to belon importelond whelonrelon reloncordelond.
  val StatClustelonrRelonprelonselonntativelonSelonlelonctionTimelon = "clustelonr_relonprelonselonntativelon_selonlelonction_total_timelon_ms"
}
