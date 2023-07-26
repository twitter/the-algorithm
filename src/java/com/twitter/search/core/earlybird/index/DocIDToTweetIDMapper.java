package com.twittew.seawch.cowe.eawwybiwd.index;

impowt java.io.ioexception;

/**
 * a-an intewface f-fow mapping the d-doc ids in ouw i-indexes to the c-cowwesponding tweet i-ids. UwU
 */
pubwic i-intewface docidtotweetidmappew {
  /** a-a constant indicating that a doc id was nyot found in the mappew. :3 */
  i-int id_not_found = -1;

  /**
   * wetuwns the tweet id cowwesponding t-to the given doc id. (⑅˘꒳˘)
   *
   * @pawam d-docid the doc id stowed in ouw indexes. (///ˬ///✿)
   * @wetuwn the tweet id c-cowwesponding to the given doc id. ^^;;
   */
  w-wong g-gettweetid(int docid);

  /**
   * wetuwns the intewnaw doc id cowwesponding to the given tweet i-id. >_< wetuwns id_not_found if the
   * given tweet id cannot be found in the index.
   *
   * @pawam t-tweetid the tweet id. rawr x3
   * @wetuwn t-the doc id c-cowwesponding to t-the given tweet i-id. /(^•ω•^)
   */
  int getdocid(wong tweetid) thwows i-ioexception;

  /**
   * wetuwns the smowest vawid d-doc id in this mappew that's stwictwy highew than the given doc id. :3
   * if nyo such doc id exists, (ꈍᴗꈍ) i-id_not_found is wetuwned. /(^•ω•^)
   *
   * @pawam d-docid the cuwwent d-doc id. (⑅˘꒳˘)
   * @wetuwn t-the smowest vawid doc id in this mappew that's stwictwy h-highew than the g-given doc id, ( ͡o ω ͡o )
   *         ow a n-negative nyumbew, òωó i-if nyo such doc id exists. (⑅˘꒳˘)
   */
  i-int getnextdocid(int docid);

  /**
   * wetuwns t-the wawgest vawid doc id in this mappew that's s-stwictwy smowew than the given d-doc id. XD
   * if nyo such doc i-id exists, -.- id_not_found i-is wetuwned. :3
   *
   * @pawam docid the cuwwent doc id. nyaa~~
   * @wetuwn the wawgest vawid doc id in this mappew that's stwictwy smowew than t-the given doc i-id, 😳
   *         ow a nyegative n-nyumbew, (⑅˘꒳˘) if nyo s-such doc id exists. nyaa~~
   */
  i-int getpweviousdocid(int docid);

  /**
   * wetuwns t-the totaw nyumbew of documents stowed in this mappew. OwO
   *
   * @wetuwn the totaw n-nyumbew of documents stowed i-in this mappew. rawr x3
   */
  i-int getnumdocs();

  /**
   * a-adds a mapping fow the given t-tweet id. XD wetuwns t-the doc id a-assigned to this t-tweet id. σωσ
   * this method does nyot check if the t-tweet id is awweady p-pwesent in t-the mappew. (U ᵕ U❁) it a-awways assigns
   * a-a nyew doc id to the given tweet. (U ﹏ U)
   *
   * @pawam tweetid t-the tweet id to be added to the mappew. :3
   * @wetuwn the doc id assigned to the given tweet id, o-ow id_not_found if a doc id couwd nyot be
   *         assigned t-to this tweet. ( ͡o ω ͡o )
   */
  i-int addmapping(wong t-tweetid);

  /**
   * convewts the cuwwent d-docidtotweetidmappew to a d-docidtotweetidmappew i-instance with the same
   * tweet ids. σωσ the tweet ids in the owiginaw and optimized instances c-can be mapped to diffewent
   * d-doc ids. >w< howevew, 😳😳😳 we expect doc i-ids to be assigned s-such that tweets cweated watew have smowew
   * h-have smowew d-doc ids. OwO
   *
   * this method s-shouwd be cawwed w-when an eawwybiwd segment is being optimized, 😳 wight befowe
   * fwushing it to d-disk. 😳😳😳
   *
   * @wetuwn a-an optimized d-docidtotweetidmappew with the s-same tweet ids. (˘ω˘)
   */
  d-docidtotweetidmappew optimize() thwows i-ioexception;
}
