/* 
 * Copyright 2013-2015   Trento Rise   trentorise.eu
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
package eu.trentorise.opendata.semantics.model.knowledge.impl;

import eu.trentorise.opendata.semantics.model.knowledge.IDict;
import eu.trentorise.opendata.semantics.model.knowledge.ISemanticText;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 *
 * @author David Leoni
 */
/**
 * An immutable dictionary copyOf translations
 *
 * @author David Leoni
 */
@Immutable
public class Dict implements IDict {

	private static final Logger LOG = Logger.getLogger(Dict.class.getName());
	
    private Map<Locale, List<String>> translations;

    public Dict() {
        translations = new HashMap<Locale, List<String>>();
    }

    protected Dict(@Nullable IDict dict) {
        this();
        if (dict != null) {
            for (Locale loc : dict.getLocales()) {
                List<String> names = new ArrayList<String>();
                for (String s : dict.getStrings(loc)) {
                    names.add(s);
                }
                this.translations.put(sanitizeLocale(loc), Collections.unmodifiableList(names));
            }
        }
    }

    private Dict(Map<Locale, List<String>> inputMap) {
        this();
        if (inputMap == null){
            throw new IllegalArgumentException("null inputMap is not allowed!");
        }
        this.translations = inputMap;
    }

    /**
     * Sets {@link java.util.Locale.ENGLISH} translation to provided text
     *
     * @param text the given text
     */
    public Dict(final String text) {
        this(text, Locale.ENGLISH);
    }

    /**
     * Sets english translation to provided text
     *
     * @param text the given text in the fiven locale
     * @param locale the locale of the provided text
     */
    public Dict(final String text, Locale locale) {
        this();
        if (text == null){
            throw new IllegalArgumentException("null text is not allowed in a " + Dict.class.getSimpleName());
        }
        /*
        todo enable when we fix https://github.com/opendatatrentino/openentity-api/issues/7
        if (locale == null){
            throw new IllegalArgumentException("null locale is not allowed in a " + Dict.class.getSimpleName());
        
        }    */
            
        
        translations.put(sanitizeLocale(locale), new ArrayList() {
            {
                add(text);
            }
        });
    }

    /**
     * Concerts null locale to {@link Locale#ROOT}
     * @since 0.26.3
     * @param locale
     */
    private Locale sanitizeLocale(@Nullable Locale locale){
    	if (locale == null){
    		LOG.warning("Found null locale, converting it to Locale.ROOT (the corresponding language tag is the empty string)");
    		return Locale.ROOT;
    	} else {
    		return locale;
    	}
    }
    
    /**
     * Sets translations to provided locale
     */
    public Dict(final List<String> texts, Locale locale) {
        this.translations = new HashMap();
        List<String> ts = new ArrayList();
        
        for (String s : texts) {
            if (s == null){
                throw new IllegalArgumentException("null texts are not allowed in a " + Dict.class.getSimpleName());
            }
            ts.add(s);
        }        
        this.translations.put(sanitizeLocale(locale), texts);
    }

    public Set<Locale> getLocales() {
        return translations.keySet();
    }

    public boolean contains(String text) {

        for (Locale loc : getLocales()) {
            String lowText = text.toLowerCase(loc);
            for (String t : translations.get(loc)) {
                if (t.toLowerCase(loc).contains(lowText)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @return a new dictionary. The original one is not mutated.
     */
    public Dict putTranslation(Locale locale, List<String> strings) {
        HashMap<Locale, List<String>> newTranslations = new HashMap<Locale, List<String>>();

        List<String> lst = new ArrayList<String>();
        for (String s : strings) {
            if (s == null){
                throw new IllegalArgumentException("null translations are not allowed in a Dict!");
            }
            lst.add(s);
        }
       
        for (Locale loc : translations.keySet()) {
            newTranslations.put(loc, translations.get(loc));
        }
        
        // todo check null locale https://github.com/opendatatrentino/openentity-api/issues/7    
        newTranslations.put(sanitizeLocale(locale), Collections.unmodifiableList(lst));
                        
        return new Dict(newTranslations);
    }

    /**
     *
     * @return a new dictionary. The original one is not mutated.
     */
    public Dict putTranslation(Locale locale, String string) {
        HashMap<Locale, List<String>> newTranslations = new HashMap<Locale, List<String>>();

        List<String> lst = new ArrayList<String>();

        if (string == null){
            throw new IllegalArgumentException("null translations are not allowed in a Dict!");
        }
        lst.add(string);

        for (Locale loc : translations.keySet()) {
            newTranslations.put(loc, translations.get(loc));
        }
        newTranslations.put(sanitizeLocale(locale), Collections.unmodifiableList(lst));

        return new Dict(newTranslations);
    }

    @Nullable
    public String getString(Locale locale) {
        List<String> as = translations.get(locale);
        if (as == null) {
            return null;
        }
        if (as.isEmpty()) {
            return null;
        } else {
            return as.get(0);
        }
    }

    /**
     * A valid string is not null, non-empty, and is not made copyOf the evil string
 "null". 
     *
     */
    private boolean isValid(String s) {
        return s != null
                && !s.isEmpty()
                && !s.equals("null");
    }

    /*
     * Returns the first valid translation (see {@link isValid(String)} in the
     * given locale. If it can't find it, null is returned.
     */
    @Nullable
    public String getValidTranslation(Locale locale) {
        if (translations.containsKey(locale)) {
            List<String> as = translations.get(locale);
            if (as == null) {
                return null;
            }
            if (as.isEmpty()) {
                return null;
            } else {
                for (String s : translations.get(locale)) {
                    if (isValid(s)) {
                        return s;
                    }
                }
            }
        }

        return null;
    }

    public List<String> getStrings(Locale locale) {
        if (translations.containsKey(locale)) {
            return translations.get(locale);
        } else {
            return new ArrayList<String>();
        }
    }

    /**
     * Returns true if there is at least one non-empty valid translation, otherwise returns false
     */
    public boolean isEmpty() {
        for (Locale locale : getLocales()) {
            String t = getValidTranslation(locale);
            if (t != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Pads white spaces until maxLength is reached
     *
     * @param msg the message to pad with spaces
     * @param maxLength length after which msg is truncated
     * @return the padded msg
     */
    private static String padLeft(String msg, int maxLength) {

        String nmot;
        if (msg.length() > maxLength) {
            nmot = msg.substring(0, msg.length() - 3) + "...";
        } else {
            nmot = String.format("%" + maxLength + "s", msg);
        }
        return nmot;
    }

    /**
     * Merges dictionary with provided one to create a new dictionary.
     *
     * @param dict
     * @return a new dictionary resulting from the merge.
     */    
    public Dict merge(IDict dict) {
        Dict ret = new Dict(this);
        for (Locale locale : dict.getLocales()) {
        	Locale sanitizedlocale = sanitizeLocale(locale);
            if (ret.translations.containsKey(sanitizedlocale)) {
                for (String t : dict.getStrings(locale)) {
                    if (!ret.translations.get(sanitizedlocale).contains(t)) {
                        ret.translations.get(sanitizedlocale).add(t);
                    }
                }
            } else {
                ret.translations.put(sanitizedlocale, dict.getStrings(locale));
            }
        }
        return ret;
    }

    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("{\n");
        for (Locale loc : translations.keySet()) {
            sb.append(padLeft(loc.toString(), 10))
                    .append(": [");
            boolean first = true;
            for (String t : translations.get(loc)) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(t);
            }
            sb.append("]\n");

        }
        sb.append("}\n");
        sb.append("\n");
        return sb.toString();
    }

    public int translationsCount() {
        int count = 0;
        for (Locale loc : translations.keySet()) {
            count += translations.get(loc).size();
        }
        return count;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.translations != null ? this.translations.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dict other = (Dict) obj;
        if (this.translations != other.translations && (this.translations == null || !this.translations.equals(other.translations))) {
            return false;
        }
        return true;
    }

    @Override
    public ISemanticText toSemText(List<Locale> locales){
        for (Locale loc : locales) {
            String t = getValidTranslation(loc);
            if (t != null) {
                return new SemanticText(t, loc);
            }
        }
        String t = getValidTranslation(Locale.ENGLISH);
        if (t != null) {
            return new SemanticText(t, Locale.ENGLISH);
        }

        for (Locale loc : getLocales()) {
            String other = getValidTranslation(loc);
            if (t != null) {
                return new SemanticText(other, loc);
            }
        }
        return new SemanticText("", Locale.ENGLISH);
    }    
    
    
    /**
     * @see #toSemText(java.util.List) 
     */
    public String prettyString(List<Locale> locales) {
        return toSemText(locales).getText();
    }        

    /** Factory method - if input is already a Dict, it is returned unchanged */
    public static Dict copyOf(IDict d){
        if (d instanceof Dict){
            return (Dict) d;
        } else {
            return new Dict(d);
        }
    };
       
}
