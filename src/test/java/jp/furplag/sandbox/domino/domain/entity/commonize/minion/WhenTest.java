/**
 * Copyright (C) 2018+ furplag (https://github.com/furplag)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.furplag.sandbox.domino.domain.entity.commonize.minion;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.time.Instant;
import java.time.ZoneId;

import org.junit.Test;

import jp.furplag.sandbox.domino.domain.entity.commonize.minion.When.Created;
import jp.furplag.sandbox.domino.domain.entity.commonize.minion.When.Modified;

public class WhenTest {

  @Test
  public void test() {

    final class WhenTesting extends When {
      final Long currentTimeMillis;

      WhenTesting(Long milli) {
        super(milli);
        currentTimeMillis = getMilli();
      }
    }

    final WhenTesting actual = new WhenTesting(null);
    assertThat(actual.currentTimeMillis, is(actual.getMilli()));

    assertThat(new When(1L) {}.getMilli(), is(1L));
    assertThat(new When(1_000_000_000L) {}.getMilli(), is(1_000_000_000L));
    assertThat(new When(0L) {}.getMoment(), is(Instant.ofEpochMilli(0L).atZone(ZoneId.systemDefault()).toLocalDateTime()));
    assertThat(new Created(0L), is(Created.of(0)));
    assertThat(new Modified(0L), is(Modified.of(0)));
  }
}
