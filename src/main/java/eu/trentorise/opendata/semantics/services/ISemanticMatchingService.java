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

import eu.trentorise.opendata.semantics.model.knowledge.IResourceContext;
import eu.trentorise.opendata.semantics.model.knowledge.ITableResource;
import eu.trentorise.opendata.semantics.services.model.ISchemaCorrespondence;
import java.util.List;

/**
 * A service that performs semantic matching between two schemas
 *
 * @deprecated use {@link ISchemaMatchingService} instead
 * 
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Moaz Reyad <reyad@disi.unitn.it>
 * @author David Leoni <david.leoni@unitn.it>
 * @author Ivan Tankoyeu <tankoyeu@disi.unitn.it>
 * 
 */
public interface ISemanticMatchingService {

    /**
     * Given a resource in tabular format guesses a target
     * entitytype and returns a matching between the source headers and the
     * attributes of the target entity type. 
     *
     * @param resourceContext the context of the resource as found in the catalog of provenance
     * @param tableResource the names of the resource columns     
     * @return - a list of scored correspondences ordered in decreasing order of scoring.
     */   
    List<ISchemaCorrespondence> matchSchemas(IResourceContext resourceContext, ITableResource tableResource);
    
}
