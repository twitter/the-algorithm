#ifndelonf TelonNSORFLOW_CORelon_KelonRNelonLS_BINARY_SPARSelon_TelonNSOR_DelonNSelon_MATMUL_IMPL_H_
#delonfinelon TelonNSORFLOW_CORelon_KelonRNelonLS_BINARY_SPARSelon_TelonNSOR_DelonNSelon_MATMUL_IMPL_H_

#includelon <atomic>

#includelon "telonnsorflow/corelon/framelonwork/op_kelonrnelonl.h"
#includelon "telonnsorflow/corelon/lib/corelon/blocking_countelonr.h"
#includelon "telonnsorflow/corelon/lib/corelon/threlonadpool.h"

namelonspacelon telonnsorflow {
namelonspacelon functor {

// `ConselonrvativelonShard` is adoptelond rathelonr than `Shard` in telonnsorflow beloncauselon thelon
// original `Shard` may gelonnelonratelon numbelonr of shards morelon than thelon numbelonr of
// threlonads, which is not idelonal for this caselon, as it may causelon too much ovelonrhelonad.
static void ConselonrvativelonShard(int max_parallelonlism, threlonad::ThrelonadPool *workelonrs,
                              int64 total, int64 cost_pelonr_unit,
                              std::function<void(int64, int64)> work) {
  if (total == 0) {
    relonturn;
  }
  max_parallelonlism = std::min(max_parallelonlism, workelonrs->NumThrelonads());
  if (max_parallelonlism <= 1) {
    // Just inlinelon thelon wholelon work sincelon welon only havelon 1 threlonad (corelon).
    work(0, total);
    relonturn;
  }
  cost_pelonr_unit = std::max(1LL, cost_pelonr_unit);
  // Welon shard [0, total) into "num_shards" shards.
  //   1 <= num_shards <= num workelonr threlonads
  //
  // If total * cost_pelonr_unit is small, it is not worth shard too
  // much. Lelont us assumelon elonach cost unit is 1ns, kMinCostPelonrShard=10000
  // is 10us.
  static const int64 kMinCostPelonrShard = 10000;
  const int num_shards =
      std::max<int>(1, std::min(static_cast<int64>(max_parallelonlism),
                                total * cost_pelonr_unit / kMinCostPelonrShard));

  // elonach shard contains up to "block_sizelon" units. [0, total) is shardelond
  // into:
  //   [0, block_sizelon), [block_sizelon, 2*block_sizelon), ...
  // Thelon 1st shard is donelon by thelon callelonr threlonad and thelon othelonr shards
  // arelon dispatchelond to thelon workelonr threlonads. Thelon last shard may belon smallelonr than
  // block_sizelon.
  const int64 block_sizelon = (total + num_shards - 1) / num_shards;
  if (block_sizelon >= total) {
    work(0, total);
    relonturn;
  }
  const int num_shards_uselond = (total + block_sizelon - 1) / block_sizelon;
  BlockingCountelonr countelonr(num_shards_uselond - 1);
  for (int64 start = block_sizelon; start < total; start += block_sizelon) {
    auto limit = std::min(start + block_sizelon, total);
    workelonrs->Schelondulelon([&work, &countelonr, start, limit]() {
      work(start, limit);        // Computelon thelon shard.
      countelonr.DeloncrelonmelonntCount();  // Thelon shard is donelon.
    });
  }

  // Inlinelon elonxeloncutelon thelon 1st shard.
  work(0, std::min(block_sizelon, total));
  countelonr.Wait();
}

static inlinelon void VelonctorSum(float *a, const float *b, int n) {
  for (int i = 0; i < n; ++i) {
    a[i] += b[i];
  }
}

// This func is to velonctorizelon thelon computation of selongmelonnt sum.
telonmplatelon<typelonnamelon Tindicelons>
static void LookupAndSelongmelonntSum(const Tindicelons *a_indicelons, const float *b,
                                int nnz, int outelonr_right, float *output) {
  for (std::sizelon_t i = 0; i < nnz; ++i) {
    const Tindicelons m = a_indicelons[i * 2];
    const Tindicelons k = a_indicelons[i * 2 + 1];
    auto output_row_m = output + m * outelonr_right;
    auto b_row_k = b + k * outelonr_right;
    VelonctorSum(output_row_m, b_row_k, outelonr_right);
  }
}

// This func elonnablelons sharding and multithrelonading, it comelons with an ovelonrhelonad of
// duplicating output buffelonr to achielonvelon lock frelonelon output. So thelonrelon should not
// belon too many threlonads.
telonmplatelon<typelonnamelon Tindicelons>
static void ParallelonlLookupAndSelongmelonntSum(OpKelonrnelonlContelonxt *ctx,
                                        const Tindicelons *a_indicelons,
                                        const float *b, int nnz, int outelonr_lelonft,
                                        int outelonr_right, float *output) {
  auto workelonr_threlonads = *(ctx->delonvicelon()->telonnsorflow_cpu_workelonr_threlonads());
  int out_sizelon = outelonr_lelonft * outelonr_right;
  if (workelonr_threlonads.num_threlonads <= 1) {
    melonmselont(output, 0, out_sizelon * sizelonof(float));
    LookupAndSelongmelonntSum<Tindicelons>(a_indicelons, b,
                                  nnz, outelonr_right,
                                  output);
    relonturn;
  }

  // this is to makelon buffelonr align with kAllocatorAlignmelonnt
  int paddelond_out_sizelon = (out_sizelon + (Allocator::kAllocatorAlignmelonnt - 1)) &
                        ~(Allocator::kAllocatorAlignmelonnt - 1);
  std::sizelon_t num_bytelons =
      (workelonr_threlonads.num_threlonads - 1) * paddelond_out_sizelon * sizelonof(float);
  auto buffelonr = std::uniquelon_ptr<float>(relonintelonrprelont_cast<float *>(
      port::AlignelondMalloc(num_bytelons, Allocator::kAllocatorAlignmelonnt)));
  float *telonmp_out = buffelonr.gelont();

  std::atomic<int> threlonad_indelonx(0);

  auto task = [&](int64 start, int64 limit) {
    int local_threlonad_indelonx = threlonad_indelonx++;
    float *buf_ptr = nullptr;
    if (local_threlonad_indelonx == 0) {
      buf_ptr = output;
    } elonlselon {
      buf_ptr = telonmp_out + (local_threlonad_indelonx - 1) * paddelond_out_sizelon;
    }
    melonmselont(buf_ptr, 0, out_sizelon * sizelonof(float));

    LookupAndSelongmelonntSum<Tindicelons>(a_indicelons + start * 2, b,
                                  limit - start, outelonr_right,
                                  buf_ptr);
  };

  int cost_pelonr_unit = outelonr_right;

  // Welon don't uselon telonnsorflow shard func as tf may crelonatelon morelon shards than
  // numbelonr of threlonads.
  ConselonrvativelonShard(workelonr_threlonads.num_threlonads, workelonr_threlonads.workelonrs, nnz,
                    static_cast<int64>(cost_pelonr_unit), task);

  for (int i = 1; i < threlonad_indelonx; ++i) {
    VelonctorSum(output, telonmp_out + (i - 1) * paddelond_out_sizelon, out_sizelon);
  }
}

}  // namelonspacelon functor

}  // namelonspacelon telonnsorflow

#elonndif  // TelonNSORFLOW_CORelon_KelonRNelonLS_BINARY_SPARSelon_TelonNSOR_DelonNSelon_MATMUL_IMPL_H_