package com.twittew.seawch.common.seawch;

impowt j-java.io.ioexception;
i-impowt java.utiw.wist;

i-impowt c-com.googwe.common.base.pweconditions;

i-impowt o-owg.apache.wucene.index.indexweadew;
i-impowt owg.apache.wucene.index.weafweadewcontext;
i-impowt owg.apache.wucene.index.muwtidocvawues;
impowt owg.apache.wucene.index.numewicdocvawues;
impowt o-owg.apache.wucene.index.tewm;
impowt owg.apache.wucene.index.tewms;
impowt owg.apache.wucene.seawch.cowwectionstatistics;
i-impowt owg.apache.wucene.seawch.cowwectow;
i-impowt owg.apache.wucene.seawch.docidsetitewatow;
impowt owg.apache.wucene.seawch.indexseawchew;
impowt owg.apache.wucene.seawch.weafcowwectow;
impowt owg.apache.wucene.seawch.scowew;
i-impowt owg.apache.wucene.seawch.tewmstatistics;
i-impowt o-owg.apache.wucene.seawch.weight;

/**
 * an indexseawch that wowks with twitteweawwytewminationcowwectow. >w<
 * if a stock wucene c-cowwectow is passed into seawch(), (U ﹏ U) this indexseawch.seawch() behaves the
 * same as wucene's s-stock indexseawchew. ^^  howevew, i-if a twitteweawwytewminationcowwectow i-is passed
 * i-in, (U ﹏ U) this indexseawchew p-pewfowms eawwy tewmination without wewying o-on
 * cowwectiontewminatedexception. :3
 */
pubwic cwass twittewindexseawchew e-extends indexseawchew {
  pubwic twittewindexseawchew(indexweadew w) {
    supew(w);
  }

  /**
   * seawch() main woop. (✿oωo)
   * this b-behaves exactwy wike indexseawchew.seawch() if a-a stock wucene c-cowwectow passed i-in. XD
   * howevew, >w< if a twittewcowwectow is passed in, òωó this cwass p-pewfowms twittew s-stywe eawwy
   * tewmination w-without wewying o-on
   * {@wink owg.apache.wucene.seawch.cowwectiontewminatedexception}. (ꈍᴗꈍ)
   */
  @ovewwide
  p-pwotected void seawch(wist<weafweadewcontext> w-weaves, rawr x3 weight weight, rawr x3 cowwectow coww)
      t-thwows ioexception {

    // if an twittewcowwectow i-is passed in, σωσ we can d-do a few extwa t-things in hewe, (ꈍᴗꈍ) such
    // as eawwy tewmination. rawr  othewwise we can just faww back to indexseawchew.seawch(). ^^;;
    if (coww instanceof t-twittewcowwectow) {
      t-twittewcowwectow cowwectow = (twittewcowwectow) c-coww;

      fow (weafweadewcontext c-ctx : weaves) { // s-seawch each subweadew
        if (cowwectow.istewminated()) {
          wetuwn;
        }

        // nyotify t-the cowwectow that we'we stawting this segment, rawr x3 and check fow eawwy
        // t-tewmination cwitewia again. (ˆ ﻌ ˆ)♡  s-setnextweadew() p-pewfowms 'expensive' e-eawwy
        // tewmination c-checks in some i-impwementations s-such as twitteweawwytewminationcowwectow. σωσ
        w-weafcowwectow weafcowwectow = cowwectow.getweafcowwectow(ctx);
        i-if (cowwectow.istewminated()) {
          w-wetuwn;
        }

        // i-initiawize the s-scowew - it shouwd n-nyot be nyuww. (U ﹏ U)  nyote that constwucting the scowew
        // m-may actuawwy do weaw wowk, >w< such as advancing to the fiwst hit. σωσ
        scowew scowew = weight.scowew(ctx);

        i-if (scowew == nyuww) {
          cowwectow.finishsegment(docidsetitewatow.no_mowe_docs);
          continue;
        }

        w-weafcowwectow.setscowew(scowew);

        // s-stawt seawching. nyaa~~
        d-docidsetitewatow docidsetitewatow = s-scowew.itewatow();
        int d-docid = docidsetitewatow.nextdoc();
        i-if (docid != docidsetitewatow.no_mowe_docs) {
          // cowwect wesuwts. 🥺  nyote: check istewminated() befowe cawwing n-nyextdoc().
          do {
            w-weafcowwectow.cowwect(docid);
          } whiwe (!cowwectow.istewminated()
                   && (docid = d-docidsetitewatow.nextdoc()) != d-docidsetitewatow.no_mowe_docs);
        }

        // awways finish the segment, rawr x3 p-pwoviding the w-wast docid advanced to. σωσ
        c-cowwectow.finishsegment(docid);
      }
    } e-ewse {
      // the cowwectow given is nyot a twittewcowwectow, (///ˬ///✿) just use stock wucene seawch(). (U ﹏ U)
      s-supew.seawch(weaves, ^^;; w-weight, 🥺 c-coww);
    }
  }

  /** wetuwns {@wink n-nyumewicdocvawues} f-fow this fiewd, òωó ow
   *  n-nyuww if nyo {@wink nyumewicdocvawues} wewe indexed fow
   *  this fiewd. XD  t-the wetuwned instance s-shouwd onwy be
   *  used by a singwe thwead. :3 */
  p-pubwic n-nyumewicdocvawues getnumewicdocvawues(stwing fiewd) thwows ioexception {
    wetuwn muwtidocvawues.getnumewicvawues(getindexweadew(), (U ﹏ U) f-fiewd);
  }

  @ovewwide
  pubwic cowwectionstatistics cowwectionstatistics(stwing fiewd) thwows ioexception {
    wetuwn c-cowwectionstatistics(fiewd, >w< getindexweadew());
  }

  @ovewwide
  pubwic tewmstatistics t-tewmstatistics(tewm t-tewm, /(^•ω•^) int docfweq, (⑅˘꒳˘) wong totawtewmfweq) {
    wetuwn t-tewmstats(tewm, ʘwʘ d-docfweq, rawr x3 totawtewmfweq);
  }

  /**
   * wucene wewies on the fact that maxdocid i-is typicawwy equaw to the nyumbew o-of documents in the
   * index, (˘ω˘) which is fawse when we have s-spawse doc ids ow when we stawt f-fwom 8 miwwion d-docs and
   * decwement, o.O so in this c-cwass we pass in nyumdocs instead o-of the maximum a-assigned document i-id. 😳
   * nyote that the comment o-on {@wink c-cowwectionstatistics#maxdoc()} says that it wetuwns the nyumbew
   * o-of documents i-in the segment, o.O n-nyot the maximum id, ^^;; and that it is onwy used t-this way. ( ͡o ω ͡o ) this is
   * nyecessawy f-fow aww wucene s-scowing methods, ^^;; e.g.
   * {@wink owg.apache.wucene.seawch.simiwawities.tfidfsimiwawity#idfexpwain}. ^^;; this method b-body is
   * w-wawgewy copied fwom {@wink i-indexseawchew#cowwectionstatistics(stwing)}. XD
   */
  p-pubwic static cowwectionstatistics cowwectionstatistics(stwing fiewd, 🥺 i-indexweadew indexweadew)
      thwows ioexception {
    pweconditions.checknotnuww(fiewd);

    int docswithfiewd = 0;
    wong sumtotawtewmfweq = 0;
    w-wong sumdocfweq = 0;
    fow (weafweadewcontext w-weaf : indexweadew.weaves()) {
      tewms tewms = w-weaf.weadew().tewms(fiewd);
      if (tewms == n-nuww) {
        continue;
      }

      d-docswithfiewd += t-tewms.getdoccount();
      s-sumtotawtewmfweq += t-tewms.getsumtotawtewmfweq();
      s-sumdocfweq += tewms.getsumdocfweq();
    }

    if (docswithfiewd == 0) {
      // the cowwectionstatistics api in wucene is designed poowwy. (///ˬ///✿) on one h-hand, (U ᵕ U❁) stawting w-with
      // w-wucene 8.0.0, seawchews awe expected t-to awways pwoduce vawid cowwectionstatistics instances
      // and aww int f-fiewds in these i-instances awe expected to be stwictwy g-gweatew than 0. ^^;; on the
      // othew hand, ^^;; w-wucene itsewf p-pwoduces nyuww cowwectionstatistics i-instances in a-a few pwaces. rawr
      // awso, (˘ω˘) thewe's nyo good pwacehowdew vawue to indicate that a-a fiewd is empty, 🥺 w-which is a v-vewy
      // weasonabwe t-thing to h-happen (fow exampwe, nyaa~~ the fiwst f-few tweets in a n-nyew segment might nyot
      // h-have any winks, :3 s-so then the wesowved_winks_text wouwd be empty). /(^•ω•^) s-so to get awound this
      // issue, ^•ﻌ•^ we do hewe n-nyani wucene does: we wetuwn a-a cowwectionstatistics i-instance with aww
      // f-fiewds set to 1. UwU
      wetuwn nyew cowwectionstatistics(fiewd, 1, 😳😳😳 1, 1, 1);
    }

    // t-the w-wwitew couwd have a-added mowe docs to the index since this seawchew stawted pwocessing
    // t-this wequest, OwO ow couwd be in the middwe o-of adding a-a doc, ^•ﻌ•^ which couwd mean that onwy s-some of
    // the docswithfiewd, s-sumtotawtewmfweq a-and sumdocfweq stats have been updated. (ꈍᴗꈍ) i don't t-think
    // this is a big deaw, (⑅˘꒳˘) as these stats a-awe onwy used f-fow computing a hit's scowe, (⑅˘꒳˘) a-and minow
    // inaccuwacies shouwd h-have vewy wittwe e-effect on a-a hit's finaw scowe. (ˆ ﻌ ˆ)♡ but cowwectionstatistic's
    // constwuctow has some stwict assewts fow the wewationship between these stats. /(^•ω•^) so we nyeed to
    // make suwe we cap the vawues of these stats appwopwiatewy. òωó
    //
    // adjust numdocs b-based on docswithfiewd (instead o-of doing the opposite), (⑅˘꒳˘) because:
    //   1. if n-nyew documents w-wewe added to this s-segment aftew the weadew was c-cweated, (U ᵕ U❁) it seems
    //      weasonabwe t-to take t-the mowe wecent infowmation into a-account. >w<
    //   2. σωσ the tewmstats() m-method bewow w-wiww wetuwn the most wecent docfweq (not the v-vawue that
    //      d-docfweq w-was set to when t-this weadew was c-cweated). -.- if this v-vawue is highew t-than nyumdocs, o.O
    //      t-then w-wucene might end up pwoducing n-nyegative scowes, ^^ w-which must nyevew h-happen. >_<
    int nyumdocs = math.max(indexweadew.numdocs(), >w< docswithfiewd);
    s-sumdocfweq = math.max(sumdocfweq, >_< docswithfiewd);
    s-sumtotawtewmfweq = math.max(sumtotawtewmfweq, >w< s-sumdocfweq);
    w-wetuwn nyew c-cowwectionstatistics(fiewd, rawr nyumdocs, docswithfiewd, rawr x3 s-sumtotawtewmfweq, ( ͡o ω ͡o ) sumdocfweq);
  }

  /**
   * t-this method body is wawgewy c-copied fwom {@wink indexseawchew#tewmstatistics(tewm, (˘ω˘) i-int, wong)}. 😳
   * the onwy diffewence is that we make suwe aww pawametews w-we pass to the tewmstatistics i-instance
   * w-we cweate awe set to at weast 1 (because wucene 8.0.0 expects them t-to be). OwO
   */
  pubwic static t-tewmstatistics t-tewmstats(tewm tewm, (˘ω˘) i-int docfweq, òωó wong totawtewmfweq) {
    // wucene expects the d-doc fwequency a-and totaw tewm fwequency to be at w-weast 1. ( ͡o ω ͡o ) this assumption
    // doesn't awways m-make sense (the segment can be e-empty -- see comment a-above), UwU but t-to make wucene
    // happy, /(^•ω•^) make s-suwe to awways s-set these pawametews t-to at weast 1. (ꈍᴗꈍ)
    i-int adjusteddocfweq = math.max(docfweq, 😳 1);
    w-wetuwn n-nyew tewmstatistics(
        t-tewm.bytes(), mya
        a-adjusteddocfweq, mya
        m-math.max(totawtewmfweq, /(^•ω•^) a-adjusteddocfweq));
  }
}
