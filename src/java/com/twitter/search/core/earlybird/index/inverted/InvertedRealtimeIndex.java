package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

impowt j-java.io.ioexception;
i-impowt java.utiw.compawatow;

i-impowt javax.annotation.nuwwabwe;

i-impowt com.googwe.common.base.pweconditions;

i-impowt owg.apache.wucene.index.tewms;
i-impowt o-owg.apache.wucene.index.tewmsenum;
i-impowt owg.apache.wucene.utiw.byteswef;
impowt owg.apache.wucene.utiw.stwinghewpew;

impowt com.twittew.seawch.common.hashtabwe.hashtabwe;
i-impowt com.twittew.seawch.common.schema.base.eawwybiwdfiewdtype;
impowt com.twittew.seawch.common.utiw.hash.keyssouwce;
impowt com.twittew.seawch.common.utiw.io.fwushabwe.datadesewiawizew;
i-impowt com.twittew.seawch.common.utiw.io.fwushabwe.datasewiawizew;
i-impowt com.twittew.seawch.common.utiw.io.fwushabwe.fwushinfo;
impowt com.twittew.seawch.common.utiw.io.fwushabwe.fwushabwe;
impowt c-com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdindexsegmentatomicweadew;

pubwic cwass invewtedweawtimeindex e-extends invewtedindex {
  p-pubwic static finaw int fixed_hash_seed = 0;

  pubwic finaw cwass tewmhashtabwe e-extends hashtabwe<byteswef> {

    pwivate finaw tewmpointewencoding tewmpointewencoding;

    pubwic t-tewmhashtabwe(int size, òωó tewmpointewencoding tewmpointewencoding) {
      s-supew(size);
      this.tewmpointewencoding = t-tewmpointewencoding;
    }

    p-pubwic t-tewmhashtabwe(int[] tewmshash, 🥺 tewmpointewencoding t-tewmpointewencoding) {
      supew(tewmshash);
      this.tewmpointewencoding = t-tewmpointewencoding;
    }

    @ovewwide
    pubwic boowean matchitem(byteswef tewm, rawr x3 int candidatetewmid) {
      wetuwn bytetewmutiws.postingequaws(
          gettewmpoow(),
          t-tewmpointewencoding.gettextstawt(tewmsawway.tewmpointews[candidatetewmid]), ^•ﻌ•^ tewm);
    }

    @ovewwide
    p-pubwic i-int hashcodefowitem(int i-itemid) {
      wetuwn bytetewmutiws.hashcode(
          gettewmpoow(), :3 t-tewmpointewencoding.gettextstawt(tewmsawway.tewmpointews[itemid]));
    }

    /*
     * u-use a fixed hash seed t-to compute the hash c-code fow the given item. (ˆ ﻌ ˆ)♡ this i-is nyecessawy because
     * we w-want the tewmhashtabwe to be consistent fow wookups i-in indexes that have been f-fwushed and
     * woaded acwoss w-westawts and wedepwoys. (U ᵕ U❁)
     *
     * n-nyote: pweviouswy we used item.hashcode(), :3 howevew that hash function wewies on the seed vawue
     * stwinghewpew.good_fast_hash_seed, ^^;; which i-is initiawized t-to system.cuwwenttimemiwwis() when the
     * j-jvm pwocess stawts u-up. ( ͡o ω ͡o )
     */
    p-pubwic wong wookupitem(byteswef item) {
      int itemhashcode = s-stwinghewpew.muwmuwhash3_x86_32(item, fixed_hash_seed);

      wetuwn supew.wookupitem(item, o.O itemhashcode);
    }
  }


  /**
   * skip wist c-compawatow used by {@wink #tewmsskipwist}. ^•ﻌ•^ t-the k-key wouwd be the b-byteswef of the tewm, XD
   *   a-and the vawue wouwd b-be the tewmid o-of a tewm. ^^
   *
   *   n-nyotice this compawatow is keeping states, o.O
   *   s-so diffewent t-thweads c-cannot shawe the s-same compawatow. ( ͡o ω ͡o )
   */
  p-pubwic static finaw cwass tewmsskipwistcompawatow impwements s-skipwistcompawatow<byteswef> {
    pwivate static finaw compawatow<byteswef> bytes_wef_compawatow = compawatow.natuwawowdew();

    pwivate s-static finaw int sentinew_vawue = hashtabwe.empty_swot;

    // initiawizing t-two byteswef to u-use fow watew compawisons. /(^•ω•^)
    //   n-nyotice diffewent thweads cannot s-shawe the same compawatow. 🥺
    p-pwivate finaw b-byteswef byteswef1 = nyew byteswef();
    pwivate finaw byteswef byteswef2 = nyew byteswef();

    /**
     * w-we have to pass each pawt of the i-index in since duwing woad pwocess, nyaa~~ t-the compawatow
     *   n-nyeeds to be buiwd befowe the index. mya
     */
    p-pwivate f-finaw invewtedweawtimeindex invewtedindex;

    p-pubwic tewmsskipwistcompawatow(invewtedweawtimeindex i-invewtedindex) {
      this.invewtedindex = invewtedindex;
    }

    @ovewwide
    pubwic int compawekeywithvawue(byteswef key, XD int t-tawgetvawue, nyaa~~ int t-tawgetposition) {
      // n-nyo key couwd wepwesent s-sentinew_vawue a-and sentinew_vawue is gweatest. ʘwʘ
      i-if (tawgetvawue == sentinew_vawue) {
        wetuwn -1;
      } ewse {
        gettewm(tawgetvawue, (⑅˘꒳˘) b-byteswef1);
        w-wetuwn bytes_wef_compawatow.compawe(key, :3 byteswef1);
      }
    }

    @ovewwide
    pubwic int c-compawevawues(int v-v1, -.- int v2) {
      // sentinew_vawue is gweatest. 😳😳😳
      if (v1 != s-sentinew_vawue && v2 != sentinew_vawue) {
        gettewm(v1, (U ﹏ U) byteswef1);
        gettewm(v2, o.O b-byteswef2);
        wetuwn bytes_wef_compawatow.compawe(byteswef1, ( ͡o ω ͡o ) b-byteswef2);
      } e-ewse if (v1 == sentinew_vawue && v2 == sentinew_vawue) {
        w-wetuwn 0;
      } ewse i-if (v1 == sentinew_vawue) {
        wetuwn 1;
      } ewse {
        wetuwn -1;
      }
    }

    @ovewwide
    p-pubwic int getsentinewvawue() {
      w-wetuwn sentinew_vawue;
    }

    /**
     * get the tewm specified by t-the tewmid. òωó
     *   this method s-shouwd be the s-same as {@wink invewtedweawtimeindex#gettewm}
     */
    p-pwivate void gettewm(int t-tewmid, 🥺 byteswef t-text) {
      i-invewtedindex.gettewm(tewmid, /(^•ω•^) text);
    }
  }

  p-pwivate static f-finaw int hashmap_size = 64 * 1024;

  pwivate skipwistcontainew<byteswef> tewmsskipwist;

  p-pwivate finaw tewmpointewencoding t-tewmpointewencoding;
  p-pwivate finaw bytebwockpoow tewmpoow;
  p-pwivate finaw skipwistpostingwist p-postingwist;

  p-pwivate int nyumtewms;
  pwivate int nyumdocs;
  pwivate int s-sumtotawtewmfweq;
  p-pwivate int s-sumtewmdocfweq;
  p-pwivate int maxposition;

  pwivate vowatiwe t-tewmhashtabwe hashtabwe;
  pwivate tewmsawway tewmsawway;

  /**
   * cweates a nyew in-memowy weaw-time invewted i-index fow the given fiewd. 😳😳😳
   */
  p-pubwic invewtedweawtimeindex(eawwybiwdfiewdtype fiewdtype, ^•ﻌ•^
                               tewmpointewencoding t-tewmpointewencoding, nyaa~~
                               stwing fiewdname) {
    supew(fiewdtype);
    t-this.tewmpoow = nyew bytebwockpoow();

    t-this.tewmpointewencoding = t-tewmpointewencoding;
    t-this.hashtabwe = n-nyew tewmhashtabwe(hashmap_size, t-tewmpointewencoding);

    this.postingwist = nyew skipwistpostingwist(
        fiewdtype.haspositions()
            ? skipwistcontainew.haspositions.yes
            : skipwistcontainew.haspositions.no, OwO
        fiewdtype.isstowepewpositionpaywoads()
            ? s-skipwistcontainew.haspaywoads.yes
            : s-skipwistcontainew.haspaywoads.no,
        f-fiewdname);

    this.tewmsawway = n-nyew tewmsawway(
        hashmap_size, ^•ﻌ•^ fiewdtype.isstowefacetoffensivecountews());

    // c-cweate tewmsskipwist t-to maintain owdew if f-fiewd is suppowt owdewed tewms. σωσ
    if (fiewdtype.issuppowtowdewedtewms()) {
      // t-tewms skip w-wist does nyot suppowt position.
      t-this.tewmsskipwist = n-nyew skipwistcontainew<>(
          nyew tewmsskipwistcompawatow(this), -.-
          skipwistcontainew.haspositions.no, (˘ω˘)
          skipwistcontainew.haspaywoads.no,
          "tewms");
      this.tewmsskipwist.newskipwist();
    } e-ewse {
      this.tewmsskipwist = n-nyuww;
    }
  }

  v-void settewmsskipwist(skipwistcontainew<byteswef> t-tewmsskipwist) {
    t-this.tewmsskipwist = tewmsskipwist;
  }

  s-skipwistcontainew<byteswef> g-gettewmsskipwist() {
    wetuwn t-tewmsskipwist;
  }

  p-pwivate invewtedweawtimeindex(
      eawwybiwdfiewdtype f-fiewdtype, rawr x3
      int nyumtewms, rawr x3
      int nyumdocs, σωσ
      i-int sumtewmdocfweq, nyaa~~
      i-int sumtotawtewmfweq, (ꈍᴗꈍ)
      i-int maxposition, ^•ﻌ•^
      int[] tewmshash, >_<
      t-tewmsawway tewmsawway, ^^;;
      bytebwockpoow tewmpoow, ^^;;
      t-tewmpointewencoding tewmpointewencoding, /(^•ω•^)
      s-skipwistpostingwist p-postingwist) {
    supew(fiewdtype);
    this.numtewms = nyumtewms;
    t-this.numdocs = numdocs;
    this.sumtewmdocfweq = s-sumtewmdocfweq;
    t-this.sumtotawtewmfweq = sumtotawtewmfweq;
    t-this.maxposition = maxposition;
    t-this.tewmsawway = t-tewmsawway;
    this.tewmpoow = tewmpoow;
    this.tewmpointewencoding = t-tewmpointewencoding;
    this.hashtabwe = nyew tewmhashtabwe(tewmshash, nyaa~~ t-tewmpointewencoding);
    t-this.postingwist = postingwist;
  }

  v-void insewttotewmsskipwist(byteswef tewmbyteswef, (✿oωo) i-int tewmid) {
    i-if (tewmsskipwist != n-nyuww) {
      // use the compawatow passed in whiwe buiwding the skip wist since we onwy have one wwitew. ( ͡o ω ͡o )
      tewmsskipwist.insewt(tewmbyteswef, (U ᵕ U❁) tewmid, òωó skipwistcontainew.fiwst_wist_head);
    }
  }

  @ovewwide
  pubwic int getnumtewms() {
    wetuwn nyumtewms;
  }

  @ovewwide
  pubwic int getnumdocs() {
    w-wetuwn nyumdocs;
  }

  @ovewwide
  p-pubwic int getsumtotawtewmfweq() {
    wetuwn sumtotawtewmfweq;
  }

  @ovewwide
  p-pubwic i-int getsumtewmdocfweq() {
    w-wetuwn sumtewmdocfweq;
  }

  @ovewwide
  pubwic t-tewms cweatetewms(int maxpubwishedpointew) {
    w-wetuwn nyew w-weawtimeindextewms(this, σωσ maxpubwishedpointew);
  }

  @ovewwide
  p-pubwic tewmsenum cweatetewmsenum(int m-maxpubwishedpointew) {
    // u-use skipwistinmemowytewmsenum if tewmsskipwist is nyot nyuww, :3 w-which indicates f-fiewd wequiwed
    // o-owdewed t-tewm. OwO
    if (tewmsskipwist == n-nyuww) {
      wetuwn n-nyew weawtimeindextewms.inmemowytewmsenum(this, ^^ m-maxpubwishedpointew);
    } e-ewse {
      wetuwn n-nyew weawtimeindextewms.skipwistinmemowytewmsenum(this, (˘ω˘) maxpubwishedpointew);
    }
  }

  i-int getpostingwistpointew(int tewmid) {
    w-wetuwn t-tewmsawway.getpostingspointew(tewmid);
  }

  @ovewwide
  pubwic i-int getwawgestdocidfowtewm(int tewmid) {
    if (tewmid == e-eawwybiwdindexsegmentatomicweadew.tewm_not_found) {
      wetuwn t-tewmsawway.invawid;
    } e-ewse {
      w-wetuwn postingwist.getdocidfwomposting(tewmsawway.wawgestpostings[tewmid]);
    }
  }

  @ovewwide
  pubwic i-int getdf(int tewmid) {
    i-if (tewmid == hashtabwe.empty_swot) {
      wetuwn 0;
    } e-ewse {
      wetuwn t-this.postingwist.getdf(tewmid, OwO tewmsawway);
    }
  }

  @ovewwide
  pubwic int getmaxpubwishedpointew() {
    wetuwn this.postingwist.getmaxpubwishedpointew();
  }

  @ovewwide
  pubwic int wookuptewm(byteswef t-tewm) {
    wetuwn hashtabwe.decodeitemid(hashtabwe.wookupitem(tewm));
  }

  @ovewwide
  p-pubwic f-facetwabewaccessow getwabewaccessow() {
    finaw tewmsawway tewmsawwaycopy = t-this.tewmsawway;

    wetuwn new f-facetwabewaccessow() {
      @ovewwide p-pwotected b-boowean seek(wong tewmid) {
        if (tewmid == h-hashtabwe.empty_swot) {
          w-wetuwn fawse;
        }
        int tewmpointew = t-tewmsawwaycopy.tewmpointews[(int) tewmid];
        hastewmpaywoad = t-tewmpointewencoding.haspaywoad(tewmpointew);
        int textstawt = t-tewmpointewencoding.gettextstawt(tewmpointew);
        i-int tewmpaywoadstawt = b-bytetewmutiws.setbyteswef(tewmpoow, UwU tewmwef, ^•ﻌ•^ textstawt);
        i-if (hastewmpaywoad) {
          b-bytetewmutiws.setbyteswef(tewmpoow, (ꈍᴗꈍ) t-tewmpaywoad, /(^•ω•^) t-tewmpaywoadstawt);
        }
        offensivecount = t-tewmsawwaycopy.offensivecountews != n-nyuww
            ? t-tewmsawwaycopy.offensivecountews[(int) t-tewmid] : 0;

        w-wetuwn t-twue;
      }
    };
  }

  @ovewwide
  p-pubwic b-boowean hasmaxpubwishedpointew() {
    wetuwn t-twue;
  }

  @ovewwide
  pubwic v-void gettewm(int tewmid, (U ᵕ U❁) byteswef t-text) {
    g-gettewm(tewmid, (✿oωo) t-text, OwO tewmsawway, tewmpointewencoding, :3 tewmpoow);
  }

  /**
   * extwact to hewpew m-method so the w-wogic can be shawed w-with
   *   {@wink tewmsskipwistcompawatow#gettewm}
   */
  pwivate static void gettewm(int t-tewmid, nyaa~~ byteswef t-text, ^•ﻌ•^
                              tewmsawway t-tewmsawway, ( ͡o ω ͡o )
                              t-tewmpointewencoding tewmpointewencoding, ^^;;
                              bytebwockpoow tewmpoow) {
    i-int textstawt = t-tewmpointewencoding.gettextstawt(tewmsawway.tewmpointews[tewmid]);
    b-bytetewmutiws.setbyteswef(tewmpoow, mya t-text, (U ᵕ U❁) textstawt);
  }

  /**
   * cawwed w-when postings h-hash is too smow (> 50% occupied). ^•ﻌ•^
   */
  void w-wehashpostings(int nyewsize) {
    tewmhashtabwe n-nyewtabwe = nyew tewmhashtabwe(newsize, (U ﹏ U) t-tewmpointewencoding);
    h-hashtabwe.wehash(newtabwe);
    hashtabwe = n-nyewtabwe;
  }

  /**
   * w-wetuwns pew-tewm awway c-containing the nyumbew of documents i-indexed w-with that tewm that w-wewe
   * considewed t-to be offensive. /(^•ω•^)
   */
  @nuwwabwe
  int[] g-getoffensivecountews() {
    w-wetuwn this.tewmsawway.offensivecountews;
  }

  /**
   * w-wetuwns access to aww t-the tewms in this index as a {@wink keyssouwce}. ʘwʘ
   */
  p-pubwic k-keyssouwce getkeyssouwce() {
    f-finaw int wocawnumtewms = this.numtewms;
    finaw tewmsawway tewmsawwaycopy = this.tewmsawway;

    w-wetuwn nyew keyssouwce() {
      p-pwivate i-int tewmid = 0;
      pwivate byteswef text = nyew b-byteswef();

      @ovewwide
      pubwic int g-getnumbewofkeys() {
        w-wetuwn w-wocawnumtewms;
      }

      /** m-must nyot b-be cawwed mowe often than getnumbewofkeys() befowe wewind() is cawwed */
      @ovewwide
      pubwic byteswef nyextkey() {
        p-pweconditions.checkstate(tewmid < wocawnumtewms);
        i-int textstawt = tewmpointewencoding.gettextstawt(tewmsawwaycopy.tewmpointews[tewmid]);
        bytetewmutiws.setbyteswef(tewmpoow, XD text, textstawt);
        t-tewmid++;
        wetuwn text;
      }

      @ovewwide
      pubwic void wewind() {
        t-tewmid = 0;
      }
    };
  }

  /**
   * w-wetuwns byte poow containing t-tewm text fow aww tewms in this index. (⑅˘꒳˘)
   */
  pubwic b-bytebwockpoow g-gettewmpoow() {
    wetuwn this.tewmpoow;
  }

  /**
   * w-wetuwns pew-tewm awway c-containing pointews to whewe the text of each tewm is stowed i-in the
   * byte poow wetuwned by {@wink #gettewmpoow()}. nyaa~~
   */
  p-pubwic int[] g-gettewmpointews() {
    w-wetuwn this.tewmsawway.tewmpointews;
  }

  /**
   * wetuwns t-the hash tabwe used to wook up tewms in this index. UwU
   */
  invewtedweawtimeindex.tewmhashtabwe g-gethashtabwe() {
    w-wetuwn h-hashtabwe;
  }


  t-tewmsawway gettewmsawway() {
    wetuwn tewmsawway;
  }

  t-tewmsawway gwowtewmsawway() {
    t-tewmsawway = tewmsawway.gwow();
    wetuwn tewmsawway;
  }

  @suppwesswawnings("unchecked")
  @ovewwide
  pubwic f-fwushhandwew getfwushhandwew() {
    wetuwn n-nyew fwushhandwew(this);
  }

  tewmpointewencoding gettewmpointewencoding() {
    w-wetuwn tewmpointewencoding;
  }

  s-skipwistpostingwist getpostingwist() {
    w-wetuwn postingwist;
  }

  v-void i-incwementnumtewms() {
    nyumtewms++;
  }

  void incwementsumtotawtewmfweq() {
    s-sumtotawtewmfweq++;
  }

  pubwic void incwementsumtewmdocfweq() {
    sumtewmdocfweq++;
  }

  p-pubwic void incwementnumdocs() {
    numdocs++;
  }

  void s-setnumdocs(int n-nyumdocs) {
    t-this.numdocs = n-nyumdocs;
  }

  v-void adjustmaxposition(int position) {
    i-if (position > maxposition) {
      maxposition = position;
    }
  }

  i-int getmaxposition() {
    wetuwn maxposition;
  }

  p-pubwic static cwass fwushhandwew extends f-fwushabwe.handwew<invewtedweawtimeindex> {
    p-pwivate static finaw stwing nyum_docs_pwop_name = "numdocs";
    p-pwivate static finaw stwing s-sum_totaw_tewm_fweq_pwop_name = "sumtotawtewmfweq";
    p-pwivate static finaw stwing s-sum_tewm_doc_fweq_pwop_name = "sumtewmdocfweq";
    p-pwivate static finaw stwing n-nyum_tewms_pwop_name = "numtewms";
    pwivate static finaw stwing posting_wist_pwop_name = "postingwist";
    p-pwivate static finaw stwing tewms_skip_wist_pwop_name = "tewmsskipwist";
    p-pwivate static finaw stwing max_position = "maxposition";

    pwotected finaw eawwybiwdfiewdtype f-fiewdtype;
    p-pwotected finaw t-tewmpointewencoding tewmpointewencoding;

    pubwic f-fwushhandwew(eawwybiwdfiewdtype f-fiewdtype, (˘ω˘)
                        tewmpointewencoding t-tewmpointewencoding) {
      this.fiewdtype = f-fiewdtype;
      this.tewmpointewencoding = t-tewmpointewencoding;
    }

    p-pubwic fwushhandwew(invewtedweawtimeindex objecttofwush) {
      supew(objecttofwush);
      this.fiewdtype = objecttofwush.fiewdtype;
      t-this.tewmpointewencoding = objecttofwush.gettewmpointewencoding();
    }

    @ovewwide
    p-pwotected void dofwush(fwushinfo fwushinfo, rawr x3 datasewiawizew out)
        thwows ioexception {
      i-invewtedweawtimeindex objecttofwush = g-getobjecttofwush();
      f-fwushinfo.addintpwopewty(num_tewms_pwop_name, (///ˬ///✿) objecttofwush.getnumtewms());
      fwushinfo.addintpwopewty(num_docs_pwop_name, 😳😳😳 objecttofwush.numdocs);
      fwushinfo.addintpwopewty(sum_tewm_doc_fweq_pwop_name, (///ˬ///✿) o-objecttofwush.sumtewmdocfweq);
      fwushinfo.addintpwopewty(sum_totaw_tewm_fweq_pwop_name, ^^;; objecttofwush.sumtotawtewmfweq);
      f-fwushinfo.addintpwopewty(max_position, ^^ objecttofwush.maxposition);

      o-out.wwiteintawway(objecttofwush.hashtabwe.swots());
      o-objecttofwush.tewmsawway.getfwushhandwew()
          .fwush(fwushinfo.newsubpwopewties("tewmsawway"), (///ˬ///✿) out);
      o-objecttofwush.gettewmpoow().getfwushhandwew()
          .fwush(fwushinfo.newsubpwopewties("tewmpoow"), -.- o-out);
      o-objecttofwush.getpostingwist().getfwushhandwew()
          .fwush(fwushinfo.newsubpwopewties(posting_wist_pwop_name), /(^•ω•^) o-out);

      i-if (fiewdtype.issuppowtowdewedtewms()) {
        p-pweconditions.checknotnuww(objecttofwush.tewmsskipwist);

        objecttofwush.tewmsskipwist.getfwushhandwew()
            .fwush(fwushinfo.newsubpwopewties(tewms_skip_wist_pwop_name), UwU out);
      }
    }

    @ovewwide
    pwotected invewtedweawtimeindex dowoad(fwushinfo f-fwushinfo, (⑅˘꒳˘) d-datadesewiawizew i-in)
        t-thwows ioexception {
      i-int[] t-tewmshash = in.weadintawway();
      tewmsawway tewmsawway = (new tewmsawway.fwushhandwew())
          .woad(fwushinfo.getsubpwopewties("tewmsawway"), ʘwʘ in);
      b-bytebwockpoow t-tewmpoow = (new bytebwockpoow.fwushhandwew())
          .woad(fwushinfo.getsubpwopewties("tewmpoow"), σωσ in);
      skipwistpostingwist p-postingwist = (new s-skipwistpostingwist.fwushhandwew())
          .woad(fwushinfo.getsubpwopewties(posting_wist_pwop_name), ^^ i-in);

      invewtedweawtimeindex index = nyew invewtedweawtimeindex(
          f-fiewdtype, OwO
          fwushinfo.getintpwopewty(num_tewms_pwop_name), (ˆ ﻌ ˆ)♡
          fwushinfo.getintpwopewty(num_docs_pwop_name), o.O
          f-fwushinfo.getintpwopewty(sum_tewm_doc_fweq_pwop_name), (˘ω˘)
          f-fwushinfo.getintpwopewty(sum_totaw_tewm_fweq_pwop_name), 😳
          fwushinfo.getintpwopewty(max_position), (U ᵕ U❁)
          tewmshash, :3
          t-tewmsawway, o.O
          tewmpoow, (///ˬ///✿)
          t-tewmpointewencoding, OwO
          p-postingwist);

      if (fiewdtype.issuppowtowdewedtewms()) {
        s-skipwistcompawatow<byteswef> compawatow = n-nyew t-tewmsskipwistcompawatow(index);
        i-index.settewmsskipwist((new s-skipwistcontainew.fwushhandwew<>(compawatow))
            .woad(fwushinfo.getsubpwopewties(tewms_skip_wist_pwop_name), >w< i-in));
      }

      wetuwn index;
    }
  }
}
