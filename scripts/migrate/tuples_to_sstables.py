#!/usr/bin/jython
"""A jython script that takes as input a set of tuples meant to be
bulk-loaded into Cassandra, and outputs a set of sstables usable
by Cassandra's sstableloader.

The Cassandra jars and configuration must be on the classpath for this
to function properly.
"""

import time
import os
import os.path
import logging

from org.apache.cassandra.utils.ByteBufferUtil import bytes
from java.nio import ByteBuffer


def utf8(val):
    return bytes(val)

def datetime(val):
    milliseconds = long(float(val) * 1e3)
    return ByteBuffer.allocate(8).putLong(0, milliseconds)

COERCERS = dict(utf8=utf8,
                datetime=datetime)


def convert_to_sstables(input_files, column_family,
                        output_dir_name, keyspace, timestamp, buffer_size,
                        data_type, verbose=False):
    import fileinput
    from java.io import File
    from org.apache.cassandra.io.sstable import SSTableSimpleUnsortedWriter
    from org.apache.cassandra.db.marshal import AsciiType
    from org.apache.cassandra.service import StorageService
    from org.apache.cassandra.io.compress import CompressionParameters

    partitioner = StorageService.getPartitioner()

    try:
        coercer = COERCERS[data_type]
    except KeyError:
        raise ValueError("invalid data type")

    output_dir = File(output_dir_name)

    if not output_dir.exists():
        output_dir.mkdir()

    compression_options = CompressionParameters.create({
        'sstable_compression': 'org.apache.cassandra.io.compress.SnappyCompressor',
        'chunk_length_kb': '64'
    })

    writer = SSTableSimpleUnsortedWriter(output_dir,
                                         partitioner,
                                         keyspace,
                                         column_family,
                                         AsciiType.instance,
                                         None,
                                         buffer_size,
                                         compression_options)


    try:
        previous_rowkey = None
        for line in fileinput.input(input_files):
            ttl = None

            t_columns = line.rstrip("\n").split("\t")
            if len(t_columns) == 3:
                rowkey, colkey, value = t_columns
            elif len(t_columns) == 4:
                rowkey, colkey, value, ttl = t_columns
                ttl = int(ttl)
            else:
                raise Exception("unknown data format for %r" % (t_columns,))

            if rowkey != previous_rowkey:
                writer.newRow(bytes(rowkey))

            coerced = coercer(value)

            if ttl is None:
                writer.addColumn(bytes(colkey), coerced, timestamp)
            else:
                # see
                # https://svn.apache.org/repos/asf/cassandra/trunk/src/java/org/apache/cassandra/io/sstable/AbstractSSTableSimpleWriter.java addExpiringColumn:expirationTimestampMS
                # for explanation
                expirationTimestampMS = (timestamp / 1000) + (ttl * 1000)
                writer.addExpiringColumn(bytes(colkey), coerced,
                                         timestamp, ttl, expirationTimestampMS)

            if verbose and fileinput.lineno() % 10000 == 0:
                print "%d items processed (%s)" % (fileinput.lineno(),
                                                   fileinput.filename())
    except:
        # it's common that whatever causes us to fail also cases the finally
        # clause below to fail, which masks the original exception
        logging.exception("Failed")
        raise
    finally:
        writer.close()


def main():
    import os
    import optparse

    parser = optparse.OptionParser(
        usage="USAGE: tuples_to_sstables [options] COLUMN_FAMILY INPUT [...]")
    parser.add_option("--timestamp",
                      type="long",
                      nargs=1, dest="timestamp",
                      default=int(time.time()*1000000),
                      help="timestamp to use for each column")
    parser.add_option("--buffer-size",
                      type="int",
                      nargs=1, dest="buffer_size", default=128,
                      help="size in MB to buffer before writing SSTables")
    parser.add_option("--data-type",
                      nargs=1, dest="data_type", default="utf8",
                      help="type to coerce data into for column values")
    parser.add_option("-k", "--keyspace",
                      nargs=1, dest="keyspace", default="reddit",
                      help="the name of the keyspace the data is for")
    parser.add_option("-o", "--output-root",
                      nargs=1, dest="output_root", default=".",
                      help="the root directory to write the SSTables into")
    parser.add_option("-v", "--verbose",
                      action="store_true", dest="verbose", default=False,
                      help="print status messages to stdout")

    options, args = parser.parse_args()
    options = dict(options.__dict__) # in jython, __dict__ is a StringMap
    options["output_dir_name"] = os.path.join(options.pop("output_root"))
    options["column_family"], input_files = args[0], args[1:]

    return convert_to_sstables(input_files, **options)


if __name__ == "__main__":
    import sys
    sys.exit(main())
