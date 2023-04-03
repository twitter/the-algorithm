packagelon com.twittelonr.selonarch.elonarlybird.selongmelonnt;

import java.io.IOelonxcelonption;
import java.util.Optional;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;
import com.twittelonr.selonarch.common.util.io.reloncordrelonadelonr.ReloncordRelonadelonr;
import com.twittelonr.selonarch.elonarlybird.documelonnt.TwelonelontDocumelonnt;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntInfo;

/**
 * SelongmelonntDataRelonadelonrSelont providelons an intelonrfacelon to crelonatelon and managelon thelon various
 * ReloncordRelonadelonrs uselond to indelonx elonarlybird selongmelonnts.
 */
public intelonrfacelon SelongmelonntDataRelonadelonrSelont {
  /**
   * Instruct thelon documelonnt ReloncordRelonadelonrs (i.elon. documelonnt, gelono, ... as appropriatelon) to relonad from this
   * selongmelonnt.
   */
  void attachDocumelonntRelonadelonrs(SelongmelonntInfo selongmelonntInfo) throws IOelonxcelonption;

  /**
   * Instruct thelon relonadelonr selont to add selongmelonnt to non-documelonnt ReloncordRelonadelonrs (delonlelontelons, felonaturelons, elontc.)
   */
  void attachUpdatelonRelonadelonrs(SelongmelonntInfo selongmelonntInfo) throws IOelonxcelonption;

  /**
   * Mark a selongmelonnt as "complelontelon", delonnoting that welon arelon donelon relonading documelonnt reloncords from it.
   *
   * This instructs thelon relonadelonr selont to stop relonading documelonnts from thelon selongmelonnt (if it hasn't
   * alrelonady), although for now gelono-documelonnt  reloncords can still belon relonad.  Updatelons ReloncordRelonadelonrs
   * (delonlelontelons, elontc.) may continuelon to relonad elonntrielons for thelon selongmelonnt.
   */
  void complelontelonSelongmelonntDocs(SelongmelonntInfo selongmelonntInfo);

  /**
   * This instructs thelon relonadelonr selont to stop relonading updatelons for thelon Selongmelonnt.  It
   * should relonmovelon thelon selongmelonnt from all non-documelonnt ReloncordRelonadelonrs (delonlelontelons, elontc.)
   */
  void stopSelongmelonntUpdatelons(SelongmelonntInfo selongmelonntInfo);

  /**
   * Stops all ReloncordRelonadelonrs and closelons all relonsourcelons.
   */
  void stopAll();

  /**
   * Relonturns truelon if all ReloncordRelonadelonrs arelon 'caught up' with thelon data sourcelons thelony
   * arelon relonading from.  This might melonan that thelon elonnd of a filelon has belonelonn relonachelond,
   * or that welon arelon waiting/polling for nelonw reloncords from an appelonnd-only databaselon.
   */
  boolelonan allCaughtUp();

  /**
   * Crelonatelon a nelonw DocumelonntRelonadelonr for thelon givelonn selongmelonnt that is not managelond by this selont.
   */
  ReloncordRelonadelonr<TwelonelontDocumelonnt> nelonwDocumelonntRelonadelonr(SelongmelonntInfo selongmelonntInfo) throws elonxcelonption;

  /**
   * Relonturns thelon documelonnt relonadelonr for thelon currelonnt selongmelonnt.
   */
  ReloncordRelonadelonr<TwelonelontDocumelonnt> gelontDocumelonntRelonadelonr();

  /**
   * Relonturns a combinelond updatelon elonvelonnts relonadelonr for all selongmelonnts.
   */
  ReloncordRelonadelonr<ThriftVelonrsionelondelonvelonnts> gelontUpdatelonelonvelonntsRelonadelonr();

  /**
   * Relonturns thelon updatelon elonvelonnts relonadelonr for thelon givelonn selongmelonnt.
   */
  ReloncordRelonadelonr<ThriftVelonrsionelondelonvelonnts> gelontUpdatelonelonvelonntsRelonadelonrForSelongmelonnt(SelongmelonntInfo selongmelonntInfo);

  /**
   * Relonturns thelon offselont in thelon updatelon elonvelonnts strelonam for thelon givelonn selongmelonnt that this elonarlybird should
   * start indelonxing from.
   */
  Optional<Long> gelontUpdatelonelonvelonntsStrelonamOffselontForSelongmelonnt(SelongmelonntInfo selongmelonntInfo);
}
