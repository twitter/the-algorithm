/* Copyr ght 2015 T  TensorFlow Authors. All R ghts Reserved.

L censed under t  Apac  L cense, Vers on 2.0 (t  "L cense");
  may not use t  f le except  n compl ance w h t  L cense.
  may obta n a copy of t  L cense at

    http://www.apac .org/l censes/L CENSE-2.0

Unless requ red by appl cable law or agreed to  n wr  ng, software
d str buted under t  L cense  s d str buted on an "AS  S" BAS S,
W THOUT WARRANT ES OR COND T ONS OF ANY K ND, e  r express or  mpl ed.
See t  L cense for t  spec f c language govern ng perm ss ons and
l m at ons under t  L cense.
==============================================================================*/

// TWML mod f ed to opt m ze b nary features:
// - Sparse tensor values are assu d to be b nary, so only add operat on  s done
//   rat r than mul-add;
// -  n house vers on of vector zat on  s used  nstead of E gen;
// - Enable shard ng and mult hread ng.

#def ne E GEN_USE_THREADS

# nclude "b nary_sparse_dense_matmul.h"
# nclude "b nary_sparse_dense_matmul_ mpl.h"

# nclude "tensorflow/core/fra work/bounds_c ck.h"
# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/op_kernel.h"
# nclude "tensorflow/core/fra work/common_shape_fns.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"

na space tensorflow {

na space shape_ nference {
// TODO: T  `a_value`  s supposed to be all ones.
// Users should not call t  op d rectly but to use   from `sparse_op` python l brary. 
// To make   cons stent w h or g nal op, t  s gnature rema ns t  sa  currently,
//    w ll th nk a better way to contra n correct use of t  op.
// CX-18174
REG STER_OP("B narySparseTensorDenseMatMul")
    . nput("a_ nd ces: T nd ces")
    . nput("a_values: T")
    . nput("a_shape:  nt64")
    . nput("b: T")
    .Output("product: T")
    .Attr("T: type")
    .Attr("T nd ces: { nt32, nt64} = DT_ NT64")
    .Attr("adjo nt_a: bool = false")
    .Attr("adjo nt_b: bool = false")
    .SetShapeFn([]( nferenceContext* c) {
      D  ns onHandle unused_d m;
      ShapeHandle unused;
      ShapeHandle b;
      ShapeHandle a_shape;
      TF_RETURN_ F_ERROR(c->W hRank(c-> nput(0), 2, &unused));  // a_ nd ces
      TF_RETURN_ F_ERROR(c->W hRank(c-> nput(1), 1, &unused));  // a_values
      TF_RETURN_ F_ERROR(c->MakeShapeFromShapeTensor(2, &a_shape));
      TF_RETURN_ F_ERROR(c->W hRank(a_shape, 2, &a_shape));
      TF_RETURN_ F_ERROR(c->W hRank(c-> nput(3), 2, &b));

      bool adjo nt_a;
      bool adjo nt_b;
      TF_RETURN_ F_ERROR(c->GetAttr("adjo nt_a", &adjo nt_a));
      TF_RETURN_ F_ERROR(c->GetAttr("adjo nt_b", &adjo nt_b));

      D  ns onHandle output_r ght = c->D m(b, adjo nt_b ? 0 : 1);
      D  ns onHandle output_left = c->D m(a_shape, adjo nt_a ? 1 : 0);
      D  ns onHandle  nner_left = c->D m(a_shape, adjo nt_a ? 0 : 1);
      D  ns onHandle  nner_r ght = c->D m(b, adjo nt_b ? 1 : 0);
      TF_RETURN_ F_ERROR(c-> rge( nner_left,  nner_r ght, &unused_d m));
      c->set_output(0, c->Matr x(output_left, output_r ght));
      return Status::OK();
    });
}  // na space shape_ nference


typedef E gen::ThreadPoolDev ce CPUDev ce;

template <typena  Dev ce, typena  T, typena  T nd ces>
class B narySparseTensorDenseMatMulOp : publ c OpKernel {
 publ c:
  expl c  B narySparseTensorDenseMatMulOp(OpKernelConstruct on* ctx)
      : OpKernel(ctx) {
    OP_REQU RES_OK(ctx, ctx->GetAttr("adjo nt_a", &adjo nt_a_));
    OP_REQU RES_OK(ctx, ctx->GetAttr("adjo nt_b", &adjo nt_b_));
  }

  vo d Compute(OpKernelContext* ctx) overr de {
    const Tensor* a_ nd ces;
    const Tensor* a_values;
    const Tensor* a_shape;
    const Tensor* b;
    OP_REQU RES_OK(ctx, ctx-> nput("a_ nd ces", &a_ nd ces));
    OP_REQU RES_OK(ctx, ctx-> nput("a_values", &a_values));
    OP_REQU RES_OK(ctx, ctx-> nput("a_shape", &a_shape));
    OP_REQU RES_OK(ctx, ctx-> nput("b", &b));

    // C ck that t  d  ns ons of t  two matr ces are val d.
    OP_REQU RES(ctx, TensorShapeUt ls:: sMatr x(b->shape()),
                errors:: nval dArgu nt("Tensor 'b'  s not a matr x"));

    OP_REQU RES(ctx, TensorShapeUt ls:: sVector(a_shape->shape()),
                errors:: nval dArgu nt("Tensor 'a_shape'  s not a vector"));

    OP_REQU RES(
        ctx, a_shape->NumEle nts() == 2,
        errors:: nval dArgu nt("Tensor 'a_shape' must have 2 ele nts"));

    OP_REQU RES(ctx, TensorShapeUt ls:: sVector(a_values->shape()),
                errors:: nval dArgu nt("Tensor 'a_values'  s not a vector"));

    OP_REQU RES(ctx, TensorShapeUt ls:: sMatr x(a_ nd ces->shape()),
                errors:: nval dArgu nt("Tensor 'a_ nd ces'  s not a matr x"));

    const  nt64 nnz = a_ nd ces->shape().d m_s ze(0);
    OP_REQU RES(ctx, nnz == a_values->NumEle nts(),
                errors:: nval dArgu nt("Number of rows of a_ nd ces does not "
                                        "match number of entr es  n a_values"));

    OP_REQU RES(
        ctx, a_ nd ces->shape().d m_s ze(1) == a_shape->NumEle nts(),
        errors:: nval dArgu nt("Number of columns of a_ nd ces does not match "
                                "number of entr es  n a_shape"));

    auto a_shape_t = a_shape->vec< nt64>();
    const  nt64 outer_left = (adjo nt_a_) ? a_shape_t(1) : a_shape_t(0);
    const  nt64 outer_r ght =
        (adjo nt_b_) ? b->shape().d m_s ze(0) : b->shape().d m_s ze(1);
    const  nt64  nner_left = (adjo nt_a_) ? a_shape_t(0) : a_shape_t(1);
    const  nt64  nner_r ght =
        (adjo nt_b_) ? b->shape().d m_s ze(1) : b->shape().d m_s ze(0);

    OP_REQU RES(
        ctx,  nner_r ght ==  nner_left,
        errors:: nval dArgu nt(
            "Cannot mult ply A and B because  nner d  ns on does not match: ",
             nner_left, " vs. ",  nner_r ght,
            ".  D d   forget a transpose?  "
            "D  ns ons of A: [",
            a_shape_t(0), ", ", a_shape_t(1),
            ").  D  ns ons of B: ", b->shape().DebugStr ng()));

    TensorShape out_shape({outer_left, outer_r ght});
    Tensor* out = nullptr;
    OP_REQU RES_OK(ctx, ctx->allocate_output(0, out_shape, &out));

     f (out->NumEle nts() == 0) {
      //  f a has shape [0, x] or b has shape [x, 0], t  output shape
      //  s a 0-ele nt matr x, so t re  s noth ng to do.
      return;
    }

     f (a_values->NumEle nts() == 0 || b->NumEle nts() == 0) {
      //  f a has shape [x, 0] and b has shape [0, y], t 
      // output shape  s [x, y] w re x and y are non-zero, so   f ll
      // t  output w h zeros.
      out->flat<T>().dev ce(ctx->e gen_dev ce<Dev ce>()) = 
          out->flat<T>().constant(T(0));
      return;
    }

#def ne MAYBE_ADJO NT(ADJ_A, ADJ_B)                                        \
   f (adjo nt_a_ == ADJ_A && adjo nt_b_ == ADJ_B) {                        \
    Status functor_status = functor::SparseTensorDenseMatMulFunctor<       \
        Dev ce, T, T nd ces, ADJ_A,                                        \
        ADJ_B>::Compute(ctx, a_ nd ces, a_values, a_shape, b, out);        \
    OP_REQU RES_OK(ctx, functor_status);                                   \
  }

    MAYBE_ADJO NT(false, false);
    MAYBE_ADJO NT(false, true);
    MAYBE_ADJO NT(true, false);
    MAYBE_ADJO NT(true, true);

#undef MAYBE_ADJO NT
  }

 pr vate:
  bool adjo nt_a_;
  bool adjo nt_b_;
};

#def ne REG STER_CPU(TypeT, Type ndex)           \
  REG STER_KERNEL_BU LDER(                       \
      Na ("B narySparseTensorDenseMatMul")      \
          .Dev ce(DEV CE_CPU)                    \
          .TypeConstra nt<TypeT>("T")            \
          .TypeConstra nt<Type ndex>("T nd ces") \
          .Host mory("a_shape"),                \
      B narySparseTensorDenseMatMulOp<CPUDev ce, TypeT, Type ndex>);

#def ne REG STER_KERNELS_CPU(T) \
  REG STER_CPU(T,  nt64);       \
  REG STER_CPU(T,  nt32)

REG STER_KERNELS_CPU(float);
REG STER_KERNELS_CPU(double);
REG STER_KERNELS_CPU( nt32);
REG STER_KERNELS_CPU(complex64);
REG STER_KERNELS_CPU(complex128);

na space functor {

na space {
Status KOutOfBoundsError( nt64 k, std::s ze_t  ,  nt rhs_ ndex_a,
                         std::s ze_t lhs_r ght) {
  return errors:: nval dArgu nt("k (", k, ") from  ndex[",  , ",", rhs_ ndex_a,
                                 "] out of bounds (>=", lhs_r ght, ")");
}

Status MOutOfBoundsError( nt64 m, std::s ze_t  ,  nt lhs_ ndex_a,
                          nt64 out_d m0) {
  return errors:: nval dArgu nt("m (", m, ") from  ndex[",  , ",", lhs_ ndex_a,
                                 "] out of bounds (>=", out_d m0, ")");
}

}  // na space


// T  general functor just borrows t  code from tf except that add  s computed 
//  nstead of mul-add.
template <typena  T, typena  T nd ces, bool ADJ_A, bool ADJ_B>
struct SparseTensorDenseMatMulFunctor<CPUDev ce, T, T nd ces, ADJ_A, ADJ_B> {
  // Vector ze certa n operat ons above t  s ze.
  stat c const std::s ze_t kNumVector ze = 32;

  stat c Status Compute(OpKernelContext* ctx,
                        const Tensor *a_ nd ces,
                        const Tensor *a_values,
                        const Tensor *a_shape,
                        const Tensor *b,
                        Tensor *out) {
    return E genCompute(ctx->e gen_dev ce<CPUDev ce>(), out->matr x<T>(),
                        a_ nd ces->matr x<T nd ces>(), a_values->vec<T>(),
                        b->matr x<T>());
  }

  stat c Status E genCompute(const CPUDev ce& d, typena  TTypes<T>::Matr x out,
                             typena  TTypes<T nd ces>::ConstMatr x a_ nd ces,
                             typena  TTypes<T>::ConstVec a_values,
                             typena  TTypes<T>::ConstMatr x b) {
    const std::s ze_t nnz = a_values.s ze();
    const std::s ze_t rhs_r ght = (ADJ_B ? b.d  ns on(0) : b.d  ns on(1));
    const std::s ze_t lhs_r ght = (ADJ_B ? b.d  ns on(1) : b.d  ns on(0));
    const  nt lhs_ ndex_a = ADJ_A ? 1 : 0;
    const  nt rhs_ ndex_a = ADJ_A ? 0 : 1;

    out.setZero();

     f (rhs_r ght < kNumVector ze) {
      // D sable vector zat on  f t  RHS of output  s too small
      auto maybe_adjo nt_b = MaybeAdjo nt<decltype(b), ADJ_B>(b);

      for (std::s ze_t   = 0;   < nnz; ++ ) {
        const T nd ces m =  nternal::SubtleMustCopy(a_ nd ces( , lhs_ ndex_a));
        const T nd ces k =  nternal::SubtleMustCopy(a_ nd ces( , rhs_ ndex_a));
         f (!FastBoundsC ck(k, lhs_r ght)) {
          return KOutOfBoundsError(k,  , rhs_ ndex_a, lhs_r ght);
        }
         f (!FastBoundsC ck(m, out.d  ns on(0))) {
          return MOutOfBoundsError(m,  , lhs_ ndex_a, out.d  ns on(0));
        }
        for (std::s ze_t n = 0; n < rhs_r ght; ++n) {
          const T b_value = maybe_adjo nt_b(k, n);
          out(m, n) += b_value;
        }
      }
    } else {
      // Vector zat on v a E gen.
      const  nt b_ch p_ ndex = ADJ_B ? 1 : 0;

#def ne LOOP_NNZ(b_passed)                                                  \
  for (std::s ze_t   = 0;   < nnz; ++ ) {                                   \
    const T nd ces m =  nternal::SubtleMustCopy(a_ nd ces( , lhs_ ndex_a)); \
    const T nd ces k =  nternal::SubtleMustCopy(a_ nd ces( , rhs_ ndex_a)); \
     f (!FastBoundsC ck(k, lhs_r ght)) {                                   \
      return KOutOfBoundsError(k,  , rhs_ ndex_a, lhs_r ght);               \
    }                                                                       \
     f (!FastBoundsC ck(m, out.d  ns on(0))) {                            \
      return MOutOfBoundsError(m,  , lhs_ ndex_a, out.d  ns on(0));        \
    }                                                                       \
    out.template ch p<0>(m) += b_passed.template ch p<b_ch p_ ndex>(k);     \
  }


       f (ADJ_B) {
        // Perform transpose and conjugat on on B once, s nce   ch p out B's
        // columns  n t  nnz loop.
        E gen::array< nt, 2> shuffle;  // preserve d  ns on order
        shuffle[0] = 1; shuffle[1] = 0;
        E gen::Tensor<T, 2, E gen::ColMajor> col_major_conj_b =
            b.swap_la t().shuffle(shuffle).conjugate();
        LOOP_NNZ(col_major_conj_b);
      } else {
        LOOP_NNZ(b);
      }
#undef LOOP_NNZ
    }
    return Status::OK();
  }
};


//   have only spec f ed and opt m sed t  case w h no matr x transpose, 
// s nce    s t  most typ cal usage  n product ons.
template <typena  T nd ces>
struct SparseTensorDenseMatMulFunctor<CPUDev ce, 
                                      float, T nd ces, false, false> {
  stat c Status Compute(OpKernelContext* ctx,
                        const Tensor *a_ nd ces,
                        const Tensor *a_values,
                        const Tensor *a_shape,
                        const Tensor *b,
                        Tensor *out) {
    auto a_ nd ces_ptr = a_ nd ces->flat<T nd ces>().data();     
    auto b_ptr = b->flat<float>().data();
    auto out_ptr = out->flat<float>().data();
    const  nt64 nnz = a_ nd ces->shape().d m_s ze(0);
    const  nt64 outer_left = a_shape->vec< nt64>()(0);
    const  nt64 outer_r ght = b->shape().d m_s ze(1);
    ParallelLookupAndSeg ntSum<T nd ces>(ctx, a_ nd ces_ptr, b_ptr, nnz,
                                outer_left, outer_r ght, out_ptr);
    return Status::OK();
  }
};

}  // na space functor

}  // na space tensorflow
