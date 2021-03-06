/*
 * Copyright 2015 Trento Rise.
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
package eu.trentorise.opendata.semantics.test.services;

import com.google.common.collect.ImmutableList;
import eu.trentorise.opendata.commons.TodConfig;
import eu.trentorise.opendata.semantics.model.entity.Etype;
import eu.trentorise.opendata.semantics.services.AttrMapping;
import eu.trentorise.opendata.semantics.services.SchemaMapping;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author David Leoni
 */
public class SchemaMatchingTest {

    @BeforeClass
    public static void setUpClass() {
        TodConfig.init(SchemaMatchingTest.class);
    }
    
    @Test
    public void testAttrMapping(){        
        AttrMapping x1 = AttrMapping.builder().addSourcePath("schema","s").addTargetPath("b").setScore(1.0).build();
        AttrMapping x2 = AttrMapping.of(ImmutableList.of("schema","s"), ImmutableList.of("b"), 0.5);
        assertTrue(x1.compareTo(x2) > 0);
    }    
    
    @Test
    public void testSchemaMapping(){        
        SchemaMapping x1 = SchemaMapping.of(ImmutableList.of(AttrMapping.of()), Etype.of(), 1.0);
        SchemaMapping x2 = SchemaMapping.builder().addMappings(AttrMapping.of()).setTargetEtype(Etype.of()).setScore(0.5).build();
        assertTrue(x1.compareTo(x2) > 0);
    }        
}
