/* 
 * Copyright 2015 TrentoRISE   (trentorise.eu).
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
package eu.trentorise.opendata.semantics.exceptions;
/**
 * A runtime exception to raise when something is not found.
 * 
 * @author David Leoni <david.leoni@unitn.it>
 */
public class UnsupportedFeatureException extends OpenEntityException {
       
    /**
     * Creates the UnsupportedOperationException using the provided throwable
     */
    public UnsupportedFeatureException(Throwable tr) {
        super(tr);
    }

    /**
     * Creates the UnsupportedOperationException using the provided message and throwable
     */
    public UnsupportedFeatureException(String msg, Throwable tr) {
        super(msg, tr);
    }

    /**
     * Creates the UnsupportedOperationException using the provided message
     */
    public UnsupportedFeatureException(String msg) {
        super(msg);
    }
}
