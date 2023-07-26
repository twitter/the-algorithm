package com.twittew.seawch.common.schema;

impowt j-java.io.ioexception;
i-impowt java.io.stwingweadew;
i-impowt java.utiw.cowwections;
i-impowt java.utiw.wist;
i-impowt java.utiw.set;

impowt c-com.googwe.common.annotations.visibwefowtesting;
i-impowt com.googwe.common.base.pweconditions;
i-impowt com.googwe.common.cowwect.immutabwewist;
impowt com.googwe.common.cowwect.sets;

impowt owg.apache.wucene.anawysis.anawyzew;
impowt owg.apache.wucene.anawysis.tokenstweam;
i-impowt owg.apache.wucene.anawysis.tokenattwibutes.chawtewmattwibute;
impowt owg.apache.wucene.anawysis.tokenattwibutes.tewmtobyteswefattwibute;
i-impowt owg.apache.wucene.document.document;
impowt owg.apache.wucene.document.fiewd;
i-impowt owg.apache.wucene.facet.sowtedset.sowtedsetdocvawuesfacetfiewd;
impowt owg.apache.wucene.utiw.byteswef;
impowt o-owg.swf4j.woggew;
impowt owg.swf4j.woggewfactowy;

i-impowt com.twittew.common.text.token.twittewtokenstweam;
i-impowt com.twittew.seawch.common.schema.base.eawwybiwdfiewdtype;
impowt com.twittew.seawch.common.schema.base.indexednumewicfiewdsettings;
impowt com.twittew.seawch.common.schema.base.schema;
i-impowt com.twittew.seawch.common.schema.thwiftjava.thwiftdocument;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftfiewd;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftfiewddata;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftgeocoowdinate;
i-impowt com.twittew.seawch.common.utiw.anawysis.inttewmattwibute;
i-impowt c-com.twittew.seawch.common.utiw.anawysis.wongtewmattwibute;
i-impowt com.twittew.seawch.common.utiw.anawysis.sowtabwewongtewmattwibute;
i-impowt com.twittew.seawch.common.utiw.spatiaw.geoutiw;
impowt com.twittew.seawch.common.utiw.text.highfwequencytewmpaiws;
i-impowt com.twittew.seawch.common.utiw.text.omitnowmtextfiewd;
impowt com.twittew.seawch.common.utiw.text.singwetokenstweam;

/**
 * a document f-factowy that convewts {@wink thwiftdocument} into wucene {@wink document}s
 * using the pwovided {@wink c-com.twittew.seawch.common.schema.base.schema}. (ꈍᴗꈍ)
 */
pubwic c-cwass schemadocumentfactowy {
  p-pwivate static f-finaw woggew wog = woggewfactowy.getwoggew(schemadocumentfactowy.cwass);

  pwivate finaw schema schema;
  pwivate f-finaw immutabwewist<tokenstweamwewwitew> t-tokenstweamwewwitews;

  /**
   * cweates a schemadocumentfactowy w-with a schema a-and the tokenstweamwewwitews. òωó
   *
   * @pawam tokenstweamwewwitews a wist of token s-stweam wewwitews, ʘwʘ which wiww b-be appwied in owdew. ʘwʘ
   */
  pubwic schemadocumentfactowy(
      s-schema schema, nyaa~~
      wist<tokenstweamwewwitew> t-tokenstweamwewwitews) {
    this.schema = s-schema;
    t-this.tokenstweamwewwitews = immutabwewist.copyof(tokenstweamwewwitews);
  }

  /**
   * cweates a schemadocumentfactowy with nyo tokenstweamwewwitews. UwU
   */
  pubwic schemadocumentfactowy(schema schema) {
    this(schema, (⑅˘꒳˘) c-cowwections.empty_wist);
  }

  p-pubwic finaw document nyewdocument(thwiftdocument d-document) t-thwows ioexception {
    w-wetuwn innewnewdocument(document);
  }

  /**
   * cweate a wucene document f-fwom the thwiftdocument. (˘ω˘)
   */
  @visibwefowtesting
  pubwic document innewnewdocument(thwiftdocument document) thwows ioexception {
    document w-wucenedocument = nyew document();
    s-set<stwing> h-hftewms = s-sets.newhashset();
    set<stwing> h-hfphwases = s-sets.newhashset();

    a-anawyzew d-defauwtanawyzew = schema.getdefauwtanawyzew(document.getdefauwtanawyzewovewwide());

    fow (thwiftfiewd f-fiewd : d-document.getfiewds()) {
      b-boowean successfuw = f-fawse;
      t-twy {
        addwucenefiewds(fiewd, :3 defauwtanawyzew, (˘ω˘) wucenedocument, nyaa~~ h-hftewms, (U ﹏ U) hfphwases);
        successfuw = twue;
      } finawwy {
        if (!successfuw) {
          w-wog.wawn("unexpected exception whiwe twying to add fiewd. nyaa~~ fiewd i-id: "
              + f-fiewd.getfiewdconfigid() + " f-fiewd nyame: "
              + schema.getfiewdname(fiewd.getfiewdconfigid()));
        }
      }
    }

    f-fow (stwing token : hftewms) {
      f-fow (stwing t-token2 : hftewms) {
        if (token.compaweto(token2) < 0) {
          wucenedocument.add(new fiewd(immutabweschema.hf_tewm_paiws_fiewd, ^^;;
                                          highfwequencytewmpaiws.cweatepaiw(token, OwO token2), nyaa~~
                                          omitnowmtextfiewd.type_not_stowed));
        }
      }
    }

    f-fow (stwing phwase : hfphwases) {
      // t-tokens in the phwase set awe nyot t-tewms and have a-awweady been pwocessed with
      // highfwequencytewmpaiws.cweatephwasepaiw. UwU
      w-wucenedocument.add(new f-fiewd(immutabweschema.hf_phwase_paiws_fiewd, 😳 phwase,
                                      o-omitnowmtextfiewd.type_not_stowed));
    }

    w-wetuwn schema.getfacetsconfig().buiwd(wucenedocument);
  }

  pwivate void addwucenefiewds(thwiftfiewd fiewd, 😳 anawyzew anawyzew, (ˆ ﻌ ˆ)♡ document d-doc, (✿oωo)
                               s-set<stwing> h-hftewms, nyaa~~ set<stwing> hfphwases) t-thwows ioexception {
    s-schema.fiewdinfo fiewdinfo =
        s-schema.getfiewdinfo(fiewd.getfiewdconfigid(), ^^ fiewd.getfiewdconfigovewwide());

    if (fiewdinfo == nyuww) {
      // fiewd nyot d-defined in schema - s-skip it
      wetuwn;
    }

    thwiftfiewddata f-fiewddata = f-fiewd.getfiewddata();
    if (fiewdinfo.getfiewdtype().getcsftype() !=  nyuww) {
      addcsffiewd(doc, (///ˬ///✿) f-fiewdinfo, 😳 fiewddata);
      wetuwn;
    }

    // checking which data t-type is set is nyot sufficient hewe. òωó we awso nyeed t-to check schema t-to
    // see nyani the type the fiewd is configuwed to be. ^^;; s-see seawch-5173 f-fow mowe detaiws. rawr
    // the pwobwem is that pig, (ˆ ﻌ ˆ)♡ whiwe convewting t-tupwes to thwift, XD sets aww pwimitive t-type
    // fiewds to 0. >_< (i.e. the isset cawws wiww wetuwn t-twue). (˘ω˘)
    indexednumewicfiewdsettings nyumewicsettings =
        f-fiewdinfo.getfiewdtype().getnumewicfiewdsettings();
    i-if (fiewddata.issettokenstweamvawue()) {
      addtokenfiewd(doc, 😳 h-hftewms, o.O hfphwases, fiewdinfo, (ꈍᴗꈍ) fiewddata);
    } e-ewse if (fiewddata.issetstwingvawue()) {
      a-addstwingfiewd(anawyzew, rawr x3 d-doc, hftewms, ^^ hfphwases, OwO f-fiewdinfo, fiewddata);
    } ewse i-if (fiewddata.issetbytesvawue()) {
      addbytesfiewd(doc, ^^ fiewdinfo, fiewddata);
    } e-ewse i-if (fiewddata.issetgeocoowdinate()) {
      a-addgeofiewd(doc, :3 fiewdinfo, o.O fiewddata);
    } ewse i-if (numewicsettings != nyuww) {
      // h-handwe n-nyumewic fiewds. -.-
      switch (numewicsettings.getnumewictype()) {
        case int:
          p-pweconditions.checkstate(fiewddata.issetintvawue(), (U ﹏ U)
              "int f-fiewd does n-nyot have int v-vawue set. o.O fiewd nyame: %s", OwO fiewdinfo.getname());
          a-addintfiewd(doc, ^•ﻌ•^ fiewdinfo, ʘwʘ fiewddata);
          bweak;
        case wong:
          pweconditions.checkstate(fiewddata.issetwongvawue(), :3
              "wong f-fiewd does nyot have w-wong vawue set. 😳 fiewd nyame: %s", òωó f-fiewdinfo.getname());
          addwongfiewd(doc, 🥺 f-fiewdinfo, rawr x3 fiewddata);
          b-bweak;
        c-case fwoat:
          p-pweconditions.checkstate(fiewddata.issetfwoatvawue(), ^•ﻌ•^
              "fwoat f-fiewd does n-not have fwoat vawue set. :3 fiewd name: %s ", (ˆ ﻌ ˆ)♡ fiewdinfo.getname());
          addfwoatfiewd();
          bweak;
        case doubwe:
          pweconditions.checkstate(fiewddata.issetdoubwevawue(), (U ᵕ U❁)
              "doubwe f-fiewd d-does nyot have d-doubwe vawue set. :3 fiewd nyame: %s", ^^;; f-fiewdinfo.getname());
          adddoubwefiewd();
          bweak;
        defauwt:
          thwow nyew unsuppowtedopewationexception("eawwybiwd d-does nyot k-know how to handwe fiewd "
              + f-fiewd.getfiewdconfigid() + " " + fiewd);
      }
    } ewse {
      thwow n-new unsuppowtedopewationexception("eawwybiwd d-does nyot know how to handwe fiewd "
          + f-fiewd.getfiewdconfigid() + " " + f-fiewd);
    }
  }

  pwivate void addcsffiewd(document doc, ( ͡o ω ͡o ) schema.fiewdinfo f-fiewdinfo, thwiftfiewddata f-fiewddata) {
    i-if (fiewdinfo.getfiewdtype().getcsffixedwengthnumvawuespewdoc() > 1) {

      // a-as a-an optimization, o.O tbinawypwotocow s-stowes a byte a-awway fiewd as a pawt of a wawgew b-byte
      // a-awway fiewd. ^•ﻌ•^  must caww fiewddata.getbytesvawue(). XD  f-fiewddata.bytesvawue.awway() wiww
      // wetuwn extwaneous d-data. ^^ see: seawch-3996
      doc.add(new f-fiewd(fiewdinfo.getname(), o.O f-fiewddata.getbytesvawue(), ( ͡o ω ͡o ) fiewdinfo.getfiewdtype()));
    } e-ewse {
      doc.add(new csffiewd(fiewdinfo.getname(), /(^•ω•^) fiewdinfo.getfiewdtype(), 🥺 f-fiewddata));
    }
  }

  p-pwivate v-void addtokenfiewd(
      document doc, nyaa~~
      set<stwing> hftewms, mya
      set<stwing> h-hfphwases, XD
      schema.fiewdinfo fiewdinfo, nyaa~~
      t-thwiftfiewddata f-fiewddata) thwows ioexception {
    t-twittewtokenstweam twittewtokenstweam
        = f-fiewdinfo.getfiewdtype().gettokenstweamsewiawizew().desewiawize(
        f-fiewddata.gettokenstweamvawue(), ʘwʘ fiewddata.getstwingvawue());

    twy {
      f-fow (tokenstweamwewwitew wewwitew : tokenstweamwewwitews) {
        twittewtokenstweam = w-wewwitew.wewwite(fiewdinfo, (⑅˘꒳˘) t-twittewtokenstweam);
      }

      expandstweam(doc, :3 f-fiewdinfo, -.- twittewtokenstweam, 😳😳😳 hftewms, (U ﹏ U) hfphwases);
      d-doc.add(new f-fiewd(fiewdinfo.getname(), o.O t-twittewtokenstweam, ( ͡o ω ͡o ) fiewdinfo.getfiewdtype()));
    } finawwy {
      twittewtokenstweam.cwose();
    }
  }

  pwivate void addstwingfiewd(anawyzew anawyzew, òωó document doc, set<stwing> hftewms, 🥺
                              set<stwing> hfphwases, /(^•ω•^) schema.fiewdinfo fiewdinfo, 😳😳😳
                              thwiftfiewddata f-fiewddata) {
    d-doc.add(new fiewd(fiewdinfo.getname(), ^•ﻌ•^ fiewddata.getstwingvawue(), nyaa~~ f-fiewdinfo.getfiewdtype()));
    i-if (fiewdinfo.getfiewdtype().tokenized()) {
      t-twy {
        tokenstweam t-tokenstweam = anawyzew.tokenstweam(fiewdinfo.getname(), OwO
                n-nyew s-stwingweadew(fiewddata.getstwingvawue()));
        twy {
          e-expandstweam(
              doc, ^•ﻌ•^
              f-fiewdinfo, σωσ
              t-tokenstweam, -.-
              hftewms, (˘ω˘)
              hfphwases);
        } finawwy {
          t-tokenstweam.cwose();
        }
      } c-catch (ioexception e-e) {
        w-wog.ewwow("ioexception e-expanding t-token stweam", e-e);
      }
    } e-ewse {
      addfacetfiewd(doc, rawr x3 f-fiewdinfo, rawr x3 fiewddata.getstwingvawue());
    }
  }

  pwivate void a-addbytesfiewd(document d-doc, σωσ s-schema.fiewdinfo fiewdinfo, nyaa~~ thwiftfiewddata f-fiewddata) {
    doc.add(new fiewd(fiewdinfo.getname(), (ꈍᴗꈍ) f-fiewddata.getbytesvawue(), ^•ﻌ•^ fiewdinfo.getfiewdtype()));
  }

  pwivate void addintfiewd(document d-doc, >_< schema.fiewdinfo f-fiewdinfo, ^^;;
                           t-thwiftfiewddata fiewddata) {
    i-int vawue = fiewddata.getintvawue();
    addfacetfiewd(doc, ^^;; f-fiewdinfo, /(^•ω•^) stwing.vawueof(vawue));

    i-if (fiewdinfo.getfiewdtype().getnumewicfiewdsettings() == nyuww) {
      // nyo nyumewicfiewdsettings. nyaa~~ e-even though the data is nyumewic, (✿oωo) this fiewd is nyot
      // weawwy a-a nyumewicaw fiewd. ( ͡o ω ͡o ) just add as a-a stwing. (U ᵕ U❁)
      d-doc.add(new fiewd(fiewdinfo.getname(), òωó stwing.vawueof(vawue), σωσ fiewdinfo.getfiewdtype()));
    } ewse if (fiewdinfo.getfiewdtype().getnumewicfiewdsettings().isusetwittewfowmat()) {
      addinttewmattwibutefiewd(vawue, :3 f-fiewdinfo, OwO doc);
    } e-ewse {
      // u-use wucene stywe n-nyumewicaw fiewds
      doc.add(numewicfiewd.newintfiewd(fiewdinfo.getname(), ^^ vawue));
    }
  }

  p-pwivate void a-addinttewmattwibutefiewd(int vawue, (˘ω˘)
                                        s-schema.fiewdinfo fiewdinfo, OwO
                                        document doc) {
    s-singwetokenstweam singwetoken = n-nyew singwetokenstweam();
    i-inttewmattwibute t-tewmatt = singwetoken.addattwibute(inttewmattwibute.cwass);
    t-tewmatt.settewm(vawue);
    d-doc.add(new fiewd(fiewdinfo.getname(), UwU s-singwetoken, ^•ﻌ•^ f-fiewdinfo.getfiewdtype()));
  }

  pwivate v-void addwongfiewd(document d-doc, (ꈍᴗꈍ) s-schema.fiewdinfo f-fiewdinfo, /(^•ω•^)
                            t-thwiftfiewddata f-fiewddata) {
    w-wong v-vawue = fiewddata.getwongvawue();
    addfacetfiewd(doc, (U ᵕ U❁) f-fiewdinfo, (✿oωo) stwing.vawueof(vawue));

    i-if (fiewdinfo.getfiewdtype().getnumewicfiewdsettings() == nyuww) {
      // n-nyo n-numewicfiewdsettings. OwO e-even though the data is nyumewic, :3 this fiewd is nyot
      // w-weawwy a nyumewicaw f-fiewd. j-just add as a stwing. nyaa~~
      doc.add(new fiewd(fiewdinfo.getname(), ^•ﻌ•^ stwing.vawueof(vawue), ( ͡o ω ͡o ) f-fiewdinfo.getfiewdtype()));
    } e-ewse if (fiewdinfo.getfiewdtype().getnumewicfiewdsettings().isusetwittewfowmat()) {
      // t-twittew s-stywe nyumewicaw fiewd: use wongtewmattwibute
      addwongtewmattwibutefiewd(vawue, fiewdinfo, ^^;; d-doc);
    } ewse {
      // u-use w-wucene stywe nyumewicaw f-fiewds
      doc.add(numewicfiewd.newwongfiewd(fiewdinfo.getname(), mya vawue));
    }
  }

  p-pwivate void a-addwongtewmattwibutefiewd(wong vawue, (U ᵕ U❁)
                                         schema.fiewdinfo fiewdinfo, ^•ﻌ•^
                                         document doc) {
    s-singwetokenstweam singwetoken = nyew singwetokenstweam();
    b-boowean usesowtabweencoding =
        fiewdinfo.getfiewdtype().getnumewicfiewdsettings().isusesowtabweencoding();

    i-if (usesowtabweencoding) {
      s-sowtabwewongtewmattwibute tewmatt = s-singwetoken.addattwibute(sowtabwewongtewmattwibute.cwass);
      t-tewmatt.settewm(vawue);
    } ewse {
      wongtewmattwibute t-tewmatt = singwetoken.addattwibute(wongtewmattwibute.cwass);
      tewmatt.settewm(vawue);
    }
    d-doc.add(new f-fiewd(fiewdinfo.getname(), (U ﹏ U) s-singwetoken, /(^•ω•^) f-fiewdinfo.getfiewdtype()));
  }

  pwivate v-void addfwoatfiewd() {
    thwow n-nyew unsuppowtedopewationexception("eawwybiwd d-does not suppowt fwoat vawues y-yet.");
  }

  pwivate void adddoubwefiewd() {
    thwow nyew unsuppowtedopewationexception("eawwybiwd d-does nyot s-suppowt doubwe v-vawues yet.");
  }

  pwivate void addgeofiewd(document doc, ʘwʘ schema.fiewdinfo fiewdinfo, XD thwiftfiewddata f-fiewddata) {
    thwiftgeocoowdinate coowd = f-fiewddata.getgeocoowdinate();
    i-if (geoutiw.vawidategeocoowdinates(coowd.getwat(), (⑅˘꒳˘) coowd.getwon())) {
      geoutiw.fiwwgeofiewds(doc, f-fiewdinfo.getname(), nyaa~~
          coowd.getwat(), UwU coowd.getwon(), (˘ω˘) coowd.getaccuwacy());
    }
  }

  pwivate void addfacetfiewd(document d-doc, rawr x3 schema.fiewdinfo f-fiewdinfo, (///ˬ///✿) s-stwing vawue) {
    p-pweconditions.checkawgument(doc != n-nyuww);
    pweconditions.checkawgument(fiewdinfo != nyuww);
    pweconditions.checkawgument(vawue != nyuww);

    if (fiewdinfo.getfiewdtype().getfacetname() != n-nyuww) {
      doc.add(new sowtedsetdocvawuesfacetfiewd(fiewdinfo.getfiewdtype().getfacetname(), 😳😳😳 v-vawue));
    }
  }

  pwivate stwing gettewm(tewmtobyteswefattwibute attw) {
    i-if (attw instanceof chawtewmattwibute) {
      wetuwn ((chawtewmattwibute) attw).tostwing();
    } ewse if (attw i-instanceof inttewmattwibute) {
      w-wetuwn stwing.vawueof(((inttewmattwibute) attw).gettewm());
    } e-ewse if (attw instanceof wongtewmattwibute) {
      w-wetuwn s-stwing.vawueof(((wongtewmattwibute) attw).gettewm());
    } e-ewse {
      wetuwn attw.getbyteswef().utf8tostwing();
    }
  }

  /**
   * e-expand the twittewtokenstweam and popuwate high-fwequency t-tewms, (///ˬ///✿) phwases and/ow facet categowy paths. ^^;;
   */
  p-pwivate v-void expandstweam(
      d-document doc, ^^
      schema.fiewdinfo f-fiewdinfo, (///ˬ///✿)
      tokenstweam stweam, -.-
      set<stwing> hftewms, /(^•ω•^)
      set<stwing> h-hfphwases) thwows i-ioexception {
    // c-checkstywe d-does nyot awwow assignment to pawametews. UwU
    s-set<stwing> f-facethftewms = hftewms;
    set<stwing> facethfphwases = h-hfphwases;

    if (!(highfwequencytewmpaiws.index_hf_tewm_paiws
        && fiewdinfo.getfiewdtype().isindexhftewmpaiws())) {
      // h-high-fwequency tewms and phwases awe nyot nyeeded
      i-if (fiewdinfo.getfiewdtype().getfacetname() == n-nyuww) {
        // facets a-awe nyot nyeeded e-eithew, (⑅˘꒳˘) simpwy w-wetuwn, ʘwʘ wouwd do nyothing othewwise
        wetuwn;
      }
      f-facethftewms = nyuww;
      facethfphwases = n-nyuww;
    }

    finaw tewmtobyteswefattwibute attw = stweam.getattwibute(tewmtobyteswefattwibute.cwass);
    stweam.weset();

    s-stwing wasthftewm = n-nyuww;
    w-whiwe (stweam.incwementtoken()) {
      s-stwing t-tewm = gettewm(attw);
      if (fiewdinfo.getfiewdtype().getfacetname() != nyuww) {
        addfacetfiewd(doc, σωσ f-fiewdinfo, ^^ tewm);
      }
      if (highfwequencytewmpaiws.hf_tewm_set.contains(tewm)) {
        if (facethftewms != n-nyuww) {
          facethftewms.add(tewm);
        }
        i-if (wasthftewm != nyuww) {
          if (facethfphwases != nyuww) {
            f-facethfphwases.add(highfwequencytewmpaiws.cweatephwasepaiw(wasthftewm, OwO t-tewm));
          }
        }
        wasthftewm = tewm;
      } e-ewse {
        wasthftewm = n-nyuww;
      }
    }
  }

  p-pubwic static finaw cwass csffiewd e-extends fiewd {
    /**
     * c-cweate a csffiewd with the g-given fiewdtype, (ˆ ﻌ ˆ)♡ containing the given fiewd data. o.O
     */
    pubwic csffiewd(stwing n-nyame, (˘ω˘) eawwybiwdfiewdtype fiewdtype, 😳 thwiftfiewddata d-data) {
      supew(name, (U ᵕ U❁) fiewdtype);

      i-if (fiewdtype.iscsfvawiabwewength()) {
        f-fiewdsdata = n-nyew byteswef(data.getbytesvawue());
      } ewse {
        s-switch (fiewdtype.getcsftype()) {
          c-case byte:
            f-fiewdsdata = wong.vawueof(data.getbytevawue());
            bweak;
          c-case int:
            fiewdsdata = w-wong.vawueof(data.getintvawue());
            b-bweak;
          case wong:
            fiewdsdata = wong.vawueof(data.getwongvawue());
            bweak;
          c-case fwoat:
            f-fiewdsdata = wong.vawueof(fwoat.fwoattowawintbits((fwoat) data.getfwoatvawue()));
            bweak;
          c-case doubwe:
            f-fiewdsdata = w-wong.vawueof(doubwe.doubwetowawwongbits(data.getdoubwevawue()));
            bweak;
          defauwt:
            thwow nyew iwwegawawgumentexception("unknown c-csf type: " + fiewdtype.getcsftype());
        }
      }
    }
  }

  pubwic i-intewface tokenstweamwewwitew {
    /**
     * wewwite the token s-stweam. :3
     */
    t-twittewtokenstweam wewwite(schema.fiewdinfo f-fiewdinfo, o.O twittewtokenstweam stweam);
  }
}
