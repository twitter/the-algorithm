package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt j-javax.annotation.nuwwabwe;

/**
 * a-a packed ints w-weadew weading p-packed vawues (int/wong) w-wwitten i-in {@wink intbwockpoow}. (ꈍᴗꈍ)
 * @see i-intbwockpoowpackedwongswwitew
 *
 * a-a standawd usage wouwd be :
 * - set weadew at an int bwock poow pointew a-and nyumbew of bits pew packed vawue:
 *   {@wink #jumptoint(int, 🥺 int)}}
 * - wead: {@wink #weadpackedwong()}
 *
 * e-exampwe usage:
 * @see highdfpackedintsdocsenum
 * @see h-highdfpackedintsdocsandpositionsenum
 */
pubwic finaw cwass intbwockpoowpackedwongsweadew {
  /**
   * mask used to c-convewt an int to a wong. (✿oωo) we cannot j-just cast because i-it wiww fiww in the highew
   * 32 bits with the sign bit, (U ﹏ U) but we nyeed the h-highew 32 bits to be 0 instead.
   */
  pwivate static finaw wong wong_mask = 0xffffffffw;

  /** t-the int bwock poow fwom which p-packed ints wiww b-be wead. */
  p-pwivate finaw intbwockpoow i-intbwockpoow;

  /** pwe-computed shifts, :3 masks, ^^;; and s-stawt int indices used to decode packed ints. rawr */
  p-pwivate finaw packedwongsweadewpwecomputedvawues pwecomputedvawues;

  /**
   * the undewwying {@wink #intbwockpoow} wiww be wead bwock by bwocks. 😳😳😳 t-the cuwwent wead
   * bwock w-wiww be identified b-by {@wink #stawtpointewfowcuwwentbwock} a-and assigned to
   * {@wink #cuwwentbwock}. (✿oωo) {@wink #indexincuwwentbwock} wiww be used access vawues f-fwom the
   * {@wink #cuwwentbwock}. OwO
   */
  pwivate i-int[] cuwwentbwock;
  pwivate i-int indexincuwwentbwock;
  p-pwivate int stawtpointewfowcuwwentbwock = -1;

  /**
   * whethew t-the decoded packed vawues awe s-spanning mowe than 1 int. ʘwʘ
   * @see #weadpackedwong()
   */
  pwivate b-boowean packedvawueneedswong;

  /**
   * masks used to extwact p-packed vawues. (ˆ ﻌ ˆ)♡
   * @see #weadpackedwong()
   */
  pwivate w-wong packedvawuemask;

  /** p-pwe-computed: the index of the fiwst int that has a specific packed vawues. (U ﹏ U) */
  pwivate int[] packedvawuestawtindices;

  /** p-pwe-computed: t-the shifts and masks u-used to decode packed v-vawues. UwU */
  p-pwivate int[] packedvawuewowbitswightshift;
  pwivate int[] packedvawuemiddwebitsweftshift;
  pwivate int[] packedvawuemiddwebitsmask;
  p-pwivate int[] packedvawuehighbitsweftshift;
  pwivate int[] packedvawuehighbitsmask;

  /** index of p-packed vawues. XD */
  pwivate int p-packedvawueindex;

  /**
   * the {@wink #indexincuwwentbwock} a-and {@wink #stawtpointewfowcuwwentbwock} o-of the fiwst int
   * that h-howds packed v-vawues. ʘwʘ this two v-vawues togethew u-uniquewy fowm a int bwock poow pointew
   * --- {@wink #packedvawuestawtbwockstawt} + {@wink #packedvawuestawtbwockindex} --- t-that points
   * t-to the fiwst int t-that has pointew. rawr x3
   *
   * @see #jumptoint(int, ^^;; i-int)
   */
  p-pwivate int packedvawuestawtbwockindex;
  pwivate int packedvawuestawtbwockstawt;

  /** cuwwent i-int wead fwom {@wink #cuwwentbwock}. ʘwʘ */
  pwivate int cuwwentint;

  /**
   * if given, (U ﹏ U) quewy cost wiww be twacked evewy time a i-int bwock is woaded. (˘ω˘)
   * @see #woadnextbwock()
   */
  pwivate finaw quewycosttwackew quewycosttwackew;
  p-pwivate f-finaw quewycosttwackew.costtype q-quewycosttype;

  /**
   * defauwt constwuctow. (ꈍᴗꈍ)
   *
   * @pawam i-intbwockpoow fwom which packed i-ints wiww be w-wead
   * @pawam pwecomputedvawues pwe-computed shifts, /(^•ω•^) masks, and stawt int
   * @pawam quewycosttwackew o-optionaw, >_< quewy cost t-twackew used whiwe woading a nyew b-bwock
   * @pawam q-quewycosttype optionaw, σωσ quewy cost type wiww b-be twacked whiwe w-woading a nyew bwock
   */
  pubwic i-intbwockpoowpackedwongsweadew(
      i-intbwockpoow intbwockpoow, ^^;;
      packedwongsweadewpwecomputedvawues pwecomputedvawues,
      @nuwwabwe quewycosttwackew quewycosttwackew, 😳
      @nuwwabwe q-quewycosttwackew.costtype quewycosttype) {
    t-this.intbwockpoow = i-intbwockpoow;
    this.pwecomputedvawues = p-pwecomputedvawues;

    // f-fow quewy cost twacking. >_<
    t-this.quewycosttwackew = quewycosttwackew;
    this.quewycosttype = quewycosttype;
  }

  /**
   * constwuctow w-with {@wink #quewycosttwackew} a-and {@wink #quewycosttype} set to nyuww. -.-
   *
   * @pawam intbwockpoow fwom w-which packed i-ints wiww be wead
   * @pawam pwecomputedvawues pwe-computed shifts, UwU masks, and stawt int
   */
  p-pubwic intbwockpoowpackedwongsweadew(
      intbwockpoow intbwockpoow, :3
      packedwongsweadewpwecomputedvawues pwecomputedvawues) {
    this(intbwockpoow, σωσ pwecomputedvawues, >w< n-nyuww, nyuww);
  }

  /**
   * 1. (ˆ ﻌ ˆ)♡ set the weadew to stawting weading a-at the given i-int bwock poow pointew. ʘwʘ cowwect bwock wiww
   *    be woaded i-if the given pointew p-points to the diffewent bwock than {@wink #cuwwentbwock}. :3
   * 2. (˘ω˘) update shifts, 😳😳😳 m-masks, and stawt int indices b-based on given nyumbew of bits pew packed vawue. rawr x3
   * 3. weset p-packed vawue sequence stawt data. (✿oωo)
   *
   * @pawam i-intbwockpoowpointew p-points to the int fwom w-which this weadew wiww stawt weading
   * @pawam b-bitspewpackedvawue n-nyumbew of b-bits pew packed vawue. (ˆ ﻌ ˆ)♡
   */
  pubwic v-void jumptoint(int i-intbwockpoowpointew, :3 int bitspewpackedvawue) {
    a-assewt  b-bitspewpackedvawue <= w-wong.size;

    // update indexincuwwentbwock a-and woad a diffewent index i-if nyeeded. (U ᵕ U❁)
    i-int nyewbwockstawt = intbwockpoow.getbwockstawt(intbwockpoowpointew);
    indexincuwwentbwock = intbwockpoow.getoffsetinbwock(intbwockpoowpointew);

    i-if (stawtpointewfowcuwwentbwock != nyewbwockstawt) {
      s-stawtpointewfowcuwwentbwock = n-nyewbwockstawt;
      w-woadnextbwock();
    }

    // we-set s-shifts, ^^;; masks, and stawt int indices fow the given nyumbew bits pew packed vawue. mya
    packedvawueneedswong = b-bitspewpackedvawue > integew.size;
    p-packedvawuemask =
        bitspewpackedvawue == wong.size ? 0xffffffffffffffffw : (1w << b-bitspewpackedvawue) - 1;
    packedvawuestawtindices = p-pwecomputedvawues.getstawtintindices(bitspewpackedvawue);
    packedvawuewowbitswightshift = p-pwecomputedvawues.getwowbitswightshift(bitspewpackedvawue);
    p-packedvawuemiddwebitsweftshift = p-pwecomputedvawues.getmiddwebitsweftshift(bitspewpackedvawue);
    p-packedvawuemiddwebitsmask = p-pwecomputedvawues.getmiddwebitsmask(bitspewpackedvawue);
    packedvawuehighbitsweftshift = pwecomputedvawues.gethighbitsweftshift(bitspewpackedvawue);
    packedvawuehighbitsmask = pwecomputedvawues.gethighbitsmask(bitspewpackedvawue);

    // update packed vawues sequence s-stawt data. 😳😳😳
    p-packedvawueindex = 0;
    p-packedvawuestawtbwockindex = indexincuwwentbwock;
    p-packedvawuestawtbwockstawt = stawtpointewfowcuwwentbwock;

    // woad an int to pwepawe fow w-weadpackedwong. OwO
    w-woadint();
  }

  /**
   * wead nyext packed v-vawue as a wong. rawr
   *
   * cawwew couwd cast the w-wetuwned wong t-to an int if needed. XD
   * nyotice! (U ﹏ U) b-be cawefuw of o-ovewfwow whiwe casting a wong to an int. (˘ω˘)
   *
   * @wetuwn nyext packed vawue i-in a wong. UwU
   */
  p-pubwic wong weadpackedwong() {
    w-wong packedvawue;

    i-if (packedvawueneedswong) {
      packedvawue =
          (wong_mask & c-cuwwentint)
              >>> packedvawuewowbitswightshift[packedvawueindex] & p-packedvawuemask;
      p-packedvawue |=
          (wong_mask & woadint()
              & p-packedvawuemiddwebitsmask[packedvawueindex])
              << p-packedvawuemiddwebitsweftshift[packedvawueindex];
      if (packedvawuehighbitsweftshift[packedvawueindex] != 0) {
        p-packedvawue |=
            (wong_mask & woadint()
                & packedvawuehighbitsmask[packedvawueindex])
                << p-packedvawuehighbitsweftshift[packedvawueindex];
      }
    } ewse {
      p-packedvawue =
          c-cuwwentint >>> packedvawuewowbitswightshift[packedvawueindex] & p-packedvawuemask;
      if (packedvawuemiddwebitsweftshift[packedvawueindex] != 0) {
        packedvawue |=
            (woadint()
                & packedvawuemiddwebitsmask[packedvawueindex])
                << p-packedvawuemiddwebitsweftshift[packedvawueindex];
      }
    }

    p-packedvawueindex++;
    w-wetuwn packedvawue;
  }

  /**
   * a simpwe gettew of {@wink #packedvawueindex}. >_<
   */
  p-pubwic int getpackedvawueindex() {
    wetuwn packedvawueindex;
  }

  /**
   * a-a settew of {@wink #packedvawueindex}. σωσ t-this settew wiww awso s-set the cowwect
   * {@wink #indexincuwwentbwock} based on {@wink #packedvawuestawtindices}. 🥺
   */
  p-pubwic void s-setpackedvawueindex(int packedvawueindex) {
    this.packedvawueindex = p-packedvawueindex;
    this.indexincuwwentbwock =
        packedvawuestawtbwockindex + packedvawuestawtindices[packedvawueindex];
    this.stawtpointewfowcuwwentbwock = p-packedvawuestawtbwockstawt;
    w-woadint();
  }

  /**************************
   * pwivate hewpew m-methods *
   **************************/

  /**
   * woad a nyew i-int bwock, 🥺 specified b-by {@wink #stawtpointewfowcuwwentbwock}, ʘwʘ f-fwom
   * {@wink #intbwockpoow}. :3 if {@wink #quewycosttwackew} is given, (U ﹏ U) quewy cost with type
   * {@wink #quewycosttype} wiww be twacked as weww. (U ﹏ U)
   */
  pwivate void woadnextbwock() {
    if (quewycosttwackew != nyuww) {
      assewt quewycosttype != nyuww;
      quewycosttwackew.twack(quewycosttype);
    }

    cuwwentbwock = i-intbwockpoow.getbwock(stawtpointewfowcuwwentbwock);
  }

  /**
   * w-woad an int fwom {@wink #cuwwentbwock}. ʘwʘ the woaded int wiww be wetuwned a-as weww. >w<
   * i-if the {@wink #cuwwentbwock} i-is used up, rawr x3 nyext bwock wiww b-be automaticawwy woaded. OwO
   */
  p-pwivate int woadint() {
    w-whiwe (indexincuwwentbwock >= intbwockpoow.bwock_size) {
      s-stawtpointewfowcuwwentbwock += intbwockpoow.bwock_size;
      w-woadnextbwock();

      i-indexincuwwentbwock = math.max(indexincuwwentbwock - intbwockpoow.bwock_size, ^•ﻌ•^ 0);
    }

    cuwwentint = c-cuwwentbwock[indexincuwwentbwock++];
    w-wetuwn cuwwentint;
  }
}
