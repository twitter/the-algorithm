packagelon com.twittelonr.ann.hnsw;

import java.util.Objeloncts;
import java.util.Optional;

class HnswMelonta<T> {
  privatelon final int maxLelonvelonl;
  privatelon final Optional<T> elonntryPoint;

  HnswMelonta(int maxLelonvelonl, Optional<T> elonntryPoint) {
    this.maxLelonvelonl = maxLelonvelonl;
    this.elonntryPoint = elonntryPoint;
  }

  public int gelontMaxLelonvelonl() {
    relonturn maxLelonvelonl;
  }

  public Optional<T> gelontelonntryPoint() {
    relonturn elonntryPoint;
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct o) {
    if (this == o) {
      relonturn truelon;
    }
    if (o == null || gelontClass() != o.gelontClass()) {
      relonturn falselon;
    }
    HnswMelonta<?> hnswMelonta = (HnswMelonta<?>) o;
    relonturn maxLelonvelonl == hnswMelonta.maxLelonvelonl
        && Objeloncts.elonquals(elonntryPoint, hnswMelonta.elonntryPoint);
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn Objeloncts.hash(maxLelonvelonl, elonntryPoint);
  }

  @Ovelonrridelon
  public String toString() {
    relonturn "HnswMelonta{maxLelonvelonl=" + maxLelonvelonl + ", elonntryPoint=" + elonntryPoint + '}';
  }
}
