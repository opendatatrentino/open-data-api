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
package eu.trentorise.opendata.semantics.model.knowledge.impl;

import eu.trentorise.opendata.semantics.model.knowledge.ISemanticText;
import eu.trentorise.opendata.semantics.model.knowledge.ISentence;
import eu.trentorise.opendata.semantics.model.knowledge.IWord;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * Class to hold semantically enriched text, suitable for data transfer to the
 * browser.
 *
 * @author David Leoni <david.leoni@unitn.it>
 * @date 11 Apr 2014
 */
@Immutable
public class SemanticText implements Serializable, ISemanticText {

    private String text;
    private Locale locale;

    private List<ISentence> sentences;

    public SemanticText() {
        this.text = "";
        this.locale = Locale.ENGLISH;
        this.sentences = new ArrayList<ISentence>();
    }

    /**
     * Creates SemanticText with the provided string. Locale is set to english
     * and the string is not enriched.
     *
     * @param text
     */
    public SemanticText(String text) {
        this();
        if (text == null) {
            throw new RuntimeException("Text in SemnaticText must not be null!");
        }
        this.text = text;
        this.locale = Locale.ENGLISH;
    }

    
    /**
     * @param sentences a list of sentences. Internally, a new copy of it is
     * created.
     */
    public SemanticText(String text, @Nullable Locale locale, List<ISentence> sentences) {
        this(text);
        
        this.locale = locale;

        List<ISentence> lst = new ArrayList<ISentence>();
        for (ISentence ss : sentences) {
            lst.add(ss);
        }
        this.sentences = Collections.unmodifiableList(lst);
    }

    /**
     * @param sentence a list of sentences. Internally, a new copy of it is
     * created.
     */
    public SemanticText(String text, Locale locale, final ISentence sentence) {
        this(text, locale, new ArrayList<ISentence>(){{add(sentence);}});
    }
    
    @Override
    public List<ISentence> getSentences() {
        return sentences;
    }

    @Override
    @Nullable 
    public Locale getLocale() {
        return locale;
    }

    @Override
    public String getText() {
        return text;
    }
    
    @Override
    public String getText(ISentence sentence) {
        return text.substring(sentence.getStartOffset(), sentence.getEndOffset());
    }

    @Override    
    public String getText(IWord word) {
        return text.substring(word.getStartOffset(), word.getEndOffset());
    }

}