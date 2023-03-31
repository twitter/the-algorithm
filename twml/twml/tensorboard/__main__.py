'\nThis module is responsible for running tensorboard.\n'
import logging,re,sys
from tensorboard.main import run_main
if __name__=='__main__':logging.getLogger('werkzeug').setLevel(logging.WARNING);sys.argv[0]=re.sub('(-script\\.pyw?|\\.exe)?$','',sys.argv[0]);sys.exit(run_main())