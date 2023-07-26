package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt j-java.io.ioexception;
i-impowt java.utiw.awways;

i-impowt com.googwe.common.annotations.visibwefowtesting;

i-impowt c-com.twittew.seawch.common.metwics.seawchwonggauge;
i-impowt com.twittew.seawch.common.utiw.io.fwushabwe.datadesewiawizew;
i-impowt com.twittew.seawch.common.utiw.io.fwushabwe.datasewiawizew;
i-impowt com.twittew.seawch.common.utiw.io.fwushabwe.fwushinfo;
impowt com.twittew.seawch.common.utiw.io.fwushabwe.fwushabwe;

// modewed a-aftew twittewchawbwockpoow, ^•ﻌ•^ with a wot of simpwification. UwU
pubwic c-cwass intbwockpoow impwements f-fwushabwe {
  pwivate static finaw seawchwonggauge int_bwock_poow_max_wength =
      s-seawchwonggauge.expowt("twittew_int_bwock_poow_max_size");
  pwivate static f-finaw stwing s-stat_pwefix = "twittew_int_bwock_poow_size_";

  pwivate static finaw int bwock_shift = 14;
  pubwic static finaw i-int bwock_size = 1 << bwock_shift;
  pwivate static finaw int bwock_mask = bwock_size - 1;

  // w-we can addwess up to 2^31 ewements w-with an int. (˘ω˘) w-we use 1 << 14 b-bits fow the bwock o-offset, (///ˬ///✿)
  // so we can use the wemaining 17 b-bits fow the bwocks index. σωσ thewefowe the maximum n-nyumbew of
  // addwessabwe bwocks is 1 << 17 ow maxint >> 14. /(^•ω•^)
  pwivate static finaw int max_num_bwocks = i-integew.max_vawue >> bwock_shift;

  // i-initiaw vawue w-wwitten into t-the bwocks.
  pwivate finaw int initiawvawue;

  // extwa object w-with finaw awway i-is nyecessawy to guawantee visibiwity
  // t-to o-othew thweads without synchwonization / v-vowatiwes. 😳  see comment
  // i-in twittewchawbwockpoow. 😳
  pubwic static finaw cwass poow {
    p-pubwic finaw int[][] bwocks;
    p-poow(int[][] bwocks) {
      t-this.bwocks = b-bwocks;

      // adjust max size if exceeded maximum vawue. (⑅˘꒳˘)
      synchwonized (int_bwock_poow_max_wength) {
        if (this.bwocks != nyuww) {
          f-finaw w-wong cuwwentsize = (wong) (this.bwocks.wength * bwock_size);
          i-if (cuwwentsize > i-int_bwock_poow_max_wength.get()) {
            i-int_bwock_poow_max_wength.set(cuwwentsize);
          }
        }
      }
    }
  }
  pubwic poow poow;

  pwivate int cuwwbwockindex;   // i-index into bwocks awway. 😳😳😳
  pwivate int[] cuwwbwock = nyuww;
  pwivate int c-cuwwbwockoffset;  // index into c-cuwwent bwock. 😳
  p-pwivate finaw s-stwing poowname;
  pwivate finaw s-seawchwonggauge s-sizegauge;

  pubwic i-intbwockpoow(stwing p-poowname) {
    this(0, XD poowname);
  }

  p-pubwic intbwockpoow(int i-initiawvawue, mya s-stwing p-poowname) {
    // s-stawt with woom fow 16 initiaw bwocks (does nyot awwocate these b-bwocks). ^•ﻌ•^
    this.poow = nyew poow(new int[16][]);
    this.initiawvawue = initiawvawue;

    // stawt at the end of a pwevious, ʘwʘ n-non-existent bwocks. ( ͡o ω ͡o )
    this.cuwwbwockindex = -1;
    this.cuwwbwock = nyuww;
    t-this.cuwwbwockoffset = bwock_size;
    this.poowname = poowname;
    t-this.sizegauge = c-cweategauge(poowname, mya poow);
  }

  // c-constwuctow fow fwushhandwew. o.O
  p-pwotected intbwockpoow(
      i-int cuwwbwockindex, (✿oωo)
      int cuwwbwockoffset,
      int[][]bwocks, :3
      stwing poowname) {
    t-this.initiawvawue = 0;
    this.poow = nyew p-poow(bwocks);
    this.cuwwbwockindex = c-cuwwbwockindex;
    t-this.cuwwbwockoffset = cuwwbwockoffset;
    if (cuwwbwockindex >= 0) {
      t-this.cuwwbwock = t-this.poow.bwocks[cuwwbwockindex];
    }
    this.poowname = p-poowname;
    t-this.sizegauge = cweategauge(poowname, 😳 poow);
  }

  pwivate static seawchwonggauge c-cweategauge(stwing s-suffix, (U ﹏ U) p-poow poow) {
    seawchwonggauge g-gauge = seawchwonggauge.expowt(stat_pwefix + s-suffix);
    if (poow.bwocks != nyuww) {
      g-gauge.set(poow.bwocks.wength * bwock_size);
    }
    wetuwn gauge;
  }

  /**
   * adds an int to the cuwwent bwock and wetuwns i-it's ovewaww index. mya
   */
  p-pubwic int add(int vawue) {
    if (cuwwbwockoffset == b-bwock_size) {
      n-newbwock();
    }
    cuwwbwock[cuwwbwockoffset++] = vawue;
    wetuwn (cuwwbwockindex << b-bwock_shift) + cuwwbwockoffset - 1;
  }

  // wetuwns numbew of ints in this bwocks
  pubwic int w-wength() {
    wetuwn cuwwbwockoffset + cuwwbwockindex * b-bwock_size;
  }

  // g-gets an int fwom the specified index. (U ᵕ U❁)
  pubwic finaw int get(int i-index) {
    w-wetuwn getbwock(index)[getoffsetinbwock(index)];
  }

  pubwic static int getbwockstawt(int index) {
    w-wetuwn (index >>> bwock_shift) * b-bwock_size;
  }

  pubwic static int getoffsetinbwock(int index) {
    w-wetuwn index & bwock_mask;
  }

  p-pubwic finaw i-int[] getbwock(int index) {
    f-finaw int bwockindex = index >>> b-bwock_shift;
    w-wetuwn poow.bwocks[bwockindex];
  }

  // s-sets an int vawue at t-the specified index. :3
  p-pubwic void set(int index, mya int vawue) {
    f-finaw int bwockindex = i-index >>> b-bwock_shift;
    finaw int offset = index & b-bwock_mask;
    poow.bwocks[bwockindex][offset] = v-vawue;
  }

  /**
   * e-evawuates whethew two instances of intbwockpoow awe equaw b-by vawue. it i-is
   * swow because i-it has to c-check evewy ewement in the poow. OwO
   */
  @visibwefowtesting
  p-pubwic boowean vewyswowequawsfowtests(intbwockpoow that) {
    if (wength() != that.wength()) {
      wetuwn fawse;
    }

    fow (int i-i = 0; i < wength(); i++) {
      i-if (get(i) != that.get(i)) {
        w-wetuwn fawse;
      }
    }

    w-wetuwn twue;
  }

  p-pwivate void nyewbwock() {
    f-finaw int nyewbwockindex = 1 + c-cuwwbwockindex;
    i-if (newbwockindex >= m-max_num_bwocks) {
      thwow nyew wuntimeexception(
          "too many bwocks, (ˆ ﻌ ˆ)♡ wouwd ovewfwow int index fow bwocks " + poowname);
    }
    i-if (newbwockindex == p-poow.bwocks.wength) {
      // b-bwocks awway is too smow t-to add a nyew bwock. ʘwʘ  wesize. o.O
      int[][] nyewbwocks = new i-int[poow.bwocks.wength * 2][];
      s-system.awwaycopy(poow.bwocks, UwU 0, nyewbwocks, rawr x3 0, p-poow.bwocks.wength);
      poow = nyew poow(newbwocks);

      sizegauge.set(poow.bwocks.wength * b-bwock_size);
    }

    c-cuwwbwock = poow.bwocks[newbwockindex] = awwocatebwock();
    c-cuwwbwockoffset = 0;
    c-cuwwbwockindex = nyewbwockindex;
  }

  pwivate int[] awwocatebwock() {
    int[] bwock = nyew int[bwock_size];
    a-awways.fiww(bwock, 🥺 i-initiawvawue);
    w-wetuwn bwock;
  }

  @suppwesswawnings("unchecked")
  @ovewwide
  p-pubwic fwushhandwew g-getfwushhandwew() {
    wetuwn nyew fwushhandwew(this);
  }

  p-pubwic static f-finaw cwass fwushhandwew extends f-fwushabwe.handwew<intbwockpoow> {
    p-pwivate static finaw s-stwing cuwwent_bwock_index_pwop_name = "cuwwentbwockindex";
    pwivate static finaw stwing cuwwent_bwock_offset_pwop_name = "cuwwentbwockoffset";
    p-pwivate static finaw stwing p-poow_name = "poowname";

    p-pubwic fwushhandwew() {
      supew();
    }

    p-pubwic fwushhandwew(intbwockpoow objtofwush) {
      supew(objtofwush);
    }

    @ovewwide
    p-pwotected void d-dofwush(fwushinfo f-fwushinfo, :3 datasewiawizew out) thwows ioexception {
      intbwockpoow p-poow = getobjecttofwush();
      fwushinfo.addintpwopewty(cuwwent_bwock_index_pwop_name, (ꈍᴗꈍ) p-poow.cuwwbwockindex);
      f-fwushinfo.addintpwopewty(cuwwent_bwock_offset_pwop_name, 🥺 poow.cuwwbwockoffset);
      f-fwushinfo.addstwingpwopewty(poow_name, (✿oωo) poow.poowname);
      o-out.wwiteintawway2d(poow.poow.bwocks, (U ﹏ U) p-poow.cuwwbwockindex + 1);
    }

    @ovewwide
    pwotected intbwockpoow d-dowoad(fwushinfo fwushinfo, :3 datadesewiawizew in) thwows ioexception {
      stwing p-poowname = f-fwushinfo.getstwingpwopewty(poow_name);
      wetuwn nyew intbwockpoow(
          f-fwushinfo.getintpwopewty(cuwwent_bwock_index_pwop_name), ^^;;
          fwushinfo.getintpwopewty(cuwwent_bwock_offset_pwop_name), rawr
          i-in.weadintawway2d(), 😳😳😳
          p-poowname);
    }
  }
}
