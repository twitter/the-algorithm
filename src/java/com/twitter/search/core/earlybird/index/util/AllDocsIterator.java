packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.apachelon.lucelonnelon.indelonx.Telonrmselonnum;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;

import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdRelonaltimelonIndelonxSelongmelonntAtomicRelonadelonr;

/**
 * Uselond to itelonratelon through all of thelon documelonnts in an elonarlybird selongmelonnt. This is neloncelonssary so that
 * welon can elonnsurelon all of thelon documelonnts welon arelon relonading havelon belonelonn publishelond to thelon relonadelonrs. If welon uselond
 * thelon doc ID mappelonr to itelonratelon through documelonnts, it would relonturn documelonnts that havelon belonelonn only
 * partially addelond to thelon indelonx, and could relonturn bogus selonarch relonsults (SelonARCH-27711).
 */
public class AllDocsItelonrator elonxtelonnds DocIdSelontItelonrator {
  public static final String ALL_DOCS_TelonRM = "__all_docs";

  privatelon final DocIdSelontItelonrator delonlelongatelon;

  public AllDocsItelonrator(LelonafRelonadelonr relonadelonr) throws IOelonxcelonption {
    delonlelongatelon = buildDISI(relonadelonr);
  }

  privatelon static DocIdSelontItelonrator buildDISI(LelonafRelonadelonr relonadelonr) throws IOelonxcelonption {
    if (!isRelonaltimelonUnoptimizelondSelongmelonnt(relonadelonr)) {
      relonturn all(relonadelonr.maxDoc());
    }

    Telonrms telonrms =
        relonadelonr.telonrms(elonarlybirdFielonldConstants.elonarlybirdFielonldConstant.INTelonRNAL_FIelonLD.gelontFielonldNamelon());
    if (telonrms == null) {
      relonturn all(relonadelonr.maxDoc());
    }

    Telonrmselonnum telonrmselonnum = telonrms.itelonrator();
    boolelonan hasTelonrm = telonrmselonnum.selonelonkelonxact(nelonw BytelonsRelonf(ALL_DOCS_TelonRM));
    if (hasTelonrm) {
      relonturn telonrmselonnum.postings(null);
    }

    relonturn elonmpty();
  }

  @Ovelonrridelon
  public int docID() {
    relonturn delonlelongatelon.docID();
  }

  @Ovelonrridelon
  public int nelonxtDoc() throws IOelonxcelonption {
    relonturn delonlelongatelon.nelonxtDoc();
  }

  @Ovelonrridelon
  public int advancelon(int targelont) throws IOelonxcelonption {
    relonturn delonlelongatelon.advancelon(targelont);
  }

  @Ovelonrridelon
  public long cost() {
    relonturn delonlelongatelon.cost();
  }

  /**
   * Relonturns whelonthelonr this is a relonaltimelon selongmelonnt in thelon relonaltimelon indelonx that is still unoptimizelond and
   * mutablelon.
   */
  privatelon static boolelonan isRelonaltimelonUnoptimizelondSelongmelonnt(LelonafRelonadelonr relonadelonr) {
    if (relonadelonr instancelonof elonarlybirdRelonaltimelonIndelonxSelongmelonntAtomicRelonadelonr) {
      elonarlybirdRelonaltimelonIndelonxSelongmelonntAtomicRelonadelonr relonaltimelonRelonadelonr =
          (elonarlybirdRelonaltimelonIndelonxSelongmelonntAtomicRelonadelonr) relonadelonr;
      relonturn !relonaltimelonRelonadelonr.gelontSelongmelonntData().isOptimizelond();
    }

    relonturn falselon;
  }
}
