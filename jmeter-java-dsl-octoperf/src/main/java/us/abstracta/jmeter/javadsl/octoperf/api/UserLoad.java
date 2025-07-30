package us.abstracta.jmeter.javadsl.octoperf.api;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import us.abstracta.jmeter.javadsl.core.threadgroups.BaseThreadGroup.SampleErrorAction;
import us.abstracta.jmeter.javadsl.core.threadgroups.DslDefaultThreadGroup;

public class UserLoad {

  // we don't need getters since Jackson gets the values from fields
  private final String name = "";
  private final String virtualUserId;
  private final String providerId;
  private final String region;
  private final UserLoadStrategy strategy;
  private final BandwidthSettings bandwidth = new BandwidthSettings();
  private final BrowserSettings browser = new BrowserSettings();
  private final DnsSettings dns = new DnsSettings();
  private final ThinkTimeSettings thinktime = new ThinkTimeSettings();
  private final MemorySettings memory = new MemorySettings();
  private final JtlSettings jtl = new JtlSettings();
  private final PropertiesSettings properties = new PropertiesSettings();
  private final SetUpTearDownSettings setUp = null;
  private final SetUpTearDownSettings tearDown = null;

  public UserLoad() {
    virtualUserId = null;
    providerId = null;
    region = null;
    strategy = null;
  }

  public UserLoad(String virtualUserId, String providerId, String region,
      UserLoadStrategy strategy) {
    this.virtualUserId = virtualUserId;
    this.providerId = providerId;
    this.region = region;
    this.strategy = strategy;
  }

  @JsonTypeInfo(use = NAME, include = PROPERTY)
  @JsonSubTypes({
      @JsonSubTypes.Type(UserLoadRampUp.class)
  })
  public abstract static class UserLoadStrategy {

  }

  @JsonTypeName("UserLoadRampup")
  public static class UserLoadRampUp {

    private static final int RAMP_UP_STAGE = 1;
    private static final int HOLD_FOR_STAGE = 2;

    private final int count;
    private final long rampUp;
    private final long duration;

    public UserLoadRampUp(int count, long rampUp, long duration) {
      this.count = count;
      this.rampUp = rampUp;
      this.duration = duration;
    }

    public static UserLoadRampUp fromThreadGroup(DslDefaultThreadGroup threadGroup) {
      return new UserLoadRampUp(
          (Integer) threadGroup.getStages().get(RAMP_UP_STAGE).threadCount(),
          threadGroup.getStages().get(RAMP_UP_STAGE).duration().toMillis(),
          threadGroup.getStages().get(HOLD_FOR_STAGE).duration() != null
              ? threadGroup.getStages().get(HOLD_FOR_STAGE).duration().toMillis()
              : 0);
    }

    public int getCount() {
      return count;
    }

    public long getRampUp() {
      return rampUp;
    }

    public long getDuration() {
      return duration;
    }

  }

}

  public static class SetUpTearDownSettings {

  }

  public static class BandwidthSettings {

    private final String name = "UNLIMITED";
    private final int bitsPerSecond = 0;

  }

  public static class BrowserSettings {

    private final String name = "AS_RECORDED";
    private final String userAgent = "";
    private final CacheManager cache = new CacheManager();
    private final CookiesManager cookies = new CookiesManager();

  }

  public static class CacheManager {

    private final int cacheSize = 5000;
    private final boolean clearCacheOnEachIteration = true;
    private final boolean useCacheControlHeaders = false;

  }

  public static class CookiesManager {

    private final boolean clearOnEachIteration = true;
    private final String policy = "STANDARD";

  }

  public static class DnsSettings {

    private final boolean clearEachIteration = false;
    private final List<String> servers = Collections.emptyList();
    private final Map<String, String> staticHosts = Collections.emptyMap();

  }

  public static class ThinkTimeSettings {

    private final ThinkTime thinktime = null;
    private final PacingSettings pacing = null;

  }

  public static class ThinkTime {

  }

  public static class PacingSettings {

  }

  public static class MemorySettings {

    private final Double vuMb = null;

  }

  public static class JtlSettings {

    private final String type = "ALL";
    private final List<String> settings = Collections.emptyList();

  }

  public static class PropertiesSettings {

    private final Map<String, String> map = Collections.emptyMap();

  }

}
