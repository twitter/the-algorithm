packagelon com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr

/**
 * Mixelonr Pipelonlinelon idelonntifielonr
 *
 * @notelon This class should always relonmain elonffelonctivelonly `final`. If for any relonason thelon `selonalelond`
 *       modifielonr is relonmovelond, thelon elonquals() implelonmelonntation must belon updatelond in ordelonr to handlelon class
 *       inhelonritor elonquality (selonelon notelon on thelon elonquals melonthod belonlow)
 */
selonalelond abstract class MixelonrPipelonlinelonIdelonntifielonr(ovelonrridelon val namelon: String)
    elonxtelonnds ComponelonntIdelonntifielonr("MixelonrPipelonlinelon", namelon) {

  /**
   * @inhelonritdoc
   */
  ovelonrridelon delonf canelonqual(that: Any): Boolelonan = that.isInstancelonOf[MixelonrPipelonlinelonIdelonntifielonr]

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
      caselon idelonntifielonr: MixelonrPipelonlinelonIdelonntifielonr =>
        // Notelon idelonntifielonr.canelonqual(this) is not neloncelonssary beloncauselon this class is elonffelonctivelonly final
        ((this elonq idelonntifielonr)
          || ((hashCodelon == idelonntifielonr.hashCodelon) && ((componelonntTypelon == idelonntifielonr.componelonntTypelon) && (namelon == idelonntifielonr.namelon))))
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
   *       - Inability to mutatelon thelon objelonct relonfelonrelonncelon on for an elonxisting instantiatelond idelonntifielonr
   *       (i.elon. elonach fielonld is a val)
   *       - Inability to mutatelon thelon fielonld objelonct instancelon itselonlf (i.elon. elonach fielonld is an immutablelon
   *       - Inability to mutatelon thelon fielonld objelonct instancelon itselonlf (i.elon. elonach fielonld is an immutablelon
   *       data structurelon), assuming stablelon hashCodelon implelonmelonntations for thelonselon objeloncts
   *
   * @notelon In ordelonr for thelon hashCodelon to belon consistelonnt with objelonct elonquality, `##` must belon uselond for
   *       boxelond numelonric typelons and null. As such, always prelonfelonr `.##` ovelonr `.hashCodelon()`.
   */
  ovelonrridelon val hashCodelon: Int = 31 * componelonntTypelon.## + namelon.##
}

objelonct MixelonrPipelonlinelonIdelonntifielonr {
  delonf apply(namelon: String)(implicit sourcelonFilelon: sourceloncodelon.Filelon): MixelonrPipelonlinelonIdelonntifielonr = {
    if (ComponelonntIdelonntifielonr.isValidNamelon(namelon))
      nelonw MixelonrPipelonlinelonIdelonntifielonr(namelon) {
        ovelonrridelon val filelon: sourceloncodelon.Filelon = sourcelonFilelon
      }
    elonlselon
      throw nelonw IllelongalArgumelonntelonxcelonption(s"Illelongal MixelonrPipelonlinelonIdelonntifielonr: $namelon")
  }
}
