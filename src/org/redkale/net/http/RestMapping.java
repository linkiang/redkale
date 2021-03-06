/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.net.http;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * 只能依附在Service实现类的public方法上  <br>
 * value默认为"/" + Service的类名去掉Service字样的小写字符串 (如HelloService，的默认路径为/hello)。  <br>
 * <p>
 * 详情见: https://redkale.org
 *
 * @author zhangjx
 */
@Inherited
@Documented
@Target({METHOD})
@Retention(RUNTIME)
@Repeatable(RestMappings.class)
public @interface RestMapping {

    /**
     * 是否屏蔽该方法的转换
     *
     * @return boolean
     */
    boolean ignore() default false;

    /**
     * 请求的方法名, 不能含特殊字符
     * 默认为方法名的小写(若方法名以createXXX、updateXXX、deleteXXX、queryXXX、findXXX、existsXXX且XXXService为Service的类名将只截取XXX之前)
     *
     * @return String
     */
    String name() default "";

    /**
     * 备注描述, 对应&#64;WebMapping.comment
     *
     * @return String
     */
    String comment() default "";

    /**
     * 是否鉴权，默认不鉴权, 对应&#64;AuthIgnore
     *
     * @return boolean
     */
    boolean auth() default false;

    /**
     * 操作ID值，鉴权时用到, 对应&#64;WebMapping.actionid
     *
     * @return int
     */
    int actionid() default 0;

    /**
     * 结果缓存的秒数, 为0表示不缓存, 对应&#64;HttpCacheable.seconds
     *
     * @return int
     */
    int cacheseconds() default 0;

    /**
     * 允许方法(不区分大小写),如:GET/POST/PUT,为空表示允许所有方法, 对应&#64;WebMapping.methods
     *
     * @return String[]
     */
    String[] methods() default {};

}
