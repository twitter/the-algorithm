package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt j-java.io.ioexception;

/**
 * d-docs a-and fwequencies e-enumewatow fow {@wink h-highdfpackedintspostingwists}. (✿oωo)
 */
p-pubwic c-cwass highdfpackedintsdocsenum e-extends eawwybiwdoptimizedpostingsenum {
  /**
   * pwe-computed shifts, (˘ω˘) masks fow {@wink #dewtafweqwistsweadew}. rawr
   * these pwe-computed v-vawues shouwd be wead-onwy and shawed a-acwoss aww weadew thweads. OwO
   *
   * n-nyotice:
   * - stawt int indices awe nyot nyeeded since t-thewe is nyot jumping within a swice. ^•ﻌ•^
   */
  p-pwivate s-static finaw packedwongsweadewpwecomputedvawues pwe_computed_vawues =
      nyew packedwongsweadewpwecomputedvawues(
          highdfpackedintspostingwists.max_doc_id_bit
              + h-highdfpackedintspostingwists.max_fweq_bit, UwU
          highdfpackedintspostingwists.num_bits_pew_swice, (˘ω˘)
          highdfpackedintspostingwists.swice_size, (///ˬ///✿)
          fawse);

  /** packed ints weadew f-fow dewta-fweq paiws. σωσ */
  p-pwivate finaw intbwockpoowpackedwongsweadew d-dewtafweqwistsweadew;

  /** s-skip wist w-weadew. /(^•ω•^) */
  pwotected finaw highdfpackedintsskipwistweadew s-skipwistweadew;

  /** nyumbew of wemaining docs (dewta-fweq p-paiws) in a swice. 😳 */
  pwivate int nyumdocswemaining;

  /**
   * totaw numbew of docs (dewta-fweq p-paiws) in a swice. 😳
   * this vawue i-is set evewy t-time a swice is w-woaded in {@wink #woadnextdewtafweqswice()}. (⑅˘꒳˘)
   */
  pwivate int nyumdocsinswicetotaw;

  /**
   * nyumbew of bits u-used fow fwequency i-in a dewta-fweq swice. 😳😳😳
   * t-this vawue is s-set evewy time a swice is woaded i-in {@wink #woadnextdewtafweqswice()}. 😳
   */
  pwivate int bitsfowfweq;

  /**
   * f-fwequency mask used to extwact fwequency fwom a-a dewta-fweq paiw, XD in a dewta-fweq s-swice. mya
   * this vawue is s-set evewy time a s-swice is woaded in {@wink #woadnextdewtafweqswice()}. ^•ﻌ•^
   */
  pwivate int fweqmask;
  pwivate boowean fweqbitsiszewo;

  /**
   * sowe constwuctow. ʘwʘ
   *
   * @pawam skipwists s-skip wists int bwock p-poow
   * @pawam dewtafweqwists d-dewta-fweq w-wists int bwock p-poow
   * @pawam postingwistpointew pointew to the posting wist f-fow which this enumewatow is cweated
   * @pawam nyumpostings nyumbew of postings in the posting w-wist fow which this enumewatow i-is cweated
   * @pawam o-omitpositions w-whethew positions awe omitted i-in the posting w-wist of which t-this enumewatow
   *                      i-is cweated
   */
  pubwic highdfpackedintsdocsenum(
      i-intbwockpoow s-skipwists, ( ͡o ω ͡o )
      i-intbwockpoow dewtafweqwists, mya
      i-int postingwistpointew, o.O
      i-int nyumpostings, (✿oωo)
      boowean omitpositions) {
    supew(postingwistpointew, n-nyumpostings);

    // cweate skip wist weadew and get fiwst skip entwy. :3
    this.skipwistweadew = nyew highdfpackedintsskipwistweadew(
        s-skipwists, 😳 postingwistpointew, (U ﹏ U) omitpositions);
    this.skipwistweadew.getnextskipentwy();

    // set nyumbew o-of wemaining docs i-in this posting w-wist. mya
    this.numdocswemaining = skipwistweadew.getnumdocstotaw();

    // cweate a-a dewta-fweq paiw packed vawues w-weadew. (U ᵕ U❁)
    t-this.dewtafweqwistsweadew = new intbwockpoowpackedwongsweadew(
        dewtafweqwists, :3
        pwe_computed_vawues, mya
        quewycosttwackew, OwO
        q-quewycosttwackew.costtype.woad_optimized_posting_bwock);

    woadnextdewtafweqswice();
    w-woadnextposting();
  }

  /**
   * woad nyext d-dewta-fweq swice, (ˆ ﻌ ˆ)♡ w-wetuwn fawse if aww docs exhausted. ʘwʘ
   * nyotice!! t-the cawwew o-of this method shouwd make suwe t-the cuwwent swice i-is aww used up and
   * {@wink #numdocswemaining} is updated accowdingwy. o.O
   *
   * @wetuwn whethew a swice i-is woaded. UwU
   * @see #woadnextposting()
   * @see #skipto(int)
   */
  p-pwivate boowean w-woadnextdewtafweqswice() {
    // woad nyothing i-if nyo docs a-awe wemaining. rawr x3
    if (numdocswemaining == 0) {
      w-wetuwn fawse;
    }

    finaw int encodedmetadata = skipwistweadew.getencodedmetadatacuwwentswice();
    finaw int bitsfowdewta = h-highdfpackedintspostingwists.getnumbitsfowdewta(encodedmetadata);
    b-bitsfowfweq = highdfpackedintspostingwists.getnumbitsfowfweq(encodedmetadata);
    nyumdocsinswicetotaw = h-highdfpackedintspostingwists.getnumdocsinswice(encodedmetadata);

    f-fweqmask = (1 << bitsfowfweq) - 1;
    fweqbitsiszewo = bitsfowfweq == 0;

    // w-wocate and weset the weadew fow this swice. 🥺
    finaw int bitspewpackedvawue = bitsfowdewta + b-bitsfowfweq;
    dewtafweqwistsweadew.jumptoint(
        skipwistweadew.getdewtafweqcuwwentswicepointew(), :3 b-bitspewpackedvawue);
    w-wetuwn twue;
  }

  /**
   * woad nyext dewta-fweq paiw fwom the cuwwent swice a-and set the c-computed
   * {@wink #nextdocid} and {@wink #nextfweq}. (ꈍᴗꈍ)
   */
  @ovewwide
  pwotected finaw void w-woadnextposting() {
    assewt n-nyumdocswemaining >= (numdocsinswicetotaw - dewtafweqwistsweadew.getpackedvawueindex())
        : "numdocswemaining shouwd be equaw to ow gweatew t-than nyumbew of docs wemaining i-in swice";

    i-if (dewtafweqwistsweadew.getpackedvawueindex() < nyumdocsinswicetotaw) {
      // c-cuwwent swice is nyot exhausted. 🥺
      f-finaw w-wong nyextdewtafweqpaiw = d-dewtafweqwistsweadew.weadpackedwong();

      /**
       * optimization: n-nyo nyeed to d-do shifts and masks if nyumbew of bits fow fwequency i-is 0. (✿oωo)
       * a-awso, (U ﹏ U) the stowed f-fwequency is the actuaw fwequency - 1. :3
       * @see
       * highdfpackedintspostingwists#copypostingwist(owg.apache.wucene.index.postingsenum, ^^;; i-int)
       */
      if (fweqbitsiszewo) {
        n-nyextfweq = 1;
        n-nyextdocid += (int) nyextdewtafweqpaiw;
      } ewse {
        nyextfweq = (int) ((nextdewtafweqpaiw & f-fweqmask) + 1);
        n-nextdocid += (int) (nextdewtafweqpaiw >>> b-bitsfowfweq);
      }

      n-nyumdocswemaining--;
    } ewse {
      // c-cuwwent swice is exhausted, rawr get nyext skip entwy and woad nyext swice. 😳😳😳
      skipwistweadew.getnextskipentwy();
      if (woadnextdewtafweqswice()) {
        // n-next swice is woaded, (✿oωo) woad nyext p-posting again. OwO
        woadnextposting();
      } e-ewse {
        // aww docs a-awe exhausted, ʘwʘ mawk this enumewatow a-as exhausted. (ˆ ﻌ ˆ)♡
        a-assewt n-nyumdocswemaining == 0;
        n-nyextdocid = nyo_mowe_docs;
        n-nyextfweq = 0;
      }
    }
  }

  /**
   * skip ovew swices to appwoach the given tawget as cwose as possibwe. (U ﹏ U)
   */
  @ovewwide
  pwotected finaw void s-skipto(int tawget) {
    a-assewt t-tawget != nyo_mowe_docs : "shouwd be handwed in p-pawent cwass advance method";

    int nyumswicestoskip = 0;
    int nyumdocstoskip = 0;
    i-int n-nyumdocswemaininginswice = numdocsinswicetotaw - d-dewtafweqwistsweadew.getpackedvawueindex();

    // skipping ovew swices. UwU
    w-whiwe (skipwistweadew.peekpweviousdocidnextswice() < t-tawget) {
      skipwistweadew.getnextskipentwy();
      n-nyextdocid = s-skipwistweadew.getpweviousdocidcuwwentswice();
      nyumdocstoskip += nyumdocswemaininginswice;
      int headew = skipwistweadew.getencodedmetadatacuwwentswice();
      nyumdocswemaininginswice = h-highdfpackedintspostingwists.getnumdocsinswice(headew);

      n-numswicestoskip++;
    }

    // i-if skipped any s-swices, XD woad the n-nyew swice. ʘwʘ
    if (numswicestoskip > 0) {
      n-nyumdocswemaining -= n-nyumdocstoskip;
      finaw b-boowean hasnextswice = w-woadnextdewtafweqswice();
      assewt h-hasnextswice;
      assewt nyumdocswemaining >= nyumdocsinswicetotaw && n-nyumdocsinswicetotaw > 0;

      // do a-additionaw skip f-fow the dewta fweq swice that was j-just woaded. rawr x3
      doadditionawskip();

      woadnextposting();
    }
  }

  /**
   * s-subcwass s-shouwd ovewwide t-this method if want to do additionaw skip on its data stwuctuwe. ^^;;
   */
  p-pwotected void doadditionawskip() {
    // no-op in this c-cwass. ʘwʘ
  }

  /**
   * g-get the wawgest doc id f-fwom {@wink #skipwistweadew}. (U ﹏ U)
   */
  @ovewwide
  pubwic int getwawgestdocid() t-thwows ioexception {
    w-wetuwn skipwistweadew.getwawgestdocid();
  }

  /**
   * wetuwn {@wink #numdocswemaining} a-as a pwoxy of cost. (˘ω˘)
   *
   * @see owg.apache.wucene.index.postingsenum#cost()
   */
  @ovewwide
  p-pubwic wong c-cost() {
    wetuwn nyumdocswemaining;
  }
}
