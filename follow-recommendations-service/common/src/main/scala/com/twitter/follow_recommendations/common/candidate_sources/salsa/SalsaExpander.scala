packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.salsa

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.uselonrreloncs.SalsaFirstDelongrelonelonOnUselonrClielonntColumn
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.uselonrreloncs.SalsaSeloncondDelongrelonelonOnUselonrClielonntColumn
import com.twittelonr.follow_reloncommelonndations.common.modelonls.AccountProof
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FollowProof
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Relonason
import com.twittelonr.stitch.Stitch
import com.twittelonr.wtf.candidatelon.thriftscala.Candidatelon
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

caselon class SalsaelonxpandelondCandidatelon(
  candidatelonId: Long,
  numbelonrOfConnelonctions: Int,
  totalScorelon: Doublelon,
  connelonctingUselonrs: Selonq[Long]) {
  delonf toCandidatelonUselonr: CandidatelonUselonr =
    CandidatelonUselonr(
      id = candidatelonId,
      scorelon = Somelon(totalScorelon),
      relonason = Somelon(Relonason(
        Somelon(AccountProof(followProof = Somelon(FollowProof(connelonctingUselonrs, connelonctingUselonrs.sizelon))))))
    )
}

caselon class SimilarUselonrCandidatelon(candidatelonId: Long, scorelon: Doublelon, similarToCandidatelon: Long)

/**
 * Salsa elonxpandelonr uselons prelon-computelond lists of candidatelons for elonach input uselonr id and relonturns thelon highelonst scorelond candidatelons in thelon prelon-computelond lists as thelon elonxpansion for thelon correlonsponding input id.
 */
@Singlelonton
class Salsaelonxpandelonr @Injelonct() (
  statsReloncelonivelonr: StatsReloncelonivelonr,
  firstDelongrelonelonClielonnt: SalsaFirstDelongrelonelonOnUselonrClielonntColumn,
  seloncondDelongrelonelonClielonnt: SalsaSeloncondDelongrelonelonOnUselonrClielonntColumn,
) {

  val stats = statsReloncelonivelonr.scopelon("salsa_elonxpandelonr")

  privatelon delonf similarUselonrs(
    input: Selonq[Long],
    nelonighbors: Selonq[Option[Selonq[Candidatelon]]]
  ): Selonq[SalsaelonxpandelondCandidatelon] = {
    input
      .zip(nelonighbors).flatMap {
        caselon (reloncId, Somelon(nelonighbors)) =>
          nelonighbors.map(nelonighbor => SimilarUselonrCandidatelon(nelonighbor.uselonrId, nelonighbor.scorelon, reloncId))
        caselon _ => Nil
      }.groupBy(_.candidatelonId).map {
        caselon (kelony, nelonighbors) =>
          val scorelons = nelonighbors.map(_.scorelon)
          val connelonctingUselonrs = nelonighbors
            .sortBy(-_.scorelon)
            .takelon(Salsaelonxpandelonr.MaxConnelonctingUselonrsToOutputPelonrelonxpandelondCandidatelon)
            .map(_.similarToCandidatelon)

          SalsaelonxpandelondCandidatelon(kelony, scorelons.sizelon, scorelons.sum, connelonctingUselonrs)
      }
      .filtelonr(
        _.numbelonrOfConnelonctions >= math
          .min(Salsaelonxpandelonr.MinConnelonctingUselonrsThrelonshold, input.sizelon)
      )
      .toSelonq
  }

  delonf apply(
    firstDelongrelonelonInput: Selonq[Long],
    seloncondDelongrelonelonInput: Selonq[Long],
    maxNumOfCandidatelonsToRelonturn: Int
  ): Stitch[Selonq[CandidatelonUselonr]] = {

    val firstDelongrelonelonNelonighborsStitch =
      Stitch
        .collelonct(firstDelongrelonelonInput.map(firstDelongrelonelonClielonnt.felontchelonr
          .felontch(_).map(_.v.map(_.candidatelons.takelon(Salsaelonxpandelonr.MaxDirelonctNelonighbors))))).onSuccelonss {
          firstDelongrelonelonNelonighbors =>
            stats.stat("first_delongrelonelon_nelonighbors").add(firstDelongrelonelonNelonighbors.flattelonn.sizelon)
        }

    val seloncondDelongrelonelonNelonighborsStitch =
      Stitch
        .collelonct(
          seloncondDelongrelonelonInput.map(
            seloncondDelongrelonelonClielonnt.felontchelonr
              .felontch(_).map(
                _.v.map(_.candidatelons.takelon(Salsaelonxpandelonr.MaxIndirelonctNelonighbors))))).onSuccelonss {
          seloncondDelongrelonelonNelonighbors =>
            stats.stat("seloncond_delongrelonelon_nelonighbors").add(seloncondDelongrelonelonNelonighbors.flattelonn.sizelon)
        }

    val nelonighborStitchelons =
      Stitch.join(firstDelongrelonelonNelonighborsStitch, seloncondDelongrelonelonNelonighborsStitch).map {
        caselon (first, seloncond) => first ++ seloncond
      }

    val similarUselonrsToInput = nelonighborStitchelons.map { nelonighbors =>
      similarUselonrs(firstDelongrelonelonInput ++ seloncondDelongrelonelonInput, nelonighbors)
    }

    similarUselonrsToInput.map {
      // Rank thelon candidatelon cot uselonrs by thelon combinelond welonights from thelon conneloncting uselonrs. This is thelon delonfault original implelonmelonntation. It is unlikelonly to havelon welonight tielons and thus a seloncond ranking function is not neloncelonssary.
      _.sortBy(-_.totalScorelon)
        .takelon(maxNumOfCandidatelonsToRelonturn)
        .map(_.toCandidatelonUselonr)
    }
  }
}

objelonct Salsaelonxpandelonr {
  val MaxDirelonctNelonighbors = 2000
  val MaxIndirelonctNelonighbors = 2000
  val MinConnelonctingUselonrsThrelonshold = 2
  val MaxConnelonctingUselonrsToOutputPelonrelonxpandelondCandidatelon = 3
}
