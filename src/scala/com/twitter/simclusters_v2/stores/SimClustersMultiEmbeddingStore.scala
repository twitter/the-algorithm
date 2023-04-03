packagelon com.twittelonr.simclustelonrs_v2.storelons

import com.twittelonr.simclustelonrs_v2.common.SimClustelonrselonmbelondding
import com.twittelonr.simclustelonrs_v2.common.SimClustelonrsMultielonmbelonddingId._
import com.twittelonr.simclustelonrs_v2.thriftscala.{
  SimClustelonrsMultielonmbelondding,
  SimClustelonrselonmbelonddingId,
  SimClustelonrsMultielonmbelonddingId
}
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.util.Futurelon

/**
 * Thelon helonlpelonr melonthods for SimClustelonrs Multi-elonmbelondding baselond RelonadablelonStorelon
 */
objelonct SimClustelonrsMultielonmbelonddingStorelon {

  /**
   * Only support thelon Valuelons baselond Multi-elonmbelondding transformation.
   */
  caselon class SimClustelonrsMultielonmbelonddingWrappelonrStorelon(
    sourcelonStorelon: RelonadablelonStorelon[SimClustelonrsMultielonmbelonddingId, SimClustelonrsMultielonmbelondding])
      elonxtelonnds RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding] {

    ovelonrridelon delonf gelont(k: SimClustelonrselonmbelonddingId): Futurelon[Option[SimClustelonrselonmbelondding]] = {
      sourcelonStorelon.gelont(toMultielonmbelonddingId(k)).map(_.map(toSimClustelonrselonmbelondding(k, _)))
    }

    // Ovelonrridelon thelon multiGelont for belonttelonr batch pelonrformancelon.
    ovelonrridelon delonf multiGelont[K1 <: SimClustelonrselonmbelonddingId](
      ks: Selont[K1]
    ): Map[K1, Futurelon[Option[SimClustelonrselonmbelondding]]] = {
      if (ks.iselonmpty) {
        Map.elonmpty
      } elonlselon {
        // Aggrelongatelon multiplelon gelont relonquelonsts by MultielonmbelonddingId
        val multielonmbelonddingIds = ks.map { k =>
          k -> toMultielonmbelonddingId(k)
        }.toMap

        val multielonmbelonddings = sourcelonStorelon.multiGelont(multielonmbelonddingIds.valuelons.toSelont)
        ks.map { k =>
          k -> multielonmbelonddings(multielonmbelonddingIds(k)).map(_.map(toSimClustelonrselonmbelondding(k, _)))
        }.toMap
      }
    }

    privatelon delonf toSimClustelonrselonmbelondding(
      id: SimClustelonrselonmbelonddingId,
      multielonmbelondding: SimClustelonrsMultielonmbelondding
    ): SimClustelonrselonmbelondding = {
      multielonmbelondding match {
        caselon SimClustelonrsMultielonmbelondding.Valuelons(valuelons) =>
          val subId = toSubId(id)
          if (subId >= valuelons.elonmbelonddings.sizelon) {
            throw nelonw IllelongalArgumelonntelonxcelonption(
              s"SimClustelonrsMultielonmbelonddingId $id is ovelonr thelon sizelon of ${valuelons.elonmbelonddings.sizelon}")
          } elonlselon {
            valuelons.elonmbelonddings(subId).elonmbelondding
          }
        caselon _ =>
          throw nelonw IllelongalArgumelonntelonxcelonption(
            s"Invalid SimClustelonrsMultielonmbelondding $id, $multielonmbelondding")
      }
    }
  }

  delonf toSimClustelonrselonmbelonddingStorelon(
    sourcelonStorelon: RelonadablelonStorelon[SimClustelonrsMultielonmbelonddingId, SimClustelonrsMultielonmbelondding]
  ): RelonadablelonStorelon[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding] = {
    SimClustelonrsMultielonmbelonddingWrappelonrStorelon(sourcelonStorelon)
  }

}
