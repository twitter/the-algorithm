# nclude " nternal/l near_search.h"
# nclude " nternal/error.h"
# nclude <twml/hash ng_d scret zer_ mpl.h>
# nclude <twml/opt m.h>
# nclude <algor hm>

na space twml {
  template<typena  Tx>
  stat c  nt64_t lo r_bound_search(const Tx *data, const Tx val, const  nt64_t buf_s ze) {
    auto  ndex_temp = std::lo r_bound(data, data + buf_s ze, val);
    return stat c_cast< nt64_t>( ndex_temp - data);
  }

  template<typena  Tx>
  stat c  nt64_t upper_bound_search(const Tx *data, const Tx val, const  nt64_t buf_s ze) {
    auto  ndex_temp = std::upper_bound(data, data + buf_s ze, val);
    return stat c_cast< nt64_t>( ndex_temp - data);
  }

  template<typena  Tx>
  us ng search_ thod =  nt64_t (*)(const Tx *, const Tx, const  nt64_t);

  typedef u nt64_t (*hash_s gnature)(u nt64_t,  nt64_t, u nt64_t);

  // u nt64_t  nteger_mult pl cat ve_hash ng()
  //
  // A funct on to hash d scret zed feature_ ds  nto one of 2**output_b s buckets.
  // T  funct on has s t  feature_ ds to ach eve a un form d str but on of
  //    Ds, so t  has d  Ds are w h h gh probab l y far apart
  // T n, bucket_ nd ces can s mply be added, result ng  n un que new  Ds w h h gh probab l y
  //    nteger hash aga n to aga n spread out t  new  Ds
  // F nally   take t  upper
  // Requ red args:
  //   feature_ d:
  //     T  feature  d of t  feature to be has d.
  //   bucket_ ndex:
  //     T  bucket  ndex of t  d scret zed feature value
  //   output_b s:
  //     T  number of b s of output space for t  features to be has d  nto.
  //
  // Note - feature_ ds may have arb rary d str but on w h n  nt32s
  // Note - 64 b  feature_ ds can be processed w h t , but t  upper
  //          32 b s have no effect on t  output
  // e.g. all feature  ds 0 through 255 ex st  n mov e-lens.
  // t  hash ng constant  s good for 32 LSBs. w ll use N=32. (can use N<32 also)
  // t  hash ng constant  s co-pr   w h 2**32, t refore   have that
  //   a != b, a and b  n [0,2**32)
  //     mpl es
  //   f(a) != f(b) w re f(x) = (hash ng_constant * x) % (2**32)
  // note that   are mostly  gnor ng t  upper 32 b s, us ng modulo 2**32 ar h t c
  u nt64_t  nteger_mult pl cat ve_hash ng(u nt64_t feature_ d,
                                           nt64_t bucket_ ndex,
                                          u nt64_t output_b s) {
    // poss bly use 14695981039346656037 for 64 b  uns gned??
    //  = 20921 * 465383 * 1509404459
    // alternat vely, 14695981039346656039  s pr  
    //   would also need to use N = 64
    const u nt64_t hash ng_constant = 2654435761;
    const u nt64_t N = 32;
    // hash once to prevent problems from anomalous  nput  d d str but ons
    feature_ d *= hash ng_constant;
    feature_ d += bucket_ ndex;
    // t  hash enables t  follow ng r ght sh ft operat on
    //  w hout los ng t  bucket  nformat on (lo r b s)
    feature_ d *= hash ng_constant;
    // output s ze  s a po r of 2
    feature_ d >>= N - output_b s;
    u nt64_t mask = (1 << output_b s) - 1;
    return mask & feature_ d;
  }

  u nt64_t  nteger64_mult pl cat ve_hash ng(u nt64_t feature_ d,
                                             nt64_t bucket_ ndex,
                                            u nt64_t output_b s) {
    const u nt64_t hash ng_constant = 14695981039346656039UL;
    const u nt64_t N = 64;
    // hash once to prevent problems from anomalous  nput  d d str but ons
    feature_ d *= hash ng_constant;
    feature_ d += bucket_ ndex;
    // t  hash enables t  follow ng r ght sh ft operat on
    //  w hout los ng t  bucket  nformat on (lo r b s)
    feature_ d *= hash ng_constant;
    // output s ze  s a po r of 2
    feature_ d >>= N - output_b s;
    u nt64_t mask = (1 << output_b s) - 1;
    return mask & feature_ d;
  }

   nt64_t opt on_b s( nt64_t opt ons,  nt64_t h gh,  nt64_t low) {
    opt ons >>= low;
    opt ons &= (1 << (h gh - low + 1)) - 1;
    return opt ons;
  }

  //    s assu d that start_compute and end_compute are val d
  template<typena  T>
  vo d hashD scret zer nfer(Tensor &output_keys,
                            Tensor &output_vals,
                            const Tensor & nput_ ds,
                            const Tensor & nput_vals,
                            const Tensor &b n_vals,
                             nt output_b s,
                            const Map< nt64_t,  nt64_t> & D_to_ ndex,
                             nt64_t start_compute,
                             nt64_t end_compute,
                             nt64_t n_b n,
                             nt64_t opt ons) {
    auto output_keys_data = output_keys.getData< nt64_t>();
    auto output_vals_data = output_vals.getData<T>();

    auto  nput_ ds_data =  nput_ ds.getData< nt64_t>();
    auto  nput_vals_data =  nput_vals.getData<T>();

    auto b n_vals_data = b n_vals.getData<T>();

    // T  funct on po nter  mple ntat on removes t  opt on_b s
    // funct on call (m ght be  nl ned) and correspond ng branch from
    // t  hot loop, but   prevents  nl n ng t se funct ons, so
    // t re w ll be funct on call over ad. Uncerta n wh ch would
    // be faster, test ng needed. Also, code opt m zers do   rd th ngs...
    hash_s gnature hash_fn =  nteger_mult pl cat ve_hash ng;
    sw ch (opt on_b s(opt ons, 4, 2)) {
      case 0:
      hash_fn =  nteger_mult pl cat ve_hash ng;
      break;
      case 1:
      hash_fn =  nteger64_mult pl cat ve_hash ng;
      break;
      default:
      hash_fn =  nteger_mult pl cat ve_hash ng;
    }

    search_ thod<T> search_fn = lo r_bound_search;
    sw ch (opt on_b s(opt ons, 1, 0)) {
      case 0:
      search_fn = lo r_bound_search<T>;
      break;
      case 1:
      search_fn = l near_search<T>;
      break;
      case 2:
      search_fn = upper_bound_search<T>;
      break;
      default:
      search_fn = lo r_bound_search<T>;
    }

    for (u nt64_t   = start_compute;   < end_compute;  ++) {
       nt64_t  d =  nput_ ds_data[ ];
      T val =  nput_vals_data[ ];

      auto  er =  D_to_ ndex.f nd( d);
       f ( er !=  D_to_ ndex.end()) {
         nt64_t feature_ dx =  er->second;
        const T *b n_vals_start = b n_vals_data + feature_ dx * n_b n;
         nt64_t out_b n_ dx = search_fn(b n_vals_start, val, n_b n);
        output_keys_data[ ] = hash_fn( d, out_b n_ dx, output_b s);
        output_vals_data[ ] = 1;
      } else {
        // feature not cal brated
        output_keys_data[ ] =  d & ((1 << output_b s) - 1);
        output_vals_data[ ] = val;
      }
    }
  }

  vo d hashD scret zer nfer(Tensor &output_keys,
                            Tensor &output_vals,
                            const Tensor & nput_ ds,
                            const Tensor & nput_vals,
                             nt n_b n,
                            const Tensor &b n_vals,
                             nt output_b s,
                            const Map< nt64_t,  nt64_t> & D_to_ ndex,
                             nt start_compute,
                             nt end_compute,
                             nt64_t opt ons) {
     f ( nput_ ds.getType() != TWML_TYPE_ NT64) {
      throw twml::Error(TWML_ERR_TYPE, " nput_ ds must be a Long Tensor");
    }

     f (output_keys.getType() != TWML_TYPE_ NT64) {
      throw twml::Error(TWML_ERR_TYPE, "output_keys must be a Long Tensor");
    }

     f ( nput_vals.getType() != b n_vals.getType()) {
      throw twml::Error(TWML_ERR_TYPE,
                "Data type of  nput_vals does not match type of b n_vals");
    }

     f (b n_vals.getNumD ms() != 1) {
      throw twml::Error(TWML_ERR_S ZE,
                "b n_vals must be 1 D  ns onal");
    }

    u nt64_t s ze =  nput_ ds.getD m(0);
     f (end_compute == -1) {
      end_compute = s ze;
    }

     f (start_compute < 0 || start_compute >= s ze) {
      throw twml::Error(TWML_ERR_S ZE,
                "start_compute out of range");
    }

     f (end_compute < -1 || end_compute > s ze) {
      throw twml::Error(TWML_ERR_S ZE,
                "end_compute out of range");
    }

     f (start_compute > end_compute && end_compute != -1) {
      throw twml::Error(TWML_ERR_S ZE,
                "must have start_compute <= end_compute, or end_compute==-1");
    }

     f (output_keys.getStr de(0) != 1 || output_vals.getStr de(0) != 1 ||
         nput_ ds.getStr de(0) != 1 ||  nput_vals.getStr de(0) != 1 ||
        b n_vals.getStr de(0) != 1) {
      throw twml::Error(TWML_ERR_S ZE,
                "All Str des must be 1.");
    }

    sw ch ( nput_vals.getType()) {
    case TWML_TYPE_FLOAT:
      twml::hashD scret zer nfer<float>(output_keys, output_vals,
                   nput_ ds,  nput_vals,
                  b n_vals, output_b s,  D_to_ ndex,
                  start_compute, end_compute, n_b n, opt ons);
      break;
    case TWML_TYPE_DOUBLE:
      twml::hashD scret zer nfer<double>(output_keys, output_vals,
                    nput_ ds,  nput_vals,
                   b n_vals, output_b s,  D_to_ ndex,
                   start_compute, end_compute, n_b n, opt ons);
      break;
    default:
      throw twml::Error(TWML_ERR_TYPE,
        "Unsupported datatype for hashD scret zer nfer");
    }
  }
}  // na space twml
