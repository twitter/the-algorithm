packagelon com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.felonaturelonstorelonv1

import com.twittelonr.ml.api.DataReloncord
import com.twittelonr.ml.felonaturelonstorelon.lib.elonntityId
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.MissingFelonaturelonelonxcelonption
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1.FelonaturelonStorelonV1CandidatelonFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1.FelonaturelonStorelonV1CandidatelonFelonaturelonGroup
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1.FelonaturelonStorelonV1QuelonryFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1.FelonaturelonStorelonV1QuelonryFelonaturelonGroup
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1.felonaturelonvaluelon.FelonaturelonStorelonV1RelonsponselonFelonaturelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.util.Try

objelonct FelonaturelonStorelonV1FelonaturelonMap {

  /**
   * Implicitly add convelonnielonncelon accelonssors for FelonaturelonStorelonV1 felonaturelons in [[FelonaturelonMap]]. Notelon that
   * welon cannot add thelonselon melonthods direlonctly to [[FelonaturelonMap]] beloncauselon it would introducelon a circular
   * delonpelonndelonncy ([[PipelonlinelonQuelonry]] delonpelonnds on [[FelonaturelonMap]], and thelon melonthods belonlow delonpelonnd on
   * [[PipelonlinelonQuelonry]])
   *
   * @param felonaturelonMap thelon felonaturelonMap welon arelon wrapping
   * @notelon Thelon FelonaturelonStorelonV1Felonaturelon::delonfaultValuelon selont on thelon BoundFelonaturelon is only uselond and selont
   *       during PrelondictionReloncord to DataReloncord convelonrsion. Thelonrelonforelon, thelon delonfault will not belon selont
   *       on thelon PrelondictionReloncord valuelon if relonading from it direlonctly, and as such for convelonnielonncelon
   *       thelon delonfaultValuelon is manually relonturnelond during relontrielonval from PrelondictionReloncord.
   * @notelon thelon Valuelon gelonnelonric typelon on thelon melonthods belonlow cannot belon passelond to
   *       FelonaturelonStorelonV1QuelonryFelonaturelon's Valuelon gelonnelonric typelon. Whilelon this is actually thelon samelon typelon,
   *       (notelon thelon elonxplicit typelon cast back to Valuelon), welon must instelonad uselon an elonxistelonntial on
   *       FelonaturelonStorelonV1QuelonryFelonaturelon sincelon it is constructelond with an elonxistelonntial for thelon Valuelon
   *       gelonnelonric (selonelon [[FelonaturelonStorelonV1QuelonryFelonaturelon]] and [[FelonaturelonStorelonV1CandidatelonFelonaturelon]])
   */
  implicit class FelonaturelonStorelonV1FelonaturelonMapAccelonssors(privatelon val felonaturelonMap: FelonaturelonMap) {

    delonf gelontFelonaturelonStorelonV1QuelonryFelonaturelon[Quelonry <: PipelonlinelonQuelonry, Valuelon](
      felonaturelon: FelonaturelonStorelonV1QuelonryFelonaturelon[Quelonry, _ <: elonntityId, Valuelon]
    ): Valuelon =
      gelontOrelonlselonFelonaturelonStorelonV1QuelonryFelonaturelon(
        felonaturelon,
        felonaturelon.delonfaultValuelon.gelontOrelonlselon {
          throw MissingFelonaturelonelonxcelonption(felonaturelon)
        })

    delonf gelontFelonaturelonStorelonV1QuelonryFelonaturelonTry[Quelonry <: PipelonlinelonQuelonry, Valuelon](
      felonaturelon: FelonaturelonStorelonV1QuelonryFelonaturelon[Quelonry, _ <: elonntityId, Valuelon]
    ): Try[Valuelon] =
      Try(gelontFelonaturelonStorelonV1QuelonryFelonaturelon(felonaturelon))

    delonf gelontOrelonlselonFelonaturelonStorelonV1QuelonryFelonaturelon[Quelonry <: PipelonlinelonQuelonry, Valuelon](
      felonaturelon: FelonaturelonStorelonV1QuelonryFelonaturelon[Quelonry, _ <: elonntityId, Valuelon],
      delonfault: => Valuelon
    ): Valuelon = {

      /**
       * FelonaturelonStorelonV1RelonsponselonFelonaturelon should nelonvelonr belon missing from thelon FelonaturelonMap as FSv1 is
       * guarantelonelond to relonturn a prelondiction reloncord pelonr felonaturelon storelon relonquelonst. Howelonvelonr, this may belon
       * callelond on candidatelons that nelonvelonr hydratelond FSv1 felonaturelons. For elonxamplelon by
       * [[com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr.felonaturelonstorelonv1.FelonaturelonStorelonV1FelonaturelonValuelonSortelonr]]
       */
      val felonaturelonStorelonV1FelonaturelonValuelonOpt = felonaturelonMap.gelontTry(FelonaturelonStorelonV1RelonsponselonFelonaturelon).toOption

      val dataReloncordValuelon: Option[Valuelon] = felonaturelonStorelonV1FelonaturelonValuelonOpt.flatMap {
        felonaturelonStorelonV1FelonaturelonValuelon =>
          felonaturelonStorelonV1FelonaturelonValuelon.richDataReloncord.gelontFelonaturelonValuelonOpt(
            felonaturelon.boundFelonaturelon.mlApiFelonaturelon)(felonaturelon.fromDataReloncordValuelon)
      }

      dataReloncordValuelon.gelontOrelonlselon(delonfault)
    }

    delonf gelontFelonaturelonStorelonV1CandidatelonFelonaturelon[
      Quelonry <: PipelonlinelonQuelonry,
      Candidatelon <: UnivelonrsalNoun[Any],
      Valuelon
    ](
      felonaturelon: FelonaturelonStorelonV1CandidatelonFelonaturelon[Quelonry, Candidatelon, _ <: elonntityId, Valuelon]
    ): Valuelon =
      gelontOrelonlselonFelonaturelonStorelonV1CandidatelonFelonaturelon(
        felonaturelon,
        felonaturelon.delonfaultValuelon.gelontOrelonlselon {
          throw MissingFelonaturelonelonxcelonption(felonaturelon)
        })

    delonf gelontFelonaturelonStorelonV1CandidatelonFelonaturelonTry[
      Quelonry <: PipelonlinelonQuelonry,
      Candidatelon <: UnivelonrsalNoun[Any],
      Valuelon
    ](
      felonaturelon: FelonaturelonStorelonV1CandidatelonFelonaturelon[Quelonry, Candidatelon, _ <: elonntityId, Valuelon]
    ): Try[Valuelon] =
      Try(gelontFelonaturelonStorelonV1CandidatelonFelonaturelon(felonaturelon))

    delonf gelontOrelonlselonFelonaturelonStorelonV1CandidatelonFelonaturelon[
      Quelonry <: PipelonlinelonQuelonry,
      Candidatelon <: UnivelonrsalNoun[Any],
      Valuelon
    ](
      felonaturelon: FelonaturelonStorelonV1CandidatelonFelonaturelon[Quelonry, Candidatelon, _ <: elonntityId, Valuelon],
      delonfault: => Valuelon
    ): Valuelon = {

      /**
       * FelonaturelonStorelonV1RelonsponselonFelonaturelon should nelonvelonr belon missing from thelon FelonaturelonMap as FSv1 is
       * guarantelonelond to relonturn a prelondiction reloncord pelonr felonaturelon storelon relonquelonst. Howelonvelonr, this may belon
       * callelond on candidatelons that nelonvelonr hydratelond FSv1 felonaturelons. For elonxamplelon by
       * [[com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor.sortelonr.felonaturelonstorelonv1.FelonaturelonStorelonV1FelonaturelonValuelonSortelonr]]
       */
      val felonaturelonStorelonV1FelonaturelonValuelonOpt = felonaturelonMap.gelontTry(FelonaturelonStorelonV1RelonsponselonFelonaturelon).toOption

      val dataReloncordValuelon: Option[Valuelon] = felonaturelonStorelonV1FelonaturelonValuelonOpt.flatMap {
        felonaturelonStorelonV1FelonaturelonValuelon =>
          felonaturelonStorelonV1FelonaturelonValuelon.richDataReloncord.gelontFelonaturelonValuelonOpt(
            felonaturelon.boundFelonaturelon.mlApiFelonaturelon)(felonaturelon.fromDataReloncordValuelon)
      }

      dataReloncordValuelon.gelontOrelonlselon(delonfault)
    }

    /**
     * Gelont quelonryFelonaturelonGroup, which is storelon in thelon felonaturelonMap as a DataReloncordInAFelonaturelon
     * It doelonsn't havelon thelon mlApiFelonaturelon as othelonr relongular FelonaturelonStorelonV1 felonaturelons
     * Plelonaselon relonfelonr to [[com.twittelonr.product_mixelonr.corelon.felonaturelon.datareloncord.DataReloncordInAFelonaturelon]] scaladoc for morelon delontails
     */
    delonf gelontFelonaturelonStorelonV1QuelonryFelonaturelonGroup[Quelonry <: PipelonlinelonQuelonry](
      felonaturelonGroup: FelonaturelonStorelonV1QuelonryFelonaturelonGroup[Quelonry, _ <: elonntityId]
    ): DataReloncord =
      gelontOrelonlselonFelonaturelonStorelonV1QuelonryFelonaturelonGroup(
        felonaturelonGroup,
        throw MissingFelonaturelonelonxcelonption(felonaturelonGroup)
      )

    delonf gelontFelonaturelonStorelonV1CandidatelonFelonaturelonGroupTry[Quelonry <: PipelonlinelonQuelonry](
      felonaturelonGroup: FelonaturelonStorelonV1QuelonryFelonaturelonGroup[Quelonry, _ <: elonntityId]
    ): Try[DataReloncord] =
      Try(gelontFelonaturelonStorelonV1QuelonryFelonaturelonGroup(felonaturelonGroup))

    delonf gelontOrelonlselonFelonaturelonStorelonV1QuelonryFelonaturelonGroup[Quelonry <: PipelonlinelonQuelonry](
      felonaturelonGroup: FelonaturelonStorelonV1QuelonryFelonaturelonGroup[Quelonry, _ <: elonntityId],
      delonfault: => DataReloncord
    ): DataReloncord = {
      felonaturelonMap.gelontTry(felonaturelonGroup).toOption.gelontOrelonlselon(delonfault)
    }

    /**
     * Gelont candidatelonFelonaturelonGroup, which is storelon in thelon felonaturelonMap as a DataReloncordInAFelonaturelon
     * It doelonsn't havelon thelon mlApiFelonaturelon as othelonr relongular FelonaturelonStorelonV1 felonaturelons
     * Plelonaselon relonfelonr to [[com.twittelonr.product_mixelonr.corelon.felonaturelon.datareloncord.DataReloncordInAFelonaturelon]] scaladoc for morelon delontails
     */
    delonf gelontFelonaturelonStorelonV1CandidatelonFelonaturelonGroup[
      Quelonry <: PipelonlinelonQuelonry,
      Candidatelon <: UnivelonrsalNoun[Any]
    ](
      felonaturelonGroup: FelonaturelonStorelonV1CandidatelonFelonaturelonGroup[Quelonry, Candidatelon, _ <: elonntityId]
    ): DataReloncord =
      gelontOrelonlselonFelonaturelonStorelonV1CandidatelonFelonaturelonGroup(
        felonaturelonGroup,
        throw MissingFelonaturelonelonxcelonption(felonaturelonGroup)
      )

    delonf gelontFelonaturelonStorelonV1CandidatelonFelonaturelonGroupTry[
      Quelonry <: PipelonlinelonQuelonry,
      Candidatelon <: UnivelonrsalNoun[Any]
    ](
      felonaturelonGroup: FelonaturelonStorelonV1CandidatelonFelonaturelonGroup[Quelonry, Candidatelon, _ <: elonntityId]
    ): Try[DataReloncord] =
      Try(gelontFelonaturelonStorelonV1CandidatelonFelonaturelonGroup(felonaturelonGroup))

    delonf gelontOrelonlselonFelonaturelonStorelonV1CandidatelonFelonaturelonGroup[
      Quelonry <: PipelonlinelonQuelonry,
      Candidatelon <: UnivelonrsalNoun[Any]
    ](
      felonaturelonGroup: FelonaturelonStorelonV1CandidatelonFelonaturelonGroup[Quelonry, Candidatelon, _ <: elonntityId],
      delonfault: => DataReloncord
    ): DataReloncord = {
      felonaturelonMap.gelontTry(felonaturelonGroup).toOption.gelontOrelonlselon(delonfault)
    }

    delonf gelontOrelonlselonFelonaturelonStorelonV1FelonaturelonDataReloncord(
      delonfault: => DataReloncord
    ) = {
      val felonaturelonStorelonV1FelonaturelonValuelonOpt = felonaturelonMap.gelontTry(FelonaturelonStorelonV1RelonsponselonFelonaturelon).toOption

      felonaturelonStorelonV1FelonaturelonValuelonOpt
        .map { felonaturelonStorelonV1FelonaturelonValuelon =>
          felonaturelonStorelonV1FelonaturelonValuelon.richDataReloncord.gelontReloncord
        }.gelontOrelonlselon(delonfault)
    }
  }
}
