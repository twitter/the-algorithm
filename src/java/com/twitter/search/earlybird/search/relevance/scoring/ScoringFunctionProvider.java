package com.tw ter.search.earlyb rd.search.relevance.scor ng;

 mport java. o. OExcept on;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.query.H Attr bute lper;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftRank ngParams;
 mport com.tw ter.search.common.rank ng.thr ftjava.Thr ftScor ngFunct onType;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.ut l.ml.tensorflow_eng ne.TensorflowModelsManager;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.except on.Cl entExcept on;
 mport com.tw ter.search.earlyb rd.ml.Scor ngModelsManager;
 mport com.tw ter.search.earlyb rd.search.Ant Gam ngF lter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultType;
 mport com.tw ter.search.queryparser.query.Query;

/**
 * Returns a scor ng funct on for a part cular exper  nt  D.
 *
 * Can be used for a/b test ng of d fferent scor ng formulas.
 */
publ c abstract class Scor ngFunct onProv der {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Scor ngFunct onProv der.class);

  /**
   * Returns t  scor ng funct on.
   */
  publ c abstract Scor ngFunct on getScor ngFunct on() throws  OExcept on, Cl entExcept on;

  publ c stat c f nal Str ng RETWEETS_SCORER_NAME = "ret ets";
  publ c stat c f nal Str ng NO_SPAM_SCORER_NAME = "no_spam";
  publ c stat c f nal Str ng TEST_SCORER_NAME = "test";

  // W t r to avo d t   decay w n scor ng top t ets.
  // Top arch ve does not need t   decay.
  pr vate stat c f nal boolean TOP_TWEET_W TH_DECAY =
          Earlyb rdConf g.getBool("top_t et_scor ng_w h_decay", true);

  /**
   * Abstract class that can be used for Scor ngFunct ons that don't throw a Cl entExcept on.
   *
   *   does throw an  OExcept on but   doesn't throw a Cl entExcept on so t  na  can be a b 
   * m slead ng.
   */
  publ c abstract stat c class Na dScor ngFunct onProv der extends Scor ngFunct onProv der {
    /**
     * Returns t  scor ng funct on.
     */
    publ c abstract Scor ngFunct on getScor ngFunct on() throws  OExcept on;
  }

  /**
   * Returns t  scor ng funct on prov der w h t  g ven na , or null  f no such prov der ex sts.
   */
  publ c stat c Na dScor ngFunct onProv der getScor ngFunct onProv derByNa (
      Str ng na , f nal  mmutableSc ma nterface sc ma) {
     f (na .equals(NO_SPAM_SCORER_NAME)) {
      return new Na dScor ngFunct onProv der() {
        @Overr de
        publ c Scor ngFunct on getScor ngFunct on() throws  OExcept on {
          return new SpamVectorScor ngFunct on(sc ma);
        }
      };
    } else  f (na .equals(RETWEETS_SCORER_NAME)) {
      return new Na dScor ngFunct onProv der() {
        @Overr de
        publ c Scor ngFunct on getScor ngFunct on() throws  OExcept on {
          // Product on top t et actually uses t .
           f (TOP_TWEET_W TH_DECAY) {
            return new Ret etBasedTopT etsScor ngFunct on(sc ma);
          } else {
            return new Ret etBasedTopT etsScor ngFunct on(sc ma, true);
          }
        }
      };
    } else  f (na .equals(TEST_SCORER_NAME)) {
      return new Na dScor ngFunct onProv der() {
        @Overr de
        publ c Scor ngFunct on getScor ngFunct on() throws  OExcept on {
          return new TestScor ngFunct on(sc ma);
        }
      };
    }
    return null;
  }

  /**
   * Returns default scor ng funct ons for d fferent scor ng funct on type
   * and prov des fallback behav or  f model-based scor ng funct on fa ls
   */
  publ c stat c class DefaultScor ngFunct onProv der extends Scor ngFunct onProv der {
    pr vate f nal Earlyb rdRequest request;
    pr vate f nal  mmutableSc ma nterface sc ma;
    pr vate f nal Thr ftSearchQuery searchQuery;
    pr vate f nal Ant Gam ngF lter ant Gam ngF lter;
    pr vate f nal UserTable userTable;
    pr vate f nal H Attr bute lper h Attr bute lper;
    pr vate f nal Query parsedQuery;
    pr vate f nal Scor ngModelsManager scor ngModelsManager;
    pr vate f nal TensorflowModelsManager tensorflowModelsManager;

    pr vate stat c f nal SearchCounter MODEL_BASED_SCOR NG_FUNCT ON_CREATED =
        SearchCounter.export("model_based_scor ng_funct on_created");
    pr vate stat c f nal SearchCounter MODEL_BASED_FALLBACK_TO_L NEAR_SCOR NG_FUNCT ON =
        SearchCounter.export("model_based_fallback_to_l near_scor ng_funct on");

    pr vate stat c f nal SearchCounter TENSORFLOW_BASED_SCOR NG_FUNCT ON_CREATED =
        SearchCounter.export("tensorflow_based_scor ng_funct on_created");
    pr vate stat c f nal SearchCounter TENSORFLOW_BASED_FALLBACK_TO_L NEAR_SCOR NG_FUNCT ON =
        SearchCounter.export("tensorflow_fallback_to_l near_funct on_scor ng_funct on");

    publ c DefaultScor ngFunct onProv der(
        f nal Earlyb rdRequest request,
        f nal  mmutableSc ma nterface sc ma,
        f nal Thr ftSearchQuery searchQuery,
        f nal Ant Gam ngF lter ant Gam ngF lter,
        f nal UserTable userTable,
        f nal H Attr bute lper h Attr bute lper,
        f nal Query parsedQuery,
        f nal Scor ngModelsManager scor ngModelsManager,
        f nal TensorflowModelsManager tensorflowModelsManager) {
      t .request = request;
      t .sc ma = sc ma;
      t .searchQuery = searchQuery;
      t .ant Gam ngF lter = ant Gam ngF lter;
      t .userTable = userTable;
      t .h Attr bute lper = h Attr bute lper;
      t .parsedQuery = parsedQuery;
      t .scor ngModelsManager = scor ngModelsManager;
      t .tensorflowModelsManager = tensorflowModelsManager;
    }

    @Overr de
    publ c Scor ngFunct on getScor ngFunct on() throws  OExcept on, Cl entExcept on {
       f (searchQuery. sSetRelevanceOpt ons()
          && searchQuery.getRelevanceOpt ons(). sSetRank ngParams()) {
        Thr ftRank ngParams params = searchQuery.getRelevanceOpt ons().getRank ngParams();
        Thr ftScor ngFunct onType type = params. sSetType()
            ? params.getType() : Thr ftScor ngFunct onType.L NEAR;  // default type
        sw ch (type) {
          case L NEAR:
            return createL near();
          case MODEL_BASED:
             f (scor ngModelsManager. sEnabled()) {
              MODEL_BASED_SCOR NG_FUNCT ON_CREATED. ncre nt();
              return createModelBased();
            } else {
              // From Scor ngModelsManager.NO_OP_MANAGER. Fall back to L nearScor ngFunct on
              MODEL_BASED_FALLBACK_TO_L NEAR_SCOR NG_FUNCT ON. ncre nt();
              return createL near();
            }
          case TENSORFLOW_BASED:
             f (tensorflowModelsManager. sEnabled()) {
              TENSORFLOW_BASED_SCOR NG_FUNCT ON_CREATED. ncre nt();
              return createTensorflowBased();
            } else {
              // Fallback to l near scor ng  f tf manager  s d sabled
              TENSORFLOW_BASED_FALLBACK_TO_L NEAR_SCOR NG_FUNCT ON. ncre nt();
              return createL near();
            }
          case TOPTWEETS:
            return createTopT ets();
          default:
            throw new  llegalArgu ntExcept on("Unknown scor ng type:  n " + searchQuery);
        }
      } else {
        LOG.error("No relevance opt ons prov ded query = " + searchQuery);
        return new DefaultScor ngFunct on(sc ma);
      }
    }

    pr vate Scor ngFunct on createL near() throws  OExcept on {
      L nearScor ngFunct on scor ngFunct on = new L nearScor ngFunct on(
          sc ma, searchQuery, ant Gam ngF lter, Thr ftSearchResultType.RELEVANCE,
          userTable);
      scor ngFunct on.setH Attr bute lperAndQuery(h Attr bute lper, parsedQuery);

      return scor ngFunct on;
    }

    /**
     * For model based scor ng funct on, Cl entExcept on w ll be throw  f cl ent selects an
     * unknown model for scor ng manager.
     * {@l nk com.tw ter.search.earlyb rd.search.relevance.scor ng.ModelBasedScor ngFunct on}
     */
    pr vate Scor ngFunct on createModelBased() throws  OExcept on, Cl entExcept on {
      ModelBasedScor ngFunct on scor ngFunct on = new ModelBasedScor ngFunct on(
          sc ma, searchQuery, ant Gam ngF lter, Thr ftSearchResultType.RELEVANCE, userTable,
          scor ngModelsManager);
      scor ngFunct on.setH Attr bute lperAndQuery(h Attr bute lper, parsedQuery);

      return scor ngFunct on;
    }

    pr vate Scor ngFunct on createTopT ets() throws  OExcept on {
      return new L nearScor ngFunct on(
          sc ma, searchQuery, ant Gam ngF lter, Thr ftSearchResultType.POPULAR, userTable);
    }

    pr vate TensorflowBasedScor ngFunct on createTensorflowBased()
      throws  OExcept on, Cl entExcept on {
      TensorflowBasedScor ngFunct on tfScor ngFunct on = new TensorflowBasedScor ngFunct on(
          request, sc ma, searchQuery, ant Gam ngF lter,
          Thr ftSearchResultType.RELEVANCE, userTable, tensorflowModelsManager);
      tfScor ngFunct on.setH Attr bute lperAndQuery(h Attr bute lper, parsedQuery);
      return tfScor ngFunct on;
    }
  }
}
