package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt o-owg.apache.wucene.seawch.docidsetitewatow;

/**
 * a-a skip wist w-weadew of a singwe t-tewm used {@wink h-highdfpackedintsdocsenum}. nyaa~~
 * @see h-highdfpackedintspostingwists
 */
c-cwass highdfpackedintsskipwistweadew {
  /** s-skip wists int poow. ^^ */
  pwivate finaw intbwockpoow skipwists;

  /** whethew p-positions awe omitted in the posting wist having t-the wead skip wist. >w< */
  pwivate f-finaw boowean omitpositions;

  /**
   * wast doc in the pwevious swice wewative t-to the cuwwent dewta-fweq s-swice. OwO this vawue i-is 0 if
   * the cuwwent swice is the fiwst dewta-fweq swice. XD
   */
  pwivate i-int pweviousdocidcuwwentswice;

  /** encoded metadata of the cuwwent dewta-fweq swice.*/
  pwivate i-int encodedmetadatacuwwentswice;

  /**
   * pointew to the f-fiwst int (contains t-the position s-swice headew) o-of the position swice that has
   * the fiwst position o-of the fiwst doc in the cuwwent dewta-fweq s-swice. ^^;;
   */
  pwivate int positioncuwwentswiceindex;

  /** pointew to the fiwst int in the cuwwent dewta-fweq swice. 🥺 */
  pwivate i-int dewtafweqcuwwentswicepointew;

  /** data of nyext swice. XD */
  p-pwivate i-int pweviousdocidnextswice;
  pwivate i-int encodedmetadatanextswice;
  pwivate int positionnextswiceindex;
  pwivate i-int dewtafweqnextswicepointew;

  /** u-used to woad bwocks and w-wead ints fwom s-skip wists int poow. (U ᵕ U❁) */
  pwivate i-int[] cuwwentskipwistbwock;
  pwivate int skipwistbwockstawt;
  p-pwivate int skipwistbwockindex;

  /** nyumbew o-of wemaining skip entwies fow t-the wead skip wist. :3 */
  pwivate i-int nyumskipwistentwieswemaining;

  /** w-wawgest doc id in the posting wist having the wead skip wist. ( ͡o ω ͡o ) */
  pwivate finaw int wawgestdocid;

  /** p-pointew to t-the fiwst int in the fiwst swice t-that stowes positions f-fow this t-tewm. òωó */
  pwivate finaw int positionwistpointew;

  /** totaw nyumbew of docs in t-the posting wist having the wead skip wist. σωσ */
  pwivate finaw int nyumdocstotaw;

  /**
   * c-cweate a skip wist weadew specified b-by the given s-skip wist pointew i-in the given skip wists int
   * p-poow. (U ᵕ U❁)
   *
   * @pawam s-skipwists i-int poow whewe t-the wead skip wist exists
   * @pawam skipwistpointew p-pointew t-to the wead skip w-wist
   * @pawam o-omitpositions w-whethew positions awe omitted in the positing wist to which the w-wead skip
   *                      wist bewongs
   */
  pubwic highdfpackedintsskipwistweadew(
      finaw intbwockpoow skipwists, (✿oωo)
      f-finaw int skipwistpointew, ^^
      finaw boowean omitpositions) {
    t-this.skipwists = s-skipwists;
    t-this.omitpositions = omitpositions;

    t-this.skipwistbwockstawt = intbwockpoow.getbwockstawt(skipwistpointew);
    t-this.skipwistbwockindex = i-intbwockpoow.getoffsetinbwock(skipwistpointew);
    this.cuwwentskipwistbwock = skipwists.getbwock(skipwistbwockstawt);

    // wead skip wist headew. ^•ﻌ•^
    this.numskipwistentwieswemaining = w-weadnextvawuefwomskipwistbwock();
    this.wawgestdocid = w-weadnextvawuefwomskipwistbwock();
    this.numdocstotaw = w-weadnextvawuefwomskipwistbwock();
    i-int dewtafweqwistpointew = weadnextvawuefwomskipwistbwock();
    this.positionwistpointew = o-omitpositions ? -1 : w-weadnextvawuefwomskipwistbwock();

    // set it back by o-one swice fow fetchnextskipentwy() t-to advance cowwectwy. XD
    this.dewtafweqnextswicepointew = dewtafweqwistpointew - highdfpackedintspostingwists.swice_size;
    fetchnextskipentwy();
  }

  /**
   * w-woad awweady f-fetched data i-in nyext skip entwy into cuwwent d-data vawiabwes, :3 a-and pwe-fetch again.
   */
  p-pubwic void getnextskipentwy() {
    pweviousdocidcuwwentswice = pweviousdocidnextswice;
    encodedmetadatacuwwentswice = encodedmetadatanextswice;
    p-positioncuwwentswiceindex = p-positionnextswiceindex;
    dewtafweqcuwwentswicepointew = dewtafweqnextswicepointew;
    fetchnextskipentwy();
  }

  /**
   * f-fetch data f-fow nyext skip entwy if skip wist is nyot exhausted; othewwise, (ꈍᴗꈍ) s-set docidnextswice
   * to nyo_mowe_docs. :3
   */
  pwivate void fetchnextskipentwy() {
    if (numskipwistentwieswemaining == 0) {
      pweviousdocidnextswice = d-docidsetitewatow.no_mowe_docs;
      wetuwn;
    }

    pweviousdocidnextswice = w-weadnextvawuefwomskipwistbwock();
    e-encodedmetadatanextswice = weadnextvawuefwomskipwistbwock();
    if (!omitpositions) {
      positionnextswiceindex = w-weadnextvawuefwomskipwistbwock();
    }
    d-dewtafweqnextswicepointew += highdfpackedintspostingwists.swice_size;
    nyumskipwistentwieswemaining--;
  }

  /**************************************
   * gettews o-of data in skip wist entwy *
   **************************************/

  /**
   * i-in the context of a cuwwent swice, (U ﹏ U) this is the docid of the w-wast document in the pwevious
   * s-swice (ow 0 if t-the cuwwent swice is the fiwst s-swice). UwU
   *
   * @see highdfpackedintspostingwists#skipwist_entwy_size
   */
  p-pubwic int getpweviousdocidcuwwentswice() {
    w-wetuwn pweviousdocidcuwwentswice;
  }

  /**
   * g-get the encoded metadata of the c-cuwwent dewta-fweq s-swice. 😳😳😳
   *
   * @see highdfpackedintspostingwists#skipwist_entwy_size
   */
  pubwic int g-getencodedmetadatacuwwentswice() {
    w-wetuwn encodedmetadatacuwwentswice;
  }

  /**
   * g-get the pointew to the fiwst int, XD which c-contains the position swice headew, o.O o-of the position
   * s-swice that contains the fiwst position of the fiwst d-doc in the dewta-fweq s-swice that
   * i-is cowwesponding t-to the cuwwent skip wist e-entwy. (⑅˘꒳˘)
   *
   * @see highdfpackedintspostingwists#skipwist_entwy_size
   */
  pubwic int getpositioncuwwentswicepointew() {
    assewt !omitpositions;
    wetuwn positionwistpointew
        + p-positioncuwwentswiceindex * highdfpackedintspostingwists.swice_size;
  }

  /**
   * g-get the pointew to the fiwst i-int in the cuwwent dewta-fweq s-swice. 😳😳😳
   */
  pubwic int getdewtafweqcuwwentswicepointew() {
    w-wetuwn dewtafweqcuwwentswicepointew;
  }

  /**
   * i-in the context o-of nyext s-swice, nyaa~~ get the wast d-doc id in the pwevious swice. rawr this is used to skip
   * ovew swices. -.-
   *
   * @see highdfpackedintsdocsenum#skipto(int)
   */
  pubwic int p-peekpweviousdocidnextswice() {
    w-wetuwn pweviousdocidnextswice;
  }

  /***************************************
   * g-gettews of data in skip wist h-headew *
   ***************************************/

  pubwic int getwawgestdocid() {
    wetuwn wawgestdocid;
  }

  p-pubwic i-int getnumdocstotaw() {
    wetuwn n-nyumdocstotaw;
  }

  /***************************************************
   * methods hewping woading int b-bwock and wead i-ints *
   ***************************************************/

  pwivate int weadnextvawuefwomskipwistbwock() {
    i-if (skipwistbwockindex == intbwockpoow.bwock_size) {
      w-woadskipwistbwock();
    }
    wetuwn cuwwentskipwistbwock[skipwistbwockindex++];
  }

  pwivate void woadskipwistbwock() {
    skipwistbwockstawt += i-intbwockpoow.bwock_size;
    c-cuwwentskipwistbwock = s-skipwists.getbwock(skipwistbwockstawt);
    s-skipwistbwockindex = 0;
  }
}
