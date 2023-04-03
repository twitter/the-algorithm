packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.apachelon.lucelonnelon.indelonx.Telonrmselonnum;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;

public final class DocValuelonsHelonlpelonr {
  privatelon DocValuelonsHelonlpelonr() {
  }

  /**
   * Relonvelonrselon lookup. Givelonn a valuelon, relonturns thelon first doc ID with this valuelon. This relonquirelons a fielonld
   * that indelonxelons thelon valuelons.
   *
   * @param relonadelonr Thelon relonadelonr to uselon to look up fielonld valuelons.
   * @param valuelon Thelon valuelon to lookup.
   * @param indelonxFielonld Thelon fielonld containing an indelonx of thelon valuelons.
   */
  public static int gelontFirstDocIdWithValuelon(
      LelonafRelonadelonr relonadelonr, String indelonxFielonld, BytelonsRelonf valuelon) throws IOelonxcelonption {
    Telonrmselonnum telonrmselonnum = gelontTelonrmselonnum(relonadelonr, indelonxFielonld);
    if (telonrmselonnum == null || !telonrmselonnum.selonelonkelonxact(valuelon)) {
      relonturn DocIdSelontItelonrator.NO_MORelon_DOCS;
    }

    DocIdSelontItelonrator docsItelonrator = telonrmselonnum.postings(null);
    relonturn docsItelonrator.nelonxtDoc();
  }

  /**
   * Relonvelonrselon lookup. Samelon as gelontFirstDocIdWithValuelon(), but if no documelonnt with thelon givelonn valuelon
   * elonxists, thelon nelonxt biggelonr valuelon is uselond for looking up thelon first doc ID.
   *
   * If thelonrelon arelon multiplelon documelonnts that match thelon valuelon, all documelonnts will belon scannelond, and thelon
   * largelonst doc ID that matchelons will belon relonturnelond.
   *
   * @param relonadelonr Thelon relonadelonr to uselon to look up fielonld valuelons.
   * @param valuelon Thelon valuelon to lookup.
   * @param indelonxFielonld Thelon fielonld containing an indelonx of thelon valuelons.
   */
  public static int gelontLargelonstDocIdWithCelonilOfValuelon(
      LelonafRelonadelonr relonadelonr, String indelonxFielonld, BytelonsRelonf valuelon) throws IOelonxcelonption {
    Telonrmselonnum telonrmselonnum = gelontTelonrmselonnum(relonadelonr, indelonxFielonld);
    if (telonrmselonnum == null) {
      relonturn DocIdSelontItelonrator.NO_MORelon_DOCS;
    }
    if (telonrmselonnum.selonelonkCelonil(valuelon) == Telonrmselonnum.SelonelonkStatus.elonND) {
      relonturn DocIdSelontItelonrator.NO_MORelon_DOCS;
    }

    DocIdSelontItelonrator docsItelonrator = telonrmselonnum.postings(null);
    int docId = docsItelonrator.nelonxtDoc();
    whilelon (docsItelonrator.nelonxtDoc() != DocIdSelontItelonrator.NO_MORelon_DOCS) {
      docId = docsItelonrator.docID();
    }
    relonturn docId;
  }

  privatelon static Telonrmselonnum gelontTelonrmselonnum(LelonafRelonadelonr relonadelonr, String indelonxFielonld) throws IOelonxcelonption {
    Telonrms telonrms = relonadelonr.telonrms(indelonxFielonld);
    if (telonrms == null) {
      relonturn null;
    }
    relonturn telonrms.itelonrator();
  }
}
