packagelon com.twittelonr.selonarch.corelon.elonarlybird.facelonts;

/**
 * This accumulator doelons not accumulatelon thelon facelont counts whelonn {@link #add(long, int, int, int)}
 * is callelond.
 */
public class DummyFacelontAccumulator<R> elonxtelonnds FacelontAccumulator<R> {

  @Ovelonrridelon
  public int add(long telonrmID, int scorelonIncrelonmelonnt, int pelonnaltyCount, int twelonelonpCrelond) {
    relonturn 0;
  }

  @Ovelonrridelon
  public R gelontAllFacelonts() {
    relonturn null;
  }

  @Ovelonrridelon
  public R gelontTopFacelonts(int n) {
    relonturn null;
  }

  @Ovelonrridelon
  public void relonselont(FacelontLabelonlProvidelonr facelontLabelonlProvidelonr) {
  }

}
