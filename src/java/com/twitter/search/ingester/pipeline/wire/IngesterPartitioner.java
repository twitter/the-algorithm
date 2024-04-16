package com.tw ter.search. ngester.p pel ne.w re;

 mport javax.nam ng.Nam ngExcept on;

 mport com.tw ter.search.common.part  on ng.base.Part  onMapp ngManager;
 mport com.tw ter.search.common.ut l. o.kafka.SearchPart  oner;

/**
 * A var ant of {@code SearchPart  oner} wh ch retr eves {@code Part  onMapp ngManager} from
 * {@code W reModule}.
 *
 * Note that t  value object has to  mple nt {@code Part  onable}.
 */
publ c class  ngesterPart  oner extends SearchPart  oner {

  publ c  ngesterPart  oner() {
    super(getPart  onMapp ngManager());
  }

  pr vate stat c Part  onMapp ngManager getPart  onMapp ngManager() {
    try {
      return W reModule.getW reModule().getPart  onMapp ngManager();
    } catch (Nam ngExcept on e) {
      throw new Runt  Except on(e);
    }
  }
}
