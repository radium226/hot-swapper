package radium.os;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import com.sun.nio.file.ExtendedWatchEventModifier;
import java.nio.file.FileVisitResult;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import java.nio.file.SimpleFileVisitor;

import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class Watcher {

    private WatchEventHandler handler;
    private Path path;

    private Watcher(Path path) {
        super();

        this.path = path;
    }

    public static Watcher watch(Path path) {
        return new Watcher(path);
    }

    public void with(WatchEventHandler handler) throws IOException {
        this.handler = handler;

        doWatchFolder();
    }

    private static Map<WatchKey, Path> registerRecusively(final Path parentPath, final WatchService watchService) throws IOException {
        final Map<WatchKey, Path> keys = new HashMap<>();
        Files.walkFileTree(parentPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path childPath, BasicFileAttributes attributes) throws IOException {
                try {
                    WatchKey key = childPath.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                    keys.put(key, childPath);
                    return FileVisitResult.CONTINUE;
                } catch (NullPointerException e) {
                    e.printStackTrace(System.err);
                    return FileVisitResult.CONTINUE;
                }
            }
        });
        return keys;
    }
    
    private void doWatchFolder() throws IOException {
        FileSystem fileSytem = FileSystems.getDefault();
        WatchService watchService = fileSytem.newWatchService();
        Path parentPath = this.path;
        
        Map<WatchKey, Path> keys = new HashMap<>();
        keys.putAll(registerRecusively(parentPath, watchService));
        
        boolean valid = false;
        do {
            WatchKey key;
            try {
                key = watchService.take();
            } catch (InterruptedException x) {
                return;
            }
            
            Path path = keys.get(key);
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if (kind == OVERFLOW) {
                    continue;
                }

                WatchEvent<Path> eventWithPath = (WatchEvent<Path>) event;
                Path fileName = eventWithPath.context();
                Path filePath = parentPath.resolve(fileName);

                if (Files.isDirectory(path, NOFOLLOW_LINKS) && kind == ENTRY_CREATE) {
                    keys.putAll(registerRecusively(path, watchService));
                } else if (!Files.isDirectory(filePath)) {
                    handler.handleEvent(eventWithPath);
                }
            }
            valid = key.reset();
            if (!valid) {
                keys.remove(key);
            }
        } while (!keys.isEmpty());
    }

}
