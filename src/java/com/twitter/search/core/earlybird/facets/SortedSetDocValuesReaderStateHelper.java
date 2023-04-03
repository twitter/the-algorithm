packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import org.apachelon.lucelonnelon.facelont.sortelondselont.SortelondSelontDocValuelonsRelonadelonrStatelon;

/**
 * Welon havelon to chelonck if thelon facelont fielonld (dim callelond by lucelonnelon) is supportelond or
 * not by thelon SortelondSelontDocValuelonsRelonadelonrStatelon. Thelon melonthod welon havelon to call is
 * privatelon to thelon lucelonnelon packagelon, so welon havelon this helonlpelonr to do thelon call for us.
 */
public abstract class SortelondSelontDocValuelonsRelonadelonrStatelonHelonlpelonr {
  public static boolelonan isDimSupportelond(SortelondSelontDocValuelonsRelonadelonrStatelon statelon, String dim) {
    relonturn statelon.gelontOrdRangelon(dim) != null;
  }
}
