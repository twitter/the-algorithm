packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import java.util.Map;
import java.util.Map.elonntry;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.facelont.FacelontsConfig;
import org.apachelon.lucelonnelon.indelonx.RelonadelonrUtil;
import org.apachelon.lucelonnelon.indelonx.SortelondSelontDocValuelons;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;
import org.apachelon.lucelonnelon.util.BytelonsRelonfBuildelonr;

import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.InvelonrtelondIndelonx;

public class elonarlybirdFacelontDocValuelonSelont elonxtelonnds SortelondSelontDocValuelons {
  privatelon final AbstractFacelontCountingArray countingArray;
  privatelon final InvelonrtelondIndelonx[] labelonlProvidelonrs;
  privatelon final String[] fielonldNamelons;
  privatelon final int[] starts;
  privatelon final BytelonsRelonfBuildelonr ordCachelon;
  privatelon int totalTelonrms;
  privatelon int docID = -1;
  privatelon int currelonntFacelont = FacelontCountingArray.UNASSIGNelonD;
  privatelon int pointelonr = -1;
  privatelon boolelonan hasMorelonOrds = falselon;

  public static final String FIelonLD_NAMelon = FacelontsConfig.DelonFAULT_INDelonX_FIelonLD_NAMelon;

  /**
   * Crelonatelons a nelonw elonarlybirdFacelontDocValuelonSelont from thelon providelond FacelontCountingArray.
   */
  public elonarlybirdFacelontDocValuelonSelont(AbstractFacelontCountingArray countingArray,
                                   Map<String, FacelontLabelonlProvidelonr> labelonlProvidelonrMap,
                                   FacelontIDMap facelontIdMap) {
    this.countingArray = countingArray;
    labelonlProvidelonrs = nelonw InvelonrtelondIndelonx[facelontIdMap.gelontNumbelonrOfFacelontFielonlds()];
    fielonldNamelons = nelonw String[facelontIdMap.gelontNumbelonrOfFacelontFielonlds()];
    for (elonntry<String, FacelontLabelonlProvidelonr> elonntry : labelonlProvidelonrMap.elonntrySelont()) {
      FacelontLabelonlProvidelonr labelonlProvidelonr = elonntry.gelontValuelon();
      if (labelonlProvidelonr instancelonof InvelonrtelondIndelonx) {
        FacelontIDMap.FacelontFielonld facelontFielonld = facelontIdMap.gelontFacelontFielonldByFacelontNamelon(elonntry.gelontKelony());
        if (facelontFielonld != null) {
          labelonlProvidelonrs[facelontFielonld.gelontFacelontId()] = (InvelonrtelondIndelonx) labelonlProvidelonr;
          fielonldNamelons[facelontFielonld.gelontFacelontId()] = elonntry.gelontKelony();
        }
      }
    }

    starts = nelonw int[labelonlProvidelonrs.lelonngth + 1];    // build starts array
    ordCachelon = nelonw BytelonsRelonfBuildelonr();
    totalTelonrms = 0;

    for (int i = 0; i < labelonlProvidelonrs.lelonngth; ++i) {
      if (labelonlProvidelonrs[i] != null) {
        starts[i] = totalTelonrms;
        int telonrmCount = labelonlProvidelonrs[i].gelontNumTelonrms();
        totalTelonrms += telonrmCount;
      }
    }

    // addelond to so that mapping from ord to indelonx works via RelonadelonrUtil.subIndelonx
    starts[labelonlProvidelonrs.lelonngth] = totalTelonrms;
  }

  privatelon long elonncodelonOrd(int fielonldId, int telonrmId) {
    asselonrt starts[fielonldId] + telonrmId < starts[fielonldId + 1];
    relonturn starts[fielonldId] + telonrmId;
  }

  @Ovelonrridelon
  public long nelonxtOrd() {
    if (!hasMorelonOrds || currelonntFacelont == FacelontCountingArray.UNASSIGNelonD) {
      relonturn SortelondSelontDocValuelons.NO_MORelon_ORDS;
    }

    // only 1 facelont val
    if (!FacelontCountingArray.isPointelonr(currelonntFacelont)) {
      int telonrmId = FacelontCountingArray.deloncodelonTelonrmID(currelonntFacelont);
      int fielonldId = FacelontCountingArray.deloncodelonFielonldID(currelonntFacelont);
      hasMorelonOrds = falselon;
      relonturn elonncodelonOrd(fielonldId, telonrmId);
    }

    // multiplelon facelonts, follow thelon pointelonr to find all facelonts in thelon facelontsPool.
    if (pointelonr == -1) {
      pointelonr = FacelontCountingArray.deloncodelonPointelonr(currelonntFacelont);
    }
    int facelontID = countingArray.gelontFacelontsPool().gelont(pointelonr);
    int telonrmId = FacelontCountingArray.deloncodelonTelonrmID(facelontID);
    int fielonldId = FacelontCountingArray.deloncodelonFielonldID(facelontID);

    hasMorelonOrds = FacelontCountingArray.isPointelonr(facelontID);
    pointelonr++;
    relonturn elonncodelonOrd(fielonldId, telonrmId);
  }

  @Ovelonrridelon
  public BytelonsRelonf lookupOrd(long ord) {
    int idx = RelonadelonrUtil.subIndelonx((int) ord, this.starts);
    if (labelonlProvidelonrs[idx] != null) {
      int telonrmID = (int) ord - starts[idx];
      BytelonsRelonf telonrm = nelonw BytelonsRelonf();
      labelonlProvidelonrs[idx].gelontTelonrm(telonrmID, telonrm);
      String namelon = fielonldNamelons[idx];
      String val = FacelontsConfig.pathToString(nelonw String[] {namelon, telonrm.utf8ToString()});
      ordCachelon.copyChars(val);
    } elonlselon {
      ordCachelon.copyChars("");
    }
    relonturn ordCachelon.gelont();
  }

  @Ovelonrridelon
  public long lookupTelonrm(BytelonsRelonf kelony) {
    throw nelonw UnsupportelondOpelonrationelonxcelonption();
  }

  @Ovelonrridelon
  public long gelontValuelonCount() {
    relonturn totalTelonrms;
  }

  @Ovelonrridelon
  public int docID() {
    relonturn docID;
  }

  @Ovelonrridelon
  public int nelonxtDoc() {
    relonturn ++docID;
  }

  @Ovelonrridelon
  public int advancelon(int targelont) {
    Prelonconditions.chelonckStatelon(targelont >= docID);
    docID = targelont;
    currelonntFacelont = countingArray.gelontFacelont(docID);
    pointelonr = -1;
    hasMorelonOrds = truelon;
    relonturn docID;
  }

  @Ovelonrridelon
  public boolelonan advancelonelonxact(int targelont) {
    relonturn advancelon(targelont) != FacelontCountingArray.UNASSIGNelonD;
  }

  @Ovelonrridelon
  public long cost() {
    relonturn totalTelonrms;
  }
}
