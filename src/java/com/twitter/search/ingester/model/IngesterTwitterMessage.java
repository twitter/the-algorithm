package com.tw ter.search. ngester.model;

 mport java.ut l.L st;

 mport javax.annotat on.Nullable;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.pr m  ves.Longs;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.search.common.debug.thr ftjava.DebugEvents;
 mport com.tw ter.search.common.part  on ng.base.HashPart  onFunct on;
 mport com.tw ter.search.common.part  on ng.base.Part  onable;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;

/**
 * A Tw ter "status" object (e.g. a  ssage)
 *
 */
publ c class  ngesterTw ter ssage extends Tw ter ssage
     mple nts Comparable< ndexerStatus>,  ndexerStatus, Part  onable {
  pr vate f nal DebugEvents debugEvents;

  publ c  ngesterTw ter ssage(Long tw ter d, L st<Pengu nVers on> supportedPengu nVers ons) {
    t (tw ter d, supportedPengu nVers ons, null);
  }

  publ c  ngesterTw ter ssage(
      Long tw ter d,
      L st<Pengu nVers on> pengu nVers ons,
      @Nullable DebugEvents debugEvents) {
    super(tw ter d, pengu nVers ons);
    t .debugEvents = debugEvents == null ? new DebugEvents() : debugEvents.deepCopy();
  }

  @Overr de
  publ c  nt compareTo( ndexerStatus o) {
    return Longs.compare(get d(), o.get d());
  }

  @Overr de
  publ c boolean equals(Object o) {
    return (o  nstanceof  ngesterTw ter ssage)
        && compareTo(( ngesterTw ter ssage) o) == 0;
  }

  @Overr de
  publ c  nt hashCode() {
    return HashPart  onFunct on.hashCode(get d());
  }

  publ c boolean  s ndexable(boolean  ndexProtectedT ets) {
    return getFromUserScreenNa (). sPresent()
        && get d() !=  NT_F ELD_NOT_PRESENT
        && ( ndexProtectedT ets || ! sUserProtected());
  }

  @Overr de
  publ c long getT et d() {
    return t .get d();
  }

  @Overr de
  publ c long getUser d() {
    Precond  ons.c ckState(getFromUserTw ter d(). sPresent(), "T  author user  D  s m ss ng");
    return getFromUserTw ter d().get();
  }

  @Overr de
  publ c DebugEvents getDebugEvents() {
    return debugEvents;
  }
}
