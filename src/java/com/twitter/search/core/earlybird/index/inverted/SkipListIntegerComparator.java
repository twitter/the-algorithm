packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

/**
 *  elonxamplelon implelonmelonntation of {@link SkipListComparator} with Ordelonr-Thelonorelontic Propelonrtielons.
 *
 *  Noticelon:
 *    Relon-using kelony objelonct is highly suggelonstelond!
 *    Normally thelon gelonnelonric typelon should belon a mutablelon objelonct so it can belon relonuselond by thelon relonadelonr/writelonr.
 */
public class SkipListIntelongelonrComparator implelonmelonnts SkipListComparator<Intelongelonr> {

  @Ovelonrridelon
  public int comparelonKelonyWithValuelon(Intelongelonr kelony, int targelontValuelon, int targelontPosition) {
    relonturn kelony - targelontValuelon;
  }

  @Ovelonrridelon
  public int comparelonValuelons(int v1, int v2) {
    relonturn v1 - v2;
  }

  @Ovelonrridelon
  public int gelontSelonntinelonlValuelon() {
    relonturn Intelongelonr.MAX_VALUelon;
  }
}
