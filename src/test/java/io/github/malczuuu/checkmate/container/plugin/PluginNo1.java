package io.github.malczuuu.checkmate.container.plugin;

import io.github.malczuuu.checkmate.spi.ImageNamePlugin;
import java.util.Optional;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class PluginNo1 implements ImageNamePlugin {

  @Override
  public Optional<String> getImageName(String service) {
    if ("svc-alpha".equals(service)) {
      return Optional.of("custom/alpha:b");
    }
    if ("svc-beta".equals(service)) {
      return Optional.of("custom/beta:b");
    }
    return Optional.empty();
  }

  @Override
  public int getPriority() {
    return 0;
  }
}
