use crate::{MAX_NUM_ NPUTS, MAX_NUM_MODELS, MAX_NUM_OUTPUTS};
use arrayvec::ArrayVec;
use clap::Parser;
use log:: nfo;
use once_cell::sync::OnceCell;
use std::error::Error;
use t  ::OffsetDateT  ;
use t  ::format_descr pt on:: ll_known::Rfc3339;
#[der ve(Parser, Debug, Clone)]
///Nav   s conf gured through CL  argu nts(for now) def ned below.
//TODO: use clap_serde to make   conf g f le dr ven
pub struct Args {
    #[clap(short, long,  lp = "gRPC port Nav  runs ons")]
    pub port:  32,
    #[clap(long, default_value_t = 9000,  lp = "pro t us  tr cs port")]
    pub pro t us_port: u16,
    #[clap(
        short,
        long,
        default_value_t = 1,
         lp = "number of worker threads for tok o async runt  "
    )]
    pub num_worker_threads: us ze,
    #[clap(
        long,
        default_value_t = 14,
         lp = "number of block ng threads  n tok o block ng thread pool"
    )]
    pub max_block ng_threads: us ze,
    #[clap(long, default_value = "16",  lp = "max mum batch s ze for a batch")]
    pub max_batch_s ze: Vec<Str ng>,
    #[clap(
        short,
        long,
        default_value = "2",
         lp = "max wa  t   for accumulat ng a batch"
    )]
    pub batch_t  _out_m ll s: Vec<Str ng>,
    #[clap(
        long,
        default_value_t = 90,
         lp = "threshold to start dropp ng batc s under stress"
    )]
    pub batch_drop_m ll s: u64,
    #[clap(
        long,
        default_value_t = 300,
         lp = "poll ng  nterval for new vers on of a model and META.json conf g"
    )]
    pub model_c ck_ nterval_secs: u64,
    #[clap(
        short,
        long,
        default_value = "models/pv deo/",
         lp = "root d rectory for models"
    )]
    pub model_d r: Vec<Str ng>,
    #[clap(
        long,
         lp = "d rectory conta n ng META.json conf g. separate from model_d r to fac l ate remote conf g manage nt"
    )]
    pub  ta_json_d r: Opt on<Str ng>,
    #[clap(short, long, default_value = "",  lp = "d rectory for ssl certs")]
    pub ssl_d r: Str ng,
    #[clap(
        long,
         lp = "call out to external process to c ck model updates. custom log c can be wr ten to pull from hdfs, gcs etc"
    )]
    pub modelsync_cl : Opt on<Str ng>,
    #[clap(
        long,
        default_value_t = 1,
         lp = "spec fy how many vers ons Nav  reta ns  n  mory. good for cases of roll ng model upgrade"
    )]
    pub vers ons_per_model: us ze,
    #[clap(
        short,
        long,
         lp = "most runt  s support load ng ops custom wr en. currently only  mple nted for TF"
    )]
    pub customops_l b: Opt on<Str ng>,
    #[clap(
        long,
        default_value = "8",
         lp = "number of threads to parallel ng computat ons  ns de an op"
    )]
    pub  ntra_op_parallel sm: Vec<Str ng>,
    #[clap(
        long,
         lp = "number of threads to parallel ze computat ons of t  graph"
    )]
    pub  nter_op_parallel sm: Vec<Str ng>,
    #[clap(
        long,
         lp = "s gnature of a serv ng. only TF"
    )]
    pub serv ng_s g: Vec<Str ng>,
    #[clap(long, default_value = "examples",  lp = "na  of each  nput tensor")]
    pub  nput: Vec<Str ng>,
    #[clap(long, default_value = "output_0",  lp = "na  of each output tensor")]
    pub output: Vec<Str ng>,
    #[clap(
        long,
        default_value_t = 500,
         lp = "max warmup records to use. warmup only  mple nted for TF"
    )]
    pub max_warmup_records: us ze,
    #[clap(long, value_parser = Args::parse_key_val::<Str ng, Str ng>, value_del m er=',')]
    pub onnx_global_thread_pool_opt ons: Vec<(Str ng, Str ng)>,
    #[clap(
    long,
    default_value = "true",
     lp = "w n to use graph parallel zat on. only for ONNX"
    )]
    pub onnx_use_parallel_mode: Str ng,
    // #[clap(long, default_value = "false")]
    // pub onnx_use_onednn: Str ng,
    #[clap(
        long,
        default_value = "true",
         lp = "trace  nternal  mory allocat on and generate bulk  mory allocat ons. only for ONNX. turn  f off  f batch s ze dynam c"
    )]
    pub onnx_use_ mory_pattern: Str ng,
    #[clap(long, value_parser = Args::parse_key_val::<Str ng, Str ng>, value_del m er=',')]
    pub onnx_ep_opt ons: Vec<(Str ng, Str ng)>,
    #[clap(long,  lp = "cho ce of gpu EPs for ONNX: cuda or tensorrt")]
    pub onnx_gpu_ep: Opt on<Str ng>,
    #[clap(
        long,
        default_value = "ho ",
         lp = "converter for var ous  nput formats"
    )]
    pub onnx_use_converter: Opt on<Str ng>,
    #[clap(
        long,
         lp = "w t r to enable runt   prof l ng. only  mple nted for ONNX for now"
    )]
    pub prof l ng: Opt on<Str ng>,
    #[clap(
        long,
        default_value = "",
         lp = " tr cs report ng for d screte features. only for Ho  converter for now"
    )]
    pub onnx_report_d screte_feature_ ds: Vec<Str ng>,
    #[clap(
        long,
        default_value = "",
         lp = " tr cs report ng for cont nuous features. only for Ho  converter for now"
    )]
    pub onnx_report_cont nuous_feature_ ds: Vec<Str ng>,
}

 mpl Args {
    pub fn get_model_specs(model_d r: Vec<Str ng>) -> Vec<Str ng> {
        let model_specs = model_d r
            . er()
            //let   pan c  f so  model_d r are wrong
            .map(|d r| {
                d r.tr m_end_matc s('/')
                    .rspl _once('/')
                    .unwrap()
                    .1
                    .to_owned()
            })
            .collect();
         nfo!("all model_specs: {:?}", model_specs);
        model_specs
    }
    pub fn vers on_str_to_epoch(dt_str: &str) -> Result< 64, anyhow::Error> {
        dt_str
            .parse::< 64>()
            .or_else(|_| {
                let ts = OffsetDateT  ::parse(dt_str, &Rfc3339)
                    .map(|d| (d.un x_t  stamp_nanos()/1_000_000) as  64);
                 f ts. s_ok() {
                     nfo!("or g nal vers on {} -> {}", dt_str, ts.unwrap());
                }
                ts
            })
            .map_err(anyhow::Error::msg)
    }
    /// Parse a s ngle key-value pa r
    fn parse_key_val<T, U>(s: &str) -> Result<(T, U), Box<dyn Error + Send + Sync + 'stat c>>
    w re
        T: std::str::FromStr,
        T::Err: Error + Send + Sync + 'stat c,
        U: std::str::FromStr,
        U::Err: Error + Send + Sync + 'stat c,
    {
        let pos = s
            .f nd('=')
            .ok_or_else(|| format!(" nval d KEY=value: no `=` found  n `{}`", s))?;
        Ok((s[..pos].parse()?, s[pos + 1..].parse()?))
    }
}

lazy_stat c! {
    pub stat c ref ARGS: Args = Args::parse();
    pub stat c ref MODEL_SPECS: ArrayVec<Str ng, MAX_NUM_MODELS> = {
        let mut specs = ArrayVec::<Str ng, MAX_NUM_MODELS>::new();
        Args::get_model_specs(ARGS.model_d r.clone())
            . nto_ er()
            .for_each(|m| specs.push(m));
        specs
    };
    pub stat c ref  NPUTS: ArrayVec<OnceCell<ArrayVec<Str ng, MAX_NUM_ NPUTS>>, MAX_NUM_MODELS> = {
        let mut  nputs =
            ArrayVec::<OnceCell<ArrayVec<Str ng, MAX_NUM_ NPUTS>>, MAX_NUM_MODELS>::new();
        for ( dx, o)  n ARGS. nput. er().enu rate() {
             f o.tr m(). s_empty() {
                 nfo!(" nput spec  s empty for model {}, auto detect later",  dx);
                 nputs.push(OnceCell::new());
            } else {
                 nputs.push(OnceCell::w h_value(
                    o.spl (",")
                        .map(|s| s.to_owned())
                        .collect::<ArrayVec<Str ng, MAX_NUM_ NPUTS>>(),
                ));
            }
        }
         nfo!("all  nputs:{:?}",  nputs);
         nputs
    };
    pub stat c ref OUTPUTS: ArrayVec<ArrayVec<Str ng, MAX_NUM_OUTPUTS>, MAX_NUM_MODELS> = {
        let mut outputs = ArrayVec::<ArrayVec<Str ng, MAX_NUM_OUTPUTS>, MAX_NUM_MODELS>::new();
        for o  n ARGS.output. er() {
            outputs.push(
                o.spl (",")
                    .map(|s| s.to_owned())
                    .collect::<ArrayVec<Str ng, MAX_NUM_OUTPUTS>>(),
            );
        }
         nfo!("all outputs:{:?}", outputs);
        outputs
    };
}
