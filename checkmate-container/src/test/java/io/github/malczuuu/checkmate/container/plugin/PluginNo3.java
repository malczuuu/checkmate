package io.github.malczuuu.checkmate.container.plugin;

import io.github.malczuuu.checkmate.spi.ImageNamePlugin;
import java.util.Optional;

public class PluginNo3 implements ImageNamePlugin {

  @Override
  public Optional<String> getImageName(String service) {
    if ("kafka".equals(service)) {
      return Optional.of("custom/kafka:a");
    }
    return Optional.empty();
  }

  @Override
  public int getPriority() {
    return 10;
  }
}
