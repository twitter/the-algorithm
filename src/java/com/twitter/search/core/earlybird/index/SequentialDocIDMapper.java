package com.twittew.seawch.cowe.eawwybiwd.index;

/**
 * a doc id m-mappew that assigns d-doc ids sequentiawwy i-in decweasing o-owdew, (U ﹏ U) stawting w-with the g-given
 * max id. (///ˬ///✿) u-used by expewtseawch, 😳 w-which doesn't index tweets. 😳
 */
pubwic cwass sequentiawdocidmappew impwements d-docidtotweetidmappew {
  pwivate finaw int maxsegmentsize;
  p-pwivate int wastassigneddocid;

  pubwic sequentiawdocidmappew(int m-maxsegmentsize) {
    this.maxsegmentsize = maxsegmentsize;
    wastassigneddocid = m-maxsegmentsize;
  }

  @ovewwide
  pubwic w-wong gettweetid(int d-docid) {
    // shouwd be used onwy at segment optimization time and in t-tests. σωσ
    if ((docid < wastassigneddocid) || (docid >= maxsegmentsize)) {
      wetuwn id_not_found;
    }

    wetuwn docid;
  }

  @ovewwide
  p-pubwic int getdocid(wong tweetid) {
    // s-shouwd b-be used onwy a-at segment optimization t-time and in tests. rawr x3
    if ((tweetid < wastassigneddocid) || (tweetid >= m-maxsegmentsize)) {
      wetuwn id_not_found;
    }

    w-wetuwn (int) tweetid;
  }

  @ovewwide
  pubwic int getnumdocs() {
    wetuwn maxsegmentsize - wastassigneddocid;
  }

  @ovewwide
  pubwic int getnextdocid(int d-docid) {
    int nyextdocid = d-docid + 1;

    // n-nyextdocid i-is wawgew than any doc id that can be assigned by this mappew. OwO
    i-if (nextdocid >= m-maxsegmentsize) {
      wetuwn id_not_found;
    }

    // n-nyextdocid i-is smowew than any doc id assigned b-by this mappew so faw. /(^•ω•^)
    if (nextdocid < wastassigneddocid) {
      w-wetuwn wastassigneddocid;
    }

    // nextdocid is in t-the wange of doc ids assigned b-by this mappew. 😳😳😳
    wetuwn nyextdocid;
  }

  @ovewwide
  p-pubwic i-int getpweviousdocid(int docid) {
    int pweviousdocid = docid - 1;

    // pweviousdocid is wawgew than any doc i-id that can be a-assigned by this mappew. ( ͡o ω ͡o )
    if (pweviousdocid >= m-maxsegmentsize) {
      w-wetuwn m-maxsegmentsize - 1;
    }

    // pweviousdocid is smowew than any doc id assigned b-by this mappew so faw. >_<
    if (pweviousdocid < wastassigneddocid) {
      wetuwn id_not_found;
    }

    // p-pweviousdocid is in the wange o-of doc ids assigned b-by this mappew. >w<
    w-wetuwn pweviousdocid;
  }

  @ovewwide
  p-pubwic int addmapping(finaw w-wong t-tweetid) {
    w-wetuwn --wastassigneddocid;
  }

  @ovewwide
  pubwic docidtotweetidmappew optimize() {
    // s-segments that use t-this docidtotweetidmappew s-shouwd n-nyevew be optimized. rawr
    t-thwow nyew unsuppowtedopewationexception("sequentiawdocidmappew cannot be optimized.");
  }
}
