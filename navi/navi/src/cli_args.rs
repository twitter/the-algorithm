use crate::{MAX_NUM_INPUTS, MAX_NUM_MODELS, MAX_NUM_OUTPUTS};
use arrayvec::ArrayVec;
use clap::Parser;
use log::info;
use once_cell::sync::OnceCell;
use std::error::Error;
use time::OffsetDateTime;
use time::format_description::well_known::Rfc3339;
#[derive(Parser, Debug, Clone)]
///Navi is configured through CLI arguments(for now) defined below.
//TODO: use clap_serde to make it config file driven
pub struct Args {
    #[clap(short, long, help = "gRPC port Navi runs ons")]
    pub port: i32,
    #[clap(long, default_value_t = 9000, help = "prometheus metrics port")]
    pub prometheus_port: u16,
    #[clap(
        short,
        long,
        default_value_t = 1,
        help = "number of worker threads for tokio async runtime"
    )]
    pub num_worker_threads: usize,
    #[clap(
        long,
        default_value_t = 14,
        help = "number of blocking threads in tokio blocking thread pool"
    )]
    pub max_blocking_threads: usize,
    #[clap(long, default_value = "16", help = "maximum batch size for a batch")]
    pub max_batch_size: Vec<String>,
    #[clap(
        short,
        long,
        default_value = "2",
        help = "max wait time for accumulating a batch"
    )]
    pub batch_time_out_millis: Vec<String>,
    #[clap(
        long,
        default_value_t = 90,
        help = "threshold to start dropping batches under stress"
    )]
    pub batch_drop_millis: u64,
    #[clap(
        long,
        default_value_t = 300,
        help = "polling interval for new version of a model and META.json config"
    )]
    pub model_check_interval_secs: u64,
    #[clap(
        short,
        long,
        default_value = "models/pvideo/",
        help = "root directory for models"
    )]
    pub model_dir: Vec<String>,
    #[clap(
        long,
        help = "directory containing META.json config. separate from model_dir to facilitate remote config management"
    )]
    pub meta_json_dir: Option<String>,
    #[clap(short, long, default_value = "", help = "directory for ssl certs")]
    pub ssl_dir: String,
    #[clap(
        long,
        help = "call out to external process to check model updates. custom logic can be written to pull from hdfs, gcs etc"
    )]
    pub modelsync_cli: Option<String>,
    #[clap(
        long,
        default_value_t = 1,
        help = "specify how many versions Navi retains in memory. good for cases of rolling model upgrade"
    )]
    pub versions_per_model: usize,
    #[clap(
        short,
        long,
        help = "most runtimes support loading ops custom writen. currently only implemented for TF"
    )]
    pub customops_lib: Option<String>,
    #[clap(
        long,
        default_value = "8",
        help = "number of threads to paralleling computations inside an op"
    )]
    pub intra_op_parallelism: Vec<String>,
    #[clap(
        long,
        help = "number of threads to parallelize computations of the graph"
    )]
    pub inter_op_parallelism: Vec<String>,
    #[clap(
        long,
        help = "signature of a serving. only TF"
    )]
    pub serving_sig: Vec<String>,
    #[clap(long, default_value = "examples", help = "name of each input tensor")]
    pub input: Vec<String>,
    #[clap(long, default_value = "output_0", help = "name of each output tensor")]
    pub output: Vec<String>,
    #[clap(
        long,
        default_value_t = 500,
        help = "max warmup records to use. warmup only implemented for TF"
    )]
    pub max_warmup_records: usize,
    #[clap(long, value_parser = Args::parse_key_val::<String, String>, value_delimiter=',')]
    pub onnx_global_thread_pool_options: Vec<(String, String)>,
    #[clap(
    long,
    default_value = "true",
    help = "when to use graph parallelization. only for ONNX"
    )]
    pub onnx_use_parallel_mode: String,
    // #[clap(long, default_value = "false")]
    // pub onnx_use_onednn: String,
    #[clap(
        long,
        default_value = "true",
        help = "trace internal memory allocation and generate bulk memory allocations. only for ONNX. turn if off if batch size dynamic"
    )]
    pub onnx_use_memory_pattern: String,
    #[clap(long, value_parser = Args::parse_key_val::<String, String>, value_delimiter=',')]
    pub onnx_ep_options: Vec<(String, String)>,
    #[clap(long, help = "choice of gpu EPs for ONNX: cuda or tensorrt")]
    pub onnx_gpu_ep: Option<String>,
    #[clap(
        long,
        default_value = "home",
        help = "converter for various input formats"
    )]
    pub onnx_use_converter: Option<String>,
    #[clap(
        long,
        help = "whether to enable runtime profiling. only implemented for ONNX for now"
    )]
    pub profiling: Option<String>,
    #[clap(
        long,
        default_value = "",
        help = "metrics reporting for discrete features. only for Home converter for now"
    )]
    pub onnx_report_discrete_feature_ids: Vec<String>,
    #[clap(
        long,
        default_value = "",
        help = "metrics reporting for continuous features. only for Home converter for now"
    )]
    pub onnx_report_continuous_feature_ids: Vec<String>,
}

impl Args {
    pub fn get_model_specs(model_dir: Vec<String>) -> Vec<String> {
        let model_specs = model_dir
            .iter()
            //let it panic if some model_dir are wrong
            .map(|dir| {
                dir.trim_end_matches('/')
                    .rsplit_once('/')
                    .unwrap()
                    .1
                    .to_owned()
            })
            .collect();
        info!("all model_specs: {:?}", model_specs);
        model_specs
    }
    pub fn version_str_to_epoch(dt_str: &str) -> Result<i64, anyhow::Error> {
        dt_str
            .parse::<i64>()
            .or_else(|_| {
                let ts = OffsetDateTime::parse(dt_str, &Rfc3339)
                    .map(|d| (d.unix_timestamp_nanos()/1_000_000) as i64);
                if ts.is_ok() {
                    info!("original version {} -> {}", dt_str, ts.unwrap());
                }
                ts
            })
            .map_err(anyhow::Error::msg)
    }
    /// Parse a single key-value pair
    fn parse_key_val<T, U>(s: &str) -> Result<(T, U), Box<dyn Error + Send + Sync + 'static>>
    where
        T: std::str::FromStr,
        T::Err: Error + Send + Sync + 'static,
        U: std::str::FromStr,
        U::Err: Error + Send + Sync + 'static,
    {
        let pos = s
            .find('=')
            .ok_or_else(|| format!("invalid KEY=value: no `=` found in `{}`", s))?;
        Ok((s[..pos].parse()?, s[pos + 1..].parse()?))
    }
}

lazy_static! {
    pub static ref ARGS: Args = Args::parse();
    pub static ref MODEL_SPECS: ArrayVec<String, MAX_NUM_MODELS> = {
        let mut specs = ArrayVec::<String, MAX_NUM_MODELS>::new();
        Args::get_model_specs(ARGS.model_dir.clone())
            .into_iter()
            .for_each(|m| specs.push(m));
        specs
    };
    pub static ref INPUTS: ArrayVec<OnceCell<ArrayVec<String, MAX_NUM_INPUTS>>, MAX_NUM_MODELS> = {
        let mut inputs =
            ArrayVec::<OnceCell<ArrayVec<String, MAX_NUM_INPUTS>>, MAX_NUM_MODELS>::new();
        for (idx, o) in ARGS.input.iter().enumerate() {
            if o.trim().is_empty() {
                info!("input spec is empty for model {}, auto detect later", idx);
                inputs.push(OnceCell::new());
            } else {
                inputs.push(OnceCell::with_value(
                    o.split(",")
                        .map(|s| s.to_owned())
                        .collect::<ArrayVec<String, MAX_NUM_INPUTS>>(),
                ));
            }
        }
        info!("all inputs:{:?}", inputs);
        inputs
    };
    pub static ref OUTPUTS: ArrayVec<ArrayVec<String, MAX_NUM_OUTPUTS>, MAX_NUM_MODELS> = {
        let mut outputs = ArrayVec::<ArrayVec<String, MAX_NUM_OUTPUTS>, MAX_NUM_MODELS>::new();
        for o in ARGS.output.iter() {
            outputs.push(
                o.split(",")
                    .map(|s| s.to_owned())
                    .collect::<ArrayVec<String, MAX_NUM_OUTPUTS>>(),
            );
        }
        info!("all outputs:{:?}", outputs);
        outputs
    };
}
