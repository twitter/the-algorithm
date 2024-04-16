package com.tw ter.search.common.encod ng.features;

 mport java.ut l.Map;
 mport java.ut l.SortedSet;
 mport java.ut l.TreeMap;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Maps;
 mport com.google.common.collect.Sets;

/**
 * Normal zes values to predef ned b ns.
 *  f t  value to normal ze  s lo r than t  lo st b n def ned, normal zes to Byte.M N_VALUE.
 */
publ c class B nByteNormal zer extends ByteNormal zer {

  pr vate f nal TreeMap<Double, Byte> b ns = Maps.newTreeMap();
  pr vate f nal TreeMap<Byte, Double> reverseB ns = Maps.newTreeMap();

  /**
   * Constructs a normal zer us ng predef ned b ns.
   * @param b ns A mapp ng bet en t  upper bound of a value and t  b n   should normal ze to.
   * For example prov d ng a map w h 2 entr es, {5=>1, 10=>2} w ll normal ze as follows:
   *   values under 5: Byte.M N_VALUE
   *   values bet en 5 and 10: 1
   *   values over 10: 2
   */
  publ c B nByteNormal zer(f nal Map<Double, Byte> b ns) {
    Precond  ons.c ckNotNull(b ns);
    Precond  ons.c ckArgu nt(!b ns. sEmpty(), "No b ns prov ded");
    Precond  ons.c ckArgu nt(has ncreas ngValues(b ns));
    t .b ns.putAll(b ns);
    for (Map.Entry<Double, Byte> entry : b ns.entrySet()) {
      reverseB ns.put(entry.getValue(), entry.getKey());
    }
  }

  /**
   * c ck that  f key1 > key2 t n val1 > val2  n t  {@code map}.
   */
  pr vate stat c boolean has ncreas ngValues(f nal Map<Double, Byte> map) {
    SortedSet<Double> orderedKeys = Sets.newTreeSet(map.keySet());
    byte prev = Byte.M N_VALUE;
    for (Double key : orderedKeys) { // save t  unbox ng
      byte cur = map.get(key);
       f (cur <= prev) {
        return false;
      }
      prev = cur;
    }
    return true;
  }

  @Overr de
  publ c byte normal ze(double val) {
    Map.Entry<Double, Byte> lo rBound = b ns.floorEntry(val);
    return lo rBound == null
        ? Byte.M N_VALUE
        : lo rBound.getValue();
    }

  @Overr de
  publ c double unnormLo rBound(byte norm) {
    return reverseB ns.get(reverseB ns.floorKey(norm));
  }

  @Overr de
  publ c double unnormUpperBound(byte norm) {
    return norm == reverseB ns.lastKey()
        ? Double.POS T VE_ NF N TY
        : reverseB ns.get(reverseB ns.floorKey((byte) (1 + norm)));
  }
}
