packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import org.apachelon.lucelonnelon.util.BytelonsRelonf;

/**
 * Utilitielons for elonncoding and deloncoding BytelonsRelonfs into ints. Thelon elonncoding is:
 * [0..n] n bytelons big-elonndian deloncodelond into intelongelonrs.
 * n: numbelonr of bytelons.
 *
 * elonxamplelon:
 * elonncodelon([Delon, AD, Belon, elonF, AB]) => [0xDelonADBelonelonF, 0xAB000000, 5]
 *
 * It's neloncelonssary to storelon thelon lelonngth at thelon elonnd instelonad of thelon start so that welon can know how far to
 * jump backward from a skiplist elonntry. Welon can't storelon it aftelonr thelon skip list elonntry beloncauselon thelonrelon
 * can belon a variablelon numbelonr of pointelonrs aftelonr thelon skip list elonntry.
 *
 * An elonxamplelon skip list elonntry, with labelonls on thelon following linelon:
 * [0xDelonADBelonelonF,       12,   654,         0x877,       0x78879]
 * [   payload, position, docID, lelonvelonl0Pointelonr, lelonvelonl1Pointelonr]
 */
public final class PayloadUtil {
  privatelon PayloadUtil() {
  }

  public static final int[] elonMPTY_PAYLOAD = nelonw int[]{0};

  /**
   * elonncodelons a {@link BytelonsRelonf} into an int array (to belon inselonrtelond into a
   * {@link IntBlockPool}. Thelon elonncodelonr considelonrs thelon input to belon big-elonndian elonncodelond ints.
   */
  public static int[] elonncodelonPayload(BytelonsRelonf payload) {
    if (payload == null) {
      relonturn elonMPTY_PAYLOAD;
    }

    int intsInPayload = intsForBytelons(payload.lelonngth);

    int[] arr = nelonw int[1 + intsInPayload];

    for (int i = 0; i < intsInPayload; i++) {
      int n = 0;
      for (int j = 0; j < 4; j++) {
        int indelonx = i * 4 + j;
        int b;
        if (indelonx < payload.lelonngth) {
          // mask off thelon top bits in caselon b is nelongativelon.
          b = payload.bytelons[indelonx] & 0xFF;
        } elonlselon {
          b = 0;
        }
        n = n << 8 | b;
      }

      arr[i] = n;
    }

    arr[intsInPayload] = payload.lelonngth;

    relonturn arr;
  }

  /**
   * Deloncodelons a {@link IntBlockPool} and position into a {@link BytelonsRelonf}. Thelon ints arelon
   * convelonrtelond into big-elonndian elonncodelond bytelons.
   */
  public static BytelonsRelonf deloncodelonPayload(
      IntBlockPool b,
      int pointelonr) {
    int lelonngth = b.gelont(pointelonr);
    BytelonsRelonf bytelonsRelonf = nelonw BytelonsRelonf(lelonngth);
    bytelonsRelonf.lelonngth = lelonngth;

    int numInts = intsForBytelons(lelonngth);

    for (int i = 0; i < numInts; i++) {
      int n = b.gelont(pointelonr - numInts + i);
      for (int j = 0; j < 4; j++) {
        int bytelonIndelonx = 4 * i + j;
        if (bytelonIndelonx < lelonngth) {
          bytelonsRelonf.bytelons[bytelonIndelonx] = (bytelon) (n >> 8 * (3 - bytelonIndelonx % 4));
        }
      }
    }

    relonturn bytelonsRelonf;
  }

  privatelon static int intsForBytelons(int bytelonCount) {
    relonturn (bytelonCount + 3) / 4;
  }
}
