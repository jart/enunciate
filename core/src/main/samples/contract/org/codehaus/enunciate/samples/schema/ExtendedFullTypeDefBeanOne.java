/*
 * Copyright 2006-2008 Web Cohesion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codehaus.enunciate.samples.schema;

/**
 * @author Ryan Heaton
 */
public class ExtendedFullTypeDefBeanOne extends FullTypeDefBeanOne {

  private String property6;

  public String getProperty6() {
    return property6;
  }

  public void setProperty6(String property6) {
    this.property6 = property6;
  }

  public static class NestedExtendedFullTypeDefBeanOne {

    private String nestedProperty;

    public String getNestedProperty() {
      return nestedProperty;
    }

    public void setNestedProperty(String nestedProperty) {
      this.nestedProperty = nestedProperty;
    }
  }
}