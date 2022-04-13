package lotto.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LottoTicket {
    private final List<LottoNumbers> lottoTicket;

    private LottoTicket(final List<LottoNumbers> lottoTicket) {
        this.lottoTicket = lottoTicket;
    }

    public static LottoTicket createAutoLottoTicket(int count) {
        return new LottoTicket(generateTickets(count));
    }

    private static List<LottoNumbers> generateTickets(int count) {
        List<LottoNumbers> lottoTicket = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            lottoTicket.add(new LottoNumbers());
        }
        return lottoTicket;
    }

    public static LottoTicket createManualLottoTicket(List<LottoNumbers> lottoTicket) {
        return new LottoTicket(lottoTicket);
    }

    public List<LottoNumbers> getLottoTicket() {
        return Collections.unmodifiableList(lottoTicket);
    }

    public WinningResult calculateWinningStatistic(WinningNumbers winningNumbers) {
        List<Ranking> rankings = lottoTicket.stream()
                .map(winningNumbers::calculateRanking)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());
        return new WinningResult(rankings);
    }

    public void addLottoTicket(LottoTicket otherLottoTicket) {
        lottoTicket.addAll(otherLottoTicket.getLottoTicket());
    }
}
