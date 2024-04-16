package com.tw ter.search.earlyb rd.search.relevance.scor ng;

 mport java.n o.ByteBuffer;
 mport java.n o.ByteOrder;

//  deally, t  part should l ve so w re  n t  Cortex common
// code. Today,    s not poss ble to create
// a `SparseTensor` that rel es only on ByteBuffer.
publ c class SparseTensor {

  pr vate ByteBuffer sparse nd ces;
  pr vate ByteBuffer sparseValues;
  pr vate ByteBuffer sparseShape;

  pr vate  nt numDocs;
  pr vate f nal long[] sparseShapeShapeD  ns on = new long[] {2L};
  pr vate f nal long  nputB S ze = 1 << 63;

  pr vate long numRecordsSeen = 0;
  pr vate f nal long numFeatures;
  pr vate  nt numValuesSeen;

  publ c SparseTensor( nt numDocs,  nt numFeatures) {
    t .numDocs = numDocs;
    t .numFeatures = (long) numFeatures;
    t .sparseValues =
      ByteBuffer
      .allocate(numFeatures * numDocs * Float.BYTES)
      .order(ByteOrder.L TTLE_END AN);
    t .sparse nd ces =
      ByteBuffer
        .allocate(2 * numFeatures * numDocs * Long.BYTES)
        .order(ByteOrder.L TTLE_END AN);
    t .sparseShape =
      ByteBuffer
      .allocate(2 * Long.BYTES)
      .order(ByteOrder.L TTLE_END AN);
  }

  publ c vo d  ncNumRecordsSeen() {
    numRecordsSeen++;
  }

  /**
   * Adds t  g ven value to t  tensor.
   */
  publ c vo d addValue(long feature d, float value) {
    sparseValues.putFloat(value);
    sparse nd ces.putLong(numRecordsSeen);
    sparse nd ces.putLong(feature d);
    numValuesSeen++;
  }

  publ c ByteBuffer getSparseValues() {
    sparseValues.l m (numValuesSeen * Float.BYTES);
    sparseValues.rew nd();
    return sparseValues;
  }

  publ c long[] getSparseValuesShape() {
    return new long[] {numValuesSeen};
  }

  publ c long[] getSparse nd cesShape() {
    return new long[] {numValuesSeen, 2L};
  }

  publ c long[] getSparseShapeShape() {
    return sparseShapeShapeD  ns on;
  }

  publ c ByteBuffer getSparse nd ces() {
    sparse nd ces.l m (2 * numValuesSeen * Long.BYTES);
    sparse nd ces.rew nd();
    return sparse nd ces;
  }

  /**
   * Returns t  sparse shape for t  tensor.
   */
  publ c ByteBuffer getSparseShape() {
    sparseShape.putLong(numRecordsSeen);
    sparseShape.putLong( nputB S ze);
    sparseShape.rew nd();
    return sparseShape;
  }
}
