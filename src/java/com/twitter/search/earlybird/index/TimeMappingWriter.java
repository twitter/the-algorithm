package com.twitter.search.earlybird.index;

import java.io.IOException;

import org.apache.lucene.util.AttributeSource;

import com.twitter.search.common.util.analysis.IntTermAttribute;
import com.twitter.search.core.earlybird.index.EarlybirdRealtimeIndexSegmentWriter;

public class TimeMappingWriter implements EarlybirdRealtimeIndexSegmentWriter.InvertedDocConsumer {
  private IntTermAttribute termAtt;
  private final RealtimeTimeMapper mapper;

  public TimeMappingWriter(RealtimeTimeMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public final void start(AttributeSource attributeSource, boolean currentDocIsOffensive) {
    termAtt = attributeSource.addAttribute(IntTermAttribute.class);
  }

  @Override
  public final void add(int docId, int position) throws IOException {
    final int timeSec = termAtt.getTerm();
    mapper.addMapping(docId, timeSec);
  }

  @Override
  public void finish() {
  }
}
