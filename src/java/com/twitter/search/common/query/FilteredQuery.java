package com.twittew.seawch.common.quewy;

impowt j-java.io.ioexception;
i-impowt java.utiw.set;

i-impowt c-com.googwe.common.base.pweconditions;

i-impowt o-owg.apache.wucene.index.indexweadew;
i-impowt owg.apache.wucene.index.weafweadewcontext;
i-impowt owg.apache.wucene.index.tewm;
impowt owg.apache.wucene.seawch.docidsetitewatow;
impowt owg.apache.wucene.seawch.expwanation;
impowt o-owg.apache.wucene.seawch.indexseawchew;
impowt owg.apache.wucene.seawch.quewy;
i-impowt owg.apache.wucene.seawch.scowew;
impowt o-owg.apache.wucene.seawch.scowemode;
impowt owg.apache.wucene.seawch.weight;

/**
 * a paiwing of a quewy and a f-fiwtew. the hits twavewsaw is dwiven b-by the quewy's d-docidsetitewatow, XD
 * and the fiwtew is used onwy to do post-fiwtewing. mya in othew w-wowds, ^•ﻌ•^ the fiwtew is nyevew used to
 * find the nyext doc id: it's onwy used t-to fiwtew out the doc ids wetuwned b-by the quewy's
 * d-docidsetitewatow. ʘwʘ t-this is u-usefuw when we nyeed to have a conjunction between a-a quewy that can
 * quickwy itewate thwough doc i-ids (eg. ( ͡o ω ͡o ) a posting wist), mya and an expensive fiwtew (eg. o.O a fiwtew based
 * on the vawues stowed i-in a csf). (✿oωo)
 *
 * fow exampwe, :3 wet s-say we want to b-buiwd a quewy t-that wetuwns aww docs that have at weast 100 faves. 😳
 *   1. one o-option is to go w-with the [min_faves 100] quewy. (U ﹏ U) t-this wouwd be vewy e-expensive though, mya
 *      because t-this quewy wouwd have to wawk t-thwough evewy doc in the segment and fow each o-one of
 *      them it wouwd have t-to extwact the numbew of faves f-fwom the fowwawd i-index. (U ᵕ U❁)
 *   2. anothew option is to go with a conjunction between this quewy and the has_engagement fiwtew:
 *      (+[min_faves 100] +[cached_fiwtew h-has_engagements]). :3 t-the has_engagement fiwtew c-couwd
 *      t-twavewse the d-doc id space fastew (if it's backed by a posting wist). mya but this a-appwoach wouwd
 *      stiww be swow, OwO because as soon as the has_engagement fiwtew f-finds a doc id, (ˆ ﻌ ˆ)♡ the conjunction
 *      s-scowew w-wouwd twiggew a-an advance(docid) caww on the m-min_faves pawt of t-the quewy, ʘwʘ which h-has
 *      the s-same pwobwem as the fiwst option. o.O
 *   3. UwU finawwy, a-a bettew option f-fow this pawticuwaw c-case wouwd b-be to dwive b-by the has_engagement
 *      fiwtew (because it can quickwy jump ovew aww docs that do nyot have a-any engagement), rawr x3 and use
 *      the min_faves fiwtew as a post-pwocessing step, 🥺 on a much smowew s-set of docs. :3
 */
pubwic cwass fiwtewedquewy extends quewy {
  /**
   * a-a doc i-id pwedicate that d-detewmines if the given doc i-id shouwd be accepted. (ꈍᴗꈍ)
   */
  @functionawintewface
  pubwic static i-intewface docidfiwtew {
    /**
     * d-detewmines if the given doc id shouwd be accepted. 🥺
     */
    boowean accept(int docid) t-thwows ioexception;
  }

  /**
   * a factowy f-fow cweating docidfiwtew instances b-based on a g-given weafweadewcontext instance. (✿oωo)
   */
  @functionawintewface
  pubwic static intewface d-docidfiwtewfactowy {
    /**
     * w-wetuwns a docidfiwtew i-instance fow t-the given weafweadewcontext instance. (U ﹏ U)
     */
    docidfiwtew getdocidfiwtew(weafweadewcontext context) thwows ioexception;
  }

  pwivate static c-cwass fiwtewedquewydocidsetitewatow e-extends docidsetitewatow {
    p-pwivate finaw docidsetitewatow q-quewyscowewitewatow;
    p-pwivate finaw docidfiwtew d-docidfiwtew;

    pubwic fiwtewedquewydocidsetitewatow(
        docidsetitewatow quewyscowewitewatow, :3 d-docidfiwtew d-docidfiwtew) {
      this.quewyscowewitewatow = pweconditions.checknotnuww(quewyscowewitewatow);
      t-this.docidfiwtew = p-pweconditions.checknotnuww(docidfiwtew);
    }

    @ovewwide
    pubwic int docid() {
      wetuwn quewyscowewitewatow.docid();
    }

    @ovewwide
    p-pubwic int nyextdoc() thwows ioexception {
      int docid;
      do {
        d-docid = quewyscowewitewatow.nextdoc();
      } whiwe (docid != n-nyo_mowe_docs && !docidfiwtew.accept(docid));
      wetuwn d-docid;
    }

    @ovewwide
    pubwic int advance(int tawget) thwows ioexception {
      i-int docid = quewyscowewitewatow.advance(tawget);
      i-if (docid == nyo_mowe_docs || docidfiwtew.accept(docid)) {
        wetuwn d-docid;
      }
      wetuwn nyextdoc();
    }

    @ovewwide
    p-pubwic wong cost() {
      wetuwn quewyscowewitewatow.cost();
    }
  }

  pwivate s-static cwass fiwtewedquewyscowew e-extends scowew {
    p-pwivate finaw scowew q-quewyscowew;
    pwivate finaw docidfiwtew d-docidfiwtew;

    p-pubwic f-fiwtewedquewyscowew(weight weight, ^^;; scowew quewyscowew, rawr d-docidfiwtew d-docidfiwtew) {
      supew(weight);
      this.quewyscowew = p-pweconditions.checknotnuww(quewyscowew);
      t-this.docidfiwtew = p-pweconditions.checknotnuww(docidfiwtew);
    }

    @ovewwide
    pubwic int docid() {
      w-wetuwn quewyscowew.docid();
    }

    @ovewwide
    pubwic fwoat s-scowe() thwows i-ioexception {
      wetuwn quewyscowew.scowe();
    }

    @ovewwide
    pubwic docidsetitewatow i-itewatow() {
      w-wetuwn nyew f-fiwtewedquewydocidsetitewatow(quewyscowew.itewatow(), d-docidfiwtew);
    }

    @ovewwide
    pubwic fwoat getmaxscowe(int u-upto) thwows ioexception {
      wetuwn quewyscowew.getmaxscowe(upto);
    }
  }

  pwivate static cwass fiwtewedquewyweight extends w-weight {
    pwivate finaw weight q-quewyweight;
    pwivate finaw d-docidfiwtewfactowy docidfiwtewfactowy;

    p-pubwic fiwtewedquewyweight(
        fiwtewedquewy q-quewy, 😳😳😳 weight q-quewyweight, (✿oωo) docidfiwtewfactowy d-docidfiwtewfactowy) {
      s-supew(quewy);
      t-this.quewyweight = pweconditions.checknotnuww(quewyweight);
      this.docidfiwtewfactowy = pweconditions.checknotnuww(docidfiwtewfactowy);
    }

    @ovewwide
    pubwic void extwacttewms(set<tewm> tewms) {
      q-quewyweight.extwacttewms(tewms);
    }

    @ovewwide
    p-pubwic expwanation e-expwain(weafweadewcontext context, OwO i-int doc) thwows ioexception {
      wetuwn quewyweight.expwain(context, ʘwʘ doc);
    }

    @ovewwide
    p-pubwic s-scowew scowew(weafweadewcontext context) thwows i-ioexception {
      scowew quewyscowew = quewyweight.scowew(context);
      i-if (quewyscowew == n-nyuww) {
        wetuwn nyuww;
      }

      w-wetuwn nyew fiwtewedquewyscowew(this, (ˆ ﻌ ˆ)♡ q-quewyscowew, (U ﹏ U) docidfiwtewfactowy.getdocidfiwtew(context));
    }

    @ovewwide
    pubwic boowean iscacheabwe(weafweadewcontext ctx) {
      w-wetuwn quewyweight.iscacheabwe(ctx);
    }
  }

  p-pwivate finaw q-quewy quewy;
  p-pwivate finaw d-docidfiwtewfactowy docidfiwtewfactowy;

  p-pubwic f-fiwtewedquewy(quewy quewy, UwU docidfiwtewfactowy d-docidfiwtewfactowy) {
    t-this.quewy = pweconditions.checknotnuww(quewy);
    this.docidfiwtewfactowy = p-pweconditions.checknotnuww(docidfiwtewfactowy);
  }

  pubwic quewy getquewy() {
    wetuwn q-quewy;
  }

  @ovewwide
  pubwic quewy wewwite(indexweadew w-weadew) thwows ioexception {
    q-quewy wewwittenquewy = quewy.wewwite(weadew);
    i-if (wewwittenquewy != quewy) {
      wetuwn nyew f-fiwtewedquewy(wewwittenquewy, XD d-docidfiwtewfactowy);
    }
    w-wetuwn this;
  }

  @ovewwide
  pubwic int hashcode() {
    wetuwn quewy.hashcode() * 13 + d-docidfiwtewfactowy.hashcode();
  }

  @ovewwide
  pubwic boowean equaws(object o-obj) {
    i-if (!(obj instanceof fiwtewedquewy)) {
      w-wetuwn fawse;
    }

    fiwtewedquewy f-fiwtewedquewy = f-fiwtewedquewy.cwass.cast(obj);
    wetuwn quewy.equaws(fiwtewedquewy.quewy)
        && d-docidfiwtewfactowy.equaws(fiwtewedquewy.docidfiwtewfactowy);
  }

  @ovewwide
  pubwic stwing tostwing(stwing fiewd) {
    stwingbuiwdew s-sb = nyew s-stwingbuiwdew();
    sb.append("fiwtewedquewy(")
        .append(quewy)
        .append(" -> ")
        .append(docidfiwtewfactowy)
        .append(")");
    w-wetuwn sb.tostwing();
  }

  @ovewwide
  pubwic w-weight cweateweight(indexseawchew s-seawchew, ʘwʘ scowemode s-scowemode, rawr x3 fwoat boost)
      thwows ioexception {
    weight quewyweight = pweconditions.checknotnuww(quewy.cweateweight(seawchew, ^^;; scowemode, ʘwʘ boost));
    wetuwn nyew fiwtewedquewyweight(this, (U ﹏ U) quewyweight, (˘ω˘) docidfiwtewfactowy);
  }
}
