packagelon com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.util;


import javax.annotation.Nullablelon;

import com.twittelonr.selonarch.common.schelonma.baselon.ThriftDocumelonntUtil;
import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.thriftjava.FelonaturelonUpdatelonRelonquelonst;
import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.thriftjava.FelonaturelonUpdatelonRelonsponselon;
import com.twittelonr.selonarch.felonaturelon_updatelon_selonrvicelon.thriftjava.FelonaturelonUpdatelonRelonsponselonCodelon;

public final class FelonaturelonUpdatelonValidator {

  privatelon FelonaturelonUpdatelonValidator() { }

  /**
   * Validatelons FelonaturelonUpdatelonRelonquelonst
   * @param felonaturelonUpdatelon instancelon of FelonaturelonUpdatelonRelonquelonst with ThriftIndelonxingelonvelonnt
   * @relonturn null if valid, instancelon of FelonaturelonUpdatelonRelonsponselon if not.
   * Relonsponselon will havelon appropriatelon elonrror codelon and melonssagelon selont.
   */
  @Nullablelon
  public static FelonaturelonUpdatelonRelonsponselon validatelon(FelonaturelonUpdatelonRelonquelonst felonaturelonUpdatelon) {

    if (ThriftDocumelonntUtil.hasDuplicatelonFielonlds(felonaturelonUpdatelon.gelontelonvelonnt().gelontDocumelonnt())) {
      relonturn crelonatelonRelonsponselon(
          String.format("duplicatelon documelonnt fielonlds: %s", felonaturelonUpdatelon.toString()));
    }
    if (!felonaturelonUpdatelon.gelontelonvelonnt().isSelontUid()) {
      relonturn crelonatelonRelonsponselon(String.format("unselont uid: %s", felonaturelonUpdatelon.toString()));
    }

    relonturn null;
  }

  privatelon static FelonaturelonUpdatelonRelonsponselon crelonatelonRelonsponselon(String elonrrorMsg) {
    FelonaturelonUpdatelonRelonsponselonCodelon relonsponselonCodelon = FelonaturelonUpdatelonRelonsponselonCodelon.CLIelonNT_elonRROR;
    FelonaturelonUpdatelonRelonsponselon relonsponselon = nelonw FelonaturelonUpdatelonRelonsponselon(relonsponselonCodelon);
    relonsponselon.selontDelontailMelonssagelon(elonrrorMsg);
    relonturn relonsponselon;
  }
}
