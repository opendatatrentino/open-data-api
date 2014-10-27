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

import javax.annotation.Nullable;
import java.util.List;

/**
 *
 * A complex structure that is represented by the set of attribute.
 *
 * @author Ivan Tankoyeu <tankoyeui@disi.unitn.it>
 * @author David Leoni <david.leoni@unitn.it>
 * @date Oct 27, 2014
 */
public interface IStructure {

    /**
     * Gets the URL of the object
     *
     * @return a string that holds the URL of the object
     */
    String getURL();


    /**
     * Gets the values contained in the structure for the given attribute def.
     * No value of the given attribute def is present in the structure, an empty
     * list is returned.
     */
    List<? extends IValue> getValues(String attrDefURL);
    
    /**
     * Returns all the values contained in the structure.
     */
    List<? extends IValue> getValues();    


    /**
     * Gets the structure type
     *
     * @return the structure type
     */
    IEntityType getEtype();

    /**
     * Gets the structure type URL     
     * @deprecated use {@link #getEtype() instead}
     */
    @Nullable
    String getEtypeURL();

    
    /**
     * Sets the URL of the object
     *
     * @deprecated we don't need methods to change objects
     * @param url a string that holds the URL of the object
     */
    void setURL(String url);
    
    
    /**
     * Gets the external identifier of the structure
     *
     * @deprecated use {@link #getValues()} instead
     *
     * @return a string representing the external identifier of the structure
     */
    List<IAttribute> getStructureAttributes();    
    
    /**
     * Sets the list of attributes in this structure
     *
     * @param attributes the list of attributes to be set in the structure
     * @deprecated Don't want setters in interfaces
     */
    void setStructureAttributes(List<IAttribute> attributes);
    
    /**
     * Sets the structure type
     *
     * @deprecated we don't need methods to change objects
     * @param type the structure type
     */
    void setEtype(IEntityType type);

    /**
     * Gets an attribute from the structure.
     *
     * @param attrDefURL the URL of the attribute definition corresponding to
     * the desired attribute.
     * @deprecated attributes are deprecated. Use {@link #getValues(java.lang.String)} instead
     * @return the attribute corresponding to the given attribute def, if
     * present. Returns null otherwise.
     */
    @Nullable
    IAttribute getAttribute(String attrDefURL);
    
   /**
     * Gets the local identifier for the entity
     *
     * @deprecated use getURL() instead
     * @return the local identifier as Long
     */
    Long getLocalID();    
}
