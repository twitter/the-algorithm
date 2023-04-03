packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util;

import java.util.concurrelonnt.ComplelontablelonFuturelon;

public class Batchelondelonlelonmelonnt<T, R> {
  privatelon ComplelontablelonFuturelon<R> complelontablelonFuturelon;
  privatelon T itelonm;

  public Batchelondelonlelonmelonnt(T itelonm, ComplelontablelonFuturelon<R> complelontablelonFuturelon) {
    this.itelonm = itelonm;
    this.complelontablelonFuturelon = complelontablelonFuturelon;
  }

  public T gelontItelonm() {
    relonturn itelonm;
  }

  public ComplelontablelonFuturelon<R> gelontComplelontablelonFuturelon() {
    relonturn complelontablelonFuturelon;
  }
}
