package com.tw ter.search.earlyb rd.part  on.freshstartup;

class KafkaOffsetPa r {
  pr vate f nal long beg nOffset;
  pr vate f nal long endOffset;

  publ c KafkaOffsetPa r(long beg nOffset, long endOffset) {
    t .beg nOffset = beg nOffset;
    t .endOffset = endOffset;
  }

  publ c boolean  ncludes(long offset) {
    return beg nOffset <= offset && offset <= endOffset;
  }

  publ c long getBeg nOffset() {
    return beg nOffset;
  }

  publ c long getEndOffset() {
    return endOffset;
  }
}
