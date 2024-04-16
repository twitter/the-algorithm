package com.tw ter.search.earlyb rd.part  on.freshstartup;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.earlyb rd.part  on.Seg ntWr er;

// Data collected and produced wh le bu ld ng a seg nt.
class Seg ntBu ld nfo {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Seg ntBu ld nfo.class);

  //  nclus ve boundar es. [start, end].
  pr vate f nal long t etStartOffset;
  pr vate f nal long t etEndOffset;
  pr vate f nal  nt  ndex;
  pr vate f nal boolean lastSeg nt;

  pr vate long startT et d;
  pr vate long max ndexedT et d;
  pr vate KafkaOffsetPa r updateKafkaOffsetPa r;
  pr vate Seg ntWr er seg ntWr er;

  publ c Seg ntBu ld nfo(long t etStartOffset,
                          long t etEndOffset,
                           nt  ndex,
                          boolean lastSeg nt) {
    t .t etStartOffset = t etStartOffset;
    t .t etEndOffset = t etEndOffset;
    t . ndex =  ndex;
    t .lastSeg nt = lastSeg nt;

    t .startT et d = -1;
    t .updateKafkaOffsetPa r = null;
    t .max ndexedT et d = -1;
    t .seg ntWr er = null;
  }

  publ c vo d setUpdateKafkaOffsetPa r(KafkaOffsetPa r updateKafkaOffsetPa r) {
    t .updateKafkaOffsetPa r = updateKafkaOffsetPa r;
  }

  publ c KafkaOffsetPa r getUpdateKafkaOffsetPa r() {
    return updateKafkaOffsetPa r;
  }

  publ c boolean  sLastSeg nt() {
    return lastSeg nt;
  }

  publ c vo d setStartT et d(long startT et d) {
    t .startT et d = startT et d;
  }

  publ c long getT etStartOffset() {
    return t etStartOffset;
  }

  publ c long getT etEndOffset() {
    return t etEndOffset;
  }

  publ c long getStartT et d() {
    return startT et d;
  }

  publ c  nt get ndex() {
    return  ndex;
  }

  publ c vo d setMax ndexedT et d(long max ndexedT et d) {
    t .max ndexedT et d = max ndexedT et d;
  }

  publ c long getMax ndexedT et d() {
    return max ndexedT et d;
  }

  publ c Seg ntWr er getSeg ntWr er() {
    return seg ntWr er;
  }

  publ c vo d setSeg ntWr er(Seg ntWr er seg ntWr er) {
    t .seg ntWr er = seg ntWr er;
  }

  publ c vo d logState() {
    LOG. nfo("Seg ntBu ld nfo ( ndex:{})",  ndex);
    LOG. nfo(Str ng.format("  Start offset: %,d", t etStartOffset));
    LOG. nfo(Str ng.format("  End offset: %,d", t etEndOffset));
    LOG. nfo(Str ng.format("  Start t et  d: %d", startT et d));
  }
}
