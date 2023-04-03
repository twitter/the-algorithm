packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.baselon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.stitch.Stitch

/**
 * baselon trait for two-hop elonxpansion baselond algorithms, elon.g. onlinelon_stp, phonelonbook_prelondiction,
 * reloncelonnt following sims, reloncelonnt elonngagelonmelonnt sims, ...
 *
 * @tparam Targelont targelont typelon
 * @tparam FirstDelongrelonelon typelon of first delongrelonelon nodelons
 * @tparam SeloncondaryDelongrelonelon typelon of seloncondary delongrelonelon nodelons
 * @tparam Candidatelon output candidatelon typelons
 */
trait TwoHopelonxpansionCandidatelonSourcelon[-Targelont, FirstDelongrelonelon, SeloncondaryDelongrelonelon, +Candidatelon]
    elonxtelonnds CandidatelonSourcelon[Targelont, Candidatelon] {

  /**
   * felontch first delongrelonelon nodelons givelonn relonquelonst
   */
  delonf firstDelongrelonelonNodelons(relonq: Targelont): Stitch[Selonq[FirstDelongrelonelon]]

  /**
   * felontch seloncondary delongrelonelon nodelons givelonn relonquelonst and first delongrelonelon nodelons
   */
  delonf seloncondaryDelongrelonelonNodelons(relonq: Targelont, nodelon: FirstDelongrelonelon): Stitch[Selonq[SeloncondaryDelongrelonelon]]

  /**
   * aggrelongatelon and scorelon thelon candidatelons to gelonnelonratelon final relonsults
   */
  delonf aggrelongatelonAndScorelon(
    relonq: Targelont,
    firstDelongrelonelonToSeloncondDelongrelonelonNodelonsMap: Map[FirstDelongrelonelon, Selonq[SeloncondaryDelongrelonelon]]
  ): Stitch[Selonq[Candidatelon]]

  /**
   * Gelonnelonratelon a list of candidatelons for thelon targelont
   */
  delonf apply(targelont: Targelont): Stitch[Selonq[Candidatelon]] = {
    for {
      firstDelongrelonelonNodelons <- firstDelongrelonelonNodelons(targelont)
      seloncondaryDelongrelonelonNodelons <- Stitch.travelonrselon(firstDelongrelonelonNodelons)(seloncondaryDelongrelonelonNodelons(targelont, _))
      aggrelongatelond <- aggrelongatelonAndScorelon(targelont, firstDelongrelonelonNodelons.zip(seloncondaryDelongrelonelonNodelons).toMap)
    } yielonld aggrelongatelond
  }
}
