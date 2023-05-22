import io
import logging
import subprocess
from threading import Lock
from typing import Any, Optional

"""
This module provides a binary data record reader for EventBus data.
It starts a EventBus subscriber in a separate process to receive EventBus streaming data.
The subscriber is supposed to outputs received data through PIPE to this module.
This module parses input and output binary data record to serve as a record reader.
"""


class BinaryRecordReader(object):
    def initialize(self):
        """Initialize the reader"""
        pass

    def read(self):
        """Read raw bytes for one record"""
        raise NotImplementedError

    def close(self):
        """Close the reader"""
        pass


class ReadableWrapper(object):
    def __init__(self, internal: io.BufferedReader):
        self.internal = internal

    def __getattr__(self, name: str) -> Any:
        return getattr(self.internal, name)

    def readable(self) -> bool:
        return True


class EventBusPipedBinaryRecordReader(BinaryRecordReader):
    JAVA = "/usr/lib/jvm/java-11-twitter/bin/java"
    RECORD_SEPARATOR_HEX = [
        0x29,
        0xD8,
        0xD5,
        0x06,
        0x58,
        0xCD,
        0x4C,
        0x29,
        0xB2,
        0xBC,
        0x57,
        0x99,
        0x21,
        0x71,
        0xBD,
        0xFF,
    ]
    RECORD_SEPARATOR = "".join([chr(i) for i in RECORD_SEPARATOR_HEX])
    RECORD_SEPARATOR_LENGTH = len(RECORD_SEPARATOR)
    CHUNK_SIZE = 8192

    def __init__(
        self,
        jar_file: str,
        num_eb_threads: int,
        subscriber_id: str,
        filter_str: Optional[str] = None,
        buffer_size: int = 32768,
        debug: bool = False,
    ):
        self.jar_file = jar_file
        self.num_eb_threads = num_eb_threads
        self.subscriber_id = subscriber_id
        self.filter_str = filter_str if filter_str else '""'
        self.buffer_size = buffer_size
        self.lock = Lock()
        self._pipe = None
        self._buffered_reader = None
        self._bytes_buffer = None
        self.debug = debug

    def initialize(self) -> None:
        if not self._pipe:
            self._pipe = subprocess.Popen(
                [
                    self.JAVA,
                    "-jar",
                    self.jar_file,
                    "-subscriberId",
                    self.subscriber_id,
                    "-numThreads",
                    str(self.num_eb_threads),
                    "-dataFilter",
                    self.filter_str,
                    "-debug" if self.debug else "",
                ],
                stdout=subprocess.PIPE,
            )
            self._buffered_reader = io.BufferedReader(
                ReadableWrapper(self._pipe.stdout), self.buffer_size
            )
            self._bytes_buffer = io.BytesIO()
        else:
            logging.warning("Already initialized")

    def _find_next_record(self) -> Optional[bytes]:
        tail = [""]
        while True:
            chunk = tail[0] + self._buffered_reader.read(self.CHUNK_SIZE)
            index = chunk.find(self.RECORD_SEPARATOR)
            if index < 0:
                self._bytes_buffer.write(chunk[: -self.RECORD_SEPARATOR_LENGTH])
                tail[0] = chunk[-self.RECORD_SEPARATOR_LENGTH :]
            else:
                self._bytes_buffer.write(chunk[:index])
                return chunk[(index + self.RECORD_SEPARATOR_LENGTH) :]

    def _read(self) -> bytes:
        with self.lock:
            remaining = self._find_next_record()
            record = self._bytes_buffer.getvalue()

            # clean up buffer
            self._bytes_buffer.close()
            self._bytes_buffer = io.BytesIO()
            self._bytes_buffer.write(remaining)

            return record

    def read(self) -> bytes:
        while True:
            try:
                return self._read()
            except Exception as e:
                logging.error(f"Error reading bytes for next record: {e}")
                if self.debug:
                    raise

    def close(self) -> None:
        try:
            self._bytes_buffer.close()
            self._buffered_reader.close()
            self._pipe.terminate()
        except Exception as e:
            logging.error("Error closing reader: {e}")
