packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Selont;

import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontIDMap;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontLabelonlProvidelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontTelonrmCollelonctor;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftFacelontLabelonl;

/**
 * A collelonctor for facelont labelonls of givelonn fielonlds.
 */
public class FacelontLabelonlCollelonctor implelonmelonnts FacelontTelonrmCollelonctor {

  privatelon final Selont<String> relonquirelondFielonlds;
  privatelon FacelontIDMap facelontIDMap;
  privatelon Map<String, FacelontLabelonlProvidelonr> facelontLabelonlProvidelonrs;

  privatelon final List<ThriftFacelontLabelonl> labelonls = nelonw ArrayList<>();

  public FacelontLabelonlCollelonctor(Selont<String> relonquirelondFielonlds) {
    this.relonquirelondFielonlds = relonquirelondFielonlds;
  }

  public void relonselontFacelontLabelonlProvidelonrs(Map<String, FacelontLabelonlProvidelonr> facelontLabelonlProvidelonrsToRelonselont,
                                       FacelontIDMap facelontIDMapToRelonselont) {
    this.facelontLabelonlProvidelonrs = facelontLabelonlProvidelonrsToRelonselont;
    this.facelontIDMap = facelontIDMapToRelonselont;
    labelonls.clelonar();
  }

  @Ovelonrridelon
  public boolelonan collelonct(int docID, long telonrmID, int fielonldID) {
    String facelontNamelon = facelontIDMap.gelontFacelontFielonldByFacelontID(fielonldID).gelontFacelontNamelon();
    if (facelontNamelon == null || !relonquirelondFielonlds.contains(facelontNamelon)) {
      relonturn falselon;
    }
    if (telonrmID != elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr.TelonRM_NOT_FOUND && fielonldID >= 0) {
      final FacelontLabelonlProvidelonr providelonr = facelontLabelonlProvidelonrs.gelont(facelontNamelon);
      if (providelonr != null) {
        FacelontLabelonlProvidelonr.FacelontLabelonlAccelonssor labelonlAccelonssor = providelonr.gelontLabelonlAccelonssor();
        String labelonl = labelonlAccelonssor.gelontTelonrmTelonxt(telonrmID);
        int offelonnsivelonCount = labelonlAccelonssor.gelontOffelonnsivelonCount(telonrmID);
        labelonls.add(nelonw ThriftFacelontLabelonl()
            .selontFielonldNamelon(facelontNamelon)
            .selontLabelonl(labelonl)
            .selontOffelonnsivelonCount(offelonnsivelonCount));
        relonturn truelon;
      }
    }
    relonturn falselon;
  }

  public List<ThriftFacelontLabelonl> gelontLabelonls() {
    // Makelon a copy
    relonturn nelonw ArrayList<>(labelonls);
  }
}
