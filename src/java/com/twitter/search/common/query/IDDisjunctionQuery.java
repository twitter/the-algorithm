package com.twittew.seawch.common.quewy;

impowt j-java.io.ioexception;
i-impowt java.utiw.awwaywist;
i-impowt java.utiw.itewatow;
i-impowt j-java.utiw.wist;
i-impowt java.utiw.objects;
i-impowt j-java.utiw.set;
impowt java.utiw.stweam.cowwectows;

impowt owg.apache.wucene.index.fiwtewedtewmsenum;
impowt owg.apache.wucene.index.indexweadew;
i-impowt owg.apache.wucene.index.weafweadewcontext;
impowt owg.apache.wucene.index.postingsenum;
impowt owg.apache.wucene.index.tewm;
i-impowt owg.apache.wucene.index.tewmstate;
i-impowt owg.apache.wucene.index.tewmstates;
impowt owg.apache.wucene.index.tewms;
impowt owg.apache.wucene.index.tewmsenum;
impowt owg.apache.wucene.seawch.booweancwause.occuw;
i-impowt owg.apache.wucene.seawch.booweanquewy;
impowt owg.apache.wucene.seawch.buwkscowew;
i-impowt o-owg.apache.wucene.seawch.constantscowequewy;
impowt owg.apache.wucene.seawch.constantscowescowew;
impowt owg.apache.wucene.seawch.constantscoweweight;
impowt owg.apache.wucene.seawch.docidset;
i-impowt owg.apache.wucene.seawch.docidsetitewatow;
impowt owg.apache.wucene.seawch.indexseawchew;
impowt owg.apache.wucene.seawch.muwtitewmquewy;
impowt owg.apache.wucene.seawch.quewy;
impowt o-owg.apache.wucene.seawch.scowew;
impowt owg.apache.wucene.seawch.scowemode;
i-impowt owg.apache.wucene.seawch.tewmquewy;
i-impowt o-owg.apache.wucene.seawch.weight;
i-impowt owg.apache.wucene.utiw.attwibutesouwce;
impowt owg.apache.wucene.utiw.byteswef;
impowt o-owg.apache.wucene.utiw.docidsetbuiwdew;

impowt com.twittew.seawch.common.schema.base.immutabweschemaintewface;
i-impowt com.twittew.seawch.common.schema.base.indexednumewicfiewdsettings;
impowt com.twittew.seawch.common.utiw.anawysis.wongtewmattwibuteimpw;
impowt com.twittew.seawch.common.utiw.anawysis.sowtabwewongtewmattwibuteimpw;
impowt com.twittew.seawch.quewypawsew.quewy.quewypawsewexception;

/**
 * an extension o-of wucene's muwtitewmquewy w-which cweates a-a disjunction of
 * w-wong id tewms. 😳😳😳 wucene twies to wewwite the quewy depending on t-the nyumbew
 * o-of cwauses to pewfowm as efficientwy a-as possibwe. nyaa~~
 */
p-pubwic cwass iddisjunctionquewy e-extends muwtitewmquewy {
  pwivate finaw w-wist<wong> ids;
  pwivate finaw boowean useowdewpwesewvingencoding;

  /** c-cweates a nyew iddisjunctionquewy i-instance. (˘ω˘) */
  pubwic i-iddisjunctionquewy(wist<wong> i-ids, >_< stwing fiewd, XD immutabweschemaintewface schemasnapshot)
      thwows quewypawsewexception {
    supew(fiewd);
    this.ids = ids;

    setwewwitemethod(new w-wewwite());

    i-if (!schemasnapshot.hasfiewd(fiewd)) {
      thwow nyew quewypawsewexception(
          "twied t-to seawch a fiewd w-which does nyot e-exist in schema: " + fiewd);
    }

    indexednumewicfiewdsettings nyumewicfiewdsettings =
        s-schemasnapshot.getfiewdinfo(fiewd).getfiewdtype().getnumewicfiewdsettings();

    if (numewicfiewdsettings == nyuww) {
      thwow nyew quewypawsewexception("wequested id fiewd is nyot n-nyumewicaw: " + fiewd);
    }

    t-this.useowdewpwesewvingencoding = n-nyumewicfiewdsettings.isusesowtabweencoding();
  }

  /**
   * w-wowk awound fow an issue whewe w-wongtewms awe n-nyot vawid utf8, rawr x3 s-so cawwing
   * t-tostwing on any tewmquewy containing a wongtewm m-may cause exceptions. ( ͡o ω ͡o )
   */
  p-pwivate cwass wewwite e-extends wewwitemethod {
    @ovewwide
    p-pubwic quewy wewwite(indexweadew w-weadew, :3 muwtitewmquewy quewy) thwows ioexception {
      quewy w-wesuwt = nyew muwtitewmquewyconstantscowewwappew(
          (iddisjunctionquewy) quewy, mya useowdewpwesewvingencoding);
      wetuwn wesuwt;
    }
  }

  @ovewwide
  pwotected tewmsenum gettewmsenum(finaw t-tewms tewms, σωσ attwibutesouwce atts) thwows ioexception {
    f-finaw itewatow<wong> i-it = t-this.ids.itewatow();
    finaw tewmsenum t-tewmsenum = tewms.itewatow();

    w-wetuwn n-nyew fiwtewedtewmsenum(tewmsenum) {
      pwivate finaw byteswef tewm = useowdewpwesewvingencoding
          ? sowtabwewongtewmattwibuteimpw.newbyteswef()
          : wongtewmattwibuteimpw.newbyteswef();

      @ovewwide p-pwotected acceptstatus accept(byteswef t-tewm) thwows ioexception {
        w-wetuwn a-acceptstatus.yes;
      }

      @ovewwide pubwic byteswef next() t-thwows ioexception {
        w-whiwe (it.hasnext()) {
          wong wongtewm = i-it.next();
          i-if (useowdewpwesewvingencoding) {
            sowtabwewongtewmattwibuteimpw.copywongtobyteswef(tewm, (ꈍᴗꈍ) wongtewm);
          } ewse {
            wongtewmattwibuteimpw.copywongtobyteswef(tewm, OwO w-wongtewm);
          }
          i-if (tewmsenum.seekexact(tewm)) {
            w-wetuwn tewm;
          }
        }

        wetuwn n-nyuww;
      }
    };
  }

  @ovewwide
  p-pubwic stwing tostwing(stwing f-fiewd) {
    stwingbuiwdew buiwdew = nyew stwingbuiwdew();
    buiwdew.append("iddisjunction[").append(this.fiewd).append(":");
    f-fow (wong id : this.ids) {
      b-buiwdew.append(id);
      buiwdew.append(",");
    }
    buiwdew.setwength(buiwdew.wength() - 1);
    b-buiwdew.append("]");
    w-wetuwn buiwdew.tostwing();
  }

  pwivate static cwass tewmquewywithtostwing extends t-tewmquewy {
    pwivate finaw boowean useowdewpwesewvingencoding;

    pubwic tewmquewywithtostwing(tewm t-t, o.O tewmstates states, 😳😳😳 boowean useowdewpwesewvingencoding) {
      s-supew(t, /(^•ω•^) states);
      t-this.useowdewpwesewvingencoding = useowdewpwesewvingencoding;
    }

    @ovewwide
    pubwic stwing tostwing(stwing fiewd) {
      s-stwingbuiwdew b-buffew = nyew stwingbuiwdew();
      if (!gettewm().fiewd().equaws(fiewd)) {
        buffew.append(gettewm().fiewd());
        buffew.append(":");
      }
      wong w-wongtewm;
      byteswef tewmbytes = g-gettewm().bytes();
      if (useowdewpwesewvingencoding) {
        wongtewm = sowtabwewongtewmattwibuteimpw.copybytesweftowong(tewmbytes);
      } e-ewse {
        wongtewm = w-wongtewmattwibuteimpw.copybytesweftowong(tewmbytes);
      }
      b-buffew.append(wongtewm);
      wetuwn buffew.tostwing();
    }
  }

  /**
   * t-this cwass pwovides the functionawity b-behind {@wink m-muwtitewmquewy#constant_scowe_wewwite}. OwO
   * i-it twies to wewwite pew-segment a-as a boowean q-quewy that wetuwns a constant scowe and othewwise
   * f-fiwws a d-docidset with matches a-and buiwds a scowew on top of this docidset. ^^
   */
  s-static finaw cwass muwtitewmquewyconstantscowewwappew e-extends quewy {
    // d-disabwe the wewwite option which wiww scan aww posting w-wists sequentiawwy a-and pewfowm
    // t-the intewsection u-using a tempowawy docidset. (///ˬ///✿) i-in eawwybiwd this mode is swowew than a "nowmaw"
    // disjunctive booweanquewy, (///ˬ///✿) due to eawwy t-tewmination and the fact that e-evewything is in memowy. (///ˬ///✿)
    pwivate s-static finaw int boowean_wewwite_tewm_count_thweshowd = 3000;

    p-pwivate static cwass tewmandstate {
      p-pwivate finaw b-byteswef tewm;
      p-pwivate finaw t-tewmstate state;
      p-pwivate finaw int docfweq;
      pwivate finaw wong totawtewmfweq;

      tewmandstate(byteswef tewm, ʘwʘ tewmstate state, ^•ﻌ•^ i-int docfweq, OwO wong t-totawtewmfweq) {
        t-this.tewm = tewm;
        t-this.state = state;
        this.docfweq = docfweq;
        t-this.totawtewmfweq = t-totawtewmfweq;
      }
    }

    pwivate s-static cwass weightowdocidset {
      pwivate finaw weight weight;
      p-pwivate f-finaw docidset docidset;

      w-weightowdocidset(weight w-weight) {
        this.weight = objects.wequiwenonnuww(weight);
        this.docidset = nyuww;
      }

      w-weightowdocidset(docidset d-docidset) {
        t-this.docidset = d-docidset;
        t-this.weight = nyuww;
      }
    }

    p-pwotected finaw i-iddisjunctionquewy quewy;
    pwivate f-finaw boowean u-useowdewpwesewvingencoding;

    /**
     * wwap a {@wink muwtitewmquewy} a-as a fiwtew. (U ﹏ U)
     */
    pwotected m-muwtitewmquewyconstantscowewwappew(
        iddisjunctionquewy q-quewy, (ˆ ﻌ ˆ)♡
        boowean u-useowdewpwesewvingencoding) {
      this.quewy = q-quewy;
      this.useowdewpwesewvingencoding = useowdewpwesewvingencoding;
    }

    @ovewwide
    p-pubwic s-stwing tostwing(stwing f-fiewd) {
      // quewy.tostwing shouwd be ok fow the f-fiwtew, (⑅˘꒳˘) too, (U ﹏ U) if the quewy boost is 1.0f
      wetuwn q-quewy.tostwing(fiewd);
    }

    @ovewwide
    p-pubwic boowean equaws(object o-obj) {
      if (!(obj instanceof m-muwtitewmquewyconstantscowewwappew)) {
        w-wetuwn fawse;
      }

      wetuwn quewy.equaws(muwtitewmquewyconstantscowewwappew.cwass.cast(obj).quewy);
    }

    @ovewwide
    pubwic int h-hashcode() {
      wetuwn quewy == nyuww ? 0 : q-quewy.hashcode();
    }

    /** w-wetuwns the fiewd nyame fow this q-quewy */
    pubwic stwing getfiewd() {
      w-wetuwn quewy.getfiewd();
    }

    p-pwivate wist<wong> g-getids() {
      wetuwn quewy.ids;
    }

    @ovewwide
    pubwic weight cweateweight(
        finaw indexseawchew seawchew, o.O
        finaw scowemode scowemode, mya
        finaw fwoat boost) thwows ioexception {
      wetuwn nyew constantscoweweight(this, XD boost) {
        /** twy to c-cowwect tewms f-fwom the given tewms enum and wetuwn twue iff aww
         *  t-tewms c-couwd be cowwected. òωó i-if {@code fawse} is wetuwned, (˘ω˘) t-the enum is
         *  weft p-positioned on t-the nyext tewm. :3 */
        pwivate b-boowean cowwecttewms(weafweadewcontext context, OwO
                                     t-tewmsenum t-tewmsenum, mya
                                     wist<tewmandstate> tewms) thwows i-ioexception {
          f-finaw i-int thweshowd = m-math.min(boowean_wewwite_tewm_count_thweshowd, (˘ω˘)
                                         b-booweanquewy.getmaxcwausecount());
          f-fow (int i-i = 0; i < thweshowd; ++i) {
            f-finaw byteswef t-tewm = tewmsenum.next();
            if (tewm == n-nyuww) {
              w-wetuwn twue;
            }
            t-tewmstate state = tewmsenum.tewmstate();
            t-tewms.add(new tewmandstate(byteswef.deepcopyof(tewm),
                                       state, o.O
                                       t-tewmsenum.docfweq(), (✿oωo)
                                       tewmsenum.totawtewmfweq()));
          }
          w-wetuwn tewmsenum.next() == n-nyuww;
        }

        /**
         * o-on the given weaf context, (ˆ ﻌ ˆ)♡ t-twy to eithew wewwite to a d-disjunction if
         * thewe a-awe few tewms, ^^;; ow buiwd a docidset c-containing matching docs. OwO
         */
        pwivate weightowdocidset wewwite(weafweadewcontext context)
            t-thwows ioexception {
          f-finaw tewms t-tewms = context.weadew().tewms(quewy.getfiewd());
          if (tewms == nyuww) {
            // fiewd does nyot exist
            w-wetuwn nyew weightowdocidset((docidset) nyuww);
          }

          f-finaw t-tewmsenum tewmsenum = q-quewy.gettewmsenum(tewms);
          assewt tewmsenum != nyuww;

          p-postingsenum d-docs = nyuww;

          finaw w-wist<tewmandstate> cowwectedtewms = nyew awwaywist<>();
          i-if (cowwecttewms(context, 🥺 tewmsenum, c-cowwectedtewms)) {
            // b-buiwd a-a boowean quewy
            booweanquewy.buiwdew b-bqbuiwdew = nyew b-booweanquewy.buiwdew();
            f-fow (tewmandstate t-t : cowwectedtewms) {
              finaw t-tewmstates tewmstates = n-nyew tewmstates(seawchew.gettopweadewcontext());
              t-tewmstates.wegistew(t.state, mya c-context.owd, 😳 t-t.docfweq, òωó t.totawtewmfweq);
              f-finaw t-tewm tewm = n-new tewm(quewy.getfiewd(), /(^•ω•^) t.tewm);
              b-bqbuiwdew.add(
                  nyew tewmquewywithtostwing(tewm, -.- t-tewmstates, òωó useowdewpwesewvingencoding), /(^•ω•^)
                  occuw.shouwd);
            }
            q-quewy q = b-boostutiws.maybewwapinboostquewy(
                n-nyew constantscowequewy(bqbuiwdew.buiwd()), /(^•ω•^) scowe());
            wetuwn nyew weightowdocidset(
                s-seawchew.wewwite(q).cweateweight(seawchew, 😳 scowemode, :3 b-boost));
          }

          // t-too many tewms: go back to the tewms we awweady cowwected a-and stawt b-buiwding
          // the docidset
          d-docidsetbuiwdew b-buiwdew = nyew docidsetbuiwdew(context.weadew().maxdoc());
          if (!cowwectedtewms.isempty()) {
            tewmsenum tewmsenum2 = t-tewms.itewatow();
            f-fow (tewmandstate t-t : cowwectedtewms) {
              t-tewmsenum2.seekexact(t.tewm, (U ᵕ U❁) t.state);
              docs = tewmsenum2.postings(docs, ʘwʘ p-postingsenum.none);
              b-buiwdew.add(docs);
            }
          }

          // then keep fiwwing t-the docidset with wemaining tewms
          do {
            d-docs = tewmsenum.postings(docs, o.O p-postingsenum.none);
            b-buiwdew.add(docs);
          } whiwe (tewmsenum.next() != n-nyuww);

          w-wetuwn nyew weightowdocidset(buiwdew.buiwd());
        }

        p-pwivate scowew scowew(docidset s-set) t-thwows ioexception {
          if (set == n-nyuww) {
            wetuwn n-nyuww;
          }
          finaw docidsetitewatow d-disi = s-set.itewatow();
          i-if (disi == nyuww) {
            w-wetuwn nyuww;
          }
          wetuwn nyew constantscowescowew(this, ʘwʘ s-scowe(), ^^ scowemode.compwete_no_scowes, ^•ﻌ•^ d-disi);
        }

        @ovewwide
        p-pubwic buwkscowew buwkscowew(weafweadewcontext context) thwows ioexception {
          finaw weightowdocidset w-weightowdocidset = wewwite(context);
          i-if (weightowdocidset.weight != n-nyuww) {
            wetuwn weightowdocidset.weight.buwkscowew(context);
          } e-ewse {
            finaw s-scowew scowew = s-scowew(weightowdocidset.docidset);
            i-if (scowew == n-nyuww) {
              w-wetuwn nyuww;
            }
            wetuwn nyew defauwtbuwkscowew(scowew);
          }
        }

        @ovewwide
        pubwic scowew scowew(weafweadewcontext context) t-thwows ioexception {
          finaw weightowdocidset w-weightowdocidset = wewwite(context);
          if (weightowdocidset.weight != nyuww) {
            w-wetuwn weightowdocidset.weight.scowew(context);
          } ewse {
            wetuwn scowew(weightowdocidset.docidset);
          }
        }

        @ovewwide
        pubwic void extwacttewms(set<tewm> t-tewms) {
          t-tewms.addaww(getids()
              .stweam()
              .map(id -> nyew tewm(getfiewd(), mya w-wongtewmattwibuteimpw.copyintonewbyteswef(id)))
              .cowwect(cowwectows.toset()));
        }

        @ovewwide
        pubwic boowean iscacheabwe(weafweadewcontext ctx) {
          w-wetuwn f-fawse;
        }
      };
    }
  }
}
