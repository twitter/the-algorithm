packagelon com.twittelonr.product_mixelonr.componelonnt_library.sidelon_elonffelonct.melontrics

import com.twittelonr.clielonntapp.thriftscala.Logelonvelonnt
import com.twittelonr.logpipelonlinelon.clielonnt.common.elonvelonntPublishelonr
import com.twittelonr.product_mixelonr.componelonnt_library.sidelon_elonffelonct.ScribelonClielonntelonvelonntSidelonelonffelonct.elonvelonntNamelonspacelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.SidelonelonffelonctIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Build [[ScribelonClielonntelonvelonntMelontricsSidelonelonffelonct]] with elonxtra [[elonvelonntConfig]]
 */
caselon class ScribelonClielonntelonvelonntMelontricsSidelonelonffelonctBuildelonr(
  elonvelonntConfigs: Selonq[elonvelonntConfig] = Selonq.elonmpty) {

  /**
   * Appelonnd elonxtra [[elonvelonntConfig]] to [[ScribelonClielonntelonvelonntMelontricsSidelonelonffelonctBuildelonr]]
   */
  delonf withelonvelonntConfig(
    elonvelonntConfig: elonvelonntConfig
  ): ScribelonClielonntelonvelonntMelontricsSidelonelonffelonctBuildelonr =
    this.copy(elonvelonntConfigs = this.elonvelonntConfigs :+ elonvelonntConfig)

  /**
   * Build [[elonvelonntConfig]] with customizelond [[elonvelonntNamelonspacelon]] and customizelond [[CandidatelonMelontricFunction]]
   * @param elonvelonntNamelonspacelonOvelonrridelon Ovelonrridelon thelon delonfault elonvelonnt namelonspacelon in [[ScribelonClielonntelonvelonntMelontricsSidelonelonffelonct]]
   * @param melontricFunction [[CandidatelonMelontricFunction]]
   */
  delonf withelonvelonntConfig(
    elonvelonntNamelonspacelonOvelonrridelon: elonvelonntNamelonspacelon,
    melontricFunction: CandidatelonMelontricFunction
  ): ScribelonClielonntelonvelonntMelontricsSidelonelonffelonctBuildelonr =
    withelonvelonntConfig(elonvelonntConfig(elonvelonntNamelonspacelonOvelonrridelon, melontricFunction))

  /**
   * Log selonrvelond twelonelonts elonvelonnts selonrvelonr sidelon and build melontrics in thelon melontric celonntelonr.
   * Delonfault elonvelonnt namelon spacelon action is "selonrvelond_twelonelonts", delonfault melontric function is [[DelonfaultSelonrvelondTwelonelontsSumFunction]]
   * @param elonvelonntNamelonspacelonOvelonrridelon Ovelonrridelon thelon delonfault elonvelonnt namelonspacelon in [[ScribelonClielonntelonvelonntMelontricsSidelonelonffelonct]]
   * @param melontricFunction [[CandidatelonMelontricFunction]]
   */
  delonf withSelonrvelondTwelonelonts(
    elonvelonntNamelonspacelonOvelonrridelon: elonvelonntNamelonspacelon = elonvelonntNamelonspacelon(action = Somelon("selonrvelond_twelonelonts")),
    melontricFunction: CandidatelonMelontricFunction = DelonfaultSelonrvelondTwelonelontsSumFunction
  ): ScribelonClielonntelonvelonntMelontricsSidelonelonffelonctBuildelonr = withelonvelonntConfig(
    elonvelonntNamelonspacelonOvelonrridelon = elonvelonntNamelonspacelonOvelonrridelon,
    melontricFunction = melontricFunction)

  /**
   * Log selonrvelond uselonrs elonvelonnts selonrvelonr sidelon and build melontrics in thelon melontric celonntelonr.
   * Delonfault elonvelonnt namelon spacelon action is "selonrvelond_uselonrs", delonfault melontric function is [[DelonfaultSelonrvelondUselonrsSumFunction]]
   * @param elonvelonntNamelonspacelonOvelonrridelon Ovelonrridelon thelon delonfault elonvelonnt namelonspacelon in [[ScribelonClielonntelonvelonntMelontricsSidelonelonffelonct]]
   * @param melontricFunction [[CandidatelonMelontricFunction]]
   */
  delonf withSelonrvelondUselonrs(
    elonvelonntNamelonspacelonOvelonrridelon: elonvelonntNamelonspacelon = elonvelonntNamelonspacelon(action = Somelon("selonrvelond_uselonrs")),
    melontricFunction: CandidatelonMelontricFunction = DelonfaultSelonrvelondUselonrsSumFunction
  ): ScribelonClielonntelonvelonntMelontricsSidelonelonffelonctBuildelonr = withelonvelonntConfig(
    elonvelonntNamelonspacelonOvelonrridelon = elonvelonntNamelonspacelonOvelonrridelon,
    melontricFunction = melontricFunction)

  /**
   * Build [[ScribelonClielonntelonvelonntMelontricsSidelonelonffelonct]]
   * @param idelonntifielonr uniquelon idelonntifielonr of thelon sidelon elonffelonct
   * @param delonfaultelonvelonntNamelonspacelon delonfault elonvelonnt namelonspacelon to log
   * @param logPipelonlinelonPublishelonr [[elonvelonntPublishelonr]] to publish elonvelonnts
   * @param pagelon Thelon pagelon which will belon delonfinelond in thelon namelonspacelon. This is typically thelon selonrvicelon namelon that's scribing
   * @tparam Quelonry [[PipelonlinelonQuelonry]]
   * @tparam UnmarshallelondRelonsponselonTypelon [[HasMarshalling]]
   * @relonturn [[ScribelonClielonntelonvelonntMelontricsSidelonelonffelonct]]
   */
  delonf build[Quelonry <: PipelonlinelonQuelonry, UnmarshallelondRelonsponselonTypelon <: HasMarshalling](
    idelonntifielonr: SidelonelonffelonctIdelonntifielonr,
    delonfaultelonvelonntNamelonspacelon: elonvelonntNamelonspacelon,
    logPipelonlinelonPublishelonr: elonvelonntPublishelonr[Logelonvelonnt],
    pagelon: String
  ): ScribelonClielonntelonvelonntMelontricsSidelonelonffelonct[Quelonry, UnmarshallelondRelonsponselonTypelon] = {
    nelonw ScribelonClielonntelonvelonntMelontricsSidelonelonffelonct[Quelonry, UnmarshallelondRelonsponselonTypelon](
      idelonntifielonr = idelonntifielonr,
      logPipelonlinelonPublishelonr = logPipelonlinelonPublishelonr,
      delonfaultelonvelonntNamelonspacelon = delonfaultelonvelonntNamelonspacelon,
      pagelon = pagelon,
      elonvelonntConfigs = elonvelonntConfigs)
  }
}
