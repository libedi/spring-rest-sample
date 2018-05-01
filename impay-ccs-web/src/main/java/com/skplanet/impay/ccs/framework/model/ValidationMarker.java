/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.impay.ccs.framework.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Jibeom Jung
 */
public class ValidationMarker {
    public interface Create {
    }

    public interface Retrieve {
    }

    public interface Update {
    }

    public interface Delete {
    }
    
    /** toString 할 때 모델에 정보값을 로그에 출력 by @KHJIN */
    
    @Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE );
	}
}
