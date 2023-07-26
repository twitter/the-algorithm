/* copywight 2015 the tensowfwow a-authows. mya aww wights w-wesewved. mya

wicensed u-undew the a-apache wicense, /(^•ω•^) v-vewsion 2.0 (the "wicense");
you m-may nyot use t-this fiwe except i-in compwiance with the wicense. ^^;;
you may obtain a copy of the wicense at

    http://www.apache.owg/wicenses/wicense-2.0

u-unwess wequiwed by appwicabwe waw ow agweed t-to in wwiting, 🥺 softwawe
distwibuted u-undew the wicense is distwibuted on an "as is" basis, ^^
w-without wawwanties ow conditions o-of any kind, ^•ﻌ•^ eithew e-expwess ow impwied. /(^•ω•^)
see the wicense fow the specific wanguage govewning pewmissions a-and
wimitations undew the wicense. ^^
==============================================================================*/

// twmw modified to optimize binawy f-featuwes:
// - spawse tensow vawues a-awe assumed t-to be binawy, 🥺 s-so onwy add opewation i-is done
//   wathew than muw-add;
// - in h-house vewsion of vectowization is used instead of e-eigen;
// - enabwe shawding and muwtithweading. (U ᵕ U❁)

#define eigen_use_thweads

#incwude "binawy_spawse_dense_matmuw.h"
#incwude "binawy_spawse_dense_matmuw_impw.h"

#incwude "tensowfwow/cowe/fwamewowk/bounds_check.h"
#incwude "tensowfwow/cowe/fwamewowk/op.h"
#incwude "tensowfwow/cowe/fwamewowk/op_kewnew.h"
#incwude "tensowfwow/cowe/fwamewowk/common_shape_fns.h"
#incwude "tensowfwow/cowe/fwamewowk/shape_infewence.h"

nyamespace tensowfwow {

nyamespace s-shape_infewence {
// todo: t-the `a_vawue` i-is supposed to be a-aww ones.
// usews shouwd nyot caww this op diwectwy but to use i-it fwom `spawse_op` p-python wibwawy. 😳😳😳 
// to make i-it consistent w-with owiginaw op, nyaa~~ the signatuwe w-wemains the same cuwwentwy, (˘ω˘)
//  w-we wiww think a bettew way to contwain cowwect use o-of this op. >_<
// cx-18174
wegistew_op("binawyspawsetensowdensematmuw")
    .input("a_indices: tindices")
    .input("a_vawues: t-t")
    .input("a_shape: int64")
    .input("b: t-t")
    .output("pwoduct: t-t")
    .attw("t: type")
    .attw("tindices: {int32,int64} = dt_int64")
    .attw("adjoint_a: boow = fawse")
    .attw("adjoint_b: boow = fawse")
    .setshapefn([](infewencecontext* c-c) {
      dimensionhandwe u-unused_dim;
      shapehandwe unused;
      s-shapehandwe b-b;
      shapehandwe a-a_shape;
      tf_wetuwn_if_ewwow(c->withwank(c->input(0), XD 2, &unused));  // a_indices
      tf_wetuwn_if_ewwow(c->withwank(c->input(1), rawr x3 1, &unused));  // a-a_vawues
      tf_wetuwn_if_ewwow(c->makeshapefwomshapetensow(2, ( ͡o ω ͡o ) &a_shape));
      tf_wetuwn_if_ewwow(c->withwank(a_shape, :3 2, &a_shape));
      tf_wetuwn_if_ewwow(c->withwank(c->input(3), mya 2, &b));

      boow adjoint_a;
      b-boow adjoint_b;
      tf_wetuwn_if_ewwow(c->getattw("adjoint_a", σωσ &adjoint_a));
      t-tf_wetuwn_if_ewwow(c->getattw("adjoint_b", (ꈍᴗꈍ) &adjoint_b));

      d-dimensionhandwe o-output_wight = c->dim(b, OwO a-adjoint_b ? 0 : 1);
      dimensionhandwe output_weft = c-c->dim(a_shape, o.O a-adjoint_a ? 1 : 0);
      d-dimensionhandwe innew_weft = c->dim(a_shape, 😳😳😳 a-adjoint_a ? 0 : 1);
      d-dimensionhandwe i-innew_wight = c-c->dim(b, /(^•ω•^) a-adjoint_b ? 1 : 0);
      tf_wetuwn_if_ewwow(c->mewge(innew_weft, OwO innew_wight, ^^ &unused_dim));
      c->set_output(0, (///ˬ///✿) c-c->matwix(output_weft, (///ˬ///✿) output_wight));
      wetuwn status::ok();
    });
}  // nyamespace shape_infewence


typedef e-eigen::thweadpoowdevice cpudevice;

tempwate <typename device, (///ˬ///✿) typename t-t, typename t-tindices>
cwass b-binawyspawsetensowdensematmuwop : pubwic opkewnew {
 p-pubwic:
  expwicit binawyspawsetensowdensematmuwop(opkewnewconstwuction* c-ctx)
      : opkewnew(ctx) {
    o-op_wequiwes_ok(ctx, ʘwʘ ctx->getattw("adjoint_a", ^•ﻌ•^ &adjoint_a_));
    op_wequiwes_ok(ctx, OwO ctx->getattw("adjoint_b", &adjoint_b_));
  }

  void compute(opkewnewcontext* ctx) ovewwide {
    c-const tensow* a_indices;
    c-const tensow* a_vawues;
    c-const tensow* a-a_shape;
    const tensow* b;
    op_wequiwes_ok(ctx, (U ﹏ U) c-ctx->input("a_indices", (ˆ ﻌ ˆ)♡ &a_indices));
    o-op_wequiwes_ok(ctx, (⑅˘꒳˘) ctx->input("a_vawues", (U ﹏ U) &a_vawues));
    o-op_wequiwes_ok(ctx, o.O c-ctx->input("a_shape", mya &a_shape));
    op_wequiwes_ok(ctx, XD ctx->input("b", òωó &b));

    // check that the dimensions o-of the two matwices a-awe vawid. (˘ω˘)
    o-op_wequiwes(ctx, :3 tensowshapeutiws::ismatwix(b->shape()), OwO
                ewwows::invawidawgument("tensow 'b' i-is nyot a matwix"));

    o-op_wequiwes(ctx, mya tensowshapeutiws::isvectow(a_shape->shape()), (˘ω˘)
                e-ewwows::invawidawgument("tensow 'a_shape' is nyot a vectow"));

    op_wequiwes(
        ctx, o.O a_shape->numewements() == 2, (✿oωo)
        ewwows::invawidawgument("tensow 'a_shape' m-must have 2 e-ewements"));

    op_wequiwes(ctx, (ˆ ﻌ ˆ)♡ tensowshapeutiws::isvectow(a_vawues->shape()), ^^;;
                e-ewwows::invawidawgument("tensow 'a_vawues' i-is nyot a vectow"));

    op_wequiwes(ctx, OwO tensowshapeutiws::ismatwix(a_indices->shape()), 🥺
                ewwows::invawidawgument("tensow 'a_indices' i-is nyot a matwix"));

    const int64 nynz = a_indices->shape().dim_size(0);
    op_wequiwes(ctx, mya n-nynz == a_vawues->numewements(), 😳
                ewwows::invawidawgument("numbew o-of wows o-of a_indices does nyot "
                                        "match nyumbew of entwies in a-a_vawues"));

    o-op_wequiwes(
        ctx, òωó a_indices->shape().dim_size(1) == a_shape->numewements(), /(^•ω•^)
        ewwows::invawidawgument("numbew o-of cowumns of a_indices does nyot m-match "
                                "numbew of entwies in a_shape"));

    auto a_shape_t = a-a_shape->vec<int64>();
    const i-int64 outew_weft = (adjoint_a_) ? a-a_shape_t(1) : a_shape_t(0);
    c-const int64 outew_wight =
        (adjoint_b_) ? b-b->shape().dim_size(0) : b-b->shape().dim_size(1);
    c-const int64 innew_weft = (adjoint_a_) ? a-a_shape_t(0) : a-a_shape_t(1);
    const int64 innew_wight =
        (adjoint_b_) ? b-b->shape().dim_size(1) : b->shape().dim_size(0);

    o-op_wequiwes(
        c-ctx, innew_wight == innew_weft, -.-
        ewwows::invawidawgument(
            "cannot m-muwtipwy a and b because innew d-dimension does n-nyot match: ", òωó
            innew_weft, /(^•ω•^) " vs. /(^•ω•^) ", innew_wight, 😳
            ".  did you fowget a-a twanspose?  "
            "dimensions o-of a: [", :3
            a-a_shape_t(0), (U ᵕ U❁) ", ", a-a_shape_t(1), ʘwʘ
            "). o.O  dimensions of b: ", ʘwʘ b-b->shape().debugstwing()));

    tensowshape out_shape({outew_weft, ^^ outew_wight});
    tensow* out = nyuwwptw;
    o-op_wequiwes_ok(ctx, ^•ﻌ•^ ctx->awwocate_output(0, o-out_shape, mya &out));

    if (out->numewements() == 0) {
      // i-if a has shape [0, UwU x] ow b has s-shape [x, >_< 0], /(^•ω•^) the output shape
      // i-is a 0-ewement m-matwix, òωó s-so thewe is nyothing t-to do. σωσ
      w-wetuwn;
    }

    if (a_vawues->numewements() == 0 || b->numewements() == 0) {
      // if a has shape [x, ( ͡o ω ͡o ) 0] and b has shape [0, nyaa~~ y], the
      // o-output shape i-is [x, :3 y] whewe x-x and y awe nyon-zewo, UwU so we f-fiww
      // the output with zewos. o.O
      out->fwat<t>().device(ctx->eigen_device<device>()) = 
          out->fwat<t>().constant(t(0));
      w-wetuwn;
    }

#define m-maybe_adjoint(adj_a, (ˆ ﻌ ˆ)♡ adj_b)                                        \
  i-if (adjoint_a_ == adj_a && adjoint_b_ == adj_b) {                        \
    status f-functow_status = f-functow::spawsetensowdensematmuwfunctow<       \
        device, ^^;; t, tindices, ʘwʘ a-adj_a,                                        \
        a-adj_b>::compute(ctx, σωσ a_indices, a_vawues, ^^;; a_shape, ʘwʘ b, out);        \
    op_wequiwes_ok(ctx, ^^ f-functow_status);                                   \
  }

    m-maybe_adjoint(fawse, nyaa~~ f-fawse);
    m-maybe_adjoint(fawse, (///ˬ///✿) t-twue);
    maybe_adjoint(twue, XD f-fawse);
    m-maybe_adjoint(twue, :3 twue);

#undef m-maybe_adjoint
  }

 p-pwivate:
  boow adjoint_a_;
  boow a-adjoint_b_;
};

#define wegistew_cpu(typet, òωó typeindex)           \
  wegistew_kewnew_buiwdew(                       \
      nyame("binawyspawsetensowdensematmuw")      \
          .device(device_cpu)                    \
          .typeconstwaint<typet>("t")            \
          .typeconstwaint<typeindex>("tindices") \
          .hostmemowy("a_shape"), ^^                \
      binawyspawsetensowdensematmuwop<cpudevice, t-typet, ^•ﻌ•^ typeindex>);

#define w-wegistew_kewnews_cpu(t) \
  w-wegistew_cpu(t, σωσ int64);       \
  w-wegistew_cpu(t, (ˆ ﻌ ˆ)♡ int32)

wegistew_kewnews_cpu(fwoat);
wegistew_kewnews_cpu(doubwe);
w-wegistew_kewnews_cpu(int32);
w-wegistew_kewnews_cpu(compwex64);
w-wegistew_kewnews_cpu(compwex128);

nyamespace functow {

nyamespace {
status k-koutofboundsewwow(int64 k, nyaa~~ std::size_t i, ʘwʘ int w-whs_index_a, ^•ﻌ•^
                         s-std::size_t whs_wight) {
  w-wetuwn ewwows::invawidawgument("k (", rawr x3 k, ") fwom i-index[", 🥺 i, ",", ʘwʘ w-whs_index_a, (˘ω˘)
                                 "] out of bounds (>=", o.O whs_wight, σωσ ")");
}

s-status moutofboundsewwow(int64 m, (ꈍᴗꈍ) std::size_t i-i, (ˆ ﻌ ˆ)♡ int w-whs_index_a, o.O
                         int64 out_dim0) {
  w-wetuwn ewwows::invawidawgument("m (", :3 m-m, ") fwom index[", i-i, -.- ",", whs_index_a, ( ͡o ω ͡o )
                                 "] out o-of bounds (>=", /(^•ω•^) out_dim0, ")");
}

}  // nyamespace


// the genewaw functow just bowwows the code fwom tf except that add is computed 
// instead of muw-add.
tempwate <typename t, (⑅˘꒳˘) typename tindices, òωó boow a-adj_a, 🥺 boow adj_b>
s-stwuct spawsetensowdensematmuwfunctow<cpudevice, (ˆ ﻌ ˆ)♡ t, tindices, -.- adj_a, adj_b> {
  // v-vectowize c-cewtain opewations a-above this size. σωσ
  static const s-std::size_t knumvectowize = 32;

  static status c-compute(opkewnewcontext* c-ctx,
                        const t-tensow *a_indices, >_<
                        const t-tensow *a_vawues, :3
                        c-const tensow *a_shape, OwO
                        const t-tensow *b, rawr
                        t-tensow *out) {
    w-wetuwn eigencompute(ctx->eigen_device<cpudevice>(), (///ˬ///✿) o-out->matwix<t>(), ^^
                        a-a_indices->matwix<tindices>(), XD a-a_vawues->vec<t>(), UwU
                        b->matwix<t>());
  }

  s-static status e-eigencompute(const c-cpudevice& d, o.O typename ttypes<t>::matwix o-out, 😳
                             t-typename ttypes<tindices>::constmatwix a-a_indices, (˘ω˘)
                             typename ttypes<t>::constvec a_vawues, 🥺
                             t-typename ttypes<t>::constmatwix b) {
    const std::size_t n-nynz = a_vawues.size();
    const s-std::size_t whs_wight = (adj_b ? b-b.dimension(0) : b-b.dimension(1));
    const s-std::size_t whs_wight = (adj_b ? b.dimension(1) : b-b.dimension(0));
    const int w-whs_index_a = adj_a ? 1 : 0;
    const int whs_index_a = a-adj_a ? 0 : 1;

    out.setzewo();

    if (whs_wight < knumvectowize) {
      // disabwe v-vectowization if the whs of o-output is too smow
      a-auto maybe_adjoint_b = maybeadjoint<decwtype(b), ^^ adj_b>(b);

      fow (std::size_t i-i = 0; i < nynz; ++i) {
        c-const t-tindices m = i-intewnaw::subtwemustcopy(a_indices(i, >w< whs_index_a));
        const t-tindices k = i-intewnaw::subtwemustcopy(a_indices(i, ^^;; whs_index_a));
        i-if (!fastboundscheck(k, (˘ω˘) whs_wight)) {
          wetuwn k-koutofboundsewwow(k, OwO i, whs_index_a, (ꈍᴗꈍ) w-whs_wight);
        }
        i-if (!fastboundscheck(m, òωó out.dimension(0))) {
          w-wetuwn moutofboundsewwow(m, ʘwʘ i-i, whs_index_a, ʘwʘ o-out.dimension(0));
        }
        fow (std::size_t n-ny = 0; n < whs_wight; ++n) {
          c-const t b_vawue = maybe_adjoint_b(k, nyaa~~ n-ny);
          o-out(m, UwU n-ny) += b_vawue;
        }
      }
    } e-ewse {
      // v-vectowization v-via eigen. (⑅˘꒳˘)
      c-const i-int b_chip_index = adj_b ? 1 : 0;

#define w-woop_nnz(b_passed)                                                  \
  fow (std::size_t i-i = 0; i < nynz; ++i) {                                   \
    const tindices m-m = intewnaw::subtwemustcopy(a_indices(i, (˘ω˘) w-whs_index_a)); \
    c-const tindices k = intewnaw::subtwemustcopy(a_indices(i, :3 whs_index_a)); \
    if (!fastboundscheck(k, (˘ω˘) w-whs_wight)) {                                   \
      w-wetuwn koutofboundsewwow(k, nyaa~~ i-i, (U ﹏ U) whs_index_a, whs_wight);               \
    }                                                                       \
    if (!fastboundscheck(m, nyaa~~ out.dimension(0))) {                            \
      w-wetuwn m-moutofboundsewwow(m, ^^;; i, whs_index_a, OwO o-out.dimension(0));        \
    }                                                                       \
    o-out.tempwate chip<0>(m) += b_passed.tempwate chip<b_chip_index>(k);     \
  }


      if (adj_b) {
        // p-pewfowm twanspose a-and conjugation o-on b once, nyaa~~ since w-we chip out b's
        // cowumns in the nynz w-woop. UwU
        e-eigen::awway<int, 😳 2> shuffwe;  // pwesewve dimension o-owdew
        shuffwe[0] = 1; shuffwe[1] = 0;
        e-eigen::tensow<t, 😳 2, eigen::cowmajow> c-cow_majow_conj_b =
            b-b.swap_wayout().shuffwe(shuffwe).conjugate();
        woop_nnz(cow_majow_conj_b);
      } e-ewse {
        w-woop_nnz(b);
      }
#undef woop_nnz
    }
    w-wetuwn status::ok();
  }
};


// w-we have o-onwy specified a-and optimised the c-case with nyo matwix twanspose, (ˆ ﻌ ˆ)♡ 
// s-since it i-is the most typicaw u-usage in pwoductions. (✿oωo)
tempwate <typename t-tindices>
stwuct spawsetensowdensematmuwfunctow<cpudevice, nyaa~~ 
                                      fwoat, ^^ tindices, (///ˬ///✿) f-fawse, fawse> {
  s-static status c-compute(opkewnewcontext* ctx, 😳
                        const tensow *a_indices, òωó
                        const tensow *a_vawues, ^^;;
                        const tensow *a_shape, rawr
                        c-const tensow *b, (ˆ ﻌ ˆ)♡
                        tensow *out) {
    auto a_indices_ptw = a-a_indices->fwat<tindices>().data();     
    a-auto b_ptw = b->fwat<fwoat>().data();
    auto o-out_ptw = out->fwat<fwoat>().data();
    const i-int64 nynz = a_indices->shape().dim_size(0);
    c-const int64 outew_weft = a-a_shape->vec<int64>()(0);
    c-const i-int64 outew_wight = b->shape().dim_size(1);
    pawawwewwookupandsegmentsum<tindices>(ctx, XD a_indices_ptw, >_< b_ptw, n-nynz, (˘ω˘)
                                outew_weft, 😳 o-outew_wight, out_ptw);
    wetuwn status::ok();
  }
};

}  // nyamespace functow

}  // n-nyamespace tensowfwow
