/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.polaris.core.persistence.cache;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.LL_Result;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE_INTERESTING;

@JCStressTest
@Description("Tests safeIncrementValue is threadsafe")
@Outcome.Outcomes({
        @Outcome(id = "0, 0", expect = ACCEPTABLE, desc = "Object not constructed yet"),
        @Outcome(id = "1, 0", expect = ACCEPTABLE, desc = "Object half-way"),
        @Outcome(id = "1, 2", expect = ACCEPTABLE, desc = "Object fully constructed"),
        @Outcome(expect = ACCEPTABLE_INTERESTING, desc = "Reordered"),
})
@State()
public class TwoFieldTest {

    private TwoFields twoFields = new TwoFields(0, 0);

    @Actor
    public void actor1() {
        twoFields.setX(1);
        twoFields.setY(2);
    }

    @Actor
    public void actor2(LL_Result longResult) {
        longResult.r1 = twoFields.x;
        longResult.r2 = twoFields.y;
    }

}