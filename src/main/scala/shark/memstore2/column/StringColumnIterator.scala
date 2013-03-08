/*
 * Copyright (C) 2012 The Regents of The University California.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package shark.memstore2.column

import org.apache.hadoop.io.Text

import shark.memstore2.buffer.ByteBufferReader


object StringColumnIterator {

  sealed class Default extends ColumnIterator {
    // In string, a length of -1 is used to represent null values.
    private val _writable = new Text
    private var _currentLen = 0

    override def next()  {
      // TODO: This is very inefficient. We should build Text directly using Java reflection.
      _currentLen = _bytesReader.getInt
      if (_currentLen >= 0) {
        val newBytes = new Array[Byte](_currentLen)
        _bytesReader.getBytes(newBytes, _currentLen)
        _writable.set(newBytes)
      }
    }

    override def current = if (_currentLen >= 0) _writable else null
  }
}