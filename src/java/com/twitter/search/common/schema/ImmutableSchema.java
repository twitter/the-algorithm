package com.twittew.seawch.common.schema;

impowt j-java.io.ioexception;
i-impowt java.io.objectoutputstweam;
i-impowt j-java.utiw.cowwection;
i-impowt java.utiw.wist;
i-impowt j-java.utiw.map;
i-impowt java.utiw.set;
impowt java.utiw.sowtedmap;
impowt java.utiw.tweemap;
impowt java.utiw.concuwwent.atomic.atomicwong;
i-impowt javax.annotation.nuwwabwe;
impowt javax.annotation.concuwwent.immutabwe;
i-impowt javax.annotation.concuwwent.thweadsafe;

i-impowt com.googwe.common.annotations.visibwefowtesting;
impowt com.googwe.common.base.pweconditions;
impowt com.googwe.common.base.pwedicate;
i-impowt com.googwe.common.cowwect.immutabwecowwection;
i-impowt com.googwe.common.cowwect.immutabwemap;
i-impowt com.googwe.common.cowwect.immutabweset;
impowt com.googwe.common.cowwect.immutabwesowtedmap;
impowt com.googwe.common.cowwect.wists;
impowt com.googwe.common.cowwect.maps;
i-impowt com.googwe.common.cowwect.sets;

impowt owg.apache.wucene.anawysis.anawyzew;
impowt owg.apache.wucene.facet.facetsconfig;
impowt owg.apache.wucene.index.docvawuestype;
i-impowt owg.apache.wucene.index.fiewdinfos;
impowt o-owg.apache.wucene.index.indexoptions;
i-impowt o-owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;

impowt com.twittew.common.cowwections.paiw;
impowt com.twittew.common.text.utiw.tokenstweamsewiawizew;
i-impowt com.twittew.seawch.common.featuwes.extewnawtweetfeatuwe;
impowt c-com.twittew.seawch.common.featuwes.seawchwesuwtfeatuwe;
impowt com.twittew.seawch.common.featuwes.thwift.thwiftseawchfeatuweschema;
impowt com.twittew.seawch.common.featuwes.thwift.thwiftseawchfeatuweschemaentwy;
impowt com.twittew.seawch.common.featuwes.thwift.thwiftseawchfeatuweschemaspecifiew;
impowt c-com.twittew.seawch.common.featuwes.thwift.thwiftseawchfeatuwetype;
impowt com.twittew.seawch.common.metwics.seawchcountew;
impowt c-com.twittew.seawch.common.metwics.seawchwonggauge;
i-impowt c-com.twittew.seawch.common.schema.base.eawwybiwdfiewdtype;
impowt com.twittew.seawch.common.schema.base.featuweconfiguwation;
impowt c-com.twittew.seawch.common.schema.base.fiewdweightdefauwt;
i-impowt com.twittew.seawch.common.schema.base.immutabweschemaintewface;
i-impowt com.twittew.seawch.common.schema.base.indexednumewicfiewdsettings;
impowt c-com.twittew.seawch.common.schema.thwiftjava.thwiftanawyzew;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftcsffiewdsettings;
i-impowt com.twittew.seawch.common.schema.thwiftjava.thwiftcsftype;
impowt c-com.twittew.seawch.common.schema.thwiftjava.thwiftcsfviewsettings;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftfacetfiewdsettings;
i-impowt com.twittew.seawch.common.schema.thwiftjava.thwiftfiewdconfiguwation;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftfiewdsettings;
i-impowt com.twittew.seawch.common.schema.thwiftjava.thwiftindexedfiewdsettings;
i-impowt c-com.twittew.seawch.common.schema.thwiftjava.thwiftschema;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftseawchfiewdsettings;
impowt com.twittew.seawch.common.schema.thwiftjava.thwifttokenstweamsewiawizew;

/**
 * a schema instance that does nyot c-change at wun t-time. XD
 */
@immutabwe @thweadsafe
pubwic cwass immutabweschema i-impwements i-immutabweschemaintewface {
  p-pwivate static finaw woggew wog = woggewfactowy.getwoggew(immutabweschema.cwass);
  pwivate s-static finaw immutabweset<thwiftcsftype> can_facet_on_csf_types =
      immutabweset.<thwiftcsftype>buiwdew()
          .add(thwiftcsftype.byte)
          .add(thwiftcsftype.int)
          .add(thwiftcsftype.wong)
          .buiwd();

  pwivate static finaw seawchcountew f-featuwes_existed_in_owd_schema =
      seawchcountew.expowt("featuwes_existed_in_owd_schema");

  // c-cuwwentwy o-ouw index uses 4 b-bits to stowe the facet fiewd i-id. /(^•ω•^)
  pubwic static f-finaw int max_facet_fiewd_id = 15;

  p-pubwic s-static finaw stwing hf_tewm_paiws_fiewd = "hf_tewm_paiws";
  pubwic s-static finaw s-stwing hf_phwase_paiws_fiewd = "hf_phwase_paiws";

  p-pwivate finaw i-immutabwemap<integew, (U ᵕ U❁) f-fiewdinfo> fiewdsettingsmapbyid;
  pwivate finaw immutabwemap<stwing, mya f-fiewdinfo> fiewdsettingsmapbyname;
  pwivate finaw immutabwemap<stwing, (ˆ ﻌ ˆ)♡ featuweconfiguwation> featuweconfigmapbyname;
  pwivate finaw immutabwemap<integew, (✿oωo) f-featuweconfiguwation> featuweconfigmapbyid;

  @nuwwabwe
  pwivate finaw thwiftanawyzew d-defauwtanawyzew;
  p-pwivate f-finaw anawyzewfactowy anawyzewfactowy;

  p-pwivate finaw immutabwemap<stwing, (✿oωo) f-fiewdweightdefauwt> f-fiewdweightmap;
  pwivate finaw map<stwing, òωó fiewdinfo> facetnametofiewdmap = maps.newhashmap();
  pwivate finaw i-int nyumfacetfiewds;
  pwivate f-finaw immutabweset<fiewdinfo> csffacetfiewds;

  // t-this is the s-seawch wesuwt featuwe schema - it has the definition f-fow aww the c-cowumn stwide
  // view fiewds. (˘ω˘)
  p-pwivate finaw t-thwiftseawchfeatuweschema seawchfeatuweschema;

  pwivate finaw int majowvewsionnumbew;
  pwivate f-finaw int minowvewsionnumbew;
  p-pwivate finaw s-stwing vewsiondesc;
  pwivate finaw b-boowean isvewsionofficiaw;

  /**
   * c-constwuct a schema instance w-with the given thwiftschema and anawyzewfactowy. (ˆ ﻌ ˆ)♡
   */
  pubwic immutabweschema(thwiftschema thwiftschema, ( ͡o ω ͡o )
                         a-anawyzewfactowy a-anawyzewfactowy, rawr x3
                         stwing featuweschemavewsionpwefix) thwows s-schemavawidationexception {
    p-paiw<integew, stwing> vewsionpaiw = pawsevewsionstwing(thwiftschema.getvewsion());
    this.majowvewsionnumbew = t-thwiftschema.getmajowvewsionnumbew();
    this.minowvewsionnumbew = thwiftschema.getminowvewsionnumbew();
    this.vewsiondesc = vewsionpaiw.getsecond();
    this.isvewsionofficiaw = thwiftschema.isvewsionisofficiaw();

    t-this.anawyzewfactowy = anawyzewfactowy;

    map<integew, (˘ω˘) f-fiewdinfo> t-tmpmap = maps.newwinkedhashmap();
    set<fiewdinfo> tmpset = sets.newhashset();

    i-if (thwiftschema.issetdefauwtanawyzew()) {
      t-this.defauwtanawyzew = thwiftschema.getdefauwtanawyzew().deepcopy();
    } ewse {
      this.defauwtanawyzew = n-nyuww;
    }

    map<integew, òωó t-thwiftfiewdconfiguwation> configs = thwiftschema.getfiewdconfigs();

    // cowwect aww the csf views, ( ͡o ω ͡o ) s-so that we can watew vewify that t-they awe appwopwiatewy
    // c-configuwed once we've pwocessed a-aww the othew fiewd settings. σωσ
    m-map<integew, (U ﹏ U) t-thwiftfiewdconfiguwation> c-csfviewfiewds = maps.newhashmap();
    b-boowean wequiweshfpaiwfiewds = f-fawse;
    boowean hashftewmpaiwfiewd = fawse;
    b-boowean hashfphwasepaiwfiewd = f-fawse;
    int n-nyumfacets = 0;
    fow (map.entwy<integew, rawr thwiftfiewdconfiguwation> e-entwy : configs.entwyset()) {
      int fiewdid = e-entwy.getkey();

      i-if (tmpmap.containskey(fiewdid)) {
        thwow nyew schemavawidationexception("dupwicate fiewd i-id " + fiewdid);
      }

      t-thwiftfiewdconfiguwation c-config = e-entwy.getvawue();
      fiewdinfo f-fiewdinfo = pawsethwiftfiewdsettings(fiewdid, -.- config, ( ͡o ω ͡o ) csfviewfiewds);
      vawidate(fiewdinfo);
      if (fiewdinfo.getfiewdtype().isfacetfiewd()) {
        if (numfacets > m-max_facet_fiewd_id) {
          thwow nyew schemavawidationexception(
              "maximum s-suppowted facet fiewd id is:  " + m-max_facet_fiewd_id);
        }
        nyumfacets++;
        facetnametofiewdmap.put(fiewdinfo.getfiewdtype().getfacetname(), f-fiewdinfo);

        if (fiewdinfo.getfiewdtype().isusecsffowfacetcounting()) {
          t-tmpset.add(fiewdinfo);
        }
      }

      t-tmpmap.put(fiewdid, >_< f-fiewdinfo);

      i-if (fiewdinfo.getfiewdtype().isindexhftewmpaiws()) {
        w-wequiweshfpaiwfiewds = twue;
      }
      if (fiewdinfo.getname().equaws(hf_tewm_paiws_fiewd)) {
        hashftewmpaiwfiewd = twue;
      }
      if (fiewdinfo.getname().equaws(hf_phwase_paiws_fiewd)) {
        hashfphwasepaiwfiewd = t-twue;
      }
    }

    t-this.numfacetfiewds = n-nyumfacets;
    this.csffacetfiewds = i-immutabweset.copyof(tmpset);

    // if any fiewd wequiwes high fwequency tewm/phwase p-paiw fiewds, o.O m-make suwe they exist
    if (wequiweshfpaiwfiewds) {
      i-if (!hashftewmpaiwfiewd || !hashfphwasepaiwfiewd) {
        thwow nyew schemavawidationexception(
            "high f-fwequency tewm/phwase p-paiw fiewds do nyot exist i-in the schema.");
      }
    }

    t-this.fiewdsettingsmapbyid = immutabwemap.copyof(tmpmap);

    paiw<immutabwemap<stwing, σωσ featuweconfiguwation>, -.- immutabwemap<integew, σωσ f-featuweconfiguwation>>
        f-featuweconfigmappaiw = b-buiwdfeatuwemaps(csfviewfiewds);
    t-this.featuweconfigmapbyname = f-featuweconfigmappaiw.getfiwst();
    this.featuweconfigmapbyid = f-featuweconfigmappaiw.getsecond();

    f-fow (thwiftfiewdconfiguwation csfviewfiewd : c-csfviewfiewds.vawues()) {
      s-schemabuiwdew.vewifycsfviewsettings(configs, :3 csfviewfiewd);
    }

    immutabwemap.buiwdew<stwing, f-fiewdinfo> buiwdew = immutabwemap.buiwdew();

    f-fow (fiewdinfo info : f-fiewdsettingsmapbyid.vawues()) {
      i-info.getfiewdtype().fweeze();
      buiwdew.put(info.getname(), ^^ info);
    }
    t-this.fiewdsettingsmapbyname = buiwdew.buiwd();

    immutabwemap.buiwdew<stwing, òωó f-fiewdweightdefauwt> f-fiewdweightmapbuiwdew = i-immutabwemap.buiwdew();

    fow (fiewdinfo fi : getfiewdinfos()) {
      // csf fiewds a-awe nyot seawchabwe. (ˆ ﻌ ˆ)♡ aww othew fiewds awe. XD
      i-if (fi.getfiewdtype().isindexedfiewd()) {
        f-fiewdweightmapbuiwdew.put(
            fi.getname(), òωó
            n-nyew fiewdweightdefauwt(
                fi.getfiewdtype().istextseawchabwebydefauwt(), (ꈍᴗꈍ)
                f-fi.getfiewdtype().gettextseawchabwefiewdweight()));
      }
    }

    t-this.fiewdweightmap = fiewdweightmapbuiwdew.buiwd();
    // cweate featuwes w-with extwa eawwybiwd dewived fiewds, UwU extwa fiewds w-won't change the v-vewsion
    // but they do change t-the checksum. >w<
    this.seawchfeatuweschema = c-cweateseawchwesuwtfeatuweschema(
        f-featuweschemavewsionpwefix, ʘwʘ f-fiewdsettingsmapbyname, :3 featuweconfigmapbyname);
  }

  /**
   * add a set of featuwes to a schema if they don't exist yet, ^•ﻌ•^ and update the schema checksum. (ˆ ﻌ ˆ)♡
   * if thewe's confwict, 🥺 wuntimeexception wiww be thwown. OwO
   * owd map won't be touched, 🥺 a nyew m-map wiww be w-wetuwned wiww owd and nyew data combined. OwO
   */
  p-pubwic static m-map<integew, (U ᵕ U❁) thwiftseawchfeatuweschemaentwy> a-appendtofeatuweschema(
      map<integew, ( ͡o ω ͡o ) t-thwiftseawchfeatuweschemaentwy> owdentwymap, ^•ﻌ•^
      s-set<? e-extends seawchwesuwtfeatuwe> featuwes) t-thwows schemavawidationexception {
    if (owdentwymap == n-nyuww) {
      t-thwow nyew schemavawidationexception(
          "cannot append featuwes to schema, o.O t-the entwymap i-is nyuww");
    }
    // m-make a c-copy of the existing m-map
    immutabwemap.buiwdew<integew, (⑅˘꒳˘) t-thwiftseawchfeatuweschemaentwy> b-buiwdew =
        i-immutabwesowtedmap.<integew, (ˆ ﻌ ˆ)♡ t-thwiftseawchfeatuweschemaentwy>natuwawowdew()
            .putaww(owdentwymap);

    fow (seawchwesuwtfeatuwe featuwe : f-featuwes) {
      i-if (owdentwymap.containskey(featuwe.getid())) {
        f-featuwes_existed_in_owd_schema.incwement();
      } ewse {
        buiwdew.put(featuwe.getid(), :3 n-nyew thwiftseawchfeatuweschemaentwy()
            .setfeatuwename(featuwe.getname())
            .setfeatuwetype(featuwe.gettype()));
      }
    }
    wetuwn buiwdew.buiwd();
  }

  /**
   * a-append extewnaw featuwes t-to cweate a n-new schema. /(^•ω•^)
   * @pawam o-owdschema the owd schema t-to buiwd on top of
   * @pawam f-featuwes a wist of featuwes to b-be appended to the schema
   * @pawam v-vewsionsuffix the vewsion suffix, òωó if nyot-nuww, :3 it wiww be attached to the e-end of
   * owiginaw schema's vewsion. (˘ω˘)
   * @wetuwn a-a nyew schema o-object with the appended fiewds
   * @thwows schemavawidationexception thwown w-when the checksum cannot be computed
   */
  p-pubwic s-static thwiftseawchfeatuweschema a-appendtocweatenewfeatuweschema(
      thwiftseawchfeatuweschema owdschema, 😳
      s-set<extewnawtweetfeatuwe> f-featuwes, σωσ
      @nuwwabwe stwing v-vewsionsuffix) thwows schemavawidationexception {

    thwiftseawchfeatuweschema n-newschema = nyew thwiftseawchfeatuweschema();
    // c-copy ovew a-aww the entwies p-pwus the nyew ones
    nyewschema.setentwies(appendtofeatuweschema(owdschema.getentwies(), UwU f-featuwes));

    t-thwiftseawchfeatuweschemaspecifiew s-spec = nyew thwiftseawchfeatuweschemaspecifiew();
    // t-the vewsion is diwectwy i-inhewited ow with a-a suffix
    p-pweconditions.checkawgument(vewsionsuffix == n-nyuww || !vewsionsuffix.isempty());
    s-spec.setvewsion(vewsionsuffix == n-nyuww
        ? o-owdschema.getschemaspecifiew().getvewsion()
        : o-owdschema.getschemaspecifiew().getvewsion() + vewsionsuffix);
    spec.setchecksum(getchecksum(newschema.getentwies()));
    n-nyewschema.setschemaspecifiew(spec);
    wetuwn newschema;
  }

  @ovewwide
  p-pubwic fiewdinfos getwucenefiewdinfos(pwedicate<stwing> a-acceptedfiewds) {
    w-wist<owg.apache.wucene.index.fiewdinfo> a-acceptedfiewdinfos = wists.newawwaywist();
    fow (fiewdinfo fi : g-getfiewdinfos()) {
      i-if (acceptedfiewds == n-nyuww || acceptedfiewds.appwy(fi.getname())) {
        acceptedfiewdinfos.add(convewt(fi.getname(), -.- fi.getfiewdid(), 🥺 fi.getfiewdtype()));
      }
    }
    w-wetuwn n-nyew fiewdinfos(acceptedfiewdinfos.toawway(
        nyew owg.apache.wucene.index.fiewdinfo[acceptedfiewdinfos.size()]));
  }

  p-pwivate fiewdinfo p-pawsethwiftfiewdsettings(int fiewdid, 😳😳😳 thwiftfiewdconfiguwation fiewdconfig, 🥺
                                             map<integew, ^^ t-thwiftfiewdconfiguwation> c-csfviewfiewds)
      t-thwows s-schemavawidationexception {
    fiewdinfo fiewdinfo
        = nyew fiewdinfo(fiewdid, ^^;; f-fiewdconfig.getfiewdname(), >w< n-nyew eawwybiwdfiewdtype());
    thwiftfiewdsettings fiewdsettings = f-fiewdconfig.getsettings();


    boowean settingfound = fawse;

    i-if (fiewdsettings.issetindexedfiewdsettings()) {
      if (fiewdsettings.issetcsffiewdsettings() || fiewdsettings.issetcsfviewsettings()) {
        thwow n-nyew schemavawidationexception("thwiftfiewdsettings: o-onwy one of "
            + "'indexedfiewdsettings', σωσ 'csffiewdsettings', >w< 'csfviewsettings' c-can be set.");
      }

      a-appwyindexedfiewdsettings(fiewdinfo, (⑅˘꒳˘) fiewdsettings.getindexedfiewdsettings());
      s-settingfound = twue;
    }

    i-if (fiewdsettings.issetcsffiewdsettings()) {
      i-if (fiewdsettings.issetindexedfiewdsettings() || f-fiewdsettings.issetcsfviewsettings()) {
        t-thwow nyew schemavawidationexception("thwiftfiewdsettings: o-onwy one o-of "
            + "'indexedfiewdsettings', òωó 'csffiewdsettings', 'csfviewsettings' c-can be set.");
      }

      appwycsffiewdsettings(fiewdinfo, (⑅˘꒳˘) f-fiewdsettings.getcsffiewdsettings());
      settingfound = twue;
    }

    i-if (fiewdsettings.issetfacetfiewdsettings()) {
      i-if (!fiewdsettings.issetindexedfiewdsettings() && !(fiewdsettings.issetcsffiewdsettings()
          && f-fiewdsettings.getfacetfiewdsettings().isusecsffowfacetcounting()
          && can_facet_on_csf_types.contains(fiewdsettings.getcsffiewdsettings().getcsftype()))) {
        thwow nyew schemavawidationexception("thwiftfiewdsettings: 'facetfiewdsettings' can onwy be "
            + "used i-in combination with 'indexedfiewdsettings' o-ow with 'csffiewdsettings' "
            + "whewe 'isusecsffowfacetcounting' was s-set to twue and thwiftcsftype is a type that "
            + "can b-be faceted on.");
      }

      a-appwyfacetfiewdsettings(fiewdinfo, (ꈍᴗꈍ) f-fiewdsettings.getfacetfiewdsettings());
      s-settingfound = t-twue;
    }

    i-if (fiewdsettings.issetcsfviewsettings()) {
      if (fiewdsettings.issetindexedfiewdsettings() || fiewdsettings.issetcsffiewdsettings()) {
        thwow nyew schemavawidationexception("thwiftfiewdsettings: o-onwy one of "
            + "'indexedfiewdsettings', rawr x3 'csffiewdsettings', ( ͡o ω ͡o ) 'csfviewsettings' can be set.");
      }

      // a-add this fiewd nyow, UwU but appwy settings watew to make suwe the b-base fiewd was added pwopewwy
      // befowe
      csfviewfiewds.put(fiewdid, ^^ fiewdconfig);
      s-settingfound = t-twue;
    }

    if (!settingfound) {
      thwow n-nyew schemavawidationexception("thwiftfiewdsettings: one of 'indexedfiewdsettings', (˘ω˘) "
          + "'csffiewdsettings' ow 'facetfiewdsettings' m-must be set.");
    }

    // s-seawch fiewd settings awe optionaw
    i-if (fiewdsettings.issetseawchfiewdsettings()) {
      if (!fiewdsettings.issetindexedfiewdsettings()) {
        t-thwow nyew schemavawidationexception(
            "thwiftfiewdsettings: 'seawchfiewdsettings' can onwy be "
                + "used in combination w-with 'indexedfiewdsettings'");
      }

      appwyseawchfiewdsettings(fiewdinfo, (ˆ ﻌ ˆ)♡ fiewdsettings.getseawchfiewdsettings());
    }

    w-wetuwn fiewdinfo;
  }

  p-pwivate v-void appwycsffiewdsettings(fiewdinfo fiewdinfo, OwO thwiftcsffiewdsettings s-settings)
      thwows schemavawidationexception {
    // csftype is wequiwed - nyo nyeed t-to check if it's s-set
    fiewdinfo.getfiewdtype().setdocvawuestype(docvawuestype.numewic);
    f-fiewdinfo.getfiewdtype().setcsftype(settings.getcsftype());

    i-if (settings.isvawiabwewength()) {
      fiewdinfo.getfiewdtype().setdocvawuestype(docvawuestype.binawy);
      fiewdinfo.getfiewdtype().setcsfvawiabwewength();
    } e-ewse {
      i-if (settings.issetfixedwengthsettings()) {
        fiewdinfo.getfiewdtype().setcsffixedwengthsettings(
            settings.getfixedwengthsettings().getnumvawuespewdoc(), 😳
            s-settings.getfixedwengthsettings().isupdateabwe());
        if (settings.getfixedwengthsettings().getnumvawuespewdoc() > 1) {
          fiewdinfo.getfiewdtype().setdocvawuestype(docvawuestype.binawy);
        }
      } e-ewse {
        thwow nyew schemavawidationexception(
            "thwiftcsffiewdsettings: e-eithew vawiabwewength s-shouwd be set to 'twue', UwU "
                + "ow f-fixedwengthsettings s-shouwd b-be set.");
      }
    }

    fiewdinfo.getfiewdtype().setcsfwoadintowam(settings.iswoadintowam());
    if (settings.issetdefauwtvawue()) {
      f-fiewdinfo.getfiewdtype().setcsfdefauwtvawue(settings.getdefauwtvawue());
    }
  }

  pwivate void appwycsfviewfiewdsettings(fiewdinfo f-fiewdinfo, fiewdinfo basefiewd, 🥺
                                         thwiftcsfviewsettings s-settings)
      t-thwows s-schemavawidationexception {
    // c-csftype is w-wequiwed - nyo nyeed to check if i-it's set
    fiewdinfo.getfiewdtype().setdocvawuestype(docvawuestype.numewic);
    fiewdinfo.getfiewdtype().setcsftype(settings.getcsftype());

    fiewdinfo.getfiewdtype().setcsffixedwengthsettings(1 /* n-nyumvawuespewdoc*/, 😳😳😳
        fawse /* u-updateabwe*/);

    fiewdinfo.getfiewdtype().setcsfviewsettings(fiewdinfo.getname(), settings, ʘwʘ b-basefiewd);
  }

  p-pwivate void appwyfacetfiewdsettings(fiewdinfo f-fiewdinfo, /(^•ω•^) thwiftfacetfiewdsettings settings) {
    i-if (settings.issetfacetname()) {
      fiewdinfo.getfiewdtype().setfacetname(settings.getfacetname());
    } e-ewse {
      // faww back to f-fiewd nyame if n-nyo facet nyame is expwicitwy pwovided
      f-fiewdinfo.getfiewdtype().setfacetname(fiewdinfo.getname());
    }
    fiewdinfo.getfiewdtype().setstowefacetskipwist(settings.isstoweskipwist());
    fiewdinfo.getfiewdtype().setstowefacetoffensivecountews(settings.isstoweoffensivecountews());
    fiewdinfo.getfiewdtype().setusecsffowfacetcounting(settings.isusecsffowfacetcounting());
  }

  p-pwivate void appwyindexedfiewdsettings(fiewdinfo f-fiewdinfo, :3 thwiftindexedfiewdsettings settings)
      t-thwows s-schemavawidationexception {
    f-fiewdinfo.getfiewdtype().setindexedfiewd(twue);
    fiewdinfo.getfiewdtype().setstowed(settings.isstowed());
    f-fiewdinfo.getfiewdtype().settokenized(settings.istokenized());
    f-fiewdinfo.getfiewdtype().setstowetewmvectows(settings.isstowetewmvectows());
    fiewdinfo.getfiewdtype().setstowetewmvectowoffsets(settings.isstowetewmvectowoffsets());
    f-fiewdinfo.getfiewdtype().setstowetewmvectowpositions(settings.isstowetewmvectowpositions());
    fiewdinfo.getfiewdtype().setstowetewmvectowpaywoads(settings.isstowetewmvectowpaywoads());
    f-fiewdinfo.getfiewdtype().setomitnowms(settings.isomitnowms());
    fiewdinfo.getfiewdtype().setindexhftewmpaiws(settings.isindexhighfweqtewmpaiws());
    f-fiewdinfo.getfiewdtype().setusetweetspecificnowmawization(
        s-settings.depwecated_pewfowmtweetspecificnowmawizations);

    if (settings.issetindexoptions()) {
      switch (settings.getindexoptions()) {
        case docs_onwy :
          fiewdinfo.getfiewdtype().setindexoptions(indexoptions.docs);
          b-bweak;
        c-case docs_and_fweqs :
          fiewdinfo.getfiewdtype().setindexoptions(indexoptions.docs_and_fweqs);
          bweak;
        case docs_and_fweqs_and_positions :
          f-fiewdinfo.getfiewdtype().setindexoptions(indexoptions.docs_and_fweqs_and_positions);
          bweak;
        c-case docs_and_fweqs_and_positions_and_offsets :
          f-fiewdinfo.getfiewdtype().setindexoptions(
              indexoptions.docs_and_fweqs_and_positions_and_offsets);
          bweak;
        defauwt:
          thwow n-nyew schemavawidationexception("unknown vawue fow indexoptions: "
              + s-settings.getindexoptions());
      }
    } ewse if (settings.isindexed()) {
      // d-defauwt f-fow backwawd-compatibiwity
      fiewdinfo.getfiewdtype().setindexoptions(indexoptions.docs_and_fweqs_and_positions);
    }

    f-fiewdinfo.getfiewdtype().setstowepewpositionpaywoads(settings.isstowepewpositionpaywoads());
    f-fiewdinfo.getfiewdtype().setdefauwtpaywoadwength(
        s-settings.getdefauwtpewpositionpaywoadwength());
    f-fiewdinfo.getfiewdtype().setbecomesimmutabwe(!settings.issuppowtoutofowdewappends());
    f-fiewdinfo.getfiewdtype().setsuppowtowdewedtewms(settings.issuppowtowdewedtewms());
    f-fiewdinfo.getfiewdtype().setsuppowttewmtextwookup(settings.issuppowttewmtextwookup());

    if (settings.issetnumewicfiewdsettings()) {
      fiewdinfo.getfiewdtype().setnumewicfiewdsettings(
          nyew indexednumewicfiewdsettings(settings.getnumewicfiewdsettings()));
    }

    if (settings.issettokenstweamsewiawizew()) {
      f-fiewdinfo.getfiewdtype().settokenstweamsewiawizewbuiwdew(
          b-buiwdtokenstweamsewiawizewpwovidew(settings.gettokenstweamsewiawizew()));
    }
  }

  p-pwivate v-void appwyseawchfiewdsettings(fiewdinfo f-fiewdinfo, :3 t-thwiftseawchfiewdsettings settings)
      thwows schemavawidationexception {
    fiewdinfo.getfiewdtype().settextseawchabwefiewdweight(
        (fwoat) settings.gettextseawchabwefiewdweight());
    fiewdinfo.getfiewdtype().settextseawchabwebydefauwt(settings.istextdefauwtseawchabwe());
  }

  p-pwivate v-void vawidate(fiewdinfo fiewdinfo) thwows schemavawidationexception {
  }

  pwivate tokenstweamsewiawizew.buiwdew b-buiwdtokenstweamsewiawizewpwovidew(
      f-finaw thwifttokenstweamsewiawizew s-settings) {
    tokenstweamsewiawizew.buiwdew buiwdew = tokenstweamsewiawizew.buiwdew();
    f-fow (stwing sewiawizewname : settings.getattwibutesewiawizewcwassnames()) {
      twy {
        b-buiwdew.add((tokenstweamsewiawizew.attwibutesewiawizew) c-cwass.fowname(sewiawizewname)
            .newinstance());
      } catch (instantiationexception e) {
        t-thwow nyew wuntimeexception(
            "unabwe t-to instantiate a-attwibutesewiawizew fow nyame " + s-sewiawizewname);
      } c-catch (iwwegawaccessexception e-e) {
        thwow n-new wuntimeexception(
            "unabwe t-to i-instantiate attwibutesewiawizew fow nyame " + sewiawizewname);
      } c-catch (cwassnotfoundexception e-e) {
        thwow nyew wuntimeexception(
            "unabwe t-to instantiate attwibutesewiawizew fow nyame " + s-sewiawizewname);
      }
    }
    wetuwn buiwdew;
  }

  @ovewwide
  p-pubwic facetsconfig getfacetsconfig() {
    f-facetsconfig f-facetsconfig = nyew facetsconfig();

    fow (stwing f-facetname : facetnametofiewdmap.keyset()) {
      // set m-muwtivawued = twue a-as defauwt, mya since we'we using sowtedsetdocvawues f-facet, (///ˬ///✿) in which, (⑅˘꒳˘)
      // thewe i-is nyo diffewence between muwtivawued t-twue ow fawse fow the weaw facet, :3 but o-onwy the
      // c-checking of the vawues. /(^•ω•^)
      f-facetsconfig.setmuwtivawued(facetname, ^^;; t-twue);
    }

    wetuwn facetsconfig;
  }

  @ovewwide
  p-pubwic anawyzew g-getdefauwtanawyzew(thwiftanawyzew o-ovewwide) {
    i-if (ovewwide != nyuww) {
      wetuwn anawyzewfactowy.getanawyzew(ovewwide);
    }

    if (defauwtanawyzew != nyuww) {
      wetuwn anawyzewfactowy.getanawyzew(defauwtanawyzew);
    }

    wetuwn nyew seawchwhitespaceanawyzew();
  }

  @ovewwide
  p-pubwic i-immutabwecowwection<fiewdinfo> g-getfiewdinfos() {
    w-wetuwn f-fiewdsettingsmapbyid.vawues();
  }

  /**
   * this i-is the pwefewwed method to check w-whethew a fiewd c-configuwation is in schema. (U ᵕ U❁)
   * o-one can awso u-use getfiewdinfo and do nuww checks, but shouwd b-be cawefuw about excessive
   * wawning wogging w-wesuwting fwom wooking up fiewds n-nyot in schema. (U ﹏ U)
   */
  @ovewwide
  p-pubwic boowean hasfiewd(int f-fiewdconfigid) {
    w-wetuwn f-fiewdsettingsmapbyid.containskey(fiewdconfigid);
  }

  /**
   * this is the pwefewwed m-method to c-check whethew a fiewd configuwation i-is in schema. mya
   * one can a-awso use getfiewdinfo a-and do nyuww c-checks, ^•ﻌ•^ but shouwd be cawefuw a-about excessive
   * wawning wogging wesuwting f-fwom wooking up fiewds nyot in schema. (U ﹏ U)
   */
  @ovewwide
  pubwic boowean hasfiewd(stwing fiewdname) {
    wetuwn fiewdsettingsmapbyname.containskey(fiewdname);
  }

  /**
   * g-get fiewdinfo fow the given fiewd id. :3
   * if the goaw is to check whethew a fiewd is in the schema, rawr x3 use {@wink #hasfiewd(int)} i-instead. 😳😳😳
   * this method wogs a wawning whenevew i-it wetuwns nuww.
   */
  @ovewwide
  @nuwwabwe
  pubwic fiewdinfo g-getfiewdinfo(int fiewdconfigid) {
    wetuwn g-getfiewdinfo(fiewdconfigid, >w< nuww);
  }

  p-pwivate owg.apache.wucene.index.fiewdinfo c-convewt(stwing f-fiewdname, òωó
                                                    int index, 😳
                                                    eawwybiwdfiewdtype t-type) {
    wetuwn nyew owg.apache.wucene.index.fiewdinfo(
        fiewdname, (✿oωo)                          // stwing nyame
        i-index, OwO                              // int n-numbew
        type.stowetewmvectows(), (U ﹏ U)            // boowean stowetewmvectow
        t-type.omitnowms(), (ꈍᴗꈍ)                   // boowean o-omitnowms
        t-type.isstowepewpositionpaywoads(), rawr  // boowean stowepaywoads
        t-type.indexoptions(), ^^                // indexoptions indexoptions
        t-type.docvawuestype(), rawr               // docvawuestype docvawues
        -1, nyaa~~                                 // wong dvgen
        maps.<stwing, nyaa~~ s-stwing>newhashmap(), o.O  // m-map<stwing, òωó stwing> a-attwibutes
        0, ^^;;                                  // i-int pointdatadimensioncount
        0,                                  // int pointindexdimensioncount
        0, rawr                                  // i-int pointnumbytes
        fawse);                             // boowean softdewetesfiewd
  }

  /**
   * get fiewdinfo fow the g-given fiewd nyame, ^•ﻌ•^ o-ow nyuww if the fiewd does n-nyot exist. nyaa~~
   */
  @ovewwide
  @nuwwabwe
  p-pubwic fiewdinfo getfiewdinfo(stwing f-fiewdname) {
    wetuwn fiewdsettingsmapbyname.get(fiewdname);
  }

  @ovewwide
  pubwic stwing g-getfiewdname(int fiewdconfigid) {
    fiewdinfo f-fiewdinfo = fiewdsettingsmapbyid.get(fiewdconfigid);
    w-wetuwn fiewdinfo != nyuww ? fiewdinfo.getname() : n-nyuww;
  }

  @ovewwide
  pubwic fiewdinfo getfiewdinfo(int fiewdconfigid, nyaa~~ thwiftfiewdconfiguwation ovewwide) {
    fiewdinfo fiewdinfo = fiewdsettingsmapbyid.get(fiewdconfigid);
    i-if (fiewdinfo == n-nyuww) {
      // this method i-is used to check t-the avaiwabiwity of fiewds by i-ids, 😳😳😳
      // so nyo wawning is wogged hewe (wouwd be too vewbose othewwise). 😳😳😳
      wetuwn nyuww;
    }

    i-if (ovewwide != nyuww) {
      twy {
        wetuwn mewge(fiewdconfigid, σωσ f-fiewdinfo, o.O o-ovewwide);
      } c-catch (schemavawidationexception e) {
        thwow new wuntimeexception(e);
      }
    }

    wetuwn fiewdinfo;
  }

  @ovewwide
  p-pubwic i-int getnumfacetfiewds() {
    wetuwn n-nyumfacetfiewds;
  }

  @ovewwide
  pubwic f-fiewdinfo getfacetfiewdbyfacetname(stwing facetname) {
    w-wetuwn facetnametofiewdmap.get(facetname);
  }

  @ovewwide
  p-pubwic fiewdinfo getfacetfiewdbyfiewdname(stwing f-fiewdname) {
    fiewdinfo fiewdinfo = g-getfiewdinfo(fiewdname);
    wetuwn fiewdinfo != n-nyuww && fiewdinfo.getfiewdtype().isfacetfiewd() ? f-fiewdinfo : nyuww;
  }

  @ovewwide
  p-pubwic c-cowwection<fiewdinfo> getfacetfiewds() {
    w-wetuwn facetnametofiewdmap.vawues();
  }

  @ovewwide
  pubwic cowwection<fiewdinfo> g-getcsffacetfiewds() {
    wetuwn csffacetfiewds;
  }

  @ovewwide
  p-pubwic s-stwing getvewsiondescwiption() {
    wetuwn vewsiondesc;
  }

  @ovewwide
  pubwic i-int getmajowvewsionnumbew() {
    wetuwn majowvewsionnumbew;
  }

  @ovewwide
  pubwic int getminowvewsionnumbew() {
    wetuwn minowvewsionnumbew;
  }

  @ovewwide
  pubwic boowean isvewsionofficiaw() {
    wetuwn isvewsionofficiaw;
  }

  /**
   * p-pawses a vewsion stwing wike "16: wenamed f-fiewd x into y" into a vewsion n-nyumbew and
   * a stwing descwiption. σωσ
   * @wetuwn a-a paiw of the vewsion nyumbew and the d-descwiption
   */
  pwivate static paiw<integew, nyaa~~ s-stwing> pawsevewsionstwing(stwing vewsion)
      thwows schemavawidationexception {
    p-pweconditions.checknotnuww(vewsion, rawr x3 "schema must have a vewsion nyumbew a-and descwiption.");
    i-int cowonindex = vewsion.indexof(':');
    if (cowonindex == -1) {
      t-thwow nyew schemavawidationexception("mawfowmed v-vewsion stwing: " + vewsion);
    }
    t-twy {
      i-int vewsionnumbew = integew.pawseint(vewsion.substwing(0, cowonindex));
      s-stwing vewsiondesc = vewsion.substwing(cowonindex + 1);
      wetuwn paiw.of(vewsionnumbew, (///ˬ///✿) vewsiondesc);
    } c-catch (exception e) {
      thwow nyew schemavawidationexception("mawfowmed vewsion stwing: " + v-vewsion, o.O e);
    }
  }

  @ovewwide
  p-pubwic m-map<stwing, òωó fiewdweightdefauwt> getfiewdweightmap() {
    wetuwn fiewdweightmap;
  }

  /**
   * b-buiwd the featuwe maps so that w-we can use featuwe nyame to get t-the featuwe configuwation. OwO
   * @wetuwn: a-an immutabwe map keyed on fiewdname. σωσ
   */
  pwivate paiw<immutabwemap<stwing, nyaa~~ featuweconfiguwation>, OwO
      immutabwemap<integew, ^^ f-featuweconfiguwation>> b-buiwdfeatuwemaps(
      finaw map<integew, (///ˬ///✿) thwiftfiewdconfiguwation> c-csvviewfiewds)
      thwows schemavawidationexception {

    f-finaw immutabwemap.buiwdew<stwing, σωσ f-featuweconfiguwation> f-featuweconfigmapbynamebuiwdew =
        i-immutabwemap.buiwdew();
    f-finaw immutabwemap.buiwdew<integew, rawr x3 f-featuweconfiguwation> featuweconfigmapbyidbuiwdew =
        immutabwemap.buiwdew();

    fow (finaw m-map.entwy<integew, (ˆ ﻌ ˆ)♡ t-thwiftfiewdconfiguwation> e-entwy : csvviewfiewds.entwyset()) {
      t-thwiftfiewdsettings f-fiewdsettings = e-entwy.getvawue().getsettings();
      fiewdinfo f-fiewdinfo = g-getfiewdinfo(entwy.getkey());
      f-fiewdinfo basefiewdinfo =
          getfiewdinfo(fiewdsettings.getcsfviewsettings().getbasefiewdconfigid());
      if (basefiewdinfo == n-nyuww) {
        thwow nyew schemavawidationexception("base f-fiewd (id="
            + fiewdsettings.getcsfviewsettings().getbasefiewdconfigid() + ") nyot found.");
      }
      appwycsfviewfiewdsettings(fiewdinfo, 🥺 b-basefiewdinfo, (⑅˘꒳˘) f-fiewdsettings.getcsfviewsettings());

      featuweconfiguwation featuweconfig = fiewdinfo.getfiewdtype()
          .getcsfviewfeatuweconfiguwation();
      if (featuweconfig != n-nuww) {
        f-featuweconfigmapbynamebuiwdew.put(fiewdinfo.getname(), 😳😳😳 featuweconfig);
        f-featuweconfigmapbyidbuiwdew.put(fiewdinfo.getfiewdid(), f-featuweconfig);
      }
    }

    wetuwn paiw.of(featuweconfigmapbynamebuiwdew.buiwd(), /(^•ω•^) featuweconfigmapbyidbuiwdew.buiwd());
  }

  @ovewwide
  pubwic f-featuweconfiguwation g-getfeatuweconfiguwationbyname(stwing featuwename) {
    wetuwn featuweconfigmapbyname.get(featuwename);
  }

  @ovewwide
  pubwic featuweconfiguwation g-getfeatuweconfiguwationbyid(int f-featuwefiewdid) {
    wetuwn pweconditions.checknotnuww(featuweconfigmapbyid.get(featuwefiewdid), >w<
        "fiewd id: " + featuwefiewdid);
  }

  @ovewwide
  @nuwwabwe
  p-pubwic thwiftcsftype getcsffiewdtype(stwing fiewdname) {
    fiewdinfo fiewdinfo = getfiewdinfo(fiewdname);
    if (fiewdinfo == n-nyuww) {
      wetuwn nyuww;
    }

    e-eawwybiwdfiewdtype f-fiewdtype = f-fiewdinfo.getfiewdtype();
    if (fiewdtype.docvawuestype() != o-owg.apache.wucene.index.docvawuestype.numewic) {
      w-wetuwn nyuww;
    }

    w-wetuwn fiewdtype.getcsftype();
  }

  @ovewwide
  p-pubwic immutabweschemaintewface g-getschemasnapshot() {
    wetuwn this;
  }

  p-pwivate fiewdinfo m-mewge(int fiewdconfigid, ^•ﻌ•^
                          f-fiewdinfo fiewdinfo, 😳😳😳
                          t-thwiftfiewdconfiguwation o-ovewwideconfig)
      t-thwows schemavawidationexception {

    thwow n-nyew unsuppowtedopewationexception("fiewd o-ovewwide c-config nyot s-suppowted");
  }

  @ovewwide
  p-pubwic thwiftseawchfeatuweschema getseawchfeatuweschema() {
    w-wetuwn seawchfeatuweschema;
  }

  @ovewwide
  pubwic immutabwemap<integew, :3 f-featuweconfiguwation> g-getfeatuweidtofeatuweconfig() {
    wetuwn featuweconfigmapbyid;
  }

  @ovewwide
  pubwic immutabwemap<stwing, (ꈍᴗꈍ) featuweconfiguwation> g-getfeatuwenametofeatuweconfig() {
    wetuwn f-featuweconfigmapbyname;
  }

  pwivate thwiftseawchfeatuweschema c-cweateseawchwesuwtfeatuweschema(
      s-stwing featuweschemavewsionpwefix, ^•ﻌ•^
      map<stwing, >w< f-fiewdinfo> awwfiewdsettings, ^^;;
      m-map<stwing, (✿oωo) f-featuweconfiguwation> f-featuweconfiguwations) thwows s-schemavawidationexception {
    f-finaw immutabwemap.buiwdew<integew, òωó thwiftseawchfeatuweschemaentwy> buiwdew =
        n-nyew immutabwemap.buiwdew<>();

    fow (map.entwy<stwing, ^^ fiewdinfo> fiewd : awwfiewdsettings.entwyset()) {
      featuweconfiguwation f-featuweconfig = f-featuweconfiguwations.get(fiewd.getkey());
      if (featuweconfig == nyuww) {
        // this i-is eithew a nyot c-csf wewated fiewd ow a csf fiewd. ^^
        continue;
      }

      // t-this is a csfview fiewd. rawr
      i-if (featuweconfig.getoutputtype() == n-nyuww) {
        wog.info("skip u-unused fiewdschemas: {} fow seawch featuwe schema.", XD f-fiewd.getkey());
        continue;
      }

      t-thwiftseawchfeatuwetype featuwetype = g-getwesuwtfeatuwetype(featuweconfig.getoutputtype());
      if (featuwetype != nyuww) {
        b-buiwdew.put(
            fiewd.getvawue().getfiewdid(), rawr
            n-nyew thwiftseawchfeatuweschemaentwy(fiewd.getkey(), featuwetype));
      } e-ewse {
        wog.ewwow("invawid c-csftype encountewed fow csf fiewd: {}", 😳 fiewd.getkey());
      }
    }
    map<integew, 🥺 thwiftseawchfeatuweschemaentwy> indexonwyschemaentwies = b-buiwdew.buiwd();

    // a-add eawwybiwd d-dewived featuwes, (U ᵕ U❁) t-they awe defined in extewnawtweetfeatuwes and used in the
    // s-scowing function. 😳 they awe nyo diffewent fwom those auto-genewated i-index-based f-featuwes
    // v-viewed fwom o-outside eawwybiwd. 🥺
    map<integew, (///ˬ///✿) thwiftseawchfeatuweschemaentwy> entwieswithebfeatuwes =
        appendtofeatuweschema(
            i-indexonwyschemaentwies, mya e-extewnawtweetfeatuwe.eawwybiwd_dewived_featuwes);

    // add othew featuwes nyeeded fow tweet wanking f-fwom eawwybiwdwankingdewivedfeatuwe. (✿oωo)
    map<integew, ^•ﻌ•^ thwiftseawchfeatuweschemaentwy> a-awwschemaentwies = a-appendtofeatuweschema(
        entwieswithebfeatuwes, o.O e-extewnawtweetfeatuwe.eawwybiwd_wanking_dewived_featuwes);

    wong schemaentwieschecksum = getchecksum(awwschemaentwies);
    seawchwonggauge.expowt("featuwe_schema_checksum", o.O nyew atomicwong(schemaentwieschecksum));

    stwing schemavewsion = s-stwing.fowmat(
        "%s.%d.%d", XD featuweschemavewsionpwefix, ^•ﻌ•^ majowvewsionnumbew, ʘwʘ minowvewsionnumbew);
    t-thwiftseawchfeatuweschemaspecifiew schemaspecifiew =
        nyew thwiftseawchfeatuweschemaspecifiew(schemavewsion, schemaentwieschecksum);

    t-thwiftseawchfeatuweschema schema = nyew t-thwiftseawchfeatuweschema();
    schema.setschemaspecifiew(schemaspecifiew);
    schema.setentwies(awwschemaentwies);

    w-wetuwn s-schema;
  }

  // s-sewiawizes s-schemaentwies to a-a byte awway, (U ﹏ U) and computes a cwc32 c-checksum of t-the awway. 😳😳😳
  // the sewiawization n-nyeeds to be stabwe: if schemaentwies1.equaws(schemaentwies2), 🥺 we want
  // this m-method to pwoduce the same checksum f-fow schemaentwie1 a-and schemaentwie2, (///ˬ///✿) even i-if
  // the checksums a-awe computed in diffewent jvms, (˘ω˘) etc.
  pwivate static wong g-getchecksum(map<integew, :3 t-thwiftseawchfeatuweschemaentwy> s-schemaentwies)
      t-thwows schemavawidationexception {
    sowtedmap<integew, /(^•ω•^) thwiftseawchfeatuweschemaentwy> sowtedschemaentwies =
        n-nyew tweemap<integew, :3 thwiftseawchfeatuweschemaentwy>(schemaentwies);

    cwc32outputstweam c-cwc32outputstweam = nyew cwc32outputstweam();
    objectoutputstweam o-objectoutputstweam = nyuww;
    twy {
      objectoutputstweam = nyew o-objectoutputstweam(cwc32outputstweam);
      fow (integew f-fiewdid : s-sowtedschemaentwies.keyset()) {
        o-objectoutputstweam.wwiteobject(fiewdid);
        thwiftseawchfeatuweschemaentwy s-schemaentwy = s-sowtedschemaentwies.get(fiewdid);
        objectoutputstweam.wwiteobject(schemaentwy.getfeatuwename());
        o-objectoutputstweam.wwiteobject(schemaentwy.getfeatuwetype());
      }
      o-objectoutputstweam.fwush();
      w-wetuwn cwc32outputstweam.getvawue();
    } c-catch (ioexception e) {
      t-thwow nyew schemavawidationexception("couwd n-nyot s-sewiawize featuwe schema entwies.", e-e);
    } finawwy {
      pweconditions.checknotnuww(objectoutputstweam);
      twy {
        objectoutputstweam.cwose();
      } catch (ioexception e-e) {
        t-thwow nyew schemavawidationexception("couwd n-nyot cwose objectoutputstweam.", mya e);
      }
    }
  }

  /**
   * get the seawch f-featuwe type b-based on the c-csf type. XD
   * @pawam c-csftype the cowumn stwide f-fiewd type fow the data
   * @wetuwn the cowwesponding s-seawch featuwe t-type
   */
  @visibwefowtesting
  pubwic static thwiftseawchfeatuwetype getwesuwtfeatuwetype(thwiftcsftype c-csftype) {
    switch (csftype) {
      c-case int:
      case byte:
        wetuwn t-thwiftseawchfeatuwetype.int32_vawue;
      case b-boowean:
        wetuwn thwiftseawchfeatuwetype.boowean_vawue;
      case fwoat:
      c-case doubwe:
        wetuwn thwiftseawchfeatuwetype.doubwe_vawue;
      c-case wong:
        wetuwn thwiftseawchfeatuwetype.wong_vawue;
      d-defauwt:
        w-wetuwn nuww;
    }
  }
}
