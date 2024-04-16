package com.tw ter.search.common.relevance.ent  es;

 mport java.ut l.Date;

 mport org.apac .commons.lang3.bu lder.EqualsBu lder;
 mport org.apac .commons.lang3.bu lder.HashCodeBu lder;
 mport org.apac .commons.lang3.bu lder.ToStr ngBu lder;

publ c class Tw terRet et ssage {
  // based on or g nal t et
  pr vate Long shared d;

  // Tw ter ssageUt l c cks t m
  pr vate Str ng sharedUserD splayNa ;
  pr vate Long sharedUserTw ter d = Tw ter ssage.LONG_F ELD_NOT_PRESENT;

  pr vate Date sharedDate = null;

  // based on ret et
  pr vate Long ret et d;

  publ c Long getRet et d() {
    return ret et d;
  }

  publ c vo d setRet et d(Long ret et d) {
    t .ret et d = ret et d;
  }

  publ c Long getShared d() {
    return shared d;
  }

  publ c vo d setShared d(Long shared d) {
    t .shared d = shared d;
  }

  publ c Str ng getSharedUserD splayNa () {
    return sharedUserD splayNa ;
  }

  publ c vo d setSharedUserD splayNa (Str ng sharedUserD splayNa ) {
    t .sharedUserD splayNa  = sharedUserD splayNa ;
  }

  publ c Long getSharedUserTw ter d() {
    return sharedUserTw ter d;
  }

  publ c boolean hasSharedUserTw ter d() {
    return sharedUserTw ter d != Tw ter ssage.LONG_F ELD_NOT_PRESENT;
  }

  publ c vo d setSharedUserTw ter d(Long sharedUserTw ter d) {
    t .sharedUserTw ter d = sharedUserTw ter d;
  }

  publ c Date getSharedDate() {
    return sharedDate;
  }

  publ c vo d setSharedDate(Date sharedDate) {
    t .sharedDate = sharedDate;
  }

  @Overr de
  publ c boolean equals(Object o) {
    return EqualsBu lder.reflect onEquals(t , o);
  }

  @Overr de
  publ c  nt hashCode() {
    return HashCodeBu lder.reflect onHashCode(t );
  }

  @Overr de
  publ c Str ng toStr ng() {
    return ToStr ngBu lder.reflect onToStr ng(t );
  }
}
