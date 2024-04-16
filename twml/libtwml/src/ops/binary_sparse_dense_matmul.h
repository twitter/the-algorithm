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

// TWML mod f ed to opt m ze b nary features 
# fndef TENSORFLOW_CORE_KERNELS_B NARY_SPARSE_TENSOR_DENSE_MATMUL_OP_H_
#def ne TENSORFLOW_CORE_KERNELS_B NARY_SPARSE_TENSOR_DENSE_MATMUL_OP_H_

# nclude "th rd_party/e gen3/unsupported/E gen/CXX11/Tensor"
# nclude "tensorflow/core/fra work/tensor_types.h"
# nclude "tensorflow/core/fra work/types.h"
# nclude "tensorflow/core/l b/core/errors.h"

na space tensorflow {

na space functor {

template <typena  Dev ce, typena  T, typena  T nd ces, bool ADJ_A,
          bool ADJ_B>
struct SparseTensorDenseMatMulFunctor {
  stat c E GEN_ALWAYS_ NL NE Status Compute(
      const Dev ce& d, typena  TTypes<T>::Matr x out,
      typena  TTypes<T nd ces>::ConstMatr x a_ nd ces,
      typena  TTypes<T>::ConstVec a_values, typena  TTypes<T>::ConstMatr x b);
};

template <typena  MATR X, bool ADJ>
class MaybeAdjo nt;

template <typena  MATR X>
class MaybeAdjo nt<MATR X, false> {
 publ c:
  E GEN_DEV CE_FUNC E GEN_STRONG_ NL NE MaybeAdjo nt(MATR X m) : m_(m) {}
  E GEN_DEV CE_FUNC E GEN_STRONG_ NL NE typena  MATR X::Scalar operator()(
      const typena  MATR X:: ndex  , const typena  MATR X:: ndex j) const {
    return m_( , j);
  }

 pr vate:
  const MATR X m_;
};

template <typena  T>
E GEN_DEV CE_FUNC E GEN_STRONG_ NL NE T MaybeConj(T v) {
  return v;
}

template <typena  MATR X>
class MaybeAdjo nt<MATR X, true> {
 publ c:
  E GEN_DEV CE_FUNC E GEN_STRONG_ NL NE MaybeAdjo nt(MATR X m) : m_(m) {}
  E GEN_DEV CE_FUNC E GEN_STRONG_ NL NE typena  MATR X::Scalar operator()(
      const typena  MATR X:: ndex  , const typena  MATR X:: ndex j) const {
    return E gen::nu xt::conj(m_(j,  ));
  }

 pr vate:
  const MATR X m_;
};

}  // end na space functor
}  // end na space tensorflow

#end f  // TENSORFLOW_CORE_KERNELS_B NARY_SPARSE_TENSOR_DENSE_MATMUL_OP_H_
