package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.*;

public class RankTest {
    @DisplayName("맞힌갯수와 등수 매치")
    @ParameterizedTest
    @CsvSource(value = {"3, true, FIFTH", "6, true, FIRST", "5, false, THIRD"})
    void findTest(int count, boolean bonus, Rank values) {
        Rank rank = Rank.findRank(count, bonus);
        assertThat(rank == values).isTrue();
    }

    @Test
    void isSecondRank() {
        Rank result = Rank.THIRD;
        assertThat(result.checkSecondOrNot(true)).isEqualTo(Rank.SECOND);

        result = Rank.FIFTH;
        assertThat(result.checkSecondOrNot(true)).isEqualTo(Rank.FIFTH);
    }
}