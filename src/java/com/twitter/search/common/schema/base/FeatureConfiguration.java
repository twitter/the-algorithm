package com.tw ter.search.common.sc ma.base;

 mport java.ut l.Set;

 mport javax.annotat on.Nullable;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Sets;

 mport com.tw ter.common.base.MorePrecond  ons;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftCSFType;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftFeatureNormal zat onType;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftFeatureUpdateConstra nt;

// FeatureConf gurat on  s def ned for all t  column str de v ew f elds.
publ c f nal class FeatureConf gurat on {
  pr vate f nal Str ng na ;
  pr vate f nal  nt  nt ndex;
  // Start pos  on  n t  g ven  nt (0-31)
  pr vate f nal  nt b StartPos;
  // Length  n b s of t  feature
  pr vate f nal  nt b Length;
  // precomputed for reuse
  pr vate f nal  nt b Mask;
  pr vate f nal  nt  nverseB Mask;
  pr vate f nal  nt maxValue;

  pr vate f nal Thr ftCSFType type;

  // T   s t  cl ent seen feature type:  f t   s null, t  f eld  s unused.
  @Nullable
  pr vate f nal Thr ftCSFType outputType;

  pr vate f nal Str ng baseF eld;

  pr vate f nal Set<FeatureConstra nt> featureUpdateConstra nts;

  pr vate f nal Thr ftFeatureNormal zat onType featureNormal zat onType;

  /**
   * Creates a new FeatureConf gurat on w h a base f eld.
   *
   * @param  nt ndex wh ch  nteger  s t  feature  n (0 based).
   * @param b StartPos at wh ch b  does t  feature start (0-31).
   * @param b Length length  n b s of t  feature
   * @param baseF eld t  CSF t  feature  s stored w h n.
   */
  pr vate FeatureConf gurat on(
          Str ng na ,
          Thr ftCSFType type,
          Thr ftCSFType outputType,
           nt  nt ndex,
           nt b StartPos,
           nt b Length,
          Str ng baseF eld,
          Set<FeatureConstra nt> featureUpdateConstra nts,
          Thr ftFeatureNormal zat onType featureNormal zat onType) {
    Precond  ons.c ckState(b StartPos + b Length <=  nteger.S ZE,
            "Feature must not cross  nt boundary.");
    t .na  = MorePrecond  ons.c ckNotBlank(na );
    t .type = Precond  ons.c ckNotNull(type);
    t .outputType = outputType;
    t . nt ndex =  nt ndex;
    t .b StartPos = b StartPos;
    t .b Length = b Length;
    // Techn cally,  nt-s zed features can use all 32 b s to store a pos  ve value greater than
    //  nteger.MAX_VALUE. But  n pract ce,   w ll convert t  values of those features to Java  nts
    // on t  read s de, so t  max value for those features w ll st ll be  nteger.MAX_VALUE.
    t .maxValue = (1 << Math.m n(b Length,  nteger.S ZE - 1)) - 1;
    t .b Mask = ( nt) (((1L << b Length) - 1) << b StartPos);
    t . nverseB Mask = ~b Mask;
    t .baseF eld = baseF eld;
    t .featureUpdateConstra nts = featureUpdateConstra nts;
    t .featureNormal zat onType = Precond  ons.c ckNotNull(featureNormal zat onType);
  }

  publ c Str ng getNa () {
    return na ;
  }

  publ c  nt getMaxValue() {
    return maxValue;
  }

  @Overr de
  publ c Str ng toStr ng() {
    return new Str ngBu lder().append(na )
            .append(" (").append( nt ndex).append(", ")
            .append(b StartPos).append(", ")
            .append(b Length).append(") ").toStr ng();
  }

  publ c  nt getValue ndex() {
    return  nt ndex;
  }

  publ c  nt getB StartPos  on() {
    return b StartPos;
  }

  publ c  nt getB Length() {
    return b Length;
  }

  publ c  nt getB Mask() {
    return b Mask;
  }

  publ c  nt get nverseB Mask() {
    return  nverseB Mask;
  }

  publ c Str ng getBaseF eld() {
    return baseF eld;
  }

  publ c Thr ftCSFType getType() {
    return type;
  }

  @Nullable
  publ c Thr ftCSFType getOutputType() {
    return outputType;
  }

  publ c Thr ftFeatureNormal zat onType getFeatureNormal zat onType() {
    return featureNormal zat onType;
  }

  /**
   * Returns t  update constra nt for t  feature.
   */
  publ c Set<Thr ftFeatureUpdateConstra nt> getUpdateConstra nts() {
     f (featureUpdateConstra nts == null) {
      return null;
    }
    Set<Thr ftFeatureUpdateConstra nt> constra ntSet = Sets.newHashSet();
    for (FeatureConstra nt constra nt : featureUpdateConstra nts) {
      constra ntSet.add(constra nt.getType());
    }
    return constra ntSet;
  }

  /**
   * Returns true  f t  g ven update sat sf es all feature update constra nts.
   */
  publ c boolean val dateFeatureUpdate(f nal Number oldValue, f nal Number newValue) {
     f (featureUpdateConstra nts != null) {
      for (FeatureConstra nt contra nt : featureUpdateConstra nts) {
         f (!contra nt.apply(oldValue, newValue)) {
          return false;
        }
      }
    }

    return true;
  }

  @Overr de
  publ c  nt hashCode() {
    return (na  == null ? 0 : na .hashCode())
        +  nt ndex * 7
        + b StartPos * 13
        + b Length * 23
        + b Mask * 31
        +  nverseB Mask * 43
        + ( nt) maxValue * 53
        + (type == null ? 0 : type.hashCode()) * 61
        + (outputType == null ? 0 : outputType.hashCode()) * 71
        + (baseF eld == null ? 0 : baseF eld.hashCode()) * 83
        + (featureUpdateConstra nts == null ? 0 : featureUpdateConstra nts.hashCode()) * 87
        + (featureNormal zat onType == null ? 0 : featureNormal zat onType.hashCode()) * 97;
  }

  @Overr de
  publ c boolean equals(Object obj) {
     f (!(obj  nstanceof FeatureConf gurat on)) {
      return false;
    }

    FeatureConf gurat on featureConf gurat on = FeatureConf gurat on.class.cast(obj);
    return (na  == featureConf gurat on.na )
        && (b StartPos == featureConf gurat on.b StartPos)
        && (b Length == featureConf gurat on.b Length)
        && (b Mask == featureConf gurat on.b Mask)
        && ( nverseB Mask == featureConf gurat on. nverseB Mask)
        && (maxValue == featureConf gurat on.maxValue)
        && (type == featureConf gurat on.type)
        && (outputType == featureConf gurat on.outputType)
        && (baseF eld == featureConf gurat on.baseF eld)
        && (featureUpdateConstra nts == null
            ? featureConf gurat on.featureUpdateConstra nts == null
            : featureUpdateConstra nts.equals(featureConf gurat on.featureUpdateConstra nts))
        && (featureNormal zat onType == null
            ? featureConf gurat on.featureNormal zat onType == null
            : featureNormal zat onType.equals(featureConf gurat on.featureNormal zat onType));
  }

  pr vate  nterface FeatureConstra nt {
    boolean apply(Number oldValue, Number newValue);
    Thr ftFeatureUpdateConstra nt getType();
  }

  publ c stat c Bu lder bu lder() {
    return new Bu lder();
  }

  publ c stat c f nal class Bu lder {
    pr vate Str ng na ;
    pr vate Thr ftCSFType type;
    pr vate Thr ftCSFType outputType;
    pr vate  nt  nt ndex;
    // Start pos  on  n t  g ven  nt (0-31)
    pr vate  nt b StartPos;
    // Length  n b s of t  feature
    pr vate  nt b Length;

    pr vate Str ng baseF eld;

    pr vate Set<FeatureConstra nt> featureUpdateConstra nts;

    pr vate Thr ftFeatureNormal zat onType featureNormal zat onType =
        Thr ftFeatureNormal zat onType.NONE;

    publ c FeatureConf gurat on bu ld() {
      return new FeatureConf gurat on(na , type, outputType,  nt ndex, b StartPos, b Length,
              baseF eld, featureUpdateConstra nts, featureNormal zat onType);
    }

    publ c Bu lder w hNa (Str ng n) {
      t .na  = n;
      return t ;
    }

    publ c Bu lder w hType(Thr ftCSFType featureType) {
      t .type = featureType;
      return t ;
    }

    publ c Bu lder w hOutputType(Thr ftCSFType featureFeatureType) {
      t .outputType = featureFeatureType;
      return t ;
    }

    publ c Bu lder w hFeatureNormal zat onType(
        Thr ftFeatureNormal zat onType normal zat onType) {
      t .featureNormal zat onType = Precond  ons.c ckNotNull(normal zat onType);
      return t ;
    }

    /**
     * Sets t  b  range at t  g ven  nt ndex, startPos and length.
     */
    publ c Bu lder w hB Range( nt  ndex,  nt startPos,  nt length) {
      t . nt ndex =  ndex;
      t .b StartPos = startPos;
      t .b Length = length;
      return t ;
    }

    publ c Bu lder w hBaseF eld(Str ng baseF eldNa ) {
      t .baseF eld = baseF eldNa ;
      return t ;
    }

    /**
     * Adds a feature update constra nt.
     */
    publ c Bu lder w hFeatureUpdateConstra nt(f nal Thr ftFeatureUpdateConstra nt constra nt) {
       f (featureUpdateConstra nts == null) {
        featureUpdateConstra nts = Sets.newHashSet();
      }

      sw ch (constra nt) {
        case  MMUTABLE:
          featureUpdateConstra nts.add(new FeatureConstra nt() {
            @Overr de publ c boolean apply(Number oldValue, Number newValue) {
              return false;
            }
            @Overr de publ c Thr ftFeatureUpdateConstra nt getType() {
              return Thr ftFeatureUpdateConstra nt. MMUTABLE;
            }
          });
          break;
        case  NC_ONLY:
          featureUpdateConstra nts.add(new FeatureConstra nt() {
            @Overr de  publ c boolean apply(Number oldValue, Number newValue) {
              return newValue. ntValue() > oldValue. ntValue();
            }
            @Overr de publ c Thr ftFeatureUpdateConstra nt getType() {
              return Thr ftFeatureUpdateConstra nt. NC_ONLY;
            }
          });
          break;
        case POS T VE:
          featureUpdateConstra nts.add(new FeatureConstra nt() {
            @Overr de  publ c boolean apply(Number oldValue, Number newValue) {
              return newValue. ntValue() >= 0;
            }
            @Overr de publ c Thr ftFeatureUpdateConstra nt getType() {
              return Thr ftFeatureUpdateConstra nt.POS T VE;
            }
          });
          break;
        default:
      }

      return t ;
    }

    pr vate Bu lder() {

    }
  }
}

