package com.tw ter.search.common.sc ma.earlyb rd;

 mport java. o. OExcept on;
 mport java.ut l.HashSet;
 mport java.ut l.L st;
 mport java.ut l.Set;
 mport javax.annotat on.Nonnull;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect. mmutableSet;
 mport com.google.common.collect.Sets;

 mport org.apac .commons.lang.Str ngUt ls;
 mport org.apac .lucene.analys s.TokenStream;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.collect ons.Pa r;
 mport com.tw ter.common.text.ut l.TokenStreamSer al zer;
 mport com.tw ter.cuad.ner.pla n.thr ftjava.Na dEnt y;
 mport com.tw ter.cuad.ner.pla n.thr ftjava.Na dEnt yContext;
 mport com.tw ter.cuad.ner.pla n.thr ftjava.Na dEnt y nputS ceType;
 mport com.tw ter.cuad.ner.thr ftjava.WholeEnt yType;
 mport com.tw ter.search.common.constants.SearchCardType;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftExpandedUrl;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftGeoLocat onS ce;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Tw terPhotoUrl;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.sc ma.Thr ftDocu ntBu lder;
 mport com.tw ter.search.common.sc ma.base.F eldNa To dMapp ng;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.common.ut l.analys s.CharTermAttr buteSer al zer;
 mport com.tw ter.search.common.ut l.analys s. ntTermAttr buteSer al zer;
 mport com.tw ter.search.common.ut l.analys s.TermPayloadAttr buteSer al zer;
 mport com.tw ter.search.common.ut l.analys s.Tw terPhotoTokenStream;
 mport com.tw ter.search.common.ut l.spat al.GeoUt l;
 mport com.tw ter.search.common.ut l.text.Token zer lper;
 mport com.tw ter.search.common.ut l.text.T etTokenStreamSer al zer;
 mport com.tw ter.search.common.ut l.text.regex.Regex;
 mport com.tw ter.search.common.ut l.url.L nkV s b l yUt ls;
 mport com.tw ter.search.common.ut l.url.URLUt ls;

 mport geo.google.datamodel.GeoAddressAccuracy;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftDocu nt;

/**
 * Bu lder class for bu ld ng a {@l nk Thr ftDocu nt}.
 */
publ c f nal class Earlyb rdThr ftDocu ntBu lder extends Thr ftDocu ntBu lder {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdThr ftDocu ntBu lder.class);

  pr vate stat c f nal SearchCounter SER AL ZE_FA LURE_COUNT_NONPENGU N_DEPENDENT =
      SearchCounter.export("tokenstream_ser al zat on_fa lure_non_pengu n_dependent");

  pr vate stat c f nal Str ng HASHTAG_SYMBOL = "#";
  pr vate stat c f nal Str ng CASHTAG_SYMBOL = "$";
  pr vate stat c f nal Str ng MENT ON_SYMBOL = "@";

  pr vate stat c f nal SearchCounter BCP47_LANGUAGE_TAG_COUNTER =
      SearchCounter.export("bcp47_language_tag");

  /**
   * Used to c ck  f a card  s v deo card.
   *
   * @see #w hSearchCard
   */
  pr vate stat c f nal Str ng AMPL FY_CARD_NAME = "ampl fy";
  pr vate stat c f nal Str ng PLAYER_CARD_NAME = "player";

  // Extra term  ndexed for nat ve ret ets, to ensure that t  "-rt" query excludes t m.
  publ c stat c f nal Str ng RETWEET_TERM = "rt";
  publ c stat c f nal Str ng QUEST ON_MARK = "?";

  pr vate stat c f nal Set<Na dEnt y nputS ceType> NAMED_ENT TY_URL_SOURCE_TYPES =
       mmutableSet.of(
          Na dEnt y nputS ceType.URL_T TLE, Na dEnt y nputS ceType.URL_DESCR PT ON);

  pr vate f nal TokenStreamSer al zer  ntTermAttr buteSer al zer =
      new TokenStreamSer al zer( mmutableL st.of(
          new  ntTermAttr buteSer al zer()));
  pr vate f nal TokenStreamSer al zer photoUrlSer al zer =
      new TokenStreamSer al zer( mmutableL st
          .<TokenStreamSer al zer.Attr buteSer al zer>of(
              new CharTermAttr buteSer al zer(), new TermPayloadAttr buteSer al zer()));
  pr vate f nal Sc ma sc ma;

  pr vate boolean  sSetLatLonCSF = false;
  pr vate boolean addLatLonCSF = true;
  pr vate boolean addEncodedT etFeatures = true;

  @Nonnull
  pr vate f nal Earlyb rdEncodedFeatures encodedT etFeatures;
  @Nullable
  pr vate f nal Earlyb rdEncodedFeatures extendedEncodedT etFeatures;

  /**
   * Default constructor
   */
  publ c Earlyb rdThr ftDocu ntBu lder(
      @Nonnull Earlyb rdEncodedFeatures encodedT etFeatures,
      @Nullable Earlyb rdEncodedFeatures extendedEncodedT etFeatures,
      F eldNa To dMapp ng  dMapp ng,
      Sc ma sc ma) {
    super( dMapp ng);
    t .sc ma = sc ma;
    t .encodedT etFeatures = Precond  ons.c ckNotNull(encodedT etFeatures);

    t .extendedEncodedT etFeatures = extendedEncodedT etFeatures;
  }

  /**
   * Get  nternal {@l nk Earlyb rdEncodedFeatures}
   */
  publ c Earlyb rdEncodedFeatures getEncodedT etFeatures() {
    return encodedT etFeatures;
  }

  /**
   * Add sk p l st entry for t  g ven f eld.
   * T  adds a term __has_f eldNa   n t   NTERNAL f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder addFacetSk pL st(Str ng f eldNa ) {
    w hStr ngF eld(Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
        Earlyb rdF eldConstant.getFacetSk pF eldNa (f eldNa ));
    return t ;
  }

  /**
   * Add a f lter term  n t   NTERNAL f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder addF lter nternalF eldTerm(Str ng f lterNa ) {
    w hStr ngF eld(Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
        Earlyb rdThr ftDocu ntUt l.formatF lter(f lterNa ));
    return t ;
  }

  /**
   * Add  d f eld and  d csf f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w h D(long  d) {
    w hLongF eld(Earlyb rdF eldConstant. D_F ELD.getF eldNa (),  d);
    w hLongF eld(Earlyb rdF eldConstant. D_CSF_F ELD.getF eldNa (),  d);
    return t ;
  }

  /**
   * Add created at f eld and created at csf f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hCreatedAt( nt createdAt) {
    w h ntF eld(Earlyb rdF eldConstant.CREATED_AT_F ELD.getF eldNa (), createdAt);
    w h ntF eld(Earlyb rdF eldConstant.CREATED_AT_CSF_F ELD.getF eldNa (), createdAt);
    return t ;
  }

  /**
   * Add t et text f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hT etText(
      Str ng text, byte[] textTokenStream) throws  OExcept on {
    w hTokenStreamF eld(Earlyb rdF eldConstants.Earlyb rdF eldConstant.TEXT_F ELD.getF eldNa (),
        text, textTokenStream);
    return t ;
  }

  publ c Earlyb rdThr ftDocu ntBu lder w hT etText(Str ng text) throws  OExcept on {
    w hT etText(text, null);
    return t ;
  }

  /**
   * Add a l st of cashTags. L ke $TWTR.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hStocksF elds(L st<Str ng> cashTags) {
     f ( sNotEmpty(cashTags)) {
      addFacetSk pL st(Earlyb rdF eldConstant.STOCKS_F ELD.getF eldNa ());
      for (Str ng cashTag : cashTags) {
        w hStr ngF eld(
            Earlyb rdF eldConstant.STOCKS_F ELD.getF eldNa (), CASHTAG_SYMBOL + cashTag);
      }
    }
    return t ;
  }

  /**
   * Add a l st of hashtags.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hHashtagsF eld(L st<Str ng> hashtags) {
     f ( sNotEmpty(hashtags)) {
       nt numHashtags = Math.m n(
          hashtags.s ze(),
          sc ma.getFeatureConf gurat onBy d(
              Earlyb rdF eldConstant.NUM_HASHTAGS.getF eld d()).getMaxValue());
      encodedT etFeatures.setFeatureValue(Earlyb rdF eldConstant.NUM_HASHTAGS, numHashtags);
      addFacetSk pL st(Earlyb rdF eldConstant.HASHTAGS_F ELD.getF eldNa ());
      for (Str ng hashtag : hashtags) {
        w hStr ngF eld(
            Earlyb rdF eldConstant.HASHTAGS_F ELD.getF eldNa (), HASHTAG_SYMBOL + hashtag);
      }
    }
    return t ;
  }

  /**
   * Added a l st of  nt ons.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w h nt onsF eld(L st<Str ng>  nt ons) {
     f ( sNotEmpty( nt ons)) {
       nt num nt ons = Math.m n(
           nt ons.s ze(),
          sc ma.getFeatureConf gurat onBy d(
              Earlyb rdF eldConstant.NUM_HASHTAGS.getF eld d()).getMaxValue());
      encodedT etFeatures.setFeatureValue(Earlyb rdF eldConstant.NUM_MENT ONS, num nt ons);
      addFacetSk pL st(Earlyb rdF eldConstant.MENT ONS_F ELD.getF eldNa ());
      for (Str ng  nt on :  nt ons) {
        w hStr ngF eld(
            Earlyb rdF eldConstant.MENT ONS_F ELD.getF eldNa (), MENT ON_SYMBOL +  nt on);
      }
    }
    return t ;
  }

  /**
   * Add a l st of Tw ter Photo URLs (tw mg URLs). T se are d fferent from regular URLs, because
   *   use t  Tw terPhotoTokenStream to  ndex t m, and   also  nclude t  status  D as payload.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hTw mgURLs(
      L st<Tw terPhotoUrl> urls) throws  OExcept on {
     f ( sNotEmpty(urls)) {
      for (Tw terPhotoUrl photoUrl : urls) {
        TokenStream ts = new Tw terPhotoTokenStream(photoUrl.getPhotoStatus d(),
            photoUrl.get d aUrl());
        byte[] ser al zedTs = photoUrlSer al zer.ser al ze(ts);
        w hTokenStreamF eld(Earlyb rdF eldConstant.TW MG_L NKS_F ELD.getF eldNa (),
            Long.toStr ng(photoUrl.getPhotoStatus d()), ser al zedTs);
        addFacetSk pL st(Earlyb rdF eldConstant.TW MG_L NKS_F ELD.getF eldNa ());
      }
      encodedT etFeatures.setFlag(Earlyb rdF eldConstant.HAS_ MAGE_URL_FLAG);
      encodedT etFeatures.setFlag(Earlyb rdF eldConstant.HAS_NAT VE_ MAGE_FLAG);
    }
    return t ;
  }

  /**
   * Add a l st of URLs. T  also add facet sk p l st terms for news /  mages / v deos  f needed.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hURLs(L st<Thr ftExpandedUrl> urls) {
     f ( sNotEmpty(urls)) {
      Set<Str ng> dedupedL nks = Sets.newHashSet();

      for (Thr ftExpandedUrl expandedUrl : urls) {
         f (expandedUrl. sSetOr g nalUrl()) {
          Str ng normal zedOr g nalUrl = URLUt ls.normal zePath(expandedUrl.getOr g nalUrl());
          dedupedL nks.add(normal zedOr g nalUrl);
        }
         f (expandedUrl. sSetExpandedUrl()) {
          dedupedL nks.add(URLUt ls.normal zePath(expandedUrl.getExpandedUrl()));
        }

         f (expandedUrl. sSetCanon calLastHopUrl()) {
          Str ng url = URLUt ls.normal zePath(expandedUrl.getCanon calLastHopUrl());
          dedupedL nks.add(url);

          Str ng facetUrl = URLUt ls.normal zeFacetURL(url);

           f (expandedUrl. sSet d aType()) {
            sw ch (expandedUrl.get d aType()) {
              case NEWS:
                w hStr ngF eld(Earlyb rdF eldConstant.NEWS_L NKS_F ELD.getF eldNa (), url);
                addFacetSk pL st(Earlyb rdF eldConstant.NEWS_L NKS_F ELD.getF eldNa ());
                encodedT etFeatures.setFlag(Earlyb rdF eldConstant.HAS_NEWS_URL_FLAG);
                break;
              case V DEO:
                w hStr ngF eld(Earlyb rdF eldConstant.V DEO_L NKS_F ELD.getF eldNa (), facetUrl);
                addFacetSk pL st(Earlyb rdF eldConstant.V DEO_L NKS_F ELD.getF eldNa ());
                encodedT etFeatures.setFlag(Earlyb rdF eldConstant.HAS_V DEO_URL_FLAG);
                break;
              case  MAGE:
                w hStr ngF eld(Earlyb rdF eldConstant. MAGE_L NKS_F ELD.getF eldNa (), facetUrl);
                addFacetSk pL st(Earlyb rdF eldConstant. MAGE_L NKS_F ELD.getF eldNa ());
                encodedT etFeatures.setFlag(Earlyb rdF eldConstant.HAS_ MAGE_URL_FLAG);
                break;
              case NAT VE_ MAGE:
                // Noth ng done  re. Nat ve  mages are handled separately.
                // T y are  n PhotoUrls  nstead of expandedUrls.
                break;
              case UNKNOWN:
                break;
              default:
                throw new Runt  Except on("Unknown  d a Type: " + expandedUrl.get d aType());
            }
          }

           f (expandedUrl. sSetL nkCategory()) {
            w h ntF eld(Earlyb rdF eldConstant.L NK_CATEGORY_F ELD.getF eldNa (),
                expandedUrl.getL nkCategory().getValue());
          }
        }
      }

       f (!dedupedL nks. sEmpty()) {
        encodedT etFeatures.setFlag(Earlyb rdF eldConstant.HAS_L NK_FLAG);

        addFacetSk pL st(Earlyb rdF eldConstant.L NKS_F ELD.getF eldNa ());

        for (Str ng l nkUrl : dedupedL nks) {
          w hStr ngF eld(Earlyb rdF eldConstant.L NKS_F ELD.getF eldNa (), l nkUrl);
        }
      }

      encodedT etFeatures.setFlagValue(
          Earlyb rdF eldConstant.HAS_V S BLE_L NK_FLAG,
          L nkV s b l yUt ls.hasV s bleL nk(urls));
    }

    return t ;
  }

  /**
   * Add a l st of places. T  place are U64 encoded place  Ds.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hPlacesF eld(L st<Str ng> places) {
     f ( sNotEmpty(places)) {
      for (Str ng place : places) {
        w hStr ngF eld(Earlyb rdF eldConstant.PLACE_F ELD.getF eldNa (), place);
      }
    }
    return t ;
  }

  /**
   * Add t et text s gnature f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hT etS gnature( nt s gnature) {
    encodedT etFeatures.setFeatureValue(Earlyb rdF eldConstant.TWEET_S GNATURE, s gnature);
    return t ;
  }

  /**
   * Add geo hash f eld and  nternal f lter f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hGeoHash(double lat, double lon,  nt accuracy) {
     f (GeoUt l.val dateGeoCoord nates(lat, lon)) {
      w hGeoF eld(
          Earlyb rdF eldConstant.GEO_HASH_F ELD.getF eldNa (),
          lat, lon, accuracy);
      w hLatLonCSF(lat, lon);
    }
    return t ;
  }

  publ c Earlyb rdThr ftDocu ntBu lder w hGeoHash(double lat, double lon) {
    w hGeoHash(lat, lon, GeoAddressAccuracy.UNKNOWN_LOCAT ON.getCode());
    return t ;
  }

  /**
   * Add geo locat on s ce to t   nternal f eld w h Thr ftGeoLocat onS ce object.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hGeoLocat onS ce(
      Thr ftGeoLocat onS ce geoLocat onS ce) {
     f (geoLocat onS ce != null) {
      w hGeoLocat onS ce(Earlyb rdF eldConstants.formatGeoType(geoLocat onS ce));
    }
    return t ;
  }

  /**
   * Add geo locat on s ce to t   nternal f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hGeoLocat onS ce(Str ng geoLocat onS ce) {
    w hStr ngF eld(Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (), geoLocat onS ce);
    return t ;
  }

  /**
   * Add encoded lat and lon to LatLonCSF f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hLatLonCSF(double lat, double lon) {
     sSetLatLonCSF = true;
    long encodedLatLon = GeoUt l.encodeLatLon nto nt64((float) lat, (float) lon);
    w hLongF eld(Earlyb rdF eldConstant.LAT_LON_CSF_F ELD.getF eldNa (), encodedLatLon);
    return t ;
  }

  /**
   * Add from ver f ed account flag to  nternal f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hFromVer f edAccountFlag() {
    encodedT etFeatures.setFlag(Earlyb rdF eldConstant.FROM_VER F ED_ACCOUNT_FLAG);
    addF lter nternalF eldTerm(Earlyb rdF eldConstant.VER F ED_F LTER_TERM);
    return t ;
  }

  /**
   * Add from blue-ver f ed account flag to  nternal f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hFromBlueVer f edAccountFlag() {
    encodedT etFeatures.setFlag(Earlyb rdF eldConstant.FROM_BLUE_VER F ED_ACCOUNT_FLAG);
    addF lter nternalF eldTerm(Earlyb rdF eldConstant.BLUE_VER F ED_F LTER_TERM);
    return t ;
  }

  /**
   * Add offens ve flag to  nternal f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hOffens veFlag() {
    encodedT etFeatures.setFlag(Earlyb rdF eldConstant. S_OFFENS VE_FLAG);
    w hStr ngF eld(
        Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
        Earlyb rdF eldConstant. S_OFFENS VE);
    return t ;
  }

  /**
   * Add user reputat on value to encoded feature.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hUserReputat on(byte score) {
    encodedT etFeatures.setFeatureValue(Earlyb rdF eldConstant.USER_REPUTAT ON, score);
    return t ;
  }

  /**
   * T   thod creates t  f elds related to docu nt language.
   * For most languages, t  r  soLanguageCode and bcp47LanguageTag are t  sa .
   * For so  languages w h var ants, t se two f elds are d fferent.
   * E.g. for s mpl f ed Ch nese, t  r  soLanguageCode  s zh, but t  r bcp47LanguageTag  s zh-cn.
   * <p>
   * T   thod adds f elds for both t   soLanguageCode and bcp47LanguageTag.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hLanguageCodes(
      Str ng  soLanguageCode, Str ng bcp47LanguageTag) {
     f ( soLanguageCode != null) {
      w h SOLanguage( soLanguageCode);
    }
     f (bcp47LanguageTag != null && !bcp47LanguageTag.equals( soLanguageCode)) {
      BCP47_LANGUAGE_TAG_COUNTER. ncre nt();
      w h SOLanguage(bcp47LanguageTag);
    }
    return t ;
  }

  /**
   * Adds a Str ng f eld  nto t   SO_LANGUAGE_F ELD.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w h SOLanguage(Str ng languageStr ng) {
    w hStr ngF eld(
        Earlyb rdF eldConstant. SO_LANGUAGE_F ELD.getF eldNa (), languageStr ng.toLo rCase());
    return t ;
  }

  /**
   * Add from user  D f elds.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hFromUser D(long fromUser d) {
    w hLongF eld(Earlyb rdF eldConstant.FROM_USER_ D_F ELD.getF eldNa (), fromUser d);
    w hLongF eld(Earlyb rdF eldConstant.FROM_USER_ D_CSF.getF eldNa (), fromUser d);
    return t ;
  }

  /**
   * Add from user  nformat on f elds.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hFromUser(
      long fromUser d, Str ng fromUser) {
    w hFromUser(fromUser d, fromUser, null);
    return t ;
  }

  /**
   * Add from user  nformat on f elds.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hFromUser(Str ng fromUser) {
    w hFromUser(fromUser, null);
    return t ;
  }

  /**
   * Add from user  nformat on f elds.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hFromUser(
      Str ng fromUser, Str ng token zedFromUser) {
    w hStr ngF eld(Earlyb rdF eldConstant.FROM_USER_F ELD.getF eldNa (), fromUser);
    w hStr ngF eld(Earlyb rdF eldConstant.TOKEN ZED_FROM_USER_F ELD.getF eldNa (),
         sNotBlank(token zedFromUser) ? token zedFromUser : fromUser);
    return t ;
  }

  /**
   * Add from user  nformat on f elds.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hFromUser(
      long fromUser d, Str ng fromUser, Str ng token zedFromUser) {
    w hFromUser D(fromUser d);
    w hFromUser(fromUser, token zedFromUser);
    return t ;
  }

  /**
   * Add to user f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hToUser(
      Str ng toUser) {
    w hStr ngF eld(Earlyb rdF eldConstant.TO_USER_F ELD.getF eldNa (), toUser);
    return t ;
  }

  /**
   * Add esc rb rd annotat on f elds.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hAnnotat onEnt  es(L st<Str ng> ent  es) {
     f ( sNotEmpty(ent  es)) {
      for (Str ng ent y : ent  es) {
        w hStr ngF eld(Earlyb rdF eldConstant.ENT TY_ D_F ELD.getF eldNa (), ent y);
      }
    }
    return t ;
  }

  /**
   * Add repl es to  nternal f eld and set  s reply flag.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hReplyFlag() {
    encodedT etFeatures.setFlag(Earlyb rdF eldConstant. S_REPLY_FLAG);
    addF lter nternalF eldTerm(Earlyb rdF eldConstant.REPL ES_F LTER_TERM);
    return t ;
  }

  publ c Earlyb rdThr ftDocu ntBu lder w hCa raComposerS ceFlag() {
    encodedT etFeatures.setFlag(Earlyb rdF eldConstant.COMPOSER_SOURCE_ S_CAMERA_FLAG);
    return t ;
  }

    /**
     * Add  n reply to user  d.
     * <p>
     * Not ce {@l nk #w hReplyFlag}  s not automat cally called s nce ret et a t et that  s
     * a reply to so  ot r t et  s not cons dered a reply.
     * T  caller should call {@l nk #w hReplyFlag} separately  f t  t et  s really a reply t et.
     */
  publ c Earlyb rdThr ftDocu ntBu lder w h nReplyToUser D(long  nReplyToUser D) {
    w hLongF eld(Earlyb rdF eldConstant. N_REPLY_TO_USER_ D_F ELD.getF eldNa (),  nReplyToUser D);
    return t ;
  }

  /**
   * Add reference t et author  d.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hReferenceAuthor D(long referenceAuthor D) {
    w hLongF eld(Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_CSF.getF eldNa (), referenceAuthor D);
    return t ;
  }

  /**
   * Add all nat ve ret et related f elds/label
   */
  @V s bleForTest ng
  publ c Earlyb rdThr ftDocu ntBu lder w hNat veRet et(f nal long ret etUser D,
                                                          f nal long sharedStatus D) {
    w hLongF eld(Earlyb rdF eldConstant.SHARED_STATUS_ D_CSF.getF eldNa (), sharedStatus D);

    w hLongF eld(Earlyb rdF eldConstant.RETWEET_SOURCE_TWEET_ D_F ELD.getF eldNa (),
                  sharedStatus D);
    w hLongF eld(Earlyb rdF eldConstant.RETWEET_SOURCE_USER_ D_F ELD.getF eldNa (),
                  ret etUser D);
    w hLongF eld(Earlyb rdF eldConstant.REFERENCE_AUTHOR_ D_CSF.getF eldNa (), ret etUser D);

    encodedT etFeatures.setFlag(Earlyb rdF eldConstant. S_RETWEET_FLAG);

    // Add nat ve ret et label to t   nternal f eld.
    addF lter nternalF eldTerm(Earlyb rdF eldConstant.NAT VE_RETWEETS_F LTER_TERM);
    w hStr ngF eld(Earlyb rdF eldConstant.TEXT_F ELD.getF eldNa (), RETWEET_TERM);
    return t ;
  }

  /**
   * Add quoted t et  d and user  d.
   */
  @V s bleForTest ng
  publ c Earlyb rdThr ftDocu ntBu lder w hQuote(
      f nal long quotedStatus d, f nal long quotedUser d) {
    w hLongF eld(Earlyb rdF eldConstant.QUOTED_TWEET_ D_F ELD.getF eldNa (), quotedStatus d);
    w hLongF eld(Earlyb rdF eldConstant.QUOTED_USER_ D_F ELD.getF eldNa (), quotedUser d);

    w hLongF eld(Earlyb rdF eldConstant.QUOTED_TWEET_ D_CSF.getF eldNa (), quotedStatus d);
    w hLongF eld(Earlyb rdF eldConstant.QUOTED_USER_ D_CSF.getF eldNa (), quotedUser d);

    encodedT etFeatures.setFlag(Earlyb rdF eldConstant.HAS_QUOTE_FLAG);

    // Add quote label to t   nternal f eld.
    addF lter nternalF eldTerm(Earlyb rdF eldConstant.QUOTE_F LTER_TERM);
    return t ;
  }

  /**
   * Add resolved l nks text f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hResolvedL nksText(Str ng l nksText) {
    w hStr ngF eld(Earlyb rdF eldConstant.RESOLVED_L NKS_TEXT_F ELD.getF eldNa (), l nksText);
    return t ;
  }

  /**
   * Add s ce f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hS ce(Str ng s ce) {
    w hStr ngF eld(Earlyb rdF eldConstant.SOURCE_F ELD.getF eldNa (), s ce);
    return t ;
  }

  /**
   * Add normal zed s ce f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hNormal zedS ce(Str ng normal zedS ce) {
    w hStr ngF eld(
        Earlyb rdF eldConstant.NORMAL ZED_SOURCE_F ELD.getF eldNa (), normal zedS ce);
    return t ;
  }

  /**
   * Add pos  ve sm ley to  nternal f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hPos  veSm ley() {
    w hStr ngF eld(
        Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
        Earlyb rdF eldConstant.HAS_POS T VE_SM LEY);
    return t ;
  }

  /**
   * Add negat ve sm ley to  nternal f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hNegat veSm ley() {
    w hStr ngF eld(
        Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa (),
        Earlyb rdF eldConstant.HAS_NEGAT VE_SM LEY);
    return t ;
  }

  /**
   * Add quest on mark label to a text f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hQuest onMark() {
    w hStr ngF eld(Earlyb rdF eldConstant.TEXT_F ELD.getF eldNa (), QUEST ON_MARK);
    return t ;
  }

  /**
   * Add card related f elds.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hSearchCard(
      Str ng na ,
      Str ng doma n,
      Str ng t le, byte[] ser al zedT leStream,
      Str ng descr pt on, byte[] ser al zedDescr pt onStream,
      Str ng lang) {
     f ( sNotBlank(t le)) {
      w hTokenStreamF eld(
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.CARD_T TLE_F ELD.getF eldNa (),
          t le, ser al zedT leStream);
    }

     f ( sNotBlank(descr pt on)) {
      w hTokenStreamF eld(
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.CARD_DESCR PT ON_F ELD.getF eldNa (),
          descr pt on, ser al zedDescr pt onStream);
    }

     f ( sNotBlank(lang)) {
      w hStr ngF eld(Earlyb rdF eldConstant.CARD_LANG.getF eldNa (), lang);
    }

     f ( sNotBlank(doma n)) {
      w hStr ngF eld(
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.CARD_DOMA N_F ELD.getF eldNa (), doma n);
    }

     f ( sNotBlank(na )) {
      w hStr ngF eld(
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.CARD_NAME_F ELD.getF eldNa (), na );
      w h ntF eld(
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.CARD_TYPE_CSF_F ELD.getF eldNa (),
          SearchCardType.cardTypeFromStr ngNa (na ).getByteValue());
    }

     f (AMPL FY_CARD_NAME.equals gnoreCase(na )
        || PLAYER_CARD_NAME.equals gnoreCase(na )) {
      // Add  nto " nternal" f eld so that t  t et  s returned by f lter:v deos.
      addFacetSk pL st(
          Earlyb rdF eldConstants.Earlyb rdF eldConstant.V DEO_L NKS_F ELD.getF eldNa ());
    }

    return t ;
  }

  publ c Earlyb rdThr ftDocu ntBu lder w hNormal zedM nEngage ntF eld(
      Str ng f eldNa ,  nt normal zedNumEngage nts) throws  OExcept on {
    Earlyb rdThr ftDocu ntUt l.addNormal zedM nEngage ntF eld(doc, f eldNa ,
        normal zedNumEngage nts);
    return t ;
  }

  /**
   * Add na d ent y w h g ven canon cal na  and type to docu nt.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hNa dEnt y(Na dEnt y na dEnt y) {
     f (na dEnt y.getContexts() == null) {
      //  n t  unl kely case,   don't have any context for na d ent y type or s ce,
      // so   can't properly  ndex    n any of   f elds.  'll just sk p    n t  case.
      return t ;
    }

    // Keep track of t  f elds  've appl ed  n t  bu lder already, to ensure   only  ndex
    // each term (f eld/value pa r) once
    Set<Pa r<Earlyb rdF eldConstant, Str ng>> f eldsAppl ed = new HashSet<>();
    for (Na dEnt yContext context : na dEnt y.getContexts()) {
       f (context. sSet nput_s ce()
          && NAMED_ENT TY_URL_SOURCE_TYPES.conta ns(context.get nput_s ce().getS ce_type())) {
        //  f t  s ce  s one of t  URL* types, add t  na d ent y to t  "from_url" f elds,
        // ensur ng   add   only once
        addNa dEnt yF elds(
            f eldsAppl ed,
            Earlyb rdF eldConstant.NAMED_ENT TY_FROM_URL_F ELD,
            Earlyb rdF eldConstant.NAMED_ENT TY_W TH_TYPE_FROM_URL_F ELD,
            na dEnt y.getCanon cal_na (),
            context);
      } else {
        addNa dEnt yF elds(
            f eldsAppl ed,
            Earlyb rdF eldConstant.NAMED_ENT TY_FROM_TEXT_F ELD,
            Earlyb rdF eldConstant.NAMED_ENT TY_W TH_TYPE_FROM_TEXT_F ELD,
            na dEnt y.getCanon cal_na (),
            context);
      }
    }

    return t ;
  }

  /**
   * Add space  d f elds.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hSpace dF elds(Set<Str ng> space ds) {
     f (!space ds. sEmpty()) {
      addFacetSk pL st(Earlyb rdF eldConstant.SPACE_ D_F ELD.getF eldNa ());
      for (Str ng space d : space ds) {
        w hStr ngF eld(Earlyb rdF eldConstant.SPACE_ D_F ELD.getF eldNa (), space d);
      }
    }
    return t ;
  }

  /**
   * Add d rected at user.
   */
  @V s bleForTest ng
  publ c Earlyb rdThr ftDocu ntBu lder w hD rectedAtUser(f nal long d rectedAtUser d) {
    w hLongF eld(Earlyb rdF eldConstant.D RECTED_AT_USER_ D_F ELD.getF eldNa (),
        d rectedAtUser d);

    w hLongF eld(Earlyb rdF eldConstant.D RECTED_AT_USER_ D_CSF.getF eldNa (), d rectedAtUser d);

    return t ;
  }

  /**
   * Add a wh e space token zed screen na  f eld.
   *
   * Example:
   *  screenNa  - "super_ ro"
   *  token zed vers on - "super  ro"
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hWh eSpaceToken zedScreenNa F eld(
      Str ng f eldNa ,
      Str ng normal zedScreenNa ) {
    Str ng wh eSpaceToken zableScreenNa  = Str ngUt ls.jo n(
        normal zedScreenNa .spl (Regex.HASHTAG_USERNAME_PUNCTUAT ON_REGEX), " ");
    w hStr ngF eld(f eldNa , wh eSpaceToken zableScreenNa );
    return t ;
  }

  /**
   * Add a ca l case token zed screen na  f eld.
   */
  publ c Earlyb rdThr ftDocu ntBu lder w hCa lCaseToken zedScreenNa F eld(
      Str ng f eldNa ,
      Str ng screenNa ,
      Str ng normal zedScreenNa ,
      TokenStream screenNa TokenStream) {

    // t  normal zed text  s cons stent to how t  token zed stream  s created from
    // Token zer lper.getNormal zedCa lcaseTokenStream -  e. just lo rcas ng.
    Str ng ca lCaseToken zedScreenNa Text =
        Token zer lper.getNormal zedCa lcaseTokenStreamText(screenNa );
    try {
      // Reset t  token stream  n case   has been read before.
      screenNa TokenStream.reset();
      byte[] ca lCaseToken zedScreenNa  =
          T etTokenStreamSer al zer.getT etTokenStreamSer al zer()
              .ser al ze(screenNa TokenStream);

      w hTokenStreamF eld(
          f eldNa ,
          ca lCaseToken zedScreenNa Text. sEmpty()
              ? normal zedScreenNa  : ca lCaseToken zedScreenNa Text,
          ca lCaseToken zedScreenNa );
    } catch ( OExcept on e) {
      LOG.error("Tw terTokenStream ser al zat on error! Could not ser al ze: " + screenNa );
      SER AL ZE_FA LURE_COUNT_NONPENGU N_DEPENDENT. ncre nt();
    }
    return t ;
  }

  pr vate vo d addNa dEnt yF elds(
      Set<Pa r<Earlyb rdF eldConstant, Str ng>> f eldsAppl ed,
      Earlyb rdF eldConstant na OnlyF eld,
      Earlyb rdF eldConstant na W hTypeF eld,
      Str ng na ,
      Na dEnt yContext context) {
    w hOneT  Str ngF eld(f eldsAppl ed, na OnlyF eld, na , false);
     f (context. sSetEnt y_type()) {
      w hOneT  Str ngF eld(f eldsAppl ed, na W hTypeF eld,
          formatNa dEnt yStr ng(na , context.getEnt y_type()), true);
    }
  }

  pr vate vo d w hOneT  Str ngF eld(
      Set<Pa r<Earlyb rdF eldConstant, Str ng>> f eldsAppl ed, Earlyb rdF eldConstant f eld,
      Str ng value, boolean addToFacets) {
    Pa r<Earlyb rdF eldConstant, Str ng> f eldValuePa r = new Pa r<>(f eld, value);
     f (!f eldsAppl ed.conta ns(f eldValuePa r)) {
       f (addToFacets) {
        addFacetSk pL st(f eld.getF eldNa ());
      }
      w hStr ngF eld(f eld.getF eldNa (), value);
      f eldsAppl ed.add(f eldValuePa r);
    }
  }

  pr vate Str ng formatNa dEnt yStr ng(Str ng na , WholeEnt yType type) {
    return Str ng.format("%s:%s", na , type).toLo rCase();
  }

  /**
   * Set w t r set LAT_LON_CSF_F ELD or not before bu ld
   *  f LAT_LON_CSF_F ELD  s not set del berately.
   *
   * @see #prepareToBu ld()
   */
  publ c Earlyb rdThr ftDocu ntBu lder setAddLatLonCSF(boolean  sSet) {
    addLatLonCSF =  sSet;
    return t ;
  }

  /**
   * Set  f add encoded t et feature f eld  n t  end.
   *
   * @see #prepareToBu ld()
   */
  publ c Earlyb rdThr ftDocu ntBu lder setAddEncodedT etFeatures(boolean  sSet) {
    addEncodedT etFeatures =  sSet;
    return t ;
  }

  @Overr de
  protected vo d prepareToBu ld() {
     f (! sSetLatLonCSF && addLatLonCSF) {
      //  n lucene arch ves, t  CSF  s needed regardless of w t r geoLocat on  s set.
      w hLatLonCSF(GeoUt l. LLEGAL_LATLON, GeoUt l. LLEGAL_LATLON);
    }

     f (addEncodedT etFeatures) {
      // Add encoded_t et_features before bu ld ng t  docu nt.
      w hBytesF eld(
          Earlyb rdF eldConstant.ENCODED_TWEET_FEATURES_F ELD.getF eldNa (),
          Earlyb rdEncodedFeaturesUt l.toBytesForThr ftDocu nt(encodedT etFeatures));
    }

     f (extendedEncodedT etFeatures != null) {
      // Add extended_encoded_t et_features before bu ld ng t  docu nt.
      w hBytesF eld(
          Earlyb rdF eldConstant.EXTENDED_ENCODED_TWEET_FEATURES_F ELD.getF eldNa (),
          Earlyb rdEncodedFeaturesUt l.toBytesForThr ftDocu nt(extendedEncodedT etFeatures));
    }
  }

  pr vate stat c boolean  sNotBlank(Str ng value) {
    return value != null && !value. sEmpty();
  }

  pr vate stat c boolean  sNotEmpty(L st<?> value) {
    return value != null && !value. sEmpty();
  }
}
