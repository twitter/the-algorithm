# fndef TENSORFLOW_CORE_KERNELS_B NARY_SPARSE_TENSOR_DENSE_MATMUL_ MPL_H_
#def ne TENSORFLOW_CORE_KERNELS_B NARY_SPARSE_TENSOR_DENSE_MATMUL_ MPL_H_

# nclude <atom c>

# nclude "tensorflow/core/fra work/op_kernel.h"
# nclude "tensorflow/core/l b/core/block ng_counter.h"
# nclude "tensorflow/core/l b/core/threadpool.h"

na space tensorflow {
na space functor {

// `Conservat veShard`  s adopted rat r than `Shard`  n tensorflow because t 
// or g nal `Shard` may generate number of shards more than t  number of
// threads, wh ch  s not  deal for t  case, as   may cause too much over ad.
stat c vo d Conservat veShard( nt max_parallel sm, thread::ThreadPool *workers,
                               nt64 total,  nt64 cost_per_un ,
                              std::funct on<vo d( nt64,  nt64)> work) {
   f (total == 0) {
    return;
  }
  max_parallel sm = std::m n(max_parallel sm, workers->NumThreads());
   f (max_parallel sm <= 1) {
    // Just  nl ne t  whole work s nce   only have 1 thread (core).
    work(0, total);
    return;
  }
  cost_per_un  = std::max(1LL, cost_per_un );
  //   shard [0, total)  nto "num_shards" shards.
  //   1 <= num_shards <= num worker threads
  //
  //  f total * cost_per_un   s small,    s not worth shard too
  // much. Let us assu  each cost un   s 1ns, kM nCostPerShard=10000
  //  s 10us.
  stat c const  nt64 kM nCostPerShard = 10000;
  const  nt num_shards =
      std::max< nt>(1, std::m n(stat c_cast< nt64>(max_parallel sm),
                                total * cost_per_un  / kM nCostPerShard));

  // Each shard conta ns up to "block_s ze" un s. [0, total)  s sharded
  //  nto:
  //   [0, block_s ze), [block_s ze, 2*block_s ze), ...
  // T  1st shard  s done by t  caller thread and t  ot r shards
  // are d spatc d to t  worker threads. T  last shard may be smaller than
  // block_s ze.
  const  nt64 block_s ze = (total + num_shards - 1) / num_shards;
   f (block_s ze >= total) {
    work(0, total);
    return;
  }
  const  nt num_shards_used = (total + block_s ze - 1) / block_s ze;
  Block ngCounter counter(num_shards_used - 1);
  for ( nt64 start = block_s ze; start < total; start += block_s ze) {
    auto l m  = std::m n(start + block_s ze, total);
    workers->Sc dule([&work, &counter, start, l m ]() {
      work(start, l m );        // Compute t  shard.
      counter.Decre ntCount();  // T  shard  s done.
    });
  }

  //  nl ne execute t  1st shard.
  work(0, std::m n(block_s ze, total));
  counter.Wa ();
}

stat c  nl ne vo d VectorSum(float *a, const float *b,  nt n) {
  for ( nt   = 0;   < n; ++ ) {
    a[ ] += b[ ];
  }
}

// T  func  s to vector ze t  computat on of seg nt sum.
template<typena  T nd ces>
stat c vo d LookupAndSeg ntSum(const T nd ces *a_ nd ces, const float *b,
                                 nt nnz,  nt outer_r ght, float *output) {
  for (std::s ze_t   = 0;   < nnz; ++ ) {
    const T nd ces m = a_ nd ces[  * 2];
    const T nd ces k = a_ nd ces[  * 2 + 1];
    auto output_row_m = output + m * outer_r ght;
    auto b_row_k = b + k * outer_r ght;
    VectorSum(output_row_m, b_row_k, outer_r ght);
  }
}

// T  func enables shard ng and mult hread ng,   co s w h an over ad of
// dupl cat ng output buffer to ach eve lock free output. So t re should not
// be too many threads.
template<typena  T nd ces>
stat c vo d ParallelLookupAndSeg ntSum(OpKernelContext *ctx,
                                        const T nd ces *a_ nd ces,
                                        const float *b,  nt nnz,  nt outer_left,
                                         nt outer_r ght, float *output) {
  auto worker_threads = *(ctx->dev ce()->tensorflow_cpu_worker_threads());
   nt out_s ze = outer_left * outer_r ght;
   f (worker_threads.num_threads <= 1) {
     mset(output, 0, out_s ze * s zeof(float));
    LookupAndSeg ntSum<T nd ces>(a_ nd ces, b, 
                                  nnz, outer_r ght,
                                  output);
    return;
  }

  // t   s to make buffer al gn w h kAllocatorAl gn nt
   nt padded_out_s ze = (out_s ze + (Allocator::kAllocatorAl gn nt - 1)) &
                        ~(Allocator::kAllocatorAl gn nt - 1);
  std::s ze_t num_bytes =
      (worker_threads.num_threads - 1) * padded_out_s ze * s zeof(float);
  auto buffer = std::un que_ptr<float>(re nterpret_cast<float *>(
      port::Al gnedMalloc(num_bytes, Allocator::kAllocatorAl gn nt)));
  float *temp_out = buffer.get();

  std::atom c< nt> thread_ ndex(0);

  auto task = [&]( nt64 start,  nt64 l m ) {
     nt local_thread_ ndex = thread_ ndex++;
    float *buf_ptr = nullptr;
     f (local_thread_ ndex == 0) {
      buf_ptr = output;
    } else {
      buf_ptr = temp_out + (local_thread_ ndex - 1) * padded_out_s ze;
    }
     mset(buf_ptr, 0, out_s ze * s zeof(float));

    LookupAndSeg ntSum<T nd ces>(a_ nd ces + start * 2, b, 
                                  l m  - start, outer_r ght,
                                  buf_ptr);
  };

   nt cost_per_un  = outer_r ght;

  //   don't use tensorflow shard func as tf may create more shards than
  // number of threads.
  Conservat veShard(worker_threads.num_threads, worker_threads.workers, nnz,
                    stat c_cast< nt64>(cost_per_un ), task);

  for ( nt   = 1;   < thread_ ndex; ++ ) {
    VectorSum(output, temp_out + (  - 1) * padded_out_s ze, out_s ze);
  }
}

}  // na space functor

}  // na space tensorflow

#end f  // TENSORFLOW_CORE_KERNELS_B NARY_SPARSE_TENSOR_DENSE_MATMUL_ MPL_H_