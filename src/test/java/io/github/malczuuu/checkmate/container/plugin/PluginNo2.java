package io.github.malczuuu.checkmate.container.plugin;

import io.github.malczuuu.checkmate.spi.ImageNamePlugin;
import java.util.Optional;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class PluginNo2 implements ImageNamePlugin {

  @Override
  public Optional<String> getImageName(String service) {
    if ("svc-gamma".equals(service)) {
      return Optional.of("custom/gamma:c");
    }
    return Optional.empty();
  }

  @Override
  public int getPriority() {
    return 0;
  }
}
