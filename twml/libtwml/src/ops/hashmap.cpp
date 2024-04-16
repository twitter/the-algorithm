# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <twml.h>

# nclude <mutex>

us ng na space tensorflow;

REG STER_OP("Hashmap")
. nput("keys:  nt64")
. nput("hash_keys:  nt64")
. nput("hash_values:  nt64")
.Output("values:  nt64")
.Output("mask:  nt8")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    // TODO: c ck  f t  s zes are d fferent  n t   nput
    c->set_output(0, c-> nput(0));
    c->set_output(1, c-> nput(0));
    return Status::OK();
  });


class Hashmap : publ c OpKernel {
 pr vate:
  twml::HashMap hmap;
  std::once_flag flag;

 publ c:
  expl c  Hashmap(OpKernelConstruct on* context) : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    try {
      // Qu ck hack
      const Tensor& keys = context-> nput(0);

      std::call_once(t ->flag, [t , context](){
          const Tensor& hash_keys = context-> nput(1);
          const Tensor& hash_values = context-> nput(2);
          const auto hash_keys_flat = hash_keys.flat< nt64>();
          const auto hash_values_flat = hash_values.flat< nt64>();
          const  nt64 N = hash_keys_flat.s ze();

          for ( nt64   = 0;   < N;  ++) {
            hmap. nsert(hash_keys_flat( ), hash_values_flat( ));
          }
        });

      Tensor* values = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(0, keys.shape(),
                                                       &values));

      Tensor* mask = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(1, keys.shape(),
                                                       &mask));

      // copy t  values w hout shar ng a storage
      values->flat< nt64>() = keys.flat< nt64>();

      auto keys_flat = keys.flat< nt64>();
      auto values_flat = values->flat< nt64>();
      auto mask_flat = mask->flat< nt8>();

      // TODO: use twml tensor
      const  nt64 N = keys_flat.s ze();
      for ( nt64   = 0;   < N;  ++) {
        // values_flat( ), keys_flat( ) return references to tensorflow:: nt64.
        // Us ng t m  n hmap.get() was caus ng  ssues because of automat c cast ng.
         nt64_t val = values_flat( );
         nt64_t key = keys_flat( );
        mask_flat( ) = hmap.get(val, key);
        values_flat( ) = val;
      }
    }  catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_KERNEL_BU LDER(
  Na ("Hashmap")
  .Dev ce(DEV CE_CPU),
  Hashmap);
