/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wikift.common.utils;

import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;

/**
 * ReflectUtils <br/>
 * 描述 : ReflectUtils <br/>
 * 作者 : qianmoQ <br/>
 * 版本 : 1.0 <br/>
 * 创建时间 : 2018-01-24 下午2:53 <br/>
 * 联系作者 : <a href="mailTo:shichengoooo@163.com">qianmoQ</a>
 */
public class ReflectUtils {

    /**
     * 抽取属性值
     *
     * @param filed  需要抽取的属性
     * @param object 抽取属性的类
     * @return 属性值
     */
    public static Object getFieldValue(String filed, Object object) {
        Object result = null;
        if (ObjectUtils.isEmpty(object)) {
            return null;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            // 设置访问private属性
            fields[j].setAccessible(true);
            if (fields[j].getName().equals(filed)) {
                try {
                    result = fields[j].get(object);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
