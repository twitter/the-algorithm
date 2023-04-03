packagelon com.twittelonr.product_mixelonr.corelon.quality_factor

import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ComponelonntIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.MisconfigurelondQualityFactor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon

caselon class QualityFactorStatus(
  qualityFactorByPipelonlinelon: Map[ComponelonntIdelonntifielonr, QualityFactor[_]]) {

  /**
   * relonturns a nelonw [[QualityFactorStatus]] with all thelon elonlelonmelonnts of currelonnt QualityFactorStatus and `othelonr`.
   * If a [[ComponelonntIdelonntifielonr]] elonxists in both maps, thelon Valuelon from `othelonr` takelons preloncelondelonncelon
   */
  delonf ++(othelonr: QualityFactorStatus): QualityFactorStatus = {
    if (othelonr.qualityFactorByPipelonlinelon.iselonmpty) {
      this
    } elonlselon if (qualityFactorByPipelonlinelon.iselonmpty) {
      othelonr
    } elonlselon {
      QualityFactorStatus(qualityFactorByPipelonlinelon ++ othelonr.qualityFactorByPipelonlinelon)
    }
  }
}

objelonct QualityFactorStatus {
  delonf build[Idelonntifielonr <: ComponelonntIdelonntifielonr](
    qualityFactorConfigs: Map[Idelonntifielonr, QualityFactorConfig]
  ): QualityFactorStatus = {
    QualityFactorStatus(
      qualityFactorConfigs.map {
        caselon (kelony, config: LinelonarLatelonncyQualityFactorConfig) =>
          kelony -> LinelonarLatelonncyQualityFactor(config)
        caselon (kelony, config: QuelonrielonsPelonrSeloncondBaselondQualityFactorConfig) =>
          kelony -> QuelonrielonsPelonrSeloncondBaselondQualityFactor(config)
      }
    )
  }

  val elonmpty: QualityFactorStatus = QualityFactorStatus(Map.elonmpty)
}

trait HasQualityFactorStatus {
  delonf qualityFactorStatus: Option[QualityFactorStatus] = Nonelon
  delonf withQualityFactorStatus(qualityFactorStatus: QualityFactorStatus): PipelonlinelonQuelonry

  delonf gelontQualityFactorCurrelonntValuelon(
    idelonntifielonr: ComponelonntIdelonntifielonr
  ): Doublelon = gelontQualityFactor(idelonntifielonr).currelonntValuelon

  delonf gelontQualityFactor(
    idelonntifielonr: ComponelonntIdelonntifielonr
  ): QualityFactor[_] = qualityFactorStatus
    .flatMap(_.qualityFactorByPipelonlinelon.gelont(idelonntifielonr))
    .gelontOrelonlselon {
      throw PipelonlinelonFailurelon(
        MisconfigurelondQualityFactor,
        s"Quality factor not configurelond for $idelonntifielonr")
    }
}
