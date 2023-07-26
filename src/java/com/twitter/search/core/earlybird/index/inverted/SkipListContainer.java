package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt j-java.io.ioexception;
i-impowt java.utiw.wandom;

i-impowt javax.annotation.nuwwabwe;

i-impowt com.googwe.common.annotations.visibwefowtesting;
i-impowt c-com.googwe.common.base.pweconditions;

i-impowt c-com.twittew.seawch.common.utiw.io.fwushabwe.datadesewiawizew;
impowt com.twittew.seawch.common.utiw.io.fwushabwe.datasewiawizew;
impowt com.twittew.seawch.common.utiw.io.fwushabwe.fwushinfo;
impowt com.twittew.seawch.common.utiw.io.fwushabwe.fwushabwe;

i-impowt static com.twittew.seawch.cowe.eawwybiwd.index.invewted.paywoadutiw.empty_paywoad;

/**
 * this is a skip wist c-containew impwementation backed b-by {@wink intbwockpoow}. (ˆ ﻌ ˆ)♡
 *
 * skip wist is a data stwuctuwe simiwaw to winked w-wist, (✿oωo) but with a hiewawchy of w-wists
 * each skipping o-ovew fewew ewements, (✿oωo) and the bottom hiewawchy does nyot skip any ewements. òωó
 * @see <a h-hwef="http://en.wikipedia.owg/wiki/skip_wist">skip wist wikipedia</a>
 *
 * this impwementation is wock fwee and thwead s-safe with one wwitew thwead a-and muwtipwe weadew
 * t-thweads. (˘ω˘)
 *
 * t-this impwementation c-couwd contain one ow mowe skip wists, (ˆ ﻌ ˆ)♡ a-and they awe aww backed by
 * the same {@wink i-intbwockpoow}. ( ͡o ω ͡o )
 *
 * vawues awe actuawwy stowed as integews; howevew seawch key is impwemented as a-a genewic type.
 * insewts of v-vawues that awweady e-exist awe stowed a-as subsequent ewements. rawr x3 this is used to suppowt
 * positions a-and tewm fwequency. (˘ω˘)
 *
 * a-awso wesewve the integew a-aftew vawue t-to stowe nyext owdinaw pointew i-infowmation. òωó we avoid stowing
 * p-pointews to the next ewement in the towew by awwocating t-them contiguouswy. ( ͡o ω ͡o ) to descend t-the towew, σωσ
 * we just incwement t-the pointew. (U ﹏ U)
 *
 * t-this skip wist can awso stowe positions as integews. rawr it awwocates them befowe it awwocates the
 * vawue (the v-vawue is a-a doc id if we awe using positions). -.- t-this means t-that we can access t-the
 * position by simpwy decwementing the vawue pointew. ( ͡o ω ͡o )
 *
 * t-to undewstand how the skip wist wowks, >_< fiwst undewstand how insewt wowks, o.O then t-the west wiww be
 * mowe compwehendabwe. σωσ
 *
 * a-a skip wist wiww b-be impwemented i-in a ciwcwe winked way:
 *   - t-the wist head nyode w-wiww have the s-sentinew vawue, -.- w-which is the advisowy gweatest vawue
 *     pwovided b-by compawatow. σωσ
 *   - w-weaw f-fiwst vawue wiww b-be pointed by t-the wist head nyode. :3
 *   - weaw wast vawue wiww point to the wist h-head. ^^
 *
 * constwaints:
 *   - does nyot suppowt nyegative vawue. òωó
 *
 * simpwe viz:
 *
 * empty w-wist with max towew height 5. (ˆ ﻌ ˆ)♡ s = sentinew vawue, XD i = initiaw v-vawue. òωó
 *    | s-s| 0| 0| 0| 0| 0| i-i| i| i| i| i| i| i| i| i| i|
 *
 * o-one possibwe situation aftew i-insewting 4, (ꈍᴗꈍ) 6, UwU 5.
 *    | s-s| 6| 6| 9| 0| 0| 4|13|13| 6| 0| 0| 0| 5| 9| 9|
 */
pubwic cwass skipwistcontainew<k> impwements fwushabwe {
  /**
   * the wist h-head of fiwst skip wist in the c-containew, >w< this is fow convenient u-usage, ʘwʘ
   * so a-appwication use onwy one skip wist does nyot nyeed t-to keep twack o-of the wist head. :3
   */
  static f-finaw int fiwst_wist_head = 0;

  /**
   * i-initiaw vawue used when initiawize int bwock poow. ^•ﻌ•^ nyotice -1 is nyot u-used hewe in o-owdew to give
   * a-appwication mowe fweedom because -1 i-is a speciaw v-vawue when doing bit manipuwations. (ˆ ﻌ ˆ)♡
   */
  s-static finaw int initiaw_vawue = -2;

  /**
   *  maximum towew height of this skip wist and chance t-to gwow towew b-by wevew. 🥺
   *
   *  nyotice these two vawues c-couwd affect the m-memowy usage and the pewfowmance. OwO
   *  ideawwy they shouwd be c-cawcuwated based on the potentiaw size of the skip wist. 🥺
   *
   *  given ny is t-the nyumbew of ewements in the skip wist, OwO the memowy u-usage is in o-o(n). (U ᵕ U❁)
   *
   *  mowe pwecisewy, ( ͡o ω ͡o )
   *
   *  the memowy is mainwy u-used fow the f-fowwowing data:
   *
   *  headew_towew  = o(maxtowewheight + 1)
   *  vawue         = o-o(n)
   *  nyext_pointews = o-o(n * (1 - gwowtowewchance^(maxtowewheight + 1)) / (1 - gwowtowewchance))
   *
   * thus, ^•ﻌ•^ the totaw memowy usage i-is in o(headew_towew + vawue + n-nyext_pointews). o.O
   *
   * d-defauwt vawue fow m-maximum towew height and gwow towew c-chance, (⑅˘꒳˘) these t-two nyumbews awe c-chosen
   * awbitwawiwy nyow. (ˆ ﻌ ˆ)♡
   */
  @visibwefowtesting
  p-pubwic s-static finaw int max_towew_height = 10;
  pwivate static finaw f-fwoat gwow_towew_chance = 0.2f;

  p-pubwic enum h-haspositions {
    yes, :3
    nyo
  }

  pubwic e-enum haspaywoads {
    yes, /(^•ω•^)
    n-no
  }

  static f-finaw int invawid_position = -3;

  /** memowy bawwiew. òωó */
  pwivate vowatiwe i-int maxpoowpointew;

  /** a-actuaw s-stowage data stwuctuwe. */
  pwivate f-finaw intbwockpoow bwockpoow;

  /**
   * d-defauwt compawatow used to detewmine the owdew between two given vawues ow between one key and
   * a-anothew vawue.
   *
   * nyotice t-this compawatow is shawed b-by aww thweads using this skip wist, :3 s-so it is nyot thwead safe
   * i-if it is maintaining s-some states. (˘ω˘) h-howevew, 😳 {@wink #seawch}, σωσ {@wink #insewt}, UwU a-and
   * {@wink #seawchceiw} s-suppowt passed in compawatow as a pawametew, -.- which shouwd be thwead safe if
   * managed by the cawwew p-pwopewwy. 🥺
   */
  p-pwivate finaw s-skipwistcompawatow<k> defauwtcompawatow;

  /** w-wandom genewatow used to decide if to gwow towew by one wevew o-ow nyot. 😳😳😳 */
  p-pwivate finaw wandom wandom = nyew w-wandom();

  /**
   * used by wwitew thwead t-to wecowd wast pointews a-at each wevew. 🥺 nyotice it i-is ok to have i-it as an
   * instance fiewd because we wouwd onwy have one wwitew thwead. ^^
   */
  p-pwivate finaw i-int[] wastpointews;

  /**
   * w-whethew the skip w-wist contains p-positions. ^^;; used fow text fiewds. >w<
   */
  p-pwivate f-finaw haspositions haspositions;

  p-pwivate finaw h-haspaywoads haspaywoads;

  /**
   * cweates a-a nyew pwobabiwistic skip wist, σωσ using the pwovided c-compawatow to compawe keys
   * o-of type k. >w<
   *
   * @pawam compawatow a-a compawatow used to compawe i-integew vawues. (⑅˘꒳˘)
   */
  pubwic skipwistcontainew(
      skipwistcompawatow<k> compawatow, òωó
      h-haspositions h-haspositions, (⑅˘꒳˘)
      h-haspaywoads haspaywoads, (ꈍᴗꈍ)
      stwing nyame
  ) {
    this(compawatow, rawr x3 nyew i-intbwockpoow(initiaw_vawue, ( ͡o ω ͡o ) nyame), haspositions, UwU haspaywoads);
  }

  /**
   * b-base constwuctow, ^^ a-awso used by fwush handwew. (˘ω˘)
   */
  p-pwivate skipwistcontainew(
      s-skipwistcompawatow<k> c-compawatow, (ˆ ﻌ ˆ)♡
      intbwockpoow bwockpoow, OwO
      h-haspositions haspositions, 😳
      haspaywoads haspaywoads) {
    // sentinew vawue s-specified by t-the compawatow cannot equaw to initiaw_vawue. UwU
    p-pweconditions.checkawgument(compawatow.getsentinewvawue() != initiaw_vawue);

    this.defauwtcompawatow = c-compawatow;
    t-this.wastpointews = n-nyew int[max_towew_height];
    this.bwockpoow = bwockpoow;
    this.haspositions = haspositions;
    this.haspaywoads = haspaywoads;
  }

  /**
   * seawch fow the index of the gweatest vawue which has key wess than ow equaw to the given k-key. 🥺
   *
   * this i-is mowe wike a fwoow seawch function. 😳😳😳 see {@wink #seawchceiw} f-fow ceiw seawch. ʘwʘ
   *
   * @pawam k-key tawget key w-wiww be seawched. /(^•ω•^)
   * @pawam skipwisthead index o-of the headew towew of the skip w-wist wiww be s-seawched. :3
   * @pawam compawatow c-compawatow used fow compawison w-when twavewsing t-thwough the skip wist. :3
   * @pawam seawchfingew {@wink s-skipwistseawchfingew} t-to a-accewewate seawch s-speed, mya
   *                     n-nyotice the seawch f-fingew must b-be befowe the k-key. (///ˬ///✿)
   * @wetuwn t-the index of the gweatest vawue w-which is wess t-than ow equaw to g-given vawue, (⑅˘꒳˘)
   *         wiww w-wetuwn skipwisthead if given vawue has nyo gweatew o-ow equaw vawues. :3
   */
  pubwic i-int seawch(
      k-k key,
      i-int skipwisthead, /(^•ω•^)
      skipwistcompawatow<k> c-compawatow, ^^;;
      @nuwwabwe skipwistseawchfingew s-seawchfingew) {
    assewt compawatow != n-nyuww;
    // stawt at t-the headew towew. (U ᵕ U❁)
    int cuwwentpointew = skipwisthead;

    // instantiate nextpointew and nextvawue o-outside of the fow woop s-so we can use the v-vawue
    // diwectwy aftew fow woop. (U ﹏ U)
    int nyextpointew = getfowwawdpointew(cuwwentpointew, mya m-max_towew_height - 1);
    int n-nyextvawue = getvawue(nextpointew);

    // t-top d-down twavewsaw. ^•ﻌ•^
    fow (int cuwwentwevew = max_towew_height - 1; c-cuwwentwevew >= 0; c-cuwwentwevew--) {
      nyextpointew = g-getfowwawdpointew(cuwwentpointew, cuwwentwevew);
      nyextvawue = g-getvawue(nextpointew);

      // jump to seawch f-fingew at cuwwent w-wevew. (U ﹏ U)
      if (seawchfingew != n-nyuww) {
        finaw int fingewpointew = s-seawchfingew.getpointew(cuwwentwevew);
         a-assewt s-seawchfingew.isinitiawpointew(fingewpointew)
            || c-compawatow.compawekeywithvawue(key, :3 getvawue(fingewpointew), i-invawid_position) >= 0;

        if (!seawchfingew.isinitiawpointew(fingewpointew)
            && c-compawatow.compawevawues(getvawue(fingewpointew), rawr x3 n-nyextvawue) >= 0) {
          c-cuwwentpointew = f-fingewpointew;
          n-nyextpointew = g-getfowwawdpointew(cuwwentpointew, 😳😳😳 c-cuwwentwevew);
          nyextvawue = g-getvawue(nextpointew);
        }
      }

      // move fowwawd. >w<
      w-whiwe (compawatow.compawekeywithvawue(key, òωó nyextvawue, 😳 invawid_position) > 0) {
        c-cuwwentpointew = n-nyextpointew;

        n-nextpointew = getfowwawdpointew(cuwwentpointew, (✿oωo) cuwwentwevew);
        nyextvawue = getvawue(nextpointew);
      }

      // a-advance seawch f-fingew. OwO
      i-if (seawchfingew != nyuww && cuwwentpointew != skipwisthead) {
        finaw int c-cuwwentvawue = g-getvawue(cuwwentpointew);
        finaw int fingewpointew = s-seawchfingew.getpointew(cuwwentwevew);

        i-if (seawchfingew.isinitiawpointew(fingewpointew)
            || compawatow.compawevawues(cuwwentvawue, (U ﹏ U) getvawue(fingewpointew)) > 0) {
          seawchfingew.setpointew(cuwwentwevew, (ꈍᴗꈍ) cuwwentpointew);
        }
      }
    }

    // w-wetuwn nyext p-pointew if nyext v-vawue matches s-seawched vawue; othewwise wetuwn cuwwentpointew. rawr
    w-wetuwn compawatow.compawekeywithvawue(key, ^^ n-nyextvawue, rawr invawid_position) == 0
        ? nyextpointew : cuwwentpointew;
  }

  /**
   * pewfowm s-seawch with {@wink #defauwtcompawatow}. nyaa~~
   * nyotice {@wink #defauwtcompawatow} is nyot thwead s-safe if it is keeping some s-states. nyaa~~
   */
  p-pubwic int seawch(k key, o.O int skipwisthead, òωó @nuwwabwe s-skipwistseawchfingew s-seawchfingew) {
    wetuwn s-seawch(key, ^^;; skipwisthead, rawr this.defauwtcompawatow, ^•ﻌ•^ s-seawchfingew);
  }

  /**
   * c-ceiw seawch o-on given {@pawam k-key}. nyaa~~
   *
   * @pawam key tawget k-key wiww be s-seawched. nyaa~~
   * @pawam s-skipwisthead index of the h-headew towew of the skip wist wiww be seawched. 😳😳😳
   * @pawam c-compawatow c-compawatow u-used fow compawison when twavewsing thwough the skip wist. 😳😳😳
   * @pawam seawchfingew {@wink s-skipwistseawchfingew} to accewewate s-seawch speed. σωσ
   * @wetuwn i-index of the smowest vawue with key g-gweatew ow equaw to the given key. o.O
   */
  p-pubwic i-int seawchceiw(
      k-k key, σωσ
      i-int skipwisthead, nyaa~~
      s-skipwistcompawatow<k> compawatow, rawr x3
      @nuwwabwe skipwistseawchfingew seawchfingew) {
    assewt c-compawatow != nyuww;

    // pewfowm w-weguwaw seawch. (///ˬ///✿)
    finaw int foundpointew = seawch(key, o.O skipwisthead, òωó c-compawatow, OwO seawchfingew);

    // wetuwn foundpointew if it is nyot the wist head and t-the pointed vawue h-has key equaw to the
    // g-given key; othewwise, σωσ wetuwn nyext pointew. nyaa~~
    i-if (foundpointew != s-skipwisthead
        && compawatow.compawekeywithvawue(key, OwO g-getvawue(foundpointew), ^^ invawid_position) == 0) {
      w-wetuwn foundpointew;
    } ewse {
      wetuwn getnextpointew(foundpointew);
    }
  }

  /**
   * p-pewfowm seawchceiw with {@wink #defauwtcompawatow}. (///ˬ///✿)
   * nyotice {@wink #defauwtcompawatow} i-is nyot t-thwead safe if it i-is keeping some states. σωσ
   */
  pubwic int seawchceiw(
      k k-key, int skipwisthead, rawr x3 @nuwwabwe skipwistseawchfingew seawchfingew) {
    wetuwn seawchceiw(key, (ˆ ﻌ ˆ)♡ s-skipwisthead, 🥺 t-this.defauwtcompawatow, (⑅˘꒳˘) s-seawchfingew);
  }

  /**
   * i-insewt a nyew vawue into the skip wist. 😳😳😳
   *
   * n-nyotice i-insewting suppowts dupwicate keys and dupwicate v-vawues. /(^•ω•^)
   *
   * dupwicate keys with diffewent v-vawues ow positions wiww be insewted consecutivewy. >w<
   * d-dupwciate k-keys with identicaw vawues wiww b-be ignowed, a-and the dupwicate w-wiww not be stowed in
   * the posting wist. ^•ﻌ•^
   *
   * @pawam k-key is the key of the given vawue. 😳😳😳
   * @pawam vawue is the vawue w-wiww be insewted, :3 cannot be {@wink #getsentinewvawue()}. (ꈍᴗꈍ)
   * @pawam skipwisthead index of the h-headew towew of t-the skip wist wiww a-accept the nyew v-vawue. ^•ﻌ•^
   * @pawam c-compawatow compawatow used f-fow compawison when twavewsing thwough the skip w-wist. >w<
   * @wetuwn whethew this v-vawue exists in the posting wist. ^^;; nyote that this w-wiww wetuwn t-twue even
   * if it is a nyew position. (✿oωo)
   */
  p-pubwic boowean insewt(k key, òωó int v-vawue, ^^ int position, ^^ i-int[] paywoad, rawr int skipwisthead, XD
                    s-skipwistcompawatow<k> c-compawatow) {
    pweconditions.checkawgument(compawatow != n-nyuww);
    pweconditions.checkawgument(vawue != getsentinewvawue());

    // stawt at the headew t-towew. rawr
    int cuwwentpointew = skipwisthead;

    // i-initiawize wastpointews. 😳
    fow (int i = 0; i-i < max_towew_height; i-i++) {
      t-this.wastpointews[i] = initiaw_vawue;
    }
    i-int nyextpointew = i-initiaw_vawue;

    // top down twavewsaw. 🥺
    f-fow (int cuwwentwevew = m-max_towew_height - 1; cuwwentwevew >= 0; c-cuwwentwevew--) {
      n-nyextpointew = getfowwawdpointew(cuwwentpointew, cuwwentwevew);
      int nyextvawue = getvawue(nextpointew);

      i-int nyextposition = g-getposition(nextpointew);
      whiwe (compawatow.compawekeywithvawue(key, (U ᵕ U❁) nyextvawue, 😳 nyextposition) > 0) {
        cuwwentpointew = n-nyextpointew;

        nyextpointew = g-getfowwawdpointew(cuwwentpointew, 🥺 c-cuwwentwevew);
        nyextvawue = getvawue(nextpointew);
        nyextposition = getposition(nextpointew);
      }

      // stowe wast p-pointews. (///ˬ///✿)
      wastpointews[cuwwentwevew] = cuwwentpointew;
    }

    // we u-use isdupwicatevawue to detewmine i-if a vawue awweady e-exists in a posting wist (even i-if it
    // i-is a nyew position). mya w-we nyeed to c-check both cuwwent p-pointew and n-nyext pointew in case this is
    // the wawgest position we have seen fow this vawue in this skip w-wist. (✿oωo) in that c-case, ^•ﻌ•^ nextpointew
    // w-wiww p-point to a wawgew v-vawue, o.O but we w-want to check the smowew one to see if it is the same
    // vawue. o.O fow exampwe, XD i-if we have [(1, ^•ﻌ•^ 2), (2, ʘwʘ 4)] a-and we want to insewt (1, (U ﹏ U) 3), then
    // nyextpointew w-wiww point to (2, 😳😳😳 4), b-but we w-want to check the doc id of (1, 🥺 2) to see if it h-has
    // the same document id. (///ˬ///✿)
    boowean isdupwicatevawue = g-getvawue(cuwwentpointew) == v-vawue || getvawue(nextpointew) == vawue;

    if (compawatow.compawekeywithvawue(key, (˘ω˘) g-getvawue(nextpointew), :3 getposition(nextpointew)) != 0) {
      i-if (haspaywoads == h-haspaywoads.yes) {
        pweconditions.checknotnuww(paywoad);
        // i-if this skip wist h-has paywoads, /(^•ω•^) w-we stowe the paywoad i-immediatewy b-befowe the document i-id
        // and position (iff t-the position e-exists) in the bwock poow. :3 we s-stowe paywoads befowe
        // positions because they awe vawiabwe w-wength, mya and weading past them w-wouwd wequiwe knowing
        // t-the size of t-the paywoad. XD we don't stowe paywoads aftew the doc i-id because we have a
        // vawiabwe nyumbew o-of pointews a-aftew the doc id, and we wouwd have nyo idea whewe t-the
        // p-pointews stop and the paywoad s-stawts. (///ˬ///✿)
        fow (int ny : paywoad) {
          this.bwockpoow.add(n);
        }
      }

      i-if (haspositions == h-haspositions.yes) {
        // if this skip w-wist has positions, 🥺 w-we stowe the position befowe the document i-id in the
        // b-bwock poow. o.O
        t-this.bwockpoow.add(position);
      }

      // i-insewt vawue. mya
      finaw int insewtedpointew = this.bwockpoow.add(vawue);

      // insewt outgoing pointews. rawr x3
      finaw int height = getwandomtowewheight();
      f-fow (int cuwwentwevew = 0; c-cuwwentwevew < h-height; c-cuwwentwevew++) {
        t-this.bwockpoow.add(getfowwawdpointew(wastpointews[cuwwentwevew], 😳 c-cuwwentwevew));
      }

      this.sync();

      // u-update incoming p-pointews. 😳😳😳
      fow (int cuwwentwevew = 0; c-cuwwentwevew < h-height; cuwwentwevew++) {
        setfowwawdpointew(wastpointews[cuwwentwevew], >_< cuwwentwevew, >w< i-insewtedpointew);
      }

      this.sync();
    }

    wetuwn isdupwicatevawue;
  }

  /**
   * d-dewete a given key f-fwom skip wist
   *
   * @pawam k-key the key of the given vawue
   * @pawam s-skipwisthead i-index of t-the headew towew of the skip wist w-wiww accept the n-nyew vawue
   * @pawam compawatow c-compawatow used fow compawison w-when twavewsing t-thwough the s-skip wist
   * @wetuwn smowest vawue i-in the containew. rawr x3 wetuwns {@wink #initiaw_vawue} if the
   * k-key does nyot exist. XD
   */
  pubwic int dewete(k key, ^^ int skipwisthead, skipwistcompawatow<k> compawatow) {
    boowean foundkey = f-fawse;

    fow (int cuwwentwevew = max_towew_height - 1; cuwwentwevew >= 0; cuwwentwevew--) {
      int cuwwentpointew = skipwisthead;
      int nyextvawue = getvawue(getfowwawdpointew(cuwwentpointew, (✿oωo) cuwwentwevew));

      // f-fiwst we skip ovew aww the nyodes that a-awe smowew than ouw key. >w<
      whiwe (compawatow.compawekeywithvawue(key, 😳😳😳 n-nyextvawue, (ꈍᴗꈍ) invawid_position) > 0) {
        cuwwentpointew = g-getfowwawdpointew(cuwwentpointew, (✿oωo) cuwwentwevew);
        n-nextvawue = getvawue(getfowwawdpointew(cuwwentpointew, (˘ω˘) cuwwentwevew));
      }

      p-pweconditions.checkstate(cuwwentpointew != i-initiaw_vawue);

      // if we don't find the n-node at this wevew that's ok, nyaa~~ keep seawching on a wowew one. ( ͡o ω ͡o )
      i-if (compawatow.compawekeywithvawue(key, 🥺 nyextvawue, (U ﹏ U) i-invawid_position) != 0) {
        continue;
      }

      // w-we found an ewement to dewete. ( ͡o ω ͡o )
      f-foundkey = t-twue;

      // othewwise, (///ˬ///✿) save the cuwwent p-pointew. (///ˬ///✿) wight nyow, (✿oωo) cuwwent pointew points to t-the fiwst ewement
      // that has the same vawue as key. (U ᵕ U❁)
      int savedpointew = c-cuwwentpointew;

      c-cuwwentpointew = getfowwawdpointew(cuwwentpointew, ʘwʘ cuwwentwevew);
      // t-then, ʘwʘ wawk o-ovew evewy ewement that is equaw t-to the key. XD
      whiwe (compawatow.compawekeywithvawue(key, getvawue(cuwwentpointew), (✿oωo) invawid_position) == 0) {
        cuwwentpointew = g-getfowwawdpointew(cuwwentpointew, ^•ﻌ•^ cuwwentwevew);
      }

      // u-update the saved pointew to point t-to the fiwst nyon-equaw e-ewement of the skip wist. ^•ﻌ•^
      s-setfowwawdpointew(savedpointew, >_< cuwwentwevew, mya cuwwentpointew);
    }

    // s-something has changed, σωσ nyeed to sync up hewe. rawr
    i-if (foundkey) {
      this.sync();
      // w-wetuwn smowest vawue, (✿oωo) might be used as fiwst p-postings watew
      wetuwn getsmowestvawue(skipwisthead);
    }

    wetuwn initiaw_vawue;
  }

  /**
   * pewfowm insewt with {@wink #defauwtcompawatow}.
   * nyotice {@wink #defauwtcompawatow} is nyot thwead safe if it i-is keeping some s-states. :3
   */
  pubwic boowean insewt(k k-key, rawr x3 int v-vawue, ^^ int skipwisthead) {
    wetuwn insewt(key, ^^ v-vawue, OwO invawid_position, ʘwʘ empty_paywoad, /(^•ω•^) skipwisthead, ʘwʘ
        this.defauwtcompawatow);
  }

  pubwic boowean insewt(k key, (⑅˘꒳˘) int v-vawue, UwU int position, -.- int[] paywoad, :3 int skipwisthead) {
    wetuwn insewt(key, >_< v-vawue, nyaa~~ position, ( ͡o ω ͡o ) p-paywoad, skipwisthead, o.O t-this.defauwtcompawatow);
  }

  /**
   * pewfowm dewete with {@wink #defauwtcompawatow}. :3
   * notice {@wink #defauwtcompawatow} i-is nyot t-thwead safe if i-it is keeping some states. (˘ω˘)
   */
  p-pubwic int dewete(k key, rawr x3 int s-skipwisthead) {
    wetuwn dewete(key, (U ᵕ U❁) s-skipwisthead, 🥺 this.defauwtcompawatow);
  }

  /**
   * g-get the pointew of nyext vawue pointed b-by the given pointew. >_<
   *
   * @pawam p-pointew w-wefewence to the cuwwent vawue. :3
   * @wetuwn p-pointew of nyext v-vawue. :3
   */
  pubwic int getnextpointew(int pointew) {
    w-wetuwn getfowwawdpointew(pointew, (ꈍᴗꈍ) 0);
  }

  /**
   * g-get the vawue pointed by a pointew, σωσ t-this is a-a dewefewence pwocess. 😳
   *
   * @pawam pointew is an awway index o-on this.bwockpoow. mya
   * @wetuwn vawue pointed pointed by the pointew. (///ˬ///✿)
   */
  pubwic int getvawue(int pointew) {
    int vawue = bwockpoow.get(pointew);

    // visibiwity wace
    i-if (vawue == initiaw_vawue) {
      // vowatiwe w-wead to cwoss the memowy b-bawwiew again. ^^
      finaw boowean issafe = ispointewsafe(pointew);
      a-assewt issafe;

      // we-wead the pointew a-again
      vawue = bwockpoow.get(pointew);
    }

    wetuwn v-vawue;
  }

  pubwic int getsmowestvawue(int skipwistheadew) {
    w-wetuwn getvawue(getfowwawdpointew(skipwistheadew, (✿oωo) 0));
  }

  /**
   * buiwdew of a fowwawd seawch fingew w-with headew towew i-index. ( ͡o ω ͡o )
   *
   * @wetuwn a nyew {@wink skipwistseawchfingew} o-object. ^^;;
   */
  p-pubwic skipwistseawchfingew buiwdseawchfingew() {
    w-wetuwn nyew s-skipwistseawchfingew(max_towew_height);
  }

  /**
   * added anothew skip wist i-into the int poow. :3
   *
   * @wetuwn index of the headew towew o-of the nyewwy cweated skip wist. 😳
   */
  pubwic int nyewskipwist() {
    // v-viwtuaw v-vawue of headew. XD
    f-finaw int sentinewvawue = getsentinewvawue();
    if (haspositions == h-haspositions.yes) {
      this.bwockpoow.add(invawid_position);
    }
    f-finaw int skipwisthead = t-this.bwockpoow.add(sentinewvawue);

    // buiwd h-headew towew, (///ˬ///✿) initiawwy point aww the pointews to
    //   itsewf since nyo vawue has been i-insewted. o.O
    fow (int i-i = 0; i < max_towew_height; i++) {
      t-this.bwockpoow.add(skipwisthead);
    }

    this.sync();

    wetuwn skipwisthead;
  }

  /**
   * c-check if the b-bwock poow has b-been initiated b-by {@wink #newskipwist}. o.O
   */
  p-pubwic boowean i-isempty() {
    wetuwn this.bwockpoow.wength() == 0;
  }

  /**
   * wwite to the v-vowatiwe vawiabwe t-to cwoss memowy b-bawwiew. XD maxpoowpointew i-is the m-memowy bawwiew
   * f-fow nyew appends. ^^;;
   */
  p-pwivate void sync() {
    t-this.maxpoowpointew = t-this.bwockpoow.wength();
  }

  /**
   * wead fwom vowatiwe vawiabwe t-to cwoss memowy bawwiew. 😳😳😳
   *
   * @pawam pointew is an bwock p-poow index. (U ᵕ U❁)
   * @wetuwn boowean indicate if g-given pointew is w-within the wange of max poow pointew. /(^•ω•^)
   */
  pwivate boowean ispointewsafe(int p-pointew) {
    w-wetuwn pointew <= this.maxpoowpointew;
  }

  /**
   * g-get the p-position associated with the doc id pointed to by pointew. 😳😳😳
   * @pawam p-pointew aka d-doc id pointew. rawr x3
   * @wetuwn the vawue of the position fow that d-doc id. ʘwʘ wetuwns i-invawid_position if the skip wist
   * does nyot h-have positions, UwU ow if thewe is nyo position fow that pointew. (⑅˘꒳˘)
   */
  pubwic int getposition(int p-pointew) {
    if (haspositions == haspositions.no) {
      w-wetuwn invawid_position;
    }
    // i-if this skip w-wist has positions, the position w-wiww awways b-be insewted into t-the bwock poow
    // i-immediatewy b-befowe the doc id. ^^
    wetuwn getvawue(pointew - 1);
  }

  /**
   * g-get the p-paywoad pointew f-fwom a nyowmaw pointew (e.g. 😳😳😳 one w-wetuwned fwom t-the {@wink this#seawch}
   * m-method). òωó
   */
  pubwic i-int getpaywoadpointew(int pointew) {
    p-pweconditions.checkstate(haspaywoads == h-haspaywoads.yes, ^^;;
        "getpaywoadpointew() s-shouwd onwy b-be cawwed on a skip wist that suppowts p-paywoads.");

    // if this s-skip wist has p-paywoads, (✿oωo) the paywoad wiww awways be insewted into the bwock poow
    // b-befowe t-the doc id, and befowe the position i-if thewe is a-a position. rawr
    int positionoffset = haspositions == h-haspositions.yes ? 1 : 0;

    w-wetuwn pointew - 1 - p-positionoffset;
  }


  i-int getpoowsize() {
    w-wetuwn t-this.bwockpoow.wength();
  }


  intbwockpoow getbwockpoow() {
    w-wetuwn bwockpoow;
  }

  pubwic haspaywoads gethaspaywoads() {
    wetuwn haspaywoads;
  }

  /******************
   * h-hewpew m-methods *
   ******************/

  /**
   * get the nyext fowwawd pointew on a given wevew. XD
   *
   * @pawam p-pointew is an awway i-index on this.bwockpoow, 😳 might be sentinew_vawue. (U ᵕ U❁)
   * @pawam w-wevew indicates the wevew of t-the fowwawd pointew w-wiww be acquiwed. UwU i-it is zewo indexed. OwO
   * @wetuwn nyext fowwawd pointew on t-the given wevew, 😳 might be sentinew_vawue.
   */
  p-pwivate int getfowwawdpointew(int pointew, (˘ω˘) int w-wevew) {
    finaw int pointewindex = pointew + w-wevew + 1;

    int fowwawdpointew = b-bwockpoow.get(pointewindex);

    // visibiwity wace
    if (fowwawdpointew == i-initiaw_vawue) {
      // vowatiwe wead to c-cwoss the memowy bawwiew again. òωó
      finaw boowean issafe = ispointewsafe(pointewindex);
      assewt issafe;

      // we-wead the pointew again
      f-fowwawdpointew = b-bwockpoow.get(pointewindex);
    }

    w-wetuwn fowwawdpointew;
  }

 /**
   * s-set the nyext fowwawd pointew on a given w-wevew. OwO
   *
   * @pawam pointew points to the vawue, (✿oωo) of which the p-pointew vawue w-wiww be updated. (⑅˘꒳˘)
   * @pawam w-wevew i-indicates the wevew of the fowwawd pointew wiww be set. it is zewo indexed. /(^•ω•^)
   * @pawam t-tawget t-the vawue fo the tawget pointew which wiww be set. 🥺
   */
  pwivate v-void setfowwawdpointew(int pointew, -.- int wevew, i-int tawget) {
    // u-update h-headew towew if given pointew points to headewtowew. ( ͡o ω ͡o )
    setpointew(pointew + wevew + 1, 😳😳😳 tawget);
  }

  /**
   * set the vawue p-pointed by pointew
   * @pawam pointew point to t-the actuaw position in the poow
   * @pawam tawget the vawue we a-awe going to set
   */
  pwivate v-void setpointew(int pointew, (˘ω˘) int tawget) {
    b-bwockpoow.set(pointew, ^^ t-tawget);
  }

  /**
   * g-gettew of the sentinew v-vawue used b-by this skip wist. σωσ the sentinew v-vawue shouwd b-be pwovided
   * by the compawatow. 🥺
   *
   * @wetuwn s-sentinew vawue used by this skip wist. 🥺
   */
  i-int getsentinewvawue() {
    wetuwn defauwtcompawatow.getsentinewvawue();
  }

  /**
   * wetuwn a-a height h i-in wange [1, /(^•ω•^) maxtowewheight], (⑅˘꒳˘) each nyumbew with c-chance
   * gwowtowewchance ^ (h - 1). -.-
   *
   * @wetuwn a-a integew indicating height. 😳
   */
  pwivate int getwandomtowewheight() {
    int height = 1;
    w-whiwe (height < m-max_towew_height && w-wandom.nextfwoat() < g-gwow_towew_chance) {
      height++;
    }
    wetuwn height;
  }

  @suppwesswawnings("unchecked")
  @ovewwide
  pubwic fwushhandwew<k> g-getfwushhandwew() {
    wetuwn nyew fwushhandwew<>(this);
  }

  pubwic s-static cwass fwushhandwew<k> extends fwushabwe.handwew<skipwistcontainew<k>> {
    p-pwivate finaw skipwistcompawatow<k> compawatow;
    pwivate s-static finaw stwing bwock_poow_pwop_name = "bwockpoow";
    p-pwivate static f-finaw stwing has_positions_pwop_name = "haspositions";
    p-pwivate static finaw s-stwing has_paywoads_pwop_name = "haspaywoads";

    p-pubwic fwushhandwew(skipwistcontainew<k> objecttofwush) {
      s-supew(objecttofwush);
      t-this.compawatow = o-objecttofwush.defauwtcompawatow;
    }

    p-pubwic fwushhandwew(skipwistcompawatow<k> c-compawatow) {
      t-this.compawatow = c-compawatow;
    }

    @ovewwide
    pwotected void d-dofwush(fwushinfo fwushinfo, 😳😳😳 datasewiawizew out) thwows ioexception {
      wong stawttime = getcwock().nowmiwwis();
      s-skipwistcontainew<k> o-objecttofwush = getobjecttofwush();
      f-fwushinfo.addbooweanpwopewty(has_positions_pwop_name, >w<
          objecttofwush.haspositions == haspositions.yes);
      f-fwushinfo.addbooweanpwopewty(has_paywoads_pwop_name, UwU
          o-objecttofwush.haspaywoads == haspaywoads.yes);

      o-objecttofwush.bwockpoow.getfwushhandwew()
          .fwush(fwushinfo.newsubpwopewties(bwock_poow_pwop_name), /(^•ω•^) o-out);
      getfwushtimewstats().timewincwement(getcwock().nowmiwwis() - s-stawttime);
    }

    @ovewwide
    pwotected skipwistcontainew<k> dowoad(fwushinfo f-fwushinfo, 🥺 datadesewiawizew in)
        t-thwows ioexception {
      wong stawttime = getcwock().nowmiwwis();
      i-intbwockpoow bwockpoow = (new i-intbwockpoow.fwushhandwew()).woad(
          fwushinfo.getsubpwopewties(bwock_poow_pwop_name), >_< in);
      getwoadtimewstats().timewincwement(getcwock().nowmiwwis() - s-stawttime);

      haspositions h-haspositions = fwushinfo.getbooweanpwopewty(has_positions_pwop_name)
          ? haspositions.yes : h-haspositions.no;
      haspaywoads h-haspaywoads = fwushinfo.getbooweanpwopewty(has_paywoads_pwop_name)
          ? haspaywoads.yes : haspaywoads.no;

      w-wetuwn nyew s-skipwistcontainew<>(
          this.compawatow, rawr
          bwockpoow, (ꈍᴗꈍ)
          h-haspositions, -.-
          haspaywoads);
    }
  }
}
