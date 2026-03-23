package io.github.malczuuu.checkmate.container.plugin;

import io.github.malczuuu.checkmate.spi.ImageNamePlugin;
import java.util.Optional;

/** Test plugin C - same priority as B but different service support. */
public class PluginNo2 implements ImageNamePlugin {

  @Override
  public Optional<String> getImageName(String service) {
    if ("redis".equals(service)) {
      return Optional.of("custom/redis:c");
    }
    return Optional.empty();
  }

  @Override
  public int getPriority() {
    return 0;
  }
}
