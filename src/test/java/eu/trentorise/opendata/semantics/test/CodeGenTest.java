/* 
 * Copyright 2015 Trento Rise  (trentorise.eu) 
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
package eu.trentorise.opendata.semantics.test;

import com.google.common.collect.ImmutableList;
import eu.trentorise.opendata.commons.Dict;
import eu.trentorise.opendata.commons.TodConfig;
import eu.trentorise.opendata.commons.validation.Ref;
import eu.trentorise.opendata.semantics.services.SearchResult;
import eu.trentorise.opendata.semantics.services.TermSearchResult;
import eu.trentorise.opendata.semtext.MeaningKind;
import eu.trentorise.opendata.traceprov.data.TraceFile;
import eu.trentorise.opendata.traceprov.data.PropertyMapping;


import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author David Leoni
 */
public class CodeGenTest {

    @BeforeClass
    public static void setUpClass() {
        TodConfig.init(CodeGenTest.class);
    }


    @Test
    public void testDataModel() {
        assertEquals("a", SearchResult.of("a", Dict.of()).getId());
        
        assertEquals("a", TermSearchResult.of("a", Dict.of(), MeaningKind.UNKNOWN).getId());
        
        assertEquals(TraceFile.of().getMappings(), ImmutableList.of());

        TraceFile.builder().setMappings(
                ImmutableList.of(PropertyMapping.of(Ref.of(), ImmutableList.of("a", "b"))));        
                
    }

}
