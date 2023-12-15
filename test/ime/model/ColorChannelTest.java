package ime.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * to test color channel enum.
 */
public class ColorChannelTest {

  /**
   * test for color channel size.
   */
  @Test
  public void testColorChannelSize() {

    List<ColorChannel> filteredColorChannel = Arrays.asList(ColorChannel.values())
            .stream().filter(channel -> channel.rgb.length != 3).collect(Collectors.toList());
    assertEquals(0, filteredColorChannel.size());
  }
}