/* T  M T L cense

   Copyr ght (c) 2008, 2009, 2011 by Attract ve Chaos <attractor@l ve.co.uk>

   Perm ss on  s  reby granted, free of charge, to any person obta n ng
   a copy of t  software and assoc ated docu ntat on f les (t 
   "Software"), to deal  n t  Software w hout restr ct on,  nclud ng
   w hout l m at on t  r ghts to use, copy, mod fy,  rge, publ sh,
   d str bute, subl cense, and/or sell cop es of t  Software, and to
   perm  persons to whom t  Software  s furn s d to do so, subject to
   t  follow ng cond  ons:

   T  above copyr ght not ce and t  perm ss on not ce shall be
    ncluded  n all cop es or substant al port ons of t  Software.

   THE SOFTWARE  S PROV DED "AS  S", W THOUT WARRANTY OF ANY K ND,
   EXPRESS OR  MPL ED,  NCLUD NG BUT NOT L M TED TO THE WARRANT ES OF
   MERCHANTAB L TY, F TNESS FOR A PART CULAR PURPOSE AND
   NON NFR NGEMENT.  N NO EVENT SHALL THE AUTHORS OR COPYR GHT HOLDERS
   BE L ABLE FOR ANY CLA M, DAMAGES OR OTHER L AB L TY, WHETHER  N AN
   ACT ON OF CONTRACT, TORT OR OTHERW SE, AR S NG FROM, OUT OF OR  N
   CONNECT ON W TH THE SOFTWARE OR THE USE OR OTHER DEAL NGS  N THE
   SOFTWARE.
*/

/*
  An example:

# nclude "khash.h"
KHASH_MAP_ N T_ NT(32, char)
 nt ma n() {
    nt ret,  s_m ss ng;
   kh er_t k;
   khash_t(32) *h = kh_ n (32);
   k = kh_put(32, h, 5, &ret);
   kh_value(h, k) = 10;
   k = kh_get(32, h, 10);
    s_m ss ng = (k == kh_end(h));
   k = kh_get(32, h, 5);
   kh_del(32, h, k);
   for (k = kh_beg n(h); k != kh_end(h); ++k)
       f (kh_ex st(h, k)) kh_value(h, k) = 1;
   kh_destroy(32, h);
   return 0;
}
*/

/*
  2013-05-02 (0.2.8):

   * Use quadrat c prob ng. W n t  capac y  s po r of 2, stepp ng funct on
      *( +1)/2 guarantees to traverse each bucket.    s better than double
     hash ng on cac  performance and  s more robust than l near prob ng.

      n t ory, double hash ng should be more robust than quadrat c prob ng.
     Ho ver,    mple ntat on  s probably not for large hash tables, because
     t  second hash funct on  s closely t ed to t  f rst hash funct on,
     wh ch reduce t  effect veness of double hash ng.

   Reference: http://research.cs.vt.edu/AVresearch/hash ng/quadrat c.php

  2011-12-29 (0.2.7):

    * M nor code clean up; no actual effect.

  2011-09-16 (0.2.6):

   * T  capac y  s a po r of 2. T  seems to dramat cally  mprove t 
     speed for s mple keys. Thank Z long Tan for t  suggest on. Reference:

      - http://code.google.com/p/ul b/
      - http://noth ngs.org/computer/judy/

   * Allow to opt onally use l near prob ng wh ch usually has better
     performance for random  nput. Double hash ng  s st ll t  default as  
      s more robust to certa n non-random  nput.

   * Added Wang's  nteger hash funct on (not used by default). T  hash
     funct on  s more robust to certa n non-random  nput.

  2011-02-14 (0.2.5):

    * Allow to declare global funct ons.

  2009-09-26 (0.2.4):

    *  mprove portab l y

  2008-09-19 (0.2.3):

   * Corrected t  example
   *  mproved  nterfaces

  2008-09-11 (0.2.2):

   *  mproved speed a l tle  n kh_put()

  2008-09-10 (0.2.1):

   * Added kh_clear()
   * F xed a comp l ng error

  2008-09-02 (0.2.0):

   * Changed to token concatenat on wh ch  ncreases flex b l y.

  2008-08-31 (0.1.2):

   * F xed a bug  n kh_get(), wh ch has not been tested prev ously.

  2008-08-31 (0.1.1):

   * Added destructor
*/


# fndef __AC_KHASH_H
#def ne __AC_KHASH_H

/*!
  @ ader

  Gener c hash table l brary.
 */

#def ne AC_VERS ON_KHASH_H "0.2.8"

# nclude <stdl b.h>
# nclude <str ng.h>
# nclude <l m s.h>

/* comp ler spec f c conf gurat on */

# f U NT_MAX == 0xffffffffu
typedef uns gned  nt kh nt32_t;
#el f ULONG_MAX == 0xffffffffu
typedef uns gned long kh nt32_t;
#end f

# f ULONG_MAX == ULLONG_MAX
typedef uns gned long kh nt64_t;
#else
typedef u nt64_t kh nt64_t;
#end f

# fndef kh_ nl ne
# fdef _MSC_VER
#def ne kh_ nl ne __ nl ne
#else
#def ne kh_ nl ne  nl ne
#end f
#end f /* kh_ nl ne */

# fndef kl b_unused
# f (def ned __clang__ && __clang_major__ >= 3) || (def ned __GNUC__ && __GNUC__ >= 3)
#def ne kl b_unused __attr bute__ ((__unused__))
#else
#def ne kl b_unused
#end f
#end f /* kl b_unused */

typedef kh nt32_t kh nt_t;
typedef kh nt_t kh er_t;

#def ne __ac_ sempty(flag,  ) ((flag[ >>4]>>(( &0xfU)<<1))&2)
#def ne __ac_ sdel(flag,  ) ((flag[ >>4]>>(( &0xfU)<<1))&1)
#def ne __ac_ se  r(flag,  ) ((flag[ >>4]>>(( &0xfU)<<1))&3)
#def ne __ac_set_ sdel_false(flag,  ) (flag[ >>4]&=~(1ul<<(( &0xfU)<<1)))
#def ne __ac_set_ sempty_false(flag,  ) (flag[ >>4]&=~(2ul<<(( &0xfU)<<1)))
#def ne __ac_set_ sboth_false(flag,  ) (flag[ >>4]&=~(3ul<<(( &0xfU)<<1)))
#def ne __ac_set_ sdel_true(flag,  ) (flag[ >>4]|=1ul<<(( &0xfU)<<1))

#def ne __ac_fs ze(m) ((m) < 16? 1 : (m)>>4)

# fndef kroundup32
#def ne kroundup32(x) (--(x), (x)|=(x)>>1, (x)|=(x)>>2, (x)|=(x)>>4, (x)|=(x)>>8, (x)|=(x)>>16, ++(x))
#end f

# fndef kcalloc
#def ne kcalloc(N,Z) calloc(N,Z)
#end f
# fndef kmalloc
#def ne kmalloc(Z) malloc(Z)
#end f
# fndef krealloc
#def ne krealloc(P,Z) realloc(P,Z)
#end f
# fndef kfree
#def ne kfree(P) free(P)
#end f

stat c const double __ac_HASH_UPPER = 0.77;

#def ne __KHASH_TYPE(na , khkey_t, khval_t) \
   typedef struct kh_##na ##_s { \
      kh nt_t n_buckets, s ze, n_occup ed, upper_bound; \
      kh nt32_t *flags; \
      khkey_t *keys; \
      khval_t *vals; \
   } kh_##na ##_t;

#def ne __KHASH_PROTOTYPES(na , khkey_t, khval_t)                \
   extern kh_##na ##_t *kh_ n _##na (vo d);                    \
   extern vo d kh_destroy_##na (kh_##na ##_t *h);               \
   extern vo d kh_clear_##na (kh_##na ##_t *h);                 \
   extern kh nt_t kh_get_##na (const kh_##na ##_t *h, khkey_t key);   \
   extern  nt kh_res ze_##na (kh_##na ##_t *h, kh nt_t new_n_buckets); \
   extern kh nt_t kh_put_##na (kh_##na ##_t *h, khkey_t key,  nt *ret); \
   extern vo d kh_del_##na (kh_##na ##_t *h, kh nt_t x);

#def ne __KHASH_ MPL(na , SCOPE, khkey_t, khval_t, kh_ s_map, __hash_func, __hash_equal) \
   SCOPE kh_##na ##_t *kh_ n _##na (vo d) {                    \
      return (kh_##na ##_t*)kcalloc(1, s zeof(kh_##na ##_t));      \
   }                                                  \
   SCOPE vo d kh_destroy_##na (kh_##na ##_t *h)                 \
   {                                                  \
       f (h) {                                        \
         kfree((vo d *)h->keys); kfree(h->flags);              \
         kfree((vo d *)h->vals);                            \
         kfree(h);                                       \
      }                                               \
   }                                                  \
   SCOPE vo d kh_clear_##na (kh_##na ##_t *h)                \
   {                                                  \
       f (h && h->flags) {                               \
          mset(h->flags, 0xaa, __ac_fs ze(h->n_buckets) * s zeof(kh nt32_t)); \
         h->s ze = h->n_occup ed = 0;                       \
      }                                               \
   }                                                  \
   SCOPE kh nt_t kh_get_##na (const kh_##na ##_t *h, khkey_t key)  \
   {                                                  \
       f (h->n_buckets) {                                   \
         kh nt_t k,  , last, mask, step = 0; \
         mask = h->n_buckets - 1;                           \
         k = __hash_func(key);   = k & mask;                   \
         last =  ; \
         wh le (!__ac_ sempty(h->flags,  ) && (__ac_ sdel(h->flags,  ) || !__hash_equal(h->keys[ ], key))) { \
              = (  + (++step)) & mask; \
             f (  == last) return h->n_buckets;                \
         }                                            \
         return __ac_ se  r(h->flags,  )? h->n_buckets :  ;     \
      } else return 0;                                   \
   }                                                  \
   SCOPE  nt kh_res ze_##na (kh_##na ##_t *h, kh nt_t new_n_buckets) \
   { /* T  funct on uses 0.25*n_buckets bytes of work ng space  nstead of [s zeof(key_t+val_t)+.25]*n_buckets. */ \
      kh nt32_t *new_flags = 0;                             \
      kh nt_t j = 1;                                     \
      {                                               \
         kroundup32(new_n_buckets);                            \
          f (new_n_buckets < 4) new_n_buckets = 4;             \
          f (h->s ze >= (kh nt_t)(new_n_buckets * __ac_HASH_UPPER + 0.5)) j = 0; /* requested s ze  s too small */ \
         else { /* hash table s ze to be changed (shr nk or expand); rehash */ \
            new_flags = (kh nt32_t*)kmalloc(__ac_fs ze(new_n_buckets) * s zeof(kh nt32_t));  \
             f (!new_flags) return -1;                      \
             mset(new_flags, 0xaa, __ac_fs ze(new_n_buckets) * s zeof(kh nt32_t)); \
             f (h->n_buckets < new_n_buckets) { /* expand */      \
               khkey_t *new_keys = (khkey_t*)krealloc((vo d *)h->keys, new_n_buckets * s zeof(khkey_t)); \
                f (!new_keys) { kfree(new_flags); return -1; }    \
               h->keys = new_keys;                          \
                f (kh_ s_map) {                          \
                  khval_t *new_vals = (khval_t*)krealloc((vo d *)h->vals, new_n_buckets * s zeof(khval_t)); \
                   f (!new_vals) { kfree(new_flags); return -1; } \
                  h->vals = new_vals;                       \
               }                                      \
            } /* ot rw se shr nk */                        \
         }                                            \
      }                                               \
       f (j) { /* rehash ng  s needed */                       \
         for (j = 0; j != h->n_buckets; ++j) {                 \
             f (__ac_ se  r(h->flags, j) == 0) {             \
               khkey_t key = h->keys[j];                    \
               khval_t val;                              \
               kh nt_t new_mask;                         \
               new_mask = new_n_buckets - 1;                   \
                f (kh_ s_map) val = h->vals[j];             \
               __ac_set_ sdel_true(h->flags, j);               \
               wh le (1) { /* k ck-out process; sort of l ke  n Cuckoo hash ng */ \
                  kh nt_t k,  , step = 0; \
                  k = __hash_func(key);                     \
                    = k & new_mask;                      \
                  wh le (!__ac_ sempty(new_flags,  ))   = (  + (++step)) & new_mask; \
                  __ac_set_ sempty_false(new_flags,  );        \
                   f (  < h->n_buckets && __ac_ se  r(h->flags,  ) == 0) { /* k ck out t  ex st ng ele nt */ \
                     { khkey_t tmp = h->keys[ ]; h->keys[ ] = key; key = tmp; } \
                      f (kh_ s_map) { khval_t tmp = h->vals[ ]; h->vals[ ] = val; val = tmp; } \
                     __ac_set_ sdel_true(h->flags,  ); /* mark   as deleted  n t  old hash table */ \
                  } else { /* wr e t  ele nt and jump out of t  loop */ \
                     h->keys[ ] = key;                   \
                      f (kh_ s_map) h->vals[ ] = val;       \
                     break;                              \
                  }                                   \
               }                                      \
            }                                         \
         }                                            \
          f (h->n_buckets > new_n_buckets) { /* shr nk t  hash table */ \
            h->keys = (khkey_t*)krealloc((vo d *)h->keys, new_n_buckets * s zeof(khkey_t)); \
             f (kh_ s_map) h->vals = (khval_t*)krealloc((vo d *)h->vals, new_n_buckets * s zeof(khval_t)); \
         }                                            \
         kfree(h->flags); /* free t  work ng space */            \
         h->flags = new_flags;                              \
         h->n_buckets = new_n_buckets;                      \
         h->n_occup ed = h->s ze;                           \
         h->upper_bound = (kh nt_t)(h->n_buckets * __ac_HASH_UPPER + 0.5); \
      }                                               \
      return 0;                                          \
   }                                                  \
   SCOPE kh nt_t kh_put_##na (kh_##na ##_t *h, khkey_t key,  nt *ret) \
   {                                                  \
      kh nt_t x;                                         \
       f (h->n_occup ed >= h->upper_bound) { /* update t  hash table */ \
          f (h->n_buckets > (h->s ze<<1)) {                    \
             f (kh_res ze_##na (h, h->n_buckets - 1) < 0) { /* clear "deleted" ele nts */ \
               *ret = -1; return h->n_buckets;                 \
            }                                         \
         } else  f (kh_res ze_##na (h, h->n_buckets + 1) < 0) { /* expand t  hash table */ \
            *ret = -1; return h->n_buckets;                    \
         }                                            \
      } /* TODO: to  mple nt automat cally shr nk ng; res ze() already support shr nk ng */ \
      {                                               \
         kh nt_t k,  , s e, last, mask = h->n_buckets - 1, step = 0; \
         x = s e = h->n_buckets; k = __hash_func(key);   = k & mask; \
          f (__ac_ sempty(h->flags,  )) x =  ; /* for speed up */ \
         else {                                          \
            last =  ; \
            wh le (!__ac_ sempty(h->flags,  ) && (__ac_ sdel(h->flags,  ) || !__hash_equal(h->keys[ ], key))) { \
                f (__ac_ sdel(h->flags,  )) s e =  ;          \
                 = (  + (++step)) & mask; \
                f (  == last) { x = s e; break; }             \
            }                                         \
             f (x == h->n_buckets) {                        \
                f (__ac_ sempty(h->flags,  ) && s e != h->n_buckets) x = s e; \
               else x =  ;                               \
            }                                         \
         }                                            \
      }                                               \
       f (__ac_ sempty(h->flags, x)) { /* not present at all */      \
         h->keys[x] = key;                               \
         __ac_set_ sboth_false(h->flags, x);                   \
         ++h->s ze; ++h->n_occup ed;                           \
         *ret = 1;                                       \
      } else  f (__ac_ sdel(h->flags, x)) { /* deleted */            \
         h->keys[x] = key;                               \
         __ac_set_ sboth_false(h->flags, x);                   \
         ++h->s ze;                                      \
         *ret = 2;                                       \
      } else *ret = 0; /* Don't touch h->keys[x]  f present and not deleted */ \
      return x;                                          \
   }                                                  \
   SCOPE vo d kh_del_##na (kh_##na ##_t *h, kh nt_t x)          \
   {                                                  \
       f (x != h->n_buckets && !__ac_ se  r(h->flags, x)) {        \
         __ac_set_ sdel_true(h->flags, x);                     \
         --h->s ze;                                      \
      }                                               \
   }

#def ne KHASH_DECLARE(na , khkey_t, khval_t)                     \
   __KHASH_TYPE(na , khkey_t, khval_t)                        \
   __KHASH_PROTOTYPES(na , khkey_t, khval_t)

#def ne KHASH_ N T2(na , SCOPE, khkey_t, khval_t, kh_ s_map, __hash_func, __hash_equal) \
   __KHASH_TYPE(na , khkey_t, khval_t)                        \
   __KHASH_ MPL(na , SCOPE, khkey_t, khval_t, kh_ s_map, __hash_func, __hash_equal)

#def ne KHASH_ N T(na , khkey_t, khval_t, kh_ s_map, __hash_func, __hash_equal) \
   KHASH_ N T2(na , stat c kh_ nl ne kl b_unused, khkey_t, khval_t, kh_ s_map, __hash_func, __hash_equal)

/* --- BEG N OF HASH FUNCT ONS --- */

/*! @funct on
  @abstract      nteger hash funct on
  @param  key   T   nteger [kh nt32_t]
  @return       T  hash value [kh nt_t]
 */
#def ne kh_ nt_hash_func(key) (kh nt32_t)(key)
/*! @funct on
  @abstract      nteger compar son funct on
 */
#def ne kh_ nt_hash_equal(a, b) ((a) == (b))
/*! @funct on
  @abstract     64-b   nteger hash funct on
  @param  key   T   nteger [kh nt64_t]
  @return       T  hash value [kh nt_t]
 */
#def ne kh_ nt64_hash_func(key) (kh nt32_t)((key)>>33^(key)^(key)<<11)
/*! @funct on
  @abstract     64-b   nteger compar son funct on
 */
#def ne kh_ nt64_hash_equal(a, b) ((a) == (b))
/*! @funct on
  @abstract     const char* hash funct on
  @param  s     Po nter to a null term nated str ng
  @return       T  hash value
 */
stat c kh_ nl ne kh nt_t __ac_X31_hash_str ng(const char *s)
{
   kh nt_t h = (kh nt_t)*s;
    f (h) for (++s ; *s; ++s) h = (h << 5) - h + (kh nt_t)*s;
   return h;
}
/*! @funct on
  @abstract     Anot r  nterface to const char* hash funct on
  @param  key   Po nter to a null term nated str ng [const char*]
  @return       T  hash value [kh nt_t]
 */
#def ne kh_str_hash_func(key) __ac_X31_hash_str ng(key)
/*! @funct on
  @abstract     Const char* compar son funct on
 */
#def ne kh_str_hash_equal(a, b) (strcmp(a, b) == 0)

stat c kh_ nl ne kh nt_t __ac_Wang_hash(kh nt_t key)
{
    key += ~(key << 15);
    key ^=  (key >> 10);
    key +=  (key << 3);
    key ^=  (key >> 6);
    key += ~(key << 11);
    key ^=  (key >> 16);
    return key;
}
#def ne kh_ nt_hash_func2(key) __ac_Wang_hash((kh nt_t)key)

/* --- END OF HASH FUNCT ONS --- */

/* Ot r conven ent macros... */

/*!
  @abstract Type of t  hash table.
  @param  na   Na  of t  hash table [symbol]
 */
#def ne khash_t(na ) kh_##na ##_t

/*! @funct on
  @abstract      n  ate a hash table.
  @param  na   Na  of t  hash table [symbol]
  @return       Po nter to t  hash table [khash_t(na )*]
 */
#def ne kh_ n (na ) kh_ n _##na ()

/*! @funct on
  @abstract     Destroy a hash table.
  @param  na   Na  of t  hash table [symbol]
  @param  h     Po nter to t  hash table [khash_t(na )*]
 */
#def ne kh_destroy(na , h) kh_destroy_##na (h)

/*! @funct on
  @abstract     Reset a hash table w hout deallocat ng  mory.
  @param  na   Na  of t  hash table [symbol]
  @param  h     Po nter to t  hash table [khash_t(na )*]
 */
#def ne kh_clear(na , h) kh_clear_##na (h)

/*! @funct on
  @abstract     Res ze a hash table.
  @param  na   Na  of t  hash table [symbol]
  @param  h     Po nter to t  hash table [khash_t(na )*]
  @param  s     New s ze [kh nt_t]
 */
#def ne kh_res ze(na , h, s) kh_res ze_##na (h, s)

/*! @funct on
  @abstract      nsert a key to t  hash table.
  @param  na   Na  of t  hash table [symbol]
  @param  h     Po nter to t  hash table [khash_t(na )*]
  @param  k     Key [type of keys]
  @param  r     Extra return code: -1  f t  operat on fa led;
                0  f t  key  s present  n t  hash table;
                1  f t  bucket  s empty (never used); 2  f t  ele nt  n
            t  bucket has been deleted [ nt*]
  @return        erator to t   nserted ele nt [kh nt_t]
 */
#def ne kh_put(na , h, k, r) kh_put_##na (h, k, r)

/*! @funct on
  @abstract     Retr eve a key from t  hash table.
  @param  na   Na  of t  hash table [symbol]
  @param  h     Po nter to t  hash table [khash_t(na )*]
  @param  k     Key [type of keys]
  @return        erator to t  found ele nt, or kh_end(h)  f t  ele nt  s absent [kh nt_t]
 */
#def ne kh_get(na , h, k) kh_get_##na (h, k)

/*! @funct on
  @abstract     Remove a key from t  hash table.
  @param  na   Na  of t  hash table [symbol]
  @param  h     Po nter to t  hash table [khash_t(na )*]
  @param  k      erator to t  ele nt to be deleted [kh nt_t]
 */
#def ne kh_del(na , h, k) kh_del_##na (h, k)

/*! @funct on
  @abstract     Test w t r a bucket conta ns data.
  @param  h     Po nter to t  hash table [khash_t(na )*]
  @param  x      erator to t  bucket [kh nt_t]
  @return       1  f conta n ng data; 0 ot rw se [ nt]
 */
#def ne kh_ex st(h, x) (!__ac_ se  r((h)->flags, (x)))

/*! @funct on
  @abstract     Get key g ven an  erator
  @param  h     Po nter to t  hash table [khash_t(na )*]
  @param  x      erator to t  bucket [kh nt_t]
  @return       Key [type of keys]
 */
#def ne kh_key(h, x) ((h)->keys[x])

/*! @funct on
  @abstract     Get value g ven an  erator
  @param  h     Po nter to t  hash table [khash_t(na )*]
  @param  x      erator to t  bucket [kh nt_t]
  @return       Value [type of values]
  @d scuss on   For hash sets, call ng t  results  n segfault.
 */
#def ne kh_val(h, x) ((h)->vals[x])

/*! @funct on
  @abstract     Al as of kh_val()
 */
#def ne kh_value(h, x) ((h)->vals[x])

/*! @funct on
  @abstract     Get t  start  erator
  @param  h     Po nter to t  hash table [khash_t(na )*]
  @return       T  start  erator [kh nt_t]
 */
#def ne kh_beg n(h) (kh nt_t)(0)

/*! @funct on
  @abstract     Get t  end  erator
  @param  h     Po nter to t  hash table [khash_t(na )*]
  @return       T  end  erator [kh nt_t]
 */
#def ne kh_end(h) ((h)->n_buckets)

/*! @funct on
  @abstract     Get t  number of ele nts  n t  hash table
  @param  h     Po nter to t  hash table [khash_t(na )*]
  @return       Number of ele nts  n t  hash table [kh nt_t]
 */
#def ne kh_s ze(h) ((h)->s ze)

/*! @funct on
  @abstract     Get t  number of buckets  n t  hash table
  @param  h     Po nter to t  hash table [khash_t(na )*]
  @return       Number of buckets  n t  hash table [kh nt_t]
 */
#def ne kh_n_buckets(h) ((h)->n_buckets)

/*! @funct on
  @abstract      erate over t  entr es  n t  hash table
  @param  h     Po nter to t  hash table [khash_t(na )*]
  @param  kvar  Var able to wh ch key w ll be ass gned
  @param  vvar  Var able to wh ch value w ll be ass gned
  @param  code  Block of code to execute
 */
#def ne kh_foreach(h, kvar, vvar, code) { kh nt_t __ ;      \
   for (__  = kh_beg n(h); __  != kh_end(h); ++__ ) {    \
       f (!kh_ex st(h,__ )) cont nue;                 \
      (kvar) = kh_key(h,__ );                      \
      (vvar) = kh_val(h,__ );                      \
      code;                                  \
   } }

/*! @funct on
  @abstract      erate over t  values  n t  hash table
  @param  h     Po nter to t  hash table [khash_t(na )*]
  @param  vvar  Var able to wh ch value w ll be ass gned
  @param  code  Block of code to execute
 */
#def ne kh_foreach_value(h, vvar, code) { kh nt_t __ ;      \
   for (__  = kh_beg n(h); __  != kh_end(h); ++__ ) {    \
       f (!kh_ex st(h,__ )) cont nue;                 \
      (vvar) = kh_val(h,__ );                      \
      code;                                  \
   } }

/* More conen ent  nterfaces */

/*! @funct on
  @abstract      nstant ate a hash set conta n ng  nteger keys
  @param  na   Na  of t  hash table [symbol]
 */
#def ne KHASH_SET_ N T_ NT(na )                            \
   KHASH_ N T(na , kh nt32_t, char, 0, kh_ nt_hash_func, kh_ nt_hash_equal)

/*! @funct on
  @abstract      nstant ate a hash map conta n ng  nteger keys
  @param  na   Na  of t  hash table [symbol]
  @param  khval_t  Type of values [type]
 */
#def ne KHASH_MAP_ N T_ NT(na , khval_t)                      \
   KHASH_ N T(na , kh nt32_t, khval_t, 1, kh_ nt_hash_func, kh_ nt_hash_equal)

/*! @funct on
  @abstract      nstant ate a hash map conta n ng 64-b   nteger keys
  @param  na   Na  of t  hash table [symbol]
 */
#def ne KHASH_SET_ N T_ NT64(na )                             \
   KHASH_ N T(na , kh nt64_t, char, 0, kh_ nt64_hash_func, kh_ nt64_hash_equal)

/*! @funct on
  @abstract      nstant ate a hash map conta n ng 64-b   nteger keys
  @param  na   Na  of t  hash table [symbol]
  @param  khval_t  Type of values [type]
 */
#def ne KHASH_MAP_ N T_ NT64(na , khval_t)                       \
   KHASH_ N T(na , kh nt64_t, khval_t, 1, kh_ nt64_hash_func, kh_ nt64_hash_equal)

typedef const char *kh_cstr_t;
/*! @funct on
  @abstract      nstant ate a hash map conta n ng const char* keys
  @param  na   Na  of t  hash table [symbol]
 */
#def ne KHASH_SET_ N T_STR(na )                            \
   KHASH_ N T(na , kh_cstr_t, char, 0, kh_str_hash_func, kh_str_hash_equal)

/*! @funct on
  @abstract      nstant ate a hash map conta n ng const char* keys
  @param  na   Na  of t  hash table [symbol]
  @param  khval_t  Type of values [type]
 */
#def ne KHASH_MAP_ N T_STR(na , khval_t)                      \
   KHASH_ N T(na , kh_cstr_t, khval_t, 1, kh_str_hash_func, kh_str_hash_equal)

#end f /* __AC_KHASH_H */
