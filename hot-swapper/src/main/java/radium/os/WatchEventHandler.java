package radium.os;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public interface WatchEventHandler {

    public void handleEvent(WatchEvent<Path> event);

}
