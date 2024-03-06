package org.TechnicalSupport.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageableUtils {

    public static PageRequest getPageable(int page, int countOnPage, Sort.Direction direction, String sortParam) {
        return PageRequest.of(page, countOnPage, Sort.by(direction, sortParam));
    }
}

