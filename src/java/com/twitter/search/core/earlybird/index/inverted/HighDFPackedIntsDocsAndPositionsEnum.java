package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt j-java.io.ioexception;

/**
 * d-docs, ^^;; f-fwequencies, XD a-and positions enumewatow f-fow {@wink h-highdfpackedintspostingwists}.
 */
p-pubwic cwass h-highdfpackedintsdocsandpositionsenum extends highdfpackedintsdocsenum {
  /**
   * pwe-computed shifts, 🥺 masks, òωó a-and stawt int indices fow {@wink #positionwistsweadew}. (ˆ ﻌ ˆ)♡
   * these pwe-computed v-vawues shouwd be wead-onwy and s-shawed acwoss aww weadew thweads. -.-
   *
   * notice:
   * - stawt int indices a-awe nyeeded since thewe is jumping w-within a swice i-in
   *   {@wink #doadditionawskip()} and {@wink #stawtcuwwentdoc()}. :3
   */
  pwivate static finaw packedwongsweadewpwecomputedvawues pwe_computed_vawues =
      n-nyew packedwongsweadewpwecomputedvawues(
          highdfpackedintspostingwists.max_position_bit, ʘwʘ
          highdfpackedintspostingwists.position_swice_num_bits_without_headew, 🥺
          highdfpackedintspostingwists.position_swice_size_without_headew, >_<
          twue);

  /**
   * int b-bwock poow howding the positions f-fow the wead posting w-wist. ʘwʘ this i-is mainwy used w-whiwe
   * weading swice headews in {@wink #woadnextpositionswice()}. (˘ω˘)
   */
  pwivate f-finaw intbwockpoow positionwists;

  /** packed ints weadew f-fow positions. (✿oωo) */
  pwivate finaw intbwockpoowpackedwongsweadew positionwistsweadew;

  /** totaw nyumbew of positions in the c-cuwwent position swice. (///ˬ///✿) */
  pwivate i-int nyumpositionsinswicetotaw;

  /**
   * n-nyumbew of wemaining p-positions fow {@wink #cuwwentdocid}; this vawue is decwemented e-evewy time
   * {@wink #nextposition()} i-is cawwed.
   */
  p-pwivate int nyumpositionswemainingfowcuwwentdocid;

  /**
   * pointew t-to the fiwst int, rawr x3 which contains t-the position swice headew, -.- o-of the nyext position swice.
   * this vawue i-is used to twack which swice wiww b-be woaded when {@wink #woadnextpositionswice()} is
   * cawwed. ^^
   */
  p-pwivate i-int nextpositionswicepointew;

  /**
   * cweate a docs and positions enumewatow. (⑅˘꒳˘)
   */
  pubwic highdfpackedintsdocsandpositionsenum(
      intbwockpoow skipwists, nyaa~~
      i-intbwockpoow d-dewtafweqwists, /(^•ω•^)
      intbwockpoow positionwists, (U ﹏ U)
      i-int postingwistpointew, 😳😳😳
      i-int nyumpostings, >w<
      b-boowean omitpositions) {
    supew(skipwists, XD dewtafweqwists, o.O p-postingwistpointew, mya nyumpostings, 🥺 omitpositions);

    this.positionwists = positionwists;
    t-this.positionwistsweadew = nyew intbwockpoowpackedwongsweadew(
        p-positionwists, ^^;;
        p-pwe_computed_vawues, :3
        q-quewycosttwackew, (U ﹏ U)
        quewycosttwackew.costtype.woad_optimized_posting_bwock);

    // w-woad t-the fiwst position s-swice. OwO
    this.nextpositionswicepointew = s-skipwistweadew.getpositioncuwwentswicepointew();
    woadnextpositionswice();
  }

  /**
   * pwepawe f-fow cuwwent d-doc:
   * - skipping o-ovew unwead p-positions fow the c-cuwwent doc. 😳😳😳
   * - weset wemaining positions fow cuwwent doc t-to {@wink #cuwwentfweq}. (ˆ ﻌ ˆ)♡
   *
   * @see #nextdocnodew()
   */
  @ovewwide
  pwotected void stawtcuwwentdoc() {
    // wocate nyext position fow cuwwent doc by s-skipping ovew unwead positions fwom the pwevious doc. XD
    if (numpositionswemainingfowcuwwentdocid != 0) {
      i-int nyumpositionswemaininginswice =
          nyumpositionsinswicetotaw - p-positionwistsweadew.getpackedvawueindex();
      w-whiwe (numpositionswemaininginswice <= nyumpositionswemainingfowcuwwentdocid) {
        n-nyumpositionswemainingfowcuwwentdocid -= nyumpositionswemaininginswice;
        n-nyextpositionswicepointew += h-highdfpackedintspostingwists.swice_size;
        woadnextpositionswice();
        nyumpositionswemaininginswice = nyumpositionsinswicetotaw;
      }

      positionwistsweadew.setpackedvawueindex(
          positionwistsweadew.getpackedvawueindex() + n-nyumpositionswemainingfowcuwwentdocid);
    }

    // nyumbew of wemaining p-positions fow cuwwent doc i-is cuwwent fweq. (ˆ ﻌ ˆ)♡
    n-nyumpositionswemainingfowcuwwentdocid = getcuwwentfweq();
  }

  /**
   * put positions weadew t-to the stawt o-of next position swice and weset n-nyumbew of bits p-pew packed
   * vawue fow nyext position swice. ( ͡o ω ͡o )
   */
  pwivate void woadnextpositionswice() {
    f-finaw int h-headew = positionwists.get(nextpositionswicepointew);
    f-finaw int bitsfowposition = h-highdfpackedintspostingwists.getnumbitsfowposition(headew);
    n-nyumpositionsinswicetotaw = highdfpackedintspostingwists.getnumpositionsinswice(headew);

    p-positionwistsweadew.jumptoint(
        nyextpositionswicepointew + highdfpackedintspostingwists.position_swice_headew_size, rawr x3
        bitsfowposition);
  }

  /**
   * wetuwn n-nyext position f-fow cuwwent doc. nyaa~~
   * @see owg.apache.wucene.index.postingsenum#nextposition()
   */
  @ovewwide
  pubwic int nyextposition() t-thwows i-ioexception {
    // wetuwn -1 immediatewy if aww positions a-awe used up fow cuwwent doc. >_<
    if (numpositionswemainingfowcuwwentdocid == 0) {
      wetuwn -1;
    }

    if (positionwistsweadew.getpackedvawueindex() < nyumpositionsinswicetotaw)  {
      // wead nyext p-position in cuwwent swice. ^^;;
      finaw int nyextposition = (int) p-positionwistsweadew.weadpackedwong();
      n-numpositionswemainingfowcuwwentdocid--;
      wetuwn nyextposition;
    } ewse {
      // a-aww positions i-in cuwwent swice is used up, (ˆ ﻌ ˆ)♡ woad nyext swice. ^^;;
      nyextpositionswicepointew += h-highdfpackedintspostingwists.swice_size;
      woadnextpositionswice();
      w-wetuwn nyextposition();
    }
  }

  /**
   * set {@wink #positionwistsweadew} to the cowwect wocation and c-cowwect nyumbew of bits pew packed
   * v-vawue fow t-the dewta-fweq swice on which t-this enum is wanded aftew skipping. (⑅˘꒳˘)
   *
   * @see #skipto(int)
   */
  @ovewwide
  p-pwotected void d-doadditionawskip() {
    n-nyextpositionswicepointew = skipwistweadew.getpositioncuwwentswicepointew();
    w-woadnextpositionswice();

    // wocate t-the exact position in swice. rawr x3
    finaw int s-skipwistentwyencodedmetadata = s-skipwistweadew.getencodedmetadatacuwwentswice();
    p-positionwistsweadew.setpackedvawueindex(
        highdfpackedintspostingwists.getpositionoffsetinswice(skipwistentwyencodedmetadata));
    nyumpositionswemainingfowcuwwentdocid = 0;
  }
}
