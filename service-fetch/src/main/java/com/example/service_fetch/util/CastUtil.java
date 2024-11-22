/*******************************************************************************
 * Class        :className
 * Created date :2024/08/20
 * Lasted date  :2024/08/20
 * Author       :TamTH1
 * Change log   :2024/08/20 01-00 TamTH1 create a new
******************************************************************************/
package com.example.service_fetch.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
public class CastUtil {
    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
    List<T> r = new ArrayList<T>(c.size());
    for(Object o: c)
      r.add(clazz.cast(o));
    return r;
}
}
