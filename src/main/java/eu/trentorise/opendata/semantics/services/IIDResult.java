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
package eu.trentorise.opendata.semantics.services;

import eu.trentorise.opendata.semantics.model.entity.IEntity;
import eu.trentorise.opendata.semantics.services.AssignmentResult;
import java.util.Set;
import javax.annotation.Nullable;

/**
 * A reconciliation result, expressed as a set of candidate entities or a new
 * one if none was found during the matching.
 *
 * @author Ivan Tankoyeu <tankoyeui@disi.unitn.it>
 * @author David Leoni <david.leoni@unitn.it>
 * 
 */
public interface IIDResult {

    /**
     * Gets the result of the id matching.
     *
     * @return the assignment result.
     */
    AssignmentResult getAssignmentResult();

    /**
     * Gets the selected entity.
     *
     * @return an entity if getAssignmentResult is REUSE or NEW, null otherwise.
     * If getAssignmentResult is REUSE, returns the 'best' candidate among the
     * possible matching entities. If the assignment result is NEW, a deep copy
     * of the original entity is returned containing an assigned URL. This new
     * entity will not be stored on the server, though. In order to store it,
     * call
     * {@link eu.trentorise.opendata.semantics.services.IEntityService#createEntityURL(eu.trentorise.opendata.semantics.model.entity.IEntity)}
     * with the new entity.
     */
    @Nullable
    IEntity getResultEntity();

    /**
     * Gets a set of possible suggested entities.
     *
     * @return a set of possible suggested entities if getAssignmentResult is
     * REUSE. If assignment result is NEW, returns a set containing only one new
     * entity with newly assigned url. Otherwise, returns an empty set.
     */
    Set<IEntity> getEntities();

    /**
     * Gets a global ID of the matched entity
     *
     * @deprecated use getURL instead
     * @return global identifier of the entity
     */
    Long getGUID();

    /**
     * Gets the URL of the matched entity
     *
     * @return the URL of the matched entity if getAssignmentResult is NEW or
     * REUSE, null otherwise. Notice that if the assignment result is NEW the
     * entity is not stored on the server.
     */
    @Nullable
    String getURL();

}
