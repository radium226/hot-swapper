package radium.hotswapper;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.Connector.Argument;

public class HotSwapper {
	private VirtualMachine vm;

	public HotSwapper() { 
        	super();
        }

	public void connect(String name) throws Exception {
		connect(null, null, name);
	}

	public void connect(String host, String port) throws Exception {
		connect(host, port, null);
	}

	// either host,port will be set, or name
	private void connect(String host, String port, String name)
			throws Exception {
		// connect to JVM
		boolean useSocket = (port != null);

		VirtualMachineManager manager = Bootstrap.virtualMachineManager();
		List<AttachingConnector> connectors = manager.attachingConnectors();
		AttachingConnector connector = null;
		// System.err.println("Connectors available");
		for (int i = 0; i < connectors.size(); i++) {
			AttachingConnector tmp = (AttachingConnector) connectors.get(i);
			// System.err.println("conn "+i+"  name="+tmp.name()+" transport="+tmp.transport().name()+
			// " description="+tmp.description());
			if (!useSocket && tmp.transport().name().equals("dt_shmem")) {
				connector = tmp;
				break;
			}
			if (useSocket && tmp.transport().name().equals("dt_socket")) {
				connector = tmp;
				break;
			}
		}
		if (connector == null) {
			throw new IllegalStateException(
					"Cannot find shared memory connector");
		}

		Map<String, Argument> arguments = connector.defaultArguments();
		// Iterator iter = args.keySet().iterator();
		// while (iter.hasNext()) {
		// Object key = iter.next();
		// Object val = args.get(key);
		// System.err.println("key:"+key.toString()+" = "+val.toString());
		// }
		Connector.Argument argument = null;
		// use name if using dt_shmem
		if (!useSocket) {
			argument = (Connector.Argument) arguments.get("name");
			argument.setValue(name);
		}
		// use port if using dt_socket
		else {
			argument = (Connector.Argument) arguments.get("port");
			argument.setValue(port);
		}
		vm = connector.attach(arguments);

		// query capabilities
		if (!vm.canRedefineClasses()) {
			throw new Exception("JVM doesn't support class replacement");
		}
		// if (!vm.canAddMethod()) {
		// throw new Exception("JVM doesn't support adding method");
		// }
		// System.err.println("attached!");
	}

	public void replace(File classFile, String className) throws Exception {
		// load class(es)
		byte[] classBytes = loadClassFile(classFile);
		// redefine in JVM
		List classes = vm.classesByName(className);

		// if the class isn't loaded on the VM, can't do the replace.
		if (classes == null || classes.size() == 0)
			return;

		for (int i = 0; i < classes.size(); i++) {
			ReferenceType refType = (ReferenceType) classes.get(i);
			HashMap map = new HashMap();
			map.put(refType, classBytes);
			vm.redefineClasses(map);
		}
		// System.err.println("class replaced!");
	}

	public void disconnect() throws Exception {
		vm.dispose();
	}

	private byte[] loadClassFile(File classFile) throws IOException {
		DataInputStream in = new DataInputStream(new FileInputStream(classFile));

		byte[] ret = new byte[(int) classFile.length()];
		in.readFully(ret);
		in.close();

		// System.err.println("class file loaded.");
		return ret;
	}
}
