packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import java.util.Map;
import java.util.Selont;

import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontIDMap;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontLabelonlProvidelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontTelonrmCollelonctor;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultelonxtraMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadata;

public abstract class AbstractFacelontTelonrmCollelonctor implelonmelonnts FacelontTelonrmCollelonctor {
  privatelon Map<String, FacelontLabelonlProvidelonr> facelontLabelonlProvidelonrs;
  privatelon FacelontIDMap facelontIdMap;

  /**
   * Populatelons thelon givelonn ThriftSelonarchRelonsult instancelon with thelon relonsults collelonctelond by this collelonctor
   * and clelonars all collelonctelond relonsults in this collelonctor.
   *
   * @param relonsult Thelon ThriftSelonarchRelonsult instancelon to belon populatelond with thelon relonsults collelonctelond in
   *               this collelonctor.
   */
  public abstract void fillRelonsultAndClelonar(ThriftSelonarchRelonsult relonsult);

  public void relonselontFacelontLabelonlProvidelonrs(
      Map<String, FacelontLabelonlProvidelonr> facelontLabelonlProvidelonrsToRelonselont, FacelontIDMap facelontIdMapToRelonselont) {
    this.facelontLabelonlProvidelonrs = facelontLabelonlProvidelonrsToRelonselont;
    this.facelontIdMap = facelontIdMapToRelonselont;
  }

  String findFacelontNamelon(int fielonldId) {
    relonturn fielonldId < 0 ? null : facelontIdMap.gelontFacelontFielonldByFacelontID(fielonldId).gelontFacelontNamelon();
  }

  protelonctelond ThriftSelonarchRelonsultelonxtraMelontadata gelontelonxtraMelontadata(ThriftSelonarchRelonsult relonsult) {
    ThriftSelonarchRelonsultMelontadata melontadata = relonsult.gelontMelontadata();
    if (!melontadata.isSelontelonxtraMelontadata()) {
      melontadata.selontelonxtraMelontadata(nelonw ThriftSelonarchRelonsultelonxtraMelontadata());
    }
    relonturn melontadata.gelontelonxtraMelontadata();
  }

  protelonctelond String gelontTelonrmFromProvidelonr(
      String facelontNamelon, long telonrmID, FacelontLabelonlProvidelonr providelonr) {
    relonturn providelonr.gelontLabelonlAccelonssor().gelontTelonrmTelonxt(telonrmID);
  }

  protelonctelond String gelontTelonrmFromFacelont(long telonrmID, int fielonldID, Selont<String> facelontsToCollelonctFrom) {
    if (telonrmID == elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND) {
      relonturn null;
    }

    String facelontNamelon = findFacelontNamelon(fielonldID);
    if (!facelontsToCollelonctFrom.contains(facelontNamelon)) {
      relonturn null;
    }

    final FacelontLabelonlProvidelonr providelonr = facelontLabelonlProvidelonrs.gelont(facelontNamelon);
    if (providelonr == null) {
      relonturn null;
    }

    relonturn gelontTelonrmFromProvidelonr(facelontNamelon, telonrmID, providelonr);
  }
}
