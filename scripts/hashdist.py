#!/usr/bin/env python2.7

from Queue import Queue
import argparse
import logging
import multiprocessing
import os
import re
import string
import subprocess
import sys
import threading


def parse_size(s):
    def mult(multiplier):
        return int(s[:-1])*multiplier

    if all(x in string.digits for x in s):
        return int(s)
    if s.endswith('b'):
        return mult(1)
    if s.endswith('k'):
        return mult(1024)
    if s.endswith('m'):
        return mult(1024*1024)
    if s.endswith('g'):
        return mult(1024*1024*1024)
    raise Exception("Can't parse %r" % (s,))


class JobInputter(threading.Thread):
    """
    Takes input originally from stdin through iq and sends it to the job
    """
    def __init__(self, job_name, popen, iq):
        self.job_name = job_name
        self.popen = popen
        self.iq = iq
        super(JobInputter, self).__init__()

    def __repr__(self):
        return "<%s %s>" % (self.__class__.__name__, self.job_name)

    def run(self):
        while True:
            item = self.iq.get()
            logging.debug("%r got item %r", self, item)
            if item is None:
                logging.debug("%r closing %r", self, self.popen.stdin)
                self.popen.stdin.close()
                self.iq.task_done()
                break

            try:
                self.popen.stdin.write(item)
                self.popen.stdin.flush()
                self.iq.task_done()
            except IOError:
                logging.exception("exception writing to popen %r", self.popen)
                return os._exit(1)


class JobOutputter(threading.Thread):
    """
    Takes output from the job and sends it to stdout
    """
    def __init__(self, job_name, popen, out_fd, lock):
        self.job_name = job_name
        self.popen = popen
        self.out_fd = out_fd
        self.lock = lock
        super(JobOutputter, self).__init__()

    def __repr__(self):
        return "<%s %s>" % (self.__class__.__name__, self.job_name)

    def run(self):
        for line in self.popen.stdout:
            logging.debug("%r read %d bytes", self, len(line))
            with self.lock:
                try:
                    self.out_fd.write(line)
                except IOError as e:
                    if e.errno != errno.EPIPE:
                        logging.exception("exception writing to output %r", self.out_fd)
                    return os._exit(1)

            logging.debug("Got eof on %r", self)


def hash_select(key, choices):
    return choices[hash(key) % len(choices)]


def main():
    try:
        return _main()
    except KeyboardInterrupt:
        # because we mess with threads a lot, we need to make sure that ^C is
        # actually a nuclear kill
        os._exit(1)

def _main():
    parser = argparse.ArgumentParser()
    parser.add_argument('-n', metavar='N', type=int,
                        default=multiprocessing.cpu_count(), dest='nprocs')
    parser.add_argument('-b', '--buffer', metavar='N', type=parse_size,
                        help="size (in lines) of input buffer for each process",
                        default=1024,
                        dest='bufsize')
    parser.add_argument('-f', metavar='FIELDSEP', type=str, default='\t',
                        dest='field_sep')
    parser.add_argument('-r', metavar='FIELDRE', type=str, default=None,
                        dest='field_re')
    parser.add_argument('--logging', help=argparse.SUPPRESS, default='error')
    parser.add_argument('cmd', nargs='+')

    args = parser.parse_args()

    if args.field_re and args.field_sep:
        args.print_usage()
        return sys.exit(1)

    if args.nprocs == 1:
        # if you only want one, what do you need me for?
        os.execvp(args.cmd[0], args.cmd)
        return sys.exit(1) # will never get here

    if args.field_re:
        first_field_re = re.compile(args.field_re)
    else:
        first_field_re = re.compile('^([^'+re.escape(args.field_sep)+']+)')

    logging.basicConfig(level=getattr(logging, args.logging.upper()))

    stdout_mutex = threading.Lock()
    processes = []

    for x in range(args.nprocs):
        logging.debug("Starting %r (%d)", args.cmd, x)
        ps = subprocess.Popen(args.cmd,
                              stdin=subprocess.PIPE,
                              stdout=subprocess.PIPE)
        psi = JobInputter(x, ps, Queue(maxsize=args.bufsize))
        pso = JobOutputter(x, ps, sys.stdout, stdout_mutex)
        psi.start()
        pso.start()
        processes.append((psi, pso))

    for line in sys.stdin:
        if not line:
            continue

        logging.debug("Read %d bytes from stdin", len(line))

        first_field_m = first_field_re.match(line)
        first_field = first_field_m.group(0)
        psi, _pso = hash_select(first_field, processes)
        logging.debug("Writing %d bytes to %r (%r)", len(line), psi, first_field)
        psi.iq.put(line)

    logging.debug("Hit eof on stdin")

    for x, (psi, pso) in enumerate(processes):
        logging.debug("Sending terminator to %d (%r)", x, psi)
        psi.iq.put(None)

    for x, (psi, pso) in enumerate(processes):
        logging.debug("Waiting for q %d (%r)", x, psi)
        psi.iq.join()
        logging.debug("Waiting for psi %d (%r)", x, psi)
        psi.join()
        logging.debug("Waiting for pso %d (%r)", x, psi)
        pso.join()

    return sys.exit(0)


if __name__ == '__main__':
    main()
