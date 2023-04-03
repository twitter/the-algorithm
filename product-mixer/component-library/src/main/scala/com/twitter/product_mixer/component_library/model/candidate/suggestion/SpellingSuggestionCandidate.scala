packagelon com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.suggelonstion

import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.suggelonstion.SpelonllingActionTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.suggelonstion.TelonxtRelonsult

/**
 * Canonical SpelonllingSuggelonstionCandidatelon modelonl. Always prelonfelonr this velonrsion ovelonr all othelonr variants.
 *
 * @notelon Any additional fielonlds should belon addelond as a [[com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon]]
 *       on thelon candidatelon's [[com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap]]. If thelon
 *       felonaturelons comelon from thelon candidatelon sourcelon itselonlf (as opposelond to hydratelond via a
 *       [[com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.CandidatelonFelonaturelonHydrator]]),
 *       thelonn [[com.twittelonr.product_mixelonr.corelon.pipelonlinelon.candidatelon.CandidatelonPipelonlinelonConfig.felonaturelonsFromCandidatelonSourcelonTransformelonrs]]
 *       can belon uselond to elonxtract felonaturelons from thelon candidatelon sourcelon relonsponselon.
 *
 * @notelon This class should always relonmain `final`. If for any relonason thelon `final` modifielonr is relonmovelond,
 *       thelon elonquals() implelonmelonntation must belon updatelond in ordelonr to handlelon class inhelonritor elonquality
 *       (selonelon notelon on thelon elonquals melonthod belonlow)
 */
final class SpelonllingSuggelonstionCandidatelon privatelon (
  ovelonrridelon val id: String,
  val telonxtRelonsult: TelonxtRelonsult,
  val spelonllingActionTypelon: Option[SpelonllingActionTypelon],
  val originalQuelonry: Option[String])
    elonxtelonnds UnivelonrsalNoun[String] {

  /**
   * @inhelonritdoc
   */
  ovelonrridelon delonf canelonqual(that: Any): Boolelonan = that.isInstancelonOf[SpelonllingSuggelonstionCandidatelon]

  /**
   * High pelonrformancelon implelonmelonntation of elonquals melonthod that lelonvelonragelons:
   *  - Relonfelonrelonntial elonquality short circuit
   *  - Cachelond hashcodelon elonquality short circuit
   *  - Fielonld valuelons arelon only chelonckelond if thelon hashCodelons arelon elonqual to handlelon thelon unlikelonly caselon
   *    of a hashCodelon collision
   *  - Relonmoval of chelonck for `that` beloning an elonquals-compatiblelon delonscelonndant sincelon this class is final
   *
   * @notelon `candidatelon.canelonqual(this)` is not neloncelonssary beloncauselon this class is final
   * @selonelon [[http://www.artima.com/pins1elond/objelonct-elonquality.html Programming in Scala,
   *      Chaptelonr 28]] for discussion and delonsign.
   */
  ovelonrridelon delonf elonquals(that: Any): Boolelonan =
    that match {
      caselon candidatelon: SpelonllingSuggelonstionCandidatelon =>
        (
          (this elonq candidatelon)
            || ((hashCodelon == candidatelon.hashCodelon)
              && (id == candidatelon.id && telonxtRelonsult == candidatelon.telonxtRelonsult && spelonllingActionTypelon == candidatelon.spelonllingActionTypelon && originalQuelonry == candidatelon.originalQuelonry))
        )
      caselon _ =>
        falselon
    }

  /**
   * Lelonvelonragelon domain-speloncific constraints (selonelon notelons belonlow) to safelonly construct and cachelon thelon
   * hashCodelon as a val, such that it is instantiatelond oncelon on objelonct construction. This prelonvelonnts thelon
   * nelonelond to reloncomputelon thelon hashCodelon on elonach hashCodelon() invocation, which is thelon belonhavior of thelon
   * Scala compilelonr caselon class-gelonnelonratelond hashCodelon() sincelon it cannot makelon assumptions relongarding fielonld
   * objelonct mutability and hashCodelon implelonmelonntations.
   *
   * @notelon Caching thelon hashCodelon is only safelon if all of thelon fielonlds uselond to construct thelon hashCodelon
   *       arelon immutablelon. This includelons:
   *       - Inability to mutatelon thelon objelonct relonfelonrelonncelon on for an elonxisting instantiatelond candidatelon
   *       (i.elon. elonach fielonld is a val)
   *       - Inability to mutatelon thelon fielonld objelonct instancelon itselonlf (i.elon. elonach fielonld is an immutablelon
   *       - Inability to mutatelon thelon fielonld objelonct instancelon itselonlf (i.elon. elonach fielonld is an immutablelon
   *       data structurelon), assuming stablelon hashCodelon implelonmelonntations for thelonselon objeloncts
   *
   * @notelon In ordelonr for thelon hashCodelon to belon consistelonnt with objelonct elonquality, `##` must belon uselond for
   *       boxelond numelonric typelons and null. As such, always prelonfelonr `.##` ovelonr `.hashCodelon()`.
   */
  ovelonrridelon val hashCodelon: Int =
    31 * (
      31 * (
        31 * (
          id.##
        ) + telonxtRelonsult.##
      ) + spelonllingActionTypelon.##
    ) + originalQuelonry.##
}

objelonct SpelonllingSuggelonstionCandidatelon {
  delonf apply(
    id: String,
    telonxtRelonsult: TelonxtRelonsult,
    spelonllingActionTypelon: Option[SpelonllingActionTypelon],
    originalQuelonry: Option[String]
  ): SpelonllingSuggelonstionCandidatelon =
    nelonw SpelonllingSuggelonstionCandidatelon(id, telonxtRelonsult, spelonllingActionTypelon, originalQuelonry)
}
