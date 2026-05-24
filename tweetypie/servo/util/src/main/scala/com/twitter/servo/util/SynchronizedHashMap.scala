package com.twitter.servo.util

import scala.collection.mutable

class SynchronizedHashMap[K, V] extends mutable.HashMap[K, V] with mutable.SynchronizedMap[K, V]
