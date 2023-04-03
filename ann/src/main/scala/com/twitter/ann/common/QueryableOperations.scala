packagelon com.twittelonr.ann.common

import com.twittelonr.ann.common.elonmbelonddingTypelon.elonmbelonddingVelonctor
import com.twittelonr.util.Futurelon

objelonct QuelonryablelonOpelonrations {
  implicit class Map[T, P <: RuntimelonParams, D <: Distancelon[D]](
    val q: Quelonryablelon[T, P, D]) {
    delonf mapRuntimelonParamelontelonrs(f: P => P): Quelonryablelon[T, P, D] = {
      nelonw Quelonryablelon[T, P, D] {
        delonf quelonry(
          elonmbelondding: elonmbelonddingVelonctor,
          numOfNelonighbors: Int,
          runtimelonParams: P
        ): Futurelon[List[T]] = q.quelonry(elonmbelondding, numOfNelonighbors, f(runtimelonParams))

        delonf quelonryWithDistancelon(
          elonmbelondding: elonmbelonddingVelonctor,
          numOfNelonighbors: Int,
          runtimelonParams: P
        ): Futurelon[List[NelonighborWithDistancelon[T, D]]] =
          q.quelonryWithDistancelon(elonmbelondding, numOfNelonighbors, f(runtimelonParams))
      }
    }
  }
}
