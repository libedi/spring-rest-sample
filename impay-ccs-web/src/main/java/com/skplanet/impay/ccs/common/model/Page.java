/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.common.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Jibeom Jung
 */
public class Page<T> implements Iterable<T> ,Serializable{

    private static final long serialVersionUID = 7364112597415624108L;

    private List<T> content;

    private int total;
    
    private Map<String,Long> totalRowMap;   //리스트 total row의 값
    
    public Page( int total, List<T> content) {
        this.content = content;
        this.total = total;
    }
    
    public Page( int total, List<T> content, Map<String,Long> totalRowMap) {
        this.content = content;
        this.total = total;
        this.totalRowMap = totalRowMap;
    }

    public List<T> getContent() { return Collections.unmodifiableList(content); }
        
    public long getTotal() { return total; }
    
    public Map<String,Long> getTotalRowMap() { return totalRowMap; }

    @Override
    public Iterator<T> iterator() {
        return this.content.iterator();
    }
}