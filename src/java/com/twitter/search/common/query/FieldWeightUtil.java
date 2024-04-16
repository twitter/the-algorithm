package com.tw ter.search.common.query;

 mport java.ut l.Collect ons;
 mport java.ut l.EnumSet;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;

 mport javax.annotat on.Nullable;

 mport com.google.common.base.Enums;
 mport com.google.common.base.Funct on;
 mport com.google.common.base.Funct ons;
 mport com.google.common.base.Pred cates;
 mport com.google.common.collect.Fluent erable;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect. erables;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.sc ma.base.F eld  ghtDefault;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.annotat on.Annotat on;
 mport com.tw ter.search.queryparser.query.annotat on.F eldAnnotat onUt ls;
 mport com.tw ter.search.queryparser.query.annotat on.F eldNa W hBoost;

publ c f nal class F eld  ghtUt l {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(F eld  ghtUt l.class);
  pr vate F eld  ghtUt l() {
  }

  /**
   * Comb nes default f eld   ght conf gurat on w h f eld annotat ons and returns a
   * f eld-to-  ght map.
   *
   * @param query T  query whose annotat ons   w ll look  nto
   * @param defaultF eld  ghtMap f eld-to-F eld  ghtDefault map
   * @param enabledF eld  ghtMap for opt m zat on, t   s t  f eld-to-  ght map  nferred from
   * t  f eld-to-F eld  ghtDefault map
   * @param f eldNa ToTyped A funct on that can turn str ng f eld na  to typed f eld
   * @param <T> T  typed f eld
   */
  publ c stat c <T>  mmutableMap<T, Float> comb neDefaultW hAnnotat on(
      Query query,
      Map<T, F eld  ghtDefault> defaultF eld  ghtMap,
      Map<T, Float> enabledF eld  ghtMap,
      Funct on<Str ng, T> f eldNa ToTyped) throws QueryParserExcept on {
    return comb neDefaultW hAnnotat on(
        query,
        defaultF eld  ghtMap,
        enabledF eld  ghtMap,
        f eldNa ToTyped,
        Collect ons.<MappableF eld, T>emptyMap(),
        Funct ons.forMap(Collect ons.<T, Str ng>emptyMap(), ""));
  }

  /**
   * Comb nes default f eld   ght conf gurat on w h f eld annotat ons and returns a
   * f eld-to-  ght map. Also maps gener c mappable f elds to f eld   ght boosts and resolves t m
   *
   * @param query T  query whose annotat ons   w ll look  nto
   * @param defaultF eld  ghtMap f eld-to-F eld  ghtDefault map
   * @param enabledF eld  ghtMap for opt m zat on, t   s t  f eld-to-  ght map  nferred from
   * t  f eld-to-F eld  ghtDefault map
   * @param f eldNa ToTyped A funct on that can turn a str ng f eld na  to typed f eld
   * @param mappableF eldMap mapp ng of mappable f elds to t  correspond ng typed f elds
   * @param typedToF eldNa  A funct on that can turn a typed f eld  nto a str ng f eld na 
   * @param <T> T  typed f eld
   *
   * Note: As a result of d scuss on on SEARCH-24029,   now allow replace and remove annotat ons
   * on a s ngle term. See http://go/f eld  ght for  nfo on f eld   ght annotat ons.
   */
  publ c stat c <T>  mmutableMap<T, Float> comb neDefaultW hAnnotat on(
        Query query,
        Map<T, F eld  ghtDefault> defaultF eld  ghtMap,
        Map<T, Float> enabledF eld  ghtMap,
        Funct on<Str ng, T> f eldNa ToTyped,
        Map<MappableF eld, T> mappableF eldMap,
        Funct on<T, Str ng> typedToF eldNa ) throws QueryParserExcept on {
    L st<Annotat on> f eldAnnotat ons = query.getAllAnnotat onsOf(Annotat on.Type.F ELD);
    L st<Annotat on> mappableF eldAnnotat ons =
      query.getAllAnnotat onsOf(Annotat on.Type.MAPPABLE_F ELD);

     f (f eldAnnotat ons. sEmpty() && mappableF eldAnnotat ons. sEmpty()) {
      return  mmutableMap.copyOf(enabledF eld  ghtMap);
    }

    // Convert mapped f elds to f eld annotat ons
     erable<Annotat on> f eldAnnotat onsForMappedF elds =
        Fluent erable.from(mappableF eldAnnotat ons)
            .transform(F eld  ghtUt l.f eldAnnotat onForMappableF eld(mappableF eldMap,
                                                                       typedToF eldNa ))
            .f lter(Pred cates.notNull());

     erable<Annotat on> annotat ons =
         erables.concat(f eldAnnotat onsForMappedF elds, f eldAnnotat ons);

    // San  ze t  f eld annotat ons f rst, remove t  ones   don't know
    // for REPLACE and REMOVE.
    L st<F eldNa W hBoost> san  zedF elds = L sts.newArrayL st();
    Set<F eldNa W hBoost.F eldMod f er> seenMod f erTypes =
        EnumSet.noneOf(F eldNa W hBoost.F eldMod f er.class);

    for (Annotat on annotat on : annotat ons) {
      F eldNa W hBoost f eldNa W hBoost = (F eldNa W hBoost) annotat on.getValue();
      T typedF eld = f eldNa ToTyped.apply(f eldNa W hBoost.getF eldNa ());
      F eldNa W hBoost.F eldMod f er mod f er = f eldNa W hBoost.getF eldMod f er();
       f (defaultF eld  ghtMap.conta nsKey(typedF eld)) {
        seenMod f erTypes.add(mod f er);
        san  zedF elds.add(f eldNa W hBoost);
      }
    }

    // Even  f t re  s no mapp ng for a mapped annotat on,  f a query  s replaced by an unknown
    // mapp ng,   should not map to ot r f elds, so   need to detect a REPLACE annotat on
     f (seenMod f erTypes. sEmpty()
        && F eldAnnotat onUt ls.hasReplaceAnnotat on(mappableF eldAnnotat ons)) {
      seenMod f erTypes.add(F eldNa W hBoost.F eldMod f er.REPLACE);
    }

    boolean onlyHasReplace = seenMod f erTypes.s ze() == 1
      && seenMod f erTypes.conta ns(F eldNa W hBoost.F eldMod f er.REPLACE);

    //  f   only have replace, start w h an empty map, ot rw se, start w h all enabled f elds.
    Map<T, Float> actualMap = onlyHasReplace
        ? Maps.<T, Float>newL nkedHashMap()
        : Maps.newL nkedHashMap(enabledF eld  ghtMap);

    // Go over all f eld annotat ons and apply t m.
    for (F eldNa W hBoost f eldAnnotat on : san  zedF elds) {
      T typedF eld = f eldNa ToTyped.apply(f eldAnnotat on.getF eldNa ());
      F eldNa W hBoost.F eldMod f er mod f er = f eldAnnotat on.getF eldMod f er();
      sw ch (mod f er) {
        case REMOVE:
          actualMap.remove(typedF eld);
          break;

        case ADD:
        case REPLACE:
           f (f eldAnnotat on.getBoost(). sPresent()) {
            actualMap.put(typedF eld, f eldAnnotat on.getBoost().get());
          } else {
            // W n annotat on does not spec fy   ght, use default   ght
            actualMap.put(
                typedF eld,
                defaultF eld  ghtMap.get(typedF eld).get  ght());
          }
          break;
        default:
          throw new QueryParserExcept on("Unknown f eld annotat on type: " + f eldAnnotat on);
      }
    }

    return  mmutableMap.copyOf(actualMap);
  }

  publ c stat c  mmutableMap<Str ng, Float> comb neDefaultW hAnnotat on(
      Query query,
      Map<Str ng, F eld  ghtDefault> defaultF eld  ghtMap,
      Map<Str ng, Float> enabledF eld  ghtMap) throws QueryParserExcept on {

    return comb neDefaultW hAnnotat on(
        query, defaultF eld  ghtMap, enabledF eld  ghtMap, Funct ons.<Str ng> dent y());
  }

  /**
   * Create an annotat on of t  F ELD type from annotat ons of t  MAPPED_F ELD type
   * @param mappableF eldMap mapp ng of mappable f elds to t  correspond ng typed f elds
   * @param typedToF eldNa  A funct on that can turn a typed f eld  nto a str ng f eld na 
   * @param <T> T  typed f eld
   * @return an Annotat on w h t  sa  mod f er and boost for a F ELD as t   ncom ng MAPPED_F ELD
   * annotat on
   */
  pr vate stat c <T> Funct on<Annotat on, Annotat on> f eldAnnotat onForMappableF eld(
      f nal Map<MappableF eld, T> mappableF eldMap,
      f nal Funct on<T, Str ng> typedToF eldNa ) {
    return new Funct on<Annotat on, Annotat on>() {
      @Nullable
      @Overr de
      publ c Annotat on apply(Annotat on mappableAnnotat on) {
        F eldNa W hBoost f eldNa W hBoost = (F eldNa W hBoost) mappableAnnotat on.getValue();
        MappableF eld mappedF eld =
            Enums.get fPresent(
                MappableF eld.class,
                f eldNa W hBoost.getF eldNa ().toUpperCase()).orNull();
        T typedF eldNa  = mappableF eldMap.get(mappedF eld);
        Annotat on f eldAnnotat on = null;
         f (typedF eldNa  != null) {
          Str ng f eldNa  = typedToF eldNa .apply(typedF eldNa );
          F eldNa W hBoost mappedF eldBoost =
              new F eldNa W hBoost(
                  f eldNa ,
                  f eldNa W hBoost.getBoost(),
                  f eldNa W hBoost.getF eldMod f er());
          f eldAnnotat on = Annotat on.Type.F ELD.new nstance(mappedF eldBoost);
        }
        return f eldAnnotat on;
      }
    };
  }
}
