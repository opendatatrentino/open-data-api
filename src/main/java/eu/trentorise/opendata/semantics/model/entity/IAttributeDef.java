/**
 * *****************************************************************************
 * Copyright 2012-2013 Trento Rise (www.trentorise.eu/)
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License (LGPL)
 * version 2.1 which accompanies this distribution, and is available at
 *
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 *******************************************************************************
 */
package eu.trentorise.opendata.semantics.model.entity;

import eu.trentorise.opendata.semantics.model.knowledge.IConcept;
import eu.trentorise.opendata.semantics.model.knowledge.IDict;
import javax.annotation.Nullable;

/**
 * The attribute definition stores information about the attributes, such as the
 * Name, the Concept and if the attribute is set or not.
 *
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Moaz Reyad <reyad@disi.unitn.it>
 * @author David Leoni <david.leoni@unitn.it>
 * @date Oct 27, 2014
 */
public interface IAttributeDef {
 
    /**
     * Gets the URL of the attribute definition
     *
     * @return a string that holds the URL of the attribute definition
     */
    String getURL();


    /**
     * Gets the attribute name in the given language
     *
     * @return the attribute name in the default languages if available
     */
    IDict getName();

    /**
     * Gets the data type of the attribute definition. Possible values for the
     * datatypes are listed in
     * {@link eu.trentorise.opendata.semantics.services.model.DataTypes} class.
     * @deprecated use {@link #getTypeSig()} instead
     * @return the data type as string
     */
    String getDataType();

    /**
     * Returns the owner entity type for this attribute definition 
     * @deprecated use {@link #getTypeSig()} instead
     * @return entity type URL
     */
     String getEtypeURL();  	

    /**
     * Return the entity type for the range, when the datatype is oe:structure or oe:entity     
     * @return the entity type URL if the data type is oe:structure or oe:entity, null otherwise
     * @deprecated use {@link #getTypeSig()} instead
     */     
     String getRangeEtypeURL();
     
     /**
      * Returns the type signature of the attribute definition.
      */
     ITypeSig getTypeSig();
     
    /**
     * Return the entity type for the range, when the datatype is oe:structure or oe:entity
     * @deprecated we only provide the URL within  {@link #getTypeSig()} 
     * @return the entity type if the data type is oe:structure or oe:entity, null otherwise
     */
    @Nullable 
    IEntityType getRangeEType();

    /**
     * Gets the concept of the attribute definition
     * @return the concept of attribute definition
     */
    @Nullable
    IConcept getConcept();

    /**
     * Gets the concept URL of the entity type
     * @deprecated Use {@link #getConcept()}} instead.
     * @return the concept URL of the entity type
     */
    String getConceptURL();

    /**
     * Gets the IsSet flag that tells if the attribute can hold a set of values
     * or it can hold only one value.
     *
     * @deprecated use {@link #getTypeSig() } instead
     * @return true if the attribute can hold a set or false if it can hold only
     * one value.
     */
    boolean isSet();

    /**
     * Tells if the attribute can hold a set of values.
     * @deprecated use {@link #getTypeSig() } instead
     * @return true if the attribute can hold a list of values
     */
    boolean isList();



    /**
     * Gets the regular expression that all the attribute values should follow
     * @deprecated Doesn't make much sense
     * 
     * @return the regular expression as string
     */
    String getRegularExpression();

    /**
     * Set the regular expression that all the attribute values should follow
     * @deprecated Doesn't make much sense
     *
     * @param regularExpression the regular expression as string
     */
    void setRegularExpression(String regularExpression);

    /**
     * Gets the IsMandatory flag that tells if the attribute is mandatory or not
     *
     * @return true if the attribute is mandatory or false if it is not
     * mandatory
     */
    boolean isMandatory();

       
    /**
     * Returns the entity type for the attribute definition 
     * @deprecated use {@link #getTypeSig() } instead
     * @return entity type
     */
     Long getEType();
    

    /**
     * Gets the Globally Unique Identifier (GUID) for the attribute definition
     * @deprecated use {@link #getURL()} instead
     * @return the Globally Unique Identifier (GUID) represented as Long
     */
    Long getGUID();
     
}
