package com.tw ter.search.common.relevance.ent  es;

 mport java.ut l.Opt onal;
 mport javax.annotat on.Nonnull;

 mport com.google.common.base.Precond  ons;

 mport org.apac .commons.lang3.bu lder.EqualsBu lder;
 mport org.apac .commons.lang3.bu lder.HashCodeBu lder;
 mport org.apac .lucene.analys s.TokenStream;

 mport com.tw ter.search.common.ut l.text.Token zer lper;

// Represents from-user, to-user,  nt ons and aud oSpace adm ns  n Tw ter ssage.
publ c f nal class Tw ter ssageUser {

  @Nonnull pr vate f nal Opt onal<Str ng> screenNa ;  // a.k.a. user handle or userna 
  @Nonnull pr vate f nal Opt onal<Str ng> d splayNa ;

  @Nonnull pr vate Opt onal<TokenStream> token zedScreenNa ;

  @Nonnull pr vate f nal Opt onal<Long>  d; // tw ter  D

  publ c stat c f nal class Bu lder {
    @Nonnull pr vate Opt onal<Str ng> screenNa  = Opt onal.empty();
    @Nonnull pr vate Opt onal<Str ng> d splayNa  = Opt onal.empty();
    @Nonnull pr vate Opt onal<TokenStream> token zedScreenNa  = Opt onal.empty();
    @Nonnull pr vate Opt onal<Long>  d = Opt onal.empty();

    publ c Bu lder() {
    }

    /**
     *  n  al zed Bu lder based on an ex st ng Tw ter ssageUser
     */
    publ c Bu lder(Tw ter ssageUser user) {
      t .screenNa  = user.screenNa ;
      t .d splayNa  = user.d splayNa ;
      t .token zedScreenNa  = user.token zedScreenNa ;
      t . d = user. d;
    }

    /**
     *  n  al zed Bu lder screen na  (handle/t  na  follow ng t  "@") and do token zat on
     * for  .
     */
    publ c Bu lder w hScreenNa (Opt onal<Str ng> newScreenNa ) {
      t .screenNa  = newScreenNa ;
       f (newScreenNa . sPresent()) {
        t .token zedScreenNa  = Opt onal.of(
            Token zer lper.getNormal zedCa lcaseTokenStream(newScreenNa .get()));
      }
      return t ;
    }

    /**
     *  n  al zed Bu lder d splay na 
     */
    publ c Bu lder w hD splayNa (Opt onal<Str ng> newD splayNa ) {
      t .d splayNa  = newD splayNa ;
      return t ;
    }

    publ c Bu lder w h d(Opt onal<Long> new d) {
      t . d = new d;
      return t ;
    }

    publ c Tw ter ssageUser bu ld() {
      return new Tw ter ssageUser(
          screenNa , d splayNa , token zedScreenNa ,  d);
    }
  }

  /** Creates a Tw ter ssageUser  nstance w h t  g ven screen na . */
  publ c stat c Tw ter ssageUser createW hScreenNa (@Nonnull Str ng screenNa ) {
    Precond  ons.c ckNotNull(screenNa , "Don't set a null screen na ");
    return new Bu lder()
        .w hScreenNa (Opt onal.of(screenNa ))
        .bu ld();
  }

  /** Creates a Tw ter ssageUser  nstance w h t  g ven d splay na . */
  publ c stat c Tw ter ssageUser createW hD splayNa (@Nonnull Str ng d splayNa ) {
    Precond  ons.c ckNotNull(d splayNa , "Don't set a null d splay na ");
    return new Bu lder()
        .w hD splayNa (Opt onal.of(d splayNa ))
        .bu ld();
  }

  /** Creates a Tw ter ssageUser  nstance w h t  g ven  D. */
  publ c stat c Tw ter ssageUser createW h d(long  d) {
    Precond  ons.c ckArgu nt( d >= 0, "Don't sent a negat ve user  D");
    return new Bu lder()
        .w h d(Opt onal.of( d))
        .bu ld();
  }

  /** Creates a Tw ter ssageUser  nstance w h t  g ven para ters. */
  publ c stat c Tw ter ssageUser createW hNa sAnd d(
      @Nonnull Str ng screenNa ,
      @Nonnull Str ng d splayNa ,
      long  d) {
    Precond  ons.c ckNotNull(screenNa , "Use anot r  thod  nstead of pass ng null na ");
    Precond  ons.c ckNotNull(d splayNa , "Use anot r  thod  nstead of pass ng null na ");
    Precond  ons.c ckArgu nt( d >= 0, "Use anot r  thod  nstead of pass ng negat ve  D");
    return new Bu lder()
        .w hScreenNa (Opt onal.of(screenNa ))
        .w hD splayNa (Opt onal.of(d splayNa ))
        .w h d(Opt onal.of( d))
        .bu ld();
  }

  /** Creates a Tw ter ssageUser  nstance w h t  g ven para ters. */
  publ c stat c Tw ter ssageUser createW hNa s(
      @Nonnull Str ng screenNa ,
      @Nonnull Str ng d splayNa ) {
    Precond  ons.c ckNotNull(screenNa , "Use anot r  thod  nstead of pass ng null na ");
    Precond  ons.c ckNotNull(d splayNa , "Use anot r  thod  nstead of pass ng null na ");
    return new Bu lder()
        .w hScreenNa (Opt onal.of(screenNa ))
        .w hD splayNa (Opt onal.of(d splayNa ))
        .bu ld();
  }

  /** Creates a Tw ter ssageUser  nstance w h t  g ven para ters. */
  publ c stat c Tw ter ssageUser createW hOpt onalNa sAnd d(
      @Nonnull Opt onal<Str ng> optScreenNa ,
      @Nonnull Opt onal<Str ng> optD splayNa ,
      @Nonnull Opt onal<Long> opt d) {
    Precond  ons.c ckNotNull(optScreenNa , "Pass Opt onal.absent()  nstead of null");
    Precond  ons.c ckNotNull(optD splayNa , "Pass Opt onal.absent()  nstead of null");
    Precond  ons.c ckNotNull(opt d, "Pass Opt onal.absent()  nstead of null");
    return new Bu lder()
        .w hScreenNa (optScreenNa )
        .w hD splayNa (optD splayNa )
        .w h d(opt d)
        .bu ld();
  }

  pr vate Tw ter ssageUser(
      @Nonnull Opt onal<Str ng> screenNa ,
      @Nonnull Opt onal<Str ng> d splayNa ,
      @Nonnull Opt onal<TokenStream> token zedScreenNa ,
      @Nonnull Opt onal<Long>  d) {
    t .screenNa  = screenNa ;
    t .d splayNa  = d splayNa ;
    t .token zedScreenNa  = token zedScreenNa ;
    t . d =  d;
  }

  /** Creates a copy of t  Tw ter ssageUser  nstance, w h t  g ven screen na . */
  publ c Tw ter ssageUser copyW hScreenNa (@Nonnull Str ng newScreenNa ) {
    Precond  ons.c ckNotNull(newScreenNa , "Don't set a null screen na ");
    return new Bu lder(t )
        .w hScreenNa (Opt onal.of(newScreenNa ))
        .bu ld();
  }

  /** Creates a copy of t  Tw ter ssageUser  nstance, w h t  g ven d splay na . */
  publ c Tw ter ssageUser copyW hD splayNa (@Nonnull Str ng newD splayNa ) {
    Precond  ons.c ckNotNull(newD splayNa , "Don't set a null d splay na ");
    return new Bu lder(t )
        .w hD splayNa (Opt onal.of(newD splayNa ))
        .bu ld();
  }

  /** Creates a copy of t  Tw ter ssageUser  nstance, w h t  g ven  D. */
  publ c Tw ter ssageUser copyW h d(long new d) {
    Precond  ons.c ckArgu nt(new d >= 0, "Don't set a negat ve user  D");
    return new Bu lder(t )
        .w h d(Opt onal.of(new d))
        .bu ld();
  }

  publ c Opt onal<Str ng> getScreenNa () {
    return screenNa ;
  }

  publ c Opt onal<Str ng> getD splayNa () {
    return d splayNa ;
  }

  publ c Opt onal<TokenStream> getToken zedScreenNa () {
    return token zedScreenNa ;
  }

  publ c Opt onal<Long> get d() {
    return  d;
  }

  @Overr de
  publ c Str ng toStr ng() {
    return "[" + screenNa  + ", " + d splayNa  + ", " +  d + "]";
  }

  /**
   * Compares t  Tw ter ssageUser  nstance to t  g ven object.
   *
   * @deprecated deprecated.
   */
  @Deprecated
  @Overr de
  publ c boolean equals(Object o) {
     f (o == null) {
      return false;
    }
     f (o == t ) {
      return true;
    }
     f (o.getClass() != getClass()) {
      return false;
    }
    Tw ter ssageUser ot r = (Tw ter ssageUser) o;
    return new EqualsBu lder()
        .append(screenNa , ot r.screenNa )
        .append(d splayNa , ot r.d splayNa )
        . sEquals();
  }

  /**
   * Returns a hash code for t  Tw ter ssageUser  nstance.
   *
   * @deprecated deprecated.
   */
  @Deprecated
  @Overr de
  publ c  nt hashCode() {
    return HashCodeBu lder.reflect onHashCode(t );
  }
}
