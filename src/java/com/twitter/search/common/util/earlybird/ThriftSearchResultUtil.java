package com.twittew.seawch.common.utiw.eawwybiwd;

impowt java.utiw.wist;
i-impowt j-java.utiw.map;
impowt j-javax.annotation.nuwwabwe;

i-impowt com.googwe.common.base.function;
i-impowt c-com.googwe.common.base.pwedicate;
i-impowt com.googwe.common.base.pwedicates;
i-impowt com.googwe.common.cowwect.itewabwes;
impowt com.googwe.common.cowwect.wists;
impowt com.googwe.common.cowwect.maps;

i-impowt com.twittew.seawch.common.constants.thwiftjava.thwiftwanguage;
impowt com.twittew.seawch.common.wewevance.wanking.actionchain;
impowt c-com.twittew.seawch.common.wewevance.wanking.fiwtews.exactdupwicatefiwtew;
impowt com.twittew.seawch.common.wewevance.text.visibwetokenwationowmawizew;
i-impowt com.twittew.seawch.common.wuntime.actionchaindebugmanagew;
impowt com.twittew.seawch.common.schema.base.schema;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftfacetfiewdwesuwts;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftfacetwesuwts;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwt;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtmetadata;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwttype;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwts;
impowt com.twittew.seawch.eawwybiwd.thwift.thwifttweetsouwce;

/**
 * thwiftseawchwesuwtutiw c-contains some simpwe static methods fow constwucting
 * thwiftseawchwesuwt o-objects. >_<
 */
pubwic finaw c-cwass thwiftseawchwesuwtutiw {
  p-pwivate thwiftseawchwesuwtutiw() { }

  p-pwivate s-static finaw visibwetokenwationowmawizew nyowmawizew =
      v-visibwetokenwationowmawizew.cweateinstance();

  pubwic static finaw function<thwiftseawchwesuwts, -.- m-map<thwiftwanguage, mya integew>> wang_map_gettew =
      seawchwesuwts -> seawchwesuwts.getwanguagehistogwam();
  pubwic static f-finaw function<thwiftseawchwesuwts, >w< map<wong, integew>> h-hit_counts_map_gettew =
      s-seawchwesuwts -> s-seawchwesuwts.gethitcounts();

  // some usefuw pwedicates
  pubwic static f-finaw pwedicate<thwiftseawchwesuwt> i-is_offensive_tweet =
      wesuwt -> {
        i-if (wesuwt != n-nyuww && wesuwt.issetmetadata()) {
          thwiftseawchwesuwtmetadata m-metadata = wesuwt.getmetadata();
          w-wetuwn metadata.isisoffensive();
        } ewse {
          wetuwn fawse;
        }
      };

  p-pubwic static finaw pwedicate<thwiftseawchwesuwt> i-is_top_tweet =
      wesuwt -> w-wesuwt != n-nuww
             && wesuwt.issetmetadata()
             && wesuwt.getmetadata().issetwesuwttype()
             && wesuwt.getmetadata().getwesuwttype() == thwiftseawchwesuwttype.popuwaw;

  pubwic static finaw pwedicate<thwiftseawchwesuwt> f-fwom_fuww_awchive =
      w-wesuwt -> wesuwt != nyuww
             && w-wesuwt.issettweetsouwce()
             && wesuwt.gettweetsouwce() == t-thwifttweetsouwce.fuww_awchive_cwustew;

  p-pubwic static finaw pwedicate<thwiftseawchwesuwt> is_fuww_awchive_top_tweet =
      pwedicates.and(fwom_fuww_awchive, (U ﹏ U) i-is_top_tweet);

  pubwic static finaw pwedicate<thwiftseawchwesuwt> is_nsfw_by_any_means_tweet =
          wesuwt -> {
            i-if (wesuwt != nyuww && w-wesuwt.issetmetadata()) {
              t-thwiftseawchwesuwtmetadata m-metadata = wesuwt.getmetadata();
              w-wetuwn metadata.isisusewnsfw()
                      || metadata.isisoffensive()
                      || m-metadata.getextwametadata().isissensitivecontent();
            } e-ewse {
              w-wetuwn fawse;
            }
          };

  /**
   * wetuwns the nyumbew o-of undewwying thwiftseawchwesuwt w-wesuwts. 😳😳😳
   */
  p-pubwic static i-int nyumwesuwts(thwiftseawchwesuwts w-wesuwts) {
    if (wesuwts == nyuww || !wesuwts.issetwesuwts()) {
      wetuwn 0;
    } e-ewse {
      wetuwn wesuwts.getwesuwtssize();
    }
  }

  /**
   * wetuwns the wist of tweet ids in thwiftseawchwesuwts. o.O
   * w-wetuwns nyuww if thewe's nyo wesuwts. òωó
   */
  @nuwwabwe
  pubwic static w-wist<wong> gettweetids(thwiftseawchwesuwts wesuwts) {
    i-if (numwesuwts(wesuwts) > 0) {
      w-wetuwn gettweetids(wesuwts.getwesuwts());
    } ewse {
      w-wetuwn nyuww;
    }
  }

  /**
   * wetuwns the w-wist of tweet ids i-in a wist of thwiftseawchwesuwt. 😳😳😳
   * wetuwns nyuww if thewe's nyo wesuwts. σωσ
   */
  pubwic static wist<wong> gettweetids(@nuwwabwe w-wist<thwiftseawchwesuwt> wesuwts) {
    i-if (wesuwts != nyuww && w-wesuwts.size() > 0) {
      w-wetuwn wists.newawwaywist(itewabwes.twansfowm(
          wesuwts, (⑅˘꒳˘)
          seawchwesuwt -> s-seawchwesuwt.getid()
      ));
    }
    w-wetuwn nyuww;
  }

  /**
   * given thwiftseawchwesuwts, (///ˬ///✿) buiwd a-a map fwom t-tweet id to the tweets metadata. 🥺
   */
  pubwic static map<wong, OwO thwiftseawchwesuwtmetadata> g-gettweetmetadatamap(
      s-schema schema, >w< t-thwiftseawchwesuwts wesuwts) {
    m-map<wong, 🥺 t-thwiftseawchwesuwtmetadata> wesuwtmap = maps.newhashmap();
    i-if (wesuwts == nyuww || wesuwts.getwesuwtssize() == 0) {
      wetuwn wesuwtmap;
    }
    fow (thwiftseawchwesuwt seawchwesuwt : w-wesuwts.getwesuwts()) {
      w-wesuwtmap.put(seawchwesuwt.getid(), nyaa~~ seawchwesuwt.getmetadata());
    }
    wetuwn w-wesuwtmap;
  }

  /**
   * w-wetuwn the totaw nyumbew of facet wesuwts in thwiftfacetwesuwts, ^^ by summing up the n-nyumbew
   * of facet wesuwts in each fiewd. >w<
   */
  pubwic static int nyumfacetwesuwts(thwiftfacetwesuwts w-wesuwts) {
    if (wesuwts == nyuww || !wesuwts.issetfacetfiewds()) {
      w-wetuwn 0;
    } e-ewse {
      int nyumwesuwts = 0;
      fow (thwiftfacetfiewdwesuwts fiewd : wesuwts.getfacetfiewds().vawues()) {
        i-if (fiewd.issettopfacets()) {
          n-nyumwesuwts += fiewd.topfacets.size();
        }
      }
      wetuwn nyumwesuwts;
    }
  }

  /**
   * u-updates the seawch statistics o-on base, OwO by adding the cowwesponding stats fwom dewta. XD
   */
  p-pubwic static void incwementcounts(thwiftseawchwesuwts b-base, ^^;;
                                     t-thwiftseawchwesuwts dewta) {
    i-if (dewta.issetnumhitspwocessed()) {
      base.setnumhitspwocessed(base.getnumhitspwocessed() + d-dewta.getnumhitspwocessed());
    }
    i-if (dewta.issetnumpawtitionseawwytewminated() && dewta.getnumpawtitionseawwytewminated() > 0) {
      // t-this cuwwentwy used fow mewging w-wesuwts on a-a singwe eawwybiwd, 🥺 so we don't sum up aww the
      // c-counts, XD j-just set it to 1 i-if we see one that was eawwy tewminated. (U ᵕ U❁)
      b-base.setnumpawtitionseawwytewminated(1);
    }
    if (dewta.issetmaxseawchedstatusid()) {
      w-wong dewtamax = d-dewta.getmaxseawchedstatusid();
      if (!base.issetmaxseawchedstatusid() || dewtamax > base.getmaxseawchedstatusid()) {
        base.setmaxseawchedstatusid(dewtamax);
      }
    }
    i-if (dewta.issetminseawchedstatusid()) {
      w-wong d-dewtamin = dewta.getminseawchedstatusid();
      i-if (!base.issetminseawchedstatusid() || dewtamin < b-base.getminseawchedstatusid()) {
        base.setminseawchedstatusid(dewtamin);
      }
    }
    if (dewta.issetscowe()) {
      if (base.issetscowe()) {
        base.setscowe(base.getscowe() + dewta.getscowe());
      } e-ewse {
        base.setscowe(dewta.getscowe());
      }
    }
  }

  /**
   * w-wemoves the dupwicates fwom the g-given wist of wesuwts. :3
   *
   * @pawam wesuwts t-the wist of thwiftseawchwesuwts. ( ͡o ω ͡o )
   * @wetuwn the given wist with d-dupwicates wemoved. òωó
   */
  pubwic s-static wist<thwiftseawchwesuwt> w-wemovedupwicates(wist<thwiftseawchwesuwt> w-wesuwts) {
    actionchain<thwiftseawchwesuwt> fiwtewchain =
      a-actionchaindebugmanagew
        .<thwiftseawchwesuwt>cweateactionchainbuiwdew("wemovedupwicatesfiwtews")
        .appendactions(new exactdupwicatefiwtew())
        .buiwd();
    wetuwn fiwtewchain.appwy(wesuwts);
  }

  /**
   * wetuwns wanking scowe fwom eawwybiwd shawd-based wanking m-modews if any, σωσ a-and 0 othewwise. (U ᵕ U❁)
   */
  p-pubwic static doubwe gettweetscowe(@nuwwabwe t-thwiftseawchwesuwt wesuwt) {
    if (wesuwt == nuww || !wesuwt.issetmetadata() || !wesuwt.getmetadata().issetscowe()) {
      w-wetuwn 0.0;
    }
    w-wetuwn wesuwt.getmetadata().getscowe();
  }
}
