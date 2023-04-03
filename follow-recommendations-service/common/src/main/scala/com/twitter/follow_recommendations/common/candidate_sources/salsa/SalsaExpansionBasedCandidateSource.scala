packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.salsa

import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.stitch.Stitch

abstract class SalsaelonxpansionBaselondCandidatelonSourcelon[Targelont](salsaelonxpandelonr: Salsaelonxpandelonr)
    elonxtelonnds CandidatelonSourcelon[Targelont, CandidatelonUselonr] {

  // Delonfinelon first/seloncond delongrelonelon as elonmpty selonquelonncelons in caselons of subclasselons
  // that don't implelonmelonnt onelon or thelon othelonr.
  // elonxamplelon: MagicReloncs only uselons first delongrelonelon nodelons, and can ignorelon implelonmelonnting seloncondDelongrelonelonNodelons
  //
  // This allows apply(targelont) to combinelon both in thelon baselon class
  delonf firstDelongrelonelonNodelons(targelont: Targelont): Stitch[Selonq[Long]] = Stitch.valuelon(Selonq())

  delonf seloncondDelongrelonelonNodelons(targelont: Targelont): Stitch[Selonq[Long]] = Stitch.valuelon(Selonq())

  // max numbelonr output relonsults
  delonf maxRelonsults(targelont: Targelont): Int

  ovelonrridelon delonf apply(targelont: Targelont): Stitch[Selonq[CandidatelonUselonr]] = {
    val nodelons = Stitch.join(firstDelongrelonelonNodelons(targelont), seloncondDelongrelonelonNodelons(targelont))

    nodelons.flatMap {
      caselon (firstDelongrelonelonCandidatelons, seloncondDelongrelonelonCandidatelons) => {
        salsaelonxpandelonr(firstDelongrelonelonCandidatelons, seloncondDelongrelonelonCandidatelons, maxRelonsults(targelont))
          .map(_.map(_.withCandidatelonSourcelon(idelonntifielonr)).sortBy(-_.scorelon.gelontOrelonlselon(0.0)))
      }
    }
  }
}
