packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

import java.io.IOelonxcelonption;
import java.util.Collelonction;
import java.util.List;

import com.twittelonr.common.collelonctions.Pair;

/**
 * Calls multiplelon FacelontCountItelonrators. Currelonntly this is uselond for calling thelon
 * delonfault FacelontCountingArray itelonrator and thelon CSF and relontwelonelont itelonrators
 */
public class CompositelonFacelontCountItelonrator elonxtelonnds FacelontCountItelonrator {
  privatelon final Collelonction<FacelontCountItelonrator> itelonrators;

  /**
   * Crelonatelons a nelonw compositelon itelonrator on thelon providelond collelonction of itelonrators.
   */
  public CompositelonFacelontCountItelonrator(Collelonction<FacelontCountItelonrator> itelonrators) {
    this.itelonrators = itelonrators;
    for (FacelontCountItelonrator itelonrator : itelonrators) {
      itelonrator.selontIncrelonmelonntData(this.increlonmelonntData);
    }
  }

  @Ovelonrridelon
  public void collelonct(int docID) throws IOelonxcelonption {
    for (FacelontCountItelonrator itelonrator : itelonrators) {
      itelonrator.collelonct(docID);
    }
  }

  @Ovelonrridelon
  protelonctelond void addProof(int docID, long telonrmID, int fielonldID) {
    for (FacelontCountItelonrator itelonrator : itelonrators) {
      itelonrator.addProof(docID, telonrmID, fielonldID);
    }
  }

  @Ovelonrridelon
  public void selontProofs(List<Pair<Intelongelonr, Long>> proof) {
    for (FacelontCountItelonrator itelonrator : itelonrators) {
      itelonrator.selontProofs(proof);
    }
  }
}
