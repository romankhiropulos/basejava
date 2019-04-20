import model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("uuid1", "Ivan");
        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(resume));
        field.set(resume, "new");
        System.out.println(resume);

        field.set(resume, "newMethod");
        Class<? extends Resume> resumeClass = resume.getClass();
        Method method = resumeClass.getMethod("toString");
        System.out.println(method.invoke(resume));



    }
}
