package radium.jdi;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.ClassPool;
import javassist.CtClass;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.ClassLoaderReference;
import com.sun.jdi.ClassType;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector.Argument;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.tools.jdi.SocketAttachingConnector;

public class ClassRedefiner {

    public static void redefineClassInVMFromClassFile(File classFile) throws IOException, IllegalConnectorArgumentsException {
        String className = getClassNameFromFile(classFile);

        System.out.println("Redefining " + className + " with " + classFile);

		//Map<ClassLoaderReference, List<ReferenceType>> referenceTypesByClassLoader = new HashMap<ClassLoaderReference, List<ReferenceType>>();
        VirtualMachine vm = connectToVM("localhost", 8000);

        ClassType classTypeToRedefine = findClassTypeInVM(vm, className, "org.eclipse.jetty.webapp.WebAppClassLoader");
        byte[] classBytes = readFile(classFile);

        Map<ReferenceType, byte[]> mapToRedefine = new HashMap<ReferenceType, byte[]>();
        mapToRedefine.put(classTypeToRedefine, classBytes);

        vm.redefineClasses(mapToRedefine);

        disconnectFromVM(vm);
    }

    public static String getClassNameFromFile(File classFile) throws FileNotFoundException, IOException, RuntimeException {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass(new FileInputStream(classFile));
        String className = ctClass.getName();
        return className;
    }

    public static ClassType findClassTypeInVM(VirtualMachine vm, String className, String classLoaderClassName) {
        for (ReferenceType referenceType : vm.allClasses()) {
            String referenceTypeClassName = referenceTypeClassName(referenceType);
            String referenceTypeClassLoaderClassName = classLoaderClassName(referenceType);
            if (referenceTypeClassName != null && referenceTypeClassLoaderClassName != null) {
                if (className.equals(referenceTypeClassName) && classLoaderClassName.equals(referenceTypeClassLoaderClassName)) {
                    return (ClassType) referenceType;
                }
            }
        }
        return null;
    }

    public static byte[] readFile(String filePath) throws IOException {
        File file = new File(filePath);
        return readFile(file);
    }

    public static byte[] readFile(File file) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));

        byte[] bytes = new byte[(int) file.length()];
        dataInputStream.readFully(bytes);
        dataInputStream.close();
        return bytes;
    }

    public static String referenceTypeClassName(ReferenceType referenceType) {
        String name = null;
        if (referenceType instanceof ClassType) {
            name = ((ClassType) referenceType).name();
        }
        return name;
    }

    public static String classLoaderClassName(ClassLoaderReference classLoaderReference) {
        if (classLoaderReference == null) {
            return null;
        }
        return referenceTypeClassName(classLoaderReference.referenceType());
    }

    public static String classLoaderClassName(ReferenceType referenceType) {
        return classLoaderClassName(referenceType.classLoader());
    }

    public static void disconnectFromVM(VirtualMachine virtualMachine) {
        virtualMachine.dispose();
    }

    public static SocketAttachingConnector chooseSocketAttachingConnector(VirtualMachineManager virtualMachineManager) {
        List<AttachingConnector> connectors = virtualMachineManager.attachingConnectors();
        for (AttachingConnector attachingConnector : connectors) {
            if (attachingConnector instanceof SocketAttachingConnector) {
                return (SocketAttachingConnector) attachingConnector;
            }
        }

        return null;
    }

    public static VirtualMachine connectToVM(String host, int port) throws IOException, IllegalConnectorArgumentsException {
        VirtualMachineManager virtualMachineManager = getVirtualMachineManager();
        SocketAttachingConnector connector = chooseSocketAttachingConnector(virtualMachineManager);
        Map<String, Argument> arguments = (Map<String, Argument>) connector.defaultArguments();

        arguments.get("hostname").setValue(host);
        arguments.get("port").setValue(Integer.toString(port));

        VirtualMachine virtualMachine = connector.attach(arguments);
        return virtualMachine;
    }

    public static VirtualMachineManager getVirtualMachineManager() {
        VirtualMachineManager virtualMachineManager = Bootstrap.virtualMachineManager();
        return virtualMachineManager;
    }

}
