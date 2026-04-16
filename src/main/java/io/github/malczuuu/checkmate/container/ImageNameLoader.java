package io.github.malczuuu.checkmate.container;

import io.github.malczuuu.checkmate.spi.ImageNamePlugin;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

final class ImageNameLoader {

  static String getImageName(String service, String defaultValue) {
    return InstanceHolder.INSTANCE.getImageNameInternal(service, defaultValue);
  }

  private final List<ImageNamePlugin> plugins;
  private final Map<String, String> imageNameCache;

  private ImageNameLoader() {
    plugins =
        ServiceLoader.load(ImageNamePlugin.class).stream()
            .map(ServiceLoader.Provider::get)
            .sorted(Comparator.comparingInt(ImageNamePlugin::getPriority))
            .toList();
    imageNameCache = new ConcurrentHashMap<>();
  }

  private String getImageNameInternal(String service, String defaultValue) {
    return imageNameCache.computeIfAbsent(service, s -> findServiceImageName(s, defaultValue));
  }

  private String findServiceImageName(String service, String defaultValue) {
    return plugins.stream()
        .map(p -> p.getImageName(service))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .findFirst()
        .orElse(defaultValue);
  }

  private static final class InstanceHolder {
    private static final ImageNameLoader INSTANCE = new ImageNameLoader();
  }
}
