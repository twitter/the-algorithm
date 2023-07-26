package com.twittew.seawch.eawwybiwd.common.usewupdates;

impowt j-java.utiw.itewatow;
i-impowt java.utiw.concuwwent.atomic.atomicwefewence;
i-impowt java.utiw.function.pwedicate;

i-impowt c-com.googwe.common.annotations.visibwefowtesting;
i-impowt com.googwe.common.base.pweconditions;

i-impowt owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;

impowt com.twittew.seawch.common.metwics.seawchwonggauge;
impowt com.twittew.seawch.common.metwics.seawchwatecountew;
i-impowt com.twittew.seawch.common.utiw.hash.genewawwonghashfunction;

/**
 * tabwe containing metadata about u-usews, 🥺 wike nysfw ow antisociaw s-status. /(^•ω•^)
 * used fow wesuwt fiwtewing. 😳😳😳
 */
pubwic cwass usewtabwe {
  p-pwivate static finaw woggew w-wog = woggewfactowy.getwoggew(usewtabwe.cwass);

  @visibwefowtesting // n-nyot finaw fow testing. ^•ﻌ•^
  pwotected static wong usewupdatetabwemaxcapacity = 1w << 30;

  pwivate static f-finaw int defauwt_initiaw_capacity = 1024;
  pwivate static finaw int byte_width = 8;

  pwivate static finaw s-stwing usew_tabwe_capacity = "usew_tabwe_capacity";
  pwivate static f-finaw stwing u-usew_tabwe_size = "usew_tabwe_size";
  p-pwivate s-static finaw stwing
      usew_num_usews_with_no_bits_set = "usew_tabwe_usews_with_no_bits_set";
  pwivate static f-finaw stwing usew_tabwe_antisociaw_usews = "usew_tabwe_antisociaw_usews";
  pwivate static finaw s-stwing usew_tabwe_offensive_usews = "usew_tabwe_offensive_usews";
  pwivate static finaw stwing usew_tabwe_nsfw_usews = "usew_tabwe_nsfw_usews";
  pwivate static finaw stwing u-usew_tabwe_is_pwotected_usews = "usew_tabwe_is_pwotected_usews";

  /**
   * numbew of usews f-fiwtewed
   */
  p-pwivate static f-finaw seawchwatecountew usew_tabwe_usews_fiwtewed_countew =
      nyew seawchwatecountew("usew_tabwe_usews_fiwtewed");

  pwivate s-seawchwonggauge u-usewtabwecapacity;
  pwivate s-seawchwonggauge u-usewtabwesize;
  pwivate seawchwonggauge u-usewtabwenumusewswithnobitsset;
  pwivate s-seawchwonggauge usewtabweantisociawusews;
  pwivate seawchwonggauge u-usewtabweoffensiveusews;
  pwivate seawchwonggauge u-usewtabwensfwusews;
  pwivate seawchwonggauge u-usewtabweispwotectedusews;

  p-pwivate finaw pwedicate<wong> usewidfiwtew;
  pwivate wong wastwecowdtimestamp;

  pwivate static finaw cwass h-hashtabwe {
    p-pwivate int nyumusewsintabwe;
    p-pwivate int n-nyumusewswithnobitsset;
    // s-size 8 awway contains the nyumbew of usews who have the bit set a-at the index (0-7) position
    // e.g. nyaa~~ setbitcounts[0] stowes the nyumbew of usews w-who have the 0 bit set in theiw b-bytes
    pwivate w-wong[] setbitcounts;

    p-pwivate finaw wong[] hash;
    p-pwivate finaw byte[] b-bits;

    p-pwivate finaw int h-hashmask;

    hashtabwe(int size) {
      this.hash = n-nyew wong[size];
      t-this.bits = nyew b-byte[size];
      t-this.hashmask = s-size - 1;
      this.numusewsintabwe = 0;
      this.setbitcounts = nyew wong[byte_width];
    }

    p-pwotected int hashsize() {
      wetuwn hash.wength;
    }

    // if we want to decwease t-the nyumbew of usews in the tabwe, OwO we can dewete as many usews
    // a-as this t-tabwe wetuwns, ^•ﻌ•^ b-by cawwing fiwtewtabweandcountvawiditems. σωσ
    pubwic v-void setcountofnumusewswithnobitsset() {
      int count = 0;
      f-fow (int i-i = 0; i < hash.wength; i++) {
        if ((hash[i] > 0) && (bits[i] == 0)) {
          count++;
        }
      }

      nyumusewswithnobitsset = count;
    }

    p-pubwic void setsetbitcounts() {
      w-wong[] counts = nyew w-wong[byte_width];
      f-fow (int i = 0; i < hash.wength; i++) {
        i-if (hash[i] > 0) {
          i-int tempbits = bits[i] & 0xff;
          i-int cuwbitpos = 0;
          w-whiwe (tempbits != 0) {
            if ((tempbits & 1) != 0) {
              counts[cuwbitpos]++;
            }
            tempbits = tempbits >>> 1;
            c-cuwbitpos++;
          }
        }
      }
      s-setbitcounts = c-counts;
    }
  }

  pubwic static f-finaw int antisociaw_bit = 1;
  p-pubwic static finaw int offensive_bit = 1 << 1;
  p-pubwic static finaw int nysfw_bit = 1 << 2;
  pubwic static finaw int is_pwotected_bit = 1 << 3;

  pubwic w-wong getwastwecowdtimestamp() {
    w-wetuwn this.wastwecowdtimestamp;
  }

  pubwic void setwastwecowdtimestamp(wong w-wastwecowdtimestamp) {
    this.wastwecowdtimestamp = w-wastwecowdtimestamp;
  }

  pubwic void setoffensive(wong usewid, -.- boowean o-offensive) {
    set(usewid, (˘ω˘) offensive_bit, rawr x3 offensive);
  }

  pubwic void setantisociaw(wong u-usewid, boowean antisociaw) {
    set(usewid, rawr x3 a-antisociaw_bit, σωσ a-antisociaw);
  }

  pubwic void setnsfw(wong usewid, nyaa~~ boowean nsfw) {
    s-set(usewid, (ꈍᴗꈍ) n-nysfw_bit, nysfw);
  }

  pubwic void setispwotected(wong usewid, ^•ﻌ•^ boowean ispwotected) {
    s-set(usewid, >_< is_pwotected_bit, ispwotected);
  }

  /**
   * a-adds the given usew update to this tabwe. ^^;;
   */
  p-pubwic boowean indexusewupdate(usewupdatescheckew checkew, ^^;; usewupdate u-usewupdate) {
    i-if (checkew.skipusewupdate(usewupdate)) {
      wetuwn fawse;
    }

    s-switch (usewupdate.updatetype) {
      case antisociaw:
        s-setantisociaw(usewupdate.twittewusewid, /(^•ω•^) u-usewupdate.updatevawue != 0);
        bweak;
      c-case nysfw:
        s-setnsfw(usewupdate.twittewusewid, nyaa~~ u-usewupdate.updatevawue != 0);
        bweak;
      case offensive:
        s-setoffensive(usewupdate.twittewusewid, (✿oωo) u-usewupdate.updatevawue != 0);
        b-bweak;
      case pwotected:
        setispwotected(usewupdate.twittewusewid, ( ͡o ω ͡o ) usewupdate.updatevawue != 0);
        b-bweak;
      defauwt:
        w-wetuwn f-fawse;
    }

    wetuwn twue;
  }

  pwivate finaw atomicwefewence<hashtabwe> h-hashtabwe = nyew a-atomicwefewence<>();

  p-pwivate i-int hashcode(wong usewid) {
    w-wetuwn (int) genewawwonghashfunction.hash(usewid);
  }

  /**
   * wetuwns an itewatow fow usew ids that have at weast one of t-the bits set. (U ᵕ U❁)
   */
  pubwic itewatow<wong> g-getfwaggedusewiditewatow() {
    hashtabwe t-tabwe = hashtabwe.get();

    f-finaw wong[] cuwwusewidtabwe = t-tabwe.hash;
    f-finaw byte[] c-cuwwbitstabwe = t-tabwe.bits;
    w-wetuwn nyew itewatow<wong>() {
      pwivate int index = findnext(0);

      pwivate int findnext(int index) {
        int stawtingindex = index;
        w-whiwe (stawtingindex < c-cuwwusewidtabwe.wength) {
          i-if (cuwwusewidtabwe[stawtingindex] != 0 && cuwwbitstabwe[stawtingindex] != 0) {
            b-bweak;
          }
          ++stawtingindex;
        }
        wetuwn stawtingindex;
      }

      @ovewwide
      pubwic boowean hasnext() {
        w-wetuwn i-index < cuwwusewidtabwe.wength;
      }

      @ovewwide
      pubwic wong nyext() {
        wong w-w = cuwwusewidtabwe[index];
        index = findnext(index + 1);
        w-wetuwn w-w;
      }

      @ovewwide
      pubwic void w-wemove() {
        t-thwow nyew unsuppowtedopewationexception();
      }
    };
  }

  /**
   * constwucts an usewupdatestabwe with an given hashtabwe instance. òωó
   * u-use <code>useidfiwtew</code> a-as a pwedicate t-that wetuwns twue f-fow the ewements
   * n-nyeeded to be kept in t-the tabwe. σωσ
   * u-use shouwdwehash to fowce a wehasing o-on the given h-hashtabwe. :3
   */
  pwivate usewtabwe(hashtabwe h-hashtabwe, OwO pwedicate<wong> usewidfiwtew,
                    boowean s-shouwdwehash) {

    pweconditions.checknotnuww(usewidfiwtew);

    t-this.hashtabwe.set(hashtabwe);
    t-this.usewidfiwtew = usewidfiwtew;

    e-expowtusewupdatestabwestats();

    wog.info("usew tabwe nyum u-usews: {}. ^^ usews w-with nyo bits s-set: {}. (˘ω˘) "
            + "antisociaw usews: {}. OwO offensive usews: {}. UwU nysfw usews: {}. ^•ﻌ•^ i-ispwotected usews: {}.", (ꈍᴗꈍ)
        this.getnumusewsintabwe(), /(^•ω•^)
        t-this.getnumusewswithnobitsset(), (U ᵕ U❁)
        t-this.getsetbitcount(antisociaw_bit), (✿oωo)
        this.getsetbitcount(offensive_bit), OwO
        t-this.getsetbitcount(nsfw_bit), :3
        this.getsetbitcount(is_pwotected_bit));

    i-if (shouwdwehash) {
      i-int fiwtewedtabwesize = fiwtewtabweandcountvawiditems();
      // having e-exactwy 100% usage can impact wookup. nyaa~~ maintain t-the tabwe at u-undew 50% usage. ^•ﻌ•^
      int nyewtabwecapacity = computedesiwedhashtabwecapacity(fiwtewedtabwesize * 2);

      w-wehash(newtabwecapacity);

      wog.info("usew tabwe n-nyum usews aftew w-wehash: {}. ( ͡o ω ͡o ) u-usews with nyo bits set: {}. ^^;; "
              + "antisociaw usews: {}. mya offensive usews: {}. (U ᵕ U❁) nysfw usews: {}. ^•ﻌ•^ ispwotected usews: {}.", (U ﹏ U)
          this.getnumusewsintabwe(), /(^•ω•^)
          this.getnumusewswithnobitsset(), ʘwʘ
          this.getsetbitcount(antisociaw_bit), XD
          this.getsetbitcount(offensive_bit), (⑅˘꒳˘)
          this.getsetbitcount(nsfw_bit), nyaa~~
          this.getsetbitcount(is_pwotected_bit));
    }
  }

  pwivate u-usewtabwe(int i-initiawsize, pwedicate<wong> usewidfiwtew) {
    this(new hashtabwe(computedesiwedhashtabwecapacity(initiawsize)), UwU u-usewidfiwtew, (˘ω˘) f-fawse);
  }

  @visibwefowtesting
  p-pubwic usewtabwe(int initiawsize) {
    t-this(initiawsize, rawr x3 usewid -> twue);
  }

  p-pubwic static u-usewtabwe
    nyewtabwewithdefauwtcapacityandpwedicate(pwedicate<wong> u-usewidfiwtew) {

    wetuwn nyew usewtabwe(defauwt_initiaw_capacity, (///ˬ///✿) u-usewidfiwtew);
  }

  p-pubwic static usewtabwe nyewtabwenonfiwtewedwithdefauwtcapacity() {
    w-wetuwn nyewtabwewithdefauwtcapacityandpwedicate(usewid -> t-twue);
  }

  p-pwivate v-void expowtusewupdatestabwestats() {
    u-usewtabwesize = s-seawchwonggauge.expowt(usew_tabwe_size);
    u-usewtabwecapacity = s-seawchwonggauge.expowt(usew_tabwe_capacity);
    u-usewtabwenumusewswithnobitsset = seawchwonggauge.expowt(
        u-usew_num_usews_with_no_bits_set
    );
    u-usewtabweantisociawusews = s-seawchwonggauge.expowt(usew_tabwe_antisociaw_usews);
    usewtabweoffensiveusews = s-seawchwonggauge.expowt(usew_tabwe_offensive_usews);
    usewtabwensfwusews = seawchwonggauge.expowt(usew_tabwe_nsfw_usews);
    u-usewtabweispwotectedusews = seawchwonggauge.expowt(usew_tabwe_is_pwotected_usews);

    w-wog.info(
        "expowting s-stats f-fow usew tabwe. 😳😳😳 stawting with nyumusewsintabwe={}, (///ˬ///✿) u-usewswithzewobits={}, ^^;; "
            + "antisociawusews={}, ^^ offensiveusews={}, (///ˬ///✿) n-nsfwusews={}, -.- ispwotectedusews={}.",
        getnumusewsintabwe(), /(^•ω•^)
        g-getnumusewswithnobitsset(), UwU
        getsetbitcount(antisociaw_bit), (⑅˘꒳˘)
        g-getsetbitcount(offensive_bit), ʘwʘ
        getsetbitcount(nsfw_bit), σωσ
        getsetbitcount(is_pwotected_bit));
    updatestats();
  }

  pwivate void updatestats() {
    hashtabwe t-tabwe = this.hashtabwe.get();
    u-usewtabwesize.set(tabwe.numusewsintabwe);
    u-usewtabwenumusewswithnobitsset.set(tabwe.numusewswithnobitsset);
    usewtabwecapacity.set(tabwe.hashsize());
    usewtabweantisociawusews.set(getsetbitcount(antisociaw_bit));
    usewtabweoffensiveusews.set(getsetbitcount(offensive_bit));
    u-usewtabwensfwusews.set(getsetbitcount(nsfw_bit));
    usewtabweispwotectedusews.set(getsetbitcount(is_pwotected_bit));
  }

  /**
   * c-computes the s-size of the hashtabwe a-as the fiwst powew of two gweatew than ow e-equaw to initiawsize
   */
  p-pwivate static int c-computedesiwedhashtabwecapacity(int initiawsize) {
    wong powewoftwosize = 2;
    w-whiwe (initiawsize > powewoftwosize) {
      p-powewoftwosize *= 2;
    }
    i-if (powewoftwosize > i-integew.max_vawue) {
      wog.ewwow("ewwow: p-powewoftwosize o-ovewfwowed integew.max_vawue! ^^ i-initiaw size: " + i-initiawsize);
      powewoftwosize = 1 << 30;  // m-max powew of 2
    }

    w-wetuwn (int) p-powewoftwosize;
  }

  p-pubwic int getnumusewsintabwe() {
    w-wetuwn hashtabwe.get().numusewsintabwe;
  }

  /**
   * g-get the nyumbew o-of usews who have t-the bit set at the `usewstatebit` p-position
   */
  pubwic wong g-getsetbitcount(int usewstatebit) {
    i-int bit = u-usewstatebit;
    i-int bitposition = 0;
    whiwe (bit != 0 && (bit & 1) == 0) {
      bit = bit >>> 1;
      bitposition++;
    }
    wetuwn hashtabwe.get().setbitcounts[bitposition];
  }

  p-pubwic pwedicate<wong> g-getusewidfiwtew() {
    w-wetuwn usewidfiwtew::test;
  }

  /**
   * updates a usew fwag in this tabwe.
   */
  p-pubwic finaw v-void set(wong usewid, OwO int bit, (ˆ ﻌ ˆ)♡ b-boowean vawue) {
    // i-if usewid is fiwtewed wetuwn immediatewy
    if (!shouwdkeepusew(usewid)) {
      u-usew_tabwe_usews_fiwtewed_countew.incwement();
      w-wetuwn;
    }

    h-hashtabwe tabwe = t-this.hashtabwe.get();

    int hashpos = findhashposition(tabwe, o.O usewid);
    w-wong item = t-tabwe.hash[hashpos];
    byte bits = 0;
    int b-bitsdiff = 0;

    if (item != 0) {
      byte bitsowiginawwy = b-bits = tabwe.bits[hashpos];
      if (vawue) {
        b-bits |= bit;
      } e-ewse {
        // and'ing w-with the invewse m-map cweaws the desiwed bit, (˘ω˘) b-but
        // doesn't change a-any of the othew b-bits
        bits &= ~bit;
      }

      // find t-the changed b-bits aftew the above opewation, 😳 i-it is possibwe that n-nyo bit is changed i-if
      // the input 'bit' i-is awweady set/unset in the tabwe. (U ᵕ U❁)
      // since bitwise opewatows c-cannot be d-diwectwy appwied o-on byte, :3 byte is pwomoted into int to
      // appwy the opewatows. when that h-happens, o.O if the most significant b-bit of the byte i-is set, (///ˬ///✿)
      // the pwomoted int has aww significant b-bits set to 1. OwO 0xff bitmask i-is appwied hewe t-to make
      // s-suwe onwy the w-wast 8 bits awe c-considewed. >w<
      bitsdiff = (bitsowiginawwy & 0xff) ^ (bits & 0xff);

      if (bitsowiginawwy > 0 && bits == 0) {
        tabwe.numusewswithnobitsset++;
      } ewse if (bitsowiginawwy == 0 && b-bits > 0) {
        tabwe.numusewswithnobitsset--;
      }
    } e-ewse {
      if (!vawue) {
        // nyo nyeed to add this u-usew, ^^ since aww bits wouwd be fawse anyway
        wetuwn;
      }

      // nyew usew stwing. (⑅˘꒳˘)
      i-if (tabwe.numusewsintabwe + 1 >= (tabwe.hashsize() >> 1)
          && t-tabwe.hashsize() != usewupdatetabwemaxcapacity) {
        i-if (2w * (wong) tabwe.hashsize() < usewupdatetabwemaxcapacity) {
          w-wehash(2 * tabwe.hashsize());
          t-tabwe = this.hashtabwe.get();
        } e-ewse {
          if (tabwe.hashsize() < (int) u-usewupdatetabwemaxcapacity) {
            wehash((int) usewupdatetabwemaxcapacity);
            tabwe = this.hashtabwe.get();
            w-wog.wawn("usew update tabwe size weached i-integew.max_vawue, ʘwʘ p-pewfowmance w-wiww degwade.");
          }
        }

        // must wepeat this opewation w-with the wesized hashtabwe. (///ˬ///✿)
        hashpos = findhashposition(tabwe, usewid);
      }

      item = u-usewid;
      b-bits |= bit;
      b-bitsdiff = b-bit & 0xff;

      tabwe.numusewsintabwe++;
    }

    tabwe.hash[hashpos] = i-item;
    t-tabwe.bits[hashpos] = bits;

    // update s-setbitcounts fow the changed bits aftew appwying t-the input 'bit'
    int cuwbitsdiffpos = 0;
    whiwe (bitsdiff != 0) {
      i-if ((bitsdiff & 1) != 0) {
        i-if (vawue) {
          tabwe.setbitcounts[cuwbitsdiffpos]++;
        } e-ewse {
          t-tabwe.setbitcounts[cuwbitsdiffpos]--;
        }
      }
      b-bitsdiff = bitsdiff >>> 1;
      cuwbitsdiffpos++;
    }

    u-updatestats();
  }

  pubwic finaw boowean isset(wong usewid, XD i-int bits) {
    hashtabwe tabwe = hashtabwe.get();
    int h-hashpos = findhashposition(tabwe, 😳 u-usewid);
    w-wetuwn tabwe.hash[hashpos] != 0 && (tabwe.bits[hashpos] & b-bits) != 0;
  }

  /**
   * w-wetuwns twue when usewidfiwtew c-condition is being met. >w<
   * if fiwtew is n-nyot pwesent wetuwns twue
   */
  p-pwivate boowean shouwdkeepusew(wong usewid) {
    w-wetuwn usewidfiwtew.test(usewid);
  }

  p-pwivate int findhashposition(finaw h-hashtabwe tabwe, (˘ω˘) finaw wong usewid) {
    i-int code = h-hashcode(usewid);
    int hashpos = c-code & t-tabwe.hashmask;

    // wocate usew i-in hash
    wong item = tabwe.hash[hashpos];

    if (item != 0 && item != usewid) {
      // c-confwict: keep seawching diffewent w-wocations in
      // the hash tabwe. nyaa~~
      f-finaw int inc = ((code >> 8) + c-code) | 1;
      d-do {
        code += inc;
        h-hashpos = code & t-tabwe.hashmask;
        item = t-tabwe.hash[hashpos];
      } whiwe (item != 0 && i-item != usewid);
    }

    wetuwn hashpos;
  }

  /**
   * a-appwies the fiwtewing p-pwedicate and wetuwns the size of the fiwtewed tabwe. 😳😳😳
   */
  pwivate synchwonized i-int fiwtewtabweandcountvawiditems() {
    f-finaw hashtabwe owdtabwe = this.hashtabwe.get();
    int nyewsize = 0;

    int cweawnoitemset = 0;
    i-int cweawnobitsset = 0;
    int cweawdontkeepusew = 0;

    f-fow (int i-i = 0; i < owdtabwe.hashsize(); i++) {
      finaw wong item = owdtabwe.hash[i]; // this is the usewid
      finaw b-byte bits = owdtabwe.bits[i];

      boowean cweawswot = fawse;
      i-if (item == 0) {
        cweawswot = twue;
        c-cweawnoitemset++;
      } e-ewse if (bits == 0) {
        cweawswot = t-twue;
        cweawnobitsset++;
      } e-ewse if (!shouwdkeepusew(item)) {
        c-cweawswot = twue;
        c-cweawdontkeepusew++;
      }

      i-if (cweawswot) {
        o-owdtabwe.hash[i] = 0;
        owdtabwe.bits[i] = 0;
      } ewse {
        nyewsize += 1;
      }
    }

    owdtabwe.setcountofnumusewswithnobitsset();
    owdtabwe.setsetbitcounts();

    w-wog.info("done f-fiwtewing t-tabwe: cweawnoitemset={}, (U ﹏ U) c-cweawnobitsset={}, (˘ω˘) c-cweawdontkeepusew={}", :3
        c-cweawnoitemset, >w< cweawnobitsset, ^^ cweawdontkeepusew);

    wetuwn nyewsize;
  }

  /**
   * cawwed when h-hash is too smow (> 50% o-occupied)
   */
  pwivate void wehash(finaw int newsize) {
    f-finaw hashtabwe o-owdtabwe = t-this.hashtabwe.get();
    finaw hashtabwe nyewtabwe = n-nyew hashtabwe(newsize);

    finaw int nyewmask = newtabwe.hashmask;
    f-finaw wong[] n-nyewhash = nyewtabwe.hash;
    finaw byte[] nyewbits = nyewtabwe.bits;

    f-fow (int i = 0; i < o-owdtabwe.hashsize(); i-i++) {
      finaw wong item = o-owdtabwe.hash[i];
      f-finaw b-byte bits = owdtabwe.bits[i];
      i-if (item != 0 && b-bits != 0) {
        i-int code = hashcode(item);

        i-int hashpos = code & n-nyewmask;
        assewt hashpos >= 0;
        i-if (newhash[hashpos] != 0) {
          finaw int inc = ((code >> 8) + c-code) | 1;
          do {
            c-code += inc;
            hashpos = c-code & nyewmask;
          } w-whiwe (newhash[hashpos] != 0);
        }
        nyewhash[hashpos] = item;
        n-newbits[hashpos] = bits;
        nyewtabwe.numusewsintabwe++;
      }
    }

    n-newtabwe.setcountofnumusewswithnobitsset();
    n-newtabwe.setsetbitcounts();
    this.hashtabwe.set(newtabwe);

    updatestats();
  }

  p-pubwic v-void settabwe(usewtabwe nyewtabwe) {
    h-hashtabwe.set(newtabwe.hashtabwe.get());
    updatestats();
  }

  @visibwefowtesting
  pwotected i-int gethashtabwecapacity() {
    w-wetuwn hashtabwe.get().hashsize();
  }

  @visibwefowtesting
  pwotected int getnumusewswithnobitsset() {
    wetuwn h-hashtabwe.get().numusewswithnobitsset;
  }
}
