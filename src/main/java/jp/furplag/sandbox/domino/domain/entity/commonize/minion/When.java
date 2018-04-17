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

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;

import javax.annotation.concurrent.Immutable;

import org.seasar.doma.Column;
import org.seasar.doma.Embeddable;
import org.seasar.doma.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


/**
 * stereotype of the timestamp field of an entity .
 *
 * @author furplag
 *
 */
@Immutable
@EqualsAndHashCode(exclude = {"moment"})
@ToString(exclude = {"milli", "moment"})
public abstract class When implements Serializable, Comparable<When> {

  /** a timestamp represented by epoch milli . */
  @Transient
  @Getter(AccessLevel.PROTECTED)
  @JsonIgnore
  private final Long milli;

  /** a timestamp represented by {@link LocalDateTime} . */
  @Transient
  @Getter(lazy = true, value = AccessLevel.PROTECTED)
  @JsonIgnore
  private final LocalDateTime moment = ofEpochMilli();

  protected When() {
    this(System.currentTimeMillis());
  }

  protected When(Long milli) {
    this.milli = Optional.ofNullable(milli).orElseGet(System::currentTimeMillis);
  }

  /**
   * stereotype of the timestamp field of an entity .
   *
   * @author furplag
   *
   */
  @Immutable
  @Embeddable
  @EqualsAndHashCode(callSuper = true, exclude = {"created"})
  @ToString(callSuper = true, doNotUseGetters = true)
  public static final class Created extends When {

    /** a timestamp represented by epoch milli . */
    @Column(updatable = false)
    @Getter
    private final Long created;

    public Created(Long created) {
      super(created);
      this.created = getMilli();
    }

    /**
     * returns an instant represented by epoch milli .
     *
     * @return {@link LocalDateTime}
     */
    public LocalDateTime getCreatedAt() {
      return getMoment();
    }

    /**
     * create an instance of {@link Created} .
     *
     * @param milli epoch milli
     * @return {@link Created}
     */
    public static Created of(final long milli) {
      return new Created(milli);
    }

    /**
     * create an instance of {@link Created} .
     *
     * @return {@link Created}
     */
    public static Created now() {
      return new Created(null);
    }
  }

  /**
   * stereotype of the timestamp field of an entity .
   *
   * @author furplag
   *
   */
  @Immutable
  @Embeddable
  @EqualsAndHashCode(callSuper = true, exclude = {"modified"})
  @ToString(callSuper = true, doNotUseGetters = true)
  public static final class Modified extends When {

    /** a timestamp represented by epoch milli . */
    @Column
    @Getter
    private final Long modified;

    public Modified(Long modified) {
      super(modified);
      this.modified = getMilli();
    }

    /**
     * returns an instant represented by epoch milli .
     *
     * @return {@link LocalDateTime}
     */
    public LocalDateTime getModifiedAt() {
      return getMoment();
    }

    /**
     * create an instance of {@link Modified} .
     *
     * @param milli epoch milli
     * @return {@link Modified}
     */
    public static Modified of(final long milli) {
      return new Modified(milli);
    }

    /**
     * create an instance of {@link Modified} .
     *
     * @return {@link Modified}
     */
    public static Modified now() {
      return new Modified(null);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(When anotherOne) {
    return Long.valueOf(milli == null ? Long.MIN_VALUE : milli).compareTo(anotherOne == null ? Long.MIN_VALUE : anotherOne.milli);
  }

  /**
   * shorthand for converting epoch milli to {@link LocalDateTime} .
   *
   * @return {@link LocalDateTime} represented by specified epoch milli
   */
  private LocalDateTime ofEpochMilli() {
    return OffsetDateTime.ofInstant(Instant.ofEpochMilli(Optional.ofNullable(milli).orElseGet(System::currentTimeMillis)), ZoneId.systemDefault()).toLocalDateTime();
  }
}
