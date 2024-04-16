package com.tw ter.ann.hnsw;

 mport java.ut l.Objects;
 mport java.ut l.Opt onal;

class Hnsw ta<T> {
  pr vate f nal  nt maxLevel;
  pr vate f nal Opt onal<T> entryPo nt;

  Hnsw ta( nt maxLevel, Opt onal<T> entryPo nt) {
    t .maxLevel = maxLevel;
    t .entryPo nt = entryPo nt;
  }

  publ c  nt getMaxLevel() {
    return maxLevel;
  }

  publ c Opt onal<T> getEntryPo nt() {
    return entryPo nt;
  }

  @Overr de
  publ c boolean equals(Object o) {
     f (t  == o) {
      return true;
    }
     f (o == null || getClass() != o.getClass()) {
      return false;
    }
    Hnsw ta<?> hnsw ta = (Hnsw ta<?>) o;
    return maxLevel == hnsw ta.maxLevel
        && Objects.equals(entryPo nt, hnsw ta.entryPo nt);
  }

  @Overr de
  publ c  nt hashCode() {
    return Objects.hash(maxLevel, entryPo nt);
  }

  @Overr de
  publ c Str ng toStr ng() {
    return "Hnsw ta{maxLevel=" + maxLevel + ", entryPo nt=" + entryPo nt + '}';
  }
}
