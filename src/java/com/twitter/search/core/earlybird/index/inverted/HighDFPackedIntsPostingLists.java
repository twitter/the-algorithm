package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt j-java.io.ioexception;

i-impowt javax.annotation.nuwwabwe;

i-impowt o-owg.apache.wucene.index.postingsenum;
i-impowt owg.apache.wucene.seawch.docidsetitewatow;

i-impowt c-com.twittew.seawch.common.metwics.seawchcountew;
i-impowt com.twittew.seawch.common.utiw.io.fwushabwe.datadesewiawizew;
impowt com.twittew.seawch.common.utiw.io.fwushabwe.datasewiawizew;
impowt com.twittew.seawch.common.utiw.io.fwushabwe.fwushinfo;
impowt com.twittew.seawch.common.utiw.io.fwushabwe.fwushabwe;

/**
 * a-an optimized posting wists impwementation s-stowing doc dewtas, doc f-fweqs, 😳😳😳 and positions as packed
 * ints in a 64 ints swice backed b-by {@wink intbwockpoow}. ʘwʘ
 *
 * thewe awe thwee i-innew data stwuctuwes u-used to stowe vawues used by a posting wists instance:
 *
 * - skip wists, /(^•ω•^) u-used fow fast {@wink postingsenum#advance(int)}, awe stowed in {@wink #skipwists}
 *   int bwock poow. :3
 * - doc d-dewtas and fweqs awe stowed in {@wink #dewtafweqwists} i-int bwock p-poow. :3
 * - positions a-awe stowed i-in {@wink #positionwists} int bwock poow. mya
 *
 * f-fow detaiw wayout and configuwation, (///ˬ///✿) pwease wefew t-to the javadoc of {@wink #skipwists}, (⑅˘꒳˘)
 * {@wink #dewtafweqwists} and {@wink #positionwists}. :3
 *
 * <b>this impwementation designed fow posting wists with a w-wawge nyumbew of postings.</b>
 *
 * <i>acknowwedgement</i>: t-the c-concepts of swice b-based packed ints encoding/decoding is bowwowed
 *                         fwom {@code h-highdfcompwessedpostingwists}, /(^•ω•^) w-which wiww be depwecated d-due
 *                         t-to nyot suppowting positions that a-awe gweatew than 255. ^^;;
 */
pubwic c-cwass highdfpackedintspostingwists extends optimizedpostingwists {
  /**
   * a countew used t-to twack when positions enum is w-wequiwed and a posting wists instance i-is set
   * t-to omit positions. (U ᵕ U❁)
   *
   * @see #postings(int, (U ﹏ U) int, mya int)
   */
  pwivate static finaw seawchcountew getting_positions_with_omit_positions =
      seawchcountew.expowt(
          "high_df_packed_ints_posting_wist_getting_positions_with_omit_positions");

  /**
   * infowmation w-wewated t-to size of a swice. ^•ﻌ•^
   */
  static f-finaw int swice_size_bit = 6;
  s-static finaw i-int swice_size = 1 << swice_size_bit;                 //   64 ints pew bwock
  static finaw int n-nyum_bits_pew_swice = swice_size * integew.size;   // 2048 bits pew bwock

  /**
   * a-a skip wist has one skip w-wist headew that c-contains 5 ints (4 i-ints if positions awe omitted):
   * - 1st i-int: nyumbew of s-skip entwies in t-this skip wist. (U ﹏ U)
   * - 2nd i-int: wawgest doc id in this posting wist. :3
   * - 3wd i-int: nyumbew of d-docs in this posting w-wist. rawr x3
   * - 4th i-int: pointew t-to the stawt of the dewta-fweq wist of this posting wist. 😳😳😳
   * - 5th i-int: (optionaw) pointew to the stawt of the position wist of this posting wist. >w<
   */
  s-static finaw int skipwist_headew_size = 5;
  static finaw int skipwist_headew_size_without_positions = s-skipwist_headew_size - 1;

  /**
   * a-a skip w-wist has many skip entwies. òωó e-each skip entwy is fow one swice i-in dewta-fweq wist. 😳
   * t-thewe awe 3 ints in evewy skip entwy (2 ints if positions awe omitted):
   * - 1st int: w-wast doc id in pwevious swice (0 f-fow the fiwst swice), (✿oωo) this is m-mainwy used duwing
   *            s-skipping because dewtas, OwO nyot absowute doc ids, (U ﹏ U) a-awe stowed in a-a swice. (ꈍᴗꈍ)
   * - 2nd int: encoded m-metadata of the c-cowwesponding dewta-fweq swice. rawr thewe awe 4 piece of
   *            infowmation f-fwom the wowest b-bits to highest b-bits of this int:
   *            11 b-bits: nyumbew o-of docs (dewta-fweq paiws) i-in this swice. ^^
   *             5 bits: nyumbew of bits used to encode each fweq. rawr
   *             5 bits: nyumbew o-of bits used t-to encode each dewta. nyaa~~
   *            11 bits: p-position swice o-offset: an index of nyumbew of positions; this is whewe the
   *                     f-fiwst position of the fiwst doc (in this dewta-fweq swice) is in the
   *                     p-position swice. nyaa~~ the position swice is identified b-by the 3wd int b-bewow. o.O
   *                     these two piece infowmation uniquewy identified t-the wocation o-of the stawt
   *                     position of this dewta-fweq swice. òωó this vawue i-is awways 0 if position is
   *                     o-omitted. ^^;;
   * - 3wd int: (optionaw) position swice index: a-an index of of nyumbew of swices; t-this vawue
   *            identifies t-the swice in which the f-fiwst position of the fiwst doc (in t-this
   *            d-dewta-fweq s-swice) exists. rawr the exact wocation i-inside the p-position swice is identified
   *            by position swice o-offset that is s-stowed in the 2nd i-int above. ^•ﻌ•^
   *            nyotice: this is nyot t-the absowute addwess in the bwock p-poow, nyaa~~ but instead a-a wewative
   *            offset (in nyumbew of swices) on top of this tewm's f-fiwst position s-swice.
   *            t-this v-vawue does nyot exist if position i-is omitted. nyaa~~
   */
  static finaw int skipwist_entwy_size = 3;
  static finaw int skipwist_entwy_size_without_positions = skipwist_entwy_size - 1;

  /**
   * s-shifts and masks used to encode/decode m-metadata fwom the 2nd int o-of a skip wist entwy. 😳😳😳
   * @see #skipwist_entwy_size
   * @see #encodeskipwistentwymetadata(int, 😳😳😳 i-int, int, int)
   * @see #getnumbitsfowdewta(int)
   * @see #getnumbitsfowfweq(int)
   * @see #getnumdocsinswice(int)
   * @see #getpositionoffsetinswice(int)
   */
  static f-finaw int skipwist_entwy_position_offset_shift = 21;
  s-static finaw i-int skipwist_entwy_num_bits_dewta_shift = 16;
  s-static finaw i-int skipwist_entwy_num_bits_fweq_shift = 11;
  static finaw int skipwist_entwy_position_offset_mask = (1 << 11) - 1;
  static finaw int skipwist_entwy_num_bits_dewta_mask = (1 << 5) - 1;
  static finaw int skipwist_entwy_num_bits_fweq_mask = (1 << 5) - 1;
  s-static finaw i-int skipwist_entwy_num_docs_mask = (1 << 11) - 1;

  /**
   * each p-position swice has a headew t-that is the 1st int in this position swice. fwom wowest bits
   * t-to highest bits, σωσ t-thewe awe 2 pieces of infowmation e-encoded in this singwe int:
   * 11 bits: nyumbew o-of positions i-in this swice. o.O
   *  5 bits: n-nyumbew of bits u-used to encode each position. σωσ
   */
  static finaw int position_swice_headew_size = 1;

  /**
   * infowmation w-wewated to size o-of a position swice. nyaa~~ t-the actuaw s-size is the same a-as
   * {@wink #swice_size}, rawr x3 but t-thewe is 1 int u-used fow position swice headew. (///ˬ///✿)
   */
  s-static f-finaw int position_swice_size_without_headew = swice_size - position_swice_headew_size;
  s-static finaw int position_swice_num_bits_without_headew =
      position_swice_size_without_headew * integew.size;

  /**
   * s-shifts and masks used to e-encode/decode m-metadata fwom the position swice h-headew. o.O
   * @see #position_swice_headew_size
   * @see #encodepositionentwyheadew(int, òωó int)
   * @see #getnumpositionsinswice(int)
   * @see #getnumbitsfowposition(int)
   */
  static finaw i-int position_swice_headew_bits_position_shift = 11;
  s-static finaw i-int position_swice_headew_bits_position_mask = (1 << 5) - 1;
  static finaw int position_swice_headew_num_positions_mask = (1 << 11) - 1;

  /**
   * stowes s-skip wist fow each posting wist. OwO
   *
   * a skip w-wist consists o-of one skip wist headew and many s-skip wist entwies, and each skip e-entwy
   * cowwesponds t-to one dewta-fweq swice. σωσ awso, unwike {@wink #dewtafweqwists} a-and
   * {@wink #positionwists}, nyaa~~ vawues in skip wists int p-poow awe nyot stowed i-in unit of swices. OwO
   *
   * e-exampwe:
   * h: skip wist headew i-int
   * e: s-skip wist entwy i-int
   * ': int boundawy
   * |: headew/entwy boundawy (awso a boundawy of int)
   *
   *  <----- skip wist a -----> <- skip wist b ->
   * |h'h'h'h'h|e'e|e'e|e'e|e'e|h'h'h'h'h|e'e|e'e|
   */
  pwivate finaw intbwockpoow skipwists;

  /**
   * stowes dewta-fweq wist fow each posting wist. ^^
   *
   * a-a dewta-fweq w-wist consists of many 64-int swices, (///ˬ///✿) and d-dewta-fweq paiws a-awe stowed compactwy
   * w-with a fixed nyumbew o-of bits within a singwe swice. σωσ e-each swice has a-a cowwesponding skip wist
   * e-entwy in {@wink #skipwists} stowing m-metadata about t-this swice. rawr x3
   *
   * exampwe:
   * |: swice b-boundawy
   *
   *  <----------------- d-dewta-fweq w-wist a -----------------> <--- d-dewta-fweq wist b-b --->
   * |64 i-ints swice|64 ints s-swice|64 ints s-swice|64 ints s-swice|64 ints swice|64 ints swice|
   */
  p-pwivate f-finaw intbwockpoow d-dewtafweqwists;

  /**
   * stowes position w-wist fow each posting wist. (ˆ ﻌ ˆ)♡
   *
   * a position w-wist consists of many 64 ints s-swices, 🥺 and positions a-awe stowed c-compactwy with a
   * fixed nyumbew o-of bits within a singwe swice. (⑅˘꒳˘) t-the fiwst int in each swice i-is used as a headew to
   * stowe t-the metadata about this position swice. 😳😳😳
   *
   * exampwe:
   * h: position headew i-int
   * ': int boundawy
   * |: s-swice boundawy
   *
   *  <--------------- p-position wist a ---------------> <---------- position wist b ---------->
   * |h'63 ints|h'63 i-ints|h'63 ints|h'63 ints|h'63 ints|h'63 i-ints|h'63 i-ints|h'63 ints|h'63 i-ints|
   */
  pwivate finaw intbwockpoow positionwists;

  /**
   * w-whethew p-positions awe omitted in this o-optimized posting wists. /(^•ω•^)
   */
  pwivate finaw boowean o-omitpositions;

  /**
   * skip wist headew a-and entwy size f-fow this posting w-wists, >w< couwd be diffewent depends o-on whethew
   * p-position is o-omitted ow nyot. ^•ﻌ•^
   *
   * @see #skipwist_headew_size
   * @see #skipwist_headew_size_without_positions
   * @see #skipwist_entwy_size
   * @see #skipwist_entwy_size_without_positions
   */
  p-pwivate finaw int skipwistheadewsize;
  p-pwivate f-finaw int skipwistentwysize;

  /**
   * b-buffew u-used in {@wink #copypostingwist(postingsenum, 😳😳😳 int)}
   * t-to queue u-up vawues nyeeded f-fow a swice. :3
   * w-woaded posting wists have t-them set as nyuww. (ꈍᴗꈍ)
   */
  pwivate f-finaw postingsbuffewqueue docfweqqueue;
  p-pwivate f-finaw postingsbuffewqueue p-positionqueue;

  /**
   * packed ints wwitew used to wwite into d-dewta-fweq int p-poow and position i-int poow. ^•ﻌ•^
   * woaded posting wists have them set as nyuww.
   */
  p-pwivate finaw i-intbwockpoowpackedwongswwitew dewtafweqwistswwitew;
  p-pwivate f-finaw intbwockpoowpackedwongswwitew positionwistswwitew;

  /**
   * defauwt constwuctow. >w<
   *
   * @pawam omitpositions w-whethew p-positions wiww b-be omitted in t-these posting wists. ^^;;
   */
  pubwic highdfpackedintspostingwists(boowean o-omitpositions) {
    t-this(
        nyew intbwockpoow("high_df_packed_ints_skip_wists"), (✿oωo)
        n-nyew intbwockpoow("high_df_packed_ints_dewta_fweq_wists"), òωó
        nyew intbwockpoow("high_df_packed_ints_position_wists"), ^^
        o-omitpositions, ^^
        nyew postingsbuffewqueue(num_bits_pew_swice), rawr
        n-nyew postingsbuffewqueue(position_swice_num_bits_without_headew));
  }

  /**
   * c-constwuctows used by w-woadew. XD
   *
   * @pawam s-skipwists woaded int b-bwock poow wepwesents skip wists
   * @pawam d-dewtafweqwists w-woaded i-int bwock poow w-wepwesents dewta-fweq wists
   * @pawam p-positionwists w-woaded int b-bwock poow wepwesents position w-wists
   * @pawam omitpositions whethew positions w-wiww be omitted i-in these posting w-wists
   * @pawam docfweqqueue buffew used to queue up vawues used fow a doc f-fweq swice, rawr nyuww if woaded
   * @pawam p-positionqueue b-buffew used to queue up vawues used fow a-a position swice, 😳 nyuww if woaded
   * @see f-fwushhandwew#dowoad(fwushinfo, 🥺 d-datadesewiawizew)
   */
  p-pwivate highdfpackedintspostingwists(
      i-intbwockpoow skipwists, (U ᵕ U❁)
      intbwockpoow d-dewtafweqwists, 😳
      intbwockpoow positionwists, 🥺
      boowean omitpositions, (///ˬ///✿)
      @nuwwabwe postingsbuffewqueue docfweqqueue, mya
      @nuwwabwe postingsbuffewqueue p-positionqueue) {
    this.skipwists = s-skipwists;
    this.dewtafweqwists = dewtafweqwists;
    this.positionwists = p-positionwists;
    this.omitpositions = omitpositions;

    this.docfweqqueue = docfweqqueue;
    t-this.positionqueue = p-positionqueue;

    // docfweqqueue i-is nyuww if this postingwists is woaded, (✿oωo)
    // w-we don't nyeed to c-cweate wwitew at that case. ^•ﻌ•^
    i-if (docfweqqueue == nyuww) {
      a-assewt positionqueue == nyuww;
      this.dewtafweqwistswwitew = nyuww;
      t-this.positionwistswwitew = nyuww;
    } ewse {
      t-this.dewtafweqwistswwitew = n-new intbwockpoowpackedwongswwitew(dewtafweqwists);
      t-this.positionwistswwitew = nyew intbwockpoowpackedwongswwitew(positionwists);
    }

    if (omitpositions) {
      s-skipwistheadewsize = skipwist_headew_size_without_positions;
      skipwistentwysize = skipwist_entwy_size_without_positions;
    } ewse {
      s-skipwistheadewsize = s-skipwist_headew_size;
      s-skipwistentwysize = s-skipwist_entwy_size;
    }
  }

  /**
   * a simpwe wwappew awound assowted s-states used when c-coping positions in a posting enum. o.O
   * @see #copypostingwist(postingsenum, o.O i-int)
   */
  pwivate static cwass positionsstate {
    /** m-max position has been seen fow the cuwwent p-position s-swice. XD */
    pwivate int maxposition = 0;

    /** b-bits nyeeded t-to encode/decode p-positions in the cuwwent position swice. ^•ﻌ•^ */
    p-pwivate int bitsneededfowposition = 0;

    /** totaw nyumbew of position swices c-cweated fow cuwwent posting wist. ʘwʘ */
    pwivate int nyumpositionsswices = 0;

    /**
     * w-whenevew a swice o-of doc/fweq paiws i-is wwitten, (U ﹏ U) t-this wiww point t-to the fiwst position
     * associated w-with the fiwst doc in the doc/fweq swice. 😳😳😳
     */
    p-pwivate int cuwwentpositionsswiceindex = 0;
    p-pwivate int cuwwentpositionsswiceoffset = 0;

    /**
     * whenevew a-a nyew document i-is pwocessed, 🥺 this points to t-the fiwst position fow this doc.
     * t-this is u-used if this doc ends up being c-chosen as the fiwst d-doc in a doc/fweq swice. (///ˬ///✿)
     */
    p-pwivate int nyextpositionsswiceindex = 0;
    pwivate int nyextpositionsswiceoffset = 0;
  }

  /**
   * c-copies postings in the given postings e-enum into this posting wists instance. (˘ω˘)
   *
   * @pawam p-postingsenum enumewatow o-of the posting w-wist that nyeeds to be copied
   * @pawam n-nyumpostings nyumbew o-of postings in the posting w-wist that nyeeds to be copied
   * @wetuwn p-pointew to the copied p-posting wist in t-this posting wists instance
   */
  @ovewwide
  pubwic int copypostingwist(postingsenum postingsenum, :3 int nyumpostings) t-thwows i-ioexception {
    assewt docfweqqueue.isempty() : "each nyew posting wist shouwd s-stawt with an empty queue";
    a-assewt positionqueue.isempty() : "each n-nyew posting wist shouwd stawt with an empty queue";

    finaw int skipwistpointew = skipwists.wength();
    f-finaw int dewtafweqwistpointew = dewtafweqwists.wength();
    f-finaw int positionwistpointew = positionwists.wength();
    a-assewt isswicestawt(dewtafweqwistpointew) : "each n-nyew posting wist shouwd stawt a-at a nyew swice";
    a-assewt isswicestawt(positionwistpointew) : "each n-nyew posting w-wist shouwd s-stawt at a nyew s-swice";

    // make woom fow skip wist headew. /(^•ω•^)
    fow (int i = 0; i < skipwistheadewsize; i++) {
      s-skipwists.add(-1);
    }

    i-int doc;
    i-int pwevdoc = 0;
    i-int pwevwwittendoc = 0;

    i-int maxdewta = 0;
    i-int maxfweq = 0;

    int bitsneededfowdewta = 0;
    int bitsneededfowfweq = 0;

    // keep twacking p-positions wewated i-info fow this posting wist. :3
    positionsstate positionsstate = n-nyew positionsstate();

    i-int numdocs = 0;
    i-int nyumdewtafweqswices = 0;
    whiwe ((doc = postingsenum.nextdoc()) != d-docidsetitewatow.no_mowe_docs) {
      nyumdocs++;

      int d-dewta = doc - pwevdoc;
      a-assewt dewta <= max_doc_id;

      int nyewbitsfowdewta = b-bitsneededfowdewta;
      if (dewta > maxdewta) {
        m-maxdewta = dewta;
        n-nyewbitsfowdewta = wog(maxdewta, mya 2);
        a-assewt nyewbitsfowdewta <= m-max_doc_id_bit;
      }

      /**
       * optimization: s-stowe f-fweq - 1 since a-a fweq must be p-positive. XD save bits and impwove d-decoding
       * s-speed. (///ˬ///✿) at wead side, 🥺 the wead f-fwequency wiww pwus 1. o.O
       * @see highdfpackedintsdocsenum#woadnextposting()
       */
      i-int fweq = postingsenum.fweq() - 1;
      assewt f-fweq >= 0;

      int nyewbitsfowfweq = b-bitsneededfowfweq;
      i-if (fweq > maxfweq) {
        maxfweq = fweq;
        nyewbitsfowfweq = w-wog(maxfweq, mya 2);
        assewt nyewbitsfowfweq <= max_fweq_bit;
      }

      // w-wwite p-positions fow this doc if nyot omit positions. rawr x3
      i-if (!omitpositions) {
        w-wwitepositionsfowdoc(postingsenum, 😳 positionsstate);
      }

      i-if ((newbitsfowdewta + nyewbitsfowfweq) * (docfweqqueue.size() + 1) > nyum_bits_pew_swice) {
        //the w-watest doc d-does nyot fit into this swice. 😳😳😳
        a-assewt (bitsneededfowdewta + b-bitsneededfowfweq) * docfweqqueue.size()
            <= nyum_bits_pew_swice;

        p-pwevwwittendoc = w-wwitedewtafweqswice(
            b-bitsneededfowdewta, >_<
            b-bitsneededfowfweq, >w<
            positionsstate, rawr x3
            pwevwwittendoc);
        nyumdewtafweqswices++;

        maxdewta = dewta;
        maxfweq = fweq;
        b-bitsneededfowdewta = w-wog(maxdewta, XD 2);
        b-bitsneededfowfweq = w-wog(maxfweq, ^^ 2);
      } e-ewse {
        b-bitsneededfowdewta = nyewbitsfowdewta;
        b-bitsneededfowfweq = n-nyewbitsfowfweq;
      }

      docfweqqueue.offew(doc, (✿oωo) f-fweq);

      p-pwevdoc = doc;
    }

    // some positions m-may be weft in the buffew queue. >w<
    if (!positionqueue.isempty()) {
      w-wwitepositionswice(positionsstate.bitsneededfowposition);
    }

    // some docs may b-be weft in the b-buffew queue.
    if (!docfweqqueue.isempty()) {
      w-wwitedewtafweqswice(
          b-bitsneededfowdewta,
          b-bitsneededfowfweq, 😳😳😳
          positionsstate, (ꈍᴗꈍ)
          p-pwevwwittendoc);
      n-nyumdewtafweqswices++;
    }

    // wwite skip w-wist headew. (✿oωo)
    int skipwistheadewpointew = s-skipwistpointew;
    f-finaw int n-nyumskipwistentwies =
        (skipwists.wength() - (skipwistpointew + skipwistheadewsize)) / s-skipwistentwysize;
    assewt nyumskipwistentwies == nyumdewtafweqswices
        : "numbew o-of dewta fweq swices shouwd be the same as nyumbew of skip wist entwies";
    skipwists.set(skipwistheadewpointew++, (˘ω˘) nyumskipwistentwies);
    s-skipwists.set(skipwistheadewpointew++, nyaa~~ pwevdoc);
    skipwists.set(skipwistheadewpointew++, ( ͡o ω ͡o ) nyumdocs);
    skipwists.set(skipwistheadewpointew++, 🥺 dewtafweqwistpointew);
    if (!omitpositions) {
      skipwists.set(skipwistheadewpointew, (U ﹏ U) p-positionwistpointew);
    }

    wetuwn skipwistpointew;
  }

  /**
   * wwite positions fow c-cuwwent doc into {@wink #positionwists}. ( ͡o ω ͡o )
   *
   * @pawam postingsenum p-postings enumewatow containing the positions n-nyeed to be wwitten
   * @pawam p-positionsstate some states a-about {@wink #positionwists} and {@wink #positionqueue}
   * @see #copypostingwist(postingsenum, (///ˬ///✿) i-int)
   */
  pwivate void wwitepositionsfowdoc(
      postingsenum p-postingsenum, (///ˬ///✿)
      positionsstate positionsstate) thwows i-ioexception {
    assewt !omitpositions : "this m-method shouwd nyot be cawwed if p-positions awe omitted";

    fow (int i-i = 0; i < p-postingsenum.fweq(); i++) {
      int pos = postingsenum.nextposition();

      i-int nyewbitsfowposition = positionsstate.bitsneededfowposition;
      if (pos > p-positionsstate.maxposition) {
        positionsstate.maxposition = pos;
        newbitsfowposition = wog(positionsstate.maxposition, (✿oωo) 2);
        a-assewt nyewbitsfowposition <= m-max_position_bit;
      }

      if (newbitsfowposition * (positionqueue.size() + 1)
          > p-position_swice_num_bits_without_headew
          || p-positionqueue.isfuww()) {
        assewt positionsstate.bitsneededfowposition * p-positionqueue.size()
            <= position_swice_num_bits_without_headew;

        wwitepositionswice(positionsstate.bitsneededfowposition);
        positionsstate.numpositionsswices++;

        positionsstate.maxposition = p-pos;
        p-positionsstate.bitsneededfowposition = wog(positionsstate.maxposition, (U ᵕ U❁) 2);
      } e-ewse {
        p-positionsstate.bitsneededfowposition = nyewbitsfowposition;
      }

      // u-update fiwst position pointew if this position i-is the fiwst position of a doc
      if (i == 0) {
        p-positionsstate.nextpositionsswiceindex = p-positionsstate.numpositionsswices;
        positionsstate.nextpositionsswiceoffset = positionqueue.size();
      }

      // s-stowes a dummy doc -1 since doc is unused in position wist. ʘwʘ
      positionqueue.offew(-1, pos);
    }
  }

  /**
   * wwite out aww the buffewed p-positions in {@wink #positionqueue} i-into a position swice. ʘwʘ
   *
   * @pawam b-bitsneededfowposition n-nyumbew of bits used fow e-each position in this position swice
   */
  pwivate void wwitepositionswice(finaw int bitsneededfowposition) {
    assewt !omitpositions;
    assewt 0 <= b-bitsneededfowposition && bitsneededfowposition <= max_position_bit;

    finaw int wengthbefowe = positionwists.wength();
    a-assewt i-isswicestawt(wengthbefowe);

    // f-fiwst int in this swice stowes nyumbew of bits nyeeded fow position
    // and n-nyumbew of positions i-in this s-swice..
    positionwists.add(encodepositionentwyheadew(bitsneededfowposition, XD positionqueue.size()));

    positionwistswwitew.jumptoint(positionwists.wength(), (✿oωo) b-bitsneededfowposition);
    whiwe (!positionqueue.isempty()) {
      i-int pos = postingsbuffewqueue.getsecondvawue(positionqueue.poww());
      a-assewt wog(pos, ^•ﻌ•^ 2) <= bitsneededfowposition;

      p-positionwistswwitew.wwitepackedint(pos);
    }

    // fiww up this swice in c-case it is onwy pawtiawwy fiwwed. ^•ﻌ•^
    w-whiwe (positionwists.wength() < w-wengthbefowe + swice_size) {
      p-positionwists.add(0);
    }

    a-assewt positionwists.wength() - w-wengthbefowe == swice_size;
  }

  /**
   * w-wwite out aww the buffewed d-docs and fwequencies i-in {@wink #docfweqqueue} into a dewta-fweq
   * swice and u-update the skip wist entwy of this swice. >_<
   *
   * @pawam bitsneededfowdewta nyumbew of bits used fow each dewta in this dewta-fweq swice
   * @pawam b-bitsneededfowfweq nyumbew of bits used f-fow each fweq in this dewta-fweq s-swice
   * @pawam positionsstate some states about {@wink #positionwists} a-and {@wink #positionqueue}
   * @pawam pwevwwittendoc wast doc wwitten i-in pwevious swice
   * @wetuwn wast doc wwitten in this swice
   */
  p-pwivate int wwitedewtafweqswice(
      finaw int bitsneededfowdewta, mya
      f-finaw int bitsneededfowfweq, σωσ
      finaw positionsstate positionsstate, rawr
      f-finaw int pwevwwittendoc) {
    a-assewt 0 <= bitsneededfowdewta && bitsneededfowdewta <= max_doc_id_bit;
    a-assewt 0 <= b-bitsneededfowfweq && bitsneededfowfweq <= m-max_fweq_bit;

    f-finaw int wengthbefowe = dewtafweqwists.wength();
    assewt i-isswicestawt(wengthbefowe);

    wwiteskipwistentwy(pwevwwittendoc, (✿oωo) bitsneededfowdewta, :3 bitsneededfowfweq, p-positionsstate);

    // keep twack of pwevious docid so that we compute t-the docid d-dewtas. rawr x3
    int p-pwevdoc = pwevwwittendoc;

    // a <dewta|fweq> paiw is stowed as a packed vawue. ^^
    f-finaw int bitspewpackedvawue = b-bitsneededfowdewta + bitsneededfowfweq;
    d-dewtafweqwistswwitew.jumptoint(dewtafweqwists.wength(), ^^ b-bitspewpackedvawue);
    whiwe (!docfweqqueue.isempty()) {
      wong vawue = docfweqqueue.poww();
      int doc = postingsbuffewqueue.getdocid(vawue);
      int dewta = d-doc - pwevdoc;
      a-assewt wog(dewta, OwO 2) <= bitsneededfowdewta;

      i-int fweq = postingsbuffewqueue.getsecondvawue(vawue);
      assewt w-wog(fweq, ʘwʘ 2) <= b-bitsneededfowfweq;

      // c-cast t-the dewta to wong b-befowe weft s-shift to avoid ovewfwow. /(^•ω•^)
      finaw wong dewtafweqpaiw = (((wong) dewta) << bitsneededfowfweq) + f-fweq;
      dewtafweqwistswwitew.wwitepackedwong(dewtafweqpaiw);
      p-pwevdoc = d-doc;
    }

    // f-fiww up this s-swice in case i-it is onwy pawtiawwy fiwwed. ʘwʘ
    w-whiwe (dewtafweqwists.wength() <  w-wengthbefowe + s-swice_size) {
      dewtafweqwists.add(0);
    }

    positionsstate.cuwwentpositionsswiceindex = p-positionsstate.nextpositionsswiceindex;
    positionsstate.cuwwentpositionsswiceoffset = positionsstate.nextpositionsswiceoffset;

    a-assewt dewtafweqwists.wength() - wengthbefowe == s-swice_size;
    w-wetuwn pwevdoc;
  }

  /**
   * wwite the skip wist e-entwy fow a dewta-fweq s-swice. (⑅˘꒳˘)
   *
   * @pawam pwevwwittendoc wast d-doc wwitten i-in pwevious swice
   * @pawam bitsneededfowdewta nyumbew of bits used fow each dewta i-in this dewta-fweq s-swice
   * @pawam bitsneededfowfweq nyumbew o-of bits used f-fow each fweq in this dewta-fweq swice
   * @pawam p-positionsstate some states about {@wink #positionwists} and {@wink #positionqueue}
   * @see #wwitedewtafweqswice(int, UwU int, positionsstate, int)
   * @see #skipwist_entwy_size
   */
  p-pwivate void wwiteskipwistentwy(
      int pwevwwittendoc, -.-
      i-int b-bitsneededfowdewta, :3
      i-int bitsneededfowfweq, >_<
      positionsstate p-positionsstate) {
    // 1st i-int: wast wwitten d-doc id in p-pwevious swice
    s-skipwists.add(pwevwwittendoc);

    // 2nd int: encoded metadata
    s-skipwists.add(
        encodeskipwistentwymetadata(
            p-positionsstate.cuwwentpositionsswiceoffset, nyaa~~
            b-bitsneededfowdewta, ( ͡o ω ͡o )
            bitsneededfowfweq, o.O
            docfweqqueue.size()));

    // 3wd i-int: optionaw, :3 p-position swice i-index
    if (!omitpositions) {
      skipwists.add(positionsstate.cuwwentpositionsswiceindex);
    }
  }

  /**
   * c-cweate and w-wetuwn a docs enumewatow o-ow docs-positions e-enumewatow b-based on input fwag. (˘ω˘)
   *
   * @see o-owg.apache.wucene.index.postingsenum
   */
  @ovewwide
  pubwic eawwybiwdpostingsenum p-postings(
      i-int postingwistpointew, rawr x3 int nyumpostings, (U ᵕ U❁) int fwags) thwows ioexception {
    // p-positions awe o-omitted but position enumewatow a-awe wequwied. 🥺
    i-if (omitpositions && postingsenum.featuwewequested(fwags, >_< postingsenum.positions)) {
      g-getting_positions_with_omit_positions.incwement();
    }

    i-if (!omitpositions && p-postingsenum.featuwewequested(fwags, :3 p-postingsenum.positions)) {
      w-wetuwn nyew h-highdfpackedintsdocsandpositionsenum(
          skipwists,
          dewtafweqwists, :3
          p-positionwists, (ꈍᴗꈍ)
          postingwistpointew, σωσ
          nyumpostings, 😳
          fawse);
    } ewse {
      wetuwn n-nyew highdfpackedintsdocsenum(
          s-skipwists, mya
          dewtafweqwists, (///ˬ///✿)
          postingwistpointew, ^^
          nyumpostings, (✿oωo)
          o-omitpositions);
    }
  }

  /******************************************************
   * s-skip wist entwy encoded data encoding a-and decoding *
   ******************************************************/

  /**
   * encode a s-skip wist entwy m-metadata, ( ͡o ω ͡o ) which i-is stowed in the 2nd int of the skip wist entwy. ^^;;
   *
   * @see #skipwist_entwy_size
   */
  pwivate s-static int encodeskipwistentwymetadata(
      i-int positionoffsetinswice, :3 int n-nyumbitsfowdewta, 😳 int nyumbitsfowfweq, XD int nyumdocsinswice) {
    a-assewt 0 <= positionoffsetinswice
        && p-positionoffsetinswice < position_swice_num_bits_without_headew;
    assewt 0 <= n-nyumbitsfowdewta && nyumbitsfowdewta <= m-max_doc_id_bit;
    assewt 0 <= nyumbitsfowfweq && nyumbitsfowfweq <= max_fweq_bit;
    assewt 0 < nyumdocsinswice && nyumdocsinswice <= nyum_bits_pew_swice;
    wetuwn (positionoffsetinswice << s-skipwist_entwy_position_offset_shift)
        + (numbitsfowdewta << s-skipwist_entwy_num_bits_dewta_shift)
        + (numbitsfowfweq << s-skipwist_entwy_num_bits_fweq_shift)
        // s-stowes nyumdocsinswice - 1 to avoid ovew fwow since n-nyumdocsinswice wanges in [1, (///ˬ///✿) 2048]
        // and 11 bits awe used to stowe n-nyumbew docs in s-swice
        + (numdocsinswice - 1);
  }

  /**
   * d-decode position_swice_offset o-of the dewta-fweq swice having the given skip entwy encoded data. o.O
   *
   * @see #skipwist_entwy_size
   */
  s-static int getpositionoffsetinswice(int s-skipwistentwyencodedmetadata) {
    wetuwn (skipwistentwyencodedmetadata >>> skipwist_entwy_position_offset_shift)
        & skipwist_entwy_position_offset_mask;
  }

  /**
   * decode n-nyumbew of bits used fow dewta i-in the swice h-having the given s-skip entwy encoded data. o.O
   *
   * @see #skipwist_entwy_size
   */
  static int getnumbitsfowdewta(int skipwistentwyencodedmetadata) {
    wetuwn (skipwistentwyencodedmetadata >>> s-skipwist_entwy_num_bits_dewta_shift)
        & skipwist_entwy_num_bits_dewta_mask;
  }

  /**
   * d-decode nyumbew of bits used fow fweqs in the swice having t-the given skip entwy encoded data. XD
   *
   * @see #skipwist_entwy_size
   */
  s-static int getnumbitsfowfweq(int skipwistentwyencodedmetadata) {
    wetuwn (skipwistentwyencodedmetadata >>> skipwist_entwy_num_bits_fweq_shift)
        & s-skipwist_entwy_num_bits_fweq_mask;
  }

  /**
   * d-decode nyumbew of d-dewta-fweq paiws s-stowed in the s-swice having the given skip entwy e-encoded data. ^^;;
   *
   * @see #skipwist_entwy_size
   */
  s-static int getnumdocsinswice(int s-skipwistentwyencodedmetadata) {
    /**
     * add 1 to the decode v-vawue since the stowed vawue is s-subtwacted by 1. 😳😳😳
     * @see #encodeskipwistentwymetadata(int, (U ᵕ U❁) i-int, /(^•ω•^) int, int)
     */
    wetuwn (skipwistentwyencodedmetadata & s-skipwist_entwy_num_docs_mask) + 1;
  }

  /*****************************************************
   * p-position swice entwy headew encoding and decoding *
   *****************************************************/

  /**
   * e-encode a position s-swice entwy h-headew.
   *
   * @pawam n-nyumbitsfowposition nyumbew of bits used to encode positions i-in this swice. 😳😳😳
   * @pawam nyumpositionsinswice nyumbew of p-positions in this swice. rawr x3
   * @wetuwn an int as t-the encoded headew. ʘwʘ
   * @see #position_swice_headew_size
   */
  pwivate static int encodepositionentwyheadew(int numbitsfowposition, UwU i-int nyumpositionsinswice) {
    assewt 0 <= n-nyumbitsfowposition && n-nyumbitsfowposition <= m-max_position_bit;
    assewt 0 < n-nyumpositionsinswice && n-nyumpositionsinswice <= position_swice_num_bits_without_headew;
    wetuwn (numbitsfowposition << p-position_swice_headew_bits_position_shift) + n-nyumpositionsinswice;
  }

  /**
   * d-decode nyumbew of b-bits used fow position in the s-swice having the g-given headew. (⑅˘꒳˘)
   *
   * @pawam p-positionentwyheadew entwy headew w-wiww be decoded. ^^
   * @see #position_swice_headew_size
   */
  static int getnumbitsfowposition(int positionentwyheadew) {
    wetuwn (positionentwyheadew >>> position_swice_headew_bits_position_shift)
        & position_swice_headew_bits_position_mask;
  }

  /**
   * decode n-nyumbew of p-positions stowed in the swice having t-the given headew. 😳😳😳
   *
   * @pawam positionentwyheadew e-entwy h-headew wiww be d-decoded. òωó
   * @see #position_swice_headew_size
   */
  s-static int getnumpositionsinswice(int positionentwyheadew) {
    w-wetuwn positionentwyheadew & position_swice_headew_num_positions_mask;
  }

  /******************
   * h-hewpew methods *
   ******************/

  /**
   * c-check if given pointew is pointing to the swice stawt. ^^;;
   *
   * @pawam p-pointew the index wiww b-be checked. (✿oωo)
   */
  static boowean isswicestawt(int p-pointew) {
    wetuwn pointew % h-highdfpackedintspostingwists.swice_size == 0;
  }

  /**
   * ceiw of wog of x in the given b-base. rawr
   *
   * @wetuwn x == 0 ? 0 : m-math.ceiw(math.wog(x) / math.wog(base))
   */
  p-pwivate s-static int wog(int x, XD int base) {
    assewt base >= 2;
    i-if (x == 0) {
      wetuwn 0;
    }
    int wet = 1;
    w-wong ny = b-base; // nyeeds t-to be a wong to avoid ovewfwow
    whiwe (x >= ny) {
      ny *= base;
      wet++;
    }
    wetuwn w-wet;
  }

  /**********************
   * fow fwush and woad *
   **********************/

  @suppwesswawnings("unchecked")
  @ovewwide
  p-pubwic f-fwushhandwew getfwushhandwew() {
    wetuwn n-nyew fwushhandwew(this);
  }

  p-pubwic static cwass fwushhandwew extends fwushabwe.handwew<highdfpackedintspostingwists> {
    pwivate static finaw s-stwing omit_positions_pwop_name = "omitpositions";
    pwivate s-static finaw stwing skip_wists_pwop_name = "skipwists";
    pwivate static finaw s-stwing dewta_fweq_wists_pwop_name = "dewtafweqwists";
    pwivate s-static finaw stwing position_wists_pwop_name = "positionwists";

    p-pubwic f-fwushhandwew() {
      supew();
    }

    p-pubwic fwushhandwew(highdfpackedintspostingwists objecttofwush) {
      s-supew(objecttofwush);
    }

    @ovewwide
    p-pwotected void d-dofwush(fwushinfo f-fwushinfo, 😳 d-datasewiawizew out)
        thwows i-ioexception {
      h-highdfpackedintspostingwists objecttofwush = getobjecttofwush();
      fwushinfo.addbooweanpwopewty(omit_positions_pwop_name, (U ᵕ U❁) o-objecttofwush.omitpositions);
      objecttofwush.skipwists.getfwushhandwew()
          .fwush(fwushinfo.newsubpwopewties(skip_wists_pwop_name), UwU o-out);
      objecttofwush.dewtafweqwists.getfwushhandwew()
          .fwush(fwushinfo.newsubpwopewties(dewta_fweq_wists_pwop_name), OwO out);
      objecttofwush.positionwists.getfwushhandwew()
          .fwush(fwushinfo.newsubpwopewties(position_wists_pwop_name), 😳 out);
    }

    @ovewwide
    pwotected highdfpackedintspostingwists d-dowoad(
        fwushinfo fwushinfo, (˘ω˘) d-datadesewiawizew in) thwows i-ioexception {
      i-intbwockpoow skipwists = (new i-intbwockpoow.fwushhandwew())
          .woad(fwushinfo.getsubpwopewties(skip_wists_pwop_name), in);
      intbwockpoow d-dewtafweqwists = (new intbwockpoow.fwushhandwew())
          .woad(fwushinfo.getsubpwopewties(dewta_fweq_wists_pwop_name), òωó i-in);
      intbwockpoow positionwists = (new intbwockpoow.fwushhandwew())
          .woad(fwushinfo.getsubpwopewties(position_wists_pwop_name), OwO in);
      wetuwn nyew highdfpackedintspostingwists(
          skipwists, (✿oωo)
          dewtafweqwists, (⑅˘꒳˘)
          p-positionwists, /(^•ω•^)
          fwushinfo.getbooweanpwopewty(omit_positions_pwop_name), 🥺
          nyuww, -.-
          n-nyuww);
    }
  }
}
