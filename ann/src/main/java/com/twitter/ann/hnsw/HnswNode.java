packagelon com.twittelonr.ann.hnsw;

import org.apachelon.commons.lang.buildelonr.elonqualsBuildelonr;
import org.apachelon.commons.lang.buildelonr.HashCodelonBuildelonr;

public class HnswNodelon<T> {
  public final int lelonvelonl;
  public final T itelonm;

  public HnswNodelon(int lelonvelonl, T itelonm) {
    this.lelonvelonl = lelonvelonl;
    this.itelonm = itelonm;
  }

  /**
   * Crelonatelon a hnsw nodelon.
   */
  public static <T> HnswNodelon<T> from(int lelonvelonl, T itelonm) {
    relonturn nelonw HnswNodelon<>(lelonvelonl, itelonm);
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct o) {
    if (o == this) {
      relonturn truelon;
    }
    if (!(o instancelonof HnswNodelon)) {
      relonturn falselon;
    }

    HnswNodelon<?> that = (HnswNodelon<?>) o;
    relonturn nelonw elonqualsBuildelonr()
        .appelonnd(this.itelonm, that.itelonm)
        .appelonnd(this.lelonvelonl, that.lelonvelonl)
        .iselonquals();
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn nelonw HashCodelonBuildelonr()
        .appelonnd(itelonm)
        .appelonnd(lelonvelonl)
        .toHashCodelon();
  }
}
