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
package eu.trentorise.opendata.semantics;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import eu.trentorise.opendata.commons.validation.Preconditions;
import static eu.trentorise.opendata.commons.OdtUtils.checkNotDirtyUrl;
import eu.trentorise.opendata.semantics.model.entity.IAttribute;
import eu.trentorise.opendata.semantics.model.entity.IAttributeDef;
import eu.trentorise.opendata.semantics.model.entity.IEntity;
import eu.trentorise.opendata.semantics.model.entity.IEntityType;
import eu.trentorise.opendata.semantics.model.entity.IStructure;
import eu.trentorise.opendata.semantics.model.entity.IValue;
import eu.trentorise.opendata.semantics.model.knowledge.IConcept;
import eu.trentorise.opendata.semantics.services.IEkb;
import eu.trentorise.opendata.semantics.services.SchemaMapping;
import eu.trentorise.opendata.semantics.services.AssignmentResult;
import eu.trentorise.opendata.semantics.services.IIDResult;

/**
 * Class to collect checkers for implementations of Open Entity API interfaces.
 *
 * @author David Leoni
 */
public final class Checker {

    private IEkb ekb;

    
    
    private Checker(IEkb ekb) {
        checkNotNull(ekb);
        this.ekb = ekb;
    }

    public static Checker of(IEkb ekb){
        return new Checker(ekb);
    }
    
    private void checkScore(Float score, String prependedErrorMessage) {
        checkNotNull(score, "%s Found null score!");
        Preconditions.checkScore(score, prependedErrorMessage);
    }

    /**
     * Checks if provided schema correspondence complies with open entity specs.
     *
     * @param schemaMapping to check
     * @throws IllegalArgumentException if provided correspondence is not
     * conformant to OpenEntity specs.
     */
    public void checkSchemaMapping(SchemaMapping schemaMapping) {
        if (schemaMapping == null) {
            throw new IllegalArgumentException("Schema mapping is null!");
        }

        try {
            checkEntityType(schemaMapping.getTargetEtype());
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Invalid etype in schema mapping!", ex);
        }
    }

    /**
     * Checks if provided entity type complies with open entity specs.
     *
     * @param etype the entity type to check
     * @throws IllegalArgumentException if provided etype is not conformant to
     * OpenEntity specs.
     */
    public void checkEntityType(IEntityType etype) {
        if (etype == null) {
            throw new IllegalArgumentException("Found null etype!");
        }
        checkNotDirtyUrl(etype.getURL(), "Invalid etype URL!");

        checkNotNull(etype.getName(), "Invalid etype name!");

        checkNotDirtyUrl(etype.getConceptURL(), "Found invalid concept for etype " + etype.getURL());

        /**
         * not supported for now if (etype.getUniqueIndexes() == null){ throw
         * new IllegalArgumentException("Found null unique indexes for etype " +
         * etype.getURL()); }
         */
        for (IAttributeDef attrDef : etype.getAttributeDefs()) {
            try {
                checkAttributeDef(attrDef);
            }
            catch (Exception ex) {
                throw new IllegalArgumentException("Found invalid attr def in etype " + etype.getURL(), ex);
            }
        }

        try {
            etype.getNameAttrDef();
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Found problem in getNameAttrDef() for etype with URL " + etype.getURL(), ex);
        }

        try {
            etype.getDescriptionAttrDef();
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Found problem in getNameAttrDef() for etype with URL " + etype.getURL(), ex);
        }

    }

    /**
     * Checks if provided attribute def complies with open entity specs.
     *
     * @param attrDef the attribute definition to check
     * @throws IllegalArgumentException if provided attrDef is not conformant to
     * OpenEntity specs.
     */
    public void checkAttributeDef(IAttributeDef attrDef) {
        if (attrDef == null) {
            throw new IllegalArgumentException("Found null attribute def!");
        }
        if (attrDef.getURL() == null) {
            throw new IllegalArgumentException("Found null URL for attribute def " + attrDef.getName());
        }
        if (attrDef.getURL().length() == 0) {
            throw new IllegalArgumentException("Found empty URL for attribute def " + attrDef.getName());
        }

        if (attrDef.getName() == null) {
            throw new IllegalArgumentException("Found null name dict for attribute def " + attrDef.getURL());
        }

        checkNotDirtyUrl(attrDef.getEtypeURL(), "Found invalid etype URL for attribute def " + attrDef.getURL());

        if (attrDef.getDatatype() == null) {
            throw new IllegalArgumentException("Found null datatype for attribute def " + attrDef.getURL());
        }
        if ((attrDef.getDatatype().equals(DataTypes.STRUCTURE) || attrDef.getDatatype().equals(DataTypes.ENTITY))) {

            checkNotDirtyUrl(attrDef.getRangeEtypeURL(), "Attribute def " + attrDef.getURL() + " with parent etype " + attrDef.getEtypeURL() + " is of datatype " + attrDef.getDatatype() + ", but is has invalid getRangeEtypeURL()");

        }

        try {
            checkNotNull(attrDef.getConceptURL());
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Found invalid concept for attr def " + attrDef.getURL(), ex);
        }

    }

    /**
     * Checks if provided ekb complies with open entity specs.
     *
     * @param ekb the entity knowledge base to check
     * @throws IllegalArgumentException if provided ekb is not conformant to
     * OpenEntity specs.
     */
    public void checkEkbQuick(IEkb ekb) {

        if (ekb == null) {
            throw new IllegalArgumentException("Found null ekb!");
        }

        if (ekb.getDefaultLocales() == null) {
            throw new IllegalArgumentException("Found null locales list in ekb " + ekb.getClass().getCanonicalName());
        }

        if (ekb.getDefaultLocales().get(0) == null) {
            throw new IllegalArgumentException("Found null first locale in ekb " + ekb.getClass().getCanonicalName());
        }
    }

    public void checkIDResult(IIDResult idResult) {

        if (idResult == null) {
            throw new IllegalArgumentException("Found null idResult!");
        }

        if (idResult.getAssignmentResult() == null) {
            throw new IllegalArgumentException("Found null assignment result in idResult: " + idResult);
        }

        if (idResult.getEntities() == null) {
            throw new IllegalArgumentException("Found null result entities!  idResult is " + idResult);
        }

        if (AssignmentResult.REUSE.equals(idResult.getAssignmentResult())
                || AssignmentResult.NEW.equals(idResult.getAssignmentResult())) {

            checkNotDirtyUrl(idResult.getURL(), "Found invalid URL in idResult! AssignmentResult is " + idResult.getAssignmentResult() + " in idResult " + idResult);

            for (IEntity entity : idResult.getEntities()) {
                try {                    
                    checkEntity(entity);
                }
                catch (Exception ex) {
                    throw new IllegalArgumentException("Failed integrity check on entity " + entity + " in idResult " + idResult, ex);
                }
            }

        }

        if (AssignmentResult.REUSE.equals(idResult.getAssignmentResult())) {

            checkEntity(idResult.getResultEntity());

            if (idResult.getEntities().isEmpty()) {
                throw new IllegalArgumentException("Found empty entities in idResult with REUSE. idResult is " + idResult);
            }
        }

        if (AssignmentResult.NEW.equals(idResult.getAssignmentResult())) {

            checkEntity(idResult.getResultEntity(), true);

            if (!idResult.getEntities().isEmpty()) {
                throw new IllegalArgumentException("Found non-empty entities in idResult with NEW. idResult is " + idResult);
            }
        }

    }

    /**
     * Checks if provided entity complies with open entity specs.
     *
     * @param entity to check
     * @throws IllegalArgumentException if provided entity is not conformant to
     * OpenEntity specs.
     */
    public void checkEntity(IEntity entity) {

        checkEntity(entity, false);
    }

    /**
     * Checks if provided entity complies with open entity specs. For synthetic
     * entities some checks will be skipped.
     *
     * @param entity to check
     * @param synthetic if true URL and local ids of attributes and values will
     * not be checked
     * @throws IllegalArgumentException if provided entity is not conformant to
     * OpenEntity specs.
     */
    public void checkEntity(IEntity entity, boolean synthetic) {

        if (entity == null) {
            throw new IllegalArgumentException("Found null entity!");
        }

        IEntityType etype = ekb.getEntityTypeService().readEntityType(entity.getEtypeURL());
        
        if (etype == null) {
            throw new IllegalArgumentException("Found null etype!");
        }

        try {
            checkStructure(entity,  synthetic);
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Found invalid structural properties of entity " + entity.getUrl(), ex);
        }

        if (!synthetic) {
            checkNotDirtyUrl(entity.getUrl(), "Found invalid URL in entity " + entity);
        }

        try {
            checkNotNull(entity.getName());
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Found invalid name in entity " + entity.getUrl(), ex);
        }

        try {
            checkNotNull(entity.getDescription());
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Found invalid description in entity " + entity.getUrl(), ex);
        }

    }

    /**
     * Checks if provided structure complies with open entity specs.
     *
     * @param structure to check
     * @throws IllegalArgumentException if provided structure is not conformant
     * to OpenEntity specs.
     */
    public void checkStructure(IStructure structure) {

        checkStructure(structure,  false);

    }

    /**
     * Checks if provided structure complies with open entity specs. For
     * synthetic structures some checks will be skipped.
     *
     * @param structure the structure to check
     * @param synthetic if true URLs and local ids of attributes and values will
     * not be checked. The URL of the structure is not checked anyway.
     */
    public void checkStructure(IStructure structure, boolean synthetic) {
        if (structure == null) {
            throw new IllegalArgumentException("Found null structure!");
        }

        IEntityType etype = ekb.getEntityTypeService().readEntityType(structure.getEtypeURL());
        
        if (etype == null) {
            throw new IllegalArgumentException("Found null etype!");
        }

        checkNotDirtyUrl(structure.getEtypeURL(), "Found invalid entity type URL in structure " + structure.getUrl());

        checkArgument(etype.getURL().equals(structure.getEtypeURL()), "Provided etype " + etype.getURL() + " is not the one referenced by the structure, which is " + structure.getEtypeURL());

        if (structure.getStructureAttributes() == null) {
            throw new IllegalArgumentException("Found null attributes in structure " + structure.getUrl());
        }

        for (IAttribute attr : structure.getStructureAttributes()) {
            try {
                checkAttribute(attr,  synthetic);
            }
            catch (Exception ex) {
                throw new IllegalArgumentException("Found invalid attribute in structure " + structure.getUrl(), ex);
            }
        }
    }

    /**
     * Checks if provided attribute complies with open entity specs.
     *
     * @param attribute to check
     * @param synthetic if true URL and local ids of attributes and values will
     * not be checked
     * @throws IllegalArgumentException if provided attribute is not conformant
     * to OpenEntity specs.
     */
    public void checkAttribute(IAttribute attribute, boolean synthetic) {
        if (attribute == null) {
            throw new IllegalArgumentException("Found null attribute!");
        }

        IAttributeDef attrDef = ekb.getEntityTypeService().readAttrDef(attribute.getAttrDefUrl());

        if (attrDef == null) {
            throw new IllegalArgumentException("Found null attr def!");
        }

        if (!synthetic && attribute.getLocalID() == null) {
            throw new IllegalArgumentException("Found null local ID in attribute " + attribute);
        }

        if (attribute.getAttrDefUrl() == null) {
            throw new IllegalArgumentException("Found null attribute definition in attribute " + attribute.getLocalID());
        }

        if (attribute.getValues() == null) {
            throw new IllegalArgumentException("Found null values list in attribute " + attribute.getLocalID());
        }

        if (attribute.getValuesCount() != attribute.getValues().size()) {
            throw new IllegalArgumentException("Found inconsistent values count in attribute " + attribute.getLocalID() + ". ValuesCount = " + attribute.getValuesCount() + ". attr.getValues.().size() = " + attribute.getValues().size());
        }

        for (IValue val : attribute.getValues()) {
            try {
                checkValue(val, attrDef,  synthetic);
            }
            catch (Exception ex) {
                throw new IllegalArgumentException("Found invalid value in attribute " + attribute.getLocalID(), ex);
            }
        }

    }

    /**
     * Checks if provided attribute complies with open entity specs.
     *
     * @param attribute to check
     * @throws IllegalArgumentException if provided attribute is not conformant
     * to OpenEntity specs.
     */
    public void checkAttribute(IAttribute attribute) {
        checkAttribute(attribute, false);

    }

    /**
     * Checks if provided value complies with open entity specs.
     *
     * @param value to check
     * @throws IllegalArgumentException if provided value is not conformant to
     * OpenEntity specs.
     */
    public void checkValue(IValue value, IAttributeDef attrDef) {
        checkValue(value, attrDef, false);
    }

    /**
     * Checks if provided value complies with open entity specs.
     *
     * @param value to check
     * @throws IllegalArgumentException if provided value is not conformant to
     * OpenEntity specs.
     */
    public void checkValue(IValue value, IAttributeDef attrDef, boolean synthetic) {

        checkNotNull(attrDef);
        
        String datatype = attrDef.getDatatype();

        if (value == null) {
            throw new IllegalArgumentException("Found null value!");
        }

        if (!synthetic && value.getLocalID() == null) {
            throw new IllegalArgumentException("Found null local ID in value " + value);
        }

        if (value.getValue() == null) {
            throw new IllegalArgumentException("Found null object in value " + value.getLocalID());
        }
//here
        if (DataTypes.getDataTypes().get(datatype) == null) {
            throw new IllegalArgumentException("Found unsupported datatype " + datatype + " in value " + value.getValue() + ". Its class is " + value.getValue().getClass().getName());
        }

        if (!(DataTypes.getDataTypes().get(datatype).isInstance(value.getValue()))) {
            throw new IllegalArgumentException("Found value not corresponding to its datatype " + datatype + ". Value is " + value.getValue() + ". Its class is " + value.getValue().getClass().getName());
        }

        if (DataTypes.STRUCTURE.equals(datatype)) { // for structures we do deep check
            IStructure s = (IStructure) value.getValue();
            if (!attrDef.getRangeEtypeURL().equals(s.getEtypeURL())) {
                throw new IllegalArgumentException("Found structure value with value ID " + value.getLocalID() + " having etype URL different from its attribute rangeEtypeURL! "
                        + "\nStructure etype URL: " + s.getEtypeURL() + "\nAttribute rangeEtypeURL: " + attrDef.getRangeEtypeURL());
            }
            checkStructure(s, synthetic);
        }

        if (DataTypes.ENTITY.equals(datatype)) { // for entities we only check URL and name            
            IEntity entity = (IEntity) value.getValue();
            if (!synthetic) {

                checkNotDirtyUrl(entity.getUrl(), "Found invalid URL in entity inside value with local ID: " + value.getLocalID());

            }

            checkNotNull(entity.getName(), "Found invalid name in entity with URL " + entity.getUrl() + " inside value with local ID: " + value.getLocalID());

        }

    }

    /**
     * Checks if provided concept complies with open entity specs.
     *
     * @param concept to check
     * @throws IllegalArgumentException if provided concept is not conformant to
     * OpenEntity specs.
     */
    public void checkConcept(IConcept concept) {
        if (concept == null) {
            throw new IllegalArgumentException("Found null concept!");
        }

        checkNotDirtyUrl(concept.getURL(), "Found invalid URL in concept " + concept);

        try {
            checkNotNull(concept.getDescription());
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Found invalid description in concept " + concept.getURL(), ex);
        }

        try {
            checkNotNull(concept.getName());
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Found invalid name in concept " + concept.getURL(), ex);
        }

    }

}
