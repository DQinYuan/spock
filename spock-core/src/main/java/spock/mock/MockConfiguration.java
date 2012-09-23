/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package spock.mock;

import java.util.List;
import java.util.Map;

import org.spockframework.util.Nullable;

import spock.lang.Experimental;

/**
 * Options for creating mock objects. {@link #getNature()} and {@link #getImplementation()} are mandatory
 * options that are typically determined by choosing the appropriate {@link spock.lang.MockingApi}
 * factory method. {@link #getType()} is a mandatory option that is typically passed directly to a {@code MockingApi}
 * factory method or inferred from the left-hand side of the enclosing assignment. The remaining options are
 * optional and are typically passed to a {@code MockingApi} factory method as named parameters.
 */
@Experimental
public class MockConfiguration {
  private final String name;
  private final Class<?> type;
  private final MockNature nature;
  private final MockImplementation implementation;
  private final List<Object> constructorArgs;
  private final IMockInvocationResponder responder;
  private final boolean global;
  private final boolean verified;
  private final boolean useObjenesis;

  @SuppressWarnings("unchecked")
  public MockConfiguration(@Nullable String name, Class<?> type, MockNature nature,
      MockImplementation implementation, Map<String, Object> options) {
    this.name = getOption(options, "name", String.class, name);
    this.type = getOption(options, "type", Class.class, type);
    this.nature = getOption(options, "nature", MockNature.class, nature);
    this.implementation = getOption(options, "implementation", MockImplementation.class, implementation);
    this.constructorArgs = getOption(options, "constructorArgs", List.class, null);
    this.responder = getOption(options, "responder", IMockInvocationResponder.class, this.nature.getResponder());
    this.global = getOption(options, "global", Boolean.class, false);
    this.verified = getOption(options, "verified", Boolean.class, this.nature.isVerified());
    this.useObjenesis = getOption(options, "useObjenesis", Boolean.class, this.nature.isUseObjenesis());
  }

  /**
   * Returns the name of the mock object.
   *
   * @return the name of the mock object
   */
  @Nullable
  public String getName() {
    return name;
  }

  /**
   * Returns the class or interface type of the mock object.
   *
   * @return the class or interface type of the mock object
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * Returns the nature of the mock object.
   *
   * @return the nature of the mock object
   */
  public MockNature getNature() {
    return nature;
  }

  /**
   * Returns the implementation of the mock object.
   *
   * @return the implementation of the mock object
   */
  public MockImplementation getImplementation() {
    return implementation;
  }

  /**
   * Returns the constructor arguments to be used during construction of the mock object.
   *
   * @return the constructor arguments to be used during construction of the mock object
   */
  @Nullable
  public List<Object> getConstructorArgs() {
    return constructorArgs;
  }

  /**
   * Returns the responder for generating return values in response to method calls on the mock object.
   *
   * @return the responder for generating return values in response to method calls on the mock object
   */
  public IMockInvocationResponder getResponder() {
    return responder;
  }

  /**
   * Tells whether a mock object stands in for all objects of the mocked type, or just for itself.
   * This is an optional feature that may not be supported by a particular {@link MockImplementation}.
   *
   * @return whether a mock object stands in for all objects of the mocked type, or just for itself
   */
  public boolean isGlobal() {
    return global;
  }

  /**
   * Tells whether invocations on the mock object should be verified. If (@code false}, invocations
   * on the mock object will not be matched against interactions that have a cardinality.
   *
   * @return whether invocations on the mock object should be verified
   */
  public boolean isVerified() {
    return verified;
  }

  /**
   * Tells whether the Objenesis library, if available on the class path, should be used for constructing
   * the mock object, rather than calling a constructor.
   *
   * @return whether the Objenesis library should be used for constructing the mock object
   */
  public boolean isUseObjenesis() {
    return useObjenesis;
  }

  private <T> T getOption(Map<String, Object> options, String key, Class<T> type, T defaultValue) {
    return options.containsKey(key) ? type.cast(options.get(key)) : defaultValue;
  }
}