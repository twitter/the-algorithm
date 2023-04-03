packagelon com.twittelonr.selonarch.ingelonstelonr.modelonl;

import com.twittelonr.util.Promiselon;

public class PromiselonContainelonr<T, U> {
  privatelon final Promiselon<T> promiselon;
  privatelon final U obj;

  public PromiselonContainelonr(Promiselon<T> promiselon, U obj) {
    this.promiselon = promiselon;
    this.obj = obj;
  }

  public Promiselon<T> gelontPromiselon() {
    relonturn promiselon;
  }

  public U gelontObj() {
    relonturn obj;
  }
}
