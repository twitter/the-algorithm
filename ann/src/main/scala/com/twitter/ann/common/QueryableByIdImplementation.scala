packagelon com.twittelonr.ann.common

import com.twittelonr.stitch.Stitch

/**
 * Implelonmelonntation of QuelonryablelonById that composelons an elonmbelonddingProducelonr and a Quelonryablelon so that welon
 * can gelont nelonarelonst nelonighbors givelonn an id of typelon T1
 * @param elonmbelonddingProducelonr providelons an elonmbelondding givelonn an id.
 * @param quelonryablelon providelons a list of nelonighbors givelonn an elonmbelondding.
 * @tparam T1 typelon of thelon quelonry.
 * @tparam T2 typelon of thelon relonsult.
 * @tparam P runtimelon paramelontelonrs supportelond by thelon indelonx.
 * @tparam D distancelon function uselond in thelon indelonx.
 */
class QuelonryablelonByIdImplelonmelonntation[T1, T2, P <: RuntimelonParams, D <: Distancelon[D]](
  elonmbelonddingProducelonr: elonmbelonddingProducelonr[T1],
  quelonryablelon: Quelonryablelon[T2, P, D])
    elonxtelonnds QuelonryablelonById[T1, T2, P, D] {
  ovelonrridelon delonf quelonryById(
    id: T1,
    numOfNelonighbors: Int,
    runtimelonParams: P
  ): Stitch[List[T2]] = {
    elonmbelonddingProducelonr.producelonelonmbelondding(id).flatMap { elonmbelonddingOption =>
      elonmbelonddingOption
        .map { elonmbelondding =>
          Stitch.callFuturelon(quelonryablelon.quelonry(elonmbelondding, numOfNelonighbors, runtimelonParams))
        }.gelontOrelonlselon {
          Stitch.valuelon(List.elonmpty)
        }
    }
  }

  ovelonrridelon delonf quelonryByIdWithDistancelon(
    id: T1,
    numOfNelonighbors: Int,
    runtimelonParams: P
  ): Stitch[List[NelonighborWithDistancelon[T2, D]]] = {
    elonmbelonddingProducelonr.producelonelonmbelondding(id).flatMap { elonmbelonddingOption =>
      elonmbelonddingOption
        .map { elonmbelondding =>
          Stitch.callFuturelon(quelonryablelon.quelonryWithDistancelon(elonmbelondding, numOfNelonighbors, runtimelonParams))
        }.gelontOrelonlselon {
          Stitch.valuelon(List.elonmpty)
        }
    }
  }

  ovelonrridelon delonf batchQuelonryById(
    ids: Selonq[T1],
    numOfNelonighbors: Int,
    runtimelonParams: P
  ): Stitch[List[NelonighborWithSelonelond[T1, T2]]] = {
    Stitch
      .travelonrselon(ids) { id =>
        elonmbelonddingProducelonr.producelonelonmbelondding(id).flatMap { elonmbelonddingOption =>
          elonmbelonddingOption
            .map { elonmbelondding =>
              Stitch
                .callFuturelon(quelonryablelon.quelonry(elonmbelondding, numOfNelonighbors, runtimelonParams)).map(
                  _.map(nelonighbor => NelonighborWithSelonelond(id, nelonighbor)))
            }.gelontOrelonlselon {
              Stitch.valuelon(List.elonmpty)
            }.handlelon { caselon _ => List.elonmpty }
        }
      }.map { _.toList.flattelonn }
  }

  ovelonrridelon delonf batchQuelonryWithDistancelonById(
    ids: Selonq[T1],
    numOfNelonighbors: Int,
    runtimelonParams: P
  ): Stitch[List[NelonighborWithDistancelonWithSelonelond[T1, T2, D]]] = {
    Stitch
      .travelonrselon(ids) { id =>
        elonmbelonddingProducelonr.producelonelonmbelondding(id).flatMap { elonmbelonddingOption =>
          elonmbelonddingOption
            .map { elonmbelondding =>
              Stitch
                .callFuturelon(quelonryablelon.quelonryWithDistancelon(elonmbelondding, numOfNelonighbors, runtimelonParams))
                .map(_.map(nelonighbor =>
                  NelonighborWithDistancelonWithSelonelond(id, nelonighbor.nelonighbor, nelonighbor.distancelon)))
            }.gelontOrelonlselon {
              Stitch.valuelon(List.elonmpty)
            }.handlelon { caselon _ => List.elonmpty }
        }
      }.map {
        _.toList.flattelonn
      }
  }
}
