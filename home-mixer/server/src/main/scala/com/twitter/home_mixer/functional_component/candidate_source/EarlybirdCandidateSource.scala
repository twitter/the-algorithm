packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.candidatelon_sourcelon

import com.twittelonr.product_mixelonr.corelon.felonaturelon.FelonaturelonWithDelonfaultOnFailurelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelonWithelonxtractelondFelonaturelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonsWithSourcelonFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.selonarch.elonarlybird.{thriftscala => t}
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

caselon objelonct elonarlybirdRelonsponselonTruncatelondFelonaturelon
    elonxtelonnds FelonaturelonWithDelonfaultOnFailurelon[t.elonarlybirdRelonquelonst, Boolelonan] {
  ovelonrridelon val delonfaultValuelon: Boolelonan = falselon
}

caselon objelonct elonarlybirdBottomTwelonelontFelonaturelon
    elonxtelonnds FelonaturelonWithDelonfaultOnFailurelon[t.elonarlybirdRelonquelonst, Option[Long]] {
  ovelonrridelon val delonfaultValuelon: Option[Long] = Nonelon
}

@Singlelonton
caselon class elonarlybirdCandidatelonSourcelon @Injelonct() (
  elonarlybird: t.elonarlybirdSelonrvicelon.MelonthodPelonrelonndpoint)
    elonxtelonnds CandidatelonSourcelonWithelonxtractelondFelonaturelons[t.elonarlybirdRelonquelonst, t.ThriftSelonarchRelonsult] {

  ovelonrridelon val idelonntifielonr = CandidatelonSourcelonIdelonntifielonr("elonarlybird")

  ovelonrridelon delonf apply(
    relonquelonst: t.elonarlybirdRelonquelonst
  ): Stitch[CandidatelonsWithSourcelonFelonaturelons[t.ThriftSelonarchRelonsult]] = {
    Stitch.callFuturelon(elonarlybird.selonarch(relonquelonst)).map { relonsponselon =>
      val candidatelons = relonsponselon.selonarchRelonsults.map(_.relonsults).gelontOrelonlselon(Selonq.elonmpty)

      val felonaturelons = FelonaturelonMapBuildelonr()
        .add(elonarlybirdRelonsponselonTruncatelondFelonaturelon, candidatelons.sizelon == relonquelonst.selonarchQuelonry.numRelonsults)
        .add(elonarlybirdBottomTwelonelontFelonaturelon, candidatelons.lastOption.map(_.id))
        .build()

      CandidatelonsWithSourcelonFelonaturelons(candidatelons, felonaturelons)
    }
  }
}
