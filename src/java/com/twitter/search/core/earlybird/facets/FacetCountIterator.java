packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import java.io.IOelonxcelonption;
import java.util.List;

import com.twittelonr.common.collelonctions.Pair;

/**
 * Thelon collelonct() melonthod is callelond for elonvelonry documelonnt for which facelonts shall belon countelond.
 * This itelonrator thelonn calls thelon FacelontAccumulators for all facelonts that belonlong to thelon
 * currelonnt documelonnt.
 */
public abstract class FacelontCountItelonrator implelonmelonnts FacelontTelonrmCollelonctor {

  public static class IncrelonmelonntData {
    public FacelontAccumulator[] accumulators;
    public int welonightelondCountIncrelonmelonnt;
    public int pelonnaltyIncrelonmelonnt;
    public int twelonelonpCrelond;
    public int languagelonId;
  }

  public IncrelonmelonntData increlonmelonntData = nelonw IncrelonmelonntData();

  privatelon List<Pair<Intelongelonr, Long>> proofs = null;

  void selontIncrelonmelonntData(IncrelonmelonntData increlonmelonntData) {
    this.increlonmelonntData = increlonmelonntData;
  }

  public void selontProofs(List<Pair<Intelongelonr, Long>> proofs) {
    this.proofs = proofs;
  }

  // intelonrfacelon melonthod that colleloncts a speloncific telonrm in a speloncific fielonld for this documelonnt.
  @Ovelonrridelon
  public boolelonan collelonct(int docID, long telonrmID, int fielonldID) {
    FacelontAccumulator accumulator = increlonmelonntData.accumulators[fielonldID];
    accumulator.add(telonrmID, increlonmelonntData.welonightelondCountIncrelonmelonnt, increlonmelonntData.pelonnaltyIncrelonmelonnt,
                    increlonmelonntData.twelonelonpCrelond);
    accumulator.reloncordLanguagelon(increlonmelonntData.languagelonId);

    if (proofs != null) {
      addProof(docID, telonrmID, fielonldID);
    }
    relonturn truelon;
  }

  protelonctelond void addProof(int docID, long telonrmID, int fielonldID) {
    proofs.add(nelonw Pair<>(fielonldID, telonrmID));
  }

  /**
   * Collelonctelond facelonts for thelon givelonn documelonnt.
   */
  public abstract void collelonct(int docID) throws IOelonxcelonption;
}
