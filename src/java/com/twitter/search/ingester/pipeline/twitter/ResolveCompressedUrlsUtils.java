package com.tw ter.search. ngester.p pel ne.tw ter;

 mport javax.annotat on.Nullable;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. erables;

 mport org.apac .commons.lang.Str ngUt ls;

 mport com.tw ter.p nk_floyd.thr ft.FetchStatusCode;
 mport com.tw ter.p nk_floyd.thr ft.HtmlBas cs;
 mport com.tw ter.p nk_floyd.thr ft.Resolut on;
 mport com.tw ter.p nk_floyd.thr ft.UrlData;
 mport com.tw ter.serv ce.sp derduck.gen.L nkCategory;
 mport com.tw ter.serv ce.sp derduck.gen. d aTypes;
 mport com.tw ter.sp derduck.common.URLUt ls;

//  lper class w h Url nfo  lper funct ons
publ c f nal class ResolveCompressedUrlsUt ls {

  pr vate ResolveCompressedUrlsUt ls() { }
  stat c class Url nfo {
    publ c Str ng or g nalUrl;
    @Nullable publ c Str ng resolvedUrl;
    @Nullable publ c Str ng language;
    @Nullable publ c  d aTypes  d aType;
    @Nullable publ c L nkCategory l nkCategory;
    @Nullable publ c Str ng descr pt on;
    @Nullable publ c Str ng t le;
  }

  /**
   * Determ nes  f t  g ven UrlData  nstance  s fully resolved.
   *
   * Based on d scuss ons w h t  URL serv ces team,   dec ded that t  most correct way to
   * determ ne that a URL was fully resolved  s to look at a few response f elds:
   *  - urlD rect nfo: both t   d a type and l nk category must be set.
   *  - htmlBas cs: P nk has successfully parsed t  resolved l nk's  tadata.
   *  - resolut on: P nk was able to successfully get to t  last hop  n t  red rect cha n.
   *                T   s espec ally  mportant, because so  s es have a robots.txt f le, wh ch
   *                prevents P nk from follow ng t  red rect cha n once   gets to that s e.
   *                 n that case,   end up w h a "last hop" URL, but t  FetchStatusCode  s not
   *                set to OK.   need to  gnore t se URLs because   don't know  f t y're really
   *                t  last hop URLs.
   *                Also, P nk has so  restr ct ons on t  page s ze. For example,   does not
   *                parse text pages that are larger than 2MB. So  f t  red rect cha n leads P nk
   *                to one of t se pages,   w ll stop t re. And aga n,   don't know  f t   s
   *                t  last hop URL or not, so   have to  gnore that URL.
   *
   * @param urlData T  UrlData  nstance.
   * @return true  f t  URL data  s fully resolved; false ot rw se.
   */
  publ c stat c boolean  sResolved(UrlData urlData) {
    // Make sure t   d aType and l nkCategory f elds are set.
    boolean  s nfoReady = urlData. sSetUrlD rect nfo()
        && urlData.getUrlD rect nfo(). sSet d aType()
        && urlData.getUrlD rect nfo(). sSetL nkCategory();

    // T   nd v dual HtmlBas cs f elds m ght or m ght not be set, depend ng on each  bs e.
    // Ho ver, all f elds should be set at t  sa  t  ,  f t y are present. Cons der t 
    // resolut on complete  f at least one of t  t le, descr pt on or language f elds  s set.
    boolean  sHtmlReady = urlData. sSetHtmlBas cs()
        && (Str ngUt ls. sNotEmpty(urlData.getHtmlBas cs().getT le())
            || Str ngUt ls. sNotEmpty(urlData.getHtmlBas cs().getDescr pt on())
            || Str ngUt ls. sNotEmpty(urlData.getHtmlBas cs().getLang()));

    Resolut on resolut on = urlData.getResolut on();
    boolean  sResolut onReady = urlData. sSetResolut on()
        && Str ngUt ls. sNotEmpty(resolut on.getLastHopCanon calUrl())
        && resolut on.getStatus() == FetchStatusCode.OK
        && resolut on.getLastHopHttpResponseStatusCode() == 200;

    return  sHtmlReady &&  s nfoReady &&  sResolut onReady;
  }

  /**
   * Creates a Url nfo  nstance from t  g ven URL data.
   *
   * @param urlData urlData from a resolver response.
   * @return t  Url nfo  nstance.
   */
  publ c stat c Url nfo getUrl nfo(UrlData urlData) {
    Precond  ons.c ckArgu nt(urlData. sSetResolut on());

    Url nfo url nfo = new Url nfo();
    url nfo.or g nalUrl = urlData.url;
    Resolut on resolut on = urlData.getResolut on();
     f (resolut on. sSetLastHopCanon calUrl()) {
      url nfo.resolvedUrl = resolut on.lastHopCanon calUrl;
    } else {
      // Just  n case lastHopCanon calUrl  s not ava lable (wh ch shouldn't happen)
       f (resolut on. sSetRed rect onCha n()) {
        url nfo.resolvedUrl =  erables.getLast(resolut on.red rect onCha n);
      } else {
        url nfo.resolvedUrl = urlData.url;
      }
      url nfo.resolvedUrl = URLUt ls.canon cal zeUrl(url nfo.resolvedUrl);
    }
     f (urlData. sSetUrlD rect nfo()) {
      url nfo. d aType = urlData.urlD rect nfo. d aType;
      url nfo.l nkCategory = urlData.urlD rect nfo.l nkCategory;
    }
     f (urlData. sSetHtmlBas cs()) {
      HtmlBas cs htmlBas cs = urlData.getHtmlBas cs();
      url nfo.language = htmlBas cs.getLang();
       f (htmlBas cs. sSetDescr pt on()) {
        url nfo.descr pt on = htmlBas cs.getDescr pt on();
      }
       f (htmlBas cs. sSetT le()) {
        url nfo.t le = htmlBas cs.getT le();
      }
    }
    return url nfo;
  }
}

