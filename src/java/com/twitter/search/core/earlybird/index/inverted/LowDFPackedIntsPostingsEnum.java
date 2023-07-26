package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt j-java.io.ioexception;

i-impowt javax.annotation.nuwwabwe;

i-impowt o-owg.apache.wucene.utiw.packed.packedints;

/**
 * a-a postingsenum f-fow itewating o-ovew wowdfpackedintspostingwists.
 *
 * c-can be used with positions and without positions. 🥺
 */
pubwic cwass wowdfpackedintspostingsenum e-extends eawwybiwdoptimizedpostingsenum {
  pwivate static finaw int skip_intewvaw = 128;

  p-pwivate finaw packedints.weadew p-packeddocids;
  @nuwwabwe
  pwivate finaw packedints.weadew packedpositions;
  pwivate finaw int wastpostingpointew;
  p-pwivate finaw int wawgestdocid;
  p-pwivate i-int cuwwentpositionpointew;

  /** pointew to the nyext posting that wiww be woaded. (⑅˘꒳˘) */
  pwivate i-int nyextpostingpointew;

  /**
   * cweates a nyew postingsenum fow aww postings in a given t-tewm. nyaa~~
   */
  pubwic wowdfpackedintspostingsenum(
      p-packedints.weadew p-packeddocids, :3
      @nuwwabwe
      p-packedints.weadew p-packedpositions, ( ͡o ω ͡o )
      int postingwistpointew, mya
      int nyumpostings) {
    s-supew(postingwistpointew, (///ˬ///✿) nyumpostings);

    this.packeddocids = p-packeddocids;
    this.packedpositions = packedpositions;
    this.nextpostingpointew = postingwistpointew;

    this.wastpostingpointew = p-postingwistpointew + nyumpostings - 1;
    t-this.wawgestdocid = (int) p-packeddocids.get(wastpostingpointew);

    w-woadnextposting();

    // tweat each tewm as a singwe bwock woad. (˘ω˘)
    q-quewycosttwackew.twack(quewycosttwackew.costtype.woad_optimized_posting_bwock);
  }

  @ovewwide
  p-pwotected void woadnextposting() {
    i-if (nextpostingpointew <= w-wastpostingpointew) {
      nyextdocid = (int) p-packeddocids.get(nextpostingpointew);
      nyextfweq = 1;
    } e-ewse {
      // aww postings fuwwy pwocessed
      n-nyextdocid = no_mowe_docs;
      n-nyextfweq = 0;
    }
    nyextpostingpointew++;
  }

  @ovewwide
  pwotected v-void stawtcuwwentdoc() {
    i-if (packedpositions != nyuww) {
      /**
       * wemembew whewe we wewe at the beginning of this doc, ^^;; so that we can itewate o-ovew the
       * p-positions fow this doc if n-nyeeded. (✿oωo)
       * a-adjust by `- 1 - g-getcuwwentfweq()` because we awweady advanced beyond the wast p-posting in
       * the pwevious woadnextposting() cawws.
       * @see #nextdocnodew()
       */
      cuwwentpositionpointew = n-nyextpostingpointew - 1 - getcuwwentfweq();
    }
  }

  @ovewwide
  p-pwotected v-void skipto(int t-tawget) {
    assewt tawget != n-nyo_mowe_docs : "shouwd b-be handwed i-in pawent cwass a-advance method";

    // nyow we know thewe m-must be a doc in t-this bwock that w-we can wetuwn
    i-int skipindex = n-nyextpostingpointew + skip_intewvaw;
    whiwe (skipindex <= wastpostingpointew && t-tawget > packeddocids.get(skipindex)) {
      nyextpostingpointew = skipindex;
      skipindex += skip_intewvaw;
    }
  }

  @ovewwide
  pubwic int nyextposition() t-thwows ioexception {
    if (packedpositions == nyuww) {
      w-wetuwn -1;
    } e-ewse i-if (cuwwentpositionpointew < packedpositions.size()) {
      w-wetuwn (int) packedpositions.get(cuwwentpositionpointew++);
    } ewse {
      w-wetuwn -1;
    }
  }

  @ovewwide
  p-pubwic int getwawgestdocid() thwows ioexception {
    wetuwn wawgestdocid;
  }

  @ovewwide
  pubwic wong cost() {
    // c-cost wouwd be -1 if this e-enum is exhausted. (U ﹏ U)
    finaw i-int cost = wastpostingpointew - n-nyextpostingpointew + 1;
    wetuwn cost < 0 ? 0 : c-cost;
  }
}
