#region License

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

#endregion

using System.Collections;
using System.Collections.Concurrent;
using System.Collections.Generic;

namespace Gremlin.Net.Driver
{
    internal class ConcurrentConnectionCollection : IEnumerable<Connection>
    {
        private const int DefaultValue = 0;
        
        // We use a ConcurrentDictionary because we need a concurrent collection with a TryRemove() method.
        // Only the keys are used here, the values just contain a dummy DefaultValue.
        private readonly ConcurrentDictionary<Connection, int> _connections =
            new ConcurrentDictionary<Connection, int>();

        public int Count => _connections.Count;

        public bool IsEmpty => _connections.IsEmpty;

        public void Add(Connection connection) => _connections.TryAdd(connection, DefaultValue);

        public bool TryRemove(Connection connection) => _connections.TryRemove(connection, out _);

        public IEnumerator<Connection> GetEnumerator() => _connections.Keys.GetEnumerator();

        IEnumerator IEnumerable.GetEnumerator() => GetEnumerator();
    }
}