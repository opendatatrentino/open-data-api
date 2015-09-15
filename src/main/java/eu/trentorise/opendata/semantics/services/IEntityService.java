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

import eu.trentorise.opendata.semantics.model.entity.Entity;

import java.io.Writer;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

/**
 * Entity services allow CRUD on entities and attributes, and also exporting in
 * various formats.
 *
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Moaz Reyad <reyad@disi.unitn.it>
 * @author David Leoni <david.leoni@unitn.it>
 * @author Ivan Tankoyeu <tankoyeu@disi.unitn.it>
 *
 *
 */
public interface IEntityService {

    /**
     * Creates an entity
     *
     * @param entity the entity to be created. Will not be changed by the
     * method. URL of the provided entity will be ignored. If an equal entity is
     * already present in the server, a duplicate with different URL will be
     * created. All linked entities must exist on the server.
     * @return a new entity with newly assigned URL
     */
    Entity createEntity(Entity entity);

    /**
     * Updates an entity. For values of type Dict, translations provided in this
     * entity are going to be merged with translations already present in the
     * Ekb. TODO need to specify MUCH better
     *
     * @param entity the entity to be updated
     * @throws eu.trentorise.opendata.semantics.NotFoundException if the entity
     * doesn't exists
     */
    void updateEntity(Entity entity);

    /**
     * Deletes an entity.
     *
     * @param URL the URL of the entity to delete.
     * @throws OpenEntityNotFoundException if the entity does not exists.
     */
    void deleteEntity(String URL);

    /**
     * Reads an entity given its URL
     *
     * @param URL the URL of the entity to read
     * @return the entity identified by this URL
     * @throws OpenEntityNotFoundException if the entity does not exists.
     */
    Entity readEntity(String URL);

    /**
     * Reads an entities given their URLs
     *
     * @param entityIds the URLs of the entity to read
     * @return the list of entities identified by the URL.
     * @throws OpenEntityNotFoundException if any of the entities is not found.
     */
    List<Entity> readEntities(List<String> entityIds);

    /**
     * Writes the given entities in rdf format into the provided writer.
     *
     * @param entityIds the URLs of the entities to export. If list is empty, an
     * IllegalArgumentException is thrown.
     * @param writer A writer to store the generated rdf
     */
    void exportToRdf(List<String> entityIds, Writer writer);

    /**
     * Writes the given entities in rdf format into the provided writer.
     *
     * @param entityIds the URLs of the entities to export. If list is empty, an
     * IllegalArgumentException is thrown.
     * @param writer A writer to store the generated jsonld
     */
    void exportToJsonLd(List<String> entityIds, Writer writer);

    /**
     * Writes the given entities in rdf format into the provided writer.
     *
     * @param entityURLs the URLs of the entities to export. If list is empty,
     * an IllegalArgumentException is thrown.
     * @param writer A writer to store the generated csv
     */
    void exportToCsv(List<String> entityURLs, Writer writer);

    /**
     * Returns a list of possible entities with name similar to provided partial
     * name. Returned entities will belong to provided etype, if any.
     *
     * @param partialName a partial entity name. It is assumed to be in one of
     * the default locales of the ekb.
     * @param etypeURL The url of the entity type. Enities returned will be
     * instances of that etype (or its descendants).
     * @return a list of candidate entities, ordered by probability. The first
     * one is the most probable.
     */
    List<SearchResult> searchEntities(String partialName, @Nullable String eTypeURL, Locale locale);

    /**
     * Returns whether or not the URL was generated during calls to assign URL
     * for deduplication purposes.
     */
    boolean isTemporaryURL(String temporaryEntityURL);

}
