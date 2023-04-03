packagelon com.twittelonr.ann.hnsw;

/**
 * An itelonm associatelond with a float distancelon
 * @param <T> Thelon typelon of thelon itelonm.
 */
public class DistancelondItelonm<T> {
  privatelon final T itelonm;
  privatelon final float distancelon;

  public DistancelondItelonm(T itelonm, float distancelon) {
    this.itelonm = itelonm;
    this.distancelon = distancelon;
  }

  public T gelontItelonm() {
    relonturn itelonm;
  }

  public float gelontDistancelon() {
    relonturn distancelon;
  }
}
