// 
// 
// 

package test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import net.sf.cglib.beans.BeanGenerator;
import java.util.Map;
import net.sf.cglib.beans.BeanMap;

public class DynamicBean
{
    private Object object;
    private BeanMap beanMap;
    
    public DynamicBean() {
        this.object = null;
        this.beanMap = null;
    }
    
    public DynamicBean(final Map propertyMap) {
        this.object = null;
        this.beanMap = null;
        this.object = this.generateBean(propertyMap);
        this.beanMap = BeanMap.create(this.object);
    }
    
    public void setValue(final String property, final Object value) {
        this.beanMap.put((Object)property, value);
    }
    
    public Object getValue(final String property) {
        return this.beanMap.get((Object)property);
    }
    
    public Object getObject() {
        return this.object;
    }
    
    private Object generateBean(final Map propertyMap) {
        final BeanGenerator generator = new BeanGenerator();
        final Set keySet = propertyMap.keySet();
        for (final Object key : keySet) {
            generator.addProperty((String)key, (Class)propertyMap.get(key));
        }
        return generator.create();
    }
    
    public static void main(final String[] args) throws ClassNotFoundException {
        final HashMap propertyMap = new HashMap();
        propertyMap.put("id", Class.forName("java.lang.Integer"));
        propertyMap.put("name", Class.forName("java.lang.String"));
        propertyMap.put("address", Class.forName("java.lang.String"));
        final DynamicBean bean = new DynamicBean(propertyMap);
        bean.setValue("id", new Integer(123));
        bean.setValue("name", "454");
        bean.setValue("address", "789");
        final Object object = bean.getObject();
        final Class clazz = object.getClass();
        final Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; ++i) {}
    }
}
