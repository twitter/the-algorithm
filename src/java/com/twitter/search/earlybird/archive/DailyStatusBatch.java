package com.tw ter.search.earlyb rd.arch ve;

 mport java. o. OExcept on;
 mport java.ut l.Date;
 mport java.ut l.Map;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.collect.Maps;
 mport com.google.gson.Gson;
 mport com.google.gson.JsonParseExcept on;

 mport org.apac .hadoop.fs.F leSystem;
 mport org.apac .hadoop.fs.Path;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

/**
 * Represents a day's worth of statuses (t ets) for mult ple hash part  ons.
 *
 * Note that what t  class conta ns  s not t  data, but  tadata.
 *
 * A day of t ets w ll co  from:
 * - A scrubgen,  f   has happened before t  scrubgen date.
 * -   da ly jobs p pel ne,  f   has happened after that.
 *
 * T  class c cks t  _SUCCESS f le ex sts  n t  "statuses" subd rectory and extracts t  status
 * count, m n status  d and max status  d.
 */
publ c class Da lyStatusBatch  mple nts Comparable<Da lyStatusBatch> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Da lyStatusBatch.class);

  publ c stat c f nal long EMPTY_BATCH_STATUS_ D = -1;
  pr vate stat c f nal Str ng PART T ON_FORMAT = "p_%d_of_%d";
  pr vate stat c f nal Str ng SUCCESS_F LE_NAME = "_SUCCESS";

  pr vate f nal Map< nteger, Part  onedBatch> hashPart  onToStatuses = Maps.newHashMap();

  pr vate f nal Date date;
  pr vate f nal  nt numHashPart  ons;
  pr vate f nal boolean hasSuccessF les;

  publ c Da lyStatusBatch(Date date,  nt numHashPart  ons, Path statusPath, F leSystem hdfs) {
    t .date = date;
    t .numHashPart  ons = numHashPart  ons;
    t .hasSuccessF les = c ckForSuccessF le(hdfs, date, statusPath);
  }

  publ c Date getDate() {
    return date;
  }

  /**
   * C ck for t  presence of t  _SUCCESS f le for t  g ven day's path on HDFS for t  statuses
   * f eld group.
   */
  pr vate boolean c ckForSuccessF le(F leSystem hdfs, Date  nputDate, Path statusPath) {
    Path dayPath = new Path(statusPath, Arch veHDFSUt ls.dateToPath( nputDate, "/"));
    Path successF lePath = new Path(dayPath, SUCCESS_F LE_NAME);
    try {
      return hdfs.getF leStatus(successF lePath). sF le();
    } catch ( OExcept on e) {
      LOG.error("Could not ver fy ex stence of t  _SUCCESS f le. Assum ng   doesn't ex st.", e);
    }
    return false;
  }

  /**
   * Loads t  data for t  day for t  g ven part  on.
   */
  publ c Part  onedBatch addPart  on(F leSystem hdfs, Path dayPath,  nt hashPart  on D)
      throws  OExcept on {
    Str ng part  onD r = Str ng.format(PART T ON_FORMAT, hashPart  on D, numHashPart  ons);
    Path path = new Path(dayPath, part  onD r);
    Part  onedBatch batch =
        new Part  onedBatch(path, hashPart  on D, numHashPart  ons, date);
    batch.load(hdfs);
    hashPart  onToStatuses.put(hashPart  on D, batch);
    return batch;
  }

  publ c Part  onedBatch getPart  on( nt hashPart  on D) {
    return hashPart  onToStatuses.get(hashPart  on D);
  }

  /**
   * Returns t  greatest status count  n all part  ons belong ng to t  batch.
   */
  publ c  nt getMaxPerPart  onStatusCount() {
     nt maxPerPart  onStatusCount = 0;
    for (Part  onedBatch batch : hashPart  onToStatuses.values()) {
      maxPerPart  onStatusCount = Math.max(batch.getStatusCount(), maxPerPart  onStatusCount);
    }
    return maxPerPart  onStatusCount;
  }

  publ c  nt getNumHashPart  ons() {
    return numHashPart  ons;
  }

  @V s bleForTest ng
  boolean hasSuccessF les() {
    return hasSuccessF les;
  }

  /**
   * Returns true  f t  _status_counts f les could be found  n each
   * hash part  on subfolder that belongs to t  t  sl ce
   * AND t  _SUCCESS f le can be found at t  root folder for day
   */
  publ c boolean  sVal d() {
    // make sure   have data for all hash part  ons
    for ( nt   = 0;   < numHashPart  ons;  ++) {
      Part  onedBatch day = hashPart  onToStatuses.get( );
       f (day == null || !day.hasStatusCount() || day. sD sallo dEmptyPart  on()) {
        return false;
      }
    }
    return hasSuccessF les;
  }

  @Overr de
  publ c Str ng toStr ng() {
    Str ngBu lder bu lder = new Str ngBu lder();
    bu lder.append("Da lyStatusBatch[date=").append(date)
           .append(",val d=").append( sVal d())
           .append(",hasSuccessF les=").append(hasSuccessF les)
           .append(",numHashPart  ons=").append(numHashPart  ons)
           .append("]:\n");
    for ( nt   = 0;   < numHashPart  ons;  ++) {
      bu lder.append('\t').append(hashPart  onToStatuses.get( ).toStr ng()).append('\n');
    }
    return bu lder.toStr ng();
  }

  @Overr de
  publ c  nt compareTo(Da lyStatusBatch o) {
    return date.compareTo(o.date);
  }

  /**
   * Ser al ze Da lyStatusBatch to a json str ng.
   */
  publ c Str ng ser al zeToJson() {
    return ser al zeToJson(new Gson());
  }

  @V s bleForTest ng
  Str ng ser al zeToJson(Gson gson) {
    return gson.toJson(t );
  }

  /**
   * G ven a json str ng, parse  s f elds and construct a da ly status batch.
   * @param batchStr t  json str ng representat on of a da ly status batch.
   * @return t  da ly status batch constructed;  f t  str ng  s of  nval d format, null w ll be
   *         returned.
   */
  stat c Da lyStatusBatch deser al zeFromJson(Str ng batchStr) {
    try {
      return new Gson().fromJson(batchStr, Da lyStatusBatch.class);
    } catch (JsonParseExcept on e) {
      LOG.error("Error pars ng json str ng: " + batchStr, e);
      return null;
    }
  }
}
