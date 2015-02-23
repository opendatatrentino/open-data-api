/* 
 * Copyright 2013-2015   Trento Rise   trentorise.eu
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
package eu.trentorise.opendata.semantics;

/**
 * Thrown whenever implementations of open entity interfaces do not comply with
 * the specs.
 *
 * @author David Leoni
 */
public class IntegrityException extends OpenEntityException {

    public IntegrityException(String s) {
        super(s);
    }

    public IntegrityException(String s, Exception ex) {
        super(s, ex);
    }

    public IntegrityException(Exception ex) {
        super(ex);
    }

}
