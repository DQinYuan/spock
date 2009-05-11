/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.spockframework.runtime;

import org.spockframework.runtime.model.MethodInfo;
import org.spockframework.runtime.model.SpeckInfo;
import org.spockframework.runtime.model.FeatureInfo;

/**
 *
 * @author Peter Niederwieser
 */
public interface IRunSupervisor {
  void beforeSpeck(SpeckInfo speck);
  void beforeFeature(FeatureInfo feature);
  /*
   * Called before the first iteration of a parameterized feature is
   * run. All data providers have been created successfully at this point.
   * Not called for non-parameterized features.
   */
  void beforeFirstIteration(int estimatedNumIterations);
  /*
   * Called before the next iteration of a parameterized feature is
   * run. All parameterization values have been computed successfully
   * at this point. Not called for non-parameterized features.
   */
  void beforeIteration();
  void afterIteration();
  void afterLastIteration();
  void afterFeature();
  void afterSpeck();

  int error(MethodInfo method, Throwable error, int runStatus);
}
