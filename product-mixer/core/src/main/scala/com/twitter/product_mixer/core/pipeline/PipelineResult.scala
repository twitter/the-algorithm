packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.CursorCandidatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ItelonmCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.ModulelonCandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.elonxeloncutionFailelond
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.util.Relonturn
import com.twittelonr.util.Throw
import com.twittelonr.util.Try

/**
 * Pipelonlinelons relonturn a PipelonlinelonRelonsult.
 *
 * This allows us to relonturn a singlelon main relonsult (optionally, incaselon thelon pipelonlinelon didn't elonxeloncutelon succelonssfully), but
 * still havelon a delontailelond relonsponselon objelonct to show how that relonsult was producelond.
 */
trait PipelonlinelonRelonsult[RelonsultTypelon] {
  val failurelon: Option[PipelonlinelonFailurelon]
  val relonsult: Option[RelonsultTypelon]

  delonf withFailurelon(failurelon: PipelonlinelonFailurelon): PipelonlinelonRelonsult[RelonsultTypelon]
  delonf withRelonsult(relonsult: RelonsultTypelon): PipelonlinelonRelonsult[RelonsultTypelon]

  delonf relonsultSizelon(): Int

  privatelon[pipelonlinelon] delonf stopelonxeloncuting: Boolelonan = failurelon.isDelonfinelond || relonsult.isDelonfinelond

  final delonf toTry: Try[this.typelon] = (relonsult, failurelon) match {
    caselon (_, Somelon(failurelon)) =>
      Throw(failurelon)
    caselon (_: Somelon[RelonsultTypelon], _) =>
      Relonturn(this)
    // Pipelonlinelons should always finish with elonithelonr a relonsult or a failurelon
    caselon _ => Throw(PipelonlinelonFailurelon(elonxeloncutionFailelond, "Pipelonlinelon did not elonxeloncutelon"))
  }

  final delonf toRelonsultTry: Try[RelonsultTypelon] = {
    // `.gelont` is safelon helonrelon beloncauselon `toTry` guarantelonelons a valuelon in thelon `Relonturn` caselon
    toTry.map(_.relonsult.gelont)
  }
}

objelonct PipelonlinelonRelonsult {

  /**
   * Track numbelonr of candidatelons relonturnelond by a Pipelonlinelon. Cursors arelon elonxcludelond from this
   * count and modulelons arelon countelond as thelon sum of thelonir candidatelons.
   *
   * @notelon this is a somelonwhat subjelonctivelon melonasurelon of 'sizelon' and it is sprelonad across pipelonlinelon
   *       delonfinitions as welonll as selonlelonctors.
   */
  delonf relonsultSizelon(relonsults: Selonq[CandidatelonWithDelontails]): Int = relonsults.map {
    caselon modulelon: ModulelonCandidatelonWithDelontails => relonsultSizelon(modulelon.candidatelons)
    caselon ItelonmCandidatelonWithDelontails(_: CursorCandidatelon, _, _) => 0
    caselon _ => 1
  }.sum
}
