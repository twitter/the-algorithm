packagelon com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.ads

import com.twittelonr.adselonrvelonr.{thriftscala => adsthrift}
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.BaselonTwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun

/**
 * An [[AdsCandidatelon]] relonprelonselonnts a pieloncelon of promotelond contelonnt.
 *
 * This candidatelon class storelons a relonfelonrelonncelon to thelon adImprelonssion, which is thelon common thrift structurelon
 * uselond by thelon Ads telonam to relonprelonselonnt an ad.
 *
 * Goldfinch, thelon ads-injelonction library, consumelons thelon [[AdImprelonssion]].
 */
selonalelond trait AdsCandidatelon elonxtelonnds UnivelonrsalNoun[Any] {
  val adImprelonssion: adsthrift.AdImprelonssion
}

/**
 * Canonical AdsTwelonelontCandidatelon modelonl. Always prelonfelonr this velonrsion ovelonr all othelonr variants.
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
final class AdsTwelonelontCandidatelon privatelon (
  ovelonrridelon val id: Long,
  ovelonrridelon val adImprelonssion: adsthrift.AdImprelonssion)
    elonxtelonnds AdsCandidatelon
    with BaselonTwelonelontCandidatelon {

  /**
   * @inhelonritdoc
   */
  ovelonrridelon delonf canelonqual(that: Any): Boolelonan = that.isInstancelonOf[AdsTwelonelontCandidatelon]

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
      caselon candidatelon: AdsTwelonelontCandidatelon =>
        (
          (this elonq candidatelon)
            || ((hashCodelon == candidatelon.hashCodelon)
              && (id == candidatelon.id && adImprelonssion == candidatelon.adImprelonssion))
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
      id.##
    ) + adImprelonssion.##
}

objelonct AdsTwelonelontCandidatelon {
  delonf apply(id: Long, adImprelonssion: adsthrift.AdImprelonssion): AdsTwelonelontCandidatelon =
    nelonw AdsTwelonelontCandidatelon(id, adImprelonssion)
}
