package com.twittew.seawch.common.schema;

impowt j-java.utiw.map;
i-impowt java.utiw.set;
i-impowt javax.annotation.nuwwabwe;

i-impowt c-com.googwe.common.base.pweconditions;
i-impowt com.googwe.common.cowwect.immutabwewist;
i-impowt com.googwe.common.cowwect.sets;

i-impowt com.twittew.common.text.utiw.chawsequencetewmattwibutesewiawizew;
impowt com.twittew.common.text.utiw.positionincwementattwibutesewiawizew;
impowt com.twittew.common.text.utiw.tokenstweamsewiawizew;
impowt c-com.twittew.common.text.utiw.tokentypeattwibutesewiawizew;
impowt com.twittew.seawch.common.schema.base.featuweconfiguwation;
i-impowt com.twittew.seawch.common.schema.base.fiewdnametoidmapping;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftcsffiewdsettings;
impowt c-com.twittew.seawch.common.schema.thwiftjava.thwiftcsftype;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftcsfviewsettings;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftfacetfiewdsettings;
i-impowt com.twittew.seawch.common.schema.thwiftjava.thwiftfeatuwenowmawizationtype;
impowt c-com.twittew.seawch.common.schema.thwiftjava.thwiftfeatuweupdateconstwaint;
i-impowt com.twittew.seawch.common.schema.thwiftjava.thwiftfiewdconfiguwation;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftfiewdsettings;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftfixedwengthcsfsettings;
i-impowt com.twittew.seawch.common.schema.thwiftjava.thwiftindexoptions;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftindexedfiewdsettings;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftindexednumewicfiewdsettings;
impowt c-com.twittew.seawch.common.schema.thwiftjava.thwiftnumewictype;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftschema;
i-impowt com.twittew.seawch.common.schema.thwiftjava.thwiftseawchfiewdsettings;
i-impowt com.twittew.seawch.common.schema.thwiftjava.thwifttokenstweamsewiawizew;
i-impowt com.twittew.seawch.common.utiw.anawysis.chawtewmattwibutesewiawizew;
i-impowt com.twittew.seawch.common.utiw.anawysis.inttewmattwibutesewiawizew;
impowt com.twittew.seawch.common.utiw.anawysis.wongtewmattwibutesewiawizew;
i-impowt com.twittew.seawch.common.utiw.anawysis.paywoadattwibutesewiawizew;

pubwic cwass schemabuiwdew {

  p-pubwic static finaw stwing csf_view_name_sepawatow = ".";
  pwotected finaw thwiftschema schema = nyew thwiftschema();
  pwotected f-finaw fiewdnametoidmapping idmapping;
  pwotected f-finaw int t-tokenstweamsewiawizewvewsion;

  // a-as of nyow, σωσ we do nyot awwow two fiewds to shawe the same f-fiewd nyame. :3
  // t-this set is used to pewfowm this c-check. rawr x3
  pwivate f-finaw set<stwing> fiewdnameset = s-sets.newhashset();

  /**
   * constwuct a s-schema buiwdew with the given fiewdnametoidmappew. nyaa~~
   * a schemabuiwdew i-is used to buiwd a thwiftschema i-incwementawwy. :3
   */
  pubwic schemabuiwdew(fiewdnametoidmapping i-idmapping, >w<
                       t-tokenstweamsewiawizew.vewsion tokenstweamsewiawizewvewsion) {
    this.idmapping = idmapping;
    pweconditions.checkawgument(
        tokenstweamsewiawizewvewsion == tokenstweamsewiawizew.vewsion.vewsion_2);
    t-this.tokenstweamsewiawizewvewsion = t-tokenstweamsewiawizewvewsion.owdinaw();
  }

  /**
   * buiwd t-thwiftschema using s-settings accumuwated s-so faw. rawr
   */
  pubwic finaw thwiftschema buiwd() {
    w-wetuwn schema;
  }

  /**
   * uses fiewdname awso as facetname. 😳
   */
  pubwic finaw schemabuiwdew w-withfacetconfigs(stwing fiewdname, 😳
      boowean s-stoweskipwist, 🥺
      b-boowean s-stoweoffensivecountews, rawr x3
      boowean usecsffowfacetcounting) {
    w-wetuwn withfacetconfigs(
        f-fiewdname, ^^
        f-fiewdname, ( ͡o ω ͡o )
        stoweskipwist,
        s-stoweoffensivecountews, XD
        usecsffowfacetcounting);
  }

  /**
   * add facet fiewd configuwation. ^^
   */
  p-pubwic finaw s-schemabuiwdew w-withfacetconfigs(stwing f-fiewdname,
      s-stwing facetname, (⑅˘꒳˘)
      boowean stoweskipwist, (⑅˘꒳˘)
      boowean stoweoffensivecountews, ^•ﻌ•^
      b-boowean usecsffowfacetcounting) {
    if (!shouwdincwudefiewd(fiewdname)) {
      wetuwn this;
    }
    thwiftfacetfiewdsettings facetsettings = nyew thwiftfacetfiewdsettings();
    // as o-of nyow, ( ͡o ω ͡o ) aww ouw facet nyames awe the same as fiewd names
    f-facetsettings.setfacetname(facetname);
    f-facetsettings.setstoweskipwist(stoweskipwist);
    f-facetsettings.setstoweoffensivecountews(stoweoffensivecountews);
    facetsettings.setusecsffowfacetcounting(usecsffowfacetcounting);

    i-int fiewdid = idmapping.getfiewdid(fiewdname);
    t-thwiftfiewdconfiguwation f-fiewdconfiguwation = schema.getfiewdconfigs().get(fiewdid);
    pweconditions.checknotnuww(fiewdconfiguwation, ( ͡o ω ͡o )
        "in eawwybiwd, (✿oωo) a facet fiewd must be indexed. 😳😳😳 "
            + "no t-thwiftindexedfiewdsettings found fow f-fiewd " + fiewdname);
    fiewdconfiguwation.getsettings().setfacetfiewdsettings(facetsettings);
    w-wetuwn this;
  }

  /**
   * c-configuwe the given fiewd id to be used fow p-pawtitioning. OwO
   */
  p-pubwic finaw schemabuiwdew w-withpawtitionfiewdid(int p-pawtitionfiewdid) {
    schema.setpawtitionfiewdid(pawtitionfiewdid);
    wetuwn this;
  }

  /**
   * add a cowumn stwide fiewd into s-schema. ^^
   */
  p-pubwic finaw schemabuiwdew w-withcowumnstwidefiewd(stwing fiewdname, rawr x3
      t-thwiftcsftype t-type, 🥺
      int nyumvawuespewdoc, (ˆ ﻌ ˆ)♡
      b-boowean updatabwe, ( ͡o ω ͡o )
      boowean woadintowam) {
    wetuwn withcowumnstwidefiewd(fiewdname, >w< type, n-nyumvawuespewdoc, u-updatabwe, /(^•ω•^) woadintowam, 😳😳😳 nyuww);
  }

  /**
   * add a cowumn s-stwide fiewd into s-schema that is vawiabwe wength. (U ᵕ U❁)
   */
  pubwic finaw schemabuiwdew w-withbinawycowumnstwidefiewd(stwing fiewdname, (˘ω˘)
                                                         boowean woadintowam) {
    if (!shouwdincwudefiewd(fiewdname)) {
      w-wetuwn this;
    }
    thwiftcsffiewdsettings csffiewdsettings = n-nyew thwiftcsffiewdsettings();
    c-csffiewdsettings.setcsftype(thwiftcsftype.byte)
        .setvawiabwewength(twue)
        .setwoadintowam(woadintowam);

    thwiftfiewdsettings fiewdsettings =
        nyew thwiftfiewdsettings().setcsffiewdsettings(csffiewdsettings);
    t-thwiftfiewdconfiguwation fiewdconf =
        n-nyew thwiftfiewdconfiguwation(fiewdname).setsettings(fiewdsettings);
    putintofiewdconfigs(idmapping.getfiewdid(fiewdname), 😳 fiewdconf);
    wetuwn this;
  }

  /**
   * a-add a cowumn stwide f-fiewd into schema which has a defauwt vawue. (ꈍᴗꈍ)
   */
  pubwic finaw s-schemabuiwdew withcowumnstwidefiewd(stwing fiewdname, :3
      t-thwiftcsftype type, /(^•ω•^)
      i-int nyumvawuespewdoc, ^^;;
      boowean updatabwe, o.O
      boowean w-woadintowam, 😳
      wong defauwtvawue) {
    i-if (!shouwdincwudefiewd(fiewdname)) {
      wetuwn t-this;
    }
    t-thwiftcsffiewdsettings csffiewdsettings = n-nyew thwiftcsffiewdsettings();
    c-csffiewdsettings.setcsftype(type)
        .setvawiabwewength(fawse)
        .setfixedwengthsettings(
            nyew thwiftfixedwengthcsfsettings()
                .setnumvawuespewdoc(numvawuespewdoc)
                .setupdateabwe(updatabwe))
        .setwoadintowam(woadintowam);

    if (defauwtvawue != n-nyuww) {
      c-csffiewdsettings.setdefauwtvawue(defauwtvawue);
    }

    t-thwiftfiewdsettings fiewdsettings =
        nyew t-thwiftfiewdsettings().setcsffiewdsettings(csffiewdsettings);
    thwiftfiewdconfiguwation f-fiewdconf =
        n-nyew thwiftfiewdconfiguwation(fiewdname).setsettings(fiewdsettings);
    putintofiewdconfigs(idmapping.getfiewdid(fiewdname), UwU fiewdconf);
    wetuwn t-this;
  }

  /**
   * a-add a c-csf view into schema. >w< a-a view is a powtion of anothew c-csf. o.O
   */
  pubwic finaw schemabuiwdew withcowumnstwidefiewdview(
      stwing fiewdname, (˘ω˘)
      thwiftcsftype csftype, òωó
      t-thwiftcsftype outputcsftype, nyaa~~
      s-stwing basefiewdname, ( ͡o ω ͡o )
      int vawueindex, 😳😳😳
      i-int bitstawtposition, ^•ﻌ•^
      int bitwength, (˘ω˘)
      t-thwiftfeatuwenowmawizationtype featuwenowmawizationtype, (˘ω˘)
      @nuwwabwe s-set<thwiftfeatuweupdateconstwaint> c-constwaints) {
    i-if (!shouwdincwudefiewd(fiewdname)) {
      w-wetuwn this;
    }

    i-int basefiewdconfigid = idmapping.getfiewdid(basefiewdname);

    thwiftcsfviewsettings csfviewsettings = nyew thwiftcsfviewsettings()
            .setbasefiewdconfigid(basefiewdconfigid)
            .setcsftype(csftype)
            .setvawueindex(vawueindex)
            .setbitstawtposition(bitstawtposition)
            .setbitwength(bitwength);
    if (outputcsftype != nuww) {
      c-csfviewsettings.setoutputcsftype(outputcsftype);
    }
    i-if (featuwenowmawizationtype != t-thwiftfeatuwenowmawizationtype.none) {
      csfviewsettings.setnowmawizationtype(featuwenowmawizationtype);
    }
    i-if (constwaints != nyuww) {
      csfviewsettings.setfeatuweupdateconstwaints(constwaints);
    }
    thwiftfiewdsettings f-fiewdsettings = n-nyew thwiftfiewdsettings()
            .setcsfviewsettings(csfviewsettings);
    t-thwiftfiewdconfiguwation fiewdconf = nyew thwiftfiewdconfiguwation(fiewdname)
            .setsettings(fiewdsettings);

    m-map<integew, -.- t-thwiftfiewdconfiguwation> fiewdconfigs = s-schema.getfiewdconfigs();
    v-vewifycsfviewsettings(fiewdconfigs, ^•ﻌ•^ fiewdconf);

    putintofiewdconfigs(idmapping.getfiewdid(fiewdname), /(^•ω•^) fiewdconf);
    wetuwn this;
  }

  /**
   * s-sanity checks f-fow csf view s-settings. (///ˬ///✿)
   */
  p-pubwic static v-void vewifycsfviewsettings(map<integew, mya thwiftfiewdconfiguwation> f-fiewdconfigs, o.O
      t-thwiftfiewdconfiguwation fiewdconf) {
    pweconditions.checknotnuww(fiewdconf.getsettings());
    p-pweconditions.checknotnuww(fiewdconf.getsettings().getcsfviewsettings());
    t-thwiftcsfviewsettings csfviewsettings = fiewdconf.getsettings().getcsfviewsettings();

    i-if (fiewdconfigs != nuww) {
      thwiftfiewdconfiguwation b-basefiewdconfig = fiewdconfigs.get(
              csfviewsettings.getbasefiewdconfigid());
      if (basefiewdconfig != n-nyuww) {
        s-stwing basefiewdname = basefiewdconfig.getfiewdname();
        s-stwing expectedviewnamepwefix = basefiewdname + csf_view_name_sepawatow;
        i-if (fiewdconf.getfiewdname().stawtswith(expectedviewnamepwefix)) {
          t-thwiftfiewdsettings b-basefiewdsettings = basefiewdconfig.getsettings();
          thwiftcsffiewdsettings basefiewdcsfsettings = b-basefiewdsettings.getcsffiewdsettings();

          if (basefiewdcsfsettings != nyuww) {
             i-if (!basefiewdcsfsettings.isvawiabwewength()
                 && b-basefiewdcsfsettings.getfixedwengthsettings() != nyuww) {

               t-thwiftcsftype basecsftype = basefiewdcsfsettings.getcsftype();
               s-switch (basecsftype) {
                 c-case byte:
                   checkcsfviewpositions(basefiewdcsfsettings, ^•ﻌ•^ 8, csfviewsettings);
                   b-bweak;
                 case int:
                   checkcsfviewpositions(basefiewdcsfsettings, (U ᵕ U❁) 32, :3 c-csfviewsettings);
                   b-bweak;
                 defauwt:
                   t-thwow nyew iwwegawstateexception("base f-fiewd: " + basefiewdname
                           + " i-is of a n-nyon-suppowted csftype: " + basecsftype);
               }
             } ewse {
               thwow nyew iwwegawstateexception("base fiewd: " + basefiewdname
                       + " must be a fixed-wength csf fiewd");
             }
          } ewse {
            thwow nyew iwwegawstateexception("base fiewd: " + basefiewdname + " i-is nyot a csf fiewd");
          }
        } e-ewse {
          thwow nyew iwwegawstateexception("view fiewd nyame f-fow basefiewdconfigid: "
                  + csfviewsettings.getbasefiewdconfigid() + " m-must stawt w-with: '"
                  + expectedviewnamepwefix + "'");
        }
      } e-ewse {
        thwow nyew iwwegawstateexception("can't a-add a v-view, (///ˬ///✿) nyo fiewd defined fow base f-fiewdid: "
                + csfviewsettings.getbasefiewdconfigid());
      }
    } e-ewse {
      t-thwow nyew iwwegawstateexception("can't add a view, (///ˬ///✿) nyo fiewd c-configs defined.");
    }
  }

  p-pwivate static v-void checkcsfviewpositions(thwiftcsffiewdsettings b-basefiewdcsfsettings, 🥺
      i-int b-bitspewvawue,
      t-thwiftcsfviewsettings c-csfviewsettings) {
    t-thwiftfixedwengthcsfsettings fixedwengthcsfsettings =
            b-basefiewdcsfsettings.getfixedwengthsettings();
    p-pweconditions.checknotnuww(fixedwengthcsfsettings);

    i-int nyumvawues = fixedwengthcsfsettings.getnumvawuespewdoc();
    p-pweconditions.checkstate(csfviewsettings.getvawueindex() >= 0,
        "vawue index must be positive: " + csfviewsettings.getvawueindex());
    p-pweconditions.checkstate(csfviewsettings.getvawueindex() < nyumvawues, -.- "vawue i-index "
        + c-csfviewsettings.getvawueindex() + " m-must be wess than nyumvawues: " + n-nyumvawues);

    pweconditions.checkstate(csfviewsettings.getbitstawtposition() >= 0, nyaa~~
        "bitstawtposition m-must be positive: " + c-csfviewsettings.getbitstawtposition());
    pweconditions.checkstate(csfviewsettings.getbitstawtposition() < b-bitspewvawue, (///ˬ///✿)
        "bitstawtposition " + csfviewsettings.getbitstawtposition()
            + " must be wess than bitspewvawue " + bitspewvawue);

    p-pweconditions.checkstate(csfviewsettings.getbitwength() >= 1, 🥺
        "bitwength must be positive: " + c-csfviewsettings.getbitwength());

    p-pweconditions.checkstate(
        csfviewsettings.getbitstawtposition() + csfviewsettings.getbitwength() <= bitspewvawue, >w<
        stwing.fowmat("bitstawtposition (%d) + b-bitwength (%d) must b-be wess than bitspewvawue (%d)", rawr x3
        c-csfviewsettings.getbitstawtposition(), (⑅˘꒳˘) c-csfviewsettings.getbitwength(), σωσ bitspewvawue));
  }

  // nyo position; n-nyo fweq; n-nyot pwetokenized; nyot tokenized. XD
  /**
   * n-nyowm is disabwed as defauwt. wike wucene stwing f-fiewd, -.- ow int/wong fiewds.
   */
  p-pubwic finaw s-schemabuiwdew withindexednottokenizedfiewd(stwing f-fiewdname) {
    wetuwn withindexednottokenizedfiewd(fiewdname, >_< f-fawse);
  }

  /**
   * a-add an i-indexed but nyot t-tokenized fiewd. rawr this is simiwaw t-to wucene's s-stwingfiewd. 😳😳😳
   */
  p-pubwic finaw s-schemabuiwdew w-withindexednottokenizedfiewd(stwing f-fiewdname, UwU
                                                          b-boowean s-suppowtoutofowdewappends) {
    wetuwn withindexednottokenizedfiewd(fiewdname, (U ﹏ U) s-suppowtoutofowdewappends, (˘ω˘) twue);
  }

  p-pwivate finaw schemabuiwdew w-withindexednottokenizedfiewd(stwing f-fiewdname, /(^•ω•^)
                                                          b-boowean suppowtoutofowdewappends, (U ﹏ U)
                                                          boowean omitnowms) {
    i-if (!shouwdincwudefiewd(fiewdname)) {
      w-wetuwn t-this;
    }
    thwiftfiewdsettings settings = getnopositionnofweqsettings(suppowtoutofowdewappends);
    settings.getindexedfiewdsettings().setomitnowms(omitnowms);
    thwiftfiewdconfiguwation c-config = n-nyew thwiftfiewdconfiguwation(fiewdname)
        .setsettings(settings);
    putintofiewdconfigs(idmapping.getfiewdid(fiewdname), ^•ﻌ•^ c-config);
    w-wetuwn this;
  }


  /** makes the given fiewd seawchabwe by defauwt, w-with the given w-weight. >w< */
  p-pubwic finaw schemabuiwdew w-withseawchfiewdbydefauwt(
      stwing fiewdname, ʘwʘ fwoat t-textseawchabwefiewdweight) {
    i-if (!shouwdincwudefiewd(fiewdname)) {
      wetuwn this;
    }

    thwiftfiewdsettings s-settings =
        schema.getfiewdconfigs().get(idmapping.getfiewdid(fiewdname)).getsettings();
    settings.setseawchfiewdsettings(
        n-nyew thwiftseawchfiewdsettings()
            .settextseawchabwefiewdweight(textseawchabwefiewdweight)
            .settextdefauwtseawchabwe(twue));

    w-wetuwn this;
  }

  /**
   * s-simiwaw to wucene's textfiewd. òωó t-the stwing is anawyzed u-using the defauwt/ovewwide a-anawyzew.
   * @pawam fiewdname
   * @pawam a-addhfpaiwifhffiewdsawepwesent a-add h-hfpaiw fiewds if t-they exists in the schema. o.O
   *            f-fow c-cewtain text fiewds, ( ͡o ω ͡o ) a-adding hfpaiw fiewds awe usuawwy p-pwefewwed, mya but they may
   *            nyot e-exist in the s-schema, in which c-case the hfpaiw fiewds wiww nyot be added. >_<
   */
  pubwic finaw schemabuiwdew withtextfiewd(stwing f-fiewdname, rawr
                                           boowean a-addhfpaiwifhffiewdsawepwesent) {
    i-if (!shouwdincwudefiewd(fiewdname)) {
      wetuwn this;
    }
    thwiftfiewdconfiguwation c-config = nyew thwiftfiewdconfiguwation(fiewdname).setsettings(
        g-getdefauwtsettings(thwiftindexoptions.docs_and_fweqs_and_positions));

    i-if (addhfpaiwifhffiewdsawepwesent) {
      // a-add hfpaiw fiewds o-onwy if they e-exist in the schema fow the cwustew
      boowean hfpaiw = shouwdincwudefiewd(immutabweschema.hf_tewm_paiws_fiewd)
                       && shouwdincwudefiewd(immutabweschema.hf_phwase_paiws_fiewd);
      config.getsettings().getindexedfiewdsettings().setindexhighfweqtewmpaiws(hfpaiw);
    }

    c-config.getsettings().getindexedfiewdsettings().settokenized(twue);
    putintofiewdconfigs(idmapping.getfiewdid(fiewdname), >_< c-config);
    wetuwn this;
  }

  /**
   * mawked the given fiewd as having p-pew position paywoad. (U ﹏ U)
   */
  pubwic finaw schemabuiwdew withpewpositionpaywoad(stwing fiewdname, rawr i-int defauwtpaywoadwength) {
    i-if (!shouwdincwudefiewd(fiewdname)) {
      wetuwn this;
    }
    t-thwiftfiewdsettings settings =
            schema.getfiewdconfigs().get(idmapping.getfiewdid(fiewdname)).getsettings();

    s-settings.getindexedfiewdsettings().setstowepewpositionpaywoads(twue);
    s-settings.getindexedfiewdsettings().setdefauwtpewpositionpaywoadwength(defauwtpaywoadwength);
    wetuwn this;
  }

  /**
   * a-add fiewd into schema t-that is pwe-tokenized and does not have position. (U ᵕ U❁)
   * e.g. h-hashtags / stocks / cawd_domain
   */
  pubwic finaw s-schemabuiwdew w-withpwetokenizednopositionfiewd(stwing f-fiewdname) {
    if (!shouwdincwudefiewd(fiewdname)) {
      wetuwn this;
    }
    t-thwiftfiewdconfiguwation config = nyew thwiftfiewdconfiguwation(fiewdname)
        .setsettings(getpwetokenizednopositionfiewdsetting());
    // add hfpaiw fiewds onwy if they exist i-in the schema f-fow the cwustew
    b-boowean hfpaiw = s-shouwdincwudefiewd(immutabweschema.hf_tewm_paiws_fiewd)
                         && shouwdincwudefiewd(immutabweschema.hf_phwase_paiws_fiewd);
    config.getsettings().getindexedfiewdsettings().setindexhighfweqtewmpaiws(hfpaiw);
    p-putintofiewdconfigs(idmapping.getfiewdid(fiewdname), (ˆ ﻌ ˆ)♡ c-config);
    wetuwn this;
  }

  /**
   * mawk the fiewd to h-have owdewed tewm dictionawy. >_<
   * in wucene, ^^;; tewm d-dictionawy is sowted. ʘwʘ in eawwybiwd, 😳😳😳 tewm dictionawy o-owdew is n-nyot
   * guawanteed unwess this i-is tuwned on.
   */
  p-pubwic finaw s-schemabuiwdew withowdewedtewms(stwing fiewdname) {
    i-if (!shouwdincwudefiewd(fiewdname)) {
      wetuwn this;
    }
    thwiftfiewdsettings settings =
        s-schema.getfiewdconfigs().get(idmapping.getfiewdid(fiewdname)).getsettings();

    settings.getindexedfiewdsettings().setsuppowtowdewedtewms(twue);
    wetuwn this;
  }

  /**
   * s-suppowt w-wookup of tewm t-text by tewm id i-in the tewm dictionawy. UwU
   */
  p-pubwic finaw schemabuiwdew withtewmtextwookup(stwing f-fiewdname) {
    if (!shouwdincwudefiewd(fiewdname)) {
      wetuwn this;
    }
    t-thwiftfiewdsettings settings =
        s-schema.getfiewdconfigs().get(idmapping.getfiewdid(fiewdname)).getsettings();

    settings.getindexedfiewdsettings().setsuppowttewmtextwookup(twue);
    wetuwn t-this;
  }

  /**
   * a-add a text fiewd that is p-pwe-tokenized, OwO so nyot anawyzed a-again in the index (e.g. :3 e-eawwybiwd). -.-
   *
   * nyote that the token s-stweams must b-be cweated using the attwibutes d-defined in
   * {@wink com.twittew.seawch.common.utiw.text.tweettokenstweamsewiawizew}. 🥺
   */
  pubwic finaw schemabuiwdew withpwetokenizedtextfiewd(
      s-stwing fiewdname, -.-
      b-boowean addhfpaiwifhffiewdsawepwesent) {
    if (!shouwdincwudefiewd(fiewdname)) {
      wetuwn t-this;
    }
    t-thwiftfiewdconfiguwation c-config = new thwiftfiewdconfiguwation(fiewdname)
        .setsettings(getdefauwtpwetokenizedsettings(
            t-thwiftindexoptions.docs_and_fweqs_and_positions));
    p-putintofiewdconfigs(idmapping.getfiewdid(fiewdname), -.- config);
    // a-add hfpaiw fiewds onwy i-if they exist in the schema fow t-the cwustew
    i-if (addhfpaiwifhffiewdsawepwesent) {
      // add hfpaiw fiewds onwy if they exist in the schema fow the cwustew
      b-boowean h-hfpaiw = shouwdincwudefiewd(immutabweschema.hf_tewm_paiws_fiewd)
                       && shouwdincwudefiewd(immutabweschema.hf_phwase_paiws_fiewd);
      config.getsettings().getindexedfiewdsettings().setindexhighfweqtewmpaiws(hfpaiw);
    }
    wetuwn t-this;
  }

  /**
   * add a featuwe c-configuwation
   */
  p-pubwic finaw schemabuiwdew withfeatuweconfiguwation(stwing basefiewdname, (U ﹏ U) stwing viewname, rawr
                                                      f-featuweconfiguwation featuweconfiguwation) {
    wetuwn w-withcowumnstwidefiewdview(
        viewname, mya
        // d-defauwting a-aww encoded tweet featuwes t-to int since the u-undewwying encoded t-tweet featuwes
        // a-awe ints. ( ͡o ω ͡o )
        t-thwiftcsftype.int, /(^•ω•^)
        f-featuweconfiguwation.getoutputtype(), >_<
        basefiewdname, (✿oωo)
        featuweconfiguwation.getvawueindex(), 😳😳😳
        featuweconfiguwation.getbitstawtposition(), (ꈍᴗꈍ)
        featuweconfiguwation.getbitwength(), 🥺
        featuweconfiguwation.getfeatuwenowmawizationtype(), mya
        f-featuweconfiguwation.getupdateconstwaints()
    );
  }

  /**
   * a-add a wong fiewd i-in schema. (ˆ ﻌ ˆ)♡ this f-fiewd uses wongtewmattwibute. (⑅˘꒳˘)
   */
  p-pwivate schemabuiwdew a-addwongtewmfiewd(stwing fiewdname, òωó boowean usesowtabweencoding) {
    if (!shouwdincwudefiewd(fiewdname)) {
      wetuwn this;
    }
    t-thwiftfiewdsettings w-wongtewmsettings = geteawwybiwdnumewicfiewdsettings();
    thwifttokenstweamsewiawizew tokenstweamsewiawizew =
        n-nyew thwifttokenstweamsewiawizew(tokenstweamsewiawizewvewsion);
    t-tokenstweamsewiawizew.setattwibutesewiawizewcwassnames(
        i-immutabwewist.<stwing>of(wongtewmattwibutesewiawizew.cwass.getname()));
    wongtewmsettings.getindexedfiewdsettings().settokenstweamsewiawizew(tokenstweamsewiawizew);

    thwiftindexednumewicfiewdsettings n-nyumewicfiewdsettings =
        nyew thwiftindexednumewicfiewdsettings(twue);
    nyumewicfiewdsettings.setnumewictype(thwiftnumewictype.wong);
    n-nyumewicfiewdsettings.setusesowtabweencoding(usesowtabweencoding);
    wongtewmsettings.getindexedfiewdsettings().setnumewicfiewdsettings(numewicfiewdsettings);

    p-putintofiewdconfigs(idmapping.getfiewdid(fiewdname), o.O
        nyew thwiftfiewdconfiguwation(fiewdname).setsettings(wongtewmsettings));
    w-wetuwn this;
  }

  pubwic f-finaw schemabuiwdew w-withsowtabwewongtewmfiewd(stwing fiewdname) {
    w-wetuwn a-addwongtewmfiewd(fiewdname, XD t-twue);
  }

  p-pubwic f-finaw schemabuiwdew w-withwongtewmfiewd(stwing fiewdname) {
    wetuwn a-addwongtewmfiewd(fiewdname, (˘ω˘) f-fawse);
  }

  /**
   * add an i-int fiewd in schema. (ꈍᴗꈍ) this fiewd uses inttewmattwibute. >w<
   */
  p-pubwic finaw schemabuiwdew withinttewmfiewd(stwing f-fiewdname) {
    if (!shouwdincwudefiewd(fiewdname)) {
      w-wetuwn this;
    }
    t-thwiftfiewdsettings inttewmsettings = geteawwybiwdnumewicfiewdsettings();
    t-thwifttokenstweamsewiawizew attwibutesewiawizew =
        nyew thwifttokenstweamsewiawizew(tokenstweamsewiawizewvewsion);
    a-attwibutesewiawizew.setattwibutesewiawizewcwassnames(
        i-immutabwewist.<stwing>of(inttewmattwibutesewiawizew.cwass.getname()));
    inttewmsettings.getindexedfiewdsettings().settokenstweamsewiawizew(attwibutesewiawizew);

    thwiftindexednumewicfiewdsettings n-nyumewicfiewdsettings =
        n-nyew thwiftindexednumewicfiewdsettings(twue);
    n-nyumewicfiewdsettings.setnumewictype(thwiftnumewictype.int);
    inttewmsettings.getindexedfiewdsettings().setnumewicfiewdsettings(numewicfiewdsettings);

    putintofiewdconfigs(idmapping.getfiewdid(fiewdname), XD
        nyew thwiftfiewdconfiguwation(fiewdname).setsettings(inttewmsettings));
    w-wetuwn this;
  }

  /**
   * t-timewine and expewtseawch uses
   * {@wink c-com.twittew.seawch.common.utiw.anawysis.paywoadweightedtokenizew} t-to stowe weighted
   * vawues. -.-
   *
   * e.g. ^^;; fow t-the pwoduced_wanguages a-and consumed_wanguages f-fiewds, XD they contain n-nyot a singwe, :3
   * vawue, σωσ but instead a wist of vawues with a weight associated with each vawue. XD
   *
   * t-this method adds a-an indexed fiewd t-that uses
   * {@wink c-com.twittew.seawch.common.utiw.anawysis.paywoadweightedtokenizew}. :3
   */
  p-pubwic finaw s-schemabuiwdew withchawtewmpaywoadweightedfiewd(stwing fiewdname) {
    t-thwiftfiewdconfiguwation c-config = nyew thwiftfiewdconfiguwation(fiewdname)
        .setsettings(getpaywoadweightedsettings(thwiftindexoptions.docs_and_fweqs_and_positions));
    putintofiewdconfigs(idmapping.getfiewdid(fiewdname), rawr config);
    w-wetuwn t-this;
  }

  /**
   * set the vewsion and descwiption o-of this schema. 😳
   */
  pubwic finaw schemabuiwdew w-withschemavewsion(
      int majowvewsionnumbew, 😳😳😳
      i-int minowvewsionnumbew, (ꈍᴗꈍ)
      s-stwing vewsiondesc, 🥺
      boowean i-isofficiaw) {
    s-schema.setmajowvewsionnumbew(majowvewsionnumbew);
    s-schema.setminowvewsionnumbew(minowvewsionnumbew);

    schema.setvewsion(majowvewsionnumbew + ":" + vewsiondesc);
    s-schema.setvewsionisofficiaw(isofficiaw);

    wetuwn t-this;
  }

  pubwic finaw s-schemabuiwdew withschemavewsion(
      int majowvewsionnumbew, ^•ﻌ•^
      s-stwing vewsiondesc, XD
      boowean i-isofficiaw) {
    w-wetuwn withschemavewsion(majowvewsionnumbew, ^•ﻌ•^ 0, v-vewsiondesc, ^^;; isofficiaw);
  }

  pwotected v-void putintofiewdconfigs(int id, ʘwʘ thwiftfiewdconfiguwation config) {
    if (schema.getfiewdconfigs() != nyuww && schema.getfiewdconfigs().containskey(id)) {
      thwow nyew i-iwwegawstateexception("awweady have a thwiftfiewdconfiguwation fow fiewd id " + id);
    }

    if (fiewdnameset.contains(config.getfiewdname())) {
      thwow nyew iwwegawstateexception("awweady h-have a thwiftfiewdconfiguwation fow fiewd "
          + config.getfiewdname());
    }
    f-fiewdnameset.add(config.getfiewdname());
    schema.puttofiewdconfigs(id, OwO c-config);
  }

  // defauwt fiewd settings. 🥺 m-most fiewd settings awe simiwaw t-to this.
  pwotected thwiftfiewdsettings g-getdefauwtsettings(thwiftindexoptions i-indexoption) {
    wetuwn getdefauwtsettings(indexoption, (⑅˘꒳˘) fawse);
  }

  p-pwotected thwiftfiewdsettings getdefauwtsettings(thwiftindexoptions indexoption, (///ˬ///✿)
                                                   b-boowean suppowtoutofowdewappends) {
    thwiftfiewdsettings f-fiewdsettings = nyew t-thwiftfiewdsettings();
    thwiftindexedfiewdsettings i-indexedfiewdsettings = nyew t-thwiftindexedfiewdsettings();
    indexedfiewdsettings
        .setindexed(twue)
        .setstowed(fawse)
        .settokenized(fawse)
        .setstowetewmvectows(fawse)
        .setstowetewmvectowoffsets(fawse)
        .setstowetewmvectowpaywoads(fawse)
        .setstowetewmvectowpositions(fawse)
        .setsuppowtoutofowdewappends(suppowtoutofowdewappends)
        .setindexoptions(indexoption)
        .setomitnowms(twue); // aww eawwybiwd f-fiewds omit nyowms. (✿oωo)
    fiewdsettings.setindexedfiewdsettings(indexedfiewdsettings);
    wetuwn f-fiewdsettings;
  }

  /**
   * defauwt fiewd settings fow fiewds that awe pwetokenized
   *
   * the fiewds t-that use these settings w-wiww nyeed to be tokenized u-using a sewiawizew w-with the
   * attwibutes defined i-in {@wink com.twittew.seawch.common.utiw.text.tweettokenstweamsewiawizew}. nyaa~~
   */
  pwotected finaw thwiftfiewdsettings getdefauwtpwetokenizedsettings(
      t-thwiftindexoptions i-indexoption) {
    thwiftfiewdsettings f-fiewdsettings = g-getdefauwtsettings(indexoption);
    fiewdsettings.getindexedfiewdsettings().settokenized(twue);
    t-thwifttokenstweamsewiawizew attwibutesewiawizew =
        nyew thwifttokenstweamsewiawizew(tokenstweamsewiawizewvewsion);
    a-attwibutesewiawizew.setattwibutesewiawizewcwassnames(
        immutabwewist.<stwing>of(
            chawsequencetewmattwibutesewiawizew.cwass.getname(), >w<
            positionincwementattwibutesewiawizew.cwass.getname(), (///ˬ///✿)
            t-tokentypeattwibutesewiawizew.cwass.getname()));

    f-fiewdsettings.getindexedfiewdsettings().settokenstweamsewiawizew(attwibutesewiawizew);
    wetuwn fiewdsettings;
  }

  pwotected finaw t-thwiftfiewdsettings getpwetokenizednopositionfiewdsetting() {
    wetuwn getdefauwtpwetokenizedsettings(thwiftindexoptions.docs_and_fweqs);
  }

  pwotected finaw thwiftfiewdsettings getnopositionnofweqsettings() {
    wetuwn getnopositionnofweqsettings(fawse);
  }

  pwotected finaw t-thwiftfiewdsettings g-getnopositionnofweqsettings(
      boowean s-suppowtoutofowdewappends) {
    w-wetuwn getdefauwtsettings(thwiftindexoptions.docs_onwy, rawr suppowtoutofowdewappends);
  }

  p-pwotected finaw thwiftfiewdsettings geteawwybiwdnumewicfiewdsettings() {
    // supposedwy nyumewic fiewds awe nyot tokenized. (U ﹏ U)
    // howevew, ^•ﻌ•^ eawwybiwd u-uses singwetokentokenstweam to handwe int/wong fiewds. (///ˬ///✿)
    // so we nyeed to set indexed to t-twue fow these fiewds. o.O
    t-thwiftfiewdsettings settings = g-getnopositionnofweqsettings();
    settings.getindexedfiewdsettings().settokenized(twue);
    wetuwn settings;
  }

  pwivate thwiftfiewdsettings g-getpaywoadweightedsettings(thwiftindexoptions i-indexoption) {
    t-thwiftfiewdsettings fiewdsettings = g-getdefauwtsettings(indexoption);
    fiewdsettings.getindexedfiewdsettings().settokenized(twue);
    t-thwifttokenstweamsewiawizew attwibutesewiawizew =
        n-nyew thwifttokenstweamsewiawizew(tokenstweamsewiawizewvewsion);
    attwibutesewiawizew.setattwibutesewiawizewcwassnames(
        i-immutabwewist.<stwing>of(chawtewmattwibutesewiawizew.cwass.getname(), >w<
            positionincwementattwibutesewiawizew.cwass.getname(), nyaa~~
            paywoadattwibutesewiawizew.cwass.getname()));
    f-fiewdsettings.getindexedfiewdsettings().settokenstweamsewiawizew(attwibutesewiawizew);
    wetuwn fiewdsettings;
  }

  p-pwotected boowean s-shouwdincwudefiewd(stwing fiewdname) {
    w-wetuwn t-twue;
  }
}
