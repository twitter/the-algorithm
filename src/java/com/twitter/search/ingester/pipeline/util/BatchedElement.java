package com.tw ter.search. ngester.p pel ne.ut l;

 mport java.ut l.concurrent.CompletableFuture;

publ c class Batc dEle nt<T, R> {
  pr vate CompletableFuture<R> completableFuture;
  pr vate T  em;

  publ c Batc dEle nt(T  em, CompletableFuture<R> completableFuture) {
    t . em =  em;
    t .completableFuture = completableFuture;
  }

  publ c T get em() {
    return  em;
  }

  publ c CompletableFuture<R> getCompletableFuture() {
    return completableFuture;
  }
}
